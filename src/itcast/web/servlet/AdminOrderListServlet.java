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

public class AdminOrderListServlet extends HttpServlet {

	// hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//点击订单按钮--显示订单列表的操作(user表和orders表一起查询 多表查询)
		// 直接传递到service层
		ManagementService service = new ManagementService();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = service.findAllOrders();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//将mapList放到域中
		request.setAttribute("mapList", mapList);
		// 转发到list.jsp页面
		request.getRequestDispatcher("/admin/orders/list.jsp").forward(request,response);
	}

}
