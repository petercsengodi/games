package hu.csega.superstition.xml;

class XmlObjectProxy {

	public XmlObjectProxy(String id, XmlNode node, XmlHandler handler) {
		this.id = id;
		this.node = node;
		this._handler = handler;
	}

	public String id;
	public XmlNode node;

	private XmlHandler _handler;
	private Object _value = null;
	private boolean _resolved = false;

	public Object value() throws XmlException {
		if(_resolved)
			return _value;

		Class<?> valueClass = XmlBinding.classOf(node.tag);
		if(valueClass == null)
			throw new XmlException("Missing class for " + node.tag);

		try {
			_value = valueClass.newInstance();
			_resolved = true;
		} catch(Exception ex) {
			throw new XmlException("Couldn't create object of class " + valueClass.getName(), ex);
		}

		_handler.complete(this);
		return _value;
	}

}
