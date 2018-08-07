<%@ page language="java"
	import="java.util.*,cn.com.checknull.model.po.TOperator"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="main-header">
    <!-- Logo -->
    <a class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>PF</b></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b><%=title%></b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="static/bootstrap/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs">
              	<%=((TOperator) request.getSession().getAttribute("operator")).getLoginName()%>
              </span>
            </a>
            <ul class="dropdown-menu drp-mnu">
              <!-- User image -->
              <li class="user-header">
                <img src="static/bootstrap/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                <p>
                  <%=((TOperator) request.getSession().getAttribute("operator")).getRealName()%>
                </p>
              </li>
              <!-- Menu Body -->
              <li class="user-body">
                <div class="row">
                  <div class="col-xs-6 text-center">
                    <a href="javascript:void(0);" onclick="viewSelf(<%=((TOperator) request.getSession().getAttribute("operator")).getId()%>)"><i class="fa fa-user"></i>个人信息</a>
                  </div>
                  <div class="col-xs-6 text-center pull-right">
                    <a href="javascript:void(0);" onclick="modifyPassword(<%=((TOperator) request.getSession().getAttribute("operator")).getId()%>)"><i class="fa fa-key"></i>密码修改</a>
                  </div>
                </div>
                <!-- /.row -->
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-right">
                  <a href="loginOut.do" class="btn btn-primary btn-sm btn-flat"><i class="fa fa-sign-out"></i>注销</a>
                </div>
              </li>
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="#" data-toggle="control-sidebar"><i class="fa fa-cogs"></i></a>
          </li>
        </ul>
      </div>
    </nav>
    
    <script type="text/javascript">
  	//查看详情
    function viewSelf(id){
    	top.Addtabs.add({
    	   id: '查看个人信息',
           url: 'TOperator/viewSelf.do?id='+id
       	});
    }
    
  	//信息设置
    function modifySelf(id){
    	top.Addtabs.add({
    	   id: '修改个人信息',
           url: 'TOperator/modifySelf.do?id='+id
       	});
    }
  	
  	//修改密码
    function modifyPassword(id){
    	top.Addtabs.add({
    	   id: '修改个人密码',
           url: 'TOperator/modifyPassword.do?id='+id
       	});
    }
  
    </script>
  </header>