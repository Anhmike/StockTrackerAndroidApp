package com.example.shares;

import java.util.Calendar;

public class Share 
{
	private String name;
	private YahooFinanceAPI.StockSymbol symbol;
	private int sharesAmount;
	
	private final static int daysInAWeek = 7;
	private final static int dayOffset = 1;
	private final static int shareDivision = 2;
	private final static int shareChangeBoundary = 0;
	private final static int hundredPercent = 100;
	private final static int subtractDayOffset = 2;
	
	//private int sharesSold;
	//private int sharesOutstanding;
	
	//private int lastWeekClose;

	/*
	 * Initialises object with share type and amount of that share in the porfolio
	 */
	public Share(int sharesAmount, YahooFinanceAPI.StockSymbol symbol, String name) 
	{
		this.sharesAmount = sharesAmount;
		this.symbol = symbol;
		this.name = name;
	}
	
	public String getShareName()
	{
		return name;
	}
	

	public float getWeekPercentChange() throws DataUnavailableException 
	{
		Calendar cal = Calendar.getInstance();
		float mondayOpen = YahooFinanceAPI.getHistoricStockData(symbol, YahooFinanceAPI.HistoricDataType.OPEN, setCalendarToLastMonday(cal));
		
		float currentPrice = getSharePrice();
		if(mondayOpen <= shareChangeBoundary || currentPrice <= shareChangeBoundary)
		{
			if(mondayOpen == shareChangeBoundary)
				throw new DataUnavailableException("N/A for week");
			
			throw new DataUnavailableException("unavailable"); 
		}
		float percentChange = ((currentPrice-mondayOpen)/mondayOpen) * hundredPercent;
		
		return percentChange;
	} 
	
	/*
	 * Returns the estimated value of the set of shares
	 */
	public float getShareSetWorth()
	{
		return getSharePrice()*sharesAmount;
	}
	
	public float getShareSetWorthLastWeekClose()
	{
		return getLastWeekClosePrice()*sharesAmount;
	}
	
	/*
	 * Returns price of individual share
	 */
	public float getSharePrice()
	{
		float ask = YahooFinanceAPI.getStockData(symbol, YahooFinanceAPI.DataType.ASK);
		float bid = YahooFinanceAPI.getStockData(symbol, YahooFinanceAPI.DataType.BID);
		
		return (ask + bid) / shareDivision;
	}	
	
	public float getPreviousCLoseFromYahoo()
	{
		return YahooFinanceAPI.getStockData(symbol, YahooFinanceAPI.DataType.PREV_CLOSE);	
	}
	
	//get marks and spencer ask price from google API
	public float getAskPriceFromYahoo()
	{
		return YahooFinanceAPI.getStockData(symbol, YahooFinanceAPI.DataType.ASK);	
	}	
	
	//get marks and spencer bid price from google API
	public float getBidPriceFromYahoo()
	{
		return YahooFinanceAPI.getStockData(symbol, YahooFinanceAPI.DataType.BID);
	}
	
	public float getLastWeekClosePrice()
	{
		Calendar cal = Calendar.getInstance();
		cal = setCalendarToLastFriday(cal);  
 
		return YahooFinanceAPI.getHistoricStockData(symbol, YahooFinanceAPI.HistoricDataType.CLOSE, cal);		
	}
	
	public static Calendar setCalendarToLastMonday(Calendar cal)  
	{  		
		return setCalendarToLastDay(cal, Calendar.MONDAY);
	} 
	
	public static Calendar setCalendarToLastFriday(Calendar cal)  
	{  
		return setCalendarToLastDay(cal, Calendar.FRIDAY);
	}
	
	public static Calendar setCalendarToLastDay(Calendar cal, int day)  
	{  
		int currentDay = cal.get( Calendar.DAY_OF_WEEK );  
		int subtractDays = -((currentDay+subtractDayOffset+day)%daysInAWeek)-dayOffset;

		cal.add( Calendar.DATE, subtractDays );
		return cal;
	}
}
