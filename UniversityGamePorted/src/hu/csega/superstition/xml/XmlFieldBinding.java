package hu.csega.superstition.xml;

import java.lang.reflect.Method;

public class XmlFieldBinding {

	public XmlFieldBinding(String field) {
		this.field = field;
	}

	public final String field;
	public Method getter;
	public Method setter;
	public boolean attribute;
}
