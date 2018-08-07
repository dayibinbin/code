//ztree配置
var setting = {
    check: {
        enable: true
    },
    view: {
        dblClickExpand: false,
        showLine: false,
        selectedMulti: false
    }
};
//标签页iframe高度自适应
function iFrameHeight(ifr) {
	var p_window = window.top;
	var height = $(p_window).height();
    var bHeight = ifr.contentWindow.document.body.scrollHeight;  
    var dHeight = $(p_window).height()-125;  
    var height = Math.max(bHeight, dHeight);  
    ifr.height = height;  
} 
$(function(){
	//jqgrid宽度自适应
	$(window).resize(function(){ 
    	$("#tb1").setGridWidth($(window).innerWidth()-10);
    });
   //滑动显示块
   $(".baseInfo").slideDown();
   //标签页初始化
   $('#tabs').addtabs({monitor:'.topbar'});
   //提示框初始化
   toastr.options = {
 		  "closeButton": true,
 		  "debug": false,//是否开起debug
 		  "progressBar": false,//显示进度条
 		  "positionClass": "toast-bottom-full-width",//弹窗位置
 		  "onclick": null,
 		  "showDuration": "500",//显示动作（从无到有这个动作）持续的时间
 		  "hideDuration": "500",//隐藏动作持续的时间
 		  "timeOut": "3000",//间隔的时间
 		  "extendedTimeOut": "5000",//提示框显示时长
 		  "showEasing": "swing",
 		  "hideEasing": "linear",
 		  "showMethod": "fadeIn",
 		  "hideMethod": "fadeOut"
	}
   //iCheck for checkbox and radio inputs
   $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
     checkboxClass: 'icheckbox_minimal-blue',
     radioClass: 'iradio_flat-blue'
   });
   //数字增减控件初始化
   $('#customize-spinner').spinner('changed',function(e, newVal, oldVal){
       $('#old-val').text(oldVal);
       $('#new-val').text(newVal);
   });
   //echarts路径配置
   require.config({
       paths: {
            echarts: "../bootstrap/echarts/build/dist"
       }
   });
});

function conf(title,message,ref){
	top.BootstrapDialog.show({
        title: title,
        message: message,
        buttons: [{
        	icon: 'fa fa-check',
            label: '确定',
            hotkey: 13, // Enter.
            cssClass: 'btn-primary',
            action: ref
        }, {
        	icon: 'fa fa-close',
            label: '关闭',
            cssClass: 'btn-warning',
            action: function(dialogItself){
                dialogItself.close();
            }
        }]
    });
}

/*var oTable;
//表格初始化
function initTable(tableId, columns, order, fnServerData){
	oTable = $('#'+tableId).dataTable({
		  "dom":
				 "<'row'<'col-sm-12'tr>>" +
				 "<'row'<'col-sm-1'l><'#refreshDiv.col-sm-1'><'col-sm-2'i><'col-sm-8'p>>",
	 	  "fixedHeader": true,//表头固定
	   	  "bProcessing": true,//加载中状态
	      "serverSide": true,//服务端查询模式
	      "bFilter" : false,// 搜索栏
	      "pagingType": "full_numbers",
	      //"sScrollX":"100%",//横向滚动条
	      //"bAutoWidth" : true, //是否自适应宽度 
	      "sScrollX": "100%",
	      "sScrollXInner": "100%",
	      "bScrollCollapse": true,
	      "columns": columns,
	     	"order": order,
	     	 "columnDefs": [
	     	           	{
	                        "sClass": "center",
	                        "fnRender": function(oObj, sVal) {
	                            return '<input type="checkbox" id="' + sVal + '" title="' + oObj.aData[0] + '" name="check' + sVal + '" />';
	                        },
	                        "bSortable": false,
	                        "aTargets": [1]
	                    }
	        ], 
	     	"fnServerData" : fnServerData,
	      	"fnDrawCallback": function(){
	  	  		var api = this.api();
	    	  	var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
	    	  	api.column(0).nodes().each(function(cell, i) {
	    	  		cell.innerHTML = startIndex + i + 1;
	   	  		}); 
	   	  	},
			"language": {
		        "sProcessing": "处理中...",
		        "sLengthMenu": " _MENU_ ",
		        "sZeroRecords": "没有匹配结果",
		        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
		        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
		        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
		        "sInfoPostFix": "",
		        "sSearch": "搜索:",
		        "sUrl": "",
		        "sEmptyTable": "表中数据为空",
		        "sLoadingRecords": "载入中...",
		        "sInfoThousands": ",",
		        "oPaginate": {
		            "sFirst": "首页",
		            "sPrevious": "上页",
		            "sNext": "下页",
		            "sLast": "末页"
		        },
		        "oAria": {
		            "sSortAscending": ": 以升序排列此列",
		            "sSortDescending": ": 以降序排列此列"
		        }
		    },
	   	  	"initComplete": function(){
	   	  		$("#refreshDiv").append("<a class='btn btn-success btn-sm' href='javascript:refresh()' id='refresh'><i class='icon-refresh icon-spin'></i> 刷新</a>");
	   	  		$("#refresh").on('click', function(){
	   	  			//alert(123);
	   	  			ajax.reload();
	   	  		})
	   	  	}
		});
	return oTable;
}
//刷新表单
function refresh(){
	oTable.api().ajax.reload();
}
//全选/反选
function allCheck(){
	if($("#all").is(':checked')){
		$("input[name='ck']").prop("checked", true);   
	}else{    
        $("input[name='ck']").prop("checked", false); 
    }   
}*/
