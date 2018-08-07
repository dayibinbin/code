<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>修改个人信息</title>
    <%@ include file="../Common/meta.jsp" %>
</head>
<body>
<section class="content">
  <form id="myfm">
  	<input type="hidden" name="toperator.id" value="${toperator.id}">
    <input type="hidden" name="toperator.createTime" value="<fmt:formatDate value="${toperator.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
    <input type="hidden" name="toperator.loginIp" value="${toperator.loginIp}">
    <input type="hidden" name="toperator.loginTime" value="<fmt:formatDate value="${toperator.loginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
    <input type="hidden" name="toperator.password" value="${toperator.password}">
    <input type="hidden" name="toperator.loginName" value="${toperator.loginName}">
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
              <input type="text" class="form-control" value="${toperator.loginName}" name="toperator.loginName" id="loginName" disabled>
            </div>
	      </div>
	      <!-- /.col -->
	      <div class="col-md-6">
	        <div class="form-group">
              <label for="password">密码：</label>
              <input type="text" class="form-control" value="${toperator.password}" name="toperator.password" id="password" disabled>
	        </div>
	      </div>
	      <div class="col-md-12">
	        <!-- /.form-group -->
	        <div class="form-group">
              <label for="realName">真实姓名：</label>
              <input type="text" class="form-control validate[required]" value="${toperator.realName}" name="toperator.realName" id="realName">
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
              <select class="form-control select2" name="toperator.gender" style="width: 100%;">
                <s:choose>
   					<s:when test="${toperator.gender == 1}">
   						<option value="1" selected="selected">男</option>
   					</s:when>
   					<s:otherwise>
   						<option value="1">男</option>
   					</s:otherwise>
   				</s:choose>
   				<s:choose>
   					<s:when test="${toperator.gender == 0}">
   						<option value="0" selected="selected">女</option>
   					</s:when>
   					<s:otherwise>
   						<option value="0">女</option>
   					</s:otherwise>
   				</s:choose>
              </select>
            </div>
	      </div>
	      <div class="col-md-6">
	      	<label>状态:</label>
	      	<select class="form-control select2" name="toperator.status" style="width: 100%;">
	      		<s:choose>
   					<s:when test="${toperator.status == 1}">
   						<option value="1" selected="selected">启用</option>
   					</s:when>
   					<s:otherwise>
   						<option value="1">启用</option>
   					</s:otherwise>
   				</s:choose>
   				<s:choose>
   					<s:when test="${toperator.status == 0}">
   						<option value="0" selected="selected">停用</option>
   					</s:when>
   					<s:otherwise>
   						<option value="0">停用</option>
   					</s:otherwise>
   				</s:choose>
            </select>
	      </div>
	      <div class="col-md-12">
	        <div class="form-group">
              <label for="email">邮箱：</label>
              <input type="text" class="form-control" value="${toperator.email}" name="toperator.email" id="email">
            </div>
	        <div class="form-group">
              <label for="phone">电话：</label>
              <input type="text" class="form-control" value="${toperator.phone}" name="toperator.phone" id="phone">
	        </div>
	        <div class="form-group">
              <label for="mobile">手机：</label>
              <input type="text" class="form-control" value="${toperator.mobile}" name="toperator.mobile" id="mobile">
	        </div>
	        <div class="form-group">
              <label for="fax">传真：</label>
              <input type="text" class="form-control" value="${toperator.qq}" name="toperator.fax" id="fax">
	        </div>
	        <div class="form-group">
              <label for="qq">QQ：</label>
              <input type="text" class="form-control" value="${toperator.qq}" name="toperator.qq" id="qq">
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
	    	<button type="botton" class="btn btn-primary" onclick="top.Addtabs.closeTab('修改操作员')">关闭</button>
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
		top.$('#loading').fadeIn(100);
		$.ajax({
	        url: '../TOperator/updateSelf.do',
	        type:'post',
	        contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	        data: param,
	        success: function(result){
	            if (result.iserror){
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.error(result.message, '修改个人信息');
	            } else {
	            	top.$("#loading").fadeOut(100);
	            	top.toastr.success(result.message, '修改个人信息');
	            	/* top.Addtabs.closeAndReload('修改操作员'); */
	            	top.Addtabs.closeTab('修改个人信息')
	            }
	        }
	    });
	}
	
}
</script>
</body>
</html>