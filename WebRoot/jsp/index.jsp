<%@ page language="java" import="java.util.*,com.gong.domain.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="renderer" content="webkit" />
    <meta charset="UTF-8">
    <title>技能鉴定信息处理系统</title>
    <link href="../css/index.css" type="text/css" rel="stylesheet" charset="gb2312">
   	<link href="../css/infopane.css" type="text/css" rel="stylesheet" charset="gb2312">
   	<link href="../font/huawen.css" type="text/css" rel="stylesheet">
   	<link href="../plugins/Nivo-Slider-master/themes/default/default.css" rel="stylesheet" type="text/css" />
   	<link href="../plugins/Nivo-Slider-master/nivo-slider.css" rel="stylesheet" type="text/css" />
   	<link href="../plugins/Nivo-Slider-master/themes/bar/bar.css" rel="stylesheet" type="text/css" />  
   	<link href="../css/jquery-ui.min.css" rel="stylesheet"/> 
   	  
   	<link rel="stylesheet" type="text/css" href="../css/CSSreset.min.css" />
    <link rel="stylesheet" type="text/css" href="../css/default.css">
    <link rel="stylesheet" type="text/css" media="screen" href="../css/als_demo.css" />
    <link href="../plugins/bxslider/jquery.bxslider.min.css" rel="stylesheet" type="text/css" /> 
    <script type="text/javascript" src="../js/jquery.min.js" charset="gb2312"></script>	
    <script type="text/javascript" src="../js/skiltest.js" charset="gb2312"></script> 
    <script src="../js/jquery-ui.min.js"></script>
    <script src="../js/jssor.slider.min.js"></script>
</head>
<script type="text/javascript">
    if (window.applicationCache) {
       // alert("你的浏览器支持HTML5");
    } else {
        alert('系统检测到您的浏览器版本过低，为了不影响您的正确使用，请使用新版浏览器，'+
        		'如果您使用的为多核浏览器，请尝试切换为极速模式');
    }
</script>
<body>
    <div id="containner">
        <jsp:include page="head.jsp"></jsp:include>
        <div id="body">
            <div id="left">
            <%
            	StuInfo info = (StuInfo)session.getAttribute("user");
            	if(info == null){
            %>
            
            	<div id="login">
	                <div id="login_title">
	                   	 用户登录
	                </div>
	                <p id="logintip"></p>
	                <div id="namediv">
	                    <div class="name">
	                        <!-- <img src="../image/222.png" id="pep"> -->
	                        <i class="fa fa-user userico" style="font-size:1.5em"></i>
	                    </div>
	                    <input class="input" value="UserName" type="text" id="username" title="用户名为学号/工号" onfocus="foucshandler(this)" onblur="onblurhandler(this)">
	                </div>
	                <div id="passworddiv">
	                    <div class="name">
	                       <i class="fa fa-unlock-alt userico" style="font-size:1.5em"></i>
	                    </div>
	                    <input class="input" type="text" value="PassWord" id="password" title="初始密码为身份证后6位或6个0" onfocus="foucshandler(this)" onblur="onblurhandler(this)">
	                </div>
	                <div id="buttons">
	                    <!-- <input type="button" class="login_button" value="登陆" onclick="login()"> -->
	                    <div class="login_button" value="登陆" onclick="login()">
	                    	<i class="fa fa-paper-plane-o" id="loginico"><span id="login_tif">登录</span></i>
	                    	
	                    </div>
	                    <div  class="init_button"  onclick="init()"><span id="init_text">重置</span></div>
	                </div>
           		 </div>
           		 <%
            		}else{
           		 %>
                <div id="info_infodiv">
				    <div id="info_imagediv">
				        <img src="../image/loginback.jpg" id="info_image">
				    </div>
				    
				    <div id="info_center">
				        <div id="info_left">
				            <p class="info_value"><%=info.getStudent_name() %></p>
				            <p class="info_name">Name</p>
				        </div>
				        <div id="info_right">
				            <p class="info_value"><%=info.getStudent_number() %></p>
				            <p class="info_name">Number</p>
				        </div>
				        <div id="info_classname">
				            <p class="info_value"><%=info.getStudent_class() %></p>
				            <p class="info_name">Class</p>
				        </div>
				        <div id="info_collge">
				            <p class="info_value"><%=info.getStudent_college() %></p>
				            <p class="info_name">College</p>
				        </div>
				        <div id="info_birth">
				            <p class="info_value"><%=info.getStudent_birth() %></p>
				            <p class="info_name">Birth</p>
				        </div>
				        <div id="info_buttons">
				            <span class="fa fa-sign-out" id="info_exit" onclick="exit()">Exit</span>
				        </div>
				    </div>
				</div>
                <%
            		}
                %>
                <div id="downloadpane">
                <img alt="" src="../image/univers.png" id="univers">
                	<div id="downtitle">
                		<i class="fa fa-cloud-download fa-lg"></i>
                		资源下载
                	</div>
                	<div class="downcontent">
                		<i class="fa fa-download"></i>
                		<a href="ed2k://|file|cn_office_language_pack_2010_x86_x64_dvd_515903.iso|1271638016|61EAE729EFC3AC4A9A4EDE588564A7CE|/" class="download">MicroOffice2010下载</a>
                	</div>
                	<div class="downcontent">
                		<i class="fa fa-download"></i>
                		<a href="http://dlsw.baidu.com/sw-search-sp/soft/72/38811/vc6.0rj6.0.8168.2.1429168101.zip" class="download">Visual C++ 6.0软件下载</a>
                	</div>
                	<div class="downcontent">
                		<i class="fa fa-download"></i>
                		<a href="http://dl8.dykxgww.com/soft1/jdk6-x64.zip" class="download">JDK 1.6软件下载</a>
                	</div>
                	<div class="downcontent">
                		<i class="fa fa-download"></i>
                		<a href="ed2k://|file|cs_sql_2005_dev_all_dvd.iso|1870581760|25D3E5CEFB407E7CA1036BE303AC4643|/" class="download">SQL Server2005下载</a>
                	</div>
                	<div class="downcontent">
                		<i class="fa fa-download"></i>
                		<a href="http://tool.oschina.net/apidocs/apidoc?api=jdk-zh" class="download">JDK1.6中文文档</a>
                	</div>
                	<div class="downcontent">
                		<i class="fa fa-download"></i>
                		<a href="https://cdn.mysql.com//Downloads/MySQLInstaller/mysql-installer-community-5.6.35.0.msi" class="download">Mysql5.6下载</a>
                	</div>
                </div>
            </div>
       <style>
        
        .jssorb01 {
            position: absolute;
        }
        .jssorb01 div, .jssorb01 div:hover, .jssorb01 .av {
            position: absolute;
            /* size of bullet elment */
            width: 12px;
            height: 12px;
            filter: alpha(opacity=70);
            opacity: .7;
            overflow: hidden;
            cursor: pointer;
            border: #000 1px solid;
        }
        .jssorb01 div { background-color: gray; }
        .jssorb01 div:hover, .jssorb01 .av:hover { background-color: #d3d3d3; }
        .jssorb01 .av { background-color: #fff; }
        .jssorb01 .dn, .jssorb01 .dn:hover { background-color: #555555; }     
    </style>
            <div id="center">
            	<div class="roll-news">  
            	 <div id="slider1_container" style="position: relative;">
            	  <div data-u="loading" style="position:absolute;top:0px;left:0px;background-color:rgba(0,0,0,0.7);">
			            <div style="filter: alpha(opacity=70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
			            <div style="position:absolute;display:block;background:url('..image/loading.gif') no-repeat center center;top:0px;left:0px;width:100%;height:100%;"></div>
			        </div>
				    <!-- Slides Container -->
				    <div id="slider_imag" u="slides" style="cursor: move; position: absolute; overflow: hidden; left: 0px; top: 0px;">
				        <div><img data-u="image" src="../image/roll1.jpg"  style="width:100%;height:100%"/></div>
				        <div><img data-u="image" src="../image/roll2.jpg" /></div>
				        <div><img data-u="image" src="../image/roll3.jpg" /></div>
				    </div>
					<div data-u="navigator" class="jssorb01" style="bottom:16px;right:16px;">
			            <div data-u="prototype" style="width:12px;height:12px;"></div>
			        </div>
			       
				</div>
       		 </div>  
                <div id="info">  
	               	<div id="info_title">
	                   <div id="info_txt"><li class="fa fa-bullhorn list_fa"> 信息公布
	              
	                   </div>
	                   <a href="notify.jsp" target="_blank">more..</a>
	              	</div>  
                </div>
                <div class="friend_div">
                	<div class="link_titlediv">
                		<i class="fa fa-link fa-lg link_ico">友情链接</i>
                	</div>
                	<section id="content2">
        <div id="lista1" class="als-container">
          	<li><a class="als-item" href="http://rjys.njrts.edu.cn/" target="blanket"><img src="../image/ryxy.png" alt="calculator" title="软件与艺术设计学院主页" /></a></li>
          	<li><a class="als-item" href="http://www.njrts.edu.cn/" target="blanket"><img src="../image/njtd.png" alt="light bulb" title="南京铁道学院主页" href=""/></a></li>
          	<li><a class="als-item" href="http://sjcx.njrts.edu.cn/" target="blanket"><img src="../image/cxjx.png" alt="card" title="创新实践与教学中心" /></a></li>
          	<li><a class="als-item" href="http://www.csdn.net/" target="blanket"><img src="../image/cdsn.jpg" alt="chess" title="CSDN网" /></a></li>
          	<li><a class="als-item" href="http://www.njwww.net/" target="blanket"><img src="../image/jsjks.png" alt="chess" title="计算机等级考试网" /></a></li>   
        </div>
    </section>


<script type="text/javascript" src="../plugins/bxslider/jquery.bxslider.min.js"></script>
<script type="text/javascript">
	window.onload = function(){
		 $('#lista1').bxSlider({ 
				autoHover:true,
	            slideWidth: 200,
				minSlides: 3,
				maxSlides: 3,
				ticker: true,
				speed: 12000,
				startSlides: 0, 
	            slideMargin: 10,
	          });
		 $('.bx-wrapper').css('max-width','1000px');
	};
</script>
                </div>
            </div>
            <div id="right">
                <div id="right_content">
                    <div id="right_title"><i class="fa fa-rocket fa-1x" id="quickico"></i> 快捷应用</div>
                  	<div class="apps">
                     	<div class="apps_cont" id = "c5">
                       	 	<img src="../image/userinfo.png" class="app_image">
                        	<a href="alterInfo.jsp" class="app_name" target="_blank">个人信息查询与修改</a>
                        </div>
                    </div>
                    <div class="apps">
	                    <div class="apps_cont" id="c2">
	                    	<img src="../image/apply.jpg" class="app_image">
	                        <a class="app_name" href="stuinfo.jsp" target="_blank">计算机等级考试预报名</a>
	                    </div>
                    </div>
                    <div class="apps">
                    	 <div class="apps_cont" id="c4">
                        	<img src="../image/classinfo.png" class="app_image">
                       	 	<a href="classinfo.jsp" class="app_name"  target="_blank">预报名分班信息查询</a>
                       	 </div>
                    </div>
                    <div class="apps">
                    	 <div class="apps_cont" id="c3">
	                        <img src="../image/find.png" class="app_image">
	                        <a href="findScore.jsp" class="app_name" target="_blank">计算机考试/模拟成绩查询</a>
	                     </div>
                    </div>
                    
                    <!-- 
                    <div class="apps">
                     	 <div class="apps_cont" id="c5">
                        	<img src="../image/payinfo.png" class="app_image">
                        	<a href="#" class="app_name" target="_blank">计算机考试缴费信息查询</a>
                        </div>
                    </div>
                     --> 
                    <div id="conn_content">
                		<div class="conn_title"><i class="fa fa-qrcode fa-lg"></i>&nbsp;联系我们</div>
                		<div class="conn_left"><img alt="" src="../image/weixin2.png" class="conn_image"><p class="image_text">南铁院微信公众号</p></div>
                		<div class="conn_left"><img alt="" src="../image/code3.png" class="QQ_chat"><p class="chat_text">南铁院QQ公众号</p></div>
                	</div>
                	
                </div>
                
            </div>
        </div>
        <jsp:include page="foot.jsp"></jsp:include>
       
    </div>
</body>
</html>