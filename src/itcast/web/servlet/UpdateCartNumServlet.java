package itcast.web.servlet;

import itcast.domain.Product;
import itcast.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateCartNumServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接收传过来的id
		String id = request.getParameter("id");
		//接收传过来的标识
		String flag = request.getParameter("flag");
		//获得session域
		HttpSession session = request.getSession();
		//获得session域中的购物车
		Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
		
		//传递到service层
		UserService service = new UserService();
		Product pro=null;
		try {
			pro = service.addId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Integer buyNum = cart.get(pro);
		//判断表示的作用
		if("add".equals(flag)){
			//如果是添加
			buyNum++;
		}else{
			//如果是减少
			buyNum--;
		}
		//放回map集合
		cart.put(pro, buyNum);
		
		//放回session域中
		request.setAttribute("cart", cart);
		//跳转到购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}

}
