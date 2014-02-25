package com.StockTake;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GraphsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);
	    getData();
	}
	
	
	public void getData()
	{
		String stockname = "SN";
		String [] historic;
		FeedParser HistoricData = new FeedParser();
		historic = HistoricData.getHistoricFeed(stockname);
		
		for(int i = 0; i < historic.length; i++) {
			Log.v("name", "4 : " + historic[i]);
		}
	}
	
}
