package com.ahenry.fuelsurcostestimator.views;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.ahenry.fuelsurcostestimator.R;
import com.ahenry.fuelsurcostestimator.customValidations.IsFieldNotEmpty;
import com.ahenry.fuelsurcostestimator.customValidations.IsFloat;
import com.ahenry.fuelsurcostestimator.customValidations.IsFloatInRange;
import com.ahenry.fuelsurcostestimator.models.FragmentFieldsData;
import com.ahenry.fuelsurcostestimator.utilities.SpinnerUtilities;

import de.psdev.formvalidations.EditTextErrorHandler;
import de.psdev.formvalidations.Field;
import de.psdev.formvalidations.Form;

//@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class IHM_CarInfo extends Fragment {
	@InjectView(R.id.mileageV) EditText fieldMileage;
	@InjectView(R.id.fCarPriceV) EditText fieldCarPrice;
	@InjectView(R.id.fCarConsoV) EditText fieldCarConso;
	@InjectView(R.id.spinner1) Spinner fieldSpinner;
	
	private Form mFormMileage;
	private Form mFormPrice;
	private Form mFormConsumption;
	private Context mContext;
	
	public static IHM_CarInfo newInstance(String aColorString, int aColorCode){
		IHM_CarInfo ihmFragment = new IHM_CarInfo();
		Bundle args = new Bundle();
		args.putString("aColorString", aColorString);
		args.putInt("aColorCode", aColorCode);
		ihmFragment.setArguments(args);
		return ihmFragment;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.displaycarinfo_fragment, container, false);
//		return inflater.inflate(R.layout.displaycarinfo_fragment, container, false);
		ButterKnife.inject(this, v);
		initValidationForm();
		mContext = v.getContext();
		return v;
	}
	
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	       if(savedInstanceState != null){
//	       // Get back arguments
//	//       String aColorString = getArguments().getString("aColorString");
//	       int aColorCode = getArguments().getInt("aColorCode", 0);
//	       Log.d("gazolinePrices()", "aColorCode : "+aColorCode);
//		}
//       
//   }
	
	public void setBackground(int aColor){
		Log.d("gazolinePrices()","we're here, let's try something stupid !");
		View v = getView();
		if(v!=null){
			Log.d("gazolinePrices()","not null, good mudafuka !");
//			v.setBackgroundColor(aColor);
//			v.setBackgroundColor(Color.rgb(255, 183, 197));

			GradientDrawable gd = (GradientDrawable) getResources().getDrawable(R.drawable.round_rect_shape);
//			gd.setColor(Color.rgb(255, 183, 197));
			gd.setColor(aColor);
			v.setBackground(gd);
			
		}else{
			Log.d("gazolinePrices()","DAYUM !!");
		}
	}
	
	public FragmentFieldsData getFieldsData(){
//		View v = getView();
		
//		EditText input = (EditText) v.findViewById(R.id.mileageV);
//	   
//	    float mileage = Float.valueOf(input.getText().toString());
//	    
//	    input = (EditText) v.findViewById(R.id.fCarPriceV);
//	    float fCarPrice = Float.valueOf(input.getText().toString());
//	    
//	    input = (EditText) v.findViewById(R.id.fCarConsoV);
//	    float fCarConso = Float.valueOf(input.getText().toString());
//	    
//	    String gaz = (String) ((Spinner) v.findViewById(R.id.spinner1)).getSelectedItem();
		
//		initValidationForm();
		float mileagePerYear = 0;
		float fCarPrice = 0;
		float fCarConsumption = 0;
		boolean isDataValid = submit();
		if(isDataValid){
			mileagePerYear = Float.valueOf(fieldMileage.getText().toString());
			fCarPrice = Float.valueOf(fieldCarPrice.getText().toString());
			fCarConsumption = Float.valueOf(fieldCarConso.getText().toString());
		}
		String gazUsed = fieldSpinner.getSelectedItem().toString();
		
		
		
//	    FragmentFieldsData data = new FragmentFieldsData(mileage, fCarPrice, fCarConso, gaz,true);
	    FragmentFieldsData data = new FragmentFieldsData(mileagePerYear, fCarPrice, fCarConsumption, gazUsed,isDataValid);
	    
	    return data;
	}
	
	private void initValidationForm(){
		mFormMileage = Form.create();
		mFormMileage.addField(Field.using(fieldMileage).validate(IsFieldNotEmpty.build()).validate(IsFloat.build()).validate(IsFloatInRange.build(1, 1000000)));
		
		mFormPrice = Form.create();
		mFormPrice.addField(Field.using(fieldCarPrice).validate(IsFieldNotEmpty.build()).validate(IsFloat.build()));
		
		mFormConsumption = Form.create();
		mFormConsumption.addField(Field.using(fieldCarConso).validate(IsFieldNotEmpty.build()).validate(IsFloat.build()).validate(IsFloatInRange.build(0, 10)));
		
		mFormMileage.errorHandler(new EditTextErrorHandler());
		mFormPrice.errorHandler(new EditTextErrorHandler());
		mFormConsumption.errorHandler(new EditTextErrorHandler());
				
	}
	
	public void initSpinnerValues(List<String> aList){
		SpinnerUtilities.addItemOnSpinner(mContext, fieldSpinner, aList);
	}
	
	private boolean submit(){
		boolean t = mFormPrice.isValid() & mFormConsumption.isValid();
		boolean t2 = t&mFormMileage.isValid();
//		return mForm.isValid();
		return t2;
	}
}
