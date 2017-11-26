package com.kim.terry.ssumap_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SpotDetailActivity extends AppCompatActivity {

    private String name;
    private String address;
    private String phone;
    private String description;
    private double longitude;
    private double latitude;

    TextView tvName;
    TextView tvDistance;

    Button btnCall;
    Button btnShare;
    Button btnRoute;

    TextView tvAddress;
    TextView tvPhone;
    TextView tvDescription;

    ImageView ivSpot;

    String TAG = "TAG";
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_detail);

        tvName = findViewById(R.id.tv_spot_title);
        tvAddress = findViewById(R.id.tv_address);
        tvPhone = findViewById(R.id.tv_phone);
        tvDescription = findViewById(R.id.tv_description);

        ivSpot = findViewById(R.id.spot_image);
        btnCall = findViewById(R.id.btn_call);
        btnShare = findViewById(R.id.btn_share);
        btnRoute = findViewById(R.id.btn_route);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        phone = intent.getStringExtra("phone");
        description = intent.getStringExtra("description");
        longitude = intent.getDoubleExtra("longitude", 126.956880);
        latitude = intent.getDoubleExtra("latitude", 37.496505);
        fileName = intent.getStringExtra("fileName");

        tvName.setText(name);
        tvAddress.setText(address);
        tvPhone.setText(phone);
        tvDescription.setText(description);

        Glide.with(this).load("http://ssumap-service.azurewebsites.net/files/" + fileName).into(ivSpot);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(name);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone.replace("-", "")));
                startActivity(intent);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = name;
                String text = "주소 : " + address + "\n전화번호 : " + phone + "\n설명 : " + description;

                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, text);
                Intent chooser = Intent.createChooser(intent, "공유하기");
                startActivity(chooser);
            }
        });

        btnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(SpotDetailActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setMessage("현재 제공되지 않는 기능입니다. 추후 업데이트를 통해 제공될 예정입니다.");
                alert.show();
            }
        });
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
