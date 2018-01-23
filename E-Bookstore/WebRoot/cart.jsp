
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
		<div class="container" style="background-color: fff;height: 100%;">

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
							<li role="presentation"><a
								href="${pageContext.request.contextPath}/showBook.action"><i
									class="fa fa-home" aria-hidden="true"></i>&nbsp;首页</a></li>
							<li role="presentation"><a href="${pageContext.request.contextPath}/showOrder.action"><i
									class="fa fa-list" aria-hidden="true"></i>&nbsp;我的订单</a></li>
							<li role="presentation" class="active"><a href="${pageContext.request.contextPath}/showCart.action"><i
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

			<!-- 购物车展示 -->
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row" id="class">
						<table class="table" id="showcart">
							<thead>
								<tr>
									<th style="text-align:center;">图书预览</th>
									<th style="text-align:center;">书名</th>
									<th style="text-align:center;">数量</th>
									<th style="text-align:center;">价格</th>
									<th style="text-align:center;">操作</th>
								</tr>
							</thead>
							<tbody valign="middle">
								<c:forEach items="${requestScope.bookList}" var="book"
									varStatus="status">
									<tr>
										<td><img
											src="${pageContext.request.contextPath}${book.image}"
											style="width:88px;height:128px;"</td>
										<td style="color: #f38106"><strong>${book.bookname}</strong>
										</td>
										<td><input type="number" onkeyup="addPrice(this);"
											min="0" max="100" id="numberInp" value="1"
											data-trigger="manual" data-container="body"
											data-toggle="popover" data-placement="top"
											data-content="非法输入!请输入非零正整数."></input></td>
										<td>${book.bPrice}</td>
										<td>
											<!-- Button trigger modal -->
											<button type="button" class="btn btn-link"
												onclick="cartremove('${book.bid}','${book.bookname}','${book.writer}','${book.bPrice}','${book.image}','${book.stock}');">
												<i class="fa fa-trash"></i>&nbsp;移&nbsp;&nbsp;除&nbsp;
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- 购物车展示结束 -->
			<div class="panel panel-default">
				<div class="panel-body">
					<strong style="color: #e51d07;font-size: 25">总价: ¥ <span
						id="totalPrice"></span> 元
					</strong>
					<button id="confirmBuy" type="button" class="btn btn-warning"
						style="float: right">
						<i class="fa fa-check"></i>&nbsp;立刻购买&nbsp;
					</button>
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
			let totalPrice = 0;
			$("#showcart > tbody tr").each(function() {
				const amount = parseFloat($(this).find("td:eq(2)").find("input").val());
				const price = parseFloat($(this).find("td:eq(3)").text());
				totalPrice += amount * price;
			})
			$('#totalPrice').text(toDecimal2(totalPrice));
		})
		function showprice() {
			var price = $("#costname").val();
			$("#price").attr("value", toDecimal2(price));
		}
		//把数字格式化成xx.xx的效果(保留2位小数,整数强制补零)
		function toDecimal2(x) {
			var f = parseFloat(x);
			if (isNaN(f)) {
				return false;
			}
			var f = Math.round(x * 100) / 100;
			var s = f.toString();
			var rs = s.indexOf('.');
			if (rs < 0) {
				rs = s.length;
				s += '.';
			}
			while (s.length <= rs + 2) {
				s += '0';
			}
			return s;
		}
	
		//数量变化是从新计算总价
		function addPrice(index) {
			let val = $(index).val();
			let totalPrice = 0;
			var regu = /^[1-9]\d*$/; /* ^\+?[1-9][0-9]*$ */
			if (val == "" || isNaN(val)) {
				$('#numberInp').popover('show');
			} else {
				$('#numberInp').popover('hide');
				$("#showcart > tbody tr").each(function() {
					const amount = parseFloat($(this).find("td:eq(2)").find("input").val());
					const price = parseFloat($(this).find("td:eq(3)").text());
					totalPrice += amount * price;
				})
				$('#totalPrice').text(toDecimal2(totalPrice));
			}
		}
	
		//从cookie中移除书籍
		function cartremove(bidval, booknameval, writerval, bPriceval, imageval, stockval) {
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
				url : "cartRemove.action",
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
	
		//删除行
		$(".btn-link").click(function() { //必须通过 .class获取dom节点(如此例在按钮class添加edit)
			$(this).parent().parent().remove();
			let totalPrice = 0;
			$("#showcart > tbody tr").each(function() {
				const amount = parseFloat($(this).find("td:eq(2)").find("input").val());
				const price = parseFloat($(this).find("td:eq(3)").text());
				totalPrice += amount * price;
			})
			$('#totalPrice').text(toDecimal2(totalPrice));
		});
	
		//确认购买
		$("#confirmBuy").unbind().bind("click", function() {
			let params = getJsonstr();
			//let params = JSON.stringify(jsonstr);
			console.log(params);
			$.ajax({
				url : "produceOrders.action",
				type : "post",
				contentType : 'application/json;charset=utf-8',
				data : params,
				success : function(result) {
	
					if (result.status == 0) {
						alert(result.message);
					} else if (result.status == 1) {
						$("#showcart > tbody tr").each(function() {
							$(this).remove();
						})
						$('#totalPrice').text(toDecimal2(0.00));
						alert(result.message);
					}
				}
			});
		})
	
		//获取购买数并拼接json字符串
		function getJsonstr() {
			/* let jsonstr='['; */
			let params = [];
			$("#showcart > tbody tr").each(function() {
				const bookname = $(this).find("td:eq(1)").find("strong").text();
				const count = $(this).find("td:eq(2)").find("input").val();
				/* jsonstr += '{';
				jsonstr += "'bookname':'" + bookname+ "',";  
	                jsonstr += "'count':'" + count +"'";  
	                jsonstr += '}'  
	                jsonstr += ',' */
				params.push({
					"bookname" : bookname,
					"count" : count
				});
	
			})
			/* jsonstr = jsonstr.substring(0, jsonstr.length - 1);  
	            jsonstr += ']';  */
			let jsonstr = JSON.stringify(params);
			return jsonstr;
		}
	</script>
</body>
</html>
