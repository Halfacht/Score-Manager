package com.halfachtempire.android.scoremanager;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.halfachtempire.android.scoremanager.adapters.SetPlayerNamesAdapter;
import com.halfachtempire.android.scoremanager.data.ScoreContract;

public class SetPlayerNamesActivity extends AppCompatActivity {

    private SetPlayerNamesAdapter mAdapter;
    private RecyclerView mSetPlayerNameRecyclerView;

    private int numOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_player_names);

        initReferences(); // Also sets onClickListeners

        // String Array to hold the Player Names
        numOfPlayers = getNumOfPlayersFromIntent();

        // Set the adapter with the amount of players
        mAdapter = new SetPlayerNamesAdapter(getNumOfPlayersFromIntent());
        mSetPlayerNameRecyclerView.setAdapter(mAdapter);
    }

    private void initReferences() {

        // Go to the next activity when the button is clicked
        Button button = (Button) findViewById(R.id.b_post_player_names);
        button.setOnClickListener(bClickListener);

        // Get a reference to the recyclerview and set the layout manager
        mSetPlayerNameRecyclerView = (RecyclerView) this.findViewById(R.id.set_players_names_list_view);
        mSetPlayerNameRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private int getNumOfPlayersFromIntent() {

        return getIntent().getIntExtra("numOfPlayers", 1);
    }

    public void setPlayerNames() {

        // Create a ContentValues array
        ContentValues[] contentValuesArray = new ContentValues[numOfPlayers];
        String playerName;

        for (int i = 0; i < numOfPlayers; i++) {
            playerName = mAdapter.mPlayerNameSet[i];

            // If there wasn't a name enter give the default value
            if (playerName.equals("")) {
                playerName = getString(R.string.default_player_name);
            }

            // Add the name to a list.
            ContentValues cv = new ContentValues();

            cv.put(ScoreContract.ScoreEntry.COLUMN_PLAYER_NAME, playerName);

            contentValuesArray[i] = cv;
        }

        getContentResolver().bulkInsert(ScoreContract.ScoreEntry.CONTENT_URI, contentValuesArray);

    } // close setPlayerNames()
}
