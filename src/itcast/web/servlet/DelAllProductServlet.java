package itcast.web.servlet;
import itcast.service.ManagementService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DelAllProductServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取提交的所有的id delItem
		String[] delItems = request.getParameterValues("delItem");
		System.out.println("delItem");
		ManagementService service = new ManagementService();
		try {
			service.delAllProduct(delItems);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//跳转到商品列表页面
		response.sendRedirect(request.getContextPath()+"/adminProductList");
		
		
	}

}

