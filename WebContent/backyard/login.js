$(function() {
	/**
	 * 抽象的登陆接口
	 */
	var I_Login = function() {

	}

	/**
	 * 登陆系统抽象方法
	 */
	I_Login.prototype.login = function() {
		throw "抽象方法";
	}

	/**
	 * 显示登录面板抽象方法
	 */
	I_Login.prototype.show = function() {
		throw "抽象方法";
	}

	/**
	 * 隐藏登陆面板抽象方法
	 */
	I_Login.prototype.hide = function() {
		throw "抽象方法";
	}

	/**
	 * 登陆实现类
	 */
	var LoginCommon = function() {

	}

	LoginCommon.prototype = new I_Login(); // 继承父类

	LoginCommon.prototype.login = function() {
		var bool = true;
		var input = $(".login-container .input");
		input.each(function(i, one) {
			bool = bool && one.checkValidity();
		});
		if (!bool) {
			return;
		}
		// Ajax登陆系统
		$.ajax({
			"url" : "http://127.0.0.1/stuenroll/user/login",
			"type" : "post",
			"dataType" : "json",
			"data" : {
				"username" : $(".login-container #username").val(),
				"password" : $(".login-container #password").val()
			},
			"success" : function(json) {
				if (json.result) {
					console.log(json.result);
					//本地存储数据
					sessionStorage.id = json.id;
					sessionStorage.photo_id = json.photo_id;
					sessionStorage.username=json.username;
					sessionStorage.name=json.name;
					sessionStorage.organization=json.organization;
					sessionStorage.organizationId=json.organizationId;
					sessionStorage.role=json.role;
					var temp="";
					var one = "";
					for(var i = 0; i < json.permissions.length; i++){
						one = json.permissions[i].permission_id;
						if(i==0){
							temp=temp+one;
						}
						else{
							temp=temp+","+one;
						}
					}
					sessionStorage.permissions=temp;
					
					location.href = "/stuenroll/backyard/index.html";
				} else {
					toastr.warning(json.reason);
				}
			},
			"error" : function() {
				alert("登陆失败");
			}
		});
	}
	LoginCommon.prototype.show = function() {
		$(".login-container").fadeIn();
	}
	LoginCommon.prototype.hide = function() {
		$(".login-container").fadeOut();
	}

	/**
	 * 抽象找回密码接口
	 */
	var I_Recover = function() {

	}

	/**
	 * 密码找回抽象方法
	 */
	I_Recover.prototype.recover = function() {
		throw "抽象方法";
	}

	/**
	 * 显示密码找回面板抽象方法
	 */
	I_Recover.prototype.show = function() {
		throw "抽象方法";
	}

	/**
	 * 隐藏密码找回面板抽象方法
	 */
	I_Recover.prototype.hide = function() {
		throw "抽象方法";
	}

	/**
	 * 切换步骤面板抽象方法
	 */
	I_Recover.prototype.next = function(i, step) {
		throw "抽象方法";
	}

	/**
	 * 密码找回类
	 */
	var RecoverCommon = function() {

	}

	RecoverCommon.prototype = new I_Recover();
	RecoverCommon.prototype.recover = function() {

	}
	RecoverCommon.prototype.show = function() {
		$(".recover-container").fadeIn();
	}
	RecoverCommon.prototype.hide = function() {
		$(".recover-container").fadeOut(function() {
			$(".step-wrapper").css("margin-left", 0); // 切换回第一个step
			$(".recover-container .input").val(""); // 清空文本框内容
			$(".step-container .step-item:gt(0)").removeClass("step-active"); //进度条切换回第一个进度
		});

	}
	RecoverCommon.prototype.next = function(i, step) {
		var bool = true;
		var input = step.find(".input"); // 查找step面板里面所有的文本框

		input.each(function(i, one) {
			if (one.id == "pid") {
				bool = bool && checkPid($(one).val());
			}
			else {
				bool = bool && one.checkValidity();
			}
		});

		if (!bool) {
			toastr.error("内容错误，请核实填写的内容");
			return;
		}

		function slide(i) {
			$(".step-wrapper").css("margin-left", -770 * i / 16 + "rem");
			$(".step-item").eq(i).addClass("step-active");
		}

		if (i == 1) {
			// 查询用户信息
			$.ajax({
				"url" : "/stuenroll/user/checkRecoverPersonalInfo",
				"type" : "post",
				"dataType" : "json",
				"data" : {
					"name" : $(".recover-container #name").val(),
					"pid" : $(".recover-container #pid").val(),
					"organization" : $(".recover-container #organization").val()
				},
				"success" : function(json) {
					if (json.result) {
						slide(i);
					}
					else {
						toastr.warning("填写的内容不真实，请重新核对");
					}
				},
				"error" : function() {
					toastr.error("系统异常");
				}
			});

		}
		else if (i == 2) {
			// 查询账号信息
			$.ajax({
				"url" : "/stuenroll/user/checkAccountInfo",
				"type" : "post",
				"dataType" : "json",
				"data" : {
					"username" : $(".recover-container #recoverUsername").val(),
					"email" : $(".recover-container #email").val(),
					"tel" : $(".recover-container #tel").val()
				},
				"success" : function(json) {
					if (json.result) {
						slide(i)
					}
					else {
						toastr.warning("填写的内容不真实，请重新核对");
					}
				},
				"error" : function() {
					toastr.error("系统异常");
				}
			});
		}
		else if (i == 3) {
			// 重设密码
			var flag = false;
			$.ajax({
				"url" : "/stuenroll/user/checkQuestionInfo",
				"async" : false, // 同步发送Ajax请求
				"type" : "post",
				"dataType" : "json",
				"data" : {
					"password" : $(".recover-container #newPassword").val(),
					"question" : $(".recover-container #question").val(),
					"answer" : $(".recover-container #answer").val()
				},
				"success" : function(json) {
					if (json.result) {
						flag = true;
					}
					else {
						toastr.warning("填写的内容不真实，请重新核对");
					}
				},
				"error" : function() {
					toastr.error("系统异常");
				}

			});

			if (!flag) {
				return;
			}
			$.ajax({
				"url" : "/stuenroll/user/recoverPassword",
				"type" : "post",
				"dataType" : "json",
				"data" : null,
				"success" : function(json) {
					if (json.result) {
						slide(i); // 设置定时器跳转
						var second = $(".recover-container .second");
						var timer = setInterval(function() {
							var i = second.text();
							i = i - 1;
							second.text(i);
							if (i == 0) {
								recover.hide();
								login.show();
								clearInterval(timer);
							}
						}, 1000);
					}
					else {
						toastr.warning("密码重设失败");
					}
				},
				"error" : function() {
					toastr.error("系统异常");
				}
			});

		}
		else if (i == 4) {

		}
	}

	/**
	 * 工厂方法
	 * 
	 * @param {Object}
	 *            key
	 */
	function factory(key) {
		if (key == "LoginCommon") {
			return new LoginCommon();
		}
		else if (key == "RecoverCommon") {
			return new RecoverCommon();
		}
		else {
			return null;
		}
	}

	var login = factory("LoginCommon"); // 从工厂方法获得登陆对象
	var recover = factory("RecoverCommon"); // 从工厂方法获得密码找回对象

	// 登陆按钮点击事件
	$("#login-btn").click(function() {
		login.login();
	});
	$(document).keydown(function(event){
		if(event.keyCode == 13)
		login.login();
	})

	// 找回密码链接点击事件
	$("#link-recover").click(function() {
		login.hide(); // 隐藏登录面板
		recover.show(); // 显示密码找回面板
	});

	// 找回密码的下一步按钮点击事件
	$(".recover-container *[name='next']").click(function() {
		var i = $(this).data("index");
		// 获得下一步按钮所在的step面板
		var step = $(this).parents(".step");
		recover.next(i, step);
	});

	// 找回密码面板输入项键盘弹起事件
	$(".recover-container .input").keyup(function() {
		if (this.id != "pid" && this.checkValidity()) {
			$(this).removeClass("error");
		}
		else {
			$(this).addClass("error");
		}

		if (this.id == "pid") {
			if (checkPid($(this).val())) {
				$(this).removeClass("error");
			}
			else {
				$(this).addClass("error");
			}
		}
	});
	
	//找回密码取消按钮
	$(".recover-container *[name='cancel']").click(function(){
		recover.hide();
		login.show();
	});

	// 登陆面板输入项键盘弹起事件
	$(".login-container .input").keyup(function() {
		if (this.checkValidity()) {
			$(this).removeClass("error");
		}
		else {
			$(this).addClass("error");
		}
	});

	
});