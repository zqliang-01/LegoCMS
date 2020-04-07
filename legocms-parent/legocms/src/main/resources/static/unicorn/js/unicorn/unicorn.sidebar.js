$(document).ready(function(){
	// === Sidebar navigation === //
	$('.submenu > a').click(function(e) {
		e.preventDefault();
		var submenu = $(this).siblings('ul');
		var li = $(this).parents('li');
		var submenus = $('#sidebar li.submenu ul');
		var submenus_parents = $('#sidebar li.submenu');
		if(li.hasClass('open'))
		{
			if(($(window).width() > 768) || ($(window).width() < 479)) {
				submenu.slideUp();
			} else {
				submenu.fadeOut(250);
			}
			li.removeClass('open');
		} else 
		{
			if(($(window).width() > 768) || ($(window).width() < 479)) {
				submenus.slideUp();			
				submenu.slideDown();
			} else {
				submenus.fadeOut(250);
				submenu.fadeIn(250);
			}
			submenus_parents.removeClass('open');		
			li.addClass('open');	
		}
	});
    
    $('.pagemenu > li > a').click(function(e) {
        var url = $(this).attr('data-ref');
        if (url) {
            var submenus_parents = $('#sidebar li.submenu');
            submenus_parents.removeClass('active');
            $('#sidebar').find('li').removeClass('active');

            var li = $(this).parents('li');
            li.addClass('active');
			
			var id = $(this).attr('id');
			var name = $(this).html();
			addTab(id, name, url);
        }
    });
	
    // 向左滚动
    $(".nav-scroll-left").click(function () {
    	// tab栏的3分之2宽度
    	var boxWidth = parseInt($(".tabbed").width() / 2);
    	if (marginLeft > 0) {
    		if (marginLeft > boxWidth) {
    			marginLeft = marginLeft - boxWidth;
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
    	var ulWidth = $("#tab-home").width();
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
    	$("#tab-home").html("");
    	$(".tab-content").html("");
    	marginLeft = 0;
    	setLeft();
    });
    // 关闭其他窗口
    $(".closeOther").click(function () {
    	$("#tab-home li").each(function () {
    		if(!$(this).hasClass("active")){
    			$(this).remove();
            	$($(this).attr('href')).remove();
    		}
    	});
    	marginLeft = 0;
    	setLeft();
    });
	var ul = $('#sidebar > ul');
	$('#sidebar > a').click(function(e) {
		e.preventDefault();
		var sidebar = $('#sidebar');
		if(sidebar.hasClass('open'))
		{
			sidebar.removeClass('open');
			ul.slideUp(250);
		} else 
		{
			sidebar.addClass('open');
			ul.slideDown(250);
		}
	});
	// === Resize window related === //
	$(window).resize(function()
	{
		if($(window).width() > 479)
		{
			ul.css({'display':'block'});	
			$('#content-header .btn-group').css({width:'auto'});		
		}
		if($(window).width() < 479)
		{
			ul.css({'display':'none'});
			fix_position();
		}
		if($(window).width() > 768)
		{
			$('#user-nav > ul').css({width:'auto',margin:'0'});
            $('#content-header .btn-group').css({width:'auto'});
		}
	});

	// === Fixes the position of buttons group in content header and top user navigation === //
	function fix_position()
	{
		var uwidth = $('#user-nav > ul').width();
		$('#user-nav > ul').css({width:uwidth + 5,'margin-left':'-' + uwidth / 2 + 'px'});
        
        var cwidth = $('#content-header .btn-group').width();
        $('#content-header .btn-group').css({width:cwidth,'margin-left':'-' + uwidth / 2 + 'px'});
	}
	
	if($(window).width() < 468)
	{
		ul.css({'display':'none'});
		fix_position();
	}
	if($(window).width() > 479)
	{
	   $('#content-header .btn-group').css({width:'auto'});
		ul.css({'display':'block'});
	}
	
});

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

function selectTab(id) {
	var tab;
	$("#tab-home li").each(function(i, e) {
		if ($(this).attr('id') != id) {
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
	$("#content-center .tab-pane").each(function() {
		if ($(this).attr('id') != page.attr('id')) {
			$(this).removeClass('active');
		}
	});
	page.addClass('active');
}

function addTab(id, name, url) {
	var tabId = id + "-tab";
	var pageId = id + "-page";
	if (selectTab(tabId)) {
		return;
	}
	push(tabId);
	var liTab = $("<li class='active'><a class='text' data-toggle='tab' href='#" + pageId + "'>" + name + "</a><a class='closetag'>&times;</a></li>");
	liTab.attr('id', tabId);
	liTab.attr('href', '#' + pageId);
	liTab.appendTo($("#tab-home"));
	liTab.click(function () {
		push(tabId);
	});
	liTab.find(".closetag").click(function () {
		let li = $(this).parent();
		closeTabWidth = li[0].offsetWidth;
		push(tabId);
		selectTab(pop());
		li.remove();
		$('#' + pageId).remove();
	});
	setNavMarginLeft();
	addPage(url, pageId);
}

function addPage(url, id) {
	var panel = $('<div style="height:100%;" class="tab-pane active"></div>');
	var iframeHtml = $('<iframe height="100%" frameborder="0" allowtransparency="true" width="100%"></iframe>');
	iframeHtml.attr('src', ctx + url);
	iframeHtml.appendTo(panel);
	panel.attr('id', id);
	panel.appendTo($('#content-center'));
	selectPage(panel);
}

var marginLeft = 0;
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

// 设置焦点
function setLeft() {
	$("#tab-home").css("left", -marginLeft + "px");
}