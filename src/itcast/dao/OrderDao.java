package itcast.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;


import itcast.domain.Order;
import itcast.domain.OrderItem;
import itcast.utils.DataSourceUtils;

public class OrderDao {
	//向order表中添加数据
	public int orders(Order order) throws Exception {
		QueryRunner runner = new QueryRunner();
		Connection conn = DataSourceUtils.getConnection();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		return runner.update(conn, sql, 
				order.getOid(),order.getMoney(),order.getReceiverAddress(),order.getReceiverName(),
				order.getReceiverPhone(),order.getPaystate(),order.getOrdertime(),order.getUser().getId()
				);
	}
	//向orderitem表插入数据的方法
	public int[] orderItem(Order order) throws Exception {
		QueryRunner runner = new QueryRunner();
		Connection conn = DataSourceUtils.getConnectionByThread();
		String sql = "insert into orderitem values(?,?,?)";
		List<OrderItem> orderItemList = order.getOrderItemList();
		Object[][] params = new Object[orderItemList.size()][3];
		for(int i=0;i<orderItemList.size();i++){
			OrderItem orderItem = orderItemList.get(i);
			params[i][0] = orderItem.getOrder().getOid();
			params[i][1] = orderItem.getProduct().getId();
			params[i][2] = orderItem.getBuyNum();
			
		}
		int[] batch = runner.batch(conn, sql, params);
		
		return batch;
	}
	//更新products表的数据的方法
	public int[] updatePnum(Order order) throws Exception {
		QueryRunner runner = new QueryRunner();
		Connection conn = DataSourceUtils.getConnectionByThread();
		String sql = "update products set pnum=pnum-? where id=?";
		List<OrderItem> orderItemList = order.getOrderItemList();
		Object[][] params = new Object[orderItemList.size()][2];
		for(int i=0;i<orderItemList.size();i++){
			OrderItem orderItem = orderItemList.get(i);
			params[i][0] = orderItem.getBuyNum();
			params[i][1] = orderItem.getProduct().getId();
		}
		int[] batch = runner.batch(conn, sql, params);
		return batch;
	}

}
