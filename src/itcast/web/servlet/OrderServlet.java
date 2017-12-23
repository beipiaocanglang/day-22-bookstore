package itcast.web.servlet;

import itcast.domain.Order;
import itcast.domain.OrderItem;
import itcast.domain.Product;
import itcast.domain.User;
import itcast.service.OrderService;
import itcast.utils.CommonsUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获得session域 
		HttpSession session = request.getSession();
		//servlet的主要i作用是分装order对象
		Order order = new Order();
		
		//封装order中的字段
		// private String oid;---uuid进行封装
			String oid = CommonsUtils.getUUID();
			order.setOid(oid);
		// private double money;//订单的合计---赋初始值0
			double money = 0;
		// private String receiverAddress;//收获地址
			//获取客户端提交的地址
			String receiverAddress = request.getParameter("receiverAddress");
			order.setReceiverAddress(receiverAddress);
		// private String receiverName;//收货人
			//获取客户端提交的收件人名字
			String receiverName = request.getParameter("receiverName");
			order.setReceiverName(receiverName);
		// private String receiverPhone;//收货人电话
			//获取客户端提交的电话
			String receiverPhone = request.getParameter("receiverPhone");
			order.setReceiverPhone(receiverPhone);
		// private int paystate;//支付状态
			order.setPaystate(0);
		// private Date ordertime;//订单时间
			order.setOrdertime(new Date());
		// private User user;
			//从域中获取user对象
			User user = (User) session.getAttribute("user");
			//封装到order中
			order.setUser(user);
		// private List<OrderItem> orderItemList;
			//从session域中获取购物车
			Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
			//封装orderItemList
			List<OrderItem> orderItemList = new ArrayList<OrderItem>();
			//将商品信息从cart中取出封装的orderItemList中
			//for循环
			for(Map.Entry<Product, Integer> entry : cart.entrySet()){
				//创建OrderItem对象
				OrderItem item = new OrderItem();
				//封装OrderItem实体
				//private Order order;
				item.setOrder(order);
				//private Product product;
				item.setProduct(entry.getKey());
				//private int buyNum;
				item.setBuyNum(entry.getValue());
				//private double itemMoney;
				item.setItemMoney(entry.getKey().getPrice()*entry.getValue());
				//添加到list集合
				orderItemList.add(item);
				money+=item.getItemMoney();
			}
			//封装setOrderItemList
			order.setOrderItemList(orderItemList);
			order.setMoney(money);
			
			//将order对象传递到service层
			OrderService service = new OrderService();
			service.submitOrder(order);
			
			//跳转到支付页面
			response.sendRedirect(request.getContextPath()+"/pay.jsp");
		
		
		
	}

}
