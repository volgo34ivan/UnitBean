package com.volgo34ivan.android.json.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.volgo34ivan.android.json.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profile_image;
    TextView txt_name,txt_comment,txt_dateComment;

    public CommentViewHolder(View itemView) {
        super(itemView);
        profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);
        txt_name = (TextView) itemView.findViewById(R.id.txt_name);
        txt_comment = (TextView) itemView.findViewById(R.id.txt_comment);
        txt_dateComment = (TextView) itemView.findViewById(R.id.txt_dateComment);
    }
}
