package com.awesome.wow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.menu_layout);


	        Button button0 = (Button) findViewById(R.id.exerciser);
	        button0.setOnClickListener(new OnClickListener() {
	            @Override
				public void onClick(View v) {
	                // Perform action on clicks	            
	                Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
	                intent.setClass(MainMenuActivity.this, ExerMenu.class);
	                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	                intent.setPackage(null);
	                MainMenuActivity.this.startActivity(intent);
	                //finish();
	            }

	        });
	        
	        //Search button action
	        Button button = (Button) findViewById(R.id.start_button);
	        button.setOnClickListener(new OnClickListener() {
	            @Override
				public void onClick(View v) {
	                // Perform action on clicks  
	           
	            
	            
	                Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
	                intent.setClass(MainMenuActivity.this, MainActivity.class);
	                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	                intent.setPackage(null);
	                MainMenuActivity.this.startActivity(intent);
	                //finish();
	            }

	        });
	        
	        Button button2 = (Button) findViewById(R.id.stats);
	        button2.setOnClickListener(new OnClickListener() {
	            @Override
				public void onClick(View v) {
	                // Perform action on clicks  
	                Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
	                intent.setClass(MainMenuActivity.this, StatsActivity.class);
	                intent.setFlags(0);
	                intent.setPackage(null);
	                startActivity(intent);
	                //finish();
	            }

	        });
	        
	        Button button3 = (Button) findViewById(R.id.login);
	        button3.setOnClickListener(new OnClickListener() {
	            @Override
				public void onClick(View v) {
	                // Perform action on clicks  
	                Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
	                intent.setClass(MainMenuActivity.this, LoginActivity.class);
	                intent.setFlags(0);
	                intent.setPackage(null);
	                startActivity(intent);
	                //finish();
	            }	            

	        });
	        
	        Button button5 = (Button) findViewById(R.id.accelerometer);
	        button5.setOnClickListener(new OnClickListener() {
	            @Override
				public void onClick(View v) {
	                // Perform action on clicks  
	                Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
	                intent.setClass(MainMenuActivity.this, AccelerometerDataActivity.class);
	                intent.setFlags(0);
	                intent.setPackage(null);
	                startActivity(intent);
	                //finish();
	            }	            

	        });
	        
	        Button button6 = (Button) findViewById(R.id.game);
	        button6.setOnClickListener(new OnClickListener() {
	            @Override
				public void onClick(View v) {
	                // Perform action on clicks  
	                Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
	                intent.setClass(MainMenuActivity.this, PlayerActivity.class);
	                intent.setFlags(0);
	                intent.setPackage(null);
	                startActivity(intent);
	                //finish();
	            }	            

	        });
	        
	        Button button4 = (Button) findViewById(R.id.exit);
	        button4.setOnClickListener(new OnClickListener() {
	            @Override
				public void onClick(View v) {
	                // Perform action on clicks 
	            	if(MapService.active)
	            	{
	            		stopService(new Intent(MainMenuActivity.this,MapService.class));
	            	}
	                finish();
	                
	            }
	            
	        });
	        

	 }
	 
	 
	
	 
	 
	
}