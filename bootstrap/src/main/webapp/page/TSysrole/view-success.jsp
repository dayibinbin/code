<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>查看角色</title>
    <%@ include file="../Common/meta.jsp" %>
</head>
<body>
<input type="hidden" id="treeIds" name="treeIds" value="${treeIds }">
<section class="content">
	<!-- <h3><i class="fa fa-list-alt"></i>基本信息</h3> -->
	<div class="box box-primary baseInfo">
      <div class="box-header with-border">
        <i class="fa fa-file"></i><label class="view-lab">&nbsp;基本信息</label>
        <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
	    </div>
      </div>
      <div class="box-body">
        <div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">角色名称：${tsysrole.name }</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">描述：${tsysrole.description }</label>
        </div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">创建时间：<fmt:formatDate value="${tsysrole.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
        </div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">状态：
        	<s:choose>
        	<s:when test="${tsysrole.status == 1}">启用</s:when>
        	<s:when test="${tsysrole.status == 0}">停用</s:when>
            <s:otherwise>未定义</s:otherwise>
            </s:choose>
          </label>
      	</div>
      </div>
    </div>
    <div class="box box-primary baseInfo">
      <div class="box-header with-border">
        <i class="fa fa-file"></i><label class="view-lab">&nbsp;拥有权限</label>
        <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
	    </div>
      </div>
      <div class="box-body">
      	<div class="view-header with-border">
      		<ul id="tree" class="ztree view-lab"></ul>
   	   	</div>
      </div>
    </div>
    <div class="row">
   	  <div style="text-align:center" class="col-md-12">
	    <button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('查看角色')">关闭</button>
	  </div>
	</div>
</section>
<%@ include file="../Common/script.jsp" %>
<script>
$(function(){
	initTree();
});
//初始化ztree
function initTree() {
	var treeIds = $("#treeIds").val();
	$.ajax({
		url: '../TSysrole/getPrivTree.do',
        type:'post',
        //data: {view: "true"},
        datatype: 'json',
        success: function(result){
        	var json = result;
        	var t = $("#tree"); 
            t = $.fn.zTree.init(t, setting, json);
            var treeObj=$.fn.zTree.getZTreeObj("tree");
            var ids = treeIds.split(",");
            var nodes = t.getNodes();
            for(var j=0;j<ids.length;j++) {
            	t.checkNode(t.getNodeByParam('id',ids[j]), true );
            	//t.expandNode(t.getNodeByParam('id',ids[i]));
            	t.setChkDisabled(t.getNodeByParam('id',ids[j]), true)
            }
            for(var i=0;i<nodes.length;i++){
            	t.setChkDisabled(nodes[i], true)
            }
        },
        error: function (result) {
        	top.toastr.error('权限列表获取失败!', '用户权限');
        }
	})
}
</script>
</body>
</html>
