package com.ahenry.fuelsurcostestimator;

import java.util.HashMap;

import android.app.Fragment;
import android.content.Context;

import com.ahenry.fuelsurcostestimator.exporters.specifics.ExportGazPricesInInternalStorage;
import com.ahenry.fuelsurcostestimator.models.FileNameAndMapToSave;

public class SaveGazPrices extends Fragment {

	public static Context aAppContext;
	
	public static SaveGazPrices newInstance(Context c){
		SaveGazPrices sGP = new SaveGazPrices();
		SaveGazPrices.aAppContext = c;
		return sGP;
	}
	
	public boolean saveLocallyInJSON(String aName, HashMap<String,Float> aMap){
		boolean isOk = true;
		FileNameAndMapToSave aFNMS = new FileNameAndMapToSave(aName, aMap);
		ExportGazPricesInInternalStorage eGPL = new ExportGazPricesInInternalStorage(aAppContext);
		eGPL.execute(aFNMS);
		try{
			isOk = eGPL.get();
		}catch(Exception ee){
			ee.printStackTrace();
			isOk=false;
		}
		return isOk;
	}
	
}
