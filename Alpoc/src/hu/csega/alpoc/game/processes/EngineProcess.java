package hu.csega.alpoc.game.processes;

public class EngineProcess implements Runnable{

	@Override
	public void run() {
		System.out.println("Thread: " + this.getClass().getSimpleName());
		
		try {
			Thread.sleep(10);
		} catch(InterruptedException ex) {
		}
	}

}
