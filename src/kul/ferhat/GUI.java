package kul.ferhat;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame{
	
	private final GUIActionListener actionListener;
	
	private final JTextField corePoolSize = new JTextField("1");
	private final JTextField maxPoolSize = new JTextField("10");
	private final JTextField keepAliveTime = new JTextField("20");
	private final JTextField workQueueSize = new JTextField("10"); //If waiting tasks reaches to limit, a new thread is created.
	private final JTextField taskCount = new JTextField("1000000");
	private final JTextField tps = new JTextField("50");
	private final JTextField taskDurationInMsec = new JTextField("200");
	
	private final JTextArea logArea = new JTextArea(5, 50);
	
	private JButton startButton;
	private JButton stopButton;
	private Configuration conf = new Configuration();

	private static JFrame frame;
	
	private LogAdapter logAdapter;

	public GUI(GUIActionListener actionListener){
		this.actionListener = actionListener;
		frame = this;
	}
	
	public void setup(){

		setTitle("Java Concurrency Demonstration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(prepareConfPanel(), BorderLayout.CENTER);
		getContentPane().add(prepareLogPanel(), BorderLayout.EAST);
		
		this.logAdapter = new LogAdapter(logArea);
		
		//4. Size the frame.
		pack();

		//5. Show it.
		setVisible(true);
	}
	
	private JPanel prepareLogPanel() {
		JPanel logPanel = new JPanel(new BorderLayout());
		
		logPanel.add(logArea, BorderLayout.CENTER);
		
		JPanel metricsPanel = new JPanel();
		metricsPanel.setLayout(new GridLayout(2, 2));
		
		metricsPanel.add(new JLabel("X"));
		metricsPanel.add(new JLabel("X"));
		
		metricsPanel.add(new JLabel("X"));
		metricsPanel.add(new JLabel("X"));
		
		logPanel.add(metricsPanel, BorderLayout.SOUTH);
		
		return logPanel;
	}

	private JPanel prepareConfPanel(){
		JPanel confPanel = new JPanel();
		confPanel.setLayout(new GridLayout(9, 3));

		
		
		JButton readMe = new JButton("<html><a>Read Me</a></html>");
		readMe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String help = "<html>When tasks are submitted,<br>    "
						+ "If the thread pool has not reached the core size, it creates new threads. <br>"
						+ "If the core size has been reached and there is no idle threads, it queues tasks.<br>"
						+ "If the core size has been reached, there is no idle threads, and the queue becomes full, it creates new threads (until it reaches the max size).<br>"  
						+ "If the max size has been reached, there is no idle threads, and the queue becomes full, the rejection policy kicks in.</html>";
				JOptionPane.showMessageDialog(frame, help);
			}
		});
		
		confPanel.add(readMe);
		confPanel.add(new JLabel());
		confPanel.add(new JLabel());
		
		
		confPanel.add(new JLabel("Core Pool Size"));
		confPanel.add(corePoolSize);
		confPanel.add(new JLabel());
		
		confPanel.add(new JLabel("Max Pool Size"));
		confPanel.add(maxPoolSize);
		final JButton setMaxPoolSize = new JButton("Set");
		setMaxPoolSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readConfiguration();
				actionListener.reloadMaxPoolSize();
			}
		});
		confPanel.add(setMaxPoolSize);
		
		confPanel.add(new JLabel("Keep Alive Time [Sec]"));
		confPanel.add(keepAliveTime);
		final JButton setKeepAliveTime = new JButton("Set");
		setKeepAliveTime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readConfiguration();
				actionListener.reloadSetKeepAliveTime();
			}
		});
		confPanel.add(setKeepAliveTime);
		
		confPanel.add(new JLabel("Work Queue Size"));
		confPanel.add(workQueueSize);
		confPanel.add(new JLabel());
		
		confPanel.add(new JLabel("TaskCount"));
		confPanel.add(taskCount);
		confPanel.add(new JLabel());
		
		confPanel.add(new JLabel("TPS"));
		confPanel.add(tps);
		final JButton setTps = new JButton("Set");
		setTps.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readConfiguration();
				actionListener.reloadTps();
			}
		});
		confPanel.add(setTps);
		
		confPanel.add(new JLabel("Task Duration [Msec]"));
		confPanel.add(taskDurationInMsec);
		final JButton setTaskDurationInMsec = new JButton("Set");
		setTaskDurationInMsec.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				readConfiguration();
			}
		});
		confPanel.add(setTaskDurationInMsec);
		
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				readConfiguration();
				actionListener.startClicked(conf, logAdapter);
			}
		});
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				actionListener.stopClicked();
			}
		});
		
		confPanel.add(startButton);
		confPanel.add(stopButton);
		confPanel.add(new JLabel());
		
		return confPanel;
	}
	
	private Configuration readConfiguration(){
		conf.setCorePoolSize(Integer.parseInt(corePoolSize.getText()));
		conf.setKeepAliveTime(Integer.parseInt(keepAliveTime.getText()));
		conf.setMaxPoolSize(Integer.parseInt(maxPoolSize.getText()));
		conf.setTaskCount(Long.parseLong(taskCount.getText()));
		conf.setTaskDurationInMsec(Integer.parseInt(taskDurationInMsec.getText()));
		conf.setTps(Integer.parseInt(tps.getText()));
		conf.setWorkQueueSize(Integer.parseInt(workQueueSize.getText()));
		
		return conf;
	}
	
	public static void main(String[] args) {
		new GUI(null).setup();
	}
}
