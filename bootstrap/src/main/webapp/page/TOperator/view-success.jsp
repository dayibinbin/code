<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>查看操作员</title>
    <%@ include file="../Common/meta.jsp" %>
</head>
<body>
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
          <label class="view-lab">登录名称：${toperator.loginName}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">真实姓名：${toperator.realName}</label>
        </div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
       	  <label class="view-lab">性别：
			<s:choose>
			<s:when test="${toperator.gender == 1}">男</s:when>
           	<s:otherwise>女</s:otherwise>
           	</s:choose>
          </label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">邮箱：${toperator.email}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">电话：${toperator.phone}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">移动手机：${toperator.mobile}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">传真：${toperator.fax}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">QQ：${toperator.qq}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">登录IP：${toperator.loginIp}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">登录时间：<fmt:formatDate value="${toperator.loginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">更新时间：<fmt:formatDate value="${toperator.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">创建时间：<fmt:formatDate value="${toperator.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
        </div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">状态：
        	<s:choose>
        	<s:when test="${toperator.status == 1}">启用</s:when>
        	<s:when test="${toperator.status == 0}">停用</s:when>
            <s:otherwise>未定义</s:otherwise>
            </s:choose>
          </label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">角色：
        	<s:if test="${roleNames!=null && !roleNames.isEmpty()}">
        		<s:forEach var="v" items="${roleNames}">
        			[<s:out value="${v}"/>]
        		</s:forEach>
        	</s:if>
          </label>
      	</div>
      </div>
    </div>
    <div class="row">
   	  <div style="text-align:center" class="col-md-12">
	    <button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('查看操作员')">关闭</button>
	  </div>
	</div>
</section>
<%@ include file="../Common/script.jsp" %>
</body>
</html>
