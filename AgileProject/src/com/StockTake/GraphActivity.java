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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GraphActivity extends Activity {

	StockManager myStockmanager;
	private Spinner spinner1;
	private Button btnSubmit;
	private Spinner spinner2;
	Bundle stateForGraph;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		stateForGraph = savedInstanceState;
		// Get the StockManager
		myStockmanager = ((StockManager)getApplicationContext());

		setContentView(R.layout.graph);
		update(); 
	}

	private void update(){


		if (checkInternetConnection()) {
			try {

				test();

			} catch(Exception e) {
				/* Parse Error */ 
				//error1.setText(Html.fromHtml(" <big>Oops!</big><br/><br/> Something went wrong when we tried to retrieve your share portfolio.<br/><br/> Please try again later."));
				//errorRow.addView(error1, params);
				//table.addView(errorRow); 
			}

		} else {
			/* No Internet Connection */
			//error1.setText(Html.fromHtml(" <big>Oops!</big><br/><br/> It seems there is a problem with your internet connection."));
			//errorRow.addView(error1, params);
			//table.addView(errorRow);
		}
	}

	private void test() throws IOException, JSONException
	{
		//String stockName = "SN";
		//getData(stockName);
		
		spinner1 = (Spinner) findViewById(R.id.stock_spinner);
		List<String> list = new ArrayList<String>();
		list.add("S&M");
		list.add("Experian");
		list.add("M&S");
		list.add("HSBC");
		list.add("BP");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_item,list);

		dataAdapter.setDropDownViewResource
		(android.R.layout.simple_spinner_dropdown_item);

		spinner1.setAdapter(dataAdapter);
		

		spinner2 = (Spinner) findViewById(R.id.time_spinner);
		List<String> list2 = new ArrayList<String>();
		list2.add("Yearly");
		list2.add("Monthly");
		list2.add("Weekly");

		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_item,list2);

		dataAdapter.setDropDownViewResource
		(android.R.layout.simple_spinner_dropdown_item);

		spinner2.setAdapter(dataAdapter1);
		
        // Button click Listener 
        addListenerOnButton();
		
	}
	

    
    //get the selected dropdown list value
    
    public void addListenerOnButton() {
    	

 
        spinner1 = (Spinner) findViewById(R.id.stock_spinner);
        spinner2 = (Spinner) findViewById(R.id.time_spinner);
         
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
 
        btnSubmit.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
            	
            	String stockname;
            	String time;
 
                Toast.makeText(GraphActivity.this,
                        "On Button Click : " + 
                        "\n" + String.valueOf(spinner1.getSelectedItem()) + "\n" + String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_LONG).show();
                
                		stockname = String.valueOf(spinner1.getSelectedItem());
                		time = String.valueOf(spinner2.getSelectedItem());
                		
                		if(stockname.equals("S&M"))
                		{
                			stockname = "SN";
                		}
                		else if (stockname.equals("Experian"))
                		{
                			stockname = "EXPN";
                		}
                		else if (stockname.equals("M&S"))
                		{
                			stockname = "MKS";
                		}
                		else if (stockname.equals("HSBC"))
                		{
                			stockname = "HSBA";
                		}
                		else if (stockname.equals("BP"))
                		{
                			stockname = "BP";
                		}
                		
                		getData(stockname, time);
                		
            }
 
        });
 
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

	public void getData(String stockname, String time)
	{
		LinkedList<Float> HistoricList = new LinkedList<Float>();
		FeedParser HistoricData = new FeedParser();
		HistoricList = HistoricData.getHistoricFeed(stockname, time);

		for(int i = 0; i < HistoricList.size(); i++) {
			Log.v("booyah", "4 : " + HistoricList.get(i));
		}
		
		//LineGraph graphData = new LineGraph();
		
		//Number[] array = HistoricList.toArray(new Number[HistoricList.size()]);
		
		//graphData.onCreate(stateForGraph, array);
	}


}