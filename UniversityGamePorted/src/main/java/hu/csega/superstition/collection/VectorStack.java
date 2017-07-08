package hu.csega.superstition.collection;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;

import hu.csega.superstition.util.Production;

public class VectorStack {

	public static synchronized Vector4f newVector4f() {
		int length = vectors.size();
		if(length <= sizeNow) {
			for(int i = length; i < sizeNow; i++)
				vectors.add(new Vector4f());
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

		Vector4f ret = new Vector4f();
		vectors.add(ret);
		sizeNow++;
		return ret;
	}

	public static synchronized boolean release(Vector4f v) {
		if(vectors.get(sizeNow-1) == v) {
			sizeNow--;
			return true;
		} else {
			if(Production.PRODUCTION)
				return false;

			throw new RuntimeException("This vector wasn't the last taken out!");
		}
	}

	private static List<Vector4f> vectors = new ArrayList<>();
	private static int sizeNow = 0;

	private static final int MAX_LENGTH = 100000;
}
