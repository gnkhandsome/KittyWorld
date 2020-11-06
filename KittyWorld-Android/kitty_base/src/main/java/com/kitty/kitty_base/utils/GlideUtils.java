package com.kitty.kitty_base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.kitty.kitty_base.R;

public class GlideUtils {

    /**
     * 加载圆角图片
     */
    public static void loadRoundImage(int cornerRadius, final Context context, String url, final ImageView imageView, int placeholderId, int errorId) {
        if (imageView == null) {
            return;
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(placeholderId)
                .error(errorId)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(cornerRadius);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * 加载圆形图片
     */
    public static void loadCirclePic(final Context context, String url, final ImageView imageView) {
        if (imageView == null || context == null) {
            return;
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.friend_icon)
                .error(R.drawable.friend_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });

    }
//
//    /**
//     * 加载圆形图片
//     */
//    public static void loadCirclePic(final Context context, String url, final ImageView imageView) {
//        if (imageView == null || context == null) {
//            return;
//        }
//        Glide.with(context)
//                .load(url)
//                .asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .into(new BitmapImageViewTarget(imageView) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        imageView.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
//
//    }

    /**
     * 加载正常图片
     */
    public static void loadNormalPic(Context context, String url, ImageView imageView) {
        if (imageView == null || context == null) {
            return;
        }
        Glide.with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载正常图片
     */
    public static void loadHomeGvPic(Context context, String url, ImageView imageView, int placeHolderImageId, int errorImageId) {
        if (imageView == null) {
            return;
        }
        Glide.with(context)
                .load(url)
                .placeholder(placeHolderImageId)
                .error(errorImageId)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载资源图片
     */
    public static void loadResourcePicture(final Context context, int resourceId, final ImageView imageView) {
        if (imageView == null) {
            return;
        }
        Glide.with(context)
                .fromResource()
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .encoder(new BitmapEncoder(Bitmap.CompressFormat.PNG, 100)).load(resourceId).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(25);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

}
