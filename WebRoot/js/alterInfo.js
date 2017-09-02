$(document).ready(function (){

	$.ajax({
		type:'POST',
		data:{operat:'GETINFO'},
		url:'../frontservlet',
		dataType:'json',
		success:function(text,status){
			if(text.state == "UnLogin"){
		 		alert("您还没有登陆，请先登陆!");
		 		window.location = "../jsp/index.jsp";
			}
			$('.info_value').each(function(index,ele){
				var name = $(this).attr('name');
				$(this).html(text.data[name]);
			});
			$('#phone_value').val(text.data['student_mobile']);
		}
	});
	$('.button').eq(0).click(function(e){
		var txt = "{";
		$('.info_value').each(function (index,ele){
			var name = $(this).attr('name');
			var v = $(this).html();
			txt += '"' + name + '":"' + v + '",';
		});
		txt += '"student_mobile' + '":"' + $('#phone_value').val() + '"}';
		$.ajax({
			type:'POST',
			data:{operat:'UPDATEINFO',data:txt},
			url:'../frontservlet',
			dataType:'text',
			success:function(text,status){
				if(text == 'SUCCESS'){
					alert('修改成功!')
				}
				else{
					alert("修改失败!");
				}	
					
			}
		});
	});
	$('.button').eq(1).click(function(e){
		window.location = '../jsp/index.jsp';
	});
});