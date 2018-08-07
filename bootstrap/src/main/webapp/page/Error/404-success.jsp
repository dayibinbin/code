<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><%=title%></title>
  <%@ include file="../Common/meta.jsp" %>
</head>
<body >
<div class="wrapper">
    <section class="content-header">
      <h1>
        404 Error Page
      </h1>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="error-page">
        <h2 class="headline text-yellow"> 404</h2>

        <div class="error-content">
          <h3><i class="fa fa-warning text-yellow"></i> 哎呀! 页面未找到.</h3>

          <p>
            	您访问的页面不存在.
          </p>
        </div>
        <!-- /.error-content -->
      </div>
      <!-- /.error-page -->
    </section>
    <!-- /.content -->
</div>
<!-- ./wrapper -->
<%@ include file="../Common/script.jsp" %>
</body>
</html>