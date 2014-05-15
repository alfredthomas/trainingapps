package com.example.sschuuac4;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.view.View;

public class ObjectAnimatorRingListener implements AnimatorListener{

	float start;
	float end;
	@Override
	public void onAnimationStart(Animator animation) {
		// TODO Auto-generated method stub
		start = ((View)((ObjectAnimator)animation).getTarget()).getRotation();
	}
	@Override
	public void onAnimationRepeat(Animator animation) {
		// TODO Auto-generated method stub
		
		end = ((View)((ObjectAnimator)animation).getTarget()).getRotation();
		((ObjectAnimator)animation).setFloatValues(end%360,((end-start)+end)%360);
		start = end;
	}
	@Override
	public void onAnimationEnd(Animator animation) {}	
	@Override
	public void onAnimationCancel(Animator animation) {}
	
}
