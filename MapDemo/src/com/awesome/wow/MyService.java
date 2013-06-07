package com.awesome.wow;


import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;



public class MyService extends Service implements LocationListener {
	static public boolean active=false;
	
	List<LatLng> points=new ArrayList<LatLng>();
	static LocationManager locationManager;
	LocationListener locationListener;
	float totdistance=0;

	private static final String TAG = "Calculations";

	@Override
	public IBinder onBind(Intent intent) {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public void onCreate() {
	    Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
	    Log.d(TAG, "onCreate");
	    active=true;
	    run();

	}
	
	@Override
	public void onDestroy(){
		active=false;
	}

	private void run(){

	    final Criteria criteria = new Criteria();

	    criteria.setAccuracy(Criteria.ACCURACY_FINE);
	    criteria.setSpeedRequired(true);
	    criteria.setAltitudeRequired(false);
	    criteria.setBearingRequired(false);
	    criteria.setCostAllowed(true);
	    criteria.setPowerRequirement(Criteria.POWER_LOW);
	    //Acquire a reference to the system Location Manager

	    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

	    // Define a listener that responds to location updates
	    locationListener = new LocationListener() {

	        @Override
			public void onLocationChanged(Location newLocation) {
	        	points.add(new LatLng(newLocation.getLatitude(), newLocation.getLongitude()));
	            //gets positi
	        	 double lat1,lat2,lon1,lon2;
		    	 float[] distance = new float[1];
		    	 float distance2;
		    	 if(points.size()>2)
		    	 {
			    	 for(int i=0;i<points.size();i++)
			    	{
			    	     lat1=points.get(i).latitude; 
			    	     lat2=points.get(i+1).latitude; 
			    	     lon1=points.get(i).longitude; 
			    	     lon2=points.get(i+1).longitude;
			    	     Location.distanceBetween(lat1,lon1,lat2,lon2,distance);
			    	     distance2=distance[0];
			    	     totdistance=totdistance+distance2;	    			
			    	}
		    	 }
	        	StatsActivity.lat=newLocation.getLatitude();
	        	StatsActivity.distance=totdistance;
	            
	            StatsActivity.run();
	        }

	        //not entirely sure what these do yet
	        @Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
	        @Override
			public void onProviderEnabled(String provider) {}
	        @Override
			public void onProviderDisabled(String provider) {}

	    };

	    // Register the listener with the Location Manager to receive location updates
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000L, 5F, locationListener);

	}



	

	@Override
	public void onLocationChanged(Location location) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	}

	}
