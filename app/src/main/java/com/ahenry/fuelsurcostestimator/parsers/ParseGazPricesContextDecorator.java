package com.ahenry.fuelsurcostestimator.parsers;

import android.content.Context;

public abstract class ParseGazPricesContextDecorator extends ParseGazPrices {

	protected ParseGazPrices p;
	protected Context aAppContext;
	
	public ParseGazPricesContextDecorator(Context c){
		aAppContext = c;
	}

}
