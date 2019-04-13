package tronglv.gs.control;

import com.doan.kimcuong.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
 
public class MusicMenu {
	MediaPlayer mediaPlayer;
	 
    /**
    * Load dữ liệu
    * @param mContext
    */
    public void loadMusic(Context mContext){
        mediaPlayer = MediaPlayer.create(mContext, R.raw.nhacnen_manchoi);
        mediaPlayer.setVolume(1f, 1f);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Nhạc nền lặp đi lặp lại, play xong thì lại play lại
                play();
            }
        });
    }
    /**
    * Play music
    */
    public void play(){
    	Log.e("", "menu play");
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }
    /**
    * Pause music
    */
    public void pause(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }
    /**
    * Resume music
    */
    public void resume(){
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }
    /**
    * Xóa bỏ
    */
    public void release(){
        mediaPlayer.release();
    }
}
