package com.kitty.kitty_base.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.kitty.kitty_base.R;

import java.io.IOException;

public class MediaUtils {


    public static void playMedia(String filename) {
        AssetManager assetManager;
        MediaPlayer player = null;
        player = new MediaPlayer();
        assetManager = Utils.getResources().getAssets();
        try {
            AssetFileDescriptor fileDescriptor = null;
            try {
                fileDescriptor = assetManager.openFd(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.reset();
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getStartOffset());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    /**
//     * 结束播放来电和呼出铃声
//     */
//    public static void stopPlayFromRawFile() {
//        if (mPlayer != null && mPlayer.isPlaying()) {
//            mPlayer.stop();
//            mPlayer.release();
//        }
//        mPlayer = null;
//    }
}
