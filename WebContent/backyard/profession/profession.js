$(function(){
	
/*---------------------------------权限验证部分----------------------------------*/
	
	/**
	 * 验证用户是否拥有查询专业的权限
	 */
	if(!checkPermission(["6_4"])){
		return;
	}
	var operation = $(".tab-container");
	/**
	 * 验证用户是否拥有添加专业的权限
	 */
	if(checkPermission(["6_1"])){
		$(".operation-item[name='add']").show();
	}
	/**
	 * 验证用户是否拥有修改专业的权限
	 */
	if(checkPermission(["6_2"])){
		$(".operation-item[name='update']").show();
	}
	/**
	 * 验证用户是否拥有删除专业的权限
	 */
	if(checkPermission(["6_3"])){
		$(".operation-item[name='delete']").show();
	}
	/**
	 * 验证用户是否拥有导入专业统计数据的权限
	 */
	if(checkPermission(["6_5"])){
		$(".operation-item[name='import']").show();
	}
	/**
	 * 验证用户是否拥有导入专业统计数据的权限
	 */
	if(checkPermission(["6_6"])){
		$(".operation-item[name='export']").show();
	}
	
/*------------------------------接口部分------------------------------------*/
	
	/**
	 * 专业管理抽象接口
	 */
	var I_Profession = function(){
		
	}
	I_Profession.prototype = new I_Profession();
	/**
	 * 查询专业统计信息抽象方法
	 */
	I_Profession.prototype.searchProfessionStatistics = function(json){
		throw "抽象方法";
	}
	/**
	 * 查询专业总数
	 */
	I_Profession.prototype.searchProfessionCount = function(json){
		throw "抽象方法";
	}
	/**
	 * 添加专业
	 */
	I_Profession.prototype.addProfession = function(json){
		throw "抽象方法";
	}
	/**
	 * 修改专业
	 */
	I_Profession.prototype.updateProfession = function(json){
		throw "抽象方法";
	}
	/**
	 * 根据专业ID删除专业
	 */
	I_Profession.prototype.deleteProfessionById = function(id){
		throw "抽象方法";
	}
	
/*----------------------------定义对象---------------------------------*/
	
	
	var menus = MenuFactory("Menu");//定义菜单面板对象
	var $page = pageFactory();//定义页码对象
	var professionList = $(".tab-container .tab-content[data-index='专业列表'] .page-list");
	var PJson = {};//定义查询条件Json

	
/*--------------------------------专业接口实现部分-------------------------------------*/	

	
	var Profession = function(){
		
	}
	
	Profession.prototype = new Profession();
	Profession.prototype.searchProfessionStatistics = function(json){
		$.ajax({
			"url" : "/stuenroll/profession/searchProfessionStatistics",
			"type" : "post",
			"dataType" : "json",
			"async":false,
			"data" : json,
			"success" : function(json) {
				var data = json.result;
				var table = $(".tab-container .tab-content[data-index='专业列表'] .data-table");
				// 清空表格数据
				table.find("tr:gt(0)").remove();

				// 获得当前页数
				var currentPage = $(".tab-container .tab-content[data-index='专业列表'] #currentPage").text();
				// 转化成数字类型
				currentPage = new Number(currentPage);
				// 当前页数的行号起始数字
				var start = (currentPage - 1) * 35;

				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td class='td'><input type='checkbox' name='id' value='" + one.id + "' /></td>"
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td>" + one.name + "</td>";
					temp += "<td>" + one.organizationAmount + "</td>";
					temp += "<td>" + one.yearAmount + "</td>";
					temp += "<td>" + one.classAmount + "</td>";
					temp += "<td>" + one.studentAmount + "</td>";
					temp += "<td>" + one.jobRate+"%" + "</td>";
					temp += "</tr>";
				}
				table.append(temp);
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}
	Profession.prototype.searchProfessionCount = function(json){
		$.ajax({
			"url" : "/stuenroll/profession/searchProfessionCount",
			"type" : "post",
			"dataType" : "json",
			"data" : null,
			"async":false,
			"success" : function(json) {
				var count = json.result; // 总记录数
				var content = $(".tab-container .tab-content[data-index='专业列表']");
				$page.showTotal(content.find(".page-list .page-item #totalRows"),count,35,content.find(".page-list .page-item #totalPages"));
				var totalPages = content.find(".page-list .page-item #totalPages").text();
				var prevBtn = content.find(".page-list .page-item a[data-btn='prevBtn']");
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
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}
	
	Profession.prototype.addProfession = function(json){
		$.ajax({
			"url" : "/stuenroll/profession/addProfession",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true,	//发送数组JSON格式
			"async":false,
			"data" :json,
			"success":function(json) {
				if(json.result == 1){
					toastr.success("添加专业成功");
				}
				else{
					toastr.warning("添加专业失败");
				}
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});		
	}
	
	Profession.prototype.updateProfession = function(json){
		// 获得被选中的记录
		$.ajax({
			"url":"/stuenroll/profession/updateProfession",
			"type":"post",
			"dataType":"json",
			"async":false,
			"data":json,
			"success":function(json){
				if(json.result == 1){
					toastr.success("修改记录成功");
				}
				else{
					toastr.warning("修改记录失败");
				}
			},
			"error":function(){
				toastr.error("系统异常");
			}
		});
	}
	
	Profession.prototype.deleteProfessionById = function(id){
		// 弹出确认对话框
		var bool = confirm("是否删除选中的记录？");
		if (bool == false) {
			return;
		}
		$.ajax({
			"url" : "/stuenroll/profession/deleteProfessionById",
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
	
/*--------------------------------工厂方法及函数调用-------------------------------------------*/
	
	function factory(key){
		if(key == "Profession"){
			return new Profession();
		}
	}
	var menus = MenuFactory("Menu");
	menus.init();
	var profession = factory("Profession");
	menus.init();//初始化菜单面板
	profession.searchProfessionStatistics(PJson,"专业列表");
	profession.searchProfessionCount(PJson,"专业列表");

/*-------------------------------------点击弹出面板---------------------------------------------*/
	
	
	var element = $(".tab-container .tab-content[data-index='专业列表']")
	//点击添加弹出面板
	element.find("*[name='add']").click(function(event){
		menus.show(this,event);
		$("#importMenu,.export-menu").fadeOut(); 
	});
	
	//**************导入*************************************
	
	//点击导入弹出面板
	element.find("*[name='import']").click(function(event){
		$(".menu,.export-menu").fadeOut(); 
		$("#importMenu").fadeIn(); //菜单出现
		event.stopPropagation();
	});

	var $upload = $("#upload");
	var $file = $(".file");
	var $selectFile = $upload.find(".select-file")
	
	$selectFile.click(function(event) {
		$file.find(".file-upload").click();
		event.stopPropagation();
	});
	
	$file.on("change", handler = function(event) {
		var path = $file.find(".file-upload").val();
		path = path.split("\\").slice(-1);
		$upload.find(".file-path").val(path);
	})
	
	$upload.find("#fileUpload").click(function() {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/profession/importProfession",
			"async": true,
			"data": new FormData($upload[0]),
			"cache": false,
			"contentType": false,
			"processData": false,
			"success": function(json) {
				if(json.result) {
					toastr.info("导入用户数据成功！");
					$("#importMenu").fadeOut();
					setTimeout(function() {
						profession.searchProfessionCount();
						profession.searchProfessionStatistics();
					}, 3000);
				} else {
					toastr.info("导入用户数据失败！");
				}
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
		$("#importMenu").fadeOut(); 
	});
	
	//**************导入：结束*************************************
	
	//点击导出弹出面板
	element.find("*[name='export']").click(function(event){
		$(".menu,.import-menu").fadeOut(); 
		$(".export-menu").fadeIn(); //菜单出现
		event.stopPropagation();
	});
	
	//点击修改弹出面板
	element.find("*[name='update']").click(function(event){
		var content = $(".tab-container .tab-content[data-index='专业列表']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框
		var td = $(checkbox).parents(".td").siblings();//获取父节点的兄弟节点
		var profession = $(td[1]).text();//获取专业名称
		$("#updateMenu [name='profession']").val(profession);//在输入框中自动填入专业名称
		if(checkbox.length == 0){
			toastr.warning("请选择您要修改的记录");
			return;
		}
		else if(checkbox.length > 1){
			toastr.warning("您只能修改一条记录");
			return;
		}
		menus.show(this,event);
		$("#importMenu,.export-menu").fadeOut(); 
	});
	
	// 输入框keyup事件
	$(".main-container .tab-container input[type='text']").keyup(function() {
		if (this.checkValidity()) {
			$(this).removeClass("error");
		} else {
			$(this).addClass("error");
		}
	});	
	
	// 
	$(document).click(function() {
		$(".import-menu,.export-menu").fadeOut();		
	});

	// 绑定 “用户列表” 点击菜单不会发生任何事情
	$(".import-menu,.export-menu").click(function(event) {
		event.stopPropagation();
	});
	
/*-----------------------------------点击保存，修改及删除-----------------------------------*/


	//添加面板中的保存按钮点击事件
	element.find("*[name='save']").click(function(){
		var name = $("#addMenu *[name='profession']").val();	
		if(name != null && name.length > 0){
			var json = {
				"name":name
			};			
			//执行插入操作
			profession.addProfession(json);
			//隐藏面板
			menus.hide();
			//重新查询数据并更新
			PJson.page = new Number($("#currentPage").text());
			profession.searchProfessionCount(PJson);
			profession.searchProfessionStatistics(PJson);
		}
		else{
			toastr.warning("请输入专业名称");
			return;
		}
	});
	
	//修改面板中的保存按钮点击事件
	element.find("*[name='saveUpdate']").click(function(){
		var name = $("#updateMenu *[name='profession']").val();	
		var content = $(".tab-container .tab-content[data-index='专业列表']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框
		var id = $(checkbox).val();
		if(name != null && name.length > 0){
			var json = {
				"id":id,
				"name":name
			};		
			//执行更新操作
			profession.updateProfession(json);
			//隐藏面板
			menus.hide();
			//重新查询数据并更新
			PJson.page = new Number($("#currentPage").text());
			profession.searchProfessionStatistics(PJson);
		}
		else{
			toastr.warning("请输入专业名称");
			return;
		}
	});
	
    //删除按钮的点击事件
	element.find("*[name='delete']").click(function() {
		var index = $(this).parents(".tab-content").data("index");
		var content = $(".tab-container .tab-content[data-index='专业列表']");
		var checkbox = content.find("*[name='id']:checked"); // 获取被选中的复选框
		if(checkbox.length <= 0){
			toastr.warning("请选择您要删除的记录");
			return;
		}
		for(var i = 0;i < checkbox.length;i++){
			var td = $(checkbox[i]).parents(".td").siblings();//获取父节点的兄弟节点
			//获取关联机构数量和培训人数
			var organizationAmount = $(td[2]).text();
			var studentAmount = $(td[5]).text();
			//如果关联机构数量和培训人数为不为0，则弹出警告，停止操作
			if(organizationAmount != 0 || studentAmount != 0){
				toastr.warning("记录不可删除");
				return;
			}			
		}
		var id = [];		
		for (var i = 0; i < checkbox.length; i++) {
			id.push($(checkbox[i]).val());
		}
		//执行删除
		profession.deleteProfessionById(id);
		//重新查询数据
		profession.searchProfessionCount(PJson);
		var  totalPages = $("#totalPages").text();
		var currentPage = $("#currentPage").text();
		
		totalPages = new Number(totalPages);
		currentPage = new Number(currentPage);
		if(currentPage > totalPages){
			currentPage = totalPages;
		}
		$("#currentPage").text(currentPage);
		PJson.page = new Number($("#currentPage").text());
		profession.searchProfessionStatistics(PJson,index);
	});
	
	
/*-------------------------------------------页码部分-----------------------------------------*/
	
	/**
	 * 点击页码切换页面
	 */
	professionList.parent().on("click",".page-item a[name='page-number']",function(){
		var index = $(this).parents(".tab-content").data("index");
		$page.changePage(this,$(this).parents(".tab-content").find("#currentPage"),PJson,index,profession.searchProfessionStatistics);
	});
	/**
	 * 上一页翻页效果
	 */
	professionList.parent().find(".page-item a[data-btn='prevBtn']").click(function(){
		var index = $(this).parents(".tab-content").data("index");
		if($("#totalPages").text() <= 0){
			return;
		}
		$page.previousPage($(this).parents(".tab-content").find("#currentPage"),this,5,PJson,index,profession.searchProfessionStatistics);
	});
	/**
	 * 下一页翻页效果
	 */
	professionList.parent().find(".page-item a[data-btn='nextBtn']").click(function(){
		var index = $(this).parents(".tab-content").data("index");
		if($("#totalPages").text() <= 0){
			return;
		}
		$page.nextPage(this,5,$(this).parents(".tab-content").find("#currentPage"),$(this).parents(".tab-content").find("#totalPages"),PJson,index,profession.searchProfessionStatistics);
	});
});
