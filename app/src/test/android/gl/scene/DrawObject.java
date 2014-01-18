package test.android.gl.scene;

import test.android.gl.resources.IDrawRenderer;
import test.android.gl.resources.Mesh;
import test.android.gl.utils.Vector3D;

public class DrawObject extends SceneGraphNode{

	private Mesh mesh;
	private String textureID;
	private Vector3D position = new Vector3D();	//initializes to 0
	private Vector3D rotation = new Vector3D();	//initializes to 0
	private Vector3D scale = new Vector3D(1.f, 1.f, 1.f);
	
	public DrawObject() {
	}
	
	public DrawObject(Mesh mesh, String textureID) {
		this.mesh = mesh;
		this.textureID = textureID;
	}
	
	@Override
	public void draw(IDrawRenderer render) {
		render.setPosition(position);
		render.setRotation(rotation);
		render.setScale(scale);
		if (textureID != null)
			render.setTexture(textureID);
		if (mesh != null)
			render.setMesh(mesh);
		render.draw();
	}
	
	public DrawObject clone() {
		DrawObject obj = new DrawObject(mesh, textureID);
		obj.position.assign(position);
		obj.rotation.assign(rotation);
		obj.scale.assign(scale);
		return obj;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	public String getTextureID() {
		return textureID;
	}

	public void setTextureID(String textureID) {
		this.textureID = textureID;
	}

	public Vector3D getPosition() {
		return position;
	}

	public void setPosition(Vector3D position) {
		this.position = position;
	}

	public Vector3D getRotation() {
		return rotation;
	}

	public void setRotation(Vector3D rotation) {
		this.rotation = rotation;
	}

	public Vector3D getScale() {
		return scale;
	}

	public void setScale(Vector3D scale) {
		this.scale = scale;
	}

	
	
}
