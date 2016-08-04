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

public class Layer {
	private java.lang.String _Id;
	private String _Type;
	private String _NumberOfNeurons;

	public Layer() {
		_Id = "";
		_Type = "";
		_NumberOfNeurons = "";
	}

	/**
	 * Required parameters constructor
	 */
	public Layer(java.lang.String id, String type, String numberOfNeurons) {
		_Id = id;
		_Type = type;
		_NumberOfNeurons = numberOfNeurons;
	}

	/**
	 * Deep copy
	 */
	public Layer(org.neurpheus.machinelearning.neuralnet.xml.Layer source) {
		_Id = source._Id;
		_Type = source._Type;
		_NumberOfNeurons = source._NumberOfNeurons;
	}

	// This attribute is mandatory
	public void setId(java.lang.String value) {
		_Id = value;
	}

	public java.lang.String getId() {
		return _Id;
	}

	// This attribute is mandatory
	public void setType(String value) {
		_Type = value;
	}

	public String getType() {
		return _Type;
	}

	// This attribute is mandatory
	public void setNumberOfNeurons(String value) {
		_NumberOfNeurons = value;
	}

	public String getNumberOfNeurons() {
		return _NumberOfNeurons;
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		// id is an attribute
		if (_Id != null) {
			out.write(" id");	// NOI18N
			out.write("='");	// NOI18N
			org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _Id, true);
			out.write("'");	// NOI18N
		}
		out.write(">\n");
		String nextIndent = indent + "	";
		if (_Type != null) {
			out.write(nextIndent);
			out.write("<type");	// NOI18N
			out.write(">");	// NOI18N
			org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _Type, false);
			out.write("</type>\n");	// NOI18N
		}
		if (_NumberOfNeurons != null) {
			out.write(nextIndent);
			out.write("<number-of-neurons");	// NOI18N
			out.write(">");	// NOI18N
			org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _NumberOfNeurons, false);
			out.write("</number-of-neurons>\n");	// NOI18N
		}
		out.write(indent);
		out.write("</"+nodeName+">\n");
	}

	public void readNode(org.w3c.dom.Node node) {
		if (node.hasAttributes()) {
			org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
			org.w3c.dom.Attr attr;
			java.lang.String attrValue;
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("id");
			if (attr != null) {
				attrValue = attr.getValue();
			} else {
				attrValue = null;
			}
			_Id = attrValue;
		}
		org.w3c.dom.NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			org.w3c.dom.Node childNode = children.item(i);
			String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
			if (childNodeName == "type") {
				_Type = childNodeValue;
			}
			else if (childNodeName == "number-of-neurons") {
				_NumberOfNeurons = childNodeValue;
			}
			else {
				// Found extra unrecognized childNode
			}
		}
	}

	public void validate() throws org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException {
		boolean restrictionFailure = false;
		// Validating property id
		if (getId() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getId() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "id", this);	// NOI18N
		}
		// Validating property type
		if (getType() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getType() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "type", this);	// NOI18N
		}
		// Validating property numberOfNeurons
		if (getNumberOfNeurons() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getNumberOfNeurons() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "numberOfNeurons", this);	// NOI18N
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "id")
			setId((java.lang.String)value);
		else if (name == "type")
			setType((String)value);
		else if (name == "numberOfNeurons")
			setNumberOfNeurons((String)value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Layer");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "id")
			return getId();
		if (name == "type")
			return getType();
		if (name == "numberOfNeurons")
			return getNumberOfNeurons();
		throw new IllegalArgumentException(name+" is not a valid property name for Layer");
	}

	public String nameSelf() {
		return "layer";
	}

	public String nameChild(Object childObj) {
		if (childObj instanceof String) {
			String child = (String) childObj;
			if (child == _Type) {
				return "type";
			}
			if (child == _NumberOfNeurons) {
				return "numberOfNeurons";
			}
		}
		if (childObj instanceof java.lang.String) {
			java.lang.String child = (java.lang.String) childObj;
			if (child == _Id) {
				return "id";
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
		if (!(o instanceof org.neurpheus.machinelearning.neuralnet.xml.Layer))
			return false;
		org.neurpheus.machinelearning.neuralnet.xml.Layer inst = (org.neurpheus.machinelearning.neuralnet.xml.Layer) o;
		if (!(_Id == null ? inst._Id == null : _Id.equals(inst._Id))) {
			return false;
		}
		if (!(_Type == null ? inst._Type == null : _Type.equals(inst._Type))) {
			return false;
		}
		if (!(_NumberOfNeurons == null ? inst._NumberOfNeurons == null : _NumberOfNeurons.equals(inst._NumberOfNeurons))) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Id == null ? 0 : _Id.hashCode());
		result = 37*result + (_Type == null ? 0 : _Type.hashCode());
		result = 37*result + (_NumberOfNeurons == null ? 0 : _NumberOfNeurons.hashCode());
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
