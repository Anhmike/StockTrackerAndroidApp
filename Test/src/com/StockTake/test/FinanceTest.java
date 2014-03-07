package com.StockTake.test;

import junit.framework.Assert;

import org.junit.Test;

import android.test.AndroidTestCase;
import android.util.Log;

import com.StockTake.Finance;

public class FinanceTest extends AndroidTestCase{
	Finance newFinance = new Finance();

	@Test
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
