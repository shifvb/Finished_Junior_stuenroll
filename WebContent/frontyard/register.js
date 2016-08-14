/**
 * @author York Chu
 */
$(function(){
	DropDown.initAll();// 初始化所有列表
	/**
	 * 报名注册接口
	 */
	var I_Register=function(){
		
	}
	/**
	 * 加载申报专业下拉列表数据
	 */
	I_Register.prototype.loadProfessionDropDown=function(){
		throw "抽象方法";
	}
	/**
	 * 加载培训机构下拉列表数据
	 */
	I_Register.prototype.loadOrganizationDropWithProfessionDown=function(){
		throw "抽象方法";
	}
	/**
	 * 提交报名数据
	 */
	I_Register.prototype.register=function(){
		throw "抽象方法";
	}
	/**
	 * 验证身份证号
	 */
	I_Register.prototype.validatePid=function(){
		throw "抽象方法";
	}
	/**
	 * 根据当前年份加载毕业年份最近的五年
	 */
	I_Register.prototype.loadRecentFiveYears = function(){
		throw "抽象方法";
	}
	
	/**
	 * 报名注册实现
	 */
	var Register = function(){
		
	}
	Register.prototype=new I_Register();
	
	
	/**
	* 加载申报专业下拉列表数据
	*/
	Register.prototype.loadProfessionDropDown=function() {
    	var $profession = $("#profession");
        var $organization = $("#organization");
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
        			register.loadOrganizationDropWithProfessionDown();// 选中专业后更新机构列表
        		})
        	},
        	"error":function(json){
        		toastr.error("系统错误1");
        	}
        });
    }
    
    /**
	 * 加载培训机构下拉列表数据
	 */
	Register.prototype.loadOrganizationDropWithProfessionDown=function()  {
    	var $organization = $("#organization");
    	$organization.find(".dropdown-item").remove();
        $.ajax({
        	"type":"post",
        	"url":"/stuenroll/organTransform/searchOrganizationsJoinByYearAtDropdown",
        	"dataType":"json",
        	"data":{
        		"year":new Date().getFullYear(),
        		"professionId":$("#profession .dropdown-item-active").data("id")
        	},
        	"success": function(json){
        		var list=json.result;
        		
        		for(var i=0;i<list.length;i++){
        			var one=list[i];
        			var li="<li class='dropdown-item' data-id='"+one.id+"'>"+one.name+"</li>";
        			$organization.find(".dropdown-list").append(li);
        		}
        		
        		// 初始化机构列表
        		DropDown.init($organization);
        	},
        	"error":function(json){
        		toastr.error("系统错误2");
        	}
        });
    }
	
	/**
	 * 提交报名数据
	 */
	Register.prototype.register = function()  {
		var bool = true;
		var input = $(".register .input");
		//判断输入框中的内容是否为空
		input.each(function(i,one){
			if(one.id == "pid"){
				bool = bool && checkPid($(one).val());
			}
			else{
				bool = bool && one.checkValidity();
			}
		});
		
		if(!bool){
			toastr.warning("必填内容不能为空！");
			return;
		}
		
		/*
		 * if(sex.length==0){ toastr.warning("性别不能为空"); return; }
		 */
		var bool_ = true;
		var dropdownItems = $(".register .dropdown-list");
		dropdownItems.each(function(i, one) {
			var item = $(one).find(".dropdown-item-active");
			if(item.length == 0){
				bool_ = false;
			}
		})
		
		if(!bool_){
			toastr.warning("必选内容不能为空！");
			return;
		}
		$.ajax({
			"url":"/stuenroll/enroll/register",
			"type":"post",
			"dataType":"json",
			"data":{
				"name":$(".register #name").val(),
				"sex":$("#sex .dropdown-item-active").text(),
				"nation":$("#nation .dropdown-item-active").text(),
				"healthy":$("#healthy .dropdown-item-active").text(),
				"graduateYear":$("#graduateYear .dropdown-item-active").text(),
				"major":$("#major .dropdown-item-active").text(),
				"education":$("#education .dropdown-item-active").text(),
				"politics":$("#politics .dropdown-item-active").text(),
				"professionId":$("#profession .dropdown-item-active").data("id"),
				// "profession":$("#profession .dropdown-item-active").text(),
				"organizationId":$("#organization .dropdown-item-active").data("id"),
				"place":$("#place .dropdown-item-active").text(),
				"birthday":$(".register #birthday").val(),
				"pid":$(".register #pid").val(),
				"graduteSchool":$(".register #graduteSchool").val(),
				"graduateDate":$(".register #graduateDate").val(),
				"residentAddress":$(".register #residentAddress").val(),
				"homeAddress":$(".register #homeAddress").val(),
				"permanentAddress":$(".register #permanentAddress").val(),
				"tel":$(".register #tel").val(),
				"homeTel":$(".register #homeTel").val(),
				"email":$(".register #email").val(),
				"wechat":$(".register #wechat").val(),
				"remark":$(".remark #homeTel").val()
			},
			"success":function(json){
				if(json.result){
					toastr.success("注册成功");
					pid = $(".register #pid").val();
					$downloadModal.find("[name = pid]").val(pid);
					modal.show($downloadModal);				
				}
			},
			"error":function(){
				
				toastr.error("系统错误1");
			}
		});
	}
	
	/**
	 * 验证身份证号
	 */
	Register.prototype.validatePid = function()  {
		var flag=false;
		$.ajax({
			"url":"/stuenroll/enroll/isEnrollEligible",
			"type":"post",
			"dataType":"json",
			"async": false,
			"data":{
				"pid":$(".register #pid").val()
			},
			"success":function(json){
				if(json.result){
					flag=true;
				}
			},
			"error":function(){
				toastr.error("系统错误2");
			}
		});
		return flag;
	}
	
	//根据当前年份加载毕业年份最近的五年
	Register.prototype.loadRecentFiveYears = function(){
		var year = new Date().getFullYear();
		//year=new Number(year);
		//alert(year);
		$graduateYear = $("#graduateYear");
		$graduateYear.find(".dropdown-item").remove();
		for(var i = 0; i < 5 ; i++){
			var li = "<li class='dropdown-item'>" + (year+i) + "</li>";
			$graduateYear.find(".dropdown-list").append(li);
		}
		DropDown.init($graduateYear);
	}
	
	function factoty(key){
		if(key == "Register"){
			return new Register();
		}
	}
	
	var register=factoty("Register");
	register.loadProfessionDropDown();
	register.loadRecentFiveYears();
	var $downloadModal = $("#downloadModal");
	var modal = new Modal();
	var pid;
	
	//点击注册按钮时触发事件
	$("#register").click(function(){
		var flag=register.validatePid();
		if(flag){
			register.register();
		}
		else{
			toastr.warning("身份证号重复注册");
		}
		//alert($("#sex .dropdown-item-active").text())
		
	});
	
	$downloadModal.on("click", "*[name='download']", function() {
		
		$downloadModal.find("form").attr("action", "/stuenroll/register/downloadRegisterReport?pid="+pid);
		$downloadModal.find("form").submit();
		$downloadModal.find("*[name='close']").click(); //关闭界面
		setInterval(function() {
			location.href = "/stuenroll/frontyard/index.html";
		}, 8000)	
	})
	
	// 输入项键盘弹起事件
	$(".register .input").keyup(function() {
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
	})
});
