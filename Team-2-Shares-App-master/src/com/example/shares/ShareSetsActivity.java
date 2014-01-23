package com.example.shares;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class ShareSetsActivity extends Activity {

	private static ArrayAdapter<String> adapter;
	private ListView listView;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_sets);
        
        listView = (ListView) findViewById(R.id.mylist);
        
        adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_3, android.R.id.text1);
        
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        
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
			if(adapter != null)
				populateList();
	    }
	};
	
	private static void populateList(){
    	String[] prices = Portfolio.getShareSetPrices();
    	
    	if(!adapter.isEmpty())
    		adapter.clear();
    	
		for(int i=0; i<prices.length; i++)
			adapter.add(prices[i]);
	}

}
