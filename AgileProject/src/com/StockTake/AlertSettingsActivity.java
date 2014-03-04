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
	HashMap <Finance, Float> portfolio;

	// ((MyApplication) context.getApplicationContext())

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the StockManager
		myStockmanager = ((StockManager) getApplicationContext());

		setContentView(R.layout.settings);
		
		portfolio = myStockmanager.getPortfolio();
		
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
		
		Map.Entry<Finance, Float> entry = portfolio.entrySet().iterator().next();
		Finance stock = entry.getKey();
		int runVal = (int)( stock.getRunValue() * 100);
		int rocketVal = (int)(stock.getRocketValue() * 100);
		int plummetVal = (int)(stock.getPlummetValue() * 100);
		SeekBar runBar = (SeekBar)findViewById(R.id.RunBar);
		runBar.setProgress(runVal);
		SeekBar rocketBar = (SeekBar)findViewById(R.id.RocketBar);
		rocketBar.setProgress(rocketVal);
		SeekBar plummetBar = (SeekBar)findViewById(R.id.PlummetBar);
		plummetBar.setProgress(plummetVal);
	}
	
	
	
	public void setAlertValues(String seekBar, int progress)
	{
		
		Iterator iterate = portfolio.keySet().iterator();
		while (iterate.hasNext()){
			Map.Entry<Finance, Float> entry = (Map.Entry<Finance, Float>)iterate.next();
			Finance stock = entry.getKey();
			float value = entry.getValue();
			/*
			portfolio.remove(stock);
			if(seekBar == "run")
				stock.setRunValue((progress/100));
			else if(seekBar == "rocket")
				stock.setRocketValue((progress/100));
			else if(seekBar == "plummet")
				stock.setPlummetValue((progress/100));
			portfolio.put(stock, value); //replaces stock with new alert values
			*/
			
		}
	}
}