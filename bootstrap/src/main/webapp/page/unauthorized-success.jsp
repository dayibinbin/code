<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="Common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><%=title%></title>
  <%@ include file="Common/meta.jsp" %>
</head>
<body >
<div class="wrapper">
    <section class="content-header">
      <h1>
        401 Error Page
      </h1>
    </section>
    <!-- Main content -->
    <section class="content">
      <div class="error-page">
        <h2 class="headline text-yellow"> 401</h2>

        <div class="error-content">
          <h3><i class="fa fa-warning text-yellow"></i> 哎呀! 未授权.</h3>

          <p>
            	您没有权限访问所请求的资源，请联系管理员.
          </p>
        </div>
        <!-- /.error-content -->
      </div>
      <!-- /.error-page -->
    </section>
    <!-- /.content -->
</div>
<!-- ./wrapper -->
<%@ include file="Common/script.jsp" %>
</body>
</html>
