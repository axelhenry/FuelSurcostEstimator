package com.ahenry.fuelsurcostestimator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONComposer;
import com.fasterxml.jackson.jr.ob.JSONObjectException;
import com.fasterxml.jackson.jr.ob.comp.ObjectComposer;

public class HelloAndroidActivity extends Activity {

	
	final String regExp = "[0-9]+([,.][0-9]{1,3})?";
    final Pattern pattern = Pattern.compile(regExp);
    HashMap<String,Float> gazPrices = new HashMap<String, Float>();
    boolean isConnected = true;
    // DEFINE THIS AS A GLOBAL VARIABLE
    
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getGazolinePrices();
        Log.d("gazolinePrices()","first step");
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        Log.d("gazolinePrices()","main booster ignited, 3...");
        Log.d("gazolinePrices()","2...");
        Log.d("gazolinePrices()","1...");
        isConnected = checkConnection(this);
        Log.d("gazolinePrices()","Launched");
        Log.d("gazolinePrices()","isConnected : "+isConnected);
        
        
        /**
         * workflow
         * 1 - check connection
         * 2 - if connected get gaz prices from net, store them in hasmap
         * 3 - if hashmap's size < 1, get gaz prices from locally stored file
         * 4 - if hashmap's size < 1, get gaz prices from application's embedded gaz prices
         * 5 - proceed to calculation
         */
        if(!isConnected){
        	builder1
            .setMessage("Currently not connected to internet, go to settings or continue like this ?")
            .setCancelable(true)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) { 
                	Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                	startActivity(intent);
                }
             })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) { 
                    dialog.cancel();
                }
             })
            .setIcon(android.R.drawable.ic_dialog_alert);
        	
        	AlertDialog alert = builder1.create();
        	alert.show();
        	
        }
        GetLocalPricesTask getLocal = new GetLocalPricesTask();
        getLocal.execute();
        ParseCarbeoGazPricesTask aParsingTask = new ParseCarbeoGazPricesTask();
        aParsingTask.execute(new String[]{"http://www.carbeo.com/index.php/prixmoyens"});
        try{
        	gazPrices = aParsingTask.get();
        	
        	
        	SaveGazPricesTask aSaveTask = new SaveGazPricesTask();
        	FileNameAndMapToSave f = new FileNameAndMapToSave("gazpricesSaves", gazPrices);
        	aSaveTask.execute(new FileNameAndMapToSave[]{f});
        	
        	GetSavedLocalPricesTask aSavedTask = new GetSavedLocalPricesTask();
        	aSavedTask.execute("gazpricesSaves");
//        	aSavedTask.execute("gazpricesSaves2");
        	
        	
        	
        	
        	
        	List<String> aList = sortHashMap(gazPrices);
        	Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        	addItemOnSpinner(spin1, aList);
        	Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        	addItemOnSpinner(spin2, aList);
        	
        }catch(Exception e){e.printStackTrace();}
//        ParseCarbeoGazPricesTask2 aTask2 = new ParseCarbeoGazPricesTask2();
//        aTask2.execute(new String[]{"http://www.carbeo.com/index.php/prixmoyens"});
//        try{
//        	sortHashMap(getGazolinePricesFromCarbeaElements(aTask2.get()));
//        	
//        }catch(Exception e){e.printStackTrace();}
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(com.ahenry.fuelsurcostestimator.R.menu.main, menu);
	return true;
    }
    
    private class GetLocalPricesTask extends AsyncTask<Void, Void, HashMap<String,Float>>{

		@Override
		protected HashMap<String, Float> doInBackground(Void... params) {
			 HashMap<String,Float> m = new HashMap<String, Float>();
			 
			 Log.d("gazolinePrices()","here we go");
			 InputStream is = getResources().openRawResource(R.raw.gazolineprices);
			 
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
    
    private class GetSavedLocalPricesTask extends AsyncTask<String, Void, HashMap<String,Float>>{

		@Override
		protected HashMap<String, Float> doInBackground(String... params) {
			HashMap<String,Float> m = new HashMap<String, Float>();
			
//			Log.d("gazolinePrices()","and we're back again");
			Log.d("gazolinePrices()","opening file : "+params[0]);
			try{
				InputStream is = openFileInput(params[0]);
				Log.d("gazolinePrices()", "file "+params[0]+" opened");
				Map<String,Object> map = JSON.std.mapFrom(is);
//				Log.d("gazolinePrices()","are we getting here ?");
//				Log.d("gazolinePrices()","size of keyset => "+map.keySet().size());
				Matcher ma;
				Float tmp;
				for(String s:map.keySet()){
//					Log.d("gazolinePrices()","OH YEAH");
//					Log.d("gazolinePrices()","working with =>"+s);
				//					 String tmp = "1.0";
					Object o = map.get(s);
//					Log.d("gazolinePrices()","OBJECTION => "+o);
				 
				 
					ma = pattern.matcher(o.toString());
					String barePrice;
					if(ma.find()){
						barePrice = ma.group().replace(",", ".");
				//							Log.d("gazolinePrices()","name "+ name);
				//							Log.d("gazolinePrices()","barePrice " +barePrice);
						tmp = Float.valueOf(barePrice);
						m.put(s,tmp);
						Log.d("gazolinePrices()","Saved => gaz : "+s+", price : "+tmp);
					}
//					 Float tmp = 
//					 String tmp = (String)map.get(s);
//					 Log.d("gazolinePrices()","tmp is screwin around ? "+tmp);
//					 m.put(s, Float.valueOf(tmp));
					
				 }
			}catch(Exception e){
				e.printStackTrace();
			}
			return m;
		}
    	
    }
    
    private class FileNameAndMapToSave{
    	
    	
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
    
    private class SaveGazPricesTask extends AsyncTask<FileNameAndMapToSave, Void, Boolean>{

		@Override
		protected Boolean doInBackground(FileNameAndMapToSave... params) {
			boolean isFine = false;
			String filename = params[0].getFilename();
			Log.d("gazolinePrices()", "saving as : "+filename);
			HashMap<String,Float> aMap = params[0].getMap();
			Log.d("gazolinePrices()","NOT casting like a mad man");
			
			try{
				FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
				FileOutputStream fos2 = openFileOutput(filename+"2", Context.MODE_PRIVATE);
				
				Log.d("gazolinePrices()", "files opened");
				ObjectComposer<JSONComposer<String>> comp = JSON.std
						  .with(JSON.Feature.PRETTY_PRINT_OUTPUT)
						  .composeString()
						  .startObject();
				for(String s:aMap.keySet()){
					comp.put(s,aMap.get(s));
				}

				String json = comp.end().finish();
				Log.d("gazolinePrices()","jsonComposer : "+json);
				
				JSON.std.write(aMap, fos);
				JSON.std.write(json, fos2);
				fos.close();
				fos2.close();
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
    
    private class ParseCarbeoGazPricesTask2 extends AsyncTask<String, Void, Elements>{

		@Override
		protected Elements doInBackground(String... params) {
			// TODO Auto-generated method stub
			Elements topicList = null;
			try{
				Log.d("gazolinePrices()","before using jsoup");
				String aUrl  = params[0];
				Log.d("gazolinePrices()","aUrl : "+aUrl);
				Connection conn = Jsoup.connect(aUrl).userAgent("Mozilla");
//				Connection conn = Jsoup.connect("http://www.automobile-club.org/se-deplacer-mobilite/prix-des-carburants.html").userAgent("Mozilla");
				Log.d("gazolinePrices()","got a conn to website \\o/");
				Document doc  = conn.get();
				Log.d("gazolinePrices()","now we got a doc");
//				Elements topicList = doc.select(".officialPriceBe_col1");
				topicList = doc.select(".officialPriceBe_odd");
				Log.d("gazolinePrices()","content retrieved");
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
//				return true;
				return topicList;
			}
			
		}
    }
    
    public boolean checkConnection(Context context){
    	ConnectivityManager cm =
    	        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	 
    	NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    	
    	return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
    
    public void estimateProfit(View view) {
		EditText input = (EditText) findViewById(R.id.mileageV);
		Spinner s;
	   
	    float mileage = Float.valueOf(input.getText().toString());
	    
	    input = (EditText) findViewById(R.id.fCarPriceV);
	    float fCarPrice = Float.valueOf(input.getText().toString());
	    
	    input = (EditText) findViewById(R.id.fCarConsoV);
	    float fCarConso = Float.valueOf(input.getText().toString());
	    
	    s = (Spinner) findViewById(R.id.spinner1);
	    float fCarGazPrice = gazPrices.get((String) s.getSelectedItem());
	    
	    input = (EditText) findViewById(R.id.sCarPriceV);
	    float sCarPrice = Float.valueOf(input.getText().toString());
	    
	    input = (EditText) findViewById(R.id.sCarConsoV);
	    float sCarConso = Float.valueOf(input.getText().toString());
	    
	    s = (Spinner) findViewById(R.id.spinner2);
	    float sCarGazPrice = gazPrices.get((String) s.getSelectedItem());
	    
	    float diffPrice = fCarPrice - sCarPrice;
	    float diffCostGaz = fCarGazPrice * fCarConso - sCarGazPrice * sCarConso;
	    float diffEntretien = 0;
	    float km = mileage/100;
	    float result = diffPrice / ((km * diffCostGaz) + diffEntretien);
	    Log.d("gazolinePrices()",diffPrice +" / "+ "(("+km+" * "+diffCostGaz+") + "+diffEntretien+")");
	    Toast.makeText(this, String.valueOf(result), Toast.LENGTH_LONG).show();
	 }
    
    public HashMap<String,Double> getGazolinePricesFromCarbeaElements(Elements aElem){
    	if(aElem!=null){
			HashMap<String,Double> aMap = new HashMap<String, Double>();
			for (Element topic : aElem) {
		//			topic.child(index)
				String name = topic.child(0).text();
//				Float f = new Float(topic.child(1).text());
//				Float f = Float.valueOf(topic.child(1).text());
				String price = topic.child(1).text();
				Log.d("gazolinePrices()", "name : "+name+", price : "+price);
				aMap.put(name,1.2);
		    }
			return aMap;
    	}else return null;
    }
    
    public List<String> sortHashMap(HashMap<String, Float> aMap){
    	List<String> aList = new ArrayList<String>(aMap.keySet());
    	Collections.sort(aList);
    	for(String aS : aList){
    		Log.d("gazolinePrices()", "sorting : "+aS);
    	}
    	return aList;
    }
     
    public void addItemOnSpinner(Spinner aSpinner, List<String>aList){
    	ArrayAdapter<String> aDataArray = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,aList);
    	aDataArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	aSpinner.setAdapter(aDataArray);
    }
    
    private class ParseCarbeoGazPricesTask extends AsyncTask<String, Void, HashMap<String,Float>>{

		@Override
		protected HashMap<String, Float> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			HashMap<String,Float> aMap = new HashMap<String, Float>();
			
			Log.d("gazolinePrices()","entering gazolinePrices()");
			try{
//				Log.d("gazolinePrices()","before using jsoup");
				String aUrl  = params[0];
//				Log.d("gazolinePrices()","aUrl : "+aUrl);
				Connection conn = Jsoup.connect(aUrl).userAgent("Mozilla");
//				Connection conn = Jsoup.connect("http://www.automobile-club.org/se-deplacer-mobilite/prix-des-carburants.html").userAgent("Mozilla");
//				Log.d("gazolinePrices()","got a conn to website \\o/");
				Document doc  = conn.get();
//				Log.d("gazolinePrices()","now we got a doc");
//				Elements topicList = doc.select(".officialPriceBe_col1");
				Elements topicList = doc.select(".officialPriceBe_odd");
//				Log.d("gazolinePrices()","content retrieved");
				
				
				
				
				for (Element topic : topicList) {
//					topic.child(index)
					String name = topic.child(0).text();
					String price = topic.child(1).text();
//					Log.d("gazolinePrices()", name);
//					Log.d("gazolinePrices()",price);
//					Log.d("gazolinePrices()","name and price extracted");
					Matcher m = pattern.matcher(price);
//					Log.d("gazolinePrices()","MATCHED");
//					Log.d("gazolinePrices()","found or not ? "+m.matches());
					String barePrice;
					if(m.find()){
						barePrice = m.group().replace(",", ".");
//						Log.d("gazolinePrices()","name "+ name);
//						Log.d("gazolinePrices()","barePrice " +barePrice);
						aMap.put(name, Float.valueOf(barePrice));
					}
//					while(m.find()){
//						Log.d("gazolinePrices()","iterate on group"+m.group());
//					}
//					Log.d("gazolinePrices()", name);
//					Log.d("gazolinePrices()",barePrice);
//					aMap.put(name, Float.valueOf(barePrice));
					
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
//				return true;
			}
			return aMap;
		}
    	
    }

    public void getGazolinePrices(){
    	new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				Log.d("gazolinePrices()","entering gazolinePrices()");
				try{
					Log.d("gazolinePrices()","before using jsoup");
					Connection conn = Jsoup.connect("http://www.carbeo.com/index.php/prixmoyens").userAgent("Mozilla");
//					Connection conn = Jsoup.connect("http://www.automobile-club.org/se-deplacer-mobilite/prix-des-carburants.html").userAgent("Mozilla");
					Log.d("gazolinePrices()","got a conn to website \\o/");
					Document doc  = conn.get();
					Log.d("gazolinePrices()","now we got a doc");
//					Elements topicList = doc.select(".officialPriceBe_col1");
					Elements topicList = doc.select(".officialPriceBe_odd");
					Log.d("gazolinePrices()","content retrieved");
					for (Element topic : topicList) {
//						topic.child(index)
						String name = topic.child(0).text();
						String price = topic.child(1).text();
						Log.d("gazolinePrices()", name);
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
//					return true;
				}
			}}).start();
		}
//    	Log.d("gazolinePrices()","entering gazolinePrices()");
//		try{
//			Log.d("gazolinePrices()","before using jsoup");
////			Connection conn = Jsoup.connect("http://www.carbeo.com/index.php/prixmoyens").userAgent("Mozilla");
//			Connection conn = Jsoup.connect("http://www.automobile-club.org/se-deplacer-mobilite/prix-des-carburants.html").userAgent("Mozilla");
//			Log.d("gazolinePrices()","got a conn to website \\o/");
//			Document doc  = conn.get();
//			Log.d("gazolinePrices()","now we got a doc");
//			Elements topicList = doc.select("officialPriceBe_col1");
//			Log.d("gazolinePrices()","content retrieved");
//			for (Element topic : topicList) {
//				String data = topic.text();
//				Log.d("gazolinePrices()", data);
//				//Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
//			}
//		}
//		catch(MalformedURLException mue){
//			mue.printStackTrace();
//			Log.d("gazolinePrices()","exception reached : "+mue.getLocalizedMessage());
//		}
//		catch(HttpStatusException hse){
//			hse.printStackTrace();
//			Log.d("gazolinePrices()","exception reached : "+hse.getLocalizedMessage());
//		}
//		catch(UnsupportedMimeTypeException umte){
//			umte.printStackTrace();
//			Log.d("gazolinePrices()","exception reached : "+umte.getLocalizedMessage());
//		}
//		catch(SocketTimeoutException ste){
//			ste.printStackTrace();
//			Log.d("gazolinePrices()","exception reached : "+ste.getLocalizedMessage());
//		}
//		catch(IOException ioe){
//			ioe.printStackTrace();
//			Log.d("gazolinePrices()","exception reached : "+ioe.getLocalizedMessage());
//		}
//		finally{
//			Log.d("gazolinePrices()","finally reached");
//			return true;
//		}		
//}
}

