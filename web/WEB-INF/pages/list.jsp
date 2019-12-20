<%@ page language="java" import="java.util.*,entity.*" pageEncoding="utf8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" /> 
	<script src="${pageContext.request.contextPath}/js/template-web.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap-mypaginator.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
	
	<script type="text/javascript"> 
    	$(function(){
    		//进入页面默认无条件加载第一页内容 
			ajaxLoadData(1);
    	});

    	function ajaxLoadData(pageNo){
    		$.ajax({
    			url:'${pageContext.request.contextPath}/product/findAllProduct.do',
                dataType: "json",
				data: {
    				"pageNo":pageNo
    			},
    			type:'post',
    			success:function(data) {
	    			var tr = $("#tb").children();
					tr.remove();
					var info = data.obj;
					if (pageNo == 1) {
						myoptions.onPageClicked = function(event, originalEvent, type,page) {
							ajaxLoadData(page);
						};
						var totalPages = info.pages;
						console.log("总页数："+totalPages);
						/* 在ul标签中显示分页项 */
						myPaginatorFun("myPages", 1, totalPages);	
					}
					//info就是Result,其内部有属性名为list的集合
					var html = template("product_item", info);
					$("#tb").append(html);
					$("#currentPage").val(info.pageNum);		            
    			}
    		})
    	}
    	function addCart(tmp) {
            $.ajax({
                url:'${pageContext.request.contextPath}/product/addCart.do',
                data:{"productId":tmp.id,"price":tmp.price},
                type:'post',
                success:function() {
                    alert("添加成功");
                }
            });
        }
    </script>	

	<script type="text/html" id="product_item">
		{{ each list  tmp}}
			<tr>
				<td>{{tmp.id}}</td>	
		        <td>{{tmp.name}}</td>
				<td>{{tmp.price}}</td>	
	    		<td><a href="javaScript:;" onclick="addCart({{tmp}});">添加购物车</a></td>
	        </tr>
		{{/each}}
	</script>
	</head>
	<body>
	  <div style="position: relative;left: 40%;">
		  <h1>商品列表</h1>
		  <!-- 当用户没有登录时，访问该页面，只显示登录/注册链接 ,
		  下面语法是if else-->
		  <c:choose>
			  <c:when test="${sessionUser==null}">
				  <a href="${pageContext.request.contextPath }/showLogin.do">登录</a>
				  <a href="${pageContext.request.contextPath }/showRegist.do">注册</a></c:when>
			  <c:otherwise>
				  <!-- 当用户登录成功后，访问该页面，会有如下显示 -->
				  <h1>欢迎您：${sessionUser.username }<a href="${pageContext.request.contextPath }/user/logout.do">注销</a></h1>
			   </c:otherwise>
		   </c:choose>
		  <hr>

		  <table border="1" >
			  <thead>
			  <tr>
				  <!-- 序号不是商品id,是由1开始，自增长的一组数字 -->
				  <th>序号</th>
				  <th>商品名称</th>
				  <th>商品单价</th>
				  <th>操作</th>
			  </tr>
			  </thead>
			  <tbody id="tb">
			  <!-- 此处为商品展示区域 -->
			  </tbody>

		  </table>
		  <!--用于显示当前是第几页  -->
		  <input style="display: none;" id="currentPage" >
		  <a href="${pageContext.request.contextPath }/showCart.do">查看购物车</a><br>

	  </div>

	    <ul id="myPages" style="position: absolute;bottom: 20px;left:40%;"></ul>
	  </body>
	</html>		