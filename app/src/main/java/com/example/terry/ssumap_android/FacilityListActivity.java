package com.example.terry.ssumap_android;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class FacilityListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Facility> mItems = new ArrayList<>();

    private String[] facilityCategorys;
    private int[] facilityImages = {
            R.drawable.list_info_sign, R.drawable.list_store,
            R.drawable.list_hospital, R.drawable.list_maejang,
            R.drawable.list_restaurant, R.drawable.list_present,
            R.drawable.list_parkinglot, R.drawable.list_money,
            R.drawable.list_aed};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_list);
        facilityCategorys = getResources().getStringArray(R.array.facility_category);

        recyclerView = findViewById(R.id.recycler_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.actionbar_title_around));

        setRecyclerView();
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

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(mItems);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Intent intent = new Intent(FacilityListActivity.this, DetailListActivity.class);
                        intent.putExtra("CategoryIndex", position);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        setData();
    }

    private void setData() {
        mItems.clear();

        for (int i = 0; i < facilityCategorys.length; ++i) {
            mItems.add(new Facility(facilityCategorys[i], facilityImages[i]));
        }

        adapter.notifyDataSetChanged();
    }
}
