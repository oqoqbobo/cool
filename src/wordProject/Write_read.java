package wordProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Write_read {
	public static String read(String url){
    	BufferedReader in = null;
    	String result = "";
    	try {
    		//"src/passP/mypass.txt"
            in = new BufferedReader(new InputStreamReader(new FileInputStream(url)));
            String str;
            if((str = in.readLine()) != null) {
            	result = str;
            }
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
			try {
				in.close();
			} catch (IOException e) {
				in = null;
			}
        }
    	return result;
    }
    public static void write(String content,String url) {
    	BufferedWriter bw = null;
 		try {
            // 根据文件路径创建缓冲输出流   "src/passP/mypass.txt"
        	 bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(url)));
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
}
