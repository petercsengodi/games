package hu.csega.games.adapters.opengl.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OpenGLLogStream extends OutputStream {

	@Override
	public void write(int b) throws IOException {
		stream.write(b);
	}

	ByteArrayOutputStream stream;
}
