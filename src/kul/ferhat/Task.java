package kul.ferhat;

public class Task implements Runnable{

	private Configuration configuration;

	public Task (Configuration configuration){
		this.configuration = configuration;
	}
	
	@Override
	public void run() {
		try {
			//System.out.println("Executing by " + Thread.currentThread().getName() );
			Thread.sleep(configuration.getTaskDurationInMsec());
			System.out.println("Executed by " + Thread.currentThread().getName() );
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}