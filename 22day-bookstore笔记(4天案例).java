前台
后台

项目开发的一般流程
	1、确定业务需求
	2、编写《需求说明书》--不涉及代码和代码中的逻辑
	3、编写《概要设计说明书》--技术选型（架构的确定）--数据库（实体）的模型	
	4、编写《详细设计说明书》--设计详细的的逻辑说明（接口说明书）--伪代码的编写（文字的描述）
	5、编码 coding---单元测 ---联测
	6、测试组进行测试----《测试说明书》--包括项目的bug、建议修改易用性
	7、上线测试（公测）---参与人员：开发人员、测试组的人员、真正的用户
	8、后期的维护、二次开发
敏捷开发--迭代开发（有迭代周期）	



项目开发
	一、前台
		1、准备工作
			创建包结构
				cn.itcast
					dao---操作数据库
					service---做一些复杂的业务逻辑
					domain---实体包
					utils---工具包
					vo---值对象包
					web---与服务端进行交互的包
						filter---过滤包
						listener---监听包
						servlet---与客户端进行交互
			webroot中导入静态页面
			
			web-inf中导入一次性验证码的配置文件
				new_words.txt
			在servlet包下导入一次性验证码的java类
				CheckCodeServlet.java
			导入jar包
				js中：
					jquery-1.8.3.min.js
					jquery.validate.min.js
				lib中：
					c3p0-0.9.1.2.jar
					commons-beanutils-1.8.3.jar
					commons-dbutils-1.4.jar
					commons-fileupload-1.2.1.jar
					commons-io-1.4.jar
					commons-logging-1.1.1.jar
					mail.jar-----发送邮件的
					mysql-connector-java-5.0.8-bin.jar
				src根目录下导入配置文件
					c3p0-config.xml
			在utils包中导入工具包
				CommonsUtils.java
				DataSourceUtils.java
				MailUtils.java
			在domain包下导入实体
				Order.java
				OrderItem.java
				Product.java
				User.java
			创建数据库
				导入即可bookstore.sql
				（创建bookstore数据表--右键运行sql文件--选择路径--导入）
		2、编码工作：
				在register.jsp文件中操作
			(1)<!-- 导入jquery -->
				<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
			(2)<!-- 导入jquery-validate -->
				<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
			(3)<!-- 使用validate插件进行表单校验 -->
				<script type="text/javascript">
				$(function(){
					$("#regForm").validate({
						//校验规则
						rules:{
							"email":{
								"required":true,
								"email":true
							},
							"username":{
								"required":true,
								"minlength":3,
								"maxlength":8
							},
							
							"gender":{
								"required":true
							}
						},
						//错误信息
						messages:{
							"email":{
								"required":"邮箱必须填写",
								"email":"邮箱格式不正确"
							},
							"username":{
								"required":"用户名必须填写",
								"minlength":"用户名至少3位",
								"maxlength":"用户名最多8位"
							}
						}
					});
				});
				</script>
			(4)将一次性验证码与注册表单进行关联
				src="${pageContext.request.contextPath}/checkCode" width="180"height="30" class="textinput" style="height:30px;" id="img" /><!--checkCode是一次性验证码servlet的虚拟路径 -->
		3、注册、验证码和发送邮箱的编码
			(1)获取客户端提交的验证码
				String checkcode_client = request.getParameter("checkCode");
				//获取session中的验证码
				String checkcode_session = (String) session.getAttribute("checkcode_session");
			(2)收集表单提交数据--封装实体
					//创建map集合
					Map<String, String[]> properties = request.getParameterMap();
					//创建user对象
					User user = new User();
					//调用dbutils
					try {
						BeanUtils.populate(user, properties);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//封装实体
					//activeCode--激活码---uuid
					String activeCode = CommonsUtils.getUUID();
					//封装激活码
					user.setActiveCode(activeCode);
					//封装激活码状态
					user.setState(0);
					//封装用户的级别--默认是普通级别
					user.setRole("普通用户");
					//封装注册时间
					user.setRegistTime(new Date());
			(3)dao层的固定操作
				QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
				String sql="insert into user values(?,?,?,?,?,?,?,?,?,?,?)";
				int update = runner.update(sql, null,user.getUsername(),user.getPassword(),user.getGender(),user.getEmail(),
				user.getTelephone(),user.getIntroduce(),user.getActiveCode(),user.getState(),user.getRole(),user.getRegistTime());
				return update;
			(4)一次性验证码
				<script type="text/javascript">
					function changeImage() {
						document.getElementById("img").src = "${pageContext.request.contextPath}/checkCode?
							time="+new Date().getTime();
					}
				</script>

				<td colspan="2" style="width:50%">
					<img src="${pageContext.request.contextPath}/checkCode" width="180"height="30" class="textinput" 
						style="height:30px;" id="img" />
					<a href="javascript:void(0);" onclick="changeImage()">看不清换一张</a>
				</td>
				路径：day22天的项目
			(5)隐藏
				<label class="error" for="gender" style="display: none">请选择性别</label>
			(6)发送邮箱
				String url="http://localhost"+request.getContextPath()+"/activeServlet?activeCode="+activeCode;
				String emailMsg = "<h2>恭喜您注册成功，请点击下面的激活链接进行激活<a href='"+url+"'>"+url+"</a></h2>";
				try {
					MailUtils.sendMail(user.getEmail(), emailMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//邮箱的工具类---day-22--MailUtils.java（具体详情待解）
		
		第一、前台展示页面
			创建product.java实体类--字段与数据库的字段一样---生成get/set方法
			创建数据库--product.sql--插入数据
			1、java代码的操作
				ProductListServlet.java
					//直接将请求发送service层，获取全部数据
					UserService service = new UserService();
					List<Product> productList=service.findAllProduct();
					//存到session域中
					request.setAttribute("productList", productList);
					//转发到product_list.jsp
					request.getRequestDispatcher("/product_list.jsp").forward(request, response);
				UserService.java
					UserDao dao = new UserDao();
					List<Product> productList = dao.findAllProduct();
					return productList;
				UserDao.java
					QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
					String sql = "select * from products";
					List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class));
					return productList;
			2、jsp页面的更改
				product_List.jsp---前台页面展示jsp
					引入jstl标签：
						<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
					<c:forEach items="${productList }" var="product">
						<div style="float:left; width:25%;">
						
							<div class="divlisttitle">
								<a href="${pageContext.request.contextPath }/productInfo?id=${product.id }">书名:${product.name}<br />售价:${product.price } </a>
							</div>
						</div>
					</c:forEach>
					<div style="clear: both"></div>
		第二：跳转到商品页面
			1、java代码操作
				(1) ProductInfoServlet.java
					//接收客户端提交的数据中的id
					String id = request.getParameter("id");
					//直接传递到service层要数据
					
					UserService service = new UserService();
					Product pro=null;
					try {
						pro = service.findId(id);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					//将数据放到session域中
					request.setAttribute("pro",pro);
					//转发到product_info.jsp页面
					request.getRequestDispatcher("/product_info.jsp").forward(request, response);
				
				(2) UserService.java
					//没有特殊业务--继续往下传
					UserDao dao = new UserDao();
					Product pro = dao.findProductById(id);
					return pro;
				
				(3) UserDao.java
					QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
					//使用带参数的方法
					String sql= "select * from products where id=?";
					return runner.query(sql, new BeanHandler<Product>(Product.class), id);
				
			2、jsp页面更改
				(1) product_List.jsp---前台页面展示jsp
					<div class="divbookpic">
						<p>
							<a href="${pageContext.request.contextPath }/productInfo?id=${product.id }">
									<img src="${product.imgurl }" width="115" height="129" border="0" /> 
							</a>
						</p>
					</div>
				(2) 商品详细页面的更改---product_info.jsp
					A: 图片
						<div class="divbookcover">
							<p>
								<img src="${pageContext.request.contextPath }/${pro.imgurl }"
									width="213" height="269" border="0" />
							</p>
						</div>
					B: 详细信息
						<td style="padding:20px 5px 5px 5px">
							<img src="images/miniicon3.gif" width="16" height="13" />
								<font class="bookname">&nbsp;&nbsp;${pro.name}</font>
							<hr />售价：<font color="#FF0000">￥：${pro.price}元</font>
							<hr /> 类别：${pro.category }

							<hr />
							<p>
								<b>内容简介：</b>
							</p>${pro.description }
						</td>
						
		第三：加入购物车
			1、jsp页面更改
				(1) 商品详细页面的更改---product_info.jsp
					<div style="text-align:center; margin-top:25px">
						<a href="${pageContext.request.contextPath }/addProductToCart?id=${pro.id}">
							<img src="images/buybutton.gif" border="0" /> 
						</a>
					</div>
				(2) 购物车页面的更改---cart.jsp
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
								<input type="button" value='-' style="width:20px">
									<input name="text" type="text"  value=${entry.value } style="width:40px;text-align:center" />
									<input type="button" value='+' style="width:20px">
								</td>
								<td width="10%">${entry.key.pnum }</td>
								<td width="10%">${entry.key.price* entry.value}</td>

								<td width="10%">
									<a href="#" style="color:#FF0000; font-weight:bold">X</a>
								</td>
							</tr>
							<!-- 没循环一次，小计相加 -->
							<c:set var="sum" value="${sum+entry.key.price*entry.value }" scope="page"></c:set>
						</c:forEach>
						
					</table
					<table cellspacing="1" class="carttable">
						<tr>
							<td style="text-align:right; padding-right:40px;">
								<font style="color:#FF6600; font-weight:bold">合计：&nbsp;&nbsp;${sum }元</font>
							</td>
						</tr>
					</table>
				(3) 购物车页面的更改---cart.jsp---继续购物按钮
					<div style="text-align:right; margin-top:10px">
						<a href="productList">
							<img src="images/gwc_jx.gif" border="0" /> 
						</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="order.jsp">
							<img src="images/gwc_buy.gif" border="0" /> 
						</a>
					</div>
			2、java代码操作
				(1) AddProductToCartServlet.java
					// 接收客户端传过来的id
					String id = request.getParameter("id");
					// 传到service
					UserService service = new UserService();
					Product product=null;
					try {
						product = service.findProductById(id);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					// 获得session域
					HttpSession session = request.getSession();
					// 获得session域中 的购物车
					Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
					// 判断购物车是否存在
					if (cart == null) {
						// 不存在--创建
						cart = new HashMap<Product, Integer>();
					}
					// 将商品放到购物车中--同时判断购物车中是否存在该商品
					// 调用hashMap的方法public boolean containsKey(Object
					// key)如果此映射包含对于指定键的映射关系，则返回 true。
					if(cart.containsKey(product)){
						//存在--取出数据并加一
						int buyNum = cart.get(product);
						//数量加一
						buyNum++;
						//在放回map集合中
						cart.put(product, buyNum);
					}else{
						//不存在--直接添加
						cart.put(product, 1);
					}
					//再将购物车放到session域中
					session.setAttribute("cart", cart);
					//跳转到购物车页面
					response.sendRedirect(request.getContextPath()+"/cart.jsp");
					
				(2) UserService.java
					UserDao dao = new UserDao();
					Product pro = dao.toCart(id);
					return pro;
					
				(3) UserDao.java
					QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
					String sql = "select * from products where id=?";
					return runner.query(sql , new BeanHandler<Product>(Product.class), id);
		
		第四：购物车中的加、减按钮及删除按钮
			1、java代码操作
				(1)加减按钮
					UpdateCartNumServlet.java
						//接收传过来的id
						String id = request.getParameter("id");
						//接收传过来的标识
						String flag = request.getParameter("flag");
						//获得session域
						HttpSession session = request.getSession();
						//获得session域中的购物车
						Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
						
						//传递到service层
						UserService service = new UserService();
						Product pro=null;
						try {
							pro = service.addId(id);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						Integer buyNum = cart.get(pro);
						//判断表示的作用
						if("add".equals(flag)){
							//如果是添加
							buyNum++;
						}else{
							//如果是减少
							buyNum--;
						}
						//放回map集合
						cart.put(pro, buyNum);
						
						//放回session域中
						request.setAttribute("cart", cart);
						//跳转到购物车页面
						response.sendRedirect(request.getContextPath()+"/cart.jsp");
						
					UserService.java
						UserDao dao = new UserDao();
						Product pro = dao.addToCart(id);
						return pro;
					UserDao.java
						QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
						String sql = "select * from products where id=?";
						return runner.query(sql , new BeanHandler<Product>(Product.class), id);
				(2)删除按钮 
					DelProFromCartServlet.java---没有其他层
						//获取要删除的id
						String id = request.getParameter("id");
						//获取session域
						HttpSession session = request.getSession();
						Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
						
						Product pro = new Product();
						pro.setId(id);
						cart.remove(pro);
						
						//将购物车放回
						session.setAttribute("cart", cart);
						
						//调回购物车页面
						response.sendRedirect(request.getContextPath()+"/cart.jsp");
			2、jsp代码操作
					添加或减少分别添加标识flag：add / derc	
					点击按钮是传递id、flag、购买的数量、库存
					在加、减按钮标签中添加鼠标点击事件oncelick
					在<script>中创方法--使用提交 location.href=“”
					再去创建响应的servlet
				(1) 购物车页面的更改---cart.jsp
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
					
				(2) 购物车页面的更改---cart.jsp--加减按钮
					<!-- 减少按钮 -->
					<!-- 给按钮绑定事件--同时定义表示为derc -->
					<input onclick="updateProfromCart('${entry.key.id }','decr',${entry.value },${entry.key.pnum})"  type="button" value='-' style="width:20px">
					<input name="text" type="text"  value=${entry.value } style="width:40px;text-align:center" />
					<!-- 添加按钮 -->
					<!-- 给按钮绑定事件--同时定义表示为add -->
					<input onclick="updateProfromCart('${entry.key.id }','add',${entry.value },${entry.key.pnum})" type="button" value='+' style="width:21px">
				(3) 购物车页面的更改---cart.jsp---删除按钮
					<td width="10%">
						<a onclick="delProFromCart('${entry.key.id }')" href="javascript:void(0);" style="color:#FF0000; font-weight:bold">X</a>
					</td>
		第五：结账按钮跳转到登录页面
			1、java代码操作
				OrderForwardServlet.java---没有其他层
					(1) 用于校验用户是否存在--OrderForwardServlet.java
						//获取session域
						HttpSession session = request.getSession();
						//获取session域中的user对象
						User user = (User) session.getAttribute("user");
						//判断域中的user对象是否存在
						if(user!=null){
							//存在--直接跳转到结算页面
							request.getRequestDispatcher("/order.jsp").forward(request, response);
						}else{
							//不存在---跳转到登录页面
							response.sendRedirect(request.getContextPath()+"/login.jsp");
						}
				
			2、jsp代码操作
				(1) 购物车页面的更改---cart.jsp---结算按钮
					<a href="${pageContext.request.contextPath }/orderForward">
						<img src="images/gwc_buy.gif" border="0" /> 
					</a>
				(2) 登录页面--login.jsp---登录按钮
					A: <form action="${pageContext.request.contextPath }/login" method="post"> 
					B: <td style="text-align:center;padding-top:20px;"><font
							color="#ff0000">${loginInfo }</font>//	密码输入错误时的提示信息
						</td>
		第六：登录页面
			1、java代码操作
				(1)LoginServlet.java
					//获取提交的用户名和密码
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					//将数据传到service层
					UserService service = new UserService();
					User user=null;
					try {
						user = service.passwor(username,password);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					//判断user是否存在
					HttpSession session = request.getSession();
					if(user!=null){
						//密码正确
						//将user放回session域中
			*****		session.setAttribute("user", user);
						//跳转
						response.sendRedirect(request.getContextPath()+"/order.jsp");
					}else{
						//密码错误
						//向用户输出提示信息
						request.setAttribute("loginInfo", "对不起！您输入的用户名或密码错误，请重新输入");
						//失败后再跳转到登录页面
						request.getRequestDispatcher("/login.jsp").forward(request, response);
					}
				(2) UserDao.java
					UserDao dao = new UserDao();
					return dao.pasword(username, password);
				(3) UserDao.java
					QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
					String sql="select * from user where username=? and password=?";
					return runner.query(sql, new BeanHandler<User>(User.class), username,password);
			2、jsp代码操作
				登录页面--login.jsp---登录按钮
				(1) <form action="${pageContext.request.contextPath }/login" method="post"> 
				(2) <td style="text-align:center;padding-top:20px;"><font
						color="#ff0000">${loginInfo }</font>//	密码输入错误时的提示信息
					</td>
				(3)登录成功---在购物车旁边显示--欢迎你，xxx
					<!-- 判断user对象步为空 -->
					<c:if test="${!empty user }">
						<span style="color:red;font-size:15px">欢迎您，${user.username }</span>
					</c:if>
		第七：结账页面
			1、java代码操作
				
			2、jsp代码操作--order.jsp
				(1) 您好：dsd先生！欢迎您来到商城结算中心
					<tr>
						<td><img src="images/buy2.gif" width="635" height="38" />
							<p>您好：${user.username }先生！欢迎您来到商城结算中心</p></td>
					</tr>
				(2) 显示商品的详细信息
						<table width="100%" border="0" cellspacing="0">
							<!-- 定义变量 -->
							<c:set var="sum" value="0.0" scope="page"></c:set>
							<!-- 循环显示 -->
							<c:forEach items="${cart}" var="entry" varStatus="vs">
							<tr>
								<td width="10%">${vs.count }</td>
								<td width="40%">${entry.key.name }</td>
								<td width="10%">${entry.key.price }</td>
								<td width="10%">${entry.key.category }</td>
								<td width="10%">${entry.value}</td>
								<td width="10%">${entry.key.price*entry.value }</td>
							</tr>
							<!-- 每训话完一次小计加加 -->
							<c:set var="sum" value="${sum+entry.key.price*entry.value }" scope="page"></c:set>
							</c:forEach>
						</table>
						<table cellspacing="1" class="carttable">
							<tr>
								<td style="text-align:right; padding-right:40px;">
									<font style="color:#FF0000">合计：&nbsp;&nbsp;${sum }元</font>
								</td>
							</tr>
						</table>
		第八：提交订单--到银行付款页面
			1、java代码页面
				OrderServlet.java---封装实体
					//获得session域 
					HttpSession session = request.getSession();
					//servlet的主要i作用是分装order对象
					Order order = new Order();
					
					//封装order中的字段
					// private String oid;---uuid进行封装
						String oid = CommonsUtils.getUUID();
						order.setOid(oid);
					// private double money;//订单的合计---赋初始值0
						double money = 0;
					// private String receiverAddress;//收获地址
						//获取客户端提交的地址
						String receiverAddress = request.getParameter("receiverAddress");
						order.setReceiverAddress(receiverAddress);
					// private String receiverName;//收货人
						//获取客户端提交的收件人名字
						String receiverName = request.getParameter("receiverName");
						order.setReceiverName(receiverName);
					// private String receiverPhone;//收货人电话
						//获取客户端提交的电话
						String receiverPhone = request.getParameter("receiverPhone");
						order.setReceiverPhone(receiverPhone);
					// private int paystate;//支付状态
						order.setPaystate(0);
					// private Date ordertime;//订单时间
						order.setOrdertime(new Date());
					// private User user;
						//从域中获取user对象
						User user = (User) session.getAttribute("user");
						//封装到order中
						order.setUser(user);
					// private List<OrderItem> orderItemList;
						//从session域中获取购物车
						Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
						//封装orderItemList
						List<OrderItem> orderItemList = new ArrayList<OrderItem>();
						//将商品信息从cart中取出封装的orderItemList中
						//for循环
						for(Map.Entry<Product, Integer> entry : cart.entrySet()){
							//创建OrderItem对象
							OrderItem item = new OrderItem();
							//封装OrderItem实体
							//private Order order;
							item.setOrder(order);
							//private Product product;
							item.setProduct(entry.getKey());
							//private int buyNum;
							item.setBuyNum(entry.getValue());
							//private double itemMoney;
							item.setItemMoney(entry.getKey().getPrice()*entry.getValue());
							//添加到list集合
							orderItemList.add(item);
							money+=item.getItemMoney();
						}
						//封装setOrderItemList
						order.setOrderItemList(orderItemList);
						order.setMoney(money);
						
						//将order对象传递到service层
						OrderService service = new OrderService();
						service.submitOrder(order);
						
						//跳转到支付页面
						response.sendRedirect(request.getContextPath()+"/pay.jsp");
				UserService.java
					//传递到service层
					OrderDao dao = new OrderDao();
					//定义标识
					boolean flag = true;
					//1、开启事务
					try {
						DataSourceUtils.startTransaction();
						//2、向order表插入数据的方法
						int orderRes = dao.orders(order);
						//3、向orderitem表插入数据的方法
						int[] orderItemRes = dao.orderItem(order);
						//4、更新products表的数据的方法
						int[] pnumRes = dao.updatePnum(order);
						
						if(orderRes<1){
							flag = false;
						}
						for(int i : orderItemRes){
							if(i<1){
								flag = false;
								break;
							}
						}
						for(int i : pnumRes){
							if(i<1){
								flag = false;
								break;
							}
						}
						if(!flag){
							//回滚
							DataSourceUtils.rollback();
						}
						
					} catch (SQLException e) {
						//回滚
						try {
							DataSourceUtils.rollback();
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						//提交
						try {
							DataSourceUtils.commitAndRelease();
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				UserDao.java
					//向order表中添加数据
					public int orders(Order order) throws Exception {
						QueryRunner runner = new QueryRunner();
						Connection conn = DataSourceUtils.getConnection();
						String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
						return runner.update(conn, sql, 
								order.getOid(),order.getMoney(),order.getReceiverAddress(),order.getReceiverName(),
								order.getReceiverPhone(),order.getPaystate(),order.getOrdertime(),order.getUser().getId()
								);
					}
					//向orderitem表插入数据的方法
					public int[] orderItem(Order order) throws Exception {
						QueryRunner runner = new QueryRunner();
						Connection conn = DataSourceUtils.getConnectionByThread();
						String sql = "insert into orderitem values(?,?,?)";
						List<OrderItem> orderItemList = order.getOrderItemList();
						Object[][] params = new Object[orderItemList.size()][3];
						for(int i=0;i<orderItemList.size();i++){
							OrderItem orderItem = orderItemList.get(i);
							params[i][0] = orderItem.getOrder().getOid();
							params[i][1] = orderItem.getProduct().getId();
							params[i][2] = orderItem.getBuyNum();
							
						}
						int[] batch = runner.batch(conn, sql, params);
						
						return batch;
					}
					//更新products表的数据的方法
					public int[] updatePnum(Order order) throws Exception {
						QueryRunner runner = new QueryRunner();
						Connection conn = DataSourceUtils.getConnectionByThread();
						String sql = "update products set pnum=pnum-? where id=?";
						List<OrderItem> orderItemList = order.getOrderItemList();
						Object[][] params = new Object[orderItemList.size()][2];
						for(int i=0;i<orderItemList.size();i++){
							OrderItem orderItem = orderItemList.get(i);
							params[i][0] = orderItem.getBuyNum();
							params[i][1] = orderItem.getProduct().getId();
						}
						int[] batch = runner.batch(conn, sql, params);
						return batch;
			
			2、jsp页面
				(1) 更改提交订单按钮--order.jsp
					<p style="text-align:right">
					<!-- 自插入a标签 --给from表单添加id和地址-->
						A:  <form id="orderForm" action="${pageContext.request.contextPath }/order" method="post">
						B:  
							<a href="javascript:void(0);" onclick="submitOrder()">
								<img src="images/gif53_029.gif" width="204" height="51" border="0" />
							</a>
					</p>
				(2) 调点击事件的方法
					<script type="text/javascript">
						function submitOrder(){
							document.getElementById("orderForm").submit();
						}

					</script>
					
		
		第十：我的账户按钮--未登录跳转到登录页面---已登录跳转到用户详情页面
			1、java代码操作
				MyAccountServlet.java
					//主要作用是判断用户是否已登录
					
					//获取session
					HttpSession session = request.getSession();
					//获取session中的user
					User user = (User) session.getAttribute("user");
					
					//判断user是否存在
					if(user!=null){
						//用户存在
						response.sendRedirect(request.getContextPath()+"/myAccount.jsp");
					}else{
						//用户不存在
						response.sendRedirect(request.getContextPath()+"/login.jsp");
						
					}
			
			2、jsp代码操作
				(1)head.jsp-更改连接地址
					<a href="${pageContext.request.contextPath }/myAccount">我的帐户</a> | 

		第十一：我的账户的详细信息
			1、java代码操作
				(1) ModifyUserServlet.java
					HttpSession session = request.getSession();
					//获取客户端提交单额数据
					Map<String,String[]> parameterMap = request.getParameterMap();
					//创建实体
					User user = new User();
					
					 try {
						BeanUtils.populate(user, parameterMap);
					} catch (Exception e) {
						e.printStackTrace();
					}
					 
					 //传递到service
					 UserService service = new UserService();
					 boolean isModifySuccess=false;
					 try {
						isModifySuccess = service.modifyUser(user);
					} catch (SQLException e) {
						e.printStackTrace();
					}  
					 if(isModifySuccess){
						//修改成功---同时也更新一下session中 数据
						User user_session =  service.findUserById(user.getId()+"");
						 //放回域中
						session.setAttribute("user", user_session);
						
						//将session删掉
						session.invalidate();
						//跳转到modifyuserinfo.jsp页面
						response.sendRedirect(request.getContextPath()+"/modifyUserInfoSuccess.jsp");
					 }else{
						 response.sendRedirect(request.getContextPath()+"/modifyuserinfo.jsp");
						 
					 }
				(2)UserService.java
					//修改用户信息的方法
					public boolean modifyUser(User user) throws SQLException {
						UserDao dao = new UserDao();
						int row =dao.modifyUser(user);
						return row>0?true:false;
					}
					//根据id同时更改session中的数据
					public User findUserById(String id) throws SQLException {
						UserDao dao = new UserDao();
						return dao.findUserById(id);
					}
				
				(3)UserDao.java
					//修改用户信息
					public int modifyUser(User user) throws SQLException {
						QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
						String sql = "update user set password=?,gender=?,telephone=? where id=?";
						return runner.update(sql, user.getPassword(),user.getGender(),user.getTelephone(),user.getId());
					}
					//修改session中的user
					public User findUserById(String id) throws SQLException {
						QueryRunner runner =  new QueryRunner(DataSourceUtils.getDataSource());
						String sql="select * from user where id=?"; 
						return runner.query(sql, new BeanHandler<User>(User.class), id);
					}
			2、jap代码操作
				modifyuserinfo.jsp---性别的操作
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
		第十二：退出登录
			1、java代码操作
				只有servlet删掉session就行--LogoutServlet.java
					//将session删掉
					request.getSession().invalidate();
					
					//跳转到首页
					response.sendRedirect(request.getContextPath()+"/index.jsp");

			2、jsp代码操作
				(1) 将myAccount.jsp---和---modifyuserinfo.jsp中的公共部分提取出来---account_menu.jsp
					代码如下：	
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
								<td class="listtd"><img src="images/miniicon.gif" width="9"
									height="6" />&nbsp;&nbsp;&nbsp;&nbsp; <a href="orderlist.jsp">订单查询</a>
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
					
				(2) 分别在 myAccount.jsp---和---modifyuserinfo.jsp中引入提取出来的jsp文件
					<td width="25%">
						<!-- 引入account_menu.jsp -->
						<%@ include file="account_menu.jsp" %>
					</td>
		
		第十三：前台分页
			1、java代码操作
				创建PageBean实体类
					创建实体对象的相应字段
						//currentPage; //当前页
						//currentCount; //当前页显示的条数
						//totalCount; //总条数
						//totalPage; //总页数
						//productList;//当前页显示的商品的集合
			
			2、jsp代码操作
				product_List.jsp---修改 <c:forEcth>--及<div>
					<div style="float:left; width:25%;"> </div>
					
		第十四：分页按钮操作
			1、jsp代码操作
				product_List.jsp
				
		第十五：我的订单---账户查询
			1、java代码操作
				(1) 动态显示订单查询的数据
					ShowOrdersServlet.java
						//获取session
						HttpSession session = request.getSession();
						//获取session中的user
						User user = (User) session.getAttribute("user");
						//获取user中的id
						int id = user.getId();
						
						//传到service层
						UserService service = new UserService();
						List<Order> showOrder=null;
						try {
							showOrder = service.showOrder(id);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						request.setAttribute("showOrder",showOrder);
						request.setAttribute("showsize",showOrder.size());
						//转发到order.jsp
						request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
					UserService.java
						UserDao dao = new UserDao();
						return dao.showOrder(id);
					UserDao.java
						QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
						//这样显示没有id编号数据
						//String sql="select * from orders where user_id=?";
						String sql="select id as oid,money,receiverName,receiverPhone,receiverAddress,paystate,ordertime 
									from orders where user_id=?";
						return runner.query(sql, new BeanListHandler<Order>(Order.class),id);
						
						
				(2) 点击订单查询中的 查看 按钮
			2、jsp代码操作
				(1) 动态显示点单查询的数据---orderlist.jsp
					<c:forEach items="${showOrder }" var="order">
						<tr>
							<td class="tableopentd02">${order.oid }</td>
							<td class="tableopentd02">${order.receiverName }</td>
							<td class="tableopentd02">${order.ordertime }</td>
							<td class="tableopentd02">${order.paystate==0?"未支付":"已支付" }</td>
							<td class="tableopentd03">
								<a href="orderInfo.jsp">查看</a>
								&nbsp;&nbsp;
								<!-- 添加判断 -->
								<c:if test="${order.paystate==1 }">
									<a href="javascript:void(0);">刪除</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				(2) 查看、删除按钮---orderlist.jsp
					<a href="${pageContext.request.contextPath }/orderInfo?id=${order.oid}">查看</a>
					&nbsp;&nbsp;
					<!-- 添加判断 -->
					<c:if test="${order.paystate==1 }">
						<a href="javascript:void(0);">刪除</a>
					</c:if>
				
				(3) 显示用户名及条数---orderlist.jsp
					<p>
						欢迎${user.username }光临商城！
					</p>
					<p>
						您有<font style="color:#FF0000">${showsize }</font>个订单
					</p>
		第十六：查看订单--点击  查看按钮  跳转到订单相对应的页面
			1、java代码操作
				OrderInfoServlet.java
					//点击查看订单的查看按钮功能
		
					//接收客户端提交的id
					String oid = request.getParameter("oid");
					//传递到service层
					UserService service = new UserService();
					Order order=null;
					try {
						order = service.findOrderInfoByOid(oid);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
					//将order放到session中
					request.setAttribute("order", order);
					//转发到orderInfo.jsp
					request.getRequestDispatcher("/orderInfo.jsp").forward(request, response);
				UserService.java
					UserDao dao = new UserDao();
					List<Map<String, Object>> mapList = dao.findOrderInfoByOid(oid);
					//将maplist中的数据封装到orders中
					Order order = new Order();
					//遍历list集合
					for(Map<String, Object> map : mapList){
						OrderItem item = new OrderItem();
						Product product =  new Product();
						try {
							BeanUtils.populate(order, map);
							BeanUtils.populate(item, map);
							BeanUtils.populate(product, map);
						} catch (Exception e) {
							e.printStackTrace();
						}
						//将product对象封装到item中
						item.setProduct(product);
						order.getOrderItemList().add(item);
					}
					
					return order;
				UserDao.java
					QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
					String sql = "select o.id as oid,o.money,o.receiverAddress,o.receiverName,o.receiverPhone,i.buynum as buyNum,"+
							" p.price*i.buynum as itemMoney,p.name,p.price,o.paystate"+
							" from orders o,orderitem i,products p"+
							" where o.id=i.order_id and i.product_id=p.id and o.id=?";
					
					/*String sql = "select o.id as oid,o.money,o.receiverAddress,o.receiverName,o.receiverPhone,i.buynum as buyNum,p.price*i.buynum as itemMoney,p.name,p.price,o.paystate"+
								" from orders o,orderitem i,product p"+
								" where o.id=i.order_id and i.prduct_id=p.id and o.id=?";*/
					 List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), oid);
					 System.out.println("mapList"+mapList);
					return mapList;
			2、jsp页面操作
				orderInfo.jsp
					<td>
						<p>订单编号:${order.oid }</p>
					</td>
					
					<c:forEach items="${order.orderItemList }" var="orderItem" varStatus="vs">
						<tr>
							<td width="10%">${vs.count }</td>
							<td width="40%">${orderItem.product.name }</td>
							<td width="10%">${orderItem.product.price }</td>
							<td width="10%">${orderItem.buyNum}</td>
							<td width="10%">${orderItem.itemMoney }</td>
						</tr>
					</c:forEach>
					
					<table cellspacing="1" class="carttable">
						<tr>
							<td style="text-align:right; padding-right:40px;">
								<font style="color:#FF0000">合计：&nbsp;&nbsp;${order.money }</font>
							</td>
						</tr>
					</table>

					<p>
						收货地址：${order.receiverAddress }&nbsp;&nbsp;&nbsp;&nbsp;<br />
						收货人：&nbsp;&nbsp;&nbsp;&nbsp;${order.receiverName }&nbsp;&nbsp;&nbsp;&nbsp;<br />
						联系方式：${order.receiverPhone }&nbsp;&nbsp;&nbsp;&nbsp;

					</p>
					<c:if test="${order.paystate==0 }">
						<a href="pay.jsp">
							<img src="images/gif53_029.gif" width="204" height="51" border="0" />
						</a>
					</c:if>
		第十七：查看订单--删除按钮
			分析：删除按钮
				jsp
					更改跳转的地址--delOrder--传id
				web层
					DelOrderServlet(虚拟路径路径：delOrder)---调用delOrderByOid（）；
				service层
					UserService----调用delOrderByOid（）；
					开启事物--同时成功或同时失败
					成功--提交
					失败--回滚/
				dao层
					应该删除两张表的数据
						orderItems表：
							1、根据id查询响应的订单项的数量
							2、根据id删除响应的订单项--delOrderItems()
							3、判断1==2
						orders表：
							根据id删除响应的数据---DelOrder()
						
							
			1、java代码操作
				DelOrderServlet.java
					//接收客户端提交的数据
					String oid = request.getParameter("oid");
					//传递到service层
					UserService service = new UserService();
					service.delOrderByOid(oid);
					//转发到--查看订单页面
					request.getRequestDispatcher("/showOrders").forward(request, response);
				UserService.java
					//查看订单中的  删除按钮
					public void delOrderByOid(String oid) {
						//传递到service
						UserDao dao = new UserDao();
						try {
							//开启事物---调工具中的方法
							DataSourceUtils.startTransaction();
							//删除订单项的方法
							dao.orderItem(oid);
							//删除订单的方法
							dao.order(oid);
							
						} catch (Exception e) {
							//回滚事物
							try {
								DataSourceUtils.rollback();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							e.printStackTrace();
						}finally{
							//提交事物
							try {
								DataSourceUtils.commitAndRelease();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				UserDao.java
					//删除订单项的方法
					public void orderItem(String oid) throws Exception {
						QueryRunner runner = new QueryRunner();
						//从工具包中调用Connection方法
						Connection conn = DataSourceUtils.getConnection();
						//根据oid查询数据库中有几条相对应的数据
						String sql_count="select count(*) from orderitem where order_id=?";
						Long queryRow = (Long) runner.query(conn, sql_count, new ScalarHandler(),oid);
						String sql="delete from orderitem where order_id=?";
						int row = runner.update(conn, sql, oid);
						if(row!=queryRow.intValue()){
							throw new SQLException();
						}
					}
					//删除订单的方法
					public void order(String oid) throws Exception {
						QueryRunner runner = new QueryRunner();
						String sql = "delete from orders where id=?";
						Connection conn = DataSourceUtils.getConnection();
						int update = runner.update(conn, sql , oid);
						//判断
						if(update<=0){
							throw new SQLException();
						}
					}
			2、jsp代码操作
				orderlist.jsp
					<script type="text/javascript">
						function deiOrder(oid){
							if(confirm("您确定要删除吗？")){
								location.href="${pageContext.request.contextPath}/delOrder?oid="+oid;
							}
						} 
					</script>

				
	二、后台
		第一：订单管理---订单查看
			1、java代码操作
				AdminOrderListServlet.java
					//点击订单按钮--显示订单列表的操作(user表和orders表一起查询 多表查询)
					// 直接传递到service层
					ManagementService service = new ManagementService();
					List<Map<String, Object>> mapList = null;
					try {
						mapList = service.findAllOrders();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					//将mapList放到域中
					request.setAttribute("mapList", mapList);
					// 转发到list.jsp页面
					request.getRequestDispatcher("/admin/orders/list.jsp").forward(request,response);
				ManagementService.java
					//传递到dao层
					ManagementDao dao  = new ManagementDao();
					return dao.findAllOrders();
				ManagementDao.java
					QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
					String sql="select o.id,o.receiverAddress,o.receiverName,o.receiverPhone,o.money,u.username,o.paystate 
						from user u,orders o where u.id=o.user_id;";
					return runner.query(sql, new MapListHandler());
			2、jsp代码操作---left.jsp
				(1) d.add(21,2,'订单查看','${pageContext.request.contextPath}/adminOrderList','','mainFrame');
				(2) 跳转到的页面admin/orders/list.jsp 
					list.jsp 
					引入：
						<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
					
					<!-- 循环显示 -->
					<c:forEach items="${mapList }" var="map">
					<tr onmouseover="this.style.backgroundColor = 'white'" onmouseout="this.style.backgroundColor = '#F5FAFE';">
						<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="20%">${map.id }</td>
						<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="10%">${map.receiverName }</td>
						<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="15%">${map.receiverAddress }</td>
						<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="10%">${map.receiverPhone }</td>
						<td style="CURSOR: hand; HEIGHT: 22px" align="center"> ${map.money}</td>
						<td width="8%" align="center">${map.username }</td>
						<td width="10%" align="center">${map.paystate==0?"未支付":"已支付" }</td>
						<td align="center" style="HEIGHT: 22px">
							<a href="#">
								<img src="${pageContext.request.contextPath}/admin/images/button_view.gif" border="0" style="CURSOR: hand">
							</a>
						</td>
						<td align="center" style="HEIGHT: 22px">
							<a href="#">
								<img src="${pageContext.request.contextPath}/admin/images/i_del.gif" width="16" height="16" border="0" style="CURSOR: hand">
							</a>
						</td>
					</tr>
					</c:forEach>
		
		第二：订单管理---订单查看---查看按钮
			1、java代码操作
				AdminOrderInfoServlet.java
					//查看订单--查看按钮
					//获得客户端提交的id
					String id = request.getParameter("id");
					//传递到service层
					ManagementService service = new ManagementService();
					List<Map<String, Object>> mapList=null;
					try {
						mapList = service.findAllOrderByOid(id);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					//放到域中
					request.setAttribute("mapList", mapList);
					//转发到view.jsp
					request.getRequestDispatcher("/admin/orders/view.jsp").forward(request, response);
				ManagementService.java
					ManagementDao dao  = new ManagementDao();
					return dao.findAllOrderByOid(id);
				ManagementDao.java
					QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
					/*String sql = "select o.id as oid,o.receiverName,o.receiverAddress,o.ordertime,o.receiverPhone,o.money,u.username,p.imgurl,p.id as pid,p.name,p.price,p.category,p.description,oi.buynum from orders o, user u,product p,orderitem oi where o.user_id=u.id and o.id=oi.order_id and oi.product_id=p.id and o.id?";
					*/																																																						
					String sql = "select o.id as oid,o.receiverName,o.receiverAddress,o.ordertime,u.username,o.receiverPhone,o.money,p.imgurl,p.id as pid,p.name,p.price,i.buynum,p.category,p.description"+
							" from user u,orders o,orderitem i,products p"+
							" where u.id=o.user_id and o.id=i.order_id and i.product_id=p.id and o.id=?";		
					return runner.query(sql, new MapListHandler(), id);
			2、jsp代码操作
				admin/orders/list.jsp
					<a href="${pageContext.request.contextPath }/adminOrderInfo?id=${map.id }"">
						<img src="${pageContext.request.contextPath}/admin/images/button_view.gif" border="0" style="CURSOR: hand">
					</a>
				点击查看跳转到----view.jsp--页面
					<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
					
					<tr>
						<td width="18%" align="center" bgColor="#f5fafe" class="ta_01"> 订单编号：</td>
						<td class="ta_01" bgColor="#ffffff">${mapList[0].oid}</td>
						<td align="center" bgColor="#f5fafe" class="ta_01">所属用户：</td>
						<td class="ta_01" bgColor="#ffffff">${mapList[0].username}</td>
					</tr>

					<tr>
						<td align="center" bgColor="#f5fafe" class="ta_01">收件人：</td>
						<td class="ta_01" bgColor="#ffffff">${mapList[0].receiverName }</td>
						<td align="center" bgColor="#f5fafe" class="ta_01">联系电话：</td>
						<td class="ta_01" bgColor="#ffffff">${mapList[0].receiverPhone }</td>
					</tr>
					<tr>
						<td align="center" bgColor="#f5fafe" class="ta_01">送货地址：</td>
						<td class="ta_01" bgColor="#ffffff">${mapList[0].receiverAddress}</td>
						<td align="center" bgColor="#f5fafe" class="ta_01">总价：</td>
						<td class="ta_01" bgColor="#ffffff">${mapList[0].money }</td>
					</tr>
					<tr>
						<td align="center" bgColor="#f5fafe" class="ta_01">下单时间：</td>
						<td class="ta_01" bgColor="#ffffff" colSpan="3">${mapList[0].ordertime}</td>
					</tr>

					<c:forEach items="${mapList}" var="map" varStatus="vs">
						<tr style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #eeeeee">
							<td align="center" width="7%">${vs.count }</td>
							<td width="8%" align="center">
								<img src="${pageContext.request.contextPath}/${map.imgurl}" width="50" height="50">
							</td>

							<td align="center" width="18%">${map.oid }</td>
							<td align="center" width="10%">${map.name }</td>
							<td align="center" width="10%">${map.price }</td>
							<td width="7%" align="center">${map.buynum }</td>
							<td width="7%" align="center">${map.category }</td>
							<td width="31%" align="center">${map.description}</td>
						</tr>
					</c:forEach>
		第三：商品管理--商品查看按钮
			1、java代码操作
				
			2、jsp代码操作
				(1)admin/login/left.jsp
					d.add(11,1,'商品查看','${pageContext.request.contextPath}/adminProductList','','mainFrame');
				(2) admin/product/list.jsp
					引入：<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
					
					<!-- 循环显示 -->
					<c:forEach items="${productList }" var="product">
						<tr onmouseover="this.style.backgroundColor = 'white'" 	onmouseout="this.style.backgroundColor = '#F5FAFE';">
							<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="23">${product.id }</td>
							<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="18%">${product.name }</td>
							<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="8%">${product.price }</td>
							<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="8%">${product.pnum }</td>
							<td style="CURSOR: hand; HEIGHT: 22px" align="center">${product.category }</td>
							<td align="center" style="HEIGHT: 22px" width="7%">
								<a href="../products/edit.jsp">
									<img src="${pageContext.request.contextPath}/admin/images/i_edit.gif" border="0" style="CURSOR: hand">
								 </a>
							</td>

							<td align="center" style="HEIGHT: 22px" width="7%">
								<a href="#">
									<img src="${pageContext.request.contextPath}/admin/images/i_del.gif" width="16" height="16" border="0" style="CURSOR: hand">
								</a>
							</td>
						</tr>
					</c:forEach>
		
		
		
		
Bootstrap--前端框架		
		
		未完成的工作
			自动登录
			Md5加密
			推出系统时清楚cookie
			代码优化
			权限
			
		
		
ctrl+ R---搜索
ctrl+ h---全局搜索
做标记--- <!--2016 hu  -->	--
ctrl+shift+t==

md5加密
		
cookie不能存中文--报错
	解决方案：
		

ajax



	
	屡思路---做文档---敲代码---简单再复杂
	debug
	打印语句
		
			
		
	亿美短信平台/云片短信平台	
	短息jar包
我的账户-servlet---从session域中找数据--有就跳转到详细信息---没有就跳转到登录页面

在jsp页面添加验证码的按钮和输入框
session与数据库同步

提取公共页面


在线支付

	三方支付平台
		易宝支付--
		国付宝
		连连支付--较多
	理财
		温商贷-
		
order表  涉及到实体与数据库的字段不一样的，，可以起别名

	
		
自动登录
ajax的异步搜索		
		

加、减按钮		flag
为什么要从session域中去数据---添加购物车		
加入购物车时的EL表达式 	

封装实体---不会


	拼接网址
	发邮件
	微信
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	