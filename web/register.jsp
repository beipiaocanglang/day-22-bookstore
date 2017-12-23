<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>bookStore注册页面</title>
<%--导入css --%>
<link rel="stylesheet" href="css/main.css" type="text/css" />
<!-- 使错误信息的字体变红 -->
<style type="text/css">
	.error{
		color:red;
	}
</style>
<!-- 导入jquery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>

<!-- 导入jquery.validate -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

<!-- 使用jquery.validate插件进行表单验证 -->
<script type="text/javascript">
$(function(){
	$("#regForm").validate({
		//校验规则
		rules:{
			"email":{
				//必须写
				"required":true,		
				"email":true
			},
			"username":{
				"required":true,
				//规定长度
				"minlength":3,
				"maxlength":8
			},
			"password":{
				"required":true,
				//规定长度
				"minlength":3,
				"maxlength":13
			},
			"repassword":{
				"required":true,
				//规定长度
				"minlength":3,
				"maxlength":13,
				"equalTo":"#pass"
			},
			"gender":{
				"required":true
			},
			"telephone":{
				"required":true,
				"minlength":10,
				"maxlength":11,
			},
			"introduce":{
				"required":true,
				//规定长度
				"minlength":5,
				"maxlength":10
			}
		},
		//错误信息提示内容
		messages:{
			"email":{
				"required":"邮箱必须填写",		
				"email":"邮箱格式不正确"
			},
			"username":{
				"required":"用户名必须填写",
				//规定长度
				"minlength":"至少3位",
				"maxlength":"最多8位"
			},
			"password":{
				"required":"密码必须输入",
				//规定长度
				"minlength":"密码至少3位",
				"maxlength":"密码最多13位"
			},
			"repassword":{
				"required":"请重新确认密码",
				//规定长度
				"minlength":"密码至少3位",
				"maxlength":"密码最多13位",
				"equalTo":"密码不一致"
			},
			//"gender":{
			//"required":"请选择性别"
			//}
			"telephone":{
				"required":"手机号必须填写(11位)",
				"minlength":"至少10位",
				"maxlength":"最多11位"
			},
			"introduce":{
				"required":"请填写不少于5个字的自我介绍",
				//规定长度
				"minlength":"自我介绍至少5位",
				"maxlength":"自我介绍最多10位"
			}
		}
	
	});

});
</script>

<script type="text/javascript">
	function changeImage() {
		document.getElementById("img").src = "${pageContext.request.contextPath}/checkCode?time="
				+ new Date().getTime();
	}
</script>
</head>


<body class="main">
	<%@include file="head.jsp"%>
	<%--导入头 --%>
	<%@include file="menu_search.jsp"%><%--导入导航条与搜索 --%>

	<div id="divcontent">
		<form id="regForm" action="${pageContext.request.contextPath}/register" method="post">
			<table width="850px" border="0" cellspacing="0">
				<tr>
					<td style="padding:30px">
						<h1>新会员注册</h1>
						
						<table width="70%" border="0" cellspacing="2" class="upline">
							<tr>
								<td style="text-align:right; width:20%">会员邮箱：</td>
								<td style="width:40%">
								<input type="text" class="textinput"
									name="email" /></td>
							</tr>
							<tr>
								<td style="text-align:right">会员名：</td>
								<td>
									<input type="text" class="textinput" name="username" />
								</td>
							</tr>
							<tr>
								<td style="text-align:right">密码：</td>
								<td><input id="pass" type="password" class="textinput"
									name="password" /></td>
							</tr>
							<tr>
								<td style="text-align:right">重复密码：</td>
								<td><input type="password" class="textinput"
									name="repassword" /></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td style="text-align:right">性别：</td>
								<td colspan="2">
									&nbsp;&nbsp;
									<input type="radio" name="gender" value="男" /> 男
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="gender" value="女" /> 女
									<label class="error" for="gender" style="display: none">请选择性别</label>
								</td>
							</tr>
							<tr>
								<td style="text-align:right">联系电话：</td>
								<td colspan="2">
									<input type="text" class="textinput" style="width:350px" name="telephone" />
								</td>
							</tr>
							<tr>
								<td style="text-align:right">个人介绍：</td>
								<td colspan="2">
									<textarea class="textarea" name="introduce"></textarea>
								</td>
							</tr>

						</table>



						<h1>注册校验</h1>
						<table width="80%" border="0" cellspacing="2" class="upline">
							<tr>
								<td style="text-align:right; width:20%">输入校验码：</td>
								<td style="width:50%">
									<input type="text" class="textinput" name="checkCode"/>
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td style="text-align:right;width:20%;">&nbsp;</td>
								<td colspan="2" style="width:50%">
								<img src="${pageContext.request.contextPath}/checkCode" width="180"
									height="30" class="textinput" style="height:30px;" id="img" />
									&nbsp;&nbsp;
									<a href="javascript:void(0);" onclick="changeImage()">看不清换一张</a>
								</td>
							</tr>
						</table>



						<table width="70%" border="0" cellspacing="0">
							<tr>
								<td style="padding-top:20px; text-align:center"><input
									type="image" src="images/signup.gif" name="submit" border="0">
								</td>
							</tr>
						</table></td>
				</tr>
			</table>
		</form>
	</div>



	<div id="divfoot">
		<table width="100%" border="0" cellspacing="0">
			<tr>
				<td rowspan="2" style="width:10%"><img
					src="images/bottomlogo.gif" width="195" height="50"
					style="margin-left:175px" /></td>
				<td style="padding-top:5px; padding-left:50px"><a href="#"><font
						color="#747556"><b>CONTACT US</b> </font> </a></td>
			</tr>
			<tr>
				<td style="padding-left:50px"><font color="#CCCCCC"><b>COPYRIGHT
							2008 &copy; eShop All Rights RESERVED.</b> </font></td>
			</tr>
		</table>
	</div>


</body>
</html>
