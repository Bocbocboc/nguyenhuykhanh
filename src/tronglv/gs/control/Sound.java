package tronglv.gs.control;
 
import tronglv.gs.model.Share;

import com.doan.kimcuong.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
 
public class Sound {
    SoundPool mSoundPool;
    Context context;
    int anXu = -1, phaNen = -1, anSet= -1, anBomNho= -1, 
    	anNguSac= -1, taoSet= -1, bamNut = -1, thang = -1, thua = -1, 
    	batdaugame = -1, sounddichuyen = -1, bombang = -1, saphetgio = -1,
    	anKC1 = -1, anKC2 = -1, anKC3 = -1, anKC4 = -1, anKC5 = -1;
    float volume = 1f;
 
    /**
    * Load dữ liệu
    * @param context
    */
    public void loadSound(Context context) {
        this.context = context;
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
        anKC5 = mSoundPool.load(this.context, R.raw.an_kc_nam, 1); 
        anSet = mSoundPool.load(this.context, R.raw.set, 1);     
        anBomNho = mSoundPool.load(this.context, R.raw.bom_nho, 1);
        anNguSac = mSoundPool.load(this.context, R.raw.ngu_sac, 1);    
        
        
        
    }
 
    public void offSound() {
        volume = 0;
    }
    
    public void setSound(Float vl){
    	volume = vl;
    }
    
    public void soundAnKC(){
    	if(Share.amThanh)
	    	new Thread(new Runnable() {             
	            @Override
	            public void run() {
	            		mSoundPool.play(anKC5, volume, volume, 1, 0, 1f);
	            }
	        }).start();  
    }
    
    public void playAnSet(){
    	if(Share.amThanh)
	    	new Thread(new Runnable() {             
	            @Override
	            public void run() {
	                mSoundPool.play(anSet, volume, volume, 1, 0, 1f);
	            }
	        }).start();  
    }
    
    public void playAnBomNho(){
    	if(Share.amThanh)
	    	new Thread(new Runnable() {             
	            @Override
	            public void run() {
	                mSoundPool.play(anBomNho, volume, volume, 1, 0, 1f);
	            }
	        }).start();  
    }
    
    public void playAnNguSac(){
    	if(Share.amThanh)
	    	new Thread(new Runnable() {             
	            @Override
	            public void run() {
	                mSoundPool.play(anNguSac, volume, volume, 1, 0, 1f);
	            }
	        }).start();  
    }
    
}