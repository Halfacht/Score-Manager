package com.halfachtempire.android.scoremanager.Adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.halfachtempire.android.scoremanager.Data.ScoreContract;
import com.halfachtempire.android.scoremanager.R;

public class ScoreByOneAdapter extends RecyclerView.Adapter<ScoreByOneAdapter.ScoreByOneViewHolder> {

    private Cursor mCursor;

    public int[] mPlayerScores;
    public String[] mPlayerNames;

    public ScoreByOneAdapter(Cursor cursor) {
        this.mCursor = cursor;
        this.mPlayerNames = new String[getItemCount()];
        this.mPlayerScores = new int[getItemCount()];

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
        return mCursor.getCount();
    }

    class ScoreByOneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView numberTextView;
        private TextView playerNameTextView;
        private TextView playerScoreTextView;
        private Button incrementButton;
        private Button decrementButton;

        public Toast mToast;

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
            if (mToast != null) {
                mToast.cancel();
            }
            int position = getAdapterPosition();

            switch (v.getId()) {
                case R.id.b_increment_score:
                    mPlayerScores[position]++;
                    notifyItemChanged(position);
                    break;
                case R.id.b_decrement_score:
                    mPlayerScores[position]--;
                    notifyItemChanged(position);
                    break;
                default:
                    break;
            }
        }
    } // Close inner class: ViewHolder
}
