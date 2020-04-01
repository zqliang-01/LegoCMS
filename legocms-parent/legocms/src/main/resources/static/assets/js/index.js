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
    })
    
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
    	marginLeft = 0;
    	setLeft();
    });
    // 关闭其他窗口
    $(".closeOther").click(function () {
    	$(".body-tabs li a").each(function () {
    		if(!$(this).hasClass("active")){
    			$(this).remove();
    		}
    	});
    	marginLeft = 0;
    	setLeft();
    });
    // 关闭当前窗口
    $(".closetag").click(function () {
        let index;
        let li = $(this).parent().parent();
    	closeTabWidth = li[0].offsetWidth;
    	if (li.index() > 0) {
    		index = $(".body-tabs li a").eq(li.index() - 1);
    	}
        else {
    		index = $(".body-tabs li a").eq(0);
    	}
        selectTag(index);
    	li.remove();
    });
    function selectTag(index) {
        $(".body-tabs li a").each(function(i, e) {
            if ($(this) != index) {
                $(this).removeClass('active');
            }
        });
        index.addClass('active');
    }
    // 设置焦点
    function setLeft() {
    	$(".body-tabs").css("left", -marginLeft + "px");
    }
})