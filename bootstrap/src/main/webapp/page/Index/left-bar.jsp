<%@ page language="java"
	import="java.util.*,cn.com.checknull.model.po.TOperator"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
    <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="static/bootstrap/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p><%=((TOperator) request.getSession().getAttribute("operator")).getRealName()%></p>
          <a href="#"><i class="fa fa-circle text-success"></i> 已登录</a>
        </div>
      </div>
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="topbar sidebar-menu">
        <li class="header">导航菜单</li>
        <s:if test="${tsysmenus!=null && !tsysmenus.isEmpty()}">
  		  <s:forEach var="tsysmenu" items="${tsysmenus}">
		  	<s:if test="${tsysmenu.level == 1}">
			  <li class="treeview">
			    <a href="#">
            	  <i class='fa fa-<s:out value="${tsysmenu.icon}"/>'></i> &nbsp;&nbsp;<span><s:out value="${tsysmenu.name}"/></span> <i class="fa fa-angle-left pull-right"></i>
         	 	</a>
         	 	<ul class="treeview-menu">
					<s:forEach var="tsysmenu2" items="${tsysmenus}">
					  <s:if test="${tsysmenu2.level == 2 && tsysmenu.id == tsysmenu2.parentMenu.id && tsysmenu2.id != tsysmenu.id}">
	                  	<li><a name="menu" onclick="openTab(this,'<s:out value="${tsysmenu2.navigateUrl}"/>?menuName=<s:out value="${tsysmenu2.name}"/>','<s:out value="${tsysmenu2.name}"/>')" href="javascript:void(0)" url="<s:out value="${tsysmenu2.navigateUrl}"/>?menuName=<s:out value="${tsysmenu2.name}"/>" data-addtab="<s:out value="${tsysmenu2.name}"/>"><i class='fa fa-<s:out value="${tsysmenu2.icon}"/>'></i> &nbsp;&nbsp;<s:out value="${tsysmenu2.name}"/></a></li>
					  </s:if>
					</s:forEach>
			  	</ul>
			   </li>
		  </s:if>
   		</s:forEach>
       </s:if>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>