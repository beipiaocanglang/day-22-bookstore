package itcast.web.servlet;

import itcast.domain.User;
import itcast.service.UserService;
import itcast.utils.CommonsUtils;
import itcast.utils.MailUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

public class RegisterServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//解决验证码
		request.setCharacterEncoding("UTF-8");
		//获得session域
		HttpSession session = request.getSession();
		
		//1、校验验证码
			//获取客户端提交的验证码
			String checkcode_client = request.getParameter("checkCode");
			//获取session中的验证码
			String checkcode_session = (String) session.getAttribute("checkcode_session");
			//将客户端提交的验证码和session域中的验证码进行对比
			if(!checkcode_session.equals(checkcode_client)){
				//向客户端写数据
				//response.getWriter().write("验证码错误，请重新填写");
				//转发
				response.sendRedirect(request.getContextPath()+"/register.jsp");
				return;
			}
		//2、收集表单提交数据--封装实体
			//创建map集合
			Map<String, String[]> properties = request.getParameterMap();
			//创建user对象
			User user = new User();
			//调用dbutils
			try {
				BeanUtils.populate(user, properties);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//封装实体
			//activeCode--激活码---uuid
			String activeCode = CommonsUtils.getUUID();
			//封装激活码
			user.setActiveCode(activeCode);
			//封装激活码状态
			user.setState(0);
			//封装用户的级别--默认是普通级别
			user.setRole("普通用户");
			//封装注册时间
			user.setRegistTime(new Date());
		//3、将user对象传到service层
			UserService service = new UserService();
			//调用service层的方法
			boolean isRegistSuccess=false;
			try {
				isRegistSuccess = service.regist(user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//4、发送邮箱
			String url="http://h6pccbgk36.proxy.qqbrowser.cc"+request.getContextPath()+"/activeServlet?activeCode="+activeCode;
			String emailMsg = "<h2>恭喜您注册成功，请点击下面的激活链接进行激活<a >"+url+"</a></h2>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
				System.out.println(user.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			//跳转
			response.sendRedirect(request.getContextPath()+"/registersuccess.jsp");
			
	}

}
