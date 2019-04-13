package tronglv.gs.animation;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import android.util.Log;

import tronglv.gs.control.ResourcesManager;
import tronglv.gs.model.AnimateMySprite;
import tronglv.gs.model.Share;

public class anijewelpurple extends AnimateMySprite {
	private float pX, pY;
	private float w, h;
	private int x, y;
	private long T1, T2, T3, T4;
	private int countAction, countMove;
	private int hinhDau, hinhCuoi;
	private int type;
	private boolean isThunderRow, isThunderColumn;
	private boolean isBom;
	private boolean isSameColor;
	private int event, eventMoveBack;
	private boolean moveBack = false;
	private boolean lock;
	private boolean remove;
	private int countRemove;
	private int moveDown;
	private int waiteMove;
	private int countMoveDown;
	private boolean changeJewel;
	private boolean checkRoi;
	private int timeWaiteMoveDown;
	private boolean callBack;
	private int timeCallBack;
	private float quangDuong;
	private boolean batDauKiemTra;
	private int timeBom;
	private int x1, y1, x2, y2;
	//public PhysicsHandler mPhysicsHandler;
	
	public anijewelpurple(float pX, float pY, float pWidth, float pHeight,int type,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int x, int y) {
		
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager );
		this.pX = pX;
		this.pY = pY;
		this.x = x;
		this.y = y;
		this.w = pWidth;
		this.h = pHeight;
		this.type = type;
		this.event = 0;
		this.lock = false;
		this.remove = false;
		this.countMove = 15;
		this.countRemove = 15;
		this.moveDown = 0;
		this.countMoveDown = 0;
		this.changeJewel = false;
		this.checkRoi = false;
		this.timeWaiteMoveDown = 0;
		this.timeCallBack = 0;
		this.callBack = false;
		this.quangDuong = 0;
		this.batDauKiemTra = false;
		this.timeBom = -1;
		this.x1 = this.y1 = this.x2 = this.y2 = -1;
		T1 = T2 = System.currentTimeMillis();
		T3 = T4 = System.currentTimeMillis();
		
	}

	@Override
	public void action() {
		
		T2 = System.currentTimeMillis();
		T4 = System.currentTimeMillis();
		if(T4 - T3 > 80){
			T3 = T4;
			this.setCurrentTileIndex(countAction);
			if (countAction < hinhCuoi) {
				countAction += 1;
			} else {
				countAction = hinhDau;
			}
		}
		if (T2 - T1 > 35) {
			T1 = T2;
			
			if(remove){
				if(countRemove < 5){
					countRemove++;
				}else
					stopJewel();
			}
			if(event == 0 && this.moveBack){
				this.event = this.eventMoveBack;
			}
			if(event > 0){
				
				if(countMove < 5){
					Move(event);
					countMove++;
					
				}else{
					if(this.batDauKiemTra ){
						Log.e("goi xu ly khi an", "goi xu ly khi an");
						this.batDauKiemTra = false;
						Share.play.XuLyKhiAn(x1, y1, x2, y2, this.event);
					}
					event = 0;
					this.lock = false;
					if(this.moveBack){
						this.lock = true;
						this.moveBack = false;
						event = this.eventMoveBack;
						countMove = 0;
					}
				}
			}
			
			if(waiteMove < 0){
				if(timeWaiteMoveDown < 0){
					if(checkRoi){
						checkRoi = false;
						Log.e("kiem traaaaaaaaaaaaa", "kiem traaaaaaaaaaaaa");
						Share.play.KiemTraCheckList();
					}
				}else{
					timeWaiteMoveDown--;
				}
				
				if(countMoveDown > 0){
					RoiXuong();
					countMoveDown--;
				}else{
					if((this.type == Share.XU_BAC || this.type == Share.XU_VANG) && XuongDay()){
						Log.e("yeah xuong day roi", "yeah xuong day roi");
						Share.play.bool_MangXoa[(int)(this.pX/w)][(int)(this.pY/w)] = true;
						Share.play.RoiAll();
						Share.play.GemMoi();
						
						x = (int)(this.pX/w);
						y = 8;
						Log.e("xxxxxxxyyyy", "xxxxxxxyyyy: "+x+" "+y);
						Share.play.listEffects[x][y] = new aniEffectEat(((x) * Share.width/ Share.column) + 8 - 2
								* (x), (y + 1) * (Share.width) / Share.column - 2 * (y), (Share.width - 6)
								/ Share.column, (Share.width - 6) / Share.column, ResourcesManager.getInstance().play_effectEatWhite_region,
								Share.play.vbom, Share.play.etEffectEat, 0, 5);
						Share.play.etEffectEat.attachChild(Share.play.listEffects[x][y]);
					}
					this.moveDown = 0;
				}
			}
			else
				waiteMove-- ;
			
			if(timeCallBack < 0){
				if(callBack){
					callBack = false;
					Share.play.CallBackDB();
				}
			}else
				timeCallBack--;
		}
	}
	
	public boolean XuongDay(){
		if((this.pY-10)/w >= Share.row-1)
			return true;
		return false;
	}
	
	public void RoiXuong(){
		this.pY += (this.quangDuong/5);
		this.setPosition(pX, pY);
	}
	
	public void setEffect(boolean SameColor, boolean ThunderRow, boolean ThunderColumn, boolean Bom){
		this.isBom = Bom;
		this.isSameColor = SameColor;
		this.isThunderColumn = ThunderColumn;
		this.isThunderRow = ThunderRow;
		if(this.type <= 7){
			if(this.isBom){
				this.hinhDau = 9*(this.type-1) + 5;
				this.hinhCuoi = 9*(this.type-1) + 8;
				countAction = hinhDau;
			}else if(this.isThunderRow){
				this.hinhDau = 9*(this.type-1) + 1;
				this.hinhCuoi = 9*(this.type-1) + 1;
				countAction = hinhDau;
			}  else if(this.isThunderColumn){
				this.hinhDau = 9*(this.type-1) + 3;
				this.hinhCuoi = 9*(this.type-1) + 3;
				countAction = hinhDau;
			}else if(this.isSameColor){
				//samecolor ghep them anh vao cuoi hoac them 1 loai kim cuong moi
				this.hinhDau = 9*(this.type-1);
				this.hinhCuoi = 9*(this.type-1) + 5;
				countAction = hinhDau;
			}else{
				this.hinhDau = 9*(this.type-1);
				this.hinhCuoi = 9*(this.type-1);
				countAction = hinhDau;
			}
		}else{
			setType(this.type);
		}
	}
	
	public void setType(int type){
		this.type = type;
		if(this.type == Share.XU_VANG){
			Log.e("xu vang ne", "xu vang ne: "+this.type);
			hinhDau = hinhCuoi = countAction = 60;
		}else if(this.type == Share.XU_BAC){
			Log.e("xu bac ne", "xu bac ne: "+this.type);
			hinhDau = hinhCuoi = countAction = 61;
		}
		this.isBom = false;
		this.isSameColor = false;
		this.isThunderColumn = false;
		this.isThunderRow = false;
	}
	
	public void setRemove(){
		this.type = 0;
		this.countRemove = 4;
		this.remove = true;
	}
	
	public void setMoveBack(boolean mb, int ev){
		this.moveBack = true;
		this.eventMoveBack = ev;
		Log.e("move back", "move back: "+this.x+" "+this.y+" - "+this.eventMoveBack);
	}
	
	public void setMoveDown(int i, boolean checkRoi){
		this.quangDuong = this.quangDuong*this.countMoveDown/5 + i*(w-1);  
//		this.moveDown = this.moveDown + i;
		this.countMoveDown = 5;
		this.waiteMove = 5;
		
		if(checkRoi){
			this.checkRoi = checkRoi;
			timeWaiteMoveDown = 6;
		}
		
	}
	
	public void moveDownNewJewel(int i, boolean checkRoi){
		this.quangDuong = + i*(w-1);  
		
//		this.moveDown = this.moveDown + i;
		this.countMoveDown = 5;
		this.waiteMove = 5;
		if(checkRoi){
			this.checkRoi = checkRoi;
			timeWaiteMoveDown = 6;
		}
	}
	
	public boolean getLock(){
		return this.lock;
	}
	
	
	
	public int getType(){
		return this.type;
	}
	
	public boolean getBom(){
		return this.isBom;
	}
	
	public boolean getThunderRow(){
		return this.isThunderRow;
	}
	
	public boolean getThunderColumn(){
		return this.isThunderColumn;
	}
	
	public boolean getSameColor(){
		return this.isSameColor;
	}
	
	
	public void Swap(int x1, int y1, int x2, int y2, int ev, boolean check){
		this.event = ev;
		this.lock = true;
		this.countMove = 0;
		if(check){
			this.batDauKiemTra = true;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}
	
	public void Move(int suKien) {
		switch (suKien) {
		case 1:
			pY = pY-w/5;
			this.setPosition(pX, pY);								
			break;
			
		case 2:
			pY = pY+w/5;
			this.setPosition(pX, pY);
			break;
			
		case 3:
			pX = pX-w/5;
			this.setPosition(pX, pY);	
			break;
			
		case 4:
			pX = pX+w/5;
			this.setPosition(pX, pY);	
			break;

		default:
			break;
		}
	}
	
	public void stopJewel(){
		ResourcesManager.getInstance().activity.runOnUpdateThread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				detachChild(anijewelpurple.this);
//				anijewelpurple.this.setVisible(false);
			}
		});
		this.remove = false;
	}
	
	public void removeJewel(){
		Log.e("bat dau remove", "bat dau remove: ");
//		this.waiteMove = 5;
//		this.timeWaiteMoveDown = 5;
		this.changeJewel = true;
	}
	
	public void startJewel(boolean goiLai){
		this.setPosition(pX, (-1+1)*(w)/Share.column - 3*(-1-1));
		this.pY = (-1+1)*(w)/Share.column - 3*(-1-1);
		Log.e("start jewel", "start jewellll: "+this.x+" "+this.y+" -- "+this.pX+" "+this.pY);
		if(!goiLai)
			this.moveDownNewJewel(y+1, false);
		else
			this.moveDownNewJewel(y+1, true);
//		this.changeJewel = false;
		
	}
	
	public void NewJewel(float px, float py, int type, int effect, int x, int y){
		Log.e("new moi ne", "new moi neeee: "+px+" "+py);
		this.pX = px;
		this.pY = py;
		this.type = type;
		this.x = x;
		this.y = y;
		if(this.type <= 7){
			if(effect == 0){//binh thuong
				this.countAction = hinhDau;
				setEffect(false, false, false, false);
			}else if(effect == 1){// 5 sac
				this.countAction = hinhDau;
				setEffect(true, false, false, false);
			}else if(effect == 2){//set ngang
				this.countAction = hinhDau;
				setEffect(false, true, false, false);
			}else if(effect == 3){//set doc
				this.countAction = hinhDau;
				setEffect(false, false, true, false);
			}else if(effect == 4){// no
				this.countAction = hinhDau;
				setEffect(false, false, false, true);
			}
		}else{
			setType(this.type);
		}
	}
	
	
	public int suKienNguoc(int suKien){
		if(suKien == 1)
			return 2;
		else if(suKien == 2)
			return 1;
		else if(suKien == 3)
			return 4;
		else if(suKien == 4)
			return 3;
		else
			return 0;
	}
	
	public void setThunderAll(boolean goiLai){
		if(Share.play.getRandom(1, 2) == 1){
			this.isThunderColumn = true;
			this.hinhDau = 3;
			this.hinhCuoi = 4;
			countAction = hinhDau;
		}else{
			this.isThunderRow = true;
			this.hinhDau = 1;
			this.hinhCuoi = 2;
			countAction = hinhDau;
		}
		if(goiLai){
			this.timeCallBack = 10;
			this.callBack = true;
		}
	}
	
	public void setBomAll(boolean goiLai){
		this.isBom = true;
		this.hinhDau = 5;
		this.hinhCuoi = 8;
		countAction = hinhDau;
		
		if(goiLai){
			this.hinhDau = 1;
			this.hinhCuoi = 2;
			countAction = hinhDau;
			this.timeCallBack = 10;
			this.callBack = true;
		}
	}
	
}
