<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>修改个人密码</title>
    <%@ include file="../Common/meta.jsp" %>
</head>
<body>
<section class="content">
  <form id="myfm">
    <input type="hidden" name="id" value="${id}">
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
              <label for="password">旧密码：</label>
              <input type="password" class="form-control validate[required]" name="password" id="password">
            </div>
	        <!-- /.form-group -->
	        <div class="form-group">
              <label for="newPassword">新密码：</label>
              <input type="password" class="form-control validate[required]"  name="newPassword" id="newPassword">
	        </div>
	        <!-- /.form-group -->
	        <div class="form-group">
              <label for="newPasswordRepeat">重复：</label>
              <input type="password" class="form-control validate[required]" name="newPasswordRepeat" id="newPasswordRepeat">
	        </div>
	        <!-- /.form-group -->
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
	    	<button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('修改密码')">关闭</button>
	    </div>
	</div>
  </form>
</section>
<%@ include file="../Common/script.jsp" %>
<script>
$(function(){
	$('#myfm').validationEngine('attach', {
        relative: true,
        promptPosition: "topRight" //验证弹出框的位置，topRight,topLeft,bottomRight,bottomLeft,centerRight,centerLeft,inline
    });
})

function submitForm(){
	var param = $("#myfm").serialize();
	if($('#myfm').validationEngine('validate') == true){
		var password = $("#password").val();
		var newPassword = $("#newPassword").val();
		var newPasswordRepeat = $("#newPasswordRepeat").val();
		if (password == newPassword){
			top.toastr.error('新旧密码相同', '修改个人密码');
			return;
		}
		if (newPassword != newPasswordRepeat){
			top.toastr.error('两次输入的新密码不一致', '修改个人密码');
			return;
		}
		top.$('#loading').fadeIn(100);
		$.ajax({
	        url: '../TOperator/updatePassword.do',
	        type:'post',
	        contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	        data: param,
	        success: function(result){
	            if (result.iserror){
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.error(result.message, '修改个人密码');
	            } else {
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.success(result.message, '修改个人密码');
	            	/* top.Addtabs.closeAndReload('修改个人密码'); */
	            	top.Addtabs.closeTab('修改个人密码')
	            }
	        }
	    });
	}
	
}
</script>
</body>
</html>