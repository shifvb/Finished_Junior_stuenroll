/**
 * 首页类
 */

$(function () {
	/**
	 * 抽象的主页接口
	 */
	var I_Index = function() {

	}
	
	/**
	 * 验证身份证号
	 */
	I_Index.prototype.validatePid = function(json)  {
		throw "抽象方法";
	}
	
	/**
	 * 切换栏目抽象方法
	 */
	I_Index.prototype.downloadModal = function(json) {
		throw "抽象方法";
	}
	
	/**
	 * 主页实现类
	 */
	var Index = function() {

	}
	
	Index.prototype = new I_Index(); // 继承父类

	/**
	 * 验证身份证号
	 */
	Index.prototype.validatePid = function(json)  {
		var flag=false;
		$.ajax({
			"url":"/stuenroll/register/hasRecord",
			"type":"post",
			"dataType":"json",
			
			"data": json,
			"success":function(json){
				if(json.result){
					flag=true;
				}
			},
			"error":function(){
				toastr.error("系统错误");
				$pid.addClass("error");
                $download.attr("disabled", "disabled");
			}
		});
		return flag;
	}
	
	function factory(key){
		if(key == "Index"){
			return new Index();
		}
	}
	
	var index = factory("Index");
	
	var modal = new Modal();
	var $downloadModal = $("#downloadModal");
	var $pid = $downloadModal.find("#pid"); //身份证文本框
	var $myReportLink = $("#myReportLink"); //下载我的报名表连接
	var $download = $downloadModal.find("*[name='download']");
	
	var $organizationModal=$("#organizationModal");
	var $professionModal=$("#professionModal");
	//弹出下载报名表
	$myReportLink.click(function(){
    	modal.show($downloadModal);
	});
	
	$download.click(function() {
		$downloadModal.find("form").submit();
		$downloadModal.find("*[name='close']").click(); //关闭界面
	});
	//弹出培训机构列表
	$("#organization").click(function(){
		$.ajax({			
			"url":"/stuenroll/organization/searchOrganization",
			"type": "post",
			"dataType": "json",
			"async": false,
			"data": {
				"year": new Date().getFullYear()
			},
			"success": function(json) {
				var data = json.result;
				var table = $("#organizationModal .data-table");
				// 清空表格数据
				table.find("tr:gt(0)").remove();
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td>" + (i + 1) + "</td>";
					temp += "<td>" + one.name + "</td>";
					temp += "<td>" + one.liaison + "</td>";
					temp += "<td>" + one.liaison_tel + "</td>";
					temp += "</tr>";
				}
				table.append(temp);
			},
			"error": function(json) {
				alert("系统错误");
			}
		});
		modal.show($organizationModal);
	});
	//弹出培训专业列表
	$("#profession").click(function(){
		$.ajax({			
			"url":"/stuenroll/profession/searchProfessionInYearAtDropDown",
			"type": "post",
			"dataType": "json",
			"async": false,
			"data": {
				"year": new Date().getFullYear()
			},
			"success": function(json) {
				var data = json.result;
				var table = $("#professionModal .data-table");
				// 清空表格数据
				table.find("tr:gt(0)").remove();
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td>" + (i + 1) + "</td>";
					temp += "<td>" + one.name + "</td>";
					temp += "</tr>";
				}
				table.append(temp);
			},
			"error": function(json) {
				alert("系统错误");
			}
		});
		modal.show($professionModal);
	});
	$pid.keyup(function () {
        if (this.checkValidity()) {
        	/*var bool = index.validatePid({
                 "pid": $pid.val()
            });*/
//        	if(bool){
        		$pid.removeClass("error");
                $download.removeAttr("disabled");
//        	}
//        	else{
//        		$pid.addClass("error");
//        	}
        }
        else{
        	$pid.addClass("error");
            $download.attr("disabled", "disabled");
        }
	});
})
