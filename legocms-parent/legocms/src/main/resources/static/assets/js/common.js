
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
		        	'<span>' + tabName + '</span><span class="closetag">×</span>' +
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

function showConfirm(msg, callback) {
	layer.confirm(msg, function(index){
		callback();
		layer.close(index);
	});
}

function showFrame(content, callback) {
	layer.open({
		type: 1,
		skin : 'layui-layer-rim', // 加上边框
		area : [ '420px', '500px' ], // 宽高
		content : content,
		btn: ['yes', 'no'],
		yes: function(index, layero){
			console.log(layero);
			callback();
			layer.close(index);
		}
	});
}

function isEmpty(value) {
    if (value == null || value == undefined || value == '') {
        return true;
    } else {
        return false;
    }
}

function isNotEmpty(value) {
    return !isEmpty(value);
}

//指定Form下的所有对象构造ajax的提交参数
function getDataFromForm(submitForm){
	var submitPara = "&";
	//获取指定Form下的所有input元素
	var inputItems=submitForm.find("input");
	//遍历指定Form下的所有input元素
	$(inputItems).each(function getInputVal(index,element){
		//获取text和hidden对象的value
		if($(element).attr("type")=="text" || $(element).attr("type")=="hidden" || $(element).attr("type")=="password"){
			submitPara += $(element).attr("name")+"="+$(element).attr("value")+"&";
		}
		//获取一组radio中被选中项的值
		if($(element).attr("type")=="radio" && $(element).attr("checked")==true){
			var radioName = $(element).attr("name");
			submitPara += radioName+"="+$(element).attr("value")+"&";
		}
		//获取复选框中被选中项的值
		if($(element).attr("type")=="checkbox"){
			var checkBoxName = $(element).attr("name");
			//一组复选框名称只能出现一次
			if(submitPara.indexOf(checkBoxName)==-1){
				submitPara += checkBoxName+"=";
			}
			if( $(element).attr("checked")==true){
				submitPara += $(element).attr("value")+",";
			}
		}
	});
	//获取指定Form下的所有select元素
	var selectItems=submitForm.find("select");
	$(selectItems).each(function getInputVal(index,element){
		//获取下拉列表中被选中项的值
		submitPara += $(element).attr("id")+"="+$(element).val()+"&";
	});
	//返回串中不包含字符串末尾的&
	return submitPara.substring(1,submitPara.length-1);
}