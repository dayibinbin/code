<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>权限管理</title>
    <%@ include file="../Common/meta.jsp" %>
</head>
<body>
<section class="content">
<div class="col-xs-12 col-md-2" style="position:relative;padding-right:15px;padding-left:5px">
	<div class="row" style="margin-top:-10px">
	  <div class="col-xs-12" style="margin-bottom:-15px;position:relative;padding-right:0px;padding-left:0px;padding-bottom:0px">
      <div class="box box-primary" style="margin-bottom:0px;">
        <div class="box-header with-border" style="height:50px;line-height: 25px">
          <div style="text-align:center">
          	<label class="view-lab"><i class="fa fa-sort-amount-asc"></i>&nbsp;系统菜单</label>
    	  </div>
        </div>
      	<div class="box-body" id="tree-content" style="overflow:auto;overflow-x:hidden;">
      	  <ul id="tree" class="ztree" style="overflow:auto;"></ul>
        </div>           	
      </div>
    </div>
	</div>
</div>
<div class="col-xs-12 col-md-10" style="position:relative;padding-right:5px;padding-left:15px">
  <div id="control" class="row" style="margin-top:-10px;">
    <div class="col-xs-12" style="margin-bottom:-15px;position:relative;padding-right:0px;padding-left:0px;padding-bottom:0px">
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
   		    <div class="form-inline col-xs-12 col-xs-12">
			  <div class="form-group">
			    <label for="nameS">权限名称:</label>
			    <input type="text" class="form-control input-sm" name="nameS" id="nameS">
			  </div>
			  <div class="form-group">
			    <label for="codeS">权限代码:</label>
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
  	<div class="col-md-12 col-sm-12" style="margin-bottom:-15px;position:relative;padding-right:0px;padding-left:0px">
  	  <div>
		<table id="tb1"></table> 
		<div id="pager"></div>
	  </div>
  	</div>
  </div><!-- /.row -->
</div>
  
</section>
<%@ include file="../Common/script.jsp" %>
<script>
var postData;
var url = "../TSyspriv/getData.do"
var colNames = ['id', '权限名称', '权限代码', '图标样式', '方法名称', '菜单名称', '创建时间', '序列号', '状态' ];
var colModel = [ 
                {name : 'id',index : 'id',hidden: true}, 
                {name : 'name',index : 'name',width:112}, 
                {name : 'code',index : 'code',width:112}, 
                {name : 'iconCls',index : 'iconCls',width:112}, 
                {name : 'method',index : 'method',width:112}, 
                {name : 'menu.name',index : 'menu',width:112}, 
                {name : 'createTime',index : 'createTime',width:150,
                		formatter:"date", formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}}, 
                {name : 'sequence',index : 'sequence', width:60}, 
                {name : 'status',index : 'status', width:60,
                		formatter:'select',editoptions:{value:"1:启用;0:停用"}}
              ];
var sortname = 'sequence';
var sortorder = 'asc';
var menuId = 0;
$(function(){
	var p_window = window.top;
    var height = $(p_window).innerHeight();  
	$("#tree-content").height(height-193);
	initTree();
	postData = {menuId:menuId};
	pageInit(url,postData,colNames,colModel,sortname,sortorder,true,false);
    $(window).resize(function(){   
    	$("#tb1").setGridWidth($(window).width()*0.82);
    	//$("#tb1").setGridWidth(document.body.clientWidth*0.99);
    });
});

function zTreeOnClick(event, treeId, treeNode) {
    if(treeNode.getParentNode() != null){
    	menuId = treeNode.id;
    	search();
    }
};
//初始化菜单栏
function initTree() {
	//ztree配置
	var setting_menu = {
	    view: {
	        showLine: false,
	    },
	   callback: {
		   onClick: zTreeOnClick
	    }
	};
	$.ajax({
		url: '../TSyspriv/getMenu.do',
		async:false,
        type:'post',
        datatype: 'json',
        success: function(result){
        	var json = eval(result);
        	var t = $("#tree"); 
            t = $.fn.zTree.init(t, setting_menu, json);
            var nodes = t.getNodes();
            for(var i=0;i<nodes.length;i++){
            	if(menuId == 0){
            		menuId = nodes[i].children[0].id;
            		t.selectNode(nodes[i].children[0]);
            	}
            	/* if(!nodes[i].open){
            		t.expandNode(nodes[i]);	
            	} */
            }
        },
        error: function (result) {
        	top.toastr.error('菜单获取失败!', '菜单');
        }
	})
}
//搜索
function search(){
	var name = $("#nameS").val();
	var code = $("#codeS").val();
	var status = $("#statusS").val();
	postData = {menuId:menuId,
				name:name,
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
    	top.toastr.warning('请选择操作项!', '查看权限');
    	//dl('请选择操作项!');
		return;
    }
    if(selectedIDs.length > 1){
    	top.toastr.warning('不支持多个操作!', '查看权限');
    	//dl('不支持多个操作!');
		return;
	}
	top.Addtabs.add({
	   id: '查看权限',
       url: 'TSyspriv/view.do?id='+selectedIDs[0]
   	});
}
//新增
function add(){
	top.Addtabs.add({
	   id: '新增权限',
       url: 'TSyspriv/add.do?menuId=' + menuId+'&menuName=<%=menuName%>'
   	});
} 
//删除
function remove(){
	var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '删除权限');
		return;
    }
    var ids = selectedIDs.join(",");
    conf('删除权限','确定删除吗?',
		function(dialogRef){
    		top.$('#loading').fadeIn(100);
    		$.ajax( {
				url : '../TSyspriv/remove.do',
				dataType:'json',
				data : {ids : ids},
				success : function(result) {
					dialogRef.close();
					if(!result.iserror){
						top.$("#loading").fadeOut(100);
						top.toastr.success(result.message, '删除权限');
					}else{
						top.$("#loading").fadeOut(100);
						top.toastr.error(result.message, '删除权限');
					}
					search();
				},
				error : function(result) {
					top.$("#loading").fadeOut(100);
					dialogRef.close();
					top.toastr.error(result.message, '删除权限');
				}
			}); 
		}
	);
}
//修改
function modify(){
	var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '修改权限');
		return;
    }
    if(selectedIDs.length > 1){
    	top.toastr.warning('不支持多个操作!', '修改权限');
		return;
	}
    top.Addtabs.add({
	   id: '修改权限',
       url: 'TSyspriv/modify.do?id='+selectedIDs[0]+'&menuName=<%=menuName%>'
   	});
}
//启用
function on(){
    var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '启用权限');
		return;
    }
    for(var i=0;i<selectedIDs.length;i++){
    	var rowData = $('#tb1').jqGrid('getRowData',selectedIDs[i]);
    	if(rowData.status == 1){
    		top.toastr.warning(rowData.name+'已是启用状态!', '启用权限');
			return
        }
    }   
    var ids = selectedIDs.join(",");
    top.$('#loading').fadeIn(100);
    $.ajax( {
		url : '../TSyspriv/on.do',
		dataType:'json',
		data : {ids : ids},
		success : function(result) {
			var msg=result.message;
			if(!result.iserror){
				top.$("#loading").fadeOut(100);
				top.toastr.success(msg, '启用权限');
			}else{
				top.$("#loading").fadeOut(100);
				top.toastr.error(result.message, '启用权限');
			}
			search();
		},
		error : function(result) {
			top.$("#loading").fadeOut(100);
			top.toastr.error(result.message, '启用权限');
		}
	});
}
//停用
function off(){
    var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '停用权限');
		return;
    }
    for(var i=0;i<selectedIDs.length;i++){
    	var rowData = $('#tb1').jqGrid('getRowData',selectedIDs[i]);
    	if(rowData.status == 0){
    		top.toastr.warning(rowData.name+"已是停用状态!", '停用权限');
			return
        }
    }   
    var ids = selectedIDs.join(",");
    top.$('#loading').fadeIn(100);
    $.ajax( {
		url : '../TSyspriv/off.do',
		dataType:'json',
		data : {ids : ids},
		success : function(result) {
			var msg=result.message;
			if(!result.iserror){
				top.$("#loading").fadeOut(100);
				top.toastr.success(msg, '停用权限');
			}else{
				top.$("#loading").fadeOut(100);
				top.toastr.error(result.message, '停用权限');
			}
			search();
		},
		error : function(result) {
			top.$("#loading").fadeOut(100);
			top.toastr.error(result.message, '停用权限');
		}
	});
}
</script>
</body>
</html>