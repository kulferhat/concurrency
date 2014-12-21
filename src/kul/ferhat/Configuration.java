package kul.ferhat;

public class Configuration {
	private int corePoolSize;
	private int maxPoolSize;
	private int keepAliveTime;
	private int workQueueSize; 
	private long taskCount;
	private int tps;
	private int taskDurationInMsec;
	public int getCorePoolSize() {
		return corePoolSize;
	}
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public int getMaxPoolSize() {
		return maxPoolSize;
	}
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	public int getKeepAliveTime() {
		return keepAliveTime;
	}
	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}
	public int getWorkQueueSize() {
		return workQueueSize;
	}
	public void setWorkQueueSize(int workQueueSize) {
		this.workQueueSize = workQueueSize;
	}
	public long getTaskCount() {
		return taskCount;
	}
	public void setTaskCount(long taskCount) {
		this.taskCount = taskCount;
	}
	public int getTps() {
		return tps;
	}
	public void setTps(int tps) {
		this.tps = tps;
	}
	public int getTaskDurationInMsec() {
		return taskDurationInMsec;
	}
	public void setTaskDurationInMsec(int taskDurationInMsec) {
		this.taskDurationInMsec = taskDurationInMsec;
	}
}
