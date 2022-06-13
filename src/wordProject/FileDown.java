package wordProject;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import jmp123.PlayBack;
import jmp123.output.Audio;

public class FileDown {
	private static PlayBack player;
	static {
		player = new PlayBack(new Audio());
	}
	
	public static void addFromSQL(String name,String url) {
		InputStream bis = SQL.getVoice(name);
		addFile(url+name+".mp3",bis);
	}
	
	/**
	 * 将InputStream的音频转化成mp3并加入到指定文件中，url决定 
	 * @param url
	 * @param bis
	 */
	private static void addFile(String url,InputStream bis) {
		OutputStream out=null;
		try {
			File file = new File(url);
			out = new FileOutputStream(file);
			int bytesWritten = 0;
			
			byte[] bytes = new byte[1024000000];
			int byteCount = 0;
			while ((byteCount = bis.read(bytes)) != -1){
				out.write(bytes, bytesWritten, byteCount);
				bytesWritten += byteCount;
			}
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**  
	 * @从制定URL下载文件并保存到指定目录
	 * @param filePath 文件将要保存的目录  
	 * @param method 请求方法，包括POST和GET  
	 * @param url 请求的路径  
	 * @return  
	 */  
	public static void saveUrlAsaddFile(String name,String url){
		String filePath = "src/voices/";
	     //创建不同的文件夹目录  
	     File file=new File(filePath);  
	     //判断文件夹是否存在  
	     if (!file.exists()){  
	        //如果文件夹不存在，则创建新的的文件夹  
	         file.mkdirs();  
	     }  
	     FileOutputStream fileOut = null;  
	     HttpURLConnection conn = null;  
	     InputStream inputStream = null;  
	     InputStream bis = null;
	     try  
	     {  
	         // 建立链接  
	         URL httpUrl=new URL(url+name);  
	         conn=(HttpURLConnection) httpUrl.openConnection();  
	         //以Post方式提交表单，默认get方式  
	         conn.setRequestMethod("GET");  
	         conn.setDoInput(true);    
	         conn.setDoOutput(true);  
	         // post方式不能使用缓存   
	         conn.setUseCaches(false);  
	         //连接指定的资源   
	         conn.connect(); 
	         String thePath = filePath+name+".mp3";
	       //获取网络输入流  
	          bis= conn.getInputStream();
	          addFile(thePath,bis);
	         conn.disconnect();  
	         bis.close();
	    } catch (Exception e){  
	         System.out.println("抛出异常！！");  
	    }
	 }
	/**
	 * 将InputStream的音频保存到数据库表中，name是保存的音频对应的单词
	 * @param name
	 * @param bis
	 */
	public static void addSQL(String name,InputStream bis) {
		try {
			byte[] bytes = inputStream2byte(bis);
			SQL.open();
	        SQL.setVoice(name, bytes);
	        SQL.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**  
	 * @从制定URL下载文件并保存到指定数据库（again）
	 * @param filePath 文件将要保存的目录  
	 * @param method 请求方法，包括POST和GET  
	 * @param url 请求的路径  
	 * @return  
	 */ 
	public static void saveUrlAsaddSQL(String name,String url){
	     FileOutputStream fileOut = null;  
	     HttpURLConnection conn = null;  
	     InputStream inputStream = null;  
	     InputStream bis = null;
	     try  
	     {  
	         // 建立链接  
	         URL httpUrl=new URL(url+name);  
	         conn=(HttpURLConnection) httpUrl.openConnection();  
	         //以Post方式提交表单，默认get方式  
	         conn.setRequestMethod("GET");  
	         conn.setDoInput(true);    
	         conn.setDoOutput(true);  
	         // post方式不能使用缓存   
	         conn.setUseCaches(false);  
	         //连接指定的资源   
	         conn.connect(); 
	       //获取网络输入流  
	          bis= conn.getInputStream();
	          addSQL(name,bis);
	         conn.disconnect();  
	         bis.close();
	    } catch (Exception e){  
	         e.printStackTrace();  
	         System.out.println("抛出异常！！");  
	    }
	 }
	/**
	 * 将inputStream转化为byte[]
	 * @param inputStream
	 * @return byte[]
	 * @throws IOException
	 */
	private static byte[] inputStream2byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
	public static boolean judge(String name) {
		File file = new File("src/voices/"+name+".mp3");
		return  file.exists();
	}
	public static void stop() {
		player.stop();
	}
	
	public static void speak(String name) {
		try {
			if(judge(name)) {
				player.open("src/voices/"+name+".mp3", "");
				
				
			}else {
				player.open("src/passP/error.mp3", "");
			}
			player.start(true);
		} catch (IOException e) {
			
		}
	}
	public static void speak() {
		try {
			player.open("src/passP/Bell.mp3", "");
			player.start(true);
		} catch (IOException e) {
			
		}
	}
	
	private static void write(String content) {
    	BufferedWriter bw = null;
 		try {
            // 根据文件路径创建缓冲输出流

        	 bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/content/other.txt")));
           // 将内容写入文件中
             bw.write(content);
         } catch (Exception e) {
        	e.printStackTrace();
         } finally {
        	 // 关闭流
        	 try {
        		 bw.close();
        	 } catch (IOException e) {
        		 bw = null;
        	 }
         }
    }
	
	
	public static void saveUrlString(String name,String url){
	     HttpURLConnection conn = null;  
	     InputStream bis = null;
	     try  
	     {  
	         // 建立链接  
	         URL httpUrl=new URL(url+name);  
	         conn=(HttpURLConnection) httpUrl.openConnection();  
	         //以Post方式提交表单，默认get方式  
	         conn.setRequestMethod("GET");  
	         conn.setDoInput(true);    
	         conn.setDoOutput(true);  
	         // post方式不能使用缓存   
	         conn.setUseCaches(false);  
	         //连接指定的资源   
	         conn.connect(); 
	       //获取网络输入流  
	          bis= conn.getInputStream();
	          Scanner scanner = new Scanner(bis, "UTF-8");
	          String text = scanner.useDelimiter("\\A").next();
	          write(text);
	         conn.disconnect();  
	         bis.close();
	    } catch (Exception e){  
	         e.printStackTrace();  
	         System.out.println("抛出异常！！");  
	    }
	 }
	public static synchronized void genUniqueKey(String name) {
        for(int i=0;i<10;i++) {
        	System.out.println(name+" "+i);
        }
	}
}
