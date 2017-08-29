package com.halfachtempire.android.scoremanager.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ScoreContract {

    public static final String CONTENT_AUTHORITY = "com.halfachtempire.android.scoremanager";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PLAYERS = "players";

    private ScoreContract() {}

    public static final class ScoreEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLAYERS).build();

        public static final String TABLE_NAME = "scoreList";

        public static final String COLUMN_PLAYER_NAME = "playerName";
        public static final String COLUMN_PLAYER_SCORE = "playerScore";
    }
//    public static final class GameEntry implements BaseColumns {

//        public static final String TABLE_NAME = "gameList";

//        public static final String COLUMN_NUMBER_OF_PLAYERS = "numberOfPlayers";
//        public static final String COLUMN_GAME = "gameName";
//        public static final String COLUMN_GAME_TYPE = "gameType";
//        public static final String COLUMN_SCORE_TYPE = "scoreType";
//    }
}
