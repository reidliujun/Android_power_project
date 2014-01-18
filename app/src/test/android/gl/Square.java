package test.android.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class Square {
	// Our vertices.
	private float vertices[] = {
		      -1.0f,  1.0f, 0.0f,  // 0, Top Left
		      -1.0f, -1.0f, 0.0f,  // 1, Bottom Left
		       1.0f, -1.0f, 0.0f,  // 2, Bottom Right
		       1.0f,  1.0f, 0.0f,  // 3, Top Right
		};
	
	float textureCoordinates[] = {0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f };

	// The order we like to connect them.
	private short[] indices = { 0, 1, 2, 0, 2, 3 };

	// Our vertex buffer.
	private FloatBuffer vertexBuffer;

	// Our index buffer.
	private ShortBuffer indexBuffer;
	
	//Our texture coordinates buffer
	private FloatBuffer uvBuffer;
	private Bitmap bitmap;
	private int textureId = -1;
	
	

	public Square() {
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
		
	}
	
//	public void loadTexture(GL10 gl, Bitmap bitmap) {
//		this.bitmap = bitmap;
//		// Generate one texture pointer...
//		int[] textures = new int[1];
//		gl.glGenTextures(1, textures, 0);
//		textureId = textures[0];
//
//		// ...and bind it to our array
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
//
//		// Create Nearest Filtered Texture
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
//				GL10.GL_LINEAR);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
//				GL10.GL_LINEAR);
//
//		// Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
//				GL10.GL_CLAMP_TO_EDGE);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
//				GL10.GL_REPEAT);
//
//		// Use the Android GLUtils to specify a two-dimensional texture image
//		// from our bitmap
//		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
//		System.out.println("Texture Loaded");
//	}

	/**
	 * This function draws our square on screen.
	 * @param gl
	 */
	public void draw(GL10 gl) {
		gl.glEnable(GL10.GL_ALPHA_TEST);
		
		gl.glColor4f(1.f, 1.f, 1.f, 0.5f);
		// Counter-clockwise winding.
//		gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
		// Enable face culling.
		//gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
		// What faces to remove with the face culling.
//		gl.glCullFace(GL10.GL_BACK); // OpenGL docs

		// Enabled the vertices buffer for writing and to be used during
		// rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, // OpenGL docs
                                 vertexBuffer);
		
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, uvBuffer);
		if (textureId != -1)
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,// OpenGL docs
				  GL10.GL_UNSIGNED_SHORT, indexBuffer);
		
		// Disable the texture coordinates buffer.
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY); // OpenGL docs
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); // OpenGL docs
		// Disable face culling.
//		gl.glDisable(GL10.GL_CULL_FACE); // OpenGL docs
		gl.glDisable(GL10.GL_ALPHA_TEST);
	}

}
