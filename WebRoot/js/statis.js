// 路径配置
$(document).ready(function(){
	$('#statis_menu').click(function(e){
		statis_menuClick(e);
	});
	
});
function statis_menuClick(e){
	$.dialog({
	       type : 'info',
	       infoText : '正在加载…',
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
				'<ul class="fa-ul chart_tip"><li class="color4"><i class="fa-li fa fa-pie-chart"></i>总人数: </li>'+
				'<li class="color1"><i class="fa-li fa fa-pie-chart"></i>通过人数: </li>'+
				'<li class="color2"><i class="fa-li fa fa-pie-chart "></i>未通过人数: </li>'+
				'<li class="color3"><i class="fa-li fa fa-pie-chart"></i>缺考人数: </li>'+
				'</ul>	</div><div class="show_chartData evl_chartData"><ul class="fa-ul chart_tip">'+
				'<li class="color1"><i class="fa-li fa fa-pie-chart"></i>优秀人数: </li>'+
				'<li class="color2"><i class="fa-li fa fa-pie-chart "></i>良好人数: </li>'+
				'<li class="color3"><i class="fa-li fa fa-pie-chart"></i>合格人数: </li>'+
				'<li class="color4"><i class="fa-li fa fa-pie-chart"></i>不合格人数: </li>'+
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
		$('.chart').html('<p class="nostatis">暂无数据统计数据</p>');
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
	// 使用
	require(
	    [
	        'echarts',
		'echarts/chart/bar','echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
		],
		function (ec) {
		    // 基于准备好的dom，初始化echarts图表
	    	var myChart = ec.init(document.getElementById('main')); 
	    	option1 = {
		    title : {
		        text: '教师通过率',
		       // subtext: '纯属虚构'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['模拟考试','实际考试']
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
		            name:'模拟考试',
		            type:'bar',
		            data:mock,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        },
		        {
		            name:'实际考试',
		            type:'bar',
		            data:exam,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        },
		        
		    ]
		};

	    	// 为echarts对象加载数据 
	    	option2 = {
	    		    title : {
	    		        text: '评定成绩',
	    		        x:'center'
	    		    },
	    		    tooltip : {
	    		        trigger: 'item',
	    		        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    		    },
	    		    legend: {
	    		        orient : 'vertical',
	    		        x : 'left',
	    		        data:['优秀','良好','合格','不合格']
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
	    		            name:'评定成绩',
	    		            type:'pie',
	    		            radius : '55%',
	    		            center: ['50%', '60%'],
	    		            data:[
	    		                {value:evl_statis[0], name:'优秀'},
	    		                {value:evl_statis[1], name:'良好'},
	    		                {value:evl_statis[2], name:'合格'},
	    		                {value:evl_statis[3], name:'不合格'},
	    		            ]
	    		        }
	    		    ]
	    		};
	    	//$('#pass_statis').setOption(option2);   
	    		option3 = {
	    			    title : {
	    			        text: '学生过关率',
	    			        x:'center'
	    			    },
	    			    tooltip : {
	    			        trigger: 'item',
	    			        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    			    },
	    			    legend: {
	    			        orient : 'vertical',
	    			        x : 'left',
	    			        data:['通过','未过','缺考']
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
	    			            name:'考试情况',
	    			            type:'pie',
	    			            radius : '55%',
	    			            center: ['50%', '60%'],
	    			            data:[
	    			                {value:stu_statis[0], name:'通过'},
	    			                {value:stu_statis[1], name:'未过'},
	    			                {value:stu_statis[2], name:'缺考'},

	    			            ]
	    			        }
	    			    ]
	    			};
	    	
	    		option4 = {
	    			    title : {
	    			        text: '教师通过率排行',
	    			    },
	    			    tooltip : {
	    			        trigger: 'axis'
	    			    },
	    			    legend: {
	    			        data:['实际考试']
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
	    			            name:'模拟考试',
	    			            type:'bar',
	    			            data:mock,
	    			        },*/
	    			        {
	    			            name:'实际考试',
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
