
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

function showTextDialog(title, text) {
	layer.open({
		type: 1,
		title: title,
		area : [ '550px', '400px' ],
		maxmin: true,
		content : "<div class='card-body'>" + text + "</div>"
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
						if (treeNode.isParent && data.selectParent) {
							layer.close(index);
						}
						if (!treeNode.isParent) {
							layer.close(index);
						}
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

function showIframe(title, url) {
	layer.open({
		type: 2,
		title: title,
		area : [ '550px', '400px' ],
		maxmin: true,
		content : url
	});
}

function isEmpty(value) {
    if (value == null || value == undefined || value === '') {
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

function getFileType(name) {
	var index= name.lastIndexOf(".");
	return name.substr(index + 1);
}

var arr = ["jpg", "png", "gif"];
function isImage(fileType) {
	return arr.indexOf(fileType) > -1
}

/**----------------------------codemirror----------------------------------**/

var dummy = {
	attrs: {
    	color: ["red", "green", "blue", "purple", "white", "black", "yellow"],
    	size: ["large", "medium", "small"],
    	description: null
    },
    children: []
};
var tags = {
	"!top": ["top"],
	"!attrs": {
		id: null,
		class: ["A", "B", "C"]
	},
	top: {
		attrs: {
			lang: ["en", "de", "fr", "nl"],
			freeform: null
		},
		children: ["animal", "plant"]
	},
	animal: {
		attrs: {
			name: null,
			isduck: ["yes", "no"]
		},
		children: ["wings", "feet", "body", "head", "tail"]
	},
	plant: {
		attrs: {
			name: null
		},
		children: ["leaves", "stem", "flowers"]
	},
	wings: dummy,
	feet: dummy,
	body: dummy,
	head: dummy,
	tail: dummy,
	leaves: dummy,
	stem: dummy,
	flowers: dummy
};

function completeAfter(cm, pred) {
	var cur = cm.getCursor();
	if (!pred || pred()) setTimeout(function() {
		if (!cm.state.completionActive)
			cm.showHint({
				completeSingle: false
			});
	}, 100);
	return CodeMirror.Pass;
}

function completeIfAfterLt(cm) {
	return completeAfter(cm, function() {
		var cur = cm.getCursor();
		return cm.getRange(CodeMirror.Pos(cur.line, cur.ch - 1), cur) == "<";
	});
}

function completeIfInTag(cm) {
	return completeAfter(cm, function() {
		var tok = cm.getTokenAt(cm.getCursor());
		if (tok.type == "string" && (!/['"]/.test(tok.string.charAt(tok.string.length - 1)) || tok.string.length == 1))
			return false;
		var inner = CodeMirror.innerMode(cm.getMode(), tok.state).state;
		return inner.tagName;
	});
}