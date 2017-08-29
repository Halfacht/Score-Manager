package com.halfachtempire.android.scoremanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mNumberOfPlayersEditText;

    private final static String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initReferences(); // Also sets On Click Listeners
    }

    private void initReferences() {

        // Set Focus to the EditText and set a Listener
        mNumberOfPlayersEditText = (EditText) findViewById(R.id.et_number_of_players);
        mNumberOfPlayersEditText.requestFocus();
        if (mNumberOfPlayersEditText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        mNumberOfPlayersEditText.setOnKeyListener(etKeyListener);

        // Get a reference to the button and set the onClickListener
        Button bGoToPlayerNames = (Button) findViewById(R.id.b_go_to_player_names);
        bGoToPlayerNames.setOnClickListener(bClickListener);
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
        // Do nothing if there is no input
        if (mNumberOfPlayersEditText.getText().length() > 0) {

            // Get the number of player and return if there aren't any
            int numOfPlayers = 0;
            try {
                numOfPlayers = Integer.parseInt(mNumberOfPlayersEditText.getText().toString());
            } catch (Exception ex) {
                Log.e(LOG_TAG, "Failed to parse Number of Players text to number: " + ex.getMessage());
            }
            if (numOfPlayers == 0) return;

            // Start next activity
            Intent intent = new Intent(this, SetPlayerNamesActivity.class);
            intent.putExtra("numOfPlayers", numOfPlayers);
            startActivity(intent);
        }
    }
}
