//设置全局变量
var jsonMyPagination;
var myPageObj;
var formData;
var paginationMap = new Object();
function myPaginationObj(jTempDivId, jTempObjDivId, queryForm, templateId, url, type, page, result) {
    this.jTempDivId = jTempDivId;
    this.jTempObjDivId = jTempObjDivId;
    this.queryForm = queryForm;
    this.templateId = templateId;
    this.url = url;
    this.type = type;
    if (page == null){
      this.page = 1;
    }else {
      this.page = page;
    }
    if (result == null){
      this.result = "page.totalPage";
    }else {
      this.result = result;
    }

    paginationMap[jTempDivId];
    return this;
}


function initMyPagination(id,templateId,url,callBackFun){
	var pObj = new myPaginationObj("mytab", "tempContentDiv", "queryForm", templateId, url, "table");
	myPagination.initPagination(id,pObj,callBackFun);

	$("#"+id+" .query").click(function() {
		myPagination.query(id,"queryForm", id);
	});

	enterClickEvent(function() {
		$("#"+id+" .query").click();
	},
	"#"+id+" input");
}

//处理回车事件
function enterClickEvent(callBackFun,keyDownBox){
	var obj=keyDownBox==null?document:keyDownBox;
	$(obj).keydown(function(e){
		if(!e)
			e=window.event;
		if((e.keyCode||e.which)==13){
			callBackFun();
		}
	});
}


//分页公共方法   callBackFun --> 回调函数
var myPagination = {
    initPagination: function(id, pObj, callBackFun) {
        if (pObj.queryForm != "") {
            formData = $("#" + id + " ." + pObj.queryForm).serialize(); //序列化表单
            formData = decodeURIComponent(formData, true); //解码
        }
        myPageObj = pObj;
        paginationMap[id] = $("#" + id + " ." + pObj.jTempObjDivId).myPagination({
            panel: panelInfo,
            currPage: pObj.page,
            pageNumber: 0,
            debug: false,
            ajax: {
                on: true,
                pageCountId: pObj.result,
                url: pObj.url,
                dataType: 'json',
                param: formData,
                ajaxStart: function() {
                    layer.load(0, {shade: false});
                },
                ajaxStop: function() {
                },
                callback: function(data) {
                    initData(data, $("#" + id + " ." + pObj.jTempDivId), pObj.templateId, pObj.type);
                    if (callBackFun != undefined) {
                        callBackFun(data);
                    }

                }
            }
        });
    },
    query: function(id,queryForm, mapKey) {
        formData = $("#" + id + " ." + queryForm).serialize(); //序列化表单
        formData = decodeURIComponent(formData, true); //解码
        window.parent.formDatas = formData; //缓存表单数据
        window.parent.currPage = 1; //重置页码

        paginationMap[mapKey].onLoad({
            param: formData
        });
    },
    reload: function(id) {
        $("#"+id+" .query").click();
    }
};

var panelInfo = {
    tipInfo_on: true,
    tipInfo: '&nbsp;总记录数：{totalCount}&nbsp;{input}/ {sumPage} 页&nbsp;&nbsp;',
    tipInfo_css: {
        width: '75px',
        height: "30px",
        border: "2px solid #f0f0f0",
        //padding: "0 0 0 5px",
        margin: "0 5px 0 5px"
        //color: "#48b9ef"
    }
};

function initData(data, pageDivObj, templateId, type) {
    if (type == "table") {
        //使用模板
        pageDivObj.find("tbody").setTemplateElement(templateId).processTemplate(data);
    } else {
        pageDivObj.setTemplateElement(templateId).processTemplate(data);
    }

}

//处理回车事件
function enterClickEvent(callBackFun,keyDownBox){
	var obj = keyDownBox == null ? document:keyDownBox;
	$(obj).keydown(function(e){
		if(!e){
			e=window.event;
	    }
		if((e.keyCode||e.which)==13){
			callBackFun();
		}
	});
}


//定义回车 tab
function bindEnterTab(container, submitbtn) {
    $(document).off("keydown", '#' + container.attr("id") + ' .forminput:visible');
    $(document).on('keydown', '#' + container.attr("id") + ' .forminput:visible' , function(e) {
        var inputs = container.find(".forminput:visible:not(':disabled')");
        var idx = inputs.index(this);

        if ( e.which == 17 ) {
            if ( e.shiftKey ) {
                for (var i = idx - 1; i >= 0; i--) {
                    var obj = inputs[i];
                    if (obj) {
                        if ($(obj).is(".minforminput")) {
                            obj.click();
                            obj.focus();
                            obj.select();
                            break;
                        }
                    }
                }
            } else {
                var isend = true;

                for (var i = idx + 1; i <= inputs.length - 1; i++) {
                    var obj = inputs[i];
                    if (obj) {
                        if ($(obj).is(".minforminput")) {
                            obj.click();
                            obj.focus();
                            obj.select();

                            isend = false;
                            break;
                        }
                    }
                }
                if (isend) {
                    submitbtn.click();
                }
            }
            return false;
        } else {
            if ( e.shiftKey && e.which == 13 ) {
                if (idx > 0) {
                    inputs[idx - 1].click();
                    inputs[idx - 1].focus();
                    inputs[idx - 1].select();
                }
                return false;
            }
            if (e.which == 13 ) {
                //alert(idx);
                if (idx >= inputs.length - 1) {
                    submitbtn.click();
                } else {
                    inputs[idx + 1].click();
                    inputs[idx + 1].focus();
                    inputs[idx + 1].select();
                }
                return false;
            }
        }
    });
}


function stop() {
    return false;
};
