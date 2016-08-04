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

public class Synapses {
	private java.util.List _Synapse = new java.util.ArrayList();	// List<Synapse>

	public Synapses() {
	}

	/**
	 * Deep copy
	 */
	public Synapses(org.neurpheus.machinelearning.neuralnet.xml.Synapses source) {
		for (java.util.Iterator it = source._Synapse.iterator(); 
			it.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Synapse element = (org.neurpheus.machinelearning.neuralnet.xml.Synapse)it.next();
			_Synapse.add((element == null) ? null : new org.neurpheus.machinelearning.neuralnet.xml.Synapse(element));
		}
	}

	// This attribute is an array, possibly empty
	public void setSynapse(org.neurpheus.machinelearning.neuralnet.xml.Synapse[] value) {
		if (value == null)
			value = new Synapse[0];
		_Synapse.clear();
		for (int i = 0; i < value.length; ++i) {
			_Synapse.add(value[i]);
		}
	}

	public void setSynapse(int index, org.neurpheus.machinelearning.neuralnet.xml.Synapse value) {
		_Synapse.set(index, value);
	}

	public org.neurpheus.machinelearning.neuralnet.xml.Synapse[] getSynapse() {
		Synapse[] arr = new Synapse[_Synapse.size()];
		return (Synapse[]) _Synapse.toArray(arr);
	}

	public java.util.List fetchSynapseList() {
		return _Synapse;
	}

	public org.neurpheus.machinelearning.neuralnet.xml.Synapse getSynapse(int index) {
		return (Synapse)_Synapse.get(index);
	}

	// Return the number of synapse
	public int sizeSynapse() {
		return _Synapse.size();
	}

	public int addSynapse(org.neurpheus.machinelearning.neuralnet.xml.Synapse value) {
		_Synapse.add(value);
		int positionOfNewItem = _Synapse.size()-1;
		return positionOfNewItem;
	}

	/**
	 * Search from the end looking for @param value, and then remove it.
	 */
	public int removeSynapse(org.neurpheus.machinelearning.neuralnet.xml.Synapse value) {
		int pos = _Synapse.indexOf(value);
		if (pos >= 0) {
			_Synapse.remove(pos);
		}
		return pos;
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		out.write(">\n");
		String nextIndent = indent + "	";
		for (java.util.Iterator it = _Synapse.iterator(); it.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Synapse element = (org.neurpheus.machinelearning.neuralnet.xml.Synapse)it.next();
			if (element != null) {
				element.writeNode(out, "synapse", nextIndent);
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
			if (childNodeName == "synapse") {
				Synapse aSynapse = new org.neurpheus.machinelearning.neuralnet.xml.Synapse();
				aSynapse.readNode(childNode);
				_Synapse.add(aSynapse);
			}
			else {
				// Found extra unrecognized childNode
			}
		}
	}

	public void validate() throws org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException {
		boolean restrictionFailure = false;
		// Validating property synapse
		for (int _index = 0; _index < sizeSynapse(); ++_index) {
			org.neurpheus.machinelearning.neuralnet.xml.Synapse element = getSynapse(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "synapse")
			addSynapse((Synapse)value);
		else if (name == "synapse[]")
			setSynapse((Synapse[]) value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Synapses");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "synapse[]")
			return getSynapse();
		throw new IllegalArgumentException(name+" is not a valid property name for Synapses");
	}

	public String nameSelf() {
		return "synapses";
	}

	public String nameChild(Object childObj) {
		if (childObj instanceof Synapse) {
			Synapse child = (Synapse) childObj;
			int index = 0;
			for (java.util.Iterator it = _Synapse.iterator(); 
				it.hasNext(); ) {
				org.neurpheus.machinelearning.neuralnet.xml.Synapse element = (org.neurpheus.machinelearning.neuralnet.xml.Synapse)it.next();
				if (child == element) {
					return "synapse."+index;
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
		for (java.util.Iterator it = _Synapse.iterator(); it.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Synapse element = (org.neurpheus.machinelearning.neuralnet.xml.Synapse)it.next();
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
		if (!(o instanceof org.neurpheus.machinelearning.neuralnet.xml.Synapses))
			return false;
		org.neurpheus.machinelearning.neuralnet.xml.Synapses inst = (org.neurpheus.machinelearning.neuralnet.xml.Synapses) o;
		if (sizeSynapse() != inst.sizeSynapse())
			return false;
		// Compare every element.
		for (java.util.Iterator it = _Synapse.iterator(), it2 = inst._Synapse.iterator(); 
			it.hasNext() && it2.hasNext(); ) {
			org.neurpheus.machinelearning.neuralnet.xml.Synapse element = (org.neurpheus.machinelearning.neuralnet.xml.Synapse)it.next();
			org.neurpheus.machinelearning.neuralnet.xml.Synapse element2 = (org.neurpheus.machinelearning.neuralnet.xml.Synapse)it2.next();
			if (!(element == null ? element2 == null : element.equals(element2))) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Synapse == null ? 0 : _Synapse.hashCode());
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
