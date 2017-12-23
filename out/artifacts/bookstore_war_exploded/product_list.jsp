<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>bookStore列表</title>
<%--导入css --%>
<link rel="stylesheet" href="css/main.css" type="text/css" />
</head>

<body class="main">

	<jsp:include page="head.jsp" />
	<jsp:include page="menu_search.jsp" />

	<div id="divpagecontent">
		<table width="100%" border="0" cellspacing="0">
			<tr>

				<td>
					<div style="text-align:right; margin:5px 10px 5px 0px">
						<a href="index.jsp">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;计算机&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;图书列表
					</div>

					<table cellspacing="0" class="listcontent">
						<tr>
							<td>
								<h1>商品目录</h1>
								<hr />
								<h1>计算机</h1>&nbsp;&nbsp;&nbsp;&nbsp;共${pageBean.totalCount }种商品
								<hr />
								<div style="margin-top:20px; margin-bottom:5px">
									<img src="images/productlist.gif" width="100%" height="38" />
								</div>
										<c:forEach items="${pageBean.productList }" var="product">
											
											<div style="float: left;width:25%;">
												<div class="divbookpic">
													<p> 
														<a href="${pageContext.request.contextPath }/productInfo?id=${product.id }"><img src="${product.imgurl }" width="115"
															height="129" border="0" /> </a>
													</p>                               
												</div>
	
												<div class="divlisttitle">
													<a href="${pageContext.request.contextPath }/productInfo?id=${product.id }">书名:${product.name }<br />售价:${product.price } </a>
												</div>
											</div>
											
										</c:forEach>
										<div style="clear: both"></div>

								<div class="pagination">
								
									<!-- 上一页 -->
									<c:if test="${pageBean.currentPage==1 }">
										<li class="disablepage">&lt;&lt;上一页 </li>
									</c:if>
									<c:if test="${pageBean.currentPage!=1 }">
										<li class="nextpage">
											<a href="${pageContext.request.contextPath }/productList?currentPage=${pageBean.currentPage-1}">&lt;&lt;上一页 </a>
										</li>
									</c:if>
									
									<!-- 显示所有的页数 -->
									<c:forEach begin="1" end="${pageBean.totalPage }" var="page">
										<!-- 判断是否是当前页 -->
										<c:if test="${page==pageBean.currentPage }">
											<li class="currentpage">${page}</li>
										</c:if>
										<c:if test="${page!=pageBean.currentPage }">
											<li><a href="${pageContext.request.contextPath }/productList?currentPage=${page }">${page }</a></li>
										</c:if>
									</c:forEach>
									
									<!-- 下一页 -->
									<c:if test="${pageBean.currentPage==pageBean.totalPage }">
										<li class="disablepage">下一页&gt;&gt; </li>
									</c:if>
									<c:if test="${pageBean.currentPage!=pageBean.totalPage }">
										<li class="nextpage">
											<a href="${pageContext.request.contextPath }/productList?currentPage=${pageBean.currentPage+1}">下一页&gt;&gt; </a>
										</li>
									</c:if>
								</div>
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
	</div>



	<jsp:include page="foot.jsp" />


</body>
</html>
