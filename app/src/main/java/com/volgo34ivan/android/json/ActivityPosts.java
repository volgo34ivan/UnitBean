package com.volgo34ivan.android.json;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.FrameLayout;

public class ActivityPosts extends SingleFragmentActivity {

    public static ActivityPosts context;

    @Override
    protected Fragment createFragment() {
        return FragmentPosts.newInstance();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        final FrameLayout mFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        context = this;
        if(ConnectionCheck.isNetworkAvailableAndConnected(this)){
            Snackbar snackbar = Snackbar.make(mFrameLayout, getResources().getString(R.string.download_complete), Snackbar.LENGTH_LONG);
            snackbar.show();
        }else{
            Snackbar snackbar = Snackbar.make(mFrameLayout, getResources().getString(R.string.download_error), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public static ActivityPosts getContext() {
        return context;
    }
}
