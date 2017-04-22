package com.halfachtempire.android.scoremanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.halfachtempire.android.scoremanager.Adapters.ScoreByOneAdapter;
import com.halfachtempire.android.scoremanager.Data.ScoreContract;
import com.halfachtempire.android.scoremanager.Data.ScoreDbHelper;

public class ScoreByOneActivity extends AppCompatActivity {

    private ScoreByOneAdapter mAdapter;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_by_one);

        // Get an instance of the RecyclerView and set a LayoutManager
        RecyclerView scoreByOneRecyclerView = (RecyclerView) findViewById(R.id.score_by_one_list_view); // In Source: this.findViewById
        scoreByOneRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a DB helper (this will create the DB if run for the first time)
        // Get a reference to the mDb until pause or killed.
        ScoreDbHelper dbHelper = new ScoreDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        // Get all player info from the database and save in a cursor
        Cursor cursor = getPlayerScores();

        // Create an adapter for that cursor to display the data and link to the RecyclerView
        mAdapter = new ScoreByOneAdapter(this, cursor);
        scoreByOneRecyclerView.setAdapter(mAdapter);
    }

    private Cursor getPlayerScores() {
        return mDb.query(
                ScoreContract.ScoreEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ScoreContract.ScoreEntry._ID
        );
    }
}
