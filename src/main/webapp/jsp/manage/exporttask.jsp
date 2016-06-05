<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 16/3/6
  Time: 下午12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp"%>
<html>
<head>
    <title>任务管理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
</head>
<link href="${ctx}/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${ctx}/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/umeditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/uploadPreview.js"></script>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript">
    (function($){
        $.fn.serializeJSON=function(){
            var serializeObj={};
            $(this.serializeArray()).each(function(){
                serializeObj[this.name]=this.value;
            });
            return serializeObj;
        };
    })(jQuery);

    var gridManager = null;
    var saveForm = null;
    var saveWindow = null;
    var um = null;
    var typeData = null;
    var checktypelist=null;
    var icon = '${ctx}/ligerUI/skins/icons/pager.gif';
    var typeData = [{ type: 1, text: '文本' }, { type: 2, text: '图片'}];
    var manager, g;
    var myDate = new Date();
    var t=myDate.getFullYear()+'-'+myDate.getMonth()+'-'+myDate.getDate()+' '+myDate.getHours()+':'+myDate.getMinutes();
    $(function() {
        $("#searchbtn").ligerButton({
            click : function() {
                search();
            }
        });
        setGrid();
        $("#begintime").ligerDateEditor({ showTime: true});
        $("#endtime").ligerDateEditor({ showTime: true});
        $("#begintime").ligerGetDateEditorManager().setValue(t);
        $("#endtime").ligerGetDateEditorManager().setValue(t);
        um = UM.getEditor('myEditor');
        $("#pageloading").hide();
//        f_initGrid();
        $("#file").uploadPreview({ Img: "ImgPr", Width: 200, Height: 200 });

    });

    function setGrid(){
        //表格
        gridManager = $("#maingrid").ligerGrid({
            columns : [
                {
                    display: '操作',
                    isSort: false,
                    isExport: false,
                    width: 50,
                    align : 'right',
                    render: function (rowdata, rowindex, value)
                    {
                        var a="<a href='#'  onclick='shownote("+JSON.stringify(rowdata)+");' >导出</a>";
                        var b="<a href='#'  onclick='shownote("+JSON.stringify(rowdata)+");' >导入</a>";
                        return a+b;
                    }
                },
                {
                    display : '任务名称',
                    name : 'name',
                    align : 'center',
                    width : 200,
                    minWidth : 30
                },{
                    display : '描述',
                    name : 'description',
                    align : 'left',
                    minWidth : 60
                }, {
                    display : '状态',
                    name : 'status',
                    minWidth : 10,
                    align : 'center',
                    width : 50,
                    render : function(rowdata, rowindex, value) {
                        if(rowdata.status==1){
                            return "有效"
                        }else if(rowdata.status==0){
                            return "无效";
                        }else{
                            return ""
                        }
                    }

                },
                {
                    display : '开始时间',
                    name : 'begintime',
                    align : 'center',
                    width : 150
                },  {
                    display : '结束时间',
                    name : 'endtime',
                    align : 'center',
                    width : 150
                }
            ],
            pageSize : 15,
            url : "${ctx}/admin/task/searchlist",
            rownumbers : true,
            checkbox : true,
            selectRowButtonOnly : true,
            isScroll : true,
            toolbar : {
                items : [ {
                    id : 'add',
                    text : '增加',
                    click : itemclick,
                    img : '${ctx}/ligerUI/skins/icons/addpage.png'
                }, {
                    id : 'delete',
                    text : '删除',
                    click : itemclick,
                    img : '${ctx}/ligerUI/skins/icons/busy.gif'
                }]
            }
        });
    }

    function editColumn(data){
        showWindow();
        $("#taskid").val(data.id);
        $("#name").val(data.name);
        $("#type").val(data.type);
        $("#begintime").val(data.begintime);
        $("#endtime").val(data.endtime);
        $("#unitprice").val(data.unitprice);
        $("#status").val(data.status);
        $("#total").val(data.total);
        $("#keyword").val(data.keyword);
        $("#description").val(data.description);
        $("#imgfile").val(data.imgfile);
//        um.setContent(data.content);
        getcontent(data.id);
        $("#ImgPr").attr('src','${ctx}/images/taskimg/'+data.logoimg);
        checktypelist.setValue(data.checktype);
        var manager = $("#maingrid2").ligerGetGridManager();

        $.ajax({
            type : "POST",
            url : "gettaskcheck",
            data : {taskid:data.id},
            dataType : "json",
            success : function(result) {
                $.each(result, function(index, content)
                {
                    manager.addRow({
                        name: content.name,
                        type: content.type,
                        minlength : content.minlength,
                        maxlength : content.maxlength,
                        regular: content.regular
                    });
                });
            }
        });

    }

    function clear(){
        $("#taskid").val('');
        $("#name").val('');
        $("#begintime").val(t);
        $("#endtime").val(t);
        $("#unitprice").val('');
        $("#total").val('');
        $("#keyword").val('');
        $("#description").val('');
//      $('#dataForm').reset();
        um.setContent('');
        $("#ImgPr").attr('src','');
//        f_initGrid();
    }

    function search(){
        var parms = $("#queryForm").serializeJson();
        gridManager.set("parms", parms);
        gridManager.loadData();
    }

    function renderDescription(rowdata, index, value) {
        if(value != null) {
            return "<div title='" + value + "'>" + value + "<div>";
        }
    }

    function showWindow(){
        if (saveWindow == null) {
            saveWindow = $.ligerDialog.open({
                target : $("#addWindow"),
                width : 800
            });
        }
        saveWindow.show();
    }

    function itemclick(item) {
        if (item.id) {
            switch (item.id) {
                case "add":
                    clear();
                    showWindow();
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0) {
                        $.ligerDialog.waitting('请选择行');
                        setTimeout(function() {
                            $.ligerDialog.closeWaitting();
                        }, 500);
                    } else {
                        $.ligerDialog.confirm('确认要删除?', function(yes) {
                            if (yes) {
                                removeData(data);
                            }
                        });
                    }
                    return;
                case "export":
                    exportExcel(gridManager, $("#queryForm"), '${ctx}');
                    return ;
            }
        }
    }

    function closeWindow() {
        saveWindow.hide();
    }

    function removeData(data) {
        $.ajax({
            type : "POST",
            url : "remove",
            data : JSON.stringify(data),
            contentType : "application/json; charset=utf-8",
            dataType : "text",
            success : function(result) {
                if (result == 'success') {
                    search();
                    $.ligerDialog.waitting('删除成功');
                    setTimeout(function() {
                        $.ligerDialog.closeWaitting();
                    }, 500);
                } else {
                    $.ligerDialog.warn(result);
                }
            }
        });
    }

    function save() {
        if (saveForm.valid()) {
            $("#pageloading").show();
            var params = saveForm.getData();
            $.ajax({
                type : "POST",
                url : "savepatient",
//                data : JSON.stringify(params),
                data : params,
                 dataType : "text",
                success : function(result) {
                    if (result == 'success') {
                        search();
                        saveWindow.hide();
                        $.ligerDialog.waitting('操作成功');
                        setTimeout(function() {
                            $.ligerDialog.closeWaitting();
                        }, 500);
                    } else {
                        $.ligerDialog.warn(result);
                    }
                    $("#pageloading").hide();
                }
            });
        }
    }

    function deleteRow()
    {
        var manager = $("#maingrid2").ligerGetGridManager();
        manager.deleteSelectedRow();
    }
    function addNewRow()
    {
        var manager = $("#maingrid2").ligerGetGridManager();
        manager.addRow({
            type: '1'
        });
    }

    function closewindow(){
        $.ligerDialog.hide();
    }

    function submitdata(){
//        var data = g.getData();
        $("#content").val(um.getContent().replace(/\"/g,"\'"));
//        $("#tabledata").val(JSON.stringify(data).replace(/\"/g,"\'"));
        $("#checktypeids").val($("#lbchecktypeids").val());



        $.ajaxFileUpload( {
            url : "savetask",
            secureuri : false,//一般设置为false
            data:$("#dataForm").serializeJSON(),
            fileElementId :"file",
            type : "POST",
            success : function(result) {
                search();
                $.ligerDialog.success("处理成功", "提示", function(){
                    $.ligerDialog.hide();
                });
            }
        });
    }

    function getcontent(id) {
            $.ajax({
                type : "POST",
                url : "getcontent",
//                data : JSON.stringify(params),
                data : {taskid:id},
                dataType : "text",
                success : function(result) {
                    um.setContent(result);
                }
            });
    }

</script>
</head>
<body style="padding: 5px;">
<div class="l-loading" style="display:block" id="pageloading"></div>
<form id="queryForm">
    <div class="l-panel-search">
        <div class="l-panel-search-item">任务名称</div>
        <div class="l-panel-search-item">
            <input type="text" id="queryname" name="queryname"  class="liger-textbox" />
        </div>
        <div class="l-panel-search-item">
            <input type="button" id="searchbtn" value="查询" />
        </div>
    </div>
</form>
<!-- 表格 -->
<div id="maingrid" style="margin:0; padding:0"></div>
<div style="display:none;"></div>
</body>
</html>