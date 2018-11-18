package com.volgo34ivan.android.json;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.volgo34ivan.android.json.Adapters.PostAdapter;
import com.volgo34ivan.android.json.Models.Comment;
import com.volgo34ivan.android.json.Models.Photo;
import com.volgo34ivan.android.json.Models.Post;
import com.volgo34ivan.android.json.Retrofit.IMyAPI;
import com.volgo34ivan.android.json.Retrofit.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FragmentPosts extends Fragment {

    private RecyclerView recycler_posts;
    IMyAPI myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static List<Post> postList = new ArrayList<>();
    private static List<Photo> photoList = new ArrayList<>();
    private static List<Comment> commentList = new ArrayList<>();

    public static FragmentPosts newInstance(){
        return new FragmentPosts();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(IMyAPI.class);
        fetchData();
    }

    private void fetchData() {
        compositeDisposable.add(myAPI.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Post>>() {
                    @Override
                    public void accept(List<Post> list) throws Exception {
                        displayData(list);
                        initLists(list);
                    }
                }));
        compositeDisposable.add(myAPI.getPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Photo>>() {
                    @Override
                    public void accept(List<Photo> list) throws Exception {
                        initLists(list);
                    }
                }));
        compositeDisposable.add(myAPI.getComments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comment>>() {
                    @Override
                    public void accept(List<Comment> list) throws Exception {
                        initLists(list);
                    }
                }));
    }

    private void initLists(List list){
        if(list.get(0) instanceof Post){
            postList = list;
        }
        if(list.get(0) instanceof Comment){
            commentList = list;
        }
        if(list.get(0) instanceof Photo){
            photoList = list;
        }
        if(!postList.isEmpty() & !commentList.isEmpty() & !photoList.isEmpty()){
            displayData(postList);
        }
    }

    private void displayData(List<Post> posts) {
        PostAdapter adapter = new PostAdapter(getActivity(), posts, commentList.size());
        recycler_posts.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Init View
        View view = inflater.inflate(R.layout.activity_posts, container, false);
        recycler_posts = (RecyclerView) view.findViewById(R.id.recycler_posts);
        recycler_posts.setHasFixedSize(true);
        recycler_posts.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public static List<Post> getPostList() {
        return postList;
    }

    public static List<Photo> getPhotoList() {
        return photoList;
    }

    public static List<Comment> getCommentList() {
        return commentList;
    }

    @Override
    public void onResume() {
        displayData(postList);
        super.onResume();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
