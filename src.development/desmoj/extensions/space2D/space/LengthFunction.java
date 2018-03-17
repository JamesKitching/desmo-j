package desmoj.extensions.space2D.space;

import desmoj.core.simulator.Units;
import desmoj.extensions.dimensions.Length;

/** A rating function using an attribute representing a length. */
public class LengthFunction implements RatingFunction {

	/** the name of the "length" attribute */
	String attrName;

	/** the unit of the "length" attribute */
	int lengthUnit;

	/**
	 * Constructs a length function that searches for an attribute named
	 * "length". Applies the length unit "m".
	 */
	public LengthFunction() {
		this.attrName = "length";
		this.lengthUnit = Units.M;
	}

	/**
	 * Constructs a length function that searches for an attribute with the
	 * given name. Applies the given length unit.
	 */
	public LengthFunction(String lengthAttributeName, int lengthUnit) {
		this.attrName = lengthAttributeName;
		this.lengthUnit = lengthUnit;
	}

	/**
	 * returns the length value in the length unit specified in the constructor.
	 */
	public double rate(AttributeList attributes) {
		double length = 0.0;
		// Wert des L�ngenattributes abfragen
		length = ((Length) attributes.getAttributeValue(attrName))
				.getValue(lengthUnit);
		return length;
	}

}
