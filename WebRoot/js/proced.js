$(document).ready(function(){
	OpStatis();
	$('#sys_prec').click(function(e){
		OpStatis();
	});
});
function OpStatis(){
	var len = $('.proce_div').length;
	if(len == 0){
		$.dialog({
	 	       type : 'info',
	 	       infoText : '正在加载…',
	 	       infoIcon : '../dialog/images/icon/loading.gif',      
	    });
		$('.right').empty();
		$('.site_right').empty();
		createOpStatis();
		$('.look_statis').click(function(e){
			statis_menuClick(e);
		});
		$.dialog.close();
	}	
}
function createOpStatis(){
	var html = '<div class="proce_div step_1"><div class="proce_title"><b>1.信息配置</b></div>'+
				'<div class="proce_top" id="info_config"></div><div class="proce_buttom">'+
				'<p class="must_title">您必须完成以下操作:</p><ul class="op_tip">'+
				'<li class="stuInfo">1.导入/添加 学生信息</li><li class="teachInfo">2.导入/添加 教师信息</li>'+
				'<li class="classInfo">3.导入/添加 强化班信息</li></ul><p class="must_title">您可能需要进行的操作:</p>'+
				'<ul class="op_tip could_tip"><li>1.下载导入模板</li><li>2.数据编辑/查询</li>'+
				'</ul></div></div><div class="proce_div step_2"><div class="proce_title"><b>2.预报名</b></div>'+
				'<div class="proce_top" id="step_preapply"></div><div class="proce_buttom">'+
				'<p class="must_title">您必须完成以下操作:</p><ul class="op_tip">'+
				'<li class="configApply">1.预报名配置</li><li class="configApply">2.设定预报名开始/结束时间</li>'+
				'<li class="importPay">3.导入缴费信息</li></ul><p class="must_title">您可能需要进行的操作:</p>'+
				'<ul class="op_tip could_tip"><li>1.下载批量导入模板</li><li>2.添加/批量报名</li>'+
				'<li>3.导出未缴费</li><li>4.查看缴费日志</li></ul></div></div><div class="proce_div step_3">'+
				'<div class="proce_title"><b>3.强化班开始</b></div><div class="proce_top" id="step_strclass">'+
				'</div><div class="proce_buttom"><p class="must_title">您必须完成以下操作:</p><ul class="op_tip">'+
				'<li class="importMock">1.导入/添加 模拟成绩</li><li class="exportExam">2.导出允许考试名单</li>'+
				'<li class="importScore">3.导入计算机等级考试成绩</li></ul><p class="must_title">您可能需要进行的操作:</p>'+
				'<ul class="op_tip could_tip"><li>1.导出强化班分班表</li><li>2.下载模拟成绩导入模板</li>'+
				'</ul></div></div><div class="step_arrow"><li class="fa fa-angle-double-right fa-5x" ></li>'+
				'</div><button class="look_statis"><i class="fa  fa-bar-chart-o fw">&nbsp;查看统计</i></button>'+
				'<div class="proced_sub"><li class="fa fa-square fw uncompelet">未完成</li>'+
				'<li class="fa fa-square fw compelet">已完成</li><li class="fa fa-square fw isdoing">正在进行</li>'+
				'<li class="fa fa-square fw maybedo">可能需要做</li><div class="proce_tip">'+
				'<p>*以上信息仅供参考,具体结果以实际操作为准</p><p>*如果您在操作过程中出现任何问题,请参考:首页->帮助</p>'+
				'</div></div></div>';
	$('.right').append(html);
	$.ajax({
		url:'../adminController',
		data:{operat:'GETOPERATSTATIS'},
		type:'post',
		dataType:'JSON',
		success:function(json,status){
			var step1 = 0;
			var step2 = 0;
			var step3 = 0;
			$.each(json,function(index,ele){
				if(ele > 0)
				$('.'+index).css('color','green');
				if(index == 'stuInfo' || index == 'teachInfo' || index == 'classInfo'){
					if(ele > 0)
						step1++;
				}else if(index == 'configApply' || index == 'importPay'){
					if(ele > 0)
						step2++;
				}else{
					if(ele > 0)
						step3++;
				}
			});
			if(step1 == 3){
				$('.step_1').css('border','3px green solid');
			}else if(step1 > 0){
				$('.step_1').css('border','3px #005dff solid');
			}
			if(step2 == 2){
				$('.step_2').css('border','3px green solid');
			}else if(step2 > 0){
				$('.step_2').css('border','3px #005dff solid');
			}
			if(step3 == 3){
				$('.step_3').css('border','3px green solid');
			}else if(step3 > 0){
				$('.step_3').css('border','3px #005dff solid');
			}
			progress(step1 * 33,step2 * 33,step3 * 33);
			console.log(step1+","+step2+","+step3);
		}
	});
}
function progress(step1,step2,step3){
	if(step1 == 99)
		step1 = 100;
	if(step2 == 66)
		step2 = 100;
	if(step3 == 99)
		step3 = 100;
	require.config({
	    paths: {
	        echarts: '../plugins/echart/dist'
	    }
	});
	// 使用
	require(
	    [
	        'echarts',
		'echarts/chart/bar','echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
		],
		function (ec) {
		    // 基于准备好的dom，初始化echarts图表
	    	
	    	var labelTop = {
	    		    normal : {
	    		        label : {
	    		            show : true,
	    		            position : 'center',
	    		            formatter : '{b}',
	    		            textStyle: {
	    		                baseline : 'bottom'
	    		            }
	    		        },
	    		        labelLine : {
	    		            show : false
	    		        }
	    		    }
	    		};
	    		var labelFromatter = {
	    		    normal : {
	    		        label : {
	    		            formatter : function (params){
	    		                return 100 - params.value + '%';
	    		            },
	    		            textStyle: {
	    		                baseline : 'top'
	    		            }
	    		        }
	    		    },
	    		}
	    		var labelBottom = {
	    		    normal : {
	    		        color: '#ccc',
	    		        label : {
	    		            show : true,
	    		            position : 'center'
	    		        },
	    		        labelLine : {
	    		            show : false
	    		        }
	    		    },
	    		    emphasis: {
	    		        color: 'rgba(0,0,0,0)'
	    		    }
	    		};
	    		var radius = [40, 55];
	    	option = {
	    		    legend: {
	    		        x : 'center',
	    		        y : '70%',
	    		        data:[
	    		            '完成状况'
	    		        ]
	    		    },
	    		    title : {
	    		        text: '配置状况',
	    		       
	    		        x: 'center'
	    		    },
	    		   
	    		    series : [
	    		        {
	    		            type : 'pie',
	    		            center : ['50%', '40%'],
	    		            radius : radius,
	    		            x: '0%', // for funnel
	    		            itemStyle : labelFromatter,
	    		            data : [
	    		                {name:'other', value:100-step1, itemStyle : labelBottom},
	    		                {name:'完成状况', value:step1,itemStyle : labelTop}
	    		            ]
	    		        },
	    		       
	    		    ]
	    		};
	    	option2 = {
	    		    legend: {
	    		        x : 'center',
	    		        y : '70%',
	    		        data:[
	    		            '完成状况'
	    		        ]
	    		    },
	    		    title : {
	    		        text: '预报名状况',
	    		       
	    		        x: 'center'
	    		    },
	    		   
	    		    series : [
	    		        {
	    		            type : 'pie',
	    		            center : ['50%', '40%'],
	    		            radius : radius,
	    		            x: '0%', // for funnel
	    		            itemStyle : labelFromatter,
	    		            data : [
	    		                {name:'other', value:100-step2, itemStyle : labelBottom},
	    		                {name:'完成状况', value:step2,itemStyle : labelTop}
	    		            ]
	    		        },
	    		       
	    		    ]
	    		};
	    	option3 = {
	    		    legend: {
	    		        x : 'center',
	    		        y : '70%',
	    		        data:[
	    		            '完成状况'
	    		        ]
	    		    },
	    		    title : {
	    		        text: '强化班进行中',
	    		       
	    		        x: 'center'
	    		    },
	    		   
	    		    series : [
	    		        {
	    		            type : 'pie',
	    		            center : ['50%', '40%'],
	    		            radius : radius,
	    		            x: '0%', // for funnel
	    		            itemStyle : labelFromatter,
	    		            data : [
	    		                {name:'other', value:100-step3, itemStyle : labelBottom},
	    		                {name:'完成状况', value:step3,itemStyle : labelTop}
	    		            ]
	    		        },
	    		       
	    		    ]
	    		};
	    	var myChart = ec.init(document.getElementById('info_config')); 
	        myChart.setOption(option); 
	        var myChart2 = ec.init(document.getElementById('step_preapply')); 
	        myChart2.setOption(option2); 
	        var myChart3 = ec.init(document.getElementById('step_strclass')); 
	        myChart3.setOption(option3); 
	        
	    }
	);
}
