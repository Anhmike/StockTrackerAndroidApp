package com.StockTake.test;


import java.io.IOException;
import java.util.NavigableMap;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;
import android.test.AndroidTestCase;
import android.util.Log;
import com.StockTake.*;

public class FeedParserTest extends AndroidTestCase {

	FeedParser feedParse;
	Finance finance;
	
	protected void setUp()
	{
		feedParse = new FeedParser();
		finance = new Finance();
	}

	public static Test suite()
	{
		return new TestSuite(FeedParserTest.class);
	}
	
	public void testGetFeed()
	{
		feedParse.getFeed(finance, "BP");
		Assert.assertNotNull("Output != null", finance.getLast());
		Assert.assertNotNull("Output != null", finance.getName());
		Assert.assertNotNull("Output != null", finance.getInstantVolume());
		Assert.assertNotNull("Output != null", finance.getVolume());
		Assert.assertNotNull("Output != null", finance.getClose());
	}
	
	public void testNotNull() throws Throwable
	{
		Assert.assertNotSame(feedParse, null);
	}
}
