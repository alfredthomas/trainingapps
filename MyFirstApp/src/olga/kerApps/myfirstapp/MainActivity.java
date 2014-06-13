package olga.kerApps.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	public final static String EXTRA_MESSAGE = "olga.kerApps.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {        	
        	
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
            
            
        }
    }
    @Override 
    protected void onStop()
    {
    	super.onStop();
    	SharedPreferences sharedPref = getSharedPreferences(
	            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPref.edit();
    	editor.remove("userName");
    	editor.commit();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	/*EditText editText = (EditText) findViewById(R.id.edit_message);
    	savedInstanceState.putString("userName", editText.getText().toString());
    	*/
        
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        
        /*EditText editText = (EditText) findViewById(R.id.edit_message);
        
        // Restore state members from saved instance
        String name = savedInstanceState.getString("userName");
        editText.setText(name);*/
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
        	
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState)
        {        	
        	super.onActivityCreated(savedInstanceState);
        	if (savedInstanceState == null)
        	{
        		EditText editText = (EditText) getActivity().findViewById(R.id.edit_message);
            	
            	SharedPreferences sharedPref = getActivity().getSharedPreferences(
    		            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            	String savedValue = sharedPref.getString("userName", "");
            	if (editText != null && savedValue != null)
            		editText.setText(savedValue);
        		
        	}
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
        
       
        
        
    }
    
    public void sendMessage(View view){
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
    public void changeToTrialActivity(View view){
    	Intent intent = new Intent(this, TrialActivity.class);    	
    	startActivity(intent);
    }
    
    public void changeToHorizontalScrollCust(View view){
    	Intent intent = new Intent(this, HorizontalScrollCust.class);    	
    	startActivity(intent);
    }
    
    public void changeToResizableActivity(View view){
    	Intent intent = new Intent(this, ResizableActivity.class);    	
    	startActivity(intent);
    }
    
    public void changeToUsMapActivity(View view){
    	Intent intent = new Intent(this, UsMapActivity.class);    	
    	startActivity(intent);
    }
    
    public void changeToWebServiceActivity(View view){
    	Intent intent = new Intent(this, WeightConvtWebSericeActivity.class);    	
    	startActivity(intent);
    }
    
    public void changeToGoogleDriveActivity(View view){
    	Intent intent = new Intent(this, GoogleDriveActivity.class);    	
    	startActivity(intent);
    }
    
    public void changeToGoogleDriveCustomActivity(View view){
    	Intent intent = new Intent(this, GoogleDriveCustomActivity.class);    	
    	startActivity(intent);
    }
   
    private String RPlusBaseURL =  "http://reportplus.test.infragistics.local/rplusdemo";
    public void reportPlusGetPermisionBtnOnClick(View view) {
    	
        
        // Use AsyncTask execute Method To Prevent ANR Problem
        new GetPermissionTask().execute(this.RPlusBaseURL);
    }
    
    private String RPlusProStagingUrl = "https://xpluscloudstaging.infragistics.com/";
    public void reportPlusProRepositoryCommunication(View view) {
    	
        PostAsyncTask loginTask = new PostAsyncTask();
        loginTask.setServerUrl(RPlusProStagingUrl);
        
        
        // Use AsyncTask execute Method To Prevent ANR Problem
        loginTask.execute("api/loginService/login", "test11@guerrillamail.com", "1234", "ReportPlus");
    }

}
