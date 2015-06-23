package com.ahenry.fuelsurcostestimator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jscience.mathematics.number.Rational;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.ahenry.fuelsurcostestimator.models.CarEquation;
import com.ahenry.fuelsurcostestimator.models.Plot;
import com.ahenry.fuelsurcostestimator.models.XYSeriesGeneratorFromPlotList;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.XPositionMetric;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.ui.YPositionMetric;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BarRenderer.BarWidthStyle;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XValueMarker;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.androidplot.xy.YValueMarker;

//class MyBarFormatter extends BarFormatter
//{
//    public MyBarFormatter(int fillColor, int borderColor)
//    {
//        super(fillColor, borderColor);
//    }
//
//    @Override public Class<? extends SeriesRenderer> getRendererClass()
//    {
//        return MyBarRenderer.class;
//    }
//
//    @Override public SeriesRenderer getRendererInstance(XYPlot plot)
//    {
//        return new MyBarRenderer(plot);
//    }
//}
//
//class MyBarRenderer extends BarRenderer<MyBarFormatter>
//{
//    public MyBarRenderer(XYPlot plot)
//    {
//        super(plot);
//    }
//
//    public MyBarFormatter getFormatter(int index, XYSeries series)
//    {
//        // return getFormatter(series);
//        if(index % 2 == 1)
//        {
//            return new MyBarFormatter(Color.BLUE, Color.TRANSPARENT);
//        }
//        else
//        {
//            return new MyBarFormatter(Color.RED, Color.TRANSPARENT);
//        }
//    }
//}

public class IHM_DisplayResults extends Fragment {

//	private static CarEquation aCarEquation1;
//	private static CarEquation aCarEquation2;
//	private static Rational aEquationResult;
//	
//	public static IHM_DisplayResults newInstance(CarEquation cE1, CarEquation cE2, Rational r){
//		IHM_DisplayResults aDisplayResult = new IHM_DisplayResults();
//		
//		aDisplayResult.aCarEquation1 = cE1;
//		aDisplayResult.aCarEquation2 = cE2;
//		aDisplayResult.aEquationResult = r;
//		
//		return aDisplayResult;
//	}
	
	
	private int fCarColor;
	private int sCarColor;
	private Paint linePaint;
	@InjectView(R.id.mySimpleXYPlot) XYPlot plotter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.displayresults_fragment, container, false);
		ButterKnife.inject(this, v);
		return v;
	}
	
	public void setColorsUsedInPlot(int fCarColor,int sCarColor){
		this.fCarColor= fCarColor;
		this.sCarColor = sCarColor;
	}
	
	private Paint getDottedLinePaint(int aColor){
		Paint p = new Paint();
		p.setColor(aColor);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setPathEffect(new DashPathEffect(new float[]{10,20}, 0));
		return p;
		
	}
	
	private Paint getTextPaint(int aColor){
		Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(aColor);
        return p;
	}
	
	public void drawPlot(CarEquation aCarEquation1, CarEquation aCarEquation2, Rational aEquationResult){
//		View v = getView();
		Log.d("gazolinePrices()","drawPlot");
		
//		XYPlot plotter = (XYPlot) v.findViewById(R.id.mySimpleXYPlot);
		//now, we want to draw a vertical dotted line to show the location of the intersection
		if(!aEquationResult.isNegative()){
			DecimalFormat df = new DecimalFormat("#.##");
			plotter.addMarker(new XValueMarker(aEquationResult.doubleValue(), df.format(aEquationResult.doubleValue()),
					new YPositionMetric(3, YLayoutStyle.ABSOLUTE_FROM_TOP), getDottedLinePaint(Color.LTGRAY), getTextPaint(Color.RED)));
			plotter.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1.0);
		}
		plotter.setDomainLabel(getView().getResources().getString(R.string.plot_domain_label));
		plotter.setRangeLabel(getView().getResources().getString(R.string.plot_range_label));
		
		LinkedList<Plot> aList = aCarEquation1.getOrderedValues(0, aEquationResult.intValue() +5 , 1, aEquationResult);
		XYSeriesGeneratorFromPlotList aXY = new XYSeriesGeneratorFromPlotList(aList);
		XYSeries aC1XYSeries = new SimpleXYSeries(aXY.getXSeries(), aXY.getYSeries(), getResources().getString(R.string.plot_firstCar_label));
		LineAndPointFormatter series1Format = new LineAndPointFormatter(fCarColor, fCarColor, null, null);
		plotter.addSeries(aC1XYSeries, series1Format);
		
		aList = aCarEquation2.getOrderedValues(0, aEquationResult.intValue() +5 , 1, aEquationResult);
		aXY = new XYSeriesGeneratorFromPlotList(aList);
		XYSeries aC2XYSeries = new SimpleXYSeries(aXY.getXSeries(), aXY.getYSeries(), getResources().getString(R.string.plot_secondCar_label));
		LineAndPointFormatter series2Format = new LineAndPointFormatter(sCarColor, sCarColor, null, null);
		plotter.addSeries(aC2XYSeries, series2Format);
		

		
	}

	public void drawBar(CarEquation aEqFCar, CarEquation aEqSCar, Rational r) {
		Log.d("gazolinePrices()","drawBar");
//		View v = getView();
//		XYPlot plotter = (XYPlot) v.findViewById(R.id.mySimpleXYPlot);
		Rational aSolution = r;
		SimpleXYSeries seriesResult;
		SimpleXYSeries seriesTotal;
//		LinkedList<Double> lResult = new LinkedList<Double>();
//		LinkedList<Double> lTotal = new LinkedList<Double>();
		//first case, we got a valid result (>=0)
//		boolean hasASolution = aSolution != Rational.valueOf(1, -1) ? true : false;
//		if(!hasASolution){
//			aSolution=Rational.valueOf("0");
//		}
//		lResult.add(aSolution.doubleValue());
//		lTotal.add(Double.valueOf(aSolution.intValue()+5));
		
		Double bound = aSolution.intValue()+ Double.valueOf("5");
		Log.d("gazolinePrices()","drawBar, solution : "+aSolution);
		Log.d("gazolinePrices()","drawBar, bound : "+bound);
//		lResult.addLast(aSolution.doubleValue());
//		lTotal.addLast(bound);
		List<Number> lResult = Arrays.asList(new Number[]{0,aSolution.doubleValue(),0});
		List<Number> lTotal = Arrays.asList(new Number[]{0,bound,0});
		
		if(!aSolution.isNegative()){
			DecimalFormat df = new DecimalFormat("#.##");
			plotter.addMarker(new YValueMarker(aSolution.doubleValue(), df.format(aSolution.doubleValue()),
					new XPositionMetric(3, XLayoutStyle.ABSOLUTE_FROM_LEFT),getDottedLinePaint(Color.LTGRAY),getTextPaint(Color.RED)));
		}
		seriesResult = new SimpleXYSeries(lResult, 
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, getResources().getString(R.string.plot_firstCar_label));
//		seriesResult = new SimpleXYSeries(Arrays.asList(new Number[]{0,-1,0}), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, getResources().getString(R.string.plot_firstCar_label));

		BarFormatter seriesResultFormat = new BarFormatter(fCarColor,Color.LTGRAY);
//		MyBarFormatter seriesSolutionFormat = new MyBarFormatter(Color.RED, Color.LTGRAY);
		plotter.addSeries(seriesResult, seriesResultFormat);
		
		seriesTotal = new SimpleXYSeries(lTotal, 
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, getResources().getString(R.string.plot_secondCar_label));
		BarFormatter seriesTotalFormat = new BarFormatter(sCarColor,Color.LTGRAY);
//		MyBarFormatter seriesTotalFormat = new MyBarFormatter(Color.BLUE,Color.LTGRAY);
		plotter.addSeries(seriesTotal, seriesTotalFormat);
		
		plotter.setDomainBoundaries(0.5, 1.5, BoundaryMode.FIXED);
//		plotter.getLayoutManager().remove(plotter.getDomainLabelWidget());
		plotter.setRangeBoundaries(0, bound.intValue(), BoundaryMode.FIXED);
		plotter.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1.0);
		plotter.setRangeLabel(getView().getResources().getString(R.string.plot_domain_label));
		
		BarRenderer renderer = (BarRenderer) plotter.getRenderer(BarRenderer.class);
//		renderer.setBarWidth(plotter.);
		renderer.setBarWidthStyle(BarWidthStyle.VARIABLE_WIDTH,60);
		plotter.getGraphWidget().getDomainLabelPaint().setColor(Color.TRANSPARENT);
		
		
		
		plotter.setGridPadding(20, 10, 20, 0);//left, top, right, bottom
		plotter.getGraphWidget().setMarginBottom(10);
		
	}
	
}


