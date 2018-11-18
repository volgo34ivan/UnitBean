package com.volgo34ivan.android.json.Retrofit;

import com.volgo34ivan.android.json.Models.Comment;
import com.volgo34ivan.android.json.Models.Photo;
import com.volgo34ivan.android.json.Models.Post;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IMyAPI {
    @GET("posts?userId=1")
    Observable<List<Post>> getPosts();

    @GET("comments?postId=1")
    Observable<List<Comment>> getComments();

    @GET("photos?albumId=1")
    Observable<List<Photo>> getPhotos();
}
