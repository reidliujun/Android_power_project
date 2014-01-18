package fi.aalto.powerconsumptor.components;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fi.aalto.powerconsumptor.Component;
import fi.aalto.powerconsumptor.R;

public class LED extends Component{

	public LED(Context context) {
		super(context, "LED");
		// TODO Auto-generated constructor stub
	}
	TextView tv;
	@Override
	public View getView(Context context, ViewGroup parent) {
		// TODO Auto-generated method stub
		tv = new TextView(context);
		return tv;
	}
	private Camera cam;
//	private Parameters params;
	@Override
	public void onStart(Context context) {
		// TODO Auto-generated method stub		
		cam = Camera.open();     
	    Parameters params = cam.getParameters();
	    params.setFlashMode(Parameters.FLASH_MODE_TORCH);
	    cam.setParameters(params);
	    cam.startPreview();
//	    cam.autoFocus(new AutoFocusCallback() {
//	        public void onAutoFocus(boolean success, Camera camera) {
//	        }
//        });
		
	}
	 
	
	@Override
	public void onStop(Context context) {
		// TODO Auto-generated method stub
//		PackageManager.FEATURE_CAMERA_FLASH
//		cam.stopPreview();
		cam.release();
	}

}
