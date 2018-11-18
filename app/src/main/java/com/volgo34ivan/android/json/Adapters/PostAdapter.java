package com.volgo34ivan.android.json.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.volgo34ivan.android.json.Models.Post;
import com.volgo34ivan.android.json.R;
import com.volgo34ivan.android.json.StringUpperCase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    Context context;
    List<Post> postList;
    Integer comments;
    StringUpperCase uc = new StringUpperCase();
    private static String timestamp;

    public PostAdapter(Context context, List<Post> postList, Integer comments) {
        this.context = context;
        this.postList = postList;
        this.comments = comments;
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_layout, parent, false);
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate =  new SimpleDateFormat("dd MMM, yyyy");
        timestamp = simpleDate.format(date);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.txt_title.setText(uc.upperCase(String.valueOf(postList.get(position).getTitle())));
        holder.txt_date.setText(timestamp);
        holder.txt_body.setText(uc.upperCase(new StringBuilder(postList.get(position).getBody().substring(0, 40))
                .append("...").toString()));
        holder.ic_comments.setImageResource(R.drawable.ic_comment);
        holder.txt_comments.setText(String.valueOf(comments));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
