package com.StockTake;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
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

		
	}
}