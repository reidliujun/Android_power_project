package test.android.gl.scene;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.graphics.Bitmap;
import test.android.gl.resources.IDrawRenderer;
import test.android.gl.resources.Mesh;

public class Plane extends DrawObject {

	
	
	private static float textureCoordinates[] = {0.0f, 0.0f,
          0.0f, 1.0f,
          1.0f, 1.0f,
          1.0f, 0.0f };

	// The order we like to connect them.
	private short[] indices = { 0, 1, 2, 0, 2, 3};

	// Our vertex buffer.
	private FloatBuffer vertexBuffer;

	// Our index buffer.
	private ShortBuffer indexBuffer;
	
	//Our texture coordinates buffer
	private FloatBuffer uvBuffer;

	public Plane(String textureID, float width, float height) {
		setTextureID(textureID);
		
		float hw = width / 2.f;
		float hh = height / 2.f;
		
		float vertices[] = {
			      -hw, hh, 0.0f,  // 0, Top Left
			      -hw, -hh, 0.0f,  // 1, Bottom Left
			       hw, -hh, 0.0f,  // 2, Bottom Right
			       hw,  hh, 0.0f,  // 3, Top Right
			};
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		// short is 2 bytes, therefore we multiply the number if
		// vertices with 2.
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
		
		ByteBuffer uvbb = ByteBuffer.allocateDirect(textureCoordinates.length * 4);
		uvbb.order(ByteOrder.nativeOrder());
		uvBuffer = uvbb.asFloatBuffer();
		uvBuffer.put(textureCoordinates);
		uvBuffer.position(0);
		
		Mesh mesh = new Mesh();
		mesh.setVertexBuffer(vertexBuffer);
		mesh.setIndicesBuffer(indexBuffer);
		mesh.setTextureBuffer(uvBuffer);
		setMesh(mesh);
	}

}
