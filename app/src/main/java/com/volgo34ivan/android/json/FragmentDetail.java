package com.volgo34ivan.android.json;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentDetail extends Fragment {

    private static final String ARG_DETAIL_ID = "post_id";
    private RecyclerView mRecyclerView;
    private final static String TAG = "FragmentDetail";
    private static List<Photos> mPhotos;
    private static List<Posts> mPosts;
    private static List<Users> mUsers;
    private static int mId = 0;
    private static final List<Bitmap> bitmapPhotos = new ArrayList<>();
    private StringUpperCase uc;


    public static FragmentDetail newInstance(Integer i){
        mId = i;
        Bundle args = new Bundle();
        args.putSerializable(ARG_DETAIL_ID, i);
        FragmentDetail fragmentDetail = new FragmentDetail();
        fragmentDetail.setArguments(args);
        return fragmentDetail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uc = new StringUpperCase();
        mPhotos = FragmentPosts.getPhotosList();
        mPosts = FragmentPosts.getPostsList();
        mUsers = FragmentPosts.getUsersList();
        new getImage(mPhotos).execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail, container, false);
        TextView titlePost = (TextView) view.findViewById(R.id.titlePost);
        TextView datePost = (TextView) view.findViewById(R.id.datePost);
        TextView bodyPost = (TextView) view.findViewById(R.id.bodyPost);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_detail);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mRecyclerView.setNestedScrollingEnabled(false);
        datePost.setText(FragmentPosts.getCurrentDate());
        titlePost.setText(uc.upperCase(mPosts.get(mId).getTitle()));
        bodyPost.setText(uc.upperCase(mPosts.get(mId).getBody()));
        return view;
    }

    private void setupAdapter(){
        if(isAdded()){
            mRecyclerView.setAdapter(new DetailAdapter());
        }
    }

    private class DetailHolder extends RecyclerView.ViewHolder{
        final TextView name;
        final TextView comment;
        final TextView commentDate;
        private final CircleImageView profile_image;
        DetailHolder(View itemView) {
            super(itemView);
            profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);
            name = (TextView) itemView.findViewById(R.id.name);
            comment = (TextView) itemView.findViewById(R.id.comment);
            commentDate = (TextView) itemView.findViewById(R.id.dateComment);
        }
        @SuppressLint("SetTextI18n")
        void bindItem(Users users, Bitmap bitmap){
            String[] parts = users.getEmail().split("@");
            name.setText(uc.upperCase(parts[0]));
            comment.setText(uc.upperCase(users.getBody()));
            commentDate.setText(FragmentPosts.getCurrentDate());
            profile_image.setImageBitmap(bitmap);
        }
    }

    private class DetailAdapter extends RecyclerView.Adapter<DetailHolder>{
        @NonNull
        @Override
        public DetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view  = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_layout, parent, false);
            return new DetailHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailHolder holder, int position) {
            Users users = mUsers.get(position);
            Bitmap bitmap = bitmapPhotos.get(position);
            holder.bindItem(users, bitmap);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getImage extends AsyncTask<Void,Void,Void> {
        final String [] url = new String[mUsers.size()];
        getImage(List<Photos> lp){
            for(int i=0; i<mUsers.size(); i++){
                url[i] = lp.get(i).getUrl();
            }
        }
        @Override
        protected Void doInBackground(Void... voids) {
            bitmapPhotos.clear();
            for(int i=0; i < mUsers.size(); i++){
                try {
                    URL aURL = new URL(url[i]);
                    URLConnection conn = aURL.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bitmapPhotos.add(BitmapFactory.decodeStream(bis));
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error getting bitmap", e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setupAdapter();
        }

    }

}
