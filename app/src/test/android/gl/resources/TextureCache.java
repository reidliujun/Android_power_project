package test.android.gl.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class TextureCache {
	private static TextureCache instance;
	
	private HashMap<String, Texture> textures;
	
	private class Texture {
		public int glIndex = -1;
		public boolean initialized = false;
		public String file = null;
	}
	
	public static TextureCache getInstance() {
		if (instance == null) {
			instance = new TextureCache();
		}
		return instance;
	}
	
	private TextureCache() {
		textures = new HashMap<String, Texture>();
	}
	
	public void addTexture(String fileName, String texName) {
		Texture tex = new Texture();
		tex.file  = fileName;
		textures.put(texName, tex);
	}
	
	/**
	 * Loads textures from files and preloads them in renderer
	 * @param renderer - default renderer
	 */
	public void initTextures(IDrawRenderer renderer) {
		int indices[] = renderer.generateTextures(textures.size());
		int i = 0;	//texture index
		for (Entry<String, Texture> entry : textures.entrySet()) {
			Texture tex = entry.getValue();
			Bitmap bmp = ResourceManager.getInstance().loadTexture(tex.file);
			if (bmp != null) {
				tex.glIndex = indices[i];
				renderer.preloadTexture(tex.glIndex, bmp);
				tex.initialized = true;
				entry.setValue(tex);
				bmp.recycle();
				bmp = null;
			}
		}
	}
	
	/**
	 * Needed for rendering process
	 * @param name
	 * @return
	 */
	public int getTextureIndex(String name) {
		Texture tex = textures.get(name);
		if (tex != null && tex.initialized) {
			return tex.glIndex;
		}
		return -1;
	}
	
	public void removeTexture(String name) {
		textures.remove(name);
	}
	
	
}