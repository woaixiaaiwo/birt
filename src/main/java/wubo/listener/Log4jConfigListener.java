package wubo.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.mozilla.javascript.tools.shell.Environment;

public class Log4jConfigListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		String path = Environment.class.getResource("").getPath();  
		String webAppPath = path.substring(0, path.toUpperCase().lastIndexOf("WEB-INF/")).replaceAll("%20", " ");  
		webAppPath = webAppPath.substring(webAppPath.indexOf("/"));
		webAppPath = webAppPath+"logs"+File.separator;
		System.out.println("The Log File's folder is: "+webAppPath);
		System.setProperty("webapp",webAppPath);
	}

}