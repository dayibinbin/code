<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>操作日志</title>
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
			    <label for="loginNameS">登录名称:</label>
			    <input type="text" class="form-control input-sm" name="loginNameS" id="loginNameS">
			  </div>
			  <div class="form-group">
			    <label for="operatorNameS">真实姓名:</label>
			    <input type="text" class="form-control input-sm" name="operatorNameS" id="operatorNameS">
			  </div>
			  <div class="form-group">
			    <label for="operateObjectS">操作对象:</label>
			    <input type="text" class="form-control input-sm" name="operateObjectS" id="operateObjectS">
			  </div>
			  <div class="form-group">
			    <label for="operateResultS">操作结果:</label>
			    <select class="form-control input-sm" name="operateResultS" id="operateResultS">
                       	<option value="">全部</option>
           				<option value="1">成功</option>
           				<option value="0">失败</option>
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
var url = "../TLogOperate/getData.do";
var colNames = ['id', '登录名称', '真实姓名', '操作对象', '操作类型','操作结果', '操作时间'];
var colModel = [ 
                {name : 'id',index : 'id',hidden: true}, 
                {name : 'loginName',index : 'loginName',width:170}, 
                {name : 'operatorName',index : 'operatorName',width:170}, 
                {name : 'operateObject',index : 'operateObject',width:170},
                {name : 'operateType',index : 'operateType',width:170},
           		{name : 'operateResult',index : 'operateResult',width:170,
               		formatter:'select',editoptions:{value:"1:成功;0:失败"}},
                {name : 'operateTime',index : 'time',width:170,
                	formatter:"date", formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}}
              ];
var sortname = 'id';
var sortorder = 'desc';
$(function(){
    pageInit(url,postData,colNames,colModel,sortname,sortorder,true,false);
});
//搜索
function search(){
	var loginName = $("#loginNameS").val();
	var operatorName = $("#operatorNameS").val();
	var operateObject = $("#operateObjectS").val();
	var operateResult = $("#operateResultS").val();
	postData = {loginName:loginName,
				operatorName:operatorName,
				operateObject:operateObject,
				operateResult:operateResult};
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
    	top.toastr.warning('请选择操作项!', '查看操作日志');
    	//dl('请选择操作项!');
		return;
    }
    if(selectedIDs.length > 1){
    	top.toastr.warning('不支持多个操作!', '查看操作日志');
    	//dl('不支持多个操作!');
		return;
	}
	top.Addtabs.add({
	   id: '查看操作日志',
       url: 'TLogOperate/view.do?id='+selectedIDs[0]
   	});
}
//删除
function remove(){
	var selectedIDs = $('#tb1').getGridParam("selarrrow");  
    if(selectedIDs.length == 0){
    	top.toastr.warning('请选择操作项!', '删除操作日志');
		return;
    }
    var ids = selectedIDs.join(",");
    conf('删除操作日志','确定删除吗?',
		function(dialogRef){
    		top.$('#loading').fadeIn(100);
    		$.ajax( {
				url : '../TLogOperate/remove.do',
				dataType:'json',
				data : {ids : ids},
				success : function(result) {
					dialogRef.close();
					if(!result.iserror){
						top.$("#loading").fadeOut(100);
						top.toastr.success(result.message, '删除操作日志');
					}else{
						top.$("#loading").fadeOut(100);
						top.toastr.error(result.message, '删除操作日志');
					}
					search();
				},
				error : function(result) {
					top.$("#loading").fadeOut(100);
					dialogRef.close();
					top.toastr.error(result.message, '删除操作日志');
				}
			}); 
		}
	);
}
</script>
</body>
</html>