package hu.csega.brp.objects;

import hu.csega.brp.objects.impl.BRPTree;

public enum BRPType {

	TREE("Tree", BRPTree.class);

	private BRPType(String label, Class<?> implementor) {
		this.label = label;
		this.implementor = implementor;
	}

	public String getLabel() {
		return label;
	}

	public Class<?> getImplementor() {
		return implementor;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	private String label;
	private Class<?> implementor;
}
