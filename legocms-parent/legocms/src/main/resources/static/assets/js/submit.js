function ajaxSubmit(url, data, successFun, errorFun){
	ajaxPost(url, data, successFun, errorFun, true);
}

function ajaxSyncSubmit(url, data, successFun, errorFun){
	ajaxPost(url, data, successFun, errorFun, false);
}

function ajaxTextSubmit(url, data, successFun, errorFun) {
	$.ajax({
		url: url ,		//发送请求的地址
		data: data,		//要传递到服务器端的数据
		type: "post",	//请求方式 ("POST" 或 "GET")， 默认为 "GET"
		async: true,	//请求默认为异步请求true
		cache: false,	//设置为 false 将不缓存此页面
		dataType: "text",	//预期服务器返回的数据类型
		success: function(data){
			if(isEmpty(successFun)){
				console.log(date);
				showMsg("未定义回调方法！", 5);
			}
			else {
				successFun(data);
			}
		},	//请求成功后的回调函数
		error: function(date){
			if(isEmpty(errorFun)){
				console.log(date);
				showMsg("系统发生未知异常……", 5);
			}
			else {
				errorFun(data);
			}
		}
	});
}

function ajaxFileSubmit(url, data, successFun, errorFun) {
	$.ajax({
		url: url,
		type: 'POST',
		data: data,
		processData: false,
		async: false,
		contentType: false,
		dataType: "json",
        beforeSend: function () {
            layer.load(0, {
                shade: false
            });
        },
        success: function(data){
			if(!isEmpty(data) && data.hasOwnProperty("code")){
				if (interceptorResponse(data)) {
					return;
				}
				if(isEmpty(successFun)){
					successShowFun(data);
				}
				else {
					layer.closeAll();
					successFun(data);
				}
			}
			else {
				showMsg("返回结果格式错误,have not code value!", 5);
			}
		},	//请求成功后的回调函数
		error: function(date){
			if(isEmpty(errorFun)){
				console.log(date);
				showMsg("系统发生未知异常……", 5);
			}
			else {
				layer.closeAll();
				errorFun(data);
			}
		}
	});
}

function ajaxPost(url, data, successFun, errorFun, async){
	if(null==async ||'boolean'!= typeof(async)){
		async=true;
	}
	$.ajax({
		url: url ,		//发送请求的地址
		data: data,		//要传递到服务器端的数据
		type: "post",	//请求方式 ("POST" 或 "GET")， 默认为 "GET"
		async: async,	//请求默认为异步请求true
		cache: false,	//设置为 false 将不缓存此页面
		dataType: "json",	//预期服务器返回的数据类型
        beforeSend: function () {
                layer.load(0, {
                    shade: false
                });
            },
		success: function(data){
			if(!isEmpty(data) && data.hasOwnProperty("code")){
				if (interceptorResponse(data)) {
					return;
				}
				if(isEmpty(successFun)){
					successShowFun(data);
				}
				else {
					layer.closeAll();
					successFun(data);
				}
			}
			else {
				showMsg("返回结果格式错误,have not code value!", 5);
			}
		},	//请求成功后的回调函数
		error: function(date){
			if(isEmpty(errorFun)){
				console.log(date);
				showMsg("系统发生未知异常……", 5);
			}
			else {
				layer.closeAll();
				errorFun(data);
			}
		}
	});
}

/*
 * ajx请求响应内容拦截，返回true不回调成功函数，返回false回调成功函数
 */
function interceptorResponse(data) {
	if (data.code != "0") {
		if (data.code == "1000") {
			showMsg(data.msg, 5, function() {
				window.open(ctx + "login","_parent");
			});
		}
		else {
			showMsg(data.msg, 5);
		}
		return true;
	}
	return false;
}

//ajax成功后的回调函数
function successShowFun(data){
	if(data.code == "0"){
		showMsg("操作成功！");
	}
	else {
		showMsg("操作失败：" + data.msg, 5);
	}
}

function showMsg(msg, type, callback){
	layer.closeAll();
	if (isEmpty(type)) {
	    layer.msg(msg);
	}
	else if (isEmpty(callback)){
		layer.msg(msg, {icon: type});
	}
	else {
		layer.msg(msg, {icon: type}, callback);
	}
}