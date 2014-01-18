package test.android.gl.text;

import javax.microedition.khronos.opengles.GL10;

import test.android.gl.utils.Vector3D;

public class GLTextArea {
	private GL10 gl;
	private Vector3D position;
	private String text;
	private int textureID;

	
	public GLTextArea(GL10 gl) {
		this.gl = gl;
	}
	
	public void draw() {
		
	}
	
	private void setPosition(Vector3D position) {
		this.position = position;
	}
	private Vector3D getPosition() {
		return position;
	}
	private void setText(String text) {
		this.text = text;
	}
	private String getText() {
		return text;
	}
}
