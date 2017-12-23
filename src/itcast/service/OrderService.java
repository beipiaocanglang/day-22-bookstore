package itcast.service;

import java.sql.SQLException;

import itcast.dao.OrderDao;
import itcast.domain.Order;
import itcast.utils.DataSourceUtils;

public class OrderService {

	public void submitOrder(Order order) {
		//传递到service层
		OrderDao dao = new OrderDao();
		//定义标识
		boolean flag = true;
		//1、开启事务
		try {
			DataSourceUtils.startTransaction();
			//2、向order表插入数据的方法
			int orderRes = dao.orders(order);
			//3、向orderitem表插入数据的方法
			int[] orderItemRes = dao.orderItem(order);
			//4、更新products表的数据的方法
			int[] pnumRes = dao.updatePnum(order);
			
			if(orderRes<1){
				flag = false;
			}
			for(int i : orderItemRes){
				if(i<1){
					flag = false;
					break;
				}
			}
			for(int i : pnumRes){
				if(i<1){
					flag = false;
					break;
				}
			}
			if(!flag){
				//回滚
				DataSourceUtils.rollback();
			}
			
		} catch (SQLException e) {
			//回滚
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//提交
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
