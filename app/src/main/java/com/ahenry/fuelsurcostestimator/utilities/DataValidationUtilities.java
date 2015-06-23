package com.ahenry.fuelsurcostestimator.utilities;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;

import android.util.Log;

public class DataValidationUtilities {

	
	public static HashMap<String,Float> isHashMapsDataValid(HashMap<String,Float> aMap,HashMap<String,Float> aReferenceMap){
		
		Set<String> aReferenceSet = aReferenceMap.keySet();
		boolean referenceIncludeMap = aReferenceSet.containsAll(aMap.keySet());
		Log.d("gazolinePrices()","is our data valid ? : "+referenceIncludeMap);
		HashMap<String,Float> m = new HashMap<String, Float>();
		if(referenceIncludeMap){
			boolean everythingIsOk = true;
			Matcher ma;
			for(String s : aReferenceSet){
				float f = aMap.get(s);
				ma = ConstantUtilities.floatPattern.matcher(String.valueOf(f));
				if(ma.find()){
					m.put(s, f);
				}else{
					everythingIsOk = false;
					break;
				}
			}
			if(!everythingIsOk){
				m.clear();
			}
		}
		return m;
	}
}
