package itcast.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import itcast.dao.UserDao;
import itcast.domain.Order;
import itcast.domain.OrderItem;
import itcast.domain.Product;
import itcast.domain.User;
import itcast.utils.DataSourceUtils;
import itcast.vo.PageBean;
import org.apache.commons.beanutils.BeanUtils;

public class UserService {
	//注册的方法
	public boolean regist(User user) throws SQLException {
		//没有特殊业务，继续往下传
		UserDao dao = new UserDao();
		int row = dao.regist(user);
		return row>0?true:false;
	}
	//用户激活邮箱的方法
	public boolean active(String activeCode) throws SQLException {
		UserDao dao = new UserDao();
		int row = dao.active(activeCode);
		return row>0?true:false;
		
	}
	//前台显示页面--分页
	public PageBean getPageBean(int currentPage,int currentCount) throws SQLException {
		UserDao dao = new UserDao();
		PageBean pageBean = new PageBean();

		// 1、private int currentPage; //当前页
		pageBean.setCurrentPage(currentPage);
		// 2、private int currentCount; //当前页显示的条数
		pageBean.setCurrentCount(currentCount);
		// 3、private int totalCount; //总条数
		int totalCount = dao.findCount();
		// 4、private int totalPage; //总页数
		/*
		 * 总条数 当前页显示的条数 页数 10 4 3 11 4 3 12 4 3 13 4 4
		 * 
		 * 总条数/当前页显示的条数---结果向上取整
		 */
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		pageBean.setTotalPage(totalPage);
		// 5、private List<Product> productList;//当前页显示的商品的集合
		// index:起始的索引 currentCount：当前页显示的条数
		/*
		 * 当前页与index索引的关系 在每页显示4条的情况下 当前页码 索引 关系 1 0 (1-1)*4 2 4 (2-1)*4 3 8
		 * (3-1)*4 总结公式：(当前页码-1)*当前页显示的条数=index
		 */
		int index = (currentPage-1)*currentCount;
		List<Product> productList = dao.findProductListByPage(index,currentCount);
		pageBean.setProductList(productList);

		return pageBean;

	}
		
	
	
//	//前台显示页面
//	public List<Product> findAllProduct() throws SQLException {
//		UserDao dao = new UserDao();
//		List<Product> productList = dao.findAllProduct();
//		return productList;
//	}
	
	
	
	//跳转商品详细信息页面
	public Product findId(String id) throws SQLException {
		//没有特殊业务--继续往下传
		UserDao dao = new UserDao();
		Product pro = dao.findProductById(id);
		return pro;
	}
	//添加到购物车
	public Product findProductById(String id) throws SQLException {
		UserDao dao = new UserDao();
		Product pro = dao.toCart(id);
		return pro;
	}
	//加减按钮
	public Product addId(String id) throws SQLException {
		UserDao dao = new UserDao();
		Product pro = dao.addToCart(id);
		return pro;
	}
	//登录按钮
	public User passwor(String username, String password) throws SQLException {
		UserDao dao = new UserDao();
		return dao.pasword(username, password);
	}
	//修改用户信息的方法
	public boolean modifyUser(User user) throws SQLException {
		UserDao dao = new UserDao();
		int row =dao.modifyUser(user);
		return row>0?true:false;
	}
	//根据id同时更改session中的数据
	public User findUserById(String id) throws SQLException {
		UserDao dao = new UserDao();
		return dao.findUserById(id);
	}
	//订单查询--查询所有订单的详细信息
	public List<Order> showOrder(int id) throws SQLException {
		UserDao dao = new UserDao();
		return dao.showOrder(id);
	}
	//点击查看订单的查看按钮功能
	public Order findOrderInfoByOid(String oid) throws SQLException {
		UserDao dao = new UserDao();
		List<Map<String, Object>> mapList = dao.findOrderInfoByOid(oid);
		//将maplist中的数据封装到orders中
		Order order = new Order();
		//遍历list集合
		for(Map<String, Object> map : mapList){
			OrderItem item = new OrderItem();
			Product product =  new Product();
			try {
				BeanUtils.populate(order, map);
				BeanUtils.populate(item, map);
				BeanUtils.populate(product, map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//将product对象封装到item中
			item.setProduct(product);
			order.getOrderItemList().add(item);
		}
		
		return order;
	}
	//查看订单中的  删除按钮
	public void delOrderByOid(String oid) {
		//传递到service
		UserDao dao = new UserDao();
		try {
			//开启事物---调工具中的方法
			DataSourceUtils.startTransaction();
			//删除订单项的方法
			dao.orderItem(oid);
			//删除订单的方法
			dao.order(oid);
			
		} catch (Exception e) {
			//回滚事物
			try {
				DataSourceUtils.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			//提交事物
			try {
				DataSourceUtils.commitAndRelease();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
