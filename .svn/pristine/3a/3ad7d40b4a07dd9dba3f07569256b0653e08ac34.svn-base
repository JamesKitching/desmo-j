package desmoj.extensions.experimentation.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Vector;
import java.util.Observer;
import java.util.Observable;

import desmoj.core.statistic.TimeSeries;
import desmoj.core.util.ExperimentListener;
import desmoj.core.util.SimRunEvent;


/**
 * A simple time series plotter that can be displayed in the experiment
 * launcher.
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @author Nicolas Knaak
 * @author Philip Joschko
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
public class TimeSeriesPlotter extends GraphicalObserver implements
		ExperimentListener, ChartOwner, Observer {

	/** The chart panel (window frame) and observers user interface */
	private ChartPanel myGUI;

	/** The data set to be plotted */
	private TimeSeries myData[];

	private int elements[];

	/**
	 * Creates a new TimeSeries plotter from the given array of TimeSeries objects.
	 * 
	 * @param title
	 *            window title
	 * @param context
	 *            the context to show this observer in
	 * @param data
	 *            array of TimeSeries data to display
	 * @param winWidth
	 *            width of window
	 * @param winHeight
	 *            height of window
	 */
	public TimeSeriesPlotter(String title, GraphicalObserverContext context,
			TimeSeries data[], int winWidth, int winHeight) {
		this(title, context, data[0], winWidth, winHeight);
		for(int i=1; i<data.length; i++)
			this.addTimeSeries(data[i]);
	
	}

	/**
	 * Creates a new TimeSeries plotter from the given TimeSeries object.
	 * This is the main constructor. The other constructors finally call this one.
	 * 
	 * @param title
	 *            window title
	 * @param context
	 *            the context to show this observer in
	 * @param data
	 *            TimeSeries to display
	 * @param winWidth
	 *            width of window
	 * @param winHeight
	 *            height of window
	 */
	public TimeSeriesPlotter(String title, GraphicalObserverContext context,
			TimeSeries data, int winWidth, int winHeight) {
		super(title, context);

		myData = new TimeSeries[1];
		myData[0]=data;
		myData[0].connectToPlotter(this);
		elements = new int[1];
		elements[0] = 0;

		myGUI = new ChartPanel(this, title, "Time", myData[0].getName());
		register();
		setVisible(true);
		setSize(winWidth, winHeight);
	}
	
	/**
	 * Creates a new TimeSeries plotter from the given TimeSeries object.
	 * 
	 * @param title
	 *            window title
	 * @param context
	 *            the context to show this observer in
	 * @param data
	 *            data of time series to display
	 * @param winWidth
	 *            width of window
	 * @param winHeight
	 *            height of window
	 * @param xLocation
	 *            horizontal position of window
	 * @param yLocation
	 *            vertical position of window
	 */
	public TimeSeriesPlotter(String title, GraphicalObserverContext context,
			TimeSeries data, int winWidth, int winHeight, int xLocation,
			int yLocation) {
		this(title, context, data, winWidth, winHeight);
		setLocation(xLocation, yLocation);
	}

	/**
	 * Creates a new TimeSeries plotter from the given array of TimeSeries objects.
	 * 
	 * @param title
	 *            window title
	 * @param context
	 *            the context to show this observer in
	 * @param data
	 *            data of time series to display
	 * @param winWidth
	 *            width of window
	 * @param winHeight
	 *            height of window
	 * @param xLocation
	 *            horizontal position of window
	 * @param yLocation
	 *            vertical position of window
	 */
	public TimeSeriesPlotter(String title, GraphicalObserverContext context,
			TimeSeries data[], int winWidth, int winHeight, int xLocation,
			int yLocation) {
		this(title, context, data, winWidth, winHeight);
		setLocation(xLocation, yLocation);
	}
	
	/**
	 * Adds another TimeSeries statistic object which will be displayed in the chart.
	 * @param newData Another TimeSeries statistic object which will be displayed in the chart.
	 */
	public void addTimeSeries(TimeSeries newData) {
		int old_elements[] = elements;
		TimeSeries old_dataArray[] = myData;
		int n=old_elements.length;
		
		if(n==1) {
			myGUI.addLegend(myData[0].getName());
			myGUI.set_ylabel("Value");
		}
		
		elements=new int[n+1];
		myData=new TimeSeries[n+1];
		
		for(int i=0; i <n; i++) {
			elements[i]=old_elements[i];
			myData[i]=old_dataArray[i];
		}
		elements[n]=0;
		myData[n]=newData;
		myData[n].connectToPlotter(this);
		myGUI.addLegend(myData[n].getName());
		
	}
	
	/**
	 * Returns the plotter's GUI (a JFreeCHartPanel)
	 * 
	 * @return a JFreeChart panel to plot the time series
	 */
	public Component getGUI() {
		return myGUI;
	}

	/**
	 * Called when the experiment is (re)started. Nothing happens
	 * 
	 * @param e
	 *            a SimRunEvent
	 */
	public void experimentRunning(SimRunEvent e) {
	}

	/**
	 * Called when the experiment is stopped. Update of display
	 * 
	 * @param e
	 *            a SimRunEvent
	 */
	public void experimentStopped(SimRunEvent e) {
		myGUI.redrawChart();
	}

	/**
	 * Called when the experiment is paused. Update of display
	 * 
	 * @param e
	 *            a SimRunEvent
	 */
	public void experimentPaused(SimRunEvent e) {
		experimentStopped(e);
	}
	
	/**
	 * Draws the values given by the TimeSeries statistic object into the 
	 * chart. This method is automatically invoked by the chart object, if it has
	 * to repaint.
	 */
	public void drawChart(Graphics g) {
		double newTime;
		double newData;
		
		for(int series=0; series<myData.length; series++) {
			g.setColor(ChartPanel.color(series));
			Vector<Double> timeValues=myData[series].getTimeValues();
			Vector<Double> dataValues=myData[series].getDataValues();
			if(timeValues!=null && dataValues!=null) {
				if(timeValues.size()>=2) {
					double lastData=dataValues.firstElement();
					double lastTime=timeValues.firstElement();
					for(int pair=1; pair<timeValues.size(); pair++) {
						newData = dataValues.elementAt(pair);
						newTime = timeValues.elementAt(pair);
						myGUI.drawLine(g, lastTime, lastData, newTime, lastData);
						myGUI.drawLine(g, newTime, lastData, newTime, newData);
						lastData=newData;
						lastTime=newTime;
					}
				}
			}
		}
	}
	
	/**
	 * The update method (required by interface 'Observer') will be called, if
	 * the TimeSeries produces new values.
	 */
	public void update(Observable x, Object y) {
		updatePlotter();
	}
	
	/**
	 * This method updates the chart. The chart will only be repainted if a new scaling is required.
	 * If no rescaling is required, then only the new values are painted.
	 * Of course this is much faster.
	 * 
	 * Because the TimeSeriesPlotter has no bench marks for scaling at the beginning,
	 * you have to call this method at least one time, when the upper and lower limits are known.
	 */
	public void updatePlotter() {
		Vector<Double> dataValues;
		Vector<Double> timeValues;
		boolean redraw=false;
		for(int series=0; series<myData.length; series++) {
			dataValues=myData[series].getDataValues();
			timeValues=myData[series].getTimeValues();
			if(dataValues==null || timeValues==null)
				continue;
			
			if(elements[series]==0 && dataValues.size()>0) {
				myGUI.setMax_x(timeValues.elementAt(0));
				myGUI.setMin_x(timeValues.elementAt(0));
				myGUI.setMax_y(dataValues.elementAt(0));
				myGUI.setMin_y(dataValues.elementAt(0));
				elements[series]=1;
			}
				
			for(int i=elements[series]; i<dataValues.size(); i++) {
				redraw=redraw || myGUI.testValue(timeValues.elementAt(i), dataValues.elementAt(i).doubleValue());
			}
			if (myGUI.isShowing()) {
				if(dataValues.size()>=2) {
					if (redraw) {
						myGUI.redrawChart();
					}
					else {
						try {
							double newData;
							double newTime;
							Color color = ChartPanel.color(series);
							double lastData=dataValues.elementAt(elements[series]-1).doubleValue();
							double lastTime=timeValues.elementAt(elements[series]-1).doubleValue();
							for(int pair=elements[series]; pair<timeValues.size(); pair++) {
								newData = dataValues.elementAt(pair).doubleValue();
								newTime = timeValues.elementAt(pair).doubleValue();
								myGUI.drawLine(color, lastTime, lastData, newTime, lastData);
								myGUI.drawLine(color, newTime, lastData, newTime, newData);
								lastData=newData;
								lastTime=newTime;
							}
						} catch (Exception e) {} // the window was closed while drawing the chart	

					}
				}
			}
			elements[series]=dataValues.size();
		}
	}
}