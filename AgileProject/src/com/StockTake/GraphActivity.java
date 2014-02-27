package com.StockTake;

import java.io.IOException;
import java.util.*;

import org.json.JSONException;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
	private XYPlot plot;

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
		list2.add("Weekly");
		list2.add("Monthly");
		list2.add("Yearly");

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
            	String stockAb ="";
 
                /*Toast.makeText(GraphActivity.this,
                        "On Button Click : " + 
                        "\n" + String.valueOf(spinner1.getSelectedItem()) + "\n" + String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_LONG).show();*/
                
                		stockname = String.valueOf(spinner1.getSelectedItem());
                		time = String.valueOf(spinner2.getSelectedItem());
                		
                		if(stockname.equals("S&M"))
                		{
                			stockname = "SN";
                			stockAb = "S&M";
                		}
                		else if (stockname.equals("Experian"))
                		{
                			stockname = "EXPN";
                			stockAb = "Experian";
                		}
                		else if (stockname.equals("M&S"))
                		{
                			stockname = "MKS";
                			stockAb = "M&S";
                		}
                		else if (stockname.equals("HSBC"))
                		{
                			stockname = "HSBA";
                			stockAb = "HSBC";
                		}
                		else if (stockname.equals("BP"))
                		{
                			stockname = "BP";
                			stockAb = "BP";
                		}
                		
                		getData(stockname, time, stockAb);             		
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

	public void getData(String stockname, String time, String stockAb)
	{
		LinkedList<Float> HistoricList = new LinkedList<Float>();
		FeedParser HistoricData = new FeedParser();
		HistoricList = HistoricData.getHistoricFeed(stockname, time);
		
		int weekBoundMax = 7;
		int monthBoundMax = 30;
		int yearBoundMax = 12;
		
		for(int i = 0; i < HistoricList.size(); i++) {
			Log.v("booyah", "4 : " + HistoricList.get(i));
		}
		
		Number[] array = HistoricList.toArray(new Number[HistoricList.size()]);
				
		// initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        plot.clear();
        
        //Set Graph Title according to selection
        String graph_title = "" + time + " Graph for Stock: " + stockAb;
        
        plot.setTitle(graph_title);
        plot.setDomainLabel("Time Period: " + time); //X-axis label
        plot.setRangeLabel("Share Value"); //Y-axis label
        
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(array),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series

        LineAndPointFormatter series1Format = new LineAndPointFormatter(
        		Color.rgb(0, 200, 0),
        		Color.rgb(0, 100, 0),
        		null,
        		null);
        
        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);

        //Domain = X-axis || Range = Y-axis
        plot.setTicksPerRangeLabel(1);
        plot.setTicksPerDomainLabel(1);
        if(time.equals("Weekly"))
        {
        	plot.setDomainBoundaries(1, weekBoundMax, BoundaryMode.FIXED);
        }
        else if(time.equals("Monthly"))
        {
        	plot.setDomainBoundaries(1, monthBoundMax, BoundaryMode.FIXED);
        }
        else if(time.equals("Yearly"))
        {
        	plot.setDomainBoundaries(1, yearBoundMax, BoundaryMode.FIXED);
        }
        
        plot.getGraphWidget().setDomainLabelOrientation(-45);
        plot.redraw();

	}


}