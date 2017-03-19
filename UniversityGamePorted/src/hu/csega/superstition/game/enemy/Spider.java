package hu.csega.superstition.game.enemy;

import org.joml.Vector3f;

import hu.csega.superstition.game.Engine;
import hu.csega.superstition.game.Library;
import hu.csega.superstition.game.StaticVectorLibrary;
import hu.csega.superstition.game.animation.Animation;
import hu.csega.superstition.game.object.Clipable;
import hu.csega.superstition.gamelib.network.GameObjectData;

public class Spider extends Enemy {

	protected class Representative extends Clipable
	{
		public boolean modified;

		public Representative() {
			super(new Vector3f(), new Vector3f(), new Vector3f());
			velocity = new Vector3f();
			modified = false;
		}

		@Override
		public void squash(StaticVectorLibrary.Direction dir, Vector3f box1, Vector3f box2, Vector3f sqpoint)
		{
			sqpoint.sub(position, diff);
			modified = true;
		}

	}

	protected Representative[] representatives;

	protected static final int STAND = 0, RUNNING = 1;
	protected static final float P_HEIGHT = 0.1f,
			P_FORE = 0.04f, P_SIDE = 0.1f,
			P_WALK = 0.01f, P_FALL = 1f,
			P_RANDOM_CIRCLE = 0.001f;

	public Spider()
	{
		representatives = new Representative[4];
		for(int i = 0; i < representatives.length; i++)
		{
			representatives[i] = new Representative();
		}
		up = new Vector3f(0f, 1f, 0f);
		direction = new Vector3f(0f, 0f, -1f);
	}

	@Override
	public void Build(Engine engine)
	{
		animations = new Animation[2];
		animations[0] = Library.Animations().getAnimation("spider_stand");
		animations[1] = Library.Animations().getAnimation("spider_running");
		current = animations[Spider.STAND];
		corner1 = current.GetBoundingBox1(0);
		corner2 = current.GetBoundingBox2(0);
	}

	class SpiderData extends GameObjectData
	{
		public boolean alive;
		public Vector3f direction;
		public Vector3f position;
		public EnemyState state;
		public Vector3f up;

		public SpiderData()
		{
			description = "Spider Data";
		}

		public Object create()
		{
			return new Spider(this);
		}

		/** Default serial version UID. */
		private static final long serialVersionUID = -1l;
	}

	@Override
	public GameObjectData getData()
	{
		SpiderData ret = new SpiderData();
		ret.alive = this.alive;
		ret.direction = this.direction;
		ret.position = this.position;
		ret.state = this.state;
		ret.up = this.up;
		return ret;
	}

	public Spider(GameObjectData spider)
	{
		SpiderData data = (Spider)spider;
		representatives = new Representative[4];
		for(int i = 0; i < representatives.length; i++)
		{
			representatives[i] = new Representative();
		}

		this.alive = data.alive;
		this.direction = data.direction;
		this.position = data.position;
		this.state = data.state;
		this.up = data.up;
	}

	@Override
	public void Period()
	{
		if(CurrentRoom != null)
		{
			Vector3f right = Vector3f.Cross(up, direction);
			Vector3f left = - right;
			boolean modified = false;
			up.Multiply(P_HEIGHT);

			representatives[0].position =
					position + up + direction * P_FORE;
			representatives[0].diff =
					up * (-2) + direction * (P_FORE * 3);
			representatives[1].position =
					position + up - direction * P_FORE;
			representatives[1].diff =
					up * (-2) - direction * (P_FORE * 3);
			representatives[2].position =
					position + up + right * P_SIDE;
			representatives[2].diff =
					up * (-2) + right * (P_SIDE * 3);
			representatives[3].position =
					position + up + left * P_SIDE;
			representatives[3].diff =
					up * (-2) + left * (P_SIDE * 3);

			for(int i = 0; i < representatives.Length; i++)
			{
				representatives[i].modified = false;
				CurrentRoom.ClipRoomsInSight(representatives[i]);
				modified |= representatives[i].modified;
				representatives[i].position +=
						representatives[i].diff;
			}

			if(!modified)
			{
				current = animations[Spider.STAND];

				// TODO: Normal falling clipping
				up = new Vector3f(0f, 1f, 0f);
				if(Math.Abs(direction.X) < 0.001f &&
						Math.Abs(direction.Z) < 0.001f)
				{
					direction = new Vector3f(0f, 0f, -1f);
				}
				else
				{
					direction.Y = 0f;
					direction.Normalize();
				}
				velocity = new Vector3f(0f, -P_FALL, 0f);
			}
			else
			{
				current = animations[Spider.RUNNING];

				// Crawling on wall
				direction = representatives[0].position -
						representatives[1].position;
				right = representatives[2].position -
						representatives[3].position;


				right.Normalize();
				direction += right * StaticRandomLibrary.
						FloatValue(-P_RANDOM_CIRCLE, P_RANDOM_CIRCLE);


				direction.Normalize();

				position =
						(representatives[0].position +
								representatives[1].position +
								representatives[2].position +
								representatives[3].position) * 0.25f;


				position += direction * P_WALK;
				up = Vector3.Cross(direction, right);
				up.Normalize();
				velocity = new Vector3f(0f, 0f, 0f);
			}

		}

		super.period();
	}


}