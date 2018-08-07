<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>新增菜单</title>
    <%@ include file="../Common/meta.jsp" %>
	</script>
</head>
<body>
<section class="content">
  <form id="myfm">
	<div class="box box-info baseInfo">
	  <div class="box-header with-border">
	    <i class="fa fa-edit"></i><label class="view-lab">&nbsp;基本信息</label>
	    <div class="box-tools pull-right">
	      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
	    </div>
	  </div>
	  <!-- /.box-header -->
	  <div class="box-body">
	    <div class="row">
	      <div class="col-md-12">
	      	<div class="form-group">
              <label for="icon">菜单图标：</label>
              <div class="input-group">
                <div class="input-group-btn">
                  <button type="button" class="btn btn-danger" onclick="openIcon()">选择图标</button>
                </div>
                <!-- /btn-group -->
                <input type="text" class="form-control validate[required]" name="icon" 
                	id="icon" data-toggle="tooltip" title="点击‘选择图标’，并复制图标后代码填入框中">
              </div>
            </div>
	        <!-- /.form-group -->
	      </div>	
	      <div class="col-md-6">
	        <div class="form-group">
              <label for="name">菜单名称：</label>
              <input type="text" class="form-control validate[required]" name="name" id="name">
            </div>
	        <!-- /.form-group -->
	        <div class="form-group">
              <label>菜单等级:</label>
	      	  <select class="form-control select2" name="level" id="menuLevel" style="width: 100%;" onchange="showChild()">
	      		<option value="1" selected="selected">一级</option>
	           	<option value="2">二级</option>
              </select>
            </div>
            <!-- /.form-group -->
	      </div>
	      <!-- /.col -->
	      <div class="col-md-6">
	        <div class="form-group">
              <label>序列号：</label>
              <div class="input-group spinner" data-trigger="spinner">
	            <input readonly="readonly" type="text" class="form-control text-center" name="sequence" data-min="10" value="10" data-step="10">
	            <div class="input-group-addon">
	              <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
	              <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
	            </div>
	          </div>
            </div>
	        <!-- /.form-group -->
	        <div class="form-group">
              <label>状态:</label>
      		  <select class="form-control select2" name="status" style="width: 100%;">
	      		<option value="1" selected="selected">启用</option>
	           	<option value="0">停用</option>
              </select>
            </div>
            <!-- /.form-group -->
	      </div>
	      <!-- /.col -->
	    </div>
	    <!-- /.row -->
	  </div>
	  <!-- /.box-body -->
	  <div class="box box-info" id="childMenu" style="display:none">
		  <div class="box-body">
		    <div class="row">
		      <div class="col-md-12">
		        <div class="form-group">
	              <label for="code">菜单代码：</label>
	              <input type="text" class="form-control validate[required]" name="code" id="code">
	            </div>
		        <div class="form-group">
	              <label for="navigateUrl">导航地址：</label>
	              <input type="text" class="form-control validate[required]" name="navigateUrl" id="navigateUrl">
		        </div>
		        <div class="form-group">
	              <label>父级菜单：</label>
	              <select class="form-control select2" name="parentId" id="parentId" style="width: 100%;">
	              </select>
	            </div>
		      </div>
		    </div>
		    <!-- /.row -->
		  </div>
		  <!-- /.box-body -->
      </div>
	  <!-- /.box -->
	</div>
	<!-- /.box -->
	<div class="row">
	    <div style="text-align:center" class="col-md-12">
	    	<botton class="btn btn-primary" onclick="submitForm()" >保存</botton>
	    	<button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('新增菜单')">关闭</button>
	    </div>
	</div>
  </form>
</section>
<%@ include file="../Common/script.jsp" %>
<script>
$(function(){
	initSelect2();
	$('#myfm').validationEngine('attach', {
        relative: true,
        promptPosition: "topRight" //验证弹出框的位置，topRight,topLeft,bottomRight,bottomLeft,centerRight,centerLeft,inline
    });
})
//弹出图标选择框
function openIcon(){
	top.$("#myModal").modal('show');
}
//初始化select2
function initSelect2() {
    $.ajax({
        url: '../TSysmenu/getParentMenu.do',
        method: 'get',
        datatype: 'json',
        success: function (data) {
            var $exampleMulti = $("#parentId").select2({
        		data: data,
        		placeholder: "选择父级菜单",
        		allowClear: true
        	});
            $exampleMulti.select2("val", null);
        }
    });
}
//显示/隐藏二级菜单选项
function showChild(){
	var menuLevel = $("#menuLevel").val();
	if(menuLevel == 1){
		$("#childMenu").slideUp();
		$("#code").prop("disabled", true);
		$("#navigateUrl").prop("disabled", true);
		$("#parentId").prop("disabled", true);
	}else if(menuLevel == 2){
		$("#childMenu").slideDown();
		$("#code").prop("disabled", false);
		$("#navigateUrl").prop("disabled", false);
		$("#parentId").prop("disabled", false);
	}
}
//提交表单
function submitForm(){
	var param = $("#myfm").serialize();
	if($('#myfm').validationEngine('validate') == true){
		top.$('#loading').fadeIn(100);
		/* var parentId = $("#parentId").val();
		if(parentId == null || parentId == ''){
			top.toastr.error('请选择父级菜单','父级菜单');
			return;
		} */
		$.ajax({
	        url: '../TSysmenu/save.do',
	        type:'post',
	        contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	        data: param,
	        success: function(result){
	            if (result.iserror){
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.error(result.message,'新增菜单');
	            } else {
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.success(result.message, '新增菜单');
	            	top.Addtabs.closeAndReload('新增菜单');
	            }
	        }
	    });
	}
}
</script>
</body>
</html>
