package com.ahenry.fuelsurcostestimator;

import java.util.Calendar;
import java.util.HashMap;

import org.joda.time.DateTime;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;

import com.ahenry.fuelsurcostestimator.models.GazPricesMapAndUpdateStatus;
import com.ahenry.fuelsurcostestimator.parsers.specific.ParseGazPricesCarbeo;
import com.ahenry.fuelsurcostestimator.parsers.specific.ParseGazPricesLocal;
import com.ahenry.fuelsurcostestimator.parsers.specific.ParseGazPricesSavedInInternalStorage;
import com.ahenry.fuelsurcostestimator.parsers.specific.ParseGazPricesSavedOnWebsite;
import com.ahenry.fuelsurcostestimator.utilities.ConnectionChecker;
import com.ahenry.fuelsurcostestimator.utilities.DataValidationUtilities;
import com.ahenry.fuelsurcostestimator.utilities.DateUtilities;
import com.ahenry.fuelsurcostestimator.utilities.FileUtilities;
import com.ahenry.fuelsurcostestimator.views.ConnectionDialog;

public class GetGazPrices extends Fragment {

	public static Context aAppContext;
	public static Activity parentActivity;
	private boolean isConnected = true;

//	public static GetGazPrices newInstance(Context c) {
	public static GetGazPrices newInstance(Activity c) {
		GetGazPrices gGP = new GetGazPrices();
		GetGazPrices.parentActivity = c;
		GetGazPrices.aAppContext = c.getApplicationContext();
		return gGP;
	}

	public GazPricesMapAndUpdateStatus getGazPrices() {

		HashMap<String, Float> m = new HashMap<String, Float>();
		HashMap<String, Float> aReferenceMap = new HashMap<String, Float>();
		
		boolean lessThan1DaySinceUpdate = false;
		boolean updateGazPricesFromWeb  = false;

		Log.d("gazolinePrices()", "first step");

//		AlertDialog.Builder builder1 = new AlertDialog.Builder(aAppContext);
		// AlertDialog.Builder builder1 = new
		// AlertDialog.Builder(getActivity().getApplication());
		Log.d("gazolinePrices()", "main booster ignited, 3...");
		Log.d("gazolinePrices()", "2...");
		Log.d("gazolinePrices()", "1...");
		Log.d("gazolinePrices()", "appContext is null ? : "+ (aAppContext == null ? true : false));
		isConnected = ConnectionChecker.checkConnection(aAppContext);
		Log.d("gazolinePrices()", "Launched");
		Log.d("gazolinePrices()", "isConnected : " + isConnected);
		
		Log.d("gazolinePrices()","before getting raw resources");
		ParseGazPricesLocal pSL = new ParseGazPricesLocal(aAppContext);
		pSL.execute(new String[] { "" });
		try {
			aReferenceMap = pSL.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("gazolinePrices()","after getting raw resources");
		
		long timestampForInternalFile = FileUtilities.getTimeStampForFile(aAppContext, "gazolineprices.json");
		Log.d("gazolinePrices()","timeStamp : "+timestampForInternalFile);
		if(timestampForInternalFile > 0){
			Log.d("gazolinePrices()", "> 0, file exists");
			Calendar c = Calendar.getInstance();
			DateTime deviceCurrentTime = new DateTime(c.getTime());
			DateTime fileModificationTime = new DateTime(timestampForInternalFile);
			long hours = DateUtilities.getHoursBetween2DateTime(fileModificationTime, deviceCurrentTime);
			Log.d("gazolinePrices()","number of hours since last update : "+hours);
			lessThan1DaySinceUpdate = hours < 24 ? true : false;
//			c.getTime();
		}
		
		//ok it gets a little messy here, so : if timestamp = -1, means our file hasn't been found,
		
		updateGazPricesFromWeb = timestampForInternalFile == -1 ? true : ( lessThan1DaySinceUpdate != true ? true : false);
		Log.d("gazolinePrices()","will i update gaz prices from the web ? :" + updateGazPricesFromWeb);
		
		//workflow modification 1 - get prices from raw resources
//		Log.d("gazolinePrices()","before getting raw resources");
//		ParseGazPricesLocal pSL = new ParseGazPricesLocal(aAppContext);
//		pSL.execute(new String[] { "" });
//		try {
//			m = pSL.get();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Log.d("gazolinePrices()","after getting raw resources");
		
		//2 -
		if(updateGazPricesFromWeb){
			//get prices from web
			Log.d("gazolinePrices()","ok, we have to update");
			if (!isConnected) {
				// Log.d("gazolinePrices()","!isConnected : before builder");
				// builder1
				// .setMessage("Currently not connected to internet, go to settings or continue like this ?")
				// .setCancelable(true)
				// .setPositiveButton(android.R.string.yes, new
				// DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog, int which) {
				// Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
				// startActivity(intent);
				// isConnected=true;
				// }
				// })
				// .setNegativeButton(android.R.string.no, new
				// DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog, int which) {
				// dialog.cancel();
				// }
				// })
				// .setIcon(android.R.drawable.ic_dialog_alert);
				//
				// Log.d("gazolinePrices()","!isConnected : after builder");
				// AlertDialog alert = builder1.create();
				// Log.d("gazolinePrices()","!isConnected : after creating builder");
				// alert.show();
				// // alert.
				// Log.d("gazolinePrices()","!isConnected : after showing alert");
				FragmentManager fm = parentActivity.getFragmentManager();
				Log.d("gazolinePrices()","is this fucking fragment manager online ?");
				ConnectionDialog diag = ConnectionDialog.newInstance("test");
				diag.show(fm, "test");
			}
			isConnected = ConnectionChecker.checkConnection(aAppContext);
			Log.d("gazolinePrices()","i want to update, am i connected ? : "+isConnected);
			if (isConnected) {
//			if (isConnected) {
//				Log.d("gazolinePrices()","getting data from the web");
//				ParseGazPricesCarbeo pC = new ParseGazPricesCarbeo(null);
//				pC.execute(new String[] { "http://www.carbeo.com/index.php/prixmoyens" });
//				try {
//					m = pC.get();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				Log.d("gazolinePrices()","getting data from the web");
				ParseGazPricesSavedOnWebsite pC = new ParseGazPricesSavedOnWebsite(aAppContext);
				pC.execute(new String[] { "http://5.196.19.32/frenchPrices.json" });
				try {
					m = pC.get();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			m = DataValidationUtilities.isHashMapsDataValid(m, aReferenceMap);
		}
		if(m.size() < 1){
			if(timestampForInternalFile > 0){
//				ParseGazPricesSavedInInternalStorage pSL = new ParseGazPricesSavedInInternalStorage(aAppContext);
				Log.d("gazolinePrices()","getting internal resources");
				ParseGazPricesSavedInInternalStorage pSLIIS = new ParseGazPricesSavedInInternalStorage(aAppContext);
				pSLIIS.execute(new String[] { "gazolineprices.json" });
				try {
					m = pSLIIS.get();
				} catch (Exception e) {
					e.printStackTrace();
				}
//				if(!DataValidationUtilities.isHashMapsDataValid(m, aReferenceMap)){
//					
//				}
//			}else{
//				m = aReferenceMap;
			}
			m = DataValidationUtilities.isHashMapsDataValid(m, aReferenceMap);
			if(m.size() < 1){
				m = aReferenceMap;
			}
		}

		/**
		 * workflow 1 - check connection 2 - if connected get gaz prices from
		 * net, store them in hasmap 3 - if hashmap's size < 1, get gaz prices
		 * from locally stored file 4 - if hashmap's size < 1, get gaz prices
		 * from application's embedded gaz prices 5 - proceed to calculation
		 */
//		if (!isConnected) {
//			// Log.d("gazolinePrices()","!isConnected : before builder");
//			// builder1
//			// .setMessage("Currently not connected to internet, go to settings or continue like this ?")
//			// .setCancelable(true)
//			// .setPositiveButton(android.R.string.yes, new
//			// DialogInterface.OnClickListener() {
//			// public void onClick(DialogInterface dialog, int which) {
//			// Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
//			// startActivity(intent);
//			// isConnected=true;
//			// }
//			// })
//			// .setNegativeButton(android.R.string.no, new
//			// DialogInterface.OnClickListener() {
//			// public void onClick(DialogInterface dialog, int which) {
//			// dialog.cancel();
//			// }
//			// })
//			// .setIcon(android.R.drawable.ic_dialog_alert);
//			//
//			// Log.d("gazolinePrices()","!isConnected : after builder");
//			// AlertDialog alert = builder1.create();
//			// Log.d("gazolinePrices()","!isConnected : after creating builder");
//			// alert.show();
//			// // alert.
//			// Log.d("gazolinePrices()","!isConnected : after showing alert");
//			FragmentManager fm = parentActivity.getFragmentManager();
//			Log.d("gazolinePrices()","is this fucking fragment manager online ?");
//			ConnectionDialog diag = ConnectionDialog.newInstance("test");
//			diag.show(fm, "test");
//		}
//
//		if (isConnected) {
//			ParseGazPricesCarbeo pC = new ParseGazPricesCarbeo(null);
//			pC.execute(new String[] { "http://www.carbeo.com/index.php/prixmoyens" });
//			try {
//				m = pC.get();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		if (m.size() < 1) {
//			ParseGazPricesSavedInInternalStorage pSL = new ParseGazPricesSavedInInternalStorage(aAppContext);
//			pSL.execute(new String[] { "gazpricesSaves" });
//			try {
//				m = pSL.get();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		if (m.size() < 1) {
//			ParseGazPricesLocal pSL = new ParseGazPricesLocal(aAppContext);
//			pSL.execute(new String[] { "" });
//			try {
//				m = pSL.get();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

		return new GazPricesMapAndUpdateStatus(m, updateGazPricesFromWeb);

	}

}
