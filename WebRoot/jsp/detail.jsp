<%@ page language="java"  pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>技能鉴定信息处理系统</title>
    <link href="../css/detailCss.css" type="text/css" rel="stylesheet" charset="utf-8">
    <link rel="stylesheet" href="../font-awesome/css/font-awesome.min.css" type="text/css">
</head>
<body>
<div id="containner">
    <jsp:include page="head.jsp"></jsp:include>
     <script type="text/javascript" src="../js/detail.js" charset="gb2312"></script>
    <div id="body">
    	<div class="notify_info_div">
    		<p class="pub_time">发布时间:&nbsp;</p>
    		<p class="publisher">发布人(部门):&nbsp;</p>
    	</div>
        <div id="center">
            <h1 id="title"></h1>
            <div id="cnt"></div>
            <div class="extr_div">
            	<i class="fa fa-paperclip ">附件:</i>
            	<a href="#"></a>
            </div>
        </div>
    </div>
    <jsp:include page="foot.jsp"></jsp:include>
</div>
</body>
</html>