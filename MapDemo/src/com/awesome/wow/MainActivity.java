package com.awesome.wow;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;



public class MainActivity extends FragmentActivity implements OnMenuItemClickListener {
	
	/**
	 * Note that this may be null if the Google Play services APK is not available.
	 */
	static boolean active = false;
	static boolean close = false;
	static Polyline track;
	static GoogleMap mMap;
	private PolylineOptions line = new PolylineOptions();
	public static LocationManager locationManager;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
	    super.onCreate(savedInstanceState);
	    active=true;
	    close=false;
	    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	    if(locationManager != null)
	    {
	        boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	        

	        if(gpsIsEnabled && MapService.active==false)
	        {
	        	startService(new Intent(this, MapService.class));
	        }
	        else if(!gpsIsEnabled)
	        {
	        	 //Show an error dialog that GPS is disabled.
	        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	     
	    			// set title
	    			alertDialogBuilder.setTitle("No GPS");
	     
	    			// set dialog message
	    			alertDialogBuilder
	    				.setMessage("Please Enable GPS!")
	    				.setCancelable(false)
	    				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
	    					@Override
							public void onClick(DialogInterface dialog,int id) {
	    						// if this button is clicked, close
	    						// current activity
	    						enableLocationSettings();
	    						
	    					}
	    				  })
	    				.setNegativeButton("No",new DialogInterface.OnClickListener() {
	    					@Override
							public void onClick(DialogInterface dialog,int id) {
	    						// if this button is clicked, just close
	    						// the dialog box and do nothing
	    						finish();
	    						
	    					}
	    				});
	     
	    				// create alert dialog
	    				AlertDialog alertDialog = alertDialogBuilder.create();
	     
	    				// show it
	    				alertDialog.show();
	        	//enableLocationSettings();
	            
	        }
	    }
	    startService(new Intent(MainActivity.this, MapService.class));
	 
	    
	    setContentView(R.layout.activity_main);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    setUpMapIfNeeded();
	   
	}
	
	private void enableLocationSettings() {
	    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    startActivity(settingsIntent);
	}
	
	@Override
	public void onPause()
	{
		
		
	    super.onPause();
	    if(close)
	    	finish();
	    //active=false;
	}

	@Override
	public void onResume()
	{
	    super.onResume();
	    if(close)
	    	finish();
	    
	    //active=true;
	    setUpMapIfNeeded();
	    

	    if(MapService.locationManager != null)
	    {
	        mMap.setMyLocationEnabled(true);
	    }
	}
	

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		active=false;
		//stopService(new Intent(this, MapService.class));
		
	}
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.main, menu);
	     return true;
	 }
	 
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		
		 switch(item.getItemId()){
		    case R.id.action_settings:
		    	View menuItemView = findViewById(R.id.action_settings); // SAME ID AS MENU ID
			    PopupMenu popup = new PopupMenu(this, menuItemView);
			    MenuInflater inflater = popup.getMenuInflater();
			    inflater.inflate(R.menu.mapmenu, popup.getMenu());
			    popup.setOnMenuItemClickListener(this);
			    popup.show();
			    return true; 
		    
		    	
		    }
		 
		    return false;   
		 
		 
		}
	 @Override
	 public boolean onMenuItemClick(MenuItem item) {
	     switch (item.getItemId()) {
	     case R.id.stats:
		    	if(MapService.active)
		    	{
		    		Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
	                intent.setClass(MainActivity.this, StatsActivity.class);
	                intent.setFlags(0);
	                intent.setPackage(null);
	                startActivity(intent);
		    		
		    	}
		    	return true;
		    case R.id.stop:
		    	stopService(new Intent(MainActivity.this,MapService.class));
		    	close=true;
		    	finish();
		    	
	         default:
	             return false;
	     }
	     }
	
	
	
	


	/**
	 * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
	 * installed) and the map has not already been instantiated.. This will ensure that we only ever
	 * call {@link #setUpMap()} once when {@link #mMap} is not null.
	 * <p>
	 * If it isn't installed {@link SupportMapFragment} (and
	 * {@link com.google.android.gms.maps.MapView
	 * MapView}) will show a prompt for the user to install/update the Google Play services APK on
	 * their device.
	 * <p>
	 * A user can return to this Activity after following the prompt and correctly
	 * installing/updating/enabling the Google Play services. Since the Activity may not have been
	 * completely destroyed during this process (it is likely that it would only be stopped or
	 * paused), {@link #onCreate(Bundle)} may not be called again so we should call this method in
	 * {@link #onResume()} to guarantee that it will be called.
	 */
	public void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    
	        // Try to obtain the map from the SupportMapFragment.
	    	 mMap = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	        // Check if we were successful in obtaining the map.

	        if (mMap != null) 
	        {
	            setUpMap();
	        }

	        //This is how you register the LocationSource
	        
	        //line = new PolylineOptions().addAll(points).width(5).color(Color.BLUE);
	        
	     
	   
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the camera. In this case, we
	 * just add a marker near Africa.
	 * <p>
	 * This should only be called once and when we are sure that {@link #mMap} is not null.
	 */
	private void setUpMap() 
	{
		 CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(37.09024,-95.712891));
		mMap.moveCamera(center);
		line = new PolylineOptions().addAll(MapService.points).width(5).color(Color.BLUE);
	    mMap.setMyLocationEnabled(true);
	    track = mMap.addPolyline(line);
	}
	
	

	
	
	
}


