package tronglv.gs.animation;

import org.andengine.entity.Entity;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import tronglv.gs.control.ResourcesManager;
import tronglv.gs.model.AnimateMySprite;
import tronglv.gs.model.Share;
import android.util.Log;

public class aniNgoiSao extends AnimateMySprite{
	private int hinhDau, hinhCuoi;
	private long T1, T2;
	private int count;
	private int waiteMove;
	private static Entity entity;
	
	private float x, y;
	private float w, h;
	private int timeCount;
	
	public aniNgoiSao(float pX, float pY, float pWidth, float pHeight,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Entity et, int hinhdau, int hinhcuoi) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager);
		
		this.hinhDau = hinhdau;
		this.hinhCuoi = hinhcuoi;
		count = hinhdau;
		T1 = T2 = System.currentTimeMillis();
		this.entity = et;
		this.waiteMove = 0;
		this.x = pX;
		this.y = pY;
		this.w = pWidth;
		this.h = pHeight;
		this.timeCount = 0;
		this.setCurrentTileIndex(hinhdau);
	}

	@Override
	public void action() {
		T2 = System.currentTimeMillis();
		if (T2 - T1 > 70) {
			T1 = T2;
			if(this.timeCount <= 20){
				this.timeCount++;
				if(this.timeCount < 10){
					h+= 4;
					w += 4;
					aniNgoiSao.this.setWidth(w);
					aniNgoiSao.this.setHeight(h);
					x -= 2;
					y -= 2;
					aniNgoiSao.this.setPosition(x, y);
				}else{
					h -= 4;
					w -= 4;
					aniNgoiSao.this.setWidth(w);
					aniNgoiSao.this.setHeight(h);
					x += 2;
					y += 2;
					aniNgoiSao.this.setPosition(x, y);
				}
//				aniNgoiSao.this.setRotationCenter(x, y);
			}else{
				removeNgoiSao();
			}
		}
	}
	
	public void startNgoiSao(float px, float py, float pWidth, float pHeight){
		this.timeCount = 0;
		this.x = px;
		this.y = py;
		this.w = pWidth;
		this.h = pHeight;
		T1 = T2 = System.currentTimeMillis();
		setPosition(px, py);
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				aniNgoiSao.entity.attachChild(aniNgoiSao.this);
			}
		});
	}
	
	public void removeNgoiSao(){
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("detach", "detach");
				aniNgoiSao.entity.detachChild(aniNgoiSao.this);
			}
		});
	}
}
