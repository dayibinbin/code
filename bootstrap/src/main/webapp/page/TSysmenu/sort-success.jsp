<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>菜单排序</title>
    <%@ include file="../Common/meta.jsp" %>
</head>
<body>
<section class="content">
<div class="box box-primary baseInfo">
  <div class="box-header with-border">
    <i class="fa fa-file"></i><label class="view-lab">&nbsp;菜单排序</label>
    <div class="box-tools pull-right">
      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
   <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
 </div>
  </div>
  <div class="box-body col-xs-12">
  	<div class="alert alert-warning">
	    <ul class="fa-ul">
	        <li>
	            <i class="fa fa-info-circle fa-lg fa-li"></i>
	                            说明：点击拖拽交换导航菜单的先后顺序，放开点击后自动保存新顺序
	        </li>
	    </ul>
	</div>
  	<section class='example'>
    <div class='gridly'>
    	<s:if test="${tsysmenus!=null && !tsysmenus.isEmpty()}">
	   		<s:forEach var="tsysmenu" items="${tsysmenus}">
	   			<div class="brick small col-xs-10 col-xs-offset-1">
	   				<i class="fa fa-chevron-right"></i>
	   				<label class="view-lab">
		   				<s:out value="${tsysmenu.name}"/>|
		   				<s:out value="${tsysmenu.id}"/>|
		   				<s:out value="${tsysmenu.sequence}"/>
			   			<s:choose>
			   				<s:when test="${tsysmenu.level == 1}">
		   						|一级
		   					</s:when>
		   					<s:when test="${tsysmenu.level == 2}">
		   						|二级
		   					</s:when>
		   					<s:otherwise>
		   						|未定义
		   					</s:otherwise>
			   			</s:choose>
		   			</label>
	   			</div>
	   		</s:forEach>
	   	</s:if>
    </div>
  </section>
  </div>
  <div class="row">
    <div style="text-align:center" class="col-md-12">
    	<button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeAndReload('菜单排序')">关闭</button>
    </div>
  </div>
</div>
</section>
<%@ include file="../Common/script.jsp" %>
<script>
(function() {
	$(function() {
		var reordered = function($elements) {
			var sorts = "";
			// 当前对象
			var currentObj = this.reordered.arguments[0];
			if (currentObj == null || currentObj == undefined || currentObj.length == 0){
				top.toastr.error('无可用菜单！','菜单排序');
				return;
			}
			var seq = 10;
			$(currentObj).each(function(index){
				var tmpText = $(this).text();
				tmpText = tmpText.replace(/\s/g, "");
				if (tmpText != null && tmpText != ''){
					var tmpArray = tmpText.split('|');
					sorts = sorts + tmpArray[1] + '-' + seq + ",";
					seq = seq + 10;
				}
			});
			sorts = sorts.substring(0,sorts.lastIndexOf(",",sorts.length - 1));
			top.$('#loading').fadeIn(100);
			$.ajax({
		        url: '../TSysmenu/sorting.do',
		        type:'post',
		        contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		        data : {sorts : sorts},
		        success: function(result){
		            if (result.iserror){
		            	top.$("#loading").fadeOut(100);
		            	top.toastr.error(result.message,'菜单排序');
		            } else {
		            	top.$("#loading").fadeOut(100);
		            	top.toastr.success(result.message, '菜单排序');
		            }
		        }
		    });
		};
		return $('.gridly').gridly({
			callbacks : {
				reordered : reordered
			}
		});
	});
}).call(this);

</script>
</body>
</html>
