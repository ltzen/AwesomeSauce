package com.awesome.wow;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;


public class StatsActivity extends Activity implements OnMenuItemClickListener {
	
	static TextView latituteField;
	static TextView distanceField;
	static TextView speedField;
	static TextView timeField;
	protected static double lat=0;
	static float distance=0;
	static float speed=0;
	static long time=0;
	static boolean active=false;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        active=true;
        //startService(new Intent(this, MapService.class));
        latituteField = (TextView) findViewById(R.id.lat);
        distanceField = (TextView) findViewById(R.id.dis);
        speedField=(TextView) findViewById(R.id.speed);
        timeField=(TextView) findViewById(R.id.time);
        StatsActivity.run();
        
        
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		active=false;
		//stopService(new Intent(this, MapService.class));
		
	}
	@Override
	public void onPause()
	{
		super.onPause();
		if(MainActivity.close)
			finish();
		//stopService(new Intent(this, MapService.class));
		
	}
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.stats_action, menu);
	     return true;
	 }
	
	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		
		 switch(item.getItemId()){
		    case R.id.stats_action:
		    	View menuItemView = findViewById(R.id.stats_action); // SAME ID AS MENU ID
			    PopupMenu popup = new PopupMenu(this, menuItemView);
			    MenuInflater inflater = popup.getMenuInflater();
			    inflater.inflate(R.menu.statsmenu, popup.getMenu());
			    popup.setOnMenuItemClickListener(this);
			    popup.show();
			    return true; 
		    
		    	
		    }
		 
		    return false;   
		 
		 
		}
	 @Override
	 public boolean onMenuItemClick(MenuItem item) {
	     switch (item.getItemId()) {
	     case R.id.map:
		    	if(MapService.active)
		    	{
		    		Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
	                intent.setClass(StatsActivity.this, MainActivity.class);
	                intent.setFlags(0);
	                intent.setPackage(null);
	                startActivity(intent);
		    		
		    	}
		    	return true;
		    case R.id.stop:
		    	stopService(new Intent(StatsActivity.this,MapService.class));
		    	MainActivity.close=true;
		    	finish();
	         default:
	             return false;
	     }
	     }
	
	static void run(){
		
	    latituteField.setText("Current Latitude:        "+String.valueOf(lat));
	    timeField.setText("Current Time:        "+Totime(time));
	    distanceField.setText("total Distance:        "+String.valueOf(toMile(distance)));
	    speedField.setText("Current Speed:        "+String.valueOf(toMPH(speed)));

	}
	public static double toMile(float distance)
	{
		double miles;
		miles=distance*0.00062137;
		miles=round(miles, 2, BigDecimal.ROUND_HALF_UP);
		return miles;
	}
	public static double toMPH(float speed)
	{
		double MPH;
		MPH=speed*2.237;
		MPH=round(MPH,0,BigDecimal.ROUND_HALF_UP);
		return MPH;
		
	}
	public static double round(double unrounded, int precision, int roundingMode)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, roundingMode);
	    return rounded.doubleValue();
	}
	public static String Totime(long time)
	{
		int min, sec;
		String out;
		if(time>60)
		{
			min=(int) (time/60);
			sec=(int) (time%60);
			out=min+":"+sec;
			return out;
		}
		else
		{
			sec=(int) time;
			out="00:"+sec;
			return out;
		}
		
		
	}

	
	

}
