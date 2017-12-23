<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 引入jstl标签库 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>电子书城</title>
<link rel="stylesheet" href="css/main.css" type="text/css" />

<script type="text/javascript">
	function updateProfromCart(id,flag,buyNum,pnum){//注意传值顺序
	if(flag=="add"){
			//跳转之前 要判断数量是否已将超过库存
			//判断当前购买的数量与库存进行对比
			if(buyNum<pnum){
				location.href="${pageContext.request.contextPath}/updateCartNum?id="+id+"&flag="+flag;
			}else{
				alert("不能超过库存数量！");
			}
		}else{
			//跳转之前 要判断数量是否大于1
			if(buyNum>1){
				location.href="${pageContext.request.contextPath}/updateCartNum?id="+id+"&flag="+flag;
			}else{
				delProFromCart(id);
			}
		}	
	}
	//传如id
	function delProFromCart(id){
		if(confirm("您确认要删除吗？")){
			location.href="${pageContext.request.contextPath}/delProFromCart?id="+id;
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

				<td><div style="text-align:right; margin:5px 10px 5px 0px">
						<a href="index.html">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;购物车
					</div>

					<table cellspacing="0" class="infocontent">
						<tr>
							<td><img src="ad/page_ad.jpg" width="666" height="89" />
								<table width="100%" border="0" cellspacing="0">
									<tr>
										<td><img src="images/buy1.gif" width="635" height="38" />
										</td>
									</tr>
									<tr>
										<td>
											<table cellspacing="1" class="carttable">
												<tr>
													<td width="10%">序号</td>
													<td width="30%">商品名称</td>
													<td width="10%">价格</td>
													<td width="20%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数量</td>
													<td width="10%">库存</td>
													<td width="10%">小计</td>
													<td width="10%">取消</td>
												</tr>
											</table> 
												<table width="100%" border="0" cellspacing="0">
													<!-- 定义变量并、初始值 -->
													<c:set var="sum" value="0.0" scope="page"></c:set>
													<!-- 加入循环 -->
													<c:forEach items="${cart}" var="entry" varStatus="vs">
														<tr>
															<td width="10%">${vs.count}</td>
															<td width="30%">${entry.key.name }</td>
	
															<td width="10%">${entry.key.price }</td>
															<td width="20%">
																<!-- 减少按钮 -->
																<!-- 给按钮绑定事件--同时定义表示为derc -->
																<input onclick="updateProfromCart('${entry.key.id }','decr',${entry.value },${entry.key.pnum})"  type="button" value='-' style="width:20px">
																<input name="text" type="text"  value=${entry.value } style="width:40px;text-align:center" />
																<!-- 添加按钮 -->
																<!-- 给按钮绑定事件--同时定义表示为add -->
																<input onclick="updateProfromCart('${entry.key.id }','add',${entry.value },${entry.key.pnum})" type="button" value='+' style="width:21px">
															</td>
															<td width="10%">${entry.key.pnum }</td>
															<td width="10%">${entry.key.price* entry.value}</td>
	
															<td width="10%">
																<a onclick="delProFromCart('${entry.key.id }')" href="javascript:void(0);" style="color:#FF0000; font-weight:bold">X</a>
															</td>
														</tr>
														<!-- 没循环一次，小计相加 -->
														<c:set var="sum" value="${sum+entry.key.price*entry.value }" scope="page"></c:set>
													</c:forEach>
												</table>
											<table cellspacing="1" class="carttable">
												<tr>
													<td style="text-align:right; padding-right:40px;"><font
														style="color:#FF6600; font-weight:bold">合计：&nbsp;&nbsp;${sum }元</font>
													</td>
												</tr>
											</table>
											<div style="text-align:right; margin-top:10px">
												<a href="productList">
													<img src="images/gwc_jx.gif" border="0" /> 
												</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="${pageContext.request.contextPath }/orderForward">
													<img src="images/gwc_buy.gif" border="0" /> 
												</a>
											</div>
										</td>
									</tr>
								</table>
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
