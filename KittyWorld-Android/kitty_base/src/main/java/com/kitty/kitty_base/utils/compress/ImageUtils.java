package com.kitty.kitty_base.utils.compress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 保存读取bitMap
 *
 * @author hoolai
 * @date 2017/8/9
 */
public class ImageUtils {

    private String imagePath;

    public ImageUtils(Context context) {
        imagePath = context.getFilesDir().getPath() + "/advertises/" + "advertise.png";
    }

    /**
     * 获取网络图片
     */
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 保存图片到本地
     */
    public void saveImageToLocal(String url, int reqHeight, int reqWidth) {

        new Thread(() -> {
            Bitmap srcBitmap = getBitmapFromURL(url);
            if (srcBitmap == null) {
                return;
            }
            Bitmap resizedBitmap = BitmapMergeUtil.getResizedBitmap(srcBitmap, reqHeight, reqWidth);
            File file = new File(imagePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                bos.flush();
                bos.close();
                if (!resizedBitmap.isRecycled()) {
                    resizedBitmap.recycle();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

    }

    /**
     * 获取本地图片
     */
    public Bitmap getImageFromLocal() {

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        try {
            File file = new File(imagePath);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    /**
     * 递归删除文件和文件夹
     */
    public void removeFile(File file) {
        file = new File(imagePath);
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                removeFile(f);
            }
            file.delete();
        }
    }

    /**
     * 通过图片地址转为Drawable
     */
    public static Drawable loadImageFromNetwork(String address) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(new URL(address).openStream(), address + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }


    /**
     * 使用BitmapFactory.decodeStream方法，创建出一个bitmap，减少创建 bitmap 时使用的java内存
     *
     * @param context context
     * @param resId   资源id
     * @return 图片bitmap
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

}
