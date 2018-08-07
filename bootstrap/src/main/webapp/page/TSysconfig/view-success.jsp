<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>查看系统配置</title>
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
          <label class="view-lab">配置名称：${tsysconfig.paramName}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">配置代码：${tsysconfig.paramCode}</label>
        </div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">配置值：${tsysconfig.paramValue}</label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">创建时间：<fmt:formatDate value="${tsysconfig.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
      	</div>
      	<div class="view-header with-border">
      	  <i style="color: #3c8dbc" class="fa fa-chevron-right"></i>
          <label class="view-lab">配置描述：${tsysconfig.description}</label>
      	</div>
      </div>
    </div>
    <div class="row">
   	  <div style="text-align:center" class="col-md-12">
	    <button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('查看系统配置')">关闭</button>
	  </div>
	</div>
</section>
<%@ include file="../Common/script.jsp" %>
</body>
</html>
