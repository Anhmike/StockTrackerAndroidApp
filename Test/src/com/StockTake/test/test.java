package com.StockTake.test;

//import junit.framework.TestCase;
import org.junit.Test;
import android.test.AndroidTestCase;
import com.StockTake.*;

public class test extends AndroidTestCase {
	@Test
	public void testPopulateSpinners()
	{
		GraphActivity ga = new GraphActivity();
		String stockAb = ""; 
		String stockName = "S&M";
		stockAb = ga.getStockName(stockName, stockAb);
		System.out.println(stockAb);
	}
}
