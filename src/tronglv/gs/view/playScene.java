package tronglv.gs.view;

import java.util.ArrayList;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;

import com.doan.process.readDataMap;

import tronglv.gs.animation.ani5Color;
import tronglv.gs.animation.aniBoom;
import tronglv.gs.animation.aniChumSao;
import tronglv.gs.animation.aniEffectEat;
import tronglv.gs.animation.aniNgoiSao;
import tronglv.gs.animation.aniNhacNuoc;
import tronglv.gs.animation.aniThunder;
import tronglv.gs.animation.aniThunderNew;
import tronglv.gs.animation.aniThunderWhite;
import tronglv.gs.animation.anijewelpurple;
import tronglv.gs.control.ResourcesManager;
import tronglv.gs.control.SceneManager;
import tronglv.gs.model.BaseScene;
import tronglv.gs.model.Share;
import android.graphics.Point;
import android.util.Log;
import android.view.KeyEvent;

public class playScene extends BaseScene {

	int[] sample;

	private int w;
	private int h;

	private CameraScene mPauseScene;

	private int soVienAn;
	private int heSo;
	Text textScore, textThoiGian;
	int timeNhacNuoc;
	private aniNhacNuoc nhacNuoc;
	private aniNgoiSao SaoTrang1, SaoTrang2, SaoTrang3, SaoTrang4;
	int timeSang;

	boolean checkMoveDown = false;
	private boolean lockAll = false;

	private Sprite spriframe;
	private Sprite spriBoard;
	private Sprite sprite_time_bg, sprite_time_dau, sprite_time_giua,
			sprite_time_cuoi;
	private Sprite sprite_score, sprite_bestScore, sprite_pause;
	private anijewelpurple anipurple, anipurple1;
	private aniBoom ani_boom;
	private aniThunder ani_Thunde;
	public Entity etJewel, etEffectEat, etBom, etThunder, etSameColor, etTime,
			etTitle, etNhacNuoc, etNgoiSao, etChumSao;

	boolean touchArea = false, checkMove = true;
	boolean anMang1 = false, anMang2 = false;
	float downX, upX, downY, upY;
	int nhacX1, nhacY1, nhacX2, nhacY2;
	int soBom;

	int saveDownX, saveUpX, saveDownY, saveUpY;
	int[] countEmpty;
	ArrayList<Point> checkList1;
	ArrayList<Point> checkList21;
	ArrayList<Point> mangAnDB;
	ArrayList<Point> mangXoa;
	ArrayList<Point> mangAnDon1;
	ArrayList<Point> mangAnDon2;

	public anijewelpurple[][] listJewels;
	public aniEffectEat[][] listEffects;
	int[][] mangAn1;
	int[][] mangAn2;
	int[][] ListRoi;
	int[][] MTThayDoi;
	public boolean[][] bool_MangXoa;
	boolean[][] bool_CheckList;

	// public aniBoom[][] listBom;

	@Override
	public void createScene() {
		
		new readDataMap(activity);
		
		this.soBom = 0;
		Share.tongSoDiem = 0;
		soVienAn = 0;
		heSo = 1;
		timeNhacNuoc = 0;
		timeSang = 0;
		w = Share.width;
		h = Share.height;
		Share.play = this;
		camera.setCenter(w / 2, h / 2);
		etJewel = new Entity();
		etEffectEat = new Entity();
		etBom = new Entity();
		etThunder = new Entity();
		etSameColor = new Entity();
		etTime = new Entity();
		etTitle = new Entity();
		etNhacNuoc = new Entity();
		etNgoiSao = new Entity();
		etChumSao = new Entity();

		countEmpty = new int[Share.column];
		checkList1 = new ArrayList<Point>();
		mangAnDB = new ArrayList<Point>();
		mangXoa = new ArrayList<Point>();
		mangAnDon1 = new ArrayList<Point>();
		mangAnDon2 = new ArrayList<Point>();

		bool_MangXoa = new boolean[Share.column][Share.row];
		bool_CheckList = new boolean[Share.column][Share.row];

		this.mPauseScene = new CameraScene(resourcesManager.camera);
		sprite_pause = new Sprite(w - 84 - 30, 10, 84, 47,
				resourcesManager.pause_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				boolean istouch = true;
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.setScale(1.05f);
					istouch = true;
					break;
				case TouchEvent.ACTION_UP:
					if (istouch) {
						istouch = false;
						this.setScale(1.0f);
						resourcesManager.engine.stop();

						ResourcesManager.getInstance().activity
								.runOnUiThread(new Runnable() {
									public void run() {
										new dialogPause(activity, Share.map);
									}
								});

					}
					break;
				}
				return true;
			}
		};
		this.mPauseScene.attachChild(sprite_pause);
		this.mPauseScene.setBackgroundEnabled(false);

		// int[] sample1 ={
		// 5, 4, 3, 2, 1, 4, 6,
		// 5, 4, 1, 4, 6, 5, 3,
		// 4, 3, 5, 5, 6, 5, 5,
		// 4, 1, 4, 5, 1, 6, 1,
		// 3, 3, 2, 3, 1, 6, 6,
		// 2, 6, 3, 5, 3, 5, 6,
		// 5, 1, 4, 1, 5, 6, 5,
		// 1, 5, 2, 3, 4, 4, 5,
		// 2, 3, 6, 1, 2, 1, 1
		// };

		// dac biet
		int[] sample1 = { 
				 5, 2, 3, 3, 1, 4, 6,
				 5, 6, 3, 4, 3, 5, 3,
				 2, 1, 6, 5, 6, 5, 5,
				 6, 6, 4, 5, 1, 1, 1,
				 6, 4, 6, 3, 4, 4, 6,
				 2, 6, 1, 5, 6, 5, 6,
				 5, 1, 5, 1, 5, 6, 5,
				 1, 12, 2, 3, 4, 4, 5,
				 2, 3, 3, 1, 2, 1, 1
		};


		sample = sample1;

		mangAn1 = new int[9][2];
		mangAn2 = new int[9][2];
		ListRoi = new int[Share.column][Share.row];
		MTThayDoi = new int[Share.column][Share.row];

		listEffects = new aniEffectEat[Share.column][Share.row];
		listJewels = new anijewelpurple[Share.column][Share.row];
		
	}
	
	public playScene(int map){
		Log.e("", "play map: "+map);
		Share.ganMap(map);
		resetMangAn();
		SinhMang();
		draw();
		
		boolean gem;
		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				if (listJewels[i][j].getType() <= Share.NGU_SAC && KiemTraAnDon(i, j)) {
					Log.e("kiem tra an ban dau", "kiem tra an ban dau: " + i+ " " + j);
					if (KiemTraSinhGemDBDon() > 1)
						addAnDBDon(listJewels[i][j].getType());
					else if (KiemTraSinhGemDBDon() == 1)
						addAnBTDon();
					int x, y;
					while (!mangXoa.isEmpty()) {
						x = mangXoa.get(0).x;
						y = mangXoa.get(0).y;
						if (listJewels[x][y].getBom()) {
							addBom(x, y, false, mangAn1[2][0], mangAn1[2][1],
									false, -1, -1);
						}
						if (listJewels[x][y].getThunderRow()) {
							addThunderRow(y, false, mangAn1[2][0],
									mangAn1[2][1], false, -1, -1);
						}
						if (listJewels[x][y].getThunderColumn()) {
							addThunderColumn(x, false, mangAn1[2][0],
									mangAn1[2][1], false, -1, -1);
						}
						// gan mau cho 5 sac sau
						if (listJewels[x][y].getSameColor()) {
							addSameColor(1, false, x, y);
						}
						mangXoa.remove(0);
					}

					SinhGemMoi1(i, j);
					RoiAll();
					GemMoi();
					resetMangAn();
				}
			}
			NhacNuoc();
		}
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {

		super.onManagedUpdate(pSecondsElapsed);
	}

	private void draw() {
		attachChild(new Sprite(0, 0, resourcesManager.play_bg_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
		Share.time = Share.thoiGianChoi;
		Share.playTime = Share.thoiGianChoi;

		sprite_score = new Sprite(20, 20, 120, 33,
				resourcesManager.score_region, vbom);
		// sprite_pause = new Sprite(w-84-30, 10, 84,47,
		// resourcesManager.pause_region, vbom);
		textScore = new Text(
				sprite_score.getX() + sprite_score.getWidth() - 10, 26,
				resourcesManager.mFont, "", 20, vbom);// Tối đa hiện thị 10 ký
														// tự

		etTitle.attachChild(sprite_score);
		// etTitle.attachChild(sprite_pause);
		etTitle.attachChild(textScore);

		textScore.setText(Share.tongSoDiem+"/"+Share.MocDiem1);

		spriframe = new Sprite(0, 0, resourcesManager.play_frame_region, vbom);
		spriBoard = new Sprite(9, w / Share.column + 2,
				resourcesManager.play_board_region, vbom);
		sprite_time_bg = new Sprite(spriBoard.getX() + 10, spriBoard.getY()
				+ spriBoard.getHeight() + 10, w - spriBoard.getX() - 30, 40,
				resourcesManager.time_bg_region, vbom);

		sprite_time_dau = new Sprite(spriBoard.getX() + 10
				+ sprite_time_bg.getWidth() / 5 - 4, spriBoard.getY()
				+ spriBoard.getHeight() + 16, 20, 25,
				resourcesManager.time_dau_region, vbom);

		sprite_time_giua = new Sprite(spriBoard.getX() + 10
				+ sprite_time_bg.getWidth() / 5 + sprite_time_dau.getWidth()
				- 6, spriBoard.getY() + spriBoard.getHeight() + 16,
				((sprite_time_bg.getWidth() * 4 / 5) - 60), 25,
				resourcesManager.time_giua_region, vbom);

		sprite_time_cuoi = new Sprite(spriBoard.getX() + 10
				+ sprite_time_bg.getWidth() / 5 + sprite_time_dau.getWidth()
				+ sprite_time_giua.getWidth() - 17, spriBoard.getY()
				+ spriBoard.getHeight() + 16, 20, 25,
				resourcesManager.time_cuoi_region, vbom);

		textThoiGian = new Text(spriBoard.getX() + 55, spriBoard.getY()
				+ spriBoard.getHeight() + 18, resourcesManager.mFontTime, "",
				10, vbom);

		etTime.attachChild(sprite_time_bg);
		etTime.attachChild(sprite_time_dau);
		etTime.attachChild(sprite_time_giua);
		etTime.attachChild(sprite_time_cuoi);
		etTime.attachChild(textThoiGian);

		textThoiGian.setText("" + 60);

		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				etJewel.attachChild(listJewels[i][j]);
			}
		}

		nhacNuoc = new aniNhacNuoc(0, 0, (w - 6) / Share.column, (w - 6)
				/ Share.column, resourcesManager.nhacNuoc_TitleRegion, vbom,
				etNhacNuoc, nhacNuocHinhDau(), nhacNuocHinhCuoi());
		etNhacNuoc.attachChild(nhacNuoc);
		etNhacNuoc.detachChild(nhacNuoc);

		SaoTrang1 = new aniNgoiSao(0, 0, 60, 60,
				resourcesManager.ngoiSaoTrang_region, vbom, etNgoiSao, 0, 0);
		SaoTrang2 = new aniNgoiSao(0, 0, 60, 60,
				resourcesManager.ngoiSaoTrang_region, vbom, etNgoiSao, 0, 0);
		SaoTrang3 = new aniNgoiSao(0, 0, 60, 60,
				resourcesManager.ngoiSaoTrang_region, vbom, etNgoiSao, 0, 0);
		SaoTrang4 = new aniNgoiSao(0, 0, 60, 60,
				resourcesManager.ngoiSaoTrang_region, vbom, etNgoiSao, 0, 0);
		etNgoiSao.attachChild(SaoTrang1);
		etNgoiSao.attachChild(SaoTrang2);
		etNgoiSao.attachChild(SaoTrang3);
		etNgoiSao.attachChild(SaoTrang4);

		etNgoiSao.detachChild(SaoTrang1);
		etNgoiSao.detachChild(SaoTrang2);
		etNgoiSao.detachChild(SaoTrang3);
		etNgoiSao.detachChild(SaoTrang4);

		this.setTouchAreaBindingOnActionMoveEnabled(true);
		this.setOnSceneTouchListener(new IOnSceneTouchListener() {

			@Override
			public boolean onSceneTouchEvent(Scene pScene,
					TouchEvent pSceneTouchEvent) {


				if (pSceneTouchEvent.getY() > w / Share.column + 2
						&& pSceneTouchEvent.getY() < 10 * w / Share.column + 2) {
					if (pSceneTouchEvent.getAction() == 2 && checkMove == true) {
						if (touchArea == false) {
							touchArea = true;
							downX = pSceneTouchEvent.getX();
							downY = pSceneTouchEvent.getY();
						} else {
							if (pSceneTouchEvent.getX() - downX >= 8
									|| pSceneTouchEvent.getY() - downY >= 8
									|| pSceneTouchEvent.getX() - downX <= -8
									|| pSceneTouchEvent.getY() - downY <= -8) {
								checkMove = false;
								upX = pSceneTouchEvent.getX();
								upY = pSceneTouchEvent.getY();

								saveDownX = (int) ((downX - 8) / (w / Share.column));
								saveDownY = (int) ((downY - (w / Share.column)) / (w / Share.column));
								saveUpX = (int) ((upX - 8) / (w / Share.column));
								saveUpY = (int) ((upY - (w / Share.column)) / (w / Share.column));

								Log.e("down", "" + saveDownX + " " + saveDownY);
								Log.e("su kien","su kien : "+ getSuKien(downX, downY, upX,upY));
								XuLySuKien(getSuKien(downX, downY, upX, upY));
							}
						}
					}
					if (pSceneTouchEvent.getAction() == 1) {
						checkMove = true;
						touchArea = false;
					}
				}

				return true;
			}
		});


		this.attachChild(spriBoard);
		this.attachChild(etEffectEat);
		this.attachChild(etJewel);
		this.attachChild(etThunder);
		this.attachChild(etSameColor);
		this.attachChild(etBom);
		this.attachChild(etNhacNuoc);
		this.attachChild(etNgoiSao);
		this.attachChild(etChumSao);
		this.attachChild(spriframe);
		this.attachChild(etTime);
		this.attachChild(etTitle);
		this.attachChild(mPauseScene);
		this.registerTouchArea(sprite_pause);

		this.registerUpdateHandler(new TimerHandler(1, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						if (Share.time > 1) {
							Share.time--;
							timeNhacNuoc++;
							if (timeNhacNuoc == 5) {
								if(nhacX1 > -1)
									nhacNuoc.startNhacNuoc(((w - 6) / Share.column)
										* (nhacX1 + nhacX2) / 2 + 7,
										((w - 6) / Share.column)
												* (nhacY1 + nhacY2 + 2) / 2,
										nhacNuocHinhDau(), nhacNuocHinhCuoi());
							}

							timeSang++;
							if (timeSang == 5) {
								timeSang = 0;
								SaoTrang1.startNgoiSao(
										getRandom(0, Share.column - 1)
												* (w - 6) / Share.column,
										getRandom(0, Share.row - 1) * (w - 6)
												/ Share.column, 60, 60);
								SaoTrang2.startNgoiSao(
										getRandom(0, Share.column - 1)
												* (w - 6) / Share.column,
										getRandom(0, Share.row - 1) * (w - 6)
												/ Share.column, 60, 60);
								SaoTrang3.startNgoiSao(
										getRandom(0, Share.column - 1)
												* (w - 6) / Share.column,
										getRandom(0, Share.row - 1) * (w - 6)
												/ Share.column, 60, 60);
								SaoTrang4.startNgoiSao(
										getRandom(0, Share.column - 1)
												* (w - 6) / Share.column,
										getRandom(0, Share.row - 1) * (w - 6)
												/ Share.column, 60, 60);
							}
							/*
							textThoiGian.setText("" + Share.time);
//							Log.e("log time","log time: "+ Share.time+ " "
//											+ (60 * (sprite_time_bg.getWidth() * 5 / 4) - 20)/ 60);
							sprite_time_giua.setWidth(60 * ((sprite_time_bg
									.getWidth() * 4 / 5) - 60) / 60);
							sprite_time_dau.setX(spriBoard.getX() + 10
									+ sprite_time_bg.getWidth() / 5 - 4);
							sprite_time_cuoi.setX(spriBoard.getX() + 10
									+ sprite_time_bg.getWidth() / 5
									+ sprite_time_dau.getWidth()
									+ sprite_time_giua.getWidth() - 17);*/
						}
						if (Share.time > 0) {
							Log.e("", "time: "+Share.time);
							sprite_time_giua.setWidth(Share.time
									* ((sprite_time_bg.getWidth() * 4 / 5) - 60)
									/ Share.playTime);
							sprite_time_dau.setX(spriBoard.getX() + 10
									+ sprite_time_bg.getWidth() / 5 - 4);
							sprite_time_cuoi.setX(spriBoard.getX() + 10
									+ sprite_time_bg.getWidth() / 5
									+ sprite_time_dau.getWidth()
									+ sprite_time_giua.getWidth() - 17);
							textThoiGian.setText("" + Share.time);
						}
						if(Share.time <= 1){
							Log.e("", "so diem: "+Share.tongSoDiem+" "+Share.MocDiem1);
							resourcesManager.engine.stop();
							if(Share.tongSoDiem < Share.MocDiem1){
								ResourcesManager.getInstance().activity
								.runOnUiThread(new Runnable() {
									public void run() {
										new dialogThuaCuoc(activity, Share.tongSoDiem);
									}
								});
							}else{
								ResourcesManager.getInstance().activity
								.runOnUiThread(new Runnable() {
									public void run() {
										int soSao;
										if(Share.tongSoDiem >= Share.MocDiem3)
											soSao = 3;
										else if(Share.tongSoDiem >= Share.MocDiem2)
											soSao = 2;
										else
											soSao = 1;
										new dialogThangCuoc(activity, Share.map, soSao);
									}
								});
							}
						}
					}
				}));
	}

	public int nhacNuocHinhDau() {
		if (nhacX1 == nhacX2)
			return 2;
		else
			return 0;
	}

	public int nhacNuocHinhCuoi() {
		if (nhacX1 == nhacX2)
			return 3;
		else
			return 1;
	}

	@Override
	public void onBackKeyPressed() {
		resourcesManager.engine.stop();
		new dialogPause(activity, Share.map);

	}

	public void SinhMang() {
		int random;
		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				random = getRandom(1, Share.soVien-1);
				anijewelpurple jewel = new anijewelpurple(
						(i * w / Share.column) + 8 - 2 * i, 
						(j + 1) * (w)/ Share.column - 2 * j, 
						(w - 6) / Share.column,(w - 6) / Share.column, random,
						resourcesManager.play_jewel_new_region, vbom, i, j);
				
				
//				 anijewelpurple jewel = new anijewelpurple((i*w/Share.column)+8 - 2*i,
//				 (j+1)*(w)/Share.column - 3*(j-1),
//				 (w-6)/Share.column, (w-6)/Share.column, sample[j*7+i],
//				 resourcesManager.play_jewel_new_region, vbom, i, j);

				listJewels[i][j] = jewel;
				listJewels[i][j].setEffect(false, false, false, false);

			}
		}
		//logMang();
		// listJewels[5][6].setEffect(false, false, false, true);
	}

	public int getRandom(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	public ITiledTextureRegion getTiledTextureRegion(int random) {
		if (random == 1) {
			return resourcesManager.play_jewelGreen_region;
		} else if (random == 2) {
			return resourcesManager.play_jewelRed_region;
		} else if (random == 3) {
			return resourcesManager.play_jewelBlue_region;
		} else if (random == 4) {
			return resourcesManager.play_jewelYeallow_region;
		} else if (random == 5) {
			return resourcesManager.play_jewelPink_region;
		} else if (random == 6) {
			return resourcesManager.play_jewelOrange_region;
		} else if (random == 7)
			return resourcesManager.play_jewelSameColor_region;
		return null;
	}

	public int getSuKien(float xDown, float yDown, float xUp, float yUp) {
		float lechDuong = 8, lechAm = -8;
		if (xUp - xDown > lechDuong || xUp - xDown < lechAm
				|| yUp - yDown > lechDuong || yUp - yDown < lechAm) {

			if (yUp - yDown < lechAm && xUp - xDown < (yUp - yDown) * (-1)
					&& saveDownY > 0)
				return Share.SU_KIEN_UP;
			if (yUp - yDown < lechAm
					&& (xUp - xDown) * (-1) < (yUp - yDown) * (-1)
					&& saveDownY > 0)
				return Share.SU_KIEN_UP;

			if (xUp - xDown > lechDuong && xUp - xDown > (yUp - yDown) * (-1)
					&& saveDownX < Share.column - 1)
				return Share.SU_KIEN_RIGHT;
			if (xUp - xDown > lechDuong && xUp - xDown > yUp - yDown
					&& saveDownX < Share.column - 1)
				return Share.SU_KIEN_RIGHT;

			if (yUp - yDown > lechDuong && xUp - xDown < yUp - yDown
					&& saveDownY < Share.row - 1)
				return Share.SU_KIEN_DOWN;
			if (yUp - yDown > lechDuong && (xUp - xDown) * (-1) < yUp - yDown
					&& saveDownY < Share.row - 1)
				return Share.SU_KIEN_DOWN;

			if (xUp - xDown < lechAm && (xUp - xDown) * (-1) > yUp - yDown
					&& saveDownX > 0)
				return Share.SU_KIEN_LEFT;
			if (xUp - xDown < lechAm
					&& (xUp - xDown) * (-1) > (yUp - yDown) * (-1)
					&& saveDownX > 0)
				return Share.SU_KIEN_LEFT;
		}
		return Share.SU_KIEN_DUNG_YEN;
	}

	public int suKienNguoc(int suKien) {
		if (suKien == 1)
			return 2;
		else if (suKien == 2)
			return 1;
		else if (suKien == 3)
			return 4;
		else if (suKien == 4)
			return 3;
		else
			return 0;
	}

	public void XuLySuKien(int suKien) {
		heSo = 1;
		timeNhacNuoc = 0;
		etNhacNuoc.detachChild(nhacNuoc);
		logMang();
		if (!lockAll) {
			switch (suKien) {
			case 1:
				if (!listJewels[saveDownX][saveDownY].getLock()
						&& !listJewels[saveDownX][saveDownY - 1].getLock()) {
					DoiCho(saveDownX, saveDownY, saveDownX, saveDownY - 1,
							suKien, false);
					// XuLyKhiAn(saveDownX, saveDownY, saveDownX, saveDownY-1,
					// suKien);
				}
				break;

			case 2:
				if (!listJewels[saveDownX][saveDownY].getLock()
						&& !listJewels[saveDownX][saveDownY + 1].getLock()) {
					DoiCho(saveDownX, saveDownY, saveDownX, saveDownY + 1,
							suKien, false);
					// XuLyKhiAn(saveDownX, saveDownY, saveDownX, saveDownY+1,
					// suKien);
				}
				break;

			case 3:
				if (!listJewels[saveDownX][saveDownY].getLock()
						&& !listJewels[saveDownX - 1][saveDownY].getLock()) {
					DoiCho(saveDownX, saveDownY, saveDownX - 1, saveDownY,
							suKien, false);
					// XuLyKhiAn(saveDownX, saveDownY, saveDownX-1, saveDownY,
					// suKien);
				}
				break;

			case 4:
				if (!listJewels[saveDownX][saveDownY].getLock()
						&& !listJewels[saveDownX + 1][saveDownY].getLock()) {
					DoiCho(saveDownX, saveDownY, saveDownX + 1, saveDownY,
							suKien, false);
					// XuLyKhiAn(saveDownX, saveDownY, saveDownX+1, saveDownY,
					// suKien);
				}
				break;

			default:
				break;
			}
		}
	}

	public void DoiCho(int x1, int y1, int x2, int y2, int suKien, boolean kiemtra) {
		anijewelpurple tempJewel1 = listJewels[x1][y1];
		anijewelpurple tempJewel2 = listJewels[x2][y2];

		if (!kiemtra) {
			listJewels[x1][y1].Swap(x1, y1, x2, y2, suKien, true);
			listJewels[x2][y2].Swap(x1, y1, x2, y2, suKienNguoc(suKien), false);
		}

		listJewels[x1][y1] = tempJewel2;
		listJewels[x2][y2] = tempJewel1;
	}

	public void AnNguSac(int x1, int y1, int x2, int y2) {
		menuScene.mSound.playAnNguSac();
		mangAn1[1][0] = x1;
		mangAn1[1][1] = y1;
		mangAn2[1][0] = x2;
		mangAn2[1][1] = y2;

		if (!listJewels[x1][y1].getSameColor()
				&& listJewels[x2][y2].getSameColor()) {
			if (!bool_MangXoa[x2][y2]) {
				bool_MangXoa[x2][y2] = true;
				mangXoa.add(new Point(x2, y2));
				// hieu ung an cua
				HieuUngSameColor(x2, y2);
				HieuUngAn(x2, y2, listJewels[x1][y1].getType());
				mangAnDB.add(new Point(x2, y2));
			}
			addSameColor(listJewels[x1][y1].getType(), false, -1, -1);
			listJewels[x2][y2].setType(listJewels[x1][y1].getType());
			listJewels[x2][y2].setEffect(false, false, false, false);

		} else if (listJewels[x1][y1].getSameColor()
				&& !listJewels[x2][y2].getSameColor()) {
			if (!bool_MangXoa[x1][y1]) {
				bool_MangXoa[x1][y1] = true;
				mangXoa.add(new Point(x1, y1));
				// hieu ung an cua
				HieuUngSameColor(x1, y1);
				HieuUngAn(x1, y1, listJewels[x2][y2].getType());
				mangAnDB.add(new Point(x1, y1));
			}
			addSameColor(listJewels[x2][y2].getType(), false, -1, -1);
			listJewels[x1][y1].setType(listJewels[x2][y2].getType());
			listJewels[x1][y1].setEffect(false, false, false, false);

		} else { // khi ca 2 vien deu la ngu sac
			Log.e("ca 2 deu la 5 sac", "ca 2 deu la 5 sao");
			AnNguSacNguSac(x1, y1, x2, y2);
		}

		KiemTraLaiMangAn();

		RoiAll();
		logCheckList();
		GemMoi();
		logMang();
		checkMoveDown = false;
		resetMangAn();
	}

	public void AnBomBom(int x, int y) {
		addBomTo(x, y);
		KiemTraLaiMangAn();
		RoiAll();
		HieuUngBomTo(x, y);
		GemMoi();
		logMang();
		checkMoveDown = false;
		resetMangAn();
	}

	public void AnThunderThunder(int x1, int y1, int x2, int y2) {
		listJewels[x1][y1].setEffect(false, false, false, false);
		listJewels[x2][y2].setEffect(false, false, false, false);
		addThunderRow(y2, false, -1, -1, false, -1, -1);
		addThunderColumn(x2, false, -1, -1, false, -1, -1);
		HieuUngThunderColumn(x2, y2, listJewels[x2][y2].getType());
		HieuUngThunderRow(x2, y2, listJewels[x2][y2].getType());
		KiemTraLaiMangAn();
		RoiAll();
		GemMoi();
		logMang();
		checkMoveDown = false;
		resetMangAn();
	}

	public void AnBomThunder(int x, int y) {
		if (x - 1 >= 0) {
			HieuUngThunderColumn(x - 1, y, listJewels[x - 1][y].getType());
			addThunderColumn(x - 1, false, -1, -1, false, -1, -1);
		}
		if (x + 1 <= Share.column - 1) {
			HieuUngThunderColumn(x + 1, y, listJewels[x + 1][y].getType());
			addThunderColumn(x + 1, false, -1, -1, false, -1, -1);
		}
		if (y - 1 >= 0) {
			HieuUngThunderRow(x, y - 1, listJewels[x][y - 1].getType());
			addThunderRow(y - 1, false, -1, -1, false, -1, -1);
		}
		if (y + 1 <= Share.row - 1) {
			HieuUngThunderRow(x, y + 1, listJewels[x][y + 1].getType());
			addThunderRow(y + 1, false, -1, -1, false, -1, -1);
		}

		HieuUngThunderRow(x, y, listJewels[x][y].getType());
		HieuUngThunderColumn(x, y, listJewels[x][y].getType());
		HieuUngBomNho(x, y);

		addThunderColumn(x, false, -1, -1, false, -1, -1);
		addThunderRow(y, false, -1, -1, false, -1, -1);

		KiemTraLaiMangAn();
		RoiAll();
		GemMoi();
		logMang();
		checkMoveDown = false;
		resetMangAn();
	}

	public void AnNguSacBom(int x1, int y1, int x2, int y2) {
		menuScene.mSound.playAnNguSac();
		int type;
		lockAll = true;
		if (listJewels[x1][y1].getSameColor()) {
			type = listJewels[x2][y2].getType();
			listJewels[x1][y1].setBomAll(true);
			HieuUngSameColor(x1, y1);
		} else {
			type = listJewels[x1][y1].getType();
			listJewels[x2][y2].setBomAll(true);
			HieuUngSameColor(x2, y2);
		}

		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				Log.e("log", "log: " + i + " " + j);
				if (listJewels[i][j].getType() == type) {
					Log.e("hieu ung", "hieu ung: " + i + " " + j);
					Log.e("hieu ung",
							"hieu ung mau: " + listJewels[i][j].getType());
					if (!listJewels[i][j].getBom()
							&& !listJewels[i][j].getThunderColumn()
							&& !listJewels[i][j].getThunderRow())
						listJewels[i][j].setBomAll(false);
					if (!bool_MangXoa[i][j]) {
						bool_MangXoa[i][j] = true;
						HieuUngSameColor(i, j);
						mangXoa.add(new Point(i, j));

						mangAnDB.add(new Point(i, j));
					}
				}
			}
		}
	}

	public void AnNguSacThunder(int x1, int y1, int x2, int y2) {
		menuScene.mSound.playAnNguSac();
		int type;
		lockAll = true;
		if (listJewels[x1][y1].getSameColor()) {
			type = listJewels[x2][y2].getType();
			listJewels[x1][y1].setThunderAll(true);
			HieuUngSameColor(x1, y1);
		} else {
			type = listJewels[x1][y1].getType();
			listJewels[x2][y2].setThunderAll(true);
			HieuUngSameColor(x2, y2);
		}

		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				if (listJewels[i][j].getType() == type) {
					if (!listJewels[i][j].getBom()
							&& !listJewels[i][j].getThunderColumn()
							&& !listJewels[i][j].getThunderRow())
						listJewels[i][j].setThunderAll(false);
					if (!bool_MangXoa[i][j]) {
						bool_MangXoa[i][j] = true;
						HieuUngSameColor(i, j);
						mangXoa.add(new Point(i, j));
						Log.e("hieu ung", "hieu ung: " + i + " " + j);
						Log.e("hieu ung",
								"hieu ung mau: " + listJewels[i][j].getType());
						mangAnDB.add(new Point(i, j));
					}
				}
			}
		}

	}

	public void AnNguSacNguSac(int x1, int y1, int x2, int y2) {
		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				bool_MangXoa[i][j] = true;
				mangXoa.add(new Point(i, j));
				mangAnDB.add(new Point(i, j));
				HieuUngAn(i, j, listJewels[i][j].getType());
			}
		}
		listJewels[x1][y1].setType(1);
		listJewels[x1][y1].setEffect(false, false, false, false);
		listJewels[x2][y2].setType(1);
		listJewels[x2][y2].setEffect(false, false, false, false);
		HieuUngSameColorTo();
	}

	public void CallBackDB() {
		for (int i = 0; i < mangXoa.size(); i++) {
			addHieuUng(mangXoa.get(i).x, mangXoa.get(i).y);
			HieuUngAn(mangXoa.get(i).x, mangXoa.get(i).y,
					listJewels[mangXoa.get(i).x][mangXoa.get(i).y].getType());
		}
		KiemTraLaiMangAn();
		RoiAll();
		GemMoi();
		logMang();
		checkMoveDown = false;
		resetMangAn();
		lockAll = false;
	}

	public void XuLyKhiAn(int x1, int y1, int x2, int y2, int suKien) {
		anijewelpurple tempJewel1 = listJewels[x1][y1];
		anijewelpurple tempJewel2 = listJewels[x2][y2];

		// listJewels[x1][y1].Swap(suKien);
		// listJewels[x2][y2].Swap(suKienNguoc(suKien));
		//
		// listJewels[x1][y1] = tempJewel2;
		// listJewels[x2][y2] = tempJewel1;

		boolean an = false;
		
		if (listJewels[x1][y1].getType() <= Share.NGU_SAC && listJewels[x2][y2].getType() <= 7 &&
				(listJewels[x1][y1].getSameColor() || listJewels[x2][y2].getSameColor()) && 
				(!listJewels[x1][y1].getBom() && !listJewels[x1][y1].getThunderColumn() &&
						!listJewels[x1][y1].getThunderRow()) && (!listJewels[x2][y2].getBom() &&
								!listJewels[x2][y2].getThunderColumn() && !listJewels[x2][y2].getThunderRow())) {
			// kiem tra an 5
			Log.e("an 5 sac", "an 5 sac");
			AnNguSac(x1, y1, x2, y2);
		} else if (listJewels[x1][y1].getBom() && listJewels[x2][y2].getBom()) {
			// kiem tra an bom + bom
			Log.e("an bom + bom", "an bom + bom: " + x2 + " " + y2);
			listJewels[x1][y1].setEffect(false, false, false, false);
			listJewels[x2][y2].setEffect(false, false, false, false);
			AnBomBom(x2, y2);
		} else if ((listJewels[x1][y1].getThunderColumn() || listJewels[x1][y1]
				.getThunderRow())
				&& (listJewels[x2][y2].getThunderColumn() || listJewels[x2][y2]
						.getThunderRow())) {
			// kiem tra an thunder + thunder
			Log.e("an thunder + thunder", "an thunder + thunder: " + x2 + " "
					+ y2);
			AnThunderThunder(x1, y1, x2, y2);
		} else if (((listJewels[x1][y1].getThunderColumn() || listJewels[x1][y1]
				.getThunderRow()) && listJewels[x2][y2].getBom())
				|| ((listJewels[x2][y2].getThunderColumn() || listJewels[x2][y2]
						.getThunderRow()) && listJewels[x1][y1].getBom())) {
			// kiểm tra an bom + thunder
			Log.e("an bom + thunder", "an bom + thunder: " + x2 + " " + y2);
			listJewels[x1][y1].setEffect(false, false, false, false);
			listJewels[x2][y2].setEffect(false, false, false, false);
			AnBomThunder(x2, y2);
		} else if ((listJewels[x1][y1].getSameColor() && listJewels[x2][y2]
				.getBom())
				|| listJewels[x2][y2].getSameColor()
				&& listJewels[x1][y1].getBom()) {
			// kiem tra an ngu sac + bom
			Log.e("an ngu sac + bom",
					"an ngu sac + bom: " + listJewels[x1][y1].getBom() + " "
							+ listJewels[x2][y2].getBom() + " " + x2 + " " + y2);
			AnNguSacBom(x1, y1, x2, y2);
		} else if (((listJewels[x1][y1].getThunderColumn() || listJewels[x1][y1]
				.getThunderRow()) && listJewels[x2][y2].getSameColor())
				|| ((listJewels[x2][y2].getThunderColumn() || listJewels[x2][y2]
						.getThunderRow()) && listJewels[x1][y1].getSameColor())) {
			// kiem tra an ngu sac + set
			Log.e("an ngu sac + set", "an ngu sac + set: " + x2 + " " + y2);
			AnNguSacThunder(x1, y1, x2, y2);
		} else {
			// khac an nhung truong hop an rat rat dac biet
			if(listJewels[x1][y1].getType() < Share.NGU_SAC){
				if (anMang1 = KiemTraAn(1, x1, y1)) {
					an = true;
				}
			}
			
			if(listJewels[x2][y2].getType() < Share.NGU_SAC){
				if (anMang2 = KiemTraAn(2, x2, y2)) {
					an = true;
				}
			}

			// ko an
			if (!an) {
				Log.e("ko an", "ko an: " + suKien + " + " + x1 + " " + y1
						+ " - " + x2 + " " + y2 + " + " + suKienNguoc(suKien));
				listJewels[x1][y1].setMoveBack(true, suKien);
				listJewels[x2][y2].setMoveBack(true, suKienNguoc(suKien));
//				listJewels[x1][y1].Swap(x1, y1, x2, y2, suKien, false);
//				listJewels[x2][y2].Swap(x1, y1, x2, y2, suKienNguoc(suKien), false);
				// doi cho
				listJewels[x1][y1] = tempJewel2;
				listJewels[x2][y2] = tempJewel1;
			} else {
				menuScene.mSound.soundAnKC();
				logMangAn();

				if (KiemTraGemDB())
					addAnDB(-1);
				else
					addAnBT();

				logArrayListXoa();

				KiemTraLaiMangAn();

				SinhGemMoi1(x1, y1);
				SinhGemMoi2(x2, y2);
				logMangXoa();
				RoiAll();
				GemMoi();
				logMang();
			}
			checkMoveDown = false;
			an = false;
			resetMangAn();
		}
	}
	

	public void KiemTraLaiMangAn() {
		int x = -1, y = -1;
		boolean DB1 = false, DB2 = false;
		if (KiemTraSinhGemDB1())
			DB1 = true;
		if (KiemTraSinhGemDB2())
			DB2 = true;

		while (!mangXoa.isEmpty()) {
			x = mangXoa.get(0).x;
			y = mangXoa.get(0).y;
			Log.e("kirm tra mang xoa", "kirm tra mang xoa: " + x + " " + y);
			if (listJewels[x][y].getBom()) {
				Log.e("vao bom", "vao bom");
				addBom(x, y, DB1, mangAn1[2][0], mangAn1[2][1], DB2,
						mangAn2[2][0], mangAn2[2][1]);
			}
			if (listJewels[x][y].getThunderRow()) {
				Log.e("vao thunder row", "vao thunder row");
				addThunderRow(y, DB1, mangAn1[2][0], mangAn1[2][1], DB2,
						mangAn2[2][0], mangAn2[2][1]);
			}
			if (listJewels[x][y].getThunderColumn()) {
				Log.e("vao thunder column", "vao thunder column");
				addThunderColumn(x, DB1, mangAn1[2][0], mangAn1[2][1], DB2,
						mangAn2[2][0], mangAn2[2][1]);
			}
			// gan mau cho 5 sac sau
			if (listJewels[x][y].getSameColor()) {
				addSameColor(1, false, x, y);
			}
			mangXoa.remove(0);
		}
	}

	public boolean KiemTraAnDon(int x, int y) {
		Log.e("kiem tra don", "kiem tra don: " + x + " " + y);
		if(listJewels[x][y].getType() <= Share.NGU_SAC){
			mangAnDon1.add(new Point(x, y));
			mangAnDon2.add(new Point(x, y));
			int xtam = x, ytam = y;
			while (xtam - 1 >= 0) {
				xtam -= 1;
				if (listJewels[xtam][ytam].getType() == listJewels[x][y].getType()) {
					mangAnDon1.add(new Point(xtam, ytam));
				} else
					break;
			}
	
			xtam = x;
			ytam = y;
			while (xtam + 1 <= Share.column - 1) {
				xtam += 1;
				if (listJewels[xtam][ytam].getType() == listJewels[x][y].getType()) {
					mangAnDon1.add(new Point(xtam, ytam));
				} else
					break;
			}
	
			xtam = x;
			ytam = y;
			while (ytam - 1 >= 0) {
				ytam -= 1;
				if (listJewels[xtam][ytam].getType() == listJewels[x][y].getType()) {
					mangAnDon2.add(new Point(xtam, ytam));
				} else
					break;
			}
	
			xtam = x;
			ytam = y;
			while (ytam + 1 <= Share.row - 1) {
				ytam += 1;
				if (listJewels[xtam][ytam].getType() == listJewels[x][y].getType()) {
					mangAnDon2.add(new Point(xtam, ytam));
				} else
					break;
			}
			if (mangAnDon1.size() < 3)
				mangAnDon1.clear();
			if (mangAnDon2.size() < 3)
				mangAnDon2.clear();
	
			for (int i = 0; i < mangAnDon1.size(); i++) {
				Log.e("mang an don 1", "mang an don 1: " + mangAnDon1.get(i).x
						+ " " + mangAnDon1.get(i).y);
			}
			for (int i = 0; i < mangAnDon2.size(); i++) {
				Log.e("mang an don 2", "mang an don 2: " + mangAnDon2.get(i).x
						+ " " + mangAnDon2.get(i).y);
			}
	
			if (KiemTraSinhGemDBDon() >= 1)
				return true;
	}
		return false;
	}

	public boolean KiemTraAn(int status, int indexX, int indexY) {
		int[][] mangAn = new int[9][2];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 2; j++) {
				mangAn[i][j] = -1;
				if (status == 1)
					mangAn1[i][j] = -1;
				else if (status == 2)
					mangAn2[i][j] = -1;
			}
		}

		mangAn[2][0] = indexX;
		mangAn[2][1] = indexY;
		// chieu ngang
		if (indexX - 1 >= 0) {
			if (listJewels[indexX - 1][indexY].getType() == listJewels[indexX][indexY]
					.getType()) {
				if (indexX - 2 >= 0) {
					if (listJewels[indexX][indexY].getType() == listJewels[indexX - 2][indexY]
							.getType()) {
						mangAn[0][0] = indexX - 2;
						mangAn[0][1] = indexY;
						mangAn[1][0] = indexX - 1;
						mangAn[1][1] = indexY;
					}
				}
				if (indexX + 1 <= Share.column - 1) {
					if (listJewels[indexX][indexY].getType() == listJewels[indexX + 1][indexY]
							.getType()) {
						mangAn[1][0] = indexX - 1;
						mangAn[1][1] = indexY;
						mangAn[3][0] = indexX + 1;
						mangAn[3][1] = indexY;
						if (indexX + 2 <= Share.column - 1) {
							if (listJewels[indexX][indexY].getType() == listJewels[indexX + 2][indexY]
									.getType()) {
								mangAn[4][0] = indexX + 2;
								mangAn[4][1] = indexY;
							}
						}
					}
				}
			}
		}

		if (indexX + 2 <= Share.column - 1) {
			if (listJewels[indexX][indexY].getType() == listJewels[indexX + 1][indexY]
					.getType()
					&& listJewels[indexX][indexY].getType() == listJewels[indexX + 2][indexY]
							.getType()) {
				mangAn[3][0] = indexX + 1;
				mangAn[3][1] = indexY;
				mangAn[4][0] = indexX + 2;
				mangAn[4][1] = indexY;
			}
		}

		// chieu doc
		if (indexY - 1 >= 0) {
			if (listJewels[indexX][indexY - 1].getType() == listJewels[indexX][indexY]
					.getType()) {
				if (indexY - 2 >= 0) {
					if (listJewels[indexX][indexY].getType() == listJewels[indexX][indexY - 2]
							.getType()) {
						mangAn[5][0] = indexX;
						mangAn[5][1] = indexY - 2;
						mangAn[6][0] = indexX;
						mangAn[6][1] = indexY - 1;
					}
				}
				if (indexY + 1 <= Share.row - 1) {
					if (listJewels[indexX][indexY].getType() == listJewels[indexX][indexY + 1]
							.getType()) {
						mangAn[6][0] = indexX;
						mangAn[6][1] = indexY - 1;
						mangAn[7][0] = indexX;
						mangAn[7][1] = indexY + 1;
						if (indexY + 2 <= Share.column - 1) {
							if (listJewels[indexX][indexY].getType() == listJewels[indexX][indexY + 2]
									.getType()) {
								mangAn[8][0] = indexX;
								mangAn[8][1] = indexY + 2;
							}
						}
					}
				}
			}
		}

		if (indexY + 2 <= Share.row - 1) {
			if (listJewels[indexX][indexY].getType() == listJewels[indexX][indexY + 1]
					.getType()
					&& listJewels[indexX][indexY].getType() == listJewels[indexX][indexY + 2]
							.getType()) {
				mangAn[7][0] = indexX;
				mangAn[7][1] = indexY + 1;
				mangAn[8][0] = indexX;
				mangAn[8][1] = indexY + 2;
			}
		}

		int dem = 0;
		if (status == 1) {
			for (int i = 0; i < 9; i++) {
				if (mangAn[i][0] > -1) {
					dem++;
					mangAn1[i][0] = mangAn[i][0];
					mangAn1[i][1] = mangAn[i][1];
				}
			}
		} else if (status == 2) {
			for (int i = 0; i < 9; i++) {
				if (mangAn[i][0] > -1) {
					dem++;
					mangAn2[i][0] = mangAn[i][0];
					mangAn2[i][1] = mangAn[i][1];
				}
			}
		}
		if (dem >= 2)
			return true;
		else {
			if (status == 1) {
				mangAn1[2][0] = -1;
				mangAn1[2][1] = -1;
			} else if (status == 2) {
				mangAn2[2][0] = -1;
				mangAn2[2][1] = -1;
			}
			return false;
		}
	}

	public int KiemTraSinhGemDBDon() {
		if (mangAnDon1.size() >= 5 || mangAnDon2.size() >= 5)
			return 5;
		if (mangAnDon1.size() == 4)
			return 4;
		if (mangAnDon2.size() == 4)
			return 3;
		if (mangAnDon1.size() + mangAnDon2.size() == 6)
			return 2;
		if (mangAnDon1.size() == 3 || mangAnDon2.size() == 3)
			return 1;
		return 0;
	}

	public boolean KiemTraSinhGemDB1() {
		if (SinhGemMang1() > 1) {
			return true;
		}
		return false;
	}

	public boolean KiemTraSinhGemDB2() {
		if (SinhGemMang2() > 1) {
			return true;
		}
		return false;
	}

	public boolean SinhGemMoi1(int x, int y) {
		anijewelpurple jewel;
		Log.e("gem mang 1", "sinh gem mang 1: " + SinhGemMang1());
		if (SinhGemMang1() == 5) {
			// listJewels[x][y] = null;
			soVienAn++;
			etJewel.detachChild(listJewels[x][y]);
//			jewel = new anijewelpurple((x * w / Share.column) + 8 - 2 * x,
//					(y + 1) * (w) / Share.column - 2 * y, (w - 6)
//							/ Share.column, (w - 6) / Share.column, 7,
//					getTiledTextureRegion(7), vbom, x, y);
//			listJewels[x][y] = jewel;
//			
//			listJewels[x][y].setEffect(true, false, false, false);
//			listJewels[x][y].NewJewel((x * w / Share.column) + 8 - 2 * x, (y + 1) * (w) / Share.column - 2 * y, 7, 1, x, y);
			listJewels[x][y].NewJewel((x*w/Share.column)+8 - 2*x,(y+1)*(w)/Share.column - 3*(y-1), 7, 1, x, y);
			etJewel.attachChild(listJewels[x][y]);
			listJewels[x][y].setVisible(true);
			Log.e("sinh 5 same", "sinh 5 same: " + listJewels[x][y].getType());
			return true;
		}
		if (SinhGemMang1() == 4) {
			soVienAn++;
			Log.e("sinh 4 ngang", "sinh 4 ngang");
			listJewels[x][y].setEffect(false, true, false, false);
			listJewels[x][y].setVisible(true);
			return true;
		}
		if (SinhGemMang1() == 3) {
			soVienAn++;
			Log.e("sinh 3 doc", "sinh 3 doc");
			listJewels[x][y].setEffect(false, false, true, false);
			listJewels[x][y].setVisible(true);
			return true;
		}
		if (SinhGemMang1() == 2) {
			soVienAn++;
			Log.e("sinh 2 no", "sinh 2 no");
			listJewels[x][y].setEffect(false, false, false, true);
			listJewels[x][y].setVisible(true);
			return true;
		}

		return false;
	}

	public boolean SinhGemMoi2(int x1, int y1) {
		anijewelpurple jewel;
		Log.e("sinh gem mang 2", "sinh gem mang 2: " + SinhGemMang2());
		if (SinhGemMang2() == 5) {
			soVienAn++;
			// listJewels[x1][y1] = null;
			etJewel.detachChild(listJewels[x1][y1]);
//			jewel = new anijewelpurple((x1 * w / Share.column) + 8 - 2 * x1,
//					(y1 + 1) * (w) / Share.column - 2 * y1, (w - 6)
//							/ Share.column, (w - 6) / Share.column, 7,
//					getTiledTextureRegion(7), vbom, x1, y1);
//			listJewels[x1][y1] = jewel;
//			
//			listJewels[x1][y1].setEffect(true, false, false, false);
//			listJewels[x1][y1].NewJewel((x1 * w / Share.column) + 8 - 2 * x1, (y1 + 1) * (w) / Share.column - 2 * y1, 7, 1, x1, y1);
			listJewels[x1][y1].NewJewel((x1*w/Share.column)+8 - 2*x1, (y1+1)*(w)/Share.column - 3*(y1-1), 7, 1, x1, y1);
			etJewel.attachChild(listJewels[x1][y1]);
			listJewels[x1][y1].setVisible(true);
			Log.e("sinh 5 same", "sinh 5 same: " + listJewels[x1][y1].getType());
			return true;
		}
		if (SinhGemMang2() == 4) {
			soVienAn++;
			Log.e("sinh 4 ngang", "sinh 4 ngang");
			listJewels[x1][y1].setEffect(false, true, false, false);
			listJewels[x1][y1].setVisible(true);
			return true;
		}
		if (SinhGemMang2() == 3) {
			soVienAn++;
			Log.e("sinh 3 doc", "sinh 3 doc");
			listJewels[x1][y1].setEffect(false, false, true, false);
			listJewels[x1][y1].setVisible(true);
			return true;
		}
		if (SinhGemMang2() == 2) {
			soVienAn++;
			Log.e("sinh 2 no", "sinh 2 no");
			listJewels[x1][y1].setEffect(false, false, false, true);
			listJewels[x1][y1].setVisible(true);
			return true;
		}
		return false;
	}

	public boolean SinhGemMoi(int x, int y, int loai) {
		anijewelpurple jewel;
		Log.e("sinh gem moi don neeee", "sinh gem moi don neeee: " + x + " "
				+ y + " - " + loai);
		if (loai == 5) {
			soVienAn++;
			// listJewels[x][y] = null;
			etJewel.detachChild(listJewels[x][y]);
//			jewel = new anijewelpurple((x * w / Share.column) + 8 - 2 * x,
//					(y + 1) * (w) / Share.column - 2 * y, (w - 6)
//							/ Share.column, (w - 6) / Share.column, 7,
//					getTiledTextureRegion(7), vbom, x, y);
//			
//			listJewels[x][y].setEffect(true, false, false, false);
//			listJewels[x][y].setVisible(true);
//			listJewels[x][y] = jewel;
//			listJewels[x][y].NewJewel((x * w / Share.column) + 8 - 2 * x, (y + 1) * (w) / Share.column - 2 * y, 7, 1, x, y);
			listJewels[x][y].NewJewel((x*w/Share.column)+8 - 2*x, (y+1)*(w)/Share.column - 3*(y-1), 7, 1, x, y);
			etJewel.attachChild(listJewels[x][y]);
			
			return true;
		}
		if (loai == 4) {
			soVienAn++;
			listJewels[x][y].setEffect(false, true, false, false);
			listJewels[x][y].setVisible(true);
			return true;
		}
		if (loai == 3) {
			soVienAn++;
			listJewels[x][y].setEffect(false, false, true, false);
			listJewels[x][y].setVisible(true);
			return true;
		}
		if (loai == 2) {
			soVienAn++;
			listJewels[x][y].setEffect(false, false, false, true);
			listJewels[x][y].setVisible(true);
			return true;
		}
		return false;
	}

	public int SinhGemMang2() {
		int m2_1 = 0, m2_2 = 0;
		for (int i = 0; i <= 4; i++) {
			if (mangAn2[i][1] > -1)
				m2_1++;
		}

		for (int i = 5; i < 9; i++) {
			if (mangAn2[i][1] > -1)
				m2_2++;
		}

		if (m2_1 == 5 || m2_2 == 4)
			return 5; // gem cap 4
		if (m2_1 == 4)
			return 4; // gem cap 3 ngang
		if (m2_2 == 3)
			return 3; // gem cap 3 doc
		if (m2_1 + m2_2 == 5)
			return 2; // gem cap 2
		return 0; // gem cap 1
	}

	public int SinhGemMang1() {
		int m1_1 = 0, m1_2 = 0;
		for (int i = 0; i <= 4; i++) {
			if (mangAn1[i][1] > -1)
				m1_1++;
		}

		for (int i = 5; i < 9; i++) {
			if (mangAn1[i][1] > -1)
				m1_2++;
		}

		if (m1_1 == 5 || m1_2 == 4)
			return 5; // gem cap 4
		if (m1_1 == 4)
			return 4; // gem cap 3 ngang
		if (m1_2 == 3)
			return 3; // gem cap 3 doc
		if (m1_1 + m1_2 == 5)
			return 2; // gem cap 2
		return 0; // gem cap 1
	}

	public void HieuUngAn(int x, int y, int mau) {
		int m = mau;
		if (mau >= Share.NGU_SAC)
			mau = 0;
		listEffects[x][y] = new aniEffectEat(((x) * w / Share.column) + 8 - 2
				* (x), (y + 1) * (w) / Share.column - 2 * (y), (w - 6)
				/ Share.column, (w - 6) / Share.column, getEffectRegion(mau),
				vbom, etEffectEat, 0, 5);
		etEffectEat.attachChild(listEffects[x][y]);
		Log.e("hieu ung an", "hieu ung an: " + x + " " + y);
	}

	public void HieuUngSameColor(int x, int y) {
		etSameColor.attachChild(new ani5Color(x * (w - 6) / Share.column, w
				/ Share.column + 2, (w - 6) / Share.column, y * (w - 6)
				/ Share.column, resourcesManager.sameColor_TitleRegion, vbom,
				etSameColor, 0, 3));
	}

	public void HieuUngSameColorTo() {
		etSameColor.attachChild(new ani5Color(
				((0) * w / Share.column) + 8 - 2 * (0), (1 + 1) * (w)
						/ Share.column - 2 * (1), 7 * (w - 6) / Share.column, 7
						* (w - 6) / Share.column,
				resourcesManager.bigSameColor_TitleRegion, vbom, etSameColor,
				0, 5));
	}

	public void HieuUngBomNho(int x, int y) {
		etBom.attachChild(new aniBoom(
				(float) (((x - 0.5) * w / Share.column) + 8 - 2 * (x - 0.5)),
				(float) ((y + 0.5) * (w) / Share.column - 2 * (y + 0.5)), 2
						* (w - 6) / Share.column, 2 * (w - 6) / Share.column,
				resourcesManager.boom_region, vbom, etBom, 0, 5));
	}

	public void HieuUngBomTo(int x, int y) {
		etBom.attachChild(new aniBoom(((x - 2) * w / Share.column) + 8 - 2
				* (x - 2), (y - 1) * (w) / Share.column - 2 * (y - 1), 5
				* (w - 6) / Share.column, 5 * (w - 6) / Share.column,
				resourcesManager.boom_region, vbom, etBom, 0, 5));
	}

	public void HieuUngThunderRow(int x, int y, int mau) {
		// etThunder.attachChild(new aniThunder(10, (y+1)*(w-6)/Share.column,
		// Share.column * (w-6)/Share.column, (w-6)/Share.column,
		// resourcesManager.thunderRow_TitleRegion, vbom, etThunder, 0, 3));

		etThunder.attachChild(new aniThunderNew(x * (w - 6) / Share.column + 2,
				(y + 1) * w / Share.column - 10, (w - 6) / Share.column,
				(w - 6) / Share.column,
				resourcesManager.thunderRow_TitleRegion, vbom, etThunder,
				mau - 1, mau - 1, false));
		etThunder.attachChild(new aniThunderWhite(x * (w - 6) / Share.column
				+ 2, (y + 1) * w / Share.column - 10, (w - 6) / Share.column,
				(w - 6) / Share.column,
				resourcesManager.thunderRow_TitleRegion, vbom, etThunder, 6, 6,
				false));

	}

	public void HieuUngThunderColumn(int x, int y, int mau) {
		Log.e("mau", "mauuuuuu: " + mau);
		// etThunder.attachChild(new aniThunder(x*(w-6)/Share.column,
		// w/Share.column+2,
		// (w-6)/Share.column, Share.row*(w-6)/Share.column,
		// resourcesManager.thunderColumn_TitleRegion, vbom, etThunder, 0, 3));

		etThunder.attachChild(new aniThunderNew(x * (w - 6) / Share.column + 2,
				(y + 1) * w / Share.column - 10, (w - 6) / Share.column,
				(w - 6) / Share.column,
				resourcesManager.thunderColumn_TitleRegion, vbom, etThunder,
				mau - 1, mau - 1, true));
		etThunder.attachChild(new aniThunderWhite(x * (w - 6) / Share.column
				+ 2, (y + 1) * w / Share.column - 10, (w - 6) / Share.column,
				(w - 6) / Share.column,
				resourcesManager.thunderColumn_TitleRegion, vbom, etThunder, 6,
				6, true));

	}

	public void addHieuUng(int x, int y) {
		if (listJewels[x][y].getBom()) {
			Log.e("bommmm", "bommmm");
			HieuUngBomNho(x, y);
		} else if (listJewels[x][y].getThunderColumn()) {
			Log.e("thunder columnnnn", "thunder columnnnn");
			HieuUngThunderColumn(x, y, listJewels[x][y].getType());
		} else if (listJewels[x][y].getThunderRow()) {
			Log.e("thunder rowwwww", "thunder rowwwww");
			HieuUngThunderRow(x, y, listJewels[x][y].getType());
		} else if (listJewels[x][y].getSameColor()) {
			Log.e("same colorrrr", "same colorrrr");
			Log.e("same color", "same color");
		}
	}

	public TiledTextureRegion getEffectRegion(int type) {
		if (type == 1)
			return resourcesManager.play_effectEatGreen_region;
		else if (type == 2)
			return resourcesManager.play_effectEatRed_region;
		else if (type == 3)
			return resourcesManager.play_effectEatBlue_region;
		else if (type == 4)
			return resourcesManager.play_effectEatYeallow_region;
		else if (type == 5)
			return resourcesManager.play_effectEatPink_region;
		else if (type == 6)
			return resourcesManager.play_effectEatOrange_region;
		else if(type == 0)
			return resourcesManager.play_effectEatWhite_region;
		else
			return null;
	}

	public void KiemTraCheckList() {
		boolean nhipRoi = false;
		Log.e("bat dau kiem tra check lít", "bat dau kiem tra check lít");
		logCheckList();
		if (!checkList1.isEmpty()) {
			while (!checkList1.isEmpty()) {
				Log.e("kiem tra checkList1", "kiem tra checkList1: " + checkList1.get(0).x + " "+ checkList1.get(0).y);
				if (KiemTraAnDon(checkList1.get(0).x, checkList1.get(0).y)) {
					if (nhipRoi == false) {
						nhipRoi = true;
						heSo++;
					}
					// KiemTraAn(1, checkList1.get(0).x, checkList1.get(0).y
					Log.e("check list 1 an","check list 1 an: " + checkList1.get(0).x + " "+ checkList1.get(0).y);
					logMang();
					// if(KiemTraGemDB())
					// addAnDB(-1);
					// else
					// addAnBT();
					if (KiemTraSinhGemDBDon() > 1)
						addAnDBDon(listJewels[checkList1.get(0).x][checkList1
								.get(0).y].getType());
					else if (KiemTraSinhGemDBDon() == 1)
						addAnBTDon();

					logArrayListXoa();
					int x, y;
					while (!mangXoa.isEmpty()) {
						x = mangXoa.get(0).x;
						y = mangXoa.get(0).y;
						if (listJewels[x][y].getBom()) {
							addBom(x, y, false, mangAn1[2][0], mangAn1[2][1],
									false, -1, -1);
						}
						if (listJewels[x][y].getThunderRow()) {
							addThunderRow(y, false, mangAn1[2][0],
									mangAn1[2][1], false, -1, -1);
						}
						if (listJewels[x][y].getThunderColumn()) {
							addThunderColumn(x, false, mangAn1[2][0],
									mangAn1[2][1], false, -1, -1);
						}
						// gan mau cho 5 sac sau
						if (listJewels[x][y].getSameColor()) {
							addSameColor(1, false, x, y);
						}
						mangXoa.remove(0);
					}

					SinhGemMoi1(checkList1.get(0).x, checkList1.get(0).y);
					RoiAll();
					logCheckList();
					GemMoi();
					resetMangAn();
				}
				checkList1.remove(0);

			}
		}
		logMang();
		NhacNuoc();
	}

	public void addCheckList(int stt) {
		checkMoveDown = false;
		int y = -1;
		for (int i = 0; i < Share.column; i++) {
			y = -1;
			for (int j = 0; j < 9; j++) {
				if (mangAn1[j][0] == i && mangAn1[j][1] > y)
					y = mangAn1[j][1];
			}

			for (int k = 0; k < 9; k++) {
				if (mangAn2[k][0] == i && mangAn2[k][1] > y)
					y = mangAn2[k][1];
			}

			while (y >= 0) {
				if (bool_CheckList[i][y] == false) {
					Log.e("bool check list", "bool check list: "
							+ bool_CheckList[i][y]);
					bool_CheckList[i][y] = true;
					checkList1.add(new Point(i, y));
				}
				// if(stt == 2){
				// checkList2.add(new Point(i, y));
				// }
				y--;
			}
		}

		logMangRoi();

		// if(!checkList1.isEmpty() || !checkList2.isEmpty())
		// KiemTraCheckList();
	}

	public void GemMoi() {
		Log.e("bat dau gem moi", "bat dau gem moi");
		int random;
		boolean goiLai = false;
		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				if (listJewels[i][j].getType() == 0) {
					Log.e("sinh gem", "sinh gem: "+i+" "+j);
					soVienAn++;
					random = getRandom(1, 6);
					listJewels[i][j].NewJewel((i*w/Share.column)+8 - 2*i,(j+1)*(w)/Share.column - 3*(j-1), random, 0, i, j);
					if (goiLai == true)
						listJewels[i][j].startJewel(false);
					else {
						goiLai = true;
						listJewels[i][j].startJewel(true);
					}
					Log.e("sinh vien moi", "sinh vien moi: " + random + ": "
							+ i + " " + j);
				}
			}
		}
		textThoiGian.setText("" + Share.time);
		Share.tongSoDiem += soVienAn * 80 * heSo;
		Log.e("", "he sooooo: " + heSo + " " + soVienAn + " "
				+ Share.tongSoDiem);
		textScore.setText("" + Share.tongSoDiem+"/"+Share.MocDiem1);
	}

	public void RoiAll() {

		Log.e("bat dau roi all", "bat dau roi all");
		boolean checkGoiLai = false;
		int x = -1, y = -1;
		anijewelpurple jewel;
		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				if (bool_MangXoa[i][j]) {
					x = i;
					y = j;
					while (y - 1 >= 0) {
						if (bool_CheckList[x][y] == false) {
							bool_CheckList[x][y] = true;
							checkList1.add(new Point(x, y));
						}

						if (checkGoiLai == true){
							Log.e("set move down", "set move down: "+x+" "+(y-1));
							listJewels[x][y - 1].setMoveDown(1, false);
						}

						if (checkGoiLai == false) {
							Log.e("danh dau goi lai", "danh dau goi lai: " + x
									+ " " + y);
							checkGoiLai = true;
							listJewels[x][y - 1].setMoveDown(1, true);
						}

						jewel = listJewels[x][y];
						listJewels[x][y] = listJewels[x][y - 1];
						listJewels[x][y - 1] = jewel;
						y--;
					}
					if (bool_CheckList[x][y] == false) {
						bool_CheckList[x][y] = true;
						checkList1.add(new Point(x, y));
					}
					Log.e("roi all bi xoa", "roi all bi xoa: " + x + " " + y);
					listJewels[x][y].setRemove();
				}
			}
		}
	}

	public boolean KiemTraGemDB() {
		for (int i = 0; i < 9; i++) {
			if (mangAn1[i][0] > 0) {
				if (listJewels[mangAn1[i][0]][mangAn1[i][1]].getBom()
						|| listJewels[mangAn1[i][0]][mangAn1[i][1]]
								.getThunderRow()
						|| listJewels[mangAn1[i][0]][mangAn1[i][1]]
								.getThunderColumn()
						|| listJewels[mangAn1[i][0]][mangAn1[i][1]]
								.getSameColor())
					return true;
			}
		}

		for (int i = 0; i < 9; i++) {
			if (mangAn2[i][0] > 0) {
				if (listJewels[mangAn2[i][0]][mangAn2[i][1]].getBom()
						|| listJewels[mangAn2[i][0]][mangAn2[i][1]]
								.getThunderRow()
						|| listJewels[mangAn2[i][0]][mangAn2[i][1]]
								.getThunderColumn()
						|| listJewels[mangAn2[i][0]][mangAn2[i][1]]
								.getSameColor())
					return true;
			}
		}
		return false;
	}

	public void addAnBT() {
		for (int i = 0; i < 9; i++) {
			if (KiemTraSinhGemDB1() && i == 2)
				continue;
			if (mangAn1[i][0] > -1) {
				Log.e("add an bt", "add an bt: " + mangAn1[i][0] + " "
						+ mangAn1[i][1]);
				if (bool_MangXoa[mangAn1[i][0]][mangAn1[i][1]] == false) {
					bool_MangXoa[mangAn1[i][0]][mangAn1[i][1]] = true;
					mangXoa.add(new Point(mangAn1[i][0], mangAn1[i][1]));
					HieuUngAn(mangAn1[i][0], mangAn1[i][1],
							listJewels[mangAn1[i][0]][mangAn1[i][1]].getType());
					addHieuUng(mangAn1[i][0], mangAn1[i][1]);
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			if (KiemTraSinhGemDB2() && i == 2)
				continue;
			if (mangAn2[i][0] > -1) {
				if (bool_MangXoa[mangAn2[i][0]][mangAn2[i][1]] == false) {
					Log.e("add an bt", "add an bt: " + mangAn2[i][0] + " "
							+ mangAn2[i][1]);
					bool_MangXoa[mangAn2[i][0]][mangAn2[i][1]] = true;
					mangXoa.add(new Point(mangAn2[i][0], mangAn2[i][1]));
					HieuUngAn(mangAn2[i][0], mangAn2[i][1],
							listJewels[mangAn2[i][0]][mangAn2[i][1]].getType());
					addHieuUng(mangAn2[i][0], mangAn2[i][1]);
				}
			}
		}
	}

	public void addAnBTDon() {
		int x = -1, y = -1;
		for (int i = 0; i < mangAnDon1.size(); i++) {
			x = mangAnDon1.get(i).x;
			y = mangAnDon1.get(i).y;
			if (bool_MangXoa[x][y] == false) {
				bool_MangXoa[x][y] = true;
				mangXoa.add(new Point(x, y));
				HieuUngAn(x, y, listJewels[x][y].getType());
				addHieuUng(x, y);
			}

		}

		for (int i = 0; i < mangAnDon2.size(); i++) {
			x = mangAnDon2.get(i).x;
			y = mangAnDon2.get(i).y;
			if (bool_MangXoa[x][y] == false) {
				bool_MangXoa[x][y] = true;
				mangXoa.add(new Point(x, y));
				HieuUngAn(x, y, listJewels[x][y].getType());
				addHieuUng(x, y);
			}

		}
	}

	public void addAnDBDon(int sameType) {
		boolean DB = false;
		int luuX = -1, luuY = -1;
		int loai = KiemTraSinhGemDBDon();
		if (loai > 0) {
			DB = true;
			if (mangAnDon1.size() > 0) {
				luuX = mangAnDon1.get(0).x;
				luuY = mangAnDon1.get(0).y;
				mangAnDon1.remove(0);
			}
			if (mangAnDon2.size() > 0) {
				luuX = mangAnDon2.get(0).x;
				luuY = mangAnDon2.get(0).y;
				mangAnDon2.remove(0);
			}
			Log.e("an don sinh gem DB", "an don sinh gem DB: " + luuX + " "
					+ luuY);
			if (listJewels[luuX][luuY].getBom())
				addBom(luuX, luuY, DB, luuX, luuY, false, -1, -1);
			else if (listJewels[luuX][luuY].getThunderColumn())
				addThunderColumn(luuX, DB, luuX, luuY, false, -1, -1);
			else if (listJewels[luuX][luuY].getThunderRow())
				addThunderRow(luuY, DB, luuX, luuY, false, -1, -1);
			HieuUngAn(luuX, luuY, sameType);
			addHieuUng(luuX, luuY);

			SinhGemMoi(luuX, luuY, loai);
		}
		int x = -1, y = -1;
		for (int i = 0; i < mangAnDon1.size(); i++) {
			x = mangAnDon1.get(i).x;
			y = mangAnDon1.get(i).y;
			Log.e("an don add mang xóa", "an don add mang xóa: " + x + " " + y);
			if (bool_MangXoa[x][y] == false) {
				bool_MangXoa[x][y] = true;
				mangXoa.add(new Point(x, y));
				if (sameType > -1) {
					HieuUngAn(x, y, sameType);
					addHieuUng(x, y);
				} else {
					addHieuUng(x, y);
					HieuUngAn(x, y, listJewels[x][y].getType());
				}
			}
			if (listJewels[x][y].getBom())
				addBom(x, y, DB, luuX, luuY, false, -1, -1);
			else if (listJewels[x][y].getThunderColumn())
				addThunderColumn(x, DB, luuX, luuY, false, -1, -1);
			else if (listJewels[x][y].getThunderRow())
				addThunderRow(y, DB, luuX, luuY, false, -1, -1);
		}

		for (int i = 0; i < mangAnDon2.size(); i++) {
			x = mangAnDon2.get(i).x;
			y = mangAnDon2.get(i).y;
			Log.e("an don add mang xóa", "an don add mang xóa: " + x + " " + y);
			if (bool_MangXoa[x][y] == false) {
				bool_MangXoa[x][y] = true;
				mangXoa.add(new Point(x, y));
				if (sameType > -1) {
					HieuUngAn(x, y, sameType);
					addHieuUng(x, y);
				} else {
					addHieuUng(x, y);
					HieuUngAn(x, y, listJewels[x][y].getType());
				}
			}

			if (listJewels[x][y].getBom())
				addBom(x, y, DB, luuX, luuY, false, -1, -1);
			else if (listJewels[x][y].getThunderColumn())
				addThunderColumn(x, DB, luuX, luuY, false, -1, -1);
			else if (listJewels[x][y].getThunderRow())
				addThunderRow(y, DB, luuX, luuY, false, -1, -1);
		}

	}

	public void addAnDB(int sameType) {
		Log.e("same color", "same color: " + sameType);
		int x = -1, y = -1;
		boolean DB1 = false, DB2 = false;
		logMangAn();
		DB1 = KiemTraSinhGemDB1();
		DB2 = KiemTraSinhGemDB2();
		Log.e("kiem tra sinh gem DB", "kiem tra sinh gem DB: " + DB1 + " "
				+ DB2);
		// mang an 1
		for (int i = 0; i < 9; i++) {
			if (DB1 && i == 2) {
				addHieuUng(mangAn1[i][0], mangAn1[i][1]);
				Log.e("continue1", "continue1");
				continue;
			}
			if (mangAn1[i][0] > -1) {
				if (bool_MangXoa[mangAn1[i][0]][mangAn1[i][1]] == false) {
					bool_MangXoa[mangAn1[i][0]][mangAn1[i][1]] = true;
					mangXoa.add(new Point(mangAn1[i][0], mangAn1[i][1]));
					if (sameType > -1) {
						HieuUngAn(mangAn1[i][0], mangAn1[i][1], sameType);
						addHieuUng(mangAn1[i][0], mangAn1[i][1]);
					} else {
						addHieuUng(mangAn1[i][0], mangAn1[i][1]);
						HieuUngAn(mangAn1[i][0], mangAn1[i][1],
								listJewels[mangAn1[i][0]][mangAn1[i][1]]
										.getType());
					}
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			if (mangAn1[i][0] > -1) {
				x = mangAn1[i][0];
				y = mangAn1[i][1];
				if (listJewels[x][y].getBom()) {
					addBom(x, y, DB1, mangAn1[2][0], mangAn1[2][1], DB2,
							mangAn2[2][0], mangAn2[2][1]);
				}
				if (listJewels[x][y].getThunderRow()) {
					addThunderRow(y, DB1, mangAn1[2][0], mangAn1[2][1], DB2,
							mangAn2[2][0], mangAn2[2][1]);
				}
				if (listJewels[x][y].getThunderColumn()) {
					addThunderColumn(x, DB1, mangAn1[2][0], mangAn1[2][1], DB2,
							mangAn2[2][0], mangAn2[2][1]);
				}
				if (listJewels[x][y].getSameColor()) {
					addSameColor(sameType, false, x, y);
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			if (DB2 && i == 2) {
				addHieuUng(mangAn2[i][0], mangAn2[i][1]);
				Log.e("continue2", "continue2");
				continue;
			}
			if (mangAn2[i][0] > -1) {
				if (bool_MangXoa[mangAn2[i][0]][mangAn2[i][1]] == false) {
					bool_MangXoa[mangAn2[i][0]][mangAn2[i][1]] = true;
					mangXoa.add(new Point(mangAn2[i][0], mangAn2[i][1]));
					if (sameType > -1) {
						addHieuUng(mangAn2[i][0], mangAn2[i][1]);
						HieuUngAn(mangAn2[i][0], mangAn2[i][1], sameType);
					} else {
						addHieuUng(mangAn2[i][0], mangAn2[i][1]);
						HieuUngAn(mangAn2[i][0], mangAn2[i][1],
								listJewels[mangAn2[i][0]][mangAn2[i][1]]
										.getType());
					}
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			if (mangAn2[i][0] > -1) {
				x = mangAn2[i][0];
				y = mangAn2[i][1];
				if (listJewels[x][y].getBom()) {
					addBom(x, y, DB1, mangAn1[2][0], mangAn1[2][1], DB2,
							mangAn2[2][0], mangAn2[2][1]);
				}
				if (listJewels[x][y].getThunderRow()) {
					addThunderRow(y, DB1, mangAn1[2][0], mangAn1[2][1], DB2,
							mangAn2[2][0], mangAn2[2][1]);
				}
				if (listJewels[x][y].getThunderColumn()) {
					addThunderColumn(x, DB1, mangAn1[2][0], mangAn1[2][1], DB2,
							mangAn2[2][0], mangAn2[2][1]);
				}
				if (listJewels[x][y].getSameColor()) {
					addSameColor(sameType, false, x, y);
				}
			}
		}

	}

	public void addBomTo(int x, int y) {
		menuScene.mSound.playAnBomNho();
		Log.e("add bom bom", "add bom bom: " + x + " " + y);
		for (int i = x - 2; i <= x + 2; i++) {
			for (int j = y - 2; j <= y + 2; j++) {
				Log.e("bom bom", "bom bom: " + i + " " + j);
				if (i <= Share.column - 1 && i >= 0 && j <= Share.row - 1
						&& j >= 0 && listJewels[i][j].getType() <= Share.NGU_SAC) {
					if (bool_MangXoa[i][j] == false) {
						bool_MangXoa[i][j] = true;
						mangXoa.add(new Point(i, j));
						addHieuUng(i, j);
						HieuUngAn(i, j, listJewels[x][y + 1].getType());
						mangAnDB.add(new Point(i, j));
					}
				}
			}
		}
	}

	public void addBom(int x, int y, Boolean DB1, int DB1x, int DB1y,
			boolean DB2, int DB2x, int DB2y) {
		menuScene.mSound.playAnBomNho();
		Log.e("add bom", "add bom: " + x + " " + y);
		if (y + 1 <= Share.row - 1)
			if (listJewels[x][y+1].getType() <= Share.NGU_SAC && !bool_MangXoa[x][y + 1]) {
				if ((!DB1 || x != DB1x || (y + 1) != DB1y)
						&& (!DB2 || x != DB2x || (y + 1) != DB2y)) {
					if (bool_MangXoa[x][y + 1] == false) {
						bool_MangXoa[x][y + 1] = true;
						mangXoa.add(new Point(x, y + 1));
						addHieuUng(x, y + 1);
						HieuUngAn(x, y + 1, listJewels[x][y + 1].getType());
						mangAnDB.add(new Point(x, y + 1));
					}
				}
			}

		if (y - 1 >= 0)
			if (listJewels[x][y-1].getType() <= Share.NGU_SAC && !bool_MangXoa[x][y - 1]) {
				if ((!DB1 || x != DB1x || (y - 1) != DB1y)
						&& (!DB2 || x != DB2x || (y - 1) != DB2y)) {
					if (bool_MangXoa[x][y - 1] == false) {
						bool_MangXoa[x][y - 1] = true;
						mangXoa.add(new Point(x, y - 1));
						addHieuUng(x, y - 1);
						HieuUngAn(x, y - 1, listJewels[x][y - 1].getType());
						mangAnDB.add(new Point(x, y - 1));
					}
				}
			}

		if (y + 1 <= Share.row - 1 && x + 1 <= Share.column - 1)
			if (listJewels[x+1][y+1].getType() <= Share.NGU_SAC && !bool_MangXoa[x + 1][y + 1] && x + 1 <= Share.column - 1) {
				if ((!DB1 || (x + 1) != DB1x || (y + 1) != DB1y)
						&& (!DB2 || (x + 1) != DB2x || (y + 1) != DB2y)) {
					if (bool_MangXoa[x + 1][y + 1] == false) {
						bool_MangXoa[x + 1][y + 1] = true;
						mangXoa.add(new Point(x + 1, y + 1));
						addHieuUng(x + 1, y + 1);
						HieuUngAn(x + 1, y + 1,
								listJewels[x + 1][y + 1].getType());
						mangAnDB.add(new Point(x + 1, y + 1));
					}
				}
			}

		if (x + 1 <= Share.column - 1 && y - 1 >= 0){
			Log.e("log gach", "log gach: "+listJewels[x+1][y-1].getType() );
			if (listJewels[x+1][y-1].getType() <= Share.NGU_SAC && !bool_MangXoa[x + 1][y - 1]) {
				if ((!DB1 || (x + 1) != DB1x || (y - 1) != DB1y)
						&& (!DB2 || (x + 1) != DB2x || (y - 1) != DB2y)) {
					if (bool_MangXoa[x + 1][y - 1] == false) {
						bool_MangXoa[x + 1][y - 1] = true;
						mangXoa.add(new Point(x + 1, y - 1));
						addHieuUng(x + 1, y - 1);
						HieuUngAn(x + 1, y - 1,
								listJewels[x + 1][y - 1].getType());
						mangAnDB.add(new Point(x + 1, y - 1));
					}
				}
			}
		}

		if (x - 1 >= 0 && y + 1 <= Share.row - 1)
			if (listJewels[x-1][y+1].getType() <= Share.NGU_SAC && !bool_MangXoa[x - 1][y + 1]) {
				if ((!DB1 || (x - 1) != DB1x || (y + 1) != DB1y)
						&& (!DB2 || (x - 1) != DB2x || (y + 1) != DB2y)) {
					if (bool_MangXoa[x - 1][y + 1] == false) {
						bool_MangXoa[x - 1][y + 1] = true;
						mangXoa.add(new Point(x - 1, y + 1));
						addHieuUng(x - 1, y + 1);
						HieuUngAn(x - 1, y + 1,
								listJewels[x - 1][y + 1].getType());
						mangAnDB.add(new Point(x - 1, y + 1));
					}
				}
			}

		if (x - 1 >= 0 && y - 1 >= 0)
			if (listJewels[x-1][y-1].getType() <= Share.NGU_SAC && !bool_MangXoa[x - 1][y - 1]) {
				if ((!DB1 || (x - 1) != DB1x || (y - 1) != DB1y)
						&& (!DB2 || (x - 1) != DB2x || (y - 1) != DB2y)) {
					if (bool_MangXoa[x - 1][y - 1] == false) {
						bool_MangXoa[x - 1][y - 1] = true;
						mangXoa.add(new Point(x - 1, y - 1));
						addHieuUng(x - 1, y - 1);
						HieuUngAn(x - 1, y - 1,
								listJewels[x - 1][y - 1].getType());
						mangAnDB.add(new Point(x - 1, y - 1));
					}
				}
			}

		if (x + 1 <= Share.column - 1){
			Log.e("log gach", "log gach: "+listJewels[x+1][y].getType() );
			if (listJewels[x+1][y].getType() <= Share.NGU_SAC && !bool_MangXoa[x + 1][y]) {
				if ((!DB1 || (x + 1) != DB1x || (y) != DB1y)
						&& (!DB2 || (x + 1) != DB2x || (y) != DB2y)) {
					if (bool_MangXoa[x + 1][y] == false) {
						bool_MangXoa[x + 1][y] = true;
						mangXoa.add(new Point(x + 1, y));
						addHieuUng(x + 1, y);
						HieuUngAn(x + 1, y, listJewels[x + 1][y].getType());
						mangAnDB.add(new Point(x + 1, y));
					}
				}
			}
		}

		if (x - 1 >= 0)
			if (listJewels[x-1][y].getType() <= Share.NGU_SAC && !bool_MangXoa[x - 1][y]) {
				if ((!DB1 || (x - 1) != DB1x || (y) != DB1y)
						&& (!DB2 || (x - 1) != DB2x || (y) != DB2y)) {
					if (bool_MangXoa[x - 1][y] == false) {
						bool_MangXoa[x - 1][y] = true;
						mangXoa.add(new Point(x - 1, y));
						addHieuUng(x - 1, y);
						HieuUngAn(x - 1, y, listJewels[x - 1][y].getType());
						mangAnDB.add(new Point(x - 1, y));
					}
				}
			}
	}
	
	public void addThunderRow(int y, boolean DB1, int DB1x, int DB1y,
			boolean DB2, int DB2x, int DB2y) {
		menuScene.mSound.playAnSet();
		int k = -1;
		Log.e("add thunder row", "add thunder row: " + y + " " + DB1 + " - "
				+ DB1x + " " + DB1y);
		Log.e("add thunder row", "add thunder row: " + y + " " + DB2 + " - "
				+ DB2x + " " + DB2y);
		for (int i = 0; i < Share.column; i++) {
			{
				etChumSao.attachChild(new aniChumSao(((i) * w / Share.column)
								+ 8 - 2 * (i), (y + 1) * (w) / Share.column - 2
								* (y), (w - 6) / Share.column, (w - 6)/ Share.column,
								resourcesManager.chumSao_region, vbom, etChumSao, 0, 2));
				if ((!DB1 || i != DB1x) && (!DB2 || i != DB2x)) {
					if (listJewels[i][y].getType() <= Share.NGU_SAC && bool_MangXoa[i][y] == false) {
						Log.e("hieu ung", "hieu ung: " + i + " " + y);
						Log.e("hieu ung", "hieu ung mau: " + listJewels[i][y].getType());
						bool_MangXoa[i][y] = true;
						mangXoa.add(new Point(i, y));
						addHieuUng(i, y);
						HieuUngAn(i, y, listJewels[i][y].getType());
						mangAnDB.add(new Point(i, y));
					}
				}
			}
		}
	}

	public void addThunderColumn(int x, boolean DB1, int DB1x, int DB1y,
			boolean DB2, int DB2x, int DB2y) {
		menuScene.mSound.playAnSet();
		Log.e("add thunder column", "add thunder column: " + x + " " + DB1
				+ " - " + DB1x + " " + DB1y);
		Log.e("add thunder column", "add thunder column: " + x + " " + DB2
				+ " - " + DB2x + " " + DB2y);
		for (int i = 0; i < Share.row; i++) {
			etChumSao.attachChild(new aniChumSao(((x) * w / Share.column) + 8
					- 2 * (x), (i + 1) * (w) / Share.column - 2 * (i), (w - 6)
					/ Share.column, (w - 6) / Share.column,
					resourcesManager.chumSao_region, vbom, etChumSao, 0, 2));
			if ((!DB1 || i != DB1y) && (!DB2 || i != DB2y)) {
				if (listJewels[x][i].getType() <= Share.NGU_SAC && !bool_MangXoa[x][i]) {
					bool_MangXoa[x][i] = true;
					mangXoa.add(new Point(x, i));
					Log.e("hieu ung", "hieu ung: " + x + " " + i);
					Log.e("hieu ung",
							"hieu ung mau: " + listJewels[x][i].getType());
					addHieuUng(x, i);
					HieuUngAn(x, i, listJewels[x][i].getType());
					mangAnDB.add(new Point(x, i));
				}
			}
		}
	}

	public void addSameColor(int type, boolean DB, int DBx, int DBy) {
		Log.e("add same color", "add same color: " + DB + " - " + DBx + " "
				+ DBy);
		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				if (listJewels[i][j].getType() == type) {
					if (!DB || i != DBx || j != DBy) {
						if (!bool_MangXoa[i][j]) {
							bool_MangXoa[i][j] = true;
							mangXoa.add(new Point(i, j));
							// hieu ung an cua
							HieuUngSameColor(i, j);
							HieuUngAn(i, j, listJewels[i][j].getType());
							addHieuUng(i, j);
							mangAnDB.add(new Point(i, j));
						}
					}
				}
			}
		}
	}

	public boolean KiemTraNuocAn(int x1, int y1, int x2, int y2) {
		Log.e("kiem tra an", "kiem tra an: " + x1 + " " + y1 + " - " + x2 + " "+ y2);

		if (listJewels[x1][y1].getType() <= 7 && listJewels[x2][y2].getType() <= 7 && 
				(listJewels[x1][y1].getSameColor() || listJewels[x2][y2].getSameColor())
				&& (!listJewels[x1][y1].getBom() && !listJewels[x1][y1].getThunderColumn() &&
						!listJewels[x1][y1].getThunderRow()) && (!listJewels[x2][y2].getBom()
						&& !listJewels[x2][y2].getThunderColumn() && !listJewels[x2][y2].getThunderRow())) {
			// kiem tra an 5
			Log.e("an 5 sac", "an 5 sac");
			nhacX1 = x1;
			nhacX2 = x2;
			nhacY1 = y1;
			nhacY2 = y2;
			return true;
		} else if (listJewels[x1][y1].getBom() && listJewels[x2][y2].getBom()) {
			// kiem tra an bom + bom
			Log.e("an bom + bom", "an bom + bom: " + x2 + " " + y2);
			nhacX1 = x1;
			nhacX2 = x2;
			nhacY1 = y1;
			nhacY2 = y2;
			return true;
		} else if ((listJewels[x1][y1].getThunderColumn() || listJewels[x1][y1]
				.getThunderRow())
				&& (listJewels[x2][y2].getThunderColumn() || listJewels[x2][y2]
						.getThunderRow())) {
			// kiem tra an thunder + thunder
			Log.e("an thunder + thunder", "an thunder + thunder: " + x2 + " "
					+ y2);
			nhacX1 = x1;
			nhacX2 = x2;
			nhacY1 = y1;
			nhacY2 = y2;
			return true;
		} else if (((listJewels[x1][y1].getThunderColumn() || listJewels[x1][y1]
				.getThunderRow()) && listJewels[x2][y2].getBom())
				|| ((listJewels[x2][y2].getThunderColumn() || listJewels[x2][y2]
						.getThunderRow()) && listJewels[x1][y1].getBom())) {
			// kiểm tra an bom + thunder
			Log.e("an bom + thunder", "an bom + thunder: " + x2 + " " + y2);
			nhacX1 = x1;
			nhacX2 = x2;
			nhacY1 = y1;
			nhacY2 = y2;
			return true;
		} else if ((listJewels[x1][y1].getSameColor() && listJewels[x2][y2]
				.getBom())
				|| listJewels[x2][y2].getSameColor()
				&& listJewels[x1][y1].getBom()) {
			// kiem tra an ngu sac + bom
			Log.e("an ngu sac + bom",
					"an ngu sac + bom: " + listJewels[x1][y1].getBom() + " "
							+ listJewels[x2][y2].getBom() + " " + x2 + " " + y2);
			nhacX1 = x1;
			nhacX2 = x2;
			nhacY1 = y1;
			nhacY2 = y2;
			return true;
		} else if (((listJewels[x1][y1].getThunderColumn() || listJewels[x1][y1]
				.getThunderRow()) && listJewels[x2][y2].getSameColor())
				|| ((listJewels[x2][y2].getThunderColumn() || listJewels[x2][y2]
						.getThunderRow()) && listJewels[x1][y1].getSameColor())) {
			// kiem tra an ngu sac + set
			Log.e("an ngu sac + set", "an ngu sac + set: " + x2 + " " + y2);
			nhacX1 = x1;
			nhacX2 = x2;
			nhacY1 = y1;
			nhacY2 = y2;
			return true;
		} else {
			// khac an nhung truong hop an rat rat dac biet
			if(listJewels[x1][y1].getType() < Share.NGU_SAC){
				if (anMang1 = KiemTraAn(1, x1, y1)) {
					nhacX1 = x1;
					nhacX2 = x2;
					nhacY1 = y1;
					nhacY2 = y2;
					return true;
				} 
			}
			if(listJewels[x2][y2].getType() < Share.NGU_SAC){
				if (anMang2 = KiemTraAn(2, x2, y2)) {
				nhacX1 = x1;
				nhacX2 = x2;
				nhacY1 = y1;
				nhacY2 = y2;
				return true;
				}
			}
		}
		return false;
	}

	public void NhacNuoc() {
		boolean an = false;
		this.nhacX1 = nhacX2 = nhacY1 = nhacY2 = -1; 
		Log.e("bat dau nhac nuoc", "bat dau nhac nuoc");
		for (int i = 0; i < Share.column - 1; i++) {
			if (an == true)
				break;
			for (int j = 0; j < Share.row - 1; j++) {
				DoiCho(i, j, i + 1, j, 4, true);
				if (KiemTraNuocAn(i, j, i + 1, j)) {
					Log.e("nhac nuoc", "nhac nuoc: " + nhacX1 + " " + nhacY1
							+ " - " + nhacX2 + " " + nhacY2);
					DoiCho(i + 1, j, i, j, 4, true);
					an = true;
					break;
				}
				DoiCho(i + 1, j, i, j, 4, true);
				DoiCho(i, j, i, j + 1, 2, true);
				if (KiemTraNuocAn(i, j, i, j + 1)) {
					Log.e("nhac nuoc", "nhac nuoc: " + nhacX1 + " " + nhacY1
							+ " - " + nhacX2 + " " + nhacY2);
					DoiCho(i, j + 1, i, j, 2, true);
					an = true;
					break;
				}
				DoiCho(i, j + 1, i, j, 2, true);
			}
		}
	}

	public void resetMangAn() {
		soVienAn = 0;
		logMangThayDoi();

		for (int i = 0; i < 9; i++) {
			mangAn1[i][0] = -1;
			mangAn1[i][1] = -1;
			mangAn2[i][0] = -1;
			mangAn2[i][1] = -1;
		}

		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				ListRoi[i][j] = 0;
				bool_MangXoa[i][j] = false;
				bool_CheckList[i][j] = false;
				MTThayDoi[i][j] = 0;
			}
		}

		mangAnDon1.clear();
		mangAnDon2.clear();
		mangAnDB.clear();
	}

	public void logMang() {
		Log.e("", "log ma tran");
		for (int i = 0; i < Share.row; i++) {
			Log.e("" + i,
					"" + listJewels[0][i].getType() + ", "
							+ listJewels[1][i].getType() + ", "
							+ listJewels[2][i].getType() + ", "
							+ listJewels[3][i].getType() + ", "
							+ listJewels[4][i].getType() + ", "
							+ listJewels[5][i].getType() + ", "
							+ listJewels[6][i].getType());
		}
	}

	public void logMangAn() {
		for (int i = 0; i < 9; i++) {
			Log.e("mang an 1", "mang an 1: " + i + ": " + mangAn1[i][0] + " "
					+ mangAn1[i][1]);
		}

		for (int i = 0; i < 9; i++) {
			Log.e("mang an 2", "mang an 2: " + i + ": " + mangAn2[i][0] + " "
					+ mangAn2[i][1]);
		}
	}

	public void logMangAnDB() {
		Log.e("bat dau log mang an dac biet", "bat dau log mang an dac biet");
		for (int i = 0; i < mangAnDB.size(); i++) {
			Log.e("mang an DB", "mang an DB: " + i + ": " + mangAnDB.get(i).x
					+ " " + mangAnDB.get(i).y);
		}
	}

	public void logMangRoi() {
		Log.e("bat dau log mang roiiiiii", "bat dau log mang roiiiiii");
		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				if (ListRoi[i][j] > 0)
					Log.e("mang roi", i + " " + j + " : " + ListRoi[i][j]);
			}
		}
	}

	public void logMangThayDoi() {
		Log.e("bat dau log mang thay doiiiii", "bat dau log mang thay doiiiii");
		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				if (MTThayDoi[i][j] > 0)
					Log.e("ma tran thay doi", i + " " + j + " : "
							+ MTThayDoi[i][j]);
			}
		}
	}

	public void logCheckList() {
		Log.e("bat dau log check list", "bat dau log check list");
		for (int i = 0; i < checkList1.size(); i++) {
			Log.e("log check list 1", "log check list 1: "
					+ checkList1.get(i).x + " " + checkList1.get(i).y);
		}

		// for(int i=0;i<checkList2.size();i++){
		// Log.e("log check list 2",
		// "log check list 2: "+checkList2.get(i).x+" "+checkList2.get(i).y);
		// }
	}

	public void logMangXoa() {
		Log.e("bat dau log mang xoa", "bat dau log mang xoa");
		for (int i = 0; i < Share.column; i++) {
			for (int j = 0; j < Share.row; j++) {
				if (bool_MangXoa[i][j]) {
					Log.e("mang xoa", "mang xoa: " + i + " " + j);
				}
			}
		}
	}

	public void logArrayListXoa() {
		Log.e("bat dau log array list xoa", "bat dau log array list xoa");
		for (int i = 0; i < mangXoa.size(); i++)
			Log.e("array list xoa", "array list xoa: " + mangXoa.get(i).x + " "
					+ mangXoa.get(i).y);
	}

	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		Log.e("menu click", "menu click");
		if (pKeyCode == KeyEvent.KEYCODE_MENU
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			resourcesManager.engine.start();
			return true;
		} else {
			return resourcesManager.activity.onKeyDown(pKeyCode, pEvent);
		}
	}
	

}
