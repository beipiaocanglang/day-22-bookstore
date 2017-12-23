package itcast.web.servlet;

import itcast.domain.Order;
import itcast.domain.User;
import itcast.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowOrdersServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取session
		HttpSession session = request.getSession();
		//获取session中的user
		User user = (User) session.getAttribute("user");
		//获取user中的id
		int id = user.getId();
		
		//传到service层
		UserService service = new UserService();
		List<Order> showOrder=null;
		try {
			showOrder = service.showOrder(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("showOrder",showOrder);
		request.setAttribute("showsize",showOrder.size());
		//转发到order.jsp
		request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
	}

}
