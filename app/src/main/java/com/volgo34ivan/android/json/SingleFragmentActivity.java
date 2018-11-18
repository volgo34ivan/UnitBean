package com.volgo34ivan.android.json;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.volgo34ivan.android.json.Models.Post;
import com.volgo34ivan.android.json.Retrofit.IMyAPI;
import com.volgo34ivan.android.json.Retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private final String LIFE = "LIFE";

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LIFE, "onCreate SingleFragmentActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        if (ConnectionCheck.isNetworkAvailableAndConnected(this)) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);

            if(fragment == null){
                fragment = createFragment();
                fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
            }
        } else{
            Toast.makeText(this, "No connection!", Toast.LENGTH_SHORT).show();
        }
    }
}
