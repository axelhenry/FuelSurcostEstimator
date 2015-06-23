package com.ahenry.fuelsurcostestimator.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.WindowManager;

//@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class OrientationUtilities {
	
	
	// TODO give wrong orientation on tablet
	public static boolean isOrientationLandscape(Activity a){
		WindowManager windowService = (WindowManager) a.getSystemService(Context.WINDOW_SERVICE);
		Point size = new Point();
		windowService.getDefaultDisplay().getSize(size);
		int width = size.x;
		int height = size.y;
		
		Log.d("gazolinePrices()","width : "+width);
		Log.d("gazolinePrices()","height : "+height);
//		int currentRotation = windowService.getDefaultDisplay().getRotation();
//		boolean isLandscape = false;
		boolean isLandscape = width > height ? true : false;
//		if(currentRotation == Surface.ROTATION_0 || currentRotation == Surface.ROTATION_180){
//			currentRotation = PORT
//			isLandscape = false;
//		}else{
//			isLandscape = true;
//		}
		return isLandscape;
	}

}
