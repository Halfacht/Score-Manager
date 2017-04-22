package com.halfachtempire.android.scoremanager.Data;

import android.provider.BaseColumns;

public class ScoreContract {

    public static final class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "Scorelist";
        public static final String COLUMN_PLAYER_NAME = "playerName";
        public static final String COLUMN_PLAYER_SCORE = "playerScore";
    }
    public static final class GameEntry implements BaseColumns {
        public static final String TABLE_NAME = "Gamelist";
        public static final String COLUMN_NUMBER_OF_PLAYERS = "numOfPlayers";
        public static final String COLUMN_GAME = "gameName";
        public static final String COLUMN_GAME_TYPE = "gameType";
        public static final String COLUMN_SCORE_TYPE = "scoreType";
    }
}
