package hu.csega.superstition.t3dcreator;

public interface IPart
{
	void move(Vector3 direction);
	void moveTexture(Vector2 direction);
	bool hasPart(IPart part);
	Vector3 centerPoint();
	void scale(Matrix matrix);
}