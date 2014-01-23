package com.example.shares;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.Spanned;

public class RiseFallActivity extends Activity {
	
	private static TextView[] textViews;
	private final static int arraySize = 5;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.risefallactivitylayout);
        
        textViews = new TextView[arraySize];
        
        textViews[0] = (TextView)findViewById(R.id.textView1);
        textViews[1] = (TextView)findViewById(R.id.textView2);
        textViews[2] = (TextView)findViewById(R.id.textView3);
        textViews[3] = (TextView)findViewById(R.id.textView4);
        textViews[4] = (TextView)findViewById(R.id.textView5);
        
        populateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_share_sets, menu);
        return true;
    }
   
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void backButtonClick(View v)
    {
    	finish();
    }
    
	public static Handler uiCallback = new Handler () {
		public void handleMessage (Message msg) {    	
				populateList();
	    }
	};
	
	private static void populateList()
	{	
		String[] prices = Portfolio.getPercentageChange();
		
    	
		for(int i=0; i<prices.length; i++){
			textViews[i].setText(prices[i]);
			if(prices[i].contains("-"))
				textViews[i].setTextColor(Color.RED);
			else if(prices[i].contains(" 0%"))
				textViews[i].setTextColor(Color.BLACK);
			else if (prices[i].contains("%") )
				textViews[i].setTextColor(Color.argb(0x90, 0x00, 0xC0, 0x00));
			
			
		}
	}

}
