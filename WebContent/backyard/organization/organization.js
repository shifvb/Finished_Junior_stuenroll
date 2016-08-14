$(function() {

	/**
	 * 1. 权限检查：根据权限隐藏或显示相应面板和操作
	 */
	var bool = (sessionStorage.organization == "辽宁省就业网");
	if (checkPermission([ "7_4" ])) {
		$(".main-container .tab-list .tab-item[data-index='机构列表']").addClass("tab-item-show");
		$(".main-container .tab-container .tab-content .operation-list .operation-item[data-operation='search']").addClass(
				"operation-item-show");
	}
	else {

	}
	if (bool) {
		$(".main-container .tab-list .tab-item[data-index='参与转换']").addClass("tab-item-show");
	}
	if (checkPermission([ "7_1" ]) && bool) {
		$(".main-container .tab-container .tab-content .operation-list .operation-item[data-operation='add']").addClass(
				"operation-item-show");
	}
	if (checkPermission([ "7_2" ]) && bool) {
		$(".main-container .tab-container .tab-content .operation-list .operation-item[data-operation='delete']").addClass(
				"operation-item-show");
	}
	if (checkPermission([ "7_3" ]) && bool) {
		$(".main-container .tab-container .tab-content .operation-list .operation-item[data-operation='update']").addClass(
				"operation-item-show");
	}
	if (checkPermission([ "7_23" ]) && bool) {
		$(".main-container .tab-container .tab-content .operation-list .operation-item[data-operation='transform']").addClass(
				"operation-item-show");
	}

	/**
	 * 2. I_Organization接口和Organization实现
	 */
	/** *********************************机构接口**************************************** */

	var I_Organization = function() {
	}

	/**
	 * 初始化抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_Organization.prototype.init = function(json) {
		throw "抽象方法";
	}

	/**
	 * 根据条件查询机构信息抽象方法
	 * 
	 * @param {Object}
	 *            json : 传入的查询条件，类似于json格式的键值对
	 */
	I_Organization.prototype.searchOrganizations = function(json) {
		throw "抽象方法";
	}

	/**
	 * 查询符合条件的机构总数抽象方法
	 * 
	 * @param {Object}
	 *            json : 传入的查询条件，类似于json格式的键值对
	 */
	I_Organization.prototype.searchOrganizationsAmount = function(json) {
		throw "抽象方法";
	}

	/**
	 * 根据填写信息添加组织机构抽象方法
	 * 
	 * @param {Object}
	 *            json : 传入添加机构的信息，类似于json格式的键值对
	 */
	I_Organization.prototype.addOrganization = function(json) {
		throw "抽象方法";
	}

	/**
	 * 根据传进的条件对机构进行修改抽象方法
	 * 
	 * @param {Object}
	 *            json : 传入的更新内容，类似于json格式的键值对
	 */
	I_Organization.prototype.updateOrganization = function(json) {
		throw "抽象方法";
	}

	/**
	 * 根据传入条件删除机构抽象方法
	 * 
	 * @param {Object}
	 *            json : 传入的删除条件，类似于json格式的键值对
	 */
	I_Organization.prototype.deleteOrganizations = function(id) {
		throw "抽象方法";
	}

	/**
	 * 根据传入信息进行机构参与转换操作抽象方法
	 * 
	 * @param {Object}
	 *            json : 传入的参与转换操作条件，类似于json格式的键值对
	 */
	I_Organization.prototype.transformOrganizations = function(json) {
		throw "抽象方法";
	}

	var Organization = function() {
	}

	Organization.prototype = new I_Organization();

	Organization.prototype.init = function(json) {
		this.searchOrganizationsAmount(json);
		this.searchOrganizations(json);
	}

	/**
	 * 根据条件查询机构
	 * 
	 * @param {Object}
	 *            json
	 */
	Organization.prototype.searchOrganizations = function(json) {
		// 检查是否拥有权限 7_4
		if (!checkPermission([ "7_4" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/organization/searchOrganizations",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				// 1. 获得json传回的数据
				var data = json.result;
				// 2. 清空当前页面的表格
				var table = $(".tab-container .tab-content[data-index='机构列表'] .data-table");
				table.find("tr:gt(0)").remove();
				// 3. 获取当前页面并计算当前表格起始序号
				var currentPage = $("#organ-currentPage").text();
				currentPage = new Number(currentPage);
				var start = (currentPage - 1) * 35;
				// 4. for 循环生成表格
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td><input type='checkbox' name='id' value='" + one.id + "' /></td>"
					temp += "<td>" + (start + i + 1) + "</td>"
					temp += "<td>" + one.name + "</td>";
					temp += "<td>" + one.abbreviation + "</td>";
					temp += "<td>" + one.address + "</td>";
					temp += "<td>" + one.tel + "</td>";
					temp += "<td>" + one.liaison + "</td>";
					temp += "<td>" + one.professionAmount + "</td>";
					temp += "<td>" + one.classAmount + "</td>";
					temp += "<td>" + one.students + "</td>";
					temp += "</tr>";
				}
				// console.log(temp);
				// 5. 显示表格
				table.append(temp);
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	// 查询符合条件的机构数量
	Organization.prototype.searchOrganizationsAmount = function(json) {
		// 检查是否拥有权限 7_4
		if (!checkPermission([ "7_4" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/organization/searchOrganizationAmount",
			"type" : "post",
			"async" : false,
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				var count = json.result; // 总记录数
				var content = $(".tab-container .tab-content[data-index='机构列表']");
				// content.find("#organ-totalRows").text(count);
				// var totalPages = (count % 35 == 0) ? count / 35 :
				// Math.floor(count / 35) + 1;
				// content.find("#organ-totalPages").text(totalPages);
				var prevBtn = content.find(".page-list .page-operation a[data-btn='prevBtn']")
				$page.showTotal($("#organ-totalRows"), count, 35, $("#organ-totalPages"));
				var totalPages = $("#organ-totalPages").text();
				totalPages = new Number(totalPages);
				if (count == 0) {
					$page.insertPageNumberByMin(prevBtn, 1, 1);
				}
				else {
					if (totalPages < 5) {
						$page.insertPageNumberByMin(prevBtn, 1, totalPages);
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

	// 添加
	Organization.prototype.addOrganization = function(json) {
		// 检查是否拥有权限 7_1
		if (!checkPermission([ "7_1" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/organization/addOrganization",
			"type" : "post",
			"dataType" : "json",
			"async" : false,
			"data" : json,
			"success" : function(json) {
				// 显示添加成功信息并刷新记录
				if (json.result == 1) {
					toastr.success("添加机构成功");
				}
				else {
					toastr.error("添加失败");
				}

			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});

	}

	// 修改
	Organization.prototype.updateOrganization = function(json) {
		// 检查是否拥有权限 7_3
		if (!checkPermission([ "7_3" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"type" : "post",
			"url" : "/stuenroll/organization/updateOrganization",
			"dataType" : "json",
			"async" : false,
			"data" : json,
			"success" : function(json) {
				if (json.result == 1) {
					toastr.success("更新机构成功");
				}
				else {
					toastr.warning("更新机构失败");
				}

			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	// 删除
	Organization.prototype.deleteOrganization = function(id) {
		// 检查是否拥有权限 7_2
		if (!checkPermission([ "7_2" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/organization/deleteOrganizationsById",
			"type" : "post",
			"traditional" : true,
			"async" : false,
			"dataType" : "json",
			"data" : {
				"organizationId" : sessionStorage.organizationId,
				"id" : id
			},
			"success" : function(json) {
				toastr.success("删除了" + json.result + "条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	// 参与转换
	Organization.prototype.organizationTransform = function(json) {
		// 检查是否拥有权限 7_23
		if (!checkPermission([ "7_23" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/organization/setOrganizationTransformInfo",
			"type" : "post",
			"traditional" : true,
			"async" : false,
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				toastr.success("成功设置了" + json.result + "个机构进行参与转换");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 3. I_OrganizationTransform接口和OrganizationTransform实现
	 */
	/** *******************************参与转换机构接口*********************************** */

	var I_OrganizationTransform = function() {
	}

	/**
	 * 初始化抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_OrganizationTransform.prototype.init = function(json) {
		throw "抽象方法";
	}

	/**
	 * 根据条件查询参与转换的机构抽象方法
	 * 
	 * @param {Object}
	 *            json : 传入的查询条件，类似于json格式的键值对
	 */
	I_OrganizationTransform.prototype.searchOrganizations = function(json) {
		throw "抽象方法";
	}

	/**
	 * 根据条件查询记录总数抽象方法
	 * 
	 * @param {Object}
	 *            json : 传入的查询条件，类似于json格式的键值对
	 */
	I_OrganizationTransform.prototype.searchOrganizationsAmount = function(json) {
		throw "抽象方法";
	}

	/**
	 * 根据更新条件更新参与转换的机构抽象方法
	 * 
	 * @param {Object}
	 *            json : 传入的更新条件，类似于json格式的键值对
	 */
	I_OrganizationTransform.prototype.updateOrganization = function(json) {
		throw "抽象方法";
	}

	/**
	 * 根据条件删除参与转换的机构抽象方法
	 * 
	 * @param {Object}
	 *            json : 传入的删除条件，类似于json格式的键值对
	 */
	I_OrganizationTransform.prototype.deleteOrganizations = function(json) {
		throw "抽象方法";
	}

	/**
	 * 更改选中机构的开放报名抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_OrganizationTransform.prototype.blockOrganization = function(json) {
		throw "抽象方法"; // 使用update方法修改
	}

	/**
	 * 将选中的机构关联上专业抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_OrganizationTransform.prototype.addOrganizationProfession = function(json) {
		throw "抽象方法";
	}

	/**
	 * 删除选中机构关联的专业
	 * 
	 * @param json
	 */
	I_OrganizationTransform.prototype.deleteOrganizationProfession = function(json) {
		throw "抽象方法";
	}

	/**
	 * 查询机构关联的专业抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_OrganizationTransform.prototype.searchProfessionsWithOrganization = function(json) {
		throw "抽象方法";
	}

	/**
	 * 查询所有的专业抽象方法
	 * 
	 * @param {Object}
	 *            json
	 */
	I_OrganizationTransform.prototype.searchAllProfessions = function(json) {
		throw "抽象方法";
	}

	/** ***********************************参与转换实现********************************** */

	var OrganizationTransform = function() {
	}

	OrganizationTransform.prototype = new I_OrganizationTransform();

	/**
	 * 初始化方法
	 * 
	 * @param {Object}
	 *            json
	 */
	OrganizationTransform.prototype.init = function(json) {
		this.searchOrganizationsAmount(json);
		this.searchOrganizations(json);
	}

	/**
	 * 根据条件查询参与转换的机构方法
	 * 
	 * @param {Object}
	 *            json : 传入的查询条件，类似于json格式的键值对
	 */
	OrganizationTransform.prototype.searchOrganizations = function(json) {
		// 检查是否拥有权限 7_4
		if (!checkPermission([ "7_4" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/organTransform/searchOrganizations",
			"type" : "post",
			"dataType" : "json",
			"async" : false,
			"data" : json,
			"success" : function(json) {
				// 从Controller获得数据
				var data = json.result;
				// 查询当前需要展示的表格
				var table = $(".tab-container .tab-content[data-index='参与转换'] .data-table");
				// 清空当前的表格
				table.find("tr:gt(0)").remove();
				// 获得当前页数
				var currentPage = $("#trans-currentPage").text();
				currentPage = new Number(currentPage);
				// 计算当前页数的起始行号
				var start = (currentPage - 1) * 35;
				// for循环构造表格
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td><input type='checkbox' name='id' value='" + one.id + "' /></td>";
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td>" + one.name + "</td>";
					temp += "<td>" + one.abbreviation + "</td>";
					temp += "<td>" + one.year + "</td>";
					temp += "<td><a data-index='professionAmount' ";
					if (sessionStorage.organization != "辽宁省就业网") {
						temp += "disabled=\"disabled\" ";
					}
					temp += ">" + one.professionAmount + "</a></td>";
					temp += "<td>" + one.classAmount + "</td>";
					temp += "<td>" + one.studentsAmount + "</td>";
					temp += "<td>" + one.quitStudentsAmount + "</td>";
					temp += "<td>" + one.employedAmount + "</td>";
					// switchbox： 开放报名
					temp += "<td><section class='model'><div class='checkbox'><input type='checkbox' ";
					if (one.block == 0) {
						temp += "checked='checked' ";
					}
					if (sessionStorage.organization != "辽宁省就业网") {
						temp += "disabled='disabled' ";
					}
					temp += "/><label></label></div></section></td>";
					temp += "</tr>";
				}
				// console.log(temp);
				table.append(temp);

			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 根据条件查询记录总数方法
	 * 
	 * @param {Object}
	 *            json : 传入的查询条件，类似于json格式的键值对
	 */
	OrganizationTransform.prototype.searchOrganizationsAmount = function(json) {
		// 检查是否拥有权限 7_4
		if (!checkPermission([ "7_4" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/organTransform/searchOrganizationsAmount",
			"type" : "post",
			"dataType" : "json",
			"async" : false,
			"data" : json,
			"success" : function(json) {
				var count = json.result;
				var pageList = $(".tab-container .tab-content[data-index='参与转换'] .page-list");
				var prevBtn = pageList.find(".page-operation a[data-btn='prevBtn']");
				$page.showTotal($("#trans-totalRows"), count, 35, $("#trans-totalPages"));
				var totalPages = $("#trans-totalPages").text();
				totalPages = new Number(totalPages);
				// 如果没有记录，则显示第一页
				if (count == 0) {
					$page.insertPageNumberByMin(prevBtn, 1, 1);
					$("#trans-totalPages").text(1);
				}
				else {
					if (totalPages < 5) {
						$page.insertPageNumberByMin(prevBtn, 1, totalPages);
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

	/**
	 * 根据更新条件更新参与转换的机构方法
	 * 
	 * @param {Object}
	 *            json : 传入的更新条件，类似于json格式的键值对
	 */
	OrganizationTransform.prototype.updateOrganization = function(json) {
		// 7_3
		if (!checkPermission([ "7_3" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/organTransform/updateOrganization",
			"type" : "post",
			"dataType" : "json",
			"async" : false,
			"data" : json,
			"success" : function(json) {
				if (json.result == 1) {
					toastr.success("更新成功");
				}
				else {
					toastr.error("更新失败");
				}
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 根据条件删除参与转换的机构方法
	 * 
	 * @param {Object}
	 *            json : 传入的删除条件，类似于json格式的键值对
	 */
	OrganizationTransform.prototype.deleteOrganizations = function(json) {
		// 7_2
		if (!checkPermission([ "7_2" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/organTransform/deleteOrganizations",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true,
			"async" : false,
			"data" : {
				"id" : json.id,
				"year" : json.year
			},
			"success" : function(json) {
				toastr.success("成功删除了" + json.result + "条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 更改选中机构的开放报名方法
	 * 
	 * @param {Object}
	 *            json
	 */
	OrganizationTransform.prototype.blockOrganization = function(json) {
		// 7_24 / 7_25
		if (!checkPermission([ "7_24" ]) || !checkPermission([ "7_25" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/organTransform/updateOrganization",
			"type" : "post",
			"dataType" : "json",
			"async" : false,
			"data" : json,
			"success" : function(json) {
				if (json.result == 1) {
					toastr.success("修改成功");
				}
				else {
					toastr.error("修改失败");
				}
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
		return json.result;
	}

	/**
	 * 将选中的机构关联上专业方法
	 * 
	 * @param {Object}
	 *            json
	 */
	OrganizationTransform.prototype.addOrganizationProfession = function(json) {
		$.ajax({
			"url" : "/stuenroll/organTransform/addOrganizationProfession",
			"type" : "post",
			"dataType" : "json",
			"async" : false,
			"data" : json,
			"traditional" : true,
			"success" : function(json) {
				toastr.success("成功添加了" + json.result + "该机构关联的专业");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 删除选中的机构关联的专业方法
	 * 
	 * @param {Object}
	 *            json
	 */
	OrganizationTransform.prototype.deleteOrganizationProfession = function(json) {
		$.ajax({
			"url" : "/stuenroll/organTransform/deleteOrganizationProfession",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true,
			"async" : false,
			"data" : json,
			"success" : function(json) {
				toastr.success("成功删除了" + json.result + "该机构关联的专业");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 查询机构关联的专业
	 * 
	 * @param {Object}
	 *            json
	 */
	OrganizationTransform.prototype.searchProfessionsWithOrganization = function(json) {
		// 6_4
		if (!checkPermission([ "6_4" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/profession/searchProfessionsWithOrganization",
			"type" : "post",
			"dataType" : "json",
			"async": false,
			"data" : json,
			"success" : function(json) {
				var data = json.result;
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					var profession = {
						"professionId" : one.professionId,
						"name" : one.name
					};
					selectedProfessions.push(profession);
				}
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 查询所有的专业
	 * 
	 * @param {Object}
	 *            json
	 */
	OrganizationTransform.prototype.searchAllProfessions = function(json) {
		// 6_4
		if (!checkPermission([ "6_4" ])) {
			toastr.error("没有权限");
			return;
		}
		$.ajax({
			"url" : "/stuenroll/profession/searchProfessionsWithOrganization",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"async": false,
			"success" : function(json) {
				var data = json.result;
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					var profession = {
						"professionId" : one.professionId,
						"name" : one.name
					};
					allProfessions.push(profession);
				}
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 4. 工厂方法
	 */
	function OrganizationFactory(key) {
		if (key == "Organization") {
			return new Organization();
		}
		else if (key == "OrganizationTransform") {
			return new OrganizationTransform();
		}
	}

	/**
	 * 5. 创建对象和全局变量
	 */
	var organSearchJson = {}; // 存储机构面板查询条件
	organSearchJson.organizationId = sessionStorage.organizationId;
	var transSearchJson = {}; // 存储参与转换查询条件
	transSearchJson.organizationId = sessionStorage.organizationId;
	var allProfessions = []; // 存储所有的专业
	var leftProfessions = []; // 存储机构未关联的专业
	var selectedProfessions = []; // 存储机构已关联专业
	var newSelectedProfessions = []; // 新选中的转换专业

	DropDown.initAll();
	var organization = OrganizationFactory("Organization");
	var organizationTransform = OrganizationFactory("OrganizationTransform");

	var $page = pageFactory();
	var tab = TabFactory();
	var menus = MenuFactory("Menu");
	menus.init();
	var professionMenus = MenuFactory("ProfessionMenu");
	professionMenus.init();

	/**
	 * 6. 面板切换事件
	 */

	organization.init(organSearchJson);

	$(".tab-list .tab-item").click(function() {
		var temp = $(this).data("index");
		tab.showTab(temp);
		if (temp == "机构列表") {
			// 显示用户对应权限的操作列表

			// 查询机构信息并更新显示
			organSearchJson = {};
			organSearchJson.organizationId = sessionStorage.organizationId;
			organization.init(organSearchJson);
		}
		else if (temp == "参与转换" && bool) {
			// 根据用户权限显示对应的操作列表

			// 查询参与转换信息并更新列表
			transSearchJson = {};
			transSearchJson.organizationId = sessionStorage.organizationId;
			organizationTransform.init(transSearchJson);
		}
	});

	// 输入框keyup事件
	$(".main-container .tab-container input[type='text']").keyup(function() {
		if (this.checkValidity()) {
			$(this).removeClass("error");
		}
		else {
			$(this).addClass("error");
		}
	});

	/**
	 * 面板清除按钮事件
	 */
	$(".tab-container .tab-content .operation-list .menu-btn[data-btn='清除']").click(function() {
		menus.clearData(this);
	});

	/**
	 * 7. 机构列表面板内事件：查询、添加、修改、删除、参与转换、翻页、随机页码
	 */
	/** **********************************机构列表面板事件************************************* */

	var operationList = $(".tab-container .tab-content[data-index='机构列表'] .operation-list");

	/**
	 * 1. 查询操作
	 */
	var organSearch = operationList.find(".operation-item[data-operation='search']");
	$(organSearch).click(function(event) {
		// 1. 判断当前机构是否为就业网用户，是则不禁用查询的输入； 否则读取当前机构信息并自动填入且禁用某些输入框
		if (sessionStorage.organization != "辽宁省就业网") {
			var dataTable = $(".tab-container .tab-content[data-index='机构列表'] .data-table");
			var td = dataTable.find("tr:nth-child(1)").find("td");
			var menu = organSearch.find(".menu");
			menu.find("input[name='name']").val($(td[2]).text());
			menu.find("input[name='name']").attr("disabled", "true");
			menu.find("input[name='abbreviation']").val($(td[3]).text());
			menu.find("input[name='abbreviation']").attr("disabled", "true");
		}
		menus.show(this, event);
	});

	/**
	 * 1.1 查询操作查询按钮事件
	 */
	organSearch.find(".menu-btn[data-btn='查询']").click(function() {
		// 查询数据->隐藏面板->更新数据并停留在当前页面
		var menu = $(this).parents(".menu");
		var input = menu.find("input");
		var bool = true;
		input.each(function(i, one) {
			bool = bool && one.checkValidity();
		});
		if (!bool) {
			toastr.warning("请填写正确的信息");
			return;
		}
		var name = menu.find("input[name='name']").val();
		var abbreviation = menu.find("input[name='abbreviation']").val();
		if (abbreviation == null || abbreviation == '') {
			toastr.warning("请填写所需信息");
			return;
		}
		var address = menu.find("input[name='address']").val();
		var liasion = menu.find("input[name='liasion']").val();
		var tel = menu.find("input[name='tel']").val();
		$("#organ-currentPage").text(1);
		organSearchJson = {
			"organizationId" : sessionStorage.organizationId,
			"name" : name,
			"abbreviation" : abbreviation,
			"address" : address,
			"liasion" : liasion,
			"tel" : tel,
			"page" : 1
		};
		// 2. 隐藏面板
		menus.hide();
		// 3. 更新页面
		organization.searchOrganizationsAmount(organSearchJson);
		// 1. 查询数据并更新
		organization.searchOrganizations(organSearchJson);
	});

	/**
	 * 2. 添加操作
	 */
	var organAdd = operationList.find(".operation-item[data-operation='add']");
	$(organAdd).click(function(event) {
		menus.show(this, event);
	});

	/**
	 * 2.1 添加操作保存按钮事件
	 */
	organAdd.find(".menu-btn[data-btn='保存']").click(function() {
		// 读取input内容 -> 执行插入 -> 更新当前页面数据
		var menu = $(this).parents(".menu");
		var input = menu.find("input");
		var bool = true;
		input.each(function(i, one) {
			bool = bool && one.checkValidity();
		});
		if (!bool) {
			toastr.warning("请填写完整的信息");
			return;
		}
		var name = menu.find("input[name='name']").val();
		var abbreviation = menu.find("input[name='abbreviation']").val();
		var address = menu.find("input[name='address']").val();
		var liasion = menu.find("input[name='liasion']").val();
		var tel = menu.find("input[name='tel']").val();
		var liasion_tel = menu.find("input[name='liasion_tel']").val();
		var json = {
			"name" : name,
			"abbreviation" : abbreviation,
			"address" : address,
			"liasion" : liasion,
			"tel" : tel,
			"liasion_tel" : liasion_tel
		};
		console.log(json);
		// 隐藏面板
		menus.hide();
		// 执行插入操作
		organization.addOrganization(json);
		// 重新查询数据并更新数据
		organSearchJson.page = new Number($("#organ-currentPage").text());
		organization.searchOrganizationsAmount(organSearchJson);
		organization.searchOrganizations(organSearchJson);
	});

	/**
	 * 3. 修改操作
	 */
	var organUpdate = operationList.find(".operation-item[data-operation='update']");
	$(organUpdate).click(function(event) {
		// 获取当前用户选择的机构信息并自动填充
		var checkbox = $(".tab-container .tab-content[data-index='机构列表'] .data-table").find("*[name='id']:checked");
		if (checkbox.length <= 0) {
			toastr.warning("请选中一条记录");
			return;
		}
		if (checkbox.length > 1) {
			toastr.warning("仅能选择一条记录");
			return;
		}
		var td = checkbox.parent().siblings();
		// 显示面板
		menus.show(this, event);
		// 自动填写数据
		var menu = $(this).find(".menu");
		menu.find("input[name='name']").val($(td[1]).text());
		menu.find("input[name='abbreviation']").val($(td[2]).text());
		menu.find("input[name='address']").val($(td[3]).text());
		menu.find("input[name='liasion']").val($(td[5]).text());
		menu.find("input[name='tel']").val($(td[4]).text());
	});

	/**
	 * 3.1 修改操作保存按钮事件
	 */
	organUpdate.find(".menu-btn[data-btn='保存']").click(function() {
		// 获取修改后的值 -> 执行更新 -> 刷新页面数据
		var menu = $(this).parents(".menu");
		var input = menu.find("input");
		var bool = true;
		input.each(function(i, one) {
			bool = bool && one.checkValidity();
		});
		if (!bool) {
			toastr.warning("请填写正确的信息");
			return;
		}
		var name = menu.find("input[name='name']").val();
		var abbreviation = menu.find("input[name='abbreviation']").val();
		var address = menu.find("input[name='address']").val();
		var liasion = menu.find("input[name='liasion']").val();
		var tel = menu.find("input[name='tel']").val();
		var organizationId = $(".tab-container .tab-content[data-index='机构列表'] .data-table").find("*[name='id']:checked").val();
		var json = {
			"organizationId" : organizationId,
			"name" : name,
			"abbreviation" : abbreviation,
			"address" : address,
			"liasion" : liasion,
			"tel" : tel
		};
		// 隐藏面板
		menus.hide();
		// 执行更新
		organization.updateOrganization(json);
		// 重新查询数据并更新
		organSearchJson.page = new Number($("#organ-currentPage").text());
		organization.searchOrganizations(organSearchJson);
		// organization.searchOrganizationsAmount();

	});

	/**
	 * 4. 删除操作
	 */
	var organDelete = operationList.find(".operation-item[data-operation='delete']");
	$(organDelete).click(function() {
		// 弹出确认删除对话框 -> 判断是否能够删除 -> 执行删除 -> 刷新页面数据
		var checkbox = $(".tab-container .tab-content[data-index='机构列表'] .data-table").find("*[name='id']:checked");
		if (checkbox.length <= 0) {
			toastr.warning("请至少选择一条记录");
			return;
		}
		// 弹出确认对话框
		var bool = confirm("是否删除选中的记录？");
		if (bool == false) {
			return;
		}
		// 判断选中机构是否关联班级、报名记录、归档记录、专业记录等。若有，则弹出警告无法删除
		for (var i = 0; i < checkbox.length; i++) {
			var td = $(checkbox[i]).parent().siblings();
			if ($(td[6]).text() != "0" || $(td[7]).text() != "0" || $(td[8]).text() != "0") {
				toastr.warning("无法删除记录");
				return;
			}
		}
		// 获取选中记录的id
		var id = [];
		for (var i = 0; i < checkbox.length; i++) {
			id.push($(checkbox[i]).val());
		}
		// 执行删除操作
		organization.deleteOrganization(id);

		// 刷新页面数据
		organization.searchOrganizationsAmount(organSearchJson);
		// 判断当前页面是否超过总页数
		var totalPages = $("#organ-totalPages").text();
		var currentPage = $("#organ-currentPage").text();
		totalPages = new Number(totalPages);
		currentPage = new Number(currentPage);
		// 是，则将当前页数设置为总页数
		if (currentPage > totalPages) {
			currentPage = totalPages;
		}
		// 更新当前页数
		$("#organ-currentPage").text(currentPage);
		organSearchJson.page = currentPage;
		// 重新查询记录
		organSearchJson.page = new Number($("#organ-currentPage").text());
		organization.searchOrganizations(organSearchJson);

	});

	/**
	 * 5. 参与转换操作
	 */
	var organTrans = operationList.find(".operation-item[data-operation='transform']");
	organTrans.click(function(event) {
		// 获取用户选择的机构数量并自动填写
		var dropdown = $(this).find(".menu .condition-list .condition .dropdown[data-dropdown='open-registration']");
		DropDown.clearAll();
		var checkbox = $(".tab-container .tab-content[data-index='机构列表'] .data-table").find("*[name='id']:checked");
		if (checkbox.length <= 0) {
			toastr.warning("请选中至少一条记录");
			return;
		}
		menus.show(this, event);
		$(this).find(".menu input[name='amount']").val(checkbox.length);
		DropDown.init(dropdown);
	});

	/**
	 * 5.1 参与转换保存按钮
	 */
	organTrans.find(".menu-btn[data-btn='保存']").click(function() {
		// 执行参与转换 -> 刷新页面数据
		var checkbox = $(".tab-container .tab-content[data-index='机构列表'] .data-table").find("*[name='id']:checked");
		if (checkbox.length <= 0) {
			toastr.warning("请选中至少一条记录");
			return;
		}
		var menu = $(this).parents(".menu");
		var year = menu.find("input[name='year']").val();
		if (year == null || year == '') {
			toastr.warning("请正确填写年届");
			return;
		}
		var block = menu.find(".condition .dropdown[data-dropdown='open-registration'] .dropdown-list .dropdown-item-active").data("id");
		var id = [];
		for (var i = 0; i < checkbox.length; i++) {
			id.push($(checkbox[i]).val());
		}
		var json = {
			"id" : id,
			"block" : block,
			"year" : year
		};
		console.log(json);
		// 隐藏面板
		menus.hide();
		// 执行参与转换操作
		organization.organizationTransform(json);
		// 更新数据
		organSearchJson.page = new Number($("#organ-currentPage").text());
		organization.searchOrganizationsAmount(organSearchJson);
		organization.searchOrganizations(organSearchJson);
	});

	// 页数
	var organPageList = $(".tab-container .tab-content[data-index='机构列表'] .page-list");

	/**
	 * 点击页数切换页面
	 */
	organPageList.on("click", ".page-operation a[name='page-number']", function() {
		$page.changePage(this, $("#organ-currentPage"), organSearchJson, organization.searchOrganizations);
	});

	// 翻页效果
	/**
	 * 上一页翻页效果
	 */
	organPageList.find(".page-item a[data-btn='prevBtn']").click(function() {
		if ($("#organ-totalPages").text() <= 0) {
			return;
		}
		$page.previousPage($("#organ-currentPage"), this, 5, organSearchJson, organization.searchOrganizations);
	});

	/**
	 * 下一页翻页效果
	 */

	organPageList.find(".page-item a[data-btn='nextBtn']").click(function() {
		if ($("#organ-totalPages").text() <= 0) {
			return;
		}
		$page.nextPage(this, 5, $("#organ-currentPage"), $("#organ-totalPages"), organSearchJson, organization.searchOrganizations);

	});

	/**
	 * 8. 参与转换面板事件：查询、修改、删除、关联专业、开放报名、翻页、随机页码
	 */
	/** ****************************参与转换面板事件******************************************** */
	var transOperationList = $(".tab-container .tab-content[data-index='参与转换'] .operation-list");
	var transDataTable = $(".tab-container .tab-content[data-index='参与转换'] .data-table");
	var professionMenu = $(".tab-container .tab-content[data-index='参与转换'] .professionMenu");
	var transPageList = $(".tab-container .tab-content[data-index='参与转换'] .page-list");

	/**
	 * 1. 查询事件
	 */
	var transSearch = transOperationList.find(".operation-item[data-operation='search']");
	transSearch.click(function(event) {
		// 1. 判断当前机构是否为就业网用户，是则不禁用查询的输入； 否则读取当前机构信息并自动填入且禁用某些输入框
		menus.show(this, event);
		if (sessionStorage.organization != "辽宁省就业网") {
			var dataTable = $(".tab-container .tab-content[data-index='参与转换'] .data-table");
			var td = dataTable.find("tr:nth-child(1)").find("td");
			var menu = transSearch.find(".menu");
			menu.find("input[name='name']").val($(td[2]).text());
			menu.find("input[name='name']").attr("disabled", "true");
			menu.find("input[name='abbreviation']").val($(td[3]).text());
			menu.find("input[name='abbreviation']").attr("disabled", "true");
		}

	});

	transSearch.find(".menu-btn[data-btn='查询']").click(function() {
		// 获取输入框内容 -> 隐藏面板 -> 查询数据 -> 更新页面数据
		var menu = $(this).parents(".menu");
		var name = menu.find("input[name='name']").val();
		var abbreviation = menu.find("input[name='abbreviation']").val();
		var year = menu.find("input[name='year']").val();
		$("#trans-currentPage").text(1);
		transSearchJson = {
			"organizationId" : sessionStorage.organizationId,
			"name" : name,
			"abbreviation" : abbreviation,
			"year" : year,
			"page" : 1
		};
		// 2. 隐藏面板
		menus.hide();
		// 3. 执行查询并更新页面数据
		organizationTransform.searchOrganizationsAmount(transSearchJson);
		organizationTransform.searchOrganizations(transSearchJson);
	});

	var oldYear = 0;
	/**
	 * 2. 修改事件
	 */
	var oldYear = 0;
	var transUpdate = transOperationList.find(".operation-item[data-operation='update']");
	transUpdate.click(function(event) {
		// 1. 判断选中记录数是否为1条
		var checkbox = transDataTable.find("*[name='id']:checked");
		if (checkbox.length <= 0 || checkbox.length > 1) {
			toastr.warning("请仅选中一条记录");
			return;
		}
		// 2. 显示面板
		menus.show(this, event);
		// 3. 自动填写数据
		var td = checkbox.parent().siblings();
		var menu = $(this).find(".menu");
		menu.find("input[name='name']").val($(td[1]).text());
		menu.find("input[name='abbreviation']").val($(td[2]).text());
		menu.find("input[name='year']").val($(td[3]).text());
		oldYear = $(td[3]).text();
	});

	transUpdate.find(".menu-btn[data-btn='保存']").click(function() {
		// 1. 获取menu数据
		var menu = $(this).parents(".menu");
		var newYear = menu.find("input[name='year']").val();
		var currentPage = $("#trans-currentPage").text();
		currentPage = new Number(currentPage);
		var json = {
			"organizationId" : transDataTable.find("*[name='id']:checked").val(),
			"oldYear" : oldYear,
			"newYear" : newYear
		};
		// 2. 隐藏面板
		menus.hide();
		// 3. 执行更新并刷新数据
		organizationTransform.updateOrganization(json);
		transSearchJson.page = currentPage;
		organizationTransform.searchOrganizationsAmount(transSearchJson);
		organizationTransform.searchOrganizations(transSearchJson);
	});

	/**
	 * 3. 删除事件
	 */
	var transDelete = transOperationList.find(".operation-item[data-operation='delete']");
	transDelete.click(function() {
		// 1. 判断是否选取记录
		var checkbox = transDataTable.find("*[name='id']:checked");
		if (checkbox.length <= 0) {
			toastr.warning("请至少选择一条记录");
			return;
		}
		// 2. 弹出确认对话框
		var bool = confirm("是否删除选中的记录？");
		if (bool == false) {
			return;
		}
		// 3. 判断要删除的机构是否关联了班级、学生、专业
		// 4. 获得删除的ID数组
		var json = {};
		var id = [];
		var year = [];
		var menu = $(this).parents(".menu");
		for (var i = 0; i < checkbox.length; i++) {
			var td = $(checkbox[i]).parent().siblings();
			if ($(td[5]).text() != "0" || $(td[6]).text() != "0" || $(td[7]).text() != "0") {
				toastr.warning("所选记录中某些机构无法删除,删除失败");
				return;
			}
			id.push($(checkbox[i]).val());
			year.push($(td[3]).text());
		}
		json.id = id;
		json.year = year;

		// 5. 执行删除并更新数据
		organizationTransform.deleteOrganizations(json);
		transSearchJson.page = new Number($("#trans-currentPage").text());
		organizationTransform.searchOrganizationsAmount(transSearchJson);
		organizationTransform.searchOrganizations(transSearchJson);
	});

	/**
	 * 设置关联专业
	 */
	var leftProfession = professionMenu.find("#leftProfessions");
	var selectedProfession = professionMenu.find("#selectedProfessions");

	/**
	 * 双击转换事件: 从左向右
	 */
	$(leftProfession).on("dblclick", ".profession", function() {
		// 1. 去除其他专业列表项的选中样式
		selectedProfession.find("span").removeClass("profession-active");
		// 2. 去除当前列表项的选中样式
		$(this).parent().siblings().find("span").removeClass("profession-active");
		// 3. 获取当前选中专业的ID
		var id = $(this).data("id");
		var text = $(this).text();
		// 4. 将选中项删除
		$(this).remove();
		// 5. 构造字符串并拼接到结尾
		var temp = "";
		temp += "<li><span class=\"profession profession-active\" data-id=\"" + id + "\">" + text + "</span></li>";
		selectedProfession.find("ul").append(temp);
	});

	/**
	 * 双击转换事件：从右向左
	 */
	$(selectedProfession).on("dblclick", ".profession", function() {
		// 1. 获取当前选中项的专业ID
		var id = $(this).data("id");
		var text = $(this).text();
		// 2. 移除当前列表所有项的激活样式
		selectedProfession.find("span").removeClass("profession-active");
		// 3. 从列表删除当前选中的项
		$(this).remove();
		// 4. 移除另一个列表所有项移除激活样式
		var span = leftProfession.find("span");
		span.removeClass("profession-active");
		// 5. 根据当前的专业ID在另一个列表结尾添加项
		var temp = "";
		temp += "<li><span class=\"profession profession-active\" data-id=\"" + id + "\">" + text + "</span></li>";
		leftProfession.find("ul").append(temp);
	});

	var organizationID = 0;
	var year = 0;

	/**
	 * 点击关联专业显示面板
	 */
	transDataTable.on("click", "tr a[data-index='professionAmount']", function(event) {
		// 1. 判断权限
		if (sessionStorage.organization != "辽宁省就业网") {
			toastr.warning("权限不足，无法设置");
			return;
		}
		// 2. 清除之前的数据
		professionMenus.clearData(professionMenu);
		var leftUl = leftProfession.find("ul");
		var selectedUl = selectedProfession.find("ul");
		leftUl.find(".profession").remove();
		selectedUl.find(".profession").remove();
		var td = $(this).parent().siblings();
		organizationID = $(td[0]).find("input").val();
		year = $(td[4]).text();
		// 3. 查询当前机构已关联专业
		var json = {
			"organizationId" : organizationID,
			"year" : year
		};
		allProfessions = [];
		leftProfessions = [];
		selectedProfessions = [];
		organizationTransform.searchProfessionsWithOrganization(json);
		// 4. 查询所有专业
		organizationTransform.searchAllProfessions();
		var temp1 = "";
		var temp2 = "";
		var flag = 1;
		for (var i = 0; i < allProfessions.length; i++) {
			var one = allProfessions[i];
			flag = 1;
			for (var j = 0; j < selectedProfessions.length; j++) {
				var two = selectedProfessions[j];
				if (one.professionId == two.professionId) {
					temp2 += "<li><span class=\"profession\" data-id=\"" + two.professionId + "\">" + two.name + "</span></li>"
					flag = 0;
					break;
				}
			}
			if (flag == 1) {
				leftProfessions.push(one);
			}
		}
		for (var i = 0; i < leftProfessions.length; i++) {
			var one = leftProfessions[i];
			temp1 += "<li><span class=\"profession\" data-id=\"" + one.professionId + "\">" + one.name + "</span></li>"
		}
		// 5. 在面板填充数据
		leftUl.append(temp1);
		selectedUl.append(temp2);
		// 6. 显示面板
		professionMenus.show(professionMenu, event);
	});

	/**
	 * 关联专业面板保存事件
	 */
	professionMenu.find(".menu-btn[data-btn='保存']").click(function() {
		// 1. 获取当前机构ID和新关联的专业ID
		// 1.1 获取当前最终选择的专业ID
		var lis = $("#selectedProfessions").find("ul li");
		var tempProfessionId = [];
		for (var i = 0; i < lis.length; i++) {
			var professionID = $(lis[i]).find("span").data("id");
			if (professionID == undefined || professionID == null) {
				continue;
			}
			tempProfessionId.push(professionID);
		}
		// 1.2 获取要删除的关联专业
		var deletedProfessions = [];
		for (var i = 0; i < selectedProfessions.length; i++) {
			var one = selectedProfessions[i];
			deletedProfessions.push(one.professionId);
			for (var j = 0; j < tempProfessionId.length; j++) {
				if (tempProfessionId[j] == one.professionId) {
					deletedProfessions.pop(one.professionId);
					break;
				}
			}
		}
		// 1.3 获取要新增的关联专业
		var addProfessions = [];
		for (var i = 0; i < tempProfessionId.length; i++) {
			var three = tempProfessionId[i];
			addProfessions.push(three);
			for (var j = 0; j < selectedProfessions.length; j++) {
				var four = selectedProfessions[j];
				if (three == four.professionId) {
					addProfessions.pop(three);
					break;
				}
			}
		}
		console.log(addProfessions);
		// 2. 隐藏面板
		professionMenus.hide();
		// 3. 调用关联专业函数
		var deleteJson = {
			"organizationId" : organizationID,
			"professionsId" : deletedProfessions,
			"year" : year
		};
		var addJson = {
			"organizationId" : organizationID,
			"professionsId" : addProfessions,
			"year" : year
		};
		organizationTransform.deleteOrganizationProfession(deleteJson);
		organizationTransform.addOrganizationProfession(addJson);
//		transDataTable.find(".professionAmount").text(tempProfessionId.length);

		 // 4. 重新查询
		 transSearchJson.page = new Number($("#trans-currentPage").text());
		 organizationTransform.searchOrganizations(transSearchJson);
	});

	/**
	 * 清除按钮事件
	 */
	$(".tab-container .tab-content .professionMenu .menu-btn[data-btn='清除']").click(function() {
		professionMenus.clearData(professionMenu);
		// 还原数据
		var temp1 = "";
		var temp2 = "";
		for (var i = 0; i < leftProfessions.length; i++) {
			var one = leftProfessions[i];
			temp1 += "<li><span class=\"profession\" data-id=\"" + one.professionId + "\">" + one.name + "</span></li>"
		}
		for (var j = 0; j < selectedProfessions.length; j++) {
			var two = selectedProfessions[j];
			temp2 += "<li><span class=\"profession\" data-id=\"" + two.professionId + "\">" + two.name + "</span></li>"
		}
		var leftUl = leftProfession.find("ul");
		var selectedUl = selectedProfession.find("ul");
		leftUl.append(temp1);
		selectedUl.append(temp2);
	});

	/**
	 * 设置开放报名
	 */
	transDataTable.on("click", ".model .checkbox input", function() {
		if (sessionStorage.organization != "辽宁省就业网") {
			toastr.warning("权限不足，无法设置");
			return;
		}
		if (!checkPermission([ "7_24" ]) || !checkPermission([ "7_25" ])) {
			toastr.warning("没有权限设置");
			return;
		}
		// 1. 判断当前switchbox的状态： checked表示从blocked状态转换到unblocked状态
		var block = $(this).is(":checked");
		if (block) {
			if (!checkPermission([ "7_24" ])) {
				toastr.warning("没有权限设置");
				return;
			}
			block = 0;
		}
		else {
			if (!checkPermission([ "7_25" ])) {
				toastr.warning("没有权限设置");
				return;
			}
			block = 1;
		}
		// 2. 获取机构ID
		var td = $(this).parents(".model").parent().siblings();
		var organizationId = $(td[0]).find("input").val();
		var year = $(td[4]).text();
		// 3. 封装json
		var json = {
			"organizationId" : organizationId,
			"oldYear" : year,
			"newYear" : year,
			"block" : block
		};
		// 4. 调用更改机构参与转换
		var result = organizationTransform.blockOrganization(json);
		if (result == 0) {
			if (block) {
				$(this).attr("checked", "");
			}
			else {
				$(this).attr("checked", "checked");
			}
		}
		// // 5. 重新查询
		// transSearchJson.page = new Number($("#trans-currentPage").text());
		// organizationTransform.search(transSearchJson);

	});

	/**
	 * 点击页数切换页面
	 */
	transPageList.on("click", ".page-operation a[name='page-number']", function() {
		$page.changePage(this, $("#trans-currentPage"), transSearchJson, organizationTransform.searchOrganizations);
	});

	// 翻页效果
	/**
	 * 上一页翻页效果
	 */
	transPageList.find(".page-item a[data-btn='prevBtn']").click(function() {
		if ($("#trans-totalPages").text() <= 0) {
			return;
		}
		$page.previousPage($("#trans-currentPage"), this, 5, transSearchJson, organizationTransform.searchOrganizations);
	});

	/**
	 * 下一页翻页效果
	 */
	transPageList.find(".page-item a[data-btn='nextBtn']").click(
			function() {
				if ($("#trans-totalPages").text() <= 0) {
					return;
				}
				$page.nextPage(this, 5, $("#trans-currentPage"), $("#trans-totalPages"), transSearchJson,
						organizationTransform.searchOrganizations);
			});

});