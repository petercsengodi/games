package hu.csega.superstition.collection;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import hu.csega.superstition.util.Production;

public class VectorStack {

	public static synchronized Vector3f newVector3f() {
		int length = vectors.size();
		if(length <= sizeNow) {
			for(int i = length; i < sizeNow; i++)
				vectors.add(new Vector3f());
		}

		if(sizeNow >= MAX_LENGTH) {
			if(Production.PRODUCTION) {
				vectors.clear();
				sizeNow = 0;
			} else {
				throw new RuntimeException("Vector Pool Overloaded!");
			}
		}

		if(sizeNow < length)
			return vectors.get(sizeNow++);

		Vector3f ret = new Vector3f();
		vectors.add(ret);
		sizeNow++;
		return ret;
	}

	public static synchronized boolean release(Vector3f v) {
		if(vectors.get(sizeNow-1) == v) {
			sizeNow--;
			return true;
		} else {
			if(Production.PRODUCTION)
				return false;

			throw new RuntimeException("This vector wasn't the last taken out!");
		}
	}

	private static List<Vector3f> vectors = new ArrayList<>();
	private static int sizeNow = 0;

	private static final int MAX_LENGTH = 100000;
}
