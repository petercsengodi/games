package hu.csega.superstition.gamelib.model;

import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.AnimationRef")
public class SAnimationRef implements SObject {

	private String name;

	public SAnimationRef() {
	}

	public SAnimationRef(String name) {
		this.name = name;
	}

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AnimationRef: " + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SAnimationRef other = (SAnimationRef) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
