<%@ page language="java"
	import="java.util.*,cn.com.checknull.model.po.TOperator"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><%=title%></title>
  <%@ include file="Common/meta.jsp" %>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
</head>
<body class="hold-transition skin-blue sidebar-mini" >
<%@ include file="Index/loading.jsp" %>
<%@ include file="TSysmenu/font-icon.jsp" %>
<div class="wrapper">
  <!-- 页头 -->
  <%@ include file="Index/header.jsp" %>
  <!-- 左侧导航栏 -->
  <%@ include file="Index/left-bar.jsp" %>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="tab-col">
        <div id="tabs">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs demo1Box" role="tablist">
                <li role="presentation" id="tab_tab_home" class="active">
                	<a href="#tab_home" aria-controls="tab_home" role="tab" data-toggle="tab">
                	<i class="fa fa-home"></i>主页</a>
                </li>                    
            </ul>
            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane fade in active" id="tab_home" role="tabpanel">
                    <%@ include file="Index/main.jsp" %>
                </div>                    
            </div>
        </div>
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <strong>版权所有 &copy; <a href="javascript:window.open('https://cknull.taobao.com/','_blank')">check_null</a>.</strong> 保留所有权.
  </footer>
  <!-- 右侧工具栏 -->
  <%@ include file="Index/right-bar.jsp" %>
</div>
<!-- ./wrapper -->
<%@ include file="Common/script.jsp" %>
<script type="text/javascript">
$.widget.bridge('uibutton', $.ui.button);
//右键菜单
var menu = new BootstrapMenu('.demo1Box', {
  actions: [{
	name: "<i class='fa fa-minus'></i> 关闭激活页",
	onClick: function() {
		var tabId = obj.find("li.active").attr('id');
		if(tabId != null && tabId != "tab_tab_home"){
			tabId = tabId.replace("tab_tab_","");
			Addtabs.closeTab(tabId);
		}
	}
  }, {
	name: "<i class='fa fa-expand'></i> 关闭未激活页",
	onClick: function() {
		var tabs = $("ul.demo1Box").find("li[role='presentation']");
		var activeTab = $("ul.demo1Box").find("li.active").attr('id');
		if(tabs.length > 1){
			tabs.each(function(){
				var tabId = $(this).attr("id");
				if(tabId != "tab_tab_home" && tabId != activeTab){
					tabId = tabId.replace("tab_tab_","tab_");
					$("#tab_" + tabId).remove();
			        $("#" + tabId).remove();
			        Addtabs.drop();
			        Addtabs.options.callback();
				}
		    });
		}
	}
  }, {
	name: "<i class='fa fa-close'></i> 关闭所有",
	onClick: function() {
		var tabs = $("ul.demo1Box").find("li[role='presentation']");
		if(tabs.length > 1){
			tabs.each(function(){
				var tabId = $(this).attr("id");
				if(tabId != "tab_tab_home"){
					tabId = tabId.replace("tab_tab_","tab_");
					$("#tab_" + tabId).remove();
			        $("#" + tabId).remove();
			        Addtabs.drop();
			        Addtabs.options.callback();
				}
		    });
			$("#tab_tab_home").addClass('active');
            $("#tab_home").addClass('active');
            $("#tab_home").addClass("in");
		}
	}
  }]
});
//导航菜单控制
function openTab(obj,url,tabId){
	var activeLi = $("ul.topbar.sidebar-menu").find("li.active").children("ul").children("li.active");
	if(activeLi.length > 0){
		activeLi.each(function(){
			$(this).removeClass('active');
		})
	}
	$(obj).parent().addClass('active');
	Addtabs.add({
	   id: tabId,
       url: url
   	});
}
</script>

</body>
</html>
