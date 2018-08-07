<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>修改角色</title>
    <%@ include file="../Common/meta.jsp" %>
	</script>
</head>
<body>
<section class="content">
  <form id="myfm">
  	<input type="hidden" id="privIds" name="privIds">
    <input type="hidden" id="menuIds" name="menuIds">
    <input type="hidden" id="treeIds" name="treeIds" value="${treeIds }">
    <input type="hidden" name="id" value="${tsysrole.id}">
    <input type="hidden" name="createTime" value="<fmt:formatDate value="${tsysrole.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
	<div class="box box-danger baseInfo">
	  <div class="box-header with-border">
	    <i class="fa fa-edit"></i><label class="view-lab">&nbsp;基本信息</label>
	    <div class="box-tools pull-right">
	      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
	    </div>
	  </div>
	  <!-- /.box-header -->
	  <div class="box-body">
	    <div class="row">
	      <div class="col-md-6">
	        <div class="form-group">
              <label for="name">角色名称：</label>
              <input type="text" class="form-control validate[required]" value="${tsysrole.name}" name="name" id="name">
            </div>
	        <!-- /.form-group -->
	        <div class="form-group">
	      	  <label>状态:</label>
	      	  <select class="form-control select2" name="status" style="width: 100%;">
	      		<s:choose>
   					<s:when test="${tsysrole.status == 1}">
   						<option value="1" selected="selected">启用</option>
   					</s:when>
   					<s:otherwise>
   						<option value="1">启用</option>
   					</s:otherwise>
   				</s:choose>
   				<s:choose>
   					<s:when test="${tsysrole.status == 0}">
   						<option value="0" selected="selected">停用</option>
   					</s:when>
   					<s:otherwise>
   						<option value="0">停用</option>
   					</s:otherwise>
   				</s:choose>
              </select>
	        </div>
	        <!-- /.form-group -->
	      </div>
	      <!-- /.col -->
	      <div class="col-md-6">
	        <div class="form-group">
              <label for="description">描述：</label>
              <input type="text" class="form-control" value="${tsysrole.description}" name="description" id="description">
	        </div>
	      </div>
	      <!-- /.col -->
	    </div>
	    <!-- /.row -->
	  </div>
	  <!-- /.box-body -->
	</div>
	<!-- /.box -->
	<div class="box box-info baseInfo">
	  <div class="box-header with-border">
	    <i class="fa fa-edit"></i><label class="view-lab">&nbsp;权限定义</label>
	    <div class="box-tools pull-right">
	      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
	    </div>
	  </div>
	  <!-- /.box-header -->
	  <div class="box-body">
	    <div class="row">
    	  <div class="col-md-12">
	        <ul id="tree" class="ztree" style="width:560px; overflow:auto;"></ul>
	      </div>
	    </div>
	    <!-- /.row -->
	  </div>
	  <!-- /.box-body -->
	</div>
	<!-- /.box -->
	<div class="row">
	    <div style="text-align:center" class="col-md-12">
	    	<botton class="btn btn-primary" onclick="submitForm()" >保存</botton>
	    	<button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('修改角色')">关闭</button>
	    </div>
	</div>
  </form>
</section>
<%@ include file="../Common/script.jsp" %>
<script>
$(function(){
	//初始化验证框
	$('#myfm').validationEngine('attach', {
        relative: true,
        promptPosition: "topRight" //验证弹出框的位置，topRight,topLeft,bottomRight,bottomLeft,centerRight,centerLeft,inline
    });
	initTree();
});
//初始化ztree
function initTree() {
	var treeIds = $("#treeIds").val();
	$.ajax({
		url: '../TSysrole/getPrivTree.do',
        type:'post',
        datatype: 'json',
        success: function(result){
        	var json = result;
        	var t = $("#tree"); 
            t = $.fn.zTree.init(t, setting, json);
            var treeObj=$.fn.zTree.getZTreeObj("tree");
            var ids = treeIds.split(",");
            for( var i = 0; i < ids.length; i ++ ) {
            	t.checkNode(t.getNodeByParam('id',ids[i]), true );
            }
        },
        error: function (result) {
        	top.toastr.error('权限列表获取失败!', '用户权限');
        }
	})
}
//获取已选权限
//获取已选择权限
function getChecked(){
	var treeObj=$.fn.zTree.getZTreeObj("tree");
    var nodes=treeObj.getCheckedNodes(true);
    var privIds = '';
    var menuIds = '';
    for(var i=0;i<nodes.length;i++){
    	var menuId1 = '';
    	var menuId2 = '';
    	var privId = '';
    	var tmpArray;
    	var nodeId = nodes[i].id + '';
    	if (nodeId == null || nodeId == '') continue;
    	if (nodeId.indexOf('-') < 0){
    		tmpArray = new Array(nodeId);
    	}else{
    		tmpArray = nodeId.split('-');
    	}
    	if (tmpArray.length == 1){
    		menuId1 = tmpArray[0] + ",";
    		if (menuIds == ''){
    			menuIds = menuId1;
    		}else if (menuIds.indexOf(menuId1) < 0){
    			menuIds = menuIds + menuId1;
    		}
    		
    	}else if (tmpArray.length == 2){
    		menuId1 = tmpArray[0] + ",";
    		menuId2 = tmpArray[1] + ",";
    		if (menuIds == ''){
    			menuIds = menuId1 + menuId2;
    		}else{
    			if (menuIds.indexOf(menuId1) < 0){
        			menuIds = menuIds + menuId1;
        		}
    			if (menuIds.indexOf(menuId2) < 0){
        			menuIds = menuIds + menuId2;
        		}
    		}
    	}else if (tmpArray.length == 3){
    		menuId1 = tmpArray[0] + ",";
    		menuId2 = tmpArray[1] + ",";
    		privId = tmpArray[2] + ",";
    		if (menuIds == ''){
    			menuIds = menuId1 + menuId2;
    		}else{
    			if (menuIds.indexOf(menuId1) < 0){
        			menuIds = menuIds + menuId1;
        		}
    			if (menuIds.indexOf(menuId2) < 0){
        			menuIds = menuIds + menuId2;
        		}
    		}
    		if (privIds == ''){
    			privIds = privId;
    		}else if(privIds.indexOf(privId) < 0){
    			privIds = privIds + privId;
    		}
    	}
	}
    if (menuIds != ''){
		menuIds = menuIds.substring(0,menuIds.lastIndexOf(",",menuIds.length - 1));
	}
	if (privIds != ''){
		privIds = privIds.substring(0,privIds.lastIndexOf(",",privIds.length - 1));
	}
    $("#menuIds").val(menuIds);
    $("#privIds").val(privIds);
}

function submitForm(){
	getChecked();
	var param = $("#myfm").serialize();
	if($('#myfm').validationEngine('validate') == true){
		top.$('#loading').fadeIn(100);
		$.ajax({
	        url: '../TSysrole/update.do',
	        type:'post',
	        contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	        data: param,
	        success: function(result){
	            if (result.iserror){
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.error(result.message,'修改角色');
	            } else {
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.success(result.message, '修改角色');
	            	top.Addtabs.closeAndReload('修改角色');
	            }
	        }
	    });
	}
	
}
</script>
</body>
</html>
