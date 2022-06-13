package wordProject;

public class MusicPlay implements Runnable{
	private static String name;
	
	public static void setName(String other) {
		name = other;
	}
	public synchronized void run(){
		FileDown.speak(name);
		
	}
}
