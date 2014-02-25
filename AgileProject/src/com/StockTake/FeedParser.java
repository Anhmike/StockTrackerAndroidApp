package com.StockTake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FeedParser
{
	
	public void getFeed(Finance toPopulate, String currentStock)
	{
		BufferedReader reader;
		String csvData[] = null;
		//toPopulate.setVolume(Integer.parseInt(csvHistoricData[1]));
		
		reader = null;
		csvData = null;
		
		try
		{
			reader = getCsvRealtime(currentStock);
			csvData = parseCsvRealtime(reader);
		}
		catch (IOException e)
		{
			Log.e("oh oh", e.toString());
		}
		
		toPopulate.setLast((Float.parseFloat(csvData[1]) / 100f));
		Log.v("price", "3 : " + (Float.parseFloat(csvData[1]) / 100f));
		toPopulate.setName(currentStock);
		Log.v("name", "4 : " + currentStock);
		//toPopulate.setMarket("NASDAQ");
		toPopulate.setInstantVolume(Integer.parseInt(csvData[2]));
		Log.v("volume", "5 : " + csvData[2]);
	}
	
	public String[] getHistoricFeed(String currentStock)
	{
		BufferedReader reader;
		String csvHistoricData[] = null;
		
		try 
		{
			reader = getCsvHistoric(currentStock);
			csvHistoricData = parseCsvString(reader);
		}
		catch (IOException e) 
		{
		}
		
		Log.v("hamish", "1P : " + csvHistoricData[0]);
		Log.v("hamish", "1P : " + csvHistoricData[1]);
		
		//toPopulate.setClose((Float.parseFloat(csvHistoricData[0])/100f));
		for(int i = 0; i < csvHistoricData.length; i++){
		Log.v("hamish", "1P : " + csvHistoricData[i]);
		}	
		
		return csvHistoricData;
	}
	
	public BufferedReader getCsvHistoric(String stockSymbol)
	{	
		// Generate URL
		URL feedUrl = null;
		InputStream  is = null;
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR) - 1;
		
		
		try
		{
			feedUrl = new URL("http://ichart.yahoo.com/table.csv?s=" + stockSymbol + ".L&a=" + month + "&b=" + day + "&c=" + year);
		}
		catch (IOException e)
		{
			Log.w("oh oh", e.toString());
		}
		try
		{
			is = feedUrl.openStream();
		}
		catch (IOException e)
		{
			Log.w("oh oh", e.toString() );
		}
		
		return new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));	
	}
	
	private String[] parseCsvString(BufferedReader csvToParse) throws IOException  
	{
		String strLine = "";
		StringTokenizer st = null;
		int lineNumber = 0, tokenNumber = 0;
		String csvdata[] = new String[365];
		while( ((strLine = csvToParse.readLine()) != null))
		{
			lineNumber++;
			Log.v("mark", "5 : " + lineNumber);
			if (lineNumber != 1) {

				st = new StringTokenizer(strLine, ",");
				String token;
				
				while(st.hasMoreTokens())
				{
					tokenNumber++;
					token = st.nextToken();
					if (tokenNumber == 5) {
						csvdata[lineNumber] = token;
						Log.v("shit", "4:" + token);
					}
				}
				tokenNumber = 0;
			}
		}
		return csvdata;
	}
	
	public BufferedReader getCsvRealtime(String stockSymbol) throws IOException
	{
		Log.w("oh oh", "Getting real time");
		// Generate URL
		URL feedUrl = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + stockSymbol + ".L&f=nb2b3va");
		
		InputStream is = feedUrl.openStream();
		
		return new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));	
	}
	
	private String[] parseCsvRealtime(BufferedReader csvToParse) 
	{
		String strLine = "";
		StringTokenizer st = null;
		int tokenNumber = 0;
		String csvdata[] = new String[4];
		
		try
		{
			strLine = csvToParse.readLine();
		}
		catch(IOException e)
		{
			Log.e("oh oh", e.toString());
		}
		strLine = strLine.replace("\"", "");
		st = new StringTokenizer(strLine, ",");
		String token;
		float ask = 0f;
		float bid = 0f;
				
		while(st.hasMoreTokens())
		{
			token = st.nextToken();
			
			if (tokenNumber == 0)
			{
				csvdata[0] = token; //name in first field
			}		
			if(tokenNumber == 1)
			{
				ask = Float.parseFloat(token);
			}
			if (tokenNumber == 2)
			{
				bid = Float.parseFloat(token);
				csvdata[1] = Float.toString((ask+bid)/2); //price in second field
			}
			if (tokenNumber == 3)
			{
				csvdata[2] = token; //volume in third field
			}
			tokenNumber++;
		}
		return csvdata;
			
	}
	
}
