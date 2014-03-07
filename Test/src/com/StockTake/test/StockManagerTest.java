package com.StockTake.test;

import java.io.IOException;
import java.util.NavigableMap;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.json.JSONException;
import android.test.AndroidTestCase;
import android.util.Log;

import com.StockTake.FeedParser;
import com.StockTake.Finance;
import com.StockTake.StockManager;

public class StockManagerTest extends AndroidTestCase {
	StockManager stockManager;
	Finance finance;
	
	protected void setUp()
	{
		stockManager = new StockManager();
		finance = new Finance();
	}

	public static Test suite()
	{
		return new TestSuite(StockManagerTest.class);
	}
	
	public void testGetRunValue() {
		Assert.assertNotNull("Output != null", stockManager.getRunValue());
		Assert.assertEquals("Output = 1.2", 1.2f, stockManager.getRunValue());
	}
	
	public void testGetRocketValue() {
		Assert.assertNotNull("Output != null", stockManager.getRocketValue());
		Assert.assertEquals("Output = 1.2", 1.2f, stockManager.getRocketValue());
	}
	
	public void testGetPlummetValue() {
		Assert.assertNotNull("Output != null", stockManager.getPlummetValue());
		Assert.assertEquals("Output = 0.1", 0.1f, stockManager.getPlummetValue());
	}
	
	public void testSetGetState() {
		stockManager.setState("Stock Manager State Test");
		Assert.assertEquals("Stock Manager State Test", stockManager.getState());
	}	

	public void testAddPortfolioEntry() throws IOException {
		stockManager.clearPortfolio();
		Assert.assertTrue("Output = true", stockManager.addPortfolioEntry("SN", "S&N", 1219));
	}
	
	public void testGetPortfolioTotal() throws IOException {
		Assert.assertTrue(stockManager.addPortfolioEntry("SN", "S&N", 1219));
		Assert.assertTrue(stockManager.getPortfolioTotal() != 0f);
	}

	public void testClearPortfolio() throws IOException {
		Assert.assertTrue(stockManager.addPortfolioEntry("SN", "S&N", 1219));
		stockManager.clearPortfolio();
		Assert.assertTrue(stockManager.getPortfolioTotal() == 0f);
	}

	public void testCreateFinanceObject() throws IOException {
		finance = stockManager.createFinanceObject("SN");
		Assert.assertNotNull(finance);
		Assert.assertEquals("SN", finance.getName());
	}
	
	public void testSortPortfolioByValue() throws IOException {
		Assert.assertTrue(stockManager.addPortfolioEntry("MKS", "M&S", 485));
		Assert.assertTrue(stockManager.addPortfolioEntry("BP", "BP", 192));
		Assert.assertTrue(stockManager.addPortfolioEntry("SN", "S&N", 1219));
		Assert.assertTrue(stockManager.addPortfolioEntry("EXPN", "Experian", 258));
		Assert.assertTrue(stockManager.addPortfolioEntry("HSBA", "HSBC", 343));
		NavigableMap navMap = stockManager.sortPortfolioByValue();
	    Log.v("Output = Descending Order", navMap.descendingMap().toString());
	}

}
