$(document).ready(function(){
	
	var id = GetQueryString('notify_id');
	$.ajax({
		type:'POST',
		data:{operat:'GETNOTIFYBYID',notify_id:id},
		dataType:'json',
		url:'../frontservlet',
		success:function(json,status){
			var txt = json['content'];
			var title = json['title'];
			var time = json['time'];
			var attach = json['attach'];
			var publisher = json['publisher'];
			
			$('#title').html(title);
			$('#cnt').html(txt);
			$('.pub_time').append(time);
			$('.publisher').append(publisher);
			$('.extr_div > a').html(attach);
			if(attach != ""){
				$('.extr_div > a').attr('href','../frontservlet?operat=GETFILE&notify_id='+id);
			}else{
				$('.extr_div').hide();
			}
			
		}
	});
});
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}