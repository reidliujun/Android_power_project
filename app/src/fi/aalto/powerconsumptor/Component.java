package fi.aalto.powerconsumptor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class Component extends RelativeLayout {


	public Component(Context context, String title) {
		super(context);
		init(title);
	}
	
	private ViewGroup header;
	private TextView tvTitle;
	private CheckBox cbEnabled;
	private View content;
	
	private void init(String title) {
		header = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.component, null);
		tvTitle = (TextView)header.findViewById(R.id.title);
		tvTitle.setText(title);
		cbEnabled = (CheckBox)header.findViewById(R.id.checkBox);
		cbEnabled.setChecked(false);
		cbEnabled.setOnCheckedChangeListener(cbListener);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_TOP);
		addView(header, params);
	}
	
	
	private OnCheckedChangeListener cbListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				if (content == null) {
					content = getView(getContext(), Component.this);
					if (content != null) {
						RelativeLayout.LayoutParams params;
						if (content.getLayoutParams() != null) {
							params = new RelativeLayout.LayoutParams(content.getLayoutParams());
						} else {
							params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
						}
						params.addRule(RelativeLayout.BELOW, header.getId());
						addView(content, params);
					}
				}
				onStart(getContext());
			} else {
				onStop(getContext());
				removeView(content);
				content = null;
			}
		}
	};
	
	public void setEnabled(boolean enabled) {
		cbEnabled.setChecked(enabled);
	}
	
	public abstract View getView(Context context, ViewGroup parent);
	
	public abstract void onStart(Context context);
	
	public abstract void onStop(Context context);

}
