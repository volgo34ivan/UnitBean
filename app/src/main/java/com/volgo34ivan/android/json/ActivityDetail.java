package com.volgo34ivan.android.json;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class ActivityDetail extends SingleFragmentActivity {

    private static final String EXTRA_DETAIL_ID =
            "com.volgo34ivan.android.json.post_id";

    @Override
    protected Fragment createFragment() {
            Integer id = (Integer) getIntent().getSerializableExtra(EXTRA_DETAIL_ID);
            return FragmentDetail.newInstance(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String title = getResources().getString(R.string.post);
        setTitle(title);
    }

    @Override
    public void setTitle(CharSequence title) {
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(title);
    }

    public static Intent newIntent(Context packageContext, Integer id) {
        Intent intent = new Intent(packageContext, ActivityDetail.class);
        intent.putExtra(EXTRA_DETAIL_ID, id);
        return intent;
    }
}
