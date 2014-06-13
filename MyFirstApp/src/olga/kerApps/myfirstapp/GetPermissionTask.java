package olga.kerApps.myfirstapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class GetPermissionTask extends AsyncTask<String, Void, Void> {
	
	//"http://reportplus.test.infragistics.local/rplusdemo
	//"http://reportplus.test.infragistics.local/rplusdemo/api/repository/Get?id=Home%2FTest%2F
    private String _url = "/api/repository/Get?id=Home%2FTest%2F";
     
    private final HttpClient Client = new DefaultHttpClient();
    private String Content;
    private String Error = null;
    
    String data =""; 
    //TextView uiUpdate = (TextView) findViewById(R.id.output);
    //TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
    int sizeData = 0;  
    
	protected Void doInBackground(String... urls) {
		 BufferedReader reader=null;
		    
         // Send data 
        try
        { 
           
        	//////////////////
        	
        	HttpClient httpClient = new DefaultHttpClient();
        	HttpGet httpGet = new HttpGet(urls[0] + _url);
        	///// PUT ACTUAL PASSWORD
        	httpGet.addHeader(BasicScheme.authenticate(
        	 new UsernamePasswordCredentials("Infragistics\\okerchentseva", "password"),
        	 "UTF-8", false));

        	HttpResponse httpResponse = httpClient.execute(httpGet);
        	HttpEntity responseEntity = httpResponse.getEntity();
        	///////////////////////
        	
           // Defined URL  where to send data
           URL url = new URL(urls[0] + _url);
              
          // Send POST data request
/*
          URLConnection conn = url.openConnection(); 
          conn.setDoOutput(true); 
          OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
          wr.write( data ); 
          wr.flush(); 
       
          // Get the server response 
            */
          reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
          StringBuilder sb = new StringBuilder();
          String line = null;
         
            // Read Server Response
            while((line = reader.readLine()) != null)
                {
                       // Append server response in string
                       sb.append(line + "");
                }
             
            // Append Server Response To Content String 
           Content = sb.toString();
        }
        catch(Exception ex)
        {
            Error = ex.getMessage();
        }
        finally
        {
            try
            {
  
                reader.close();
            }

            catch(Exception ex) {}
        }
     
        /*****************************************************/
        return null;
		
	}
	
	protected void onPostExecute(Void result) {
		 /****************** Start Parse Response JSON Data *************/
        
        String OutputData = "";
        JSONObject jsonResponse;
               
        try {
               
             /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
             jsonResponse = new JSONObject(Content);
               
             /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
             /*******  Returns null otherwise.  *******/
             JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
               
             /*********** Process each JSON Node ************/

             int lengthJsonArr = jsonMainNode.length();  

             for(int i=0; i < lengthJsonArr; i++) 
             {
                 /****** Get Object for each JSON node.***********/
                 JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                   
                 /******* Fetch node values **********/
                 String name       = jsonChildNode.optString("name").toString();
                 String number     = jsonChildNode.optString("number").toString();
                 String date_added = jsonChildNode.optString("date_added").toString();
                   
                 
                 OutputData += " Name           : "+ name +" "
                             + "Number      : "+ number +" "
                             + "Time                : "+ date_added +" " 
                             +"--------------------------------------------------"
                             + "";
                 
                  
            }
         /****************** End Parse Response JSON Data *************/    
              
             //Show Parsed Output on screen (activity)
             //jsonParsed.setText( OutputData );
              
               
         } catch (JSONException e) {
   
             e.printStackTrace();
         }
    }
}
