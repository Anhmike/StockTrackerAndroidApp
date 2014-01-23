package com.example.shares;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        YahooFinanceAPI.refreshLoop(20, ShareSetsActivity.uiCallback);  //Start updating every 60 secs
        YahooFinanceAPI.refreshLastFridayHistoricLoop();
        
        Portfolio.addShares();
        //Portfolio.getPortfolioWorthLastWeekClose();        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	public void portfolioActivityStart(View view) {
		Intent intent = new Intent(this, PortfolioTotal.class);
		startActivity(intent);
	}
	
	public void shareSetsActivityStart(View view) {
		Intent intent = new Intent(this, ShareSetsActivity.class);
	    startActivity(intent);
	}
	
	public void riseFallActivityStart(View view) {
		Intent intent = new Intent(this, RiseFallActivity.class);
		startActivity(intent);
	}
}
