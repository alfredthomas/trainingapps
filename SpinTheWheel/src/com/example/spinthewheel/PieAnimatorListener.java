package com.example.spinthewheel;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.util.Log;
import android.view.View;

public class PieAnimatorListener implements AnimatorListener{

	float start = 0;
	float end = 0;
	int dir = 0;
	@Override
	public void onAnimationStart(Animator animation) {
		// TODO Auto-generated method stub
		if(start ==0)
		start = ((View)((ObjectAnimator)animation).getTarget()).getRotation();
	}
	@Override
	public void onAnimationRepeat(Animator animation) {
		// TODO Auto-generated method stub
		
		end = ((View)((ObjectAnimator)animation).getTarget()).getRotation();
		end = Math.round(end);
		if(end>360 ||end <-360)
			end = end%360;
		
		((View)((ObjectAnimator)animation).getTarget()).setRotation(end);
		
		Log.d("animation"," dir = "+dir+" rotate ="+((View)((ObjectAnimator)animation).getTarget()).getRotation()+ " start = "+start+" end = "+end);
		((ObjectAnimator)animation).setFloatValues(end%360,((end-start)+end)%360);
		if (end == 365)
			end = 0;
		start = end;
//		long duration = animation.getDuration();
//		animation.setDuration((long) (duration*1.05));
	}
	@Override
	public void onAnimationEnd(Animator animation) {
		//start = end;
	}	
	@Override
	public void onAnimationCancel(Animator animation) {}
	
}
