package com.awesome.wow;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Chronometer;
import android.widget.Toast;

public class MapService extends Service implements LocationListener, LocationSource {

	static List<LatLng> points=new ArrayList<LatLng>();
	static public boolean active=false;
	StopWatch timer = new StopWatch();
    
	private OnLocationChangedListener mListener;
	public static LocationManager locationManager;
	float totdistance=0;
	int i=0;
	@Override public void onCreate()
	{
		Toast.makeText(this, "GPS started", Toast.LENGTH_LONG).show();
		active=true;
		timer.start();
		 locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		    if(locationManager != null)
		    {
		        //boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		        

		        
		            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000L, 5F, this);
		            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000L, 5F, this);
		        
		        
		    }
		    else
		    {
		        //Show a generic error dialog since LocationManager is null for some reason
		    }
		    MainActivity.mMap.setLocationSource(this);
		    
		    
	}
	
	
	
	@Override
	public void onDestroy()
	{
		active=false;
		locationManager.removeUpdates(this);
		timer.stop();
		Toast.makeText(this, "GPS stopped", Toast.LENGTH_LONG).show();
		
	}
	
	
	@Override
	public void activate(OnLocationChangedListener listener) 
	{
	    mListener = listener;
	}

	@Override
	public void deactivate() 
	{
	    mListener = null;
	}

	@Override
	public void onLocationChanged(Location location) 
	{
		points.add(new LatLng(location.getLatitude(), location.getLongitude()));
		
	    if( mListener != null )
	    {
	        mListener.onLocationChanged( location );
	        //Move the camera to the user's location once it's available!
	        if(MainActivity.active==true)
	        {
	        	MainActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
	        	MainActivity.track.setPoints(points);
	        }
	        
	        double lat1,lat2,lon1,lon2;
	    	 float[] distance = new float[1];
	    	 float distance2;
	    	 if(points.size()>2)
	    	 {
		    	while(i<points.size()-1)
		    	{
		    	     lat1=points.get(i).latitude; 
		    	     lat2=points.get(i+1).latitude; 
		    	     lon1=points.get(i).longitude; 
		    	     lon2=points.get(i+1).longitude;
		    	     Location.distanceBetween(lat1,lon1,lat2,lon2,distance);
		    	     distance2=distance[0];
		    	     totdistance=totdistance+distance2;
		    	     i++;
		    	}
	    	 }
	    	 if(StatsActivity.active==true)
	    	 {
		    	 StatsActivity.lat=location.getLatitude();
		    	 StatsActivity.distance=totdistance;
		    	 StatsActivity.speed=location.getSpeed();
		    	 StatsActivity.time=timer.getElapsedTimeSecs();
		    	 StatsActivity.run();
	    	 }
	        
	    }
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
	    // TODO Auto-generated method stub
	    Toast.makeText(this, "provider disabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
	    // TODO Auto-generated method stub
	    Toast.makeText(this, "provider enabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
	    // TODO Auto-generated method stub
	    Toast.makeText(this, "status changed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
