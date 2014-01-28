package com.StockTake;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.Assert;
import android.test.AndroidTestCase;

public class FinanceTest extends AndroidTestCase
{
	Finance finance;

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}

	protected void setUp()
	{
		finance = new Finance();
	}

	public static Test suite()
	{
		return new TestSuite(FinanceTest.class);
	}

	public void testNotNull() throws Throwable
	{
		Assert.assertNotSame(finance, null);
	}

	public void testSetGetName() throws Throwable
	{
		finance.setName("Test Name");
		Assert.assertEquals("Test Name", finance.getName());
	}
	
	public void testSetGetLast() throws Throwable
	{
		finance.setLast(1.234f);
		Assert.assertEquals(1.234f, finance.getLast(), 0);
	}
	
	public void testSetGetMarket() throws Throwable
	{
		finance.setMarket("Test Market Name");
		Assert.assertEquals("Test Market Name", finance.getMarket());
	}
	
	public void testGetSummary() throws Throwable
	{
		finance.setName("Test Name");
		finance.setLast(1.234f);
		Assert.assertEquals("Test Name:  1.234", finance.getSummary());
	}
	
	public void testSetGetClose() throws Throwable
	{
		finance.setClose(2.345f);
		Assert.assertEquals(2.345f, finance.getClose(), 0);
	}
	
	public void testSetGetVolume() throws Throwable
	{
		finance.setVolume(4321);
		Assert.assertEquals(4321, finance.getVolume(), 0);
	}
	
	public void testSetGetInstantVolume() throws Throwable
	{
		finance.setInstantVolume(6354);
		Assert.assertEquals(6354, finance.getInstantVolume());
	}
	
	public void testCalcRunAndIsRun() throws Throwable
	{
		finance.setVolume(1000);
		finance.setInstantVolume(1099);
		finance.calcRun();
		assertFalse(finance.isRun());
		finance.setInstantVolume(1100);
		finance.calcRun();
		assertFalse(finance.is_run);
		finance.setInstantVolume(1101);
		finance.calcRun();
		assertTrue(finance.is_run);		
	}
	
	public void testCalcRocketPlummetAndIsPlummetIsRocket() throws Throwable
	{
		// Rocket boundary tests...
		finance.setClose(1000f);
		finance.setLast(1099);
		finance.calcRocketPlummet();
		assertFalse(finance.isRocket());
		finance.setLast(1100);
		finance.calcRocketPlummet();
		assertFalse(finance.isRocket());
		finance.setLast(1101);
		finance.calcRocketPlummet();
		assertTrue(finance.isRocket());
		// Plummet boundary tests...
		finance.setLast(801);
		finance.calcRocketPlummet();
		assertFalse(finance.isPlummet());
		finance.setLast(800);
		finance.calcRocketPlummet();
		assertFalse(finance.isPlummet());
		finance.setLast(799);
		finance.calcRocketPlummet();
		assertTrue(finance.isPlummet());
		
	}
}
