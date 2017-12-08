package hu.csega.games.library.story;

import java.util.List;

import hu.csega.games.library.model.SObject;
import hu.csega.games.library.model.STextureRef;
import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Superstition.Room")
public class SRoom implements SObject {

	@XmlField("text")
	public String getText() {
		return text;
	}

	@XmlField("text")
	public void setText(String text) {
		this.text = text;
	}

	@XmlField("element")
	public String getElement() {
		return element;
	}

	@XmlField("element")
	public void setElement(String element) {
		this.element = element;
	}

	@XmlField("special")
	public String getSpecial() {
		return special;
	}

	@XmlField("special")
	public void setSpecial(String special) {
		this.special = special;
	}

	@XmlField("weapon")
	public String getWeapon() {
		return weapon;
	}

	@XmlField("weapon")
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	@XmlField("x")
	public int getX() {
		return x;
	}

	@XmlField("x")
	public void setX(int x) {
		this.x = x;
	}

	@XmlField("y")
	public int getY() {
		return y;
	}

	@XmlField("y")
	public void setY(int y) {
		this.y = y;
	}

	@XmlField("corridors")
	public List<SRoom> getCorridors() {
		return corridors;
	}

	@XmlField("corridors")
	public void setCorridors(List<SRoom> corridors) {
		this.corridors = corridors;
	}

	@XmlField("wall")
	public STextureRef getWall() {
		return wall;
	}

	@XmlField("wall")
	public void setWall(STextureRef wall) {
		this.wall = wall;
	}

	@XmlField("floor")
	public STextureRef getFloor() {
		return floor;
	}

	@XmlField("floor")
	public void setFloor(STextureRef floor) {
		this.floor = floor;
	}

	private String text;
	private String element;
	private String special;
	private String weapon;
	private int x;
	private int y;

	private List<SRoom> corridors;
	private STextureRef wall;
	private STextureRef floor;
}