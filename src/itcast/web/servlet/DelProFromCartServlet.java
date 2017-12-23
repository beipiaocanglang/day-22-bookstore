package itcast.web.servlet;

import itcast.domain.Product;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DelProFromCartServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取要删除的id
		String id = request.getParameter("id");
		//获取session域
		HttpSession session = request.getSession();
		Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
		
		Product pro = new Product();
		pro.setId(id);
		cart.remove(pro);
		
		//将购物车放回
		session.setAttribute("cart", cart);
		
		//调回购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}

}
