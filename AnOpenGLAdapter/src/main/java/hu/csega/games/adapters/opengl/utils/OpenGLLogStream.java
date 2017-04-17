package hu.csega.games.adapters.opengl.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OpenGLLogStream extends OutputStream {

	@Override
	public void write(int b) throws IOException {
		if(stream == null)
			stream = new ByteArrayOutputStream();
		stream.write(b);
	}

	ByteArrayOutputStream stream;
}
