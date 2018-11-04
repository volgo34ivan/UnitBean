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

public class StartActivity extends Activity {

    private static int THREAD_DELAY_1 = 3000;
    private Handler handler;
    private boolean connection;

    @Override
    protected void onResume() {
        super.onResume();
        connection = new ConnectionCheck(StartActivity.this).isNetworkAvailableAndConnected();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connection = new ConnectionCheck(StartActivity.this).isNetworkAvailableAndConnected();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.start_activity);

        ImageView startView = (ImageView) findViewById(R.id.startView);
        try
        {
            InputStream ins = getAssets().open("logoUnitBean.png");
            Drawable d = Drawable.createFromStream(ins, null);
            startView.setImageDrawable(d);
            ins .close();
        }
        catch(IOException ex)
        {
            return;
        }
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(connection){
                    Intent start = new Intent(StartActivity.this, ActivityPosts.class);
                    StartActivity.this.startActivity(start);
                    THREAD_DELAY_1 = 1000;
                    connection = true;
                    StartActivity.this.finish();
                }else{
                    exit();
                }
                overridePendingTransition(R.layout.fadein, R.layout.fadeout);
            }
        },THREAD_DELAY_1);
    }

    private void exit(){
        if(!connection){
            Toast.makeText(StartActivity.this, "No connection!" +
                    "\n" +
                    "The application" + "\n" +
                    "will be turned off", Toast.LENGTH_SHORT).show();
            int THREAD_DELAY_2 = 3000;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!connection){
                        StartActivity.this.finish();
                        System.exit(0);
                    }
                }
            }, THREAD_DELAY_2);
        }
    }
}
