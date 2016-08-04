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

public class Variable {
	private String _Name;
	private String _Description;

	public Variable() {
		_Name = "";
		_Description = "";
	}

	/**
	 * Required parameters constructor
	 */
	public Variable(String name, String description) {
		_Name = name;
		_Description = description;
	}

	/**
	 * Deep copy
	 */
	public Variable(org.neurpheus.machinelearning.neuralnet.xml.Variable source) {
		_Name = source._Name;
		_Description = source._Description;
	}

	// This attribute is mandatory
	public void setName(String value) {
		_Name = value;
	}

	public String getName() {
		return _Name;
	}

	// This attribute is mandatory
	public void setDescription(String value) {
		_Description = value;
	}

	public String getDescription() {
		return _Description;
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		out.write(">\n");
		String nextIndent = indent + "	";
		if (_Name != null) {
			out.write(nextIndent);
			out.write("<name");	// NOI18N
			out.write(">");	// NOI18N
			org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _Name, false);
			out.write("</name>\n");	// NOI18N
		}
		if (_Description != null) {
			out.write(nextIndent);
			out.write("<description");	// NOI18N
			out.write(">");	// NOI18N
			org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _Description, false);
			out.write("</description>\n");	// NOI18N
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
			if (childNodeName == "name") {
				_Name = childNodeValue;
			}
			else if (childNodeName == "description") {
				_Description = childNodeValue;
			}
			else {
				// Found extra unrecognized childNode
			}
		}
	}

	public void validate() throws org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException {
		boolean restrictionFailure = false;
		// Validating property name
		if (getName() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getName() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "name", this);	// NOI18N
		}
		// Validating property description
		if (getDescription() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getDescription() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "description", this);	// NOI18N
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "name")
			setName((String)value);
		else if (name == "description")
			setDescription((String)value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Variable");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "name")
			return getName();
		if (name == "description")
			return getDescription();
		throw new IllegalArgumentException(name+" is not a valid property name for Variable");
	}

	public String nameSelf() {
		return "variable";
	}

	public String nameChild(Object childObj) {
		if (childObj instanceof String) {
			String child = (String) childObj;
			if (child == _Name) {
				return "name";
			}
			if (child == _Description) {
				return "description";
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
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof org.neurpheus.machinelearning.neuralnet.xml.Variable))
			return false;
		org.neurpheus.machinelearning.neuralnet.xml.Variable inst = (org.neurpheus.machinelearning.neuralnet.xml.Variable) o;
		if (!(_Name == null ? inst._Name == null : _Name.equals(inst._Name))) {
			return false;
		}
		if (!(_Description == null ? inst._Description == null : _Description.equals(inst._Description))) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Name == null ? 0 : _Name.hashCode());
		result = 37*result + (_Description == null ? 0 : _Description.hashCode());
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
