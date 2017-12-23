package itcast.web.servlet;

import itcast.domain.Product;
import itcast.service.ManagementService;
import itcast.utils.CommonsUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
public class AddProductServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//后台添加商品按钮--有图片
		try {
			//定义一个存储数据的map
			Map<String,Object> map = new HashMap<String,Object>();
			//文件上传表单的提交----有上传图片的操作
			//1、创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//2、创建核心操作类
			ServletFileUpload upload = new ServletFileUpload(factory);
			//3、解析request
			List<FileItem> parseRequest = upload.parseRequest(request);
			//4、遍历所有FileItem
			for(FileItem item:parseRequest){
				//判断是否是文件上传项
				if(item.isFormField()){
					//普通表单项 收集表单项数据
					String filedName = item.getFieldName();
					String value = item.getString("UTF-8");
					//将数据封装到Product对象
					map.put(filedName, value);
				}else{
					//文件上传项
					//获得文件的名称
					String filename = item.getName();
					//获得随机名称
					filename = CommonsUtils.getRandomName(filename);//uuid.jpg
					//获得随机目录
					String randomDir = CommonsUtils.getRandomDir(filename);//  /b/a
					String dirPath = this.getServletContext().getRealPath("/bookcover")+randomDir;
					File file = new File(dirPath);
					if(!file.exists()){
						file.mkdirs();
					}
					//将图片的路径存在数据库
					map.put("imgurl", "bookcover"+randomDir+"/"+filename);
					
					//获得上传的文件内容
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(dirPath+"/"+filename);
					IOUtils.copy(in, out);
					in.close();
					out.close();
				}
			}
			
			//将map中的数据映射封装Product中
			Product pro = new Product();
			pro.setId(CommonsUtils.getUUID());
			try {
				BeanUtils.populate(pro, map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ManagementService service = new ManagementService();
			try {
				service.addProduct(pro);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			response.sendRedirect(request.getContextPath()+"/adminProductList");
			
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}

}
