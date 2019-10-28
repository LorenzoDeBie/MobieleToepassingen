package be.ugent.oomt.quizapp.adapters;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageViewBindingAdapter {

    @BindingAdapter({"imageRemoteUri"})
    public static void setImageRemoteUri(ImageView imageView, String imageUri) {
        Log.d(ImageViewBindingAdapter.class.getName(),"bindingAdapter called: setImageUri");

        RequestOptions options = new RequestOptions();
        options.fitCenter();

        Glide.with(imageView.getContext())
                .load(imageUri)
                .into(imageView);
    }
}
