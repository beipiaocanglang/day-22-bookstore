package itcast.web.servlet;

import itcast.service.ManagementService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DelProductServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取要删除的商品的id
		String id = request.getParameter("id");
		
		//该id传递到service层、
		ManagementService service = new ManagementService();
			try {
				service.delProductById(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		//跳转到商品列表页面,让servlet准备数据
		response.sendRedirect(request.getContextPath()+"/adminProductList");
		
	}

}