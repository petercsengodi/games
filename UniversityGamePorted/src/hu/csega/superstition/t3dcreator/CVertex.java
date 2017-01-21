package hu.csega.superstition.t3dcreator;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("T3DCreator.CVertex")
public class CVertex implements IModelPart {

	public Vector3f position;
	public Vector2f texture_coordinates;

	public CVertex() {
		this.texture_coordinates = new Vector2f();
		this.position = new Vector3f();
	}

	public CVertex(Vector3f position) {
		this.texture_coordinates = new Vector2f();
		this.position = position;
	}

	public CVertex(Vector3f position, Vector2f texture_coordinates) {
		this.texture_coordinates = texture_coordinates;
		this.position = position;
	}

	@XmlField("position")
	public Vector3f getPosition() {
		return position;
	}

	@XmlField("position")
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	@XmlField("texture_coordinates")
	public Vector2f getTexture_coordinates() {
		return texture_coordinates;
	}

	@XmlField("texture_coordinates")
	public void setTexture_coordinates(Vector2f texture_coordinates) {
		this.texture_coordinates = texture_coordinates;
	}

	public String toString() {
		return "Vertex (" + position.x + ";" + position.y + ";" + position.z + ") [" +
			texture_coordinates.x + ";" + texture_coordinates.y + "]";
	}

	public void move(Vector3f direction) {
		this.position.add(direction, position);
	}

	public void moveTexture(Vector2f direction) {
		this.texture_coordinates.add(direction, this.texture_coordinates);

		if(texture_coordinates.x < 0f)
			texture_coordinates.x = 0f;
		if(texture_coordinates.x > 1f)
			texture_coordinates.x = 1f;
		if(texture_coordinates.y < 0f)
			texture_coordinates.y = 0f;
		if(texture_coordinates.y > 1f)
			texture_coordinates.y = 1f;
	}

	public boolean hasPart(IModelPart part) {
		return part.equals(this);
	}

	public Vector3f centerPoint() {
		return position;
	}

	public void scale(Matrix3f matrix) {
		this.position.mul(matrix, position);
	}

}