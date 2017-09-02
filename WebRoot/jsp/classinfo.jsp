<%@ page language="java" import="java.util.*,com.gong.db.*,com.gong.domain.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="renderer" content="webkit"/>
    <meta charset="UTF-8">
    <title>技能鉴定信息处理系统</title>
    <link href="../css/classinfoCss.css" type="text/css" rel="stylesheet" charset="gb2312">
    <script type="text/javascript" src="../js/jquery.min.js" charset="gb2312"></script>
    <script type="text/javascript" src="../js/skiltest.js" charset="gb2312"></script>

</head>
<script type="text/javascript">
	$.ajax({
		type:'POST',
		data:{operat:'PREAPPLYINFO'},
		url:'../frontservlet',
		dataType:'json',
		success:function(text,textStatus){
			//alert(text.infos['student_name']);
			if(text.state == "UnLogin"){
		 		alert("您还没有登陆，请先登陆!");
		 		window.location = "../jsp/index.jsp";
		 	}else if(text.state == "UnApply"){
		 		alert("您还没有进行预报名，请先预报名!");
		 		window.location = "../jsp/stuinfo.jsp";
		 	}
		}
	});
</script>
<body>
<%
	StuInfo info = (StuInfo)session.getAttribute("user");	
	if(info != null){
		String payinfo = "未缴费";
		PreApplyDAO predao = new PreApplyDAO();
		boolean ispay = predao.getFlag(info.getStudent_number());
		if(ispay)
			payinfo ="已缴费";
		StrClassDAO strdao = new StrClassDAO();
		HashMap<String, String> cls = (HashMap<String, String>)strdao.getStrClass(info.getStudent_number());
	
%>
<div id="containner">
    <jsp:include page="head.jsp"></jsp:include>
    <div id="body">
        <div id="center">
            <div id="title">
                
                <p id="title_name">计算机二级强化班预分班信息</p>
            </div>
            <div id="info_div">
                <div class="info">
                    <div class="info_name">姓名:</div>
                    <div class="info_value" id="stu_number2"><%=info.getStudent_name() %></div>
                </div>
                <div class="info">
                    <div class="info_name">学号:</div>
                    <div class="info_value"><%=info.getStudent_number() %></div>
                </div>
                <div class="info">
                    <div class="info_name">强化班名称:</div>
                    <div class="info_value"><%=cls.get("className") %></div>
                </div>
                <div class="info">
                    <div class="info_name">教师</div>
                    <div class="info_value"><%=cls.get("teacher") %></div>
                </div>
                <div class="info">
                    <div class="info_name">开课时间</div>
                    <div class="info_value"><%=cls.get("startTime") %></div>
                </div>
                <div class="info">
                    <div class="info_name">结束时间</div>
                    <div class="info_value"><%=cls.get("endTime") %></div>
                </div>
                 <div class="info">
                    <div class="info_name">上课地点</div>
                    <div class="info_value"><%=cls.get("place") %></div>
                </div>
                 <div class="info">
                    <div class="info_name">是否缴费:</div>
                    <div class="info_value"><%=payinfo %></div>
                </div>
                 <div class="info">
                    <div class="info_name">报名状态:</div>
                    <div class="info_value">预报名成功</div>
                </div>
            </div>
            <div id="tipdiv">
               	<p class="tip">*以上信息仅为预计班级信息，具体班级信息请在指定日期查看</p>
               	<p class="tip">*预报名后请在预报名结束之前尽快将考试费用缴到指定地点，否则将注销预报名信息</p>
    		</div>
	</div>
	<%} %>
 	<div id="foot_div">
 		<jsp:include page="foot.jsp"></jsp:include>
 	</div>
</body>
</html>