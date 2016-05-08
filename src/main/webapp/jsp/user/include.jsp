<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- jquery -->
<script type="text/javascript" src='${ctx}/js/jquery.min.js'></script>
<script type="text/javascript" src='${ctx}/js/flexible.debug.js'></script>
<script type="text/javascript" src='${ctx}/js/flexible_css.debug.js'></script>
<script>
    function gopage(p){
        if(1==p){
            window.location.href="${ctx}/zdk/task1";
        }else if(2==p){
            window.location.href="${ctx}/zdk/task2";
        }else if(3==p){
            window.location.href="${ctx}/zdk/td";
        }else if(4==p){
            window.location.href="${ctx}/zdk/home";
        }else if(5==p){
            window.location.href="${ctx}/zdk/index";
        }
    }

</script>

<!-- css -->
<link href="${ctx}/css/zdk.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/css/base.css" rel="stylesheet" type="text/css"/>