<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>查看登录日志</title>
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
          <label class="view-lab">登录名称：${tlogLogin.loginName}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">真实姓名：${tlogLogin.operatorName}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
   		  <label class="view-lab">类型：
   		   <s:choose>
	  		   <s:when test="${tlogLogin.type == 1}">登录</s:when>
	           <s:when test="${tlogLogin.type == 2}">注销</s:when>
	           <s:otherwise>未定义</s:otherwise>
           </s:choose>
       	  </label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
   		  <label class="view-lab">结果：
  		   <s:choose>
	  		   <s:when test="${tlogLogin.result == 1}">成功</s:when>
	           <s:when test="${tlogLogin.result == 0}">失败</s:when>
	           <s:otherwise>未定义</s:otherwise>
           </s:choose>
       	  </label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">时间：<fmt:formatDate value="${tlogLogin.time}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">IP地址：${tlogLogin.loginIp}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">描述：${tlogLogin.description}</label>
      	</div>
      </div>
    </div>
    <div class="row">
   	  <div style="text-align:center" class="col-md-12">
	    <button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('查看登录日志')">关闭</button>
	  </div>
	</div>
</section>
<%@ include file="../Common/script.jsp" %>
</body>
</html>
