package kul.ferhat;

public class ConcurrencyDemo {
	public static void main(String[] args) {
		GUIActionListener actionListener = new GUIActionListener();
		GUI gui = new GUI(actionListener);
		gui.setup();
		
	}
}
