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
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript">

    var gridManager = null;
    var saveForm = null;
    var saveWindow = null;
    var noteWindow = null;
    var um = null;
    var typeData = null;
    var icon = '${ctx}/ligerUI/skins/icons/pager.gif';
    var manager, g;
    $(function() {
        $("#searchbtn").ligerButton({
            click : function() {
                search();
            }
        });
        setGrid();
        $("#pageloading").hide();
        f_initGrid();
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
                    align : 'center',
                    render: function (rowdata, rowindex, value)
                    {
                        return "<a href='#'  onclick='shownote("+JSON.stringify(rowdata)+");' >审核</a>";
                    }
                },
                {
                    display : '推广员姓名',
                    name : 'name',
                    align : 'center',
                    width : 100,
                    minWidth : 30
                },{
                    display : '手机号',
                    name : 'phone',
                    align : 'left',
                    minWidth : 60
                },{
                    display : '单子名称',
                    name : 'taskname',
                    align : 'left',
                    minWidth : 60
                },{
                    display : '状态',
                    name : 'status',
                    minWidth : 10,
                    align : 'center',
                    width : 50,
                    render : function(rowdata, rowindex, value) {
                        if(rowdata.status==1){
                            return "已审核"
                        }else if(rowdata.status==0){
                            return "未审核";
                        }else{
                            return ""
                        }
                    }
                },{
                    display : '团队/公司名称',
                    name : 'teamname',
                    align : 'left',
                    minWidth : 60
                },{
                    display : '支付宝',
                    name : 'alipay',
                    align : 'left',
                    minWidth : 60
                },{
                    display : 'qq',
                    name : 'qq',
                    align : 'left',
                    minWidth : 60
                },{
                    display: '文件下载',
                    isSort: false,
                    isExport: false,
                    width: 50,
                    render: function (rowdata, rowindex, value)
                    {
                        return "<a href='${ctx}/file/"+rowdata.filename+"' target='_parent' >下载</a>";
                    }
                },
                {
                    display : '提交时间',
                    name : 'create_time',
                    align : 'center',
                    width : 150
                }
            ],
            pageSize : 15,
            url : "${ctx}/admin/batch/searchlist",
            rownumbers : true,
            selectRowButtonOnly : true,
            isScroll : true
        });
    }


    function search(){
        var parms = $("#queryForm").serializeJson();
        gridManager.set("parms", parms);
        gridManager.loadData();
    }

    function closeWindow() {
        saveWindow.hide();
    }

    function shownote(data){
        if (noteWindow == null) {
            noteWindow = $.ligerDialog.open({
                title : "note",
                target : $("#noteWindow"),
                width : 600,
                height : 'auto',
                isResize : true
            });
        }
        $("#upnoteid").val(data.id);
        noteWindow.show();
    }
    function addnote(){
        var upid = $("#upnoteid").val();
        var note = $("#note").val();

        $.ajaxFileUpload({
            type : "POST",
            url : "updatenote",
            data : {'id':upid,'note':note},
            dataType : "text",
            secureuri : false,//一般设置为false
            fileElementId :"file",
            success : function(result) {
                if (result == 'success') {
                    search();
                    $.ligerDialog.waitting('success');
                    setTimeout(function() {
                        $.ligerDialog.closeWaitting();
                    }, 500);
                } else {
                    $.ligerDialog.warn(result);
                }
            }
        });
        closenoteWindow();
    }

    function closenoteWindow() {
        noteWindow.hide();
        $("#note").val("");
        cleanFile();
    }

    function cleanFile(){
        var file = $("#file");
        file.after(file.clone().val(""));
        file.remove();
    }

</script>
</head>
<body style="padding: 5px;">
<div class="l-loading" style="display:block" id="pageloading"></div>
<form id="queryForm">
    <div class="l-panel-search">
        <div class="l-panel-search-item">推广员姓名</div>
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
<div id="noteWindow" style="width:99%; margin:3px; display:none;">
    <div class="l-dialog-body" style="width: 100%;">
        <textarea cols="100" rows="15" class="l-textarea"  style="width: 99%" id="note" name="note"></textarea>
        <input type="file" id="file" name="file">
        <input type="hidden" id="upnoteid" name="upnoteid" />
        <div class="l-dialog-buttons">
            <div class="l-dialog-buttons-inner">
                <div class="l-dialog-btn">
                    <div class="l-dialog-btn-l"></div>
                    <div class="l-dialog-btn-r"></div>
                    <div class="l-dialog-btn-inner" onclick="closenoteWindow();">取消</div>
                </div>
                <div class="l-dialog-btn">
                    <div class="l-dialog-btn-l"></div>
                    <div class="l-dialog-btn-r"></div>
                    <div class="l-dialog-btn-inner" onclick="addnote();">确定</div>
                </div>
                <div class="l-clear"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>