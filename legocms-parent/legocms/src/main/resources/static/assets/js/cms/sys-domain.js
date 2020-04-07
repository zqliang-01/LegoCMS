$(function() {
	var url = ctx + "/admin/directive/sysDomainList";
	initMyPagination("MyPagination-domain-list", "Template-domain-list", url);
})

function del(code) {
	window.parent.showConfirm("是否删除该域名信息！", '域名删除', function(){
		var url = ctx + "/admin/domain/delete";
		ajaxSubmit(url, "code=" + code, function() {
			myPagination.reload('MyPagination-domain-list');
		});
	})
}
