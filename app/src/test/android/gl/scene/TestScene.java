package test.android.gl.scene;

import test.android.gl.Square;
import test.android.gl.resources.IDrawRenderer;
import test.android.gl.resources.ResourceManager;
import test.android.gl.resources.TextureCache;
import test.android.gl.utils.Vector3D;
import android.content.Context;

public class TestScene implements IGameScene {

	private ResourceManager resManager;
	private SceneGraph sceneGraph;
	
	public TestScene(Context context) {
		resManager = ResourceManager.getInstance(context.getApplicationContext());
        resManager.addMesh("object.obj", "kubas");
//        resManager.addTexture("texture.png", "simple");
        TextureCache.getInstance().addTexture("texture.png", "simple");
        
//        DrawObject myObject;
        DrawObject myObject = new DrawObject(resManager.getMesh("kubas"), "simple");
//        myObject = new Plane("simple", 5, 2);
//        myObject = new WashingMachine();
//        myObject.setTextureID("simple");
        sceneGraph = new SceneGraph();
        
        sceneGraph.addChild(null, myObject);
//        myObject = myObject.clone();
//        myObject.setPosition(new Vector3D(0.f, 0.f, 5.f));
//        sceneGraph.addChild(null, myObject);
	}
	
	@Override
	public void render(IDrawRenderer render) {
		
		sceneGraph.draw(render);
	}

}
