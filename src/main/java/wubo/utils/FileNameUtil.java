package wubo.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileNameUtil {

	//将指定字符串进行md5加密
	public static String encodeToFileName(String fileName){
		MessageDigest md5 =null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("获取MD5工具失败...");
			return "";
		}
		byte[] filebytes = md5.digest(fileName.getBytes());
		StringBuffer hexValue = new StringBuffer();  
	    for (int i = 0; i < filebytes.length; i++){  
	         int val = ((int) filebytes[i]) & 0xff;  
	         if (val < 16)hexValue.append("0");  
	         hexValue.append(Integer.toHexString(val));  
	    }  
	    return hexValue.toString();
	}

}