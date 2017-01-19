package hu.csega.superstition.game.menu;

public class TriggerParams {

	public MainMenuSelection command;
	public String StringParameter;
	public Object ObjectParameter;

	public TriggerParams(MainMenuSelection command) {
		this.command = command;
		this.StringParameter = null;
		this.ObjectParameter = null;
	}

	public TriggerParams(MainMenuSelection command, String StringParameter) {
		this.command = command;
		this.StringParameter = StringParameter;
		this.ObjectParameter = null;
	}

	public TriggerParams(MainMenuSelection command, String StringParameter, Object ObjectParameter) {
		this.command = command;
		this.StringParameter = StringParameter;
		this.ObjectParameter = ObjectParameter;
	}
};