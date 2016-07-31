package hu.csega.agomol.characters;

import hu.csega.toolshed.v1.json.common.JSONGetter;
import hu.csega.toolshed.v1.json.common.JSONSetter;
import hu.csega.toolshed.v1.json.common.JSONUtil;

public class Player {

	@JSONGetter
	public String getName() {
		return name;
	}

	@JSONSetter
	public void setName(String name) {
		this.name = name;
	}

	@JSONGetter(Long.class)
	public Long getHealthPoints() {
		return healthPoints;
	}

	@JSONSetter(Long.class)
	public void setHealthPoints(Long healthPoints) {
		this.healthPoints = healthPoints;
	}

	@JSONGetter
	public String getGuardianId() {
		return guardianId;
	}

	@JSONSetter
	public void setGuardianId(String guardianId) {
		this.guardianId = guardianId;
	}

	@JSONGetter(Long.class)
	public Long getHeight() {
		return height;
	}

	@JSONSetter(Long.class)
	public void setHeight(Long height) {
		this.height = height;
	}

	@JSONGetter(Long.class)
	public Long getWeight() {
		return weight;
	}

	@JSONSetter(Long.class)
	public void setWeight(Long weight) {
		this.weight = weight;
	}

	@JSONGetter(Long.class)
	public Long getStrengthPoints() {
		return strengthPoints;
	}

	@JSONSetter(Long.class)
	public void setStrengthPoints(Long strengthPoints) {
		this.strengthPoints = strengthPoints;
	}

	@JSONGetter(Long.class)
	public Long getIntelligencePoint() {
		return intelligencePoint;
	}

	@JSONSetter(Long.class)
	public void setIntelligencePoint(Long intelligencePoint) {
		this.intelligencePoint = intelligencePoint;
	}

	@JSONGetter(Long.class)
	public Long getCharmPoints() {
		return charmPoints;
	}

	@JSONSetter(Long.class)
	public void setCharmPoints(Long charmPoints) {
		this.charmPoints = charmPoints;
	}

	@JSONGetter(Long.class)
	public Long getManaPoints() {
		return manaPoints;
	}

	@JSONSetter(Long.class)
	public void setManaPoints(Long manaPoints) {
		this.manaPoints = manaPoints;
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

	private String name;
	private Long healthPoints;
	private String guardianId;
	private Long height;
	private Long weight;
	private Long strengthPoints;
	private Long intelligencePoint;
	private Long charmPoints;
	private Long manaPoints;
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
