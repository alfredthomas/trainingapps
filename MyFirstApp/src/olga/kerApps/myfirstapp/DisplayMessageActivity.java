package olga.kerApps.myfirstapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends ActionBarActivity {

	private String message = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// getting my message
		Intent intent = getIntent();
		message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		// Create the text view
	    TextView textView = new TextView(this);
	    textView.setTextSize(40);
	    textView.setText("Hello my friend, " + message + "!");

	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(textView);
		
//		


	}
	@Override
	protected void onStop() {
	    super.onStop();  // Always call the superclass method first
	    
	   
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_message, menu);
        return true;
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch (id) {
			case R.id.action_search:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("you pressed search")
				.setTitle("Search Dialog");
				
				AlertDialog dialog = builder.create();
				dialog.show();
				return true;
			case android.R.id.home:
				 Context context = this;
				    SharedPreferences sharedPref = context.getSharedPreferences(
				            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
				    SharedPreferences.Editor editor = sharedPref.edit();
				    editor.putString("userName", message);
				    editor.commit();
		        NavUtils.navigateUpFromSameTask(this);
		        return true;
			case R.id.action_settings:
				return true;
		}
		
		
		return super.onOptionsItemSelected(item);
	}

	

}
