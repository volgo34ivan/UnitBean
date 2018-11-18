package com.volgo34ivan.android.json;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ActivityStart extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            Intent start = new Intent(this, ActivityPosts.class);
            this.startActivity(start);
            finish();
    }
}
