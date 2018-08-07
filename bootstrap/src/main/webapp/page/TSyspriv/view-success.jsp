<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>查看权限</title>
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
          <label class="view-lab">所属菜单：${tsyspriv.menu.name}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">权限名称：${tsyspriv.name}</label>
        </div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
   		  <label class="view-lab">权限代码：${tsyspriv.code}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
   		  <label class="view-lab">按钮样式：
   		  	<s:choose>
   		  	<s:when test="${tsyspriv.iconCls =='btn btn-default'}"><button class="btn btn-sm btn-default">样式1</button></s:when>
        	<s:when test="${tsyspriv.iconCls =='btn btn-primary'}"><button class="btn btn-sm btn-primary">样式2</button></s:when>
        	<s:when test="${tsyspriv.iconCls =='btn btn-success'}"><button class="btn btn-sm btn-success">样式3</button></s:when>
        	<s:when test="${tsyspriv.iconCls =='btn btn-info'}"><button class="btn btn-sm btn-info">样式4</button></s:when>
        	<s:when test="${tsyspriv.iconCls =='btn btn-danger'}"><button class="btn btn-sm btn-danger">样式5</button></s:when>
        	<s:when test="${tsyspriv.iconCls =='btn btn-warning'}"><button class="btn btn-sm btn-warning">样式6</button></s:when>
            <s:otherwise>未定义</s:otherwise>
            </s:choose>
   		  </label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
   		  <label class="view-lab">图标样式：<i class='fa fa-${tsyspriv.icon}'></i></label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
   		  <label class="view-lab">方法名称：${tsyspriv.method}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
   		  <label class="view-lab">序列号：${tsyspriv.sequence}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">创建时间：<fmt:formatDate value="${tsyspriv.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
        </div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">状态：
        	<s:choose>
        	<s:when test="${tsyspriv.status == 1}">启用</s:when>
        	<s:when test="${tsyspriv.status == 0}">停用</s:when>
            <s:otherwise>未定义</s:otherwise>
            </s:choose>
          </label>
      	</div>
      </div>
    </div>
    <div class="row">
   	  <div style="text-align:center" class="col-md-12">
	    <button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('查看权限')">关闭</button>
	  </div>
	</div>
</section>
<%@ include file="../Common/script.jsp" %>
</body>
</html>
