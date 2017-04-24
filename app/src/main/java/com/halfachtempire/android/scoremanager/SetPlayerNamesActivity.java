package com.halfachtempire.android.scoremanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.halfachtempire.android.scoremanager.Adapters.SetPlayerNamesAdapter;
import com.halfachtempire.android.scoremanager.Data.ScoreContract;
import com.halfachtempire.android.scoremanager.Data.ScoreDbHelper;

import java.util.ArrayList;
import java.util.List;

public class SetPlayerNamesActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private RecyclerView mSetPlayerNameRecyclerView;
    private SetPlayerNamesAdapter mAdapter;
    public String[] mPlayerNameset;

    private int numOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_player_names);

        // Go to the next activity when the button is clicked
        Button button = (Button) findViewById(R.id.b_post_player_names);
        button.setOnClickListener(bClickListener);

        // Get a reference to the recyclerview and set the layout manager
        mSetPlayerNameRecyclerView = (RecyclerView) this.findViewById(R.id.set_players_names_list_view);
        mSetPlayerNameRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Database
        ScoreDbHelper dbHelper = new ScoreDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        // Get the number of players
        numOfPlayers = getNumOfPlayers();
        mPlayerNameset = new String[numOfPlayers];

        // Set the adapter with the amount of players
        mAdapter = new SetPlayerNamesAdapter((mPlayerNameset));
        mSetPlayerNameRecyclerView.setAdapter(mAdapter);
        //mSetPlayerNameRecyclerView.setItemViewCacheSize(numOfPlayers);  // Saves the values in the EditText when scrolling.
        //mSetPlayerNameRecyclerView.getRecycledViewPool().setMaxRecycledViews(R.id.et_set_player_name, 0);

    }

    View.OnClickListener bClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // Update the Database
            setPlayerNames();
            // Start the game
            Intent intent = new Intent(SetPlayerNamesActivity.this, ScoreByOneActivity.class);
            startActivity(intent);

        }
    };

    public int getNumOfPlayers() {
        numOfPlayers = 1;
        Cursor cursor = mDb.query(
                ScoreContract.GameEntry.TABLE_NAME,
                null, null,  null, null, null, null
        );
        cursor.moveToLast();
        numOfPlayers = cursor.getInt(cursor.getColumnIndex(ScoreContract.GameEntry.COLUMN_NUMBER_OF_PLAYERS));

        return numOfPlayers;
    }

    public void setPlayerNames() {
        List<ContentValues> list = new ArrayList<>();
        mPlayerNameset = mAdapter.mDataset;
        String playerName;

        for (int i = 0; i < numOfPlayers; i++) {
            playerName = mPlayerNameset[i];

            // If there wasn't a name enter give the default value
            if (playerName.equals("")) {playerName = getString(R.string.default_player_name);}

            // Add the name to a list.
            ContentValues cv = new ContentValues();
            cv.put(ScoreContract.ScoreEntry.COLUMN_PLAYER_NAME, playerName);
            list.add(cv);

        }
        // Add all the names to the database at once
        try {
            mDb.beginTransaction();
            // Clear the table
            mDb.delete(ScoreContract.ScoreEntry.TABLE_NAME, null, null);
            // iterate through the list and add one by one
            for (ContentValues c:list) {
                mDb.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, c);
            }
            mDb.setTransactionSuccessful();
        } catch (SQLException e) {
            // too bad
        } finally {
            mDb.endTransaction();
        }

    } // close setPlayerNames()
}
