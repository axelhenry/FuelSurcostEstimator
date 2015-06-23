package com.ahenry.fuelsurcostestimator.exporters.specifics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.HashMap;

import com.ahenry.fuelsurcostestimator.exporters.ExportGazPricesDecorator;
import com.ahenry.fuelsurcostestimator.models.FileNameAndMapToSave;
import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONComposer;
import com.fasterxml.jackson.jr.ob.JSONObjectException;
import com.fasterxml.jackson.jr.ob.comp.ObjectComposer;

import android.content.Context;
import android.util.Log;


public class ExportGazPricesInInternalStorage extends ExportGazPricesDecorator {
	
	public ExportGazPricesInInternalStorage(Context c) {
		super(c);
	}
	
	protected Boolean doInBackground(FileNameAndMapToSave... params){
		boolean isFine = false;
		String filename = params[0].getFilename();
		Log.d("gazolinePrices()", "saving as : "+filename);
		HashMap<String,Float> aMap = params[0].getMap();
		Log.d("gazolinePrices()","NOT casting like a mad man");
		
		try{
			FileOutputStream fos = aAppContext.openFileOutput(filename, Context.MODE_PRIVATE);
//			FileOutputStream fos2 = aAppContext.openFileOutput(filename+"2", Context.MODE_PRIVATE);
			
			Log.d("gazolinePrices()", "files opened");
//			ObjectComposer<JSONComposer<String>> comp = JSON.std
//					  .with(JSON.Feature.PRETTY_PRINT_OUTPUT)
//					  .composeString()
//					  .startObject();
//			for(String s:aMap.keySet()){
//				comp.put(s,aMap.get(s));
//			}
//
//			String json = comp.end().finish();
//			Log.d("gazolinePrices()","jsonComposer : "+json);
			
			JSON.std.write(aMap, fos);
//			JSON.std.write(json, fos2);
			fos.close();
//			fos2.close();
			isFine = true;
			Log.d("gazolinePrices()","isFine : "+isFine);
		}catch(JSONObjectException joe){
			isFine=false;
		}catch(FileNotFoundException fe){
			isFine=false;
		}catch(IOException ioe){
			isFine=false;
		}
		return isFine;
	}

}
