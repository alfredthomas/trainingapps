package olga.kerApps.myfirstapp;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

public class ResizableView extends View {
	ScaleGestureDetector  mScaleDetector  = null;
	private float mScaleFactor = 1.f;
	private Paint myPaint;
	private Paint mainPaint; 
	int left = 200;
	int right = 200;
	int top = 200;
	int bottom = 200;
	Rect mainRegion  = new Rect();
	Rect leftTopRegion  = new Rect();
	Rect rightTopRegion  = new Rect();
	Rect leftBottomRegion  = new Rect();
	Rect rightBottomRegion  = new Rect();
	Rect topRegion = new Rect();
	Rect leftRegion  = new Rect();
	Rect rightRegion  = new Rect();
	Rect bottomRegion  = new Rect();
	int canvasCenterX, canvasCenterY, canvasWidth, canvasHeight;
	int touchSize = 40;
	
	
	public ResizableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        myPaint = new Paint();
        myPaint.setColor(Color.BLUE);
        
        mainPaint = new Paint();
        
        mainPaint.setColor(getResources().getColor(R.color.translucent_red));
        mScaleDetector  = new ScaleGestureDetector (this.getContext(), new ResizableGestureListener());
    }
	
	
	boolean checkSizes = false;
	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		
		// Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	       int oldLeft = left;
	       int oldTop = top;
	       int oldBottom = bottom;
	       int oldRight = right;
	       
	       right = oldBottom;
	       top = oldRight;
	       left = oldTop;
	       bottom = oldLeft;
	       
	       float oldX = mPosX;
	       mPosX = mPosY;
	       mPosY = oldX;
	       
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	int oldLeft = left;
	    	int oldTop = top;
	    	int oldBottom = bottom;
	    	int oldRight = right;
			   
	    	bottom = oldRight;			  
	    	right = oldTop;
	    	top = oldLeft;
	    	left = oldBottom;	
	    	
	    	float oldX = mPosX;
	    	mPosX = mPosY;
	    	mPosY = oldX;
	    }
	    
	    
	    checkSizes = true;
	    
	}



	@Override
	protected Parcelable onSaveInstanceState() {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
	    bundle.putParcelable("instanceState", super.onSaveInstanceState());
	    bundle.putInt("left", left);
	    bundle.putInt("right", right);
	    bundle.putInt("top", top);
	    bundle.putInt("bottom", bottom);
	    // ... save everything
	    return bundle;
	}



	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
		      Bundle bundle = (Bundle) state;
		      this.left = bundle.getInt("left");
		      this.right = bundle.getInt("right");
		      this.top = bundle.getInt("top");
		      this.bottom = bundle.getInt("bottom");
		      // ... load everything
		      state = bundle.getParcelable("instanceState");
		    }
		    super.onRestoreInstanceState(state);
	}



	@Override
	public void onDraw(Canvas canvas) {
	    super.onDraw(canvas);	    
	   
	    	    
	    
	    
	    canvasWidth = canvas.getWidth();
	    canvasHeight = canvas.getHeight();
	    canvasCenterX = canvasWidth/2 + (int)mPosX;
	    canvasCenterY = canvasHeight/2 + (int)mPosY;
	    
	    if (checkSizes) {
	    	checkSizes = false;
	    	
		    bottom = CheckBottomSize(bottom);
		    right = CheckRightSize(right);
		    left = CheckLeftSize(left);
		    top = CheckTopSize(top);
	    }
	    
	    // l , t , r , b
	    
	    int leftMain =canvasCenterX - left; 
	    int topMain = canvasCenterY - top;
	    int rightMain = canvasCenterX + right;
	    int bottomMain = canvasCenterY + bottom;
	    mainRegion.set(leftMain, topMain , rightMain, bottomMain);
	    canvas.drawRect(mainRegion, mainPaint);
	   
	    leftTopRegion.set(leftMain - touchSize, topMain -touchSize, leftMain + touchSize, topMain + touchSize);
	    canvas.drawRect(leftTopRegion, myPaint);
	    
	    leftBottomRegion.set(leftMain - touchSize, bottomMain -touchSize, leftMain + touchSize, bottomMain + touchSize);
	    canvas.drawRect(leftBottomRegion, myPaint);
	    
	    leftRegion.set(leftMain - touchSize, canvasCenterY -touchSize, leftMain + touchSize, canvasCenterY + touchSize);
	    canvas.drawRect(leftRegion, myPaint);	    
	    
	    topRegion.set(canvasCenterX - touchSize , topMain -touchSize, canvasCenterX + touchSize,topMain +touchSize);
	    canvas.drawRect(topRegion, myPaint);
	    
	    rightRegion.set(rightMain - touchSize, canvasCenterY - touchSize, rightMain + touchSize, canvasCenterY + touchSize);
	    canvas.drawRect(rightRegion, myPaint);	 
	    
	    rightTopRegion.set(rightMain - touchSize, topMain -touchSize, rightMain + touchSize, topMain + touchSize);	    	   
	    canvas.drawRect(rightTopRegion, myPaint);
	    
	    rightBottomRegion.set(rightMain - touchSize, bottomMain -touchSize, rightMain + touchSize, bottomMain +touchSize);
	    canvas.drawRect(rightBottomRegion, myPaint);
	    
	    bottomRegion.set(canvasCenterX - touchSize , bottomMain -touchSize, canvasCenterX + touchSize,bottomMain +touchSize);
	    canvas.drawRect(bottomRegion, myPaint);
	    
	}
	
	private int mActivePointerId = MotionEvent.INVALID_POINTER_ID;
	private float mLastTouchX = -1;
	private float mLastTouchY = -1;
	private ResizeDirection resizeDir = null;
	private float mPosX =0;;
	private float mPosY = 0;
	boolean moving = false;
	public enum ResizeDirection {
	    Top, Left, Right, Bottom, LeftTop, RightTop, LeftBottom, RightBottom 
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		// Let the ScaleGestureDetector inspect all events.
		//boolean handled = mScaleDetector.onTouchEvent(ev);
	             
	    final int action = MotionEventCompat.getActionMasked(ev); 
	        
	    switch (action) { 
	    case MotionEvent.ACTION_DOWN: {
	        final int pointerIndex = MotionEventCompat.getActionIndex(ev); 
	        final float x = MotionEventCompat.getX(ev, pointerIndex); 
	        final float y = MotionEventCompat.getY(ev, pointerIndex); 
	        
	        int xInt = (int)x;
	        int yInt = (int)y;
	       
	        if (topRegion.contains(xInt, yInt)) 
	        	resizeDir = ResizeDirection.Top;
	    	else if  (leftRegion.contains(xInt, yInt))
	    		resizeDir = ResizeDirection.Left;
	    	else if  (rightRegion.contains(xInt, yInt))
	    		resizeDir = ResizeDirection.Right;
	    	else if  (bottomRegion.contains(xInt, yInt))
	    		resizeDir = ResizeDirection.Bottom;
	    	else if  (leftTopRegion.contains(xInt, yInt))	    		
	    		resizeDir = ResizeDirection.LeftTop;
	    	else if  (leftBottomRegion.contains(xInt, yInt))
	    		resizeDir = ResizeDirection.LeftBottom;
	    	else if  (rightTopRegion.contains(xInt, yInt))
	    		resizeDir = ResizeDirection.RightTop;
	    	else if  (rightBottomRegion.contains(xInt, yInt))
	    		resizeDir = ResizeDirection.RightBottom;
	    	else if (mainRegion.contains(xInt, yInt)) {
	    		moving = true;
	    	}
	    		
	        		    	
	        
	        if (resizeDir != null || moving) {
	        	// Remember where we started (for dragging)
		        mLastTouchX = x;
		        mLastTouchY = y;
		        // Save the ID of this pointer (for dragging)
		        mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
	        }
	        	
	        break;
	    }
	            
	    case MotionEvent.ACTION_MOVE: {
	        
	    	 final int pointerIndex = MotionEventCompat.getActionIndex(ev); 
		        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex); 

	    	if (pointerId == mActivePointerId) {
	            // This was our active pointer going up. Choose a new
	            // active pointer and adjust accordingly.


	        	
		        final float x = MotionEventCompat.getX(ev, pointerIndex);
		        final float y = MotionEventCompat.getY(ev, pointerIndex);
		            
		        // Calculate the distance moved
		        final float dx = x - mLastTouchX;
		        final float dy = y - mLastTouchY;
		        
		        mLastTouchX = x;
		        mLastTouchY = y;
		        if (moving) {
		        	mPosX += dx;
		            mPosY += dy;	
		            
		            mPosX = CheckPosX(mPosX);
		            mPosY = CheckPosY(mPosY);
		        }
		        else {
		        	switch (resizeDir) {
		        	
			        	case Top: {
			        		int newTop = top -(int)dy;
			        		
			        		top = CheckTopSize(newTop);
			        		break;
			        	}
			        	case Left: {
			        		int newLeft = left - (int)dx;	        			        		
			        		left = CheckLeftSize(newLeft);
			        		
			        		break;
			        	}
			        	case Right: {
			        		int newRight = right + (int)dx;
			        			        		
			        		right = CheckRightSize(newRight);
			        		break;
			        	}
			        	case Bottom: {
			        		int newBottom = bottom + (int)dy;	        			        			        			        		
			        		bottom = CheckBottomSize(newBottom);
			        		break;
			        	}
			        	case LeftTop: {
			        		int newLeft = left - (int)dx;	        			        		
			        		left = CheckLeftSize(newLeft);
		        			
			        		int newTop = top -(int)dy;	        		
			        		top = CheckTopSize(newTop);
			        		
			        		break;
			        	}
			        	case LeftBottom: {
			        		int newLeft = left - (int)dx;	        			        		
			        		left = CheckLeftSize(newLeft);
			        		
			        		int newBottom = bottom + (int)dy;	        			        			        			        		
			        		bottom = CheckBottomSize(newBottom);
			        		break;
			        	}
			        	case RightTop: {
			        		int newRight = right + (int)dx;		        		
			        		right = CheckRightSize(newRight);
			        		
			        		int newTop = top -(int)dy;	        		
			        		top = CheckTopSize(newTop);
			        		
			        		break;
			        	}
			        	case RightBottom: {
			        		int newRight = right + (int)dx;		        		
			        		right = CheckRightSize(newRight);
			        		
			        		int newBottom = bottom + (int)dy;	        			        			        			        		
			        		bottom = CheckBottomSize(newBottom);
			        		break;
			        	}
		        			        
		        	}
		        }
		        invalidate();
		        
	        }
	        	
	    	break;
	    }
	    case MotionEvent.ACTION_UP: {
	        mActivePointerId = MotionEvent.INVALID_POINTER_ID;
	        resizeDir = null;
	        moving = false;
	        break;
	    }
	            
	    case MotionEvent.ACTION_CANCEL: {
	        mActivePointerId = MotionEvent.INVALID_POINTER_ID;
	        resizeDir = null;
	        moving = false;
	        break;
	    }
	        
	    case MotionEvent.ACTION_POINTER_UP: {
	            
	        
	        break;
	    }
	    }       
	    return true;
	}
	
	private float CheckPosX(float x) {
		int originalCenterX = canvasWidth/2;
		float newX = originalCenterX+x;
		if (newX - left - touchSize < 0)
			return  touchSize + left - originalCenterX;
		else if (right + newX + touchSize> canvasWidth )
			return canvasWidth - touchSize - originalCenterX - right;
		return x;
	}
	
	private float CheckPosY(float y) {
		int originalCenterY = canvasHeight/2;
		float newY = originalCenterY+y;
		if (newY - top - touchSize < 0)
			return  touchSize + top - originalCenterY;
		else if (bottom + newY + touchSize> canvasHeight )
			return canvasHeight - touchSize - originalCenterY - bottom;
		return y;
	}
	
	private int CheckBottomSize(int newBottom) {
		if (newBottom + canvasCenterY > canvasHeight - touchSize)
			newBottom = canvasHeight - canvasCenterY - touchSize;
		else if (newBottom + canvasCenterY < canvasCenterY + touchSize)
			newBottom = touchSize;	
		return newBottom;
	}
	private int CheckRightSize(int newRight) {
		if (newRight + canvasCenterX > canvasWidth - touchSize)
			newRight = canvasWidth - canvasCenterX - touchSize;
		else if (newRight + canvasCenterX < canvasCenterX + touchSize)
			newRight = touchSize;
		return newRight;
	}
	
	private int CheckLeftSize(int newLeft) {
		if (canvasCenterX - newLeft < touchSize)
			newLeft = canvasCenterX - touchSize;
		if (newLeft < touchSize)
			newLeft = touchSize;
		return newLeft;
	}
	
	private int CheckTopSize(int newTop) {
		if (canvasCenterY - newTop < touchSize)
			newTop = canvasCenterY - touchSize;
		if (newTop < touchSize)
			newTop = touchSize;
		return newTop;
	}
	
	private class ResizableGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener  {
	   
	   
	   @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        mScaleFactor *= detector.getScaleFactor();

	        // Don't let the object get too small or too large.
	        Log.i("olga", "want" + ((Float)mScaleFactor).toString());
	        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 250.0f));
	        setScaleX(mScaleFactor);
	        setScaleY(mScaleFactor);
	        Log.i("olga", ((Float)mScaleFactor).toString());
	        invalidate();
	        return true;
	    }
	}
}
