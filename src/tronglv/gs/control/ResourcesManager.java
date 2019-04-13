package tronglv.gs.control;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.doan.kimcuong.MainApp;

import android.graphics.Color;
import android.graphics.Typeface;


public class ResourcesManager {
	private static final ResourcesManager INSTANCE = new ResourcesManager();
	public static Engine engine;
	public MainApp activity;
	public ZoomCamera camera;
	public VertexBufferObjectManager vbom;
	
	// flash
	public ITextureRegion flash_logo_region;
	
	// menu
	public ITextureRegion menu_bg_region, menu_btnplay_region;
	
	//giao dien
	public ITextureRegion time_bg_region, time_dau_region, time_giua_region, time_cuoi_region;
	
	public ITextureRegion score_region, bestScore_region;
	public TiledTextureRegion pause_region;
    public Font mFont, mFontTime, mFontMenu;

    //map
    public ITextureRegion levelBg, level, khoa;
    public TiledTextureRegion so_region;
    
	// play
	public ITextureRegion play_bg_region, play_frame_region, play_board_region;
	public TiledTextureRegion play_jewelGreen_region, play_jewelRed_region, play_jewelBlue_region, play_jewelYeallow_region,
	play_jewelPink_region, play_jewelOrange_region, play_jewelSameColor_region, play_jewel_new_region;
	public TiledTextureRegion play_jewel_region;
	
	public TiledTextureRegion ngoiSaoTim_region, ngoiSaoTrang_region, chumSao_region;
	
	//effect eat
	public TiledTextureRegion play_effectEatGreen_region, play_effectEatRed_region, play_effectEatBlue_region,
		play_effectEatYeallow_region, play_effectEatPink_region, play_effectEatOrange_region, play_effectEatWhite_region;
	
	//hieu ung
	public TiledTextureRegion boom_region, thunderRow_TitleRegion, thunderColumn_TitleRegion, sameColor_TitleRegion,
		bigSameColor_TitleRegion, nhacNuoc_TitleRegion;


	public static void prepareManager(Engine engine, MainApp activity,
			ZoomCamera camera, VertexBufferObjectManager vbom) {
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
	}

	public static ResourcesManager getInstance() {
		return INSTANCE;
	}
	
	public void loadFlash() {
		flash_logo_region = ResourcesLoader.loadResources("logo_company.png");
	}
	
	public void loadMenu() {
		menu_bg_region = ResourcesLoader.loadResources("ic_background.jpg");
		menu_btnplay_region = ResourcesLoader.loadResources("ic_arcade.png");
	}
	
	public void loadPlayTime(){
		time_bg_region = ResourcesLoader.loadResources("ic_progress_bg.png");
		time_dau_region = ResourcesLoader.loadResources("ic_level_progress_left.png");
		time_giua_region = ResourcesLoader.loadResources("ic_level_progress_middle.png");
		time_cuoi_region = ResourcesLoader.loadResources("ic_level_progress_right.png");
		pause_region = ResourcesLoader.loadTileResources("ic_game_pause.png", 1, 2);
		score_region = ResourcesLoader.loadResources("ic_title_score.png");
		bestScore_region = ResourcesLoader.loadResources("ic_title_best_score.png");
		
		this.mFont = FontFactory.create(this.activity.getFontManager(), this.activity.getTextureManager(), 
				256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 26, Color.YELLOW);
        
        this.mFontTime = FontFactory.create(this.activity.getFontManager(), this.activity.getTextureManager(), 
				256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 18, Color.WHITE);
        
        this.mFontMenu = FontFactory.create(this.activity.getFontManager(), this.activity.getTextureManager(), 
				256, 256, Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL), 30, Color.WHITE);
        
        mFontMenu.load();
        mFont.load();  
        mFontTime.load();
	}
	
	public void loadPlay(){
		// khung
		play_bg_region = ResourcesLoader.loadResources("ic_timer_scene_bg.jpg");
		play_frame_region = ResourcesLoader.loadResources("ic_mineral_scene_bg2.png");
		play_board_region = ResourcesLoader.loadResources("ic_game_chessboard.png");
		
		play_jewel_new_region = ResourcesLoader.loadTileResources("jewel_new.png", 9, 9);
		
		//hieu ung an
		play_effectEatGreen_region = ResourcesLoader.loadTileResources("ic_jewel_eliminate_1.png", 3, 2);
		play_effectEatRed_region = ResourcesLoader.loadTileResources("ic_jewel_eliminate_2.png", 3, 2);
		play_effectEatBlue_region = ResourcesLoader.loadTileResources("ic_jewel_eliminate_3.png", 3, 2);
		play_effectEatYeallow_region = ResourcesLoader.loadTileResources("ic_jewel_eliminate_4.png", 3, 2);
		play_effectEatPink_region = ResourcesLoader.loadTileResources("ic_jewel_eliminate_5.png", 3, 2);
		play_effectEatOrange_region = ResourcesLoader.loadTileResources("ic_jewel_eliminate_6.png", 3, 2);
		play_effectEatWhite_region = ResourcesLoader.loadTileResources("ic_jewel_eliminate_0.png", 3, 2);
		
		//hieu ung kim cuong
		boom_region = ResourcesLoader.loadTileResources("ic_fire_jewel_animation.png", 3, 3);
		thunderRow_TitleRegion = ResourcesLoader.loadTileResources("setngang.png", 1, 7);
		thunderColumn_TitleRegion = ResourcesLoader.loadTileResources("setdoc.png", 7, 1);
		sameColor_TitleRegion = ResourcesLoader.loadTileResources("ic_eliminate_lighting.png", 4, 1);
		bigSameColor_TitleRegion = ResourcesLoader.loadTileResources("effect.png", 3, 2);
		
		nhacNuoc_TitleRegion = ResourcesLoader.loadTileResources("ic_swap_tips.png", 2, 2);
		
		ngoiSaoTim_region = ResourcesLoader.loadTileResources("ic_title_sharking_animation.png", 1, 1);
		ngoiSaoTrang_region = ResourcesLoader.loadTileResources("start.png", 1, 1);
		chumSao_region = ResourcesLoader.loadTileResources("jewel_effect_start.png", 3, 1);
		
		//map
		levelBg = ResourcesLoader.loadResources("ic_timer_scene_bg.jpg");
		level = ResourcesLoader.loadResources("level.png");
		khoa = ResourcesLoader.loadResources("khoa.png");
		so_region = ResourcesLoader.loadTileResources("number.png", 10, 1);
		
		loadMenu();
		loadPlayTime();
		
	}
}
