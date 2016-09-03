package hu.csega.agomol.characters;

import hu.csega.toolshed.v1.json.common.JSONGetter;
import hu.csega.toolshed.v1.json.common.JSONSetter;
import hu.csega.toolshed.v1.json.common.JSONUtil;

public class Player extends Character {

	@JSONGetter
	public String getGuardianId() {
		return guardianId;
	}

	@JSONSetter
	public void setGuardianId(String guardianId) {
		this.guardianId = guardianId;
	}

	@JSONGetter
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	@JSONSetter
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@JSONGetter
	public String getSpells() {
		return spells;
	}

	@JSONSetter
	public void setSpells(String spells) {
		this.spells = spells;
	}

	@JSONGetter
	public String getInventory() {
		return inventory;
	}

	@JSONSetter
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	private String guardianId;
	private String lastUpdateDate;
	private String spells;
	private String inventory;

	public static void main(String[] args) throws Exception {
		Player player = new Player();
		player.setName("csega");
		player.setHealthPoints(10000l);

		String json = JSONUtil.toJSON(player);
		System.out.println(json);

		Player p2 = JSONUtil.fromJSON(Player.class, json);
		System.out.println("Name: " + p2.getName());
		System.out.println("Health points: " + p2.getHealthPoints());
	}
}
