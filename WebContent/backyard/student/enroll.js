$(function() {
	
//-----------------------------权限管理部分----------------------------------------
	
	var tabContainer=$(".tab-container");
	if (!checkPermission([ "3_4" ])) {
		return;
	}
	
	
	if(checkPermission(["3_4"])){
		tabContainer.find(".menu-item[data-operation='查询']").show();
	}
	if(checkPermission(["3_1"])){
		tabContainer.find(".menu-item[data-operation='添加']").show();
	}
	if(checkPermission(["3_2"])){
		tabContainer.find(".menu-item[data-operation='删除']").show();
	}
	if(checkPermission(["3_3"])){
		tabContainer.find(".menu-item[data-operation='修改']").show();
	}
	if(checkPermission(["3_19"])){
		tabContainer.find(".menu-item[data-operation='学员分班']").show();
	}
	if(checkPermission(["3_20"])){
		tabContainer.find(".menu-item[data-operation='取消分班']").show();
	}
	if(checkPermission(["3_19"])){
		tabContainer.find(".menu-item[data-operation='学员中退']").show();
	}
	if(checkPermission(["3_20"])){
		tabContainer.find(".menu-item[data-operation='取消中退']").show();
	}
	
	
//---------------------------------------接口定义部分----------------------------------------
	
	
	/**
	 * 报名管理抽象接口
	 */
	var I_Enroll = function() {
		
	}

	/**
	 * 根据条件查询
	 */
	I_Enroll.prototype.searchEnroll = function(json,index) {
		throw "抽象方法";
	}
	/**
	 * 分页，显示总页数与每页记录数
	 */
	I_Enroll.prototype.searchEnrollCount = function(json,index) {
		throw "抽象方法";
	}
	/**
	 * 根据ID删除
	 */
	I_Enroll.prototype.deleteById = function() {
		throw "抽象方法";
	}
	/**
	 * 根据ID查询
	 */
	I_Enroll.prototype.searchEnrollById = function() {
		throw "抽象方法";
	}
	/**
	 * 填充被选中的报名记录
	 */
	I_Enroll.prototype.fillSelectedEnroll = function(json) {
		throw "抽象方法";
	}
	/**
	 * 更新报名记录
	 */
	I_Enroll.prototype.modifyEnroll = function() {
		throw "抽象方法";
	}
	/**
	 * 添加报名记录
	 */
	I_Enroll.prototype.addEnroll = function() {
		throw "抽象方法";
	}
	/**
	 * 加载申报专业下拉列表数据
	 */
	I_Enroll.prototype.loadProfessionDropDown = function($menu) {
		throw "抽象方法";
	}
	/**
	 * 加载培训机构下拉列表数据
	 */
	I_Enroll.prototype.loadOrganizationDropWithProfessionDown=function($menu) {
		throw "抽象方法";
	}
	/**
	 * 加载培训机构下拉列表数据
	 */
	I_Enroll.prototype.loadOrganizationDropDown = function() {
		throw "抽象方法";
	}
	/**
	 * 根据机构加载专业下拉列表数据
	 */
	I_Enroll.prototype.loadProfessionDropWithOrganizationDown = function() {
		throw "抽象方法";
	}
	/**
	 * 根据专业与机构加载专业下拉列表数据
	 */
	I_Enroll.prototype.loadProfessionDropWithOrganizationAndProfessionDown = function() {
		throw "抽象方法";
	}
	/**
	 * 验证身份证号
	 */
	I_Enroll.prototype.validatePid = function() {
		throw "抽象方法";
	}
	/**
	 * 检验是否分班
	 */
	I_Enroll.prototype.hasAllot = function() {
		throw "抽象方法";
	}
	/**
	 * 根据当前年份加载毕业年份最近的五年
	 */
	I_Enroll.prototype.loadRecentFiveYears = function(){
		throw "抽象方法";
	}
	/**
	 * 学员分班
	 */
	I_Enroll.prototype.allot=function(json){
		throw "抽象方法";
	}
	/**
	 * 取消学员分班
	 */
	I_Enroll.prototype.cancelAllot = function(json){
		throw "抽象方法";
	}
	/**
	 * 学员中退
	 */
	I_Enroll.prototype.quit=function(json){
		throw "抽象方法";
	}
	/**
	 * 取消学员中退
	 */
	I_Enroll.prototype.cancelQuit = function(json){
		throw "抽象方法";
	}
	/**
	 * 检验是否已中退
	 */
	I_Enroll.prototype.hasQuit = function(flag) {
		throw "抽象方法";
	}
	/**
	 * 	清除面板中的内容
	 */
	I_Enroll.prototype.clearData = function(){
		throw "抽象方法";
	}	

	
//----------------------------------------接口实现部分------------------------------------------------
	
	
	var Enroll = function() {

	}
	Enroll.prototype = new I_Enroll();
	/**
	* 加载申报专业下拉列表数据
	*/
	Enroll.prototype.loadProfessionDropDown=function($menu) {
    	var $profession = $("[name=curriculum]");
        var $organization = $("[name=organization]");
        // 删除专业与机构中的选项
        $profession.find(".dropdown-item").remove();
        $organization.find(".dropdown-item").remove();
        $.ajax({
        	"type":"post",
        	"url":"/stuenroll/profession/searchProfessionInYearAtDropDown",
        	"dataType":"json",
        	"data":{
        		"year":new Date().getFullYear()
        	},
        	"async": false,
        	"success": function(json){
        		var list=json.result;
        		var temp="";
        		for(var i=0;i<list.length;i++){
        			var one=list[i];
        			var li="<li class='dropdown-item' data-id='"+one.id+"'>"+one.name+"</li>";
        			temp+=li;
        		}
        		$profession.find(".dropdown-list").append(temp);
        		// 初始化专业列表
        		DropDown.init($profession);
        		// 专业列表点击之后清空机构列表
        		$profession.click(function(){
        			$organization.find(".value").text("- 选择 -");
        		});
        		// 专业列表选项点击事件
        		$profession.find(".dropdown-item").click(function(){
        			$organization.find(".value").text("- 选择 -");
        			enroll.loadOrganizationDropWithProfessionDown($menu);// 选中专业后更新机构列表 			
        		})
        	},
        	"error":function(json){
        		toastr.error("系统错误");
        	}
        });
    }
    
	
    /**
	 * 加载培训机构下拉列表数据
	 */
	Enroll.prototype.loadOrganizationDropWithProfessionDown=function($menu)  {
    	var $organization = $("[name=organization]");
    	var $profession = $("[name=curriculum]");
    	$organization.find(".dropdown-item").remove();
        $.ajax({
        	"type": "post",
        	"url": "/stuenroll/organTransform/searchOrganizationsJoinByYearAtDropdown",
        	"dataType": "json",
        	"data": {
        		"year": new Date().getFullYear(),
        		"professionId": $menu.find("[name=curriculum] .dropdown-item-active").data("id")
        	},
        	"async": false,
        	"success": function(json){
        		var list=json.result;
        		
        		for(var i=0;i<list.length;i++){
        			var one=list[i];
        			var li="<li class='dropdown-item' data-id='"+one.id+"'>"+one.name+"</li>";
        			$organization.find(".dropdown-list").append(li);
        		}
        		// 初始化机构列表
        		DropDown.init($organization);
        		// 专业列表点击之后清空机构列表
        		$profession.click(function(){
        			$organization.find(".value").text("- 选择 -");
        		});
        		// 专业列表选项点击事件
        		$profession.find(".dropdown-item").click(function(){
        			$organization.find(".value").text("- 选择 -");
        			enroll.loadOrganizationDropWithProfessionDown($menu);// 选中专业后更新机构列表 			
        		})
        	},
        	"error": function(json){
        		toastr.error("系统错误");
        	}
        });
    }
	
	/**
	 * 根据条件查询，然后显示页面
	 */
	Enroll.prototype.searchEnroll = function(json,index) {
		$.ajax({
			"url" : "/stuenroll/enroll/searchEnroll",
			"type" : "post",
			"dataType" : "json",
			"async":false,
			"data" : json,
			"success" : function(json) {
				var data = json.result;
				if(index == "全部学员"){
					var table = $(".tab-container .tab-content[data-index='全部学员'] .data-table");
					// 获得当前页数
					var currentPage = $(".tab-container .tab-content[data-index='全部学员'] #currentPage").text();
				}
				else if(index == "中退学员"){
					var table = $(".tab-container .tab-content[data-index='中退学员'] .data-table");
					// 获得当前页数
					var currentPage = $(".tab-container .tab-content[data-index='中退学员'] #currentPage").text();
				}
				else if(index == "已分班"){
					var table = $(".tab-container .tab-content[data-index='已分班'] .data-table");
					// 获得当前页数
					var currentPage = $(".tab-container .tab-content[data-index='已分班'] #currentPage").text();
				}
				else if(index == "未分班"){
					var table = $(".tab-container .tab-content[data-index='未分班'] .data-table");
					// 获得当前页数
					var currentPage = $(".tab-container .tab-content[data-index='未分班'] #currentPage").text();
				}
				// 清空表格数据
				table.find("tr:gt(0)").remove();

				// 转化成数字类型
				currentPage = new Number(currentPage);
				// 当前页数的行号起始数字
				var start = (currentPage - 1) * 35;

				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td><input type='checkbox' name='id' value='" + one.id + "' /></td>"
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td>" + one.name + "</td>";
					temp += "<td>" + one.pid + "</td>";
					temp += "<td>" + one.tel + "</td>";
					temp += "<td>" + one.organization + "</td>";
					temp += "<td>" + one.profession + "</td>";
					temp += "<td>" + one.classinfo + "</td>";
					temp += "<td>" + one.year + "</td>";
					temp += "<td>" + one.state + "</td>";
					temp += "</tr>";
				}
				table.append(temp);
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}
	
	/**
	 * 学员中退
	 */
	Enroll.prototype.quit=function(json){
		$.ajax({
			"url" : "/stuenroll/enroll/quit",
			"type" : "post",
			"dataType" : "json",
			"async": false,
			"data" : json,
			"success" : function(json) {
				toastr.success("成功中退了" + json.result + "条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}
	/**
	 * 取消学员中退
	 */
	I_Enroll.prototype.cancelQuit = function(json){
		$.ajax({
			"url" : "/stuenroll/enroll/cancelQuit",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true,	//发送数组JSON格式
			"async": false,
			"data" : json,
			"success" : function(json) {
				toastr.success("取消了中退" + json.result + "条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}
	/**
	 * 检验是否已中退
	 */
	I_Enroll.prototype.hasQuit = function(flag) {
		var bool = false;
		var content = $(".tab-container .tab-content[data-index='"+index+"']");
		var checkbox = content.find("*[name = 'id']:checked");
		// 是否被选中
		if(checkbox.length <= 0){
			toastr.warning("请至少选中一条记录!");
			return -1;
		}
		if(flag == "quit"){
			if(checkbox.length != 1){
				toastr.warning("只能选中一条记录！");
				return -1;
			}
		}
		for(var i = 0; i < checkbox.length; i++){
			var td = $(checkbox[i]).parent().siblings();
			var state = $(td[8]).text();
			if(state == "中退"){
				bool = true;
				break;
			}
		}
		return bool;
	}
	
//-----------------------------------学员分班--------------------------
	
	var $allotMenu = $("#allotMenu");
	var $class = $allotMenu.find("[name=class]");
	/**
	 * 1. 加载培训机构下拉列表数据
	 */
	Enroll.prototype.loadOrganizationDropDown = function() {
		var $organization = $allotMenu.find("[name=organization]");
    	var $profession = $allotMenu.find("[name=curriculum]");	
    	// 删除专业与机构中的选项
        $profession.find(".dropdown-item").remove();
        $organization.find(".dropdown-item").remove();
        $class.find(".dropdown-item").remove();
        $.ajax({
        	"type": "post",
        	"url": "/stuenroll/organTransform/searchOrganizationsJoinByYearAtDropdown",
        	"dataType": "json",
        	"data": {
        		"year": new Date().getFullYear()
        	},
        	"async": false,
        	"success": function(json){
        		var list=json.result;
        		for(var i=0;i<list.length;i++){
        			var one=list[i];
        			var li="<li class='dropdown-item' data-id='"+one.id+"'>"+one.name+"</li>";
        			$organization.find(".dropdown-list").append(li);
        		}
        		// 初始化机构列表
        		DropDown.init($organization);
        		// 机构列表点击之后清空机构列表
        		$organization.click(function(){
        			$profession.find(".value").text("- 选择 -");
        		});
        		// 专业列表选项点击事件
        		$organization.find(".dropdown-item").click(function(){
        			$profession.find(".value").text("- 选择 -");
        			enroll.loadProfessionDropWithOrganizationDown();// 选中专业后更新机构列表 			
        		})
        	},
        	"error": function(json){
        		toastr.error("系统错误");
        	}
        });
	}
	
	/**
	 * 2. 根据机构加载专业下拉列表数据
	 */
	Enroll.prototype.loadProfessionDropWithOrganizationDown = function($menu) {
		var $organization = $allotMenu.find("[name=organization]");
    	var $profession = $allotMenu.find("[name=curriculum]");      
    	$profession.find(".dropdown-item").remove();
        $class.find(".dropdown-item").remove();
        $.ajax({
        	"type":"post",
        	"url":"/stuenroll/profession/searchProfessionsWithOrganization",
        	"dataType":"json",
        	"data":{
        		"year":new Date().getFullYear(),
        		"orgainzationId": $organization.find(".dropdown-item-active").data("id")
        	},
        	"success": function(json){
        		var list=json.result;
        		var temp="";
        		for(var i=0;i<list.length;i++){
        			var one=list[i];
        			var li="<li class='dropdown-item' data-id='"+one.id+"'>"+one.name+"</li>";
        			temp+=li;
        		}
        		$profession.find(".dropdown-list").append(temp);
        		// 初始化专业列表
        		DropDown.init($profession);
        		// 专业列表点击之后清空班级列表
        		$profession.click(function(){
        			$class.find(".value").text("- 选择 -");
        		});
        		// 专业列表选项点击事件
        		$profession.find(".dropdown-item").click(function(){
        			$class.find(".value").text("- 选择 -");
        			enroll.loadProfessionDropWithOrganizationAndProfessionDown();// 选中专业后更新机构列表 			
        		})
        	},
        	"error":function(json){
        		toastr.error("系统错误");
        	}
        });
	}
	
	/**
	 * 3. 根据专业与机构加载专业下拉列表班级数据
	 */
	Enroll.prototype.loadProfessionDropWithOrganizationAndProfessionDown = function() {
		var $organization = $allotMenu.find("[name=organization]");
    	var $profession = $allotMenu.find("[name=curriculum]");      
        $class.find(".dropdown-item").remove();
        $.ajax({
        	"type":"post",
        	"url":"/stuenroll/classInfo/searchClassInfo",
        	"dataType":"json",
        	"data":{
        		"orgainzationId": $organization.find(".dropdown-item-active").data("id"),
        		"professionId": $profession.find(".dropdown-item-active").data("id")
        	},
        	"success": function(json){
        		var list=json.result;
        		var temp="";
        		for(var i=0;i<list.length;i++){
        			var one=list[i];
        			var li="<li class='dropdown-item' data-id='"+one.id+"'>"+one.className+"</li>";
        			temp+=li;
        		}
        		$class.find(".dropdown-list").append(temp);
        		// 初始化专业列表
        		DropDown.init($class);
        	},
        	"error":function(json){
        		toastr.error("系统错误");
        	}
        });
	}
	
	/**
	 * 检验是否分班
	 */
	Enroll.prototype.hasAllot = function() {
		var bool = false;
		var content = $(".tab-container .tab-content[data-index='"+index+"']");
		var checkbox = content.find("*[name = 'id']:checked");
		// 是否被选中
		if(checkbox.length <= 0){
			toastr.warning("请至少选中一条记录!");
			return -1;
		}
		for(var i = 0; i < checkbox.length; i++){
			var td = $(checkbox[i]).parent().siblings();
			var classinfo = $(td[6]).text();
			if(classinfo.length > 0){
				bool = true;
				break;
			}
		}
		return bool;
	}
	
	/**
	 * 	清除面板中的内容
	 */
	Enroll.prototype.clearData = function(){
		var menu = $(".tab-container .menu");
		menu.find(":text").val("");
		menu.find("textarea").val("");
		DropDown.clearAll();
	}	
	
	/**
	 * 学员分班
	 */
	Enroll.prototype.allot=function(json){
		$.ajax({
			"url" : "/stuenroll/enroll/allot",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true,	//发送数组JSON格式
			"async": false,
			"data" : json,
			"success" : function(json) {
				toastr.success("分班了" + json.result + "条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}
	
	/**
	 * 取消学员分班
	 */
	Enroll.prototype.cancelAllot = function(json){
		$.ajax({
			"url" : "/stuenroll/enroll/cancelAllot",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true,	//发送数组JSON格式
			"async": false,
			"data" : json,
			"success" : function(json) {
				toastr.success("取消分班了" + json.result + "条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}
	
	/**
	 * 分页，显示总页数与每页记录数
	 */
	Enroll.prototype.searchEnrollCount = function(json, index) {
		var count = 0;
		$.ajax({
			"url" : "/stuenroll/enroll/searchEnrollCount",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"async":false,
			"success" : function(json) {
				count = json.result; // 总记录数
				if(count < 1){
					toastr.warning("未查询到相关的记录！")
					return;
				}
				if(index == "全部学员"){
					var content = $(".tab-container .tab-content[data-index='全部学员']");
				}
				else if(index == "中退学员"){
					var content = $(".tab-container .tab-content[data-index='中退学员']");
				}
				else if(index == "已分班"){
					var content = $(".tab-container .tab-content[data-index='已分班']");
				}
				else if(index == "未分班"){
					var content = $(".tab-container .tab-content[data-index='未分班']");
				}
				
				//content.find("#totalRows").text(count);
				var totalRows = $(".tab-container .tab-content[data-index='"+index+"'] #totalRows");
				var $totalPages = $(".tab-container .tab-content[data-index='"+index+"'] #totalPages");
				$page.showTotal(totalRows, count, 35, $totalPages);
				var totalPages = $totalPages.text();
				var prevBtn = content.find(".page-list [name='prevBtn']");
				totalPages = new Number(totalPages);
				if (totalPages < 5) {
					$page.insertPageNumberByMin(prevBtn, 1, totalPages);
				} else {
					$page.insertPageNumberByMin(prevBtn, 1, 5);
				}
				var a = prevBtn.siblings();
				$(a[0]).addClass("page-active");
				
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
		return count;
	}

	/**
	 * 添加报名记录
	 */
	Enroll.prototype.addEnroll = function(json) {
		$.ajax({
			"url" : "/stuenroll/enroll/addEnroll",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"async": false,
			"success" : function(json) {
				if(json.result == 1)
					toastr.success("成功添加了1条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}
	
	/**
	 * 删除记录
	 */
	Enroll.prototype.deleteById = function() {
		
		// 获得被选中的记录
		index = $(".tab-list .tab-active").data("index");
		var content = $(".tab-container .tab-content[data-index='"+index+"']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框
		// 是否被选中
		if(checkbox.length <= 0){
			toastr.warning("请至少选中一条记录？");
			return;
		}
		// 弹出确认对话框
		var bool = confirm("是否删除选中的记录？");
		if (bool == false) {
			return;
		}
		var id = [];
		for (var i = 0; i < checkbox.length; i++) {
			id.push($(checkbox[i]).val());
		}

		$.ajax({
			"url" : "/stuenroll/enroll/deleteById",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true,	//发送数组JSON格式
			"async": false,
			"data" : {
				"id" : id
			},
			"success" : function(json) {
				toastr.success("删除了" + json.deleteRows + "条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}	

	/**
	 * 修改
	 * 1.根据ID查询
	 */

	Enroll.prototype.searchEnrollById = function() {
		// 获得被选中的记录
		var bool = false;
		index = $(".tab-list .tab-active").data("index");
		var content = $(".tab-container .tab-content[data-index='"+index+"']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框
		if(checkbox.length != 1){
			toastr.warning("请选中一条记录！");
			return bool;
		}
		selectedId = checkbox.val();
		$.ajax({
			"url" : "/stuenroll/enroll/searchEnrollById",
			"type" : "post",
			"dataType" : "json",
			"async": false,
			"data" : {
				"id" : selectedId
			},
			"success" : function(json) {
				selectedJson = json.result;
				enroll.fillSelectedEnroll(selectedJson);
				bool = true;
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
		return bool;
	}
	/**
	 * 修改
	 * 2.填充被选中的报名记录
	 */ 
	Enroll.prototype.fillSelectedEnroll = function(json) {
		var $modifyMenu = $(".tab-container #modifyMenu");
		$modifyMenu.find("[name=name]").val(json.name);
		$modifyMenu.find("[name=pid]").val(json.pid);
		$modifyMenu.find("[name=graduteSchool]").val(json.graduate_school);
		$modifyMenu.find("[name=graduteDate]").val(json.graduate_date);
		$modifyMenu.find("[name=birthday]").val(json.birthday);
		$modifyMenu.find("[name=residentAddress]").val(json.resident_address);
		$modifyMenu.find("[name=permanentAddress]").val(json.permanent_address);
		$modifyMenu.find("[name=homeAddress]").val(json.home_address);
		$modifyMenu.find("[name=tel]").val(json.tel);
		$modifyMenu.find("[name=homeTel]").val(json.home_tel);
		$modifyMenu.find("[name=email]").val(json.email);
		$modifyMenu.find("[name=sex] .value").text(json.sex);
		$modifyMenu.find("[name=sex] .dropdown-item").each(function(i, one) {			
			if($(one).text() == json.sex){
				$(one).addClass("dropdown-item-active");
			}
		});
		$modifyMenu.find("[name=nation] .value").text(json.nation);
		$modifyMenu.find("[name=nation] .dropdown-item").each(function(i, one) {
			if($(one).text() == json.nation){
				$(one).addClass("dropdown-item-active");
			}
		});
		$modifyMenu.find("[name=graduteYear] .value").text(json.graduate_year);
		$modifyMenu.find("[name=graduteYear] .dropdown-item").each(function(i, one) {
			if($(one).text() == json.graduate_year){
				$(one).addClass("dropdown-item-active");
			}
		});
		$modifyMenu.find("[name=education] .value").text(json.education);
		$modifyMenu.find("[name=education] .dropdown-item").each(function(i, one) {
			if($(one).text() == json.education){
				$(one).addClass("dropdown-item-active");
			}
		});
		$modifyMenu.find("[name=graduteCurriculum] .value").text(json.major);
		$modifyMenu.find("[name=graduteCurriculum] .dropdown-item").each(function(i, one) {
			if($(one).text() == json.major){
				$(one).addClass("dropdown-item-active");
			}
		});
		$modifyMenu.find("[name=healthy] .value").text(json.healthy);
		$modifyMenu.find("[name=healthy] .dropdown-item").each(function(i, one) {
			if($(one).text() == json.healthy){
				$(one).addClass("dropdown-item-active");
			}
		});
		$modifyMenu.find("[name=politics] .value").text(json.politics);
		$modifyMenu.find("[name=politics] .dropdown-item").each(function(i, one) {
			if($(one).text() == json.politics){
				$(one).addClass("dropdown-item-active");
			}
		});
		$modifyMenu.find("[name=place] .value").text(json.place);
		$modifyMenu.find("[name=place] .dropdown-item").each(function(i, one) {
			if($(one).text() == json.place){
				$(one).addClass("dropdown-item-active");
			}
		});
		var curriculum = $modifyMenu.find("[name=curriculum] .dropdown-list [data-id='"+json.profession_id+"']").text();
		$modifyMenu.find("[name=curriculum] .value").text(curriculum);
		$modifyMenu.find("[name=curriculum] .dropdown-item").each(function(i, one) {
			if($(one).data("id") == json.profession_id){
				$(one).addClass("dropdown-item-active");
			}
		});
		enroll.loadOrganizationDropWithProfessionDown($modifyMenu);
		var organization = $modifyMenu.find("[name=organization] .dropdown-list [data-id='"+json.organization_id+"']").text();
		$modifyMenu.find("[name=organization] .value").text(organization);
		$modifyMenu.find("[name=organization] .dropdown-item").each(function(i, one) {
			if($(one).data("id") == json.organization_id){
				$(one).addClass("dropdown-item-active");
			}
		});		
	}
	/**
	 * 修改
	 * 3.更新报名记录
	 */
	Enroll.prototype.modifyEnroll = function(json) {
		var $modifyMenu = $("#modifyMenu");
		/*var bool = true;
		var input = $modifyMenu.find(".keyword");
		input.each(function(i,one){
			if(one.id == "pid"){
				bool = bool && checkPid($(one).val());
			}
			else{
				bool = bool && one.checkValidity();
			}
		});	
		if(!bool){
			toastr.warning("输入框内容不能为空！");
			return;
		}
		
		var bool_ = true;
		var dropdownItems = $modifyMenu.find(".dropdown-list");
		dropdownItems.each(function(i, e) {
			var item = $(e).find(".dropdown-item-active");
			if(item.length == 0)
				bool_ = false;
		})
		if(!bool_){
			toastr.warning("下拉框选项不能为空！");
			return;
		}*/
		
		$.ajax({
			"url" : "/stuenroll/enroll/modifyEnroll",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"async": false,
			"success" : function(json) {
				if(json.result == 1)
					toastr.success("成功更新了一条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}
	
//---------------------------------工厂部分-----------------------------------------------------
	
	//工厂方法
	function factory(key) {
		if (key == "Tab") {
			return new Tab();
		}
		else if (key == "Enroll") {
			return new Enroll();
		}
	}
	
	
//-----------------------------------初始化方法部分--------------------------------------------
	

	var tab = factory("Tab");
	var enroll = factory("Enroll");
	var $page = pageFactory();	
	
	//alert($(".tab-list .tab-active").data("index"))
	var index = "全部学员";
	var enrollJson = {}; //保存当前查询所用的数据
	var selectedJson = {}; //保存修改时被选中的记录数据
	var selectedId = -1;
	enroll.searchEnroll(enrollJson, index);
	enroll.searchEnrollCount(enrollJson, index);
	DropDown.initAll();
	

//-----------------------------------事件部分--------------------------------------------------

	//TODO 切换页面显示
	$(".tab-list .tab-item").click(function() {
		index = $(this).data("index");
		tab.showTab(index);
		enrollJson = {};
		// 切换选项卡，重新查询数据
		if (index == "全部学员") {
			if(checkPermission(["3_19"])){
				tabContainer.find(".menu-item[data-operation='学员分班']").show();
			}
			if(checkPermission(["3_20"])){
				tabContainer.find(".menu-item[data-operation='取消分班']").show();
			}
			if(checkPermission(["5_1"])){
				tabContainer.find(".menu-item[data-operation='学员中退']").show();
			}
			if(checkPermission(["5_2"])){
				tabContainer.find(".menu-item[data-operation='取消中退']").show();
			}
			$(".tab-container .tab-content[data-index='全部学员'] #currentPage").text(1);
			enroll.searchEnroll(null, index);
			enroll.searchEnrollCount(null, index);
		}
		if (index == "已分班") {
			$(".tab-container .tab-content[data-index='已分班'] #currentPage").text(1);
			tabContainer.find(".menu-item[data-operation='学员分班']").hide();
			tabContainer.find(".menu-item[data-operation='学员中退']").hide();
			tabContainer.find(".menu-item[data-operation='取消中退']").hide();
			if(checkPermission(["3_20"])){
				tabContainer.find(".menu-item[data-operation='取消分班']").show();
			}
			enrollJson.flag = index;
			enroll.searchEnroll(enrollJson, index);
			enroll.searchEnrollCount(enrollJson, index);
		}
		if (index == "未分班") {
			tabContainer.find(".menu-item[data-operation='取消分班']").hide();
			tabContainer.find(".menu-item[data-operation='学员中退']").hide();
			tabContainer.find(".menu-item[data-operation='取消中退']").hide();
			if(checkPermission(["3_19"])){
				tabContainer.find(".menu-item[data-operation='学员分班']").show();
			}
			$(".tab-container .tab-content[data-index='未分班'] #currentPage").text(1);
			enrollJson.flag = index;
			enroll.searchEnroll(enrollJson, index);
			enroll.searchEnrollCount(enrollJson, index);
		}
		if (index == "中退学员") {
			tabContainer.find(".menu-item[data-operation='取消分班']").hide();
			tabContainer.find(".menu-item[data-operation='学员分班']").hide();
			tabContainer.find(".menu-item[data-operation='学员中退']").hide();
			if(checkPermission(["5_2"])){
				tabContainer.find(".menu-item[data-operation='取消中退']").show();
			}
			$(".tab-container .tab-content[data-index='中退学员'] #currentPage").text(1);
			enrollJson.stateId = 4;
			enroll.searchEnroll(enrollJson, index);
			enroll.searchEnrollCount(enrollJson, index);
		}
	});
	
	
	// 在学员分班面板上点击分班按钮
	tabContainer.find("*[name = 'allot']").click(function() {
		index = $(".tab-list .tab-active").data("index");
		var content = $(".tab-container .tab-content[data-index='"+index+"']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框
		var id = [];
		for (var i = 0; i < checkbox.length; i++) {
			id.push($(checkbox[i]).val());
		}
		var $allotMenu = $("#allotMenu");
		// 再分班
		enroll.allot({
			"id": id,
			"organizationId": $allotMenu.find("[name=organization] .dropdown-item-active").data("id"),
			"professionId": $allotMenu.find("[name=curriculum] .dropdown-item-active").data("id"),
			"classId": $allotMenu.find("[name=class] .dropdown-item-active").data("id"),
			"place": $allotMenu.find("[name=place] .dropdown-item-active").text()
		});
		// 最后查询
		var $currentPage = $(".tab-container .tab-content[data-index='"+index+"'] #currentPage");
		var currentPage = $currentPage.text();
		currentPage = new Number(currentPage);
		enrollJson.page = currentPage;
		enroll.searchEnroll(enrollJson, index);
		//隐藏面板
		$(".tab-container .menu").fadeOut();
		$(".menu").parent().removeClass("item-active");
	});
	
	
	// 在学员分班面板上点击取消分班按钮
	tabContainer.find(".menu-item[data-operation='取消分班']").click(function() {
		var content = $(".tab-container .tab-content[data-index='"+index+"']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框
		// 是否被选中
		if(checkbox.length < 1){
			toastr.warning("至少选中一条记录!");
			return;
		}
		if(enroll.hasAllot() == false){
			toastr.warning("存在未分班的记录!");
			return;
		}
		// 弹出确认对话框
		var bool = confirm("是否取消分班选中的记录？");
		if (bool == false) {
			return;
		}
		var id = [];
		for (var i = 0; i < checkbox.length; i++) {
			id.push($(checkbox[i]).val());
		}		
		// 先分班
		enroll.cancelAllot({
			"id": id
		});
		// 再查询
		var $currentPage = $(".tab-container .tab-content[data-index='"+index+"'] #currentPage");
		var currentPage = $currentPage.text();
		currentPage = new Number(currentPage);
		enrollJson.page = currentPage;
		enroll.searchEnroll(enrollJson, index);
	});

	// 在学员中退面板上点击保存按钮
	tabContainer.find("*[name = 'dropout']").click(function() {
		var content = $(".tab-container .tab-content[data-index='"+index+"']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框
		var id = checkbox.val();
		var $dropoutMenu = $("#dropoutMenu");
		// 再分班
		enroll.quit({
			"id": id,
			"quitReason": $dropoutMenu.find("[name=reason]").val(),
			"quitDate": $dropoutMenu.find("[name=quitDate]").val(),
		});
		// 最后查询
		var $currentPage = $(".tab-container .tab-content[data-index='"+index+"'] #currentPage");
		var currentPage = $currentPage.text();
		currentPage = new Number(currentPage);
		enrollJson.page = currentPage;
		enroll.searchEnroll(enrollJson, index);
		//隐藏面板
		$(".tab-container .menu").fadeOut();
		$(".menu").parent().removeClass("item-active");
	});
	
	
	// TODO 在学员中退面板上点击取消中退按钮
	tabContainer.find(".menu-item[data-operation='取消中退']").click(function() {
		var content = $(".tab-container .tab-content[data-index='"+index+"']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框
		// 是否被选中
		if(checkbox.length < 1){
			toastr.warning("至少选中一条记录!");
			return;
		}
		if(enroll.hasQuit(null) == false){
			toastr.warning("存在未中退的记录!");
			return;
		}
		// 弹出确认对话框
		var bool = confirm("是否取消中退选中的记录？");
		if (bool == false) {
			return;
		}
		var id = [];
		for (var i = 0; i < checkbox.length; i++) {
			id.push($(checkbox[i]).val());
		}		
		// 先分班
		enroll.cancelQuit({
			"id": id
		});
		// 再查询
		var $currentPage = $(".tab-container .tab-content[data-index='"+index+"'] #currentPage");
		var currentPage = $currentPage.text();
		currentPage = new Number(currentPage);
		enrollJson.page = currentPage;
		enroll.searchEnroll(enrollJson, index);
	});
	
//---------------------------增删改查事件---------------------------------
	
	//点击删除按钮
	tabContainer.find(".menu-item[data-operation='删除']").click(function() {
		// 先删除
		enroll.deleteById();
		// 再查询
		enroll.searchEnrollCount(enrollJson, index);
		var $currentPage = $(".tab-container .tab-content[data-index='"+index+"'] #currentPage");
		var currentPage = $currentPage.text();
		var totalPages = $(".tab-container .tab-content[data-index='"+index+"'] #totalPages").text();
		totalPages = new Number(totalPages);
		currentPage = new Number(currentPage);
		if (currentPage > totalPages) {
			currentPage = totalPages;
		}
		$currentPage.text(currentPage); // 更新当前页数
		enrollJson.page = currentPage;
		enroll.searchEnroll(enrollJson, index);
	});
	
	//在查找面板点击查询按钮
	tabContainer.find("*[name = 'search']").click(function(){
		enrollJson = {};
		index = $(".tab-list .tab-active").data("index");
		enrollJson = {
			"name": $("#searchMenu").find("[name=name]").val(),
			"pid":  $("#searchMenu").find("[name=pid]").val(),
			"year": $("#searchMenu").find("[name=year]").val(),
			"sex": $("#searchMenu").find("[name=sex] .dropdown-item-active").text(),
			"education": $("#searchMenu").find("[name=education] .dropdown-item-active").text(),
			"organizationId": $("#searchMenu").find("[name=organization] .dropdown-item-active").data("id"),
			"stateId": $("#searchMenu").find("[name=state] .dropdown-item-active").data("id"),
			"professionId": $("#searchMenu").find("[name=curriculum] .dropdown-item-active").data("id"),
			"classinfoId": $("#searchMenu").find("[name=class]").val(),
			//"flag": index,
			"page": 1
		}
		if(index == "中退学员"){
			enrollJson.stateId = 5;
		}
		$(".tab-container .tab-content[data-index='"+index+"'] #currentPage").text(1);
		
		var count = enroll.searchEnrollCount(enrollJson, index);
		if(count > 0){
			enroll.searchEnroll(enrollJson, index); 
		}
		// 更新当前页数
		//隐藏面板
		$(".tab-container .menu").fadeOut();
		$(".menu").parent().removeClass("item-active");
	});
	
	
	//在添加面板点击保存按钮
	tabContainer.find("*[name = 'saveAdd']").click(function(){
		//alert($addMenu.find("[name=homeTel]").val());
		// 1.先添加记录
		index = $(".tab-list .tab-active").data("index");
		var $addMenu = $("#addMenu");
		var bool = true;
		var input = $addMenu.find(".keyword");
		input.each(function(i,one){
			if(one.id == "pid"){
				bool = bool && checkPid($(one).val());
			}
			else{
				bool = bool && one.checkValidity();
			}
		});	
		if(!bool){
			toastr.warning("输入框内容不能为空！");
			return;
		}
		
		var bool_ = true;
		var dropdownItems = $addMenu.find(".dropdown-list");
		dropdownItems.each(function(i, e) {
			var item = $(e).find(".dropdown-item-active");
			if(item.length == 0)
				bool_ = false;
		})
		if(!bool_){
			toastr.warning("下拉框选项不能为空！");
			return;
		}
		
		var addJson = {
			"name": $addMenu.find("[name=name]").val(),
			"pid":  $addMenu.find("[name=pid]").val(),
			"graduteSchool":  $addMenu.find("[name=graduteSchool]").val(),
			"graduteDate":  $addMenu.find("[name=graduteDate]").val(),
			"residentAddress":  $addMenu.find("[name=residentAddress]").val(),
			"homeAddress":  $addMenu.find("[name=homeAddress]").val(),
			"birthday":  $addMenu.find("[name=birthday]").val(),
			"permanentAddress":  $addMenu.find("[name=permanentAddress]").val(),
			"tel":  $addMenu.find("[name=tel]").val(),
			"homeTel":  $addMenu.find("[name=homeTel]").val(),
			"email":  $addMenu.find("[name=email]").val(),
			"sex": $addMenu.find("[name=sex] .dropdown-item-active").text(),
			"education": $addMenu.find("[name=education] .dropdown-item-active").text(),
			"nation": $addMenu.find("[name=nation] .dropdown-item-active").text(),
			"graduteYear": $addMenu.find("[name=graduteYear] .dropdown-item-active").text(),
			"graduteCurriculum": $addMenu.find("[name=graduteCurriculum] .dropdown-item-active").text(),
			"place": $addMenu.find("[name=place] .dropdown-item-active").text(),
			"healthy": $addMenu.find("[name=healthy] .dropdown-item-active").text(),
			"politics": $addMenu.find("[name=politics] .dropdown-item-active").text(),
			"nation": $addMenu.find("[name=nation] .dropdown-item-active").text(),
			"organizationId": $addMenu.find("[name=organization] .dropdown-item-active").data("id"),
			"professionId": $addMenu.find("[name=curriculum] .dropdown-item-active").data("id"),
		}
		enroll.addEnroll(addJson);
		// 2 再查询总数
		// 2.3 设置页码
		var $currentPage = $(".tab-container .tab-content[data-index='"+index+"'] #currentPage");
		var currentPage = $currentPage.text();
		currentPage = new Number(currentPage);
		enrollJson.page = currentPage;
		enroll.searchEnroll(enrollJson, index);
		//隐藏面板
		$(".tab-container .menu").fadeOut();
		$(".menu").parent().removeClass("item-active");
	});
	
	
	//在修改面板点击保存按钮
	tabContainer.find("*[name = 'saveModify']").click(function(){
		// 1.先添加记录
		var $modifyMenu = $("#modifyMenu");
		var modifyJson = {
			"id": selectedId,
			"name": $modifyMenu.find("[name=name]").val(),
			"pid":  $modifyMenu.find("[name=pid]").val(),
			"graduteSchool":  $modifyMenu.find("[name=graduteSchool]").val(),
			"graduteDate":  $modifyMenu.find("[name=graduteDate]").val(),
			"residentAddress":  $modifyMenu.find("[name=residentAddress]").val(),
			"homeAddress":  $modifyMenu.find("[name=homeAddress]").val(),
			"birthday":  $modifyMenu.find("[name=birthday]").val(),
			"permanentAddress":  $modifyMenu.find("[name=permanentAddress]").val(),
			"tel":  $modifyMenu.find("[name=tel]").val(),
			"homeTel":  $modifyMenu.find("[name=homeTel]").val(),
			"email":  $modifyMenu.find("[name=email]").val(),
			"sex": $modifyMenu.find("[name=sex] .dropdown-item-active").text(),
			"education": $modifyMenu.find("[name=education] .dropdown-item-active").text(),
			"nation": $modifyMenu.find("[name=nation] .dropdown-item-active").text(),
			"graduteYear": $modifyMenu.find("[name=graduteYear] .dropdown-item-active").text(),
			"graduteCurriculum": $modifyMenu.find("[name=graduteCurriculum] .dropdown-item-active").text(),
			"place": $modifyMenu.find("[name=place] .dropdown-item-active").text(),
			"healthy": $modifyMenu.find("[name=healthy] .dropdown-item-active").text(),
			"politics": $modifyMenu.find("[name=politics] .dropdown-item-active").text(),
			"nation": $modifyMenu.find("[name=nation] .dropdown-item-active").text(),
			"organizationId": $modifyMenu.find("[name=organization] .dropdown-item-active").data("id"),
			"professionId": $modifyMenu.find("[name=curriculum] .dropdown-item-active").data("id"),
		}
		console.log(modifyJson);
		enroll.modifyEnroll(modifyJson);
		// 2 再查询总数
		// 2.3 设置页码
		var $currentPage = $(".tab-container .tab-content[data-index='"+index+"'] #currentPage");
		var currentPage = $currentPage.text();
		currentPage = new Number(currentPage);
		enrollJson.page = currentPage;
		enroll.searchEnroll(enrollJson, index);
		//隐藏面板
		$(".tab-container .menu").fadeOut();
		$(".menu").parent().removeClass("item-active");
	});
	
	//在查找面板点击清除按钮
	tabContainer.find("*[name = 'clear']").click(function(){
		enroll.clearData();
	});
	
	// 输入项键盘弹起事件
	$(".menu .keyword").keyup(function() {
		if(this.id != "pid" && this.checkValidity()){
			$(this).removeClass("error");
		}
		else{
			$(this).addClass("error");
		}
		
		if(this.id == "pid"){
			if(checkPid($(this).val())){
				$(this).removeClass("error");
			}
			else{
				$(this).addClass("error");
			}
		}
	});
	
//-----------------------------------分页部分--------------------------------------------------
	

	var element = $(".tab-container .tab-content");
	/**
	 * 下一页翻页效果
	 */
	element.find("*[name='nextBtn']").click(function() {
		index = $(this).parents(".tab-content").data("index");
		var currentPage = $(".tab-container .tab-content[data-index='"+index+"'] #currentPage");
		var totalPages = $(".tab-container .tab-content[data-index='"+index+"'] #totalPages");
		$page.nextPage(this, 5, currentPage, totalPages, enrollJson, index, enroll.searchEnroll);
	});
	
	/**
	 * 上一页翻页效果
	 */
	element.find("*[name='prevBtn']").click(function() {
		index = $(this).parents(".tab-content").data("index");
		var currentPage = $(".tab-container .tab-content[data-index='"+index+"'] #currentPage");
		var totalPages = $(".tab-container .tab-content[data-index='"+index+"'] #totalPages");
		$page.previousPage(currentPage, this, 5 , enrollJson, index, enroll.searchEnroll);
	});
	
	/**
	 * 点击页面跳转效果
	 */
	element.on("click","a[name='page-number']", function() {
		index = $(this).parents(".tab-content").data("index");
		var currentPage = $(".tab-container .tab-content[data-index='"+index+"'] #currentPage");
		$page.changePage(this, currentPage, enrollJson, index, enroll.searchEnroll);
	});
	
	
	//---------------------菜单切换部分------------------------------
	
	//点击不同的操作按钮切换相应的菜单
	$(".tab-container .menu-item").click(function(event) {
		var obj = $(this).find(".menu");
		if(obj.length == 0)
			return;
		enroll.loadProfessionDropDown(obj);
		$(".menu").fadeOut();
		obj.parent().siblings().removeClass("item-active"); //取消所有选项
		obj.parent().addClass("item-active"); //选项被选中
		if(obj[0].id == "modifyMenu"){
			enroll.clearData();
			var bool = enroll.searchEnrollById();
			if(bool)
				obj.fadeIn();
		}	
		else if(obj[0].id == "allotMenu"){
			// 先检查是否存在已分班的记录
			var bool = enroll.hasAllot();
			if(bool == -1)
				return;
			if(bool){
				toastr.warning("存在已分班的数据！");
				return;
			}
			enroll.clearData();
			enroll.loadOrganizationDropDown();
			obj.fadeIn(); //菜单出现
		}
		else if(obj[0].id == "dropoutMenu"){
			// 先检查是否存在已中退的记录
			var bool = enroll.hasQuit("quit");
			if(bool == -1)
				return;
			if(bool){
				toastr.warning("存在已中退的数据！");
				return;
			}
			enroll.clearData();
			obj.fadeIn(); //菜单出现
		}
		else{
			obj.fadeIn(); //菜单出现
			enroll.clearData();
		}	
		event.stopPropagation();//阻止把事件分派到其他节点
	});

	//点击面板
	$(".menu").click(function(event) {
		event.stopPropagation();
	});

	//点击空白处
	$(document).click(function() {
		var $obj = $(".menu");
		$obj.fadeOut();
		$obj.parent().removeClass("item-active");
		DropDown.closeDropDown();
	});

});