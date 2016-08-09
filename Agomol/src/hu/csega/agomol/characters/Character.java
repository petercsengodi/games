package hu.csega.agomol.characters;

import hu.csega.toolshed.v1.json.common.JSONGetter;
import hu.csega.toolshed.v1.json.common.JSONSetter;

public class Character {

	@JSONGetter
	public String getId() {
		return id;
	}

	@JSONSetter
	public void setId(String id) {
		this.id = id;
	}

	@JSONGetter
	public String getName() {
		return name;
	}

	@JSONSetter
	public void setName(String name) {
		this.name = name;
	}

	@JSONGetter
	public String getCharacterType() {
		return characterType;
	}

	@JSONSetter
	public void setCharacterType(String characterType) {
		this.characterType = characterType;
	}

	@JSONGetter(Long.class)
	public Long getHealthPoints() {
		return healthPoints;
	}

	@JSONSetter(Long.class)
	public void setHealthPoints(Long healthPoints) {
		this.healthPoints = healthPoints;
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

	protected String id;
	protected String name;
	protected String characterType;
	protected Long healthPoints;
	protected Long strengthPoints;
	protected Long intelligencePoint;
	protected Long charmPoints;
	protected Long manaPoints;
	protected Long height;
	protected Long weight;

}
