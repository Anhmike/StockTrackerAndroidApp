package com.StockTake.test;

import org.junit.Test;

import android.test.AndroidTestCase;
import android.util.Log;

import com.StockTake.Finance;

public class FinanceTest extends AndroidTestCase{
	Finance newFinance = new Finance();

	@Test
	public void testCalcRun() {
		 newFinance.calcRun(1.2f);
		 String temp = String.valueOf(newFinance.isRun());
		 Log.v("mate", temp);
		
		
	}

	public void testCalcRocketPlummet() {
		
	}
}
