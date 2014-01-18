package fi.aalto.powerconsumptor.components;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import fi.aalto.powerconsumptor.Component;

public class CPUComponent extends Component {

	public CPUComponent(Context context) {
		super(context, "CPU");
		// TODO Auto-generated constructor stub
	}

	class MyView extends ImageView {

		public MyView(Context context) {
			super(context);
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
		}
		Paint paint = new Paint();
		Random rand = new Random();
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			for (int i = 0; i < 1000; i++) {
				float cx = rand.nextInt(getMeasuredWidth());
				float cy = rand.nextInt(getMeasuredHeight());
				float radius = rand.nextInt(getMeasuredHeight());
				paint.setColor(rand.nextInt());
				canvas.drawCircle(cx, cy, radius, paint);
			}
		}
	}
	
	MyView view;
	
	@Override
	public View getView(Context context, ViewGroup parent) {
		view = new MyView(context);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 100));
		return view;
	}

	boolean stop;
	
	@Override
	public void onStart(Context context) {
		// TODO Auto-generated method stub
		stop = false;
		new Thread(){
			public void run() {
				while (!stop) {
					Math.sqrt(Math.sin(Math.cos(new Random().nextDouble())));
					view.postInvalidate();
				}
			}
		}.start();
		new Thread(){
			public void run() {
				while (!stop) {
					Math.sqrt(Math.sin(Math.cos(new Random().nextDouble())));
					//view.postInvalidate();
				}
			}
		}.start();
	}

	@Override
	public void onStop(Context context) {
		// TODO Auto-generated method stub
		stop = true;
	}

}
