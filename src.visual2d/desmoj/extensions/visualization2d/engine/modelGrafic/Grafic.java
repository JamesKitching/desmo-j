package desmoj.extensions.visualization2d.engine.modelGrafic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;

/**
 * defines some constants used in this package
 * 
 * @version DESMO-J, Ver. 2.5.1e copyright (c) 2017
 * @author christian.mueller@th-wildau.de
 *         For information about subproject: desmoj.extensions.visualization2d
 *         please have a look at: 
 *         http://www.th-wildau.de/cmueller/Desmo-J/Visualization2d/ 
 * 
 *         Licensed under the Apache License, Version 2.0 (the "License"); you
 *         may not use this file except in compliance with the License. You may
 *         obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 *         Unless required by applicable law or agreed to in writing, software
 *         distributed under the License is distributed on an "AS IS" BASIS,
 *         WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *         implied. See the License for the specific language governing
 *         permissions and limitations under the License.
 *
 */
public interface Grafic {

	public  static final int		layer0						= JLayeredPane.DEFAULT_LAYER.intValue();
	public  static final Integer	LAYER_BACKGROUND			= new Integer(layer0 + 0);
	public  static final Integer	LAYER_BackGroundLine		= new Integer(layer0 + 1);
	public  static final Integer	LAYER_BackGroundElement		= new Integer(layer0 + 2);
	public  static final Integer	LAYER_PROCESS_LINE_LIST		= new Integer(layer0 + 3);
	public  static final Integer	LAYER_ROUTE_STATIC			= new Integer(layer0 + 4);
	public  static final Integer	LAYER_STATION				= new Integer(layer0 + 5);
	public  static final Integer	LAYER_LIST					= new Integer(layer0 + 6);
	public  static final Integer	LAYER_PROCESS				= new Integer(layer0 + 7);
	public  static final Integer	LAYER_RESOURCE				= new Integer(layer0 + 8);
	public  static final Integer	LAYER_STOCK					= new Integer(layer0 + 9);
	public  static final Integer	LAYER_Bin					= new Integer(layer0 + 10);
	public  static final Integer	LAYER_WAITING_QUEUE			= new Integer(layer0 + 11);
	public  static final Integer	LAYER_ENTITY				= new Integer(layer0 + 12);
	public  static final Integer	LAYER_STATISTIC				= new Integer(layer0 + 13);
	public  static final Integer	LAYER_ROUTE_DYNAMIC			= new Integer(layer0 + 19);
	public  static final Integer	LAYER_MARKER				= new Integer(layer0 + 20);
	public  static final Color		COLOR_BACKGROUND			= Color.white;
	public  static final Color		COLOR_FOREGROUND			= Color.black;
	public  static final Color		COLOR_BORDER				= Color.black;
	public  static final Color		COLOR_ZOOM_MARKER			= Color.gray;
	public  static final Color		COLOR_INFOPANE_MARKED		= Color.lightGray;
	public  static final Border		Border_Default				= BorderFactory.createEtchedBorder();
	public 	static final Color[] 	COLOR_SWITCH_BACKGROUND		= {new Color(255,230,230), new Color(230,255,255)};
	public 	static final Color[] 	COLOR_SWITCH_STOCK_BOUND	= {COLOR_FOREGROUND, new Color(200,0,0), new Color(0,200,0)};
	public  static final int		BOUNDARY_WIDTH				= 200; //Pixel
	public static final Font   		FONT_DEFAULT 				= new Font("SansSerif",Font.PLAIN,10);
	public static final Font   		FONT_SMALL 					= new Font("SansSerif",Font.PLAIN,8);
	public static final Font   		FONT_BIG 					= new Font("SansSerif",Font.PLAIN,20);
	public static final Dimension   STATION_DEFAULT_DIMENSION 	= new Dimension(20,5);

}
