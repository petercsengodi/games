package hu.csega.superstition.game.menu;

public class TriggerParams
{
	public MainMenuSelection command;
	public string StringParameter;
	public object ObjectParameter;

	public TriggerParams(MainMenuSelection command)
	{
		this.command = command;
		this.StringParameter = null;
		this.ObjectParameter = null;
	}

	public TriggerParams(MainMenuSelection command, string StringParameter)
	{
		this.command = command;
		this.StringParameter = StringParameter;
		this.ObjectParameter = null;
	}

	public TriggerParams(MainMenuSelection command,
		string StringParameter, object ObjectParameter)
	{
		this.command = command;
		this.StringParameter = StringParameter;
		this.ObjectParameter = ObjectParameter;
	}
};