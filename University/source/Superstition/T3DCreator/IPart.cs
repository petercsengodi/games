using Microsoft.DirectX;

namespace T3DCreator
{
	public interface IPart
	{
		void move(Vector3 direction);
		void moveTexture(Vector2 direction);
		bool hasPart(IPart part);
		Vector3 centerPoint();
		void scale(Matrix matrix);
	}
}