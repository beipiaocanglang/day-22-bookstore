<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript" >
	function logout(){
		if(confirm("您确定要注销吗？")){
			location.href="${pageContext.request.contextPath}/logout";
		}
	}


</script>
<!-- 提取出来的共有部分 -->
<table width="100%" border="0" cellspacing="0"
	style="margin-top:30px">
	<tr>
		<td class="listtitle">我的帐户</td>
	</tr>
	<tr>
		<td class="listtd"><img src="images/miniicon.gif" width="9"
			height="6" />&nbsp;&nbsp;&nbsp;&nbsp; <a
			href="modifyuserinfo.jsp">用户信息修改</a></td>
	</tr>

	<tr>
		<td class="listtd"><img src="images/miniicon.gif" width="9" height="6" />
			&nbsp;&nbsp;&nbsp;&nbsp;
			 <a href="${pageContext.request.contextPath }/showOrders">订单查询</a>
		</td>
	</tr>

	<tr>
		<td class="listtd">
			<img src="images/miniicon.gif" width="9" height="6" />
				&nbsp;&nbsp;&nbsp;&nbsp; 
			<a onclick="logout()" href="javascript:void(0);">用戶退出</a>
		</td>
	</tr>
</table>