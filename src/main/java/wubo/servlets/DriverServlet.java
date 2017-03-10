package wubo.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class DriverServlet
 */
//使用@WebServlet配置UploadServlet的访问路径
@WebServlet(name="DriverServlet",urlPatterns="/UploadDriver")
//使用注解@MultipartConfig将一个Servlet标识为支持文件上传
@MultipartConfig//标识Servlet支持文件上传
public class DriverServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(DriverServlet.class); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DriverServlet() {
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
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		JSONObject responsejson = new JSONObject();
		
		log.info("开始获取文件列表...");
		//获取上传的文件集合
		Collection<Part> parts = request.getParts();
		log.info("开始文件列表成功!");
		
		log.info("开始初始化文件路径...");
		String savePath = request.getServletContext().getRealPath("/WEB-INF/lib");
		log.info("文件路径为"+savePath); 
		 
		 //上传单个文件
		 if (parts.size()==1) {
		 //Servlet3.0将multipart/form-data的POST请求封装成Part，通过Part对上传的文件进行操作。
		 //Part part = parts[0];//从上传的文件集合中获取Part对象
			 log.info("获取文件...");
			 Part part = request.getPart("file1");//通过表单file控件(<input type="file" name="file">)的名字直接获取Part对象
		 //Servlet3没有提供直接获取文件名的方法,需要从请求头中解析出来
		 //获取请求头，请求头的格式：form-data; name="file"; filename="snmp4j--api.zip"
			 String header = part.getHeader("content-disposition");
		 //获取文件名
			 String fileName = getFileName(header);
			 log.info("文件名为:"+fileName);
		 //把文件写到指定路径
			 log.info("创建文件...");
			 try{
				 part.write(savePath+File.separator+fileName);
			 }catch(Exception e){
				 log.error("创建文件失败...",e);
				 PrintWriter out = response.getWriter();
				 responsejson.put("success",false);
				 responsejson.put("message",e.getMessage());
				 out.print(responsejson);
				 out.flush();
				 out.close();
				 return;
			 }
			 log.info("创建文件成功");
		 }else {
			 //一次性上传多个文件
			 for (Part part : parts) {//循环处理上传的文件
				 //获取请求头，请求头的格式：form-data; name="file"; filename="snmp4j--api.zip"
				 String header = part.getHeader("content-disposition");
				 //获取文件名
				 String fileName = getFileName(header);
				 //把文件写到指定路径
				 part.write(savePath+File.separator+fileName);
			 }
		 }
		 PrintWriter out = response.getWriter();
		 responsejson.put("success",true);
		 responsejson.put("message","上传成功");
		 out.print(responsejson);
		 out.flush();
		 out.close();
		 return;
	}
	
	public String getFileName(String header) {
		 /**
		 * String[] tempArr1 = header.split(";");代码执行完之后，在不同的浏览器下，tempArr1数组里面的内容稍有区别
		 * 火狐或者google浏览器下：tempArr1={form-data,name="file",filename="snmp4j--api.zip"}
		 * IE浏览器下：tempArr1={form-data,name="file",filename="E:\snmp4j--api.zip"}
		 */
		 String[] tempArr1 = header.split(";");
		 /**
		 *火狐或者google浏览器下：tempArr2={filename,"snmp4j--api.zip"}
		 *IE浏览器下：tempArr2={filename,"E:\snmp4j--api.zip"}
		  */
		 String[] tempArr2 = tempArr1[2].split("=");
		 //获取文件名，兼容各种浏览器的写法
		 String fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\")+1).replaceAll("\"", "");
		 return fileName;
	}
	

	
	private String readJSONString(HttpServletRequest request) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
		StringBuffer json = new StringBuffer();
		String line = null;
		try {
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null) {
			json.append(line);
		}
		}
		catch(Exception e) {
		System.out.println(e.toString());
		}
		return json.toString();
	}

}

