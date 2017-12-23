package itcast.web.servlet;

import itcast.domain.Product;
import itcast.service.ManagementService;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModifyProductServlet extends HttpServlet {

	// hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 解决post的中文乱码
		request.setCharacterEncoding("UTF-8");
		//接收表单提交数据--创建实体
		Product pro = new Product();
		Map properties = request.getParameterMap();
		try {
			BeanUtils.populate(pro, properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//传递pro到service层
		ManagementService service= new ManagementService();
		try {
			service.modifyProduct(pro);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//将商品信息跳转到商品列表的页面
		response.sendRedirect(request.getContextPath()+"/adminProductList");
	
	}

}