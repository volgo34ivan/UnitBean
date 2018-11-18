package com.volgo34ivan.android.json;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.volgo34ivan.android.json.Adapters.CommentAdapter;
import com.volgo34ivan.android.json.Models.Comment;
import com.volgo34ivan.android.json.Models.Photo;
import com.volgo34ivan.android.json.Retrofit.IMyAPI;
import com.volgo34ivan.android.json.Retrofit.RetrofitClient;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FragmentComments extends Fragment {

    private static final String TAG = "FragmentComments";

    private static final String ARG_DETAIL_ID = "post_id";
    private static int mId = 0;
    private IMyAPI myAPI;
    private RecyclerView recycler_comments;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private TextView txt_titlePost,txt_datePost, txt_bodyPost;
    private StringUpperCase uc;

    public static FragmentComments newInstance(Integer i){
        mId = i;
        Bundle args = new Bundle();
        FragmentComments fragmentComments = new FragmentComments();
        fragmentComments.setArguments(args);

        return fragmentComments;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uc = new StringUpperCase();
        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(IMyAPI.class);
    }

    private void displayData(List<Comment> comments, List<Photo> photos) {
        CommentAdapter adapter = new CommentAdapter(getActivity(), comments, photos);
        recycler_comments.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Init View
        View view = inflater.inflate(R.layout.activity_comments, container, false);
        txt_titlePost = (TextView) view.findViewById(R.id.titlePost);
        txt_datePost = (TextView) view.findViewById(R.id.datePost);
        txt_bodyPost = (TextView) view.findViewById(R.id.bodyPost);
        txt_titlePost.setText(uc.upperCase(FragmentPosts.getPostList().get(mId).getTitle()));
        txt_bodyPost.setText(uc.upperCase(FragmentPosts.getPostList().get(mId).getBody()));
        txt_datePost.setText(CommentAdapter.getTimestamp());
        recycler_comments = (RecyclerView) view.findViewById(R.id.recycler_comments);
        recycler_comments.setHasFixedSize(true);
        recycler_comments.setLayoutManager(new LinearLayoutManager(getActivity()));
        displayData(FragmentPosts.getCommentList(),FragmentPosts.getPhotoList());
        return view;
    }

    @Override
    public void onResume() {
        displayData(FragmentPosts.getCommentList(),FragmentPosts.getPhotoList());
        super.onResume();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
