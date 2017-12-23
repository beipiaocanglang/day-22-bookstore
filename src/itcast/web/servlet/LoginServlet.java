package itcast.web.servlet;

import itcast.domain.User;
import itcast.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取提交的用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//将数据传到service层
		UserService service = new UserService();
		User user=null;
		try {
			user = service.passwor(username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//判断user是否存在
		HttpSession session = request.getSession();
		if(user!=null){
			//密码正确
			//将user放回session域中
			session.setAttribute("user", user);
			//*******************判断用户是否勾选自动登录********************
			String autoLogin = request.getParameter("autoLogin");
			if("autoLogin".equals(autoLogin)){
				//客户要求自动登录
				Cookie cookie_username = new Cookie("cookie_username",user.getUsername());   
				Cookie cookie_password = new Cookie("cookie_password",user.getPassword()); 
				cookie_username.setMaxAge(60*10);
				cookie_password.setMaxAge(60*10);
				response.addCookie(cookie_username);
				response.addCookie(cookie_password);
			}
			//***************************************************
			
			
			//重定向
			response.sendRedirect(request.getContextPath()+"/order.jsp");
		}else{
			//密码错误
			//向用户输出提示信息
			request.setAttribute("loginInfo", "对不起！您输入的用户名或密码错误，请重新输入");
			//失败后再跳转到登录页面---转发
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
