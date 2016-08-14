$(function() {

	/**
	 * 根据最大页码在HTML标签前插入页码
	 * @param {Object} obj 需要插入a标签的HTML标签对象
	 * @param {Object} max 所需最大页码
	 * @param {Object} number 所需标签个数
	 */
	function insertPageNumberByMax(obj, max, number) {
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
	function insertPageNumberByMin(obj, min, number) {
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
	 * 
	 * @param {Object} totalRows 总记录数对象
	 * @param {Object} rows  总记录数
	 * @param {Object} rowsPerPage 每页最大显示的记录数
	 * @param {Object} totalPages 总页数对象
	 */
	function showTotal(totalRows, rows, rowsPerPage, totalPages) {
		rows = new Number(rows);
		rowsPerPage = new Number(rowsPerPage);
		pages = rows % rowsPerPage == 0 ? rows / rowsPerPage : Math.floor(rows / rowsPerPage) + 1;
		$(totalRows).text(rows);
		$(totalPages).text(pages);
	}
	

	function a(name) {
		alert(name);
	}

	function b(name, age) {
		alert(name);
		alert(age);
	}

	function c(functions, options) {
		functions.fun1(options.opt1);
		functions.fun2(options.opt2.name, options.opt2.age);
	}

	$(".btn").click(function() {
		var funs = {
			"fun1": a,
			"fun2": b
		};
		var opts = {
			"opt1": "haha",
			"opt2": {
				"name": "aa",
				"age": 12
			}
		};
//		c(funs, opts);
		
//		showTotal($("#totalRows"), 500, 40, $("#totalPages"));
		
//		alert($(".page-list .page-item a[data-btn='nextBtn']").val());
		
		insertPageNumberByMax($(".page-list .page-item a[data-btn='nextBtn']"), 5, 5);
		
	});

});