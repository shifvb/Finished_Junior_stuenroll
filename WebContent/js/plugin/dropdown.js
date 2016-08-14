/**
 * 抽象下拉列表类
 */
function DropDown() {

}

    /**
    * 关闭列表
    */
	DropDown.closeDropDown = function()  {
        var $allDropDown = $(".dropdown"); //全部列表
        var $allList = $(".dropdown-list"); //全部列表的列表项
        $allDropDown.removeClass("dropdown-active");
        $allList.removeClass("dropdown-list-active");
    }
    /**
    * 初始化所有列表
    */
	DropDown.initAll = function()  {
        var $dropdown = $(".dropdown");
        $dropdown.each(function (i, one) {
            var $list = $(one).find(".dropdown-list");
            var $item = $list.find(".dropdown-item");
            //点击列表显示选项
            $(one).unbind("click");
            $(one).click(function (event) {
                DropDown.closeDropDown();
                $(one).addClass("dropdown-active");
                $list.addClass("dropdown-list-active");
                event.stopPropagation();
            });
            //鼠标选中列表项
            $item.unbind("click");
            $item.click(function (event) {
                var value = $(this).text();
                $(one).find(".value").text(value);
                $(one).toggleClass("dropdown-active");
                $list.toggleClass("dropdown-list-active");
                $(this).siblings().removeClass("dropdown-item-active");
                $(this).addClass("dropdown-item-active");
                event.stopPropagation();
            });
        });
    }
    /**
     * 初始化某个列表
     */
    DropDown.init= function(obj) {
        var $list = obj.find(".dropdown-list");
        var $item = obj.find(".dropdown-item");
        //点击列表显示选项
        obj.unbind("click");
        obj.click(function (event) {
            DropDown.closeDropDown();
            obj.addClass("dropdown-active");
            $list.addClass("dropdown-list-active");
            event.stopPropagation();
        });
        //鼠标选中列表项
        $item.unbind("click");
        $item.click(function (event) {
            var value = $(this).text();
            obj.find(".value").text(value);
            obj.toggleClass("dropdown-active");
            $list.toggleClass("dropdown-list-active");
            $(this).siblings().removeClass("dropdown-item-active");
            $(this).addClass("dropdown-item-active");
            event.stopPropagation();
        });
    }
    /**
    * 清空所有下拉列表内容
    */
    DropDown.clearAll= function() {
        this.closeDropDown();
        var $dropdown = $(".dropdown");
        $dropdown.find(".value").text("- 选择 -");
        $dropdown.find(".dropdown-item").removeClass("dropdown-item-active");
    }

