var userName = "1000";
/*$.ajax({
	async:false,
	url:'../adminController',
	data:{operat:'AUTHUSER'},
	type:'POST',
	success:function(msg,status){
		if(msg == 'FAILL'){
			alert('您还没有登录或者没有相关权限,请登录后重试!');
			window.location = '../jsp/index.jsp';
		}else{
			userName = msg;
		}
	}
});*/
$(document).ready(function () {
	$('.greet').append(userName);
	$('.alterpane').click(function(e){showdialog(e)});
    $('.left-p').click(function(e){
    	$(this).next().siblings('.menulist').css('display','none');
    	$(this).next('.menulist').slideToggle(500);
    });
   
    $('.left-a,.left-a-last').click(function(e){
    	$(this).parent('.menulist').slideToggle(500);
    	var menu  = $(this).parent('.menulist').prev().html();
    	var list = $(this).html();
    	$('.site').html(menu + '&nbsp;/&nbsp' + list);
    });
    $('.exitpane').click(function(e){
    	$.ajax({
    		type:'POST',
    		data:{operat:'exit'},
    		url:'../frontservlet',
    		dataType:'text',
    		success:function(text,textStatus){
    			window.location = '../jsp/index.jsp';
    		}
    	});
    });
    $('#sys_help').click(function(e){
    	$('.right').empty();
    	var frame = '<iframe src="../html/help.html" width="100%" height="100%" style="border:none;"></iframe>';
    	$('.right').append(frame);
    });
});
