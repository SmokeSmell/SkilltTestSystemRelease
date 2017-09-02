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
	 	       infoText : '���ڼ��ء�',
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
	var html = '<div class="proce_div step_1"><div class="proce_title"><b>1.��Ϣ����</b></div>'+
				'<div class="proce_top" id="info_config"></div><div class="proce_buttom">'+
				'<p class="must_title">������������²���:</p><ul class="op_tip">'+
				'<li class="stuInfo">1.����/��� ѧ����Ϣ</li><li class="teachInfo">2.����/��� ��ʦ��Ϣ</li>'+
				'<li class="classInfo">3.����/��� ǿ������Ϣ</li></ul><p class="must_title">��������Ҫ���еĲ���:</p>'+
				'<ul class="op_tip could_tip"><li>1.���ص���ģ��</li><li>2.���ݱ༭/��ѯ</li>'+
				'</ul></div></div><div class="proce_div step_2"><div class="proce_title"><b>2.Ԥ����</b></div>'+
				'<div class="proce_top" id="step_preapply"></div><div class="proce_buttom">'+
				'<p class="must_title">������������²���:</p><ul class="op_tip">'+
				'<li class="configApply">1.Ԥ��������</li><li class="configApply">2.�趨Ԥ������ʼ/����ʱ��</li>'+
				'<li class="importPay">3.����ɷ���Ϣ</li></ul><p class="must_title">��������Ҫ���еĲ���:</p>'+
				'<ul class="op_tip could_tip"><li>1.������������ģ��</li><li>2.���/��������</li>'+
				'<li>3.����δ�ɷ�</li><li>4.�鿴�ɷ���־</li></ul></div></div><div class="proce_div step_3">'+
				'<div class="proce_title"><b>3.ǿ���࿪ʼ</b></div><div class="proce_top" id="step_strclass">'+
				'</div><div class="proce_buttom"><p class="must_title">������������²���:</p><ul class="op_tip">'+
				'<li class="importMock">1.����/��� ģ��ɼ�</li><li class="exportExam">2.��������������</li>'+
				'<li class="importScore">3.���������ȼ����Գɼ�</li></ul><p class="must_title">��������Ҫ���еĲ���:</p>'+
				'<ul class="op_tip could_tip"><li>1.����ǿ����ְ��</li><li>2.����ģ��ɼ�����ģ��</li>'+
				'</ul></div></div><div class="step_arrow"><li class="fa fa-angle-double-right fa-5x" ></li>'+
				'</div><button class="look_statis"><i class="fa  fa-bar-chart-o fw">&nbsp;�鿴ͳ��</i></button>'+
				'<div class="proced_sub"><li class="fa fa-square fw uncompelet">δ���</li>'+
				'<li class="fa fa-square fw compelet">�����</li><li class="fa fa-square fw isdoing">���ڽ���</li>'+
				'<li class="fa fa-square fw maybedo">������Ҫ��</li><div class="proce_tip">'+
				'<p>*������Ϣ�����ο�,��������ʵ�ʲ���Ϊ׼</p><p>*������ڲ��������г����κ�����,��ο�:��ҳ->����</p>'+
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
	// ʹ��
	require(
	    [
	        'echarts',
		'echarts/chart/bar','echarts/chart/pie' // ʹ����״ͼ�ͼ���barģ�飬�������
		],
		function (ec) {
		    // ����׼���õ�dom����ʼ��echartsͼ��
	    	
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
	    		            '���״��'
	    		        ]
	    		    },
	    		    title : {
	    		        text: '����״��',
	    		       
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
	    		                {name:'���״��', value:step1,itemStyle : labelTop}
	    		            ]
	    		        },
	    		       
	    		    ]
	    		};
	    	option2 = {
	    		    legend: {
	    		        x : 'center',
	    		        y : '70%',
	    		        data:[
	    		            '���״��'
	    		        ]
	    		    },
	    		    title : {
	    		        text: 'Ԥ����״��',
	    		       
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
	    		                {name:'���״��', value:step2,itemStyle : labelTop}
	    		            ]
	    		        },
	    		       
	    		    ]
	    		};
	    	option3 = {
	    		    legend: {
	    		        x : 'center',
	    		        y : '70%',
	    		        data:[
	    		            '���״��'
	    		        ]
	    		    },
	    		    title : {
	    		        text: 'ǿ���������',
	    		       
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
	    		                {name:'���״��', value:step3,itemStyle : labelTop}
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
