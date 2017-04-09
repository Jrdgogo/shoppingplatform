
(function(doc, win) {
	var docEl = doc.documentElement, resizeEvt = 'orientationchange' in window ? 'orientationchange'
			: 'resize', recalc = function() {
		var clientWidth = docEl.clientWidth;
		if (!clientWidth)
			return;
		if (clientWidth >= 600) {
			docEl.style.fontSize = 100 * (clientWidth / 600) + 'px';
		} else {
			docEl.style.fontSize = '100px';
		}
	};

	if (!doc.addEventListener)
		return;
	win.addEventListener(resizeEvt, recalc, false);
	doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);
//验证用户名是否存在
function validateNameisHave() {
	var username = $("#zcname").val();
	if (username == "")
		return;
	$.ajax({
		type : "post",
		url : getRootPath("/home/getUserByName.ajax"),
		dataType : "json",
		data : "username=" + username,
		success : function(data, textStatus, jqXHR) {
			if (data) {
				$("#zcname").val("");
				$("#zcname").addClass("invalid");
				$("#zcname")
						.attr("placeholder", "该用户名 '" + username + "' 已被使用");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

			alert(XMLHttpRequest.responseText);
		}

	});

}

// 注册验证
function reg() {

	var username = $("#zcname").val();
	var password = $("#password-zc").val();
	var pwd = $("#pwdcheck").val();
	var email = $("#email").val();
	var code = $("#code").val();
	if (username == "")
		return;
	$.ajax({
		type : "post",
		url : getRootPath("/home/registerUser.ajax"),
		dataType : "json",
		data : "username=" + username + "&password=" + password + "&pwd=" + pwd
				+ "&email=" + email + "&code=" + code,
		success : function(data, textStatus, jqXHR) {
			if (data == 2) {
				$("#code").val("");
				$("#code").addClass("invalid");
				$("#code").attr("placeholder", "验证码错误");
			} else if (data == 3) {
				$("#pwdcheck").val("");
				$("#pwdcheck").addClass("invalid");
				$("#pwdcheck").attr("placeholder", "密码不一致");
			} else if (data == 1) {
				alert('注册成功,请查收邮件，激活用户');
				$("#zcclose").click();
				$("#adlopen").click();
			} else if (data == 0)
				alert('注册失败');
			changeCode();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

			alert(XMLHttpRequest.responseText);
			changeCode();
		}

	});

}
function changeCode() {
	var src = $("#imgCode").attr("src") + "?date=" + new Date().getTime();
	$("#imgCode").attr("src", src);
}

// 登录验证
function login() {
	var username = $("#username").val();
	var password = $("#password-dlu").val();
	$.ajax({
		type : "post",
		url : getRootPath("/home/loginValidate.ajax"),
		dataType : "json",
		data : "username=" + username + "&password=" + password,
		success : function(data, textStatus, jqXHR) {
			if (data == -1) {
				$("#username").val("");
				$("#username").addClass("invalid");
				$("#username").attr("placeholder", "用户名或密码错误");
			} else if (data == 2) {
				$("#username").val("");
				$("#username").addClass("invalid");
				$("#username").attr("placeholder", "该用户已注销");
			} else if (data == 0) {
				$("#username").val("");
				$("#username").addClass("invalid");
				$("#username").attr("placeholder", "该用户未激活");
			} else if (data == 1) {
				$("#dluclose").click();
				window.location.href = "/";
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

			alert(XMLHttpRequest.responseText);
		}

	});
}

/* 项目路径 */
function getRootPath(path) {
	/*
	 * //获取当前网址，如： http://localhost:8083/projectName/welcome.html var curWwwPath =
	 * window.document.location.href;
	 * 
	 * //获取主机地址之后的目录，如： /projectName/welcome.html var pathName =
	 * window.document.location.pathname;
	 * 
	 * //获取带"/"的项目名，如：/projectName var projectName = pathName.substring(0,
	 * pathName.substr(1).indexOf('/'));
	 * 
	 * return projectName+path;
	 */
	return path;
}
