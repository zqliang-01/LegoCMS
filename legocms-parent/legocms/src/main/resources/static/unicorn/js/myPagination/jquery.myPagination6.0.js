 (function($) {
     function init(param, obj) {
         function getParam() {
             var a = "current=" + opts.currPage;
             return opts.ajax.param && (a += "&" + opts.ajax.param),
             a
         }
         function getPanelTipInfo() {
             var a = "";
             if (opts.panel.tipInfo_on) {
                 var b = document.createElement("span");
                 b = $(b);
                 var c = b.text(opts.panel.tipInfo).text();
                 if ( - 1 != c.indexOf("{input}")) {
                     var d = "<input type='text' value='" + opts.currPage + "' >";
                     c = c.replace("{input}", d),
                     c = c.replace("{sumPage}", opts.pageCount),
                     c = c.replace("{totalCount}", opts.totalCount),
                     b.html(c),
                     d = b.children(":text:first"),
                     d.css(opts.panel.tipInfo_css),
                     a = b.html()
                 } else if ( - 1 != c.indexOf("{select}")) {
                     for (var e = document.createElement("select"), f = 1; parseInt(opts.pageCount) >= f; f++) {
                         var g = new Option(f, f);
                         e.options.add(g)
                     }
                     b.html("");
                     var h = c.substr(0, c.indexOf("{select}")),
                     i = c.substr(c.indexOf("{select}") + "{select}".length).replace("{sumPage}", opts.pageCount);
                     b.append(h),
                     b.append(e),
                     b.append(i),
                     a = b.html()
                 }
             }
             return a
         }
         function onRequest() {
             debug(opts.id),
             debug("ajax请求参数列表:"),
             debug(getParam()),
             opts.ajax.on ? (opts.ajax.ajaxStart(), $.ajax({
                 url: opts.ajax.url,
                 type: opts.ajax.type,
                 data: getParam(),
                 contentType: "application/x-www-form-urlencoded;utf-8",
                 async: !0,
                 cache: !1,
                 timeout: 30000,
                 error: function() {
                     layer.closeAll();
                     layer.msg('数据加载失败！');
                 },
                 success: function(a) {
                     layer.closeAll();
                     opts.ajax.ajaxStop(),
                     responseHandle(a),
                     createPageBar()
                 }
             })) : createPageBar()
         }
         function responseHandle(data) {
             var pageCountId = opts.ajax.pageCountId,
             resultPageCount = 1;
             resultTotalCount = 1;
             switch (opts.ajax.dataType) {
             case "json":
                 var responseState = true;
                 try {
                     responseState = interceptorResponse(data);
                     if (responseState) {
     					 return;
     				 }
                     data = eval('(' + data + ')')
                 } catch(err) {} finally {
                     if (responseState) {
                         return;
                     }
                     resultPageCount = eval("data." + pageCountId),
                     resultTotalCount = eval("data.page.totalCount")
                 }
                 break;
             case "xml":
                 try {
                     resultPageCount = $(data).find(pageCountId).text()
                 } catch(e) {
                     debug("xml返回数据解析错误，使用默认的pageCount=1"),
                     resultPageCount = 1
                 }
                 break;
             default:
                 try {
                     resultPageCount = $(data).find(":hidden[id='" + pageCountId + "']").val()
                 } catch(e) {
                     debug("html返回数据解析错误，使用默认的pageCount=1"),
                     resultPageCount = 1
                 }
             }
             debug(opts.id),
             debug("返回总页数:" + resultPageCount),
             opts.pageCount = resultPageCount,
             opts.totalCount = resultTotalCount,
             opts.ajax.callback(data)
         }
         function createPageBar() {
             if (opts.pageCount) {
                 var a = opts.panel.links;
                 opts.currPage = opts.currPage > opts.pageCount ? opts.pageCount: opts.currPage;
                 var b = opts.currPage,
                 c = parseInt(opts.pageCount),
                 d = parseInt(opts.pageNumber / 2),
                 e = opts.pageNumber,
                 f = "";
                 opts.panel.first_on && (f = "<a href='" + a + "' paged='1'>" + opts.panel.first + "</a>"),
                 opts.panel.prev_on && (f += 1 == b ? '<span class="disabled" paged="' + opts.panel.prev + '">' + opts.panel.prev + " </span>": "<a href='" + a + "' paged='" + (b - 1) + "'>" + opts.panel.prev + " </a>");
                 var g = lastPage = 1;
                 f += getPanelTipInfo();
                 for (g = b - d > 0 ? g = b - d: 1, g + e > c ? (lastPage = c + 1, g = lastPage - e) : lastPage = g + e, 0 >= g && (g = 1), g; lastPage > g; g++) f += g == b ? '<span class="current" paged="' + g + '">' + g + "</span>": "<a href='" + a + "' paged='" + g + "'>" + g + "</a>";
                 opts.panel.next_on && (f += b == c ? '<span class="disabled" paged="' + opts.panel.next + '">' + opts.panel.next + " </span>": "<a href='" + a + "' paged='" + (parseInt(b) + 1) + "'>" + opts.panel.next + " </a>"),
                 opts.panel.last_on && (f += "<a href='" + a + "' paged='" + c + "'>" + opts.panel.last + "</a>"),
                 debug(opts.id),
                 debug("最终生成菜单："),
                 debug(f),
                 obj.html(f),
                 obj.children("select").val(opts.currPage),
                 obj.children("select").change(function() {
                     var a = parseInt($(this).children("option:selected").val());
                     opts.currPage = a,
                     onRequest()
                 }),
                 obj.children(":text").keyup(function() {
                     var a = $(this),
                     b = $.trim($(this).val());
                     if (0 != b.length) {
                         var c = /^\+?[0-9][0-9]*$/;
                         c.exec(b) || a.val(1)
                     }
                 }),
                 obj.children(":text").keypress(function(a) {
                     var b = a.which;
                     if (13 == b) {
                         var c = $.trim($(this).val());
                         c = 1 > c ? 1 : c,
                         c = c > opts.pageCount ? opts.pageCount: c,
                         obj.children("a").unbind("click"),
                         obj.children("a").each(function() {
                             $(this).click(function() {
                                 return ! 1
                             })
                         }),
                         opts.currPage = c,
                         onRequest()
                     }
                 }),
                 obj.children("a").each(function() {
                     var c = $(this);
                     c.click(function() {
                         var a = parseInt(c.attr("paged"));
                         return a = a > 0 ? a: 1,
                         c.children("a").unbind("click"),
                         c.children("a").each(function() {
                             $(this).click(function() {
                                 return ! 1
                             })
                         }),
                         opts.currPage = a,
                         opts.ajax.onClick(a),
                         onRequest(),
                         $(this).focus(),
                         !1
                     })
                 })
             } else {
                 if (opts.ajax.param == null || opts.ajax.param == "") {
                     obj.html("<center style='font-size:14px;'>暂无信息记录，快去添加吧</center>")
                 } else {
                     obj.html("<center style='font-size:14px;'>暂无符合条件的记录</center>")
                 }
             }
         }
         function debug(a) {
             opts.debug && $.fn.debug(a)
         }
         var defaults = {
             currPage: 1,
             pageCount: 10,
             pageNumber: 5,
             cssStyle: "badoo",
             debug: !1,
             ajax: {
                 on: !1,
                 type: "POST",
                 pageCountId: "pageCount",
                 url: "jsonTest.php",
                 dataType: "json",
                 param: !1,
                 onClick: function() {
                     return ! 1
                 },
                 ajaxStart: function() {
                     return ! 1
                 },
                 ajaxStop: function() {
                     return ! 1
                 },
                 callback: function() {
                     return ! 1
                 }
             },
             panel: {
                 first: "首页",
                 last: "尾页",
                 next: "下一页",
                 prev: "上一页",
                 first_on: !0,
                 last_on: !0,
                 next_on: !0,
                 prev_on: !0,
                 links: "#",
                 tipInfo_on: !1,
                 tipInfo: "<span>&nbsp;&nbsp;跳{currText}/{sumPage}页</span>",
                 tipInfo_css: {
                     width: "22px"
                 },
                 tipSelect_on: !1,
                 tipSelect: "跳转到{select} 共{sumPage}页"
             }
         },
         opts = $.extend(!0, defaults, param);
         opts.id = obj.attr("id"),
         obj.addClass(opts.cssStyle),
         onRequest();
         var method = {};
         return method.id = opts.id,
         method.getPage = function() {
             return opts.currPage
         },
         method.onReload = function() {
             debug("reload()"),
             onRequest()
         },
         method.onLoad = function(a) {
             a && a instanceof Object && (debug(a), opts.currPage = 1, opts.ajax.param = a.param, onRequest())
         },
         method.jumpPage = function(a) {
             debug("jumpPage(" + a + ")"),
             a = (1 > a ? 1 : a),
             a = (a > opts.pageCount ? opts.pageCount: a),
             opts.currPage = a,
             onRequest(),
             alert("11")
         },
         method
     }
     $.fn.myPagination = function(a) {
         return init(a, $(this))
     },
     $.fn.debug = function(a) {
         window.console && window.console.log && console.log(a)
     }
 })(jQuery);
