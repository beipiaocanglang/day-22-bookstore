package itcast.web.servlet;

import itcast.domain.Product;
import itcast.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddProductToCartServlet extends HttpServlet {

	// hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 接收客户端传过来的id
		String id = request.getParameter("id");
		// 传到service
		UserService service = new UserService();
		Product product=null;
		try {
			product = service.findProductById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 获得session域
		HttpSession session = request.getSession();
		// 获得session域中 的购物车
		Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
		// 判断购物车是否存在
		if (cart == null) {
			// 不存在--创建
			cart = new HashMap<Product, Integer>();
		}
		// 将商品放到购物车中--同时判断购物车中是否存在该商品
		// 调用hashMap的方法public boolean containsKey(Object
		// key)如果此映射包含对于指定键的映射关系，则返回 true。
		if(cart.containsKey(product)){
			//存在--取出数据并加一
			int buyNum = cart.get(product);
			//数量加一
			buyNum++;
			//在放回map集合中
			cart.put(product, buyNum);
		}else{
			//不存在--直接添加
			cart.put(product, 1);
		}
		//再将购物车放到session域中
		session.setAttribute("cart", cart);
		//跳转到购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

}
