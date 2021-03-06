package com.halfachtempire.android.scoremanager.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

public class ScoreProvider extends ContentProvider {

    // Constants used to match URI with the data they are looking for.
    public static final int CODE_PLAYERS = 100;
    public static final int CODE_PLAYER_WITH_ID = 101;

    // The URI Matcher used by this content provider.
    // The leading 's' in this variable name signifies that this UriMatcher is a static member variable.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ScoreDbHelper mDbHelper;

    // Creates a UriMatcher that will match each URi to the constants defined above.
    private static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ScoreContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, ScoreContract.PATH_PLAYERS, CODE_PLAYERS);
        uriMatcher.addURI(authority, ScoreContract.PATH_PLAYERS + "/#", CODE_PLAYER_WITH_ID);

        return uriMatcher;
    }

    /**
     * Initialize content provider on startup.
     *
     * @return true if the provider was succesfully loader, false otherwise
     */
    @Override
    public boolean onCreate() {

        mDbHelper = new ScoreDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        switch(sUriMatcher.match(uri)) {

            case CODE_PLAYERS:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long id = db.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, value);
                        // test
                        Cursor cursor = db.query(ScoreContract.ScoreEntry.TABLE_NAME,
                                null,
                                null,
                                null,
                                null,
                                null,
                                ScoreContract.ScoreEntry._ID);
                        if (cursor.moveToFirst()) {
                            do {
                                String data = cursor.getString(cursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_PLAYER_NAME));
                                System.out.println(data);
                            } while(cursor.moveToNext());
                        }

                        if (id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                        db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor rCursor;

        switch(sUriMatcher.match(uri)) {
            case CODE_PLAYERS:
                rCursor = db.query(ScoreContract.ScoreEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_PLAYER_WITH_ID:
                String playerId = uri.getLastPathSegment();
                selectionArgs = new String[]{playerId};

                rCursor = db.query(ScoreContract.ScoreEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        rCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return rCursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Uri rUri; // Uri to be returned;
        long id;

        switch (sUriMatcher.match(uri)) {
            case CODE_PLAYERS:
                id = db.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, values);

                if ( id > 0 ) {
                    rUri = ContentUris.withAppendedId(ScoreContract.ScoreEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        // Keep track of the number of deleted items
        int rowsDeleted = 0;


        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowsUpdated = 0;

        switch (sUriMatcher.match(uri)) {
            case CODE_PLAYERS:
                rowsUpdated = db.update(ScoreContract.ScoreEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case CODE_PLAYER_WITH_ID:
                String id = uri.getLastPathSegment();
                rowsUpdated = db.update(ScoreContract.ScoreEntry.TABLE_NAME,
                        values,
                        ScoreContract.ScoreEntry._ID + "=" + id,
                        null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
