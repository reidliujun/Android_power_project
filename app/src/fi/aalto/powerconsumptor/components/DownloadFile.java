package fi.aalto.powerconsumptor.components;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fi.aalto.powerconsumptor.Component;
import fi.aalto.powerconsumptor.NetworkUtils;
import fi.aalto.powerconsumptor.NetworkUtils.IDownloadAction;
import fi.aalto.powerconsumptor.UIUtils;

public class DownloadFile extends Component {

	public DownloadFile(Context context, String fileName) {
		super(context, "File Downloader");
		file = fileName;
	}

	private String file;
	private TextView text;
	private boolean stop;
	private int times = 0;
	
	@Override
	public View getView(Context context, ViewGroup parent) {
		text = new TextView(context);
		text.setText("Ready");
		return text;
	}

	@Override
	public void onStart(Context context) {
		setStopped(false);
		text.setText("Downloading..");
		times = 0;
		new Thread("File download " + file) {
			public void run() {
				while (!isStopped()) {
					NetworkUtils.downloadFile(new IDownloadAction() {
						
						@Override
						public String getRemoteFileName() {
							return file;
						}
						
						@Override
						public String getLocalFileName() {
							return null;
						}
						
						@Override
						public boolean doDownload(InputStream is) throws IOException {
							byte[] buffer = new byte[1024*1024];
							int read = 0;
							int bytes = 0;
							int total = 0;
							long start = System.nanoTime();
							long time = 0;
							while (!isStopped() && (read = is.read(buffer)) != -1) {
								bytes += read;
								total += read;
								time = System.nanoTime() - start;
								
								if (time >= 1e9) {
									final double th = bytes / (time / 1e9d) / 1024d;
									final int t = total;
									UIUtils.runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											text.setText(
													String.format("Downloading  %4.2f kBps %.2f KB (%d)", 
													th, (t / 1024d), times));
										}
									});
									bytes = 0;
									time = 0;
									start = System.nanoTime();
								}
							}
							return true;
						}
					});
					times++;
					UIUtils.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							text.setText("Downloading... (" + times + ")");
						}
					});
				}
			}
		}.start();
		
	}
	
	private synchronized boolean isStopped() {
		return stop;
	}
	
	private synchronized void setStopped(boolean stopped) {
		stop = stopped;
	}

	@Override
	public void onStop(Context context) {
		setStopped(true);
		text.setText("Stopped");
	}

}
