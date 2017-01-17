using Microsoft.DirectX;

namespace AnimaTool
{
	interface IPart
	{
		Vector3 centerPoint(int scene);
		void scale(Matrix matrix, int scene);
		void move(Vector3 direction, int scene);
	}
}