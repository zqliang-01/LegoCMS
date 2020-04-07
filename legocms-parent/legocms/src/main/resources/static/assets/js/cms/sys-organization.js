$(function(){
	refreshTree();
	function refreshTree() {
		var treeUrl = ctx + "/admin/directive/sysOrganizationTree";
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
			$.fn.zTree.init($("#organizationTree"), setting, data.simpleTree);
		});
	}
	
	function refreshForm(code) {
		var detailUrl = ctx + "/admin/directive/sysOrganization";
	    ajaxSubmit(detailUrl, 'code=' + code, function(data) {
	    	var organization = data.organization;
	    	var form = $('#save-organization-form');
	    	console.log(organization);
	    	form.resetForm();
	    	form.setForm(organization);
	    	form.find("[name=code]").attr("readonly", "");
	    })
	}
	
	$('#add').click(function() {
		var nodes = getTree('organizationTree').getSelectedNodes();
		if (isEmpty(nodes)) {
			showMsg('请选择上级授权', 5);
			return;
		}
		var form = $('#save-organization-form');
		form.resetForm();
		form.find("input[name=code]").removeAttr("readonly");
		form.find("input[name='parent.code']").val(nodes[0].code);
	});
	
	$('#delete').click(function() {
		var code = $('#save-organization-form').find("[name=code]").val();
		if (isEmpty(code)) {
			showMsg("请选择需要删除的权限", 5);
			return;
		}
		window.parent.showConfirm("是否确认删除该部门信息！", function(){
			var url = ctx + "/admin/organization/delete";
			ajaxSubmit(url, "code=" + code, function() {
				$('#save-organization-form').resetForm();
				refreshTree();
			});
		})
	});
	
	ajaxForm('save-organization-form', function() {
		showMsg('操作成功！', 1, function() {
			refreshTree();
			var code = $('#save-organization-form').find("[name=code]").val();
			refreshForm(code);
		});
	});
})