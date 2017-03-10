package wubo.servlets;

import java.io.File;

import org.apache.log4j.Logger;
import org.mozilla.javascript.tools.shell.Environment;

public class test {
	
	private static Logger logger = Logger.getLogger(test.class);  

    

	public static void main(String[] args) {
		String path = Environment.class.getResource("").getPath();  
		String webAppPath = path.substring(0, path.toUpperCase().lastIndexOf("WEB-INF/")).replaceAll("%20", " ");  
		System.setProperty("webapp",webAppPath);  
		File f = new File(webAppPath+"/logs/log.log");
		System.out.println(f);
	}
	
	
}