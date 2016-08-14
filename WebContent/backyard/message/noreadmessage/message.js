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
				toastr.error("系统异常");
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
			"url" : "/stuenroll/message/queryinfo",
			"dataType" : "json",
			"data" : {
				"type" : 2
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
							temp+='						<dt class="title-item">系统消息</dt>';
							temp+='						<dt class="title-item">From : '+data[j].writer+'</dt>';
							temp+='						<dt class="title-item">'+data[j].title+'</dt>';
							temp+='						<dt class="title-item">'+data[j].message+'</dt>';														
							temp+='						<dt class="title-item">'+data[j].systime2+'</dt>';
							temp+='                     <dt class="title-item delect"></dt>'
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
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		})
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
	$(".getsendmailbar .delect").click(function(){
		$(this).parent().parent().empty();
	})
})
