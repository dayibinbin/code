<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>新增系统配置</title>
    <%@ include file="../Common/meta.jsp" %>
	</script>
</head>
<body>
<section class="content">
  <form id="myfm">
  	<input type="hidden" id="roleIds" name="roleIds">
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
              <label for="paramName">配置名称：</label>
              <input type="text" class="form-control validate[required]" name="paramName" id="paramName">
            </div>
	        <!-- /.form-group -->
	        <div class="form-group">
              <label for="paramCode">配置代码：</label>
              <input type="text" class="form-control validate[required]" name="paramCode" id="paramCode">
	        </div>
	        <!-- /.form-group -->
	        <div class="form-group">
              <label for="paramValue">配置值：</label>
              <input type="text" class="form-control validate[required]" name="paramValue" id="paramValue">
	        </div>
	        <!-- /.form-group -->
	        <div class="form-group">
              <label for="description">配置描述：</label>
              <input type="text" class="form-control" name="description" id="description">
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
	    	<button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('新增系统配置')">关闭</button>
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

function submitForm(){
	var param = $("#myfm").serialize();
	if($('#myfm').validationEngine('validate') == true){
		top.$('#loading').fadeIn(100);
		$.ajax({
	        url: '../TSysconfig/save.do',
	        type:'post',
	        contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	        data: param,
	        success: function(result){
	            if (result.iserror){
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.error(result.message,'新增系统配置');
	            } else {
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.success(result.message, '新增系统配置');
	            	top.Addtabs.closeAndReload('新增系统配置');
	            }
	        }
	    });
	}
	
}
</script>
</body>
</html>
