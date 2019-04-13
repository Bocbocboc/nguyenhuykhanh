package tronglv.gs.control;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import tronglv.gs.model.Share;

public class ResourcesLoader {
	private final static String IMAGE_MENU = "img/menu/";
	private final static String IMAGE_PLAY = "img/play/";
	private final static String FLASH_LOGO = "img/";
	private static String FONT_PREFIX = "font/";
	static {
		FONT_PREFIX = "font/";
	}

	public static Font loadTTFFont(String paramString, int paramInt1,
			int paramInt2) {
		BitmapTextureAtlas localBitmapTextureAtlas = new BitmapTextureAtlas(
				Share.activity.getTextureManager(), 512, 512,
				TextureOptions.BILINEAR);
		Font localFont = FontFactory.createFromAsset(
				Share.activity.getFontManager(), localBitmapTextureAtlas,
				Share.activity.getAssets(), FONT_PREFIX + paramString,
				paramInt1, true, paramInt2);
		localBitmapTextureAtlas.load();
		localFont.load();
		return localFont;
	}

	public static ITextureRegion loadResources(final String paramString) {
		ITextureRegion i;
		try {
			BitmapTexture localBitmapTexture = new BitmapTexture(
					Share.activity.getTextureManager(),
					new IInputStreamOpener() {
						@Override
						public InputStream open() throws IOException {
							return Share.activity.getAssets().open(
									ResourcesLoader.IMAGE_PLAY + paramString);
						}
					}, TextureOptions.BILINEAR);
			localBitmapTexture.load();
			TextureRegion localTextureRegion2 = TextureRegionFactory
					.extractFromTexture(localBitmapTexture);
			i = localTextureRegion2;
			return i;
		} catch (Exception e) {
			return null;
		}
	}

	public static TiledTextureRegion loadTileResources(
			final String paramString, int paramInt1, int paramInt2) {
		TiledTextureRegion i;
		try {
			BitmapTexture localBitmapTexture = new BitmapTexture(
					Share.activity.getTextureManager(),
					new IInputStreamOpener() {

						@Override
						public InputStream open() throws IOException {
							return Share.activity.getAssets().open(
									ResourcesLoader.IMAGE_PLAY + paramString);
						}
					}, TextureOptions.BILINEAR);
			localBitmapTexture.load();
			TiledTextureRegion localTiledTextureRegion2 = TextureRegionFactory
					.extractTiledFromTexture(localBitmapTexture, paramInt1,
							paramInt2);
			i = localTiledTextureRegion2;
			return i;
		} catch (Exception e) {
			return null;
		}
	}
}
