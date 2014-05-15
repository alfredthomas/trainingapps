package com.example.sschuuac4;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.view.View;

public class ObjectAnimatorFillListener implements AnimatorListener{

	float start;
	float end;
	@Override
	public void onAnimationStart(Animator animation) {
		// TODO Auto-generated method stub
		start = ((View)((ObjectAnimator)animation).getTarget()).getAlpha();
		end = 1-start;
	}
	@Override
	public void onAnimationRepeat(Animator animation) {
		// TODO Auto-generated method stub
		float temp = start;
		start = end;
		end = temp;
		((ObjectAnimator)animation).setFloatValues(start,end);
		
	}
	@Override
	public void onAnimationEnd(Animator animation) {}	
	@Override
	public void onAnimationCancel(Animator animation) {}
	
}
