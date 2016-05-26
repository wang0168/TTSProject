package tts.project.igg.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * 网络加载图片
 * Created by lenove on 2016/5/6.
 */
public class ImageLoader {
    public static void load(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imageView);
    }

    public static void load(Context context, String imageUrl, ImageView imageView, int defaultImg) {
        Glide.with(context).load(imageUrl).placeholder(defaultImg).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imageView);
    }

    public static void load(Context context, String imageUrl, ImageView imageView, Drawable defaultImg) {
        Glide.with(context).load(imageUrl).placeholder(defaultImg).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imageView);
    }

//    public static void with(Context context, Uri imageUri, ImageView imageView, Drawable defaultImg) {
//        Glide.with(context).load(imageUri).placeholder(defaultImg).diskCacheStrategy(DiskCacheStrategy.ALL).
//                into(imageView);
//    }
}
