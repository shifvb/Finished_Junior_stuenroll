$(function() {

	require.config({
		paths: {
			echarts: '../../../js/echarts'
		}
	});

	// 接口与实现
	I_RecordDetail = function() {}

	/**
	 * 初始化抽象方法
	 * 
	 * @param {Object}
	 *            json
	 * @param {Object}
	 *            bool
	 */
	I_RecordDetail.prototype.init = function(json, bool) {
		throw "抽象方法";
	}

	/**
	 * 加载学生信息抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_RecordDetail.loadStudentInfo = function(json) {
		throw "抽象方法";
	}

	/**
	 * 加载学生头像抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_RecordDetail.loadStudentImg = function(json) {
		throw "抽象方法";
	}

	/**
	 * 加载回访详细信息抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_RecordDetail.prototype.loadRecordDetails = function(json) {
		throw "抽象方法";
	}

	/**
	 * 加载录音文件
	 * 
	 * @param {Object}
	 *            json
	 */
	I_RecordDetail.prototype.loadRecord = function(json) {
		throw "抽象方法";
	}

	/**
	 * 加载学生出勤信息并绘制出勤/请假环形图以及月历
	 * 
	 * @param {Object}
	 *            json
	 */
	I_RecordDetail.prototype.loadStudentAttendance = function(json) {
		throw "抽象方法";
	}

	/**
	 * 保存回访信息抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_RecordDetail.prototype.saveInfo = function(json) {
		throw "抽象方法";
	}

	/** ****************************实现************************************ */
	var RecordDetail = function() {}
	RecordDetail.prototype = new I_RecordDetail();

	/**
	 * 初始化抽象方法
	 * 
	 * @param {Object}
	 *            json
	 * @param {Object}
	 *            bool
	 */
	RecordDetail.prototype.init = function(json, bool) {
		this.loadStudentInfo(json);
		this.loadStudentImg(json);
		this.loadStudentAttendance(json);
		this.loadStudentScore(json);
		if (bool == "true") {
			this.loadRecordDetails(json);
			this.loadRecord(json);
		}
	}

	/**
	 * 加载学生信息抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	RecordDetail.prototype.loadStudentInfo = function(json) {
		var details = $(".main-container .review-container .info-container[data-index='studentInfo'] .student .details");
		var student = $(".main-container .charts-container .phone-container .student");
		if (bool == "true") {
			$.ajax({
				"url": "/stuenroll/reviewDetails/searchReviewStudent",
				"type": "post",
				"dataType": "json",
				"async": false,
				"data": {
					"reviewId": sessionStorage.reviewId
				},
				"success": function(json) {
					var data = json.result;
					if (data == null) {
						toastr.error("无此人信息");
					}
					details.find("input[name='name']").val(data.name);
					details.find("input[data-sex='" + data.sex + "']").attr("checked", "checked");
					details.find("input[name='year']").val(data.year);
					details.find("input[name='month']").val(data.month);
					details.find("input[name='day']").val(data.day);
					details.find("input[name='pid']").val(data.pid);
					details.find("input[name='email']").val(data.email);
					details.find("input[name='tel']").val(data.tel);
					details.find("input[name='residentAddress']").val(data.resident_address);
					details.find("input[name='homeAddress']").val(data.home_address);
					details.find("input[name='permanentAddress']").val(data.permanent_address);
					details.find("input[name='graduateSchool']").val(data.graduate_school);
					details.find("input[name='major']").val(data.major);
					student.find(".info span[name='name']").text(data.name);
					student.find(".info span[name='sex']").text(data.sex);
					var year = new Number(data.year);
					year = new Number(new Date().getFullYear() - year + 1);
					student.find(".info span[name='age']").text(year);
					student.find(".tel span").text(data.tel);
				},
				"error": function() {
					toastr.error("系统异常");
				}
			});
		} else {
			details.find("input[name='name']").val(sessionStorage.studentName);
			details.find("input[data-sex='" + sessionStorage.studentSex + "']").attr("checked", "checked");
			details.find("input[name='year']").val(sessionStorage.studentBrthYear);
			details.find("input[name='month']").val(sessionStorage.studentBrthMonth);
			details.find("input[name='day']").val(sessionStorage.studentBrthDay);
			details.find("input[name='pid']").val(sessionStorage.studentPid);
			details.find("input[name='email']").val(sessionStorage.studentEmail);
			details.find("input[name='tel']").val(sessionStorage.studentTel);
			details.find("input[name='residentAddress']").val(sessionStorage.studentResidentAddress);
			details.find("input[name='homeAddress']").val(sessionStorage.studentHomeAddress);
			details.find("input[name='permanentAddress']").val(sessionStorage.studentPermanentAddress);
			details.find("input[name='graduateSchool']").val(sessionStorage.studentGraduateSchool);
			details.find("input[name='major']").val(sessionStorage.studentMajor);
			student.find(".info span[name='name']").text(sessionStorage.studentName);
			student.find(".info span[name='sex']").text(sessionStorage.studentSex);
			var year = new Number(sessionStorage.studentBrthYear);
			year = new Number(new Date().getFullYear() - year + 1);
			student.find(".info span[name='age']").text(year);
			student.find(".tel span").text(sessionStorage.studentTel);
		}
	}

	/**
	 * 加载学生头像抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	RecordDetail.prototype.loadStudentImg = function(json) {
		$.ajax({
			"url": "/stuenroll/reviewDetails/searchStudentImage",
			"type": "post",
			"dataType": "json",
			"data": json,
			"success": function(json) {
				var student = $(".main-container .charts-container .phone-container .student");
				if (json == null || json.result == null) {
					student.find(".photo img").attr("src", "../../../img/5.png");
				} else {
					student.find(".photo img").attr("src", "http://127.0.0.1/stuenroll/img/searchImage?id=" + json.result);
				}

			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 加载回访详细信息方法
	 * 
	 * @param {Object}
	 *            json
	 */
	RecordDetail.prototype.loadRecordDetails = function(json) {
		$.ajax({
			"url": "/stuenroll/reviewDetails/searchReviewDetails",
			"type": "post",
			"dataType": "json",
			"async" : false,
			"data": json,
			"success": function(json) {
				var data = json.result;
				if (data == null) {
					toastr.warning("无回访详细信息");
					return;
				}
				var details = $(".main-container .review-container .info-container[data-index='reviewInfo'] .questions");
				var answer = details.find(".answer");
				$(answer[0]).find("input[name='" + data.student_info + "']").attr("checked", "checked");
				$(answer[1]).find("input[name='" + data.conversion_trianing + "']").attr("checked", "checked");
				$(answer[2]).find("input[name='" + data.conversion_organization + "']").attr("checked", "checked");
				$(answer[3]).find("input[name='" + data.conversion_teaching + "']").attr("checked", "checked");
				$(answer[4]).find("input[name='" + data.daliy_management + "']").attr("checked", "checked");
				$(answer[5]).find("input[name='" + data.profession_setting + "']").attr("checked", "checked");
				$(answer[6]).find("input[name='" + data.learning_cycle + "']").attr("checked", "checked");
				$(answer[7]).find("textarea[name='changes']").text(data.organization_changes);
				$(answer[8]).find("input[name='" + data.employed + "']").attr("checked", "checked");
				$(answer[9]).find("input[name='" + data.company_size + "']").attr("checked", "checked");
				$(answer[10]).find("input[name='" + data.company_nature + "']").attr("checked", "checked");
				$(answer[11]).find("input[name='" + data.salary_level + "']").attr("checked", "checked");
				$(answer[12]).find("input[name='" + data.profession_counterparts + "']").attr("checked", "checked");
				$(answer[13]).find("input[name='" + data.job_satisfaction + "']").attr("checked", "checked");
				$(answer[14]).find("input[name='" + data.employed_way + "']").attr("checked", "checked");
				$(answer[15]).find("input[name='" + data.employed_satisfaction + "']").attr("checked", "checked");
				$(answer[16]).find("textarea[name='jobAdvice']").text(data.job_advice);
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 加载录音文件
	 * 
	 * @param {Object}
	 *            json
	 */
	RecordDetail.prototype.loadRecord = function(json) {
		$.ajax({
			"url": "",
			"type": "post",
			"dataType": "json",
			"data": json,
			"success": function(json) {

			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 加载学生出勤信息并绘制出勤/请假环形图以及月历
	 * 
	 * @param {Object}
	 *            json
	 */
	RecordDetail.prototype.loadStudentAttendance = function(json) {
		$.ajax({
			"url": "/stuenroll/reviewDetails/searchStudentAttendence",
			"type": "post",
			"dataType": "json",
			"data": json,
			"success": function(json) {

			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 加载学生考试成绩数据并绘制考试成绩折线图和考试成绩列表
	 * 
	 * @param {Object}
	 *            json
	 */
	RecordDetail.prototype.loadStudentScore = function(json) {
		$.ajax({
			"url": "/stuenroll/reviewDetails/searchStudentScore",
			"type": "post",
			"dataType": "json",
			"data": json,
			"success": function(json) {

			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 保存回访信息
	 * 
	 * @param {Object}
	 *            json
	 */
	RecordDetail.prototype.saveInfo = function(json) {
		$.ajax({
			"url": "/stuenroll/reviewDetails/addReviewRecord",
			"type": "post",
			"dataType": "json",
			"data": json,
			"success": function(json) {
				location.href = sessionStorage.locations;
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	/** ****************************实现结束************************************ */

	/** ****************************工厂方法*********************************** */
	function Factory(key) {
		if (key == "RecordDetail") {
			return new RecordDetail();
		}
	}
	/** ***************************工厂方法结束********************************** */

	var recordDetail = Factory("RecordDetail");
	var recordJson = {};
	// 1. 读取sessionStorage数据并根据具体情况加载数据
	var bool = sessionStorage.bool;
	recordJson.bool = bool;
	// 1.1 加载被访问学生信息
	if (bool == "true") {
		recordJson.reviewId = sessionStorage.reviewId;
		// 1.2 禁用所有input
		$("input").attr("disabled", "true");
		$("textarea").attr("disabled", "true");
	} else {
		recordJson.studentId = sessionStorage.studentId;
	}
	recordDetail.init(recordJson, bool);

	require(
		[
			'echarts',
			'echarts/chart/pie'
		],
		function(ec) {
			var myChart = ec.init(document.getElementById('attendence'), 'macarons');
			var option = {
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c} ({d}%)"
				},
				legend: {
					data: ['出勤', '缺勤']
				},
				toolbox: {
					show: true,
					feature: {
						magicType: {
							show: true,
							type: ['pie']
						},
						saveAsImage: {
							show: true
						}
					}
				},
				calculable: true,
				series: [{
					name: '出勤比例',
					type: 'pie',
					radius: ['50%', '70%'],
					itemStyle: {
						normal: {
							label: {
								show: false
							},
							labelLine: {
								show: false
							}
						},
						emphasis: {
							label: {
								show: true,
								position: 'center',
								textStyle: {
									fontSize: '30',
									fontWeight: 'bold'
								}
							}
						}
					},
					data: [{
							value: 600,
							name: '出勤'
						}, {
							value: 100,
							name: '缺勤'
						},

					]
				}]
			};
			myChart.setOption(option);
		}
	);

	require(
		[
			'echarts',
			'echarts/chart/line'
		],
		function(ec) {
			var myChart = ec.init(document.getElementById('score'), 'macarons');
			var option = {
				title: {
					text: '学生成绩走线',
				},
				tooltip: {
					trigger: 'axis'
				},
				legend: {
					data: ['成绩']
				},
				toolbox: {
					show: false,
					feature: {
						mark: {
							show: true
						},
						magicType: {
							show: true,
							type: ['line']
						},

					}
				},
				calculable: true,
				xAxis: [{
					type: 'category',
					boundaryGap: false,
					data: ['5/12', '6/15', '7/09', '8/11', '9/17', '10/05', '11/18']
				}],
				yAxis: [{
					type: 'value',
					axisLabel: {
						formatter: '{value} '
					},
					min: 70,
					max: 100
				}],
				series: [{
					name: '成绩',
					type: 'line',
					data: [85, 80, 78, 89, 86, 82, 95],
					markPoint: {
						data: [{
							type: 'max',
							name: '最大值'
						}, {
							type: 'min',
							name: '最小值'
						}]
					},
					markLine: {
						data: [{
							type: 'average',
							name: '平均值'
						}]
					}
				}]
			};
			myChart.setOption(option);
		}
	);

	// TODO 2. 使用echarts加载图例
	//	recordDetail.loadStudentAttendance(recordJson);
	//	recordDetail.loadStudentScore(recordJson);
	// TODO 3. 设置定时器并判断是否打通电话
	var bools = false;
	if (bool == "false") {
		var timer = setTimeout(function() {
			var details = $(".main-container .review-container .info-container[data-index='reviewInfo'] .questions");
			var checkbox = details.find(".answer input[type='checkbox']");
			for (var i = 0; i < checkbox.length; i++) {
				bools = bools || $(checkbox[i]).is(":checked");
			}
			var textarea = details.find(".answer textarea");
			for (var i = 0; i < textarea.length; i++) {
				bools = bools || ($(textarea[i]).text() != '');
			}
			if (!bools) {
				var json = {};
				var end = new Date().getSeconds();
				json.reviewee = $(".main-container .review-container .info-container .details .content input[name='name']").val();
				json.time = end - start;
				json.reviewer = sessionStorage.studentName;
				json.content = "测试内容";
				json.classInfoId = sessionStorage.classInfoId;
				json.studentId = sessionStorage.studentId;
				json.reviewResult = 1;
				json.satisfaction = "不满意";
				json.username = sessionStorage.username;
				recordDetail.saveInfo(json);
				location.href = sessionStorage.locations;
			}
			clearTimeout(timer);
		}, 30000);
	}
	// TODO 4. 跳转到当前页面即开始拨打电话
	var callBtn = $(".main-container .charts-container .phone-container .call .callBtn");
	callBtn.find("span[name='拨打']").removeClass("item-active");
	callBtn.find("span[name='挂断']").addClass("item-active");
	var start = new Date().getSeconds();

	// TODO 5. 挂断电话即保存此次回访信息：若打通，则保存回访记录和回访内容；若不打通，则只保存回访记录
	$(".main-container .charts-container .phone-container .call .callBtn span").click(function() {
		$(this).removeClass("item-active");
		$(this).siblings().addClass("item-active");
		var end = new Date().getSeconds();
		if (sessionStorage.bool == "true") {
			return;
		}
		// 1. 读取回访记录详细信息
		var date = new Date();
		var json = {};
		if (bools) {
			var details = $(".main-container .review-container .info-container[data-index='reviewInfo'] .questions");
			var answer = details.find("answer");
			var checkbox = answer.find("input[type='checkbox']");
			var q = [];
			var j = 0;
			for (var i = 0; i < checkbox.length; i++) {
				if ($(checkbox[i]).is(":checked")) {
					q[j] = $(checkbox[i]).val();
					j = j + 1;
				}
			}
			q[j] = $(answer[8]).find("textarea[name='changes']").text();
			j = j + 1;
			q[j] = $(answer[18]).find("textarea[name='jobAdvice']").text();
			json.answer = q;
		}
		// 2. 保存回访记录
		json.reviewee = $(this).parents(".phone-container").find(".student .info span[name='name']").text();
		json.time = end - start;
		json.reviewer = sessionStorage.name;
		json.content = "测试内容";
		json.classInfoId = sessionStorage.classInfoId;
		json.studentId = sessionStorage.studentId;
		json.reviewResult = bools == false ? 1 : 0;
		json.satisfaction = "满意";
		recordDetail.saveInfo(json);
		location.href = sessionStorage.locations;
	});

	$(".main-container .review-container .info-container[data-index='reviewInfo'] .questions .answer input[type='checkbox']").click(
		function() {
			$(this).siblings("input[type='checkbox']").removeAttr("checked");
		});

});