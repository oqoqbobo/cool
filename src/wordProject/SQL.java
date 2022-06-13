package wordProject;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

public class SQL {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306?characterEncoding=utf8";
    static final String F_URL = "jdbc:mysql://localhost:3306/again?characterEncoding=utf8";
    static final String USER = "root";
    static String PASS;
    
	private static Connection conn;
    private static PreparedStatement stmt;
    private static ResultSet rs;
    public static boolean exist = false;
    public static int or;
    
    static {
    	PASS = Write_read.read("src/passP/mypass.txt");
    }
    

    public static void open() {
    	try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(F_URL,USER,PASS);
		}catch (Exception e) {
			exist = false;
			return;
		}
        exist = true;
}
    
    public static void login() {
    	if(!login(PASS)) {
			LoginMySQL lg = new LoginMySQL("数据库登录");
			lg.setLg(lg);
    	}
    }
    
    public static boolean login(String pass) {
    	or=1;
    	try{
    		Write_read.write(pass,"src/passP/mypass.txt");
	        PASS = Write_read.read("src/passP/mypass.txt");
	        Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			or=2;
	        stmt = conn.prepareStatement("use again");
	        stmt.executeQuery();
	       
	        or = 3;
	        
	        stmt = conn.prepareStatement("select * from words");
	        stmt.executeQuery();
	    }catch(Exception e){
	    	if(or == 2) {
	    		createDataBase();
	    		createTable();
	        }
	    	if(or == 1) {
	    		return false;
	    	}
	    	if(or == 3) {
	    		createTable();
	    	}
	    }
        
    	//判断有没有表，做好准备
	//	createTable();
		close();
    	
    	return true;
    }

    private static void createTable() {
    	open();
    	try{
        	stmt = conn.prepareStatement("CREATE TABLE words(word varchar(40) primary key,symbol varchar(40),mean varchar(40))");
        	stmt.executeUpdate();
	    }catch(Exception e){
        	e.printStackTrace();
	    }
    }
   
    private static void createDataBase(){
    	try {
    		stmt = conn.prepareStatement("CREATE DATABASE again CHARSET=utf8mb4");
    		stmt.executeUpdate();
    		stmt = conn.prepareStatement("USE again");
    		stmt.executeQuery();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public static void close() {
    	try {
    		if(conn != null) {
        		conn.close();
        		conn = null;
        	}
    		if(stmt != null) {
    			stmt.close();
    			stmt = null;
    		}
    		if(rs != null){
    			rs.close();
    			rs = null;
    		}
    	}catch(Exception e ){
    		e.printStackTrace();
    	}
    }
    
    public boolean insert(String word,String symbol,String mean) {
    	boolean isTrue = true;
    	String sql = "INSERT INTO words (word,symbol,mean) VALUES (?,?,?);";
    	open();
    	try {
    		stmt = conn.prepareStatement(sql);
    		stmt.setString(1, word);
    		stmt.setString(2, symbol);
    		stmt.setString(3, mean);
    		stmt.executeUpdate();
    	}catch(Exception e) {
    		isTrue = false;
    	}
    	close();
    	return isTrue;
    }
    
    public void delete(String word) {
    	open();
    	String sql = "delete from words where word = ?";
    	try {
    		stmt = conn.prepareStatement(sql);
    		stmt.setString(1, word);
    		stmt.executeUpdate();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	close();
    }
    
    public void update(String word,String symbol,String mean ) {
    	open();
    	String sql = "update words set symbol = ?,mean = ?  where word = ? ";
    	try {
    		stmt = conn.prepareStatement(sql);
    		stmt.setString(1, symbol);
    		stmt.setString(2, mean);
    		stmt.setString(3, word);
    		stmt.executeUpdate();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	close();
    }
   
   
    public boolean search(String sql) {
		open();
    	try {
    		stmt = conn.prepareStatement(sql);
	        rs = stmt.executeQuery();
	        Vacabulary w;
	        
	        vacabularys.clean();
	        //给全局数组赋值
	        while(rs.next()){
	        	String word  = rs.getString("word");
	            String symbol = rs.getString("symbol");
	            String mean = rs.getString("mean");
	            w = new Vacabulary();
	            w.setWord(word);
	            w.setSymbol(symbol);
	            w.setMean(mean);
	            vacabularys.getList().add(w);
	        }
	        close();
	        return true;
    	}catch(SQLException e) {
            close();
    		return false;
    	}
    	
    }
    public static void setVoice(String name,byte[] bytes) {
    	open();
    	try {
    		String sql="INSERT INTO voice VALUES (?,?)";
            stmt=conn.prepareStatement(sql);
            stmt.setString(1,name);
			/*
			 * 得到Blob
			 * 1.我们有的是文件，目标是Blob
			 * 2.先把文件变成byte[]
			 * 3.再使用byte[]创建Blob
			 * */
            Blob blob=new SerialBlob(bytes);
            stmt.setBlob(2, blob);
            stmt.executeUpdate();
    	}catch(Exception e) {

    	}
    	close();
    }
    public static InputStream getVoice(String name) {
    	open();
    	String sql="select * from voice where word = ?";
    	InputStream in = null;
        try {
        	stmt=conn.prepareStatement(sql);
        	stmt.setString(1,name);
            rs=stmt.executeQuery();
            /*
             * 4.获取rs列中名为data的数据
             * */
            if(rs.next()){
    	        Blob blob=rs.getBlob("v");
    	        /*
    	         * 把Blob变为磁盘上的文件
    	         * */
    	        /*
    	         * 1.通过Blob得到输入流对象
    	         * 2.自己创建输出流对象
    	         * 3.把输入流对象写入到输出流中
    	         * */
    	        in = blob.getBinaryStream();
           }
        }catch(Exception e) {

        }
        close();
        return in;
    }
}
