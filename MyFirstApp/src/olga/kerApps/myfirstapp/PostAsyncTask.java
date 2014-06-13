package olga.kerApps.myfirstapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class PostAsyncTask extends AsyncTask<String, Void, Void> {
	
	public void setServerUrl(String url) {
		if (!url.endsWith("/"))
			url += "/";
		this._url = url;
		
	}
	
	//"http://reportplus.test.infragistics.local/rplusdemo
	//"http://reportplus.test.infragistics.local/rplusdemo/api/repository/Get?id=Home%2FTest%2F
    private String _url = "";
     
    private final HttpClient Client = new DefaultHttpClient();
    private String Content;
    private String Error = null;
    
    String data =""; 
    //TextView uiUpdate = (TextView) findViewById(R.id.output);
    //TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
    int sizeData = 0;  
    
    /* Expected params 
     * [0] = partial url for the web service method
     * [1] = username
     * [2] = password 
     * [3] = product
     * */
	protected Void doInBackground(String... params) {
		 BufferedReader reader=null;
		    
         // Send data 
        try
        { 
           
        	//////////////////
        	
        	HttpClient httpClient = new DefaultHttpClient();
        	HttpPost post = new HttpPost(_url + params[0]);
        	
        	
        	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
             nameValuePairs.add(new BasicNameValuePair("UserName",  params[1]));
             nameValuePairs.add(new BasicNameValuePair("Password", params[2]));
             nameValuePairs.add(new BasicNameValuePair("Product", params[3]));
             
             
             post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
             HttpResponse response = httpClient.execute(post);
             
             reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
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
                 String cookieName       = jsonChildNode.optString("CookieName").toString();
                 String cookieValue     = jsonChildNode.optString("CookieValue").toString();
                 String userId = jsonChildNode.optString("UserId").toString();
                 String userDisplayName = jsonChildNode.optString("UserDisplayName").toString();
                 String message = jsonChildNode.optString("Message").toString();
                   
                                 
                 
                  
            }
         /****************** End Parse Response JSON Data *************/    
              
             //Show Parsed Output on screen (activity)
             //jsonParsed.setText( OutputData );
              
               
         } catch (JSONException e) {
   
             e.printStackTrace();
         }
    }
}
