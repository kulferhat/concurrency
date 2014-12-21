package kul.ferhat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class PerformanceReporter {
	
	private ThreadPoolExecutor executor;
	private LogAdapter logAdapter;
	private LinkedBlockingQueue<Runnable> workQueue;

	public PerformanceReporter(ThreadPoolExecutor executor, LogAdapter logAdapter, LinkedBlockingQueue<Runnable> workQueue){
		this.executor = executor;
		this.logAdapter = logAdapter;
		this.workQueue = workQueue;
	}
	
	public void start() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try{
					String log = String
							.format("[PoolSize/CorePoolSize] [%d/%d] \n"
									+ "Active Thread Count: %d, \n"
									+ "Completed: %d, \n"
									+ "Task: %d, \n"
									+ "isShutdown: %s, \n"
									+ "isTerminated: %s, \n"
									+ "Waiting in queue: %d",
									executor.getPoolSize(),
									executor.getCorePoolSize(),
									executor.getActiveCount(),
									executor.getCompletedTaskCount(),
									executor.getTaskCount(),
									executor.isShutdown(),
									executor.isTerminated(),
									workQueue.size());
					
					logAdapter.log(log);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}, 1000, 1000);
	}

}
