package itcast.web.servlet;


import itcast.domain.Product;
import itcast.service.ManagementService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ModifyProductForwardServlet extends HttpServlet {

	// hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取商品的id
		String id = request.getParameter("id");
		// 传递id返回一个product对象
		ManagementService service = new ManagementService();
		Product pro = null;
		try {
			pro = service.findProductByd(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 存到域中
		request.setAttribute("pro", pro);
		// 转发到edit.jsp页面
		request.getRequestDispatcher("/admin/products/edit.jsp").forward(
				request, response);

	}

}
