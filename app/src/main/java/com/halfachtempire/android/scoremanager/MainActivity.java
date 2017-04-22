package com.halfachtempire.android.scoremanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.halfachtempire.android.scoremanager.Data.ScoreContract;
import com.halfachtempire.android.scoremanager.Data.ScoreDbHelper;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    private EditText mNumberOfPlayersEditText;
    private Button mGoToPlayerNamesButton;

    private final static String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database
        ScoreDbHelper dbHelper = new ScoreDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        // Drops old SQL Table
        newGame();

        // Set Focus to the EditText and set a Listener
        mNumberOfPlayersEditText = (EditText) findViewById(R.id.et_number_of_players);
        mNumberOfPlayersEditText.requestFocus();
        if (mNumberOfPlayersEditText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        mNumberOfPlayersEditText.setOnKeyListener(etKeyListener);

        // Get a reference to the button and set the onClickListener
        mGoToPlayerNamesButton = (Button) findViewById(R.id.b_go_to_player_names);
        mGoToPlayerNamesButton.setOnClickListener(bClickListener);
    }


    View.OnKeyListener etKeyListener =  new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        goToPlayerNames();
                        return true;
                    default:
                        break;
                }
            } // close if
            return false;
        } // close inner method
    };

    View.OnClickListener bClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            goToPlayerNames();
        }
    };

    public void goToPlayerNames() {
        if (mNumberOfPlayersEditText.getText().length() > 0) {
            // Get the number of player and return if there aren't any
            int numOfPlayers = 0;
            try {
                numOfPlayers = Integer.parseInt(mNumberOfPlayersEditText.getText().toString());
            } catch (Exception ex) {
                Log.e(LOG_TAG, "Failes to parse Number of Players text to number: " + ex.getMessage());
            }
            if (numOfPlayers == 0) return;

            // Add the numOfPlayers to the database
            addNumOfPlayers(numOfPlayers);

            // Start next activity
            Intent intent = new Intent(this, SetPlayerNamesActivity.class);
            startActivity(intent);
        }
    }

    public void addNumOfPlayers(int numOfPlayers) {
        ContentValues cv = new ContentValues();
        cv.put(ScoreContract.GameEntry.COLUMN_NUMBER_OF_PLAYERS, numOfPlayers);
        mDb.insert(ScoreContract.GameEntry.TABLE_NAME, null, cv);
        //return mDb.update(ScoreContract.GameEntry.TABLE_NAME, cv, "_id=" + 0, null);
    }

//    public long addNumOfPlayers(int numOfPlayers) {
//        ContentValues cv = new ContentValues();
//        cv.put(ScoreContract.GameEntry.COLUMN_NUMBER_OF_PLAYERS, numOfPlayers);
//        return mDb.insert(ScoreContract.GameEntry.TABLE_NAME, null, cv);
//        //return mDb.update(ScoreContract.GameEntry.TABLE_NAME, cv, "_id=" + 0, null);
//    }

    public void newGame() {
        mDb.delete(ScoreContract.ScoreEntry.TABLE_NAME, null, null);
        mDb.execSQL("DELETE FROM " + ScoreContract.GameEntry.TABLE_NAME);
    }
}
