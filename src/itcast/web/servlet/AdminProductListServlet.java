package itcast.web.servlet;

import itcast.domain.Product;
import itcast.service.ManagementService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminProductListServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//后台商品查看按钮---查看所有商品
		//直接传递到service层
		ManagementService service = new ManagementService();
		List<Product> productList=null;
		try {
			productList = service.findAllProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//将product对象放到域中
		request.setAttribute("productList", productList);
		//转发到list.jsp 页面
		request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
	}

}
