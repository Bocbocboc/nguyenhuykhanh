package tronglv.gs.model;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import tronglv.gs.control.ResourcesManager;
import android.app.Activity;

/**
 * @author jvm
 * @author tronglv@goldsoft.com.vn
 * @version 1.0
 */
public abstract class BaseScene extends Scene {

	public Engine engine;
	public Activity activity;
	public static ResourcesManager resourcesManager;
	public static VertexBufferObjectManager vbom;
	public ZoomCamera camera;

	public BaseScene() {
		this.resourcesManager = ResourcesManager.getInstance();
		this.engine = resourcesManager.engine;
		this.activity = resourcesManager.activity;
		this.vbom = resourcesManager.vbom;
		this.camera = resourcesManager.camera;
		createScene();
	}

	public abstract void createScene();

	public abstract void onBackKeyPressed();
}