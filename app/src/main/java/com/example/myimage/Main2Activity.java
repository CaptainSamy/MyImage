package com.example.myimage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myimage.Model.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class Main2Activity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1000 ;
    FloatingActionButton fab, fab1, fab2;
    TextView tv1, tv2;
    ImageView imgAnhMain2;
    boolean An_Hien = false;
    AlertDialog dialog;
    String link, link2;
    String mmmm;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Ðã được cho phép", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Quyền bị từ chối", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            },PERMISSION_REQUEST_CODE);

//        imgAnhMain2 = findViewById(R.id.imageViewMain2);
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(this, );
        viewPager.setAdapter(viewpagerAdapter);

        Intent intent2 = getIntent();
        if (intent2 != null){
            if (intent2.hasExtra("dulieu")){
                mmmm = intent2.getStringExtra("dulieu");
            }
        }
        //Log.d("hhhhhh", imageUrls.length + "");

//        Bundle bundle2 = intent2.getExtras();
//        link = (String) bundle2.get("urlLinkL");
//        link2 = (String) bundle2.get("urlLinkO");
//        Picasso.with(this).load(link).into(imgAnhMain2);
        //Log.d("kkkkk", link2);


        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        tv1 = findViewById(R.id.tv1);
        fab2 = findViewById(R.id.fab2);
        tv2 = findViewById(R.id.tv2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (An_Hien == false){
                    Hien();
                    An_Hien = true;
                } else {
                    An();
                    An_Hien = false;
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(Main2Activity.this, "",Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSION_REQUEST_CODE);
                    return;
                } else {
                    dialog = new SpotsDialog.Builder().setContext(Main2Activity.this).build();
                    dialog.show();
                    dialog.setMessage("Downloading...");

                    String fileName = UUID.randomUUID().toString()+".jpg";
                    Picasso.with(getBaseContext()).load(link).into(new SaveImageHelper(getBaseContext(),
                            dialog,
                            getApplicationContext().getContentResolver(),
                            fileName, "Image description"));
                }

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(Main2Activity.this, "",Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSION_REQUEST_CODE);
                    return;
                } else {
                    dialog = new SpotsDialog.Builder().setContext(Main2Activity.this).build();
                    dialog.show();
                    dialog.setMessage("Downloading...");

                    String fileName2 = UUID.randomUUID().toString()+".png";
                    Picasso.with(getBaseContext()).load(link).into(new SaveImageHelper(getBaseContext(),
                            dialog,
                            getApplicationContext().getContentResolver(),
                            fileName2, "Image description2"));

                }

            }
        });
    }

    private void An() {
        fab1.hide();
        fab2.hide();
        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
    }

    private void Hien() {
        fab1.show();
        fab2.show();
        tv1.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);
    }
}
