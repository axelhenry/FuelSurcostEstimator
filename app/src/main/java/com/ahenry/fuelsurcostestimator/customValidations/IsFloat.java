package com.ahenry.fuelsurcostestimator.customValidations;

import android.content.Context;

import com.ahenry.fuelsurcostestimator.R;

import de.psdev.formvalidations.validations.Validation;

public class IsFloat implements Validation {

	public static IsFloat build(){
		return new IsFloat();
	}
	
	private IsFloat(){
		
	}
	
	@Override
	public String getErrorMessage(Context context) {
		// TODO Auto-generated method stub
		return context.getString(R.string.float_validation_error);
	}

	@Override
	public boolean isValid(String text) {
		// TODO Auto-generated method stub
		return text.matches("([0-9]*\\.[0-9]+|[0-9]+)");
	}

}
