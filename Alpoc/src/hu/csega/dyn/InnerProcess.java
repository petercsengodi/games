package hu.csega.dyn;

public class InnerProcess {

	public static void main(String[] args) throws Exception {
		System.out.println("Hello");
		int nextProcessCode = DynamicFramework.EXIT_CODE;
		
		if(args.length > 0) {
			System.out.println("Argument: " + args[0]);
			int processCode = Integer.parseInt(args[0]);
			if(processCode == DynamicFramework.UPGRADE_PROCESS) {
				System.out.println("Starting upgrade process.");
				upgrade();
				System.out.println("Upgrade process finished.");
				nextProcessCode = 202;
			} else {
				System.out.println("Starting engine.");
				engine();
				System.out.println("Engine finished.");
			}
		} else {
			System.out.println("No argument received.");
		}
		
		System.out.println("Next process ID: " + nextProcessCode);
		System.exit(nextProcessCode);
	}
	
	public static void upgrade() {
	}
	
	public static void engine() {
	}
}
