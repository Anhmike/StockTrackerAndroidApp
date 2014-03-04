package com.StockTake;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AlertSettingsActivity extends Activity {

	/** Called when the activity is first created. */

	StockManager myStockmanager;

	// ((MyApplication) context.getApplicationContext())

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the StockManager
		myStockmanager = ((StockManager) getApplicationContext());

		setContentView(R.layout.settings);
		
		SeekBar runBar = (SeekBar)findViewById(R.id.RunBar);
		SeekBar rocketBar = (SeekBar)findViewById(R.id.RocketBar);
		SeekBar plummetBar = (SeekBar)findViewById(R.id.PlummetBar);
		
		runBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				setAlertValues("run", progress);
			}
			@Override
	        public void onStartTrackingTouch(SeekBar seekBar) {}
	        @Override
	        public void onStopTrackingTouch(SeekBar seekBar) {}
			
		});
		rocketBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				setAlertValues("rocket", progress);
			}
			@Override
	        public void onStartTrackingTouch(SeekBar seekBar) {}
	        @Override
	        public void onStopTrackingTouch(SeekBar seekBar) {}
			
		});
		plummetBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				setAlertValues("plummet", progress);
			}
			@Override
	        public void onStartTrackingTouch(SeekBar seekBar) {}
	        @Override
	        public void onStopTrackingTouch(SeekBar seekBar) {}
			
		});
		setSliders();

		
	}
	
	public void setSliders()
	{
		int runVal = (int)( myStockmanager.getRunValue() * 100);
		int rocketVal = (int)(myStockmanager.getRocketValue() * 100);
		int plummetVal = (int)(myStockmanager.getPlummetValue() * 100);
		SeekBar runBar = (SeekBar)findViewById(R.id.RunBar);
		runBar.setProgress(runVal);
		SeekBar rocketBar = (SeekBar)findViewById(R.id.RocketBar);
		rocketBar.setProgress(rocketVal);
		SeekBar plummetBar = (SeekBar)findViewById(R.id.PlummetBar);
		plummetBar.setProgress(plummetVal);
	}
	
	
	
	public void setAlertValues(String seekBar, int progress)
	{	
		float prog = progress;
		if(seekBar == "run"){
			TextView runBar = (TextView)findViewById(R.id.runBarValue);
			runBar.setText((progress) + " %");
			myStockmanager.setRunValue((prog/100));
			Log.e("run", Float.toString(prog/100));
		}
		else if(seekBar == "rocket"){
			TextView rocketBar = (TextView)findViewById(R.id.rocketBarValue);
			rocketBar.setText((progress) + " %");
			myStockmanager.setRocketValue((prog/100));
			Log.e("rocket", Float.toString(prog/100));
		}
		else if(seekBar == "plummet"){
			TextView plummetBar = (TextView)findViewById(R.id.plummetBarValue);
			plummetBar.setText((progress) + " %");
			myStockmanager.setPlummetValue((prog/100));
			Log.e("plummet", Float.toString(prog/100));
		}
		
		myStockmanager.updateRuns();
	}
}