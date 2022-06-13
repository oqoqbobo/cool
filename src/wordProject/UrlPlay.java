package wordProject;

public class UrlPlay implements Runnable{
	private static String name;
	
	public static void setName(String other) {
		name = other;
	}
	public synchronized void run(){
		FileDown.saveUrlAsaddFile(name,"http://dict.youdao.com/dictvoice?type=1&audio=");
	}
}