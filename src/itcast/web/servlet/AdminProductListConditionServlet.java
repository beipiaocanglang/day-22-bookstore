package itcast.web.servlet;

import itcast.domain.Product;
import itcast.service.ManagementService;
import itcast.vo.Condition;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

public class AdminProductListConditionServlet extends HttpServlet {

	//hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//解决中文乱码
		request.setCharacterEncoding("UTF-8");
		
		//获取查询条件的表单，进行封装Condition对象 ---value object
		Map properties = request.getParameterMap();
		Condition condition = new Condition();
		try {
			BeanUtils.populate(condition, properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//将Condition对象传递到service层
		ManagementService service =new ManagementService();
		List<Product> productList = null;
		try {
			productList = service.findProductListByCondition(condition);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//封装到域中
		request.setAttribute("condition", condition);
		request.setAttribute("productList", productList);
		
		//转发
		request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
		
	}

}
