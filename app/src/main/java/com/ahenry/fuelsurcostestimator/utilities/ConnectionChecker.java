package com.ahenry.fuelsurcostestimator.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionChecker {
	
	public static boolean checkConnection(Context context){
		ConnectivityManager cm =
    	        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	 
    	NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    	
    	return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
	}

}
