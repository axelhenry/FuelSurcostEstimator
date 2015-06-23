package com.ahenry.fuelsurcostestimator.models;

import java.util.HashMap;
import java.util.LinkedList;

import org.jscience.mathematics.number.Rational;

import android.os.Parcel;
import android.os.Parcelable;

import com.ahenry.fuelsurcostestimator.utilities.SortingUtilities;

public class CarEquation implements Parcelable{

	private String aEquation;
	private Float variablePart;
	private Float carPrice;
	
	public static final Parcelable.Creator<CarEquation> CREATOR = new Parcelable.Creator<CarEquation>() {

		public CarEquation createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new CarEquation(source);
		}

		public CarEquation[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CarEquation[size];
		}
		
		
	};
	
	public CarEquation(Parcel in){
		this.aEquation = in.readString();
		this.variablePart = in.readFloat();
		this.carPrice = in.readFloat();
	}
	
	public CarEquation(FragmentFieldsData aField, HashMap<String,Float> aMap) {
		StringBuffer aSB = new StringBuffer();
		
		
		variablePart = aMap.get(aField.getGazUsed()) * aField.getGazLitresPer100km() * aField.getMileagePerYear()/100 + aField.getInsuranceCostPerYear() + aField.getUsageCostPerYear();
		carPrice = aField.getCarPrice();
		Float variablePart10k = Float.valueOf(variablePart * 10000);
		Float carPrice10k = Float.valueOf(carPrice * 10000);
//		aSB.append(Float.toString(variablePart)).append("x").append(" + ").append(Float.toString(aField.getCarPrice()));
		//here we're using a little trick cause our equation solver work only with integer, so lame
		aSB.append(Integer.toString(variablePart10k.intValue())).append("x").append(" + ").append(Integer.toString(carPrice10k.intValue()));

		aEquation = aSB.toString();
	}
	
	public String toString(){
		return aEquation;
	}
	
	private LinkedList<Plot> getYValuesForInterval(int from, int to, float interval, Rational aSolutionValue){
		LinkedList<Plot> l = new LinkedList<Plot>();
		Plot p;
		Plot pSolution = new Plot(aSolutionValue.doubleValue(), carPrice + variablePart * aSolutionValue.doubleValue());
		boolean hasASolution = aSolutionValue != Rational.valueOf(1, -1) ? true : false;
		for(double d = from; d <= to; d+=interval){
			Double x = d;
			Double y = carPrice + d * variablePart;
			
			p = new Plot(x,y);
			l.addLast(p);
			
		}
		
		if (hasASolution){
			l.addLast(pSolution);
		}
		return l;		
	}
	
	public LinkedList<Plot> getOrderedValues(int from, int to, float interval, Rational aSolutionValue){
		return SortingUtilities.sortList(getYValuesForInterval(from, to, interval, aSolutionValue));
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(aEquation);
		dest.writeFloat(variablePart);
		dest.writeFloat(carPrice);
	}
	
	public double getYForGivenX(float f){
		return carPrice + f * variablePart;
	}

}
