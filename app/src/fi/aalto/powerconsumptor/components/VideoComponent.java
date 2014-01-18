package fi.aalto.powerconsumptor.components;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import fi.aalto.powerconsumptor.Component;
import fi.aalto.powerconsumptor.R;


public class VideoComponent extends Component {

	public VideoComponent(Context context, String file) {
		super(context, "Video");
		this.file = file;
	}
	
	private String file;
	private VideoView vv;
	private Uri videoUri;
	
	@Override
	public View getView(Context context, ViewGroup parent) {
		vv = new VideoView(context);
		vv.setId(1);
		vv.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.setLooping(true);
			}
		});
		
		Uri u = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + file);
		vv.setVideoURI(u);
		vv.setLayoutParams(new RelativeLayout.LayoutParams(640, 360));
		return vv;
	}
	
	/* user methods */
	
	public void setVideoURI(Uri uri) {
		videoUri = uri;
		vv.setVideoURI(uri);
	}

	@Override
	public void onStart(Context context) {
		vv.seekTo(0);
		vv.start();
	}

	@Override
	public void onStop(Context context) {
		vv.stopPlayback();
	}

}
