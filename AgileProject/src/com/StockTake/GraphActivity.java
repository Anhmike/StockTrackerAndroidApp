package com.StockTake;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

public class GraphActivity extends Activity {

	StockManager myStockmanager;
	private Spinner spinner_stocks, spinner_time;
	private Button btnGenerateGraph;
	private XYPlot plot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the StockManager
		myStockmanager = ((StockManager)getApplicationContext());
		setContentView(R.layout.graph);
		update(); 
	}

	private void update() {

		if (checkInternetConnection()) {
			try {
				populateSpinners();
				
			} catch(Exception e) {
				
			}
		} else {
		}
	}

	private void populateSpinners() throws IOException, JSONException {
		spinner_stocks = (Spinner) findViewById(R.id.stock_spinner);
		final String[] stockArray = {"S&M","Experian","M&S","HSBC","BP"};

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_item,stockArray);
		
		dataAdapter.setDropDownViewResource
		(android.R.layout.simple_spinner_dropdown_item);

		spinner_stocks.setAdapter(dataAdapter);

		spinner_time = (Spinner) findViewById(R.id.time_spinner);
		final String[] timeArray = {"Weekly","Monthly","Yearly"};

		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_item,timeArray);

		dataAdapter1.setDropDownViewResource
		(android.R.layout.simple_spinner_dropdown_item);

		spinner_time.setAdapter(dataAdapter1);

        // Button click Listener 
        addListenerGenerateGraphBtn();
	}
    
    private void addListenerGenerateGraphBtn() {
		spinner_stocks = (Spinner) findViewById(R.id.stock_spinner);
		spinner_time = (Spinner) findViewById(R.id.time_spinner);
		btnGenerateGraph = (Button) findViewById(R.id.btnSubmit);
		btnGenerateGraph.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String stockname = String.valueOf(spinner_stocks.getSelectedItem());;
				String time = String.valueOf(spinner_time.getSelectedItem());;
				String stockAb = "";

				if (stockname.equals("S&N")) {
					stockAb = "SN";
				} else if (stockname.equals("Experian")) {
					stockAb = "EXPN";
				} else if (stockname.equals("M&S")) {
					stockAb = "MKS";
				} else if (stockname.equals("HSBC")) {
					stockAb = "HSBA";
				} else if (stockname.equals("BP")) {
					stockAb = "BP";
				}
				getGraphData(stockAb, time, stockname);
			}
		});
    }

	private boolean checkInternetConnection() {
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	private void getGraphData(String stockAb, String time, String stockName)
	{
		LinkedList<Float> HistoricList = new LinkedList<Float>();
		FeedParser HistoricData = new FeedParser();
		HistoricList = HistoricData.getHistoricFeed(stockAb, time);

		int weekBoundMax = 5;
		int monthBoundMax = 20;
		int yearBoundMax = 365;

		//String[] week_vals = {"Mon", "Tues", "Wed", "Thur", "Fri"};
		//String[] month_vals = {""};
		//String[] year_vals = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};

		Number[] array = HistoricList.toArray(new Number[HistoricList.size()]);

		// initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        plot.clear();
  
        formatGraph(time,stockName,array);
		
        // Turn the above arrays into XYSeries':
		XYSeries series1 = new SimpleXYSeries(Arrays.asList(array), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");

		LineAndPointFormatter series1Format = new LineAndPointFormatter(
				Color.rgb(0, 200, 0), Color.rgb(0, 100, 0), null, null);
        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);

        //Domain = X-axis || Range = Y-axis
        plot.setTicksPerRangeLabel(1);
        plot.setTicksPerDomainLabel(1);
        
        if(time.equals("Weekly")) {
        	plot.setDomainBoundaries(1, weekBoundMax, BoundaryMode.FIXED);
        	//set domain labels as string [x-axis]
        	//plot.getGraphWidget().setDomainValueFormat(new GraphXLabelFormat());
        }
        else if(time.equals("Monthly")) {
        	plot.setDomainBoundaries(1, monthBoundMax, BoundaryMode.FIXED);
        	//set domain labels as string [x-axis]
        	//plot.getGraphWidget().setDomainValueFormat(new GraphXLabelFormat());
        }
        else if(time.equals("Yearly")) {
        	plot.setDomainBoundaries(1, yearBoundMax, BoundaryMode.FIXED);
        	//set domain labels as string [x-axis]
        	//plot.getGraphWidget().setDomainValueFormat(new GraphXLabelFormat());
        } 
        plot.getGraphWidget().setDomainLabelOrientation(-45);
        plot.redraw();
	}
	
	private void formatGraph(String time, String stockName, Number[] array) {
		//Set Graph Title according to selection
        String graph_title = "" + time + " Graph for Stock: " + stockName;
		plot.setTitle(graph_title);
		plot.setDomainLabel("Time Period: " + time); // X-axis label
		plot.setRangeLabel("Share Value"); // Y-axis label
		plot.getLegendWidget().setVisible(false);
		plot.setDomainValueFormat(new DecimalFormat("0"));
		plot.setRangeValueFormat(new DecimalFormat("0"));
	}
}