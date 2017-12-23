<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>电子书城</title>
<link rel="stylesheet" href="css/main.css" type="text/css" />
<!-- 引入jquery -->

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.min.js"></script>

<script type="text/javascript">
	window.onload=function(){
		//获取session中的gender
		var gender = "${user.gender}";
		//获取所有的gender
		var genders = document.getElementsByName("gender");
		
		//判断 哪个radio的value值等于user
		for(var i = 0; i < genders.length; i++){
			if(genders[i].value == gender){
				genders[i].checked = true;
			}
		}
	}

</script>


</head>

<body class="main">
	<jsp:include page="head.jsp" />

	<jsp:include page="menu_search.jsp" />

	<div id="divpagecontent">
		<table width="100%" border="0" cellspacing="0">
			<tr>
				<td width="25%">
					<!-- 引入account_menu.jsp -->
					<%@ include file="account_menu.jsp" %>
				</td>
				<td>
					<div style="text-align:right; margin:5px 10px 5px 0px">
						<a href="index.jsp">首页</a>
							&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;
						<a href="myAccount.jsp">&nbsp;我的帐户</a>
							&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;
							用户信息修改
					</div>


					<table cellspacing="0" class="infocontent">
						<tr>
							<td>
								<form action="${pageContext.request.contextPath }/modifyUser" method="post">
									<input type="hidden" name="id" value="${user.id}">
									<table width="100%" border="0" cellspacing="2" class="upline">
										<tr>
											<td style="text-align:right; width:20%">会员邮箱：</td>
											<td style="width:40%; padding-left:20px">${user.email }</td>
											<td>&nbsp;</td>


										</tr>
										<tr>
											<td style="text-align:right">会员名：</td>
											<td style="padding-left:20px">${user.username }</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td style="text-align:right">修改密码：</td>
											<td>
												<input type="password" name="password" class="textinput" />
											</td>
											<td>
												<font color="#999999">密码设置至少6位，请区分大小写</font>
											</td>
										</tr>
										<tr>
											<td style="text-align:right">重复密码：</td>
											<td>
												<input type="password" class="textinput" />
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td style="text-align:right">性别：</td>
											<td colspan="2">
													&nbsp;&nbsp;
												<input type="radio" name="gender" value="man" checked="checked" /> 男
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="radio" name="gender" value="nv" /> 女
											</td>
										</tr>
										<tr>
											<td style="text-align:right">联系方式：</td>
											<td colspan="2">
												<input name="telephone" type="text" value="${user.telephone }" class="textinput" />
											</td>
										</tr>

										<tr>
											<td style="text-align:right">&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
									</table>



									<p style="text-align:center">

										<input type="image" src="images/botton_gif_025.gif" border="0">

									</p>
									<p style="text-align:center">&nbsp;</p>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>



	<jsp:include page="foot.jsp" />


</body>
</html>
