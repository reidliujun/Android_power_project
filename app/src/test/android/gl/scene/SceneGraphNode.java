package test.android.gl.scene;

import java.util.LinkedList;

import test.android.gl.resources.IDrawRenderer;


public abstract class SceneGraphNode {
	private LinkedList<SceneGraphNode> children;
		
	public void addChild(SceneGraphNode child) {
		if (children == null) {
			children = new LinkedList<SceneGraphNode>();
		}
		children.add(child);
	}
	
	public void removeChild(SceneGraphNode child) {
		if (children == null) {
			children.remove(child);
		}
	}
	
	public LinkedList<SceneGraphNode> getChildren() {
		return children;
	}
	
	/**
	 * Drawing an object
	 * No recursive calls implemented here to let scene graph
	 * manage drawing process
	 */
	public abstract void draw(IDrawRenderer renderer);
	
}
