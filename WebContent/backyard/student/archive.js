$(function(){
	
/*------------------------------权限验证部分------------------------------------*/

	/**
	 * 验证用户是否拥有查询归档记录的权限
	 */
	if(!checkPermission(["4_4"])){
		return;
	}
	var operation = $(".profession-tab-container");
	if(checkPermission(["4_4"])){
		$(".operation-item[name='search']").show();
	}
	/**
	 * 验证用户是否拥有添加归档记录的权限
	 */
	if(checkPermission(["4_1"])){
		$(".operation-item[name='add']").show();
	}
	/**
	 * 验证用户是否拥有修改归档记录的权限
	 */
	if(checkPermission(["4_2"])){
		$(".operation-item[name='update']").show();
	}
	/**
	 * 验证用户是否拥有删除归档记录的权限
	 */
	if(checkPermission(["4_3"])){
		$(".operation-item[name='delete']").show();
	}
	
	
/*------------------------------------接口部分-------------------------------*/

	/*
	 * 归档管理抽象接口
	 */
	var I_Archive = function(){
	
	}
	
	I_Archive.prototype = new I_Archive();
	/**
	 * 查询归档记录抽象方法
	 */
	I_Archive.prototype.searchArchive = function(json,index){
		throw "抽象方法";
	}
	/**
	 * 查询归档记录数量抽象方法
	 */
	I_Archive.prototype.searchArchiveCount = function(json,index){
		throw "抽象方法";
	}
	/**
	 *添加归档记录抽象方法
	 */
	I_Archive.prototype.addArchive = function(json){
		throw "抽象方法";
	}
	/**
	 * 修改归档记录抽象方法
	 */
	I_Archive.prototype.updateArchive = function(json){
		throw "抽象方法";
	}
	/**
	 * 删除归档记录抽象方法
	 */
	I_Archive.prototype.deleteArchiveById = function(id){
		throw "抽象方法";
	}
	/**
	 * 查询专业
	 */
	I_Archive.prototype.searchProfessions = function(object){
		throw "抽象方法";
	}
	/**
	 * 根据专业查询机构
	 */
	I_Archive.prototype.searchOrganizationWithProfession = function(object){
		throw "抽象方法";
	}
	/**
	 * 查询学生状态
	 */
	I_Archive.prototype.searchStudentState = function(object){
		throw "抽象方法";
	}
	/**
	 * 查询班级信息
	 */
	I_Archive.prototype.searchClassInfo = function(object){
		throw "抽象方法";
	}

/*-------------------------------定义对象及下拉列表数组----------------------------------*/
	
	var DropDownProfessionFlag = false;
	var searchJson = {};//定义查询条件json
	var operationList = $(".tab-container .tab-content .operation-list");
	var menus = MenuFactory("Menu");//定义菜单面板对象
	var $page = pageFactory();//定义页码对象
	var tab = TabFactory();
	var sexValue = new Array("男","女");
	var healthyValue = new Array("健康","残疾");
	var educationValue = new Array("大专","本科","硕士","博士");
	var nationValue = new Array("阿昌族","白族","保安族","布朗族","布依族","朝鲜族","达斡尔族","傣族","德昂族","东乡族","侗族","独龙族","俄罗斯族","鄂温克族","高山族","仡佬族","汉族","哈尼族","哈萨克族","赫哲族","回族","基诺族","景颇族","京族","柯尔克孜族","拉祜族","珞巴族","僳僳族","黎族","满族","毛南族","门巴族","蒙古族","苗族","仫佬族","纳西族","怒族","普米族","羌族","撒拉族","畲族","水族","塔吉克族","塔塔尔族","土家族","土族","佤族","维吾尔族","乌孜别克族","锡伯族","瑶族","彝族","裕固族","藏族","壮族");
	var majorValue = new Array("文学","历史学","理学","工学","农学","医学","军事学","管理学");
	var politicsValue = new Array("中共党员","中共预备党员","共青团员","民革党员","民盟盟员","民建会员","民进会员","农工党党员","致公党党员","九三学社社员","台盟盟员","无党派民主人士","群众");
	var placeValue = new Array("沈阳","大连","鞍山");
	var date = new Date();
	var years = date.getFullYear();
	var yearValue = new Array();
	for(var i=0;i<10;i++){
		yearValue[i] = years-i;
	}	
		
/*---------------------------------加载数组数据到下拉框----------------------------------*/

	//性别选择下拉框
	for(var i=0;i<sexValue.length;i++){
		$("*[name='sex'] .dropdown-list").append("<li class='dropdown-item'>"+sexValue[i]+"</li>");
	}
	//健康状况选择下拉框
	for(var i=0;i<healthyValue.length;i++){
		$("*[name='health'] .dropdown-list").append("<li class='dropdown-item'>"+healthyValue[i]+"</li>");
	}
	//学历选择下拉框
	for(var i=0;i<educationValue.length;i++){
		$("*[name='education'] .dropdown-list").append("<li class='dropdown-item'>"+educationValue[i]+"</li>");
	}
	//民族选择下拉框
	for(var i=0;i<nationValue.length;i++){
		$("*[name='nation'] .dropdown-list").append("<li class='dropdown-item'>"+nationValue[i]+"</li>");
	}
	//所学专业选择下拉框
	for(var i=0;i<majorValue.length;i++){
		$("*[name='major'] .dropdown-list").append("<li class='dropdown-item'>"+majorValue[i]+"</li>");
	}
	//政治面貌选择下拉框
	for(var i=0;i<politicsValue.length;i++){
		$("*[name='politics'] .dropdown-list").append("<li class='dropdown-item'>"+politicsValue[i]+"</li>");
	}
	//培训地点选择下拉框
	for(var i=0;i<placeValue.length;i++){
		$("*[name='place'] .dropdown-list").append("<li class='dropdown-item'>"+placeValue[i]+"</li>");
	}
	//毕业年份选择下拉框
	for(var i=0;i<yearValue.length;i++){
		$("*[name='graduateYear'] .dropdown-list").append("<li class='dropdown-item'>"+yearValue[i]+"</li>");
	}

/*-------------------------------------归档管理接口实现---------------------------------*/


	var Archive = function(){
		
	}
	Archive.prototype = new Archive();
	Archive.prototype.searchArchive = function(json,index){
		$.ajax({
			"url":"/stuenroll/archive/searchArchive",
			"type":"post",
			"dataType":"json",
			"async":false,
			"data":json,
			"success":function(json){
				var data = json.result;
				var content;
				if(index == "全部学员"){
					content = $(".tab-container .tab-content[data-index='全部学员']");					
				}
				else if(index == "中退学员"){
					content = $(".tab-container .tab-content[data-index='中退学员']");					
				}
				var table = content.find(".data-table");
				table.find("tr:gt(0)").remove();
				var currentPage = content.find(".page-list .page-info #currentPage").text();
				currentPage = new Number(currentPage);
				var start = (currentPage - 1) * 35;
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td class='td'><input type='checkbox' name='id' value='" + one.id + "' /></td>"
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td>" + one.name + "</td>";
					temp += "<td>" + one.pid + "</td>";
					temp += "<td>" + one.tel + "</td>";
					temp += "<td>" + one.organization + "</td>";
					temp += "<td>" + one.profession + "</td>";
					temp += "<td>" + one.classinfo + "</td>";
					temp += "<td>" + one.year + "</td>";
					if(index == "全部学员"){
						temp += "<td>已归档</td>";
					}
					else if(index == "中退学员"){
						temp += "<td>已中退</td>";
					}
					temp += "</tr>";
				}
				table.append(temp);
			},
			"error":function(){
				toastr.error("系统异常");
			}
		});
	}
	
	Archive.prototype.searchArchiveCount = function(json,index){
		$.ajax({
			"url":"/stuenroll/archive/searchArchiveCount",
			"type":"post",
			"dataType":"json",
			"data":json,
			"async":false,
			"success":function(json){
				var count = json.result;
				var content;
				if(index == "全部学员"){
					content = $(".tab-container .tab-content[data-index='全部学员']");					
				}
				else if(index == "中退学员"){
					content = $(".tab-container .tab-content[data-index='中退学员']");					
				}
				$page.showTotal(content.find(".page-list .page-item #totalRows"),count,35,content.find(".page-list .page-item #totalPages"));
				var prevBtn = content.find(".page-list .page-item a[data-btn='prevBtn']");
				var totalPages = content.find(".page-list .page-item #totalPages").text();
				totalPages = new Number(totalPages);
				if(count == 0){
					$page.insertPageNumberByMin(prevBtn,1,1);
				}
				else{
					if(totalPages < 5){
						$page.insertPageNumberByMin(prevBtn,1,totalPages);
					}
					else{
						$page.insertPageNumberByMin(prevBtn,1,5);
					}
				}
				var a = prevBtn.siblings();
				$(a[0]).addClass("page-active");
			},
			"error":function(){
				toastr.error("系统异常");
			}
		});
	}
	//TODO 添加
	Archive.prototype.addArchive = function(json){
		$.ajax({
			"url":"/stuenroll/archive/addArchive",
			"type":"post",
			"async":false,
			"dataType":"json",
			"data":json,
			"success":function(json){
				if(json.result == 1){
					toastr.success("添加记录成功");
				}
				else{
					toastr.error("添加记录失败");
				}
			},
			"error":function(){
				toastr.error("系统异常");
			}
		});
	}
	//TODO 修改
	Archive.prototype.updateArchive = function(json){
		$.ajax({
			"url":"/stuenroll/archive/updateArchive",
			"type":"post",
			"async":false,
			"dataType":"json",
			"data":json,
			"success":function(json){
				if(json.result == 1){
					toastr.success("修改记录成功");
				}
				else{
					toastr.error("修改记录失败");
				}
			},
			"error":function(){
				toastr.error("系统异常");
			}
		});
	}
	//TODO 删除
	Archive.prototype.deleteArchiveById = function(id){
		// 弹出确认对话框
		var bool = confirm("是否删除选中的记录？");
		if (bool == false) {
			return;
		}
		$.ajax({
			"url" : "/stuenroll/archive/deleteArchiveById",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true,	//发送数组JSON格式
			"async":false,
			"data" : {
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
	//加载数据到专业下拉选择框
	Archive.prototype.searchProfession = function(object){
		var $profession = $(object).find("[name='professionId']");
		var $organization = $(object).find("[name='organizationID']");	
		$profession.find(".dropdown-item").remove();//清空下拉框的数据
		$.ajax({
			"url":"/stuenroll/profession/searchAllProfession",
			"type":"post",
			"dataType":"json",
			"async":false,
			"data":null,
			"success":function(json){
				var data = json.result;
				var temp = "";
				for (var i=0;i<data.length;i++) {
					var one = data[i];
					temp += "<li class='dropdown-item' data-id="+one.id+">"+one.name+"</li>";
				}
				$profession.find(".dropdown-list").append(temp);//加载数据到申报专业下拉列表
				DropDown.init($profession);//初始化下拉列表
				// 专业列表选项点击事件
				$profession.click(function(){
					$organization.find(".value").text("- 选择 -");//点击专业下拉框清空机构下拉框
//					$profession.find(".dropdown-item").remove();//清空下拉框的数据
				});
				$profession.find(".dropdown-item").click(function(event) {	
        			DropDownProfessionFlag = true;
        			archive.searchOrganizationWithProfession(object);// 选中专业后更新机构列表
				});						
			},
			"error":function(){
				toastr.error("系统异常");
			}
			
		});
	}
	Archive.prototype.searchOrganizationWithProfession = function(object){
		var $organization = $(object).find("[name='organizationID']");	
		//获取所选专业ID
		var activeProfessionId = $("*[name='professionId'] .dropdown-list .dropdown-item-active").data("id");
		$organization.find(".dropdown-item").remove();//清空下拉框中的数据
		$.ajax({
			"url":"/stuenroll/organTransform/searchOrganizationWithProfession",
			"type":"post",
			"dataType":"json",
			"async":false,
			"data":{
				"professionId":activeProfessionId
			},
			"success":function(json){
				var data = json.result;
				var temp = "";
				for (var i=0;i<data.length;i++) {
					var one = data[i];
					temp += "<li class='dropdown-item' data-id="+one.id+">"+one.name+"</li>";
				}
				if(DropDownProfessionFlag){
					$organization.find(".dropdown-list").append(temp);//加载数据到培训机构下拉列表
				}
				$organization.click(function(){
					if(!DropDownProfessionFlag){
						toastr.warning("请先选择专业！");
						return;
					}
					DropDown.init($organization);//初始化下拉列表			
				});
			},
			"error":function(){
				toastr.error("系统异常");
			}
		});
	}
	Archive.prototype.searchStudentState = function(object){
		var $studentState = $(object).find("[name='student_state']");
		$studentState.find(".dropdown-item").remove();
		$.ajax({
			"url":"/stuenroll/archive/searchStudentState",
			"type":"post",
			"dataType":"json",
			"async":false,
			"data":null,
			"success":function(json){
				var data = json.result;
				var temp = "";
				for (var i=0;i<data.length;i++) {
					var one = data[i];
					temp += "<li class='dropdown-item' data-id="+one.id+">"+one.name+"</li>";
				}
				$studentState.find(".dropdown-list").append(temp);
				DropDown.init($studentState);
				
			},
			"error":function(){
				toastr.error("系统异常");
			}
		});
	}
	Archive.prototype.searchClassInfo = function(object){
		var $classInfo = $(object).find("[name='classInfoId']");
		$classInfo.find(".dropdown-item").remove();
		$.ajax({
			"url":"/stuenroll/archive/searchClassInfo",
			"type":"post",
			"dataType":"json",
			"async":false,
			"data":null,
			"success":function(json){
				var data = json.result;
				var temp = "";
				for (var i=0;i<data.length;i++) {
					var one = data[i];
					temp += "<li class='dropdown-item' data-id="+one.id+">"+one.name+"</li>";
				}
				$classInfo.find(".dropdown-list").append(temp);
				DropDown.init($classInfo);
			},
			"error":function(){
				toastr.error("系统异常");
			}
		});
	}

/*------------------------------------工厂方法及函数调用---------------------------------------*/
	
	function factory(key){
		if(key == "Archive"){
			return new Archive();
		}
	}
	var archive = factory("Archive");
	//初始化菜单面板
	menus.init();
	archive.searchArchive(searchJson,"全部学员");
	archive.searchArchiveCount(searchJson,"全部学员");

/*-----------------------------------点击切换面板--------------------------------------*/

	$(".main-container .tab-list .tab-item").click(function(){
		var temp = $(this).data("index");
		tab.showTab(temp);
		if (temp == "全部学员") {
			$(".tab-container .tab-content[data-index='全部学员'] #currentPage").text(1);
			//清除数据，重新查询全部学员
			searchJson = {};
			archive.searchArchive(searchJson,"全部学员");
			archive.searchArchiveCount(searchJson,"全部学员");
		} 
		if(temp == "中退学员"){
			$(".tab-container .tab-content[data-index='中退学员'] #currentPage").text(1);
			//清除数据，重新查询中退学员
			searchJson = {"stateId":4};
			archive.searchArchive(searchJson,"中退学员");
			archive.searchArchiveCount(searchJson,"中退学员");
		}
	});
	
/*-----------------------------------点击弹出面板---------------------------------------*/

	//点击弹出查询面板
	operationList.parent().find("[name='search']").click(function(event){
		menus.show(this,event);
		DropDownProfessionFlag = false;
		var index = $(this).parents(".tab-content").data("index");
		//初始化各种下拉列表
		DropDown.initAll();
		archive.searchClassInfo(this);
		if(index == "中退学员"){
			//找到学生状态的下拉框并删除数据，设置学生状态的选项为中退
			var studentState = $(this).find("[name='student_state'] .dropdown-list");
			studentState.find(".dropdown-item").remove();
			studentState.append("<li class='dropdown-item' data-id='"+4+"'>中退</li>");
		}
		else if(index == "全部学员"){
			archive.searchStudentState(this);			
		}
		archive.searchProfession(this);
		archive.searchOrganizationWithProfession(this);
		//设置下拉框的初始值
		var condition = $(this).find(".condition-list .condition .value").text("- 选择 -");
	});
	//点击弹出添加面板
	operationList.parent().find("[name='add']").click(function(event){
		menus.show(this,event);
		DropDownProfessionFlag = false;
		archive.searchClassInfo(this);
		var index = $(this).parents(".tab-content").data("index");
		//初始化各种下拉列表
		DropDown.initAll();
		archive.searchClassInfo();
		if(index == "中退学员"){
			//找到学生状态的下拉框并删除数据
			var studentState = $(this).find("[name='student_state'] .dropdown-list");
			studentState.find(".dropdown-item").remove();
			studentState.append("<li class='dropdown-item' data-id='"+4+"'>中退</li>");
		}
		else if(index == "全部学员"){
			archive.searchStudentState(this);			
		}
		archive.searchProfession(this);
		archive.searchOrganizationWithProfession(this);
		//设置下拉框的初始值
		var condition = $(this).find(".condition-list .condition .value").text("- 选择 -");
	});
	
	//点击弹出修改面板
	operationList.parent().find("[name='update']").click(function(event){
		var index = $(this).parents(".tab-content").data("index");
		if(index == "全部学员"){
			content = $(".tab-container .tab-content[data-index='全部学员']");					
		}
		else if(index == "中退学员"){
			content = $(".tab-container .tab-content[data-index='中退学员']");					
		}
		var checkbox = content.find("[name='id']:checked"); // 被选中的复选框
		var id = checkbox.val();
		//判断被选中的复选框的数量
		if(checkbox.length == 0){
			toastr.warning("请选择您要修改的记录");
			return;
		}
		else if(checkbox.length > 1){
			toastr.warning("您只能修改一条记录");
			return;
		}
		//向后台发送ajax获取信息
		menus.show(this,event);
		DropDownProfessionFlag = true;
		$.ajax({
			"url" : "/stuenroll/archive/searchArchive",
			"type" : "post",
			"dataType" : "json",
			"async":false,
			"data" : {
				"id":id
			},
			"success" : function(json) {
				//向修改面板的文本框中自动填写相关信息
				var data = json.result;
				var condition = content.find("[name='updateMenu']");
				condition.find("[name='name']").val(data[0].name);
				condition.find("[name='sex'] .value").text(data[0].sex);
				condition.find("[name='nation'] .value").text(data[0].nation);
				condition.find("[name='pid']").val(data[0].pid);
				condition.find("[name='graduateSchool']").val(data[0].graduate_school);
				condition.find("[name='graduateYear'] .value").text(data[0].graduate_year);
				condition.find("[name='graduateDate']").val(data[0].graduate_date);
				condition.find("[name='education'] .value").text(data[0].education);
				condition.find("[name='major'] .value").text(data[0].major);
				condition.find("[name='health'] .value").text(data[0].healthy);
				condition.find("[name='politics'] .value").text(data[0].politics);
				condition.find("[name='birthday']").val(data[0].birthday);
				condition.find("[name='resident_address']").val(data[0].resident_address);
				condition.find("[name='home_address']").val(data[0].home_address);
				condition.find("[name='permanent_address']").val(data[0].permanent_address);
				condition.find("[name='tel']").val(data[0].tel);
				condition.find("[name='home_tel']").val(data[0].home_tel);
				condition.find("[name='email']").val(data[0].email);
				condition.find("[name='year']").val(data[0].year);
				condition.find("[name='professionId'] .value").text(data[0].profession);
				condition.find("[name='organizationID'] .value").text(data[0].organization);
				condition.find("[name='classInfoId'] .value").text(data[0].classinfo);
				condition.find("[name='student_state'] .value").text(data[0].state);
				condition.find("[name='place'] .value").text(data[0].place);
				condition.find("[name='remark']").val(data[0].remark);
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});		
		
		//初始化各种下拉列表
		DropDown.initAll();
			
		archive.searchClassInfo(this);
		if(index == "中退学员"){
			//找到学生状态的下拉框并删除数据
			var studentState = $(this).find("[name='student_state'] .dropdown-list");
			studentState.find(".dropdown-item").remove();
			studentState.append("<li class='dropdown-item' data-id='"+4+"'>中退</li>");
		}
		else if(index == "全部学员"){
			archive.searchStudentState(this);			
		}
		archive.searchProfession(this);
		
		//将下拉框中与所要修改学生的信息相同的值设为dropdown-item-active
		var professionValue = new Array();
		professionValue = $(this).find(".condition-list .condition [name='professionId'] .dropdown-item");	
		for(var i=0;i<professionValue.length;i++){
			var one = professionValue[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='professionId'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
		archive.searchOrganizationWithProfession(this);
		
		var sexVal = new Array();
		sexVal = $(this).find(".condition-list .condition [name='sex'] .dropdown-item");
		for(var i=0;i<sexVal.length;i++){
			var one = sexVal[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='sex'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
		var nationVal = new Array();
		nationVal = $(this).find(".condition-list .condition [name='nation'] .dropdown-item");
		for(var i=0;i<nationVal.length;i++){
			var one = nationVal[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='nation'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
		var yearVal = new Array();
		yearVal = $(this).find(".condition-list .condition [name='graduateYear'] .dropdown-item");
		for(var i=0;i<yearVal.length;i++){
			var one = yearVal[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='graduateYear'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
		var educationVal = new Array();
		educationVal = $(this).find(".condition-list .condition [name='education'] .dropdown-item");
		for(var i=0;i<educationVal.length;i++){
			var one = educationVal[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='education'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
		var majorVal = new Array();
		majorVal = $(this).find(".condition-list .condition [name='major'] .dropdown-item");
		for(var i=0;i<majorVal.length;i++){
			var one = majorVal[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='major'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
		var healthyVal = new Array();
		healthyVal = $(this).find(".condition-list .condition [name='health'] .dropdown-item");
		for(var i=0;i<healthyVal.length;i++){
			var one = healthyVal[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='health'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
		var politicsVal = new Array();
		politicsVal = $(this).find(".condition-list .condition [name='politics'] .dropdown-item");
		for(var i=0;i<politicsVal.length;i++){
			var one = politicsVal[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='politics'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
		var placeVal = new Array();
		placeVal = $(this).find(".condition-list .condition [name='place'] .dropdown-item");
		for(var i=0;i<placeVal.length;i++){
			var one = placeVal[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='place'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
		var classValue = new Array();
		classValue = $(this).find(".condition-list .condition [name='classInfoId'] .dropdown-item");	
		for(var i=0;i<classValue.length;i++){
			var one = classValue[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='classInfoId'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
		var stateValue = new Array();
		stateValue = $(this).find(".condition-list .condition [name='student_state'] .dropdown-item");	
		for(var i=0;i<stateValue.length;i++){
			var one = stateValue[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='student_state'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}		
		
		var organizationValue = new Array();
		organizationValue = $(this).find(".condition-list .condition [name='organizationID'] .dropdown-item");	
		for(var i=0;i<organizationValue.length;i++){
			var one = organizationValue[i];
			if($(one).text() == $(this).find(".condition-list .condition [name='organizationID'] .value").text()){
				$(one).addClass("dropdown-item-active");
			}
		}
	});
	
	// 输入框keyup事件
	$(".main-container .tab-container input[type='text']").keyup(function() {
		if (this.name != "pid" && this.checkValidity()) {
			$(this).removeClass("error");
		}
		else {
			$(this).addClass("error");
		}

		if (this.name == "pid") {
			if (checkPid($(this).val())) {
				$(this).removeClass("error");
			}
			else {
				$(this).addClass("error");
			}
		}
	});
	//电子信箱keyup事件
	$(".main-container .tab-container input[type='email']").keyup(function() {
		if (this.checkValidity()) {
			$(this).removeClass("error");
		} else {
			$(this).addClass("error");
		}
	});
	
	
/*-----------------------------------增删改查点击事件---------------------------------------*/
	
	//点击查询按钮
	operationList.parent().find("[name='search_save']").click(function(){
		var flag = $(this).parents(".tab-content");
		var index = flag.data("index");
		//将查询条件打包成json格式
		searchJson = {
			"name":flag.find("[name=searchMenu] [name='name']").val(),
			"pid":flag.find("[name=searchMenu] [name='pid']").val(),
			"year":flag.find("[name=searchMenu] [name='year']").val(),
			"sex":flag.find("[name=searchMenu] [name='sex'] .dropdown-item-active").text(),
			"education":flag.find("[name=searchMenu] [name='education'] .dropdown-item-active").text(),
			"organizationID":flag.find("[name=searchMenu] [name='organizationID'] .dropdown-item-active").data("id"),
			"professionId":flag.find("[name=searchMenu] [name='professionId'] .dropdown-item-active").data("id"),
			"classInfoId":flag.find("[name=searchMenu] [name='classInfoId'] .dropdown-item-active").data("id"),
			"stateId":flag.find("[name=searchMenu] [name='student_state'] .dropdown-item-active").data("id"),
			"page":1
		};
		//隐藏菜单面板
		menus.hide();
		var currentPage = $(this).parents(".tab-content").find("#currentPage").text(1);
		currentPage = new Number(currentPage);
		//重新查询数据
		archive.searchArchiveCount(searchJson,index);
		archive.searchArchive(searchJson,index);
	});
	// 点击添加保存按钮
	operationList.parent().find("[name='add_save']").click(function(){		
		var flag = $(this).parents(".tab-content");
		var index = flag.data("index");
		//验证输入框中内容是否为空
		var bool = true;
		var input = flag.find("[name='addMenu'] .condition .keyword");
		input.each(function(i, one){
			bool = bool && one.checkValidity();
		});
		
		if(!bool){
			toastr.warning("请将内容填写完整");
			return;
		}
		var addJson = "";
		addJson = {
				"name":flag.find("[name='addMenu'] [name='name']").val(),
				"sex":flag.find("[name='addMenu'] [name='sex'] .dropdown-item-active").text(),
				"nation":flag.find("[name='addMenu'] [name='nation'] .dropdown-item-active").text(),
				"pid":flag.find("[name='addMenu'] [name='pid']").val(),
				"graduateSchool":flag.find("[name='addMenu'] [name='graduateSchool']").val(),
				"graduateYear":flag.find("[name='addMenu'] [name='graduateYear'] .dropdown-item-active").text(),
				"graduateDate":flag.find("[name='addMenu'] [name='graduateDate']").val(),
				"education":flag.find("[name='addMenu'] [name='education'] .dropdown-item-active").text(),
				"major":flag.find("[name='addMenu'] [name='major'] .dropdown-item-active").text(),
				"healthy":flag.find("[name='addMenu'] [name='health'] .dropdown-item-active").text(),
				"politics":flag.find("[name='addMenu'] [name='politics'] .dropdown-item-active").text(),
				"birthday":flag.find("[name='addMenu'] [name='birthday']").val(),
				"resident_address":flag.find("[name='addMenu'] [name='resident_address']").val(),
				"home_address":flag.find("[name='addMenu'] [name='home_address']").val(),
				"permanent_address":flag.find("[name='addMenu'] [name='permanent_address']").val(),
				"tel":flag.find("[name='addMenu'] [name='tel']").val(),
				"home_tel":flag.find("[name='addMenu'] [name='home_tel']").val(),
				"email":flag.find("[name='addMenu'] [name='email']").val(),
				"organizationID":flag.find("[name='addMenu'] [name='organizationID'] .dropdown-item-active").data("id"),
				"professionId":flag.find("[name='addMenu'] [name='professionId'] .dropdown-item-active").data("id"),
				"stateId":flag.find("[name='addMenu'] [name='student_state'] .dropdown-item-active").data("id"),
				"classInfoId":flag.find("[name='addMenu'] [name='classInfoId'] .dropdown-item-active").data("id"),
				"place":flag.find("[name='addMenu'] [name='place'] .dropdown-item-active").text(),
				"year":flag.find("[name='addMenu'] [name='year']").val(),
				"remark":flag.find("[name='addMenu'] [name='remark']").val()
		};
		//隐藏面板
		menus.hide();
		//执行添加函数
		archive.addArchive(addJson);
		//重新查询数据
		setTimeout(function() {
			archive.searchArchiveCount(searchJson,index);
			archive.searchArchive(searchJson,index);			
		}, 1000);
	});
	operationList.parent().find("[name='update_save']").click(function(){
		//验证输入框中内容是否为空
		var bool = true;
		var input = $(this).find(".keyword");
		input.each(function(i, one){
			bool = bool && one.checkValidity();
		});
		if(!bool){
			toastr.warning("请将内容填写完整");
			return;
		}
		var flag = $(this).parents(".tab-content");
		var checkbox = flag.find("[name='id']:checked");
		var id = checkbox.val();
		var index = flag.data("index");
		var updateJson = "";
		updateJson = {
				"id":id,
				"name":flag.find("[name='updateMenu'] [name='name']").val(),
				"sex":flag.find("[name='updateMenu'] [name='sex'] .dropdown-item-active").text(),
				"nation":flag.find("[name='updateMenu'] [name='nation'] .dropdown-item-active").text(),
				"pid":flag.find("[name='updateMenu'] [name='pid']").val(),
				"graduateSchool":flag.find("[name='updateMenu'] [name='graduateSchool']").val(),
				"graduateYear":flag.find("[name='updateMenu'] [name='graduateYear'] .dropdown-item-active").text(),
				"graduateDate":flag.find("[name='updateMenu'] [name='graduateDate']").val(),
				"education":flag.find("[name='updateMenu'] [name='education'] .dropdown-item-active").text(),
				"major":flag.find("[name='updateMenu'] [name='major'] .dropdown-item-active").text(),
				"healthy":flag.find("[name='updateMenu'] [name='health'] .dropdown-item-active").text(),
				"politics":flag.find("[name='updateMenu'] [name='politics'] .dropdown-item-active").text(),
				"birthday":flag.find("[name='updateMenu'] [name='birthday']").val(),
				"resident_address":flag.find("[name='updateMenu'] [name='resident_address']").val(),
				"home_address":flag.find("[name='updateMenu'] [name='home_address']").val(),
				"permanent_address":flag.find("[name='updateMenu'] [name='permanent_address']").val(),
				"tel":flag.find("[name='updateMenu'] [name='tel']").val(),
				"home_tel":flag.find("[name='updateMenu'] [name='home_tel']").val(),
				"email":flag.find("[name='updateMenu'] [name='email']").val(),
				"organizationID":flag.find("[name='updateMenu'] [name='organizationID'] .dropdown-item-active").data("id"),
				"professionId":flag.find("[name='updateMenu'] [name='professionId'] .dropdown-item-active").data("id"),
				"stateId":flag.find("[name='updateMenu'] [name='student_state'] .dropdown-item-active").data("id"),
				"classInfoId":flag.find("[name='updateMenu'] [name='classInfoId'] .dropdown-item-active").data("id"),
				"place":flag.find("[name='updateMenu'] [name='place'] .dropdown-item-active").text(),
				"year":flag.find("[name='updateMenu'] [name='year']").val(),
				"remark":flag.find("[name='updateMenu'] [name='remark']").val()
		};
		//隐藏菜单面板
		menus.hide();
		//执行修改操作
		archive.updateArchive(updateJson);
		//重新查询数据
		setTimeout(function() {
			archive.searchArchive(searchJson,index);			
		}, 1000);
	});
	operationList.parent().find("[name='delete']").click(function(){
		var flag = $(this).parents(".tab-content");
		var index = flag.data("index");
		var checkbox = flag.find("*[name='id']:checked"); // 获取被选中的复选框
		if(checkbox.length <= 0){
			toastr.warning("请选择您要删除的记录");
			return;
		}
		var id = [];		
		for (var i = 0; i < checkbox.length; i++) {
			id.push($(checkbox[i]).val());
		}
		archive.deleteArchiveById(id);
		setTimeout(function() {
			archive.searchArchiveCount(searchJson,index);
		}, 1000);
		var  totalPages = flag.find("#totalPages").text();
		var currentPage = flag.find("#currentPage").text();
		
		totalPages = new Number(totalPages);
		currentPage = new Number(currentPage);
		//如果当前页数大于总页数，则使当前页为总页数
		if(currentPage > totalPages){
			currentPage = totalPages;
		}
		flag.find("#currentPage").text(currentPage);
		searchJson.page = new Number($("#currentPage").text());
		setTimeout(function() {
			archive.searchArchive(searchJson,index);			
		}, 1000);
		
	});
	
/*-----------------------------------清除按钮点击事件-----------------------------------------*/

	operationList.parent().find("*[name='clear']").click(function(){
		menus.clearData(this);	
		DropDown.clearAll();
		DropDownProfessionFlag = false;
	});

/*-------------------------------------------页码部分-----------------------------------------*/
	
	
	/**
	 * 点击页码切换页面
	 */
	operationList.parent().on("click",".page-item a[name='page-number']",function(){
		var index = $(this).parents(".tab-content").data("index");
		if(index == "中退学员"){
			searchJson = {
					"stateId":4
			}
		}
		$page.changePage(this,$(this).parents(".tab-content").find("#currentPage"),searchJson,index,archive.searchArchive);
	});
	/**
	 * 上一页翻页效果
	 */
	operationList.parent().find(".page-item a[data-btn='prevBtn']").click(function(){
		var index = $(this).parents(".tab-content").data("index");
		if(index == "中退学员"){
			searchJson = {
					"stateId":4
			}
		}
		if($("#totalPages").text() <= 0){
			return;
		}
		$page.previousPage($(this).parents(".tab-content").find("#currentPage"),this,5,searchJson,index,archive.searchArchive);
	});
	/**
	 * 下一页翻页效果
	 */
	operationList.parent().find(".page-item a[data-btn='nextBtn']").click(function(){
		var index = $(this).parents(".tab-content").data("index");
		if(index == "中退学员"){
			searchJson = {
					"stateId":4
			}
		}
		if($("#totalPages").text() <= 0){
			return;
		}
		$page.nextPage(this,5,$(this).parents(".tab-content").find("#currentPage"),$(this).parents(".tab-content").find("#totalPages"),searchJson,index,archive.searchArchive);
	});
});
