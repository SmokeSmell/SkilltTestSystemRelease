var payoption = '<select class="payselect"><option class="0">未缴费</option><option class="1">已缴费</option></select>';
var clsoption = "";
var selected = -1;
var preTotal = 0;
var selected = 0;
var recreate = true;
var curpage = 0;
var name="";
var flag = false;
var va="";
$(document).ready(function(){
	$('#pay_log').click(function(e){
		
		$('.right').empty();
		$(".site_center").empty();
		var htm = '<div class = "paylog_div"><table class="table" id="tab">'+
					'<thead><tr id="table_head"></tr></thead><tbody></tbody></table></div>';
		$('.right').html(htm);
		$('.site_right').empty();
		findPay_Log();
	});
	$('#pre_apply').click(function(e){
		flag = false;
		recreate = true;
		name = "";
		va = "";
		$.dialog({
		       type : 'info',
		       infoText : '正在加载…',
		       infoIcon : '../dialog/images/icon/loading.gif',      
		});
		$('.right').empty();
		$(".site_center").empty();
		clsoption = "";
		getAllClass();
		
		createButtonHtml();
		var height = $('.right').height();
		s = (height - 70) / 45; 
		shownum = parseInt(s);
		getPreApplyData(true,1,shownum);
	});
});
function findPay_Log(){
	$.ajax({
		url:'../adminController',
		data:{operat:'GETPAYLOG'},
		dataType:'JSON',
		success:function(json,status){
			var title = json['title'];
			var colName = json['colName'];
			var data = json['data'];
			
			$('#table_head').empty();
			$('tbody').empty();
			$(title).each(function(index,ele){
				$('#table_head').append('<td>'+ele+'</td>');
			});
			$(data).each(function(index,ele){
				var tr = '<tr>';
				$(colName).each(function(i,e){
					if(i == 1)
						tr += '<td><div class="log">' + ele[e] + '</div></td>';
					else if(i == 2 && ele[e] != '无'){
						tr += '<td><a href="../payed/'+ele[e]+'">'+ele[e]+'</a></td>';
					}else
						tr += '<td class="log">' + ele[e] + '</td>';
				});
				tr += '</tr>';
				$('tbody').append(tr);
			});
		}
	});
}
function getAllClass(){
	$.ajax({
		url:'../adminController',
		data:{operat:'GETALLCLASS'},
		dataType:'json',
		success:function(json,status){
			clsoption += "<select>";
			$(json).each(function(index,ele){
				clsoption += '<option class="'+ele['class_id']+'">'+ele['class_name']+"</option>";
			});
			clsoption +="</select>";
		}
	});
}
function PreApply(json){
	createPreHtml();
	createPreApplyTable(json);
	total = json['records'];
	pagenum = total % shownum == 0 ? total / shownum : total / shownum + 1; 
	$('.M-box2').pagination({
	    coping:true,
	    pageCount:pagenum,
	    homePage:'首页',
	    endPage:'末页',
	    prevContent:'上页',
	    nextContent:'下页',
	    jump:true,
	    jumpBtn:'跳转',
	    callback:function(index){
	    	curpage = index.getCurrent();
	    	if(curpage <= pagenum){
	    		$('#table_head').empty();
		    	$('tbody').empty();
	    		getPreApplyData(false,curpage,shownum);
	    		
	    	}
	    }
	}); 
	if(shownum>total) $('.shownum').html("当前显示 1 - "+total+"条记录,共"+total+"条记录");
	else $('.shownum').html("当前显示 1 - "+shownum+"条记录,共"+total+"条记录");
}
function upload(){
	var v = $('#upload_file').val();
	if(v == ''){
		alert('请选择文件!');
		return;
	}
	var op = $('.batchHandler').find('option:checked').attr('name');
	$.dialog({
	       type : 'info',
	       infoText : '正在执行操作…',
	       infoIcon : '../dialog/images/icon/loading.gif',      
	});
	var fd = new FormData();
    fd.append("upload_file", document.getElementById("upload_file").files[0]);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "../adminController?operat="+op);
    xhr.send(fd);
    xhr.onreadystatechange = function(){
	    if (xhr.readyState == 4 && xhr.status == 200) {
	    	$.dialog.close();
	    	var resp = xhr.responseText;
	    	if(resp.indexOf("数据读取失败", 0) > 0 || resp.indexOf("批量预报名失败", 0) > 0
	    			|| resp.indexOf("成功预报名0人", 0) > 0)
	    	 zeroModal.error(resp);
	    	else
	    		zeroModal.success(resp);
	    	$('.right').empty();
	    	if(clsoption == ""){
				getAllClass();
			}
			flag = false;
			createButtonHtml();
			var height = $('.right').height();
			s = (height - 70) / 45; 
			shownum = parseInt(s);
			getPreApplyData(true,1,shownum);
			
	    }
    };
}
function createButtonHtml(){
	var addbutton = '<div class="buttonsdiv save"><div class="preapply_savediv"><i class="fa fa-save fa-fw"></i>&nbsp;保存</div></div>'+
	'<div class="buttonsdiv del"><div class="deldiv"><i class="fa fa-minus fa-fw"></i>&nbsp;删除</div></div>'+
	'<div class="buttonsdiv add" ><div class="adddiv" ><i class="fa fa-plus fa-fw"></i>&nbsp;添加</div></div>'+
	'<div class="buttonsdiv import batchApply" onClick="importview()"><div class="importdiv"><i class="fa  fa-file-excel-o fa-fw"></i>&nbsp;批量报名/缴费</div></div>'+
	'<div class="importContextdiv"><select class="BatchHandler"><option name="HANDBATCHAPPLY">批量预报名</option><option name="IMPORTPAYED">批量缴费</option></select>'+
	'<input type="file" id="upload_file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"></input>'+
	'<input type="button" id="pre_upButton" value="上传"></input></div>';
	
	$('.site_right').empty();
	$('.site_right').html(addbutton);
	$('.importContextdiv').hide();
	$('.adddiv').click(function(e){
		preAddRow();
	});
	$('.deldiv').click(function(e){
		preDelRow();
	});
	$('.preapply_savediv').click(function(e){
		preApplySava();
	});
	$('#pre_upButton').click(function(e){
		upload();
	});
}
function preApplySava(){
	var data = collectData();
	if(data == false){
		alert("请填写完整数据!");
		return;
	}
	else{
		$.ajax({
			url:'../adminController',
			type:'POST',
			data:{operat:'PREAPPLYMANAGER',data:data},
			dataType:'JSON',
			success:function(txt,status){
				var addn = txt['add'];
				var deln = txt['del'];
				var update = txt['update'];
				var add_fail = txt['add-fail'];
				var repeat = txt['repeat'];
				var add_total = txt['add-total'];
				var err = txt['err'];
				var cont = '<p class="info-text" class="rs_tip">成功添加'+addn+'条记录(共'+add_total+'条),修改'+update+'条记录,删除'+deln+'条记录</p>';
				
				if(add_fail > 0 || repeat > 0){
					cont += '<p>失败项:</p>';
					if(add_fail > 0){
						cont += '<p>以下添加项学号与姓名不对应,请确认数据无误性:</p>'+err;
					}
					if(repeat > 0){
						cont += '<p>当前添加的预报中有'+repeat+'个已经预报名,不需要重复报名';
					}
				}
				
				 $.dialog({
				    // autoClose : 1500,
					 buttonText : {
				            ok : '确定',
				            cancel : '自定义-取消'
				     },
				     onClickOk : function(){
				    	 $('.del').attr("style","background-color:grey");
				    	 
				    	 getPreApplyData(true,0,shownum);
				     },
				     contentHtml : cont
				 });
				
			}
		});
	}
}
function createPreHtml(){
	$('.right').html(' ');
	var tbhtml = '<div class = "preApplyed_div"><table class="table" id="tab">	<thead><tr id="table_head"></tr></thead>'+
				'<tbody></tbody></table></div>';
	$('.right').html(tbhtml);
	
	var html = '<div id="right_buttom"><div class="M-box2"></div><div class="refreshdiv">'+
	'<i class="fa fa-refresh fa-lg refico"></i><div class="refresh">刷新</div>'+
	'</div><span class="shownum"></span><div class="delelteBox"><input type="checkbox" class="pre_deletAll">全选(删除)</div></div>';
	$('.right').append(html);
	$('.pre_deletAll').click(function(e){
    	var check = $(this).is(':checked');
    	if(check){
    		$('.remove').prop('checked',true);
    	}else{
    		$('.remove').prop('checked',false);
    	}
    });
	$('.refreshdiv').click(function(e){
		name = "";
		va = "";
		//recreate = true;
		$('#table_head').empty();
    	$('tbody').empty();
    	$('#search_input').val('');
		getPreApplyData(true,0,shownum);
	});
}
function collectData(){
	var hsnull = false;
	var selAdd = '{"add":[';
	$('.add:checked').each(function(index,ele){
		var td = $(this).parent().prevAll();
		var stu_id = $(td).eq(4).find('input').val();
		var stu_name = $(td).eq(3).find('input').val();
		var cls_id = $(td).eq(2).find('select option:checked').attr('class');
		var ispay = $(td).eq(1).find('select option:checked').attr('class');
		if(stu_id != "" && stu_name != "" && cls_id != "" && ispay != ""){
			var add = "{";
			add += '"stu_number":' + '"'+stu_id+'",';
			add += '"stu_name":' + '"'+stu_name+'",';
			add += '"class_id":' + '"'+cls_id+'",';
			add += '"ispay":' + '"'+ispay+'"}';
			selAdd += add + ',';
		}else{
			selAdd = "";
			hsnull = true;
			return;
		}
	});
	if(hsnull) return false;
	if(selAdd.length > 8)
	selAdd = selAdd.substring(0, selAdd.length - 1);
	selAdd += ']';
	if(selAdd.length < 5)
		selAdd = '{"add":[]';
	var del = '"del":[';
	$('.remove:checked').each(function(index,ele){
		var td = $(this).parent().prevAll();
		var stu_id = $(td).eq(5).html();
		del += '"'+stu_id+'",';
	});
	if(del.length > 7)
	del = del.substring(0, del.length - 1);
	del += ']';
	var update = '"update":[';
	$('.change:checked').each(function(index,ele){
		var td = $(this).parent().prevAll();
		var stu_id = $(td).eq(6).html();
		var stu_name = $(td).eq(5).html();
		var cls_id = $(td).eq(4).find('select option:checked').attr('class');
		var ispay = $(td).eq(3).find('select option:checked').attr('class');
		if(stu_id != "" && stu_name != "" && cls_id != "" && ispay != ""){
			var add = "{";
			add += '"stu_number":' + '"'+stu_id+'",';
			add += '"stu_name":' + '"'+stu_name+'",';
			add += '"class_id":' + '"'+cls_id+'",';
			add += '"ispay":' + '"'+ispay+'"}';
			update += add + ',';
		}
	});
	if(update.length > 10)
	update = update.substring(0, update.length - 1);
	update += ']}';
	var json = selAdd + ',' + del + ',' + update;
	return json;
	
}
function createPreApplyTable(json){	
	titles = json['titles'];
	var colNames = json['colNames'];
	var data = json['data'];
	preTotal = data.length;
	$('#table_head').empty();
	$('tbody').empty();

	if(flag==false){
		var txt='<div class="search_div"><div class="search_frame"><select class="search_select">';
		var tp =new Array("stu_number","student_name","class_name","pay","apply_time");
		for(var i=0;i<titles.length-3;i++){
			if(i==0) name=tp[i];
			txt+='<option value='+tp[i]+'>'+colNames[i]+'</option>';
		}
		txt+='</select><input id="search_input" placeholder="键入关键字查找" type="text"><li class="search_button fa fa-search fa-fw" title="点击搜索'
			+'"></li></div></div>';
		$(".site_right").append(txt);
		$('.search_button').tooltip({
			  classes: {
				"ui-tooltip": "highlight"
			  }
		});
		$('.search_button').click(function(e){
			tem();
		});
		$('#search_input').keyup(function(e){
			tem();
		});
		flag = true;
	}
	$(colNames).each(function(index,ele){
		$('#table_head').append('<td>'+ele+'</td>');
	});
	$(data).each(function(index,ele){
		var tr = '<tr>';
		$(titles).each(function(i,e){
			if(i < titles.length - 3)
				tr += '<td>' + ele[e] + '</td>';
			else{
				tr += '<td><input class="'+e+'" type="checkbox"></td>';
			}
		});
		tr += '</tr>';
		$('tbody').append(tr);
	});
	trClick();
	$('.add').attr("disabled","disabled");
	$('.remove').each(function(index,ele){
		$(this).click(function(e){
			var v = $(this).prop('checked');
			if(v){
				$('.change').eq(index).prop('checked',false);
				var plc = $(this).parent().prevAll().eq(3);
				var pay = $(this).parent().prevAll().eq(2);
				var tmp = $(plc).find('select').val();
				$(plc).html(tmp);
				var tmp2 = $(pay).find('select').val();
				$(pay).html(tmp2);
			}else{
				$('.pre_deletAll').prop('checked',false);
			}
		});
	});
	$('.change').each(function(index,ele){
		$(this).click(function(e){
			var v = $(this).prop('checked');
			var plc = $(this).parent().prevAll().eq(4);
			var pay = $(this).parent().prevAll().eq(3);
			if(v){
				$('.remove').eq(index).prop('checked',false);
				var tmp = $(plc).html();
				$(plc).html(clsoption);
				$(plc).find('select').val(tmp);
				
				var tmp2 = $(pay).html();
				$(pay).html(payoption);
				$(pay).find('select').val(tmp2);
			}else{
				var tmp = $(plc).find('select').val();
				$(plc).html(tmp);
				var tmp2 = $(pay).find('select').val();
				$(pay).html(tmp2);
			}
		});
	});
}
function trClick(){
	$('tr').each(function(index,ele){
		$(this).click(function(e){
			if(index != 0)
			$(this).addClass('trselected');
			$(this).siblings().removeClass('trselected');
			selected = index;
		});
	});
}
function getPreApplyData(iscreate,page,num){
	$.ajax({
		url:'../adminController',
		data:{operat:'GETSElE',page:page,num:shownum,name:name,value:va},
		type:'POST',
		dataType:'JSON',
		success:function(json,status){
			size = json['data'].length;
			if(iscreate && size >= 0){
				PreApply(json);
				recreate = false;
			}	
			else if(size > 0){
				createPreApplyTable(json);
				begin = (curpage-1) * shownum+1;
	    		end = begin + size - 1;
	    		$('.shownum').html('当前显示 '+begin+'-'+end+'条记录,共'+total+'条记录');
			}
			$.dialog.close();
		}
	});
}

function preAddRow(){

	$('.deldiv').attr("style","cursor:pointer");
	$('.del').attr("style","background-color:#48A748");
	
	var tr = '<tr>';
	$(titles).each(function(i,e){
		if(i < titles.length - 3){
			if(i == titles.length - 4){
				var mydate = new Date();
				var date = mydate.toLocaleDateString();
				tr += '<td>'+date+'</td>';
			}else if(i == titles.length - 5){
				tr += '<td>'+payoption+'</td>';
			}else if(i == titles.length - 6){
				tr += '<td>'+clsoption+'</td>';
			}else{
				tr += '<td><input type="text"></td>';
			}
		}
		else if(i == titles.length - 3){
			tr += '<td><input class="'+e+'" type="checkbox" disabled="disabled" checked = "true"></td>';
		}else{
			tr += '<td><input type="checkbox" disabled="disabled" ></td>';
		}
	});
	tr += '</tr>';
	//alert(tr);
	$('tbody').append(tr);
	$('.add').attr("disabled","disabled");
	trClick();
}
function preDelRow(){
	trClick();
	if(selected > preTotal)
		$('tr').eq(selected).remove();
	var l = $('tr').length;
	if( l - 1 == preTotal){
		$('.deldiv').attr("style","cursor:default");
		$('.del').attr("style","background-color:grey");
	}
	
}
function tem(){
	curpage = 1;
	va = $("#search_input").val();
	//alert(va);
	name = $('.search_select').val();
	$.ajax({
		type:'POST',
		data:{operat:'GETSElE',page:1,num:shownum,name:name,value:va},
		url:'../adminController',
		dataType:'json',
		success:function(json,textStatus){
			$('.M-box2').empty();
			console.log(json);
			PreApply(json);
		}
	});
}
