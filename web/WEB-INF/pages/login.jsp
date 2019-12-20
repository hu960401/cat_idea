<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript">
        function codeRefresh(){
            $("#img").attr("src","${pageContext.request.contextPath}/user/getCode.do?rt="+Math.random());
        }

        //后台登录时校验
        function submit(){
            var login_name = $("#login_name").val();
            var password = $("#password").val();
            var randomCode = $("#randomCode").val();
            if(login_name == ""){
                $("#errMsg").text("用户名不能为空");
                return false;
            }

            if(password == ""){
                $("#errMsg").text("密码不能为空");
                return false;
            }

            if(randomCode == ""){
                $("#errMsg").text("验证码不能为空");
                //return false;
            }

            $.ajax(
                {
                    url:'${pageContext.request.contextPath}/user/login.do',
                    data:{
                        "password":password,
                        "username":login_name,
                        "code":randomCode
                    },
                    type:'post',
                    dataType:'json',
                    success:function(data) {
                        if(data.success =="success"){
                            location.href="${pageContext.request.contextPath}/showList.do"
                        }else{
                            $("#errMsg").text(data.errMsg);
                        }
                    }
                });
        }

	</script>
</head>
  <body>
  	<div style="margin:0 auto; text-align:left; width:200px;">
	   <h1>用户登录</h1>	   
	   <a href="${pageContext.request.contextPath }/showRegist.do">返回注册</a>&nbsp;&nbsp;
	   <a href="${pageContext.request.contextPath }/showList.do">返回商品列表</a>
   </div>
   <hr>
   <div style="background-color:#0aa;margin:0 auto; text-align:left; width:400px;">
	   	用户名：<input type="text" name="name" id="login_name"><br>
	   	密&nbsp;&nbsp;&nbsp;码：<input type="password" name="pass" id="password"><br>  	
	   	验证码：<input type="text" name="code" id="randomCode">
	   	<a href="javascript:;">
	   		<img id="img" alt="加载中..." src="${pageContext.request.contextPath}/user/getCode.do" onclick="codeRefresh()">
	   	</a><br>
	   	<div style="text-align:center;">
			<button type="button" onclick ="submit()">登&nbsp;&nbsp;录</button>
		</div>
	</div>
   	<span style="color:red" id="errMsg"></span>  	
  </body>

</html>		