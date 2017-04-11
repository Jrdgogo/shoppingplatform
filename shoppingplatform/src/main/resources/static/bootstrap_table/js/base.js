
$(function() {
	//初始化对话框
	$.messager.model = { 
			ok:{ text: "确定", classed: 'btn btn-danger' },
			cancel: { text: "取消", classed: 'btn btn-primary' }
	};
	//初始化时间控件
	var datetimepickers= $(".datetimepicker");
	if(datetimepickers!=null&&datetimepickers.length>0){
		$(".datetimepicker").datetimepicker({
			language: 'zh-CN', //汉化 
		}); 
	}

});
	