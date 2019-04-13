package tronglv.gs.view;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.IModifier;

import tronglv.gs.control.SceneManager;
import tronglv.gs.model.BaseScene;
import tronglv.gs.model.Share;

import com.badlogic.gdx.math.Vector2;

public class flashScene extends BaseScene {
	private int w, h;
	private PhysicsWorld mPhysicsWorld;
	private Sprite sprilogo;
	private Entity etFlash;
	private SequenceEntityModifier entityModifier;

	@Override
	public void createScene() {
		w = Share.width;
		h = Share.height;
		camera.setCenter(w / 2, h / 2);
		this.setBackground(new Background(0, 0, 0));
		etFlash = new Entity();
		draw();
		engine.registerUpdateHandler(new FPSLogger());
		this.mPhysicsWorld = new FixedStepPhysicsWorld(30, new Vector2(0, 0),
				false, 8, 1);
		this.registerUpdateHandler(this.mPhysicsWorld);
	}

	private void draw() {
		sprilogo = new Sprite(w / 2
				- resourcesManager.flash_logo_region.getWidth() / 2, h / 2
				- resourcesManager.flash_logo_region.getHeight() / 2,
				resourcesManager.flash_logo_region, vbom);
		etFlash.attachChild(sprilogo);
		entriModiferLogoSprite(sprilogo);
		this.attachChild(etFlash);
	}

	private void entriModiferLogoSprite(Sprite entity) {
		final SequenceEntityModifier entityModifier = new SequenceEntityModifier(
				new IEntityModifierListener() {
					@Override
					public void onModifierStarted(
							final IModifier<IEntity> pModifier,
							final IEntity pItem) {
						
					}

					@Override
					public void onModifierFinished(
							final IModifier<IEntity> pEntityModifier,
							final IEntity pEntity) {
						try {
							SceneManager.getInstance().createMenu();
						} catch (Exception e) {

						}
					}
				}, new AlphaModifier(5, 0, 1f));
		entity.registerEntityModifier(entityModifier);
	}

	@Override
	public void onBackKeyPressed() {
	}

	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
}
