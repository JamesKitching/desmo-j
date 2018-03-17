package desmoj.extensions.space2D.space;

import java.util.HashMap;
import java.util.Iterator;

/**
 * An attribute list is, well, a list of attributes.
 */
public class AttributeList {
	// ///////////// ATTRIBUTE ///////////////////////////////////////

	/**
	 * the attributes are stored in a HashMap (key = attribute name, value=
	 * attribute)
	 */
	private HashMap elements = new HashMap();

	// ///////////// KONSTRUKTOREN ///////////////////////////////////

	/**
	 * Constructs a new and empty attribute list.
	 */
	public AttributeList() {
		this.elements = new HashMap(2); // lieber mal mit ner kleinen Liste
		// anfangen!
	}

	/**
	 * Constructs a new attribute list out of the given attribute array.
	 */
	public AttributeList(Attribute[] attributes) {
		this();
		for (int i = 0; i < attributes.length; i++) {
			this.elements.put(attributes[i].getName(), attributes[i]);
		}
	}

	/**
	 * Copy constructor.
	 */
	public AttributeList(AttributeList attrList) {
		this(attrList.getAttributes());
	}

	// ///////////// METHODEN ////////////////////////////////////////

	/**
	 * returns <code>true</code> if this attribute list contains an attribute
	 * with the given name.
	 * 
	 * @param attributeName
	 *            the name of the attribute whose presence in this list is to be
	 *            tested
	 * @return <code>true</code> if this list contains the attribute with the
	 *         given name, <code>false</code> otherwise.
	 */
	public boolean containsAttribute(String attributeName) {
		return this.elements.containsKey(attributeName);
	}

	/**
	 * returns <code>true</code> if this attribute list contains the given
	 * attribute.
	 * 
	 * @param attribute
	 *            the attribute whose presence in this list is to be tested
	 * @return <code>true</code> if this list contains the attribute,
	 *         <code>false</code> otherwise.
	 */
	public boolean containsAttribute(Attribute attribute) {
		return this.elements.containsKey(attribute.getName());
	}

	/**
	 * tests if this attribute list contains no attributes.
	 * 
	 * @return <code>true</code> if this list is empty, <code>false</code>
	 *         otherwise.
	 */
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}

	/**
	 * returns the number of attributes in this list.
	 */
	public int size() {
		return this.elements.size();
	}

	/**
	 * returns the attribute with the given name or <code>null</code> if no
	 * such attribute exists in this list.
	 * 
	 * @param attributeName
	 *            the name of the attribute to be retrieved
	 */
	public Attribute getAttribute(String attributeName) {
		if (this.elements.containsKey(attributeName)) {
			return (Attribute) this.elements.get(attributeName);
		}
		return null;
	}

	/**
	 * stores the given attribute in this list. If the list already contains an
	 * attribute of the same name, the old attribute will be replaced by the new
	 * one.
	 * 
	 * @param attribute
	 *            the attribute to be stored in this list
	 */
	public void putAttribute(Attribute attribute) {
		this.elements.put(attribute.getName(), attribute);
	}

	/**
	 * removes the attribute with the given name from this list. This method
	 * does nothing if there is no such attribute in this list.
	 * 
	 * @param attributeName
	 *            the name of the attribute to be removed
	 */
	public AttributeList removeAttribute(String attributeName) {
		this.elements.remove(attributeName);
		return this;
	}

	/**
	 * returns the value of the attribute with the given name or
	 * <code>null</code> if there is no such attribute in this list.
	 * 
	 * @param attributeName
	 *            the name of the attribute whose value is to be retrieved
	 */
	public Object getAttributeValue(String attributeName) {
		if (this.elements.containsKey(attributeName))
			return ((Attribute) this.elements.get(attributeName)).getValue();
		return null;
	}

	/**
	 * returns the complete list of attributes as an array.
	 */
	public Attribute[] getAttributes() {
		Attribute[] attributes = new Attribute[this.elements.size()];
		int j = 0;
		for (Iterator i = this.elements.values().iterator(); i.hasNext();) {
			attributes[j++] = (Attribute) i.next();
		}
		return attributes;
	}

	/**
	 * stores all of the given attributes in this list. If the list already
	 * contains some of these attributes their old values are replaced with the
	 * new values.
	 */
	public void putAttributes(Attribute[] attributes) {
		for (int i = 0; i < attributes.length; i++) {
			this.elements.put(attributes[i].getName(), attributes[i]);
		}
	}

	/**
	 * stores all of the given attributes in this list. If the list already
	 * contains some of these attributes their old values are replaced with the
	 * new values.
	 */
	public void putAttributes(AttributeList attrList) {
		putAttributes(attrList.getAttributes());
	}

	/**
	 * returns a String representation of this attribute list.
	 */
	public String toString() {
		StringBuffer text = new StringBuffer("( ");
		if (this.elements.size() > 0) {
			for (Iterator i = this.elements.values().iterator(); i.hasNext();) {
				Attribute attr = (Attribute) i.next();
				text.append(attr.getName());
				text.append(": ");
				text.append(attr.getValue());
				text.append(" , ");
			}
			text.delete(text.length() - 3, text.length() - 1);
		}
		text.append(")");
		return text.toString();
	}

	// SKR	
	// Extended on 22.07.2010 
	// Reason: 		Created a further equals method to check the attribute list. Checking
	//				all the attributes by ignoring their order. Please note that these
	//				checks do not compare values (as defined in method equal of class Attribute).
	// Problems:	None.
	
	/**
	 * Overriding equals method.
	 */
	
	public boolean equals(Object arg0) {
		// If the object is from class AttributeList ...
		// ... than it could be the same. Cast it.	
		if (arg0 instanceof AttributeList) {
			AttributeList wsAl = (AttributeList) arg0;
			
			// Do they have the same number of observed attributes?
			// ... if no: they cannot be the same
			if (this.elements.values().size() != wsAl.elements.values().size()) {
				return false;
			} else {
			// ... if yes: we have to checked each and every attribute 
			// if they are identical. Please note that the order does NOT matter.	
				Attribute[] wsA = wsAl.getAttributes();
				Attribute[] cA = this.getAttributes();
	
				boolean prelimResult = true;
				
				for (int i = 0; i < wsA.length; i++) {
					boolean attrPrelimCheck = false;
					
					 for (int j = 0; j < cA.length; j++){
						 if (wsA[i].equals(cA[j])) {
							 attrPrelimCheck = true;
						 }
					 }
					 
					 if (attrPrelimCheck = false) {
						 prelimResult = false;
					 }
				}	 
				return prelimResult;
			}
		} else {
			return false;
		}	
	}	
// SKR
}
