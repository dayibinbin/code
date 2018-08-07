<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>新增权限</title>
    <%@ include file="../Common/meta.jsp" %>
	</script>
</head>
<body>
<section class="content">
  <form id="myfm">
  	<input type="hidden" name="menuId" value="${menuId}">
	<div class="box box-danger baseInfo">
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
              <label>按钮样式：</label>
              <div class="form-group">
                <label class="col-md-2">
                  <input value="btn btn-default" type="radio" name="iconCls" class="minimal" checked>
                  <button class="btn btn-default">样式1</button>
                </label>
                <label class="col-md-2">
                  <input value="btn btn-primary" type="radio" name="iconCls" class="minimal">
                  <button class="btn btn-primary">样式2</button>
                </label>
                <label class="col-md-2">
                  <input value="btn btn-success" type="radio" name="iconCls" class="minimal">
                  <button class="btn btn-success">样式3</button>
                </label>
                <label class="col-md-2">
                  <input value="btn btn-info" type="radio" name="iconCls" class="minimal">
                  <button class="btn btn-info">样式4</button>
                </label>
                <label class="col-md-2">
                  <input value="btn btn-danger" type="radio" name="iconCls" class="minimal">
                  <button class="btn btn-danger">样式5</button>
                </label>
                <label class="col-md-2">
                  <input value="btn btn-warning" type="radio" name="iconCls" class="minimal">
                  <button class="btn btn-warning">样式6</button>
                </label>
              </div>
	        </div>
	      </div>
	      <div class="col-md-12">
	      	<div class="form-group">
              <label for="icon">按钮图标：</label>
              <div class="input-group">
                <div class="input-group-btn">
                  <button type="button" class="btn btn-danger" onclick="openIcon()">选择图标</button>
                </div>
                <!-- /btn-group -->
                <input type="text" class="form-control" name="icon" 
                	id="icon" data-toggle="tooltip" title="点击‘选择图标’，并复制图标后代码填入框中">
              </div>
            </div>
	        <!-- /.form-group -->
	      </div>	
	      <div class="col-md-6">
	        <div class="form-group">
              <label for="name">权限名称：</label>
              <input type="text" class="form-control validate[required]" name="name" id="name">
            </div>
	        <!-- /.form-group -->
	        <div class="form-group">
              <label for="code">权限代码：</label>
              <input type="text" class="form-control validate[required]" name="code" id="code">
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
	      <div class="col-md-6">
	      	<div class="form-group">
              <label for="menuName">所属菜单：</label>
              <input type="text" class="form-control" value="${tsysmenu.name}" name="name" id="menuName" disabled>
            </div>
	        <!-- /.form-group -->
	        <div class="form-group">
	      	  <label for="method">方法名称:</label>
	      	  <input type="text" class="form-control validate[required]" name="method" id="method">
	        </div>
	        <!-- /.form-group -->
	        <div class="form-group">
	      	  <label>序列号:</label>
	      	  <div class="input-group spinner" data-trigger="spinner">
	            <input readonly="readonly" type="text" class="form-control text-center" name="sequence" data-min="10" value="10" data-step="10">
	            <div class="input-group-addon">
	              <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
	              <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
	            </div>
	          </div>
	        </div>
	        <!-- /.form-group -->
	      </div>
	      <!-- /.col -->
	    </div>
	    <!-- /.row -->
	  </div>
	  <!-- /.box-body -->
	</div>
	<!-- /.box -->
	<div class="row">
	    <div style="text-align:center" class="col-md-12">
	    	<botton class="btn btn-primary" onclick="submitForm()" >保存</botton>
	    	<button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('新增权限')">关闭</button>
	    </div>
	</div>
  </form>
</section>
<%@ include file="../Common/script.jsp" %>
<script>

$(function(){
	//初始化验证框
	$('#myfm').validationEngine('attach', {
        relative: true,
        promptPosition: "topRight" //验证弹出框的位置，topRight,topLeft,bottomRight,bottomLeft,centerRight,centerLeft,inline
    });
	
});
//弹出图标选择框
function openIcon(){
	top.$("#myModal").modal('show');
}
function submitForm(){
	var param = $("#myfm").serialize();
	if($('#myfm').validationEngine('validate') == true){
		top.$('#loading').fadeIn(100);
		$.ajax({
	        url: '../TSyspriv/save.do',
	        type:'post',
	        contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	        data: param,
	        success: function(result){
	            if (result.iserror){
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.error(result.message,'新增权限');
	            } else {
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.success(result.message, '新增权限');
	            	top.Addtabs.closeAndReload('新增权限');
	            }
	        }
	    });
	}
}
</script>
</body>
</html>
