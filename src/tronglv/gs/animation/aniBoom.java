package tronglv.gs.animation;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import tronglv.gs.control.ResourcesManager;
import tronglv.gs.model.AnimateMySprite;
import tronglv.gs.model.BaseScene;
import tronglv.gs.view.playScene;

public class aniBoom extends AnimateMySprite {
	
	private int x, y;
	private long T1, T2;
	private int count;
	private static Entity entity;

	public aniBoom(float pX, float pY, float pWidth, float pHeight,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Entity et, int x, int y) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		count = x;
		T1 = T2 = System.currentTimeMillis();
		this.entity = et;
	}

	@Override
	public void action() {
		T2 = System.currentTimeMillis();
		if (T2 - T1 > 80) {
			T1 = T2;
			this.setCurrentTileIndex(count);
			if (count < y) {
				count += 1;
			} else
				removeBom();
		}
	}
	
	public void removeBom(){
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				aniBoom.entity.detachChild(aniBoom.this);
			}
		});
	}

}
