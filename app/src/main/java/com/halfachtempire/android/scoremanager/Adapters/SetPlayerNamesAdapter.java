package com.halfachtempire.android.scoremanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.halfachtempire.android.scoremanager.R;

public class SetPlayerNamesAdapter extends RecyclerView.Adapter<SetPlayerNamesAdapter.SetPlayerNameViewHolder> {

    private Context context;
    public String[] mPlayerNameSet;

    public SetPlayerNamesAdapter(int numberOfPlayers) {
        mPlayerNameSet = new String[numberOfPlayers];
    }

    @Override
    public SetPlayerNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.set_player_name_list_item, parent, false);
        return new SetPlayerNameViewHolder(view, new MyEditTextListener());
    }

    @Override
    public void onBindViewHolder(SetPlayerNameViewHolder holder, int position) {
        int playerNumber = position + 1;
        holder.numberTextView.setText(String.valueOf(playerNumber));

        // Update MyEditTextListener every time we bind a new item
        // So that it knows what item in mDataset to update
        holder.myEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.playerNameEditText.setText(mPlayerNameSet[holder.getAdapterPosition()]);
    }

    @Override
    public int getItemCount() {
        return mPlayerNameSet.length;
    }

    public static class SetPlayerNameViewHolder extends RecyclerView.ViewHolder {

        private TextView numberTextView;
        public EditText playerNameEditText;
        public MyEditTextListener myEditTextListener;

        public SetPlayerNameViewHolder(View itemView, MyEditTextListener myEditTextListener) {
            super(itemView);

            this.numberTextView = (TextView) itemView.findViewById(R.id.tv_set_player_name_number);
            this.playerNameEditText = (EditText) itemView.findViewById(R.id.et_set_player_name);

            this.myEditTextListener = myEditTextListener;
            this.playerNameEditText.addTextChangedListener(myEditTextListener);
        }
    }

    private class MyEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mPlayerNameSet[position] = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
