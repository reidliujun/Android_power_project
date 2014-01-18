package test.android.gl.scene;

import java.util.LinkedList;
import test.android.gl.resources.IDrawRenderer;

public class SceneGraph {
	private SceneGraphNode root;
	
	public SceneGraph() {
		root = new SceneGraphNode() {
			
			@Override
			public void draw(IDrawRenderer renderer) {
			}
		};
	}
	
	public SceneGraphNode getRoot() {
		return root;
	}
	
	public void addChild(SceneGraphNode parent, SceneGraphNode child) {
		if (parent == null) {
			root.addChild(child);
		} else {
			parent.addChild(child);
		}
	}
	
	public void draw(IDrawRenderer render) {
		draw(render, root);
	}
	
	/**
	 * Simplest recursive solution
	 * @param rootNode
	 */
	private void draw(IDrawRenderer render, SceneGraphNode rootNode) {
		for (SceneGraphNode node : rootNode.getChildren()) {
			render.saveState();
			node.draw(render);
			if (node.getChildren() != null) {
				draw(render, node);
			}
			render.restoreState();
		}
	}
	
}
