package tronglv.gs.view;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

import tronglv.gs.control.MusicMenu;
import tronglv.gs.control.SceneManager;
import tronglv.gs.control.Sound;
import tronglv.gs.model.BaseScene;
import tronglv.gs.model.Share;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.doan.process.savePreference;

public class menuScene extends BaseScene {
	private int w, h;
	private PhysicsWorld mPhysicsWorld;
	private Sprite spriArcade, spriteNhac, SpriteThoat;
	Text textSound;
	private Entity etButton;
	public static Sound mSound;
	public static MusicMenu mMenu;

	@Override
	public void createScene() {
		
		if(!Share.khoiTaoSound){
			Log.e("", "khoi tao sound");
			Share.khoiTaoSound = true;
			mMenu = new MusicMenu();
			mMenu.loadMusic(activity);
			mMenu.play();
			mSound = new Sound();
			mSound.loadSound(activity);
		}
		
		Share.save = new savePreference(activity);
		Share.save.readMangDiem();
		
		w = Share.width;
		h = Share.height;
		camera.setCenter(w / 2, h / 2);
		etButton = new Entity();
		draw();
		engine.registerUpdateHandler(new FPSLogger());
		this.mPhysicsWorld = new FixedStepPhysicsWorld(30, new Vector2(0, 0),
				false, 8, 1);
		this.registerUpdateHandler(this.mPhysicsWorld);
	}

	private void draw() {
		// initialize and attach
		attachChild(new Sprite(0, 0, resourcesManager.menu_bg_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
		spriArcade = new Sprite(w / 2 - resourcesManager.menu_btnplay_region.getWidth() / 2, h / 3
				+ resourcesManager.menu_btnplay_region.getHeight() + 5,
				resourcesManager.menu_btnplay_region.getWidth(),
				resourcesManager.menu_btnplay_region.getHeight(),
				resourcesManager.level, vbom) {
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
						
						//SceneManager.getInstance().createMenutoPlay();
						SceneManager.getInstance().CreateMap();
						
					}
					break;
				}
				return true;
			}
		};
		this.registerTouchArea(spriArcade);
		etButton.attachChild(spriArcade);
		
		etButton.attachChild(new Text(w / 2 - resourcesManager.menu_btnplay_region.getWidth() / 2+60, 
				h / 3 + resourcesManager.menu_btnplay_region.getHeight() + 5+5,
				resourcesManager.mFontMenu, "Play", resourcesManager.vbom));
		
		
		spriteNhac = new Sprite(w / 2 - resourcesManager.menu_btnplay_region.getWidth() / 2, h / 3 + h/7
				+ resourcesManager.menu_btnplay_region.getHeight() + 5,
				resourcesManager.menu_btnplay_region.getWidth(),
				resourcesManager.menu_btnplay_region.getHeight(),
				resourcesManager.level, vbom) {
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
						if(Share.amThanh){
							Share.amThanh = false;
							textSound.setText("Sound: Off");
							mMenu.pause();
						}else{
							Share.amThanh = true;
							textSound.setText("Sound: On");
							mMenu.play();
						}
						
					}
					break;
				}
				return true;
			}
		};
		this.registerTouchArea(spriteNhac);
		etButton.attachChild(spriteNhac);
		if(Share.amThanh){
			Log.e("", "sound 1");
			textSound = new Text(w / 2 - resourcesManager.menu_btnplay_region.getWidth() / 2+20, 
					h / 3 +h/7+ resourcesManager.menu_btnplay_region.getHeight() + 5+5,
					resourcesManager.mFontMenu, "Sound: On", resourcesManager.vbom);
		}else{
			Log.e("", "sound 2");
			textSound = new Text(w / 2 - resourcesManager.menu_btnplay_region.getWidth() / 2+20, 
					h / 3 +h/7+ resourcesManager.menu_btnplay_region.getHeight() + 5+5,
					resourcesManager.mFontMenu, "Sound: Off", resourcesManager.vbom);
		}
		
		etButton.attachChild(textSound);
		
		SpriteThoat = new Sprite(w / 2 - resourcesManager.menu_btnplay_region.getWidth() / 2, h / 3 +2*h/7
				+ resourcesManager.menu_btnplay_region.getHeight() + 5,
				resourcesManager.menu_btnplay_region.getWidth(),
				resourcesManager.menu_btnplay_region.getHeight(),
				resourcesManager.level, vbom) {
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
						
						//SceneManager.getInstance().createMenutoPlay();
						System.exit(0);
						
					}
					break;
				}
				return true;
			}
		};
		this.registerTouchArea(SpriteThoat);
		etButton.attachChild(SpriteThoat);
		
		etButton.attachChild(new Text(w / 2 - resourcesManager.menu_btnplay_region.getWidth() / 2+60, 
				h / 3 +2*h/7+ resourcesManager.menu_btnplay_region.getHeight() + 5+5,
				resourcesManager.mFontMenu, "Exit", resourcesManager.vbom));
		
		
		
		this.attachChild(etButton);
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

}
