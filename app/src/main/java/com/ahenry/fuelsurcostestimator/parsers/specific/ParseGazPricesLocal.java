package com.ahenry.fuelsurcostestimator.parsers.specific;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.ahenry.fuelsurcostestimator.R;
import com.ahenry.fuelsurcostestimator.parsers.ParseGazPricesContextDecorator;
import com.fasterxml.jackson.jr.ob.JSON;

public class ParseGazPricesLocal extends ParseGazPricesContextDecorator {
	
	public ParseGazPricesLocal(Context c){
		// TODO Auto-generated constructor stub
		super(c);
	}
	
	@Override
	protected HashMap<String, Float> doInBackground(String... params) {
		HashMap<String,Float> m = new HashMap<String, Float>();
		 
		 Log.d("gazolinePrices()","here we go");
		 InputStream is = aAppContext.getResources().openRawResource(R.raw.gazolineprices);
		 
		 try{
			 Map<String,Object> map = JSON.std.mapFrom(is);
			 for(String s:map.keySet()){
				 m.put(s, Float.valueOf((String)map.get(s)));
				 Log.d("gazolinePrices()","Local => gaz : "+s+", price : "+map.get(s));
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return m;
	}

}
