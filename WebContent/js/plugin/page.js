/**
 * author LiYadong and York Chu
 * version 1.0
 */

/*******************************************页码接口**************************************/
var I_Page = function() {

}

/**
 * 显示总记录数和总页数
 * @param {Object} totalRows 总记录数jQuery对象
 * @param {Object} rows 总记录数
 * @param {Object} rowsPerPage 每页最大显示的记录数
 * @param {Object} totalPages 总页数jQuery对象
 */
I_Page.prototype.showTotal = function(totalRows, rows, rowsPerPage, totalPages) {
	throw "抽象方法";
}

/**
 * 根据最大页码在HTML标签前插入页码
 * @param {Object} obj 需要插入a标签的HTML标签jQuery对象
 * @param {Object} max 所需最大页码
 * @param {Object} number 所需标签个数
 */
I_Page.prototype.insertPageNumberByMax = function(obj, max, number) {
	throw "抽象方法";
}

/**
 * 根据最小页码在HTML标签后插入页码
 * @param {Object} obj 需要插入a标签的HTML标签jQuery对象
 * @param {Object} min 所需最小页码
 * @param {Object} number 所需标签个数
 */
I_Page.prototype.insertPageNumberByMin = function(obj, min, number) {
	throw "抽象方法";
}

/**
 * 点击页码切换页面方法
 * @param {Object} clickPage 被点击的页码对象
 * @param {Object} currentPage 当前页面对象
 * @param {Object} json 回调函数参数，json形式
 * @param {Object} callback 回调函数名称，一般是查询函数
 */
I_Page.prototype.changePage = function(clickPage, currentPage, json, callback) {
	throw "抽象方法";
}

/**
 * 点击页码切换页面方法（报名管理专用）
 * @param {Object} clickPage 被点击的页码对象
 * @param {Object} currentPage 当前页面对象
 * @param {Object} json 回调函数参数，json形式
 * @param {Object} index 回调函数参数， 代表当前页面
 * @param {Object} callback 回调函数名称，一般是查询函数
 */
I_Page.prototype.changePage = function(clickPage, currentPage, json, index, callback) {
	throw "抽象方法";
}

/**
 * 上一页翻页效果
 * @param {Object} $currentPage 当前页数对象
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} json 回调函数参数， json格式
 * @param {Object} callback 回调函数名称
 */
I_Page.prototype.previousPage = function($currentPage, $clickObj, number, json, callback){
	throw "抽象方法";
}

/**
 * 上一页翻页效果（报名管理专用）
 * @param {Object} $currentPage 当前页数对象
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} json 回调函数参数， json格式
 * @param {Object} index 回调函数参数， 代表当前页面
 * @param {Object} callback 回调函数名称
 */
I_Page.prototype.previousPage = function($currentPage, $clickObj, number, json, index, callback){
	throw "抽象方法";
}

/**
 * 下一页翻页效果
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} $currentPage 当前页数对象
 * @param {Object} $totalPages 当前总页数对象
 * @param {Object} json 回调函数参数， json格式
 * @param {Object} callback 回调函数名称
 */
I_Page.prototype.nextPage = function($clickObj, number, $currentPage, $totalPages, json, callback){
	throw "抽象方法";
}

/**
 * 下一页翻页效果（报名管理专用）
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} $currentPage 当前页数对象
 * @param {Object} $totalPages 当前总页数对象
 * @param {Object} json 回调函数参数， json格式
 * @param {Object} index 回调函数参数， 代表当前页面
 * @param {Object} callback 回调函数名称
 */
I_Page.prototype.nextPage = function($clickObj, number, $currentPage, $totalPages, json, index, callback){
	throw "抽象方法";
}

/**
 * 获取被点击的页
 * @param {Object} clickPage 被点击的页码对象
 * @param {Object} currentPage 当前页面对象
 */
I_Page.prototype.getClickedPage = function(clickPage, currentPage) {
	throw "抽象方法";
}

/**
 * 获取下一页页码
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} $currentPage 当前页数对象
 * @param {Object} $totalPages 当前总页数对象
 */
I_Page.prototype.getNextPage = function($clickObj, number, $currentPage, $totalPages) {
	throw "抽象方法";
}

/**
 * 获取上一页页码
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} $currentPage 当前页数对象
 */
I_Page.prototype.getPreviousPage = function($clickObj, number, $currentPage) {
	throw "抽象方法";
}

/************************************************页码接口实现*****************************************/
var Page = function() {

}

Page.prototype = new I_Page();

/**
 * 
 * @param {Object} totalRows 总记录数对象
 * @param {Object} rows  总记录数
 * @param {Object} rowsPerPage 每页最大显示的记录数
 * @param {Object} totalPages 总页数对象
 */
Page.prototype.showTotal = function(totalRows, rows, rowsPerPage, totalPages) {
	if (rows == 0) {
		$(totalRows).text(rows);
		$(totalPages).text(1);
		return;
	}
	rows = new Number(rows);
	rowsPerPage = new Number(rowsPerPage);
	pages = rows % rowsPerPage == 0 ? rows / rowsPerPage : Math.floor(rows / rowsPerPage) + 1;
	$(totalRows).text(rows);
	$(totalPages).text(pages);
}

/**
 * 根据最大页码在HTML标签前插入页码
 * @param {Object} obj 需要插入a标签的HTML标签jQuery对象
 * @param {Object} max 所需最大页码
 * @param {Object} number 所需标签个数
 */
Page.prototype.insertPageNumberByMax = function(obj, max, number) {
	// 1. 清除obj间的a标签
	$(obj).parent().find("a[name='page-number']").remove();
	// 2. 生成a标签并插入obj中
	var temp = "";
	max = new Number(max);
	number = new Number(number);
	for (var i = max - number + 1; i <= max; i++) {
		temp = "<a name='page-number'>" + i + "</a>";
		$(temp).insertBefore($(obj));
	}
}

/**
 * 根据最小页码在HTML标签后插入页码
 * @param {Object} obj 需要插入a标签的HTML标签对象
 * @param {Object} min 所需最小页码
 * @param {Object} number 所需标签个数
 */
Page.prototype.insertPageNumberByMin = function(obj, min, number) {
	// 1. 清除obj间的a标签
	$(obj).parent().find("a[name='page-number']").remove();
	// 2. 生成a标签并插入obj中
	var temp = "";
	min = new Number(min);
	number = new Number(number);
	for (var i = min + number - 1; i >= min; i--) {
		temp = "<a name='page-number'>" + i + "</a>";
		$(temp).insertAfter($(obj));
	}
}

/**
 * 点击页码切换页面方法
 * @param {Object} clickPage 被点击的页码对象
 * @param {Object} currentPage 当前页面对象
 * @param {Object} json 回调函数参数，json形式
 * @param {Object} callback 回调函数名称，一般是查询函数
 */
Page.prototype.changePage = function(clickPage, currentPage, json, callback) {
	json.page = this.getClickedPage(clickPage, currentPage);
	callback(json);
}

/**
 * 点击页码切换页面方法（报名管理专用）
 * @param {Object} clickPage 被点击的页码对象
 * @param {Object} currentPage 当前页面对象
 * @param {Object} json 回调函数参数，json形式
 * @param {Object} index 回调函数参数， 代表当前页面
 * @param {Object} callback 回调函数名称，一般是查询函数
 */
Page.prototype.changePage = function(clickPage, currentPage, json, index, callback) {
	json.page = this.getClickedPage(clickPage, currentPage);
	callback(json, index);
}

/**
 * 获取被点击的页
 * @param {Object} clickPage 被点击的页码对象
 * @param {Object} currentPage 当前页面对象
 */
Page.prototype.getClickedPage = function(clickPage, currentPage){
	// 1. 获取当前点击的页码
	var pageNum = $(clickPage).text();
	pageNum = new Number(pageNum);
	// 2. 移除所有页码激活状态
	$(clickPage).siblings().removeClass("page-active");
	// 3. 当前点击页码计入激活状态
	$(clickPage).addClass("page-active");
	// 4. 设置当前页面
	$(currentPage).text(pageNum);
	return pageNum;
}

/**
 * 上一页翻页效果
 * @param {Object} $currentPage 当前页数对象
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} json 回调函数参数， json格式
 * @param {Object} callback 回调函数名称
 */
Page.prototype.previousPage = function($currentPage, $clickObj, number, json, callback) {
	// 6. 设置页码与索引
	json.page = this.getPreviousPage($clickObj, number, $currentPage);
	// 7. 调用回调函数
	if(json.page != null)
		callback(json);
}

/**
 * 上一页翻页效果(报名管理)
 * @param {Object} $currentPage 当前页数对象
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} json 回调函数参数， json格式
 * @param {Object} index 回调函数参数， 代表当前页面
 * @param {Object} callback 回调函数名称
 */
Page.prototype.previousPage = function($currentPage, $clickObj, number, json, index, callback) {
	// 6. 设置页码与索引
	json.page = this.getPreviousPage($clickObj, number, $currentPage);
	// 7. 调用回调函数
	if(json.page != null)
		callback(json, index);
}

/**
 * 下一页翻页效果
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} $currentPage 当前页数对象
 * @param {Object} $totalPages 当前总页数对象
 * @param {Object} json 回调函数参数， json格式
 * @param {Object} callback 回调函数名称
 */
Page.prototype.nextPage = function($clickObj, number, $currentPage, $totalPages, json, callback) {
	json.page = this.getNextPage($clickObj, number, $currentPage, $totalPages);
	// 6. 更新数据
	if(json.page != null)
		callback(json);
}

/**
 * 下一页翻页效果(报名管理专用)
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} $currentPage 当前页数对象
 * @param {Object} $totalPages 当前总页数对象
 * @param {Object} json 回调函数参数， json格式
 * @param {Object} index 回调函数参数， 代表当前页面
 * @param {Object} callback 回调函数名称
 */
Page.prototype.nextPage = function($clickObj, number, $currentPage, $totalPages, json, index, callback) {
	json.page = this.getNextPage($clickObj, number, $currentPage, $totalPages);
	// 6. 更新数据
	if(json.page != null)
		callback(json, index);
}

/**
 * 获取下一页页码
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} $currentPage 当前页数对象
 * @param {Object} $totalPages 当前总页数对象
 */
Page.prototype.getNextPage = function($clickObj, number, $currentPage, $totalPages) {
	// 1. 获取当前页面和总页数
	var currentPage = $($currentPage).text();
	var totalPages = $($totalPages).text();
	// 2. 判断是否为最后一页
	if(currentPage == totalPages){
		toastr.warning("当前页面已经是最后一页");
		return;
	}
	currentPage = new Number(currentPage);
	totalPages = new Number(totalPages);
	// 3. 获取最后一个a标签的当前值
	var a = $($clickObj).siblings();
	$(a).removeClass("page-active");
	var currentMaxPage = $(a[a.length - 1]).text();
	currentMaxPage = new Number(currentMaxPage);
	// 4. 判断当前页数是否小于a标签的值
	if (currentPage < currentMaxPage) {
		// 4.1 若小于，则更改当前页数
		for(var i = 1; i < a.length; i++){
			var page = $(a[i]).text();
			page = new Number(page);
			if(page == currentPage+1){
				$(a[i]).addClass("page-active");
				//$($currentPage).text(page);
				break;
			}
		}
	}
	// 4.2 若不小于， 则更改a标签数字并使新生成的第一个a标签加上page-active样式
	else {
		// 4.2.1 动态生成页码
		if (number + currentPage >= totalPages) {
			number = totalPages - currentPage;
		}
		this.insertPageNumberByMax($clickObj, number + currentPage, number)
		// 4.2.2 将最后一个页码加上page-active
		a = $($clickObj).siblings();
		$(a[1]).addClass("page-active");
	}
	// 5. 设置当前页数
	currentPage++;
	$($currentPage).text(currentPage);
	// 6. 更新数据
	return currentPage;
}

/**
 * 获取上一页页码
 * @param {Object} $clickObj 当前被点击的对象
 * @param {Object} number 所需显示的页码个数
 * @param {Object} $currentPage 当前页数对象
 */
Page.prototype.getPreviousPage = function($clickObj, number, $currentPage) {
	// 1. 获取当前页数
	var currentPage = new Number($($currentPage).text());
	number = new Number(number);
	// 2. 判断是否为第一页
	if (currentPage == 1) {
		toastr.warning("已为第一页");
		return;
	}
	var a = $($clickObj).siblings();
	// 3. 获取当前显示的最小页码
	var currentMinPage = $(a[0]).text();
	currentMinPage = new Number(currentMinPage);
	// 4. 判断当前页面是否小于a标签的值
	// 4.1 若大于
	if (currentPage > currentMinPage) {
		// 去除页码激活样式
		$(a).removeClass("page-active");
		// 循环找到下一页页码对象并设置CSS样式
		for (var i = 0; i < $(a).length - 1; i++) {
			var pageNum = $(a[i]).text();
			pageNum = new Number(pageNum);
			if (pageNum == currentPage - 1) {
				$(a[i]).addClass("page-active");
				break;
			}
		}
	}
	// 4.2 若不大于
	else {
		// 去除页码激活样式
		$(a).removeClass("page-active");
		// 动态生成页码
		if (currentPage == number + 1) {
			this.insertPageNumberByMin($clickObj, 1, number);
		} else {
			this.insertPageNumberByMin($clickObj, currentPage - number, number);
		}
		// 给最后一个页码设置CSS样式
		a = $($clickObj).siblings();
		$(a).removeClass("page-active");
		$(a[$(a).length - 2]).addClass("page-active");
	}
	// 5. 修改当前页码页数
	currentPage = currentPage - 1;
	$($currentPage).text(currentPage);
	// 6. 返回数据
	return currentPage;
}

/**********************************************工厂方法************************************************/

function pageFactory() {
	return new Page();
}