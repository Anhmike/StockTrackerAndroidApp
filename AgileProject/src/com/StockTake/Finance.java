package com.StockTake;


public class Finance
{

	public String name; // Stock name
	public float last  = 0; // Last stock value
	public String market; // Market
	public float close = 0;
	public int volume = 0;
	public int instant_volume = 0;
	public boolean is_run;
	public boolean is_rocket;
	public boolean is_plummet;
	
	public final float RUN_CONST = 1.1f;
	public final float ROCKET_CONST = 1.1f;
	public final float PLUMMET_CONST = 0.8f;

	public Finance()
	{
		name = "Default";
		last = 0;
	}

	public void setLast(float newLast)
	{
		last = newLast;
	}

	public float getLast()
	{
		return last;
	}

	public void setName(String newName)
	{
		name = newName;
	}

	public String getName()
	{
		return name;
	}

	public void setMarket(String newMarket)
	{
		market = newMarket;
	}

	public String getMarket()
	{
		return market;
	}

	public String getSummary()
	{
		return name + ":  " + last;
	}
	
	public void setClose(float newClose)
	{
		close = newClose;
	}

	public float getClose()
	{
		return close;
	}
	
	public void setVolume(int newVol)
	{
		volume = newVol;
	}

	public int getVolume()
	{
		return volume;
	}
	
	public void setInstantVolume(int newVol)
	{
		instant_volume = newVol;
	}

	public int getInstantVolume()
	{
		return instant_volume;
	}
	
	public boolean isRun() {
		return is_run;
	}
	
	public boolean isRocket() {
		return is_rocket;
	}
	
	public boolean isPlummet() {
		return is_plummet;
	}
	
	public void calcRun() {
		
		if (volume != 0 && instant_volume != 0) {
			if (instant_volume > (RUN_CONST * volume)) {
				is_run = true;
			} else {
				is_run = false;
			}
		}
	}
	
	public void calcRocketPlummet() {
		
		is_plummet = false;
		is_rocket = false;
		if (last != 0 && close != 0) {
			if (last > (ROCKET_CONST * close)) {
				is_rocket = true;
			} else if (last < (PLUMMET_CONST * close)) {
				is_plummet = true;
			}
				
		}
		
	}

}
