$(function(){
	$("#btn").click(function(){
		var username=$("#username");	//文本框
		$.ajax({
			"url":"/stuenroll/test/testController/sayHelloAjax",
			"type":"post",
			"dataType":"json",
			"data":{"username":username.val()},	//上传数据
			"success":function(resp){
//				alert("成功");
				var $info=$("#info");
				$info.empty();//清空div内容
				$info.append("<p>"+resp.name+"</p>");
				$info.append("<p>"+resp.sex+"</p>");
				$info.append("<p>"+resp.tel+"</p>");
			},
			"error":function(){
				alert("失败");
			}
		
		});
	});
});