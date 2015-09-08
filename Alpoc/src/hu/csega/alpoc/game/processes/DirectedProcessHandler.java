package hu.csega.alpoc.game.processes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import hu.csega.alpoc.game.ai.AIProcess;
import hu.csega.alpoc.game.graphics.RenderingProcess;
import hu.csega.alpoc.game.logic.LogicProcess;
import hu.csega.alpoc.game.physics.PhysicsProcess;

public class DirectedProcessHandler implements Runnable {
	
	public DirectedProcessHandler() {
	}
	
	public void initialize() throws Exception {
		this.numberOfProcessDefinitions = processDefinitions.size();
		this.processes = new EngineProcess[numberOfProcessDefinitions];
		this.percentages = new double[numberOfProcessDefinitions];
		
		int counter = 0;
		for(Entry<Class<? extends EngineProcess>, Double> entry : processDefinitions.entrySet()) {
			this.processes[counter] = entry.getKey().newInstance();
			this.percentages[counter] = entry.getValue();
			counter++;
		}
		
		double sum = 0.0;
		for(int i = 0; i < counter; i++) {
			sum += this.percentages[i];
		}
		
		for(int i = 0; i < counter; i++) {
			this.percentages[i] /= sum;
		}
		
		int totalLength = numberOfProcessDefinitions * MEASUREMENT_BLOCKS_PER_PROCESS;
		this.blocks = new ProcessMeasurementBlock[totalLength];
		for(int i = 0; i < totalLength; i++) {
			this.blocks[i] = new ProcessMeasurementBlock();
		}
	}
	
	@Override
	public void run() {
		int processId = pickProcess();
		
		long second = System.currentTimeMillis() / 1000;
		int blockIndex = processId * MEASUREMENT_BLOCKS_PER_PROCESS + (int)(second % 20);
		ProcessMeasurementBlock block = blocks[blockIndex];
		if(block.second == second) {
			block.numberOfRuns++;
		} else {
			block.second = second;
			block.numberOfRuns = 1;
		}
	}

	private int pickProcess() {
		double randomDouble = rnd.nextDouble();
		
		for(int i = 0; i < numberOfProcessDefinitions; i++) {
			double percentage = percentages[i];
			if(percentage > randomDouble) {
				return i;
			} else {
				randomDouble -= percentage;
			}
		}
		
		return numberOfProcessDefinitions - 1;
	}
	

	public void logMeasurement() {
		for(int i = 0; i < numberOfProcessDefinitions; i++) {
			logProcessMeasurement(i);
		}
	}
	
	public void logProcessMeasurement(int process) {
		String processName = processes[process].getClass().getSimpleName();
		System.out.println("-------------------- " + (processName) + " -----------------------------");
		long maxTime = Integer.MIN_VALUE;
		int startIndex = process * MEASUREMENT_BLOCKS_PER_PROCESS;
		for(int i = 0; i < MEASUREMENT_BLOCKS_PER_PROCESS; i++) {
			maxTime = Math.max(maxTime, this.blocks[startIndex].second);
			startIndex++;
		}
		
		if(maxTime == 0) {
			maxTime = 20;
		}
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		startIndex = process * MEASUREMENT_BLOCKS_PER_PROCESS;
		for(int i = 0; i < MEASUREMENT_BLOCKS_PER_PROCESS; i++) {
			int blockIndex = startIndex + (int)(maxTime % MEASUREMENT_BLOCKS_PER_PROCESS);
			ProcessMeasurementBlock block = blocks[blockIndex];
			String date = format.format(new Date(block.second * 1000));
			System.out.println(date + ": " + block.numberOfRuns);
			maxTime--;
		}
	}

	private Random rnd = new Random(System.currentTimeMillis());
	
	private EngineProcess[] processes;
	private double[] percentages;
	private int numberOfProcessDefinitions;
	private ProcessMeasurementBlock[] blocks; 
	
	private static final int MEASUREMENT_BLOCKS_PER_PROCESS = 20;
	
	/** Process Class -> percentage */
	private static Map<Class<? extends EngineProcess>, Double> processDefinitions;
	
	static {
		processDefinitions = new Hashtable<>();
		processDefinitions.put(PhysicsProcess.class, 0.4);
		processDefinitions.put(LogicProcess.class, 0.3);
		processDefinitions.put(RenderingProcess.class, 0.2);
		processDefinitions.put(AIProcess.class, 0.1);
	}

}
