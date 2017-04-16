package hu.csega.superstition.xml;

import java.util.Map;

class XmlFillFieldRequest {

	public XmlFillFieldRequest() {
	}

	public XmlFillFieldRequest(XmlFieldBinding fb, Object obj, String reference) {
		this.fb = fb;
		this.obj = obj;
		this.reference = reference;
	}

	public void execute(Map<String, Object> references) throws XmlReflectionException {
		try {
			Object value = references.get(reference);
			if(value != null)
				fb.setter.invoke(obj, value);
		} catch (Exception e) {
			String className = obj.getClass().getName();
			String property = className + '.' + fb.field;
			throw new XmlReflectionException(property + " <- ref:" + reference, e);
		}
	}

	public XmlFieldBinding fb;
	public Object obj;
	public String reference;
}
