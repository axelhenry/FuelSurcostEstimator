package com.ahenry.fuelsurcostestimator.models;

import java.util.Iterator;
import java.util.LinkedList;

public class XYSeriesGeneratorFromPlotList {

	private LinkedList<Double> aXList = new LinkedList<Double>();
	private LinkedList<Double> aYList = new LinkedList<Double>();
	
	public XYSeriesGeneratorFromPlotList(LinkedList<Plot> aList) {
		Iterator<Plot> it = aList.iterator();
		Plot p;
		while(it.hasNext()){
			p = it.next();
			aXList.addLast(p.getX());
			aYList.addLast(p.getY());
		}
	}
	
	public LinkedList<Double> getXSeries(){
		return aXList;
	}
	
	public LinkedList<Double> getYSeries(){
		return aYList;
	}
	
}
