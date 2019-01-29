/**
 * 工作台页面js
 */
$(function(){
	window.onload = function(){
		calcHeight();
		initChart();
		initDate();
	};

	$(window).resize(function(){
		calcHeight();
		initDate();
	}).resize();
	
	$('body').niceScroll({cursorcolor:"#ccc"});
});

//计算宽度和高度
var calcHeight = function(){
	var wh = $(window).height()/2 - 30;
	var whFir = wh - 91;
	var applyListH = wh - $('.title').height() - 10;
	var appLiChildH = (applyListH/2 - 78)/3*2;
	$('.part').height(wh);
	$('.part > .userMain').height(whFir);
	//	应用列表
	$('.applistWrap').height(applyListH);
	$('.applistWrap .icon').each(function(i){
		if(i < 4){
			$('.applistWrap .icon').eq(i).css({'margin-top':appLiChildH});
		}
	})
	// 最新新闻
	$('.sortListWrap').height(applyListH);
	$('.sortListWrap li:last-child').css({'margin-bottom':0});
		// 滚动条
	$('.sortListWrap > ul').niceScroll();
	//	薪资信息概要
	$('.summaryWrap').height(applyListH);
}

/*初始化echart图*/
var initChart = function(){
	var $tag = $('#historyWrap');
	var tagW = $('.part.history').width();
	var tagH = $('.part.history').height() - 25;
	$tag.width(tagW).height(tagH);
	
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('historyWrap'));

	// 指定图表的配置项和数据
	var dataOption = {
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['网络费用','电话费用','短信费用']
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		            data : ['一月','二月','三月','四月','五月','六月','七月']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            splitLine:false,
		        }
		    ],
		    series : [
		        {
		            name:'网络费用',
		            type:'line',
		            stack: '总量',
		            areaStyle: {normal: {}},
		            smooth: true,
		            data:[120, 132, 101, 134, 90, 230, 210],
		            itemStyle:{
		            	normal:{color:'#50acf5'}
		            }
		        },
		        {
		            name:'电话费用',
		            type:'line',
		            stack: '总量',
		            smooth: true,
		            areaStyle: {normal: {}},
		            data:[220, 182, 191, 234, 290, 330, 310],
		            itemStyle:{
		            	normal:{color:'#96cffd'}
		            }
		        },
		        {
		            name:'短信费用',
		            type:'line',
		            stack: '总量',
		            smooth: true,
		            areaStyle: {normal: {}},
		            data:[150, 232, 201, 154, 190, 330, 410],
		            itemStyle:{
		            	normal:{color:'#d9eeff'}
		            }
		        }
		    ]
		};

	// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(dataOption);
    
    
    $(window).resize(function(){
		myChart.resize();
	}).resize();
}

/*初始化日历控件*/
var initDate = function(){
	var $tag = $('#dateArrange');
	var tagW = $('.part.date').width() - 50;
	var tagH = $('.part.date').height() - 25 - 50;
	$tag.width(tagW).height(tagH);
	var dateArrange = $tag.datepicker({
      changeMonth: true,
      changeYear: true
    });
}













