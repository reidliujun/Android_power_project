package fi.aalto.powerconsumptor.components;

import test.android.gl.NewGLSurfaceView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import fi.aalto.powerconsumptor.Component;

public class GPUComponent extends Component {

	public GPUComponent(Context context) {
		//Set the name of the component
		super(context, "Open GL Component");
	}

	private NewGLSurfaceView glView;
	
	@Override
	public View getView(Context context, ViewGroup parent) {
		//Create component layout
		glView = new NewGLSurfaceView(context);
		//Set layout params (if not default)
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 150);
		glView.setLayoutParams(params);
		//return component for display
		return glView;
	}

	@Override
	public void onStart(Context context) {
		glView.onResume();
	}

	@Override
	public void onStop(Context context) {
		glView.onPause();
	}

}
