<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>查看菜单</title>
    <%@ include file="../Common/meta.jsp" %>
</head>
<body>
<section class="content">
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
          <label class="view-lab">菜单名称：${tsysmenu.name}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">菜单图标：</label><i class='fa fa-${tsysmenu.icon}'></i>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
   		  <label class="view-lab">菜单等级：
   		  	<s:choose>
        	<s:when test="${tsysmenu.level == 1}">一级</s:when>
        	<s:when test="${tsysmenu.level == 2}">二级</s:when>
            <s:otherwise>未定义</s:otherwise>
            </s:choose>
       	  </label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">创建时间：<fmt:formatDate value="${tsysmenu.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">序列号：${tsysmenu.sequence}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">状态：
           <s:choose>
        	<s:when test="${tsysmenu.status == 1}">启用</s:when>
        	<s:when test="${tsysmenu.status == 0}">停用</s:when>
            <s:otherwise>未定义</s:otherwise>
            </s:choose>
          </label>
      	</div>
      </div>
    </div>
    <div class="box box-primary" style="display:none" id="childDetail">
      <div class="box-header with-border">
        <i class="fa fa-file"></i><label class="view-lab">&nbsp;二级菜单详细信息</label>
        <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
	    </div>
      </div>
      <div class="box-body">
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">菜单代码：${tsysmenu.code}</label>
        </div>
        <div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">导航地址：${tsysmenu.navigateUrl}</label>
        </div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">父级菜单：${tsysmenu.parentMenu.name}</label>
        </div>
      </div>
    </div>
    <div class="box box-primary" style="display:none" id="childMenus">
      <div class="box-header with-border">
        <i class="fa fa-file"></i><label class="view-lab">&nbsp;二级菜单</label>
        <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
	    </div>
      </div>
      <div class="box-body">
      	<s:if test="${tsysmenus!=null && !tsysmenus.isEmpty()}">
       		<s:forEach var="tmp" items="${tsysmenus}">
       			<div class="view-header with-border">
		   		   <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
		            <label class="view-lab">菜单名称:
		        	 [<s:out value="${tmp.name}"/>]
		           </label>
		   		  </div>
       		</s:forEach>
        </s:if>
        <%-- <s:iterator value="tsysmenus" var="tmp">
   		  <div class="view-header with-border">
   		   <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
            <label class="view-lab">菜单名称:
        	 <s:property value="#tmp.name"/>
           </label>
   		  </div>
   	   	</s:iterator> --%>
      </div>
    </div>
    <div class="row">
   	  <div style="text-align:center" class="col-md-12">
	    <button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('查看菜单')">关闭</button>
	  </div>
	</div>
</section>
<%@ include file="../Common/script.jsp" %>
<script>
$(function(){
	var menuLevel = ${tsysmenu.level};
	if(menuLevel == 1){
		$("#childMenus").slideDown();
	}else if(menuLevel == 2){
		$("#childDetail").slideDown();
	}
})
</script>
</body>
</html>
