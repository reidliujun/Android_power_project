package test.android.gl.resources;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import test.android.gl.arcball.ArcBall;
import test.android.gl.arcball.Matrix4f;
import test.android.gl.arcball.Point2f;
import test.android.gl.arcball.Quat4f;
import test.android.gl.scene.DrawObject;
import test.android.gl.scene.IGameScene;
import test.android.gl.utils.Vector3D;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

public class GLRenderer implements Renderer, IDrawRenderer {
	private boolean firstLoad = true;
	private Vector3D angle;
	private Bitmap bitmap;
	private Resources resources;
	private int[] textures;
	private Vector3D color = new Vector3D(0.3f, 0.3f, 0.3f);
	private Vector3D bgColor = new Vector3D(1.f, 0.f, 0.f);
	private Vector3D initPosition = new Vector3D(0.f, 0.f, 0.f);
	private Vector3D position = new Vector3D(0.f, 0.f, 0.f);
	private Vector3D scale = new Vector3D(0.05f, 0.05f, 0.05f);
	private float zoom = -1.f;
	
	private float sideRotateOffset = 0.f; //in percent
	private float sideX;
	private float sideY;
	//For ArcBall rotation
	private Matrix4f lastRot = new Matrix4f();
    private Matrix4f thisRot = new Matrix4f();
    //private final Object matrixLock = new Object(); //for synchronization
    private float[] matrix = new float[16];

    private ArcBall arcBall;
    private DrawObject drawableObj = null;
	private float width;
	private float height;
	private Context context = null;
	private GL10 gl = null;
	private float previousX;
	private float previousY;
	private Vector3D oldScale = new Vector3D();
	private int textureID;
	private Mesh mesh;
	private DrawObject drawableObj2;
	private DrawObject drawableObj3;
	
	private IGameScene scene;
	
	
	public GLRenderer(Context context){
		this.context = context;
	}
	/**
	 * Temporary function
	 */
	private void initScene() {
		ResourceManager manager = ResourceManager.getInstance(context);
		manager.setMeshLoader(new OBJLoader(context));
		manager.addMesh("kubas.obj", "kubas");
		TextureCache textures = TextureCache.getInstance();
		textures.addTexture("factory.bmp", "factory");
		textures.initTextures(this);
		drawableObj = new DrawObject(manager.getMesh("kubas"), "factory");
//		drawableObj2 = new DrawObject(manager.getMesh("kubas"), "factory");
//		drawableObj2.setPosition(new Vector3D(0.f, 0.f, 10.f));
//		drawableObj3 = new DrawObject(manager.getMesh("kubas"), "factory");
//		drawableObj3.setPosition(new Vector3D(0.f, 0.f, -10.f));
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		if(this.gl == null)
			this.gl = gl;
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.f, 0.f, 0.f, 0.f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		// Telling OpenGL to enable textures.
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		TextureCache.getInstance().initTextures(this);	//lol
//		initScene();
		
		//Simple GL lighting
		// Enable lighting
//		gl.glEnable(GL10.GL_LIGHTING);
//		gl.glEnable(GL10.GL_LIGHT0);
//		float lightPos[] = {1.f, 1.f, 1.f, 0.f};
//		FloatBuffer lightPosBuffer = null;
//		lightPosBuffer.wrap(lightPos);
//		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPos, 0);
//		
//		float diffuseColor[] = {1.f, 1.f, 1.f, 0.f};
//		FloatBuffer diffuseColorBuffer = null;
//		diffuseColorBuffer.wrap(diffuseColor);
//		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseColor, 0);
//		
//		float shininess[] = {50.f};
//		FloatBuffer shininessBuffer = null;
//		shininessBuffer.wrap(shininess);
//		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SHININESS, shininess, 0);
		
	}


	public void resetDrag() {
		lastRot.setIdentity();   // Reset Rotation
        thisRot.setIdentity();   // Reset Rotation
	}

	
	public void startDrag( float x, float y ) {	
		if (y > sideY && x < sideX) { //exclude lower left corner
			return;
		}
		if (x <= sideX && y < sideY) {
			x = this.width / 2.f;
		}
		if (y >= sideY) {
			y = this.height / 2.f;
		}
		
        lastRot.set( thisRot );  // Set Last Static Rotation To Last Dynamic One
        arcBall.click( new Point2f(x, y) );    // Update Start Vector And Prepare For Dragging
    }


	public void drag( float x, float y )       // Perform Motion Updates Here
    {		
		if (y > sideY && x < sideX) { //exclude lower left corner
			return;
		}
		if (x <= sideX && y < sideY) {
			x = this.width / 2.f;
		}
		if (y >= sideY) {
			y = this.height / 2.f;
		}
        Quat4f thisQuat = new Quat4f();

        // Update End Vector And Get Rotation As Quaternion
        arcBall.drag( new Point2f(x, y) , thisQuat); 
        thisRot.setRotation(thisQuat);  // Convert Quaternion Into Matrix3fT
        thisRot.mul( thisRot, lastRot); // Accumulate Last Rotation Into This One
    }
	
	public void onDrawFrame(GL10 gl) {
		
//		gl.glClearColor(color.x, color.y, color.z, 1.f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
//		gl.glPushMatrix();	//identity pushing.. lame?
		gl.glTranslatef(position.x, position.y, position.z);
		gl.glScalef(scale.x, scale.y, scale.z);
		gl.glPushMatrix();	//scale & translate of all scene
		thisRot.get(matrix);
		gl.glMultMatrixf(matrix, 0); 

		//Draw current scene
		if (scene != null) {
			gl.glPushMatrix();
			scene.render(this);
			gl.glPopMatrix();
		}
//		gl.glPushMatrix();
//		drawableObj2.draw(this);
//		gl.glPopMatrix();
//		gl.glPushMatrix();
//		drawableObj3.draw(this);
//		gl.glPopMatrix();
//		gl.glPopMatrix();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.width = (float) width;
		this.height = (float) height;
		// Start Of ArcBall Initialization
		arcBall = new ArcBall(width, height);
		
		lastRot.setIdentity();   // Reset Rotation
		thisRot.setIdentity();   // Reset Rotation
		thisRot.get(matrix);
		position.z = zoom;
		initPosition.z = zoom;
//		if (firstLoad || true) {		// <-- VERY need to fix this
//			firstLoad = false;
			// Sets the current view port to the new size.
			gl.glViewport(0, 0, width, height);
			// Select the projection matrix
			gl.glMatrixMode(GL10.GL_PROJECTION);
			// Reset the projection matrix
			gl.glLoadIdentity();
			// Calculate the aspect ratio of the window
			GLU.gluPerspective(gl, 45.0f, this.width / this.height, 0.1f,
					100.0f);
			// Select the modelview matrix
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			// Reset the modelview matrix
			gl.glLoadIdentity();
//		}

        arcBall.setBounds(this.width, this.height);
        sideX = this.width / 100.f * this.sideRotateOffset;
        sideY = this.height / 100.f * (100.f - this.sideRotateOffset);
        
        new Thread("GL mover") {
        	private Random rand = new Random(System.nanoTime());
        	
        	public void run() {
        		startDrag((int) (GLRenderer.this.width / 2), (int) (GLRenderer.this.height / 2));
        		drag((int) (GLRenderer.this.width / 2), (int) (GLRenderer.this.height / 4));
        		int dragY = (int) (GLRenderer.this.height / 2);
        		int dragX = 100;
        		startDrag(dragX, dragY);
        		while (true) {
	        		if (arcBall != null) {
	        			try {
//			        		startDrag(100, 100);
//			        		drag(100 + rand.nextInt(100) - 50, 100 + rand.nextInt(100) - 50);
	        				dragX += 10;
	        				drag(dragX, dragY);
	        				if (dragX > 300) {
	        					dragX = 100;
	        					startDrag(dragX, dragY);
	        				}
			        		try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
	        			} catch (Exception e) {
		        			break;
		        		}
	        		}
        		}
        	}
        }.start();
	}
	
	public void setColor(float r, float g, float b) {
		color = new Vector3D(r, g, b);
	}

	public void setResourcesHandle(Resources resources) {
		this.resources = resources;
		
	}

	public void startZoom(float x, float y) {
		previousX = x;
		previousY = y;
		oldScale.assign(scale);
	}
	
	public void zoom(float x, float y) {
		float dist = (float) Math.sqrt((x - previousX) * (x - previousX) + (y - previousY) * (y - previousY));
		float zoom;
		if (y > previousY){ //zoom down
			zoom = (this.height - dist) / this.height;
		} else {
			zoom = this.height / (this.height - dist);
		}
		scale.assign(oldScale);
		this.scale.mult(new Vector3D(zoom, zoom, zoom));
	}

	public void stopZoom(float x, float y) {
		oldScale.assign(scale);
	}
	
	public void startPan(float x, float y) {
		previousX = x;
		previousY = y;
	}
	
	public void pan(float x, float y) {
		position.assign(initPosition);
		position.x += (previousX - x) / zoom;
		position.y += (y - previousY) / zoom;
	}
	
	public void stopPan(float x, float y) {
		pan(x, y);
		initPosition.assign(position);
	}

	public void setScale(float scaleFactor) {
		this.scale = new Vector3D(scaleFactor, scaleFactor, scaleFactor);
		Log.d("temp", "New scale is " + scaleFactor);
	}

	@Override
	public void setPosition(Vector3D position) {
		gl.glTranslatef(position.x, position.y, position.z);
		
	}

	@Override
	public void setRotation(Vector3D rotation) {
		gl.glRotatef(rotation.x, 1, 0, 0);
		gl.glRotatef(rotation.y, 0, 1, 0);
		gl.glRotatef(rotation.z, 0, 0, 1);
	}

	@Override
	public void setScale(Vector3D scale) {
		gl.glScalef(scale.x, scale.y, scale.z);
		
	}

	@Override
	public void setTexture(String textureID) {
		int tex = TextureCache.getInstance().getTextureIndex(textureID);
		this.textureID = tex;
	}

	@Override
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
		
	}

	@Override
	public void draw() {
		gl.glEnable(GL10.GL_ALPHA_TEST);
		
//		gl.glColor4f(1.f, 1.f, 1.f, 0.5f);
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK); // OpenGL docs

		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		// Enabled the vertices buffer for writing and to be used during
		// rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mesh.getVertexBuffer());
		
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		if (mesh.getTextureBuffer() != null)
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mesh.getTextureBuffer());
		if (textureID != -1)
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
		if (mesh.getIndicesBuffer() != null) {
			gl.glDrawElements(GL10.GL_TRIANGLES, mesh.getIndicesBuffer().capacity(), GL10.GL_UNSIGNED_SHORT, mesh.getIndicesBuffer());
		} else {
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, mesh.getVertexBuffer().capacity() / 3);
		}
		// Disable the texture coordinates buffer.
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY); // OpenGL docs
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); // OpenGL docs
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE); // OpenGL docs
		gl.glDisable(GL10.GL_ALPHA_TEST);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
	}

	@Override
	public int[] generateTextures(int count) {
		int[] textures = new int[count];
		gl.glGenTextures(count, textures, 0);
		return textures;
	}

	@Override
	public void preloadTexture(int texID, Bitmap bmp) {
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texID);
	 
		// Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	 
		// Different possible texture parameters
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
	 
		// Use the Android GLUtils to specify a two-dimensional texture image
		// from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
		
	}
	
	public void setScene(IGameScene scene) {
		
		this.scene = scene;
	}
	@Override
	public void saveState() {
		gl.glPushMatrix();
		
	}
	@Override
	public void restoreState() {
		gl.glPopMatrix();
	}
}
