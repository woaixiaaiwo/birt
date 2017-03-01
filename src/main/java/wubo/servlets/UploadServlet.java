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

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import wubo.utils.FileNameUtil;
/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
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
		String jsondata = readJSONString(request);
		JSONObject requestjson = JSONObject.fromObject(jsondata);
		JSONObject responsejson = new JSONObject();
		String filename = requestjson.getString("fileName");
		filename = FileNameUtil.encodeToFileName(filename);
		String filedata = requestjson.getString("fileData");
		if(filename == null || "".equals(filename)){
			responsejson.put("success",false);
			responsejson.put("message","文件名不存在");
			response.getWriter().print(responsejson);
			System.out.println("文件名不存在！");
			return;
		}
		else if(filedata == null || "".equals(filedata)){
			responsejson.put("success",false);
			responsejson.put("message","文件数据不存在");
			response.getWriter().print(responsejson);
			System.out.println("文件数据不存在！");
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
			response.getWriter().print(responsejson);
			System.out.println("保存成功！");
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
