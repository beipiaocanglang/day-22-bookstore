package itcast.dao;

import itcast.domain.Product;
import itcast.utils.DataSourceUtils;
import itcast.vo.Condition;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class ManagementDao {
	//点击订单按钮--显示订单列表的操作(user表和orders表一起查询 多表查询)
	public List<Map<String, Object>> findAllOrders() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select o.id,o.receiverAddress,o.receiverName,o.receiverPhone,o.money,u.username,o.paystate from user u,orders o where u.id=o.user_id;";
		return runner.query(sql, new MapListHandler());
	}
	//查看订单---查看按钮
	public List<Map<String, Object>> findAllOrderByOid(String id) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		/*String sql = "select o.id as oid,o.receiverName,o.receiverAddress,o.ordertime,o.receiverPhone,o.money,u.username,p.imgurl,p.id as pid,p.name,p.price,p.category,p.description,oi.buynum " +
				"from orders o, user u,product p,orderitem oi " +
				"where o.user_id=u.id and o.id=oi.order_id and oi.product_id=p.id and o.id?";*/
																																																											
		String sql = "select o.id as oid,o.receiverName,o.receiverAddress,o.ordertime,u.username,o.receiverPhone,o.money,p.imgurl,p.id as pid,p.name,p.price,i.buynum,p.category,p.description"+
				" from user u,orders o,orderitem i,products p"+
				" where u.id=o.user_id and o.id=i.order_id and i.product_id=p.id and o.id=?";		
		return runner.query(sql, new MapListHandler(), id);
	}
	//后台商品查看按钮---查看所有商品
	public List<Product> findAllProduct() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from products";
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}
	//后台添加商品按钮
	public void addProduct(Product pro) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into products values(?,?,?,?,?,?,?)";
		runner.update(sql,pro.getId(),pro.getName(),pro.getPrice(),pro.getCategory(),pro.getPnum(),pro.getImgurl(),pro.getDescription());
	
	}
	public Product findProductById(String id) throws SQLException {
		//根据商品id去查询该id的商品信息
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from products where id=?";
		Product pro = runner.query(sql, new BeanHandler<Product>(Product.class),id);
		 return pro;
	}
	//更新商品信息-- 更新数据库
	public void modifyProduct(Product pro) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update products set name=?,price=?,pnum=?,category=?,description=? where id=?";
		runner.update(sql, pro.getName(),pro.getPrice(),pro.getPnum(),pro.getCategory(),pro.getDescription(),pro.getId());
		
	}
	//删除商品
	public void delProductById(String id) throws SQLException {
		//进行数据库操作
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		//创建sql语句
		
		//修改数据库
		String sql="delete from products where id=?";
		runner.update(sql, id);
		
	}
	//批量删除数据
	public void delAllProduct(String[] delItems) throws SQLException {
		//获取连接池对象
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		//sql语句
		String sql = "delete from products where id=?";
		//二维数组
		Object[][] params = new Object[delItems.length][1];
		//遍历数组
		for(int x=0;x<delItems.length;x++){
			params[x][0]=delItems[x];
		}
		runner.batch(sql, params);
	}
	//查询商品
	public List<Product> findProductListByCondition(Condition condition) throws SQLException {
QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "select * from products where 1=1";
		
		//select * from products where id like '%3%' and name like ? and category=? and price>=minPrice and price<=maxPrice;
		
		//创建一个容器收集参数
		List<Object> conditions = new ArrayList<Object>();
		
		if(condition.getId()!=null&&!condition.getId().trim().equals("")){
			sql+=" and id like ?";
			conditions.add("%"+condition.getId()+"%");
		}
		if(condition.getName()!=null&&!condition.getName().trim().equals("")){
			sql+=" and name like ?";
			conditions.add("%"+condition.getName()+"%");
		}
		if(condition.getCategory()!=null&&!condition.getCategory().trim().equals("")){
			sql+=" and category = ?";
			conditions.add(condition.getCategory());
		}
		if(condition.getMinPrice()!=null&&!condition.getMinPrice().trim().equals("")){
			sql+=" and price >= ?";
			conditions.add(condition.getMinPrice());
		}
		if(condition.getMaxPrice()!=null&&!condition.getMaxPrice().trim().equals("")){
			sql+=" and price <= ?";
			conditions.add(condition.getMaxPrice());
		}
		
		//将list转成数组
		Object[] array = conditions.toArray();
		
		List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class), array);
		
		return productList;
	}
	

}
