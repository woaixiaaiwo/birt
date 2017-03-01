package wubo.servlets;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import wubo.utils.FileNameUtil;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
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
		request.setCharacterEncoding("utf-8");
		String filename = request.getParameter("fileName");
		
		JSONObject responsejson = new JSONObject();
		
		if(filename == null || "".equals(filename)){
				responsejson.put("success",false);
				responsejson.put("message","nofilename");
				response.getWriter().print(responsejson);
				return;
		}
		filename = FileNameUtil.encodeToFileName(filename);
		for(File f:files){
			System.out.println(f.getName());
			fileNames.add(f.getName().substring(0,f.getName().indexOf(".rptdesign")));
		}

		response.setCharacterEncoding("utf-8");
		if(fileNames.contains(filename)){
			file = new File(realpath+"/"+filename+".rptdesign");
			System.out.println(file.exists());
			long len = file.length();
			byte[] bytes = new byte[(int) len];
			FileInputStream in = null;
			try {
				in = new FileInputStream(file);
				in.read(bytes);
			}catch(Exception e){
				
			}
			in.close();
			response.getOutputStream().write(bytes);
			return;
		}else{
			responsejson.put("success",false);
			responsejson.put("message","nofile");
			response.getWriter().print(responsejson);
			return;
		}
		
	}
	
}
