package hu.csega.editors.anm.model;

import java.util.ArrayList;
import java.util.List;

public class AnimatorModel {

	private AnimationPersistent persistent;
	private List<Object> previousStates;
	private List<Object> nextStates;

	public AnimatorModel() {
		this.previousStates = new ArrayList<>();
		this.nextStates = new ArrayList<>();
	}

	public void finalizeMoves() {
	}

	public AnimationPersistent getPersistent() {
		return persistent;
	}

	public void setPersistent(AnimationPersistent persistent) {
		this.persistent = persistent;
	}

	public List<Object> getPreviousStates() {
		return previousStates;
	}

	public void setPreviousStates(List<Object> previousStates) {
		this.previousStates = previousStates;
	}

	public List<Object> getNextStates() {
		return nextStates;
	}

	public void setNextStates(List<Object> nextStates) {
		this.nextStates = nextStates;
	}

}
