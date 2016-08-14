/**
 * author LiYadong
 * version 1.0
 */

/********************************************弹出菜单接口*******************************************/
/**
 * 弹出面板接口
 */
var I_Menu = function() {

}

/**
 * 初始化抽象方法，必须最先调用
 */
I_Menu.prototype.init = function() {
	throw "抽象方法";
}

/**
 * 显示菜单
 * @param {Object} obj 传入的operation-item
 * @param {Object} event 点击事件
 */
I_Menu.prototype.show = function(obj, event) {
	throw "抽象方法";
}

/**
 * 显示菜单
 * @param {Object} obj 传入的operation-item
 * @param {Object} event 点击事件
 */
I_Menu.prototype.showMenu = function(obj, event) {
	throw "抽象方法";
}

/**
 * 隐藏菜单
 */
I_Menu.prototype.hide = function() {
	throw "抽象方法";
}

/**
 * 清空弹出菜单内所填内容
 * @param {Object} obj 传入清空按钮对象
 */
I_Menu.prototype.clearData = function(obj) {
	throw "抽象方法";
}

/*********************************************弹出菜单实现类**********************************/
var Menu = function() {

}
Menu.prototype = new I_Menu();

//override
Menu.prototype.init = function() {
	
	var obj = this;
	//Menu之外任意位置点击鼠标，Menu消失
	$(document).click(function() {
		obj.hide();
	});

	//Menu内部点击鼠标阻止Menu消失
	$(".menu").click(function(event) {
		event.stopPropagation();
	});
}

/**
 * 显示菜单
 * @param {Object} obj 传入的operation-item
 * @param {Object} event 点击事件
 */
Menu.prototype.showMenu = function(obj, event) {
	$(".menu").fadeOut(); //隐藏所有菜单
	$(obj).parent().siblings().removeClass("item-active"); //取消所有选项
	$(obj).parent().addClass("item-active"); //选项被选中
	$(obj).fadeIn(); //菜单出现
	event.stopPropagation();
}

Menu.prototype.show = function(obj, event) {
	var menu = $(obj).find(".menu");
	$(".menu").fadeOut(); //隐藏所有菜单
	menu.parent().siblings().removeClass("item-active"); //取消所有选项
	menu.parent().addClass("item-active"); //选项被选中
	// 清理之前的数据
	menu.find(":text").val('');
	menu.find("input[type=email]").val('');
	DropDown.closeDropDown();
	menu.fadeIn(); //菜单出现
	event.stopPropagation();
}

Menu.prototype.hide = function() {
	var menu = $(".menu");
	menu.fadeOut();
//	menu.find(":text").val('');
	menu.parent().removeClass("item-active");
//	DropDown.closeDropDown();
}

Menu.prototype.clearData = function(obj) {
	var menu = $(obj).parents(".menu");
	menu.find(":text").val('');
	menu.find("input[type=email]").val('');
	DropDown.clearAll();
}

/**************************************关联专业面板*************************************************/

var ProfessionMenu = function() {
	
}

ProfessionMenu.prototype = new I_Menu();

ProfessionMenu.prototype.init = function() {
	var obj = this;
	
	$(document).click(function() {
		obj.hide();
	});
	
	$(".professionMenu").click(function(event) {
		event.stopPropagation();
	})
}

ProfessionMenu.prototype.show = function(obj, event) {
	$(obj).fadeOut();
	$(obj).fadeIn();
	event.stopPropagation();
}

ProfessionMenu.prototype.hide = function() {
	$(".professionMenu").fadeOut();
}

ProfessionMenu.prototype.clearData = function(obj) {
	$(obj).find("li").remove();
}


/****************************************ClassMenu***********************************************/
var ClassMenu = function() {}
ClassMenu.prototype = new I_Menu();

ClassMenu.prototype.init = function() {
	var obj = this;
	
	$(document).click(function() {
		obj.hide();
	});
	
	$(".classMenu").click(function(event) {
		event.stopPropagation();
	})
}

ClassMenu.prototype.show = function(obj, event) {
	this.clearData(null);
	$(obj).fadeOut();
	$(obj).fadeIn();
	event.stopPropagation();
}

ClassMenu.prototype.hide = function() {
	$(".classMenu").fadeOut();
}

ClassMenu.prototype.clearData = function(obj) {
	$(".classMenu .students .data-table").find("tr:gt(0)").remove();
	var pageList = $(".classMenu .page-list");
	pageList.find(".page-info #totalRows").text('');
	pageList.find(".page-info #totalPages").text('');
	pageList.find(".page-info #currentPage").text(1);
	pageList.find(".page-operation a[name='page-number']").remove();
}


/****************************************工厂方法**************************************************/

function MenuFactory(key) {
	if (key == "Menu") {
		return new Menu();
	} else if(key == "ProfessionMenu") {
		return new ProfessionMenu();
	} else if (key == "ClassMenu") {
		return new ClassMenu();
	}
	
}