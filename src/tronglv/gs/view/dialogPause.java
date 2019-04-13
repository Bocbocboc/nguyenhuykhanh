package tronglv.gs.view;

import com.doan.kimcuong.R;

import tronglv.gs.control.ResourcesManager;
import tronglv.gs.control.SceneManager;
import tronglv.gs.model.Share;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class dialogPause {
	private Activity activity;
	private Dialog dialog;
	private ImageView bt_resum, bt_restart, bt_menu;  
	
	public dialogPause(Activity activity, int map) {
		this.activity = activity;
		Share.map = map;
		creatDiaLog();
	}
	
	public void creatDiaLog() {
		dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.pause_dialog);

		dialog.show();
		
		bt_menu = (ImageView) dialog.findViewById(R.id.bt_menu);
		bt_restart = (ImageView) dialog.findViewById(R.id.bt_restart);
		bt_resum = (ImageView) dialog.findViewById(R.id.bt_resume);
		
		bt_resum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ResourcesManager.getInstance().engine.start();
				dialog.dismiss();
			}
		});
		
		bt_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ResourcesManager.getInstance().engine.start();
				SceneManager.getInstance().createMenu();
				dialog.dismiss();
			}
		});
		
		bt_restart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				ResourcesManager.getInstance().engine.start();
				SceneManager.getInstance().createMenutoPlay(Share.map);
			}
		});
	}
}
