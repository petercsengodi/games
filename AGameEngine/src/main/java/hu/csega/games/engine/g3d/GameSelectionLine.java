package hu.csega.games.engine.g3d;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class GameSelectionLine {

	public void setBaseMatricesAndViewPort(Matrix4f inversePerspective, Matrix4f inverseCamera, int width, int height) {
		this.inversePerspective.set(inversePerspective);
		this.inverseCamera.set(inverseCamera);
		this.width = width;
		this.height = height;
	}

	public boolean initialize(float x, float y) {
		if(x < 0 || x >= width || y < 0 || y >= height) {
			valid = false;
			return valid;
		}

		nearEndPoint.set(x, y, 0f, 1f);
		farEndPoint.set(x, y, 1f, 1f);

		inversePerspective.transform(nearEndPoint);
		inversePerspective.transform(farEndPoint);

		inverseCamera.transform(nearEndPoint);
		inverseCamera.transform(farEndPoint);

		valid = true;
		return valid;
	}

	@Override
	public String toString() {
		if(valid)
			return "Selection Line [ " + nearEndPoint + " -> " + farEndPoint + " ]";
		else
			return "Selection Line [ invalid ]";
	}

	public float intersection(GameObjectPlacement placement, GameHitBox box) {
		placement.calculateBasicLookAt(basicLookAt);
		basicLookAt.transform(nearEndPoint, nearTmpPoint);
		basicLookAt.transform(farEndPoint, farTmpPoint);

		norm(nearTmpPoint);
		norm(farTmpPoint);

		float t = Float.POSITIVE_INFINITY;

		{
			float t2 = intersectionWithPlain(nearTmpPoint.x, farTmpPoint.x, box.x1);
			if(t2 < t) {
				float y = (farTmpPoint.y - nearTmpPoint.y) * t2 + nearTmpPoint.y;
				if(y >= box.y1 && y <= box.y2) {
					float z = (farTmpPoint.z - nearTmpPoint.z) * t2 + nearTmpPoint.z;
					if(z >= box.z1 && z <= box.z2) {
						t = t2;
					}
				}

			}
		}

		{
			float t2 = intersectionWithPlain(nearTmpPoint.x, farTmpPoint.x, box.x2);
			if(t2 < t) {
				float y = (farTmpPoint.y - nearTmpPoint.y) * t2 + nearTmpPoint.y;
				if(y >= box.y1 && y <= box.y2) {
					float z = (farTmpPoint.z - nearTmpPoint.z) * t2 + nearTmpPoint.z;
					if(z >= box.z1 && z <= box.z2) {
						t = t2;
					}
				}

			}
		}

		{
			float t2 = intersectionWithPlain(nearTmpPoint.y, farTmpPoint.y, box.y1);
			if(t2 < t) {
				float x = (farTmpPoint.x - nearTmpPoint.x) * t2 + nearTmpPoint.x;
				if(x >= box.x1 && x <= box.x2) {
					float z = (farTmpPoint.z - nearTmpPoint.z) * t2 + nearTmpPoint.z;
					if(z >= box.z1 && z <= box.z2) {
						t = t2;
					}
				}

			}
		}

		{
			float t2 = intersectionWithPlain(nearTmpPoint.y, farTmpPoint.y, box.y2);
			if(t2 < t) {
				float x = (farTmpPoint.x - nearTmpPoint.x) * t2 + nearTmpPoint.x;
				if(x >= box.x1 && x <= box.x2) {
					float z = (farTmpPoint.z - nearTmpPoint.z) * t2 + nearTmpPoint.z;
					if(z >= box.z1 && z <= box.z2) {
						t = t2;
					}
				}

			}
		}

		{
			float t2 = intersectionWithPlain(nearTmpPoint.z, farTmpPoint.z, box.z1);
			if(t2 < t) {
				float x = (farTmpPoint.x - nearTmpPoint.x) * t2 + nearTmpPoint.x;
				if(x >= box.x1 && x <= box.x2) {
					float y = (farTmpPoint.y - nearTmpPoint.y) * t2 + nearTmpPoint.y;
					if(y >= box.y1 && y <= box.y2) {
						t = t2;
					}
				}

			}
		}

		{
			float t2 = intersectionWithPlain(nearTmpPoint.z, farTmpPoint.z, box.z2);
			if(t2 < t) {
				float x = (farTmpPoint.x - nearTmpPoint.x) * t2 + nearTmpPoint.x;
				if(x >= box.x1 && x <= box.x2) {
					float y = (farTmpPoint.y - nearTmpPoint.y) * t2 + nearTmpPoint.y;
					if(y >= box.y1 && y <= box.y2) {
						t = t2;
					}
				}

			}
		}

		return t;
	}

	private float intersectionWithPlain(float f1, float f2, float plainParameter) {
		return (plainParameter - f1) / (f2 - f1);
	}

	private Vector4f norm(Vector4f v) {
		float w = v.w;
		v.x /= w;
		v.y /= w;
		v.z /= w;
		v.w = 1f;
		return v;
	}

	public Matrix4f inversePerspective = new Matrix4f();
	public Matrix4f inverseCamera = new Matrix4f();
	public int width;
	public int height;

	public Vector4f nearEndPoint = new Vector4f();
	public Vector4f farEndPoint = new Vector4f();
	public boolean valid;

	private Vector4f nearTmpPoint = new Vector4f();
	private Vector4f farTmpPoint = new Vector4f();
	private Matrix4f basicLookAt = new Matrix4f();

}
