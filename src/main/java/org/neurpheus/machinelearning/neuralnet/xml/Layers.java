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

public class Layers {
	private java.util.List _Layer = new java.util.ArrayList();	// List<Layer>

	public Layers() {
	}

	/**
	 * Required parameters constructor
	 */
	public Layers(org.neurpheus.machinelearning.neuralnet.xml.Layer[] layer) {
		if (layer!= null) {
			for (int i = 0; i < layer.length; ++i) {
				_Layer.add(layer[i]);
			}
		}
	}

	/**
	 * Deep copy
	 */
	public Layers(org.neurpheus.machinelearning.neuralnet.xml.Layers source) {
		for (java.util.Iterator it = source._Layer.iterator(); 
			it.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Layer element = (org.neurpheus.machinelearning.neuralnet.xml.Layer)it.next();
			_Layer.add((element == null) ? null : new org.neurpheus.machinelearning.neuralnet.xml.Layer(element));
		}
	}

	// This attribute is an array containing at least one element
	public void setLayer(org.neurpheus.machinelearning.neuralnet.xml.Layer[] value) {
		if (value == null)
			value = new Layer[0];
		_Layer.clear();
		for (int i = 0; i < value.length; ++i) {
			_Layer.add(value[i]);
		}
	}

	public void setLayer(int index, org.neurpheus.machinelearning.neuralnet.xml.Layer value) {
		_Layer.set(index, value);
	}

	public org.neurpheus.machinelearning.neuralnet.xml.Layer[] getLayer() {
		Layer[] arr = new Layer[_Layer.size()];
		return (Layer[]) _Layer.toArray(arr);
	}

	public java.util.List fetchLayerList() {
		return _Layer;
	}

	public org.neurpheus.machinelearning.neuralnet.xml.Layer getLayer(int index) {
		return (Layer)_Layer.get(index);
	}

	// Return the number of layer
	public int sizeLayer() {
		return _Layer.size();
	}

	public int addLayer(org.neurpheus.machinelearning.neuralnet.xml.Layer value) {
		_Layer.add(value);
		int positionOfNewItem = _Layer.size()-1;
		return positionOfNewItem;
	}

	/**
	 * Search from the end looking for @param value, and then remove it.
	 */
	public int removeLayer(org.neurpheus.machinelearning.neuralnet.xml.Layer value) {
		int pos = _Layer.indexOf(value);
		if (pos >= 0) {
			_Layer.remove(pos);
		}
		return pos;
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		out.write(">\n");
		String nextIndent = indent + "	";
		for (java.util.Iterator it = _Layer.iterator(); it.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Layer element = (org.neurpheus.machinelearning.neuralnet.xml.Layer)it.next();
			if (element != null) {
				element.writeNode(out, "layer", nextIndent);
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
			if (childNodeName == "layer") {
				Layer aLayer = new org.neurpheus.machinelearning.neuralnet.xml.Layer();
				aLayer.readNode(childNode);
				_Layer.add(aLayer);
			}
			else {
				// Found extra unrecognized childNode
			}
		}
	}

	public void validate() throws org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException {
		boolean restrictionFailure = false;
		// Validating property layer
		if (sizeLayer() == 0) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("sizeLayer() == 0", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "layer", this);	// NOI18N
		}
		for (int _index = 0; _index < sizeLayer(); ++_index) {
			org.neurpheus.machinelearning.neuralnet.xml.Layer element = getLayer(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "layer")
			addLayer((Layer)value);
		else if (name == "layer[]")
			setLayer((Layer[]) value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Layers");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "layer[]")
			return getLayer();
		throw new IllegalArgumentException(name+" is not a valid property name for Layers");
	}

	public String nameSelf() {
		return "layers";
	}

	public String nameChild(Object childObj) {
		if (childObj instanceof Layer) {
			Layer child = (Layer) childObj;
			int index = 0;
			for (java.util.Iterator it = _Layer.iterator(); it.hasNext(); 
				) {
				org.neurpheus.machinelearning.neuralnet.xml.Layer element = (org.neurpheus.machinelearning.neuralnet.xml.Layer)it.next();
				if (child == element) {
					return "layer."+index;
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
		for (java.util.Iterator it = _Layer.iterator(); it.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Layer element = (org.neurpheus.machinelearning.neuralnet.xml.Layer)it.next();
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
		if (!(o instanceof org.neurpheus.machinelearning.neuralnet.xml.Layers))
			return false;
		org.neurpheus.machinelearning.neuralnet.xml.Layers inst = (org.neurpheus.machinelearning.neuralnet.xml.Layers) o;
		if (sizeLayer() != inst.sizeLayer())
			return false;
		// Compare every element.
		for (java.util.Iterator it = _Layer.iterator(), it2 = inst._Layer.iterator(); 
			it.hasNext() && it2.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Layer element = (org.neurpheus.machinelearning.neuralnet.xml.Layer)it.next();
			org.neurpheus.machinelearning.neuralnet.xml.Layer element2 = (org.neurpheus.machinelearning.neuralnet.xml.Layer)it2.next();
			if (!(element == null ? element2 == null : element.equals(element2))) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Layer == null ? 0 : _Layer.hashCode());
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
