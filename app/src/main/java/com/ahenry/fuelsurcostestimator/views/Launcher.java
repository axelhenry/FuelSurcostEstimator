package com.ahenry.fuelsurcostestimator.views;


import java.util.HashMap;
import java.util.List;

import org.jscience.mathematics.number.Rational;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ahenry.fuelsurcostestimator.GetGazPrices;
import com.ahenry.fuelsurcostestimator.R;
import com.ahenry.fuelsurcostestimator.SaveGazPrices;
import com.ahenry.fuelsurcostestimator.models.CarEquation;
import com.ahenry.fuelsurcostestimator.models.FragmentFieldsData;
import com.ahenry.fuelsurcostestimator.models.GazPricesMapAndUpdateStatus;
import com.ahenry.fuelsurcostestimator.utilities.EquationSolver;
import com.ahenry.fuelsurcostestimator.utilities.SortingUtilities;

public class Launcher extends Activity {

	private static boolean DEVELOPPING = false;
	
	private HashMap <String,Float> aMap = new HashMap<String,Float>();
	
	private int fCarColor = Color.parseColor("#005869");
	private int sCarColor = Color.parseColor("#00856A");
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);
        
        
         
        
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        IHM_CarInfo ihmFragment = IHM_CarInfo.newInstance("Blue",Color.BLUE);
//        ft.replace(R.id.carInfoHolder1, ihmFragment);
//        ft.commit();
        
//        GetGazPrices g = GetGazPrices.newInstance(getApplicationContext());
        GetGazPrices g = GetGazPrices.newInstance(this);
        GazPricesMapAndUpdateStatus aCurrentState = g.getGazPrices();
//        aMap = g.getGazPrices();
        aMap = aCurrentState.getHashMap();
        if(aCurrentState.getUpdateStatus()){
        	SaveGazPrices sgp = SaveGazPrices.newInstance(getApplicationContext());
        	sgp.saveLocallyInJSON("gazolineprices.json", aMap);
        }
        
        List<String> aSortedList = SortingUtilities.sortHashMap(aMap);
        
        FragmentManager fm = getFragmentManager();
        IHM_CarInfo ihmFragmentFCar = (IHM_CarInfo) fm.findFragmentById(R.id.carInfoHolder1);
        IHM_CarInfo ihmFragmentSCar = (IHM_CarInfo) fm.findFragmentById(R.id.carInfoHolder2);
        
        ihmFragmentFCar.setBackground(fCarColor);
        ihmFragmentFCar.initSpinnerValues(aSortedList);
//        Spinner spin1 = (Spinner) ihmFragmentFCar.getView().findViewById(R.id.spinner1);
//        SpinnerUtilities.addItemOnSpinner(getApplication(), spin1, aSortedList);
        
        ihmFragmentSCar.setBackground(sCarColor);  
        ihmFragmentSCar.initSpinnerValues(aSortedList);
//        Spinner spin2 = (Spinner) ihmFragmentSCar.getView().findViewById(R.id.spinner1);
//        SpinnerUtilities.addItemOnSpinner(getApplication(), spin2, aSortedList);
        
//        FragmentFieldsData dataFCar = ihmFragmentFCar.getFieldsData();
//        FragmentFieldsData dataSCar = ihmFragmentSCar.getFieldsData();
//        
//        if(dataFCar.isDataValid()==true && dataSCar.isDataValid()){
//        	CarEquation aEqFCar = new CarEquation(dataFCar);
//        	Log.d("gazolinePrices()","fst Eq : "+aEqFCar.toString());
//        	CarEquation aEqSCar = new CarEquation(dataSCar);
//        	Log.d("gazolinePrices()","snd Eq : "+aEqSCar.toString());
//        	
//        	String aCompleteEq = aEqFCar.toString()+" = "+aEqSCar.toString();
//        	Log.d("gazolinePrices()","comptete Eq : "+aCompleteEq);
//        	
//        	EquationBuilder b = new EquationBuilder();
//        	new EquationParser(b).parse(aCompleteEq);
//        	
//        	Equation e = b.build();
//        	e.evaluateUsingNewtonsMethod();
//        	
//        	Set<Rational> aSet = e.getAnswers();
//        }
        

	}
	
	public void estimateProfit(View v){
		FragmentManager fm = getFragmentManager();
        IHM_CarInfo ihmFragmentFCar = (IHM_CarInfo) fm.findFragmentById(R.id.carInfoHolder1);
        IHM_CarInfo ihmFragmentSCar = (IHM_CarInfo) fm.findFragmentById(R.id.carInfoHolder2);
//		Toast.makeText(this, "here we go, dayum", Toast.LENGTH_LONG).show();
        Log.d("gazolinePrices()","here we go, dayum");
		FragmentFieldsData dataFCar;
		FragmentFieldsData dataSCar; 
		if(DEVELOPPING){
			//just in case, if we don't want 
    		dataFCar = new FragmentFieldsData((float)20000, (float)5000, (float)6.5, "Sans Plomb 98", true);
    		dataSCar = new FragmentFieldsData((float)30000, (float)7500, (float)4.5, "Gazole", true);
    	}else{
    		dataFCar= ihmFragmentFCar.getFieldsData();
    		dataSCar= ihmFragmentSCar.getFieldsData();
    	}
		Log.d("gazolinePrices()","fCarToString" + dataFCar.toString());
        
        Log.d("gazolinePrices()","sCarToString" + dataSCar.toString());
        
        if(dataFCar.isDataValid()==true && dataSCar.isDataValid()){
        	Log.d("gazolinePrices()","dataIsValid (of course, not verified)");
        	CarEquation aEqFCar = new CarEquation(dataFCar,aMap);
        	Log.d("gazolinePrices()","fst Eq : "+aEqFCar.toString());
        	CarEquation aEqSCar = new CarEquation(dataSCar, aMap);
        	Log.d("gazolinePrices()","snd Eq : "+aEqSCar.toString());
        	
        	EquationSolver aEqSolver = EquationSolver.newInstance(aEqFCar, aEqSCar);
        	Rational r = aEqSolver.findEqualityValuesBetweenEquations();
        	
//    		Toast.makeText(this, Float.toString(r.floatValue()), Toast.LENGTH_LONG).show();
    		String rationalToString = r.toString();
    		Log.d("gazolinePrices()","rationalToString in first Activity : " +rationalToString);
    		Intent intent = new Intent(this,LauncherResults.class);
    		intent.putExtra("fCarColor", fCarColor);
    		intent.putExtra("carEquation1", aEqFCar);
    		intent.putExtra("sCarColor", sCarColor);
    		intent.putExtra("carEquation2", aEqSCar);
    		intent.putExtra("aRationalSolution", rationalToString);
    		startActivity(intent);
        }
	}

}
