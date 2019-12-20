<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head> 
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap-mypaginator.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
  <script src="${pageContext.request.contextPath}/js/template-web.js"></script>
  <!--用于弹出框的样式  -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" />
  <script>
  $(function(){
  		ajaxLoadData(1);
	  $("#confirmBt").bind("click",modifyItem);
  });
  
  function ajaxLoadData(pageNo) {
   		$.ajax(
   		{
   			url:'${pageContext.request.contextPath}/product/findCart.do',
   			data:
   			{	
   				"pageNo":pageNo
   			},    			
   			type:'post',
            dataType: "json",
   			success:function(data)
   			{
   				//清空上次启启启记录			
    			$("#tb").children().remove();
				var info = data.obj;
				if (pageNo == 1) 
				{
					myoptions.onPageClicked = function(event, originalEvent, type, page) {
						ajaxLoadData(page);
					};
					var totalPages = info.pages;
					/* 在ul标签中显示分页项 */
					myPaginatorFun("myPages", 1, totalPages);	
				}					
				var html = template("cart_item", info);

				$("#tb").append(html);		            
   				$("#currentPage").val(info.pageNum);
   			}
   		})
   	}
    	
    function modifyItem(){
        var productId = $("#modify-product-id").val();
        var num = $("#modify-product-num").val();
        var price= $("#modify-product-price").val();
        $.ajax({

            url:'${pageContext.request.contextPath}/product/modifyCart.do',
            data:{"productId":productId,"num":num,"price":price},
            type:'post',
            success:function(data)
            {
                ajaxLoadData($("#currentPage").val());
            }
        })
    }
  	function showModify(tmp){
	  	$("#modify-product-id").val(tmp.productId);
	  	$("#modify-product-name").val(tmp.productName);
	  	$("#modify-product-num").val(tmp.num);
	  	$("#modify-product-price").val(tmp.price/tmp.num);
		$("#showItem").modal("show");  
	}  
	
  function deleteItem(productId){
  	//必须用==号，因为返回的是字符串
	if(!(confirm("删除？")==true)){
		return;
	}
  	//删除
  	$.ajax({
 		url:'${pageContext.request.contextPath}/product/deleteCart.do',
 		data:{"productId":productId},    			
 		type:'post',
 		success:function(data)
 		{
 			ajaxLoadData($("#currentPage").val());
		}		            
 	})
  }
  
   function removeAll(){
  	//必须用==号，因为返回的是字符串
	if(!(confirm("删除？")==true)){
		return;
	}
  	//删除
  	$.ajax({ 	
 		url:'${pageContext.request.contextPath}/product/deleteAllCart.do',    			
 		type:'post',
 		success:function(data)
 		{
 			ajaxLoadData($("#currentPage").val());
		}		            
 	})
  }
  </script>
	<script type="text/html" id="cart_item">
		{{ each list  tmp i}}
			<tr>
				<td>{{i}}</td>	
		        <td>{{tmp.productName}}</td>
				<td>{{tmp.num}}</td>		
				<td>{{tmp.price}}</td>
				<td>
					<a href="javaScript:;" onclick = "showModify({{tmp}})">修改</a>
					<a href="javaScript:;" onclick="deleteItem({{tmp.productId}})">删除</a>
				</td>	
	        </tr>
		{{/each}}
	</script>
  </head> 
  <body>
    <h1>我的购物车</h1>

    <table border="1">
    	<thead>
    	<tr>
    		<th>序号</th>
    		<th>商品名称</th>
    		<th>商品数量</th>
    		<th>商品总价</th>
    		<th>操作</th>
    	</tr>
    	</thead>
    	<tbody id="tb">
             <!-- 此处为购物车展示区域 -->
        </tbody>
    	
    </table>
    <hr><br>

  <a href="javaScript:;" onclick="removeAll()">清空购物车</a>
  <a href="${pageContext.request.contextPath}/showList.do">继续购物</a> 
  <a href="${pageContext.request.contextPath}/showOrder.do">结算</a>
  
  <div class="modal fade" tabindex="-1" id="showItem">
     <!-- 修改商品个数的窗口声明 -->
     <div class="modal-dialog modal-lg">
         <!-- 内容声明 -->
         <div class="modal-content">
             <!-- 头部、主体、脚注 -->
             <div class="modal-header">
                 <button class="close" data-dismiss="modal">&times;</button>
                 <h4 class="modal-title">修改Item</h4>
             </div>
             <div class="modal-body text-center">
                 <div class="row text-right" id="course-id-input" style="display: none;" >
                     <label for="modify-product-id" class="col-xs-4 control-label">商品id：</label>
                     <div class="col-xs-4">
                         <input type="text" class="form-control" id="modify-product-id" name="modify_product_id" readonly />
                     </div>
                 </div>
                 <br>
                 <div class="row text-right">
                     <label for="modify-product-name" class="col-xs-4 control-label">商品名称：</label>
                     <div class="col-xs-4">
                         <input type="text" class="form-control" id="modify-product-name" name="modify_product_name" readonly/>
                     </div>
                 </div>
                 <br>
                 <div class="row text-right">
                     <label for="modify-product-num" class="col-xs-4 control-label">商品数量：</label>
                     <div class="col-xs-4">
                         <input type="text" class="form-control" id="modify-product-num" name="show_product_num" />
                     </div>
                 </div>
                 <div class="row text-right">
                     <label for="modify-product-price" class="col-xs-4 control-label">商品单价：</label>
                     <div class="col-xs-4">
                         <input type="text" class="form-control" id="modify-product-price" name="show_product_price" readonly/>
                     </div>
                 </div>
                 <br>
                 <div class="row text-right">
                     <label class="col-xs-4 control-label">封面图片：</label>
                     <div class="col-xs-4">
                         <a href="javascript:imageUpload('#cover-image');" class="file" >选择文件</a>
                         <input type="file" name="cover_image" style="display: none;" onchange="imageChange(this)" id="cover-image" />
                     </div>
                 </div>
                 <br>
             </div>
             <div class="modal-footer">
                 <button id="confirmBt" onclick="modifyItem(this,{{item.id}},0)"class="btn btn-primary curse-btn" data-dismiss="modal">确定</button>
                 <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
             </div>
         </div>
     </div>
 </div>
  	<ul id="myPages" ></ul>
  <!--用于显示当前是第几页  -->
	<input type="text" style="display: none;" id="currentPage" >
		
 </body>
  
</html>