<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap-mypaginator.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
  <script src="${pageContext.request.contextPath}/js/template-web.js"></script>
  <!--用于弹出框的样式  -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" />

<script type="text/javascript">
	$(function(){
  		ajaxLoadData();
	});
  
  	function ajaxLoadData() {
   		$.ajax({
   			url:'${pageContext.request.contextPath}/product/findAllCart.do',  			
   			type:'post',
            dataType: "json",
   			success:function(data)
   			{
   				var sum=0;
        		for(var i in data){
        			sum=sum+data[i].price;
        		}
        		$("#sumPrice").val(sum);	
   			}
   		})
   	}
</script>
</head>
	<body>
	    <h1>确认订单</h1>
	    <hr>
		用户：${sessionUser.username }
		电话：${sessionUser.phone }
		地址：${sessionUser.address }<br>
		<hr>
		<form action="${pageContext.request.contextPath }/product/createOrder.do">
		合计：<input type="text" readonly id="sumPrice" name="sumPrice">元<br>		 
		<input type="submit" value="提交">
		</form>
		<a href="${pageContext.request.contextPath }/showList.do">继续购物</a>
	 </body>
</html>