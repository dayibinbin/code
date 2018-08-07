<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String menuName = request.getParameter("menuName");
if (menuName != null && menuName != ""){
	menuName = new String(menuName.getBytes("ISO-8859-1"),"UTF-8");
}
String title = new String("通用权限管理系统".getBytes("ISO-8859-1"),"UTF-8");;
%>
