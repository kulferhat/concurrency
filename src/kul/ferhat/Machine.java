package kul.ferhat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class Machine {
	private LinkedBlockingQueue<Runnable> workQueue;
	private ThreadPoolExecutor executor;
	private RateLimiter rateLimiter;
	private LogAdapter logAdapter;

	private Configuration configuration;
	private boolean stopFlagSet = false;
	
	public Machine(Configuration configuration, LogAdapter logAdapter){
		this.configuration = configuration;
		
		this.logAdapter = logAdapter;
		
		setup();

	}
	private void setup() {
		this.workQueue = new LinkedBlockingQueue<Runnable>(configuration.getWorkQueueSize());
		
		ThreadFactory factory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ferhat-%d").build();
		
		RejectedExecutionHandler rejectedHander = new RejectedExecutionHandler() {

			@Override
			public void rejectedExecution(Runnable arg0, ThreadPoolExecutor arg1) {
				System.out.println(arg0 + " is rejected");
			}
		};

		executor = new ThreadPoolExecutor(configuration.getCorePoolSize(), 
				configuration.getMaxPoolSize(),
				configuration.getKeepAliveTime(),
				TimeUnit.SECONDS, 
				workQueue, 
				factory,
				rejectedHander);
		
		//This setting is important and initial thread count should be selected carefully.
		executor.allowCoreThreadTimeOut(true);
		
		prepareReporter();
	}
	
	private void prepareReporter() {
		PerformanceReporter reporter = new PerformanceReporter(executor, logAdapter, workQueue);
		reporter.start();
	}
	
	public void start(){
		int tps = configuration.getTps();
		rateLimiter = RateLimiter.create(tps);

		long taskCount = configuration.getTaskCount();
		
		for (int i = 0; i < taskCount && !stopFlagSet; i++){
			rateLimiter.acquire();
			executor.submit(new Task(configuration));
		}
	}
	
	public void setStopFlag(){
		this.stopFlagSet  = true;
	}
	public void reloadTps() {
		if (rateLimiter != null){
			rateLimiter.setRate(configuration.getTps());
		}
	}
	
	public void reloadMaxPoolSize() {
		executor.setMaximumPoolSize(configuration.getMaxPoolSize());
	}
	public void reloadSetKeepAliveTime() {
		executor.setKeepAliveTime(configuration.getKeepAliveTime(), TimeUnit.SECONDS);
	}
}
