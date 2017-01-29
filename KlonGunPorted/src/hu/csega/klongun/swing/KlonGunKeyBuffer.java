package hu.csega.klongun.swing;

public class KlonGunKeyBuffer {

	// blocks thread until key is pressed
	public char readKey() {

		synchronized (this) {
			if(isKeyPressed())
				return next();
		}

		try {
			requestOn = true;
			waitForKeyBuffer.wait();
			requestOn = false;
		} catch (InterruptedException e) {
		}

		synchronized (this) {
			if(isKeyPressed())
				return next();
			else return 0;
		}
	}

	public synchronized boolean isKeyPressed() {
		return pos != limit;
	}

	public synchronized void add(char c) {
		b[(limit++) % b.length] = c;
		if(limit >= b.length)
			limit -= b.length;

		if(requestOn) {
			waitForKeyBuffer.notify();
		}
	}

	public synchronized char next() {
		char r = b[(pos++) % b.length];
		if (pos >= b.length)
			pos -= b.length;
		return r;
	}

	public char[] b = new char[1000];
	public int pos = 0, limit = 0;

	public boolean requestOn = false;
	public Object waitForKeyBuffer = "123123----213-12-3-123-12-3-23-12-3-123-12-3-123-21-3-123-12-3-123-12-3";

}
