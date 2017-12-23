package itcast.web.servlet;

import itcast.domain.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderForwardServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//用于校验用户是否存在
		
		//获取session域
		HttpSession session = request.getSession();
		//获取session域中的user对象
		User user = (User) session.getAttribute("user");
		//判断域中的user对象是否存在
		if(user!=null){
			//存在--直接跳转到结算页面
			
			request.getRequestDispatcher("/order.jsp").forward(request, response);
		}else{
			//不存在---跳转到登录页面
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
	}

}
