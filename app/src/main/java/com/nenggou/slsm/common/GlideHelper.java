package com.nenggou.slsm.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class GlideHelper {

    public static void load(Context context, String url, ImageView imageView, int default_image) {
        if (context != null) {
            Glide.with(context).load(url).centerCrop().error(default_image).crossFade
                    ().into(imageView);
        }
    }

    public static void load(String url, int placeHolder, ImageView target){
        if(target!=null&&target.getContext()!=null) {
            Glide.with(target.getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .into(new ImageTarget(target));
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Activity activity, String url, int placeHolder, ImageView target){
        if(activity!=null&&!activity.isDestroyed()) {
            Glide.with(activity)
                    .load(url)
                    .placeholder(placeHolder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(target);
        }
    }
    public static void load(Fragment fragment, String url, int placeHolder, ImageView target){
        if(fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment)
                    .load(url)
                    .placeholder(placeHolder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(target);
        }
    }

    public static void load(android.app.Fragment fragment, String url, ImageView imageView, int default_image) {
        if (fragment != null && fragment.getActivity() != null) {
            Glide.with(fragment).load(url).centerCrop().error(default_image).crossFade
                    ().into(imageView);
        }
    }
    private static class ImageTarget extends SimpleTarget<GlideDrawable>{

        private final ImageView mImageView;

        public ImageTarget(ImageView imageView){
            mImageView = imageView;
        }

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            mImageView.setImageDrawable(resource);
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            mImageView.setImageDrawable(errorDrawable);
        }
    }
}
