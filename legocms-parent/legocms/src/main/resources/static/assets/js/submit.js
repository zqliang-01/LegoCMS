function ajaxForm(form, successFun){
	form.submit(function(e){
		if (form[0].checkValidity() == false) {
			form.addClass('was-validated');
			return;
		}
		var url = form.attr('action');
		var method = form.attr('method');
		if (isEmpty(method)) {
			method = 'POST';
		}
		if (form.hasFileInput()) {
			var data = form.serializeFile();
			ajaxFileSubmit(url, data, function(data) {
				form.removeClass('was-validated');
				showMsg("操作成功！", 1, function(){
					if (isNotEmpty(successFun)) {
						successFun(data);
					}
				});
			}, null, true, method);
		}
		else {
			var data = form.serialize();
			ajaxRequest(url, data, function(data) {
				form.removeClass('was-validated');
				showMsg("操作成功！", 1, function(){
					if (isNotEmpty(successFun)) {
						successFun(data);
					}
				});
			}, null, true, method);
		}
	});
}

function ajaxSubmit(url, data, successFun, errorFun){
	ajaxRequest(url, data, successFun, errorFun, true, 'POST');
}

function ajaxSyncSubmit(url, data, successFun, errorFun){
	ajaxRequest(url, data, successFun, errorFun, false, 'POST');
}

function ajaxSyncGetSubmit(url, data, successFun, errorFun){
	ajaxRequest(url, data, successFun, errorFun, false, 'GET');
}

function ajaxFileSubmit(url, data, successFun, errorFun) {
	var index;
	$.ajax({
		url: url,
		type: 'POST',
		data: data,
		processData: false,
		async: false,
		contentType: false,
		dataType: "json",
        beforeSend: function () { index = layer.load(); },
        success: function(data){
			layer.close(index);
			if(!isEmpty(data) && data.hasOwnProperty("code")){
				if (interceptorResponse(data)) {
					return;
				}
				if(isEmpty(successFun)){
					successShowFun(data);
				}
				else {
					successFun(data);
				}
			}
			else {
				showMsg("返回结果格式错误,have not code value!", 5);
			}
		},	//请求成功后的回调函数
		error: function(date){
			layer.close(index);
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

function ajaxRequest(url, data, successFun, errorFun, async, method){
	if(null==async ||'boolean'!= typeof(async)){
		async=true;
	}
	var index;
	$.ajax({
		url: url ,		//发送请求的地址
		data: data,		//要传递到服务器端的数据
		type: method,	//请求方式 ("POST" 或 "GET")
		async: async,	//请求默认为异步请求true
		cache: false,	//设置为 false 将不缓存此页面
		dataType: "json",	//预期服务器返回的数据类型
        beforeSend: function () { index = layer.load(); },
		success: function(data){
			layer.close(index);
			if(!isEmpty(data) && data.hasOwnProperty("code")){
				if (interceptorResponse(data)) {
					return;
				}
				if(isEmpty(successFun)){
					successShowFun(data);
				}
				else {
					successFun(data);
				}
			}
			else {
				showMsg("返回结果格式错误,have not code value!", 5);
			}
		},	//请求成功后的回调函数
		error: function(date){
			layer.close(index);
			if(isEmpty(errorFun)){
				showMsg("系统发生未知异常……" + date.responseJSON.status, 5);
			}
			else {
				layer.close(index);
				errorFun(data);
			}
		}
	});
}

function successShowFun(data){
	if(data.code == "0"){
		showMsg("操作成功！", 1);
	}
	else {
		showMsg("操作失败：" + data.msg, 5);
	}
}

function interceptorResponse(data) {
	if (data.code != "0") {
		if (data.code == "1000") {
			showMsg(data.msg, 5, function() {
				window.open(ctx + "/admin/login","_parent");
			});
		}
		else {
			showMsg(data.msg, 5);
		}
		return true;
	}
	return false;
}

