package com.StockTake;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.Assert;
import android.test.AndroidTestCase;

public class FeedParserTest extends AndroidTestCase
{
	FeedParser feedparse;
	Finance finance;

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}

	protected void setUp()
	{
		feedparse = new FeedParser();
		finance = new Finance();
	}

	public static Test suite()
	{
		return new TestSuite(FeedParserTest.class);
	}

	public void testNotNull() throws Throwable
	{
		Assert.assertNotSame(feedparse, null);
	}

	public void testJSONParse() throws Throwable
	{
		feedparse.parseJSON(finance, "BP");
		assertNotNull(finance.getClose());
		assertNotNull(finance.getName());
		assertNotNull(finance.getSummary());
		assertNotNull(finance.getInstantVolume());
		assertNotNull(finance.getLast());
		assertNotNull(finance.getMarket());
	}

	public void testGetHistoric() throws Throwable
	{
		Assert.assertNotNull(finance);
		finance.setName("BP");
		feedparse.getHistoric(finance, "BP");
		Assert.assertSame(finance.getName(), "BP");
	}
		
	public void testVolCharToInt() throws Throwable
	{
		String testAmount = "123M";
		Assert.assertEquals(123000000, feedparse.volCharToInt(testAmount));
	}
}
