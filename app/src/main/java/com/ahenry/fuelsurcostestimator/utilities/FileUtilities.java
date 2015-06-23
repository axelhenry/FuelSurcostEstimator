package com.ahenry.fuelsurcostestimator.utilities;

import java.io.File;

import android.content.Context;

public class FileUtilities {
	
	public static long getTimeStampForFile(Context aContext,String aName){
		long timestamp = -1;
		
		File f = new File(aContext.getFilesDir()+"/"+aName);
		if(f.exists()){
			timestamp = f.lastModified();
		}

		return timestamp;
		
	}

}
