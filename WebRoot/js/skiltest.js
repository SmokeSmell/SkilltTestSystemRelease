/**
 * 
 */

$(document).ready(function(){
	var height = window.screen.height - 50;
	$("#containner").css("height",height);
	var roll_width = $('.roll-news').width();
	var roll_height = $('.roll-news').height();
	$('#slider1_container').css('width',roll_width);
	$('#slider1_container').css('height',roll_height);
	$('#slider_imag').css('width',roll_width);
	$('#slider_imag').css('height',roll_height);
	
	var _SlideshowTransitions = [
         {$Duration:1200,x:0.2,y:-0.1,$Delay:20,$Cols:8,$Rows:4,$Clip:15,$During:{$Left:[0.3,0.7],$Top:[0.3,0.7]},$Formation:$JssorSlideshowFormations$.$FormationStraightStairs,$Easing:{$Left:$Jease$.$InWave,$Top:$Jease$.$InWave,$Clip:$Jease$.$OutQuad},$Assembly:260,$Outside:true,$Round:{$Left:1.3,$Top:2.5}},
         {$Duration:1000,$Delay:80,$Cols:8,$Rows:4,$Opacity:2},
         {$Duration:1200,x:0.2,y:-0.1,$Delay:20,$Cols:8,$Rows:4,$Clip:15,$During:{$Left:[0.3,0.7],$Top:[0.3,0.7]},$Formation:$JssorSlideshowFormations$.$FormationStraightStairs,$Easing:{$Left:$Jease$.$InWave,$Top:$Jease$.$InWave,$Clip:$Jease$.$OutQuad},$Assembly:260,$Round:{$Left:1.3,$Top:2.5}},
         {$Duration:1200,y:0.3,$Cols:2,$During:{$Top:[0.3,0.7]},$ChessMode:{$Column:12},$Easing:{$Top:$Jease$.$InCubic,$Opacity:$Jease$.$Linear},$Opacity:2},
         {$Duration:1200,x:0.2,y:-0.1,$Delay:20,$Cols:8,$Rows:4,$Clip:15,$During:{$Left:[0.3,0.7],$Top:[0.3,0.7]},$Formation:$JssorSlideshowFormations$.$FormationStraightStairs,$Easing:{$Left:$Jease$.$InWave,$Top:$Jease$.$InWave,$Clip:$Jease$.$OutQuad},$Assembly:260,$Outside:true,$Round:{$Left:1.3,$Top:2.5}},
         {$Duration:1200,x:0.2,y:-0.1,$Delay:80,$Cols:8,$Rows:4,$Clip:15,$During:{$Left:[0.3,0.7],$Top:[0.3,0.7]},$Easing:{$Left:$Jease$.$InWave,$Top:$Jease$.$InWave,$Clip:$Jease$.$OutQuad},$Outside:true,$Round:{$Left:1.3,$Top:2.5}},
         {$Duration:1200,x:0.2,y:-0.1,$Delay:20,$Cols:8,$Rows:4,$Clip:15,$During:{$Left:[0.3,0.7],$Top:[0.3,0.7]},$SlideOut:true,$Formation:$JssorSlideshowFormations$.$FormationSwirl,$Easing:{$Left:$Jease$.$InWave,$Top:$Jease$.$InWave,$Clip:$Jease$.$OutQuad},$Assembly:260,$Round:{$Left:1.3,$Top:2.5}},
         {$Duration:1500,x:0.2,y:-0.1,$Delay:150,$Cols:12,$SlideOut:true,$Formation:$JssorSlideshowFormations$.$FormationStraightStairs,$Easing:{$Left:$Jease$.$Linear,$Top:$Jease$.$OutWave,$Opacity:$Jease$.$Linear},$Assembly:260,$Opacity:2,$Round:{$Top:2}},
         {$Duration:4000,x:-1,y:0.7,$Delay:80,$Cols:12,$Clip:11,$Move:true,$During:{$Left:[0.35,0.65],$Top:[0.35,0.65],$Clip:[0,0.1]},$Formation:$JssorSlideshowFormations$.$FormationStraight,$Easing:{$Left:$Jease$.$OutQuad,$Top:$Jease$.$OutJump,$Clip:$Jease$.$OutQuad},$Assembly:2049,$ScaleClip:0.7,$Round:{$Top:4}},
         {$Duration:1500,x:-1,y:-0.5,$Delay:50,$Cols:8,$Rows:4,$Formation:$JssorSlideshowFormations$.$FormationStraight,$Easing:{$Left:$Jease$.$Swing,$Top:$Jease$.$InJump},$Assembly:513,$Round:{$Top:1.5}}
         ];
	 var options = { 
     		$AutoPlay: 1,
     		$SlideshowOptions: {
                $Class: $JssorSlideshowRunner$,
                $Transitions: _SlideshowTransitions,
                $TransitionsOrder: 0,
                $ShowLink: true
            },
            $ArrowNavigatorOptions: {
                $Class: $JssorArrowNavigator$
              },
              $BulletNavigatorOptions: {
                $Class: $JssorBulletNavigator$
              }
     	};
     var slider = new $JssorSlider$('slider1_container', options);
	
	$.ajax({
		type:'POST',
		url:'../frontservlet',
		data:{operat:'GETALLNOTIFY',limit:10},
		dataType:'json',
		success:function(json,status){
			var dat = json['data'];
			$(dat).each(function(index,ele){
				var title = ele['title'];
				var id = ele['notify_id'];
				var publisher = ele['publisher'];
				var time = ele['time'];
				createNotify(id,title,publisher,time);
			});
		}
	});
	$('#password').keyup(function(e){
		if(e.keyCode == 13)
			login();
	});
});

function tishi()
{ 
	window.location.href="stuinfo.jsp";
}

function backpage(){
	window.location.href="index.jsp";
}
function login(){
	var name = $("#username").val();
	var password = $("#password").val();
	if(name == "UserName"){
		$("#logintip").html("请输入用户名");
		$("#logintip").show();
		return;
	}else
	if(password == "PassWord" || password == ''){
		$("#logintip").html("请输入密码");
		$("#logintip").show();
		return;
	}else{
		$("#logintip").html("<i class=\"fa fa-spinner fa-spin fa-lg\"></i>&nbsp;&nbsp;正在登录...");
		$("#logintip").css("color","black");
		$("#logintip").show();
	}
	$.ajax({
		type:'POST',
		url:'../frontservlet',
		data:{operat:'login',username:name,password:password},
		success:function(msg,status){
	    	   if(msg == "LOGINFAILL"){
	    		   $("#logintip").html("用户名或密码错误");
	    		   $("#logintip").css("color","red");
	    		   $("#logintip").show();
	    	   }else if(msg == '学生'){
	    		   window.location.reload();
	    	   }else if(msg == '管理员'){
	    		   window.location.href = '../jsp/admin.jsp';
	    	   }
		}
	});
}
function apply(){
	var name = $("#stu_name").html();
	if(name ==  null || name == ""){
		alert("您还没有登录，请先登录!");
	}else{
		window.location.href="stuinfo.jsp";
	}
}
function exit(){
	var xhr = new XMLHttpRequest;
	xhr.open("POST", "../frontservlet");
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send("operat=exit");
	xhr.onreadystatechange = function(){
       if (xhr.readyState == 4 && xhr.status == 200) {
    	   window.location.reload();
        }
	};
}
function init(){
	$("#username").val("UserName");
	$("#password").attr("type","text");
	$("#password").val("PassWord");
	$("#logintip").html("");
}
function foucshandler(ele){
	var value = $(ele).val();
	if(value == "UserName"){
		$(ele).val("");	
	}else if(value == "PassWord"){
		$(ele).val("");	
		$(ele).attr("type","password");
	}
	$("#logintip").html("");
}
function onblurhandler(ele){
	var id = $(ele).attr("id");
	var value = $(ele).val();
	if(id == "username" && value == ""){
		$(ele).val("UserName");	
	}else if(id == "password" && value == ""){
		$(ele).val("PassWord");	
		$(ele).attr("type","text");
	}
}
function createNotify(id,title,pub,time){
	var href = '../jsp/detail.jsp?operat=GETNOTIFYBYID&notify_id='+id;
	var htm = '<div class="infolist"><i class="fa fa-angle-double-right fa-lg list_point"></i>'+
			  '<a class="list_title" href="'+href+'" target="_black">'+title+'</a><div class="up_time"'+
			  '>'+time+'</div><div class="up_people">'+pub+'</div></div>';
	$('#info').append(htm);
}