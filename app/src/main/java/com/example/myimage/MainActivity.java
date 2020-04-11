package com.example.myimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
        // thông báo trạng thái khi đợi dữ liệu trả về
        final ProgressDialog loading = new ProgressDialog(MainActivity.this);
        loading.setMessage("Loading...");
        loading.show();
        //RequestQueue: nơi giữ các request trước khi gửi
        //tạo một RequestQueue bằng lệnh
        RequestQueue requestQueue =
                Volley.newRequestQueue(MainActivity.this);
        //StringRequest: kế thừa từ Request, là class đại diện cho request trả về String
        // khai báo stringRepuest, phương thức POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://www.flickr.com/services/rest", new Response.Listener<String>() { //Nơi bạn nhận dữ liệu trả về từ server khi request hoàn thành
            @Override
            public void onResponse(String response) {
                //là một thư viện java giúp chuyển đổi qua lại giữa JSON và Java
                Gson gson = new Gson();

                FlickrPhoto flickrPhoto =
                        gson.fromJson(response, FlickrPhoto.class);

                List<Photo> photos = flickrPhoto.getPhotos().getPhoto();

                // gọi interface bên adapter để bắt sự kiện chuyển màn hình và truyền position của item đã click sang màn hình main2
                imageViewAdapter = new ImageViewAdapter(getApplication(), (ArrayList<Photo>) photos,
                        new ImageViewAdapter.AdapterListener() {
                    @Override
                    public void OnClick(int position) {
                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        intent.putExtra("position",position);
                        startActivity(intent);
                    }
                });
                // 1 dạng layout trong recyclerView giúp view hiển thị theo dạng lưới tùy theo kích thước của item
                StaggeredGridLayoutManager staggeredGridLayoutManager = new
                        StaggeredGridLayoutManager(NUM_COLUMNS,LinearLayoutManager.VERTICAL);
                recyclerViewImage.setLayoutManager(staggeredGridLayoutManager);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
//                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//                recyclerViewImage.setLayoutManager(linearLayoutManager);
                recyclerViewImage.setAdapter(imageViewAdapter);
                loading.dismiss();// dừng hiển thị thông báo loading
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // nơi nhận các lỗi xảy ra khi request
                loading.dismiss();
                Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // lưu giữ các giá trị theo cặp key/value
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
        requestQueue.add(stringRequest); // thêm vào nơi giữ các request để gửi lên server
    }
}
