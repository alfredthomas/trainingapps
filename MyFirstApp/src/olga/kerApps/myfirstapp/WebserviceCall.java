package olga.kerApps.myfirstapp;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.*;
/**
 * @author AndroidExample DotNetWebService Class
 * 
 */
public class WebserviceCall {
     
    /**
     * Variable Decleration................
     * 
     */
    String namespace = "http://www.webserviceX.NET/";
    private String url = "http://www.webservicex.net/ConvertWeight.asmx";
     
    String SOAP_ACTION;
    SoapObject request = null, objMessages = null;
    SoapSerializationEnvelope envelope;
    HttpTransportSE androidHttpTransport;
   
    public WebserviceCall() {
    }
 
     
    /**
     * Set Envelope
     */
    protected void SetEnvelope() {
      
    	
        try {
            String tmp;
            tmp = "hello";
            // Creating SOAP envelope           
            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            
            //You can comment that line if your web service is not .NET one.
            envelope.dotNet = true;
             
            envelope.setOutputSoapObject(request);
            androidHttpTransport = new HttpTransportSE(url);
            androidHttpTransport.debug = true;
            
             
        } catch (Exception e) {
            //System.out.println("Soap Exception---->>>" + e.toString());    
        }
    }
 
    // MethodName variable is define for which webservice function  will call
    public String getConvertedWeight(String MethodName, String weight,
            String fromUnit, String toUnit) 
      {
         
        try {
            SOAP_ACTION = namespace + MethodName;
             
            //Adding values to request object
            request = new SoapObject(namespace, MethodName);
             
            //Adding Double value to request object
            PropertyInfo weightProp =new PropertyInfo();
            weightProp.setName("Weight");
            weightProp.setValue(weight);
            weightProp.setType(double.class);
            request.addProperty(weightProp);
             
            //Adding String value to request object
            request.addProperty("FromUnit", "" + fromUnit);
            request.addProperty("ToUnit", "" + toUnit);
             
            SetEnvelope();
             
            try {
                 
                //SOAP calling webservice
                androidHttpTransport.call(SOAP_ACTION, envelope);
                 
                //Got Webservice response
                String result = envelope.getResponse().toString();
 
                return result;
                 
            } catch (Exception e) {
                // TODO: handle exception
                return e.toString();
            }
        } catch (Exception e) {
            // TODO: handle exception
            return e.toString();
        }
    	//return "";
 
    }
 
     
    /************************************/
}
