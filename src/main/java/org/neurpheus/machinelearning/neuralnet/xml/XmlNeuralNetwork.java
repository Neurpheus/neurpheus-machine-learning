/**
 *	This generated bean class NeuralNetwork
 *	matches the schema element 'neural-network'.
 *
 *	Generated on Mon Nov 26 22:10:07 CET 2007
 *
 *	This class matches the root element of the DTD,
 *	and is the root of the bean graph.
 *
 * 	neural-network : NeuralNetwork
 * 		[attr: version CDATA #REQUIRED ]
 * 		name : String[0,1]
 * 		description : String[0,1]
 * 		creation-date : String[0,1]
 * 		modification-date : String[0,1]
 * 		variables : Variables
 * 			variable : Variable[0,n]
 * 				name : String
 * 				description : String
 * 		layers : Layers
 * 			layer : Layer[1,n]
 * 				[attr: id ID #REQUIRED ]
 * 				type : String
 * 				number-of-neurons : String
 * 		input-layer : Boolean
 * 			[attr: layerId IDREF #REQUIRED ]
 * 			EMPTY : String
 * 		output-layer : Boolean
 * 			[attr: layerId IDREF #REQUIRED ]
 * 			EMPTY : String
 * 		synapses : Synapses
 * 			synapse : Synapse[0,n]
 * 				[attr: id ID #REQUIRED ]
 * 				type : String
 * 				source-layer : Boolean
 * 					[attr: layerId IDREF #REQUIRED ]
 * 					EMPTY : String
 * 				destination-layer : Boolean
 * 					[attr: layerId IDREF #REQUIRED ]
 * 					EMPTY : String
 *
 * @generated
 */

package org.neurpheus.machinelearning.neuralnet.xml;

public class XmlNeuralNetwork {
	private java.lang.String _Version;
	private String _Name;
	private String _Description;
	private String _CreationDate;
	private String _ModificationDate;
	private Variables _Variables;
	private Layers _Layers;
	private boolean _InputLayer;
	private java.lang.String _InputLayerLayerId;
	private boolean _OutputLayer;
	private java.lang.String _OutputLayerLayerId;
	private Synapses _Synapses;
	private java.lang.String schemaLocation;

	public XmlNeuralNetwork() {
		_Version = "";
		_Variables = new Variables();
		_Layers = new Layers();
		_InputLayerLayerId = "";
		_OutputLayerLayerId = "";
		_Synapses = new Synapses();
	}

	/**
	 * Required parameters constructor
	 */
	public XmlNeuralNetwork(java.lang.String version, org.neurpheus.machinelearning.neuralnet.xml.Variables variables, org.neurpheus.machinelearning.neuralnet.xml.Layers layers, boolean inputLayer, java.lang.String inputLayerLayerId, boolean outputLayer, java.lang.String outputLayerLayerId, org.neurpheus.machinelearning.neuralnet.xml.Synapses synapses) {
		_Version = version;
		_Variables = variables;
		_Layers = layers;
		_InputLayer = inputLayer;
		_InputLayerLayerId = inputLayerLayerId;
		_OutputLayer = outputLayer;
		_OutputLayerLayerId = outputLayerLayerId;
		_Synapses = synapses;
	}

	/**
	 * Deep copy
	 */
	public XmlNeuralNetwork(org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork source) {
		_Version = source._Version;
		_Name = source._Name;
		_Description = source._Description;
		_CreationDate = source._CreationDate;
		_ModificationDate = source._ModificationDate;
		_Variables = (source._Variables == null) ? null : new org.neurpheus.machinelearning.neuralnet.xml.Variables(source._Variables);
		_Layers = (source._Layers == null) ? null : new org.neurpheus.machinelearning.neuralnet.xml.Layers(source._Layers);
		_InputLayer = source._InputLayer;
		_InputLayerLayerId = source._InputLayerLayerId;
		_OutputLayer = source._OutputLayer;
		_OutputLayerLayerId = source._OutputLayerLayerId;
		_Synapses = (source._Synapses == null) ? null : new org.neurpheus.machinelearning.neuralnet.xml.Synapses(source._Synapses);
		schemaLocation = source.schemaLocation;
	}

	// This attribute is mandatory
	public void setVersion(java.lang.String value) {
		_Version = value;
	}

	public java.lang.String getVersion() {
		return _Version;
	}

	// This attribute is optional
	public void setName(String value) {
		_Name = value;
	}

	public String getName() {
		return _Name;
	}

	// This attribute is optional
	public void setDescription(String value) {
		_Description = value;
	}

	public String getDescription() {
		return _Description;
	}

	// This attribute is optional
	public void setCreationDate(String value) {
		_CreationDate = value;
	}

	public String getCreationDate() {
		return _CreationDate;
	}

	// This attribute is optional
	public void setModificationDate(String value) {
		_ModificationDate = value;
	}

	public String getModificationDate() {
		return _ModificationDate;
	}

	// This attribute is mandatory
	public void setVariables(org.neurpheus.machinelearning.neuralnet.xml.Variables value) {
		_Variables = value;
	}

	public org.neurpheus.machinelearning.neuralnet.xml.Variables getVariables() {
		return _Variables;
	}

	// This attribute is mandatory
	public void setLayers(org.neurpheus.machinelearning.neuralnet.xml.Layers value) {
		_Layers = value;
	}

	public org.neurpheus.machinelearning.neuralnet.xml.Layers getLayers() {
		return _Layers;
	}

	// This attribute is mandatory
	public void setInputLayer(boolean value) {
		_InputLayer = value;
	}

	public boolean isInputLayer() {
		return _InputLayer;
	}

	// This attribute is mandatory
	public void setInputLayerLayerId(java.lang.String value) {
		_InputLayerLayerId = value;
	}

	public java.lang.String getInputLayerLayerId() {
		return _InputLayerLayerId;
	}

	// This attribute is mandatory
	public void setOutputLayer(boolean value) {
		_OutputLayer = value;
	}

	public boolean isOutputLayer() {
		return _OutputLayer;
	}

	// This attribute is mandatory
	public void setOutputLayerLayerId(java.lang.String value) {
		_OutputLayerLayerId = value;
	}

	public java.lang.String getOutputLayerLayerId() {
		return _OutputLayerLayerId;
	}

	// This attribute is mandatory
	public void setSynapses(org.neurpheus.machinelearning.neuralnet.xml.Synapses value) {
		_Synapses = value;
	}

	public org.neurpheus.machinelearning.neuralnet.xml.Synapses getSynapses() {
		return _Synapses;
	}

	public void _setSchemaLocation(String location) {
		schemaLocation = location;
	}

	public String _getSchemaLocation() {
		return schemaLocation;
	}

	public void write(java.io.OutputStream out) throws java.io.IOException {
		write(out, null);
	}

	public void write(java.io.OutputStream out, String encoding) throws java.io.IOException {
		java.io.Writer w;
		if (encoding == null) {
			encoding = "UTF-8";	// NOI18N
		}
		w = new java.io.BufferedWriter(new java.io.OutputStreamWriter(out, encoding));
		write(w, encoding);
		w.flush();
	}

	/**
	 * Print this Java Bean to @param out including an XML header.
	 * @param encoding is the encoding style that @param out was opened with.
	 */
	public void write(java.io.Writer out, String encoding) throws java.io.IOException {
		out.write("<?xml version='1.0'");	// NOI18N
		if (encoding != null)
			out.write(" encoding='"+encoding+"'");	// NOI18N
		out.write(" ?>\n");	// NOI18N
		writeNode(out, "neural-network", "");	// NOI18N
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		if (schemaLocation != null) {
			out.write(" xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='");
			out.write(schemaLocation);
			out.write("'");	// NOI18N
		}
		// version is an attribute
		if (_Version != null) {
			out.write(" version");	// NOI18N
			out.write("='");	// NOI18N
			org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _Version, true);
			out.write("'");	// NOI18N
		}
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
		if (_CreationDate != null) {
			out.write(nextIndent);
			out.write("<creation-date");	// NOI18N
			out.write(">");	// NOI18N
			org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _CreationDate, false);
			out.write("</creation-date>\n");	// NOI18N
		}
		if (_ModificationDate != null) {
			out.write(nextIndent);
			out.write("<modification-date");	// NOI18N
			out.write(">");	// NOI18N
			org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _ModificationDate, false);
			out.write("</modification-date>\n");	// NOI18N
		}
		if (_Variables != null) {
			_Variables.writeNode(out, "variables", nextIndent);
		}
		if (_Layers != null) {
			_Layers.writeNode(out, "layers", nextIndent);
		}
		if (_InputLayer) {
			out.write(nextIndent);
			out.write("<input-layer");	// NOI18N
			// layerId is an attribute
			if (_InputLayerLayerId != null) {
				out.write(" layerId");	// NOI18N
				out.write("='");	// NOI18N
				org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _InputLayerLayerId, true);
				out.write("'");	// NOI18N
			}
			out.write("/>\n");	// NOI18N
		}
		if (_OutputLayer) {
			out.write(nextIndent);
			out.write("<output-layer");	// NOI18N
			// layerId is an attribute
			if (_OutputLayerLayerId != null) {
				out.write(" layerId");	// NOI18N
				out.write("='");	// NOI18N
				org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.writeXML(out, _OutputLayerLayerId, true);
				out.write("'");	// NOI18N
			}
			out.write("/>\n");	// NOI18N
		}
		if (_Synapses != null) {
			_Synapses.writeNode(out, "synapses", nextIndent);
		}
		out.write(indent);
		out.write("</"+nodeName+">\n");
	}

	public static XmlNeuralNetwork read(java.io.InputStream in) throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException {
		return read(new org.xml.sax.InputSource(in), false, null, null);
	}

	/**
	 * Warning: in readNoEntityResolver character and entity references will
	 * not be read from any DTD in the XML source.
	 * However, this way is faster since no DTDs are looked up
	 * (possibly skipping network access) or parsed.
	 */
	public static XmlNeuralNetwork readNoEntityResolver(java.io.InputStream in) throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException {
		return read(new org.xml.sax.InputSource(in), false,
			new org.xml.sax.EntityResolver() {
			public org.xml.sax.InputSource resolveEntity(String publicId, String systemId) {
				java.io.ByteArrayInputStream bin = new java.io.ByteArrayInputStream(new byte[0]);
				return new org.xml.sax.InputSource(bin);
			}
		}
			, null);
	}

	public static XmlNeuralNetwork read(org.xml.sax.InputSource in, boolean validate, org.xml.sax.EntityResolver er, org.xml.sax.ErrorHandler eh) throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException {
		javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		dbf.setValidating(validate);
		javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
		if (er != null)	db.setEntityResolver(er);
		if (eh != null)	db.setErrorHandler(eh);
		org.w3c.dom.Document doc = db.parse(in);
		return read(doc);
	}

	public static XmlNeuralNetwork read(org.w3c.dom.Document document) {
		XmlNeuralNetwork aNeuralNetwork = new XmlNeuralNetwork();
		aNeuralNetwork.readNode(document.getDocumentElement());
		return aNeuralNetwork;
	}

	public void readNode(org.w3c.dom.Node node) {
		if (node.hasAttributes()) {
			org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
			org.w3c.dom.Attr attr;
			java.lang.String attrValue;
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("xsi:schemaLocation");
			if (attr != null) {
				attrValue = attr.getValue();
			} else {
				attrValue = null;
			}
			schemaLocation = attrValue;
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("version");
			if (attr != null) {
				attrValue = attr.getValue();
			} else {
				attrValue = null;
			}
			_Version = attrValue;
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
			if (childNodeName == "name") {
				_Name = childNodeValue;
				lastElementType = 1;
			}
			else if (childNodeName == "description") {
				_Description = childNodeValue;
				lastElementType = 2;
			}
			else if (childNodeName == "creation-date") {
				_CreationDate = childNodeValue;
				lastElementType = 3;
			}
			else if (childNodeName == "modification-date") {
				_ModificationDate = childNodeValue;
				lastElementType = 4;
			}
			else if (childNodeName == "variables") {
				_Variables = new org.neurpheus.machinelearning.neuralnet.xml.Variables();
				_Variables.readNode(childNode);
				lastElementType = 5;
			}
			else if (childNodeName == "layers") {
				_Layers = new org.neurpheus.machinelearning.neuralnet.xml.Layers();
				_Layers.readNode(childNode);
				lastElementType = 6;
			}
			else if (childNodeName == "input-layer") {
				if (childNode.getFirstChild() == null)
					_InputLayer = true;
				else
					_InputLayer = java.lang.Boolean.valueOf(childNodeValue).booleanValue();
				attr = (org.w3c.dom.Attr) attrs.getNamedItem("layerId");
				if (attr != null) {
					attrValue = attr.getValue();
				} else {
					attrValue = null;
				}
				_InputLayerLayerId = attrValue;
				lastElementType = 7;
			}
			else if (childNodeName == "output-layer") {
				if (childNode.getFirstChild() == null)
					_OutputLayer = true;
				else
					_OutputLayer = java.lang.Boolean.valueOf(childNodeValue).booleanValue();
				attr = (org.w3c.dom.Attr) attrs.getNamedItem("layerId");
				if (attr != null) {
					attrValue = attr.getValue();
				} else {
					attrValue = null;
				}
				_OutputLayerLayerId = attrValue;
				lastElementType = 9;
			}
			else if (childNodeName == "synapses") {
				_Synapses = new org.neurpheus.machinelearning.neuralnet.xml.Synapses();
				_Synapses.readNode(childNode);
				lastElementType = 11;
			}
			else {
				// Found extra unrecognized childNode
			}
		}
	}

	/**
	 * Takes some text to be printed into an XML stream and escapes any
	 * characters that might make it invalid XML (like '<').
	 */
	public static void writeXML(java.io.Writer out, String msg) throws java.io.IOException {
		writeXML(out, msg, true);
	}

	public static void writeXML(java.io.Writer out, String msg, boolean attribute) throws java.io.IOException {
		if (msg == null)
			return;
		int msgLength = msg.length();
		for (int i = 0; i < msgLength; ++i) {
			char c = msg.charAt(i);
			writeXML(out, c, attribute);
		}
	}

	public static void writeXML(java.io.Writer out, char msg, boolean attribute) throws java.io.IOException {
		if (msg == '&')
			out.write("&amp;");
		else if (msg == '<')
			out.write("&lt;");
		else if (msg == '>')
			out.write("&gt;");
		else if (attribute && msg == '"')
			out.write("&quot;");
		else if (attribute && msg == '\'')
			out.write("&apos;");
		else if (attribute && msg == '\n')
			out.write("&#xA;");
		else if (attribute && msg == '\t')
			out.write("&#x9;");
		else
			out.write(msg);
	}

	public static class ValidateException extends Exception {
		private java.lang.Object failedBean;
		private String failedPropertyName;
		private FailureType failureType;
		public ValidateException(String msg, String failedPropertyName, java.lang.Object failedBean) {
			super(msg);
			this.failedBean = failedBean;
			this.failedPropertyName = failedPropertyName;
		}
		public ValidateException(String msg, FailureType ft, String failedPropertyName, java.lang.Object failedBean) {
			super(msg);
			this.failureType = ft;
			this.failedBean = failedBean;
			this.failedPropertyName = failedPropertyName;
		}
		public String getFailedPropertyName() {return failedPropertyName;}
		public FailureType getFailureType() {return failureType;}
		public java.lang.Object getFailedBean() {return failedBean;}
		public static class FailureType {
			private final String name;
			private FailureType(String name) {this.name = name;}
			public String toString() { return name;}
			public static final FailureType NULL_VALUE = new FailureType("NULL_VALUE");
			public static final FailureType DATA_RESTRICTION = new FailureType("DATA_RESTRICTION");
			public static final FailureType ENUM_RESTRICTION = new FailureType("ENUM_RESTRICTION");
			public static final FailureType MUTUALLY_EXCLUSIVE = new FailureType("MUTUALLY_EXCLUSIVE");
		}
	}

	public void validate() throws org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException {
		boolean restrictionFailure = false;
		// Validating property version
		if (getVersion() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getVersion() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "version", this);	// NOI18N
		}
		// Validating property name
		// Validating property description
		// Validating property creationDate
		// Validating property modificationDate
		// Validating property variables
		if (getVariables() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getVariables() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "variables", this);	// NOI18N
		}
		getVariables().validate();
		// Validating property layers
		if (getLayers() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getLayers() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "layers", this);	// NOI18N
		}
		getLayers().validate();
		// Validating property inputLayer
		// Validating property inputLayerLayerId
		if (getInputLayerLayerId() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getInputLayerLayerId() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "inputLayerLayerId", this);	// NOI18N
		}
		// Validating property outputLayer
		// Validating property outputLayerLayerId
		if (getOutputLayerLayerId() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getOutputLayerLayerId() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "outputLayerLayerId", this);	// NOI18N
		}
		// Validating property synapses
		if (getSynapses() == null) {
			throw new org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException("getSynapses() == null", org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork.ValidateException.FailureType.NULL_VALUE, "synapses", this);	// NOI18N
		}
		getSynapses().validate();
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "version")
			setVersion((java.lang.String)value);
		else if (name == "name")
			setName((String)value);
		else if (name == "description")
			setDescription((String)value);
		else if (name == "creationDate")
			setCreationDate((String)value);
		else if (name == "modificationDate")
			setModificationDate((String)value);
		else if (name == "variables")
			setVariables((Variables)value);
		else if (name == "layers")
			setLayers((Layers)value);
		else if (name == "inputLayer")
			setInputLayer(((java.lang.Boolean)value).booleanValue());
		else if (name == "inputLayerLayerId")
			setInputLayerLayerId((java.lang.String)value);
		else if (name == "outputLayer")
			setOutputLayer(((java.lang.Boolean)value).booleanValue());
		else if (name == "outputLayerLayerId")
			setOutputLayerLayerId((java.lang.String)value);
		else if (name == "synapses")
			setSynapses((Synapses)value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for NeuralNetwork");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "version")
			return getVersion();
		if (name == "name")
			return getName();
		if (name == "description")
			return getDescription();
		if (name == "creationDate")
			return getCreationDate();
		if (name == "modificationDate")
			return getModificationDate();
		if (name == "variables")
			return getVariables();
		if (name == "layers")
			return getLayers();
		if (name == "inputLayer")
			return (isInputLayer() ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE);
		if (name == "inputLayerLayerId")
			return getInputLayerLayerId();
		if (name == "outputLayer")
			return (isOutputLayer() ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE);
		if (name == "outputLayerLayerId")
			return getOutputLayerLayerId();
		if (name == "synapses")
			return getSynapses();
		throw new IllegalArgumentException(name+" is not a valid property name for NeuralNetwork");
	}

	public String nameSelf() {
		return "/neural-network";
	}

	public String nameChild(Object childObj) {
		if (childObj instanceof Synapses) {
			Synapses child = (Synapses) childObj;
			if (child == _Synapses) {
				return "synapses";
			}
		}
		if (childObj instanceof Variables) {
			Variables child = (Variables) childObj;
			if (child == _Variables) {
				return "variables";
			}
		}
		if (childObj instanceof java.lang.Boolean) {
			java.lang.Boolean child = (java.lang.Boolean) childObj;
			if (((java.lang.Boolean)child).booleanValue() == _InputLayer) {
				return "inputLayer";
			}
			if (((java.lang.Boolean)child).booleanValue() == _OutputLayer) {
				return "outputLayer";
			}
		}
		if (childObj instanceof Layers) {
			Layers child = (Layers) childObj;
			if (child == _Layers) {
				return "layers";
			}
		}
		if (childObj instanceof String) {
			String child = (String) childObj;
			if (child == _Name) {
				return "name";
			}
			if (child == _Description) {
				return "description";
			}
			if (child == _CreationDate) {
				return "creationDate";
			}
			if (child == _ModificationDate) {
				return "modificationDate";
			}
		}
		if (childObj instanceof java.lang.String) {
			java.lang.String child = (java.lang.String) childObj;
			if (child == _Version) {
				return "version";
			}
			if (child == _InputLayerLayerId) {
				return "inputLayerLayerId";
			}
			if (child == _OutputLayerLayerId) {
				return "outputLayerLayerId";
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
		if (_Variables != null) {
			if (recursive) {
				_Variables.childBeans(true, beans);
			}
			beans.add(_Variables);
		}
		if (_Layers != null) {
			if (recursive) {
				_Layers.childBeans(true, beans);
			}
			beans.add(_Layers);
		}
		if (_Synapses != null) {
			if (recursive) {
				_Synapses.childBeans(true, beans);
			}
			beans.add(_Synapses);
		}
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork))
			return false;
		org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork inst = (org.neurpheus.machinelearning.neuralnet.xml.XmlNeuralNetwork) o;
		if (!(_Version == null ? inst._Version == null : _Version.equals(inst._Version))) {
			return false;
		}
		if (!(_Name == null ? inst._Name == null : _Name.equals(inst._Name))) {
			return false;
		}
		if (!(_Description == null ? inst._Description == null : _Description.equals(inst._Description))) {
			return false;
		}
		if (!(_CreationDate == null ? inst._CreationDate == null : _CreationDate.equals(inst._CreationDate))) {
			return false;
		}
		if (!(_ModificationDate == null ? inst._ModificationDate == null : _ModificationDate.equals(inst._ModificationDate))) {
			return false;
		}
		if (!(_Variables == null ? inst._Variables == null : _Variables.equals(inst._Variables))) {
			return false;
		}
		if (!(_Layers == null ? inst._Layers == null : _Layers.equals(inst._Layers))) {
			return false;
		}
		if (!(_InputLayer == inst._InputLayer)) {
			return false;
		}
		if (!(_InputLayerLayerId == null ? inst._InputLayerLayerId == null : _InputLayerLayerId.equals(inst._InputLayerLayerId))) {
			return false;
		}
		if (!(_OutputLayer == inst._OutputLayer)) {
			return false;
		}
		if (!(_OutputLayerLayerId == null ? inst._OutputLayerLayerId == null : _OutputLayerLayerId.equals(inst._OutputLayerLayerId))) {
			return false;
		}
		if (!(_Synapses == null ? inst._Synapses == null : _Synapses.equals(inst._Synapses))) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Version == null ? 0 : _Version.hashCode());
		result = 37*result + (_Name == null ? 0 : _Name.hashCode());
		result = 37*result + (_Description == null ? 0 : _Description.hashCode());
		result = 37*result + (_CreationDate == null ? 0 : _CreationDate.hashCode());
		result = 37*result + (_ModificationDate == null ? 0 : _ModificationDate.hashCode());
		result = 37*result + (_Variables == null ? 0 : _Variables.hashCode());
		result = 37*result + (_Layers == null ? 0 : _Layers.hashCode());
		result = 37*result + (_InputLayer ? 0 : 1);
		result = 37*result + (_InputLayerLayerId == null ? 0 : _InputLayerLayerId.hashCode());
		result = 37*result + (_OutputLayer ? 0 : 1);
		result = 37*result + (_OutputLayerLayerId == null ? 0 : _OutputLayerLayerId.hashCode());
		result = 37*result + (_Synapses == null ? 0 : _Synapses.hashCode());
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
