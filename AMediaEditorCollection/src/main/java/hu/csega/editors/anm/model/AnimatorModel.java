package hu.csega.editors.anm.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

	private static byte[] serialize(Serializable object) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ObjectOutputStream out = new ObjectOutputStream(baos)) {
			out.writeObject(object);
		} catch (IOException ex) {
			throw new RuntimeException("Error occurred when trying to serialize object!", ex);
		}
		return baos.toByteArray();
	}

	private static Serializable deserialize(byte[] array) {
		Serializable ret = null;

		ByteArrayInputStream bais = new ByteArrayInputStream(array);
		try (ObjectInputStream in = new ObjectInputStream(bais)) {
			ret = (Serializable) in.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			throw new RuntimeException("Error occurred when trying to deserialize object!", ex);
		}

		return ret;
	}

}
