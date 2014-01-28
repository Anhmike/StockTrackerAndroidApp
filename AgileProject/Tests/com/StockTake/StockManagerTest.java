package com.StockTake;

import java.io.IOException;

import org.json.JSONException;

import junit.framework.Assert;
import junit.framework.TestCase;

public class StockManagerTest extends TestCase
{

	StockManager stockManager = new StockManager();
	
	public void testSetGetState()
	{
		stockManager.setState("Stock Manager State Test");
		Assert.assertEquals("Stock Manager State Test", stockManager.getState());
	}	

	public void testAddPortfolioEntry() throws IOException, JSONException
	{
		Assert.assertTrue(stockManager.addPortfolioEntry("BP", "BP Amaco", 1234));		
	}
	
	public void testGetPortfolioTotal() throws IOException, JSONException
	{
		Assert.assertTrue(stockManager.addPortfolioEntry("BP", "BP Amaco", 1234));
		Assert.assertTrue(stockManager.getPortfolioTotal() != 0f);
	}

	public void testClearPortfolio() throws IOException, JSONException
	{
		Assert.assertTrue(stockManager.addPortfolioEntry("BP", "BP Amaco", 1234));
		stockManager.clearPortfolio();
		Assert.assertTrue(stockManager.getPortfolioTotal() == 0f);
	}

	public void testCreateFinanceObject() throws IOException, JSONException
	{
		Finance finance;
		finance = stockManager.createFinanceObject("BP");
		Assert.assertNotNull(finance);
		Assert.assertEquals("BP", finance.getName());
	}

}
