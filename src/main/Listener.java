package main;

import java.util.LinkedList;
/**
 * Listener Abstract Class
 * Provides static members and functions to facilitate program action
 * 
*/
public abstract class Listener {
	private static boolean active = true;
	private static LinkedList<Edit> defaultlist = null;
	private static LinkedList<Edit> activelist = null;
	
	
	public static void setDefaultList(LinkedList<Edit> e) {
		defaultlist = e;
	}
	
	
	public static LinkedList<Edit> getDefaultList(){
		return defaultlist;
	}
	
	
	public static void setActiveList(LinkedList<Edit> e) {
		activelist = e;
	}
	
	public static LinkedList<Edit> getActiveList(){
		return activelist;
	}
	
	public static void setActive(boolean active) {
		Listener.active = active;
	}
	
	public static boolean getActive() {
		return active;
	}
	

}
