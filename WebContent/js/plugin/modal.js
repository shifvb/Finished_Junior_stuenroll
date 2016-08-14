/**
 * 弹出层抽象类
 */
var Modal = function() {

}

Modal.prototype.show = function($obj) {
	$obj.before("<div class='shade no-select'></div>"); // 添加遮罩层
	var $shade = $(".shade");
	$shade.fadeIn(); // 显示遮罩层
	$obj.fadeIn(); // 显示弹出层
	// 遮罩层点击事件
	$shade.click(function(e) {
		$obj.fadeOut(); // 隐藏弹出层
		// 隐藏遮罩层
		$shade.fadeOut(function() {
			$shade.unbind(); // 解除绑定事件
			$shade.remove(); // 删除遮罩层
		});
	});
	$obj.find(".operation *[name='close']").click(function() {
		$shade.click();
	});
}
