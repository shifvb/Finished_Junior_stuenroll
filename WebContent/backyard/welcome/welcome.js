$(function() {

	require.config({
		paths : {
			echarts : '../../js/echarts'
		}
	});

	/**
	 * 年度统计抽象接口
	 */
	var I_AnnualStaistics = function() {

	}
	/**
	 * 初始化抽象方法
	 */
	I_AnnualStaistics.prototype.init = function() {
		throw "抽象方法";
	}
	I_AnnualStaistics.prototype.clock = function() {
		throw "抽象方法";
	}
	I_AnnualStaistics.prototype.monthdata = function(year,month) {
		throw "抽象方法";
	}
	
//---------------------------------------实现类---------------------------------------------------------------	
	/**
	 * 年度统计实现
	 */
	var AnnualStatistics = function() {

	}

	AnnualStatistics.prototype = new I_AnnualStaistics();
	
//---------------------------------------------------------------初始化开始-----------------------------------------
	// @override
	AnnualStatistics.prototype.init = function() {
		//如果不具备相应权限，则不再继续执行程序
		if (!checkPermission(["3_4","4_4"])) {
			return;
		}
		// 设置组织机构名字
		
		$("#oname").text(sessionStorage.organization);
		
		// 设置年份
		var date = new Date();
		var year = date.getFullYear();
		var annual_statistics = $(".annual-statistics");
		annual_statistics.find(".year").text(year);

		// 更新进度
		var month = date.getMonth() + 1;
		var items = annual_statistics.find(".step-list .step-item");
		items.removeClass("step-item-active");
		if (month >= 1 && month < 3) {
			$(items[0]).addClass("step-item-active");
		}
		else if (month >= 3 && month < 7) {
			$(items[1]).addClass("step-item-active");
		}
		else if (month >= 7 && month < 9) {
			$(items[2]).addClass("step-item-active");
		}
		else if (month >= 9 && month < 11) {
			$(items[3]).addClass("step-item-active");
		}
		else {
			$(items[4]).addClass("step-item-active");
		}

		// Ajax查询年度纪录
		$.ajax({
			"type" : "post",
			"url" : "/stuenroll/welcome/statisticsInYear",
			"dataType" : "json",
			"async" : false,
			"data" : {
				"year" : year
			},
			"success" : function(json) {
				var list1 = json.statistics.报名数据;
				var list2 = json.statistics.审查数据;
				var list3 = json.statistics.学习数据;
				var list4 = json.statistics.中退数据;
				var list5 = json.statistics.就业数据;
				// 使用
				require([ 'echarts', 'echarts/chart/line' ], function(ec) {
					// 基于准备好的dom，初始化echarts图表
					var myChart = ec.init(document.getElementsByClassName("statistics")[0], 'macarons');

					var option = {
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							data : [ '报名人数', '审查人数', '学习人数', '中退人数', '就业人数' ]
						},
						toolbox : {
							show : true,
							feature : {
								dataView : {
									show : true,
									readOnly : false
								},
								restore : {
									show : true
								},
								saveAsImage : {
									show : true
								}
							}
						},
						calculable : false,
						xAxis : [ {
							type : 'category',
							boundaryGap : false,
							data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月', ]
						} ],
						yAxis : [ {
							type : 'value'
						} ],
						series : [ {
							name : '报名人数',
							type : 'line',
							stack : '总量',
							itemStyle : {
								normal : {
									areaStyle : {
										type : 'default'
									}
								}
							},
							data : list1
						}, {
							name : '审查人数',
							type : 'line',
							stack : '总量',
							itemStyle : {
								normal : {
									areaStyle : {
										type : 'default'
									}
								}
							},
							data : list2
						}, {
							name : '学习人数',
							type : 'line',
							stack : '总量',
							itemStyle : {
								normal : {
									areaStyle : {
										type : 'default'
									}
								}
							},
							data : list3
						}, {
							name : '中退人数',
							type : 'line',
							stack : '总量',
							itemStyle : {
								normal : {
									areaStyle : {
										type : 'default'
									}
								}
							},
							data : list4
						}, {
							name : '就业人数',
							type : 'line',
							stack : '总量',
							itemStyle : {
								normal : {
									areaStyle : {
										type : 'default'
									}
								}
							},
							data : list5
						} ]
					};

					// 为echarts对象加载数据
					myChart.setOption(option);
				});
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
//----------------------------------------------------------------------开始@GZC-------------------------------------	
		/**
		 * 第二列第一个饼图
		 */
		$.ajax({
			"type" : "post",
			"url" : "http://127.0.0.1/stuenroll/index/seachHotMajor",
			"dataType" : "json",
			"async" : false,
			"data" : {
				"oid" : '738620746913419264'
			},
			"success" : function(json) {
				var items = json.result;
				var list1 = items.map(function(one){
					return one.name;
				})
				var list2 = items.map(function(one){
					return {
						'value':one.amount,
						'name':one.name
					};
				})
//				alert(json.result[0].name);
			    require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
					var myChar = ec.init(document.getElementsByClassName("statistics-4-1-pie")[0], 'macarons');
					var option={
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)",
					    },
					    legend: {
					        orient : 'horizontal',
					        y: 'bottom',
					        data:list1
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
					            name:'访问来源',
					            type:'pie',
					            radius : ['50%', '70%'],
					            center : ['50%', '40%'],
					            itemStyle : {
					                normal : {
					                    label : {
					                        show : false
					                    },
					                    labelLine : {
					                        show : false
					                    }
					                },
					                emphasis : {
					                    label : {
					                        show : true,
					                        position : 'center',
					                        textStyle : {
					                            fontSize : '30',
					                            fontWeight : 'bold'
					                        }
					                    }
					                }
					            },
					            data:list2
					        }
					    ]				
					}
					myChar.setOption(option);
				}		
			)
			}
		})



		/**
		 * 第二列第二个饼图
		 */
		$.ajax({
			"type" : "post",
			"url" : "http://127.0.0.1/stuenroll/index/seachHotPlace",
			"async" : false,
			"dataType" : "json",
			"data" : {
				"oid" : '738620746913419264'
			},
			"success" : function(json) {
				var items = json.result;
				var list1 = items.map(function(one){
					return one.place;
				})
				var list2 = items.map(function(one){
					return {
						'value':one.amount,
						'name':one.place
					};
				})
			    require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
					var myChart = ec.init(document.getElementsByClassName("statistics-4-1-pie")[1], 'macarons');
					var option={
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)",
					    },
					    legend: {
					        orient : 'horizontal',
					        y: 'bottom',
					        data:list1
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
					            name:'访问来源',
					            type:'pie',
					            radius : ['50%', '70%'],
					            center : ['50%', '40%'],
					            itemStyle : {
					                normal : {
					                    label : {
					                        show : false
					                    },
					                    labelLine : {
					                        show : false
					                    }
					                },
					                emphasis : {
					                    label : {
					                        show : true,
					                        position : 'center',
					                        textStyle : {
					                            fontSize : '30',
					                            fontWeight : 'bold'
					                        }
					                    }
					                }
					            },
					            data:list2
					        }
					    ]				
					}
					myChart.setOption(option);
				})		
			}
		})
		
		//第二个饼图结束
		
		
		/**
		 * 第二列第三个饼图
		 */
		$.ajax({
			"type" : "post",
			"async" : false,
			"url" : "http://127.0.0.1/stuenroll/index/seachHotEducation",
			"dataType" : "json",
			"data" : {
				"oid" : '738620746913419264'
			},
			"success" : function(json) {
				var items = json.result;
				var list1 = items.map(function(one){
					return one.education;
				})
				var list2 = items.map(function(one){
					return {
						'value':one.amount,
						'name':one.education
					};
				})
//				alert(list1);
			    require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
					var myChart = ec.init(document.getElementsByClassName("statistics-4-1-pie")[2], 'macarons');
					var option={
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)",
					    },
					    legend: {
					        orient : 'horizontal',
					        y: 'bottom',
					        data:list1
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
					            name:'访问来源',
					            type:'pie',
					            radius : ['50%', '70%'],
					            center : ['50%', '40%'],
					            itemStyle : {
					                normal : {
					                    label : {
					                        show : false
					                    },
					                    labelLine : {
					                        show : false
					                    }
					                },
					                emphasis : {
					                    label : {
					                        show : true,
					                        position : 'center',
					                        textStyle : {
					                            fontSize : '30',
					                            fontWeight : 'bold'
					                        }
					                    }
					                }
					            },
					            data:list2
					        }
					    ]				
					}
					myChart.setOption(option);
				})		
			}
		})		
		
		//第三个饼图结束
		
		
		/**
		 * 第二列第四个图 柱形图
		 */
		$.ajax({
			"type" : "post",
			"async" : false,
			"url" : "http://127.0.0.1/stuenroll/index/searchHotMajorByClass",
			"dataType" : "json",
			"data" : {
				"oid" : '738620746913419264'
			},
			"success" : function(json) {
				var items = json.result;
				var list1 = items.map(function(one){
					return one.name;
				})
				var list2 = items.map(function(one){
					return one.amount
				})
//				alert(json.result[0].name);
			    require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
					var myChart = ec.init(document.getElementsByClassName("statistics-4-1-pie")[3], 'macarons');
					var option = {
					    tooltip : {
					        trigger: 'axis'
					    },
					    calculable : true,
					    xAxis : [
					        {
					            type : 'value',
					            boundaryGap : [0, 0.01]
					        }
					    ],
				        toolbox: {
					        show : true,
					        feature : {
					            dataView : {show: true, readOnly: false}
					        }
					    },
					    yAxis : [
					        {
					            type : 'category',
					            data : list1
					        }
					    ],
					    series : [
					        {
					            type:'bar',
					            data: list2
					        }
					    ]
					};
					myChart.setOption(option);
				})		
			}
		})
		
		//第四个图（柱形图）结束
		
		
		/**
		 * 数据流视图
		 */
		$.ajax({
			"type" : "post",
			"async" : false,
			"url" : "/stuenroll/index/nginxCounter",
			"dataType" : "json",
			"success" : function(json) {
				var result = json.result;
				var list1 = result[0];
				var list2 = result[1];
				require([ 'echarts', 'echarts/chart/line' ], function(ec) {
				var myChart = ec.init(document.getElementsByClassName("statistics-2-1")[0], 'macarons');
				var option = {
				    title : {
				        text: '数据流量',
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['浏览器','手机端']
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
				            type : 'category',
				            boundaryGap : false,
				            data : ['一天前','二天前','三天前','四天前','五天前','六天前','七天前']
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            axisLabel : {
				                formatter: '{value} 次'
				            }
				        }
				    ],
				    series : [
				        {
				            name:'浏览器',
				            type:'line',
				            data:list1,
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
				            name:'手机端',
				            type:'line',
				            data:list2,
				            markPoint : {
				                data : [
				                    {type : 'max', name: '最大值'},
				                    {type : 'min', name: '最小值'}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name : '平均值'}
				                ]
				            }
				        }
				    ]
				};
				myChart.setOption(option);
			})
			},
			"error" : function(){
				toastr.error("系统异常");
			}
		})

		
//------------------------------------雷达图 就业排名--------------------------------------------------------
		$.ajax({
			"type" : "post",
			"async" : false,
			"url" : "http://127.0.0.1/stuenroll/index/searchHotMajorByJob",
			"dataType" : "json",
			"data" : {
				"oid" : '738620746913419264'
			},
			"success" : function(json) {
				var items = json.result;
				var max_amount = 0;
				var list1 = items.map(function(one){
					if(max_amount<one.amount){
						max_amount=one.amount;
					}
					return one.amount;
				})
				var list2 = items.map(function(one){
					return {
						'text':one.name,
						'max':max_amount
					};
				})
				require([ 'echarts', 'echarts/chart/radar' ], function(ec) {
					var myChart = ec.init(document.getElementsByClassName("statistics-2-1")[1], 'macarons');
					var option = {
					    title : {
					        text: '就业排名',
					    },
					    tooltip : {
					        trigger: 'axis'
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            mark : {show: true},
					            dataView : {show: true, readOnly: false},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    polar : [
					        {
					            indicator : list2,
					            radius : 130
					        }
					    ],
					    series : [
					        {
					            name: '就业率',
					            type: 'radar',
					            itemStyle: {
					                normal: {
					                    areaStyle: {
					                        type: 'default'
					                    }
					                }
					            },
					            data : [
					                {
					                    value : list1,
					                    name : '就业率'
					                }
					            ]
					        }
					    ]
					};                  
					myChart.setOption(option);
				})
			}
		})
		
//-------------------------------------------------------------------------------------------------------
   }
/*-----------------------------------------------------------------初始化结束--------------------------------------------
 * 
 */
//----------------------------------------------------右侧钟表-------------------------------------------
	AnnualStatistics.prototype.clock = function() {
		function startTime(){
			var today=new Date()
			var h=today.getHours()
			var m=today.getMinutes()
			var s=today.getSeconds()
			var yy=today.getFullYear()
			var mm=today.getMonth()
			var dd=today.getDate()
			var ww=today.getDay()
			mm=mm+1
			var weekday=new Array(7)
			weekday[0]="日"
			weekday[1]="一"
			weekday[2]="二"
			weekday[3]="三"
			weekday[4]="四"
			weekday[5]="五"
			weekday[6]="六"

			var clocktop=yy+"年"+mm+"月"+dd+"日 "+"\r\r\r\r"+" 星期"+weekday[ww]
			// add a zero in front of numbers<10
			m=checkTime(m);
			s=checkTime(s);
			var clockbottom=h+" : "+m+" : "+s
			$(".clock-top").text(clocktop)
			$(".clock-bottom").text(clockbottom);
		}
		function checkTime(i){
			if (i<10) {
				i="0" + i
			}
		  	return i
		}
		
		var timer = setInterval(function() {
			startTime();
		}, 500);
	}
	
//-------------------------------------钟表初始化结束--------------------------------------------

//------------------------------------月份数据初始化-------------------------------------------------------

	AnnualStatistics.prototype.monthdata = function(year,month) {
		$("#year1").text(year);
		$("#month1").text(month);
		$.ajax({
			"type" : "post",
			"async" : false,
			"url" : "http://127.0.0.1/stuenroll/index/searchHotMonthData",
			"dataType" : "json",
			"data" : {
				"oid" : '738620746913419264',
				"year": year,
				"month": month
			},
			"success" : function(json) {
				var list = json.result;
				var total = 0;
				list.map(function(one){
					total+=one.amount;
				})
				$("#apply-2").text("0人");
				$("#examine-2").text("0人");
				$("#archive-2").text("0人");
				$("#quit-2").text("0人");
				$("#apply-1").css("width",0);
				$("#examine-1").css("width",0);	
				$("#archive-1").css("width",0);
				$("#quit-1").css("width",0);	
				list.map(function(one){
					switch (one.state_id){
						case 1:
							var aaa = one.amount/total*100;
							aaa = aaa+"%";
							$("#apply-1").css("width",aaa);
							$("#apply-2").text(one.amount+'人');
							break;
						case 2:
							var aaa = one.amount/total*100;
							aaa = aaa+"%";
							$("#examine-1").css("width",aaa);						
							$("#examine-2").text(one.amount+'人');
							break;
						case 4:
							var aaa = one.amount/total*100;
							aaa = aaa+"%";
							$("#quit-1").css("width",aaa);						
							$("#quit-2").text(one.amount+'人');
							break;
						case 3:
							break;
						case 5:
							break;
						default:
							var aaa = one.amount/total*100;
							aaa = aaa+"%";
							$("#archive-1").css("width",aaa);						
							$("#archive-2").text(one.amount+'人');
							break;
					}
				})
			}
		})
	}
//--------------------------------系统消息初始化-----------------------------------

	$.ajax({
		"type":"post",
		"datatype":"json",
		"url":"http://127.0.0.1/stuenroll/index/dynamic",
		"success":function(json){
			var bar = $(".message-list");
			var data = json.result;
			var temp = "";
			for (var i = 0; i < data.length&&i<3; i++) {
				temp+='<dt class="message-item">';
				temp+='<div class="img">';
				temp+='     <img src="../../img/matthew.png" />';
				temp+='</div>';
				temp+='<div class="text">';
				temp+='		<div class="text-title">';
				temp+='			<div class="left">'+data[i].writer+'</div>';
				temp+='			<div class="right">'+data[i].systime+'</div>';
				temp+='		</div>';
				temp+='		<div class="text-all">'+data[i].message+'</div>';
				temp+='</div>';
				temp+='</dt>';
			}
			bar.append(temp);			
		}
	})
//---------------------------------------------------------------------------------------------------
	/**
	 * 工厂方法
	 * 
	 * @param key
	 */
	function Factory(key) {
		if (key == "AnnualStatistics") {
			return new AnnualStatistics();
		}
	}

	// 申明变量
	var annualStatistics = Factory("AnnualStatistics");
	annualStatistics.init();
	annualStatistics.clock();
//--------------------初始化传参-------------------------------------
	var date = new Date();
	var year = date.getFullYear();
	var month= date.getMonth();
	annualStatistics.monthdata(year,2);
	
	$("#lastmonth").click(function(){
		var month = $("#month1").text()-1;
		if (month>0) {
			$("#month1").text(month);
			annualStatistics.monthdata(year,month);
		}
		else{
			toastr.warning("已经刷新至一月份");
		}
	})
	$("#nextmonth").click(function(){
		var month = $("#month1").text();
		month++;
		if (month<date.getMonth()+2) {
			$("#month1").text(month);
			annualStatistics.monthdata(year,month);
		}
		else{
			toastr.warning("已经刷新至最新月份");
		}
	})

});