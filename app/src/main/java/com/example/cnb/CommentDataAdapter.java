package com.example.cnb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentDataAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<CommentData> commentDataList;
    private int layout;

    public CommentDataAdapter(Context context, int layout, ArrayList<CommentData> data) {
        mContext = context;
        this.layout = layout;
        this.commentDataList = data;
    }

    @Override
    public int getCount() {
        return commentDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public CommentData getItem(int position) {
        return commentDataList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=LayoutInflater.from(mContext).inflate(this.layout, parent, false);
        }

        CommentData comment = this.commentDataList.get(position);

        TextView commentId = (TextView)convertView.findViewById(R.id.comment_Id);
        TextView commentText = (TextView)convertView.findViewById(R.id.comment_text);

        commentId.setText(comment.getUserId());
        commentId.setTag(comment.getUserNum());
        commentText.setText(comment.getReview());

        return convertView;
    }


}
