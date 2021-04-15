package main;

public abstract class Listener {
	private static boolean active = true;
	public static void setActive(boolean active) {
		Listener.active = active;
	}
	
	public static boolean getActive() {
		return active;
	}
	

}
