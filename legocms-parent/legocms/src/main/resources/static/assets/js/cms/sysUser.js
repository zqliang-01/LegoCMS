$(function() {
	var url = ctx + "/admin/directive/sysUserList";
	initMyPagination("MyPagination-user-list", "Template-user-list", url);

})

function active(code) {
	window.parent.showConfirm("是否激活该用户信息！", function(){
		var url = ctx + "/admin/user/active";
		ajaxSubmit(url, "code=" + code, function() {
			myPagination.reload('MyPagination-user-list');
		});
	})
}
function invalid(code) {
	window.parent.showConfirm("是否注销该用户信息！", function(){
		var url = ctx + "/admin/user/invalid";
		ajaxSubmit(url, "code=" + code, function() {
			myPagination.reload('MyPagination-user-list');
		});
	})
}