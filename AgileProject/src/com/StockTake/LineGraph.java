package com.StockTake;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import com.androidplot.xy.*;
import java.util.Arrays;

/**
 * Using AndroidPlot to plot some data.
 */
public class LineGraph extends Activity
{

    private XYPlot plot;

    public void onDisplay(Bundle savedInstanceState, Number[] stockValues)
    {
        //super.onCreate(savedInstanceState);

        // fun little snippet that prevents users from taking screenshots
        // on ICS+ devices :-)
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
 //                                WindowManager.LayoutParams.FLAG_SECURE);
        //setContentView(R.layout.graph);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(stockValues),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        //LineAndPointFormatter series1Format = new LineAndPointFormatter();
        //series1Format.setPointLabelFormatter(new PointLabelFormatter());
        //series1Format.configure(getApplicationContext(),
               //R.xml.line_point_formatter_with_plf1);

        LineAndPointFormatter series1Format = new LineAndPointFormatter(
        		Color.rgb(0, 200, 0),
        		Color.rgb(0, 100, 0),
        		null,
        		new PointLabelFormatter(Color.WHITE));
        
        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);


        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);

    }
}
