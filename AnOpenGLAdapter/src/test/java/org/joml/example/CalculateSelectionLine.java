package org.joml.example;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import hu.csega.games.engine.g3d.GameHitBox;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameSelectionLine;

public class CalculateSelectionLine {

	public static void main(String[] args) {

		// Object placement.
		GameObjectPlacement placement = new GameObjectPlacement();

		Matrix4f inverseModelTransformation = new Matrix4f();

		Vector4f tmpEye = new Vector4f();
		Vector4f tmpCenter = new Vector4f();
		Vector4f tmpUp = new Vector4f();

		Matrix4f modelTransformation = new Matrix4f();
		placement.calculateInverseLookAt(inverseModelTransformation, tmpEye, tmpCenter, tmpUp, modelTransformation);


		// Camera transformation:
		Vector4f cameraEye = new Vector4f(0f, -1000f, 1000f, 1f);
		Vector4f cameraCenter = new Vector4f(0f, 0f, 0f, 1f);
		Vector4f cameraUp = new Vector4f(0f, -1f, -1f, 1f);
		Matrix4f camera = new Matrix4f();
		camera.lookAt(cameraEye.x, cameraEye.y, cameraEye.z,
				cameraCenter.x, cameraCenter.y, cameraCenter.z,
				cameraUp.x, cameraUp.y, cameraUp.z);

		Matrix4f inverseCamera = new Matrix4f();
		camera.invert(inverseCamera);

		// View port:
		int WIDTH = 800;
		int HEIGHT = 600;

		// Perspective:
		float viewAngle = (float) Math.toRadians(45);
		float aspect = (float) WIDTH / HEIGHT;
		float zNear = 0.1f;
		float zFar = 10000.0f;
		Matrix4f perspectiveMatrix = new Matrix4f().identity().setPerspective(viewAngle, aspect, zNear, zFar);
		Matrix4f inversePerspectiveMatrix = new Matrix4f();
		perspectiveMatrix.invert(inversePerspectiveMatrix);

		// Click at pixel:
		int CLICK_X = 300;
		int CLICK_Y = 300;

		Vector4f near = new Vector4f(CLICK_X, CLICK_Y, 0f, 1f);
		Vector4f far = new Vector4f(CLICK_X, CLICK_Y, 1f, 1f);
		System.out.println("Normal near: " + near + " Far: " + far);

		Vector4f iNear = norm(inversePerspectiveMatrix.transform(near));
		Vector4f iFar = norm(inversePerspectiveMatrix.transform(far));
		System.out.println("Inverse near: " + iNear + " Far: " + iFar);

		Vector4f cNear = norm(perspectiveMatrix.transform(iNear).normalize());
		Vector4f cFar = norm(perspectiveMatrix.transform(iFar).normalize());
		System.out.println("Check near: " + cNear + " Far: " + cFar);



		// Sample triangle:
		Vector4f st1 = new Vector4f(-50f, 0f, -50f, 1f);
		Vector4f st2 = new Vector4f(50f, 0f, -50f, 1f);
		Vector4f st3 = new Vector4f(-50f, 0f, 50f, 1f);
		Vector4f sw = weightOf(st1, st2, st3);

		System.out.println("ST: " + st1 + " " + st2 + " " + st3 + "\nWeight point: " + sw);

		// Transformed:
		Vector4f tt1 = transform(st1, modelTransformation, camera, perspectiveMatrix);
		Vector4f tt2 = transform(st2, modelTransformation, camera, perspectiveMatrix);
		Vector4f tt3 = transform(st3, modelTransformation, camera, perspectiveMatrix);
		Vector4f tw = weightOf(tt1,  tt2,  tt3);
		System.out.println("TT: " + tt1 + " " + tt2 + " " + tt3 + "\nWeight point: " + tw);

		// Check selection:
		Vector4f ct1 = transform(tt1, inversePerspectiveMatrix, inverseCamera, inverseModelTransformation);
		Vector4f ct2 = transform(tt2, inversePerspectiveMatrix, inverseCamera, inverseModelTransformation);
		Vector4f ct3 = transform(tt3, inversePerspectiveMatrix, inverseCamera, inverseModelTransformation);
		Vector4f cw = weightOf(ct1,  ct2,  ct3);
		System.out.println("CT: " + ct1 + " " + ct2 + " " + ct3 + "\nWeight point: " + cw);

		float referenceValue;
		{
			// Selection line:
			Vector4f sl1 = new Vector4f(tw.x, tw.y, 0f, 1f);
			Vector4f sl2 = new Vector4f(tw.x, tw.y, 1f, 1f);

			Vector4f tl1 = transform(sl1, inversePerspectiveMatrix, inverseCamera, inverseModelTransformation);
			Vector4f tl2 = transform(sl2, inversePerspectiveMatrix, inverseCamera, inverseModelTransformation);
			System.out.println("Selection Line (1): " + tl1 + " -> " + tl2);

			// Equation: p = (tl2 - tl1) * t + tl1 Intersection of the y=0 plane:
			float t = -tl1.y / (tl2.y - tl1.y);
			System.out.println("T parameter: " + t);
			referenceValue = t;

			float x = (tl2.x - tl1.x) * t + tl1.x;
			float z = (tl2.z - tl1.z) * t + tl1.z;
			Vector4f intersection = new Vector4f(x, 0f, z, 1f);
			System.out.println("Point on y=0 plane: " + intersection);
			System.out.println("Original weight point: " + sw);
		}

		{
			// Selection line:
			Vector4f sl1 = new Vector4f(0f, 0f, 0f, 1f);
			Vector4f sl2 = new Vector4f(tw.x, tw.y, 1f, 1f);

			Vector4f tl1 = transform(sl1, inversePerspectiveMatrix, inverseCamera, inverseModelTransformation);
			Vector4f tl2 = transform(sl2, inversePerspectiveMatrix, inverseCamera, inverseModelTransformation);
			System.out.println("Selection Line (2): " + tl1 + " -> " + tl2);

			// Equation: p = (tl2 - tl1) * t + tl1 Intersection of the y=0 plane:
			float t = -tl1.y / (tl2.y - tl1.y);
			System.out.println("T parameter: " + t);

			float x = (tl2.x - tl1.x) * t + tl1.x;
			float z = (tl2.z - tl1.z) * t + tl1.z;
			Vector4f intersection = new Vector4f(x, 0f, z, 1f);
			System.out.println("Point on y=0 plane: " + intersection);
			System.out.println("Original weight point: " + sw);
		}

		GameSelectionLine line = new GameSelectionLine();
		line.setBaseMatricesAndViewPort(inversePerspectiveMatrix, inverseCamera, WIDTH, HEIGHT);
		line.initialize(tw.x, tw.y);
		System.out.println("Selection line in world: " + line);

		Vector4f nt1 = norm(new Vector4f(line.nearEndPoint));
		Vector4f nt2 = norm(new Vector4f(line.farEndPoint));
		Vector4f sl1 = new Vector4f(tw.x, tw.y, 0f, 1f);
		Vector4f sl2 = new Vector4f(tw.x, tw.y, 1f, 1f);
		Vector4f tl1 = transform(sl1, inversePerspectiveMatrix, inverseCamera);
		Vector4f tl2 = transform(sl2, inversePerspectiveMatrix, inverseCamera);
		System.out.println("Selection line in world: " + nt1 + " -> " + nt2 + " Reference values: " + tl1 + " -> " + tl2);

		GameHitBox box = new GameHitBox(-50, 0, -50, 50, 20, 50);
		float t = line.intersection(placement, box);
		System.out.println("T parameter: " + t + " Reference value: " + referenceValue);

	}

	public static Vector4f transform(Vector4f basic, Matrix4f... transformation) {
		Vector4f v = new Vector4f(basic);

		for(Matrix4f t : transformation) {
			v = t.transform(v);
		}

		return norm(v);
	}

	public static Vector4f weightOf(Vector4f t1, Vector4f t2, Vector4f t3) {
		Vector4f v = new Vector4f();
		v.x = (t1.x + t2.x + t3.x) / 3f;
		v.y = (t1.y + t2.y + t3.y) / 3f;
		v.z = (t1.z + t2.z + t3.z) / 3f;
		v.w = (t1.w + t2.w + t3.w) / 3f;
		return norm(v);
	}

	public static Vector4f norm(Vector4f v) {
		float w = v.w;
		v.x /= w;
		v.y /= w;
		v.z /= w;
		v.w = 1f;
		return v;
	}

}
