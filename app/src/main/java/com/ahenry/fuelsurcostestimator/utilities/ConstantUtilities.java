package com.ahenry.fuelsurcostestimator.utilities;

import android.net.Uri;

import java.util.regex.Pattern;

public class ConstantUtilities {
	
	public static final String FLOATREGEX = "[0-9]+([,.][0-9]{1,3})?";
	public static final Pattern floatPattern = Pattern.compile(FLOATREGEX);
	public static String mServerUri = "http://localhost:5000/";
    public static String  mPublicKeyUri = mServerUri + "getKey";

}
