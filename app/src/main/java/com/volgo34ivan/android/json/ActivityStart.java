package com.volgo34ivan.android.json;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;

public class ActivityStart extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ConnectionCheck.isNetworkAvailableAndConnected(this)) {
            Intent start = new Intent(this, ActivityPosts.class);
            this.startActivity(start);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            finish();
        }

    }
}
