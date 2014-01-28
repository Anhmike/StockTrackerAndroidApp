package com.StockTake;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SummaryActivity extends Activity
{

	/** Called when the activity is first created. */

	StockManager myStockmanager;
	
	/* Create Error Messages */
	TableLayout table; 
	TableRow errorRow;
	TextView error1;
	TableRow.LayoutParams params;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Get the StockManager
		myStockmanager = ((StockManager)getApplicationContext());
		
		setContentView(R.layout.summary);

	    update();

	}

	public void update() {
		
		
		table = (TableLayout) this.findViewById(R.id.tableLayout1); 
		
		errorRow = new TableRow(this);
		error1 = new TextView(this);
		params = new TableRow.LayoutParams();  
	    params.span = 4;
		
		if (checkInternetConnection()) {
			try {
				
				onClick();
				myStockmanager.summaryTable(this);
				
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
	}

	/* Click Refresh */
	public void onClick() throws IOException, JSONException {
	
		myStockmanager.clearPortfolio();
		myStockmanager.addPortfolioEntry("BLVN", "BowLeven Plc", 3960);
		myStockmanager.addPortfolioEntry("BP", "BP Amoco Plc", 192);
		myStockmanager.addPortfolioEntry("HSBA", "HSBC Holdings Plc Ord.", 343);
		myStockmanager.addPortfolioEntry("EXPN", "Experian", 258);
		myStockmanager.addPortfolioEntry("MKS", "Marks & Spencer Ord.", 485);
		myStockmanager.addPortfolioEntry("SN", "Smith & Nephew Plc Ord.", 1219);
		
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


}