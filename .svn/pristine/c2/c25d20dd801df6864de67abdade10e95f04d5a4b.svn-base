package desmoj.extensions.space2D.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import desmoj.extensions.space2D.space.ComparableAttribute;

/**
 * colour coding provides a mapping of a range of values to a colour scale. This
 * is used for displaying an attribute in space.
 */
public class ColourCoding {

	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/** the colour scale. With scale[0] = baseColor. */
	private Color[] scale;

	/**
	 * the upper bounds of the value classes (intervals). Each value class is
	 * mapped to a colour in the colour scale.
	 */
	private Comparable[] upperBounds;

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * constructs a new colour coding for the given attribute distribution using
	 * the given base colour. The attribute values will be grouped into the
	 * specified number of classes, the colour scale will consist of the same
	 * number of elements.
	 */
	public ColourCoding(Color baseColor, int numOfClasses,
			HashMap attrDistribution) {
		this.scale = getColorScale(baseColor, numOfClasses);
//		this.scale = new Color[]{Color.green, Color.yellow, Color.red};
		
		Comparable[] values = extractAttributeValues(attrDistribution);
//		Comparable[] values = new Comparable[]{1.0, 0.5, 0.0};
		int[] bounds = getValueClasses(values, numOfClasses);
		
		this.upperBounds = new Comparable[bounds.length];
		for (int i = 0; i < this.upperBounds.length; i++) {
			this.upperBounds[i] = values[bounds[i] - 1];
		}

		// print(this.scale);
		// print(values);
		// print(bounds);
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * returns the color corresponding to the given value. The value should be
	 * in the range defined by the underlying attribute distribution.
	 */
	public Color getColour(Comparable value) {
		return this.scale[getValueClass(value)];
	}

	// -------------------------------------------------------------
	// Hilfsmethoden

	/**
	 * constructs the colour scale using the given base color as the first color
	 * and deriving the other (numOfColors-1) colors as less saturated colors
	 * than the base color.
	 */
	private Color[] getColorScale(Color baseColor, int numOfColors) {
		Color[] scale = new Color[numOfColors];
		float[] hsb = Color.RGBtoHSB(baseColor.getRed(), baseColor.getGreen(),
				baseColor.getBlue(), null);
		for (int i = 0; i < scale.length; i++) {
			scale[i] = Color.getHSBColor(hsb[0], hsb[1] - 0.3f * i, hsb[2]);
		}
		return scale;
	}

	/**
	 * groups the given values into the specified number of classes. The value
	 * list provides the different attribute values in descending order. The
	 * classes are built to hold an equal number of elements. Returns the upper
	 * bounds of the classes (NOT enclosed in the respective class) as index
	 * into the value list.
	 */
	private int[] getValueClasses(Comparable[] valueList, int numClasses) {
		// valueList enth�lt die ABsteigend sortierten Werte
		int numElements = valueList.length / numClasses;
		int restElements = valueList.length % numClasses;
		// Anzahl Elemente pro Klasse bestimmen
		int[] elems = new int[numClasses];
		for (int i = 0; i < elems.length; i++) {
			elems[i] = numElements;
		}
		for (int i = 0; i < restElements; i++) {
			elems[i]++; // Restelemente verteilen: auf die ersten restElements
			// Gruppen je 1 Element mehr
		}
		// Klassengrenzen (Indices) bestimmen
		int[] classes = new int[numClasses];
		classes[0] = elems[0];
		for (int i = 1; i < numClasses; i++) {
			classes[i] = classes[i - 1] + elems[i];
		}
		// classes[i] enth�lt die obere (nicht mehr enthaltene) Grenze des i-ten
		// Intervalls
		return classes;
	}

	/**
	 * returns the class the given value falls into. This is used as an index to
	 * this.scale to determine the corresponding colour.
	 */
	private int getValueClass(Comparable value) {
		int i = 0;
		while (i < this.upperBounds.length
				&& this.upperBounds[i].compareTo(value) > 0) {
			i++;
		}
		if (i == this.upperBounds.length)
			i--;
		return i;
	}

	/**
	 * extracts the unique attribute values from the given attribute
	 * distribution and sorts them into an array in descending order.
	 */
	private Comparable[] extractAttributeValues(HashMap attributeDistribution) {
		// die einzelnen Attribute aus der HashMap auslesen und in einen
		// ABsteigend sortierten Vector einf�gen (dabei doppelte auslassen!)
		Vector list = new Vector();
		int j = 0;
		for (Iterator i = attributeDistribution.values().iterator(); i
				.hasNext();) {
			insert(list, (Comparable) ((ComparableAttribute) i.next())
					.getValue());
		}
		// list enh�lt nun die ABsteigend sortierten UNTERSCHIEDLICHEN Werte
		Comparable[] temp = new Comparable[list.size()];
		list.copyInto(temp);
		return temp;
	}

	/**
	 * helper method: inserts the given Comparable value into the given list
	 * keeping the descending order and skipping multiple exemplars of the same
	 * value (algorithm: insert with binary search)
	 */
	private void insert(Vector list, Comparable value) {
		int left = 0;
		int right = list.size();
		Comparable temp;
		while (left < right) {
			int middle = (left + right) / 2;
			temp = (Comparable) list.get(middle);
			if (temp.compareTo(value) == 0)
				return; // Wert schon vorhanden
			if (temp.compareTo(value) > 0) {
				left = middle + 1;
			} else {
				right = middle;
			}
		}
		list.insertElementAt(value, right);
	}

	// ---------------------------------------------------------------- zum
	// Testen

	/** test helper method */
	public static void print(int[] bounds) {
		System.out.println("The class bounds:");
		for (int i = 0; i < bounds.length; i++) {
			System.out.print(bounds[i] + " ");
		}
		System.out.println("");
	}

	/** test helper method */
	public static void print(Comparable[] sorted) {
		System.out.println("The sorted attribute values:");
		for (int i = 0; i < sorted.length; i++) {
			System.out.print(sorted[i] + " - ");
		}
		System.out.println("");
	}

	/** test helper method */
	public static void print(Color[] scale) {
		System.out.println("The color scale (R G B):");
		for (int i = 0; i < scale.length; i++) {
			System.out.println(scale[i].getRed() + "\t" + scale[i].getGreen()
					+ "\t" + scale[i].getBlue());
		}
	}

	/** test method */
	public static void main(String[] args) {
		int num = 4;
		HashMap list = new HashMap();
		ComparableAttribute[] attr = new ComparableAttribute[8];
		attr[0] = new ComparableAttribute("sugar", new Double(23.9));
		attr[1] = new ComparableAttribute("sugar", new Double(3.9));
		attr[2] = new ComparableAttribute("sugar", new Double(2.3));
		attr[3] = new ComparableAttribute("sugar", new Double(14.2));
		attr[4] = new ComparableAttribute("sugar", new Double(3.8));
		attr[5] = new ComparableAttribute("sugar", new Double(23.9));
		attr[6] = new ComparableAttribute("sugar", new Double(230.9));
		attr[7] = new ComparableAttribute("sugar", new Double(12.9));
		// attr[8] = new ComparableAttribute("sugar", new Double(13.4));
		for (int i = 0; i < attr.length; i++) {
			list.put(new Integer(i), attr[i]);
		}

		ColourCoding c = new ColourCoding(Color.orange, num, list);
		double testV = 400;
		Color testC = c.getColour(new Double(testV));
		System.out.println("color for " + testV + " is " + testC);

	}

} /* end of class ColourCoding */