package itcast.service;

import itcast.dao.ManagementDao;
import itcast.domain.Product;
import itcast.vo.Condition;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


//后台操作的service
public class ManagementService {
	//点击订单按钮--显示订单列表的操作(user表和orders表一起查询 多表查询)
	public List<Map<String, Object>> findAllOrders() throws SQLException {
		//传递到dao层
		ManagementDao dao  = new ManagementDao();
		return dao.findAllOrders();
	}
	//查看订单---查看按钮
	public List<Map<String, Object>> findAllOrderByOid(String id) throws SQLException {
		ManagementDao dao  = new ManagementDao();
		return dao.findAllOrderByOid(id);
	}
	//后台商品查看按钮---查看所有商品
	public List<Product> findAllProduct() throws SQLException {
		ManagementDao dao = new ManagementDao();
		return dao.findAllProduct();
	}
	//后台添加商品按钮
	public void addProduct(Product pro) throws SQLException {
		ManagementDao dao = new ManagementDao();
		dao.addProduct(pro);

	}
	public Product findProductByd(String id) throws SQLException {
		// 根据获取的id查询商品
		ManagementDao dao =new ManagementDao();
		return dao.findProductById(id);
	}
	//更新商品信息
	public void modifyProduct(Product pro) throws SQLException {
		ManagementDao dao =new ManagementDao();
		dao.modifyProduct(pro);
		
	}
	//删除商品
	public void delProductById(String id) throws SQLException {
		//没有特殊业务，就继续往下传
		ManagementDao dao = new ManagementDao();
		dao.delProductById(id);
	}
	//批量删除数据
	public void delAllProduct(String[] delItems) throws SQLException {
		//没有特殊业务  ，继续传
		ManagementDao dao = new ManagementDao();
		dao.delAllProduct(delItems);
	}
	//查询商品
	public List<Product> findProductListByCondition(Condition condition) throws SQLException {
		//根据条件查询商品信息
		ManagementDao dao = new ManagementDao();
		return dao.findProductListByCondition(condition);
	}
	

}
