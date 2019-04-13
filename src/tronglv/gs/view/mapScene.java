package tronglv.gs.view;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import android.util.Log;
import tronglv.gs.control.SceneManager;
import tronglv.gs.model.BaseScene;
import tronglv.gs.model.Share;

public class mapScene  extends BaseScene{

	private int w, h;
	private Entity etlevel;
	private Sprite[] mangLevel;
	private AnimatedSprite[] mangSo;
	public ITextureRegion sao1, sao2, sao3;
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		w = Share.width;
		h = Share.height;
		mangLevel = new Sprite[10];
		mangSo = new AnimatedSprite[20];
		camera.setCenter(w / 2, h / 2);
		etlevel = new Entity();
		draw();
	}
	

	private void draw() {
		attachChild(new Sprite(0, 0, resourcesManager.levelBg, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
		
		
		for(int i=0;i<=9;i++){
			Log.e("", "i: "+i);
			VeLevel(i);
		}
		
//		ma = new Sprite(w/2-resourcesManager.map1b_region.getWidth()/2+10, 4*h/5-20, 
//				getITiledTextureRegion(this.map), vbom);
//		etlevel.attachChild(ma);
		this.attachChild(etlevel);
	}
	
	public void VeLevel(final int i){
		float x = 30; 
		float y = h/3;
		if(i==0 || i==4 || i==8)
			x = 30;
		else if(i == 1 || i == 5)
			x = 30 + 23 + resourcesManager.level.getWidth();
		else if(i == 2 || i == 6)
			x = 30 + 2*(23 + resourcesManager.level.getWidth());
		else if(i == 3 || i == 7 || i == 9)
			x = 30 + 3*(23 + resourcesManager.level.getWidth());
		
		if(i>=0 && i<=3)
			y = h/3;
		else if(i>=4 && i<=7)
			y = h/3 + resourcesManager.level.getHeight()+50;
		else if(i==8 || i == 9)
			y = h/3 + 2*(resourcesManager.level.getHeight()+50);
		
		mangLevel[i] = new Sprite(x, y,
				resourcesManager.level, vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				boolean istouch = true;
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.setScale(1.2f);
					istouch = true;
					break;
				case TouchEvent.ACTION_UP:
					if (istouch) {
						istouch = false;
						this.setScale(1.0f);
						//if(Share.save.mangSao[i+(map-1)*10] >= 0)
						SceneManager.getInstance().createMenutoPlay(i+1);
					}
					break;
					
				case TouchEvent.ACTION_MOVE:
					if (istouch) {
						istouch = false;
						this.setScale(1.0f);
					}
				}
				return true;
			}
		};
		this.registerTouchArea(mangLevel[i]);
		
		
	/*	if(Share.save.mangSao[i+(map-1)*10] == 0){
			sao1 = resourcesManager.start0_region;
			sao2 = resourcesManager.start0_region;
			sao3 = resourcesManager.start0_region;
		}else if(Share.save.mangSao[i+(map-1)*10] == 1){
			sao1 = resourcesManager.start1_region;
			sao2 = resourcesManager.start0_region;
			sao3 = resourcesManager.start0_region;
		}else if(Share.save.mangSao[i+(map-1)*10] == 2){
			sao1 = resourcesManager.start1_region;
			sao2 = resourcesManager.start1_region;
			sao3 = resourcesManager.start0_region;
		}else if(Share.save.mangSao[i+(map-1)*10] == 3){
			sao1 = resourcesManager.start1_region;
			sao2 = resourcesManager.start1_region;
			sao3 = resourcesManager.start1_region;
		}*/
			
		etlevel.attachChild(mangLevel[i]);
		
		/*
		if(Share.save.mangSao[i+(map-1)*10] > -1){
			etlevel.attachChild(new Sprite(x, y+50, sao1, vbom));
			etlevel.attachChild(new Sprite(x+resourcesManager.start1_region.getHeight(), 
					y+50, sao2, vbom));
			etlevel.attachChild(new Sprite(x+2*(resourcesManager.start1_region.getHeight()), 
					y+50, sao3, vbom));
		}
		*/
		mangSo[2*i] = new AnimatedSprite(x+15, y+5, resourcesManager.so_region, vbom);
		mangSo[2*i+1] = new AnimatedSprite(x+45,y+5, resourcesManager.so_region, vbom);
		Log.e("", "so: "+((i+1)%10));
		mangSo[2*i+1].setCurrentTileIndex((i+1)%10);
		if(i+1 > 9)
			mangSo[2*i].setCurrentTileIndex(1);
		else
			mangSo[2*i].setCurrentTileIndex(0);
		etlevel.attachChild(mangSo[2*i]);
		etlevel.attachChild(mangSo[2*i+1]);
		/*if(Share.save.mangSao[i+(map-1)*10] < 0)
			etlevel.attachChild(new Sprite(mangLevel[i].getX()+7, mangLevel[i].getY()+8,
				resourcesManager.daykhoa_region, vbom));
		*/
		if(Share.save.mangLevel[i] < 0)
			etlevel.attachChild(new Sprite(x+5, y+3, resourcesManager.khoa, resourcesManager.vbom));
		
	}
	

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		SceneManager.getInstance().loadPlaytoMenu();
	}

	

	

}
