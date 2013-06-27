/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.awesome.wow;

//import PlayerActivity;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
//import java.io.Serializable;
/**
 * This is an example of using the accelerometer to integrate the device's
 * acceleration to a position using the Verlet method. This is illustrated with
 * a very simple particle system comprised of a few iron balls freely moving on
 * an inclined wooden table. The inclination of the virtual table is controlled
 * by the device's accelerometer.
 * 
 * @see SensorManager
 * @see SensorEvent
 * @see Sensor
 */

public class AccelerometerDataActivity extends Activity implements SensorEventListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5715638316257322820L;
	private SensorManager mSensorManager;
	private PowerManager mPowerManager;
	private WindowManager mWindowManager;
	private Display mDisplay;
	private WakeLock mWakeLock;
	private Sensor mAccelerometer;
	
	private static int steps=0;
	private static int jacks=0;
	private static int crunches=0;
    private int mLastMatch = -1;
	private float mSensorX;
	private float mSensorY;
	private float mSensorZ;
	private float mYOffset = 480 * 05f;
    private float mLimit = 10;
	float sum = 0;
	int num = 0;
	float average = 0;
	
	private float[] gravity = new float[3];
	private float[] linear_acceleration = new float [3];
	private float[] mScale = new float[2];
    private float[] mLastValues = new float[3*2];
    private float[] mLastDirections = new float[3*2];    
    private float[][] mLastExtremes = { new float[3*2], new float[3*2] };
    private float[] mLastDiff = new float[3*2];
/*
	private TextView mx;
	private TextView my;
	private TextView mz;
	private TextView gx;
	private TextView gy;
	private TextView gz;
	*/
	private TextView sv;
	private TextView jv;
	private TextView cv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accel);  

		// Get an instance of the SensorManager
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

		// Get an instance of the PowerManager
		mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

		// Get an instance of the WindowManager
		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		mDisplay = mWindowManager.getDefaultDisplay();

		// Create a bright wake lock
		mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());

		mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		/*
		mx = (TextView) this.findViewById(R.id.sensorx);
		my = (TextView) this.findViewById(R.id.sensory);
		mz = (TextView) this.findViewById(R.id.sensorz);
		//SensorManager.getRotationMatrix(rotation, inclination, gravity, geomagnetic);
		gx = (TextView) this.findViewById(R.id.gravityx);
		gy = (TextView) this.findViewById(R.id.gravityy);
		gz = (TextView) this.findViewById(R.id.gravityz);
		*/
		sv = (TextView) this.findViewById(R.id.step_value);
		jv = (TextView) this.findViewById(R.id.jack_value);
		cv = (TextView) this.findViewById(R.id.crunch_value);
		
		//sv.setText(Integer.toString(steps));
		//gx.setText(Float.toString(gravity[0]));
        //gy.setText(Float.toString(gravity[1]));
		//gz.setText(Float.toString(gravity[2]));
        
        Button stop = (Button) findViewById(R.id.doneaccel);
        stop.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View v) {
                // Perform action on clicks  
            	
            	// We want to save the values now
            	//steps = 0;
            	//jacks = 0;
            	//crunches = 0;
            	Scores score = new Scores(jacks, Integer.toString(crunches));
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
                intent.setClass(AccelerometerDataActivity.this, MainMenuActivity.class);
                intent.putExtra("testObject", score);
                intent.setFlags(0);
                intent.setPackage(null);
                startActivity(intent);
                finish();
            }

        });
        
        
	}

	@Override
	protected void onResume() {
		super.onResume();
		mWakeLock.acquire();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
		mWakeLock.release();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

			/*
            alpha is calculated as t / (t + dT)
            with t, the low-pass filter's time-constant
            and dT, the event delivery rate
			 */

			final float alpha = (float) 0.8;

			gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
			gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
			gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

			linear_acceleration[0] = event.values[0] - gravity[0];
			linear_acceleration[1] = event.values[1] - gravity[1];
			linear_acceleration[2] = event.values[2] - gravity[2];

			/*
			 * record the accelerometer data, the event's timestamp as well as
			 * the current time. The latter is needed so we can calculate the
			 * "present" time during rendering. In this application, we need to
			 * take into account how the screen is rotated with respect to the
			 * sensors (which always return data in a coordinate space aligned
			 * to with the screen in its native orientation).
			 */
/*
			switch (mDisplay.getRotation()) {
			case Surface.ROTATION_0:
				mSensorX = event.values[0];
				mSensorY = event.values[1];
				mSensorZ = event.values[2];
				break;
			case Surface.ROTATION_90:
				mSensorX = -event.values[1];
				mSensorY = event.values[0];
				mSensorZ = event.values[2];
				break;
			case Surface.ROTATION_180:
				mSensorX = -event.values[0];
				mSensorY = -event.values[1];
				mSensorZ = event.values[2];
				break;
			case Surface.ROTATION_270:
				mSensorX = event.values[1];
				mSensorY = -event.values[0];
				mSensorZ = event.values[2];
				break;
			}
	*/		
			float accel = linear_acceleration[0]*linear_acceleration[0] + linear_acceleration[1]*linear_acceleration[1] + linear_acceleration[2]*linear_acceleration[2];
			sum += accel;
			num++;
			
			Log.w("gravx", Float.toString(gravity[0]));
			Log.w("gravy", Float.toString(gravity[1]));
			Log.w("gravz", Float.toString(gravity[2]));

			Log.w("linaccx", Float.toString(linear_acceleration[0]));
			Log.w("linaccy", Float.toString(linear_acceleration[1]));
			Log.w("linaccz", Float.toString(linear_acceleration[2]));
			
			Log.w("totaccel", Float.toString(accel));
			
			//Log.w("xaccel", Float.toString(mSensorX));
			//Log.w("yaccel", Float.toString(mSensorY));
			//Log.w("zaccel", Float.toString(mSensorZ-SensorManager.GRAVITY_EARTH));
			
			/*
			mx.setText(Float.toString(linear_acceleration[0]));
			my.setText(Float.toString(linear_acceleration[1]));
			mz.setText(Float.toString(linear_acceleration[2]));
			
			gx.setText(Float.toString(gravity[0]));
	        gy.setText(Float.toString(gravity[1]));
	        gz.setText(Float.toString(gravity[2]));
			*/
			
			//pedometer code:
			
			int j = (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) ? 1 : 0;
	        mScale[0] = - (480 * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
	        mScale[1] = - (480 * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
            if (j == 1) {
                float vSum = 0;
                for (int i=0 ; i<3 ; i++) {
                    final float v = mYOffset + event.values[i] * mScale[j];
                    vSum += v;
                }
                int k = 0;
                float v = vSum / 3;
                
                Log.w("vel", Float.toString(v));
                
                float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
                if (direction == - mLastDirections[k]) {
                    // Direction changed
                    int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
                    mLastExtremes[extType][k] = mLastValues[k];
                    float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);

                    if (diff > mLimit) {
                        
                        boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k]*2/3);
                        boolean isPreviousLargeEnough = mLastDiff[k] > (diff/3);
                        boolean isNotContra = (mLastMatch != 1 - extType);
                        
                        if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                        	steps++;
                            Log.w("step", Integer.toString(steps));
                            //for (StepListener stepListener : mStepListeners) {
                            //    stepListener.onStep();
                            //}
                            mLastMatch = extType;
                            average = sum/num;
                            sum = 0;
                            num = 0;
                            Log.w("average", Float.toString(average));
                            if(average >= 100){
                            	jacks++;
                            	// making connection to game here for starters
                            	PlayerActivity player = new PlayerActivity();
                            	player.setHP(5);
                            }
                            if(average<= 50){
                            	crunches++;
                            }
                            
                        }
                        else {
                            mLastMatch = -1;
                        }
                    }
                    mLastDiff[k] = diff;
                }
                mLastDirections[k] = direction;
                mLastValues[k] = v;
            }
			sv.setText(Integer.toString(steps));
			jv.setText(Integer.toString(jacks));
			cv.setText(Integer.toString(crunches));
		}
	}	

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

}