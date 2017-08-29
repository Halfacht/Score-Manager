package com.halfachtempire.android.scoremanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.halfachtempire.android.scoremanager.data.ScoreContract.ScoreEntry;

public class ScoreDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "scoreManager.db";
    private static final int DATABASE_VERSION = 5;

    // Constructor
    public ScoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SCORE_TABLE = "CREATE TABLE " + ScoreEntry.TABLE_NAME + " (" +
                ScoreEntry._ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT, "    +
                ScoreEntry.COLUMN_PLAYER_NAME   + " TEXT NOT NULL, "                        +
                ScoreEntry.COLUMN_PLAYER_SCORE  + " INT "                                   +
                ");";
        db.execSQL(SQL_CREATE_SCORE_TABLE);

//        final String SQL_CREATE_GAME_TABLE = " CREATE TABLE " + GameEntry.TABLE_NAME + " ("     +
//                GameEntry._ID                       + " INTEGER PRIMARY KEY AUTOINCREMENT, "    +
//                GameEntry.COLUMN_NUMBER_OF_PLAYERS  + " INTEGER NOT NULL, "                     +
//                GameEntry.COLUMN_GAME               + " TEXT, "                                 +
//                GameEntry.COLUMN_GAME_TYPE          + " TEXT, "                                 +
//                GameEntry.COLUMN_SCORE_TYPE         + " TEXT "                                  +
//                ");";
//        db.execSQL(SQL_CREATE_GAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ScoreDbHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " +
                    newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + GameEntry.TABLE_NAME);
        onCreate(db);
    }
}
