package com.example.appquiz.adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageViewBindingAdapter {
    @BindingAdapter({"imageRemoteUri"})
    public static void loadImage(ImageView view, String imageUri) {
        Log.d(ImageViewBindingAdapter.class.getName(),"bindingAdapter called: setImageUri");

        RequestOptions options = new RequestOptions();
        options.fitCenter();

        Glide.with(view.getContext())
                .load(imageUri)
                .into(view);
    }
}
