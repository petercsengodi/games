package hu.csega.superstition.util;

import java.util.Random;

public class StaticRandomLibrary {
	private static final Random random = new Random();

	public static int selectValue(double[] distribution) {
		return selectValue(distribution, 0, distribution.length - 1);
	}

	public static int selectValue(double[] distribution, int min, int max) {
		double val = random.nextDouble();
		double[] distribution2;
		int i;

		double all = 0.0;
		for (i = min; i <= max; i++)
			all += distribution[i];

		distribution2 = new double[distribution.length];

		for (i = 0; i < distribution.length; i++) {
			if ((i < min) || (i > max))
				distribution2[i] = 0.0;
			else
				distribution2[i] = distribution[i] / all;
		}

		if (val == 0.0) {
			return min;
		} else if (val == 1.0) {
			return max;
		} else {
			for (i = 0; val >= 0.0; i++)
				val -= distribution2[i];
		}

		return i - 1;
	}

	public static boolean decision(double probability) {
		return (random.nextDouble() <= probability);
	}

	public static double doubleValue() {
		return random.nextDouble();
	}

	public static double DoubleValue(double scale) {
		return random.nextDouble() * scale;
	}

	private StaticRandomLibrary() {
	}
}