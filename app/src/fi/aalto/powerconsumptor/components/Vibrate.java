package fi.aalto.powerconsumptor.components;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewGroup;
import fi.aalto.powerconsumptor.Component;

public class Vibrate extends Component {

	public Vibrate(Context context) {
		super(context, "Vibrate");
		// TODO Auto-generated constructor stub
	}
	
	private Vibrator v;
	private boolean vibrate;
	@Override
	public View getView(Context context, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Context context) {
		// TODO Auto-generated method stub
		vibrate = true;
		v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		// Vibrate for 500 milliseconds
		new Thread() {
			public void run() {
				while (vibrate) {
					v.vibrate(500);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	public void onStop(Context context) {
		// TODO Auto-generated method stub
		vibrate = false;
	}

}
