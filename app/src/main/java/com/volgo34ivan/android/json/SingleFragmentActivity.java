package com.volgo34ivan.android.json;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG = "SingleFragmentActivity";
    private static int check = 0;
    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(ConnectionCheck.isNetworkAvailableAndConnected(this) | check == 1){
            if(fragment == null){
                fragment = createFragment();
                fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
                check = 1;
            }
        }else{
            Log.d(TAG, "No connection");
        }
    }
}
