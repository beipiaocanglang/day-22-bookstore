package itcast.web.servlet;

import itcast.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActiveServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//接收连接传过来的激活码
		String activeCode = request.getParameter("activeCode");
		//去数据库中查找该激活码状态的用户
		UserService service = new UserService(); 
		boolean isActiveSuccess=false;
		try {
			isActiveSuccess = service.active(activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//跳转到激活成功页面
		if(isActiveSuccess){
			response.sendRedirect(request.getContextPath()+"/activesuccess.jsp");
		}
	}

}
