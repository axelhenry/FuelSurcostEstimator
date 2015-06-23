package com.ahenry.fuelsurcostestimator.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.ahenry.fuelsurcostestimator.models.Plot;
import com.ahenry.fuelsurcostestimator.utilities.comparators.PlotComparator;

public class SortingUtilities {

	public static List<String> sortHashMap(HashMap<String,Float> aMap){
		List<String> aList = new ArrayList<String>(aMap.keySet());
    	Collections.sort(aList);
//    	for(String aS : aList){
//    		Log.d("gazolinePrices()", "sorting : "+aS);
//    	}
    	return aList;
	}
	
	public static LinkedList<Plot> sortList(LinkedList<Plot> aList){
		Collections.sort(aList, new PlotComparator());
		return aList;
	}
	
}
