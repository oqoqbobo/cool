package wordProject;

public class PlayMusic {
	public static MusicPlay mP;
	public static UrlPlay uP;
	public static Thread threadM;
	public static Thread threadU;
	static {
		mP = new MusicPlay();
		uP = new UrlPlay();
		setmP();
		setuP();
	}
	public static void setmP() {
		threadM = new Thread(mP);
	}
	public static void setuP() {
		threadU = new Thread(uP);
	}
	public static void speak(String name){
		if(threadM.isAlive()) {
			threadM.stop();
		}
		mP.setName(name);
		try {
			threadM.start();
		} catch (Exception e1) {
		//	threadM.stop();
			setmP();
			threadM.start();
		}
		System.out.println(threadM);
	}
	public static void saveUrlAsaddFile(String name){
		if(threadU.isAlive()) {
			threadU.stop();
		}
		uP.setName(name);
		try {
			threadU.start();
		} catch (Exception e1) {
			threadU.stop();
			setuP();
			threadU.start();
		}
	}
}
