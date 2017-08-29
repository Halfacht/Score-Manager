package com.halfachtempire.android.scoremanager.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.halfachtempire.android.scoremanager.R;
import com.halfachtempire.android.scoremanager.data.ScoreContract;

public class ScoreByOneAdapter extends RecyclerView.Adapter<ScoreByOneAdapter.ScoreByOneViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public String[] mPlayerNames;
    public int[] mPlayerScores;

    /**
     * The Constructor
     */
    public ScoreByOneAdapter(Context context, Cursor cursor) {

        this.mContext = context;
        this.mCursor = cursor;

        mPlayerNames = new String[getItemCount()];
        mPlayerScores = new int[getItemCount()];

        getPlayerData();
    }

    private void getPlayerData() {
        for (int i = 0; i < getItemCount(); i++) {
            if (!mCursor.moveToPosition(i)) return;
            mPlayerNames[i] = mCursor.getString(mCursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_PLAYER_NAME));
            mPlayerScores[i] = mCursor.getInt(mCursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_PLAYER_SCORE));
        }
    }

    @Override
    public ScoreByOneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.score_by_one_list_item, parent, false);

        return new ScoreByOneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScoreByOneViewHolder holder, int position) {
        // Set the TextViews to the values
        int playerNumber = position + 1;
        holder.numberTextView.setText(String.valueOf(playerNumber));
        holder.playerNameTextView.setText(mPlayerNames[holder.getAdapterPosition()]);
        holder.playerScoreTextView.setText(String.valueOf(mPlayerScores[holder.getAdapterPosition()]));
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor c) {

        // Return is this cursor is the same as the previous cursor
        if (mCursor == c) return;

        this.mCursor = c;

        // Check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
    }

    class ScoreByOneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView numberTextView;
        private TextView playerNameTextView;
        private TextView playerScoreTextView;
        private Button incrementButton;
        private Button decrementButton;

        public ScoreByOneViewHolder(View itemView) {
            super(itemView);
            numberTextView = (TextView) itemView.findViewById(R.id.tv_player_number);
            playerNameTextView = (TextView) itemView.findViewById(R.id.tv_player_name);
            playerScoreTextView = (TextView) itemView.findViewById(R.id.tv_player_score);
            incrementButton = (Button) itemView.findViewById(R.id.b_increment_score);
            decrementButton = (Button) itemView.findViewById(R.id.b_decrement_score);

            incrementButton.setOnClickListener(this);
            decrementButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            switch (v.getId()) {
                case R.id.b_increment_score:
                    mPlayerScores[position]++;
                    updatePlayerScore(position);
                    break;
                case R.id.b_decrement_score:
                    mPlayerScores[position]--;
                    updatePlayerScore(position);
                    break;
            }
        }

        private void updatePlayerScore(int position) {
            ContentValues cv = new ContentValues();
            cv.put(ScoreContract.ScoreEntry.COLUMN_PLAYER_SCORE, mPlayerScores[position]);

            notifyItemChanged(position);

        }
    } // Close inner class: ViewHolder
}
