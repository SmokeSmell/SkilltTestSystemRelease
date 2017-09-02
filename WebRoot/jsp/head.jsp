<%@ page language="java" import="java.util.*,com.gong.domain.*" pageEncoding="gb2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta name="renderer" content="webkit" />
	 <link href="../css/headCss.css" type="text/css" rel="stylesheet" charset="gb2312">
	 <link rel="stylesheet" href="../font-awesome/css/font-awesome.min.css">
	 <link rel="stylesheet" href="../dialog/css/dialog.css">
	 <link rel="stylesheet" href="../css/dropdownlistcss.css" charset="gb2312">
	 <script type="text/javascript" src="../js/jquery.min.js" charset="gb2312"></script>
	  <link href="../css/alterpw.css" type="text/css" rel="stylesheet" >
	 <script type="text/javascript" src="../dialog/js/dialog.js"></script> 
	 <script src="../dialog/js/zepto.min.js" charset="gb2312"></script>
	 <script type="text/javascript" src="../js/alterpw.js" charset="gb2312"></script>
  </head>
  <%
	   	StuInfo info = (StuInfo)session.getAttribute("user");
   %>
  <body id="head_body">
  	<div id="navdiv">
  		<%
  			if(info == null){
  		 %>
  		<p id="prehello">您好,欢迎使用本系统，请先登陆</p>
  		<%
  			}else{
  		 %>
  		<p id="hello">你好</p>
  		<p id="name"><%=info.getStudent_name() %></p>
  		<p id="stu_number" style="display:none"><%=info.getStudent_number() %></p>
  		<div class="nav setpane" id="setpane">
            <ul class="nav-main">
            <i class="fa fa-cog fa-lg" id="setimage"></i>
                <li id="li-1">设置<span></span></li>
            </ul>
            <div id="box-1" class="hidden-box hidden-loc-index">
                <ul class="nav-min">
                    <li id="changepw">修改密码</li>
                </ul>
            </div>

        </div>
        <script type="text/javascript" src="../js/list.js"></script>
  		
  		<div id="messagepane">
  			<img alt="" src="../image/clock.png" id="msgimag">
  			<p id="tip">提醒(0)</p>
  		</div>
  		<%
  			}
  		 %>
  	</div>
   <!-- <div id="head">   
            <div id="head_sep"></div>
    </div> --> 
    
   <iframe name="content_frame" id = "headFrame" marginwidth=0 marginheight=0 width=100% height=20% frameborder=0></iframe> 
 	<div class="fill"></div>
 	<script type="text/javascript">
    	if (!!window.ActiveXObject || "ActiveXObject" in window){
    		$('#headFrame').attr('src','../html/head2.html');
    	}else{
    		var rand = parseInt(Math.random() * 5);
    		var head = ''; 
    		if(rand == 0 || rand == 1)
    			head = 'head.html';
    		else if(rand == 2 || rand == 3)
    			head = 'head2.html';
    		else
    			head = 'head3.html';
    			
    		$('#headFrame').attr('src','../html/'+head);
    	}
    </script>
  </body>
</html>
