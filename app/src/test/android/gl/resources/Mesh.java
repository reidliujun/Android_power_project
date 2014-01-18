package test.android.gl.resources;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.util.Log;

public class Mesh {
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private FloatBuffer textureBuffer;
	private FloatBuffer colorBuffer;
	private ShortBuffer indices;
	
	public Mesh() {
		
	}
	
	public void init(FloatBuffer vBuffer, FloatBuffer nBuffer, FloatBuffer tBuffer,
			FloatBuffer cBuffer, ShortBuffer indBuffer) {
		vertexBuffer = vBuffer;
		if (vertexBuffer != null) vertexBuffer.position(0);
		normalBuffer = nBuffer;
		if (normalBuffer != null) normalBuffer.position(0);
		textureBuffer = tBuffer;
		if (textureBuffer != null) textureBuffer.position(0);
		colorBuffer = cBuffer;
		if (colorBuffer != null) colorBuffer.position(0);
		indices = indBuffer;
		if (indices != null) indices.position(0);
	}
	
	private FloatBuffer initFloatBuffer(FloatBuffer buffer, int size) {
		buffer = null;
		ByteBuffer buf = ByteBuffer.allocateDirect(size * 4);
		buf.order(ByteOrder.nativeOrder());
		buf.position(0);
		buffer = buf.asFloatBuffer();
		return buffer;
	}
	
	private ShortBuffer initShortBuffer(ShortBuffer buffer, int size) {
		buffer = null;
		ByteBuffer buf = ByteBuffer.allocateDirect(size * 2);
		buf.order(ByteOrder.nativeOrder());
		buf.position(0);
		buffer = buf.asShortBuffer();
		return buffer;
	}
	 
	public FloatBuffer getInitVertexBuffer(int size) {
		return initFloatBuffer(vertexBuffer, size);
	}
	
	public FloatBuffer getInitNormalBuffer(int size) {
		return initFloatBuffer(normalBuffer, size);
	}
	
	public FloatBuffer getInitTextureBuffer(int size) {
		return initFloatBuffer(textureBuffer, size);
	}
	
	public FloatBuffer getInitColorBuffer(int size) {
		return initFloatBuffer(colorBuffer, size);
	}
	
	public ShortBuffer getInitIndicesBuffer(int size) {
		return initShortBuffer(indices, size);
	}

	public FloatBuffer getVertexBuffer() {
		return vertexBuffer;
	}

	public void setVertexBuffer(FloatBuffer vertexBuffer) {
		this.vertexBuffer = vertexBuffer;
	}

	public FloatBuffer getNormalBuffer() {
		return normalBuffer;
	}

	public void setNormalBuffer(FloatBuffer normalBuffer) {
		this.normalBuffer = normalBuffer;
	}

	public FloatBuffer getTextureBuffer() {
		return textureBuffer;
	}

	public void setTextureBuffer(FloatBuffer textureBuffer) {
		this.textureBuffer = textureBuffer;
	}

	public FloatBuffer getColorBuffer() {
		return colorBuffer;
	}

	public void setColorBuffer(FloatBuffer colorBuffer) {
		this.colorBuffer = colorBuffer;
	}

	public ShortBuffer getIndicesBuffer() {
		return indices;
	}

	public void setIndicesBuffer(ShortBuffer indBuffer) {
		this.indices = indBuffer;
	}
	
	
	
	
}
