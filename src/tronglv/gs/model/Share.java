package tronglv.gs.model;

import org.andengine.ui.activity.BaseGameActivity;

import android.R.bool;
import android.util.Log;

import com.doan.process.readDataMap;
import com.doan.process.savePreference;

import tronglv.gs.control.SceneManager;
import tronglv.gs.view.playScene;

public class Share {
	public static BaseGameActivity activity;
	
	public static final int width = 480;
	public static final int height = 800;
	public static final int row = 9;
	public static final int column = 7;
	public static playScene play;
	public static int playTime;
	public static int time;
	public static int tongSoDiem;
	public static int map;
	public static boolean amThanh = true;
	public static boolean khoiTaoSound = true;
	
	//map
	public static int soVien;
	public static int thoiGianChoi;
	public static int MocDiem1;
	public static int MocDiem2;
	public static int MocDiem3;
	public static int Xu;
	public static int soLuongMap = 10;
	public static savePreference save;
	
	// dinh nghia cac su kien
    public static int SU_KIEN_UP = 1;
    public static int SU_KIEN_DOWN = 2;
    public static int SU_KIEN_LEFT = 3;
    public static int SU_KIEN_RIGHT = 4;
    public static int SU_KIEN_DUNG_YEN = 0;
    
    // TYPE cac vien dac biet
    public static int XANH = 1;
    public static int NGU_SAC = 7;
    public static int XU_VANG = 11;
    public static int XU_BAC = 12;
    
    
    public static void ganMap(int m){
    	map = m;
    	Log.e("", "share map: "+map);
    	soVien = readDataMap.listMap.get(m-1).getSoKimCuong();
    	thoiGianChoi = readDataMap.listMap.get(m-1).getThoiGianChoi();
    	MocDiem1 = readDataMap.listMap.get(m-1).getMocDiem1();
    	MocDiem2 = readDataMap.listMap.get(m-1).getMocDiem2();
    	MocDiem3 = readDataMap.listMap.get(m-1).getMocDiem3();
    	Xu = readDataMap.listMap.get(m-1).getXuVang() + readDataMap.listMap.get(m-1).getXuBac();
    	
    	Log.e("", "map: "+(m-1)+"/nso vien: "+soVien+"/n thoi gian choi: "+thoiGianChoi+" moc1: "+MocDiem1
    			+" moc2: "+MocDiem2+" moc3: "+MocDiem3);
    }
}
