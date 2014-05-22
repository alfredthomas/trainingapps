package com.example.sschuuac5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.graphics.drawable.BitmapDrawable;

public class ImageViewer extends ImageView {

	private int posX;
	private int posY;
	private float scale = 1.0f;
	
	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	private final String TAG= "ImageViewer";
	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	String savedItemClicked;
	Paint paint;
	ScaleGestureDetector gestDet;
	
	
	public ImageViewer(Context context) {
		super(context);
		gestDet = new ScaleGestureDetector(context, new ScaleListener());
		scale = 1.0f;
		posX = 0;
		posY = 0;
		paint = new Paint();
	}
	/* (non-Javadoc)
	 * @see android.widget.ImageView#setScaleType(android.widget.ImageView.ScaleType)
	 */
	@Override
	public void setScaleType(ScaleType scaleType) {
		// TODO Auto-generated method stub
		super.setScaleType(scaleType);
	}
	
	@Override
	public ScaleType getScaleType() {
		// TODO Auto-generated method stub
		return super.getScaleType();
	}
	@Override
	public void draw(Canvas canvas) {
	    super.draw(canvas);
	    canvas.drawRect(0, 0, getWidth() - 1, getHeight() - 1, paint);
	    
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		  canvas.drawRect(0, 0, getWidth() - 1, getHeight() - 1, paint);
		    if (this.getDrawable() != null) {
		        canvas.save();
		        canvas.translate(posX, posY);

		        
		        matrix.postScale(scale, scale, pivotPointX,
		                pivotPointY);
		        // canvas.setMatrix(matrix);

		        canvas.drawBitmap(
		                ((BitmapDrawable) this.getDrawable()).getBitmap(), matrix,
		                null);

		        // this.getDrawable().draw(canvas);
		        canvas.restore();
		    }
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//gestDet.onTouchEvent(event);
		
		//final int action = event.getAction();
		
		   // Handle touch events here...
	    switch (event.getAction() & MotionEvent.ACTION_MASK) {
	    case MotionEvent.ACTION_DOWN:
	        savedMatrix.set(matrix);
	        start.set(event.getX(), event.getY());
	        Log.d(TAG, "mode=DRAG");
	        mode = DRAG;
	        break;
	    case MotionEvent.ACTION_POINTER_DOWN:
	        oldDist = spacing(event);
	        Log.d(TAG, "oldDist=" + oldDist);
	        if (oldDist > 10f) {
	            savedMatrix.set(matrix);
	            midPoint(mid, event);
	            mode = ZOOM;
	            Log.d(TAG, "mode=ZOOM");
	        }
	        break;
	    case MotionEvent.ACTION_UP:
	    case MotionEvent.ACTION_POINTER_UP:
	        mode = NONE;
	        Log.d(TAG, "mode=NONE");
	        break;
	    case MotionEvent.ACTION_MOVE:
	        if (mode == DRAG) {
	            // ...
	            matrix.set(savedMatrix);
	            matrix.postTranslate(event.getX() - start.x, event.getY()
	                    - start.y);
	        } else if (mode == ZOOM) {
	            float newDist = spacing(event);
	            Log.d(TAG, "newDist=" + newDist);
	            if (newDist > 10f) {
	                matrix.set(savedMatrix);
	                float scale = newDist / oldDist;
	                matrix.postScale(scale, scale, mid.x, mid.y);
	            }
	        }
	        break;
	    }

	    this.setImageMatrix(matrix);
	    return true;
	}
	/** Determine the space between the first two fingers */
	private float spacing(MotionEvent event) {
	    float x = event.getX(0) - event.getX(1);
	    float y = event.getY(0) - event.getY(1);
	    return (float) Math.sqrt(x * x + y * y);
	}

	/** Calculate the mid point of the first two fingers */
	private void midPoint(PointF point, MotionEvent event) {
	    float x = event.getX(0) + event.getX(1);
	    float y = event.getY(0) + event.getY(1);
	    point.set(x / 2, y / 2);
	}
	@Override
	public void setImageDrawable(Drawable drawable) {
	    // Constrain to given size but keep aspect ratio
	    int width = drawable.getIntrinsicWidth();
	    int height = drawable.getIntrinsicHeight();
	    posX = 0;
	    posY = 0;

	    scale = Math.min(((float) getLayoutParams().width)
	            / width, ((float) getLayoutParams().height)
	            / height);
	    pivotPointX = (((float) getLayoutParams().width) - (int) (width * scale)) / 2;
	    pivotPointY = (((float) getLayoutParams().height) - (int) (height * scale)) / 2;
	    super.setImageDrawable(drawable);
	}

	float pivotPointX = 0f;
	float pivotPointY = 0f;

	private class ScaleListener extends
	        ScaleGestureDetector.SimpleOnScaleGestureListener {

	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        scale *= detector.getScaleFactor();

	        pivotPointX = detector.getFocusX();
	        pivotPointY = detector.getFocusY();

	        Log.d(TAG, "mScaleFactor " + scale);
	        Log.d(TAG, "pivotPointY " + pivotPointY + ", pivotPointX= "
	                + pivotPointX);
	        scale = Math.max(0.05f, scale);

	        invalidate();
	        return true;
	    }
	

}
	}
