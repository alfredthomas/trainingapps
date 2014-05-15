package com.example.sschuuac4;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ObjectAnimator outside = new ObjectAnimator().ofFloat(this.findViewById(R.id.outer), ImageView.ROTATION, 0.0f, 30.0f);
        outside.setRepeatCount(outside.INFINITE);
        outside.setDuration(5000);
        outside.addListener(new ObjectAnimatorRingListener());
        
        ObjectAnimator middle = new ObjectAnimator().ofFloat(this.findViewById(R.id.middle), ImageView.ROTATION, 0f, -30.0f);
        middle.setRepeatCount(outside.INFINITE);
        middle.setDuration(5000);
        middle.addListener(new ObjectAnimatorRingListener());
        
        ObjectAnimator inside = new ObjectAnimator().ofFloat(this.findViewById(R.id.inner), ImageView.ROTATION, 0.0f, 30.0f);
        inside.setRepeatCount(outside.INFINITE);
        inside.setDuration(5000);
        inside.addListener(new ObjectAnimatorRingListener());
        
        
        
        @SuppressWarnings("static-access")
		ObjectAnimator fill = new ObjectAnimator().ofFloat(this.findViewById(R.id.fill), "alpha", 1.0f, 0f);
        fill.setRepeatCount(fill.INFINITE);
        fill.setDuration(5000);
        fill.addListener(new ObjectAnimatorFillListener());
        @SuppressWarnings("static-access")
		ObjectAnimator smiley = new ObjectAnimator().ofFloat(this.findViewById(R.id.smiley), "alpha", 0.0f, 1.0f);
        smiley.setRepeatCount(smiley.INFINITE);
        smiley.setDuration(5000);
        smiley.addListener(new ObjectAnimatorFillListener());
        
        
        AnimatorSet set = new AnimatorSet();
        
        set.play(fill).with(outside).with(middle).with(inside).with(smiley);
        
        set.start();
        
        
        
    }
    
}
