package wubo.servlets;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.util.Finalizable;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class ValidateDrivers
 */
public class ValidateDrivers extends HttpServlet {
	//private final Logger log = LoggerFactory.getLogger(ValidateDrivers.class);

	private static Logger log = Logger.getLogger(ValidateDrivers.class); 
	private static final long serialVersionUID = 1L;
	
	private static final String [] FILENAMES={
			"axis-ant.jar",
			"axis.jar",
			"cas-client-core-3.2.1.jar",
			"com.ibm.icu_50.1.1.v201304230130.jar",
			"com.lowagie.text_2.1.7.v201004222200.jar",
			"commons-beanutils-1.7.0.jar",
			"commons-cli-1.0.jar",
			"commons-codec-1.9.jar",
			"commons-collections-3.1.jar",
			"commons-discovery-0.2.jar",
			"commons-fileupload-1.3.2.jar",
			"commons-io-2.2.jar",
			"commons-lang-2.5.jar",
			"commons-logging-1.2.jar",
			"commons-logging.jar",
			"derby.jar",
			"ezmorph-1.0.3.jar",
			"flute.jar",
			"httpclient-4.5.2.jar",
			"httpcore-4.4.4.jar",
			"javax.wsdl_1.5.1.v201012040544.jar",
			"javax.xml.stream_1.0.1.v201004272200.jar",
			"jaxrpc.jar",
			"js.jar",
			"json-lib-2.1-jdk15.jar",
			"org.apache.batik.bridge_1.6.0.v201011041432.jar",
			"org.apache.batik.css_1.6.0.v201011041432.jar",
			"org.apache.batik.dom.svg_1.6.0.v201011041432.jar",
			"org.apache.batik.dom_1.6.0.v201011041432.jar",
			"org.apache.batik.ext.awt_1.6.0.v201011041432.jar",
			"org.apache.batik.parser_1.6.0.v201011041432.jar",
			"org.apache.batik.pdf_1.6.0.v201105071520.jar",
			"org.apache.batik.svggen_1.6.0.v201011041432.jar",
			"org.apache.batik.transcoder_1.6.0.v201011041432.jar",
			"org.apache.batik.util.gui_1.6.0.v201011041432.jar",
			"org.apache.batik.util_1.6.0.v201011041432.jar",
			"org.apache.batik.xml_1.6.0.v201011041432.jar",
			"org.apache.commons.codec_1.3.0.v201101211617.jar",
			"org.apache.commons.logging_1.0.4.v201101211617.jar",
			"org.apache.commons.logging_1.1.1.v201101211721.jar",
			"org.apache.poi_3.9.0.v201303080712.jar",
			"org.apache.xerces_2.9.0.v201101211617.jar",
			"org.apache.xml.resolver_1.2.0.v201005080400.jar",
			"org.apache.xml.serializer_2.7.1.v201005080400.jar",
			"org.eclipse.birt.runtime_4.3.2.v20140225-1404.jar",
			"org.eclipse.core.contenttype_3.4.200.v20130326-1255.jar",
			"org.eclipse.core.expressions_3.4.501.v20131118-1915.jar",
			"org.eclipse.core.filesystem_1.4.0.v20130514-1240.jar",
			"org.eclipse.core.jobs_3.5.300.v20130429-1813.jar",
			"org.eclipse.core.resources_3.8.101.v20130717-0806.jar",
			"org.eclipse.core.runtime_3.9.100.v20131218-1515.jar",
			"org.eclipse.datatools.connectivity.apache.derby.dbdefinition_1.0.2.v201107221459.jar",
			"org.eclipse.datatools.connectivity.apache.derby_1.0.103.v201212070447.jar",
			"org.eclipse.datatools.connectivity.console.profile_1.0.10.v201109250955.jar",
			"org.eclipse.datatools.connectivity.db.generic_1.0.1.v201107221459.jar",
			"org.eclipse.datatools.connectivity.dbdefinition.genericJDBC_1.0.2.v201310181001.jar",
			"org.eclipse.datatools.connectivity.oda.consumer_3.2.6.v201305170644.jar",
			"org.eclipse.datatools.connectivity.oda.design_3.3.6.v201212070447.jar",
			"org.eclipse.datatools.connectivity.oda.flatfile_3.1.7.v201311081026.jar",
			"org.eclipse.datatools.connectivity.oda.profile_3.2.9.v201307270622.jar",
			"org.eclipse.datatools.connectivity.oda_3.4.2.v201311051159.jar",
			"org.eclipse.datatools.connectivity.sqm.core_1.2.8.v201401230755.jar",
			"org.eclipse.datatools.connectivity_1.2.11.v201401230755.jar",
			"org.eclipse.datatools.enablement.hsqldb.dbdefinition_1.0.0.v201107221502.jar",
			"org.eclipse.datatools.enablement.hsqldb_1.0.0.v201107221502.jar",
			"org.eclipse.datatools.enablement.ibm.db2.luw.dbdefinition_1.0.6.v201401290336.jar",
			"org.eclipse.datatools.enablement.ibm.db2.luw_1.0.3.v201401170830.jar",
			"org.eclipse.datatools.enablement.ibm.informix.dbdefinition_1.0.4.v201107221502.jar",
			"org.eclipse.datatools.enablement.ibm.informix_1.0.1.v201107221502.jar",
			"org.eclipse.datatools.enablement.msft.sqlserver.dbdefinition_1.0.1.v201201240505.jar",
			"org.eclipse.datatools.enablement.msft.sqlserver_1.0.3.v201308161009.jar",
			"org.eclipse.datatools.enablement.mysql.dbdefinition_1.0.4.v201109022331.jar",
			"org.eclipse.datatools.enablement.mysql_1.0.4.v201212120617.jar",
			"org.eclipse.datatools.enablement.oda.ws_1.2.6.v201307051812.jar",
			"org.eclipse.datatools.enablement.oda.xml_1.2.5.v201305031101.jar",
			"org.eclipse.datatools.enablement.oracle.dbdefinition_1.0.103.v201206010214.jar",
			"org.eclipse.datatools.enablement.oracle_1.0.0.v201107221506.jar",
			"org.eclipse.datatools.enablement.postgresql.dbdefinition_1.0.2.v201110070445.jar",
			"org.eclipse.datatools.enablement.postgresql_1.1.1.v201205252207.jar",
			"org.eclipse.datatools.modelbase.dbdefinition_1.0.2.v201107221519.jar",
			"org.eclipse.datatools.modelbase.derby_1.0.0.v201107221519.jar",
			"org.eclipse.datatools.modelbase.sql.query_1.1.4.v201212120619.jar",
			"org.eclipse.datatools.modelbase.sql_1.0.6.v201208230744.jar",
			"org.eclipse.emf.common_2.9.2.v20131212-0545.jar",
			"org.eclipse.emf.ecore.change_2.9.0.v20131212-0545.jar",
			"org.eclipse.emf.ecore.xmi_2.9.1.v20131212-0545.jar",
			"org.eclipse.emf.ecore_2.9.2.v20131212-0545.jar",
			"org.eclipse.emf_2.6.0.v20140203-1126.jar",
			"org.eclipse.equinox.app_1.3.100.v20130327-1442.jar",
			"org.eclipse.equinox.common_3.6.200.v20130402-1505.jar",
			"org.eclipse.equinox.preferences_3.5.100.v20130422-1538.jar",
			"org.eclipse.equinox.registry_3.5.301.v20130717-1549.jar",
			"org.eclipse.orbit.mongodb_2.10.1.v20130422-1135.jar",
			"org.eclipse.osgi.services_3.3.100.v20130513-1956.jar",
			"org.eclipse.osgi_3.9.1.v20140110-1610.jar",
			"org.eclipse.update.configurator_3.3.200.v20140203-1328.jar",
			"org.w3c.css.sac_1.3.0.v200805290154.jar",
			"org.w3c.dom.smil_1.0.0.v200806040011.jar",
			"org.w3c.dom.svg_1.1.0.v201011041433.jar",
			"saaj.jar",
			"servlet-api.jar",
			"Tidy.jar",
			"viewservlets.jar",
			"log4j-1.2.17.jar",
			"slf4j-api-1.7.21.jar",
			"slf4j-log4j12-1.7.21.jar"
	};
	
	private static final String MySQL_DRIVER_NAME="com.mysql.jdbc.Driver";
	
	private static final String ORACLE_DRIVER_NAME="oracle.jdbc.driver.OracleDriver";
	
	private static final String SQLSERVER_2000_DRIVER_NAME="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	
	private static final String SQLSERVER_2005_DRIVER_NAME="com.microsoft.sqlserver.jdbc.SQLServerDriver";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateDrivers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject responsejson = new JSONObject();
		String uploadPath = request.getServletContext().getRealPath("/WEB-INF/lib");
		validate(responsejson,uploadPath);
		log.info("请求成功！"+responsejson.toString());
		response.getWriter().write(responsejson.toString());
	}
	
	
	private void validate(JSONObject responsejson,String uploadPath){
		log.info("开始扫描lib文件夹:"+uploadPath);
		File f = new File(uploadPath);
		List<File> extrafiles = new ArrayList<File>();
		List<String> filenames = new ArrayList<String>();
		for(String filename:FILENAMES){
			filenames.add(filename);
		}
		if(f.isDirectory()){
			File files[] = f.listFiles();
			for(File f1:files){
				if(filenames.contains(f1.getName())){
					continue;
				}
				extrafiles.add(f1);
			}
		}
		log.info("额外jar包为：");
		for(File file:extrafiles){
			log.info(file.getName());
		}
		//MySql驱动
		try{
			//mysql驱动
			boolean mysql = false;
			//oracle驱动
			boolean oracle = false;
			//sqlserver2000驱动
			boolean sqlser2000 = false;
			//sqlserver2005驱动
			boolean sqlser2005 = false;
			for(File file:extrafiles){
				log.info("开始校验"+file.getName());
				if(validateDriver(file,MySQL_DRIVER_NAME)){
					mysql = true;
					continue;
				}
				if(validateDriver(file,ORACLE_DRIVER_NAME)){
					oracle = true;
					continue;
				}
				if(validateDriver(file,SQLSERVER_2000_DRIVER_NAME)){
					sqlser2000 = true;
					continue;
				}
				if(validateDriver(file,SQLSERVER_2005_DRIVER_NAME)){
					sqlser2005 = true;
					continue;
				}
			}
			responsejson.put("mysql("+MySQL_DRIVER_NAME+")",mysql);
			responsejson.put("oracle("+ORACLE_DRIVER_NAME+")",oracle);
			responsejson.put("sqlserver2000("+SQLSERVER_2000_DRIVER_NAME+")",sqlser2000);
			responsejson.put("sqlserver2005("+SQLSERVER_2005_DRIVER_NAME+")",sqlser2005);
		}catch(Exception e){
			responsejson = new JSONObject();
			responsejson.put("success",false);
			responsejson.put("message","校验失败");
			log.error("校验失败");
		}
		
		
	}
	
	
	public boolean validateDriver(File f,String drivername) throws Exception{
		JarFile jarFile = null;
		try{
        	URL url= f.toURI().toURL();
        	url=new URL("jar:"+url.toString()+"!/");
    		URLConnection connection = url.openConnection();
        	JarURLConnection jrConnection = (JarURLConnection)connection;
        	jarFile = jrConnection.getJarFile();
        	Enumeration<JarEntry> jarEntries = jarFile.entries();
        	while(jarEntries.hasMoreElements()){
        		JarEntry jarEntry = jarEntries.nextElement();  
                String jarEntryName = jarEntry.getName();
                jarEntryName = jarEntryName.replaceAll("/",".");
                if(jarEntryName.lastIndexOf(".class") != -1){
                	jarEntryName = jarEntryName.substring(0, jarEntryName.lastIndexOf(".class"));
                }
                if(drivername.equals(jarEntryName)){
                	//关闭jar文件
                	jarFile.close();
                    return true;
                }
        	}
        	//读取完毕且没有发现驱动类，关闭jar文件
        	jarFile.close();
    	}catch(Exception e){
    		if(jarFile != null){
    			try{
    				jarFile.close();
    				throw new Exception("错误");
    			}catch(Exception ex){
    				ex.printStackTrace();
    				throw new Exception("错误");
    			}
    		}
            return false;
    	}
		return false;
	}

}