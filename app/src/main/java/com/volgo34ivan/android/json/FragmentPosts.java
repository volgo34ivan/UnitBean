package com.volgo34ivan.android.json;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentPosts extends Fragment {

    private RecyclerView mRecyclerView;
    private static List<Photos> mPhotos = new ArrayList<>();
    private static List<Posts> mPosts = new ArrayList<>();
    private static List<Users> mUser = new ArrayList<>();
    private static final String TAG = "myFragment";
    private static String timestamp;
    private StringUpperCase uc;

    public static List<Users> getUsersList(){
        return mUser;
    }
    public static List<Posts> getPostsList(){
        return mPosts;
    }
    public static List<Photos> getPhotosList(){
        return mPhotos;
    }
    public static String getCurrentDate(){
        return timestamp;
    }
    public static FragmentPosts newInstance(){
        return new FragmentPosts();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uc = new StringUpperCase();
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate =  new SimpleDateFormat("dd MMM, yyyy");
        timestamp = simpleDate.format(date);
        setRetainInstance(true);
        getJsonFromUrl();
    }

    private void getJsonFromUrl(){
        //- получение фото из 1-го альбома;
        String photosUrl = "https://jsonplaceholder.typicode.com/photos?albumId=1";
        new FetchPhotosTask(photosUrl).execute();
        //- получение постов от 1-го пользователя
        String postsUrl = "https://jsonplaceholder.typicode.com/posts?userId=1";
        new FetchPostsTask(postsUrl).execute();
        //- получение коментариев к посту 1
        String commentsUrl = "https://jsonplaceholder.typicode.com/comments?postId=1";
        new FetchCommentsTask(commentsUrl).execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        setupAdapter();
        return view;
    }

    private void setupAdapter(){
        if(isAdded()){
            mRecyclerView.setAdapter(new ItemAdapter());
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder{

        final TextView text1;
        final TextView text2;
        final TextView text3;
        final TextView text4;

        ItemHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);
            text3 = (TextView) itemView.findViewById(R.id.text3);
            text4 = (TextView) itemView.findViewById(R.id.text4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = ActivityDetail.newIntent(getActivity(),getAdapterPosition());
                        startActivity(intent);
                }
            });
        }

        @SuppressLint("SetTextI18n")
        void bindItem(Posts posts){
            text1.setText(uc.upperCase(posts.getTitle()));
            text2.setText(timestamp);
            text3.setText(uc.upperCase(posts.getBody().substring(0,100) + "..."));
            text4.setText(Integer.toString(mUser.size()));
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder>{

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view  = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item, parent, false);
            ImageView ic_msg = (ImageView) view.findViewById(R.id.image1);
            try
            {
                InputStream ins = getActivity().getAssets().open("ic_comment.png");
                Drawable d = Drawable.createFromStream(ins, null);
                ic_msg.setImageDrawable(d);
                ins .close();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            Posts posts = mPosts.get(position);
            holder.bindItem(posts);
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchPhotosTask extends AsyncTask<Void,Void,List<Photos>>{
        private final String url;
        FetchPhotosTask(String u){
            url = u;
        }
        @Override
        protected List<Photos> doInBackground(Void... voids) {
            try {
                String result = new PhotosFetcher().getUtlString(url);
                Log.i(TAG, "Fetched contents of URL: " + result);
            }catch (IOException ioe){
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return new PhotosFetcher().fetchItems(url);
        }
        @Override
        protected void onPostExecute(List<Photos> items){
            mPhotos = items;
            setupAdapter();
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class FetchPostsTask extends AsyncTask<Void,Void,List<Posts>>{
        private final String url;
        FetchPostsTask(String u){
            url = u;
        }
        @Override
        protected List<Posts> doInBackground(Void... voids) {
            try {
                String result = new PostFetcher().getUtlString(url);
                Log.i(TAG, "Fetched contents of URL: " + result);
            }catch (IOException ioe){
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return new PostFetcher().fetchItems(url);
        }
        @Override
        protected void onPostExecute(List<Posts> items){
            mPosts = items;
            setupAdapter();
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class FetchCommentsTask extends AsyncTask<Void,Void,List<Users>>{
        private final String url;
        FetchCommentsTask(String u){
            url = u;
        }
        @Override
        protected List<Users> doInBackground(Void... voids) {
            try {
                String result = new CommentsFetcher().getUtlString(url);
                Log.i(TAG, "Fetched contents of URL: " + result);
            }catch (IOException ioe){
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return new CommentsFetcher().fetchItems(url);
        }
        @Override
        protected void onPostExecute(List<Users> items){
            mUser = items;
            setupAdapter();
        }
    }
}
