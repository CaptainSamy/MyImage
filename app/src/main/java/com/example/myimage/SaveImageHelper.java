package com.example.myimage;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.lang.ref.WeakReference;

public class SaveImageHelper implements Target {  //sử dụng interface Target của Picasso đẻ lưu ảnh về storage
    Context context;  //Thằng này cần để gọi activity mới sau khi tải xong
    WeakReference<AlertDialog> alertDialogWeakReference;  // Thằng này sẽ ánh xạ tới cái dialog ở Main2Activity
    WeakReference<ContentResolver> contentResolverWeakReference;  // Thằng này thì ánh xạ tới contentResolver ở Main2Activity
    String name;
    String desc;

    public SaveImageHelper(Context context,AlertDialog alertDialog, ContentResolver contentResolver, String name, String desc) {
        this.context = context;
        this.alertDialogWeakReference = new WeakReference<AlertDialog>(alertDialog);
        this.contentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.desc = desc;
    }


    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        // Đây là nơi viết code xử lý sau khi tải thành công image
        ContentResolver resolver = contentResolverWeakReference.get();
        AlertDialog dialog = alertDialogWeakReference.get();
        if (resolver != null){
            MediaStore.Images.Media.insertImage(resolver, bitmap, name, desc);
            dialog.dismiss();
            Toast.makeText(context, "Đã tải xong!",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        // Đây là nơi viết code xử lý sau khi không tải được image
        Toast.makeText(context, "Tải thất bại!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        // Đây là nơi viết code xử lý khi đang tải image
        Toast.makeText(context, "Đang tải...",Toast.LENGTH_LONG).show();
    }
}
