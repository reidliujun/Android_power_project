package fi.aalto.powerconsumptor.components;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.CompoundButton.OnCheckedChangeListener;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private Context c;
	private boolean useFlash;

	public CameraPreview(Context context, Camera camera, boolean useFlash) {
		super(context);
		mCamera = camera;
		c = context;
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		// mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, now tell the camera where to draw the
		// preview.
		try {
			mCamera.setPreviewDisplay(holder);

			mCamera.startPreview();

		} catch (IOException e) {
			Log.d("Camera", "Error setting camera preview: " + e.getMessage());
		}
		if (c instanceof Activity) {
			setCameraDisplayOrientation((Activity) c, 0, mCamera);
		}

	}

	public static void setCameraDisplayOrientation(Activity activity,
			int cameraId, android.hardware.Camera camera) {

		android.hardware.Camera.CameraInfo info =
				new android.hardware.Camera.CameraInfo();

		android.hardware.Camera.getCameraInfo(cameraId, info);

		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;

		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else { // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// empty. Take care of releasing the Camera preview in your activity.
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.

		if (mHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or
		// reformatting changes here

		// start preview with new settings
		try {
			mCamera.setPreviewTexture(new SurfaceTexture(0));
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
			if (useFlash) {
				// Parameters p = mCamera.getParameters();
				// p.setFlashMode(Parameters.FLASH_MODE_TORCH);
				// mCamera.setParameters(p);
				Parameters params = mCamera.getParameters();
				params.setFlashMode(Parameters.FLASH_MODE_ON);
				mCamera.setParameters(params);

				params = mCamera.getParameters();
				params.setFlashMode(Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(params);
			}
			mCamera.autoFocus(new AutoFocusCallback() {
				public void onAutoFocus(boolean success, Camera camera) {
				}
			});

		} catch (Exception e) {
			Log.d("Camrea", "Error starting camera preview: " + e.getMessage());
		}
	}
}