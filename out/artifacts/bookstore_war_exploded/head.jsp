<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="divhead">
	<table cellspacing="0" class="headtable">
		<tr>
			<td>
				<a href="index.jsp">
					<img src="images/logo.png" width="95" height="30" border="0" /> 
				</a>
			</td>
			<td style="text-align:right">
			<!-- 判断user对象步为空 -->
			<c:if test="${!empty user }">
				<span style="color:red;font-size:20px">欢迎您，${user.username }!!!</span>
			</c:if>
				<img src="images/cart.gif" width="26" height="23" style="margin-bottom:-4px" />
					&nbsp;
				<a href="cart.jsp">购物车</a> | 
				<a href="#">帮助中心</a> | 
				<a href="${pageContext.request.contextPath }/myAccount">我的帐户</a> | 
				<a href="register.jsp">新用户注册</a>
			</td>
		</tr>
	</table>
</div>