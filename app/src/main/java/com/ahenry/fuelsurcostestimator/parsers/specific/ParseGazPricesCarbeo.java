package com.ahenry.fuelsurcostestimator.parsers.specific;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.regex.Matcher;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ahenry.fuelsurcostestimator.parsers.ParseGazPricesContextDecorator;
import com.ahenry.fuelsurcostestimator.utilities.ConstantUtilities;

import android.content.Context;
import android.util.Log;

public class ParseGazPricesCarbeo extends ParseGazPricesContextDecorator {
	
	public ParseGazPricesCarbeo(Context c) {
		// TODO Auto-generated constructor stub
		super(c);
	}
	
	@Override
	public HashMap<String, Float> doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		HashMap<String,Float> aMap = new HashMap<String, Float>();
		
		Log.d("gazolinePrices()","entering gazolinePrices()");
		try{
//			Log.d("gazolinePrices()","before using jsoup");
			String aUrl  = params[0];
//			Log.d("gazolinePrices()","aUrl : "+aUrl);
			Connection conn = Jsoup.connect(aUrl).userAgent("Mozilla");
//			Connection conn = Jsoup.connect("http://www.automobile-club.org/se-deplacer-mobilite/prix-des-carburants.html").userAgent("Mozilla");
//			Log.d("gazolinePrices()","got a conn to website \\o/");
			Document doc  = conn.get();
//			Log.d("gazolinePrices()","now we got a doc");
//			Elements topicList = doc.select(".officialPriceBe_col1");
			Elements topicList = doc.select(".officialPriceBe_odd");
//			Log.d("gazolinePrices()","content retrieved");
			
			
			
			
			for (Element topic : topicList) {
//				topic.child(index)
				String name = topic.child(0).text();
				String price = topic.child(1).text();
//				Log.d("gazolinePrices()", name);
//				Log.d("gazolinePrices()",price);
//				Log.d("gazolinePrices()","name and price extracted");
//				Matcher m = pattern.matcher(price);
				Matcher m = ConstantUtilities.floatPattern.matcher(price);
//				Log.d("gazolinePrices()","MATCHED");
//				Log.d("gazolinePrices()","found or not ? "+m.matches());
				String barePrice;
				if(m.find()){
					barePrice = m.group().replace(",", ".");
//					Log.d("gazolinePrices()","name "+ name);
//					Log.d("gazolinePrices()","barePrice " +barePrice);
					aMap.put(name, Float.valueOf(barePrice));
				}
//				while(m.find()){
//					Log.d("gazolinePrices()","iterate on group"+m.group());
//				}
//				Log.d("gazolinePrices()", name);
//				Log.d("gazolinePrices()",barePrice);
//				aMap.put(name, Float.valueOf(barePrice));
				
				//Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
			}
		}
		catch(MalformedURLException mue){
			mue.printStackTrace();
			Log.d("gazolinePrices()","exception reached : "+mue.getLocalizedMessage());
		}
		catch(HttpStatusException hse){
			hse.printStackTrace();
			Log.d("gazolinePrices()","exception reached : "+hse.getLocalizedMessage());
		}
		catch(UnsupportedMimeTypeException umte){
			umte.printStackTrace();
			Log.d("gazolinePrices()","exception reached : "+umte.getLocalizedMessage());
		}
		catch(SocketTimeoutException ste){
			ste.printStackTrace();
			Log.d("gazolinePrices()","exception reached : "+ste.getLocalizedMessage());
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			Log.d("gazolinePrices()","exception reached : "+ioe.getLocalizedMessage());
		}
		finally{
			Log.d("gazolinePrices()","finally reached");
//			return true;
		}
		return aMap;
	}
	
}
