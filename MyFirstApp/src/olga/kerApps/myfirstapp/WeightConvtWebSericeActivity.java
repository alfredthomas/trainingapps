package olga.kerApps.myfirstapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WeightConvtWebSericeActivity extends Activity {

	String namespace = "http://www.webserviceX.NET/";
    private String url = "http://www.webservicex.net/ConvertWeight.asmx";
     
    String[] units = { "Grains", "Scruples", "Carats", "Grams","Pennyweight","DramAvoir","DramApoth",
    			"OuncesAvoir","OuncesTroyApoth","Poundals","PoundsTroy","PoundsAvoir","Kilograms","Stones",
    			"QuarterUS","Slugs","weight100UScwt","ShortTons","MetricTonsTonne","LongTons" }; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
      
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_convt_web_serice);  
        
        MyOnClickListner clicker = new MyOnClickListner();
        
        final Button GetServerData = (Button) findViewById(R.id.GetServerData);
        //GetServerData.setOnClickListener(clicker);
        
        final TextView weightTxt = (TextView) findViewById(R.id.weightTxt);    	
        weightTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        	public void onFocusChange(View v, boolean hasFocus){

                if(v.getId() == R.id.weightTxt && !hasFocus) {

                    InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }
            }
        
        });
        
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, units);
      
		
        Spinner fromUnitsSpinner = (Spinner) findViewById(R.id.fromUnitsSpinner);
        fromUnitsSpinner.setAdapter(adapter);
      //  fromUnitsSpinner.setOnTouchListener(clicker);
        //fromUnitsSpinner.setOnItemClickListener(clicker);
        
        Spinner toUnitsSpinner = (Spinner) findViewById(R.id.toUnitsSpinner);
        toUnitsSpinner.setAdapter(adapter);
        //toUnitsSpinner.setOnItemClickListener(clicker);
        
        GetServerData.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                
            	InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
                
            	new WebServiceCallTask().execute();
               /* webserviceResponse.setText("Requesting to server .....");
                
               //Create Webservice class object
                olga.kerApps.myfirstapp.WebserviceCall com = new olga.kerApps.myfirstapp.WebserviceCall(); 
                
               // Initialize variables
               String weight   = "18000";
               String fromUnit = "Grams";
               String toUnit   = "Kilograms";
                
               //Call Webservice class method and pass values and get response
               String aResponse = com.getConvertedWeight("ConvertWeight", weight, fromUnit, toUnit);   
                
               //Alert message to show webservice response
               Toast.makeText(getApplicationContext(), weight+" Gram= "+aResponse+" Kilograms", 
                  Toast.LENGTH_LONG).show();
                   
               Log.i("AndroidExampleOutput", "----"+aResponse);
                
               webserviceResponse.setText("Response : "+aResponse);*/
            }
        });
        
       
           
          
    }
    
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	private AlertDialog dialog;
    public void ShowWaitMessage() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Please wait...")
		.setTitle("Contacting Server");
		
		dialog = builder.create();
		dialog.show();
    }
    
    public void HideWaitMessage () {
    	if (dialog != null)
    		dialog.hide();
    }
    
    
    //private class WebServiceCallTask  extends AsyncTask<String, Void, Void> {
    private class WebServiceCallTask  extends AsyncTask<Void, Void, Void> {
    	String aResponse = "";
    	String weight   = "";
        String fromUnit = "Grams";
        String toUnit   = "Kilograms";
    	protected void onPreExecute() {
    		 final TextView weightTxt = (TextView) findViewById(R.id.weightTxt);    		
             // Initialize variables
             weight   = weightTxt.getText().toString();
             
             Spinner fromUnitsSpinner = (Spinner) findViewById(R.id.fromUnitsSpinner);
             Object spinnerSelection =fromUnitsSpinner.getSelectedItem();
             if (spinnerSelection != null)
            	 fromUnit = spinnerSelection.toString();
             
             Spinner toUnitsSpinner = (Spinner) findViewById(R.id.toUnitsSpinner);
             spinnerSelection =toUnitsSpinner.getSelectedItem();
             if (spinnerSelection != null)
            	 toUnit = spinnerSelection.toString();
             
             
    		ShowWaitMessage();
			
    	}
    	
    	 protected Void doInBackground(Void... unused) {
    		 olga.kerApps.myfirstapp.WebserviceCall com = new olga.kerApps.myfirstapp.WebserviceCall(); 
             
    		
              
             //Call Webservice class method and pass values and get response
             aResponse = com.getConvertedWeight("ConvertWeight", weight, fromUnit, toUnit);   
              
    		 return null; 
    	 }
    	 
    	 protected void onPostExecute(Void unused) {
    		 HideWaitMessage();
    		 final TextView webserviceResponse = (TextView) findViewById(R.id.webserviceResponse);
    		 webserviceResponse.setText("Response : "+aResponse);
    	 }
    }
    
//    private class MyOnClickListner implements OnTouchListener {
//    	public boolean onTouch(View v , MotionEvent event) {
//    		if (v != null && !(v instanceof EditText)) {
//    			InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//    	    }
//    		return super.onTouch(v, event);
//    	}
//    }
    
    
    
  private class MyOnClickListner implements AdapterView.OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        
		
	}
  	
  }
  
   
}
