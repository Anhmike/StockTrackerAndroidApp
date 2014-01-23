package com.example.shares;

import java.lang.Math;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;

public class Portfolio {

	private static Share[] shares;
	
	private final static int bpShareAmount = 192;
	private final static int ExperienShareAmount = 258;
	private final static int HSBCShareAmount = 343;
	private final static int MandSShareAmount = 485;
	private final static int SandNShareAmount = 1219;
	private final static int startingPortfolioWorth = 0;
	private final static int hundredPercent = 100;
	private final static int monthOffset = 1;
	
	
	public static void addShares() {
		shares = new Share[YahooFinanceAPI.StockSymbol.values().length];

		shares[0] = new Share(bpShareAmount, YahooFinanceAPI.StockSymbol.BP, "BP");
		shares[1] = new Share(ExperienShareAmount, YahooFinanceAPI.StockSymbol.EXPN, "Experian");
		shares[2] = new Share(HSBCShareAmount, YahooFinanceAPI.StockSymbol.HSBA, "HSBC");
		shares[3] = new Share(MandSShareAmount, YahooFinanceAPI.StockSymbol.MKS, "M&S");
		shares[4] = new Share(SandNShareAmount, YahooFinanceAPI.StockSymbol.SN, "S&N");
	}

	/*
	 * Returns the total worth of portfolio
	 */
	public static String getPortfolioWorth() {
		String err = "";
		float portfolioWorth = startingPortfolioWorth;

		for (int i = 0; i < shares.length; i++) {
			if (shares[i].getShareSetWorth() > YahooFinanceAPI.ERROR)
				portfolioWorth += shares[i].getShareSetWorth();
			else
				err = err.concat(shares[i].getShareName() + " ");
		}

		if (err.isEmpty())
			return "£" + formatter(portfolioWorth);
		else
			return "£" + formatter(portfolioWorth) + "\n\nData for " + err
					+ " is unavailable";
	}

	/*
	 * 
	 */
	public static String[] getSharePrices() {
		String[] sharePrice = new String[shares.length];

		for (int i = 0; i < shares.length; i++) {
			if (shares[i].getShareSetWorth() > YahooFinanceAPI.ERROR)
				sharePrice[i] = shares[i].getShareName() + ": £"
						+ formatter(shares[i].getSharePrice());
			else
				sharePrice[i] = shares[i].getShareName() + ": unavailable";
		}

		return sharePrice;
	}

	/*
	 * Returns set of share prices
	 */
	public static String[] getShareSetPrices() {
		String[] shareWorth = new String[shares.length];

		for (int i = 0; i < shares.length; i++) {
			if (shares[i].getShareSetWorth() > YahooFinanceAPI.ERROR)
				shareWorth[i] = shares[i].getShareName() + ": £"
						+ formatter(shares[i].getShareSetWorth());
			else
				shareWorth[i] = shares[i].getShareName() + ": unavailable";
		}

		return shareWorth;
	}

	/*
	 * Returns estimate of portfolio worth as of last week's close
	 */
	public static String getPortfolioWorthLastWeekClose() {
		String err = "";
		String updated = Portfolio.getFormattedDate(Share
				.setCalendarToLastFriday(Calendar.getInstance()));
		float portfolioWorthWeekClose = startingPortfolioWorth;

		for (int i = 0; i < shares.length; i++) {
			if (shares[i].getShareSetWorthLastWeekClose() > YahooFinanceAPI.ERROR)
				portfolioWorthWeekClose += shares[i]
						.getShareSetWorthLastWeekClose();
			else
				err = err.concat(shares[i].getShareName() + " ");
		}

		if (err.isEmpty())
			return "£" + formatter(portfolioWorthWeekClose)
					+ "\n\n Last Updated: " + updated;
		else
			return "£" + formatter(portfolioWorthWeekClose)
					+ "\n\n Last Updated: " + updated + "\n\nData for " + err
					+ " is unavailable";
	}

	/*
	 * Pence to £ Rounds to nearest pound Splits to commas
	 */
	public static String formatter(Float number) {
		number /= hundredPercent;
		int roundedNum = Math.round(number);

		// Comma grouping from
		// http://www.daniweb.com/software-development/java/threads/205639/put-comma-in-number-format
		DecimalFormat df = new DecimalFormat();
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator(',');

		df.setDecimalFormatSymbols(dfs);

		return df.format(roundedNum);
	}

	/*
	 * Gets a string representation of a Calendar object in the form DD-MM-YYYY
	 */
	public static String getFormattedDate(Calendar date) {
		return date.get(Calendar.DATE) + "-" + (date.get(Calendar.MONTH) + monthOffset)
				+ "-" + date.get(Calendar.YEAR);
	}

	public static String[] getPercentageChange() {
		String[] output = new String[shares.length];

		for (int i = 0; i < shares.length; i++) {
			output[i] = shares[i].getShareName();
			try{
				output[i] += ": "
					+ Integer.toString(Math.round(shares[i]
							.getWeekPercentChange())) + "%";
			}
			catch (DataUnavailableException DUE)
			{
				output[i] += ": " + DUE.getMessage();
			}
		}
		return output;
	}

}
