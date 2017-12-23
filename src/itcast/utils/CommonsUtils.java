package itcast.utils;

import java.util.UUID;

public class CommonsUtils {

	public static String getUUID(){
		return UUID.randomUUID().toString();
	}


	//解决上传文件名称磁盘符问题
	public static String getRealName(String filename){
		//C:\abc\xxx.txt
		//获得随后一个斜杠\的位置
		int index = filename.lastIndexOf("\\");
		filename = filename.substring(index+1);
		return filename;
	}


	//解决上传文件的重名问题
	public static String getRandomName(String filename){
		//xxx.jpg
		//截取最后点的位置
		int index = filename.lastIndexOf(".");
		//扩展名 .jpg
		String exetend = filename.substring(index);
		String uuid = UUID.randomUUID().toString();
		return uuid+exetend;
	}

	//获得文件的随机目录
	public static String getRandomDir(String filename){
		//xxx.txt
		//根据文件名获得hashcode
		int hashCode = filename.hashCode();
		//将hashcode转成16进制   
		String hexString = Integer.toHexString(hashCode);
		//System.out.println(hexString);
		//   ba243579    f8834945   c016e922
		//16进制的这个字符串 将每一个字符作为一级目录
		//取2级目录的深度
		//   /b/a        /f/8       /c/0
		return "/"+hexString.charAt(0)+"/"+hexString.charAt(1);
	}

}
