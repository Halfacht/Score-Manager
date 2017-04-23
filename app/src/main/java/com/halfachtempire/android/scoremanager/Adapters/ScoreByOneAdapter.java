package com.halfachtempire.android.scoremanager.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfachtempire.android.scoremanager.Data.ScoreContract;
import com.halfachtempire.android.scoremanager.R;

public class ScoreByOneAdapter extends RecyclerView.Adapter<ScoreByOneAdapter.ScoreByOneViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public ScoreByOneAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public ScoreByOneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.score_by_one_list_item, parent, false);
        return new ScoreByOneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScoreByOneViewHolder holder, int position) {
        // return is there is nothing at that cursor position
        if (!mCursor.moveToPosition(position)) return;

        // Get the values from the database
        String playerName   = mCursor.getString(mCursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_PLAYER_NAME));
        int playerScore     = mCursor.getInt(mCursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_PLAYER_SCORE));

        Log.v("playerName and position", playerName + " " + position);

        // Set the TextViews to the values
        int playerNumber = position + 1;
        holder.numberTextView.setText(String.valueOf(playerNumber));
        holder.playerNameTextView.setText(playerName);
        holder.playerScoreTextView.setText(String.valueOf(playerScore));

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class ScoreByOneViewHolder extends RecyclerView.ViewHolder {

        TextView numberTextView;
        TextView playerNameTextView;
        TextView playerScoreTextView;

        public ScoreByOneViewHolder(View itemView) {
            super(itemView);
            numberTextView      = (TextView) itemView.findViewById(R.id.tv_player_number);
            playerNameTextView  = (TextView) itemView.findViewById(R.id.tv_player_name);
            playerScoreTextView = (TextView) itemView.findViewById(R.id.tv_player_score);

        }
    }
}
