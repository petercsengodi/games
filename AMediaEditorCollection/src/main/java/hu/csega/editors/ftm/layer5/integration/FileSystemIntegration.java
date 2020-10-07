package hu.csega.editors.ftm.layer5.integration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class FileSystemIntegration {

	public static byte[] serialize(Serializable serializable) {
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

	public static Serializable deserialize(byte[] serialized) {
		try {
			InputStream input = new ByteArrayInputStream(serialized);
			ObjectInputStream ois = new ObjectInputStream(input);
			return (Serializable) ois.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			logger.error("Error during deserializing...", ex);
			return null;
		}
	}

	private static final Logger logger = LoggerFactory.createLogger(FileSystemIntegration.class);
}
