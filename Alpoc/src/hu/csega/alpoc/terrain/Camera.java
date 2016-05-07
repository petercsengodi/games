package hu.csega.alpoc.terrain;

public class Camera {

	public Camera() {
		ux = uz = 0;
		uy = 1;
		alfa = 0;
		x = z = 0;
		y = 100;

		recalculateMatrix();
	}

	public void recalculateMatrix() {
		r11 = r22 = Math.cos(alfa);
		r12 = - (r21 = Math.sin(alfa));
	}

	public double x, y, z;
	public double dx, dy, dz;
	public double alfa;
	public double ux, uy, uz;

	public double r11, r12, r21, r22;

	public static Camera CAMERA = new Camera();
}
