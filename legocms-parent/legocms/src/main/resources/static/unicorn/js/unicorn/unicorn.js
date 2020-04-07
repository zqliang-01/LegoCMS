$(document).ready(function(){
	// === Tooltips === //
	$('.tip').tooltip();	
	$('.tip-left').tooltip({ placement: 'left' });	
	$('.tip-right').tooltip({ placement: 'right' });	
	$('.tip-top').tooltip({ placement: 'top' });	
	$('.tip-bottom').tooltip({ placement: 'bottom' });	
	
	// === Search input typeahead === //
	$('#search input[type=text]').typeahead({
		source: ['Dashboard','Form elements','Common Elements','Validation','Wizard','Buttons','Icons','Interface elements','Support','Calendar','Gallery','Reports','Charts','Graphs','Widgets'],
		items: 4
	});
	
	// === Style switcher === //
	$('#style-switcher i').click(function()
	{
		if($(this).hasClass('open'))
		{
			$(this).parent().animate({marginRight:'-=190'});
			$(this).removeClass('open');
		} else 
		{
			$(this).parent().animate({marginRight:'+=190'});
			$(this).addClass('open');
		}
		$(this).toggleClass('icon-arrow-left');
		$(this).toggleClass('icon-arrow-right');
	});
	
	$('#style-switcher a').click(function()
	{
		var style = $(this).attr('href').replace('#','');
		$('.skin-color').attr('href', res + '/unicorn/css/unicorn.'+style+'.css');
		$(this).siblings('a').css({'border-color':'transparent'});
		$(this).css({'border-color':'#aaaaaa'});
	});
	
	$('.style-item').click(function() {
		var style = $(this).attr('href').replace('#','');
		$('.skin-color').attr('href', res + '/unicorn/css/unicorn.'+style+'.css');
		$(this).css({'border-color':'#aaaaaa'});
	})
	
	// === datepicker === //
	Bind_Date($('.selectdate'), "right", 2);
	Bind_Date($('.selectdate-left'), "left", 2);

	$('.chosen-select').chosen();

	initMyPagination("table-condition", "table-template");
});

//全屏
function launchFullscreen(element) {
 if (!$("body").hasClass("full-screen")) {

     $("body").addClass("full-screen");

     if (element.requestFullscreen) {
         element.requestFullscreen();
     } else if (element.mozRequestFullScreen) {
         element.mozRequestFullScreen();
     } else if (element.webkitRequestFullscreen) {
         element.webkitRequestFullscreen();
     } else if (element.msRequestFullscreen) {
         element.msRequestFullscreen();
     }

 } else {

     $("body").removeClass("full-screen");

     if (document.exitFullscreen) {
         document.exitFullscreen();
     } else if (document.mozCancelFullScreen) {
         document.mozCancelFullScreen();
     } else if (document.webkitExitFullscreen) {
         document.webkitExitFullscreen();
     }

 }
}