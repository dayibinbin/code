<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="Common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">  
	    <title><%=title%></title>
        <meta name="description" content="">
        <meta name="author" content="templatemo">
        <!-- bootstrp -->
        <link rel="stylesheet" href="static/bootstrap/fonts/css/font-awesome.min.css">
	    <!-- <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,700' rel='stylesheet' type='text/css'> -->
	    <link rel="stylesheet" href="static/bootstrap/css/login.css">
	    <link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
	    <!-- validationEngine -->
    	<link rel="stylesheet" href="static/validationEngine/css/validationEngine.jquery.css" type="text/css" media="screen"/>
		<script src="static/validationEngine/js/jquery-1.8.2.min.js" type="text/javascript"></script>
		<!-- <script src="static/validationEngine/js/languages/jquery.validationEngine-en.js" type="text/javascript"></script> -->
		<script src="static/validationEngine/js/jquery.validationEngine-zh_CN.js"></script>
		<script src="static/validationEngine/js/jquery.validationEngine.js" type="text/javascript"></script>
		
		
	    <script type="text/javascript">
	    $(document).ready(function() {
	    	$('#form1').validationEngine();
		    document.onkeydown=function(event){
				event = event|| window.event;
				if (event.keyCode == 13){
					submitForm();
				}
		    };
		});
		function submitForm(){
			var flag = $('#form1').validationEngine('validate');
			if (!flag) return;
			var tmpPwd = $("#password").val();
			var pwd = tmpPwd;
			pwd = MD5.md5(pwd).toUpperCase();
			$("#password").val(pwd);
			
			$.ajax({
				async:false,
	            url: 'userLogin.do',
	            type:'post',
	            data: {
	                'loginName': $("#loginName").val(),
	                'password': pwd
	            },
	            dataType:'json',
	            success: function(data){
	                if (data.iserror){
	                	$("#password").val(tmpPwd);
	                	showError(data.message);
	                } else {
	                	window.location.href="index.do";
	                }
	            },
	            error : function() {  
	            	showError('系统异常');
	            }  
        	});
		}
		
	    function init() {
			var path = "static/js/", script = null;
			var head = document.getElementsByTagName("head")[0];
			var scripts = ["jquery.md5.js"];
			setTimeout(function() {
				for ( var i = 0; i < scripts.length; i++) {
					script = document.createElement("script");
					script.src = path + scripts[i];
					head.appendChild(script);
				}
			}, 500);
			if(document.readyState=="complete"){
		  		document.getElementById("loginName").focus(); 
			}
		}	  

	    function showError(msg){
	    	$('#errorMsg').show();
	    	$('#loginError').html(msg);
	    }
	</script>
	</head>
	<body class="light-gray-bg" onload="init()">
		<div class="templatemo-content-widget templatemo-login-widget white-bg">
			<header class="text-center">
	          <!-- <div class="square"></div> -->
	          <h2><font color="#13895F"><%=title%></font></h2>
	        </header>
	        <form method="post" id="form1" class="templatemo-login-form" novalidate>
	        	<div class="form-group">
	        		<div class="input-group">
		        		<div class="input-group-addon"><i class="fa fa-user fa-fw"></i></div>	        		
		              	<input type="text" class="validate[required] form-control" id="loginName" name="loginName">           
		          	</div>	
	        	</div>
	        	<div class="form-group">
	        		<div class="input-group">
		        		<div class="input-group-addon"><i class="fa fa-key fa-fw"></i></div>	        		
		              	<input type="password" class="validate[required] form-control" id="password" name="password">           
		          	</div>	
	        	</div>
	        	<div class="form-group" id="errorMsg" style="display: none;">
	        		<div class="input-group">
		        		<div class="cell"><span id="loginError" class="loginError"><s:out value="${result.message}"/></span></div>     
		          	</div>	
	        	</div>	    
	          	<!-- <div class="form-group">
				    <div class="checkbox squaredTwo">
				        <input type="checkbox" id="c1" name="cc" />
						<label for="c1"><span></span>Remember me</label>
				    </div>				    
				</div> -->
				<div class="form-group">
					<button type="button" onclick="submitForm();" class="templatemo-blue-button width-100">登   录</button>
				</div>
	        </form>
		</div>
	</body>
</html>