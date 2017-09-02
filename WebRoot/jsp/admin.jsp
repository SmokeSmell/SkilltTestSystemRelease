<%@ page language="java" import="java.util.*,com.gong.domain.*" pageEncoding="utf-8"%>
<html>

<head>
	<meta name="renderer" content="webkit" />
    <title>技能鉴定信息处理系统-Admin</title>
    <link href="../css/jquery-ui.min.css" rel="stylesheet"/>
    <link href="../css/jquery-ui.theme.min.css" rel="stylesheet"/>
    <link href="../css/alterpw.css" type="text/css" rel="stylesheet" >
    <link href="../css/old.css" type="text/css" rel="stylesheet" charset="gb2312">
    <link rel="stylesheet" href="../font-awesome/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="../dialog/css/dialog.css" charset="utf-8">
	<link href="../table/pagination.css" rel="stylesheet"/> 
  	<link href="../table/normalize.css" rel="stylesheet"/> 
  	<link href="../css/admin.css" rel="stylesheet" charset="utf-8"/> 
  	<link href="../table/common.css" rel="stylesheet"/>
  	<link rel="stylesheet" href="../plugins/trumbowyg/design/css/trumbowyg.css">
  	<link href="../css/notify.css" rel="stylesheet"/> 
  	<link href="../css/configApply.css" rel="stylesheet"/>
  	<link href="../css/preApply.css" rel="stylesheet"/>
  	<link href="../css/statis.css" rel="stylesheet"/>
  	<link rel="stylesheet" type="text/css" href="../plugins/zeroModal/zeroModal.css" />
  	<link href="../css/procedure.css" rel="stylesheet" charset="gb2312"/>
  	
  	<script type="text/javascript" src="../js/jquery.min.js" charset="gb2312"></script>
  	<script src="../plugins/zeroModal/zeroModal.min.js" charset="utf-8"></script>
  	<script src="../js/jquery-ui.min.js"></script>
	<script src="../table/jquery.pagination.min.js"></script> 
	<script type="text/javascript" src="../dialog/js/dialog.js" charset="utf-8"></script> 
	<script src="../dialog/js/zepto.min.js" charset="gb2312"></script>
	<script src="../js/admin.js" charset="gb2312"></script>
	<script src="../js/table.js" charset="gb2312"></script>
	<script type="text/javascript" src="../js/alterpw.js" charset="gb2312"></script>
	<script type="text/javascript" src="../js/notify.js" charset="gb2312"></script>
	<script src="../plugins/trumbowyg/trumbowyg.js"></script>
	
	<script src="../js/applyConfig.js" charset="gb2312"></script>
	<script src="../js/preApply.js" charset="gb2312"></script>
	<script src="../plugins/echart/dist/echarts.js" charset="utf-8"></script>
	<script src="../js/statis.js" charset="gb2312"></script>
	<script src="../js/proced.js" charset="gb2312"></script>
	<script src="../js/help.js" charset="gb2312"></script>
	<style type="text/css">
		#statis_frame {
			width: 100%;
			height: 100%;
			border: 0px;
		}
	</style>
</head>
<body>
<div class="box">
    <div class="header">
        <div class="header-content-one">
            <div class="header-content-one-front"><p class="systitle">技能鉴定后台处理系统</p>
            </div>
            <div class="func">
            	<div class="greetdiv">
            		<p class="greet">Hi ~ admin:</p>
 
            	</div>  
                <div class="alterpane">
                	<i class="fa fa-unlock-alt fa-fw pwico"></i>
                	修改密码
                </div>
                <div class="exitpane">
                	<i class="fa fa-sign-out fa-fw eixtico"></i>
                	退出
                </div>
            </div>
        </div>

        <div class="header-content-two">
            <div class="header-content-two-front">
            	<p class="copyright">版权所有©南京铁道职业技术学院 ALL RIGHTS RESERVED</p>
            </div>
            <div style="height: 100%;width: 86%;float: left" id="right_top_right">
                <h3 class="site">任务管理 /
                    	任务信息管理
                </h3>
            	<div class="site_right">
            		
            	</div>
            </div>
        </div>
    </div>
    <div class="content">
        <div class="left">
        	<p class="left-p" id="home-top" ><i class="fa fa-home fa-fw"></i>&nbsp;首页</p>
	            <div id="" class="menulist">
	                <a class="left-a" id="statis_menu">统计</a>
	                <a class="left-a" id="sys_prec">系统流程</a>
	                <a class="left-a" id="sys_help">帮助</a>
	            </div>
            <p class="left-p" id="home-top" ><i class="fa fa-cogs fa-fw"></i>&nbsp;预报名管理</p>
            <div id="" class="menulist">
                <a class="left-a" id="new_preapply">预报名配置</a>
                <a class="left-a" id="pre_apply">预报名</a>
                <a class="left-a-last" id="pay_log">缴费日志</a>
            </div>

           <p class="left-p" id="edit-top" ><i class="fa fa-edit fa-fw"></i>&nbsp数据编辑</p>
            <div id="edit" class="menulist">
                <a class="left-a" onClick="editTable('users','edit')">用户信息编辑</a>
                <a class="left-a" onClick="editTable('stuinfo','edit')">学生信息编辑</a>
                <a class="left-a" onClick="editTable('teachers','edit')">教师信息编辑</a>
                <a class="left-a" onClick="editTable('strclass','edit')">强化班信息编辑</a>
                <a class="left-a" onClick="editTable('mockscore','edit')">模拟成绩信息编辑</a>
                <a class="left-a-last" onClick="editTable('score','edit')">考证成绩信息编辑</a>
            </div>
            <p class="left-p" id="search-top" ><i class="fa fa-search fa-fw" onclick=""></i> &nbsp;数据查询</p>
            <div id="search" class="menulist">
                <a class="left-a" onclick="editTable('notpay','look')">需缴费学生名册</a>
                <a class="left-a" onclick="editTable('arrangeclass','look')">强化班安排表</a>
                <a class="left-a-last" onclick="editTable('apply','look')">报名表</a>
            </div>

            <p class="left-p" ><i class="fa fa-bullhorn fa-fw"></i> &nbsp;通知管理</p>
            <div id="search" class="menulist">
                <a class="left-a" id="publishNotify">通知发布</a>
                <a class="left-a" id="find_Notify">通知管理</a>
            </div>
        </div>
        <div class="right">
			
    	</div>
</div>
</body>

</html>
