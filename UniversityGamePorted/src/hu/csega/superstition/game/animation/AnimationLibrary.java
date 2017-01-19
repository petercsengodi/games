package hu.csega.superstition.game.animation;

import hu.csega.superstition.game.Library;

public class AnimationLibrary extends Library
{
	public static AnimationLibrary create(Engine engine)
	{
		return new AnimationLibrary(engine);
	}

	private AnimationLibrary(Engine engine) : base(engine)
	{
	}

	public Animation getAnimation(string file_name)
	{
		AnimID id = null;

		foreach(AnimID aid in library)
		{
			if(aid.file_name.Equals(file_name))
			{
				id = aid;
				break;
			}
		}

		if(id == null)
		{
			id = new AnimID();
			id.file_name = file_name;
			id.anim = engine.GetAnimation(file_name);
		}

		return id.anim;
	}

	@Override
	public override void Clear()
	{
		library.Clear();
	}

} // End of class AnimLibrary