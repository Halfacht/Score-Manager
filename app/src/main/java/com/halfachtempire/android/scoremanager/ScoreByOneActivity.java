package com.halfachtempire.android.scoremanager;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.halfachtempire.android.scoremanager.adapters.ScoreByOneAdapter;
import com.halfachtempire.android.scoremanager.data.ScoreContract;

public class ScoreByOneActivity extends AppCompatActivity
{

    private RecyclerView mRecyclerView;
    private ScoreByOneAdapter mAdapter;

//    private ProgressBar mLoadingIndicator;
//    private static final int LOADER_ID = 0;
//    private static final String TAG = ScoreByOneActivity.class.getSimpleName();


    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_by_one);

//        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        initRecyclerView();

    }

    private void initRecyclerView() {

        // Get an instance of the RecyclerView and set a LayoutManager
        mRecyclerView = (RecyclerView) findViewById(R.id.score_by_one_list_view); // In Source: this.findViewById
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = getAllPlayers();

        // Create an adapter for that cursor to display the data and link to the RecyclerView
        mAdapter = new ScoreByOneAdapter(this, cursor);
        mRecyclerView.setAdapter(mAdapter);
    }

    private Cursor getAllPlayers() {
        return getContentResolver().query(ScoreContract.ScoreEntry.CONTENT_URI,
                null,
                null,
                null,
                ScoreContract.ScoreEntry._ID);
    }

//    private void initMyLoader() {
//
//        LoaderManager.LoaderCallbacks<Cursor> callback = ScoreByOneActivity.this;
//        getSupportLoaderManager().initLoader(LOADER_ID, null, callback);
//    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
//        return new AsyncTaskLoader<Cursor>(this) {
//
//            Cursor mPlayerData = null;
//
//            @Override
//            protected void onStartLoading() {
//                if (mPlayerData != null) {
//                    deliverResult(mPlayerData);
//                } else {
//                    mLoadingIndicator.setVisibility(View.VISIBLE);
//                    forceLoad();
//                }
//            }
//
//            @Override
//            public Cursor loadInBackground() {
//
//                try {
//                    return getContentResolver().query(ScoreContract.ScoreEntry.CONTENT_URI,
//                            null,
//                            null,
//                            null,
//                            ScoreContract.ScoreEntry._ID);
//
//                } catch (Exception e) {
//                    Log.e(TAG,  "Failed to asynchronously load data.");
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//
//            @Override
//            public void deliverResult(Cursor data) {
//                mPlayerData = data;
//                super.deliverResult(data);
//            }
//        };
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mLoadingIndicator.setVisibility(View.INVISIBLE);
//        mAdapter.swapCursor(data);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        mAdapter.swapCursor(null);
//    }

    //    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // Save RecyclerView state
//        mBundleRecyclerViewState = new Bundle();
//        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
//        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);

//        // Restore RecyclerView state
//        if (mBundleRecyclerViewState != null) {
//            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
//            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
//        }
//    }
}
