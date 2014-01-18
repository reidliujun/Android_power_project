package fi.aalto.powerconsumptor.components;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fi.aalto.powerconsumptor.Component;

public class CallComponent extends Component {

	public CallComponent(Context context) {
		super(context, "Make a call");
	}

	TextView tv;
	
	@Override
	public View getView(Context context, ViewGroup parent) {
		tv = new TextView(context);
		return tv;
	}

	@Override
	public void onStart(Context context) {
		tv.setText("Calling ");
		performDial(context, "");
	}
	
	private void performDial(Context c, String numberString) {
	    if (!numberString.equals("")) {
	       Uri number = Uri.parse("tel:" + numberString);
	       Intent dial = new Intent(Intent.ACTION_CALL, number);
	       c.startActivity(dial);
	    }
	}

	@Override
	public void onStop(Context context) {
		// TODO Auto-generated method stub

	}

}
