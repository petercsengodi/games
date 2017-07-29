package hu.csega.game.play.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PlayFrame implements Serializable {

	private Map<PlayRole, PlayAct> acting = new HashMap<>();
	private String instructions;

	private static final long serialVersionUID = 1L;

}
