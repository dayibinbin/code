/**
 * jqgrid init
 */
function pageInit(url,postData, colNames, colModel, sortname, sortorder,autowidth,shrinkToFit){
  if(autowidth == null){
	  autowidth = true;
  }
  if(shrinkToFit == null){
	  shrinkToFit = true;
  }
  jQuery("#tb1").jqGrid(
      {
        url : url,
        datatype : "json",
        mtype : "post",
        postData : postData,
        colNames : colNames,
        colModel : colModel,
	    sortname : sortname,
	    sortorder : sortorder,
	    styleUI: 'Bootstrap',
        rownumbers:true,
        multiselect:true,
        //multiboxonly: true,
        //altRows: true,
        pginput:true, //是否显示跳转页面的输入框
        rowNum : 15,
        rowList : [ 15, 30, 50, 100 ],
        //autoheight: true,
        autowidth: autowidth,
        shrinkToFit: shrinkToFit,
        pager : '#pager',
        viewrecords : true,
        emptyrecords: "无符合条件记录",
        height: "100%",
        scrollOffset: 1,//设置垂直滚动条宽度
        jsonReader : {
        	repeatitems: false,
        	root:"rows",
            page: "page",
            total: "total",
            records: "records"
        },
        loadComplete: function(){
        	var p_window = window.top;
            var height = $(p_window).innerHeight();  
            var controlH = $("#control").height();
        	$("#tb1").setGridHeight(height-210-controlH);
        }
      });
  jQuery("#tb1").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false,search : false});
}