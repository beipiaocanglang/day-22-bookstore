package itcast.web.servlet;

import itcast.domain.Product;
import itcast.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductInfoServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接收客户端提交的数据中的id
		String id = request.getParameter("id");
		//直接传递到service层要数据
		
		UserService service = new UserService();
		Product pro=null;
		try {
			pro = service.findId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//将数据放到session域中
		request.setAttribute("pro",pro);
		//转发到product_info.jsp页面
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

}
