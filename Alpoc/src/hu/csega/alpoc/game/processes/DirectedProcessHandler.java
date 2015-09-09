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
		this.takenTimePercentages =  new double[numberOfProcessDefinitions];
		
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

		long stopperStart = System.currentTimeMillis();
		processes[processId].run();
		long stopperEnd = System.currentTimeMillis();
		
		long secondStart = stopperStart / 1000;
		long secondEnd = stopperEnd / 1000;
		long duration = stopperEnd - stopperStart;
		
		// 1. we add the number of runs
		ProcessMeasurementBlock block = pickBlock(processId, secondEnd);
		if(block.second == secondEnd) {
			// we are already using this block, we just increase the numberOfRuns counter
			block.numberOfRuns++;
		} else {
			// this block will be re-used for a new time second 
			block.second = secondEnd;
			
			// re-init block
			block.numberOfRuns = 1; // this is the first run
			block.millisSpent = 0; // no duration is registered, yet
		}
		
		// 2. we register the duration
		if(secondStart == secondEnd) {
			// the entire run happened in this time slot, so we can simply add up the numbers
			block.millisSpent += duration;
		} else {
			// that part of the duration that happened in this second can be added up
			long millisSpentInCurrentSecond = stopperEnd % 1000;
			block.millisSpent += millisSpentInCurrentSecond;
			
			// we need to register the rest of the time in the underlying blocks.
			// although we hope that none of the durations will be longer, than one second, we prepare for the worst
			duration -= millisSpentInCurrentSecond;
			for(long i = 1; i < MEASUREMENT_BLOCKS_PER_PROCESS; i++) {
				block = pickBlock(processId, secondEnd - i);
				if(duration >= 1000) {
					// if the duration left is longer than one second, we slowly divide the time for each time slots
					block.millisSpent += 1000;
					duration -= 1000;
				} else {
					// the duration left is less than a second, we register it, and break the loop
					block.millisSpent += duration;
					break;
				}
				
				// if the duration is longer than 20 seconds / blocks, we don't care anymore, there are no
				// more examined slots.
			}
		}
		
		// 3. just in case: at the end of this algorithm the duration left shall be zero
		duration = 0;
	}

	private int pickBlockIndex(int processId, long second) {
		return processId * MEASUREMENT_BLOCKS_PER_PROCESS + (int)(second % MEASUREMENT_BLOCKS_PER_PROCESS);
	}
	
	private ProcessMeasurementBlock pickBlock(int processId, long second) {
		return blocks[pickBlockIndex(processId, second)];
	}

	private int pickProcess() {
		// percentages are calculated without measuring "wasted" time (like picking process, etc.)
		
		for(int p = 0; p < numberOfProcessDefinitions; p++) {
			takenTimePercentages[p] = 0.0;
		}
		
		// we check let's say last three seconds
		long currentSecond = System.currentTimeMillis() / 1000;
		double sum = 0.0;
		for(int p = 0; p < numberOfProcessDefinitions; p++) {
			for(int i = 0; i < 3; i++) {
				
				ProcessMeasurementBlock block = pickBlock(p, currentSecond - i);
				double v = block.millisSpent;
				sum += v;
				takenTimePercentages[p] += v;
			}
		}

		if(sum > 0) {
			// if there is data, we pick the most starving process
			
			for(int p = 0; p < numberOfProcessDefinitions; p++) {
				takenTimePercentages[p] /= sum;
			}
			
			// quick and dirty solution: we substract the percentage they should reach.
			// the one with the most negative number wins
			for(int p = 0; p < numberOfProcessDefinitions; p++) {
				takenTimePercentages[p] -= percentages[p];
			}
			
			double min = 0.0;
			int minIndex = -1;
			for(int p = 0; p < numberOfProcessDefinitions; p++) {
				double taken = takenTimePercentages[p];
				if(taken < 0.0 && taken < min) {
					min = taken;
					minIndex = p;
				}
			}
			
			if(minIndex > -1) {
				return minIndex;
			}
		}
		
		// if no data yet, or the selection failed, we just pick a random process
		return rnd.nextInt(numberOfProcessDefinitions);
	}
	
	// may be slow, don't care
	public void logMeasurement() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder log = new StringBuilder();
		
		for(int i = 0; i < numberOfProcessDefinitions; i++) {
			logProcessMeasurement(i, log, format);
		}
		
		logWasted(log, format);
		System.out.println(log);
	}
	
	// may be slow, don't care
	public void logProcessMeasurement(int process, StringBuilder log, DateFormat format) {
		String processName = processes[process].getClass().getSimpleName();
		log.append("-------------------- ").append(processName).append(" -----------------------------\n");
		
		long maxTime = Integer.MIN_VALUE;
		int startIndex = process * MEASUREMENT_BLOCKS_PER_PROCESS;
		for(int i = 0; i < MEASUREMENT_BLOCKS_PER_PROCESS; i++) {
			maxTime = Math.max(maxTime, this.blocks[startIndex].second);
			startIndex++;
		}
		
		if(maxTime == 0) {
			maxTime = 20;
		}
		
		for(int i = 0; i < MEASUREMENT_BLOCKS_PER_PROCESS; i++) {
			ProcessMeasurementBlock block = pickBlock(process, maxTime);
			String date = format.format(new Date(block.second * 1000));
			log.append(date).append(" : ").append(block.numberOfRuns).append(" runs / ").append(block.millisSpent).append(" ms\n");
			maxTime--;
		}
	}

	// may be slow, don't care
	public void logWasted(StringBuilder log, DateFormat format) {
		log.append("-------------------- WASTED -----------------------------\n");
		long maxTime = Integer.MIN_VALUE;
		for(int i = 0; i < blocks.length; i++) {
			maxTime = Math.max(maxTime, this.blocks[i].second);
		}
		
		if(maxTime == 0) {
			maxTime = 20;
		}
		
		for(int i = 0; i < MEASUREMENT_BLOCKS_PER_PROCESS; i++) {
			long sum = 0;
			for(int p = 0; p < numberOfProcessDefinitions; p++) {
				ProcessMeasurementBlock block = pickBlock(p, maxTime);
				sum += block.millisSpent;
			}
			
			String date = format.format(new Date(maxTime * 1000));
			log.append(date).append(" : ").append(1000 - sum).append('\n');
			maxTime--;
		}
	}

	private EngineProcess[] processes;
	private double[] percentages;
	private int numberOfProcessDefinitions;
	private ProcessMeasurementBlock[] blocks; 
	private double[] takenTimePercentages;
	
	private static final int MEASUREMENT_BLOCKS_PER_PROCESS = 20;
	private static final Random rnd = new Random(System.currentTimeMillis());
	
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
