package com.example.spinthewheel;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class WheelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        
        final ImageView wheel = (ImageView) findViewById(R.id.wheel);
        GestureListener gl = new GestureListener();
        final GestureDetector gdt = new GestureDetector(this, gl);
        
        wheel.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            }
            
        });
        



        
    }
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private class GestureListener extends SimpleOnGestureListener {
        int currNum = 0;
        int[] values = new int[]{600,100,200,300,400,500,300,200,100,400,300,400,100,700,-2,100,300,200,-1,100,500,400,300,200};
    	
    	@Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        	ObjectAnimator spin;
//        	if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                Log.d("wheel_fling", " right to left Vx = "+velocityX+" Vy = "+velocityY);
//            	spin = new ObjectAnimator().ofFloat(findViewById(R.id.wheel), ImageView.ROTATION, 0f, Math.max(velocityX, velocityY));
//            	spin.setDuration((long)(Math.abs(Math.max(velocityX, velocityY))%360));
//            	spin.start();
//                return false; // Right to left
//            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//            	Log.d("wheel_fling", " left to right Vx = "+velocityX+" Vy = "+velocityY);
//            	return false; // Left to right
//            }
//
//            if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//            	Log.d("wheel_fling", " bottom to top Vx = "+velocityX+" Vy = "+velocityY);
//            	return false; // Bottom to top
//            }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//            	Log.d("wheel_fling", " top to bottom Vx = "+velocityX+" Vy = "+velocityY);
//                return false; // Top to bottom
//            }
        	float maxVelocity =Math.abs(velocityX) > Math.abs(velocityX)? velocityX:velocityY;
//        	float maxVelocity =velocityX* velocityY;
        	float maxTime = (Math.abs(maxVelocity)/360)*100;
        	int spinDiff = (int) ((maxVelocity%360)/(360/values.length));
        	spinDiff =maxVelocity>=0? spinDiff:-1*spinDiff; 
        	if(currNum+spinDiff<0)
        		currNum = values.length-1 - Math.abs(currNum+spinDiff);
        	else if(currNum+spinDiff>=values.length)
        		currNum = (currNum+spinDiff)%values.length;
        	else
        		currNum = currNum + spinDiff;
        	Log.d("wheel_fling", " Max Velocity = "+maxVelocity+" Spin Time = "+maxTime+" Value = "+currNum);
        	((TextView)findViewById(R.id.values)).setText(""+values[currNum]); 
        	spin = new ObjectAnimator().ofFloat(findViewById(R.id.wheel), ImageView.ROTATION, 0f,maxVelocity );
        	spin.setDuration((long)maxTime);
        	spin.start();
            return true;
        }
    }
}


