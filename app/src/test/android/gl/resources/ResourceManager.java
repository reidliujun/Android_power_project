package test.android.gl.resources;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class ResourceManager {
	
	private static ResourceManager instance;
	private Context context;
	private HashMap<String, Bitmap> textures;
	private HashMap<String, Mesh> meshes;
	private IMeshLoader meshLoader;
	
	
	private ResourceManager() {
		textures = new HashMap<String, Bitmap>();
		meshes = new HashMap<String, Mesh>();
	}
	
	public static ResourceManager getInstance() {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}
	
	public static ResourceManager getInstance(Context c) {
		instance = getInstance();
		instance.init(c);
		return instance;
	}
	
	public void init(Context c) {
		context = c;
		setMeshLoader(new OBJLoader(c));
	}
	
	public void setMeshLoader(IMeshLoader loader) {
		this.meshLoader = loader;
	}
	
	public Bitmap loadTexture(String fileName) {
		if (context == null) return null;
		try {
			Bitmap bmp = BitmapFactory.decodeStream(context.getAssets().open(fileName));
			Matrix flip = new Matrix();
			flip.postScale(1f, -1f);
			Bitmap ret = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), flip, true);
			bmp.recycle();
			bmp = null;
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addTexture(String fileName, String id) {
		Bitmap ret = loadTexture(fileName);
		if (ret != null) {
			textures.put(id, ret);
		}
	}
	
	public void addMesh(String fileName, String id) {
		try {
			Mesh mesh = meshLoader.load(fileName);
			meshes.put(id, mesh);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* Getters */
	public Bitmap getTexture(String id) {
		return textures.get(id);
	}
	
	public Mesh getMesh(String id) {
		return meshes.get(id);
	}
}
