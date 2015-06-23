package com.ahenry.fuelsurcostestimator.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONObjectException;

public class JSONUtilities {

	public static HashMap<String, Float> getMapFromInputStream(InputStream is, Pattern p, String Tag){
		HashMap<String, Float> m = new HashMap<String, Float>();
		
		try{
			Map<String,Object> map = JSON.std.mapFrom(is);
	//		Log.d("gazolinePrices()","are we getting here ?");
	//		Log.d("gazolinePrices()","size of keyset => "+map.keySet().size());
			Matcher ma;
			Float tmp;
			for(String s:map.keySet()){
	//			Log.d("gazolinePrices()","OH YEAH");
	//			Log.d("gazolinePrices()","working with =>"+s);
			//					 String tmp = "1.0";
				Object o = map.get(s);
	//			Log.d("gazolinePrices()","OBJECTION => "+o);
			 
			 
				ma = p.matcher(o.toString());
				String barePrice;
				if(ma.find()){
					barePrice = ma.group().replace(",", ".");
			//							Log.d("gazolinePrices()","name "+ name);
			//							Log.d("gazolinePrices()","barePrice " +barePrice);
					tmp = Float.valueOf(barePrice);
					m.put(s,tmp);
					Log.d("gazolinePrices()",Tag+" => gaz : "+s+", price : "+tmp);
				}
			}
		}catch(JSONObjectException jsoe){
			jsoe.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		return m;
	}
	
}
