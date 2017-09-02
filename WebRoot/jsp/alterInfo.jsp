<%@ page language="java" import="java.util.*,com.gong.domain.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>个人信息修改</title>
    <link href="../css/alterInfo.css" type="text/css" rel="stylesheet" charset="gb2312">
    <script type="text/javascript" src="../js/jquery.min.js" charset="gb2312"></script>
    <script type="text/javascript" src="../js/alterInfo.js" charset="gb2312"></script>
</head>
<body>
<div id="containner">
    <jsp:include page="head.jsp"></jsp:include>
    <div id="body">
        <div id="center">
            <div id="title">
                <p id="title_name">个人信息修改</p>
            </div>
            <div id="personinfodiv">
            	<p id="info_title">个人信息</p>
            </div>
            <div id="info_div">
         
                <div class="info">
                    <div class="info_name">姓名:</div>
                    <div class="info_value" name="student_name"></div>
                </div>
                <div class="info">
                    <div class="info_name">性别</div>
                    <div class="info_value" name="student_sex"></div>
                </div>
     
        		 <div class="info">
                    <div class="info_name">身份证号:</div>
                    <div class="info_value" name="student_identity"></div>
                </div>
            </div>
            <div id="personinfodiv">
            	<p id="info_title" >在校信息</p>
            </div>
            <div id="info_div">
            	<div class="info">
                    <div class="info_name">学号</div>
                    <div class="info_value" name="student_number"></div>
                </div>
                <div class="info">
                    <div class="info_name">二级学院</div>
                    <div class="info_value" name="student_college"></div>
                </div>
                <div class="info">
                    <div class="info_name">班级</div>
                    <div class="info_value" name="student_class"></div>
                </div>
                <div class="info">
                    <div class="info_name">学历</div>
                    <div class="info_value" name="student_edu"></div>
                </div>
            </div>
            <div id="personinfodiv">
            	<p id="info_title">联系方式</p>
            </div>
             <div id="phone_div">
           	  	<div id="phone_name">手机号</div>
                <input id="phone_value"  name="student_mobile">
              </div>
          
              <div id="button_div">
                  <input type="button" value="确定" class="button">
                  <input type="button" value="返回" class="button" onclick="backpage()">
           	</div>
        </div>
    </div>
    <jsp:include page="foot.jsp"></jsp:include>
</div>
</body>
</html>