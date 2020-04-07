$(document).ready(function(){
	var url = ctx + "/admin/directive/sysUserList";
	initMyPagination("MyPagination-user-list", "Template-user-list", url);
});