package com.volgo34ivan.android.json.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.volgo34ivan.android.json.ActivityComments;
import com.volgo34ivan.android.json.ActivityPosts;
import com.volgo34ivan.android.json.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    TextView txt_title,txt_date,txt_body,txt_comments;
    ImageView ic_comments;

    public PostViewHolder(View itemView) {
        super(itemView);
        txt_title = (TextView) itemView.findViewById(R.id.txt_title);
        txt_date = (TextView) itemView.findViewById(R.id.txt_date);
        txt_body = (TextView) itemView.findViewById(R.id.txt_body);
        txt_comments = (TextView) itemView.findViewById(R.id.txt_comments);
        ic_comments = (ImageView) itemView.findViewById(R.id.ic_comments);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ActivityComments.newIntent(ActivityPosts.getContext(),getAdapterPosition());
                ActivityPosts.getContext().startActivity(intent);
            }
        });
    }
}
