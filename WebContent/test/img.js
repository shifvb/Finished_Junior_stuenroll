$(function(){
	var img_id=null;
	$(".a-upload").on("change","input[type='file']",function(){
		var path=$(this).val();		
		alert(path)
		var formData = new FormData($("#uploadForm")[0]);
		$.ajax({
			"url" : "/stuenroll/image/saveImage",
			"type" : "post",
			// "dataType" : "json",
			"data" : formData,
			"async" : false,
			"cache" : false,
			"contentType" : false,
			"processData" : false,
			"success" : function(json) {
				img_id = json.result;
				alert("图片上传成功！");
				alert(img_id);
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
		$("img").attr("src", "http://127.0.0.1/stuenroll/image/searchImage?id="+img_id);
	});
	
});
