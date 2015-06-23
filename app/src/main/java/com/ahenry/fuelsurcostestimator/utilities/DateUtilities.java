package com.ahenry.fuelsurcostestimator.utilities;

import org.joda.time.DateTime;
import org.joda.time.Interval;



public class DateUtilities {
	
	public static long getHoursBetween2DateTime(DateTime d1,DateTime d2){
		Interval i = new Interval(d1,d2);
		return i.toDuration().getStandardHours();
	}

}
