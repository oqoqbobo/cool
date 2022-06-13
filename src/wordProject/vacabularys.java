package wordProject;

import java.util.ArrayList;

public class vacabularys {
	//设置全局数组
	private static ArrayList<Vacabulary> list = new ArrayList<Vacabulary>();
	public static ArrayList<Vacabulary> getList() {
		return list;
	}
	public static void clean() {
		list = null;
		list = new ArrayList<Vacabulary>();
	}
	static {
		SQL sql = new SQL();
		sql.search("select * from words;");
	}
}	
