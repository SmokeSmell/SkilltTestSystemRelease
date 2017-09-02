$(document).ready(function(){
	$.dialog({
	       type : 'info',
	       infoText : '���ڼ��ء�',
	       infoIcon : '../dialog/images/icon/loading.gif',      
	});
	$.ajax({
		type:'POST',
		data:{operat:'FINDSCORE'},
		url:'../frontservlet',
		dataType:'json',
		success:function(text,textStatus){
			var t = text;
			if(text.state == "UnLogin"){
		 		alert("����û�е�½��û�����Ȩ�ޣ����½������!");
		 		window.location = "../jsp/index.jsp";
		 	}else if(text.state == "No Find"){
		 		alert("δ��ѯ�����Ŀ��Գɼ�!");
		 		window.close();
		 	}else{
		 		$('#title_name').html(text['head']);
		 		var key = text['key'];
		 		var title = text['title'];
		 		var info = text['info'];
		 		var data = text['data'];
		 		$(key).each(function(index,ele){
		 			var html = '<div class="info"><div class="info_name">'+title[index]+
		 			'</div><div class="info_value">';
		 			if(index < 3){
		 				html += info[ele];
		 			}else{
		 				html += data[ele];
		 			}
		 			html += '</div>';
		 			$('#info_div').append(html);
		 		});
		 		$.dialog.close();
		 	}
		}
});
});
