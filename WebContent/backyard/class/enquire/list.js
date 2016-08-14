$(function() {

	// 1.根据权限显示相应选项
	$(".main-container .title .organization").text(sessionStorage.classOrganizationName);
	$(".main-container .title .className").text(sessionStorage.className);

	// TODO 2. 接口与实现
	/** *********************************回访记录接口********************************** */
	var I_CallBackDetail = function() {
	}

	/**
	 * 初始化抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_CallBackDetail.prototype.init = function(json) {
		throw "抽象方法";
	}

	/**
	 * 查询回访记录详细信息抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_CallBackDetail.prototype.searchCallbackDetails = function(json) {
		throw "抽象方法";
	}

	/**
	 * 查询回访记录数抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_CallBackDetail.prototype.searchCallbackAmount = function(json) {
		throw "抽象方法";
	}

	/**
	 * 删除回访记录抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_CallBackDetail.prototype.deleteCallback = function(json) {
		throw "抽象方法";
	}

	/** *********************************回访记录实现********************************** */
	var CallBackDetail = function() {
	}

	CallBackDetail.prototype = new I_CallBackDetail();

	/**
	 * 
	 * @param {Object}
	 *            json
	 */
	CallBackDetail.prototype.init = function(json) {
		this.searchCallbackAmount(json);
		this.searchCallbackDetails(json);
	}

	CallBackDetail.prototype.searchCallbackDetails = function(json) {
		// TODO checkPermission
		$.ajax({
			"url" : "/stuenroll/review/searchReviewInfo",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				var data = json.result;
				var dataTable = $(".main-container .tab-container .tab-content .data-table");
				dataTable.find("tr:gt(0)").remove();
				var pageList = dataTable.siblings(".page-list");
				var currentPage = new Number(pageList.find(".page-info #currentPage").text());
				var start = (currentPage - 1) * 35;
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td><input type=\"checkbox\" name=\"id\" value=\"" + one.id + "\" /></td>";
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td>" + one.reviewee + "</td>";
					temp += "<td>" + one.time + "</td>";
					temp += "<td>" + one.duration + "</td>";
					temp += "<td>" + one.reviewer + "</td>";
					temp += "<td>" + one.satisfaction + "</td>";
					temp += "<td>" + one.content + "</td>";
					temp += "<td><span class=\"details\">[&nbsp;详细记录&nbsp;]</span></td>"
					temp += "</tr>";
				}
				dataTable.append(temp);
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	CallBackDetail.prototype.searchCallbackAmount = function(json) {
		// TODO checkPermission
		$.ajax({
			"url" : "/stuenroll/review/searchReviewCounts",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				var count = json.result;
				var pageList = $(".main-container .tab-container .tab-content .page-list");
				var prevBtn = $(pageList).find(".page-operation a[data-btn='prevBtn']");
				var totalRows = pageList.find(".page-info #totalRows");
				var totalPages = pageList.find(".page-info #totalPages");
				$page.showTotal(totalRows, count, 35, totalPages);
				var totalPage = new Number(totalPages.text());
				if (count == 0) {
					$page.insertPageNumberByMin(prevBtn, 1, 1);
				}
				else {
					if (totalPage < 5) {
						$page.insertPageNumberByMin(prevBtn, 1, totalPage);
					}
					else {
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

	CallBackDetail.prototype.deleteCallback = function(json) {
		// TODO checkPermission
		$.ajax({
			"url" : "/stuenroll/review/deleteReviewInfo",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true,
			"async" : false,
			"data" : json,
			"success" : function(json) {
				toastr.success("成功删除了" + json.result + "条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	// 工厂方法
	function Factory(key) {
		if (key == "CallBackDetail") {
			return new CallBackDetail();
		}
	}

	// 3. 变量声明和初始化
	DropDown.initAll();
	var menus = MenuFactory("Menu");
	menus.init();
	var $page = pageFactory();
	var callBackDetail = Factory("CallBackDetail");
	var callbackSearchJson = {};
	callbackSearchJson.classId = sessionStorage.classInfoId;
	callBackDetail.init(callbackSearchJson);

	// 4. 点击事件

	// 输入框keyup事件
	$(".main-container .tab-container input[type='text']").keyup(function() {
		if (this.checkValidity()) {
			$(this).removeClass("error");
		}
		else {
			$(this).addClass("error");
		}
	});

	var operationList = $(".main-container .tab-container .tab-content .operation-list");

	// 清除按钮事件
	operationList.find(".operation-item .menu input[data-btn='清除']").click(function() {
		$(this).parents(".menu").find("input[type='text']").val('');
		$(this).parents(".menu").find(".condition .dropdown .value").text("- 选择 -");
	});

	// 4.1查询
	operationList.find(".operation-item[data-operation='search']").click(function(event) {
		// 1. 初始化下拉列表
		DropDown.initAll();
		menus.show(this, event);
		$(this).find(".value").text("- 选择 -");
		var date = new Date();
		var str = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
		var menu = $(this).find(".menu");
		menu.find("input[name='startDate']").attr("placeholder", str);
		menu.find("input[name='endDate']").attr("placeholder", str);
		// 2. 显示菜单

	});

	operationList.find(".operation-item[data-operation='search'] .menu .menu-btn[data-btn='查询']").click(
			function() {
				// 1. 获取用户写入的数据
				var menu = $(this).parents(".menu");
				var reviewee = menu.find("input[name='reviewee']").val();
				var reviewer = menu.find("input[name='reviewer']").val();
				var startDate = menu.find("input[name='startDate']").val();
				var endDate = menu.find("input[name='endDate']").val();
				var satisfaction = menu.find(".dropdown[data-dropdown='satisfication'] .dropdown-list .dropdown-item-active").data(
						"satisfication");
				var result = menu.find(".dropdown[data-dropdown='result'] .dropdown-list .dropdown-item-active").data("result");

				// 2. 准备JSON数据
				callbackSearchJson = {
					"classInfoId" : sessionStorage.classInfoId,
					"reviewee" : reviewee,
					"reviewer" : reviewer,
					"startDate" : startDate,
					"endDate" : endDate,
					"satisfaction" : satisfaction,
					"reviewResult" : result
				};
				menus.hide();
				// 3. 调用函数并重新查询数据
				callBackDetail.searchCallbackAmount(callbackSearchJson);
				callBackDetail.searchCallbackDetails(callbackSearchJson);

			});

	// 4.2 删除
	operationList.find(".operation-item[data-operation='delete']").click(function() {
		var checkbox = operationList.siblings(".data-table").find("*[name='id']:checked");
		if (checkbox.length <= 0) {
			toastr.warning("请至少选择一条记录");
			return;
		}
		var id = [];
		for (var i = 0; i < checkbox.length; i++) {
			var one = checkbox[i];
			id.push($(one).val());
		}
		var json = {
			"id" : id
//			"username" : sessionStorage.username
		};
		console.log(json);
		// 执行删除
		callBackDetail.deleteCallback(json);
		// 重新查找并更新数据
		callBackDetail.searchCallbackAmount(callbackSearchJson);
		callBackDetail.searchCallbackDetails(callbackSearchJson);
	});

	// 4.3 导出
	$(document).click(function() {
		$(".menu2,.menu3").fadeOut();
	});

	operationList.find(".operation-item[data-operation='export']").click(function(event) {
		$(".menu2,.menu3").fadeOut();
		$(this).siblings().removeClass("item-active");
		$(this).addClass("item-active");
		$("#exportReviewMenu").fadeIn();
		event.stopPropagation();
	});

	$("#exportReviewMenu").find("input").click(function(event) {
		event.stopPropagation();
	});

	// 4.4 导入

	operationList.find(".operation-item[data-operation='import']").click(function(event) {
		$(".menu2,.menu3").fadeOut();
		$(this).siblings().removeClass("item-active");
		$(this).addClass("item-active");
		$("#importReviewMenu").fadeIn();
		event.stopPropagation();
	});

	// 上传控件
	var $uploadFileForm = $("#uploadFileForm");
	var $selectFile = $uploadFileForm.find(".selectFile");
	$selectFile.find(".selectFilePresenter").click(function(event) {
		$selectFile.find(".addFileView").click();
		event.stopPropagation();
	});
	$selectFile.on("change", handler = function(event) {
		var temp_path = $selectFile.find(".addFileView").val();
		temp_path = temp_path.split("\\").slice(-1);
		$uploadFileForm.find(".showFilePath").val(temp_path);
	});

	$uploadFileForm.find("#uploadFile").click(function(event) {
		event.stopPropagation();
	})

	// ajax上传表单
	$uploadFileForm.find(".uploadFile").click(function(event) {
		
		$.ajax({
			"url" : "/stuenroll/review/importReviewInfo",
			"type" : "post",
			"async" : true,
			"data" : new FormData($("#uploadFileForm")[0]),
			"cache" : false,
			"contentType" : false,
			"processData" : false,
			"success" : function(json) {
				if (json.result.count == 1) {
					toastr.info("导入用户数据成功！");
					$(".menu2,.menu3").fadeOut();
					$(".menu2,.menu3").parent().siblings().removeClass("item-active");
					$(".menu2,.menu3").parent().removeClass("item-active");
					setTimeout(function() {
						var callbackSearchJson = {};
						callbackSearchJson.classId = sessionStorage.classInfoId;
						callBackDetail.searchCallbackAmount(callbackSearchJson);
						callBackDetail.searchCallbackDetails(callbackSearchJson);
					}, 3000);
				}
				else {

					toastr.info(json.result.reason);
				}
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	});

	/**
	 * 所有页面的上一页翻页事件
	 */
	operationList.siblings(".page-list").find(".page-operation a[data-btn='prevBtn']").click(function() {
		var $currentPage = $(this).parents(".page-list").find(".page-info #currentPage");
		$page.previousPage($currentPage, this, 5, callbackSearchJson, callBackDetail.searchCallbackDetails);
	});

	/**
	 * 所有页面的下一页翻页事件
	 */
	operationList.siblings(".page-list").find(".page-operation a[data-btn='nextBtn']").click(
			function() {
				var pageInfo = $(this).parent().siblings(".page-info");
				$page.nextPage(this, 5, pageInfo.find("#currentPage"), pageInfo.find("#totalPages"), callbackSearchJson,
						callBackDetail.searchCallbackDetails);
			});

	/**
	 * 所有页面点击页数切换页面事件
	 */
	operationList.siblings(".page-list").find(".page-operation").on("click", "a[name='page-number']", function() {
		var $currentPage = $(this).parents(".page-list").find(".page-info #currentPage");
		$page.changePage(this, $currentPage, callbackSearchJson, callBackDetail.searchCallbackDetails);
	});

	// 点击回访结果跳转页面
	operationList.parent(".tab-content").find(".data-table").on("click", "tr td .details", function() {
		var td = $(this).parent().siblings();
		var reviewId = $(td[0]).find("input[name='id']").val();
		sessionStorage.reviewId = reviewId;
		sessionStorage.bool = true;
		sessionStorage.locations = "list.html";
		location.href = "record.html";
	});

});