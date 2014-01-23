package com.example.shares;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.StringBuilder;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.NullPointerException;
import java.lang.Thread;
import java.lang.ArrayIndexOutOfBoundsException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.Handler;

/*
 * Boundary class between Yahoo Finance API and app.  
 * Helps get shares content from Yahoo Finance.
 */
public final class YahooFinanceAPI{
	
	//Enums define structure of storage arrays
	public enum StockSymbol {BP, EXPN, HSBA, MKS, SN}
	public enum DataType {ASK, BID, PREV_CLOSE, PERCENT_CHANGE, VOL, TOTAL_SHARES}
	public enum HistoricDataType {DATE, CLOSE, OPEN} //, HIGH, LOW, VOL, ADJ_CLOSE}
	
	public static String[][] stockData;
	public static String[][][] historicData;
	private static Calendar lastUpdate;
	private static boolean connected;
	private static boolean historicConnected = false;
	
	private static final String BASE_URL = "http://www.finance.yahoo.com/d/quotes.csv?s=BP.L+EXPN.L+HSBA.L+MKS.L+SN.L&f=";
	public static final float ERROR = -1; 
	
	private static final int timeToWait = 1000;
	private static final int monthOffset = 1;
		
	/*
	 * Get data for specified stock and data type
	 */
	public static float getStockData(StockSymbol symbol, DataType type)	{
		try{	
			return Float.parseFloat(stockData[symbol.ordinal()][type.ordinal()]);
		}
		catch(NullPointerException npe)
		{
			
		}
		catch(ArrayIndexOutOfBoundsException aobex)
		{
			
		}
		catch(NumberFormatException nfe){
			
		}
		
		return ERROR;
	}

	
	/*
	 * Gets historic information about share on a specific date.
	 */
	public static float getHistoricStockData(StockSymbol symbol, HistoricDataType type, Calendar date) {

		String formattedDate = getFormattedDate(date);
		
		//if(historicData == null)
			//refreshHistoric(date, date); //Init historic data if null
		
		try
		{
			
			try{
				for(int i=0; i<historicData[symbol.ordinal()].length; i++){	
					if (historicData[symbol.ordinal()][i][HistoricDataType.DATE.ordinal()].equals(formattedDate))
						return Float.parseFloat(historicData[symbol.ordinal()][i][type.ordinal()]); 
				}
			}
			catch(NullPointerException npe)
			{
				
			}
			
			refreshHistoric(date, date); //otherwise get required historic data and return that
			
			//Look again with refreshed data
			for(int i=0; i<historicData[symbol.ordinal()].length; i++){	
				if (historicData[symbol.ordinal()][i][HistoricDataType.DATE.ordinal()].equals(formattedDate))
					return Float.parseFloat(historicData[symbol.ordinal()][i][type.ordinal()]); 
			}
		}
		catch(NullPointerException npe)
		{
			
		}	
		
		return ERROR;
	}
	
	/*
	 * Gets a string representation of a Calendar object in the form YYYY-MM-DD 
	 * Increments the month by one to rid zero referenced months i.e. JAN=0 now JAN=1 
	 */
	public static String getFormattedDate(Calendar date) {
		return (date.get(Calendar.YEAR) 
				+ "-" + String.format("%02d", (date.get(Calendar.MONTH)+monthOffset))
				+ "-" + String.format("%02d", date.get(Calendar.DATE)));
	}
	
	/*
	 * Loops until historic data is obtained
	 */
	public static void refreshLastFridayHistoricLoop(){
		
		new Thread (new Runnable() {
			//@Override
			public void run() {
				Calendar date = Share.setCalendarToLastFriday(Calendar.getInstance());
				while(!historicConnected){
					YahooFinanceAPI.refreshHistoric(date, date);
					
					try {
						Thread.sleep(timeToWait);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        }
	    }).start();
	}
	
	/*
	 * Starts thread which continually runs refresh to update stock info.  
	 * Runs at time interval specified parameter
	 * Takes a handler to tell the UI every time new data is received so it can update  
	 */
	public static void refreshLoop(final int interval, final Handler uiCallBack){
		new Thread (new Runnable() {
			//@Override
			public void run() {
				while(true){
					YahooFinanceAPI.refresh();
					uiCallBack.sendEmptyMessage(0);
					
					try {
						Thread.sleep(((long)interval) * timeToWait);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        }
	    }).start();
		
	}

	/*
	 * Updates stockData with current data
	 */
	public static void refresh() {
		lastUpdate = new GregorianCalendar();
		
		ArrayList< ArrayList<String> > stocks = new ArrayList< ArrayList<String> >();
		
		String[] file = getFile(BASE_URL + "abpp2"); //get ask, bid, prevClose and %change
		
		for(int i=0; i<file.length; i++){			
			stocks.add(new ArrayList<String>(Arrays.asList(parser(file[i]))));
		}
		
		file = getFile(BASE_URL + "v");						//get volume
		for(int i=0; i<file.length; i++){
			file[i] = file[i].replaceAll("[\\D]", "");  	//remove anything non-numeric
			stocks.get(i).add(file[i]);
		}
		
		file = getFile(BASE_URL + "j2");					//get total shares
		for(int i=0; i<file.length; i++){
			file[i] = file[i].replaceAll("[\\D]", "");  	//remove anything non-numeric
			stocks.get(i).add(file[i]);
		}
		
		//Update stockData field
		stockData = new String[stocks.size()][];
		for(int i=0; i<stockData.length; i++)
			stockData[i] =  Arrays.copyOf(stocks.get(i).toArray(), stocks.get(i).size(), String[].class);  //Line based on http://stackoverflow.com/questions/1018750/how-to-convert-object-array-to-string-array-in-java
		
	}
	
	/*
	 * Cleans up string from Yahoo then splits it up around commas
	 */
	private static String[] parser(String delimitedStr) {
		delimitedStr = delimitedStr.replaceAll("[^\\d,-.]", "");  //remove anything non-numeric except , . -  

		return delimitedStr.split(",");	
	}
	
	/*
	 * historicData[SYMBOL][DATE][DATA]
	 * Gets historic data for all shares between startDate and endDate
	 */
	public static void refreshHistoric(Calendar startDate, Calendar endDate) {
		//if(startDate.after(endDate))
		
		historicData = new String[StockSymbol.values().length][][];  //make field for each stock symbol
		
		for (StockSymbol symbol : StockSymbol.values()){
			String url = makeHistoricUrl(symbol.name() + ".L", startDate, endDate);
			
			historicData[symbol.ordinal()] = parseHistoricXml(url);
		}
	}
	
	/*
	 * Builds url to fetch old data from Yahoo
	 */
	private static String makeHistoricUrl(String symbol, Calendar startDate, Calendar endDate){
		return "http://query.yahooapis.com/v1/public/yql?q=select%20Date%2C%20Close%2C%20Open%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22" +
		symbol +
		"%22%20and%20startDate%20%3D%20%22" +
		getFormattedDate(startDate) +
		"%22%20and%20endDate%20%3D%20%22" +
		getFormattedDate(endDate) +
		"%22&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
	}
	
	/*
	 * Returns flag indicating the success of the last connection attempt
	 */
	public static boolean isConnected()
	{
		return connected;
	}
	
	/*
	 * Connects and reads file from given url 
	 * 
	 * @param urlName - get the page from this url
	 * @return String[] each element contains a line of the web page
	 */
	private static String[] getFile(String urlName) {
		
		StringBuilder strBuilder = new StringBuilder();  //makes string appends faster
		String str;
		connected = true;
		
		try {	
			//Make connection
			URL url = new URL(urlName);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));			
			
			//Append each line to strBuilder until stream is empty
			while((str=br.readLine()) != null)
				strBuilder.append(str + "\r\n");
			
			br.close();
			
		} catch (MalformedURLException e) {
			
		} catch (IOException ioe) {
			connected = false;
		}
		
		//split string at line breaks
		return strBuilder.toString().split("\r\n");
	}
	
	/*
	 * Loads xml from specified url into historic data array 
	 * providing "Date" and "CLose" elements are used in the xml
	 */
	private static String[][] parseHistoricXml(String url)
	{		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null;
		
		historicConnected = false;
		
		try {
			
			db = dbf.newDocumentBuilder();
			doc = db.parse(url);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		NodeList dates = doc.getElementsByTagName("Date");
		NodeList close = doc.getElementsByTagName("Close");
		NodeList open = doc.getElementsByTagName("Open");
		
		String[][] str = new String [dates.getLength()][HistoricDataType.values().length];
		
		for(int i=0; i<dates.getLength(); i++){
			str[i][HistoricDataType.DATE.ordinal()] = dates.item(i).getTextContent();
			str[i][HistoricDataType.CLOSE.ordinal()] = close.item(i).getTextContent();
			str[i][HistoricDataType.OPEN.ordinal()] = open.item(i).getTextContent();
		}
		
		historicConnected = true;
		return str;
	}
}