package itcast.web.servlet;

import itcast.service.ManagementService;
import itcast.service.UserService;
import itcast.vo.PageBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductListServlet extends HttpServlet {

	// hu
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		//int currentPage = 1; //ģ���ǵ�һҳ
		String currentPageStr = request.getParameter("currentPage");
		//�ж�
		if(currentPageStr==null){
			currentPageStr="1";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		//ģ��һҳ��ʾ4�����
		int currentCount=4;
		
		
		// ���յ�Ŀ���ǽ�PageBean��װ������
		UserService service = new UserService();
		PageBean pageBean=null;
		try {
			pageBean = service.getPageBean(currentPage,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// ��pagebean�ŵ�����
		request.setAttribute("pageBean", pageBean);

		request.getRequestDispatcher("/product_list.jsp").forward(request,
				response);

		
		
		
		
		
		
		
		// // ֱ�ӽ������͸�service ��ȡȫ�������
		// ProductListService service = new ProductListService();
		// List<Product> productList = null;
		// try {
		// productList = service.findAllProduct();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// request.setAttribute("productList", productList);
		//
		// request.getRequestDispatcher("/product_list.jsp").forward(request,
		// response);

	}

}
