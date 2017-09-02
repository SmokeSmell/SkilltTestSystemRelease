$(document).ready(function(){
	create(true);
});
function create(is,page){
	$.ajax({
		type:'POST',
		url:'../frontservlet',
		data:{operat:'GETALLNOTIFY',limit:15,page:page},
		dataType:'json',
		success:function(json,status){
			var dat = json['data'];
			$('.notify_div').remove();
			$(dat).each(function(index,ele){
				var title = ele['title'];
				var id = ele['notify_id'];
				var publisher = ele['publisher'];
				var time = ele['time'];
				
				createNotify(id,title,publisher,time);
				
			});
			if(is){
				var num = json['num'];
				n = num / 15;
				n = num % 15 == 0 ? n : n + 1;
				createSkip(n);
			}
			
		}
	});
	
}
function createSkip(n){
	$('.page_button').append('<div class="M-box2"></div>');
	$('.M-box2').pagination({
	    coping:true,
	    pageCount:n,
	    homePage:'Ê×Ò³',
	    endPage:'Ä©Ò³',
	    prevContent:'ÉÏÒ³',
	    nextContent:'ÏÂÒ³',
	    jump:true,
	    jumpBtn:'Ìø×ª',
	    callback:function(index){
	    	curpage = index.getCurrent();
	    	create(false,curpage);
	    }
	}); 
}
function createNotify(id,title,publisher,time){
	var url = '../jsp/detail.jsp?operat=GETNOTIFYBYID&notify_id='+id;
	var html = '<div class="notify_div"><a href="'+url+'" class="notify_name" target="_black">'+title+'</a>'+
			   '<p class="depart">'+publisher+'</p><p class="time">'+time+'</p></div>';
	$('#body').append(html);
}