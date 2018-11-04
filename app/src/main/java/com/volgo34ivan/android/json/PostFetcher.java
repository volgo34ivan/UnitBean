package com.volgo34ivan.android.json;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class PostFetcher {

    private static final String TAG = "Posts_Fetcher";

    private byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()
                        + ": with "
                        +  urlSpec);
            }
            int byteRead;
            byte[] buffer = new byte[1024];
            while ((byteRead = in.read(buffer)) > 0){
                out.write(buffer, 0, byteRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

    public String getUtlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Posts> fetchItems(String urlSpec){
        List<Posts> items = new ArrayList<>();
        try {
            String jsonString = getUtlString(urlSpec);
            JSONArray jsonArray = new JSONArray(jsonString);
            parseItems(items, jsonArray);
        }catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return items;
    }

    private void parseItems(List<Posts> items, JSONArray jsonArray) throws JSONException{
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Posts item = new Posts();
            item.setUserId(jsonObject.getString("userId"));
            item.setId(jsonObject.getString("id"));
            item.setTitle(jsonObject.getString("title"));
            item.setBody(jsonObject.getString("body"));
            items.add(item);
        }
    }
}
