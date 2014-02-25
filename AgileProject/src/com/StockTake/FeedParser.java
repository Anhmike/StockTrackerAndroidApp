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
	
	public void getFeed(Finance toPopulate, String currentStock, int day, int month, int year)
	{
		BufferedReader reader;
		String csvData[] = null;
				
		try 
		{
			reader = getCsvHistoric(currentStock, day, month, year);
			csvData = parseCsvString(reader);
		}
		catch (IOException e) 
		{
			
		}
		
		toPopulate.setClose((Float.parseFloat(csvData[0])/100f));
		Log.v("his close", "1 : " + csvData[0]);
		toPopulate.setVolume(Integer.parseInt(csvData[1]));
		Log.v("his volume", "2 : " + csvData[1]);
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
	
	public BufferedReader getCsvHistoric(String stockSymbol, int day, int month, int year)
	{	
		// Generate URL
		URL feedUrl = null;
		InputStream  is = null;
		try
		{
			feedUrl = new URL("http://ichart.finance.yahoo.com/table.csv?s=" + stockSymbol + ".L&a=" + month + "&b=" + day + "&c=" + year);
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
		String csvdata[] = new String[2];
		while( ((strLine = csvToParse.readLine()) != null) && lineNumber < 2)
		{
			lineNumber++;
			
			if (lineNumber == 2) {

				st = new StringTokenizer(strLine, ",");
				String token;
				
				while(st.hasMoreTokens())
				{
					tokenNumber++;
					token = st.nextToken();
					if (tokenNumber == 5) {
						csvdata[0] = token;
					}
					
					if (tokenNumber == 6) {
						csvdata[1] = token;
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
