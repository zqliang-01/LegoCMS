
/**----------------------------多窗口控制----------------------------------**/
var marginLeft = 0;

// 添加窗口
function addTab(menuId, tabName, menuUrl) {
    if (getTab(menuId + "_tab")) {
    	return;
    }
    menuUrl = ctx + menuUrl;
	var tabHtml = 
    	  '<li class="nav-item">' +
		       '<a role="tab" class="nav-link" id="' + menuId + '_tab" data-toggle="tab" href="#' + menuId + '_page">' +
		        	'<span>' + tabName + '</span><span class="closetag">&times;</span>' +
		       '</a>' +
		  '</li>';
	var liTab = $(tabHtml);
	liTab.appendTo($("#tab-home"));
	liTab.find(".closetag").click(function() {
        let index;
        let a = $(this).parent();
        let li = a.parent();
    	closeTabWidth = li[0].offsetWidth;
    	if (li.index() > 0) {
    		index = $(".body-tabs li a").eq(li.index() - 1);
    	}
        else {
    		index = $(".body-tabs li a").eq(0);
    	}
        selectTag(index);
    	$(a.attr('href')).remove();
    	li.remove();
	});
    selectTag(liTab.find('.nav-link'));
	setNavMarginLeft();
    addPage(menuId, menuUrl);
}

function getTab(tabId) {
	var isContain = false;
	$("#tab-home").children().each(function (i, e) {
		if ($(this).find('.nav-link').attr('id') == tabId) {
			selectTag($('#' + tabId));
			isContain = true;
			return false;
		}
	});
	return isContain;
}

// 添加内容页面
function addPage(menuId, path) {
	var height = $('.app-main__inner').height() - 5;
	if (height < 300) {
		height = document.body.scrollHeight;
	}
	var iframeHtml = '<iframe onload="this.height=' + height + '" frameborder="0" allowtransparency="true" width="100%" src="' + path + '"></iframe>';
	var pageHtml = '<div class="tab-pane tabs-animation fade" id="' + menuId + '_page" role="tabpanel">' + iframeHtml + '</div>';
	var childdiv = $(pageHtml);
	childdiv.appendTo($(".tab-content"));
	selectPage(childdiv);
}

function setNavMarginLeft() {
	var boxWidth = $(".tabbed").width();
	var ulWidth = $("#tab-home").width();
	if (boxWidth < ulWidth) {
		marginLeft = ulWidth - boxWidth + 15;
	} else {
		marginLeft = 0;
	}
	setLeft();
}

function selectTag(tab) {
    $(".body-tabs li a").each(function(i, e) {
        if ($(this) != tab) {
            $(this).removeClass('active');
        }
    });
    tab.addClass('active');
	selectPage($(tab.attr('href')));
}

function selectPage(page) {
	$(".tab-content .tab-pane").each(function() {
		if ($(this) != page) {
            $(this).removeClass('active');
            $(this).removeClass('show');
        }
	})
	page.addClass('show');
	page.addClass('active');
}

// 设置焦点
function setLeft() {
	$(".body-tabs").css("left", -marginLeft + "px");
}

/**----------------------------ajax----------------------------------**/

function showMsg(msg, type, callback){
	layer.closeAll();
	if (isEmpty(type)) {
	    layer.msg(msg, {time: 2000});
	}
	else if (isEmpty(callback)){
		layer.msg(msg, {icon: type, time: 2000});
	}
	else {
		layer.msg(msg, {icon: type, time: 2000}, callback);
	}
}

function showConfirm(msg, callback) {
	layer.confirm(msg, function(index){
		callback();
		layer.close(index);
	});
}

function showFrame(data, callback) {
	layer.open({
		type: 1,
		skin : 'layui-layer-rim', // 加上边框
		area : [ '420px', '500px' ], // 宽高
		content : content,
		btn: ['确定', '取消'],
		yes: function(index, layero){
			console.log(layero);
			callback();
			layer.close(index);
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