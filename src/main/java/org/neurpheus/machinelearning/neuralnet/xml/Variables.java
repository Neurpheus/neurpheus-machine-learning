/*
 * Neurpheus - Machine Learning Package
 *
 * Copyright (C) 2009, 2016 Jakub Strychowski
 *
 *  This library is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as published by the Free
 *  Software Foundation; either version 3.0 of the License, or (at your option)
 *  any later version.
 *
 *  This library is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 *  for more details.
 */

package org.neurpheus.machinelearning.neuralnet.xml;

public class Variables {
	private java.util.List _Variable = new java.util.ArrayList();	// List<Variable>

	public Variables() {
	}

	/**
	 * Deep copy
	 */
	public Variables(org.neurpheus.machinelearning.neuralnet.xml.Variables source) {
		for (java.util.Iterator it = source._Variable.iterator(); 
			it.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Variable element = (org.neurpheus.machinelearning.neuralnet.xml.Variable)it.next();
			_Variable.add((element == null) ? null : new org.neurpheus.machinelearning.neuralnet.xml.Variable(element));
		}
	}

	// This attribute is an array, possibly empty
	public void setVariable(org.neurpheus.machinelearning.neuralnet.xml.Variable[] value) {
		if (value == null)
			value = new Variable[0];
		_Variable.clear();
		for (int i = 0; i < value.length; ++i) {
			_Variable.add(value[i]);
		}
	}

	public void setVariable(int index, org.neurpheus.machinelearning.neuralnet.xml.Variable value) {
		_Variable.set(index, value);
	}

	public org.neurpheus.machinelearning.neuralnet.xml.Variable[] getVariable() {
		Variable[] arr = new Variable[_Variable.size()];
		return (Variable[]) _Variable.toArray(arr);
	}

	public java.util.List fetchVariableList() {
		return _Variable;
	}

	public org.neurpheus.machinelearning.neuralnet.xml.Variable getVariable(int index) {
		return (Variable)_Variable.get(index);
	}

	// Return the number of variable
	public int sizeVariable() {
		return _Variable.size();
	}

	public int addVariable(org.neurpheus.machinelearning.neuralnet.xml.Variable value) {
		_Variable.add(value);
		int positionOfNewItem = _Variable.size()-1;
		return positionOfNewItem;
	}

	/**
	 * Search from the end looking for @param value, and then remove it.
	 */
	public int removeVariable(org.neurpheus.machinelearning.neuralnet.xml.Variable value) {
		int pos = _Variable.indexOf(value);
		if (pos >= 0) {
			_Variable.remove(pos);
		}
		return pos;
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		out.write(">\n");
		String nextIndent = indent + "	";
		for (java.util.Iterator it = _Variable.iterator(); it.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Variable element = (org.neurpheus.machinelearning.neuralnet.xml.Variable)it.next();
			if (element != null) {
				element.writeNode(out, "variable", nextIndent);
			}
		}
		out.write(indent);
		out.write("</"+nodeName+">\n");
	}

	public void readNode(org.w3c.dom.Node node) {
		org.w3c.dom.NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			org.w3c.dom.Node childNode = children.item(i);
			String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
			if (childNodeName == "variable") {
				Variable aVariable = new org.neurpheus.machinelearning.neuralnet.xml.Variable();
				aVariable.readNode(childNode);
				_Variable.add(aVariable);
			}
			else {
				// Found extra unrecognized childNode
			}
		}
	}

	public void validate() throws org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException {
		boolean restrictionFailure = false;
		// Validating property variable
		for (int _index = 0; _index < sizeVariable(); ++_index) {
			org.neurpheus.machinelearning.neuralnet.xml.Variable element = getVariable(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "variable")
			addVariable((Variable)value);
		else if (name == "variable[]")
			setVariable((Variable[]) value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Variables");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "variable[]")
			return getVariable();
		throw new IllegalArgumentException(name+" is not a valid property name for Variables");
	}

	public String nameSelf() {
		return "variables";
	}

	public String nameChild(Object childObj) {
		if (childObj instanceof Variable) {
			Variable child = (Variable) childObj;
			int index = 0;
			for (java.util.Iterator it = _Variable.iterator(); 
				it.hasNext(); ) {
				org.neurpheus.machinelearning.neuralnet.xml.Variable element = (org.neurpheus.machinelearning.neuralnet.xml.Variable)it.next();
				if (child == element) {
					return "variable."+index;
				}
				++index;
			}
		}
		return null;
	}

	/**
	 * Return an array of all of the properties that are beans and are set.
	 */
	public java.lang.Object[] childBeans(boolean recursive) {
		java.util.List children = new java.util.LinkedList();
		childBeans(recursive, children);
		java.lang.Object[] result = new java.lang.Object[children.size()];
		return (java.lang.Object[]) children.toArray(result);
	}

	/**
	 * Put all child beans into the beans list.
	 */
	public void childBeans(boolean recursive, java.util.List beans) {
		for (java.util.Iterator it = _Variable.iterator(); it.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Variable element = (org.neurpheus.machinelearning.neuralnet.xml.Variable)it.next();
			if (element != null) {
				if (recursive) {
					element.childBeans(true, beans);
				}
				beans.add(element);
			}
		}
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof org.neurpheus.machinelearning.neuralnet.xml.Variables))
			return false;
		org.neurpheus.machinelearning.neuralnet.xml.Variables inst = (org.neurpheus.machinelearning.neuralnet.xml.Variables) o;
		if (sizeVariable() != inst.sizeVariable())
			return false;
		// Compare every element.
		for (java.util.Iterator it = _Variable.iterator(), it2 = inst._Variable.iterator(); 
			it.hasNext() && it2.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Variable element = (org.neurpheus.machinelearning.neuralnet.xml.Variable)it.next();
			org.neurpheus.machinelearning.neuralnet.xml.Variable element2 = (org.neurpheus.machinelearning.neuralnet.xml.Variable)it2.next();
			if (!(element == null ? element2 == null : element.equals(element2))) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Variable == null ? 0 : _Variable.hashCode());
		return result;
	}

}


/*
		The following schema file has been used for generation:

<?xml version='1.0' encoding='UTF-8'?>
<!ELEMENT neural-network 
    (name?, 
    description?, 
    creation-date?, 
    modification-date?, 
    variables, 
    layers, 
    input-layer, 
    output-layer,
    synapses
    )>
<!ATTLIST neural-network version CDATA #REQUIRED>
<!ELEMENT name (#PCDATA)>
<!ELEMENT description (#PCDATA)>
<!ELEMENT creation-date (#PCDATA)>
<!ELEMENT modification-date (#PCDATA)>

<!ELEMENT variables (variable*)>
<!ELEMENT variable (name, description)>

<!ELEMENT layers (layer+)>
<!ELEMENT layer (type, number-of-neurons)>
<!ATTLIST layer id ID #REQUIRED>
<!ELEMENT type (#PCDATA)>
<!ELEMENT number-of-neurons (#PCDATA)>

<!ELEMENT input-layer EMPTY>
<!ATTLIST input-layer layerId IDREF #REQUIRED>

<!ELEMENT output-layer EMPTY>
<!ATTLIST output-layer layerId IDREF #REQUIRED>

<!ELEMENT synapses (synapse*)>
<!ELEMENT synapse (type, source-layer, destination-layer)>
<!ATTLIST synapse id ID #REQUIRED>
<!ELEMENT source-layer EMPTY>
<!ATTLIST source-layer layerId IDREF #REQUIRED>
<!ELEMENT destination-layer EMPTY>
<!ATTLIST destination-layer layerId IDREF #REQUIRED>

*/
