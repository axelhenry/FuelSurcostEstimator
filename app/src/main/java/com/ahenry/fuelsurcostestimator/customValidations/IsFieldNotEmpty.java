package com.ahenry.fuelsurcostestimator.customValidations;

import com.ahenry.fuelsurcostestimator.R;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import de.psdev.formvalidations.validations.Validation;

public class IsFieldNotEmpty implements Validation {

	
	public static IsFieldNotEmpty build(){
		return new IsFieldNotEmpty();
	}
	
	private IsFieldNotEmpty(){
		
	}
	
	@Override
	public String getErrorMessage(Context context) {
		// TODO Auto-generated method stub
		return context.getString(R.string.field_empty_validation_error);
	}

	@Override
	public boolean isValid(String text) {
		// TODO Auto-generated method stub
		Log.d("gazolinePrices()","is field not empty");
		Log.d("gazolinePrices()","text : "+text);
		Log.d("gazolinePrices()","return : "+!TextUtils.isEmpty(text));
		return !TextUtils.isEmpty(text);
	}

}
