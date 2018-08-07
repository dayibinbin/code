<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>新增操作员</title>
    <%@ include file="../Common/meta.jsp" %>
	</script>
</head>
<body>
<section class="content">
  <form id="myfm">
  	<input type="hidden" id="roleIds" name="roleIds">
	<div class="box box-danger baseInfo">
	  <div class="box-header with-border">
	    <i class="fa fa-edit"></i><label class="view-lab">&nbsp;必填项</label>
	    <div class="box-tools pull-right">
	      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
	    </div>
	  </div>
	  <!-- /.box-header -->
	  <div class="box-body">
	    <div class="row">
	      <div class="col-md-6">
	        <div class="form-group">
              <label for="loginName">登录名称：</label>
              <input type="text" class="form-control validate[required]" name="loginName" id="loginName">
            </div>
	        <!-- /.form-group -->
	        <div class="form-group">
              <label for="realName">真实姓名：</label>
              <input type="text" class="form-control validate[required]" name="realName" id="realName">
	        </div>
	        <!-- /.form-group -->
	      </div>
	      <!-- /.col -->
	      <div class="col-md-6">
	        <div class="form-group">
              <label for="password">密码：</label>
              <input type="text" class="form-control validate[required]" name="password" id="password" value="123456">
	        </div>
	        <!-- /.form-group -->
	        <div class="form-group">
              <label>角色定义：</label>
              <select class="form-control select2" id="roleSelect" multiple="multiple" data-placeholder="选择角色" style="width: 100%;">
              </select>
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
	      <div class="col-md-6">
	      	<div class="form-group">
              <label>性别：</label>
              <select class="form-control select2" name="gender" style="width: 100%;">
                <option value="1" selected="selected">男</option>
   				<option value="0">女</option>
              </select>
            </div>
	      </div>
	      <div class="col-md-6">
	      	<label>状态:</label>
	      	<select class="form-control select2" name="status" style="width: 100%;">
	      		<option value="1" selected="selected">启用</option>
	           	<option value="0">停用</option>
              </select>
	      </div>
	      <div class="col-md-12">
	        <div class="form-group">
              <label for="email">邮箱：</label>
              <input type="text" class="form-control" name="email" id="email">
            </div>
	        <div class="form-group">
              <label for="phone">电话：</label>
              <input type="text" class="form-control" name="phone" id="phone">
	        </div>
	        <div class="form-group">
              <label for="mobile">手机：</label>
              <input type="text" class="form-control" name="mobile" id="mobile">
	        </div>
	        <div class="form-group">
              <label for="fax">传真：</label>
              <input type="text" class="form-control" name="fax" id="fax">
	        </div>
	        <div class="form-group">
              <label for="qq">QQ：</label>
              <input type="text" class="form-control" name="qq" id="qq">
	        </div>
	      </div>
	    </div>
	    <!-- /.row -->
	  </div>
	  <!-- /.box-body -->
	</div>
	<!-- /.box -->
	<div class="row">
	    <div style="text-align:center" class="col-md-12">
	    	<botton class="btn btn-primary" onclick="submitForm()" >保存</botton>
	    	<button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('新增操作员')">关闭</button>
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
//初始化多选框
function initSelect2() {
	var roleArray = $("#roleIds").val().split(",");
    $.ajax({
        url: '../TOperator/getRoleTree.do',
        method: 'get',
        datatype: 'json',
        success: function (data) {
            var $exampleMulti = $("#roleSelect").select2({
        		data: data
        	});
        }
    });
}
function getRoles(){
	var roleIds = $("#roleSelect").val();
    $("#roleIds").val(roleIds);
}

function submitForm(){
	getRoles();
	var param = $("#myfm").serialize();
	if($('#myfm').validationEngine('validate') == true){
		top.$('#loading').fadeIn(100);
		$.ajax({
	        url: '../TOperator/save.do',
	        type:'post',
	        contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	        data: param,
	        success: function(result){
	            if (result.iserror){
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.error(result.message,'新增操作员');
	            } else {
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.success(result.message, '新增操作员');
	            	top.Addtabs.closeAndReload('新增操作员');
	            }
	        }
	    });
	}
	
}
</script>
</body>
</html>
