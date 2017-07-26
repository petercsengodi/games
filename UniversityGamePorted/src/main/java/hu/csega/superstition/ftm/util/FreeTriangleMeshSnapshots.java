package hu.csega.superstition.ftm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class FreeTriangleMeshSnapshots {

	private List<byte[]> previousStates = new ArrayList<>();
	private List<byte[]> nextStates = new ArrayList<>();

	public void addState(Serializable state) {
		byte[] snapshot = serialize(state);
		previousStates.add(snapshot);
		nextStates.clear();
	}

	public Serializable undo(Serializable current) {
		if(previousStates.isEmpty())
			return null;

		byte[] currentState = serialize(current);
		nextStates.add(currentState);
		byte[] snapshot = previousStates.remove(previousStates.size() - 1);
		Serializable newState = (Serializable)deserialize(snapshot);
		return newState;
	}

	public Serializable redo(Serializable current) {
		if(nextStates.isEmpty())
			return null;

		byte[] currentState = serialize(current);
		previousStates.add(currentState);
		byte[] snapshot = nextStates.remove(nextStates.size() - 1);
		Serializable newState = (Serializable)deserialize(snapshot);
		return newState;
	}

	public void clear() {
		previousStates.clear();
		nextStates.clear();
	}

	public Serializable currentSnapshot() {
		byte[] snapshot = previousStates.get(previousStates.size() - 1);
		return (Serializable) deserialize(snapshot);
	}

	public static byte[] serialize(Object serializable) {
		if (serializable == null)
			return null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(serializable);
			oos.close();
			return baos.toByteArray();
		} catch (IOException ex) {
			logger.error("Error during serializing " + serializable.getClass().getSimpleName(), ex);
			return null;
		}
	}

	public static Object deserialize(byte[] serialized) {
		try {
			InputStream input = new ByteArrayInputStream(serialized);
			ObjectInputStream ois = new ObjectInputStream(input);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			logger.error("Error during deserializing...", ex);
			return null;
		}
	}

	public static void writeAllBytes(String fileName, byte[] bytes) {
		if(bytes == null || bytes.length == 0) {
			logger.error("Not writing zero sized file: " + fileName);
			return;
		}

		File file = new File(fileName);
		try (OutputStream stream = new FileOutputStream(file)) {
			stream.write(bytes);
		} catch(IOException ex) {
			logger.error("Error during writing file: " + fileName, ex);
		}
	}

	public static byte[] readAllBytes(String fileName) {
		File file = new File(fileName);
		int size = (int)file.length();
		if(size == 0) {
			logger.error("Zero sized file: " + fileName);
			return null;
		}

		byte[] ret = new byte[size];
		byte[] array = new byte[2000];
		int pos = 0;
		int read;

		try (InputStream ios = new FileInputStream(file)) {
			while ( (read = ios.read(array, 0, 2000)) >= 0 ) {
				if(read == 0)
					continue;

				if(pos + read > size) {
					logger.error("Invalid sized file: " + fileName);
					return null;
				}

				System.arraycopy(array, 0, ret, pos, read);
				pos += read;
			}
		} catch(IOException ex) {
			logger.error("Error during reading file: " + fileName, ex);
			return null;
		}

		return array;
	}

	private static final Logger logger = LoggerFactory.createLogger(FreeTriangleMeshSnapshots.class);
}
