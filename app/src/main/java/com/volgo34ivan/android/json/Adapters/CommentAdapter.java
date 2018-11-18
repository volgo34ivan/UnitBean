package com.volgo34ivan.android.json.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.volgo34ivan.android.json.Models.Comment;
import com.volgo34ivan.android.json.Models.Photo;
import com.volgo34ivan.android.json.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private Context context;
    private List<Comment> commentList;
    private List<Photo> photoList;

    private static String timestamp;

    public CommentAdapter(Context context, List<Comment> commentList, List<Photo> photoList) {
        this.context = context;
        this.commentList = commentList;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_layout, parent, false);
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate =  new SimpleDateFormat("dd MMM, yyyy");
        timestamp = simpleDate.format(date);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.txt_name.setText(commentList.get(position).getEmail().split("@")[0]);
        holder.txt_comment.setText(commentList.get(position).getBody());
        holder.txt_dateComment.setText(timestamp);
        Glide.with(context).load(photoList.get(position).getUrl()).into(holder.profile_image);
        //holder.profile_image;
        //profile image problem
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static String getTimestamp() {
        return timestamp;
    }
}
