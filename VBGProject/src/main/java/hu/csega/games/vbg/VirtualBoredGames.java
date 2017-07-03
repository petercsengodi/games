package hu.csega.games.vbg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import hu.csega.games.vbg.main.VBGAllGames;
import hu.csega.games.vbg.swing.VBGCanvas;
import hu.csega.games.vbg.swing.VBGFrame;
import hu.csega.games.vbg.util.GeometricUtil;

public class VirtualBoredGames {

	private static VBGFrame frame;
	private static VBGCanvas canvas;

	public static VBGFrame getFrame() {
		return frame;
	}

	public static VBGCanvas getCanvas() {
		return canvas;
	}

	public static void main(String[] args) {

		// I don't know, why, but it seems that because of the dynamic classloader
		// some classes may only be accessed if the main class references it
		GeometricUtil.distance(0, 0, 1, 1);

		// STARTING THE APPLICATION

		frame = new VBGFrame();
		canvas = frame.getCanvas();
		canvas.setGame(VBGAllGames.SELECTOR);
		frame.setFullScreen(true);
		frame.setVisible(true);
		frame.startThread();
	}

	public static String fromSerializableToString(Serializable serializable) {
		if(serializable == null)
			return "";

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(serializable);
			oos.close();
			return Base64.encode(baos.toByteArray());
		} catch(IOException ex) {
			System.out.println("Serialization failed.");
			ex.printStackTrace();
			return "";
		}
	}

	public static Serializable fromStringToSerializable(String serialized) {
		if(serialized == null || serialized.length() == 0)
			return null;

		byte[] serializedBytes = Base64.decode(serialized);
		InputStream input = new ByteArrayInputStream(serializedBytes);

		try {
			ObjectInputStream ois = new ObjectInputStream(input);
			return (Serializable)ois.readObject();
		} catch(IOException | ClassNotFoundException ex) {
			System.out.println("Deserialization failed.");
			ex.printStackTrace();
			return null;
		}
	}

	public void start() {
		main(null);
	}

}
