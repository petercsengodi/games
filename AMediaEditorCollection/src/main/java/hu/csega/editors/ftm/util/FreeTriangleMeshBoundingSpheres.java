package hu.csega.editors.ftm.util;

import static hu.csega.editors.ftm.util.FreeTriangleMeshMathLibrary.*;

import java.util.ArrayList;
import java.util.List;

import hu.csega.editors.ftm.model.FreeTriangleMeshPoint;

public class FreeTriangleMeshBoundingSpheres {

	private double radius;
	private List<FreeTriangleMeshPoint> centers = new ArrayList<>();

	/**
	 * If all points of the triangle are in the sphere around the weight center, we create that sphere,
	 * if not, then we cut the triangle into half at the longest edge.
	 */
	public void addTriangle(FreeTriangleMeshPoint p1, FreeTriangleMeshPoint p2, FreeTriangleMeshPoint p3) {
		FreeTriangleMeshPoint weightCenter = avg(p1, p2, p3);
		double d1, d2, d3;

		d1 = distance(p1, weightCenter);
		if(d1 < radius) {
			d2 = distance(p2, weightCenter);
			if(d2 < radius) {
				d3 = distance(p3, weightCenter);
				if(d3 < radius) {
					centers.add(weightCenter);
					return;
				}
			}
		}

		d1 = distance(p1, p2);
		d2 = distance(p2, p3);
		d3 = distance(p3, p1);

		if(d1 >= d2 && d1 >= d3) {
			// first edge is the longest
			weightCenter = avg(p1, p2);
			addTriangle(p1, weightCenter, p3);
			addTriangle(weightCenter, p2, p3);
			return;
		}

		// either d2 or d3 is bigger than d1,
		// and obviously the bigger will be the biggest

		if(d2 >= d3) {
			// second edge is the longest
			weightCenter = avg(p2, p3);
			addTriangle(p2, weightCenter, p1);
			addTriangle(weightCenter, p3, p1);
			return;
		}

		// third edge is the longest
		weightCenter = avg(p3, p1);
		addTriangle(p3, weightCenter, p2);
		addTriangle(weightCenter, p1, p2);
		return;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void clearSphereCenters() {
		centers.clear();
	}

	public List<FreeTriangleMeshPoint> getSphereCenters() {
		return centers;
	}
}
