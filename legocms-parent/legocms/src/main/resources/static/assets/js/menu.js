$(function(){
    // left menu
    $('.menu-item').click(function(){
        let that = $(this);
        $('.menu-item').each(function(e){
            if (that != $(this) && $(this).hasClass('mm-active')) {
                $(this).removeClass('mm-active');
            }
        })
        that.addClass('mm-active');
        let parent = that.parent().parent().parent('.menu-item-top');
        $('.menu-item-top').each(function(e){
            if (parent != $(this)) {
                $(this).removeClass('mm-active');
            }
        });
        that.parent().parent().parent().find('.menu-item-top').addClass('mm-active');
        var menuId = that.attr('id');
        var menuName = that.html();
        var menuUrl = that.attr('data-url');
        addTab(menuId, menuName, menuUrl);
        $(document).attr("title", that.attr('name'));
    });
    
    // 向左滚动
    $(".nav-scroll-left").click(function () {
    	// tab栏的3分之2宽度
    	var boxWidth1 = parseInt($(".tabbed").width() / 2);
    	if (marginLeft > 0) {
    		if (marginLeft > boxWidth1) {
    			marginLeft = marginLeft - boxWidth1;
    		}
    		else {
    			marginLeft = 0;
    		}
    		setLeft();
    	}
    });
    // 向右滚动
    $(".nav-scroll-right").click(function () {
    	var boxWidth = $(".tabbed").width();
    	var ulWidth = $(".body-tabs").width();
    	var boxWidth1 = parseInt($(".tabbed").width() / 3) * 2;
    	if (marginLeft < ulWidth - boxWidth) {
    		if (ulWidth - boxWidth - boxWidth1 > marginLeft) {
    			marginLeft = marginLeft + boxWidth1;
    		} else {
    			marginLeft = ulWidth - boxWidth + 15;
    		}
    		setLeft();
    	}
    });
    // 关闭所有窗口
    $(".closeAll").click(function() {
    	$(".body-tabs").html("");
    	$(".tab-content").html("");
    	tabArray.length = 0;
    	marginLeft = 0;
    	setLeft();
    });
    // 关闭其他窗口
    $(".closeOther").click(function () {
    	$(".body-tabs li a").each(function () {
    		if(!$(this).hasClass("active")){
    			$(this).remove();
            	$($(this).attr('href')).remove();
    		}
    	});
    	tabArray.length = 0;
    	push($(this).parent().attr('id'));
    	marginLeft = 0;
    	setLeft();
    });
})
var marginLeft = 0;
var tabArray = new Array();
function push(id) {
	for(var i = 0; i < tabArray.length; i++) {
		if (tabArray[i] == id) {
			tabArray.splice(i, 1);
		}
	}
	tabArray.push(id);
}
function pop() {
	tabArray.pop();
	return tabArray[tabArray.length - 1];
}

// 添加窗口
function addTab(menuId, tabName, menuUrl) {
	var tabId = menuId + "-tab";
	var pageId = menuId + "-page";
    if (selectTab(tabId)) {
    	return;
    }
	push(tabId);
	var tabHtml = 
    	  '<li class="nav-item">' +
		       '<a role="tab" class="nav-link active show" id="' + tabId + '" data-toggle="tab" href="#' + pageId + '">' +
		        	'<span>' + tabName + '</span><span class="closetag">&times;</span>' +
		       '</a>' +
		  '</li>';
	var liTab = $(tabHtml);
	liTab.appendTo($("#tab-home"));
	liTab.click(function () {
		push(tabId);
	});
	liTab.find(".closetag").click(function() {
        let a = $(this).parent();
        let li = a.parent();
    	closeTabWidth = li[0].offsetWidth;
		push(tabId);
		selectTab(pop());
		$('#' + pageId).remove();
    	li.remove();
	});
	setNavMarginLeft();
    addPage(pageId, menuUrl);
}

// 添加内容页面
function addPage(pageId, path) {
	var iframe = $('<iframe class="tab-pane tabs-animation fade" role="tabpanel" frameborder="0" allowtransparency="true" width="100%"></iframe>');
	iframe.attr("src", ctx + path);
	iframe.attr("id", pageId);
	iframe.appendTo($(".tab-content"));
	selectPage(iframe);
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

function selectTab(tabId) {
	var tab;
    $(".body-tabs li a").each(function(i, e) {
        if ($(this).attr('id') != tabId) {
            $(this).removeClass('active');
        }
		else {
			tab = $(this);
			tab.addClass('active');
			selectPage($(tab.attr('href')));
		}
    });
	return tab;
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
