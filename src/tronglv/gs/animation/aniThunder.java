package tronglv.gs.animation;

import org.andengine.entity.Entity;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import tronglv.gs.control.ResourcesManager;
import tronglv.gs.model.AnimateMySprite;

import android.util.Log;

public class aniThunder extends AnimateMySprite {

	private int hinhDau, hinhCuoi;
	private long T1, T2;
	private int count;
	private int waiteMove;
	public static boolean stop = false;
	private static Entity entity;

	public aniThunder(float pX, float pY, float pWidth, float pHeight,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Entity et, int hinhdau, int hinhcuoi) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager);
		
		this.hinhDau = hinhdau;
		this.hinhCuoi = hinhcuoi;
		count = hinhdau;
		T1 = T2 = System.currentTimeMillis();
		this.entity = et;
		this.waiteMove = 0;
	}

	@Override
	public void action() {
		T2 = System.currentTimeMillis();
		if (T2 - T1 > 60) {
			T1 = T2;
			if(waiteMove < 0 ){
//				startThunder();
				this.setCurrentTileIndex(count);
				if (count < hinhCuoi) {
					count += 1;
				} else {				
					removeThunder();
				}
			}else
				waiteMove--;
		}
	}
	
	public void removeThunder(){
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				aniThunder.entity.detachChild(aniThunder.this);
			}
		});
	}
	
//	public void removeBom(int idBom) {
//        synchronized (mapSpriteBom) {
//            if (mapSpriteBom.containsKey(idBom)) {
//                final Sprite mb = mapSpriteBom.get(idBom);
//                handleRemoveSprite(mb);
//            }
//        }
//    }
	
//	public void handleRemoveSprite(final Entity mb) {
//        Message message = mHandler.obtainMessage(0, mb);
//        mHandler.sendMessage(message);
//    }
//	
//	Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            removeSprite((Entity) msg.obj);
//        }
//    };
// 
//    public void removeSprite(final Entity mb) {
//        ResourcesManager.getInstance().engine.runOnUpdateThread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                .mScene.detachChild(mb);
//                }catch(Exception e){}
//            }
//        });
//    }
}
