package fi.aalto.powerconsumptor.components;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fi.aalto.powerconsumptor.Component;

public class WifiHotSpot extends Component {

	public WifiHotSpot(Context context) {
		super(context, "Wifi Hot Spot");
	}

	@Override
	public View getView(Context context, ViewGroup parent) {
		tv = new TextView(context);
		return tv;
	}
	
	private WifiManager wifiManager;
	private boolean enabled;
	private TextView tv;

	@Override
	public void onStart(Context context) {
		//Enable 3G
		ConnectivityManager dataManager;
		dataManager  = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		Method dataMtd;
		try {
			dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
			dataMtd.setAccessible(true);
			dataMtd.invoke(dataManager, true);        //True - to enable data connectivity 
		} catch (Exception e1) {
			enabled = false;
			tv.setText("Could not turn on 3G");
			e1.printStackTrace();
			return;
		}
		
		//Enable wifi hotspot
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		WifiConfiguration netConfig = new WifiConfiguration();
        netConfig.SSID = "AccessPoint"; 
        netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        
		wifiManager.setWifiEnabled(false);

		try {
			Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
			method.invoke(wifiManager, netConfig, true);
			enabled = true;
		} catch (NoSuchMethodException e) {
			enabled = false;
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			enabled = false;
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			enabled = false;
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			enabled = false;
			e.printStackTrace();
		}
		tv.setText(enabled ? "Ap Enabled" : "Ap not supported");
	}

	@Override
	public void onStop(Context context) {
		if (enabled) {
			try {
				Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
				method.invoke(wifiManager, null, false);
			} catch (NoSuchMethodException e) {
				enabled = false;
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				enabled = false;
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				enabled = false;
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				enabled = false;
				e.printStackTrace();
			}
		}
		enabled = false;
	}

}
