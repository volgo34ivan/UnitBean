package com.volgo34ivan.android.json;

import android.support.v4.app.Fragment;
import android.os.Bundle;

public class ActivityPosts extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return FragmentPosts.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
