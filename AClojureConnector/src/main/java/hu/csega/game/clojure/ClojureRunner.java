package hu.csega.game.clojure;

public class ClojureRunner {

	public Object start(ClojureWrapper wrapper) {
		System.out.println("Wrapper class: " + wrapper.getClass().getName());
		Object ret = wrapper.run(this);
		System.out.println("Returned by callback: " + ret);
		if(ret != null) {
			System.out.println("Class: " + ret.getClass().getName());
		}

		return ret;
	}

}
