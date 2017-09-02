<%@ page language="java"  pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>技能鉴定信息处理系统</title>
    <link href="../css/stuinfoCss.css" type="text/css" rel="stylesheet" charset="gb2312">
    <script type="text/javascript" src="../js/jquery.min.js" charset="gb2312"></script>
    <script type="text/javascript" src="../js/stuinfo.js" charset="gb2312"></script>
</head>
<body>
<div id="containner">
    <jsp:include page="head.jsp"></jsp:include>
    <div id="body">
        <div id="center">
            <div id="title">
                <p id="title_name">计算机二级强化班预报名——信息确认</p>
            </div>
            <div id="personinfodiv">
            	<p id="info_title">个人信息</p>
            </div>
            <div id="info_div">
         
                <div class="info">
                    <div class="info_name">姓名:</div>
                    <div class="info_value"></div>
                </div>
                <div class="info">
                    <div class="info_name">性别</div>
                    <div class="info_value"></div>
                </div>
                <div class="info">
                    <div class="info_name">考试类型:</div>
                    <div class="info_value"></div>
                </div>
        		 <div class="info">
                    <div class="info_name">身份证号:</div>
                    <div class="info_value"></div>
                </div>
            </div>
            <div id="personinfodiv">
            	<p id="info_title">在校信息</p>
            </div>
            <div id="info_div">
            	<div class="info">
                    <div class="info_name">学号</div>
                    <div class="info_value"></div>
                </div>
                <div class="info">
                    <div class="info_name">二级学院</div>
                    <div class="info_value"></div>
                </div>
                <div class="info">
                    <div class="info_name">班级</div>
                    <div class="info_value"></div>
                </div>
                <div class="info">
                    <div class="info_name">学历</div>
                    <div class="info_value"></div>
                </div>
            </div>
            <div id="personinfodiv">
            	<p id="info_title">联系方式</p>
            </div>
             <div id="phone_div">
           	  	<div id="phone_name">手机号</div>
                <div id="phone_value"></div>
                <input id="phone_button" type="button" value="修改">
              </div>
            <div id="tipdiv">
               	<p class="tip">*提交前请务必确认信息无误，然后再进行预报名</p>
               	<p class="tip">*以上信息如有错误，请尽快到教务处进行修改</p>
            </div>
                <div id="button_div">
                    <input type="button" value="确定" class="button" onclick="preapply()">
                    <input type="button" value="返回" class="button" onclick="backpage()">
            	</div>
        </div>
    </div>
    <jsp:include page="foot.jsp"></jsp:include>
</div>
</body>
</html>