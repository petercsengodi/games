package hu.csega.superstition.game.enemy;

class Spider : Enemy
{
	protected class Representative : Clipable
	{
		public bool modified;

		public Representative()
			: base(new Vector3(0f, 0f, 0f),
			new Vector3(0f, 0f, 0f),
			new Vector3(0f, 0f, 0f))
		{
			velocity = new Vector3(0f, 0f, 0f);
			modified = false;
		}

		public override void Squash(Engine.StaticVectorLibrary.Direction dir, Vector3 box1, Vector3 box2, Vector3 sqpoint)
		{
			diff = sqpoint - position;
			modified = true;
		}

	}

	protected Representative[] representatives;

	protected const int STAND = 0, RUNNING = 1;
	protected const float P_HEIGHT = 0.1f,
		P_FORE = 0.04f, P_SIDE = 0.1f,
		P_WALK = 0.01f, P_FALL = 1f,
		P_RANDOM_CIRCLE = 0.001f;

	public Spider()
	{
		representatives = new Representative[4];
		for(int i = 0; i < representatives.Length; i++)
		{
			representatives[i] = new Representative();
		}
		up = new Vector3(0f, 1f, 0f);
		direction = new Vector3(0f, 0f, -1f);
	}

	public override void Build(Engine.Engine engine)
	{
		animations = new Animation[2];
		animations[0] = Library.Animations().getAnimation("spider_stand");
		animations[1] = Library.Animations().getAnimation("spider_running");
		current = animations[Spider.STAND];
		corner1 = current.GetBoundingBox1(0);
		corner2 = current.GetBoundingBox2(0);
	}

	[Serializable]
	class SpiderData : GameObjectData
	{
		public bool alive;
		public Vector3 direction;
		public Vector3 position;
		public EnemyState state;
		public Vector3 up;

		public SpiderData()
		{
			description = "Spider Data";
		}

		public override object create()
		{
			return new Spider(this);
		}

	}

	public override GameLib.GameObjectData getData()
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
		SpiderData data = spider as SpiderData;
		representatives = new Representative[4];
		for(int i = 0; i < representatives.Length; i++)
		{
			representatives[i] = new Representative();
		}

		this.alive = data.alive;
		this.direction = data.direction;
		this.position = data.position;
		this.state = data.state;
		this.up = data.up;
	}

	public override void Period()
	{
		if(CurrentRoom != null)
		{
			Vector3 right = Vector3.Cross(up, direction);
			Vector3 left = - right;
			bool modified = false;
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
				up = new Vector3(0f, 1f, 0f);
				if(Math.Abs(direction.X) < 0.001f &&
					Math.Abs(direction.Z) < 0.001f)
				{
					direction = new Vector3(0f, 0f, -1f);
				}
				else
				{
					direction.Y = 0f;
					direction.Normalize();
				}
				velocity = new Vector3(0f, -P_FALL, 0f);
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
				velocity = new Vector3(0f, 0f, 0f);
			}

		}

		base.Period();
	}


}