package itcast.web.servlet;

import itcast.domain.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyAccountServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//主要作用是判断用户是否已登录
		
		//获取session
		HttpSession session = request.getSession();
		//获取session中的user
		User user = (User) session.getAttribute("user");
		
		//判断user是否存在
		if(user!=null){
			//用户存在
			response.sendRedirect(request.getContextPath()+"/myAccount.jsp");
		}else{
			//用户不存在
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			
		}
	}

}
