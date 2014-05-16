package com.example.sschuuac4;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ObjectAnimator outside = ObjectAnimator.ofFloat(this.findViewById(R.id.outer), ImageView.ROTATION, 0.0f, 30.0f);
        outside.setRepeatCount(ObjectAnimator.INFINITE);
        outside.setDuration(5000);
        outside.addListener(new ObjectAnimatorRingListener());
        
        ObjectAnimator middle = ObjectAnimator.ofFloat(this.findViewById(R.id.middle), ImageView.ROTATION, 0f, -30.0f);
        middle.setRepeatCount(ObjectAnimator.INFINITE);
        middle.setDuration(5000);
        middle.addListener(new ObjectAnimatorRingListener());
        
        
		ObjectAnimator inside = ObjectAnimator.ofFloat(this.findViewById(R.id.inner), ImageView.ROTATION, 0.0f, 30.0f);
        inside.setRepeatCount(ObjectAnimator.INFINITE);
        inside.setDuration(5000);
        inside.addListener(new ObjectAnimatorRingListener());
        
        
        
        
		ObjectAnimator fill = ObjectAnimator.ofFloat(this.findViewById(R.id.fill), "alpha", 1.0f, 0f);
        fill.setRepeatCount(ObjectAnimator.INFINITE);
        fill.setDuration(5000);
        fill.addListener(new ObjectAnimatorFillListener());
        @SuppressWarnings("static-access")
		ObjectAnimator smiley = new ObjectAnimator().ofFloat(this.findViewById(R.id.smiley), "alpha", 0.0f, 1.0f);
        smiley.setRepeatCount(ObjectAnimator.INFINITE);
        smiley.setDuration(5000);
        smiley.addListener(new ObjectAnimatorFillListener());
        
        
        AnimatorSet set = new AnimatorSet();
        
        set.play(fill).with(outside).with(middle).with(inside).with(smiley);
        
        set.start();
        
        
        
    }
    
}
