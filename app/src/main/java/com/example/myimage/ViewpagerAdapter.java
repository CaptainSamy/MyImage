package com.example.myimage;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myimage.Model.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewpagerAdapter extends PagerAdapter {
    Context context;
    Photo photoArrayListV;

    public ViewpagerAdapter(Context context, Photo photoArrayListV) {
        this.context = context;
        this.photoArrayListV = photoArrayListV;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context).load(photoArrayListV.getUrlL()).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
