<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>操作员管理</title>
    <%@ include file="../Common/meta.jsp" %>
</head>
<body>
<section class="content">
  <div id="control" class="row" style="margin-top:-10px">
    <div class="col-xs-12" style="margin-bottom:-15px;position:relative;padding-right:5px;padding-left:5px">
      <div class="box box-primary">
        <div class="box-header with-border">
          <div class="pull-left">
          	<s:if test="${privList!=null && !privList.isEmpty()}">
            	<s:forEach var="priv" items="${privList}">
            		<span class="operateBtn"><a class="<s:out value="${priv.iconCls}"/>" href="javascript:<s:out value="${priv.method}"/>;" >
          			<i class="fa fa-<s:out value="${priv.icon}"/>"></i>&nbsp;<s:out value="${priv.name}"/></a></span>
            	</s:forEach>
            </s:if>
            <!-- <span class="operateBtn"><a class="btn btn-primary" href="javascript:add();" >
          			<i class="fa fa-plus"></i>&nbsp;新增</a></span> -->
    	  </div>
          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
          </div>
        </div>
      	<div class="box-body">
   		    <div class="form-inline col-xs-12">
			  <div class="form-group">
			    <label for="loginNameS">登录名:</label>
			    <input type="text" class="form-control input-sm" name="loginNameS" id="loginNameS">
			  </div>
			  <div class="form-group">
			    <label for="realNameS">真实姓名:</label>
			    <input type="text" class="form-control input-sm" name="realNameS" id="realNameS">
			  </div>
			  <div class="form-group">
			    <label for="statusS">状态:</label>
			    <select class="form-control input-sm" name="statusS" id="statusS">
                       	<option value="">全部</option>
           				<option value="1">启用</option>
           				<option value="0">停用</option>
           			</select>
			  </div>
			  <button onclick="search()" class="btn btn-default btn-sm pull-right"><i class="fa fa-search"></i>&nbsp;查询</button>
			</div>
        </div>           	
      </div>
      <!-- /.box -->
    </div>
    <!-- /.col -->
  </div>
  <!-- /.row -->
  <div class="row">
  	<div class="col-md-12 col-sm-12" style="margin-bottom:-15px;position:relative;padding-right:5px;padding-left:5px">
  	  <div>
		<table id="tb1"></table> 
		<div id="pager"></div>
	  </div>
  	</div>
  </div><!-- /.row -->
</section>
<%@ include file="../Common/script.jsp" %>
<script>
var postData;
var url = "../TOperator/getData.do";
var colNames = ['id', '登录名', '真实姓名', '邮箱', '电话', '创建时间', '状态' ];
var colModel = [ 
                {name : 'id',index : 'id',hidden: true}, 
                {name : 'loginName',index : 'loginName',width : 170}, 
                {name : 'realName',index : 'realName',width : 170}, 
                {name : 'email',index : 'email',width : 170}, 
                {name : 'mobile',index : 'mobile',width : 170}, 
                {name : 'createTime',index : 'createTime',width : 170,
                		formatter:"date", formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}},
                {name : 'status',index : 'status',width:170,
                		formatter:'select',editoptions:{value:"1:启用;0:停用"}}
              ];
var sortname = 'id';
var sortorder = 'asc';
$(function(){
    pageInit(url,postData,colNames,colModel,sortname,sortorder,true, false);
});
//搜索
function search(){
	var loginName = $("#loginNameS").val();
	var realName = $("#realNameS").val();
	var status = $("#statusS").val();
	postData = {loginName:loginName,
        		realName:realName,
        		status:status};
	$("#tb1").jqGrid('setGridParam',{  
	    datatype:'json',
	    postData:postData,
	    page:0
   	}).trigger("reloadGrid");
}
//查看详情
function view(){
    var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '查看操作员');
    	//dl('请选择操作项!');
		return;
    }
    if(selectedIDs.length > 1){
    	top.toastr.warning('不支持多个操作!', '查看操作员');
    	//dl('不支持多个操作!');
		return;
	}
	top.Addtabs.add({
	   id: '查看操作员',
       url: 'TOperator/view.do?id='+selectedIDs[0]
   	});
}
//新增
function add(){
	top.Addtabs.add({
	   id: '新增操作员',
       url: 'TOperator/add.do?menuName=<%=menuName%>'
   	});
} 
//删除
function remove(){
	var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '删除操作员');
		return;
    }
    var ids = selectedIDs.join(",");
    conf('删除操作员','确定删除吗?',
		function(dialogRef){
    		top.$('#loading').fadeIn(100);
    		$.ajax( {
				url : '../TOperator/remove.do',
				dataType:'json',
				data : {ids : ids},
				success : function(result) {
					dialogRef.close();
					if(!result.iserror){
						top.$("#loading").fadeOut(100);
						top.toastr.success(result.message, '删除操作员');	
					}else{
						top.$("#loading").fadeOut(100);
						top.toastr.error(result.message, '删除操作员');
					}
					search();
				},
				error : function(result) {
					dialogRef.close();
					top.toastr.error(result.message, '删除操作员');
				}
			}); 
		}
	);
}
//修改
function modify(){
	var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '修改操作员');
		return;
    }
    if(selectedIDs.length > 1){
    	top.toastr.warning('不支持多个操作!', '修改操作员');
		return;
	}
    top.Addtabs.add({
	   id: '修改操作员',
       url: 'TOperator/modify.do?id='+selectedIDs[0]+'&menuName=<%=menuName%>'
   	});
}
//启用
function on(){
    var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '启用操作员');
		return;
    }
    for(var i=0;i<selectedIDs.length;i++){
    	var rowData = $('#tb1').jqGrid('getRowData',selectedIDs[i]);
    	if(rowData.status == 1){
    		top.toastr.warning(rowData.loginName+'已是启用状态!', '启用操作员');
			return
        }
    }   
    top.$('#loading').fadeIn(100);
    var ids = selectedIDs.join(",");
    $.ajax( {
		url : '../TOperator/on.do',
		dataType:'json',
		data : {ids : ids},
		success : function(result) {
			var msg=result.message;
			if(!result.iserror){
				top.$("#loading").fadeOut(100);
				top.toastr.success(msg, '启用操作员');	
			}else{
				top.$("#loading").fadeOut(100);
				top.toastr.error(result.message, '启用操作员');
			}
			search();
		},
		error : function(result) {
			top.$("#loading").fadeOut(100);
			top.toastr.error(result.message, '启用操作员');
		}
	});
}
//停用
function off(){
    var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '停用操作员');
		return;
    }
    for(var i=0;i<selectedIDs.length;i++){
    	var rowData = $('#tb1').jqGrid('getRowData',selectedIDs[i]);
    	if(rowData.status == 0){
    		top.toastr.warning(rowData.loginName+"已是停用状态!", '停用操作员');
			return
        }
    }   
    var ids = selectedIDs.join(",");
    top.$('#loading').fadeIn(100);
    $.ajax( {
		url : '../TOperator/off.do',
		dataType:'json',
		data : {ids : ids},
		success : function(result) {
			var msg=result.message;
			if(!result.iserror){
				top.$("#loading").fadeOut(100);
				top.toastr.success(msg, '停用操作员');	
			}else{
				top.$("#loading").fadeOut(100);
				top.toastr.error(result.message, '停用操作员');
			}
			search();
		},
		error : function(result) {
			top.$("#loading").fadeOut(100);
			top.toastr.error(result.message, '停用操作员');
		}
	});
}
//密码重置
function resetPassword(){
	var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '密码重置');
		return;
    }
    var ids = selectedIDs.join(",");
    conf('重置密码','确定重置吗?',
		function(dialogRef){
    		top.$('#loading').fadeIn(100);
    		$.ajax( {
				url : '../TOperator/resetPassword.do',
				dataType:'json',
				data : {ids : ids},
				success : function(result) {
					dialogRef.close();
					if(!result.iserror){
						top.$("#loading").fadeOut(100);
						top.toastr.success(result.message, '密码重置');	
					}else{
						top.$("#loading").fadeOut(100);
						top.toastr.error(result.message, '密码重置');
					}
					search();
				},
				error : function(result) {
					top.$("#loading").fadeOut(100);
					dialogRef.close();
					top.toastr.error(result.message, '密码重置');
				}
			}); 
		}
	);
}
</script>
</body>
</html>