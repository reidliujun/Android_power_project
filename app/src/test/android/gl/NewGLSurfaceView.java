package test.android.gl;

import test.android.gl.resources.GLRenderer;
import test.android.gl.scene.TestScene;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;


public class NewGLSurfaceView extends GLSurfaceView {
	static final int MODE_ROTATE = 1;
	static final int MODE_ZOOM = 2;
	static final int MODE_PAN = 3;
	private GLRenderer mRenderer;
	private float previousX;
	private float previousY;
	private static boolean startZoom = false;
	private float oldDist = 0.f;
	private int mode = MODE_ROTATE;
	public float scaleFactor = 0.05f;	//lame, but thats how it goes
	

	
	private ScaleGestureDetector mScaleDetector;	
	
	public NewGLSurfaceView(Context context) {
        super(context);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        
        mRenderer = new GLRenderer(context);
        mRenderer.setResourcesHandle(context.getResources());
        mRenderer.setScale(scaleFactor);        
        
        
        mRenderer.setScene(new TestScene(context.getApplicationContext()));
        
        setRenderer(mRenderer);
        
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

	
	
	private float spacing(MotionEvent event) {
		   float x = event.getX(0) - event.getX(1);
		   float y = event.getY(0) - event.getY(1);
		   return (float)Math.sqrt(x * x + y * y);
	}
	
	public void setMode(int mode) {
		this.mode  = mode;
	}
	
	

	public boolean onTouchEvent(final MotionEvent event) {
		mScaleDetector.onTouchEvent(event);
		if (mScaleDetector.isInProgress())
			return true;
		
//		queueEvent(new Runnable(){
//			public void run() {

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					switch (NewGLSurfaceView.this.mode){
					case MODE_ROTATE:
						mRenderer.startDrag(event.getX(), event.getY());
						break;
					case MODE_ZOOM:
						mRenderer.startZoom(event.getX(), event.getY());
						break;
					case MODE_PAN:
						mRenderer.startPan(event.getX(), event.getY());

						break;
					}
					break;

				case MotionEvent.ACTION_MOVE:
					switch (NewGLSurfaceView.this.mode){
					case MODE_ROTATE:
						mRenderer.drag(event.getX(), event.getY());
						break;
					case MODE_ZOOM:
						mRenderer.zoom(event.getX(), event.getY());
						break;
					case MODE_PAN:
						mRenderer.pan(event.getX(), event.getY());
						break;
					}
					break;
				case MotionEvent.ACTION_UP:
					switch (NewGLSurfaceView.this.mode){
					case MODE_ROTATE:
						//mRenderer.drag(event.getX(), event.getY());
						break;
					case MODE_ZOOM:
						mRenderer.stopZoom(event.getX(), event.getY());
						break;
					case MODE_PAN:
						mRenderer.stopPan(event.getX(), event.getY());
						break;
					}
					break;

				}
//			}});
	        
    	
        return true;
     }
	
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        scaleFactor  *= detector.getScaleFactor();       
	        // Don't let the object get too small or too large.
//	        scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
	        mRenderer.setScale(scaleFactor);
	        return true;
	    }
	}

}
