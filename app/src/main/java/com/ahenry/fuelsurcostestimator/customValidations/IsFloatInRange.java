package com.ahenry.fuelsurcostestimator.customValidations;

import com.ahenry.fuelsurcostestimator.R;

import android.content.Context;
import de.psdev.formvalidations.validations.Validation;

public class IsFloatInRange implements Validation {
	
	private float mMin;
	private float mMax;
	
	public static IsFloatInRange build(int min, int max){
		return new IsFloatInRange(min, max);
	}
	
	private IsFloatInRange(int min, int max){
		this.mMin = (float) min;
		this.mMax = (float) max;
	}

	@Override
	public String getErrorMessage(Context context) {
		// TODO Auto-generated method stub
		return context.getString(R.string.float_validation_error_out_of_range);
	}

	@Override
	public boolean isValid(String text) {
		// TODO Auto-generated method stub
		try {
            final float value = Float.parseFloat(text);
            if ((value > mMin) && (value < mMax)) {
                return true;
            }
        } catch (final NumberFormatException ignored) {
        }
        return false;
	}

}
