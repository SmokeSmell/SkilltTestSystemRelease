/**
 * 
 */
var curpage = 1;
var total = 0;
var allpage = 0;
var begin = 0;
var end = 0;
var shownum = 1;
var message = '加载成功';
var proposals = [];
var rowIndex = 0;
var cols = 0;
var rows = 0;
var names =null;
var titles = null;
var ms = null;
var allpage = 0;
var op = null;
var fSave = false;
//var alldatas = null;
var falg = true;
var fa = true;
var roles = ["管理员","学生"];
var name,va;
var selflag = false;

function createTableHtml(){
	var html = '<div class="right-content"><table class="table" id="tab" onClick="cli(event)"><thead>'+
		'<tr id="table_head">  </tr></thead><tbody></tbody> </table></div>'+
		'<div id="right_buttom"><div class="M-box2"></div><div class="refreshdiv">'+
		'<i class="fa fa-refresh fa-lg refico"></i><div class="refresh">刷新</div>'+
		'</div><span class="shownum"></span><div class="delelteBox"><input type="checkbox" class="deletAll">全选(删除)</div></div>';
	var addbutton = "";
	if(op=="edit"){
		addbutton = '<div class="buttonsdiv save" onClick="send()"><div class="savediv"><i class="fa fa-save fa-fw"></i>&nbsp;保存</div></div>'+
			'<div class="buttonsdiv del" onClick="removeRow()"><div class="deldiv"><i class="fa fa-minus fa-fw"></i>&nbsp;删除</div></div>'+
			'<div class="buttonsdiv add" onClick="addRow()"><div class="adddiv" ><i class="fa fa-plus fa-fw"></i>&nbsp;添加</div></div>'+
			'<div class="buttonsdiv import" onClick="importview()"><div class="importdiv"><i class="fa  fa-file-excel-o fa-fw"></i>&nbsp;导入</div></div>'+
			'<div class="importContextdiv"><input type="file" id="file" onClick="upButtonDisabled()" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"></input>'+
			'<input type="button" id="upButton" value="上传" onClick="upFile()" disabled="true"></input></div>';
	}else{
		addbutton = '<div class="buttonsdiv export" onClick="epFile()"><div class="exportdiv"><i class="fa fa-upload fa-fw"></i>&nbsp;导出</div></div>';
		
	}
	$('.right').html(html);
	$('.site_right').empty();
	$('.site_right').html(addbutton);
	$('.importContextdiv').hide();
	
    $('.refreshdiv').click(function(e){
    	message = '刷新成功';
    	refreshdialog = $.dialog({
 	       type : 'info',
 	       infoText : '正在刷新…',
 	       infoIcon : '../dialog/images/icon/loading.gif',      
    	});
    	refreshTable();
    });
    $('.deletAll').click(function(e){
    	var check = $(this).is(':checked');
    	if(check){
    		deletBox('delete');
    	}else{
    		$('[name=delete]:checkbox').prop('checked',false);
    	}
    });
}
function deletBox(name){
	$('[name='+name+']:checkbox').prop('checked',true);
}
function editTable(msg,oper){
	ms = msg;
	op = oper;
	selflag = false;
	createTableHtml();
	
	if(ms =="users" || ms=="preapply"){
		$('.import').hide();
	}else{
		$('.import').show();
	}
	var height = $('.right').height();
	s = (height - 70) / 45; 
	shownum = parseInt(s);
	 $.ajax({
 		type:'POST',
 		data:{operat:'getlist',listname:'user_name'},
 		url:'../adminController',
 		dataType:'json',
 		success:function(text,textStatus){
 			for(var i=0;i<text.length;i++){
 				proposals.push(text[i]);
 			}
 		}
 	});
	 infodialog = $.dialog({
	       type : 'info',
	       infoText : '表格加载中…',
	       infoIcon : '../dialog/images/icon/loading.gif',      
	});
	 $.ajax({
			type:'POST',
			data:{operat:'GETDATA',page:1,num:shownum,source:ms},
			url:'../adminController',
			dataType:'json',
			success:function(json,textStatus){
				total = json.total;
				if(shownum>total) shownum = total;
				allpage = json.allpage;
				if(op=="edit"){
					createTable(json);
				}else{
					lookTable(json);
				}
				$('.M-box2').pagination({
				    coping:true,
				    pageCount:allpage,
				    homePage:'首页',
				    endPage:'末页',
				    prevContent:'上页',
				    nextContent:'下页',
				    jump:true,
				    jumpBtn:'跳转',
				    callback:function(index){
				    	judegeupd();
				    	if(fSave==true){
					    	if(confirm("您确定要保存吗？")){
					    		send();
					    		fSave = false;
					    		rowIndex = 0;
					    		$('.deldiv').attr("style","cursor:default");
								$('.del').attr("style","background-color:grey");
					    	}else{
					    		fSave = false;
					    	}
				    	}
				    	curpage = index.getCurrent();
				    	begin = (curpage-1) * shownum+1;
				    	end = (curpage)*shownum;
				    	$.dialog({
				 	       type : 'info',
				 	       infoText : '表格加载中…',
				 	       infoIcon : '../dialog/images/icon/loading.gif',      
				 	});
				    	getData(curpage,shownum,ms);
				    }
				}); 
				$('.shownum').html("当前显示 1 - "+shownum+"条记录,共"+total+"条记录");
			}
		});
}
function refreshTable(){
	editTable(ms,op);
}
function showTip(){
	 
	if(curpage == allpage){
		$('.shownum').html("当前显示 "+begin+" - "+total+"条记录,共"+total+"条记录");
	}else
		$('.shownum').html("当前显示 "+begin+" - "+end+"条记录,共"+total+"条记录");
}
function getData(page,shownum,msg){
	var val = $("#search_input").val();
	$.ajax({
		type:'POST',
		data:{operat:'GETDATA',page:page,num:shownum,source:msg,name:name,value:val},
		url:'../frontservlet',
		dataType:'json',
		success:function(json,textStatus){
			console.log(json);
			if(json.datas.length > 0){
				total = json.total;
		    	begin = (curpage-1) * shownum+1;
		    	end = (curpage)*shownum;
				allpage = json.allpage;
				if(op=="edit"){
					createTable(json);
				}else{
					lookTable(json);
				}
			}
			showTip();
		}
	});
}

function createTable(jsontext){
	
	$('#table_head').empty();
	$('tbody').empty();
	json = jsontext;
	titles = json.titles;
	names = json.names;
	var datas = json.datas;
	alldatas = json.alldatas;
	rows = json.datas.length;
	cols = json.names.length;
	if(selflag==false){
		var temps = new Array("stu_id","stu_name","exam_number","auth_occupation","auth_class","exam_degree","practice_score","exam_status","evaluate_score");
		var txt='<div class="search_div"><div class="search_frame"><select onchange="getSelect(this.value);">';
		for(var i=0;i<titles.length-3;i++){
			if(ms=="score"){
				if(i==0) name=temps[i];
				txt+='<option value='+temps[i]+'>'+titles[i]+'</option>';
			}else{
				if(i==0) name=names[i];
				txt+='<option value='+names[i]+'>'+titles[i]+'</option>';
			}
		}
		txt+='</select><input id="search_input" placeholder="键入关键字查找" type="text"><li class="search_button fa fa-search fa-fw" title="点击搜索"></li></div></div>';
		$(".site_right").append(txt);
		$('.search_button').tooltip({
			  classes: {
				"ui-tooltip": "highlight"
			  }
		});
		$('.search_button').click(function(e){
			temp();
		});
		$('#search_input').keyup(function(e){
			temp();
		});
		selflag = true;
	}
	for(var i=0;i<titles.length;i++){
		
		$('#table_head').append('<th>'+titles[i]+'</th>');
	}
	for(var i=0;i<datas.length;i++){
		var data = datas[i];
		var html = "<tr>";
		for(var j=0;j<names.length;j++){
			var key = names[j];
			var value ="";
			if(ms=="mockscore" && key=="stu_name" || ms=="score" && key=="stu_name" ){
				value = json.stunames[i];
			}else{
				value = data[key];
			}
			if(j<names.length-3){
				if(key=="role"){
					html += '<td noWrap width="154px"><select disabled="true" class='+key+' name='+key+'>';
					for(var a =0;a<roles.length;a++){
						if (value == roles[a]){
							html+='<option selected="true" value='+roles[a]+'>'+roles[a]+'</option>';
						}else{
							html+='<option value='+roles[a]+'>'+roles[a]+'</option>';
						}
					}
					html+='</select></td>';
				}else{
					if(ms=="strclass" && key=="tea_id"){
						var teachers = json.teachers;
						html += '<td noWrap width="154px"><select disabled="true" class='+key+' name='+key+'>';
						for(var a =0;a<teachers.length;a++){
							if (value == teachers[a]["teacher_id"]){
								html+='<option selected="true" value='+teachers[a]["teacher_name"]+'>'+teachers[a]["teacher_name"]+'</option>';
							}else{
								html+='<option value='+teachers[a]["teacher_name"]+'>'+teachers[a]["teacher_name"]+'</option>';
							}
						}
						html+='</select></td>';
					}else{
						
						html += '<td noWrap  width="154px"><input disabled="true" class="text '+key+'" value="'+value+'" name='+key+'></td>';
					}
				}
			}else{
				if(j==names.length-3){
					html += '<td valign="middle" class="checktd"><input type="checkbox" class="check" disabled="true" name='+key+'></input></td>';
				}else{
					
					if(key == 'delete')
						html += '<td valign="middle" class="checktd"><input type="checkbox" class="check delete" onClick="chooseOne(this,event),upd(this,event)" name='+key+'></input></td>';
					else
						html += '<td valign="middle" class="checktd"><input type="checkbox" class="check" onClick="chooseOne(this,event),upd(this,event)" name='+key+'></input></td>';
				}
			}
		}
		html += "</tr>";
		$('tbody').append(html);
		$('.delete').click(function(e){
			var check = $(this).is(':checked');
			if(!check){
				$('.deletAll').prop('checked',false);
			}
		});
		/*$('.delete').each(function(index,ele){
			//$(this).c
			var check = $(this).is(':checked');
			if(check){
				alert(check+','+index);
			}
			
		});*/
	}
	
	$.dialog.close();
	$('.refreshdiv').show();
	
}
function temp(){
	va = $("#search_input").val();
	$.ajax({
		type:'POST',
		data:{operat:'GETDATA',page:1,num:shownum,source:ms,name:name,value:va},
		url:'../frontservlet',
		dataType:'json',
		success:function(json,textStatus){
			$('.M-box2').empty();
			total = json.total;
	    	begin = (curpage-1) * shownum+1;
	    	end = (curpage)*shownum;
			allpage = json.allpage;
			if(op=="edit"){
				createTable(json);
			}else{
				lookTable(json);
			}
			$('.M-box2').pagination({
			    coping:true,
			    pageCount:allpage,
			    homePage:'首页',
			    endPage:'末页',
			    prevContent:'上页',
			    nextContent:'下页',
			    jump:true,
			    jumpBtn:'跳转',
			    callback:function(index){
			    	judegeupd();
			    	if(fSave==true){
				    	if(confirm("您确定要保存吗？")){
				    		send();
				    		rowIndex = 0;
				    		$('.deldiv').attr("style","cursor:default");
							$('.del').attr("style","background-color:grey");
				    		fSave = false;
				    	}else{
				    		
				    	}
			    	}
			    	curpage = index.getCurrent();
			    	begin = (curpage-1) * shownum+1;
			    	end = (curpage)*shownum;
			    	getData(curpage,shownum,ms);
			    }
			}); 
			if(shownum>total) $('.shownum').html("当前显示 1 - "+total+"条记录,共"+total+"条记录");
			else $('.shownum').html("当前显示 1 - "+shownum+"条记录,共"+total+"条记录");
		}
	});
}
function lookTable(jsontext){
	$('.delelteBox').hide();
	$('#table_head').empty();
	$('tbody').empty();
	json = jsontext;
	var titles = json.titles;
	names = json.names;
	var datas = json.datas;
	rows = json.datas.length;
	cols = json.names.length;
	if(selflag==false){
		var txt='<div class="search_div"><div class="search_frame"><select onchange="getSelect(this.value);">';
		for(var i=0;i<titles.length;i++){
			if(i==0) name=names[i];
			txt+='<option value='+names[i]+'>'+titles[i]+'</option>';
		}
		txt+='</select><input id="search_input" placeholder="键入关键字查找" type="text"><li class="search_button fa fa-search fa-fw" title="点击搜索"></li></div></div>';
		$(".site_right").append(txt);
		$('.search_button').tooltip({
			  classes: {
				"ui-tooltip": "highlight"
			  }
		});
		$('.search_button').click(function(e){
			temp();
		});
		$('#search_input').keyup(function(e){
			temp();
		});
		selflag = true;
	}
	for(var i=0;i<titles.length;i++){
		
		$('#table_head').append('<th>'+titles[i]+'</th>');
	}
	for(var i=0;i<datas.length;i++){
		
		var data = datas[i];
		var html = "<tr>";
		for(var j=0;j<names.length;j++){
			var key = names[j];
			var value = data[key];
			html += '<td noWrap>'+value+'</td>';
		}
		
		html += "</tr>";
		$('tbody').append(html);
	}
	
	$.dialog.close();
	$('.refreshdiv').show();
}
function cli(e) {
	var e = e || window.event; 
	var target = e.target || e.srcElement;
	
	if(target.tagName.toLowerCase() === "input") {
		rowIndex = target.parentNode.parentNode.rowIndex + 1;
	}
	if(target.tagName.toLowerCase() === "td") {
		rowIndex = target.parentNode.rowIndex + 1;
	}
}
function chooseOne(chk,event){
	cli(event);
	for (var i=cols-3; i<cols; i++){
		if (tab.rows[rowIndex-1].cells[i].childNodes.item(0).checked==true && tab.rows[rowIndex-1].cells[i].childNodes.item(0)==chk){
			for (var j=cols-3;j<cols;j++){
				if (i!=j) {
					tab.rows[rowIndex - 1].cells[j].childNodes.item(0).checked = false;
				}
			}
		}
	}
}
function upd(chk,event){
	chooseOne(chk,event);
	
	var obj = chk.getAttribute("name");

	if (obj=="update" && chk.checked==true){
		for(var j = 1;j<cols-3;j++){
			if(ms=="strclass" && j==7 || ms=="mockscore" && j==1 || ms=="score" && j==1){
				
			}else{
				var input =tab.rows[rowIndex-1].cells[j];
				input.childNodes.item(0).disabled=false;
				input.childNodes.item(0).setAttribute("style","border: 1px solid #d8cccc;border-radius: 3px;box-shadow: -1px -1px 0px #8f7f7f;height: 100%;");
			}
		}
	}else{
		for(var j = 1;j<cols-3;j++){
			var input =tab.rows[rowIndex-1].cells[j];
			input.childNodes.item(0).disabled=true;
			input.childNodes.item(0).setAttribute("style","border:none");
		}
	}
}

function addRow(){
	
	$('.deldiv').attr("style","cursor:pointer");
	$('.del').attr("style","background-color:#F6470E");
	
	var ta = document.getElementById("tab");
	var html = "<tr>";
	for (var j =0;j<cols;j++) {
		if(j<cols-3){
			if(names[j]=="role"){
				html += '<td noWrap><select class='+names[j]+' name='+names[j]+'>';
				for(var a =0;a<roles.length;a++){
					
					html+='<option value='+roles[a]+'>'+roles[a]+'</option>';
					
				}
				html+='</select></td>';
			}else{
				if(ms=="strclass" && names[j]=="tea_id"){
					var teachers = json.teachers;
					html += '<td noWrap width="154px"><select class='+names[j]+' name='+names[j]+'>';
					for(var a =0;a<teachers.length;a++){
						
						html+='<option value='+teachers[a]["teacher_name"]+'>'+teachers[a]["teacher_name"]+'</option>';
						
					}
					html+='</select></td>';
				}else{
					
					if(ms=="strclass" && j==0 || ms=="strclass" && j==7 ||  ms=="score" && j==1){
						html += '<td noWrap><input class="text" name='+names[j]+' id='+names[j]+' style="border: 1px solid #d8cccc;border-radius: 3px;box-shadow: -1px -1px 0px #8f7f7f;height: 100%;" disabled="true" value="不需要填写"></td>';
					}else{
						html += '<td noWrap><input class="text" name='+names[j]+' id='+names[j]+' style="border: 1px solid #d8cccc;border-radius: 3px;box-shadow: -1px -1px 0px #8f7f7f;height: 100%;"></td>';
					}
				}
			}
		}else{
			if(j==names.length-3){
				html += '<td valign="middle" class="checktd"><input type="checkbox" class="check" disabled="true" checked="true" name='+names[j]+' style="border:1px solid lightgray"></input></td>';
			}else{
				html += '<td valign="middle" class="checktd"><input type="checkbox" class="check" disabled="true" style="border:1px solid lightgray"></input></td>';
			}
		}
	}
	html += "</tr>";
	$('tbody').append(html);

	rowIndex = ta.rows.length;
}
function removeRow(){
	var table = document.getElementById("tab");
	var len = table.rows.length-1;
	if (len > rows&&rowIndex>rows) {
		table.deleteRow(rowIndex-1);
		rowIndex = 0;
		if (table.rows.length-1 == rows){
			$('.deldiv').attr("style","cursor:default");
			$('.del').attr("style","background-color:grey");
			
		}
	}
	
}
function checkdata(){
	
	flag = fa = true;
	for (var i = 0; i<adds.length; i++){
		var colmns = cols-3;
		if(ms=="stuinfo") colmns = 3;
		for (var col=0; col < colmns; col++) {

			var value = tab.rows[adds[i]].cells[col].childNodes.item(0).value;
			if(ms=="stuinfo" && col==3){
				if(value.length>18){
					flag = false;
					alert("身份证信息填写不正确！");
					break;
				}
				
			}
			if (value==""){
				flag=false;
				alert("第"+adds[i]+"行 "+(titles[col])+"列数据填写不完整");
				break;
			}
		}
		if(flag==false || fa==false) break;
	}
	
	if(flag == true && fa == true){
		for (var i = 0; i<updates.length; i++){
			var colmns = cols-3;
			if(ms=="stuinfo") colmns = 3;
			for (var col=0; col < colmns; col++) {
	
				var value = tab.rows[updates[i]].cells[col].childNodes.item(0).value;
				if(ms=="stuinfo" && col==3){
					if(value.length>18){
						flag = false;
						alert("身份证信息填写不正确！");
					}
					
				}
				if (value==""){
					flag=false;
					alert("第"+updates[i]+"行第"+(titles[col])+"列数据填写不完整");
					break;
				}
			}
			
			if(flag==false) break;
		}
	}
	if (flag==true && fa==true){
		fuse();
	}
}
function find(){
	str="[]";
	adds = [];
	deletes = [];
	updates = [];
	var inputs = document.getElementsByName("add");
	for (var i=0;i<inputs.length;i++){
		if (inputs[i].checked==true){
			adds.push(i+1);
		}
	}
	var inputs = document.getElementsByName("delete");
	for (var i=0;i<inputs.length;i++){
		if (inputs[i].checked==true){
			deletes.push(i+1);
		}
	}
	var inputs = document.getElementsByName("update");
	for (var i=0;i<inputs.length;i++){
		if (inputs[i].checked==true){
			updates.push(i+1);
		}
	}
	checkdata();
}
function judegeupd(){
	var adds = [];
	var deletes = [];
	var updates = [];
	var inputs = document.getElementsByName("add");
	for (var i=0;i<inputs.length;i++){
		if (inputs[i].checked==true){
			adds.push(i+1);
		}
	}
	var inputs = document.getElementsByName("delete");
	for (var i=0;i<inputs.length;i++){
		if (inputs[i].checked==true){
			deletes.push(i+1);
		}
	}
	var inputs = document.getElementsByName("update");
	for (var i=0;i<inputs.length;i++){
		if (inputs[i].checked==true){
			updates.push(i+1);
		}
	}
	if(adds.length!=0 || deletes.length!=0 || updates.length!=0){
		fSave = true;
	}
}
function fuse(){
	str="[";
	for (var i=0;i<adds.length;i++){
		if (i != 0) {
			str += ",";
		}
		for (var col=0; col < cols-3; col++) {
			var input = tab.rows[adds[i]].cells[col].childNodes.item(0);
			var value = input.value;
			if (col == 0) {
				str += "{";
			}
			if(ms=="score" && col==1 || ms=="strclass" && col==0 || ms=="strclass" && col==7 ){
				continue;
			}else{
				if(ms=="strclass" && col==4){
					var teachers = json.teachers;
					for(var a=0;a<teachers.length;a++){
						if(value==teachers[a]["teacher_name"]) value = teachers[a]["teacher_id"];
					}
				}
				str += "\"" + input.getAttribute("name") + "\":\"" + value + "\",";
			}
			
			if (col==cols-4){
				str += "\"operator\":\"" + 10 + "\"}";
			}
		}

	}
	for (var i=0;i<deletes.length;i++){
		if (adds.length<=0 && i==0) {

		}else{
			str += ",";
		}
		for (var col=0; col < cols-3; col++) {
			var input = tab.rows[deletes[i]].cells[col].childNodes.item(0);
			var value = input.value;
			if (col == 0) {
				str += "{";
			}
			
			if(ms=="mockscore" && col==1 || ms=="score" && col==1){
				continue;
			}else{
				if(ms=="strclass" && col==4){
					var teachers = json.teachers;
					for(var a=0;a<teachers.length;a++){
						if(value==teachers[a]["teacher_name"]) value = teachers[a]["teacher_id"];
					}
				}
				str += "\"" + input.getAttribute("name") + "\":\"" + value + "\",";
			}
			
			if (col==cols-4){
				str += "\"operator\":\"" + 20 + "\"}";
			}
		}

	}
	for (var i=0;i<updates.length;i++){
		if (adds.length==0 && deletes.length==0 && i==0) {

		}else{
			str += ",";
		}
		for (var col=0; col < cols-3; col++) {
			var input = tab.rows[updates[i]].cells[col].childNodes.item(0);
			var value = input.value;
			if (col == 0) {
				str += "{";
			}
			
			if(ms=="mockscore" && col==1 || ms=="score" && col==1){
				continue;
			}else{
				if(ms=="strclass" && col==4){
					var teachers = json.teachers;
					for(var a=0;a<teachers.length;a++){
						if(value==teachers[a]["teacher_name"]) value = teachers[a]["teacher_id"];
					}
				}
				str += "\"" + input.getAttribute("name") + "\":\"" + value + "\",";
			}
			
			if (col==cols-4){
				str += "\"operator\":\"" + 30 + "\"}";
			}
		}
	}
	str+="]";
	initRequest();
	
}
var xmlhttp = null;
function initRequest(){
	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = handler;
}
function send(){
	find();
	if(flag == true && fa==true){
		initRequest();
	    xmlhttp.open("post", "../frontservlet");
	    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	    xmlhttp.send("operat=UPDATE&source="+ms+"&data="+str);
	}
}
function handler(){
	if (xmlhttp.status == 200){
		if (xmlhttp.readyState == 4){
			var str = xmlhttp.responseText;
			zeroModal.success(str);
			//alert(str);
			refreshTable();
			$('.deldiv').attr("style","cursor:default");
			$('.del').attr("style","background-color:grey");
		}
	}
}	
function epFile(){
	window.location = '../frontservlet?operat=EXPORT&source='+ms;
}
function upButtonDisabled(){
	
	document.getElementById("upButton").disabled = false;
	
}
function upFile(){
	var fd = new FormData();
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function(){
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
			var str = xmlhttp.responseText;
			$.dialog.close();
			var rs = xmlhttp.responseText;
			console.log(rs);
			if(rs.indexOf("导入失败",0) > 0 || rs == '表格格式有误,请检查列名是否对应!' 
					|| rs.indexOf("成功导入 0 条记录", 0) > 0){
				zeroModal.error('<div class="msg_tip">'+rs+"</div>");
			}else
				zeroModal.success('<div class="msg_tip">'+rs+"</div>");
			refreshTable();
		}
	};
	if (document.getElementById("file").files[0] != null){
		$.dialog({
		       type : 'info',
		       infoText : '正在执行操作…',
		       infoIcon : '../dialog/images/icon/loading.gif',      
		});
		fd.append("id", document.getElementById("file").files[0]);
		var url = '../dataImport?fileName='+ms;
		xmlhttp.open("POST",url);
		xmlhttp.send(fd);
	}else{
		alert("未选中文件");
		document.getElementById("upButton").disabled = true;
	}
}
function importview(){
	$('.importContextdiv').slideToggle(function(){
		var display = $(this).css('display');
		if(display == 'none'){
			$('.search_div').show();
		}else{
			$('.search_div').hide();
		}
	});
}
function getSelect(obj)
{
    name = obj;
}
