<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 16/2/29
  Time: 上午1:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="add" method="post">
    <input type="hidden" name="id" id="id" />
<dl>
    <dt>新增<input type="text" id="txt1" class="l-text-field" ligeruiid="txt1"></dt>
    <dd>name:<input id="name" name="name" type="text"></dd>
    <dd><input type="submit" title="提交"/><input type="reset" title="重置"/></dd>
</dl>

    <dl>
        <dt>查询</dt>
        <c:forEach items="${list}" var="l">
            <dd>name:${l.name}
                <input type="button"  onclick="update('${l.id}','${l.name}')" title="修改" value="update" />
            <input type="button" value="del" onclick="del('${l.id}')" title="删除"/>
            </dd>
        </c:forEach>
    </dl>
</form>
</body>
   <link href="${ctx}/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src='../js/jquery.min.js'></script>
   <script src="${ctx}/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${ctx}/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function() {
        $("#txt1").ligerDateEditor({ showTime: true, label: '带时间', labelWidth: 100, labelAlign: 'left' });
    });

    function del(id){
        $.ajax({
            type : "POST",
            url : "del",
            data: {id:id},
            dataType: "json",
            success : function() {
                window.location.reload();
            }
        });
    }
    function update(id,name){
        $("#id").val(id);
        $("#name").val(name);
    }
</script>

</html>
