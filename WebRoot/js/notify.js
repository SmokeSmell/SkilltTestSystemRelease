
/**
 * 通知管理js文件
 */
$(document).ready(function(){
	$('#publishNotify').click(function(e){
		publishNotify(false);
	});
	$('#find_Notify').click(function(e){
		createFindNotifyHtml();
		findNotify(true);
	});
});
function submitNotify(alterNotify,id){
	var ht = $('.editor').html();
	var file = document.getElementById("upload_file").files[0];
	var fileName = '';
	if(file != undefined){
		fileName = file.name;
	}
	
	var da = "{";
	da += '"title":' + '"'+ $('.title_div > input').val() + '",';
	/*da += '"content":'+ '\'' + ht + '\',';*/
	da += '"attach":' + '"' + fileName + '",';
	da += '"publisher":' + '"' + $('.publisher > input[type=text]').val() + '"}';
	var re=/\n/g;  
	ht=ht.replace(re,"");
	var op = alterNotify == true ? 'ALTERNOTIFY' : 'PUBLISHNOTIFY';
	//alert(ht);
	$.ajax({
		url:'../adminController',
		type:'POST',
		data:{operat:op,data:da,notify_id:id,content:ht},
		success:function(text,status){
			if(text == 'SUCCESS'){
				tipdialog.dialog.update({
			        autoClose : 1500,
			        infoText : "发布成功",
			        infoIcon : '../dialog/images/icon/success.png'
				});
				createFindNotifyHtml();
				findNotify(true);
			}
		}
	});
}
function createNotifyHtml(){
	var htm = '<div class="title_div"><p>标题:</p><input type="text" placeholder="点击此处添加标题">'+
			   '</div><div class="content_div"><p>通知内容</p><div id="customized-buttonpane" class="editor"></div>'+
			   '</div><div class="upload_div"><p>上传附件:</p><input type="file" id="upload_file">'+
			   '<p id="del_file">删除</p><p class="upload_tip">若要上传多个文件请使用压缩包上传</p>'+
			   '</div><div class="publisher"><p>发布人(部门):</p><input type="text" placeholder="输入部门信息">'+
			   '<input type="button" value="发布" class="upload_button"></div>';
	$('.right').html(htm);
	$('#customized-buttonpane').trumbowyg({	
    });
	$('#customized-buttonpane').css('height','');
}
function createFindNotifyHtml(){
	$('.right').html(' ');
	$('.site_right').html(' ');
	var head = '<div class="notify_head"><p>通知名称</p><p name="depart_title">发布人(部门)</p>'+
				'<p name="pub_time_title">发布时间</p><p name="op_title">操作</p></div>';
	$('.right').append(head);
}
function findNotify(is,page){
	$.dialog({
	       type : 'info',
	       infoText : '正在加载…',
	       infoIcon : '../dialog/images/icon/loading.gif',      
	});
	$.ajax({
		type:'POST',
		url:'../frontservlet',
		data:{operat:'GETALLNOTIFY',limit:'15',page:page},
		dataType:'json',
		success:function(text,status){
			creatNotify(text);
			if(is){
				var num = text['num'];
				n = num / 15;
				n = num % 15 == 0 ? n : n + 1;
				createSkip(n);
			}
			$.dialog.close();
		}
	});
}
function creatTag(cls,val,name){
	var tit = $('<p></p>');
	tit.attr('class',cls);
	tit.attr('name',name);
	tit.text(val);
	return tit;
}
function creatNotify(json){
	$('.find_notify_div').remove();
	$(json['data']).each(function(index,ele){
		var title = ele['title'];
		var notify_id = ele['notify_id'];
		var publisher = ele['publisher'];
		var time = ele['time'];
		var div = $('<div></div>');
		div.attr('class','find_notify_div');
		var tit = creatTag('notify_name',title,notify_id);
		var depart = creatTag('depart',publisher);
		var tim = creatTag('time',time,'a');
		var change = creatTag('update_notify','修改');
		var del = creatTag('del_notify','删除');
		div.append(tit);
		div.append(depart);
		div.append(tim);
		div.append(change);
		div.append(del);
		$('.right').append(div);
		$(tit).click(function (e){
			var id = $(this).attr('name');
			window.open('../jsp/detail.jsp?operat=GETNOTIFYBYID&notify_id='+id);
		});
	});
	$('.del_notify').click(function(e){
		var id = $(this).prevAll('.notify_name').attr('name');
		$.ajax({
			type:'POST',
			url:'../adminController',
			data:{operat:'DELNOTIFY',notify_id:id},
			dataType:'json',
			success:function(text,status){
				//createFindNotifyHtml();
				findNotify(false,curpage);
			}
		});
	});
	$('.update_notify').click(function(e){
		var id = $(this).prevAll().eq(2).attr('name');
		publishNotify(true,id);
		$.ajax({
			type:'POST',
			url:'../frontservlet',
			data:{operat:'GETNOTIFYBYID',notify_id:id},
			dataType:'json',
			success:function(json,status){
				var txt = json['content'];
				var title = json['title'];
				var publisher = json['publisher'];
				
				$('.title_div > input').val(title);
				$('.editor').html(txt);
				$('.publisher > input[type=text]').val(publisher);			
			}
		});
	});
}
function createSkip(n,cur){
	$('.right').append('<div class="M-box"></div>');
	$('.M-box').pagination({
	    coping:true,
	    pageCount:n,
	    homePage:'首页',
	    endPage:'末页',
	    prevContent:'上页',
	    nextContent:'下页',
	    jump:true,
	    jumpBtn:'跳转',
	    callback:function(index){
	    	curpage = index.getCurrent();
	    	findNotify(false,curpage);
	    }
	}); 
}
function publishNotify(alterNotify,id){
	$('.right').html(' ');
	$('.site_right').html(' ');
	createNotifyHtml();
	$('.upload_button').click(function(e){
		tipdialog = $.dialog({
	 	       type : 'info',
	 	       infoText : '正在执行操作…',
	 	       infoIcon : '../dialog/images/icon/loading.gif',      
	    });
		if($('#upload_file').val().length > 0){
			var fd = new FormData();
		    fd.append("upload_file", document.getElementById("upload_file").files[0]);
		    var xhr = new XMLHttpRequest();
		    xhr.open("POST", "../adminController?operat=UPLOAD");
		    xhr.send(fd);
		    xhr.onreadystatechange = function(){
		        if (xhr.readyState == 4 && xhr.status == 200) {
		        	if(xhr.responseText == 'SUCCESS'){
		        		submitNotify(alterNotify,id);
		        	}
		        }
		    };
		}else{
			submitNotify(alterNotify,id);
		}
		
	});
	$('#del_file').click(function(e){
		var obj = document.getElementById("upload_file");
		obj.outerHTML=obj.outerHTML; 
	});
}