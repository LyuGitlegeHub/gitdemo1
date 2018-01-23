<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'login.jsp' starting page</title>

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

<body>
	<div class="row" style="margin:200 auto;">
		<div class="col-md-5 col-md-offset-3">
			<div class="well well-lg">

				<!-- 登录表单 -->
				<form class="form-horizontal" id="loginform">
				<div class="form-group">
						<label for="inputEmail3" class="col-sm-5 control-label">电子书城登录</label>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-10">
							<input name="username" type="text" class="form-control"
								id="loginUsername" placeholder="输入用户名">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-10">
							<input name="password" type="password" class="form-control"
								id="loginPassword" placeholder="输入密码">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button id="login" type="button" class="btn btn-primary">
								登 录</button>
							<button id="register" type="button" class="btn btn-success">
								注 册</button>
						</div>
					</div>
				</form>

				<!-- 注册表单 -->
				<form class="form-horizontal" id="registerform"
					Style="display:none;">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-5 control-label">电子书城注册</label>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-10">
							<input name="username" type="text" class="form-control"
								id="registerUsername" placeholder="输入用户名">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-10">
							<input name="password" type="password" class="form-control"
								id="registerPassword" placeholder="输入密码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">邮箱</label>
						<div class="col-sm-10">
							<input name="email" type="email" class="form-control"
								id="registerEmail" placeholder="输入邮箱">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button id="registersub" type="button" class="btn btn-primary">
								提 交</button>
								 <a href="${pageContext.request.contextPath}/login.jsp"> 返 回 </a>
						</div>
					</div>
				</form>
			</div>
		</div>

		<script type="text/javascript" src="./js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="./js/jquery.serializejson.js"></script>
		<script type="text/javascript">
			$(function() {
				//点击注册按钮
				$("#register").unbind().bind("click", function() {
					$("#loginform")[0].reset();
					$("#loginform").toggle();
					$("#registerform").toggle();
				});
				
				//提交登录信息
				$("#login").unbind().bind("click", function() {
					if(isNull($("#loginUsername").val())){
						alert("用户名不能为空!");
					}else if(isNull($("#loginPassword").val())){
						alert("密码不能为空!");
					} else{
						const data = $("#loginform").serializeJSON();//form表单据数转为json字符串 需要引入jquery.serializejson.js
						const user = JSON.stringify(data); //json对象转为json字符串
						$.ajax({
							url : "login.action",
							type : "post",
							contentType : 'application/json;charset=utf-8',
							data : user,
							success : function(result) {
								if(result.status==0){
									alert(result.message);
									$("#loginUsername").focus();
								} else if (result.status==1){
									alert(result.message);
									window.location.href = "${pageContext.request.contextPath}/showBook.action"
								}
							}
						}); 
					}
				})
				
				//提交注册信息
				$("#registersub").unbind().bind("click", function() {
					if(isNull($("#registerUsername").val())){
						alert("用户名不能为空!");
					}else if(isNull($("#registerPassword").val())){
						alert("密码不能为空!");
					}else if(isNull($("#registerEmail").val())){
						alert("邮箱不能为空!");
					} else{
						const data = $("#registerform").serializeJSON();//form表单据数转为json字符串 需要引入jquery.serializejson.js
						const user = JSON.stringify(data); //json对象转为json字符串
						$.ajax({
							url : "register.action",
							type : "post",
							contentType : 'application/json;charset=utf-8',
							data : user,
							success : function(result) {
								if(result.status==0){
									alert(result.message);
									$("#registerUsername").focus();
								} else if (result.status==1){
									alert(result.message);
									$("#registerform")[0].reset();
									$("#registerform").toggle();
									$("#loginform").toggle();
									$("#loginUsername").focus();
								}
							}
						}); 
					}
				})
			})

			function isNull(str) {
				if (str == "")
					return true;
				var regu = "^[ ]+$";
				var re = new RegExp(regu);
				return re.test(str);
			}
		</script>
</body>
</html>
