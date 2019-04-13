package tronglv.gs.animation;

import org.andengine.entity.Entity;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import tronglv.gs.control.ResourcesManager;
import tronglv.gs.model.AnimateMySprite;

public class aniChumSao extends AnimateMySprite{
	private int hinhdau, hinhcuoi;
	private long T1, T2;
	private int count;
	private static Entity entity;

	public aniChumSao(float pX, float pY, float pWidth, float pHeight,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Entity et, int hinhdau, int hinhcuoi) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager);
		
		this.hinhdau = hinhdau;
		this.hinhcuoi = hinhcuoi;
		count = hinhdau;
		T1 = T2 = System.currentTimeMillis();
		this.entity = et;

	}

	@Override
	public void action() {
		T2 = System.currentTimeMillis();
		if (T2 - T1 > 150) {
			T1 = T2;
			this.setCurrentTileIndex(count);
			if (count < hinhcuoi) {
				count += 1;
			} else {
				count = hinhdau;
				removeChumSao();
			}
		}
	}
	
	public void removeChumSao(){
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				aniChumSao.entity.detachChild(aniChumSao.this);
			}
		});
	}
}
