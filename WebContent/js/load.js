/**
 * 加载可选的专业与相关的机构,以及最近的年份
 * @author York Chu
 * 
 */

/**********************************抽象类*************************/

/**
 * 下拉框加载接口
 */
var I_Load = function() {

}

/**
 * 加载申报专业下拉列表数据
 */
I_Load.prototype.loadProfessionDropDown = function($profession, $organization) {
	throw "抽象方法";
}
/**
 * 加载培训机构下拉列表数据
 */
I_Load.prototype.loadOrganizationDropWithProfessionDown = function($profession, $organization) {
	throw "抽象方法";
}
/**
 * 根据当前年份加载毕业年份最近的五年
 */
I_Load.prototype.loadRecentFiveYears = function($graduateYear) {
	throw "抽象方法";
}

/***********************************实现类************************ */

// 实现加载接口
var Load = function() {
	
}
Load.prototype = new I_Load();


/**
* 加载申报专业下拉列表数据
*/
Load.prototype.loadProfessionDropDown=function($profession, $organization) {
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
    			load.loadOrganizationDropWithProfessionDown($profession, $organization);// 选中专业后更新机构列表
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
Load.prototype.loadOrganizationDropWithProfessionDown=function($profession, $organization)  {
	$organization.find(".dropdown-item").remove();
    $.ajax({
    	"type":"post",
    	"url":"/stuenroll/organization/searchOrganizationsJoinWithProfessionByYearAtDropdown",
    	"dataType":"json",
    	
    	"data":{
    		"year":new Date().getFullYear(),
    		"professionId": $profession.find(".dropdown-item-active").data("id")
    	},
    	"success": function(json){
    		var list=json.result;
    		
    		for(var i=0;i<list.length;i++){
    			var one=list[i];
    			var li="<li class='dropdown-item' data-id='"+one.id+"'>"+one.name+"</li>";
    			$organization.find(".dropdown-list").append(li);
    		}
    		console.log($organization.find(".dropdown-list").html());
    		// 初始化机构列表
    		DropDown.init($organization);
    		
    	},
    	"error":function(json){
    		toastr.error("系统错误");
    	}
    });
}

//根据当前年份加载毕业年份最近的五年
Load.prototype.loadRecentFiveYears = function($graduateYear){
	var year = new Date().getFullYear();
	//year=new Number(year);
	//alert(year);
	$graduateYear.find(".dropdown-item").remove();
	for(var i = 0; i < 5 ; i++){
		var li = "<li class='dropdown-item'>" + (year+i) + "</li>";
		$graduateYear.find(".dropdown-list").append(li);
	}
	DropDown.init($graduateYear);
}

function loadFactory(){
	return new Load();
}

var load = loadFactory();