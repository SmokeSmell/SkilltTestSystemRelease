$(document).ready(function(){
	
	$('#new_preapply').click(function(e){
		$('.site_right').empty();
		$(".site_center").empty();
		$.dialog({
		       type : 'info',
		       infoText : '加载中…',
		       infoIcon : '../dialog/images/icon/loading.gif',      
		});
		$.ajax({
			url:'../adminController',
			type:'POST',
			data:{operat:'GETPRETASK'},
			dataType:'json',
			success:function(txt){
				$.dialog.close();
				if(txt == null){
					var htm = '<div class="preConfig_tip_div"><p>当前没有正在进行的预报名,点击下方按钮新建预报名</p>'+
        			'<button class="new_preApply_button"><li class="fa fa-plus fw">&nbsp;新建预报名'+
					'</button></div>';
					$('.right').html(htm);
					$('.new_preApply_button').button();
					$('.new_preApply_button').click(function(e){
						$.dialog({
						       type : 'info',
						       infoText : '加载中…',
						       infoIcon : '../dialog/images/icon/loading.gif',      
						});
						createRightHtml();
					});
				}else{
					createRightHtml();
					$('.pre_title_div > input').val(txt['title']);
					$('.pre_title_div > input').attr('name',txt['id']);
					$('.date').eq(0).val(txt['start_time']);
					$('.date').eq(1).val(txt['end_time']);
					$('.pre_title_div > input').attr('disabled',true);
					$('.date').attr('disabled',true);
					$('.applyConfig_submit').text('修改');
				}
			}
		});
		
	});
});
function clickEvent(){
	$('.select_place').click(function(e){
		if(!$(this).prop('checked')){
			$('.applyConfig_selectAll_div > input').prop({checked:false});
		}
	});
	$('.applyConfig_selectAll_div > input').click(function(e){
		if($(this).prop('checked')){
			$('input[type=checkbox]').prop({checked:true});
		}
	});
	$('.applyConfig_submit').click(function(e){
		var name = $(this).text();
		if(name == '修改'){
			$('.pre_title_div > input').attr('disabled',false);
			$('.date').attr('disabled',false);
			$('.applyConfig_submit').text('提交');
		}else{
			buttonClick();
		}
	});
}
function buttonClick(){
	var title = $('.pre_title_div > input').val();
	var id = $('.pre_title_div > input').attr('name');
	if(title == ""){
		$('.applyConfig_tip').eq(0).css('display','inline');
		$('.applyConfig_tip').eq(0).text('*请输入标题!');
		return;
	}
	var check = true;
	$('.pre_time_div').find('input').each(function(index,ele){
		if($(this).val() == ""){
			$('.applyConfig_tip').eq(1).css('display','inline');
			$('.applyConfig_tip').eq(1).text('*请填写完整时间!');
			check = false;
			return;
		}
	});
	$('.pre_time_div').find('input').click(function(e){
		$('.applyConfig_tip').eq(1).css('display','none');
	});
	if(!check) return;
	infodialog = $.dialog({
	       type : 'info',
	       infoText : '提交中…',
	       infoIcon : '../dialog/images/icon/loading.gif',      
	});
	var txt = '{"title":"'+title+'",';
	$('.pre_time_div').find('input').each(function(index,ele){
		var v = $(this).val();
		if(index == 0){
			txt += '"pre_start":"'+v+'",';
		}else if(index == 1){
			txt += '"pre_end":"'+v+'",';
		}else if(index == 2){
			txt += '"str_start":"'+v+'",';
		}else{
			txt += '"str_end":"'+v+'"';
		};
	});
	txt += '}';
	$.ajax({
		url:'../adminController',
		data:{operat:'PREAPPLYTASK',data:txt,id:id},
		type:'POST',
		success:function(text,status){
			var msg = "";
			var img = "";
			if(text == 'SUCCESS'){
				msg = "提交成功";
				img = '../dialog/images/icon/success.png';
				$('.pre_title_div > input').attr('disabled',true);
				$('.date').attr('disabled',true);
				$('.applyConfig_submit').text('修改');
			}else{
				msg = "提交失败";
				img = '../dialog/images/icon/fail.png';
			}
			infodialog.dialog.update({
		        autoClose : 1500,
		        infoText : msg,
		        infoIcon : img
			});
		}
	});
}
function createApplyConfigTable(json){
	var title = json['titles'];
	var colName = json['names'];
	var data = json['datas'];
	$(title).each(function(index,ele){
		if(index < title.length - 3 && index != 2 && index != 3){
			$('#table_head').append('<td>'+ele+'</td>');
		}
	});
	$(data).each(function(i,ele){
		var tr = "<tr>";
		$(colName).each(function(j,ele2){
			if(j < colName.length - 3 && j != 2 && j != 3){
				tr += '<td>'+ele[ele2]+'</td>';
			}else if(j == 2){
				$('.date').eq(2).val(ele[ele2]);
			}else if(j == 3){
				$('.date').eq(3).val(ele[ele2]);
			}
		});
		$('tbody').append(tr);
	});
}
function createRightHtml(){
	var htm = '<div class="pre_title_div"><p>标题:</p><input type="text" placeholder="点击此处输入标题">'+
		'<p class="applyConfig_tip">*tip</p></div><div class="time_config_title">'+
		'<li class="fa fa-calendar fw">&nbsp;时间设置</li><p class="applyConfig_tip">*tip</p>'+
		'</div><div class="pre_time_div"><div class="start_time"><p>预报名开始时间:</p>'+
		'<input class="date" type="text" placeholder="点击选择时间"></div><div class="end_time">'+
		'<p>预报名结束时间:</p><input class="date" type="text" placeholder="点击选择时间">'+
		'</div></div><div class="pre_time_div"><div class="start_time"><p>强化班开始时间:</p>'+
		'<input class="date" type="text" placeholder="点击选择时间"></div><div class="end_time">'+
		'<p>强化班结束时间:</p><input class="date" type="text" placeholder="点击选择时间"></div>'+
		'</div><div class="place_config_title"><li class="fa fa-map-marker fw">&nbsp;强化班信息</li>'+
		'<p class="applyConfig_tip">*tip</p></div><div class="place_div"><table class="table">'+
		'<thead><tr id="table_head"></tr></thead><tbody></tbody></table></div>'+
		'<div class="applyConfig_button"><button class="applyConfig_submit">提交</button></div>';
	$('.right').html(htm);
	$('.date').datepicker({	
		inline: true,
	});
	$('.applyConfig_submit').button();	
	$.ajax({
			url:'../adminController',
			type:'POST',
			dataType:'json',
			data:{operat:'GETDATA',source:'strclass',page:1,num:100},
			success:function(json,status){
				createApplyConfigTable(json);
				clickEvent();
				$.dialog.close();
			}
		});
}