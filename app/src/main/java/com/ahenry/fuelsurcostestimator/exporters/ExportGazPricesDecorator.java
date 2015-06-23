package com.ahenry.fuelsurcostestimator.exporters;

import android.content.Context;

public abstract class ExportGazPricesDecorator extends ExportGazPrices {

	protected ExportGazPrices eGP;
	public Context aAppContext;
	
	public ExportGazPricesDecorator(Context c) {
		aAppContext = c;
	}

}
