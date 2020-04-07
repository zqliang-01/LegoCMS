
var messageMap = new Map();

/**----------------------------ajax----------------------------------**/

function showMsg(msg, type, callback){
	if (isEmpty(msg)) {
		return;
	}
	if (isEmpty(type)) {
	    layer.msg(msg, {time: 1000});
	}
	else if (isEmpty(callback)){
		layer.msg(msg, {icon: type, time: 1000});
	}
	else {
		layer.msg(msg, {icon: type, time: 1000}, callback);
	}
}

function showConfirm(msg, title, callback) {
	if (isNotEmpty(msg)) {
		layer.confirm(msg, {icon: 3, title: title}, function(index){
			callback();
			layer.close(index);
		});
	}
}

function showFormDialog(title, id, init, callback) {
	var form;
	var submitNum = 0;
	layer.open({
		type: 1,
		title: title,
		area : [ '420px' ],
		content : id.html(),
		success: function(layero, index) {
			form = $(id.attr('data-form'));
			if (isNotEmpty(init)) {
				init(form);
			}
		},
		btn: ['确定', '取消'],
		yes: function(index, layero){
			if (submitNum == 0) {
				ajaxForm(form, function() {
					if (isNotEmpty(callback)) {
						callback(form);
					}
					layer.close(index);
				});
			}
			submitNum += 1;
			form.submit();
		}
	});
}

function showSimpleDialog(title, id, callback) {
	layer.open({
		type: 1,
		title: title,
		area : [ '520px', '600px'],
		content : id.html(),
		success: function(layero, index){
			$(id.attr('data-form')).click(function() {
				if (isNotEmpty(callback)) {
					callback($(this));
				}
				layer.close(index);
			})
		}
	});
}

function showSimpleTree(title, data, callBack) {
	var index = layer.open({
		type: 1,
		title: title,
		area : [ '300px', '450px' ],
		content : '<ul id="simpleTree" class="ztree"></ul>',
		success: function(layero, index){
			var setting = {
				data: {
					simpleData: {
						enable: true,
						idKey : 'code',
						pIdKey : "parentCode"
					}
				},
				callback: {
					onClick: function(event, treeId, treeNode) {
						callBack(treeNode);
						layer.close(index);
					}
				}
			};
			$.fn.zTree.init($("#simpleTree"), setting, data);
		}
	});
}

function showCheckTree(title, data, callBack) {
	var index = layer.open({
		type: 1,
		title: title,
		area : [ '300px', '400px' ],
		content : '<ul id="checkTree" class="ztree"></ul>',
		btn: ['确定', '取消'],
		success: function(layero, index){
			var setting = {
				check: {enable: true},
				data: {
					simpleData: {
						enable: true,
						idKey : 'code',
						pIdKey : "parentCode"
					}
				}
			};
			$.fn.zTree.init($("#checkTree"), setting, data);
		},
		yes: function() {
			var nodes = getTree('checkTree').getCheckedNodes(true);
			callBack(nodes);
			layer.close(index);
		}
	});
}

function isEmpty(value) {
    if (value == null || value == undefined || value == '') {
        return true;
    }
    if($.isArray(value) && value.length == 0){
        return true;
    }
    return false;
}

function isNotEmpty(value) {
    return !isEmpty(value);
}

function getTree(treeId) {
	return $.fn.zTree.getZTreeObj(treeId);
}

function getMessage(code) {
	var message = messageMap.get(code);
	console.log(message);
	if (isEmpty(message)) {
		var url = ctx + '/admin/message';
		ajaxSyncGetSubmit(url, "code=" + code, function(date){
			message = date.result;
			if (isNotEmpty(message)) {
				messageMap.set(code, message);
			}
			else {
				message = 'error message code:' + code;
			}
		});
	}
	console.log(parent.messageMap);
	return message;
}