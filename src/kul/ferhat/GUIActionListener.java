package kul.ferhat;

public class GUIActionListener {
	private Machine machine = null;
	public GUIActionListener(){
		
	}

	public void startClicked(Configuration conf, LogAdapter logAdapter) {
		machine = new Machine(conf, logAdapter);
		new Thread(){
			public void run(){
				machine.start();
			}
		}.start();
	}

	public void stopClicked() {
		machine.setStopFlag();
	}

	public void reloadTps() {
		if (machine != null){
			machine.reloadTps();
		}
	}

	public void reloadMaxPoolSize() {
		machine.reloadMaxPoolSize();
	}

	public void reloadSetKeepAliveTime() {
		machine.reloadSetKeepAliveTime();
	}
}
