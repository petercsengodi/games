package hu.csega.dyn;

public class OuterProcess {

	public static void main(String[] args) throws Exception {
		int processCode = DynamicFramework.UPGRADE_PROCESS;
		
		while(processCode > DynamicFramework.LAST_UNACCEPTED_CODE) {
			System.out.println("Starting process with code: " + processCode);
			String[] startOptions = {"java", "hu.csega.alpoc.dyn.InnerProcess", String.valueOf(processCode)};
			Process process = new ProcessBuilder(startOptions).start();
			processCode = process.waitFor();
			System.out.println("Returned process code: " + processCode);
		}
	}
}
