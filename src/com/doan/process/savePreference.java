package com.doan.process;

import tronglv.gs.model.Share;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class savePreference {
	Activity activity;
	public int[] mangLevel;
	
	public savePreference(Activity activity) {
		this.activity = activity;
	}
	
	public void saveMangDiem(int[] mang) {

		SharedPreferences fileSaved1 = activity.getSharedPreferences("MangSao",
				activity.MODE_PRIVATE);
		SharedPreferences.Editor editor1 = fileSaved1.edit();
		// editor1.clear();

		mangLevel = new int[Share.soLuongMap];
		for (int i = 0; i < Share.soLuongMap; i++) {

			editor1.putInt("lv" + i, mang[i]);

		}
		editor1.commit();
	}

	public void readMangDiem() {
		SharedPreferences settings = activity.getSharedPreferences("MangSao",
				Context.MODE_PRIVATE);
		mangLevel = new int[Share.soLuongMap];
		for (int i = 0; i < Share.soLuongMap; i++) {

			mangLevel[i] = settings.getInt("lv" + i, -1);

		}
		if(mangLevel[0] < 0)
			mangLevel[0] = 0;
	}
}
