package com.example.sschuuac8;

import java.util.List;


import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearlayout);
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);
        for(PackageInfo p:packages)
        {
        	final String name = p.packageName;
        	if(p.packageName.startsWith("com.example.") && !p.packageName.endsWith("8"))
        	{
        		String title= (String)p.applicationInfo.loadLabel(getPackageManager());
        		String desc= (String)p.applicationInfo.loadDescription(getPackageManager());
        		Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
        		LinearLayout sample= new LinearLayout(this);
        		sample.setOrientation(LinearLayout.VERTICAL);
        		
        		ImageView iv = new ImageView(this);
        		iv.setImageDrawable(icon);
        		
        		TextView text = new TextView(this);
        		text.setText(desc);
        		Button button = new Button(this);
        		button.setText(title);
        		button.setOnClickListener(new OnClickListener()  {
					@Override
					public void onClick(View v) {
						startActivity(getPackageManager().getLaunchIntentForPackage(name));
					}
				});
        		sample.addView(iv);
        		sample.addView(text);
        		sample.addView(button);
                ll.addView(sample);
        	}
        }
        
       	
        //startActivity(intent);
        //        intent.setPackage("com.example.sschuuac1");
        
    }
}
