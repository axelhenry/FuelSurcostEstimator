package com.ahenry.fuelsurcostestimator.views;

import org.jscience.mathematics.number.Rational;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.ahenry.fuelsurcostestimator.IHM_DisplayResults;
import com.ahenry.fuelsurcostestimator.R;
import com.ahenry.fuelsurcostestimator.models.CarEquation;
import com.ahenry.fuelsurcostestimator.utilities.OrientationUtilities;

public class LauncherResults extends Activity {
	
	private int fCarColor;
	private int sCarColor;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcherresults_activity);
		
		Intent intent = getIntent();
		String aRationalString = intent.getStringExtra("aRationalSolution");
		
		Log.d("gazolinePrices()","rationalToString in second Activity : " +aRationalString);
		
		Rational r = Rational.valueOf(aRationalString);
		CarEquation aEqFCar = intent.getParcelableExtra("carEquation1");
		CarEquation aEqSCar = intent.getParcelableExtra("carEquation2");
		fCarColor = intent.getIntExtra("fCarColor", Color.BLUE);
		sCarColor = intent.getIntExtra("sCarColor", Color.RED);
		FragmentManager fm = getFragmentManager();
		 
		IHM_DisplayResults displayResults = (IHM_DisplayResults) fm.findFragmentById(R.id.resultsHolder);
		displayResults.setColorsUsedInPlot(fCarColor, sCarColor);
		 
		//check current screen orientation, to know which graph style to display (bar if portrait, graph if landscape)
		if(OrientationUtilities.isOrientationLandscape(this)){
			Log.d("gazolinePrices()","landscape");
			displayResults.drawPlot(aEqFCar, aEqSCar,r );
		}else{
			Log.d("gazolinePrices()","portrait");
			displayResults.drawBar(aEqFCar,aEqSCar,r);
		}
	}
	
}
