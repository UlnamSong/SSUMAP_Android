package com.kim.terry.ssumap_android;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by terry on 2017. 11. 26..
 */

public class FacilityListApi extends AsyncTask<String, Void, String> {

    public String response;
    public int categoryIndex = 0;
    public int take = 30;

    public String base_url = "http://ssumap-service.azurewebsites.net/api/spots/?categoryIndex=";

    public FacilityListApi(int index, int take) {
        this.categoryIndex = index;
        this.take = take;
    }

    @Override
    protected String doInBackground(String... params) {
        OutputStream os   = null;
        InputStream is   = null;
        ByteArrayOutputStream baos = null;

        try {
            URL url = new URL(base_url + categoryIndex);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            Log.d(TAG, "doInBackground: Request URL : " + url);

            int status = httpURLConnection.getResponseCode();
            Log.d(TAG, "doInBackground: status : " + status);

            if(status == HttpURLConnection.HTTP_OK) {
                is = httpURLConnection.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();
                response = new String(byteData);
                Log.i("TAG", "DATA response = " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONArray jar = new JSONArray(result);

            for(int i = 0; i < jar.length(); ++i) {
                JSONObject job = jar.getJSONObject(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}