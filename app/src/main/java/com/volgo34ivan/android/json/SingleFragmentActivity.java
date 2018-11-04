package com.volgo34ivan.android.json;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean connection = new ConnectionCheck(getApplicationContext()).isNetworkAvailableAndConnected();
        if (connection) {
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
