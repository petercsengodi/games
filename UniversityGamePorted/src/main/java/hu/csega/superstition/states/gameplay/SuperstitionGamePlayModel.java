package hu.csega.superstition.states.gameplay;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.superstition.game.SuperstitionGameModify;
import hu.csega.superstition.game.SuperstitionGamePlayElements;
import hu.csega.superstition.game.SuperstitionSerializableModel;

public class SuperstitionGamePlayModel implements GameEngineCallback {

	private SuperstitionSerializableModel serializableModel = new SuperstitionSerializableModel();
	public SuperstitionGamePlayElements elements = new SuperstitionGamePlayElements();

	public SuperstitionSerializableModel getSerializableModel() {
		return serializableModel;
	}

	public void setSerializableModel(SuperstitionSerializableModel serializableModel) {
		this.serializableModel = serializableModel;
	}

	public SuperstitionGamePlayElements getElements() {
		return elements;
	}

	public void setElements(SuperstitionGamePlayElements elements) {
		this.elements = elements;
	}

	@Override
	public Object call(GameEngineFacade facade) {

		GameControl control = facade.control();
		SuperstitionGameModify.modify(serializableModel, control);

		return facade;
	}

}
