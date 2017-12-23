package itcast.web.servlet;

import itcast.service.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DelOrderServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接收客户端提交的数据
		String oid = request.getParameter("oid");
		
		//传递到service层
		UserService service = new UserService();
		service.delOrderByOid(oid);
		
		//转发到--查看订单页面
		request.getRequestDispatcher("/showOrders").forward(request, response);
	}

}
