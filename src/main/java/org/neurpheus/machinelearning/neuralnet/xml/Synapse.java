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

public class Synapse {
	private java.lang.String _Id;
	private String _Type;
	private boolean _SourceLayer;
	private java.lang.String _SourceLayerLayerId;
	private boolean _DestinationLayer;
	private java.lang.String _DestinationLayerLayerId;

	public Synapse() {
		_Id = "";
		_Type = "";
		_SourceLayerLayerId = "";
		_DestinationLayerLayerId = "";
	}

	/**
	 * Required parameters constructor
	 */
	public Synapse(java.lang.String id, String type, boolean sourceLayer, java.lang.String sourceLayerLayerId, boolean destinationLayer, java.lang.String destinationLayerLayerId) {
		_Id = id;
		_Type = type;
		_SourceLayer = sourceLayer;
		_SourceLayerLayerId = sourceLayerLayerId;
		_DestinationLayer = destinationLayer;
		_DestinationLayerLayerId = destinationLayerLayerId;
	}

	/**
	 * Deep copy
	 */
	public Synapse(org.neurpheus.machinelearning.neuralnet.xml.Synapse source) {
		_Id = source._Id;
		_Type = source._Type;
		_SourceLayer = source._SourceLayer;
		_SourceLayerLayerId = source._SourceLayerLayerId;
		_DestinationLayer = source._DestinationLayer;
		_DestinationLayerLayerId = source._DestinationLayerLayerId;
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
	public void setSourceLayer(boolean value) {
		_SourceLayer = value;
	}

	public boolean isSourceLayer() {
		return _SourceLayer;
	}

	// This attribute is mandatory
	public void setSourceLayerLayerId(java.lang.String value) {
		_SourceLayerLayerId = value;
	}

	public java.lang.String getSourceLayerLayerId() {
		return _SourceLayerLayerId;
	}

	// This attribute is mandatory
	public void setDestinationLayer(boolean value) {
		_DestinationLayer = value;
	}

	public boolean isDestinationLayer() {
		return _DestinationLayer;
	}

	// This attribute is mandatory
	public void setDestinationLayerLayerId(java.lang.String value) {
		_DestinationLayerLayerId = value;
	}

	public java.lang.String getDestinationLayerLayerId() {
		return _DestinationLayerLayerId;
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
		if (_SourceLayer) {
			out.write(nextIndent);
			out.write("<source-layer");	// NOI18N
			// layerId is an attribute
			if (_SourceLayerLayerId != null) {
				out.write(" layerId");	// NOI18N
				out.write("='");	// NOI18N
				org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _SourceLayerLayerId, true);
				out.write("'");	// NOI18N
			}
			out.write("/>\n");	// NOI18N
		}
		if (_DestinationLayer) {
			out.write(nextIndent);
			out.write("<destination-layer");	// NOI18N
			// layerId is an attribute
			if (_DestinationLayerLayerId != null) {
				out.write(" layerId");	// NOI18N
				out.write("='");	// NOI18N
				org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _DestinationLayerLayerId, true);
				out.write("'");	// NOI18N
			}
			out.write("/>\n");	// NOI18N
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
		int lastElementType = 0;
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			org.w3c.dom.Node childNode = children.item(i);
			String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
			org.w3c.dom.NamedNodeMap attrs = childNode.getAttributes();
			org.w3c.dom.Attr attr;
			java.lang.String attrValue;
			if (childNodeName == "type") {
				_Type = childNodeValue;
				lastElementType = 1;
			}
			else if (childNodeName == "source-layer") {
				if (childNode.getFirstChild() == null)
					_SourceLayer = true;
				else
					_SourceLayer = java.lang.Boolean.valueOf(childNodeValue).booleanValue();
				attr = (org.w3c.dom.Attr) attrs.getNamedItem("layerId");
				if (attr != null) {
					attrValue = attr.getValue();
				} else {
					attrValue = null;
				}
				_SourceLayerLayerId = attrValue;
				lastElementType = 2;
			}
			else if (childNodeName == "destination-layer") {
				if (childNode.getFirstChild() == null)
					_DestinationLayer = true;
				else
					_DestinationLayer = java.lang.Boolean.valueOf(childNodeValue).booleanValue();
				attr = (org.w3c.dom.Attr) attrs.getNamedItem("layerId");
				if (attr != null) {
					attrValue = attr.getValue();
				} else {
					attrValue = null;
				}
				_DestinationLayerLayerId = attrValue;
				lastElementType = 4;
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
		// Validating property sourceLayer
		// Validating property sourceLayerLayerId
		if (getSourceLayerLayerId() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getSourceLayerLayerId() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "sourceLayerLayerId", this);	// NOI18N
		}
		// Validating property destinationLayer
		// Validating property destinationLayerLayerId
		if (getDestinationLayerLayerId() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getDestinationLayerLayerId() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "destinationLayerLayerId", this);	// NOI18N
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "id")
			setId((java.lang.String)value);
		else if (name == "type")
			setType((String)value);
		else if (name == "sourceLayer")
			setSourceLayer(((java.lang.Boolean)value).booleanValue());
		else if (name == "sourceLayerLayerId")
			setSourceLayerLayerId((java.lang.String)value);
		else if (name == "destinationLayer")
			setDestinationLayer(((java.lang.Boolean)value).booleanValue());
		else if (name == "destinationLayerLayerId")
			setDestinationLayerLayerId((java.lang.String)value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Synapse");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "id")
			return getId();
		if (name == "type")
			return getType();
		if (name == "sourceLayer")
			return (isSourceLayer() ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE);
		if (name == "sourceLayerLayerId")
			return getSourceLayerLayerId();
		if (name == "destinationLayer")
			return (isDestinationLayer() ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE);
		if (name == "destinationLayerLayerId")
			return getDestinationLayerLayerId();
		throw new IllegalArgumentException(name+" is not a valid property name for Synapse");
	}

	public String nameSelf() {
		return "synapse";
	}

	public String nameChild(Object childObj) {
		if (childObj instanceof java.lang.Boolean) {
			java.lang.Boolean child = (java.lang.Boolean) childObj;
			if (((java.lang.Boolean)child).booleanValue() == _SourceLayer) {
				return "sourceLayer";
			}
			if (((java.lang.Boolean)child).booleanValue() == _DestinationLayer) {
				return "destinationLayer";
			}
		}
		if (childObj instanceof String) {
			String child = (String) childObj;
			if (child == _Type) {
				return "type";
			}
		}
		if (childObj instanceof java.lang.String) {
			java.lang.String child = (java.lang.String) childObj;
			if (child == _Id) {
				return "id";
			}
			if (child == _SourceLayerLayerId) {
				return "sourceLayerLayerId";
			}
			if (child == _DestinationLayerLayerId) {
				return "destinationLayerLayerId";
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
		if (!(o instanceof org.neurpheus.machinelearning.neuralnet.xml.Synapse))
			return false;
		org.neurpheus.machinelearning.neuralnet.xml.Synapse inst = (org.neurpheus.machinelearning.neuralnet.xml.Synapse) o;
		if (!(_Id == null ? inst._Id == null : _Id.equals(inst._Id))) {
			return false;
		}
		if (!(_Type == null ? inst._Type == null : _Type.equals(inst._Type))) {
			return false;
		}
		if (!(_SourceLayer == inst._SourceLayer)) {
			return false;
		}
		if (!(_SourceLayerLayerId == null ? inst._SourceLayerLayerId == null : _SourceLayerLayerId.equals(inst._SourceLayerLayerId))) {
			return false;
		}
		if (!(_DestinationLayer == inst._DestinationLayer)) {
			return false;
		}
		if (!(_DestinationLayerLayerId == null ? inst._DestinationLayerLayerId == null : _DestinationLayerLayerId.equals(inst._DestinationLayerLayerId))) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Id == null ? 0 : _Id.hashCode());
		result = 37*result + (_Type == null ? 0 : _Type.hashCode());
		result = 37*result + (_SourceLayer ? 0 : 1);
		result = 37*result + (_SourceLayerLayerId == null ? 0 : _SourceLayerLayerId.hashCode());
		result = 37*result + (_DestinationLayer ? 0 : 1);
		result = 37*result + (_DestinationLayerLayerId == null ? 0 : _DestinationLayerLayerId.hashCode());
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
