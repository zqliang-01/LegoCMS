$(function(){
	refreshTree();
	function refreshTree() {
		var treeUrl = ctx + "/admin/directive/sysPermissionTree";
		ajaxSubmit(treeUrl, null, function(data) {
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
						refreshForm(treeNode.code);
					}
				}
			};
			$.fn.zTree.init($("#permissionTree"), setting, data.simpleTree);
		});
	}
	
	function refreshForm(code) {
		var detailUrl = ctx + "/admin/directive/sysPermission";
	    ajaxSubmit(detailUrl, 'code=' + code, function(data) {
	    	var permission = data.permission;
	    	var form = $('#save-permission-form');
	    	form.resetForm();
	    	form.setForm(permission);
	    	form.find("[name=code]").attr("readonly", "");
	    	refreshIcon(permission.icon);
	    })
	}
	
	function refreshIcon(icon) {
    	$('#icon-view').removeClass();
    	$('#icon-view').addClass(icon);
	}
	
	$('#add').click(function() {
		var nodes = getTree('permissionTree').getSelectedNodes();
		if (isEmpty(nodes)) {
			showMsg('请选择上级授权', 5);
			return;
		}
		var form = $('#save-permission-form');
		form.resetForm();
		form.find("input[name=code]").removeAttr("readonly");
		form.find("input[name='parent.code']").val(nodes[0].code);
		refreshIcon('');
	});
	
	$('#delete').click(function() {
		var code = $('#save-permission-form').find("[name=code]").val();
		if (isEmpty(code)) {
			showMsg("请选择需要删除的权限", 5);
			return;
		}
		window.parent.showConfirm("是否删除该用授权信息，子节点会关联删除！", "权限删除", function(){
			var url = ctx + "/admin/permission/delete";
			ajaxSubmit(url, "code=" + code, function() {
				$('#save-permission-form').resetForm();
				refreshTree();
				refreshIcon('');
			});
		})
	});
	
	$('#chose-icon').click(function(){
		window.parent.showSimpleDialog("图标选择", $('#icon-modal'), function(id){
			var form = $('#save-permission-form');
			form.find("input[name='icon']").val(id.find('p').html());
			refreshIcon(id.find('p').html());
		})
	})
	
	ajaxForm($('#save-permission-form'), function() {
		refreshTree();
		var code = $('#save-permission-form').find("[name=code]").val();
		refreshForm(code);
	});
});