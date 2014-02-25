package com.StockTake;

import java.io.IOException;
import java.util.*;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GraphActivity extends Activity {

	StockManager myStockmanager;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.graph);

		super.onCreate(savedInstanceState);

		// Get the StockManager
		myStockmanager = ((StockManager)getApplicationContext());

		setContentView(R.layout.rocket);

		/* Create Error Messages */
		TableLayout table = (TableLayout) this.findViewById(R.id.tableLayout3);
		TableRow errorRow = new TableRow(this);
		TextView error1 = new TextView(this);
		TableRow.LayoutParams params = new TableRow.LayoutParams();
		params.span = 4;

		/* Init refresh button */
		//Button button = (Button) findViewById(R.id.btnRefresh);
		//button.setOnClickListener(this);

		if (checkInternetConnection()) {
			try {


				int rocketplummet = myStockmanager.rocketTable(this);
				if (rocketplummet == 0) {

					error1.setText(Html.fromHtml("No graphs yet!"));
					errorRow.addView(error1, params);
					table.addView(errorRow);
				}

			} catch(Exception e) {
				/* Parse Error */
				error1.setText(Html.fromHtml(" <big>Oops!</big><br/><br/> Something went wrong when we tried to retrieve your share portfolio.<br/><br/> Please try again later."));
				errorRow.addView(error1, params);
				table.addView(errorRow);
			}

		} else {
			/* No Internet Connection */
			error1.setText(Html.fromHtml(" <big>Oops!</big><br/><br/> It seems there is a problem with your internet connection."));
			errorRow.addView(error1, params);
			table.addView(errorRow);
		}
		getData();
	}

	private boolean checkInternetConnection() {

		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		// ARE WE CONNECTED TO THE INTERNET
		if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}

	}

	public void getData()
	{
		String stockname = "SN";
		LinkedList<Float> HistoricList = new LinkedList<Float>();
		FeedParser HistoricData = new FeedParser();
		HistoricList = HistoricData.getHistoricFeed(stockname);

		for(int i = 0; i < HistoricList.size(); i++) {
			Log.v("name", "4 : " + HistoricList.get(i));
		}
	}


}