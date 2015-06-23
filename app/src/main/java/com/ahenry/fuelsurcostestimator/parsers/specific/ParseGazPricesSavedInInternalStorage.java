package com.ahenry.fuelsurcostestimator.parsers.specific;

import java.io.InputStream;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.ahenry.fuelsurcostestimator.parsers.ParseGazPricesContextDecorator;
import com.ahenry.fuelsurcostestimator.utilities.ConstantUtilities;
import com.ahenry.fuelsurcostestimator.utilities.JSONUtilities;

public class ParseGazPricesSavedInInternalStorage extends ParseGazPricesContextDecorator {
	
	public ParseGazPricesSavedInInternalStorage(Context c){
		// TODO Auto-generated constructor stub
		super(c);
	}
	
	@Override
	protected HashMap<String, Float> doInBackground(String... params) {
		HashMap<String,Float> m = new HashMap<String, Float>();
		
//		Log.d("gazolinePrices()","and we're back again");
		Log.d("gazolinePrices()","opening file : "+params[0]);
		try{
			InputStream is = aAppContext.openFileInput(params[0]);
			Log.d("gazolinePrices()", "file "+params[0]+" opened");
//			Map<String,Object> map = JSON.std.mapFrom(is);
////			Log.d("gazolinePrices()","are we getting here ?");
////			Log.d("gazolinePrices()","size of keyset => "+map.keySet().size());
//			Matcher ma;
//			Float tmp;
//			for(String s:map.keySet()){
////				Log.d("gazolinePrices()","OH YEAH");
////				Log.d("gazolinePrices()","working with =>"+s);
//			//					 String tmp = "1.0";
//				Object o = map.get(s);
////				Log.d("gazolinePrices()","OBJECTION => "+o);
//			 
//			 
//				ma = pattern.matcher(o.toString());
//				String barePrice;
//				if(ma.find()){
//					barePrice = ma.group().replace(",", ".");
//			//							Log.d("gazolinePrices()","name "+ name);
//			//							Log.d("gazolinePrices()","barePrice " +barePrice);
//					tmp = Float.valueOf(barePrice);
//					m.put(s,tmp);
//					Log.d("gazolinePrices()","Saved => gaz : "+s+", price : "+tmp);
//				}
////				 Float tmp = 
////				 String tmp = (String)map.get(s);
////				 Log.d("gazolinePrices()","tmp is screwin around ? "+tmp);
////				 m.put(s, Float.valueOf(tmp));
//				
//			 }
			m = JSONUtilities.getMapFromInputStream(is, ConstantUtilities.floatPattern, "Saved");
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	}

}
