package com.ahenry.fuelsurcostestimator.parsers.specific;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.ahenry.fuelsurcostestimator.parsers.ParseGazPricesContextDecorator;
import com.ahenry.fuelsurcostestimator.utilities.ConstantUtilities;
import com.ahenry.fuelsurcostestimator.utilities.JSONUtilities;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

public class ParseGazPricesSavedOnWebsite extends ParseGazPricesContextDecorator {

	public ParseGazPricesSavedOnWebsite(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected HashMap<String, Float> doInBackground(String... params) {
		// TODO Auto-generated method stub
		HashMap<String,Float> m = new HashMap<String, Float>();
		
		Log.d("gazolinePrices()","getting prices from website, first step");
		try{
			Log.d("gazolinePrices()","get prices or die trying");
			String aUrl  = params[0];
//			Log.d("gazolinePrices()","aUrl : "+aUrl);
			HttpRequest request =  HttpRequest.get(aUrl);
			File f = null;
			if(request.ok()){
				f = File.createTempFile("gazolinePricesWeb", ".json");
				request.receive(f);
				FileInputStream fis = new FileInputStream(f);
				m = JSONUtilities.getMapFromInputStream(fis, ConstantUtilities.floatPattern, "Got from web");
			}
		}catch(HttpRequestException httpException){
			httpException.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		return m;
	}

}
