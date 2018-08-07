<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>查看操作日志</title>
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
          <label class="view-lab">登录名称：${tlogOperate.loginName}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">真实姓名：${tlogOperate.operatorName}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">操作对象：${tlogOperate.operateObject}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">操作值：${tlogOperate.operateValue}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">操作类型：${tlogOperate.operateType}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
   		  <label class="view-lab">操作结果：
  		   <s:choose>
	           	<s:when test="${tlogOperate.operateResult == 1}">
	           	成功
	           	</s:when>
	           	<s:otherwise>
	           	失败
	           	</s:otherwise>
	       </s:choose>
       	  </label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">操作时间：<fmt:formatDate value="${tlogOperate.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
      	</div>
      </div>
    </div>
    <div class="row">
   	  <div style="text-align:center" class="col-md-12">
	    <button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('查看操作日志')">关闭</button>
	  </div>
	</div>
</section>
<%@ include file="../Common/script.jsp" %>
</body>
</html>
