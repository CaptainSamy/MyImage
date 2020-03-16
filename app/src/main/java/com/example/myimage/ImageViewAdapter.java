package com.example.myimage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myimage.Model.Photo;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Photo> photoArrayList;

    public ImageViewAdapter(Context context, ArrayList<Photo> photoArrayList) {
        this.context = context;
        this.photoArrayList = photoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Photo photo = photoArrayList.get(position);
        holder.tvView.setText(photo.getViews());
        Picasso.with(context).load(photo.getUrlL()).into(holder.imgPhoto);





        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dulieu", photo.getUrlL());

//                Bundle bundle = new Bundle();
//                bundle.putString("urlLinkL", photo.getUrlL());
//                bundle.putString("urlLinkO", photo.getUrlO());
//                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imageview);
            tvView = itemView.findViewById(R.id.textviewView);
        }
    }
}
