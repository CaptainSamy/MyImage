package com.example.myimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myimage.Model.FlickrPhoto;
import com.example.myimage.Model.Photo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    ImageViewAdapter imageViewAdapter;
    RecyclerView recyclerViewImage;
    private static final int NUM_COLUMNS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewImage = findViewById(R.id.recyclerview);
        GetData();
    }

    private void GetData() {
        RequestQueue requestQueue =
                Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://www.flickr.com/services/rest", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                FlickrPhoto flickrPhoto =
                        gson.fromJson(response, FlickrPhoto.class);

                List<Photo> photos = flickrPhoto.getPhotos().getPhoto();

                imageViewAdapter = new ImageViewAdapter(getApplication(), (ArrayList<Photo>) photos);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS,LinearLayoutManager.VERTICAL);
                recyclerViewImage.setLayoutManager(staggeredGridLayoutManager);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
//                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//                recyclerViewImage.setLayoutManager(linearLayoutManager);
                recyclerViewImage.setAdapter(imageViewAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("api_key", "7a4b5ef02077a1f5dd3f1fef0d14ecb6");
                params.put("user_id", "186424648@N06");
                params.put("extras", "views, media, path_alias, url_l, url_o");
                params.put("format", "json");
                params.put("method", "flickr.favorites.getList");
                params.put("nojsoncallback", "1");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
