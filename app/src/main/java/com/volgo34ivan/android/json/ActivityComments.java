package com.volgo34ivan.android.json;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;


public class ActivityComments extends SingleFragmentActivity {

    private static final String EXTRA_DETAIL_ID =
            "com.volgo34ivan.android.json.post_id";

    @Override
    protected Fragment createFragment() {
            Integer id = (Integer) getIntent().getSerializableExtra(EXTRA_DETAIL_ID);
            return FragmentComments.newInstance(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        String title = getResources().getString(R.string.post);
        setTitle(title);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_up);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(title);
    }

    public static Intent newIntent(Context packageContext, Integer id) {
        Intent intent = new Intent(packageContext, ActivityComments.class);
        intent.putExtra(EXTRA_DETAIL_ID, id);
        return intent;
    }
}
