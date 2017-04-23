package com.halfachtempire.android.scoremanager.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.halfachtempire.android.scoremanager.R;

public class SetPlayerNamesAdapter extends RecyclerView.Adapter<SetPlayerNamesAdapter.SetPlayerNameViewHolder> {

    private Context mContext;
    private int mNumberItems;

    public SetPlayerNamesAdapter(){}

    public SetPlayerNamesAdapter(Context context, int numberOfItems) {
        mContext = context;
        mNumberItems = numberOfItems;

    }

    @Override
    public SetPlayerNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.set_player_name_list_item, parent, false);
        return new SetPlayerNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SetPlayerNameViewHolder holder, int position) {
        int playerNumber = position + 1;
        holder.numberTextView.setText(String.valueOf(playerNumber));
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class SetPlayerNameViewHolder extends RecyclerView.ViewHolder {

        TextView numberTextView;
        public EditText playerNameEditText;

        public SetPlayerNameViewHolder(View itemView) {
            super(itemView);
            numberTextView = (TextView) itemView.findViewById(R.id.tv_set_player_name_number);
            playerNameEditText = (EditText) itemView.findViewById(R.id.et_set_player_name);
        }
    }
}
