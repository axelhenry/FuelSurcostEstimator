package com.ahenry.fuelsurcostestimator.utilities.comparators;

import java.util.Comparator;

import com.ahenry.fuelsurcostestimator.models.Plot;

public class PlotComparator implements Comparator<Plot> {

	public int compare(Plot lhs, Plot rhs) {
		
		return Double.compare(lhs.getX(),rhs.getX());
	}

}
