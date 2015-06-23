package com.ahenry.fuelsurcostestimator.models;

public class FragmentFieldsData {
	
	private float mileagePerYear=0;
	private float carPrice=0;
	private float gazLitresPer100km=0;
	private String gazUsed="";
	private float usageCostPerYear = 0;
	private float insuranceCostPerYear = 0;
	private boolean isValid = false;
	
	public FragmentFieldsData(float mileagePerYear,float carPrice, float gazLitresPer100km, String gazUsed,boolean isValid) {
		this.isValid=isValid;
		if(isValid){
			this.mileagePerYear=mileagePerYear;
			this.carPrice = carPrice;
			this.gazLitresPer100km = gazLitresPer100km;
			this.gazUsed = gazUsed;
		}
	}


	public float getMileagePerYear() {
		return mileagePerYear;
	}

	public float getCarPrice() {
		return carPrice;
	}

	public float getGazLitresPer100km() {
		return gazLitresPer100km;
	}

	public String getGazUsed() {
		return gazUsed;
	}

	public float getUsageCostPerYear() {
		return usageCostPerYear;
	}

	public float getInsuranceCostPerYear() {
		return insuranceCostPerYear;
	}
	
	public void setInsuranceCostPerYear(float f){
		this.insuranceCostPerYear = f;
	}
	
	public void setUsageCostPerYear(float f){
		this.usageCostPerYear = f;
	}
	
	public boolean isDataValid(){
		return this.isValid;
	}
	
	public String toString(){
		StringBuffer aSB = new StringBuffer();
		aSB.append("mileagePerYear").append(" : ").append(Float.toString(mileagePerYear)).append("\n");
		aSB.append("carPrice").append(" : ").append(Float.toString(carPrice)).append("\n");
		aSB.append("gazLitresPer100km").append(" : ").append(Float.toString(gazLitresPer100km)).append("\n");
		aSB.append("gazUsed").append(" : ").append(gazUsed).append("\n");
		aSB.append("isValid").append(" : ").append(isValid).append("\n");
		aSB.append("usageCostPerYear").append(" : ").append(Float.toString(usageCostPerYear)).append("\n");
		aSB.append("insuranceCostPerYear").append(" : ").append(Float.toString(insuranceCostPerYear)).append("\n");
		
		return aSB.toString();
	}
	
	
}
