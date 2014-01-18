package test.android.gl.resources;

import test.android.gl.utils.Vector3D;
import android.graphics.Bitmap;

public interface IDrawRenderer {
	public void setPosition(Vector3D position);
	public void setRotation(Vector3D rotation);
	public void setScale(Vector3D scale);
	public void setTexture(String textureID);
	public void setMesh(Mesh mesh);
	public void draw();
	
	//Texturing
	public int[] generateTextures(int count);
	public void preloadTexture(int texID, Bitmap bmp);
	public void saveState();
	public void restoreState();
}
