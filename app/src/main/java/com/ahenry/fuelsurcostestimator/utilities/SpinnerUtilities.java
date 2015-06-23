package com.ahenry.fuelsurcostestimator.utilities;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerUtilities {

	public static void addItemOnSpinner(Context aContext,Spinner aSpinner, List<String>aList){
    	ArrayAdapter<String> aDataArray = new ArrayAdapter<String>(aContext,android.R.layout.simple_spinner_item,aList);
    	aDataArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	aSpinner.setAdapter(aDataArray);
    }
}
