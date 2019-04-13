package tronglv.gs.view;

import com.doan.kimcuong.R;

import tronglv.gs.control.ResourcesManager;
import tronglv.gs.control.SceneManager;
import tronglv.gs.model.Share;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class dialogThuaCuoc {
	private Activity activity;
	private Dialog dialog;
	private ImageView bt_restart, bt_menu;  
	private TextView tv_BestScore, tv_YourScore;
	int score;
	
	public dialogThuaCuoc(Activity activity, int yourScore) {
		this.activity = activity;
		this.score = yourScore;
		Log.e("", "map: "+Share.map);
		creatDiaLog();
	}
	
	public void creatDiaLog() {
		dialog = new Dialog(this.activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.thuacuoc_dialog);

		dialog.show();
		
		bt_menu = (ImageView) dialog.findViewById(R.id.bt_menu_thuacuoc);
		bt_restart = (ImageView) dialog.findViewById(R.id.bt_replay_thuacuoc);
		tv_YourScore = (TextView) dialog.findViewById(R.id.tv_your_score);
		tv_YourScore.setTextColor(Color.BLUE);
		
		tv_YourScore.setText(""+score);
		
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
