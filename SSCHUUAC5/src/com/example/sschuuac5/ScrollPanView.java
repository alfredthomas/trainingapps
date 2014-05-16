package com.example.sschuuac5;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class ScrollPanView extends ImageView {
	
	final float minZoom = 0.5f;
	final float maxZoom = 3.0f;
	float scale = 1f;
	int posX=0;
	int posY=0;

	
	Paint borderPaint;
	Paint backgroundPaint;
	ScaleGestureDetector scaleDet;
	
	public ScrollPanView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		scaleDet = new ScaleGestureDetector(context, new ScaleListener());
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		if(scaleDet.onTouchEvent(event))
			{
			this.setScaleX(scale);
			this.setScaleY(scale);
			};
		
//		switch (event.getAction()&MotionEvent.ACTION_MASK)
//		{
//		case MotionEvent.ACTION_DOWN:
//			Log.d("Touch event", "Action down @ "+ event.getX()+", "+event.getY());
//		case MotionEvent.ACTION_UP:
//			Log.d("Touch event", "Action up @ "+ event.getX()+", "+event.getY());
//		case MotionEvent.ACTION_MOVE:
//			Log.d("Touch event", "Action move @ "+ event.getX()+", "+event.getY());
//		
//			/*too sensitive
//		case MotionEvent.ACTION_POINTER_DOWN:
//			Log.d("Touch event", "Action pointer down @ "+ event.getX()+", "+event.getY());
//		case MotionEvent.ACTION_POINTER_UP:
//			Log.d("Touch event", "Action pointer up @ "+ event.getX()+", "+event.getY());
//		*/		
//		}
		
		
		
		return true;
	}
	public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			
			scale *= detector.getScaleFactor();
			Log.d("Scale Listener ", ""+scale+"" + detector.getPreviousSpan()+"" + detector.getCurrentSpan());
			scale = Math.min(scale, maxZoom);
			scale = Math.max(scale, minZoom);
			
			return true;
		}
		
		
	}
}
