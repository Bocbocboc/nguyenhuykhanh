package tronglv.gs.animation;

import org.andengine.entity.Entity;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import tronglv.gs.control.ResourcesManager;
import tronglv.gs.model.AnimateMySprite;
import tronglv.gs.model.Share;
import android.util.Log;

public class aniThunderWhite extends AnimateMySprite{
	private int hinhDau, hinhCuoi;
	private long T1, T2;
	private int count;
	private int waiteMove;
	public static boolean stop = false;
	private static Entity entity;
	
	private float x, y;
	private float w, h;
	private int timeCount;
	private boolean setDoc;

	public aniThunderWhite(float pX, float pY, float pWidth, float pHeight, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Entity et, int hinhdau, int hinhcuoi, boolean setDoc) {
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
		this.setDoc = setDoc;
		this.setCurrentTileIndex(hinhdau);
		Log.e("height dau", "height dau: "+this.h);
	}

	@Override
	public void action() {
		T2 = System.currentTimeMillis();
		if (T2 - T1 > 40) {
			T1 = T2;
			if(this.timeCount <= Share.row){
				if(this.setDoc){
					this.timeCount++;
					if(this.timeCount < 5){
						setPosition(this.x, this.y - h);
						this.y -= h;
						aniThunderWhite.this.setHeight(this.h*(this.timeCount+1));
						Log.e("height sau", "height sau: "+this.h*(this.timeCount+1));
					}else{
						setPosition(this.x, this.y - h);
						this.y -= h;
					}
				}else{
					this.timeCount++;
					if(this.timeCount < 5){
						setPosition(this.x-h, this.y);
						this.x -= h;
						aniThunderWhite.this.setWidth(this.h*(this.timeCount+1));
						Log.e("width sau", "width sau: "+this.h*(this.timeCount+1));
					}else{
						setPosition(this.x-h, this.y);
						this.x -= h;
					}
				}
			}else{
				removeThunder();
			}
		}
	}
	
	public void removeThunder(){
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				aniThunderWhite.entity.detachChild(aniThunderWhite.this);
			}
		});
	}
}
