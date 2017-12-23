package itcast.web.servlet;

import itcast.domain.User;
import itcast.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyUserServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		//获取客户端提交单额数据
		Map<String,String[]> parameterMap = request.getParameterMap();
		//创建实体
		User user = new User();
		
		 try {
			BeanUtils.populate(user, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 //传递到service
		 UserService service = new UserService();
		 boolean isModifySuccess=false;
		 try {
			isModifySuccess = service.modifyUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		 if(isModifySuccess){
			//修改成功---同时也更新一下session中 数据
			User user_session=null;
			try {
				user_session = service.findUserById(user.getId()+"");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 //放回域中
			session.setAttribute("user", user_session);
			
			//将session删掉
			session.invalidate();
			
			//跳转到modifyuserinfo.jsp页面
			response.sendRedirect(request.getContextPath()+"/modifyUserInfoSuccess.jsp");
		 }else{
			 response.sendRedirect(request.getContextPath()+"/modifyuserinfo.jsp");
			 
		 }
		 
		 
	}

}
