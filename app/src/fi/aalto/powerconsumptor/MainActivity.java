package fi.aalto.powerconsumptor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import fi.aalto.powerconsumptor.components.BlueTooth;
import fi.aalto.powerconsumptor.components.CPUComponent;
import fi.aalto.powerconsumptor.components.CameraComponent;
import fi.aalto.powerconsumptor.components.DownloadFile;
import fi.aalto.powerconsumptor.components.GPS;
import fi.aalto.powerconsumptor.components.GPUComponent;
import fi.aalto.powerconsumptor.components.LED;
import fi.aalto.powerconsumptor.components.Sensors;
import fi.aalto.powerconsumptor.components.Vibrate;
import fi.aalto.powerconsumptor.components.VideoComponent;
import fi.aalto.powerconsumptor.components.WifiHotSpot;

public class MainActivity extends Activity {

	private ViewGroup list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		list = (ViewGroup) findViewById(R.id.component_list);
		
		list.addView(new GPUComponent(this));
		list.addView(new WifiHotSpot(this));
		list.addView(new DownloadFile(this, "http://54.194.111.197:8080/file"));
		list.addView(new DownloadFile(this, "http://54.194.111.197:8081/file"));
		list.addView(new CameraComponent(this, "Camera"));
//		list.addView(new VideoComponent(this, "video_c2"));
		list.addView(new BlueTooth(this));
		list.addView(new GPS(this));
		//list.addView(new LED(this));
		list.addView(new Sensors(this));
		list.addView(new Vibrate(this));
		list.addView(new CPUComponent(this));
		
		LinearLayout main = (LinearLayout)findViewById(R.id.mainlayout);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 200);
		
//		params.addRule(RelativeLayout.BELOW, R.id.list);
//		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		main.addView(new VideoComponent(this, "video_c2"), params);
	}
	
	@Override
	protected void onStop() {
		for (int i = 0, n = list.getChildCount(); i < n; i ++) {
			View v = list.getChildAt(i);
			if (v instanceof Component) {
				((Component)v).setEnabled(false);
			}
		}
		super.onStop();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
