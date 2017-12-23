package itcast.dao;

import itcast.domain.Order;
import itcast.domain.Product;
import itcast.domain.User;
import itcast.utils.DataSourceUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;



public class UserDao {

	public int regist(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?,?)";
		int update = runner.update(sql, null,user.getUsername(),user.getPassword(),user.getGender(),user.getEmail(),user.getTelephone(),
				user.getIntroduce(),user.getActiveCode(),user.getState(),user.getRole(),user.getRegistTime());
		return update;
	}
	//鐢ㄦ埛婵�娲婚偖绠辩殑鏂规硶
	public int active(String activeCode) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update user set state=? where activeCode=?";
		runner.update(sql, 1,activeCode);
		return 0;
		
	}
	//鍓嶅彴鏄剧ず椤甸潰--鍒嗛〉
	
	
	
	
//	//鍓嶅彴鏄剧ず椤甸潰
//	public List<Product> findAllProduct() throws SQLException {
//		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
//		String sql = "select * from products";
//		List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class));
//		return productList;
//	}
	//璺宠浆鍟嗗搧璇︾粏淇℃伅椤甸潰
	public Product findProductById(String id) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		//浣跨敤甯﹀弬鏁扮殑鏂规硶
		String sql= "select * from products where id=?";
		return runner.query(sql, new BeanHandler<Product>(Product.class), id);
	}
	//娣诲姞鍒拌喘鐗╄溅--鍔犲噺鎸夐挳
	public Product toCart(String id) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from products where id=?";
		return runner.query(sql , new BeanHandler<Product>(Product.class), id);
	}
	//鍔犲噺鎸夐挳
	public Product addToCart(String id) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from products where id=?";
		return runner.query(sql , new BeanHandler<Product>(Product.class), id);
	}
	//鐧诲綍鎸夐挳
	public User pasword(String username, String password) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from user where username=? and password=?";
		return runner.query(sql, new BeanHandler<User>(User.class), username,password);
	}
	 //淇敼鐢ㄦ埛淇℃伅
	public int modifyUser(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set password=?,gender=?,telephone=? where id=?";
		return runner.update(sql, user.getPassword(),user.getGender(),user.getTelephone(),user.getId());
	}
	//淇敼session涓殑user
	public User findUserById(String id) throws SQLException {
		QueryRunner runner =  new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from user where id=?"; 
		return runner.query(sql, new BeanHandler<User>(User.class), id);
	}
	//鏌ヨproducts鎬绘潯鏁�
	public int findCount() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from products";
		Long query = (Long) runner.query(sql,new ScalarHandler());
		return query.intValue();
		
	}
	//鑾峰彇褰撳墠椤电殑鍟嗗搧鐨勯泦鍚�  index浠ｈ〃璧峰绱㈠紩
	public List<Product> findProductListByPage(int index, int currentCount) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from products limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), index,currentCount);
		}
	//璁㈠崟鏌ヨ--鏌ヨ鎵�鏈夎鍗曠殑璇︾粏淇℃伅
	public List<Order> showOrder(int id) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		//杩欐牱鏄剧ず娌℃湁id缂栧彿鏁版嵁
		//String sql="select * from orders where user_id=?";
		String sql="select id as oid,money,receiverName,receiverPhone,receiverAddress,paystate,ordertime from orders where user_id=?";
		return runner.query(sql, new BeanListHandler<Order>(Order.class),id);
		
	}
	//鐐瑰嚮鏌ョ湅璁㈠崟鐨勬煡鐪嬫寜閽姛鑳�
	public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select o.id as oid,o.money,o.receiverAddress,o.receiverName,o.receiverPhone,i.buynum as buyNum,"+
				" p.price*i.buynum as itemMoney,p.name,p.price,o.paystate"+
				" from orders o,orderitem i,products p"+
				" where o.id=i.order_id and i.product_id=p.id and o.id=?";
		
		/*String sql = "select o.id as oid,o.money,o.receiverAddress,o.receiverName,o.receiverPhone,i.buynum as buyNum,p.price*i.buynum as itemMoney,p.name,p.price,o.paystate"+
					" from orders o,orderitem i,product p"+
					" where o.id=i.order_id and i.prduct_id=p.id and o.id=?";*/
		 List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), oid);
		 System.out.println("mapList"+mapList);
		return mapList;
	}
	//鍒犻櫎璁㈠崟椤圭殑鏂规硶
	public void orderItem(String oid) throws Exception {
		QueryRunner runner = new QueryRunner();
		//浠庡伐鍏峰寘涓皟鐢–onnection鏂规硶
		Connection conn = DataSourceUtils.getConnection();
		//鏍规嵁oid鏌ヨ鏁版嵁搴撲腑鏈夊嚑鏉＄浉瀵瑰簲鐨勬暟鎹�
		String sql_count="select count(*) from orderitem where order_id=?";
		Long queryRow = (Long) runner.query(conn, sql_count, new ScalarHandler(),oid);
		String sql="delete from orderitem where order_id=?";
		int row = runner.update(conn, sql, oid);
		if(row!=queryRow.intValue()){
			throw new SQLException();
		}
	}
	//鍒犻櫎璁㈠崟鐨勬柟娉�
	public void order(String oid) throws Exception {
		QueryRunner runner = new QueryRunner();
		String sql = "delete from orders where id=?";
		Connection conn = DataSourceUtils.getConnection();
		int update = runner.update(conn, sql , oid);
		//鍒ゆ柇
		if(update<=0){
			throw new SQLException();
		}
	}
	

}
