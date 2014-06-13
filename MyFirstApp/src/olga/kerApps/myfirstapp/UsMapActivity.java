package olga.kerApps.myfirstapp;

import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class UsMapActivity extends ActionBarActivity {
	private static final String MAP_FRAGMENT_TAG = "map";
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_us_map);
		// It isn't possible to set a fragment's id programmatically so we set a tag instead and
        // search for it using that.
        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentByTag(MAP_FRAGMENT_TAG);

        // We only create a fragment if it doesn't already exist.
        if (mMapFragment == null) {
            // To programmatically add the map, we first create a SupportMapFragment.
            mMapFragment = SupportMapFragment.newInstance();

            // Then we add it using a FragmentTransaction.
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(android.R.id.content, mMapFragment, MAP_FRAGMENT_TAG);
            fragmentTransaction.commit();
        }

        // We can't be guaranteed that the map is available because Google Play services might
        // not be available.
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // In case Google Play services has since become available.
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = mMapFragment.getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        
        LatLng homeLocal = GetLatLong("250 Olive Street, Warminster, PA, 18974");
        if (homeLocal != null ) {        	
        	mMap.addMarker(new MarkerOptions().position(homeLocal).title("Home").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));        	        	
        }
        LatLng workLocal = GetLatLong("2 Commerce Drive, Cranbury, NJ");
        if (workLocal != null ) {        	
        	mMap.addMarker(new MarkerOptions().position(workLocal).title("Work").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));        	        	
        }
        
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLocal, 5)); // this will position the camera instantly
        CameraPosition cameraPos = new CameraPosition.Builder()
        	.target(homeLocal)
        	.zoom(5)	// zoom
        	//.bearing(90) // camera orientaion
        	.tilt(30) // tilte of camera
        	.build();
        
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));
    }
    
    private LatLng GetLatLong(String strAddress) {
    	Geocoder coder = new Geocoder(this);
    	List<Address> address;

    	try {
    	    address = coder.getFromLocationName(strAddress,5);
    	    if (address == null) {
    	        return null;
    	    }
    	    Address location = address.get(0);
    	    location.getLatitude();
    	    location.getLongitude();

    	    /*GeoPoint p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
    	                      (int) (location.getLongitude() * 1E6));
*/
    	    return new LatLng(location.getLatitude(), location.getLongitude());
    	}
    	catch (Exception e) {
    		return null;
    	}
    	
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.us_map, menu);
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

	

}
