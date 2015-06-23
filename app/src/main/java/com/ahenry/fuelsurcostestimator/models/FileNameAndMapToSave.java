package com.ahenry.fuelsurcostestimator.models;

import java.util.HashMap;

public class FileNameAndMapToSave {

	String fileName;
	HashMap<String,Float> aMap;
	
	public FileNameAndMapToSave(String s,HashMap<String,Float> m){
		fileName = s;
		aMap = m;
	}
	
	public String getFilename(){
		return fileName;
	}
	
	public HashMap<String, Float> getMap(){
		return aMap;
	}
	
}
