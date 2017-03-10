package wubo.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import wubo.utils.FileNameUtil;
/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(UploadServlet.class); 
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public UploadServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String filePath=this.getServletConfig().getServletContext().getRealPath("/"); 
		String realpath = filePath+"/reportFiles";
		log.info("要上传的文件路径为："+realpath);
		String jsondata = readJSONString(request);
		JSONObject requestjson = JSONObject.fromObject(jsondata);
		JSONObject responsejson = new JSONObject();
		String filename = requestjson.getString("fileName");
		filename = FileNameUtil.encodeToFileName(filename);
		String filedata = requestjson.getString("fileData");
		log.info("文件名为:"+filename);
		if(filename == null || "".equals(filename)){
			responsejson.put("success",false);
			responsejson.put("message","文件名不存在");
			log.error("文件名为空"+responsejson.toString());
			response.getWriter().print(responsejson);
			return;
		}
		else if(filedata == null || "".equals(filedata)){
			responsejson.put("success",false);
			responsejson.put("message","文件数据不存在");
			log.error("文件数据不存在！"+responsejson.toString());
			response.getWriter().print(responsejson);
			return;
		}
        try{
        	File f = new File(realpath+"/"+filename+".rptdesign");
        	BASE64Decoder decoder = new BASE64Decoder();
    		byte data[] = decoder.decodeBuffer(filedata);
    		FileOutputStream fos = new FileOutputStream(f);
    		fos.write(data);
    		fos.flush();
    		fos.close();
    		responsejson.put("success",true);
			responsejson.put("message","保存成功");
			log.info("保存成功！"+responsejson.toString());
			response.getWriter().print(responsejson);
			return;
        }catch(Exception e){
        	e.printStackTrace();
        }
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