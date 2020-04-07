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
        if (!getTab(menuId + "_tab")) {
            addTab(menuId, that.attr('name'));
            addPage(menuId, that.attr('data-url'));
        }
    });

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

    // 添加窗口
    function addTab(menuId, tabName) {
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
    }

    // 添加内容页面
    function addPage(menuId, path) {
    	var width = $('.app-main__inner').width() - 5;
    	var height = $('.app-main__inner').height() - 5;
    	var iframeHtml = '<iframe onload="this.height=' + height + '" frameborder="0" allowtransparency="true" width="' + width + 'px" src="' + path + '"></iframe>';
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

    var selectTag = function(tab) {
        $(".body-tabs li a").each(function(i, e) {
            if ($(this) != tab) {
                $(this).removeClass('active');
            }
        });
        tab.addClass('active');
    	selectPage($(tab.attr('href')));
    }
    
    var selectPage = function(page) {
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
    var setLeft = function() {
    	$(".body-tabs").css("left", -marginLeft + "px");
    }
    
    // top nav
    var marginLeft = 0;
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
    	marginLeft = 0;
    	setLeft();
    });
    // 关闭当前窗口
    $(".closetag").click(function () {
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
})