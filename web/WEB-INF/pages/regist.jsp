<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
  <body>
    <h1>用户注册</h1> 
    <a href="${pageContext.request.contextPath }/showLogin.do">返回登录</a>
    <hr>
    <form action="${pageContext.request.contextPath }/user/regist.do" method="post">
    	用户名：<input type="text" name="username"><br>
    	密码：<input type="password" name="password"><br>
    	电话：<input type="text" name="phone"><br>
    	地址：<input type="text" name="address"><br>
    	验证码：<input type="text" name="code">
	   	<a href="javascript:;">
	   		<img id="img" alt="加载中..." src="${pageContext.request.contextPath }/user/getCode.do" onclick="codeRefresh()">
	   	</a><br>
    	<input type="submit" value="注册">

	   	<span style="color:red">${registMsg }</span>
    </form>
  </body>
  	<head>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript">
		function codeRefresh(){
		 $("#img").attr("src","${pageContext.request.contextPath}/user/getCode.do?rt="+Math.random());
		} 
	</script>
 	</head>
</html>