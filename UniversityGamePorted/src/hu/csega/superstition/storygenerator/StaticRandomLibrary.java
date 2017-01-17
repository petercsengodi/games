package hu.csega.superstition.storygenerator;

public class StaticRandomLibrary
{
	static private Random random;

	public static void Initialize()
	{
		random = new Random();
	}

	public static int SelectValue(double[] distribution)
	{
		return SelectValue(distribution, 0, distribution.Length-1);
	}

	public static int SelectValue(double[] distribution, int min, int max)
	{
		double val = random.NextDouble();
		double[] distribution2; int i;

		double all = 0.0;
		for(i = min; i <= max; i++) all += distribution[i];
		distribution2 = new double[distribution.Length];

		for(i = 0; i < distribution.Length; i++)
		{
			if((i < min) || (i > max)) distribution2[i] = 0.0;
			else distribution2[i] = distribution[i] / all;
		}

		if(val == 0.0) return min; else if(val == 1.0) return max; else
			for(i=0; val >= 0.0; i++) val -= distribution2[i]; return i-1;
	}

	public static bool Decidion(double probability)
	{
		if(random.NextDouble() <= probability) return true; else return false;
	}

	public static double DoubleValue()
	{
		return random.NextDouble();
	}

	public static double DoubleValue(double Scale)
	{
		return random.NextDouble() * Scale;
	}
}