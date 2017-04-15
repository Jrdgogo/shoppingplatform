/**
 * 时间工具函数
 */
var DateUtils = {
	createDateByMillisecond:function(ms){
		return new Date(ms);
	},
	formatByDate:function(date,format){
		function _0(num){
			if(num<10){
				return "0"+num;
			}
			return num.toString();
		}
		var year = _0(date.getFullYear());
		var month = _0(date.getMonth() + 1);
		var day = _0(date.getDate());
		
		var h = _0(date.getHours());
		var m = _0(date.getMinutes());
		var s = _0(date.getSeconds());
		
		if(format=="yyyy-MM-dd"){
			return year+"-"+month+"-"+day;
		}else if(format=="yyyy-MM-dd HH:mm"){
			return year+"-"+month+"-"+day+" "+h+":"+m;
		}else if(format=="yyyy-MM-dd HH:mm:ss"){
			return year+"-"+month+"-"+day+" "+h+":"+m+":"+s;
		}
	},
	formatByMs:function(ms,format){
		var d = new Date(ms);
		return DateUtils.formatByDate(d,format);
	}
};

/**
 * 系统工具函数
 */
var SystemUtils = {
	basehref:"",
	baseUrl:function(){
		return SystemUtils.basehref;
	}
};

//初始化
$(function(){
	$base = $("base");
	if($base.length==0){
		return;
	}
	SystemUtils.basehref = $("base").attr("href");
});