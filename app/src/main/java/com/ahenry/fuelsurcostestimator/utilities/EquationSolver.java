package com.ahenry.fuelsurcostestimator.utilities;

import java.util.Iterator;
import java.util.Set;

import org.jscience.mathematics.number.Rational;

import android.app.Fragment;
import android.os.AsyncTask;

import com.ahenry.fuelsurcostestimator.models.CarEquation;
import com.showyourwork.engine.Equation;
import com.showyourwork.engine.EquationBuilder;
import com.showyourwork.engine.EquationParser;

public class EquationSolver extends Fragment {
	
	private static CarEquation aCarEquation1;
	private static CarEquation aCarEquation2;
	
	private class EquationSolverAsync extends AsyncTask<Void, Void, Rational>{

		@Override
		protected Rational doInBackground(Void... params) {
			
			String aCompleteEq = aCarEquation1 + " = "+aCarEquation2;
			
			EquationBuilder b = new EquationBuilder();
			
        	new EquationParser(b).parse(aCompleteEq);
        	
        	Equation e = b.build();
        	e.evaluateUsingNewtonsMethod();
        	
        	Set<Rational> aSet = e.getAnswers();
        	
        	Rational r = Rational.valueOf(1, -1);
        	if(!aSet.isEmpty()){
        		Iterator<Rational> it = aSet.iterator();
        		r = it.next();
        	}
        	
        	return r;
		}
		
	}
	
	public static EquationSolver newInstance(CarEquation aCE1,CarEquation aCE2){
		EquationSolver aEqSolv = new EquationSolver();
		aCarEquation1 = aCE1;
		aCarEquation2 = aCE2;
		
		return aEqSolv;
	}
	
	public Rational findEqualityValuesBetweenEquations(){
		EquationSolverAsync aTask = new EquationSolverAsync();
		aTask.execute();
		Rational r = Rational.valueOf(1, -1);
		try{
			r = aTask.get();
		}catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}

}
