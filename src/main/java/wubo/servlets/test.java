package wubo.servlets;

import java.io.File;

import net.sf.json.JSONObject;

public class test {
	
	private static final String MySQL_DRIVER_NAME="com.mysql.jdbc.Driver";
	
	private static final String ORACLE_DRIVER_NAME="oracle.jdbc.driver.OracleDriver";
	
	private static final String SQLSERVER_2000_DRIVER_NAME="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	
	private static final String SQLSERVER_2005_DRIVER_NAME="com.microsoft.sqlserver.jdbc.SQLServerDriver";
       
    

	public static void main(String[] args) {
		File f = new File("D:\\SelfCode\\birtdemo\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\birt\\WEB-INF\\lib");
		if(f.isDirectory()){
			File files[] = f.listFiles();
			for(File f1:files){
				System.out.println("\""+f1.getName()+"\",");
			}
		}
	}
	
	private static void validate(JSONObject responsejson){
		
		//MySql驱动
		try{
			Class.forName(MySQL_DRIVER_NAME);
			responsejson.put("mysql",true);
		}catch(Exception e){
			responsejson.put("mysql",false);
		}
		
		//Oracle驱动
		try{
			Class.forName(ORACLE_DRIVER_NAME);
			responsejson.put("oracle",true);
		}catch(Exception e){
			responsejson.put("oracle",false);
		}
		
		//SqlServer2000驱动
		try{
			Class.forName(SQLSERVER_2000_DRIVER_NAME);
			responsejson.put("sqlserver2000",true);
		}catch(Exception e){
			responsejson.put("sqlserver2000",false);
		}
		
		//SqlServer2000驱动
		try{
			Class.forName(SQLSERVER_2005_DRIVER_NAME);
			responsejson.put("sqlserver2005",true);
		}catch(Exception e){
			responsejson.put("sqlserver2005",false);
		}
		
	}


}
