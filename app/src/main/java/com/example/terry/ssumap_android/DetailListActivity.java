package com.example.terry.ssumap_android;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.daum.android.map.coord.MapCoord;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DetailListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Facility> mItems = new ArrayList<>();

    private int categoryIndex = 0;

    private String[] facilityCategorys;
    private int[] facilityImages = {
            R.drawable.list_info_sign, R.drawable.list_store,
            R.drawable.list_hospital, R.drawable.list_maejang,
            R.drawable.list_restaurant, R.drawable.list_present,
            R.drawable.list_parkinglot, R.drawable.list_money,
            R.drawable.list_aed};

    private ArrayList<Spot> spots;
    private MapView mapView;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        facilityCategorys = getResources().getStringArray(R.array.facility_category);
        recyclerView = findViewById(R.id.recycler_view);

        mapView = new MapView(DetailListActivity.this);
        mapView.setDaumMapApiKey("ea8d49685012f33f11397e22230ecba1");
        mapView.setDefaultCurrentLocationMarker();
        mapView.setZoomLevel(1, true);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        spots = new ArrayList<>();

        Intent intent = getIntent();
        categoryIndex = intent.getIntExtra("CategoryIndex", 0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(facilityCategorys[categoryIndex]);

        try {
            String response = new FacilityListApi(categoryIndex, 30).execute().get();

            JSONArray jar = new JSONArray(response);

            for(int i = 0; i < jar.length(); ++i) {
                JSONObject job = jar.getJSONObject(i);
                Log.d("TAG", "onCreate: " + job);

                // File URL :  hostURL + /files/[file_name]
                spots.add(new Spot(URLDecoder.decode(job.getString("name"), "UTF-8"), URLDecoder.decode(job.getString("fileName"), "UTF-8"),
                        URLDecoder.decode(job.getString("address"), "UTF-8"), URLDecoder.decode(job.getString("description"), "UTF-8"),
                        job.getDouble("longitude"), job.getDouble("latitude"),
                        URLDecoder.decode(job.getString("phoneNumber"), "UTF-8")));

                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(URLDecoder.decode(job.getString("name"), "UTF-8"));
                marker.setTag(0);
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(job.getDouble("latitude"), job.getDouble("longitude")));
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(job.getDouble("latitude"), job.getDouble("longitude")), true);
                mapView.addPOIItem(marker);
            }



            setRecyclerView();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(mItems);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Spot spot = spots.get(position);

                Intent intent = new Intent(DetailListActivity.this, SpotDetailActivity.class);
                intent.putExtra("name", spot.getName());
                intent.putExtra("address", spot.getAddress());
                intent.putExtra("phone", spot.getPhoneNumber());
                intent.putExtra("description", spot.getDescription());
                intent.putExtra("longitude", spot.getLongitude());
                intent.putExtra("latitude", spot.getLatitude());
                intent.putExtra("fileName", spot.getFileName());

                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        setData();
    }

    private void setData() {
        mItems.clear();

        for(int i = 0; i < spots.size(); ++i) {
            mItems.add(new Facility(spots.get(i).getName(), facilityImages[categoryIndex]));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
