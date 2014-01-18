package fi.aalto.powerconsumptor.components;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fi.aalto.powerconsumptor.Component;

public class Sensors extends Component {

	public Sensors(Context context) {
		super(context, "Sensors");
		// TODO Auto-generated constructor stub
	}

	TextView tv;
	
	@Override
	public View getView(Context context, ViewGroup parent) {
		tv = new TextView(context);
		return tv;
	}

	SensorManager mSensorManager;
	
	SensorEventListener listener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	public void onStart(Context context) {
		// TODO Auto-generated method stub
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ALL);
		mSensorManager.registerListener(listener, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
		
		mSensorManager.registerListener(listener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(listener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(listener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
				SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(listener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
				SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(listener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_ALL),
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	public void onStop(Context context) {
		// TODO Auto-generated method stub
		mSensorManager.unregisterListener(listener);
	}

}
