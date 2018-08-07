<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head >
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>菜单管理</title>
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
    	  </div>
          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
          </div>
        </div>
      	<div class="box-body">
   		    <div class="form-inline col-xs-12">
			  <div class="form-group">
			    <label for="nameS">菜单名称:</label>
			    <input type="text" class="form-control input-sm" name="nameS" id="nameS">
			  </div>
			  <div class="form-group">
			    <label for="codeS">菜单代码:</label>
			    <input type="text" class="form-control input-sm" name="codeS" id="codeS">
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
var url = "../TSysmenu/getData.do";
var colNames = ['id', '菜单名称', '菜单代码', '导航地址','菜单等级', '父级菜单', '创建时间', '序列号', '状态' ];
var colModel = [ 
                {name : 'id',index : 'id',hidden: true}, 
                {name : 'name',index : 'name',width:127.5}, 
                {name : 'code',index : 'code',width:127.5}, 
                {name : 'navigateUrl',index : 'navigateUrl',width:127.5},
                {name : 'level',index : 'level',width:127.5,
            		formatter:'select',editoptions:{value:"1:一级;2:二级"}},
                {name : 'parentMenu.name',index : 'parentMenu.name',width:127.5,sortable:false},
                {name : 'createTime',index : 'createTime',width:127.5,
                	formatter:"date", formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}},
                {name : 'sequence',index : 'sequence',width:127.5}, 
                {name : 'status',index : 'status',width:127.5,
                		formatter:'select',editoptions:{value:"1:启用;0:停用"}}
              ];
var sortname = 'sequence';
var sortorder = 'asc';
$(function(){
    pageInit(url,postData,colNames,colModel,sortname,sortorder,true,false);
});
//搜索
function search(){
	var name = $("#nameS").val();
	var code = $("#codeS").val();
	var status = $("#statusS").val();
	postData = {name:name,
        		code:code,
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
    	top.toastr.warning('请选择操作项!', '查看菜单');
    	//dl('请选择操作项!');
		return;
    }
    if(selectedIDs.length > 1){
    	top.toastr.warning('不支持多个操作!', '查看菜单');
    	//dl('不支持多个操作!');
		return;
	}
	top.Addtabs.add({
	   id: '查看菜单',
       url: 'TSysmenu/view.do?id='+selectedIDs[0]
   	});
}
//菜单排序
function sort(){
	top.Addtabs.add({
	   id: '菜单排序',
       url: 'TSysmenu/sort.do?menuName=<%=menuName%>'
   	});
} 
//新增
function add(){
	top.Addtabs.add({
	   id: '新增菜单',
       url: 'TSysmenu/add.do?menuName=<%=menuName%>'
   	});
} 
//删除
function remove(){
	var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '删除菜单');
		return;
    }
    var ids = selectedIDs.join(",");
    conf('删除菜单','确定删除吗?',
		function(dialogRef){
    		top.$('#loading').fadeIn(100);
    		$.ajax( {
				url : '../TSysmenu/remove.do',
				dataType:'json',
				data : {ids : ids},
				success : function(result) {
					dialogRef.close();
					if(!result.iserror){
						top.$("#loading").fadeOut(100);
						top.toastr.success(result.message, '删除菜单');
					}else{
						top.$("#loading").fadeOut(100);
						top.toastr.error(result.message, '删除菜单');
					}
					search();
				},
				error : function(result) {
					top.$("#loading").fadeOut(100);
					dialogRef.close();
					top.toastr.error(result.message, '删除菜单');
				}
			}); 
		}
	);
}
//修改
function modify(){
	var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '修改菜单');
		return;
    }
    if(selectedIDs.length > 1){
    	top.toastr.warning('不支持多个操作!', '修改菜单');
		return;
	}
    top.Addtabs.add({
	   id: '修改菜单',
       url: 'TSysmenu/modify.do?id='+selectedIDs[0]+'&menuName=<%=menuName%>'
   	});
}
//启用
function on(){
    var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '启用菜单');
		return;
    }
    for(var i=0;i<selectedIDs.length;i++){
    	var rowData = $('#tb1').jqGrid('getRowData',selectedIDs[i]);
    	if(rowData.status == 1){
    		top.toastr.warning(rowData.name+'已是启用状态!', '启用菜单');
			return
        }
    }   
    var ids = selectedIDs.join(",");
    top.$('#loading').fadeIn(100);
    $.ajax( {
		url : '../TSysmenu/on.do',
		dataType:'json',
		data : {ids : ids},
		success : function(result) {
			var msg=result.message;
			if(!result.iserror){
				top.$("#loading").fadeOut(100);
				top.toastr.success(msg, '启用菜单');	
			}else{
				top.$("#loading").fadeOut(100);
				top.toastr.error(result.message, '启用菜单');
			}
			search();
		},
		error : function(result) {
			top.$("#loading").fadeOut(100);
			top.toastr.error(result.message, '启用菜单');
		}
	});
}
//停用
function off(){
    var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '停用菜单');
		return;
    }
    for(var i=0;i<selectedIDs.length;i++){
    	var rowData = $('#tb1').jqGrid('getRowData',selectedIDs[i]);
    	if(rowData.status == 0){
    		top.toastr.warning(rowData.name+"已是停用状态!", '停用菜单');
			return
        }
    }   
    var ids = selectedIDs.join(",");
    top.$('#loading').fadeIn(100);
    $.ajax( {
		url : '../TSysmenu/off.do',
		dataType:'json',
		data : {ids : ids},
		success : function(result) {
			var msg=result.message;
			if(!result.iserror){
				top.$("#loading").fadeOut(100);
				top.toastr.success(msg, '停用菜单');	
			}else{
				top.$("#loading").fadeOut(100);
				top.toastr.error(result.message, '停用菜单');
			}
			search();
		},
		error : function(result) {
			top.$("#loading").fadeOut(100);
			top.toastr.error(result.message, '停用菜单');
		}
	});
}
</script>
</body>
</html>