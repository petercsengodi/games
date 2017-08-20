package hu.csega.games.engine.intf;

public class GameDescriptor {

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMouseCentered() {
		return mouseCentered;
	}

	public void setMouseCentered(boolean mouseCentered) {
		this.mouseCentered = mouseCentered;
	}

	private String id;
	private String title;
	private String version;
	private String description;
	private boolean mouseCentered = true;
}
