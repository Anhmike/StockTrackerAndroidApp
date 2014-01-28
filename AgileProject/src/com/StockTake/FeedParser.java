package com.StockTake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FeedParser
{

	public void parseJSON(Finance toPopulate, String currentStock) throws IOException, JSONException
	{
		// Create JSON and Finance objects
		JSONObject jObject;


		// Generate URL
		URL feedUrl = new URL("http://finance.google.com/finance/info?client=ig&infotype=infoquoteall&q=LON:" + currentStock);
		// Read JSON
		InputStream is = feedUrl.openStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1)
		{
			sb.append((char) cp);
		}
		String jsonText = sb.toString();
		jsonText = jsonText.substring(5, jsonText.length() - 2);
		is.close();
		// Init object
		jObject = new JSONObject(jsonText);
		// Set 'Last' value
		toPopulate.setLast(Float.parseFloat(jObject.getString("l")) / 100f);
		//Log.v("LOGCATZ", " " + toPopulate.getLast());
		// Set 'Company' name
		toPopulate.setName(jObject.getString("t"));
		// Set 'Market'
		toPopulate.setMarket(jObject.getString("e"));
		// Set 'Instant Volume'
		int instantVolume = volCharToInt(jObject.getString("vo"));
		toPopulate.setInstantVolume(instantVolume);
		
	


	}
	
	public void getHistoric(Finance toPopulate, String stockToGet) {
		

		BufferedReader csvBr;
		String csvData[] = null;
				
		try {
			csvBr = getCsvFeed(stockToGet);
			csvData = parseCsvString(csvBr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		toPopulate.setClose(Float.parseFloat(csvData[0]) / 100f);
		toPopulate.setVolume(Integer.parseInt(csvData[1]));
		

	}
	
	private BufferedReader getCsvFeed(String stockSymbol) throws IOException {
		
		// Check dates
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE) - 4;
		int month = cal.get(Calendar.MONTH);
		
		// Generate URL
		URL feedUrl = new URL("http://ichart.finance.yahoo.com/table.csv?s=" + stockSymbol + ".L" + "&a=" + month + "&b=" + day + "&c=2011");
		
		InputStream is = feedUrl.openStream();
		return new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));			
	}
	
	private String[] parseCsvString(BufferedReader csvToParse) throws IOException  {
		
		String strLine = "";
		StringTokenizer st = null;
		int lineNumber = 0, tokenNumber = 0;
		String csvdata[] = new String[2];
		
		while( ((strLine = csvToParse.readLine()) != null))
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
						//Log.v("LOGCATZ",  "Last Close: " + csvdata[0]);
					}
					
					if (tokenNumber == 6) {
						csvdata[1] = token;
						//Log.v("LOGCATZ",  "Last Volume: " + csvdata[1]);
					}
				}
				tokenNumber = 0;
			}
		}
		
		return csvdata;
			
	}
	
	public int volCharToInt(String amount)
	{
		float convertedVal = 0;
		int multiplier = 1;
		int returnValue = 0;
		try
		{
			amount = amount.replaceAll(",", "");
			String valComponent = amount.substring(0, amount.length()-1);
			String multComponent = amount.substring(amount.length()-1);
			convertedVal = Float.parseFloat(valComponent);
			multComponent = multComponent.toUpperCase();
			if (multComponent.equals("M"))
			{
				multiplier = 1000000;
			}
			if (multComponent.equals("K"))
			{
				multiplier = 1000;
			}
			convertedVal = convertedVal * (float)multiplier;
			returnValue = (int) convertedVal;
		}
		catch(Exception e)
		{
			returnValue = 0;
		}
		return returnValue;
	}
	
	
	
}
