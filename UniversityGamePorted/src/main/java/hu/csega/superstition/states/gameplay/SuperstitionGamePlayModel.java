package hu.csega.superstition.states.gameplay;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.superstition.game.SuperstitionGameModify;
import hu.csega.superstition.game.SuperstitionGameElements;
import hu.csega.superstition.game.SuperstitionSerializableModel;

public class SuperstitionGamePlayModel implements GameEngineCallback {

	private SuperstitionSerializableModel serializableModel = new SuperstitionSerializableModel();
	public SuperstitionGameElements elements = new SuperstitionGameElements();

	public SuperstitionSerializableModel getSerializableModel() {
		return serializableModel;
	}

	public void setSerializableModel(SuperstitionSerializableModel serializableModel) {
		this.serializableModel = serializableModel;
	}

	public SuperstitionGameElements getElements() {
		return elements;
	}

	public void setElements(SuperstitionGameElements elements) {
		this.elements = elements;
	}

	@Override
	public Object call(GameEngineFacade facade) {

		GameControl control = facade.control();
		SuperstitionGameModify.modify(serializableModel, control);

		return facade;
	}

}
