package tronglv.gs.view;

import tronglv.gs.control.ResourcesManager;
import tronglv.gs.control.SceneManager;
import tronglv.gs.model.Share;

import com.doan.kimcuong.R;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class dialogThangCuoc {
	private Activity activity;
	private Dialog dialog;
	private ImageView bt_NextMap, bt_menu, bt_replay;  
	private ImageView sao1, sao2, sao3;
	private TextView tv_your_score;
	int map, mode;
	int sosao;
	int[] mangsao;
	
	public dialogThangCuoc(Activity activity, int map, int soSao) {
		this.activity = activity;
		this.map = map;
		this.sosao = soSao;
		Log.e("", "so saoooooooooooooo: "+this.sosao+" map: "+map);
		
		Share.save.mangLevel[map] = 0;
		for(int i=0;i<Share.save.mangLevel.length;i++)
			Log.e("", "m: "+Share.save.mangLevel[i]);
		
		Share.save.saveMangDiem(Share.save.mangLevel);
		Share.save.readMangDiem();
		
		for(int i=0;i<Share.save.mangLevel.length;i++)
			Log.e("", "m1: "+Share.save.mangLevel[i]);
		
		creatDiaLog();
	}
	
	public void creatDiaLog() {
		dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen){
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK) {
                    return false;
                }

                return super.onKeyDown(keyCode, event);
            }
        };
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.quaman_dialog);
		dialog.show();
		
		sao1 = (ImageView) dialog.findViewById(R.id.img_sao1);
		sao2 = (ImageView) dialog.findViewById(R.id.img_sao2);
		sao3 = (ImageView) dialog.findViewById(R.id.img_sao3);
		
		if(sosao == 0){
			sao1.setImageResource(R.drawable.star1);
			sao2.setImageResource(R.drawable.star1);
			sao3.setImageResource(R.drawable.star1);
		}else if(sosao == 1){
			sao2.setImageResource(R.drawable.star1);
			sao3.setImageResource(R.drawable.star1);
		}else if(sosao == 2){
			sao3.setImageResource(R.drawable.star1);
		}
		
		bt_menu = (ImageView) dialog.findViewById(R.id.bt_menu_quaman);
		bt_NextMap = (ImageView) dialog.findViewById(R.id.bt_next_map);
		bt_replay = (ImageView) dialog.findViewById(R.id.bt_replay_map);
		
		bt_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ResourcesManager.engine.start();
//				menuScene.musicPlay.pause();
				SceneManager.getInstance().createMenu();
//				ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						ResourcesManager.getInstance().gold.initInnner();
//					}
//				});
				dialog.dismiss();
			}
		});
		
		bt_NextMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				for(int i=0;i<Share.column;i++){
//					for(int j=0;j<Share.row;j++){
//						if(Share.play.listJewels[i][j].checkr)
//					}
//				}
				ResourcesManager.engine.start();
				SceneManager.getInstance().createMenutoPlay(map+1);
				dialog.dismiss();
				
			}
		});
		
		bt_replay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ResourcesManager.engine.start();
				dialog.dismiss();
				SceneManager.getInstance().createMenutoPlay(map);
			}
		});
		
		tv_your_score = (TextView) dialog.findViewById(R.id.tv_your_score_quaman);
		tv_your_score.setTextColor(Color.parseColor("#7c00b5"));
		tv_your_score.setText(""+Share.tongSoDiem);
	}
}
