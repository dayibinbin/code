<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>系统配置</title>
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
			    <label for="paramNameS">配置名称:</label>
			    <input type="text" class="form-control input-sm" name="paramNameS" id="paramNameS">
			  </div>
			  <div class="form-group">
			    <label for="paramCodeS">配置代码:</label>
			    <input type="text" class="form-control input-sm" name="paramCodeS" id="paramCodeS">
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
var url = "../TSysconfig/getData.do";
var colNames = ['id', '配置名称', '配置代码', '配置值', '创建时间'];
var colModel = [ 
                {name : 'id',index : 'id',hidden: true}, 
                {name : 'paramName',index : 'paramName',width:255}, 
                {name : 'paramCode',index : 'paramCode',width:255},
                {name : 'paramValue',index : 'paramValue',width:255},
                {name : 'createTime',index : 'createTime',width:255,
                	formatter:"date", formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}},
              ];
var sortname = 'id';
var sortorder = 'asc';
$(function(){
    pageInit(url,postData,colNames,colModel,sortname,sortorder,true,false);
});
//搜索
function search(){
	var paramName = $("#paramNameS").val();
	var paramCode = $("#paramCodeS").val();
	postData = {paramName:paramName,
				paramCode:paramCode,};
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
    	top.toastr.warning('请选择操作项!', '查看系统配置');
    	//dl('请选择操作项!');
		return;
    }
    if(selectedIDs.length > 1){
    	top.toastr.warning('不支持多个操作!', '查看系统配置');
    	//dl('不支持多个操作!');
		return;
	}
	top.Addtabs.add({
	   id: '查看系统配置',
       url: 'TSysconfig/view.do?id='+selectedIDs[0]
   	});
}
//新增
function add(){
	top.Addtabs.add({
	   id: '新增系统配置',
       url: 'TSysconfig/add.do?menuName=<%=menuName%>'
   	});
} 
//删除
function remove(){
	var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '删除系统配置');
		return;
    }
    var ids = selectedIDs.join(",");
    conf('删除系统配置','确定删除吗?',
		function(dialogRef){
    		top.$('#loading').fadeIn(100);
    		$.ajax( {
				url : '../TSysconfig/remove.do',
				dataType:'json',
				data : {ids : ids},
				success : function(result) {
					dialogRef.close();
					if(!result.iserror){
						top.$("#loading").fadeOut(100);
						top.toastr.success(result.message, '删除系统配置');	
					}else{
						top.$("#loading").fadeOut(100);
						top.toastr.error(result.message, '删除系统配置');
					}
					search();
				},
				error : function(result) {
					dialogRef.close();
					top.toastr.error(result.message, '删除系统配置');
				}
			}); 
		}
	);
}
//修改
function modify(){
	var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '修改系统配置');
		return;
    }
    if(selectedIDs.length > 1){
    	top.toastr.warning('不支持多个操作!', '修改系统配置');
		return;
	}
    top.Addtabs.add({
	   id: '修改系统配置',
       url: 'TSysconfig/modify.do?id='+selectedIDs[0]+'&menuName=<%=menuName%>'
   	});
}
</script>
</body>
</html>