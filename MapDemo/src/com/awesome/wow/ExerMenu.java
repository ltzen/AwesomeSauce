package com.awesome.wow;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ExerMenu extends Activity {

	private Spinner spinner1, spinner2;
	private Button btnSubmit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinner);

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);

		addItemsOnSpinner2();
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();
	}

	// add items into spinner dynamically
	public void addItemsOnSpinner2() {

		List<String> list = new ArrayList<String>();
		list.add("All");
		list.add("Speed");
		list.add("Strength");
		list.add("Stamina");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
	}

	public void addListenerOnSpinnerItemSelection() {
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	// get the selected dropdown list value
	public void addListenerOnButton() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(String.valueOf(spinner1.getSelectedItem()).equals("Sprints")
						||String.valueOf(spinner1.getSelectedItem()).equals("Biking")
						||String.valueOf(spinner1.getSelectedItem()).equals("Distance Running")
						||String.valueOf(spinner1.getSelectedItem()).equals("Other GPS exercise")
						){
					Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
					intent.setClass(ExerMenu.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					intent.setPackage(null);
					ExerMenu.this.startActivity(intent);
				}
				else{
					// Perform action on clicks	            
					Intent intent = getPackageManager().getLaunchIntentForPackage("com.awesome.wow");
					intent.setClass(ExerMenu.this, AccelerometerDataActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					intent.setPackage(null);
					ExerMenu.this.startActivity(intent);
					//finish();
				}
			}
		});
	}
}