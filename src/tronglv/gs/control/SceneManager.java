package tronglv.gs.control;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import android.util.Log;

import tronglv.gs.model.BaseScene;
import tronglv.gs.view.flashScene;
import tronglv.gs.view.menuScene;
import tronglv.gs.view.playScene;

public class SceneManager {
	private static final SceneManager INSTANCE = new SceneManager();
	private BaseScene flashscene, menuscene, playscene, mapScene;
	private BaseScene currentScene;
	private Engine engine = ResourcesManager.getInstance().engine;

	private void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;

	}

	public static SceneManager getInstance() {
		return INSTANCE;
	}

	public BaseScene getCurrentScene() {
		return currentScene;
	}

	public void createFlashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourcesManager.getInstance().loadFlash();
		flashscene = new flashScene();
		SceneManager.getInstance().setScene(flashscene);
		ResourcesManager.getInstance().loadMenu();
		ResourcesManager.getInstance().loadPlay();
		pOnCreateSceneCallback.onCreateSceneFinished(flashscene);
	}

	public void createMenu() {
//		ResourcesManager.getInstance().unloadFlash();
//		ResourcesManager.getInstance().loadMenu();
		menuscene = new menuScene();
		SceneManager.getInstance().setScene(menuscene);
	}

	public void createMenutoPlay(int map) {
		Log.e("", "map: "+map);
		playscene = new playScene(map);
		SceneManager.getInstance().setScene(playscene);
	}

	public void loadPlaytoMenu() {
//		ResourcesManager.getInstance().unloadPlay();
//		ResourcesManager.getInstance().loadMenu();
		menuscene = new menuScene();
		SceneManager.getInstance().setScene(menuscene);
	}
	
	public void CreateMap() {
//		ResourcesManager.getInstance().unloadPlay();
//		ResourcesManager.getInstance().loadMenu();
		mapScene = new tronglv.gs.view.mapScene();
		SceneManager.getInstance().setScene(mapScene);
	}
	
}
