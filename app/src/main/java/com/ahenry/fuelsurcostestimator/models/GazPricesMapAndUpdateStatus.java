package com.ahenry.fuelsurcostestimator.models;

import java.util.HashMap;

public class GazPricesMapAndUpdateStatus {
	
	boolean hasBeenUpdated;
	HashMap<String,Float> aMap;
	
	public GazPricesMapAndUpdateStatus(HashMap<String,Float> map,boolean status) {
		aMap = map;
		hasBeenUpdated = status;
	}

	
	public HashMap<String, Float> getHashMap(){
		return aMap;
	}
	
	public boolean getUpdateStatus(){
		return hasBeenUpdated;
	}
}
