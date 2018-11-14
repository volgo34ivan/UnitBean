package com.volgo34ivan.android.json;

import android.content.Context;
import android.net.ConnectivityManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

class ConnectionCheck {
    public static boolean isNetworkAvailableAndConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        assert cm != null;
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        return isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
    }
}
