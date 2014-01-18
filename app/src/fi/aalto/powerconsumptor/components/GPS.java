package fi.aalto.powerconsumptor.components;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fi.aalto.powerconsumptor.Component;

public class GPS extends Component{

	public GPS(Context context) {
		super(context, "GPS");
		// TODO Auto-generated constructor stub
	}
	TextView tv;
	@Override
	public View getView(Context context, ViewGroup parent) {
		// TODO Auto-generated method stub
		tv = new TextView(context);
		return tv;
	}
	
	private LocationManager mLocationManager;
	private LocationProvider gpsLocationProvider, netwLocationProvider;
//	private LocationRequest mLocationRequest;
	@Override
	public void onStart(Context context) {
		int INTERVAL_IN_SECONDS = 1;
	    long INTERVAL = 1000 * INTERVAL_IN_SECONDS;
		
		// TODO Auto-generated method stub
		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
		
		//		mLocationRequest = LocationRequest.create();
//		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//		mLocationRequest.setFastestInterval(INTERVAL);
		
		tv.setText("Started searching");
	}
	
	private LocationListener listener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			System.out.println(status);
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("Provider enabled " + provider);
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("Provider disabled " + provider);
		}
		
		@Override
		public void onLocationChanged(Location location) {
			tv.setText(location.toString());
		}
	};

	@Override
	public void onStop(Context context) {
		// TODO Auto-generated method stub
		mLocationManager.removeUpdates(listener);
	}

}
