package tronglv.gs.animation;

import org.andengine.entity.Entity;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;
import tronglv.gs.control.ResourcesManager;
import tronglv.gs.model.AnimateMySprite;
import tronglv.gs.model.Share;

public class aniEffectEat extends AnimateMySprite{
	private int hinhDau, hinhCuoi;
	private long T1, T2;
	private int count;
	private static Entity entity;
	private int waiteMove;
	private boolean roiAll;

	public aniEffectEat(float pX, float pY, float pWidth, float pHeight,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Entity et, int x, int y) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		
		this.hinhDau = x;
		this.hinhCuoi = y;
		count = x;
		T1 = T2 = System.currentTimeMillis();
		this.entity = et;
		this.waiteMove = 0;
		this.roiAll = roiAll;
	}

	@Override
	public void action() {
		T2 = System.currentTimeMillis();
		if (T2 - T1 > 60) {
			T1 = T2;
			this.setCurrentTileIndex(count);
			if (count < hinhCuoi) {
				count += 1;
			} else {
				removeEffect();
			}
		}
	}
	
	public void removeEffect(){
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				aniEffectEat.this.dispose();
				aniEffectEat.this.clearUpdateHandlers();
				aniEffectEat.this.detachSelf();
				aniEffectEat.entity.detachChild(aniEffectEat.this);
			}
		});
	}
}
