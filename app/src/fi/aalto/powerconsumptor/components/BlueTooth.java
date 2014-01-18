package fi.aalto.powerconsumptor.components;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fi.aalto.powerconsumptor.Component;
import fi.aalto.powerconsumptor.UIUtils;

public class BlueTooth extends Component {

	public BlueTooth(Context context) {
		super(context, "Blue Tooth");
		// TODO Auto-generated constructor stub
	}
	TextView tv;
	@Override
	public View getView(Context context, ViewGroup parent) {
		// TODO Auto-generated method stub
		tv = new TextView(context);
		return tv;
	}
	private BluetoothAdapter mBluetoothAdapter;
	private boolean isEnabling;
	private boolean isDiscovered;
	private Thread mThread;
	@Override
	public void onStart(Context context) {
		
		//Register the BroadcastReceiver
	    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	    filter.addAction(BluetoothDevice.ACTION_UUID);
	    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
	    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	    context.registerReceiver(ActionFoundReceiver, filter); // Don't forget to unregister during onDestroy
	 
		
		mThread = new  Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//				isEnabling = mBluetoothAdapter.enable();
				isEnabling=mBluetoothAdapter.enable();
				UIUtils.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						tv.setText(isEnabling? "BT Enabled" : "BT not supported");
					}
				});
//				tv.setText(isEnabling? "BT Enabled" : "BT not supported");
				mBluetoothAdapter.startDiscovery();
				try {
					Thread.sleep(12000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isDiscovered=mBluetoothAdapter.isDiscovering ();
				UIUtils.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						tv.setText(isDiscovered? "BT discovered" : "BT not discovered");
					}
				});
				
//				tv.setText(isDiscovered? "BT discovered" : "BT not discovered");
				super.run();
			}
		};
		mThread.start();
		
		
		

	}

	@Override
	public void onStop(Context context) {
		// TODO Auto-generated method stub
		if (mBluetoothAdapter != null) {
			isEnabling = mBluetoothAdapter.disable();
		}
		context.unregisterReceiver(ActionFoundReceiver);
	}
	
	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){
	     
	    @Override
	    public void onReceive(Context context, Intent intent) {
	     String action = intent.getAction();
	     if(BluetoothDevice.ACTION_FOUND.equals(action)) {
	       BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	       tv.setText("\n  Device: " + device.getName() + ", " + device);
	     } else {
	       if(BluetoothDevice.ACTION_UUID.equals(action)) {
	         BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	         Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
	         StringBuilder all = new StringBuilder();
	         for (int i=0; i<uuidExtra.length; i++) {
	           all.append("\n  Device: " + device.getName() + ", " + device + ", Service: " + uuidExtra[i].toString());
	         }
	         tv.setText(all.toString());
	       } else {
	         if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
	        	 tv.setText("\nDiscovery Started...");
	         } else {
	           if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
	        	 tv.setText("\nDiscovery Finished");
	           }
	         }
	       }
	     }
	    }
	};

}
