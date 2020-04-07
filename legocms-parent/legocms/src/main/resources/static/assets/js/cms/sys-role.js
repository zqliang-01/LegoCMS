$(function() {
	var url = ctx + "/admin/directive/sysRoleList";
	initMyPagination("MyPagination-role-list", "Template-role-list", url);

	ajaxForm('save-role-form', function() {
		$('#save-role-form').find("[name=code]").attr("readonly", "");
		myPagination.reload('MyPagination-role-list');
		$("#role-modal .close").click();
	});
})
function permission(code) {
	var url = ctx + "/admin/directive/sysPermissionCheckTree";
	ajaxSubmit(url, "roleCode=" + code, function(data) {
		window.parent.showCheckTree('角色授权', data.simpleCheckTree, function(nodes){
			var permissionCodes = new Array();
			$.each(nodes, function(index,value){
				permissionCodes.push(value.code);
			});
			console.log(permissionCodes);
			var url = ctx + "/admin/role/authorize";
			ajaxSubmit(url, { permisisons : permissionCodes, roleCode : code});
		});
	});
}
function edit(code) {
	var url = ctx + "/admin/directive/sysRole";
	ajaxSubmit(url, "code=" + code, function(data) {
		var form = $('#save-role-form');
		form.resetForm();
		if (data.role) {
			form.setForm(data.role);
	    	form.find("[name=code]").attr("readonly", "");
		}
		else {
			form.find("input[name=code]").removeAttr("readonly");
		}
	});
}
function del(code) {
	var url = ctx + "/admin/role/delete";
	window.parent.showConfirm("是否删除该角色信息！", function(){
		ajaxSubmit(url, "code=" + code, function(){
			myPagination.reload('MyPagination-role-list');
		});
	});
}