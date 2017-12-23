<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/admin/css/Style.css"
	rel="stylesheet" type="text/css" />
<script language="javascript"
	src="${pageContext.request.contextPath}/admin/js/public.js"></script>
	
<script language="javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	//点击添加按钮 跳转到添加页面
	function addProduct() {
		window.location.href = "${pageContext.request.contextPath}/admin/products/add.jsp";
	}
	
	//点击删除按钮 
	function delProduct(id){
		if(confirm("您确认要删除此项吗？")){
			//要删除此项
			location.href="${pageContext.request.contextPath}/delProduct?id="+id;
		}
	}
	
	//选择全部的表单项的checkbox
	function checkAll(obj){
		$("input[name='delItem']").attr("checked",obj.checked);
	}
	
	//批量删除的按钮
	function delAll(){
		//让表单进行提交
		if(confirm("您确认要删除选中商品吗？")){
			$("#Form2").submit();
		}
	}
	
	$(function(){
		$("#category option[value='${condition.category}']").attr("selected",true);
	});
	
</script>
</HEAD>
<body>
	<br>
	<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/adminProductListCondition" method="post">
		<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>查 询 条 件</strong>
					</td>
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td height="22" align="center" bgColor="#f5fafe" class="ta_01"> 商品编号</td>
								<td class="ta_01" bgColor="#ffffff">
									<input type="text" name="id" size="15" value="${condition.id }" id="Form1_userName" class="bg" />
								</td>
								<td height="22" align="center" bgColor="#f5fafe" class="ta_01"> 类别：</td>
								<td class="ta_01" bgColor="#ffffff">
									<select name="category" id="category">
										<option value="" selected="selected">--选择商品类加--</option>
										<option value="文学">文学</option>
										<option value="生活">生活</option>
										<option value="计算机">计算机</option>
										<option value="外语">外语</option>
										<option value="经营">经营</option>
										<option value="励志">励志</option>
										<option value="社科">社科</option>
										<option value="学术">学术</option>
										<option value="少儿">少儿</option>
										<option value="艺术">艺术</option>
										<option value="原版">原版</option>
										<option value="科技">科技</option>
										<option value="考试">考试</option>
										<option value="生活百科">生活百科</option>
									</select>
								</td>
							</tr>

							<tr>
								<td height="22" align="center" bgColor="#f5fafe" class="ta_01">商品名称：</td>
								<td class="ta_01" bgColor="#ffffff">
									<input type="text" name="name" size="15" value="${condition.name }" id="Form1_userName" class="bg" />
								</td>
								<td height="22" align="center" bgColor="#f5fafe" class="ta_01">价格区间(元)：</td>
								<td class="ta_01" bgColor="#ffffff">
									<input type="text" name="minPrice" size="10" value="${condition.minPrice }" />- 
									<input type="text" name="maxPrice" size="10" value="${condition.maxPrice }" />
								</td>
							</tr>

							<tr>
								<td width="100" height="22" align="center" bgColor="#f5fafe"
									class="ta_01"></td>
								<td class="ta_01" bgColor="#ffffff">
									<font face="宋体" color="red"> &nbsp;</font>
								</td>
								<td align="right" bgColor="#ffffff" class="ta_01"><br>
									<br></td>
								<td align="right" bgColor="#ffffff" class="ta_01">
									<button type="submit" id="search" name="search" value="&#26597;&#35810;" class="button_view">
										&#26597;&#35810;
									</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									<input type="reset" name="reset" value="&#37325;&#32622;" class="button_view" />
								</td>
							</tr>
						</table>
					</td>

				</tr>
				
			</table>
				
		</form>
				
				
				<!-- ---------------------拆成两个form--------------------- -->
		
		<form id="Form2" name="Form2"
				action="${pageContext.request.contextPath}/delAllProduct"
				method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center"
					bgColor="#f5fafe" border="0">
				
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>商品列表</strong>
					</TD>
				</tr>
				<tr>
					<td class="ta_01" align="right">
						<button type="button" id="delAll2" class="button_add" onclick="delAll()">
							批量删除
						</button>
						<button type="button" id="add" name="add" value="&#28155;&#21152;"
							class="button_add" onclick="addProduct()">&#28155;&#21152;
						</button>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">
								<td align="center" width="4%">
									<input type="checkbox" onclick="checkAll(this)">
								</td>
								<td align="center" width="20%">商品编号</td>
								<td align="center" width="18%">商品名称</td>
								<td align="center" width="9%">商品价格</td>
								<td align="center" width="9%">商品数量</td>
								<td width="8%" align="center">商品类别</td>
								<td width="8%" align="center">编辑</td>
								<td width="8%" align="center">删除</td>
							</tr>

							<c:forEach items="${productList }" var="product">
							
								<tr onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE';">
									
									<td style="text-align: center;">
										<input type="checkbox" name="delItem" value="${product.id }">
									</td>
									
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="23">${product.id }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="18%">${product.name }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="8%">${product.price }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="8%">${product.pnum }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center">${product.category}</td>
									<td align="center" style="HEIGHT: 22px" width="7%"><a
										href="${pageContext.request.contextPath}/modifyProductForward?id=${product.id}">
											<img
											src="${pageContext.request.contextPath}/admin/images/i_edit.gif"
											border="0" style="CURSOR: hand"> </a>
									</td>

									<td align="center" style="HEIGHT: 22px" width="7%">
										<a onclick="delProduct('${product.id}')" href="javascript:void(0);">
											<img src="${pageContext.request.contextPath}/admin/images/i_del.gif"
											width="16" height="16" border="0" style="CURSOR: hand">
										</a>
									</td>
								</tr>
							
							</c:forEach>
							
						</table>
					</td>
				</tr>
		</table>
	</form>
</body>
</HTML>

