package itcast.web.servlet;

import itcast.domain.Order;
import itcast.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderInfoServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//点击查看订单的查看按钮功能
		
		//接收客户端提交的id
		String oid = request.getParameter("oid");
		//传递到service层
		UserService service = new UserService();
		Order order=null;
		try {
			order = service.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//将order放到session中
		request.setAttribute("order", order);
		//转发到orderInfo.jsp
		request.getRequestDispatcher("/orderInfo.jsp").forward(request, response);
	}

}
