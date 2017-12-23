package itcast.web.servlet;

import itcast.service.ManagementService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminOrderInfoServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//查看订单--查看按钮
		//获得客户端提交的id
		String id = request.getParameter("id");
		//传递到service层
		ManagementService service = new ManagementService();
		List<Map<String, Object>> mapList=null;
		try {
			mapList = service.findAllOrderByOid(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//放到域中
		request.setAttribute("mapList", mapList);
		//转发到view.jsp
		request.getRequestDispatcher("/admin/orders/view.jsp").forward(request, response);
	}

}
