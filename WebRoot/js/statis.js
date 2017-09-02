// ·������
$(document).ready(function(){
	$('#statis_menu').click(function(e){
		statis_menuClick(e);
	});
	
});
function statis_menuClick(e){
	$.dialog({
	       type : 'info',
	       infoText : '���ڼ��ء�',
	       infoIcon : '../dialog/images/icon/loading.gif',      
 });
	$('.right').empty();
	$('.site_right').empty();
	createStatisHtml();
	$.ajax({
		url:'../adminController',
		data:{operat:'GETSTATIS'},
		type:'POST',
		dataType:'JSON',
		success:function(json,status){
			parseData(json);
			$.dialog.close();
		}
	});
}
function createStatisHtml(){
	var html = '<div id="rigth_left"><div id="main" class="chart"></div>'+
				'<div class="chart" id="teach_range"></div></div>'+
				'<div id="pass_statis" class="chart"><div id="statis_score"></div>'+
				'<div id="statis_evl"></div><div class="show_chartData score_chart_Data">'+
				'<ul class="fa-ul chart_tip"><li class="color4"><i class="fa-li fa fa-pie-chart"></i>������: </li>'+
				'<li class="color1"><i class="fa-li fa fa-pie-chart"></i>ͨ������: </li>'+
				'<li class="color2"><i class="fa-li fa fa-pie-chart "></i>δͨ������: </li>'+
				'<li class="color3"><i class="fa-li fa fa-pie-chart"></i>ȱ������: </li>'+
				'</ul>	</div><div class="show_chartData evl_chartData"><ul class="fa-ul chart_tip">'+
				'<li class="color1"><i class="fa-li fa fa-pie-chart"></i>��������: </li>'+
				'<li class="color2"><i class="fa-li fa fa-pie-chart "></i>��������: </li>'+
				'<li class="color3"><i class="fa-li fa fa-pie-chart"></i>�ϸ�����: </li>'+
				'<li class="color4"><i class="fa-li fa fa-pie-chart"></i>���ϸ�����: </li>'+
				'</ul></div></div>';
	$('.right').append(html);
}
function parseData(json){
	var mock = [];
	var exam = [];
	var teachNames = [];
	console.log(json);
	var teach = json['teacher'];
	var student = json['student'];
	if(teach == null){
		$('.chart').html('<p class="nostatis">��������ͳ������</p>');
		return;
	}
	$(teach).each(function(index,ele){
		var exam_num = ele['exam_num'];
		var exam_pass = ele['exam_pass'];
		var mock_num = ele['mock_num'];
		var mock_pass = ele['mock_pass'];
		var rs1 = exam_pass / exam_num;
		rs1 = Math.round(rs1*1000) / 10;
		var rs2 = mock_pass / mock_num;
		rs2 = Math.round(rs2*1000) / 10;
		mock.push(rs2);
		exam.push(rs1);
		teachNames.push(ele['teacher_name']);
	});
	var stu_statis = [];
	var evl_statis = [];
	var total = 0;
	$(student).each(function(index,ele){
		if(index == 0){
			var not = ele['not'];
			var pass = ele['pass'];
			total = ele['total'];
			stu_statis.push(pass);
			stu_statis.push(total - pass - not);
			stu_statis.push(not);
			$('.chart_tip > li').eq(0).append(total);
			$('.chart_tip > li').eq(1).append(pass);
			$('.chart_tip > li').eq(2).append(total - pass - not);
			$('.chart_tip > li').eq(3).append(not);
		}else{
			var best = ele['best'];
			var great = ele['great'];
			var qualify = ele['qualify'];
			var notQualify = total - best - great - qualify;
			evl_statis.push(best);
			evl_statis.push(great);
			evl_statis.push(qualify);
			evl_statis.push(notQualify);
			$('.chart_tip > li').eq(4).append(best);
			$('.chart_tip > li').eq(5).append(great);
			$('.chart_tip > li').eq(6).append(qualify);
			$('.chart_tip > li').eq(7).append(notQualify);
		}
		
		configChart(mock,exam,teachNames,stu_statis,evl_statis);
	});
}
function configChart(mock,exam,teachNames,stu_statis,evl_statis){
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
	    	var myChart = ec.init(document.getElementById('main')); 
	    	option1 = {
		    title : {
		        text: '��ʦͨ����',
		       // subtext: '�����鹹'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['ģ�⿼��','ʵ�ʿ���']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: false},
		            dataView : {show: true, readOnly: false},
		            magicType : {show: false, type: ['line', 'bar']},
		            restore : {show: false},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : teachNames
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'ģ�⿼��',
		            type:'bar',
		            data:mock,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '���ֵ'},
		                    {type : 'min', name: '��Сֵ'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: 'ƽ��ֵ'}
		                ]
		            }
		        },
		        {
		            name:'ʵ�ʿ���',
		            type:'bar',
		            data:exam,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '���ֵ'},
		                    {type : 'min', name: '��Сֵ'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: 'ƽ��ֵ'}
		                ]
		            }
		        },
		        
		    ]
		};

	    	// Ϊecharts����������� 
	    	option2 = {
	    		    title : {
	    		        text: '�����ɼ�',
	    		        x:'center'
	    		    },
	    		    tooltip : {
	    		        trigger: 'item',
	    		        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    		    },
	    		    legend: {
	    		        orient : 'vertical',
	    		        x : 'left',
	    		        data:['����','����','�ϸ�','���ϸ�']
	    		    },
	    		    toolbox: {
	    		        show : true,
	    		        feature : {
	    		            dataView : {show: true, readOnly: false},
	    		           
	    		            saveAsImage : {show: true}
	    		        }
	    		    },
	    		    calculable : true,
	    		    series : [
	    		        {
	    		            name:'�����ɼ�',
	    		            type:'pie',
	    		            radius : '55%',
	    		            center: ['50%', '60%'],
	    		            data:[
	    		                {value:evl_statis[0], name:'����'},
	    		                {value:evl_statis[1], name:'����'},
	    		                {value:evl_statis[2], name:'�ϸ�'},
	    		                {value:evl_statis[3], name:'���ϸ�'},
	    		            ]
	    		        }
	    		    ]
	    		};
	    	//$('#pass_statis').setOption(option2);   
	    		option3 = {
	    			    title : {
	    			        text: 'ѧ��������',
	    			        x:'center'
	    			    },
	    			    tooltip : {
	    			        trigger: 'item',
	    			        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    			    },
	    			    legend: {
	    			        orient : 'vertical',
	    			        x : 'left',
	    			        data:['ͨ��','δ��','ȱ��']
	    			    },
	    			    toolbox: {
	    			        show : true,
	    			        feature : {
	
	    			            dataView : {show: true, readOnly: false},
	    			            saveAsImage : {show: true}
	    			        }
	    			    },
	    			    calculable : true,
	    			    series : [
	    			        {
	    			            name:'�������',
	    			            type:'pie',
	    			            radius : '55%',
	    			            center: ['50%', '60%'],
	    			            data:[
	    			                {value:stu_statis[0], name:'ͨ��'},
	    			                {value:stu_statis[1], name:'δ��'},
	    			                {value:stu_statis[2], name:'ȱ��'},

	    			            ]
	    			        }
	    			    ]
	    			};
	    	
	    		option4 = {
	    			    title : {
	    			        text: '��ʦͨ��������',
	    			    },
	    			    tooltip : {
	    			        trigger: 'axis'
	    			    },
	    			    legend: {
	    			        data:['ʵ�ʿ���']
	    			    },
	    			    toolbox: {
	    			        show : true,
	    			        feature : {	           
	    			            dataView : {show: true, readOnly: false},
	    			            saveAsImage : {show: true}
	    			        }
	    			    },
	    			    calculable : true,
	    			    xAxis : [
	    			        {
	    			            type : 'value',
	    			            boundaryGap : [0, 0.01]
	    			        }
	    			    ],
	    			    yAxis : [
	    			        {
	    			            type : 'category',
	    			            data : teachNames,
	    			        }
	    			    ],
	    			    series : [
	    			       /* {
	    			            name:'ģ�⿼��',
	    			            type:'bar',
	    			            data:mock,
	    			        },*/
	    			        {
	    			            name:'ʵ�ʿ���',
	    			            type:'bar',
	    			            data:exam,
	    			        }
	    			    ]
	    			};
	        myChart.setOption(option1); 
	        var myChart2 = ec.init(document.getElementById('statis_score')); 
	    	myChart2.setOption(option3); 
	        var myChart3 = ec.init(document.getElementById('statis_evl')); 
	    	myChart3.setOption(option2); 
	    	 var myChart4 = ec.init(document.getElementById('teach_range')); 
		    myChart4.setOption(option4); 
	    }
	);
}
