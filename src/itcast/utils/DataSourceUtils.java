package itcast.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	
	private static String driverClass = null;
	private static String url = null;
	private static String user = null;
	private static String password = null;
	

	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	public static Connection getConnectionByThread() throws Exception{
		Connection conn = tl.get();
		if(conn==null){
			conn = getConnection();
			tl.set(conn);
		}
		return conn;
	}
	
	public static void startTransaction() throws Exception{
		Connection conn = getConnectionByThread();
		conn.setAutoCommit(false);
	}
	
	public static void commitAndRelease() throws Exception{
		Connection conn = getConnectionByThread();
		conn.commit();
		tl.remove();
		conn.close();
	}
	
	public static void rollback() throws Exception{
		Connection conn = getConnectionByThread();
		conn.rollback();
	}


	public static Connection getConnection() throws Exception{
		Connection conn = dataSource.getConnection();
		return conn;
	}
	
	public static void close(Connection conn,Statement stmt,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				rs = null;
			}
		}
		
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				stmt = null;
			}
		}
		
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				conn = null;
			}
		}
		
	}

}
