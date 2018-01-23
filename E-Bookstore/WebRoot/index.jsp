
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="${pageContext.request.contextPath}/css/bootstrap.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet">
</head>

<style type="text/css">
.linkdiv:hover {
	text-decoration: none
}

.linkdiv {
	color: #333;
}

#class td /*设置表格文字左右和上下居中对齐*/ {
	vertical-align: middle;
	text-align: center;
}
</style>
<body style="background-color: f3f3f3;">
	<div class="app">
		<div class="container" style="background-color: fff;height: auto">

			<!-- 页头 -->
			<div class="page-header">
				<h1>
					<strong>尚学电子书城</strong> <small>Love Study E-Bookstore</small>
				</h1>
			</div>
			<div class="row">
				<div class="col-md-7">
					<h4>
						<ul class="nav nav-tabs">
							<li role="presentation"><h4>欢迎你, ${user.username}
									&nbsp;&nbsp;&nbsp;&nbsp;</h4></li>
							<li role="presentation" class="active"><a
								href="${pageContext.request.contextPath}/showBook.action"><i
									class="fa fa-home" aria-hidden="true"></i>&nbsp;首页</a></li>
							<li role="presentation"><a
								href="${pageContext.request.contextPath}/showOrder.action"><i
									class="fa fa-list" aria-hidden="true"></i>&nbsp;我的订单</a></li>
							<li role="presentation"><a
								href="${pageContext.request.contextPath}/showCart.action"><i
									class="fa fa-shopping-cart" aria-hidden="true"></i>&nbsp;购物车</a></li>
							<li role="presentation"><a
								href="${pageContext.request.contextPath}/logout.action"><i
									class="fa fa-sign-out" aria-hidden="true"></i>&nbsp;注销</a></li>
						</ul>
					</h4>
				</div>
				<div class="col-md-3" style="margin:15 0 0 0 ">
					<div class="input-group" data-toggle="tooltip"
						data-placement="bottom" title="支持模糊查询">
						<input id="searchText" type="text" class="form-control"
							placeholder="关键字" data-container="body" data-toggle="popover"
							data-trigger="manual" data-placement="top"
							data-content="查询关键字不能为空!"> <span class="input-group-btn">
							<button class="btn btn-info" type="button" id="searchBtn">
								&nbsp;<i class="fa fa-search" aria-hidden="true"></i>&nbsp;搜&nbsp;索&nbsp;&nbsp;&nbsp;&nbsp;
							</button>
						</span>
					</div>
				</div>
				<div class="col-md-2"></div>
			</div>

			<!-- 书目展示 -->
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row" id="class">
						<table class="table" id="showbooks">
							<thead>
								<tr>
									<th style="text-align:center;">书名</th>
									<th style="text-align:center;">作者</th>
									<th style="text-align:center;">价格</th>
									<th style="text-align:center;">库存</th>
									<th style="text-align:center;">图书预览</th>
									<th style="text-align:center;">操作</th>
								</tr>
							</thead>
							<tbody valign="middle">
								<c:forEach items="${requestScope.pageutil.pageData}" var="book"
									varStatus="status">
									<tr>
										<td style="color: #f38106"><strong>${book.bookname}</strong>
										</td>
										<td>${book.writer}</td>
										<td>${book.bPrice}</td>
										<td>${book.stock}</td>
										<td><img
											src="${pageContext.request.contextPath}${book.image}"
											style="width:88px;height:128px;"</td>
										<td>
											<!-- Button trigger modal -->
											<button type="button" class="btn btn-primary"
												onclick="cartadd('${book.bid}','${book.bookname}','${book.writer}','${book.bPrice}','${book.image}','${book.stock}');">
												<i class="fa fa-shopping-cart"></i>&nbsp;放入购物车
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<!-- 页码 -->
				<div class="col-md-10">
					<nav aria-label="Page navigation" style="float:right;height:60px;">
					<ul class="pagination pagination" style="margin:10px 0 0;">
						<c:if test="${requestScope.pageutil.currnav!=1 }">
							<li><a href="showBookByPage.action?currnav=1">首页</a></li>
							<li><a
								href="showBookByPage.action?currnav=${requestScope.pageutil.pre }"
								aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
							</a></li>
						</c:if>
						<c:forEach begin="${requestScope.pageutil.begin }"
							end="${requestScope.pageutil.end }" var="i">
							<c:choose>
								<c:when test="${requestScope.pageutil.currnav==i }">
									<li><a href="showBookByPage.action?currnav=${i }">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="showBookByPage.action?currnav=${i}">${i }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${requestScope.pageutil.currnav!=pageutil.last }">
							<li><a
								href="showBookByPage.action?currnav=${requestScope.pageutil.next }"
								aria-label="Next"> <span aria-hidden="true">&raquo;</span>
							</a></li>
							<li><a
								href="showBookByPage.action?currnav=${requestScope.pageutil.last }">尾页</a></li>
						</c:if>
					</ul>
					</nav>
				</div>
			</div>


		</div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="./js/jquery.serializejson.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript">
		//初始化bootstrap弹出框
		$(function() {
			$('[data-toggle="popover"]').popover();
		})
		//点击放入购物车按钮
		function cartadd(bidval, booknameval, writerval, bPriceval, imageval, stockval) {
			let bookstr = {
				bid : bidval,
				bookname : booknameval,
				writer : writerval,
				bPrice : bPriceval,
				image : imageval,
				stock : bidval
			};
			const book = JSON.stringify(bookstr); //json对象转为json字符串
			$.ajax({
				url : "cartAdd.action",
				type : "post",
				contentType : 'application/json;charset=utf-8',
				data : book,
				success : function(result) {
					if (result.status == 0) {
						alert(result.message);
					} else if (result.status == 1) {
						alert(result.message);
					}
				}
			});
		}
	
		//模糊查询
		$(function() {
			$("#searchBtn").click(function() {
				let param = $("#searchText").val();
				if (param.replace(/(^\s*)|(\s*$)/g, "").length == 0) { //输入框为空
					$("#searchText")[0].focus();
					$('#searchText').popover('show'); //弹出提示框
				} else {
					window.location.href = "${pageContext.request.contextPath}/fuzzyQueryBookByPage.action?param="+param;
				}
			});
			//需要手动隐藏弹出框
			$("#searchText").blur(function() {
				$('#searchText').popover('hide');
			});
		});
	</script>
</body>
</html>
