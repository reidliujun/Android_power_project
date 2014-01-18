package test.android.gl.scene;

import test.android.gl.resources.IDrawRenderer;
import test.android.gl.utils.Vector3D;

public class WashingMachine extends CompositeDrawObject {

	Plane front, back, side1, side2, bottom, top;
	
	public WashingMachine() {
		super();
		
		float width = 2.f;
		float height = 2.5f;
		float depth = 5f;
		
		front = new Plane("simple", width, height);
		side1 = new Plane("simple", depth, height);
		side2 = new Plane("simple", depth, height);
		back = new Plane("simple", width, height);
		top = new Plane("simple", depth, width);
		
		top.setRotation(new Vector3D(0, -90.f, 0.f));
//		top.setPosition(new Vector3D());
		
		front.setPosition(new Vector3D(0, 0, depth / 2.f));
		
		side1.setPosition(new Vector3D(-width / 2.f, 0.f, 0.f));
		side1.setRotation(new Vector3D(0, -90.f, 0.f));
		
		side2.setPosition(new Vector3D(width / 2.f, 0.f, 0.f));
		side2.setRotation(new Vector3D(0, 90.f, 0));
		
		back.setRotation(new Vector3D(0, 180, 0));
		back.setPosition(new Vector3D(0, 0, -depth / 2.f));
		
		addChild(front);
		addChild(side1);
		addChild(side2);
		addChild(back);
		addChild(top);
	}
	

}
