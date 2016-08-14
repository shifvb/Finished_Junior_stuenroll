$(function(){
	/**
	 * 抽象的边栏接口
	 */
	var I_Message = function() {

	}

	/**
	 * 切换栏目抽象方法
	 */
	I_Message.prototype.receivepage = function() {
		throw "抽象方法";
	}
	I_Message.prototype.receivepage2 = function() {
		throw "抽象方法";
	}
	I_Message.prototype.delect = function() {
		throw "抽象方法";
	}
	/**
	 * 实现类
	 */
	var Message = function() {
		
	}
	Message.prototype = new I_Message();
	
//-------------------------------------------------------------------------------------------
	/**
	 * 删除消息
	 */
	Message.prototype.delect = function(id){
		$.ajax({
			"type" : "post",
			"url" : "/stuenroll/message/delect",
			"dataType" : "json",
			"data" : {
				"id" : id
			},
			"success" : function(json) {
				toastr.warning("一条记录已被永久删除");
			},
			"error" : function(){
				toastr.error("权限不够 请联系超管");
			}
		})
	}
	/**
	 * 收消息
	 */
	Message.prototype.receivepage = function(){
		var date = new Date();
		var year = date.getFullYear();
		var month= date.getMonth()+1;
		month=checkTime(month);
		var day = date.getDate();
		day=checkTime(day);
		var time = year+''+month+''+day;
		function checkTime(i){
			if (i<10) {
				i="0" + i
			}
		  	return i
		}
		$.ajax({
			"type" : "post",
			"url" : "/stuenroll/message/receive",
			"dataType" : "json",
			"data" : {
				"type" : 1
			},
			"success" : function(json) {
				var bar = $(".getmailbar");
				var data = json.result;
				var list3 = [];var list = [];
				data.map(function(one){
					if($.inArray(one.systime,list3)==-1){
						list3.push(one.systime);
					}
				});
				list3.sort();
				for(var i=0;i<list3.length;i++){
					list[i] = list3[list3.length-i-1];
				}
				var list2 = list.map(function(one){
					if(one==time){
						one = "今天";
					}
					if(one==time-1){
						one = "昨天";
					}
					return one;
				})
				var temp = "";var list4=[];
				for (var i = 0; i < list.length; i++) {
					var flag = 0;
					for (var j =0; j < data.length; j++) {
						if(data[j].systime == list[i]){
							flag++;
						}
					}
					list4[i] = flag;
				}
				for (var i = 0; i < list.length; i++) {
					temp+='<div class="daymail">';
					temp+='		<div class="date"><span>'+list2[i]+'</span><span>('+list4[i]+'封)</span></div>';
					temp+='		<dl class="mail-list">';
					for (var j = data.length-1; j>=0; j--) {
						if(data[j].systime == list[i]){
							temp+='				<dt class="mail-item" data-index="'+data[j].id+'">';
							temp+='					<dl class="title-list">';
							temp+='						<dt class="title-item">公告/消息</dt>';
							temp+='						<dt class="title-item">From : '+data[j].writer+'</dt>';
							temp+='						<dt class="title-item">'+data[j].title+'</dt>';
							temp+='						<dt class="title-item">'+data[j].message+'</dt>';														
							temp+='						<dt class="title-item">'+data[j].systime2+'</dt>';
							temp+='                     <dt class="title-item delect hidden">删除</dt>'
							temp+='					</dl>';
							temp+='				</dt>';
						}
					}
					temp+='		</dl>';
					temp+='</div>';
				}
				bar.empty();
				bar.append(temp);
				$(".getmailbar .delect").click(function(){
					var id = $(this).parent().parent().data("index");
					message.delect(id);
					message.receivepage();
				})
				var item = $(".daymail .hidden");
				var permissions = sessionStorage.permissions.split(","); // 权限数组
				permissions.map(function(one){
					if(one == "9_2"){
						$(".daymail .delect").removeClass("hidden");
					}
				})
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		})
		
		
	}			
					
	/**
	 * 收取发消息历史记录
	 */
	Message.prototype.receivepage2 = function(){
		var date = new Date();
		var year = date.getFullYear();
		var month= date.getMonth()+1;
		month=checkTime(month);
		var day = date.getDate();
		day=checkTime(day);
		var time = year+''+month+''+day;
		function checkTime(i){
			if (i<10) {
				i="0" + i
			}
		  	return i
		}
		$.ajax({
			"type" : "post",
			"url" : "/stuenroll/message/receive",
			"dataType" : "json",
			"data" : {
				"type" : 2
			},
			"success" : function(json) {
				var bar = $(".getsendmailbar");
				var data = json.result;
				var list3 = [];var list = [];
				data.map(function(one){
					if($.inArray(one.systime,list3)==-1){
						list3.push(one.systime);
					}
				});
				list3.sort();
				for(var i=0;i<list3.length;i++){
					list[i] = list3[list3.length-i-1];
				}
				var list2 = list.map(function(one){
					if(one==time){
						one = "今天";
					}
					if(one==time-1){
						one = "昨天";
					}
					return one;
				})
				var temp = "";var list4=[];
				for (var i = 0; i < list.length; i++) {
					var flag = 0;
					for (var j =0; j < data.length; j++) {
						if(data[j].systime == list[i]){
							flag++;
						}
					}
					list4[i] = flag;
				}
				for (var i = 0; i < list.length; i++) {
					temp+='<div class="daymail">';
					temp+='		<div class="date"><span>'+list2[i]+'</span><span>('+list4[i]+'封)</span></div>';
					temp+='		<dl class="mail-list">';
					for (var j = 0; j < data.length; j++) {
						if(data[j].systime == list[i]){
							temp+='				<dt class="mail-item" data-index="'+data[j].id+'">';
							temp+='					<dl class="title-list">';
							temp+='						<dt class="title-item">公告/消息</dt>';
							temp+='						<dt class="title-item">To : '+data[j].receiver+'</dt>';
							temp+='						<dt class="title-item">'+data[j].title+'</dt>';
							temp+='						<dt class="title-item">'+data[j].message+'</dt>';							
							temp+='						<dt class="title-item">'+data[j].systime2+'</dt>';
							temp+='                     <dt class="title-item delect hidden">删除</dt>'
							temp+='					</dl>';
							temp+='				</dt>';
							console.log(data[j].title);
						}
					}
					temp+='		</dl>';
					temp+='</div>';
				}
				bar.empty();
				bar.append(temp);
				$(".getsendmailbar .delect").click(function(){
					var id = $(this).parent().parent().data("index");
					message.delect(id);
					message.receivepage2();
				})
				var item = $(".daymail .hidden");
				var permissions = sessionStorage.permissions.split(","); // 权限数组
				permissions.map(function(one){
					if(one == "9_2"){
						$(".daymail .delect").removeClass("hidden");
					}
				})
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		})
//		message.addPermission();
	}
	/**
	 * 工厂方法
	 * 
	 * @param key
	 */
	function Factory(key) {
		if (key == "Message") {
			return new Message();
		}
	}

	// 申明变量
	var message = Factory("Message");
	//初始化
	message.receivepage();
//--------------------------------------------------------------------------------------------
	$(".title-list .title-item").click(function(){
		$(".title-list .title-item").removeClass("active");
		$(this).addClass("active");
		var flag = $(this).data("index");
		var index = "."+flag;
		$(index).siblings().addClass("hidden");
		$(".chart-container").removeClass("hidden");
		$(index).removeClass("hidden");
		if(flag == "getmailbar"){
			message.receivepage();
		}
		if(flag == "getsendmailbar"){
			message.receivepage2();
		}
		if(flag == "majormessage"){
			$.ajax({
				"type" : "post",
				"url" : "/stuenroll/message/querymajor",
				"dataType" : "json",
				"success" : function(json) {
					var result = json.result;
//					sessionStorage.major_hash = result;
					var list=[];
					result.map(function(one){
						if($.inArray(one.name,list)==-1){
							list.push(one.name);
						}
					});
					sessionStorage.major_hash = list;
					var bar = $("#write2-major");
					var temp = "";
					var list2 =[];
					for(var i = 0; i < list.length; i++){
						result.map(function(one){
							if(one.name == list[i]){
								list2.push(one.year);
							}
						});
						sessionStorage.setItem(list[i],list2);
						list2 = [];
						temp+='<option value="'+list[i]+'">'+list[i]+'</option>';
					}
					if(list.length!=0){
						bar.empty();
					}
					else{
						$("#write2-btn2").addClass("hidden");
					}
					bar.append(temp);
					var str = list[0];
					var year = sessionStorage.getItem(str);
					var list = year.split(",");
					var bar = $("#write2-grade");
					var temp = "";
					for(var i = 0; i < list.length; i++){
						temp+='<option value="'+list[i]+'">'+list[i]+'</option>';
					}
					bar.empty();
					bar.append(temp);
				},
				"error" : function() {
					toastr.error("系统异常");
				}
			})
			
		}
	})
	$("#write-btn2").click(function(){
		var name = $("#write-receiver").val();
		var title = $("#write-title").val();
		var maininfo = $("#write-maininfo").val();
		$.ajax({
			"type" : "post",
			"url" : "/stuenroll/message/write",
			"dataType" : "json",
			"data" : {
				"title" : title,
				"maininfo" : maininfo,
				"name" : name
			},
			"success" : function(json) {
				toastr.success("邮件发送成功");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		})
	})
	$("#write2-btn2").click(function(){
		var year = $("#write2-grade").val();
		var profession = $("#write2-major").val(); 
		var title = $("#write2-title").val();
		var maininfo = $("#write2-maininfo").val();
		var listtext = $("#write2-listtext").val();
		$.ajax({
			"type" : "post",
			"url" : "/stuenroll/message/writemajormessage",
			"dataType" : "json",
			"data" : {
				"year" : year,
				"text" : maininfo,
				"title" : title,
				"profession" : profession,
				"list_text" : listtext
			},
			"success" : function(json) {
				toastr.success("邮件发送成功");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		})
	})
	$(".getsendmailbar .delect").click(function(){
		$(this).parent().parent().empty();
	})
	$("#write2-major").change(function(){
		var str = $(this).val();
		var year = sessionStorage.getItem(str);
		var list = year.split(",");
		var bar = $("#write2-grade");
		var temp = "";
		for(var i = 0; i < list.length; i++){
			temp+='<option value="'+list[i]+'">'+list[i]+'</option>';
		}
		bar.empty();
		bar.append(temp);
	})
})
