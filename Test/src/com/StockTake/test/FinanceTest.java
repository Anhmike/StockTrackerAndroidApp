package com.StockTake.test;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import android.test.AndroidTestCase;
import android.util.Log;

import com.StockTake.Finance;

public class FinanceTest extends AndroidTestCase{
	Finance newFinance;
	
	public void setUp()
	{
		newFinance = new Finance();
	}

	public static Test suite()
	{
		return new TestSuite(FinanceTest.class);
	}

	public void testCalcRun() {
		 newFinance.calcRun(1.2f);
		 Assert.assertEquals("Output = false", false, newFinance.isRun());
	}

	public void testCalcRocketPlummet() {
		newFinance.calcRocketPlummet(0.5f, 0.1f);
		Assert.assertEquals("Output = false", false, newFinance.isRocket());
		Assert.assertEquals("Output = false", false, newFinance.isPlummet());
	}
}
