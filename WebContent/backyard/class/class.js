$(function() {

	var $page = pageFactory();

	/**
	 * 1. 判断权限并开放相应面板和选项：根据用户机构和权限开放
	 */
	var bool = true;

	/**
	 * 显示面板
	 */
	if (sessionStorage.organization == "辽宁省就业网") {
		$(".main-container .tab-list .tab-item").addClass("tab-show");
	} else {
		$(".main-container .tab-list .tab-item[data-index='班级列表']").addClass("tab-show");
		$(".main-container .tab-list .tab-item[data-index='未归档']").addClass("tab-show");
		$(".main-container .tab-list .tab-item[data-index='已归档']").addClass("tab-show");
	}

	/**
	 * 根据权限显示选项
	 */
	if (checkPermission(["8_1"])) {
		$(".main-container .tab-container .tab-content .operation-list .operation-item[data-operation='add']").addClass(
			"operation-item-show");
	}

	if (checkPermission(["8_2"])) {
		$(".main-container .tab-container .tab-content .operation-list .operation-item[data-operation='delete']").addClass(
			"operation-item-show");
	}

	if (checkPermission(["8_3"])) {
		$(".main-container .tab-container .tab-content .operation-list .operation-item[data-operation='update']").addClass(
			"operation-item-show");
	}

	if (checkPermission(["8_4"])) {
		$(".main-container .tab-container .tab-content .operation-list .operation-item[data-operation='search']").addClass(
			"operation-item-show");
	} else {
		// TODO 隐藏全部
	}

	if (checkPermission(["8_10"])) {
		$(".main-container .tab-container .tab-content .operation-list .operation-item[data-operation='archive']").addClass(
			"operation-item-show");
	}

	/**
	 * 2. 接口和实现
	 */
	/** ************************************************班级信息管理接口*********************************** */
	var I_ClassInfo = function() {

	}

	/**
	 * 初始化抽象方法
	 * 
	 * @param {Object} json
	 * @param {Object} index 所属面板
	 */
	I_ClassInfo.prototype.init = function(json, index) {
		throw "抽象方法";
	}
	
	/**
	 * 查询所有班级
	 * @param {Object} json
	 */
	I_ClassInfo.prototype.searchAllClasses = function(json, index) {
		throw "抽象方法";
	}

	/**
	 * 根据条件搜索班级信息
	 * 
	 * @param {Object}
	 *            json 条件
	 * @param {Object}
	 *            index 所属面板
	 */
	I_ClassInfo.prototype.searchClassInfo = function(json, index) {
		throw "抽象方法";
	}

	/**
	 * 搜索符合条件的班级数量
	 * 
	 * @param {Object}
	 *            json 条件
	 * @param {Object}
	 *            index 所属面板
	 */
	I_ClassInfo.prototype.searchClassAmount = function(json, index) {
		throw "抽象方法";
	}

	/**
	 * 根据专业加载机构名称下拉列表
	 * 
	 * @param {Object}
	 *            json
	 * @param {Object}
	 *            obj 被点击的HTML对象
	 */
	I_ClassInfo.prototype.loadOrganizationsWithProfession = function(json, obj) {
		throw "抽象方法";
	}

	/**
	 * 根据机构加载机构关联的专业
	 * 
	 * @param {Object}
	 *            json
	 * @param {Object}
	 *            obj 被点击的HTML对象
	 */
	I_ClassInfo.prototype.loadProfessionsWithOrganization = function(json, obj) {
		throw "抽象方法";
	}

	/**
	 * 添加班级
	 * 
	 * @param {Object}
	 *            json 条件
	 * @param {Object}
	 *            index 所属面板
	 */
	I_ClassInfo.prototype.addClass = function(json, index) {
		throw "抽象方法";
	}

	/**
	 * 更新班级信息
	 * 
	 * @param {Object}
	 *            json 条件
	 * @param {Object}
	 *            index 所属面板
	 */
	I_ClassInfo.prototype.updateClassInfo = function(json, index) {
		throw "抽象方法";
	}

	/**
	 * 删除班级
	 * 
	 * @param {Object}
	 *            json 条件
	 * @param {Object}
	 *            index 所属面板
	 */
	I_ClassInfo.prototype.deleteClasses = function(json, index) {
		throw "抽象方法";
	}

	/**
	 * 班级归档
	 * 
	 * @param {Object}
	 *            json 条件
	 * @param {Object}
	 *            index 所属面板
	 */
	I_ClassInfo.prototype.archiveClasses = function(json, index) {
		throw "抽象方法";
	}

	/**
	 * 获得班级的人数
	 * 
	 * @param {Object}
	 *            json
	 * @param {Object}
	 *            index
	 */
	I_ClassInfo.prototype.getStudentsInClass = function(json, index) {
		throw "抽象方法";
	}

	/** *********************************************班级信息接口实现***************************************** */
	var ClassInfo = function() {}
	ClassInfo.prototype = new I_ClassInfo();

	ClassInfo.prototype.init = function(json, index) {
		this.searchClassAmount(json, index);
		this.searchClassInfo(json, index);
	}
	
	ClassInfo.prototype.searchAllClasses = function(json, index) {
		$.ajax({
				"url": "/stuenroll/classInfo/searchAllClasses",
				"type": "post",
				"async": false,
				"dataType": "json",
				"data": json,
				"success": function(json) {
					var content = $(".main-container .tab-container .tab-content[data-index='"+ index +"']");
					var temp = "";
					var data = json.result;
					for (var i = 0; i < data.length; i++) {
						var one = data[i];
						temp += "<li class=\"dropdown-item\" data-id=\"" + one.id + "\">" + one.name + "</li>";
					}
					// 1. 查找所有的查询面板里的班级名称下拉列表
					var dropdown = content
						.find(".operation-list .operation-item .menu .condition-list .condition .dropdown[data-dropdown='className']");
					// 2. 清除数据
					$(dropdown).find(".dropdown-list .dropdown-item").remove();
					// 3. 添加数据
					$(dropdown).find(".dropdown-list").append(temp);
					// 4. 初始化
					DropDown.init($(dropdown));
				},
				"error" : function() {
					toastr.error("系统异常");
				}
			});
	}

	ClassInfo.prototype.searchClassInfo = function(json, index) {
		if (!checkPermission(["8_4"])) {
			 toastr.error("没有相应权限");
			 return;
		}
		$.ajax({
			"url": "/stuenroll/classInfo/searchClassInfo",
			"type": "post",
			"async": false,
			"dataType": "json",
			"data": json,
			"success": function(json) {
				var data = json.result;
				var content = $(".tab-container .tab-content[data-index='" + index + "']");
				var table = content.find(".operation-list").siblings(".data-table");
				$(table).find("tr:gt(0)").remove();
				var pageList = table.siblings(".page-list");
				var currentPage = pageList.find(".page-info #currentPage").text();
				var start = (currentPage - 1) * 35;
				var temp = "";
				var rate = 0;
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					rate = new Number(one.rate);
					temp += "<tr>";
					temp += "<td><input type=\"checkbox\" name=\"id\" value=\"" + one.id + "\" /></td>";
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td>" + one.className + "</td>";
					temp += "<td>" + one.organizationName + "</td>";
					temp += "<td>" + one.professionName + "</td>";
					temp += "<td>" + one.year + "</td>";
					temp += "<td><span class=\"studentsAmount\">" + one.studentsAmount + "</span></td>";
					temp += "<td>" + one.classStateInfo + "</td>";
					// alert(one.statedStudentsAmount);
					temp += "<td>" + one.statedStudentsAmount + "</td>";
					temp += "<td>" + rate *100 + "%" + "</td>";
					temp += "</tr>";
				}
//				 console.log(temp);
				table.append(temp);
			},
			"error": function() {
				toastr.error("系统异常");
			}

		});
	}

	ClassInfo.prototype.loadOrganizationsWithProfession = function(json, obj) {
		var $condition = $(obj).find(".menu .condition-list .condition");
		$.ajax({
			"url": "/stuenroll/organization/searchOrganizations",
			"type": "post",
			"async": false,
			"dataType": "json",
			"data": json,
			"success": function(json) {
				var data = json.result;
				var $dropdown = $condition.find(".dropdown[data-dropdown='organization']");
				// 1. 清除下拉列表数据
				$dropdown.find(".dropdown-list .dropdown-item").remove();
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<li class=\"dropdown-item\" data-id=\"" + one.id + "\">" + one.name + "</li>";
				}
				// 2. 添加下拉列表数据
				$dropdown.find(".dropdown-list").append(temp);
				// 3. 初始化
				DropDown.init($dropdown);
				// 4. 专业下拉列表点击初始化事件
				$dropdown2 = $condition.find(".dropdown[data-dropdown='profession']");
				$dropdown.find(".dropdown-item").click(function() {
					$dropdown2.find(".dropdown-list .dropdown-item").remove();
					$dropdown2.find(".value").text("- 选择 -");
					$(this).siblings().remove("dropdown-item-active");
					$condition.find(".dropdown[data-dropdown='className'] .dropdown-list .dropdown-item").remove();
					$condition.find(".dropdown[data-dropdown='className'] .dropdown-list .value").text("- 选择 -");
					var organizationID = $(this).data("id");
					var json = {
							"organizationId" : organizationID
					};
					classInfo.loadProfessionsWithOrganization(json, $(this).parents(".operation-item"));
				});
				
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	ClassInfo.prototype.loadProfessionsWithOrganization = function(json, obj) {
		var $condition = $(obj).find(".menu .condition-list .condition");
		var organizationID = json.organizationId;
		$.ajax({
			"url": "/stuenroll/profession/searchProfessionsWithOrganization",
			"type": "post",
			"async": false,
			"dataType": "json",
			"data": json,
			"success": function(json) {
				var data = json.result;
				var $dropdown = $condition.find(".dropdown[data-dropdown='profession']");
				// 1. 清除下拉列表数据
				$dropdown.find(".dropdown-list .dropdown-item").remove();
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<li class=\"dropdown-item\" data-id=\"" + one.professionId + "\">" + one.name + "</li>";
				}
				// 2. 添加数据
				$dropdown.find(".dropdown-list").append(temp);
				// 3. 初始化下拉列表
				DropDown.init($dropdown);
				// 4. 班级下拉列表初始化事件
				var $dropdown2 = $condition.find(".dropdown[data-dropdown='className']");
				$dropdown.find(".dropdown-item").click(function() {
					$dropdown2.find(".dropdown-list .dropdown-item").remove();
					$dropdown2.find(".value").text("- 选择 -");
					$(this).siblings().remove("dropdown-item-active");
					var json = {
							"organizationId" : organizationID,
							"professionId": $(this).data("id")
						}
					var index = $(this).parents(".tab-content").data("index");
					classInfo.searchAllClasses(json, index);
				});
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	ClassInfo.prototype.searchClassAmount = function(json, index) {
		if (!checkPermission(["8_4"])) {
			toastr.error("您没有相应权限");
			return;
		}
		$.ajax({
			"url": "/stuenroll/classInfo/searchClassAmount",
			"type": "post",
			"async": false,
			"dataType": "json",
			"data": json,
			"success": function(json) {
				var count = json.result;
				var content = $(".tab-container .tab-content[data-index='" + index + "']");
				var pageList = content.find(".operation-list").siblings(".page-list");
				var totalRows = pageList.find(".page-info #totalRows");
				var totalPages = pageList.find(".page-info #totalPages");
				var prevBtn = pageList.find(".page-operation a[data-btn='prevBtn']");
				$page.showTotal(totalRows, count, 35, totalPages);
				var totalPage = new Number(totalPages.text());
				if (count == 0) {
					$page.insertPageNumberByMin(prevBtn, 1, 1);
				} else {
					if (totalPage < 5) {
						$page.insertPageNumberByMin(prevBtn, 1, totalPage);
					} else {
						$page.insertPageNumberByMin(prevBtn, 1, 5);
					}
				}
				var a = prevBtn.siblings();
				$(a[0]).addClass("page-active");
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	ClassInfo.prototype.addClass = function(json) {
		if (!checkPermission(["8_1"])) {
			toastr.error("您没有相应权限");
			return;
		}
		$.ajax({
			"url": "/stuenroll/classInfo/addClass",
			"type": "post",
			"async": false,
			"dataType": "json",
			"data": json,
			"success": function(json) {
				if (json.result == 1) {
					toastr.success("添加成功");
				} else {
					toastr.error("添加失败");
				}
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	ClassInfo.prototype.updateClassInfo = function(json) {
		if (!checkPermission(["8_3"])) {
			toastr.error("您没有相应权限");
			return;
		}
		$.ajax({
			"url": "/stuenroll/classInfo/updateClassInfo",
			"type": "post",
			"dataType": "json",
			"async": false,
			"data": json,
			"success": function(json) {
				if (json.result == 1) {
					toastr.success("修改成功");
				} else {
					toastr.error("修改失败");
				}
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	ClassInfo.prototype.deleteClasses = function(json) {
		if (!checkPermission(["8_2"])) {
			toastr.error("您没有相应权限");
			return;
		}
		$.ajax({
			"url": "/stuenroll/classInfo/deleteClasses",
			"type": "post",
			"traditional": true,
			"async": false,
			"dataType": "json",
			"data": json,
			"success": function(json) {
				toastr.success("成功删除了" + json.result + "条记录");
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	ClassInfo.prototype.archiveClasses = function(json, index) {
		if (!checkPermission(["8_10"])) {
			toastr.error("您没有相应权限");
			return;
		}
		$.ajax({
			"url": "/stuenroll/classInfo/archiveClasses",
			"type": "post",
			"traditional": true,
			"async": false,
			"dataType": "json",
			"data": json,
			"success": function(json) {
				toastr.success("成功归档了" + json.result + "个班级");
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}
	
	ClassInfo.prototype.getStudentsInClass = function(json, index) {
		if (index != "班级列表") {
			json.classState = index == "未归档" ? 1 : 2;
		}
		$.ajax({
			"url": "/stuenroll/classInfo/getStudentsInClass",
			"type": "post",
			"async": false,
			"dataType": "json",	
			"data": json,
			"success": function(json) {
				var dataTable = $(".tab-container .tab-content[data-index='"+ index +"'] .classMenu .data-table");
				dataTable.find("tr:gt(0)").remove();
				var pageList = dataTable.siblings(".page-list");
				var currentPage = pageList.find(".page-info #currentPage").text();
				var start = (currentPage - 1) * 10;
				var data = json.result;
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td>" + one.name + "</td>";
					temp += "<td>" + one.sex + "</td>";
					temp += "<td>" + one.tel + "</td>";
					temp += "<td>" + one.pid + "</td>";
					temp += "<td>" + "已" + one.state + "</td>";
					temp += "</tr>";
				}
				dataTable.append(temp);
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}
	
	ClassInfo.prototype.getStudentsAmountInClass = function(json, index) {
		if (index != "班级列表") {
			json.classState = index == "未归档" ? 1 : 2;
		}
		$.ajax({
			"url": "/stuenroll/classInfo/getStudentsAmountInClass",
			"type" : "post",
			"async" : false,
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				var count = json.result;
				var pageList = $(".tab-container .tab-content[data-index='"+ index +"'] .classMenu .page-list");
				$page.showTotal(pageList.find(".page-info #totalRows"), count, 10, pageList.find(".page-info #totalPages"));
				var prevBtn  = pageList.find(".page-operation a[data-btn='prevBtn']");
				var totalPage = new Number(pageList.find(".page-info #totalPages").text());
				if (count == 0) {
					$page.insertPageNumberByMin(prevBtn, 1, 1);
				} else {
					if (totalPage < 5) {
						$page.insertPageNumberByMin(prevBtn, 1, totalPage);
					} else {
						$page.insertPageNumberByMin(prevBtn, 1, 5);
					}
				}
				var a = prevBtn.siblings();
				$(a[0]).addClass("page-active");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/** **********************************************电话回访接口***************************************** */
	var I_CallBack = function() {}

	/**
	 * 初始化抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_CallBack.prototype.init = function(json) {
		throw "抽象方法";
	}
	
	/**
	 * 查找班级的回访信息
	 * 
	 * @param {Object}
	 *            json
	 */
	I_CallBack.prototype.searchClassReview = function(json) {
		throw "抽象方法";
	}
	
	/**
	 * 查找班级回访总记录
	 * 
	 * @param {Object}
	 *            json
	 */
	I_CallBack.prototype.searchClassReviewCount = function(json) {
		throw "抽象方法";
	}
	
	/**
	 * 随机选取一个班级的某位同学进行回访
	 */
	I_CallBack.prototype.chooseStudentForReview = function(json) {
		throw "抽象方法";
	}

	var CallBack = function() {}
	CallBack.prototype = new I_CallBack();
	
	CallBack.prototype.init = function(json) {
		this.searchClassReviewCount(json);
		this.searchClassReview(json);
	}
	
	CallBack.prototype.searchClassReview = function(json) {
		if (!checkPermission(["8_4"])) {
			toastr.error("您没有相应权限");
			return;
		}
		$.ajax({
			"url":"/stuenroll/classInfo/searchCallbackInfo",
			"type":"post",
			"async": false,
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				var data = json.result;
				var dataTable = $(".main-container .tab-container .tab-content[data-index='电话回访'] .data-table");
				dataTable.find("tr:gt(0)").remove();
				var pageList = dataTable.siblings(".page-list");
				var currentPage = pageList.find(".page-info #currentPage").text();
				var start = (currentPage - 1) * 35;
				var temp = "";	
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td><span name=\"classInfoId\" data-id=\""+ one.id +"\" >" + (start + i + 1) + "</span></td>";
					temp += "<td>" + one.className + "</td>";
					temp += "<td>" + one.organizationName + "</td>";
					temp += "<td>" + one.professionName + "</td>";
					temp += "<td>" + one.year + "</td>";
					temp += "<td>" + one.reviewCount + "</td>";
					temp += "<td>" + one.reviewRate*100 + "%" + "</td>";
					temp += "<td>" + one.qualifiedRate*100 + "%" + "</td>";
					temp += "<td><span name=\"details\">[&nbsp;详细&nbsp;]</span></td>";
					temp += "<td><span name=\"callback\">[&nbsp;电话回访&nbsp;]</span></td>";
					temp += "</tr>";
				}
				dataTable.append(temp);
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}
	
	CallBack.prototype.searchClassReviewCount = function(json) {
		if (!checkPermission(["8_4"])) {
			toastr.error("您没有相应权限");
			return;
		}
		$.ajax({
			"url":"/stuenroll/classInfo/searchCallbackCount",
			"type":"post",
			"async": false,
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				var count = json.result;
				var content = $(".tab-container .tab-content[data-index='电话回访']");
				var pageList = content.find(".operation-list").siblings(".page-list");
				var totalRows = pageList.find(".page-info #totalRows");
				var totalPages = pageList.find(".page-info #totalPages");
				var prevBtn = pageList.find(".page-operation a[data-btn='prevBtn']");
				$page.showTotal(totalRows, count, 35, totalPages);
				var totalPage = new Number(totalPages.text());
				if (count == 0) {
					$page.insertPageNumberByMin(prevBtn, 1, 1);
				} else {
					if (totalPage < 5) {
						$page.insertPageNumberByMin(prevBtn, 1, totalPage);
					} else {
						$page.insertPageNumberByMin(prevBtn, 1, 5);
					}
				}
				var a = prevBtn.siblings();
				$(a[0]).addClass("page-active");
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}
	
	CallBack.prototype.chooseStudentForReview = function(json) {
		$.ajax({
			"url":"/stuenroll/classInfo/chooseStudentInClassForReview",
			"type":"post",
			"async": false,
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				if (json.result == null) {
					toastr.warning("该班级没有学生");
					location.href = "class.html";
				} else {
					var data = json.result;
					sessionStorage.studentId = data.id;
					sessionStorage.studentName = data.name;
					sessionStorage.studentSex = data.sex;
					sessionStorage.studentBrthYear = data.year;
					sessionStorage.studentBrthMonth = data.month;
					sessionStorage.studentBrthDay = data.day;
					sessionStorage.studentPid = data.pid;
					sessionStorage.studentEmail = data.email;
					sessionStorage.studentTel = data.tel;
					sessionStorage.studentResidentAddress = data.resident_address;
					sessionStorage.studentHomeAddress = data.home_address;
					sessionStorage.studentPermanentAddress = data.permanent_address;
					sessionStorage.studentGraduateSchool = data.graduate_school;
					sessionStorage.studentMajor = data.major;
				}
				
			},
			"error": function() {
				toastr.error("系统异常");
				location.href = "class.html";
			}
		});
	}

	/** *******************************************工厂方法*************************************** */
	function ClassFactory(key) {
		if (key == "ClassInfo") {
			return new ClassInfo();
		} else if (key == "CallBack") {
			return new CallBack();
		}
	}

	/**
	 * 3. 初始化和变量声明
	 */
	var tab = TabFactory();
	var menus = MenuFactory("Menu");
	menus.init();
	var classMenus = MenuFactory("ClassMenu");
	classMenus.init();
	var $page = pageFactory();
	var classInfo = ClassFactory("ClassInfo");
	var classSearchJson = {};
	classSearchJson.orgainzationId = sessionStorage.orgainzationId;
	classSearchJson.stuState = 5;
	classSearchJson.username = sessionStorage.username;
	classInfo.searchClassAmount(classSearchJson, "班级列表");
	classInfo.searchClassInfo(classSearchJson, "班级列表");
	var callbackSearchJson = {};
	var callBack = ClassFactory("CallBack");

	/**
	 * 4. 事件
	 */
	/**
	 * 4.1 面板切换事件
	 */
	$(".main-container .tab-list .tab-item").click(function() {
		var temp = $(this).data("index");
		tab.showTab(temp);
		if (temp == "班级列表") {
			// 查询信息并更新显示
			// jsonInit(sessionStorage.orgainzationId, null, 5,
			// classSearchJson);
			classSearchJson = {};
			classSearchJson.organizationId = sessionStorage.organizationId;
			classSearchJson.stuState = 5;
			classSearchJson.username = sessionStorage.username;
			classInfo.init(classSearchJson, temp);
		} else if (temp == "未归档") {
			// 查询信息并更新列表
			// jsonInit(sessionStorage.orgainzationId, 1, 5, classSearchJson);
			classSearchJson = {};
			classSearchJson.organizationId = sessionStorage.organizationId;
			classSearchJson.stuState = 5;
			classSearchJson.classState = 1;
			classSearchJson.username = sessionStorage.username;
			classInfo.init(classSearchJson, temp);
		} else if (temp == "已归档") {
			// 查询信息并更新列表
			classSearchJson = {};
			classSearchJson.organizationId = sessionStorage.organizationId;
			classSearchJson.stuState = 5;
			classSearchJson.classState = 2;
			classSearchJson.username = sessionStorage.username;
			classInfo.init(classSearchJson, temp);
		} else if (temp == "电话回访") {
			// 查询信息并更新列表
			callbackSearchJson = {};
			callBack.searchClassReviewCount(callbackSearchJson);
			callBack.searchClassReview(callbackSearchJson);
		}
	});

	/**
	 * 4.2 面板填写验证事件
	 */
	// 输入框keyup事件
	$(".main-container .tab-container input[type='text']").keyup(function() {
		if (this.checkValidity()) {
			$(this).removeClass("error");
		} else {
			$(this).addClass("error");
		}
	});

	var operationList = $(".main-container .tab-container .tab-content .operation-list");
	var classOperationList = $(".main-container .tab-container .tab-content[data-index='班级列表'] .operation-list");
	
	/**
	 * 设置机构下拉列表
	 * @param {Object} $dropdown 需要设置的机构下拉列表对象
	 * @param {Object} callback 非就业网用户需要调用的回调函数
	 * @param {Object} handler 就业网用户需要调用的回调函数
	 */
	function dropdownOrganization($dropdown, callback, handler) {
		var $operationItem = $($dropdown).parents(".operation-item");
		var json = {};
		// 1. 就业网用户显示全部机构
		if (sessionStorage.organization == "辽宁省就业网") {
			handler(json, $operationItem);
		} else {
			// 2. 非就业网用户只显示本机构
			json.organizationId = sessionStorage.organizationId;
			json.organization = sessionStorage.organization;
			var temp = "<li class=\"dropdown-item dropdown-item-active\" data-id=\"" + sessionStorage.orgainzationId + "\">" + sessionStorage.organization + "</li>";
			$($dropdown).find(".dropdown-list .dropdown-item").remove();
			$($dropdown).find(".dropdown-list").append(temp);
			// 3. 将span设置为本机构
			$($dropdown).find(".value").text(sessionStorage.organization);
			// 4. 将下列列表设为不可点击事件
			$($dropdown).click(function() {
				toastr.warning("无法选择");
				return;
			});
			callback(json, $operationItem);
			DropDown.init($($dropdown));
		}
	}

	/**
	 * 面板清除按钮事件
	 */
	operationList.find(".operation-item .menu dd input[data-btn='清除']").click(
		function() {
			menus.clearData(this);
			dropdownOrganization($(this).parents(".menu").find(".condition-list .condition .dropdown[data-dropdown='organization']"), classInfo.loadProfessionsWithOrganization,classInfo.loadOrganizationsWithProfession);
		});

	/**
	 * 4.3 查询事件
	 */
	operationList.find(".operation-item[data-operation='search']").click(
		function(event) {
			var $condition = $(this).find(".menu .condition-list .condition");
			var index = $(this).parents(".tab-content").data("index");
			var json = {};
			menus.show(this, event);
			dropdownOrganization($(this).find(".menu .condition-list .condition .dropdown[data-dropdown='organization']"), classInfo.loadProfessionsWithOrganization,classInfo.loadOrganizationsWithProfession);
			// 初始化下拉列表：1）若是就业网用户，则查询所有机构及其关联的专业 2）若是某非就业网机构用户则只查询该机构关联的专业
//			if (sessionStorage.organization == "辽宁省就业网") {
//				// 1. 查询所有机构和专业以及班级名称
//				classInfo.loadOrganizationsWithProfession(json, this);
//			} else {
//				// 2. 非就业网用户设置json的organizationId以便区分查询
//				json.organizationId = sessionStorage.orgainzationId;
//				// 3. 设置查询面板里的机构名称选项为该机构并禁用点击选择
//				var $dropdownList = $condition.find(".dropdown[data-dropdown='organization'] .dropdown-list");
//				$dropdownList.find(".dropdown-item").remove();
//				var temp = "<li class=\"dropdown-item dropdown-item-active\" data-id=\"" + sessionStorage.orgainzationId + "\">" + sessionStorage.organization + "</li>";
//				$dropdownList.append(temp);
//				$dropdownList.find(".value").text(sessionStorage.organization);
//				classInfo.loadProfessionsWithOrganization(json, this);
//				$condition.find(".dropdown[data-dropdown='organization']").click(function() {
//					toastr.warning("无法选择");
//					return;
//				});
//				// 4. 初始化下拉列表
//				DropDown.init($dropdownList.parent());
//			}
		});

	/**
	 * 查询按钮事件
	 */
	operationList.find(".operation-item[data-operation='search'] .menu-btn[data-btn='查询']").click(function() {
		var menu = $(this).parents(".menu");
		var classId = menu.find(".condition .dropdown[data-dropdown='className'] .dropdown-list .dropdown-item-active").data("id");
		var year = menu.find(".condition input[name='year']").val();
		year = new Number(year);
		if (year == null || year <= 2000) {
			toastr.warning("必须正确填写年届");
			return;
		}
		var organizationId = menu.find(".dropdown[data-dropdown='organization'] .dropdown-list .dropdown-item-active").data("id");
		var professionId = menu.find(".dropdown[data-dropdown='profession'] .dropdown-list .dropdown-item-active").data("id");
		menus.hide();
		// 生成json
		classSearchJson.classInfoId = classId;
		classSearchJson.year = year;
		classSearchJson.organizationId = organizationId;
		classSearchJson.professionId = professionId;
		callbackSearchJson.classInfoId = classId;
		callbackSearchJson.year = year;
		callbackSearchJson.organizationId = organizationId;
		callbackSearchJson.professionId = professionId;
		// 判断是哪个面板的查询事件以分辨调用什么函数
		var index = $(this).parents(".tab-content").data("index");
		classSearchJson.page = $(this).parents(".operation-list").siblings(".page-list").find(".page-info #currentPage").text();
		callbackSearchJson.page =  $(this).parents(".operation-list").siblings(".page-list").find(".page-info #currentPage").text();
		if (index == "电话回访") {
			// 调用电话回访查询方法
			callBack.searchClassReviewCount(callbackSearchJson);
			callBack.searchClassReview(callbackSearchJson)
		} else {
			classInfo.searchClassAmount(classSearchJson, index);
			classInfo.searchClassInfo(classSearchJson, index);
		}
	});
	/**
	 * 4.4 添加事件
	 */
	classOperationList.find(".operation-item[data-operation='add']").click(function(event) {
		// 读取数据并初始化下拉列表
		var json = {};
		menus.show(this, event);
		if (sessionStorage.organization == "辽宁省就业网") {
			classInfo.loadOrganizationsWithProfession(json, this);
		} else {
			toastr.error("没有权限");
			return;
		}
	})

	/**
	 * 保存按钮事件
	 */
	classOperationList.find(".operation-item[data-operation='add'] .menu-btn[data-btn='保存']").click(function() {
		var menu = $(this).parents(".menu");
		var className = menu.find("input[name='className']").val();
		if (className == null || className == "") {
			toastr.warning("必须正确填写班级编号");
			return;
		}
		var year = menu.find("input[name='year']").val();
		year = new Number(year);
		if (year == null || year <= 2000) {
			toastr.warning("必须正确填写年届");
			return;
		}
		var organizationId = menu.find(".dropdown[data-dropdown='organization'] .dropdown-list .dropdown-item-active").data("id");
		var professionId = menu.find(".dropdown[data-dropdown='profession'] .dropdown-list .dropdown-item-active").data("id");
		menus.hide();
		// 准备JSON数据
		var json = {};
		json.organizationId = organizationId;
		json.className = className;
		json.year = year;
		json.professionId = professionId;
		json.username = sessionStorage.username;
		// 执行添加
		classInfo.addClass(json, "班级列表");
		// 根据原来条件重新查询数据并更新
		classSearchJson.page = classOperationList.siblings(".page-list").find(".page-info #currentPage").text();
		setTimeout(function() {
			classInfo.searchClassAmount(classSearchJson);
			classInfo.searchClassInfo(classSearchJson);
		}, 3000);
	});

	/**
	 * 4.5 修改事件
	 */
	var classId = 0;
	classOperationList.find(".operation-item[data-operation='update']").click(function(event) {
		var checkbox = $(this).parent().siblings(".data-table").find("*[name='id']:checked");
		classId = checkbox.val();
		if (checkbox.length <= 0) {
			toastr.warning("请选中一条记录");
			return;
		}
		if (checkbox.length > 1) {
			toastr.warning("选中记录多于一条");
			return;
		}
		// 从选中记录中读取数据
		var td = checkbox.parent().siblings();
		var menu = $(this).find(".menu");
		menus.show(this, event);
		menu.find(".condition-list .condition input[name='className']").val($(td[1]).text());
		menu.find(".condition-list .condition input[name='year']").val($(td[4]).text());
		// 读取数据并初始化下拉列表
		var json = {
				"organizationId" : sessionStorage.organizationId
		}
		classInfo.loadOrganizationsWithProfession(json, this);
		
		// 设置dropdown-item-active
		var organizationDropdown = menu.find(".condition-list .condition .dropdown[data-dropdown='organization']");
		var li = organizationDropdown.find("ul li");
		for (var i = 0; i < li.length; i++) {
			if ($(li[i]).text() == $(td[2]).text()) {
				$(li[i]).addClass("dropdown-item-active");
				$(organizationDropdown).find(".value").text($(td[2]).text());
				json.organizationId = $(li[i]).data("id");
				classInfo.loadProfessionsWithOrganization(json, this);
			}
		}
		var professionDropdown = menu.find(".condition-list .condition .dropdown[data-dropdown='profession']");
		li = professionDropdown.find(".dropdown-item");
		li.removeClass("dropdown-item-active");
		for (var i = 0; i < li.length; i++) {
			if ($(li[i]).text() == $(td[3]).text()) {
				$(professionDropdown).find(".value").text($(td[3]).text());
				$(li[i]).addClass("dropdown-item-active");
				console.log($(li[i]));
			}
		}
	});

	/**
	 * 更新面板保存方法
	 */
	classOperationList.find(".operation-item[data-operation='update'] .menu-btn[data-btn='保存']").click(function() {
		var menu = $(this).parents(".menu");
		var className = menu.find(".keyword[name='className']").val();
		var year = menu.find(".keyword[name='year']").val();
		var organizationId = menu.find(".dropdown[data-dropdown='organization'] .dropdown-list .dropdown-item-active").data("id");
		var professionId = menu.find(".dropdown[data-dropdown='profession'] .dropdown-list .dropdown-item-active").data("id");
		menus.hide();
		// 准备JSON数据
		var json = {};
		json.classId = classId;
		json.className = className;
		json.year = year;
		json.organizationId = organizationId;
		json.professionId = professionId;
		json.username = sessionStorage.username;
		console.log(json);
		// 执行修改
		classInfo.updateClassInfo(json, "班级列表");
		// 重新查询数据并更新
		classSearchJson.page = classOperationList.siblings(".page-list").find(".page-info #currentPage").text();
		classInfo.searchClassInfo(classSearchJson, "班级列表");
	});

	/**
	 * 4.6 删除事件
	 */
	classOperationList.find(".operation-item[data-operation='delete']").click(function(event) {
		var checkbox = $(this).parents(".tab-content").find(".data-table *[name='id']:checked");
		if (checkbox.length <= 0) {
			toastr.warning("请至少选中一条记录");
			return;
		}
		var bool = confirm("是否删除选中的记录？");
		if (bool == false) {
			return;
		}
		var id = [];
		var td = "";
		for (var i = 0; i < checkbox.length; i++) {
			var one = checkbox[i];
			td = $(one).parent().siblings();
			if ($(td[5]).text() != "0") {
				toastr.warning("选中记录中有班级已关联学生，无法删除！请重新选择记录");
				return;
			}
			id.push($(one).val());
		}
		// 准备JSON数据
		var json = {
			"id": id,
			"username": sessionStorage.username
		};
		console.log(json.id[0]);
		// 执行删除操作
		classInfo.deleteClasses(json);
		// 重新查询数据并更新
		var currentPage = new Number(classOperationList.siblings(".page-list").find(".page-info #currentPage").text());
		var totalPages = new Number(classOperationList.siblings(".page-list").find(".page-info #totalPages").text());
		if (currentPage > totalPages) {
			currentPage = totalPages;
		}
		classOperationList.siblings(".page-list").find(".page-info #currentPage").text(currentPage);
		classSearchJson.page = currentPage;
		classInfo.searchClassInfo(classSearchJson, "班级列表");
	});

	/**
	 * 4.7 班级归档事件
	 */
	operationList.find(".operation-item[data-operation='archive']").click(function(event) {
		var checkbox = $(this).parents(".tab-content").find(".data-table *[name='id']:checked");
		if (checkbox.length <= 0) {
			toastr.warning("请至少选择一条记录");
			return;
		}
		// 弹出确认对话框
		var bool = confirm("是否归档选中的记录？");
		if (bool == false) {
			return;
		}
		var id = [];
		var td = "";
		for (var i = 0; i < checkbox.length; i++) {
			var one = checkbox[i];
			td = $(one).parent().siblings();
			if ($(td[6]).text() == "已归档") {
				toastr.warning("选中记录中包含已归档班级，归档失败！请重新选择");
				return;
			}
			id.push($(one).val());
		}
		// 准备JSON数据
		var json = {
			"id": id,
			"username": sessionStorage.username
		};
		// 调用函数进行归档
		var index = $(this).parents(".tab-content").data("index");
		classInfo.archiveClasses(json, index);
		// 重新查询数据并更新页面
		classInfo.searchClassAmount(classSearchJson, index);
		classInfo.searchClassInfo(classSearchJson, index);
	});


	var classStudentsSearchJson = {};
	/**
	 * 5. 班级人数面板事件
	 */
	operationList.parent().on("click", ".data-table tr .studentsAmount", function(event) {
		if ($(this).text() == 0) {
			toastr.warning("该班级没有人数");
			return;
		}
		// 清除面板数据
		classMenus.clearData();
		
		var td = $(this).parent().siblings();
		classStudentsSearchJson.classId = $(td[0]).find("input[name='id']").val();

		// 初始化面板数据
		var index = $(this).parents(".tab-content").data("index");
		// 显示面板
		classMenus.show(operationList.siblings(".classMenu"), event);
		if (index == "班级列表") {
			classStudentsSearchJson.classState = $(td[6]).text() == "未归档" ? 1 : 2;
		}
		classInfo.getStudentsAmountInClass(classStudentsSearchJson, index);
		classInfo.getStudentsInClass(classStudentsSearchJson, index);
		
	});
	
	/**
	 * 关闭面板事件
	 */
	operationList.parent().find(".classMenu .students .menu-btn[data-btn='关闭']").click(function() {
		classMenus.hide();
	});

	/**
	 * 全选事件
	 */
	operationList.parent().find(".data-table tr th input[name='select-all']").click(function(event) {
		var bool = $(this).is(":checked");
		if (bool) {
			// TODO 将所有其他的checkbox选中
			// operationList.parent().find(".data-table
			// *[name='id']").attr("checked", "checked");
			$(this).parents("tr").siblings().find("td input[name='id']").attr("checked", "checked");
			operationList.parent().on("", ".data-table *[name='id']", function() {
				$(this).attr("checked", "checked");
			});
		} else {
			// TODO 将所有其他的checkbox不选中
			// operationList.parent().find(".data-table
			// *[name='id']").removeAttr("checked");
			$(this).parents("tr").siblings().find("td input[name='id']").removeAttr("checked");
			operationList.parent().on("", ".data-table *[name='id']", function() {
				$(this).removeAttr("checked");
			});
		}
	});

	/**
	 * 所有页面的上一页翻页事件
	 */
	operationList.siblings(".page-list").find(".page-operation a[data-btn='prevBtn']").click(function() {
		var index = $(this).parents(".tab-content").data("index");
		var $currentPage = $(this).parents(".page-list").find(".page-info #currentPage");
		$page.previousPage($currentPage, this, 5, classSearchJson, index, classInfo.searchClassInfo);
	});

	/**
	 * 所有页面的下一页翻页事件
	 */
	operationList.siblings(".page-list").find(".page-operation a[data-btn='nextBtn']").click(function() {
		var index = $(this).parents(".tab-content").data("index");
		var pageInfo = $(this).parent().siblings(".page-info");
		$page.nextPage(this, 5, pageInfo.find("#currentPage"), pageInfo.find("#totalPages"), classSearchJson, index, classInfo.searchClassInfo);
	});

	/**
	 * 所有页面点击页数切换页面事件
	 */
	operationList.siblings(".page-list").find(".page-operation").on("click", "a[name='page-number']", function() {
		var index = $(this).parents(".tab-content").data("index");
		var $currentPage = $(this).parents(".page-list").find(".page-info #currentPage");
		$page.changePage(this, $currentPage, classSearchJson, index, classInfo.searchClassInfo);
	});

	/**
	 * 班级人数面板上一页翻页事件
	 */
	operationList.siblings(".classMenu").find(".page-list .page-operation a[data-btn='prevBtn']").click(function() {
		var $currentPage = $(this).parents(".page-list").find(".page-info #currentPage");
		var index = $(this).parents(".tab-content").data("index");
		$page.previousPage($currentPage, this, 5, classStudentsSearchJson, index, classInfo.getStudentsInClass);
	});

	/**
	 * 班级人数面板下一页翻页事件
	 */
	operationList.siblings(".classMenu").find(".page-list .page-operation a[data-btn='nextBtn']").click(function() {
		var pageInfo = $(this).parents(".page-list").find(".page-info");
		var index = $(this).parents(".tab-content").data("index");
		$page.nextPage(this, 5, pageInfo.find("#currentPage"), pageInfo.find("#totalPages"), classStudentsSearchJson, index, classInfo.getStudentsInClass);
	});
	/**
	 * 班级人数面板点击页数翻页
	 */
	operationList.siblings(".classMenu").on("click", ".page-list .page-operation a[name='page-number']", function() {
		var $currentPage = $(this).parents(".page-list").find(".page-info #currentPage");
		var index = $(this).parents(".tab-content").data("index");
		$page.changePage(this, $currentPage, classStudentsSearchJson, index, classInfo.getStudentsInClass);
	});
	
	/**
	 * 回访详细信息页面
	 */
	operationList.siblings(".data-table").on("click", "tr td span[name='details']", function() {
		var td = $(this).parent().siblings();
		sessionStorage.classInfoId = $(td[0]).find("span").data("id");
		sessionStorage.className = $(td[1]).text();
		sessionStorage.classOrganizationName = $(td[2]).text();
		sessionStorage.locations = "../class.html";
		location.href = "enquire/list.html";
	});
	
	/**
	 * 电话回访页面
	 */
	operationList.siblings(".data-table").on("click", "tr td span[name='callback']", function() {
		var td = $(this).parent().siblings();
		sessionStorage.classInfoId = $(td[0]).find("span").data("id");
		callbackSearchJson.classInfoId = sessionStorage.classInfoId;
		callBack.chooseStudentForReview(callbackSearchJson);
		sessionStorage.className = $(td[1]).text();
		sessionStorage.classOrganizationName = $(td[2]).text();
		sessionStorage.bool = false;
		sessionStorage.locations = "../class.html";
		location.href = "enquire/record.html";
	});

});