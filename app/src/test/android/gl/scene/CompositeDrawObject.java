package test.android.gl.scene;

import java.util.ArrayList;
import java.util.List;

import test.android.gl.resources.IDrawRenderer;
import test.android.gl.resources.Mesh;

public class CompositeDrawObject extends DrawObject {

	private List<DrawObject> children;
	
	public CompositeDrawObject() {
		children = new ArrayList<DrawObject>();
	}
	
	public void addChild(DrawObject obj) {
		children.add(obj);
	}
	
	public void removeChild(DrawObject obj) {
		children.remove(obj);
	}
	
	@Override
	public void draw(IDrawRenderer render) {
		for (DrawObject obj : children) {
			render.saveState();
			obj.draw(render);
			render.restoreState();
		}
	}

}
