package com.example.spinthewheel;

import android.R.anim;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
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
//    private static final int SWIPE_MIN_DISTANCE = 120;
//    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private class GestureListener extends SimpleOnGestureListener {
        int currNum = 0;
        int prevNum = 0;
        
        int[] values = new int[]{600,100,200,300,400,500,300,200,100,400,300,400,100,700,-2,100,300,200,-1,100,500,400,300,200};
    	int panelAngle = (360/values.length);
    	@Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    		
        	float maxVelocity =velocityY/8;
        	
        	int completePanels = (int)(maxVelocity/panelAngle);
        	int dir = e1.getY()< e2.getY()? 1:-1;
        	
        	int delta = completePanels % values.length;

        	      		
        	//wrap around 0
        	if(currNum+delta<0)
        		currNum = values.length + currNum+delta;
        	//wrap around 1
        	else if(currNum+delta>=values.length)
        		currNum = (currNum+delta)-values.length;
        	else
        		currNum = currNum + delta;
        	
        	int completeSpins = (int) Math.abs(Math.floor(completePanels/values.length));
        	int remainingPanels = Math.abs(completePanels%values.length);
        	
        	Log.d("wheel_fling", " currNum = "+currNum+" prevNum = "+prevNum+" Complete Spins = "+completeSpins+" Remaining Panels = "+remainingPanels+" Value = "+values[currNum]);
        	
        	((TextView)findViewById(R.id.values)).setText(""+values[currNum]); 
        	
        	int prevAngle = prevNum*panelAngle;
        	
        	ImageView wheel = (ImageView) findViewById(R.id.wheel);
        	ObjectAnimator fullSpin = null;
        	if(completeSpins>0){	
//        		fullSpin=  new ObjectAnimator().ofFloat(wheel, "rotation", prevAngle, dir * (prevAngle+360));
        		fullSpin=  new ObjectAnimator().ofFloat(wheel, "rotation", prevAngle, prevAngle+(dir * 360));
    		    fullSpin.setInterpolator(new DecelerateInterpolator());
        		fullSpin.setDuration((long)1600);
        		fullSpin.setRepeatCount(completeSpins);
	        	fullSpin.start();
        		
        	}
        	
        	ObjectAnimator partialSpin =null;
        	if(remainingPanels>0){
        	 partialSpin= new ObjectAnimator().ofFloat(wheel, "rotation", prevAngle, (dir *panelAngle)+prevAngle);
	        	partialSpin.setDuration((long)300);
	        	partialSpin.setRepeatCount(remainingPanels);
	        	PieAnimatorListener pieListener = new PieAnimatorListener();
	        	pieListener.dir = dir;
	        	partialSpin.addListener(pieListener);
//	        	spin.start();
        	}
	        	AnimatorSet set = new AnimatorSet();
	            
	        	if (fullSpin!=null && partialSpin !=null)
	        		set.play(fullSpin).before(partialSpin);
	        	else if (fullSpin==null && partialSpin!=null)
	        	{
	        		set.play(partialSpin);
	        	}
	        	else if (fullSpin!=null && partialSpin==null)
	        	{
	        		set.play(fullSpin);
	        	}
	        	else{
	        		//do nothing
	        	}
	            
	            if (set.getChildAnimations() != null)
	            set.start();
        
	        	prevNum = currNum;
            return true;
        }
    }
}


