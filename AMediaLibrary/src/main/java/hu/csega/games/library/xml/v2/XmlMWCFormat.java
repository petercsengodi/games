package hu.csega.games.library.xml.v2;

import java.util.Arrays;
import java.util.List;

import hu.csega.games.library.mwc.v1.MWCSimpleMesh;
import hu.csega.games.library.mwc.v1.MWCSphere;
import hu.csega.games.library.mwc.v1.MWCVertex;

public class XmlMWCFormat implements XmlFormat {

	public XmlMWCFormat() {
	}

	@Override
	public XmlBinding provideBinding() {
		List<Class<?>> classes = Arrays.asList(MWCSimpleMesh.class, MWCVertex.class, MWCSphere.class);
		XmlBinding binding = new XmlBinding(classes);
		return binding;
	}
}
