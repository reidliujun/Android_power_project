package fi.aalto.powerconsumptor.components;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import fi.aalto.powerconsumptor.Component;

public class CameraComponent extends Component {

	public CameraComponent(Context context, String title) {
		super(context, "Camera");
		// TODO Auto-generated constructor stub
	}
	
	private boolean cameraPresent = false;
	private Camera cam1;

	@Override
	public View getView(Context context, ViewGroup parent) {
		cameraPresent = checkCameraHardware(context);
		if (!cameraPresent) {
			TextView tv = new TextView(context);
			tv.setText("Camera not found");
			return tv;
		}
		if (cameraPresent) {
			cam1 = getCameraInstance(0);
		}
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.VERTICAL);
		
		if (cam1 != null) {
			
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 150, 1);
			CameraPreview preview = new CameraPreview(context, cam1, true);
			ll.addView(preview, params);
		}
		return ll;
	}

	@Override
	public void onStart(Context context) {
		
	}

	@Override
	public void onStop(Context context) {
		if (cam1 != null) {
			cam1.release();
		}
	}
	
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(int index){
	    Camera c = null;
	    try {
	        c = Camera.open(index); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	
	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}

}
