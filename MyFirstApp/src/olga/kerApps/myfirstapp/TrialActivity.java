package olga.kerApps.myfirstapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class TrialActivity extends ActionBarActivity {

	//private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trial);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.scrollView1, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trial, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/*
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		private float mScaleFactor = 1.f;
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			mScaleFactor *= detector.getScaleFactor();
		
		    // Don't let the object get too small or too large.
		    mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
		
		    invalidate();
		    return true;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ScaleGestureDetector mScaleDetector;
		private float mScaleFactor = 1.f;
		public PlaceholderFragment() {
			//mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_trial,
					container, false);
			 //View square = new View();
			// rootView.setBackgroundColor(Color.GREEN);
			 //rootView.setLayoutParams(params);
			return rootView;
		}
		
		
		

}
	
}
