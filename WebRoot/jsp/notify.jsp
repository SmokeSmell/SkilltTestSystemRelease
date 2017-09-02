<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
   
    <link href="../table/pagination.css" rel="stylesheet"/> 
  	<link href="../table/normalize.css" rel="stylesheet"/> 
  	<link href="../table/common.css" rel="stylesheet"/> 
    <title>技能鉴定信息处理系统</title>
</head>
<body>
<div id="containner">
    <jsp:include page="head.jsp"></jsp:include>
     <link href="../css/AllNotify.css" type="text/css" rel="stylesheet" charset="utf-8">
     <script src="../js/showAllNotify.js" charset="gb2312"></script> 
    
	<script src="../table/jquery.pagination.min.js"></script>
    <div id="body">
    	<h1>查看所有通知</h1>
    	<div class="title_info">
    		<p class="name">通知名称</p>
    		<p class="depart_title">发布人(部门)</p>
    		<p class="time_title">发布时间</p>
    	</div>
    </div>
    <div class="page_button"></div>
    <jsp:include page="foot.jsp"></jsp:include>
</div>
</body>
</html>