package hu.csega.agomol.characters;

import hu.csega.toolshed.v1.json.common.JSONGetter;
import hu.csega.toolshed.v1.json.common.JSONSetter;

public class Fight {

	@JSONGetter
	public String getId() {
		return id;
	}

	@JSONSetter
	public void setId(String id) {
		this.id = id;
	}

	@JSONGetter
	public String getPlayer() {
		return player;
	}

	@JSONSetter
	public void setPlayer(String player) {
		this.player = player;
	}

	@JSONGetter
	public String getMonster1() {
		return monster1;
	}

	@JSONSetter
	public void setMonster1(String monster1) {
		this.monster1 = monster1;
	}

	@JSONGetter
	public String getMonster2() {
		return monster2;
	}

	@JSONSetter
	public void setMonster2(String monster2) {
		this.monster2 = monster2;
	}

	@JSONGetter
	public String getMonster3() {
		return monster3;
	}

	@JSONSetter
	public void setMonster3(String monster3) {
		this.monster3 = monster3;
	}

	private String id;
	private String player;
	private String monster1;
	private String monster2;
	private String monster3;
}
