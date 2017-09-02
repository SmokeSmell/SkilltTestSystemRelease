/**
 * 
 */
var ph;
var flag = false;
 $(document).ready(function (){
	 $.dialog({
	       type : 'info',
	       infoText : '正在加载…',
	       infoIcon : '../dialog/images/icon/loading.gif',      
	});
	 $.ajax({
		type:'POST',
		data:{operat:'GETSTUINFO'},
		url:'../frontservlet',
		dataType:'json',
		success:function(text,textStatus){
			//alert(text.infos['student_name']);
			$.dialog.close();
			onSuccess(text);
		}
	});
 	$("#phone_button").click(function(){
 		var title = $("#phone_button").val();
 		var val = $('#phone_value').html();
 		if(title == "修改"){
 			$("#phone_value").html("<input id='inp' type='text' value="+val+" >");
 			$("#phone_button").val("保存");
 		}else{
 			var txt = $("#inp").val();
 			$("#phone_value").html($("#inp").val());
 			$("#phone_button").val("修改");
 			if(txt!=""){
	 			$.ajax({
	 				type:'POST',
	 				data:{operat:'UPDATEMOBILE',data:txt},
	 				url:'../frontservlet',
	 				dataType:'text',
	 				success:function(text,status){
	 					if(text == 'SUCCESS'){
	 						alert('修改成功!');
	 						flag = true;
	 					}
	 					else if(text == 'FAIL'){
	 						alert("修改失败!");
	 					}else{
	 						alert('用户信息过期，请重新登录!');
	 						window.location = '../jsp/index.jsp';
	 					}	
	 						
	 				}
	 			});
 			}
 		}
 		
 	});
 });
 function preapply(){
	 if(flag==false){
			alert("联系方式未填写完整！");
	}else{	
		 tipdialog = $.dialog({
		       type : 'info',
		       infoText : '正在执行操作…',
		       infoIcon : '../dialog/images/icon/loading.gif',      
		 });	
		 $.ajax({
				type:'POST',
				url:'../frontservlet',
				data:{operat:'preapply'},
				dataType:'text',
				success:function(text,textStatus){
					if(text == 'SUCCESS'){
						tipdialog.dialog.update({
					        autoClose : 2000,
					        infoText : "预报名成功",
					        infoIcon : '../dialog/images/icon/success.png',
					        
						});
						window.setTimeout(function() {
							window.location.reload(true);
					    }, 2000);
					}else if(text == 'NOTAPPLYTIME'){
						alert('当前不是预报名时间,尽请留意相关通知!');
						window.close();
					}
					else if(text == 'FAILED'){
						tipdialog.dialog.update({
					        autoClose : 1500,
					        infoText : "预报名失败",
					        infoIcon : '../dialog/images/icon/fail.png'
						});
					}else if(text == 'UNLOGIN'){
						alert('登录信息过期，请重新登录');
						window.location.href = '../jsp/index.jsp';
					}
					else{
						tipdialog.dialog.update({
					        autoClose : 1500,
					        infoText : "报名人数已满",
					        infoIcon : '../dialog/images/icon/fail.png'
						});
					}
				}
			});
		}
	}
 function onSuccess(text){
 	if(text.state == "UnLogin"){
 		alert("您还没有登陆，请先登陆!");
 		window.location = "../jsp/index.jsp";
 	}else if(text.state == "APPLYED"){
 		window.location = "../jsp/classinfo.jsp";
 	} else{
 		$('.info_value').eq(0).html(text.infos['student_name']);
 		$('.info_value').eq(1).html(text.infos['student_sex']);
 		$('.info_value').eq(2).html(text.examType);
 		$('.info_value').eq(3).html(text.infos['student_identity']);
 		$('.info_value').eq(4).html(text.infos['student_number']);
 		$('.info_value').eq(5).html(text.infos['student_college']);
 		$('.info_value').eq(6).html(text.infos['student_class']);
 		$('.info_value').eq(7).html(text.infos['student_edu']);
 		$('#phone_value').html(text.infos['student_mobile']);
 		ph = text.infos['student_mobile'];
 		if(ph!="") flag=true;
 	}
 	
 }
 
 