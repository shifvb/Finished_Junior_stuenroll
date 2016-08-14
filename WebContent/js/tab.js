/**
 * 选项卡接口
 */
var I_Tab = function() {

}

/**
 * 显示Tab
 * 
 * @param {Object}
 *            index
 */
I_Tab.prototype.showTab = function(index) {
	throw "抽象方法";
}

var Tab = function() {

}

Tab.prototype = new I_Tab();

Tab.prototype.showTab = function(index) {
	var content = $(".tab-container .tab-content"); // 查找所有的内容面板
	// 查找data-index内容与参数相同的面板
	for (var i = 0; i < content.length; i++) {
		var one = content[i]; // 某个面板对象
		if ($(one).data("index") == index) {
			$(one).siblings().removeClass("content-active"); // 删除当前面板的所有兄弟面板的样式，隐藏它们
			$(one).addClass("content-active"); // 显示面板
			break;
		}

	}

	var tab = $(".tab-list .tab-item");
	for (var i = 0; i < tab.length; i++) {
		var one = tab[i];
		if ($(one).data("index") == index) {
			$(one).siblings().removeClass("tab-active");
			$(one).addClass("tab-active");
			break;
		}
	}
}

function TabFactory() {
	return new Tab();
}
