package wubo.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import wubo.utils.FileNameUtil;

/**
 * Servlet implementation class DeleteServlet
 */
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
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
		String filePath=this.getServletConfig().getServletContext().getRealPath("/"); 
		String realpath = filePath+"/reportFiles";
		File file = new File(realpath);
		File[] files = file.listFiles();
		ArrayList<String> fileNames = new ArrayList<String>();
		String jsondata = readJSONString(request);
		JSONObject requestjson = JSONObject.fromObject(jsondata);
		String filename = requestjson.getString("fileName");
		filename = FileNameUtil.encodeToFileName(filename);
		for(File f:files){
			System.out.println(f.getName());
			fileNames.add(f.getName().substring(0,f.getName().indexOf(".rptdesign")));
		}
		JSONObject responsejson = new JSONObject();

		response.setCharacterEncoding("utf-8");
		if(fileNames.contains(filename)){
			file = new File(realpath+"/"+filename+".rptdesign");
			if(file.exists()){
				file.delete();
				responsejson.put("success",true);
				responsejson.put("message","删除成功");
				response.getWriter().print(responsejson);
				return;
			}
			else{
				responsejson.put("success",false);
				responsejson.put("message","文件不存在");
				response.getWriter().print(responsejson);
				return;
			}
		}else{
			responsejson.put("success",false);
			responsejson.put("message","文件不存在");
			response.getWriter().print(responsejson);
			return;
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
