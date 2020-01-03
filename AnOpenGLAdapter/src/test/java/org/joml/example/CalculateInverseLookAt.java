package org.joml.example;

import java.util.Random;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class CalculateInverseLookAt {

	public static void main(String[] args) {

		// Object placement.
		Vector4f modelPosition = new Vector4f(100f, 30f, 50f, 1f);
		Vector4f modelDirection = new Vector4f(25f, 25f, 15f, 1f);
		Vector4f modelUp = new Vector4f(3f, 3f, 10f, 1f);

		/*
		modelPosition = new Vector4f(100f, 30f, -50f, 1f);
		modelDirection = new Vector4f(-25f, -25f, 15f, 1f);
		modelUp = new Vector4f(13f, -3f, 10f, 1f);
		 */

		// Calculating original lookAt matrix.
		Vector4f modelCenter = new Vector4f();
		modelCenter.add(modelPosition);
		modelCenter.add(modelDirection);

		Matrix4f lookAt = new Matrix4f();
		lookAt.lookAt(modelPosition.x, modelPosition.y, modelPosition.z,
				modelCenter.x, modelCenter.y, modelCenter.z,
				modelUp.x, modelUp.y, modelUp.z);

		// Log it.
		System.out.println("LookAt:\n" + lookAt);


		// Inverse by library:
		Matrix4f invLookAt = new Matrix4f(lookAt);
		invLookAt.invertLookAt();
		// invLookAt.invert();
		System.out.println("LibInverse LookAt:\n" + invLookAt);


		// Signature vectors:
		Vector4f worldEye = new Vector4f(0f, 0f, 0f, 1f);
		Vector4f worldCenter = new Vector4f(0f, 0f, -1f, 1f); // Why -1f ???
		Vector4f worldUp = new Vector4f(0f, 1f, 0f, 1f);

		// Conversion:

		Vector4f convertedEye = new Vector4f();
		Vector4f convertedCenter = new Vector4f();
		Vector4f convertedUp = new Vector4f();

		lookAt.transform(worldEye, convertedEye);
		lookAt.transform(worldCenter, convertedCenter);
		lookAt.transform(worldUp, convertedUp);

		System.out.println("Converted Eye:\n" + convertedEye);
		System.out.println("Converted Center:\n" + convertedCenter);
		System.out.println("Converted Up:\n" + convertedUp);
		System.out.println();

		// Creating new lookAt matrix:
		Matrix4f convertedLookAt = new Matrix4f();
		convertedLookAt.lookAt(convertedEye.x, convertedEye.y, convertedEye.z,
				convertedCenter.x, convertedCenter.y, convertedCenter.z,
				convertedUp.x - convertedEye.x, convertedUp.y - convertedEye.y, convertedUp.z - convertedEye.z);
		System.out.println("Converted LookAt:\n" + convertedLookAt);

		// Reverse:
		Vector4f reversedEye = new Vector4f();
		Vector4f reversedCenter = new Vector4f();
		Vector4f reversedUp = new Vector4f();
		convertedLookAt.transform(convertedEye, reversedEye);
		convertedLookAt.transform(convertedCenter, reversedCenter);
		convertedLookAt.transform(convertedUp, reversedUp);

		System.out.println("Reversed Eye:\n" + reversedEye);
		System.out.println("Reversed Center:\n" + reversedCenter);
		System.out.println("Reversed Up:\n" + reversedUp);
		System.out.println();


		// Inverted:
		Vector4f invertedEye = new Vector4f();
		Vector4f invertedCenter = new Vector4f();
		Vector4f invertedUp = new Vector4f();
		invLookAt.transform(convertedEye, invertedEye);
		invLookAt.transform(convertedCenter, invertedCenter);
		invLookAt.transform(convertedUp, invertedUp);

		System.out.println("Inverted Eye:\n" + invertedEye);
		System.out.println("Inverted Center:\n" + invertedCenter);
		System.out.println("Inverted Up:\n" + invertedUp);
		System.out.println();

		// Extra tests:
		Random rnd = new Random(System.currentTimeMillis());
		for(int index = 0; index < 10; index++) {
			Vector4f original = new Vector4f(rnd.nextInt(200) - 100, rnd.nextInt(200) - 100, rnd.nextInt(200) - 100, 1f);
			Vector4f reversed = new Vector4f();
			Vector4f inverted = new Vector4f();
			invLookAt.transform(original, inverted);
			convertedLookAt.transform(original, reversed);
			System.out.println("Case " + index + ":\nOriginal: " + original + "\nReversed: " + reversed + "\nInverted: " + inverted + '\n');
		}
	}

}
