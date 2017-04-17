package hu.csega.games.adapters.opengl.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLProgramLogger extends PrintStream {

	public OpenGLProgramLogger(OpenGLLogStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public void println() {
		super.println();
		flushStream();
	}

	@Override
	public void println(char x) {
		super.println(x);
		flushStream();
	}

	@Override
	public void println(float x) {
		super.println(x);
		flushStream();
	}

	@Override
	public void println(char[] x) {
		super.println(x);
		flushStream();
	}

	@Override
	public void println(boolean x) {
		super.println(x);
		flushStream();
	}

	@Override
	public void println(double x) {
		super.println(x);
		flushStream();
	}

	@Override
	public void println(int x) {
		super.println(x);
		flushStream();
	}

	@Override
	public void println(long x) {
		super.println(x);
		flushStream();
	}

	@Override
	public void println(Object x) {
		super.println(x);
		flushStream();
	}

	@Override
	public void println(String x) {
		super.println(x);
		flushStream();
	}

	@Override
	public void flush() {
		super.flush();
		flushStream();
	}

	@Override
	public void close() {
		super.close();
		flushStream();
	}

	private void flushStream() {
		try {
			String s = new String(stream.stream.toByteArray(), "UTF-8");
			logger.info(s);
		} catch (Exception ex) {
			logger.error("*** LOGGING FAILED ***", ex);
		}

		stream.stream = new ByteArrayOutputStream();
	}

	private OpenGLLogStream stream;

	private static final Logger logger = LoggerFactory.createLogger(OpenGLProgramLogger.class);
}
