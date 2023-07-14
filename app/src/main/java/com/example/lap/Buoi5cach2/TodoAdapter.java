package com.example.lap.Buoi5cach2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lap.R;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends BaseAdapter {

    Context context;
    List<ItemTodo> mList = new ArrayList<>();


    public TodoAdapter(Context context, List<ItemTodo> mList) {

        this.context = context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_todos, parent, false);

        TextView tvID = (TextView) rowView.findViewById(R.id.tvId);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView user = (TextView) rowView.findViewById(R.id.user_id);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTittle);
        TextView tvStatus = (TextView) rowView.findViewById(R.id.tvCompleted);

//        user.setText(String.valueOf(mList.get(position).getUser_id()));
        tvID.setText(String.valueOf(mList.get(position).getId()));
        tvTitle.setText(String.valueOf(mList.get(position).getTitle()));

        if (mList.get(position).isCompleted()) {
            tvStatus.setText("Done");
        } else {
            tvStatus.setText("Not yet");
        }



        return rowView;
    }
}
