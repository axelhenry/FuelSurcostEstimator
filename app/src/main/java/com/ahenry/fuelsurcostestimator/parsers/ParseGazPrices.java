package com.ahenry.fuelsurcostestimator.parsers;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.ahenry.fuelsurcostestimator.utilities.ConstantUtilities;

import android.os.AsyncTask;

abstract class ParseGazPrices extends AsyncTask<String, Void, HashMap<String,Float>> {

//	final protected String regExp = "[0-9]+([,.][0-9]{1,3})?";
//    final protected Pattern pattern = Pattern.compile(ConstantUtilities.FLOATREGEX);
	
	@Override
	protected abstract HashMap<String, Float> doInBackground(String... params);
 
}
