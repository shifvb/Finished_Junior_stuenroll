$(function() {

	// 加载本模块必须要有1_4权限（查询用户）
	if(!checkPermission(["1_4"])) {
		return;
	}

	// 细粒度判断 tabContainer中的按钮
	var tabContainer = $(".tab-container");
	// 添加用户需要1_1权限
	if(checkPermission(["1_1"])) {
		tabContainer.find(".operation-item[name='addUser']").show();
	}
	// 修改用户按钮需要1_3权限
	if(checkPermission(["1_3"])) {
		tabContainer.find(".operation-item[name='modifyUser']").show();
	}
	// 删除用户按钮需要1_2权限
	if(checkPermission(["1_2"])) {
		tabContainer.find(".operation-item[name='deleteUser']").show();
	}
	// 导入用户按钮需要1_3权限
	if(checkPermission(["1_3"])) {
		tabContainer.find(".operation-item[name='importUser']").show();
	}
	// 导出用户按钮需要1_4权限
	if(checkPermission(["1_4"])) {
		tabContainer.find(".operation-item[name='exportUser']").show();
	}

	//	调试前端的时候用的， 正常使用时请注释
	// var tabContainer = $(".tab-container");
	// tabContainer.find(".operation-item[name='addUser']").show();
	// tabContainer.find(".operation-item[name='modifyUser']").show();
	// tabContainer.find(".operation-item[name='deleteUser']").show();
	// tabContainer.find(".operation-item[name='importUser']").show();
	// tabContainer.find(".operation-item[name='exportUser']").show();

	// 用户管理抽象接口
	var I_User = function() {

	};

	// 根据当前页数显示用户列表
	I_User.prototype.searchUser = function(json) {
		throw "抽象方法";
	};

	// 更新总条数和总页数
	I_User.prototype.searchUserCount = function() {
		throw "抽象方法";
	};

	// 改变网页右下角索引
	I_User.prototype.setPageIndexes = function() {
		throw "抽象方法";
	}

	// 改变user的开关状态
	I_User.prototype.changeUserStatus = function() {
		throw "抽象方法";
	};

	// 将菜单恢复到新的状态
	I_User.prototype.refreshMenu = function($menu) {
		throw "抽象方法";
	};

	// 添加用户
	I_User.prototype.addUser = function(p_json) {
		throw "抽象方法";
	}

	// 修改用户
	I_User.prototype.changeUserByUserId = function(p_json) {
		throw "抽象方法";
	}

	// 删除用户
	I_User.prototype.deleteUserByUserId = function() {
		throw "抽象方法";
	}

	// 填充全部机构的id，name
	I_User.prototype.fillMenuOrganizations = function($menu) {
		throw "抽象方法";
	}

	// 填充全部角色的id，name
	I_User.prototype.fillMenuRoles = function($menu) {
		throw "抽象方法";
	}

	// 根据选择的用户ID, 将用来修改的用户信息存入selectedUserValues变量
	I_User.prototype.searchUserById = function() {
		throw "抽象方法";
	}

	// 填充修改用户面板的input及dropdownlist的初始值
	I_User.prototype.fillMainModifyUserMenuValues = function() {
		throw "抽象方法";
	}

	// 得到添加/修改用户菜单的值
	I_User.prototype.checkAndGetUserValues = function($menu) {
		throw "抽象方法";
	}

	// 上传图片，得到photo_id
	I_User.prototype.uploadPhotoAndNextStep = function($menu) {
		throw "抽象方法";
	}

	// 好友列表抽象接口
	var I_Friend = function() {

	};

	// 搜索好友(专用于修改用户)
	I_Friend.prototype.searchFriend = function() {
		throw "抽象方法";
	}

	// 搜索好友个数(专用于修改用户)
	I_Friend.prototype.searchFriendCount = function() {
		throw "抽象方法";
	}

	// 跟据username搜索用户
	I_Friend.prototype.searchUserByUsername = function() {
		throw "抽象方法";
	}

	//	根据自己的userID和对方的userID添加对方为好友， （对方并不会知道）
	I_Friend.prototype.addUserToFriendListByUserId = function() {
		throw "抽象方法";
	}

	//  用户管理实现类
	var User = function() {

	};

	User.prototype = new I_User();

	User.prototype.searchUserCount = function() {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/systemUser/searchUserCount",
			"async": "false",
			"data": null,
			"success": function(json) {
				var count = json.result;
				$userTab.find("#totalRows").text(count);
				var _temp_totalPages = (count % 35 == 0) ? count / 35 : Math.floor(count / 35) + 1;
				if (_temp_totalPages == 0) {
					_temp_totalPages = 1;
				}
				$userTab.find("#totalPages").text(_temp_totalPages);
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	}

	User.prototype.searchUser = function(json) {
		// 如果调用的时候没有传json， 那么json中的page属性会被置为当前页
		if(json == null) {
			json = {
				"page": $userTab.find("#currentPage").text(),
			}
		}
		$.ajax({
			"type": "post",
			"url": "/stuenroll/systemUser/searchUser",
			"async": "false",
			"data": json,
			"success": function(json) {
				var data = json.result;
				var $table = $userTab.find(".data-table");
				// 清空表格数据
				$table.find("tr:gt(0)").remove();
				// 获得当前页数
				var currentPage = Number($userTab.find("#currentPage").text());
				// 当前页数的行号起始数字
				var start = (currentPage - 1) * 35;

				var temp = "";
				for(var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td><input type='checkbox' name='id' value='" + one.user_id + "' /></td>";
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td>" + one.username + "</td>";
					temp += "<td>" + one.name + "</td>";
					temp += "<td>" + one.organization_name + "</td>";
					temp += "<td>" + one.role_name + "</td>";
					temp += "<td>" + one.tel + "</td>";
					temp += "<td>" + one.email + "</td>";
					temp += "<td>";
					if(one.block) {
						temp += "<div class='statusBtnBase statusBtnBaseOff'>";
						temp += "	<div class='statusCircus statusCircusOff'></div>"
						temp += "	<span class='statusText statusTextOff no-select'>关</span>"
						temp += "</div>";

					} else {
						temp += "<div class='statusBtnBase'>";
						temp += "	<div class='statusCircus'></div>"
						temp += "	<span class='statusText no-select'>开</span>"
						temp += "</div>";
					}
					temp += "</td>";
					temp += "</tr>";
				}
				$table.append(temp);

				// 重新绑定角色状态开关
				$table.find(".statusBtnBase").click(function(event) {
					$(this).toggleClass("statusBtnBaseOff");
					$(this).find(".statusCircus").toggleClass("statusCircusOff");
					var $statusText = $(this).find(".statusText");
					$statusText.toggleClass("statusTextOff");
					if($statusText.text() == "开") {
						$statusText.text("关");
						user.changeUserStatus($statusText.parents("tr").find("input").attr("value"), "1");
					} else {
						$statusText.text("开");
						user.changeUserStatus($statusText.parents("tr").find("input").attr("value"), "0");
					}
					event.stopPropagation();
				});
				user.setPageIndexes();
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	User.prototype.setPageIndexes = function() {
		var currentPage = Number($userTab.find("#currentPage").text());
		var totalPages = Number($userTab.find("#totalPages").text());
		var indexs = $(".page-operation a").slice(1, -1);
		var start = -1;
		var end = -1;

		if(currentPage <= 2) {
			start = 0;
			end = 4;
		} else {
			start = currentPage - 3;
			end = currentPage + 1;
		}

		for(var i = start; i <= end; i++) {
			var one = $(indexs[i - start]);
			one.text(i + 1);
			one.data("page", i + 1);
			one.removeClass("page-active");
			one.removeClass("page-disable");
			one.unbind("click");
			if(i + 1 <= totalPages) {
				one.click(function(event) {
					$userTab.find("#currentPage").text($(this).data("page")),
						user.searchUser({
							"page": $(this).data("page"),
						});
					user.setPageIndexes();
					event.stopPropagation();
				});
			} else {
				one.addClass("page-disable");
			}
			if(i + 1 == currentPage) {
				one.addClass("page-active");
			}
		}
	}

	User.prototype.changeUserStatus = function(p_user_id, p_block) {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/systemUser/changeUserStatus",
			"async": "true",
			"data": {
				"user_id": p_user_id,
				"block": p_block,
			},
			"success": function(json) {
				if(json.result) {
					toastr.info("改变用户状态成功!");
				} else {
					toastr.warning("改变用户状态失败！请刷新重试！");
				}
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	User.prototype.addUser = function(p_json) {
		// ajax传后台
		$.ajax({
			"type": "post",
			"url": "/stuenroll/systemUser/addUser",
			"async": "true",
			"data": p_json,
			"traditional": true,
			"success": function(json) {
				if(json.result) {
					toastr.info("添加用户成功");
					// 消失菜单
					$(".menu").fadeOut();
					$userTab.find(".menu").parent().siblings().removeClass("operation-active");
					// 刷新表格
					setTimeout(function() {
						user.searchUserCount();
						user.searchUser();
					}, 3000);
				}
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	}

	// 填充全部机构的id，name
	User.prototype.fillMenuOrganizations = function($menu) {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/systemUser/searchAllOrganizationIdAndNames",
			"async": false,
			"data": null,
			"success": function(json) {
				var data = json.result;
				var temp = "";
				for(var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<li class='dropdown-item' data-id='" + one.organization_id + "'>" + one.organization_name + "</li>";
				}
				$menu.find("#organization .dropdown-list").html(temp);
				DropDown.init($menu.find("#organization"));
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	}

	// 填充全部角色的id，name
	User.prototype.fillMenuRoles = function($menu) {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/systemUser/searchAllRoleIdAndNames",
			"async": false,
			"data": null,
			"success": function(json) {
				var data = json.result;
				var temp = "";
				for(var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<li class='dropdown-item' data-id='" + one.role_id + "'>" + one.role_name + "</li>";
				}
				$menu.find("#role .dropdown-list").html(temp);
				DropDown.init($menu.find("#role"));
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	}

	// 将菜单恢复到新的状态
	User.prototype.refreshMenu = function($menu) {
		$menu.find("input").val("");
		var $tempDropDown = $menu.find(".dropdown");
		$tempDropDown.find(".value").text("- 选择 -");
		// 清空好友
		$menu.find(".friends-list .friends-item").remove();
		// 清空图片
		selectedPhotoName = "";
		$menu.find(".preView").attr("src", "../../img/temp_photo.PNG");
	}

	// 根据选择的用户ID, 将用来修改的用户信息存入selectedUserValues变量
	User.prototype.searchUserById = function() {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/systemUser/searchUserById",
			"async": false,
			"data": {
				"user_id": selectedUserId,
			},
			"success": function(json) {
				selectedUserValues = json.result;
				// 完成后调用函数
				user.fillMainModifyUserMenuValues();
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	}

	// 填充修改用户面板的input及dropdownlist的初始值
	User.prototype.fillMainModifyUserMenuValues = function() {
		$mainModifyUserMenu.find(".preView").attr("src", "/stuenroll/image/searchImage?collectionName=image&id=" + selectedUserValues.photo_id);
		$mainModifyUserMenu.find("#username").val(selectedUserValues.username);
		$mainModifyUserMenu.find("#password,#re-password").val(selectedUserValues.password);
		$mainModifyUserMenu.find("#question").val(selectedUserValues.question);
		$mainModifyUserMenu.find("#answer").val(selectedUserValues.answer);
		$mainModifyUserMenu.find("#name").val(selectedUserValues.name);
		$mainModifyUserMenu.find("#pid").val(selectedUserValues.pid);
		$mainModifyUserMenu.find("#tel").val(selectedUserValues.tel);
		$mainModifyUserMenu.find("#email").val(selectedUserValues.email);
		$mainModifyUserMenu.find("#position").val(selectedUserValues.position);
		$mainModifyUserMenu.find("#sex .value").text(selectedUserValues.sex);
		$mainModifyUserMenu.find("#sex .dropdown-item").each(function(i, x) {
			if($(x).text() == selectedUserValues.sex) {
				$(x).addClass("dropdown-item-active");
			}
		});
		$mainModifyUserMenu.find("#organization .value").text(selectedUserValues.organization_name);
		$mainModifyUserMenu.find("#organization .dropdown-item").each(function(i, x) {
			if($(x).data("id") == selectedUserValues.organization_id) {
				$(x).addClass("dropdown-item-active");
			}
		});
		$mainModifyUserMenu.find("#role .value").text(selectedUserValues.role_name);
		$mainModifyUserMenu.find("#role .dropdown-item").each(function(i, x) {
			if($(x).data("id") == selectedUserValues.role_id) {
				$(x).addClass("dropdown-item-active");
			}
		});
		if(selectedUserValues.block == false) {
			$mainModifyUserMenu.find("#status .value").text("开");
			$mainModifyUserMenu.find("#status .dropdown-item:eq(0)").addClass("dropdown-item-active");
		} else {
			$mainModifyUserMenu.find("#status .value").text("关");
			$mainModifyUserMenu.find("#status .dropdown-item:eq(1)").addClass("dropdown-item-active");
		}

	}

	// 检查用户是否上传/修改图片，然后根据相应的状态采取不同的行动，返回不同的值
	User.prototype.uploadPhotoAndNextStep = function($menu) {
		if($menu == $mainAddUserMenu) {
			if(selectedPhotoName == "") {
				// 如果是新建用户，没有选图片，就warning然后return null
				toastr.warning("请选择图片!");
				return;
			} else {
				// 如果是新建用户，选了图片，立即请求
				$.ajax({
					"type": "post",
					"url": "/stuenroll/image/saveImage?collectionName=image",
					"async": true,
					"data": new FormData($menu.find(".uploadImageForm")[0]),
					"cache": false,
					"contentType": false,
					"processData": false,
					"success": function(json) {
						if(json.result) {
							var _photo_id = json.result;
							temp_result = user.checkAndGetUserValues($menu, _photo_id);
						}
					},
					"error": function() {
						toastr.error("系统异常");
					}
				});
			}
		} else {
			// 如果是修改用户，判断修没修改图片再请求
			if(selectedPhotoName == "") {
				// 没有修改，就把原来用户的
				temp_result = user.checkAndGetUserValues($menu, selectedUserValues.photo_id);
			} else {
				// 修改，也请求
				$.ajax({
					"type": "post",
					"url": "/stuenroll/image/saveImage?collectionName=image",
					"async": true,
					"data": new FormData($menu.find(".uploadImageForm")[0]),
					"cache": false,
					"contentType": false,
					"processData": false,
					"success": function(json) {
						if(json.result) {
							var _photo_id = json.result;
							temp_result = user.checkAndGetUserValues($menu, _photo_id);
						}
					},
					"error": function() {
						toastr.error("系统异常");
					}
				});
			}
		}
	}

	// 得到添加/修改用户菜单的值
	User.prototype.checkAndGetUserValues = function($menu, p_photo_id) {

		// 前台检查用户名是否符合条件
		var $username = $menu.find("#username");
		if(!$username[0].checkValidity()) {
			toastr.warning("用户名格式不正确，请重新输入!");
			return;
		}
		var _username = $username.val();

		// 前台检查密码是否符合条件
		var $password = $menu.find("#password");
		if(!$password[0].checkValidity()) {
			toastr.warning("密码格式不正确，请重新输入！");
			return;
		}
		var _password = $password.val();

		// 前台检查重复密码是否符合条件
		var $repassword = $menu.find("#re-password");
		if(!$repassword[0].checkValidity()) {
			toastr.warning("重复密码格式不正确，请重新输入！");
			return;
		}

		// 前台检查密码是否和重复密码一致
		if(!($password.val() == $repassword.val())) {
			toastr.warning("两次输入密码不一致，请重新输入！");
			return;
		}

		// 前台提示问题是否符合条件
		var $question = $menu.find("#question");
		if(!$question[0].checkValidity()) {
			toastr.warning("提示问题格式错误，请重新输入！");
			return;
		}
		var _question = $question.val();

		// 前台用户答案是否符合条件
		var $answer = $menu.find("#answer");
		if(!$answer[0].checkValidity()) {
			toastr.warning("用户答案格式错误，请重新输入！");
			return;
		}
		var _answer = $answer.val();

		// 前台检查真实姓名是否符合要求
		var $name = $menu.find("#name")
		if(!$name[0].checkValidity()) {
			toastr.warning("真实姓名格式不正确，请重新输入！");
			return;
		}
		var _name = $name.val();

		// 前台检查性别是否符合要求
		var $sex = $menu.find("#sex")
		if($sex.find(".value").text() == "- 选择 -") {
			toastr.warning("请选择性别");
			return;
		}
		var _sex = $sex.find(".value").text();

		// 前台检查身份证号是否符合要求
		var $pid = $menu.find("#pid");
		if(!checkPid($pid.val())) {
			toastr.warning("身份证格式不正确，请重新输入！");
			return;
		}
		var _pid = $pid.val();

		// 前台检查电话是否符合要求
		var $tel = $menu.find("#tel");
		if(!$tel[0].checkValidity()) {
			toastr.warning("电话号码不正确，请重新输入!");
			return;
		}
		var _tel = $tel.val();

		// 前台检查电子邮箱是否符合要求
		var $email = $menu.find("#email");
		if(!$email[0].checkValidity()) {
			toastr.warning("电子邮箱输入不正确，请重新输入！");
			return;
		}
		var _email = $email.val();

		// 前台检查培训机构是否符合要求
		var $organization = $menu.find("#organization");
		if($organization.find(".value").text() == "- 选择 -") {
			toastr.warning("请选择培训机构");
			return;
		}
		var _organization_id = $organization.find(".dropdown-item-active").data("id");

		// 前台检查职位是否符合要求
		var $position = $menu.find("#position");
		if(!$position[0].checkValidity()) {
			toastr.warning("职位输入格式不正确，请重新输入！");
			return;
		}
		var _position = $position.val();

		// 前台检查角色是否符合要求
		var $role = $menu.find("#role");
		if($role.find(".value").text() == "- 选择 -") {
			toastr.warning("请选择角色!");
			return;
		}
		var _role_id = $role.find(".dropdown-item-active").data("id");

		// 前台检查用户状态是否符合要求
		var $status = $menu.find("#status");
		if($status.find(".value").text() == "- 选择 -") {
			toastr.warning("请选择用户状态！");
			return;
		}
		var _status = $status.find(".value").text();

		var check_json = {
			"username": _username,
			"password": _password,
			"question": _question,
			"answer": _answer,
			"name": _name,
			"sex": _sex,
			"pid": _pid,
			"tel": _tel,
			"email": _email,
			"organization_id": _organization_id,
			"position": _position,
			"role_id": _role_id,
			"status": _status,
			"photo_id": p_photo_id,
		};
		console.log("[DEBUG]: ");
		console.log(check_json);

		if($menu == $mainAddUserMenu) {
			// 添加用户调用addUser方法
			user.addUser(check_json);
		} else {
			// 修改用户调用changeUserByUserId方法
			user.changeUserByUserId(check_json);
		}

	}

	// 修改用户
	User.prototype.changeUserByUserId = function(p_json) {

		// 用来上传的dict
		var temp_param = {};

		// get answer
		if(p_json.answer != selectedUserValues.answer) {
			temp_param.answer = p_json.answer;
		}

		// get email
		if(p_json.email != selectedUserValues.email) {
			temp_param.email = p_json.email;
		}

		// get name
		if(p_json.name != selectedUserValues.name) {
			temp_param.name = p_json.name;
		}

		// get organization_id
		if(p_json.organization_id != selectedUserValues.organization_id) {
			temp_param.organization_id = p_json.organization_id;
		}

		// get password		
		if(p_json.password != selectedUserValues.password) {
			temp_param.password = p_json.password;
		}

		// get pid
		if(p_json.pid != selectedUserValues.pid) {
			temp_param.pid = p_json.pid;
		}

		// get position
		if(p_json.position != selectedUserValues.position) {
			temp_param.position = p_json.position;
		}

		// get question
		if(p_json.question != selectedUserValues.question) {
			temp_param.question = p_json.question;
		}

		// get role_id
		if(p_json.role_id != selectedUserValues.role_id) {
			temp_param.role_id = p_json.role_id;
		}

		// get sex
		if(p_json.sex != selectedUserValues.sex) {
			temp_param.sex = p_json.sex;
		}

		// get tel
		if(p_json.tel != selectedUserValues.tel) {
			temp_param.tel = p_json.tel;
		}

		// get username
		if(p_json.username != selectedUserValues.username) {
			temp_param.username = p_json.username;
		}

		// get block
		if(p_json.status == "关") {
			if(selectedUserValues.block == false) {
				temp_param.block = "1";
			}
		} else if(p_json.status == "开") {
			if(selectedUserValues.block == true) {
				temp_param.block = "0";
			}
		}

		// get photo_id 
		if(p_json.photo_id != selectedUserValues.photo_id) {
			temp_param.photo_id = p_json.photo_id;
		}

		// get user_id
		temp_param.user_id = selectedUserId;

		// Check if the user has changed the data, if none of above data has changed,
		// the warning will be shown.
		temp_count = 0;
		for(x in temp_param) {
			temp_count += 1;
		}
		console.log(temp_count == 1);
		if(temp_count == 1) {
			toastr.warning("未修改任何数据");
			return;
		}

		$.ajax({
			"type": "post",
			"url": "/stuenroll/systemUser/modifyUserById",
			"async": true,
			"data": temp_param,
			"success": function(json) {
				if(json.result) {
					toastr.info("修改用户成功！");
					// 隐藏菜单
					$(".menu").fadeOut();
					$userTab.find(".menu").parent().siblings().removeClass("operation-active");
					// 刷新表格
					setTimeout(function() {
						user.searchUserCount();
						user.searchUser();
					}, 3000);
				} else {
					toastr.warning("修改用户信息失败，请检查修改并重试！");
				}
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	User.prototype.deleteUserByUserId = function(p_ids) {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/systemUser/deleteUserById",
			"async": true,
			"traditional": true,
			"data": {
				"user_ids": p_ids,
			},
			"success": function(json) {
				if(json.result) {
					toastr.info("删除用户成功");
					setTimeout(function() {
						user.searchUserCount();
						user.searchUser();
					}, 3000);
				} else {
					toastr.warning("删除用户失败，请重试");
				}
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	//  好友列表实现类
	var Friend = function() {

	};

	Friend.prototype = new I_Friend();

	// 搜索好友
	Friend.prototype.searchFriend = function($menu) {
		var $currentPage = $menu.find(".currentPage");
		var currentPage = Number($currentPage.text());
		var temp_id = sessionStorage.id;
		$.ajax({
			"type": "post",
			"url": "/stuenroll/friend/searchFriend",
			"async": false,
			"data": {
				"user_id": temp_id,
				"page": currentPage,
			},
			"success": function(json) {
				if(json.result.length > 0) {
					var data = json.result;
					// 刷新的时候，收藏的置顶
					data.sort(function(x, y) {
						return x.favorite == "1" || y.favorite == "0" ? -1 : 1;
					});
					var temp_html = "";
					for(i = 0; i < data.length; i++) {
						var one = data[i];
						var t = "";
						t += "<div class='friends-item' data-targetid='" + one.user_id + "'>";
						t += "	<div class='user-img-wrapper'>";
						t += "		<img src= '" + "data:image;base64," + one.photo_base64 + "'/>";
						if(one.online) {
							t += "		<div class='status-circle'></div>";
						} else {
							t += "		<div class='status-circle status-circle-offline'></div>";
						}
						t += "	</div>";
						t += "	<div class='user-text-wrapper'>";
						t += "		<div class='text-title-1'>";
						t += "			<span>" + one.name + "</span>";
						t += "			<i class='iconfont chacha-copy'>&#xe708;</i>";
						if(one.favorite == "1") {
							t += "			<i class='iconfont xingxing xingxing-active'>&#xe601;</i>";
						} else {
							t += "			<i class='iconfont xingxing'>&#xe601;</i>";
						}

						t += "		</div>";
						t += "		<div class='text-title-2'>" + one.role_name + "</div>";
						t += "		<div class='text-title-2'>" + one.organization_name + "</div>";
						t += "	</div>";
						t += "</div>";
						temp_html += t;
					}
					$menu.find(".friends-list").html(temp_html);
				} else {
					$menu.find(".friends-list").html("<div><span style='display: inline-block; margin-top: 3.125rem; font-size: 1rem; text-align: center'>暂无好友</span></div>");
				}
				// 绑定鼠标移动激活事件
				$menu.find(".friends-item").mouseenter(function() {
					$(this).addClass("friends-item-active");
				});
				$menu.find(".friends-item").mouseleave(function() {
					$(this).removeClass("friends-item-active");
				});
				// 绑定点击叉叉删除好友事件
				$menu.find(".friends-list .friends-item .chacha-copy").click(function(event) {
					var temp_sponser_id = sessionStorage.id;
					var temp_target_id = $(this).parents(".friends-item-active").data("targetid");
					var temp_this = $(this);
					$.ajax({
						"type": "post",
						"url": "/stuenroll/friend/deleteFriend",
						"async": true,
						"data": {
							"sponser_id": temp_sponser_id,
							"target_id": temp_target_id,
						},
						"success": function(json) {
							if(json.result) {
								toastr.info("删除好友成功！");
								temp_this.parents(".friends-item").slideUp(1000, easing = "swing", callback = function() {
									temp_this.remove();
								});
							} else {
								toastr.warning("删除好友失败！");
							}
						},
						"error": function() {
							toastr.error("系统异常");
						}
					});
					event.stopPropagation();
				});
				// 绑定用户收藏事件
				$menu.find(".friends-list .friends-item .xingxing").click(function(event) {
					var temp_sponser_id = sessionStorage.id;
					var $temp_this = $(this);
					var temp_target_id = $(this).parents(".friends-item-active").data("targetid");
					var temp_favorite = $(this).hasClass("xingxing-active") ? "0" : "1";
					$.ajax({
						"type": "post",
						"url": "/stuenroll/friend/changeFriendStatus",
						"async": true,
						"data": {
							"sponser_id": temp_sponser_id,
							"target_id": temp_target_id,
							"favorite": temp_favorite,
						},
						"success": function(json) {
							if(json.result) {
								$temp_this.toggleClass("xingxing-active");
								if(temp_favorite == "1") {
									toastr.info("收藏成功");
								} else {
									toastr.info("取消收藏成功");
								}
								setTimeout(function() {
									friend.searchFriend($menu);
								}, 700);
							}
						},
						"error": function() {
							toastr.error("系统异常");
						},
					});
					event.stopPropagation();
				});
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	// 搜索好友个数
	Friend.prototype.searchFriendCount = function($menu) {
		var temp_id = sessionStorage.id;
		$.ajax({
			"type": "post",
			"url": "/stuenroll/friend/searchFriendCount",
			"async": false,
			"data": {
				"user_id": temp_id,
			},
			"success": function(json) {
				if(json.result) {
					var data = json.result;
					var totalCount = Number(data);
					var totalPages = totalCount % 13 == 0 ? totalCount / 13 : Math.floor(totalCount / 13) + 1;
					$menu.find(".totalPages").text(totalPages);
				}
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	// 跟据username搜索用户
	Friend.prototype.searchUserByUsername = function($menu) {
		$.ajax({
			"type": "post",
			"url": " /stuenroll/friend/searchUserByUsername",
			"async": true,
			"data": {
				"username": $menu.find(".friends-search #friend-search").val(),
			},
			"success": function(json) {
				if(json.result) {
					var data = json.result;
					searchedFriendValues = data;
					$temp_result_item = $menu.find(".search-result .friends-item");
					$temp_result_item.data("id", data.user_id);
					$temp_result_item.find(".result-name").text(data.name);
					$temp_result_item.find("img").attr("src", "/stuenroll/image/searchImage?collectionName=image&id=" + searchedFriendValues.photo_id);
					if(data.online) {
						// 用户在线
						$temp_result_item.find(".status-circle").removeClass("status-circle-offline");
						console.log("[DEBUG] :");
						console.log($temp_result_item.find(".status-circle")[0])
						console.log($temp_result_item.find(".status-circle").hasClass("status-circle-offline"))
					} else {
						// 用户不在线
						$temp_result_item.find(".status-circle").addClass("status-circle-offline");
					}
					$temp_result_item.find(".result-role-name").text(data.role_name);
					$temp_result_item.find(".result-organization-name").text(data.organization_name);
					$menu.find(".friends-search").animate({
						"height": "12.4375rem",
					}, 700);
				} else {
					toastr.warning("用户未找到")
				}
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	}

	//	根据自己的userID和对方的userID添加对方为好友， （对方并不会知道）
	Friend.prototype.addUserToFriendListByUserId = function($menu) {
		var temp_sponser_id = sessionStorage.id;
		var temp_target_id = $menu.find(".search-result .friends-item").data("id");
		$.ajax({
			"type": "post",
			"url": "/stuenroll/friend/addUserToFriendListByUserId",
			"async": true,
			"data": {
				"sponser": temp_sponser_id,
				"target": temp_target_id,
			},
			"success": function(json) {
				if(json.result) {
					toastr.info("添加好友成功");
					setTimeout(function() {
						friend.searchFriendCount($menu);
						friend.searchFriend($menu);
					}, 1400);
					$menu.click();
				} else {
					if(json.reason) {
						toastr.warning(json.reason);
					}
				}
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	}

	// -----------------------------------------类实现结束--------------------------------------------------

	// ----------------------------------------工厂方法开始---------------------------------------------------

	/**
	 * 工厂方法
	 * @param {Object} key
	 */
	function factory(key) {
		if(key == "User") {
			return new User();
		} else if(key == "Tab") {
			return new Tab();
		} else if(key == "Friend") {
			return new Friend();
		} else {
			return null;
		}
	}

	// ----------------------------------------工厂方法结束---------------------------------------------------

	// 初始化对象
	var user = factory("User");
	var tab = factory("Tab");
	var friend = factory("Friend");
	var $userTab = $(".tab-container .tab-content[data-index='用户列表']");
	var $addUserMenu = $userTab.find("#addUserMenu");
	var $mainAddUserMenu = $addUserMenu.find(".mainAddUserMenu");
	var $friendsAddUserMenu = $addUserMenu.find(".friends");
	var $modifyUserMenu = $userTab.find("#modifyUserMenu");
	var $mainModifyUserMenu = $modifyUserMenu.find(".mainModifyUserMenu");
	var $friendsModifyUserMenu = $modifyUserMenu.find(".friends");
	var $exportUserMenu = $userTab.find("#exportUserMenu");
	var $importUserMenu = $userTab.find("#importUserMenu");
	var selectedUserId = "";
	var selectedUserValues = {};
	var searchedFriendValues = {};
	var selectedPhotoName = ""

	//	显示html表格内容, 前台调试时请注释
	user.searchUserCount();
	user.searchUser();

	// 绑定切换选项卡，切换tab-content的事件
	$(".tab-list .tab-item").click(function() {
		var temp = $(this).data("index");
		tab.showTab(temp);
		// 切换选项卡，重新查询数据
		if(temp == "用户列表") {
			$userTab.find("#currentPage").text(1);
			user.searchUserCount();
			user.searchUser();
		}
	});

	// ---------------------------------------- “用户列表“ 绑定 start ---------------------------------------------------

	// --------------------- 1) “用户列表“ 一般绑定  start-------------------

	// 绑定 “用户列表” 面板上一页的事件
	$userTab.find("*[name='prevBtn']").click(function() {
		var $currentPage = $userTab.find("#currentPage");
		var currentPage = Number($currentPage.text());
		// 请求Ajax并更新数据
		if(currentPage > 1) {
			user.searchUser({
				"page": currentPage - 1,
			});
			$currentPage.text(currentPage - 1);
		} else {
			toastr.info("当前为第一页");
		}
	});

	// 绑定 “用户列表” 面板下一页的事件
	$userTab.find("*[name='nextBtn']").click(function() {
		var $currentPage = $userTab.find("#currentPage");
		var currentPage = Number($currentPage.text());
		var totalPages = Number($userTab.find("#totalPages").text());
		// 请求Ajax并更新数据
		if(currentPage < totalPages) {
			user.searchUser({
				"page": currentPage + 1,
			});
			$currentPage.text(currentPage + 1);
		} else {
			toastr.info("当前为最后一页");
		}
	});

	// 绑定 “角色列表”： 点击菜单以外的地方消失class为menu2, menu3的菜单
	$(document).click(function() {
		$(".menu2,.menu3").fadeOut();
		$(".menu2,.menu3").removeClass("operation-active");
	});

	// 绑定 “用户列表” 点击菜单不会发生任何事情
	$userTab.find(".menu2,.menu3").click(function(event) {
		event.stopPropagation();
	});
	$userTab.find(".menu").click(function(event) {
		$friendsAddUserMenu.find(".friends-search").slideUp();
		$friendsModifyUserMenu.find(".friends-search").slideUp();
		event.stopPropagation();
	});

	// --------------------- 1) “用户列表“ 一般绑定  end-------------------

	// --------------------- 2) “用户列表“ 添加按钮绑定  start-------------------

	// 绑定 “用户列表” 面板添加按钮 点击显示菜单
	$userTab.find("*[name='addUser']").click(function(event) {

		user.refreshMenu($addUserMenu);
		user.fillMenuOrganizations($mainAddUserMenu);
		user.fillMenuRoles($mainAddUserMenu);

		// 好友列表
		addedFriends = [];
		friend.searchFriendCount($friendsAddUserMenu);
		friend.searchFriend($friendsAddUserMenu);

		$(".menu2,.menu3").fadeOut();
		$userTab.find(".menu2,.menu3").parent().siblings().removeClass("operation-active");
		$addUserMenu.addClass("operation-active");
		$addUserMenu.fadeIn();
		event.stopPropagation();
	});

	// 绑定“用户列表” 添加用户菜单>主菜单>文本框键盘弹起事件
	$mainAddUserMenu.find("input").keyup(function() {
		var temp_bool = false;
		if($(this).attr("name") == 'pid') {
			temp_bool = checkPid($(this).val());
		} else {
			temp_bool = this.checkValidity();
		}
		if(temp_bool) {
			$(this).removeClass("input-error");
		} else {
			$(this).addClass("input-error");
		}
	});

	// 绑定“用户列表” 添加用户菜单>主菜单>点击注册事件
	$mainAddUserMenu.find("#register").click(function(event) {
		user.uploadPhotoAndNextStep($mainAddUserMenu);
		event.stopPropagation();
	});

	// 绑定“用户列表” 添加用户菜单>主菜单>点击取消按钮事件
	$mainAddUserMenu.find("#cancel").click(function(event) {
		$(".menu").fadeOut();
		$userTab.find(".menu").parent().siblings().removeClass("operation-active");
		event.stopPropagation();
	});

	// --------------------- 2) “用户列表“ 添加按钮绑定  end -------------------

	// --------------------- 3) “用户列表“ 修改按钮绑定  start -------------------

	// 绑定 “用户列表” 面板修改按钮 点击显示菜单
	$userTab.find("*[name='modifyUser']").click(function(event) {
		// 得到用户选中的用户
		var temp_selected_ids = [];
		$(".data-table input").each(function(i, x) {
			if(x.checked) {
				temp_selected_ids.push($(x).attr("value"));
			}
		});
		// 必须选中一个用户进行修改
		if(temp_selected_ids.length != 1) {
			toastr.warning("必须选择一个用户修改！");
			return;
		} else {
			selectedUserId = temp_selected_ids[0];
		}
		// 清空菜单	
		user.refreshMenu($modifyUserMenu);
		// 全舰装填dropdown-item
		user.fillMenuOrganizations($mainModifyUserMenu);
		user.fillMenuRoles($mainModifyUserMenu);
		// 全舰装填用户当前值
		user.searchUserById();
		// 好友列表
		friend.searchFriendCount($friendsModifyUserMenu);
		friend.searchFriend($friendsModifyUserMenu);

		// 隐藏所有菜单
		$(".menu2,.menu3").fadeOut();
		$userTab.find(".menu2,.menu3").parent().siblings().removeClass("operation-active");
		// 显示当前菜单
		$modifyUserMenu.addClass("operation-active");
		$modifyUserMenu.fadeIn();
		// 停止事件传播
		event.stopPropagation();
	});

	// 绑定“用户列表” 修改用户菜单>主菜单>点击注册事件
	$mainModifyUserMenu.find("#register").click(function(event) {
		var temp_result = user.uploadPhotoAndNextStep($mainModifyUserMenu);
		event.stopPropagation();
	});

	// 绑定“用户列表” 修改用户菜单>主菜单>点击取消按钮事件
	$mainModifyUserMenu.find("#cancel").click(function(event) {
		$(".menu").fadeOut();
		$userTab.find(".menu").parent().siblings().removeClass("operation-active");
		event.stopPropagation();
	});

	// --------------------- 3) “用户列表“ 修改按钮绑定  end -------------------

	// --------------------- 3) “用户列表“ 删除按钮绑定  start -------------------
	$userTab.find("[name='deleteUser']").click(function(event) {
		// 得到用户选中的用户
		var temp_selected_ids = [];
		$(".data-table input").each(function(i, x) {
			if(x.checked) {
				temp_selected_ids.push($(x).attr("value"));
			}
		});

		// 必须选中一个用户进行修改
		if(temp_selected_ids.length < 1) {
			toastr.warning("必须选择用户才能删除！");
			return;
		}

		if(confirm("确定删除所选用户？") == false) {
			return;
		}

		// ajax请求
		user.deleteUserByUserId(temp_selected_ids);
		event.stopPropagation();
	});

	// --------------------- 3) “用户列表“ 删除按钮绑定  end -------------------

	// --------------------- 4) “用户列表“ 导入按钮绑定  start -------------------

	$userTab.find("[name='importUser']").click(function(event) {
		$(".menu2,.menu3").fadeOut();
		$userTab.find(".menu2,.menu3").parent().siblings().removeClass("operation-active");
		$importUserMenu.addClass("operation-active");
		$importUserMenu.fadeIn();
		event.stopPropagation();
	});

	// 上传控件
	var $uploadFileForm = $("#uploadFileForm");
	var $selectFile = $uploadFileForm.find(".selectFile");
	$selectFile.find(".selectFilePresenter").click(function(event) {
		$selectFile.find(".addFileView").click();
		event.stopPropagation();
	});
	$uploadFileForm.find(".addFileView").on("change", handler = function() {
		var temp_path = $selectFile.find(".addFileView").val();
		temp_path = temp_path.split("\\").slice(-1);
		$uploadFileForm.find(".showFilePath").val(temp_path);
	});
	// ajax上传表单
	$uploadFileForm.find(".uploadFile").click(function() {
		// 判断用户是否已经选择文件，如果没有选择文件，就弹出提示
		var temp_file_path = $uploadFileForm.find(".showFilePath").val();
		if(temp_file_path == "") {
			toastr.warning("请选择上传文件!");
			return;
		}

		$.ajax({
			"type": "post",
			"url": "/stuenroll/systemUser/importUser",
			"async": true,
			"data": new FormData($("#uploadFileForm")[0]),
			"cache": false,
			"contentType": false,
			"processData": false,
			"success": function(json) {
				if(json.result) {
					toastr.info("导入用户数据成功！");
					$(".menu2,.menu3").fadeOut();
					$userTab.find(".menu2,.menu3").parent().siblings().removeClass("operation-active");
					setTimeout(function() {
						user.searchUserCount();
						user.searchUser();
					}, 3000);
				} else {
					toastr.info("导入用户数据失败！");
				}
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	});

	// --------------------- 4) “用户列表“ 导入按钮绑定  end -------------------

	// --------------------- 5) “用户列表“ 导出按钮绑定  start -------------------

	// 绑定 “用户列表” 面板导出按钮 点击显示菜单
	$userTab.find("[name='exportUser']").click(function(event) {
		$(".menu2,.menu3").fadeOut();
		$userTab.find(".menu2,.menu3").parent().siblings().removeClass("operation-active");
		$exportUserMenu.addClass("operation-active");
		$exportUserMenu.fadeIn();
		event.stopPropagation();
	});

	$exportUserMenu.find("input").click(function(event) {
		//		var p_type = $(this).attr("id").split("export")[1];
		//		$exportUserMenu.find("#type").attr("value", p_type);
		event.stopPropagation();
	});

	// --------------------- 5) “用户列表“ 导出按钮绑定  end -------------------

	// --------------------- 6) “用户列表“ 好友列表绑定  start -------------------

	// 好友列表 点击加号 显示搜索好友下拉菜单
	$friendsAddUserMenu.find(".friends-title i").click(function(event) {
		$friendsAddUserMenu.find("#friend-search").val("");
		$friendsAddUserMenu.find(".friends-search").css("height", "6.8125rem");
		$friendsAddUserMenu.find(".friends-search").slideToggle();
		event.stopPropagation();
	});
	$friendsModifyUserMenu.find(".friends-title i").click(function(event) {
		$friendsModifyUserMenu.find("#friend-search").val("");
		$friendsModifyUserMenu.find(".friends-search").css("height", "6.8125rem");
		$friendsModifyUserMenu.find(".friends-search").slideToggle();
		event.stopPropagation();
	});

	// 好友列表 点击好友下拉菜单 停止事件传播
	$friendsAddUserMenu.find(".friends-search").click(function(event) {
		event.stopPropagation();
	});
	$friendsModifyUserMenu.find(".friends-search").click(function(event) {
		event.stopPropagation();
	});

	// 好友列表 点击任意地方 隐藏搜索好友下拉菜单
	$friendsAddUserMenu.click(function(event) {
		$friendsAddUserMenu.find(".friends-search").slideUp();
		event.stopPropagation();
	});
	$friendsModifyUserMenu.click(function(event) {
		$friendsModifyUserMenu.find(".friends-search").slideUp();
		event.stopPropagation();
	});

	// 搜索好友下拉菜单点击叉叉清除搜索文本
	$friendsAddUserMenu.find(".friends-search .chacha-copy").click(function(event) {
		$friendsAddUserMenu.find("#friend-search").val("");
		$friendsAddUserMenu.find(".friends-search").animate({
			"height": "6.8125rem"
		}, 700);
		event.stopPropagation();
	});
	$friendsModifyUserMenu.find(".friends-search .chacha-copy").click(function(event) {
		$friendsModifyUserMenu.find("#friend-search").val("");
		$friendsModifyUserMenu.find(".friends-search").animate({
			"height": "6.8125rem"
		}, 700);
		event.stopPropagation();
	});

	// 搜索好友下拉菜单敲回车搜索好友
	$friendsAddUserMenu.find(".friends-search").keyup(function(event) {
		if(event.which == "13") {
			friend.searchUserByUsername($friendsAddUserMenu);
		}
	});
	$friendsModifyUserMenu.find(".friends-search").keyup(function(event) {
		if(event.which == "13") {
			friend.searchUserByUsername($friendsModifyUserMenu);
		}
	});

	// 好友列表 搜索结果 点击加号添加好友，消失结果菜单
	$friendsAddUserMenu.find(".search-result .jia").click(function(event) {
		friend.addUserToFriendListByUserId($friendsAddUserMenu);
		event.stopPropagation();
	});
	$friendsModifyUserMenu.find(".search-result .jia").click(function(event) {
		friend.addUserToFriendListByUserId($friendsModifyUserMenu);
		event.stopPropagation();
	});

	// 好友列表 上翻页
	$friendsAddUserMenu.find(".prevBtn").click(function(event) {
		var $f_t_currentPage = $friendsAddUserMenu.find(".currentPage");
		var f_t_currentPage = Number($f_t_currentPage.text());
		if(f_t_currentPage == 1) {
			toastr.info("当前为第一页");
			event.stopPropagation();
			return;
		}
		$f_t_currentPage.text(f_t_currentPage - 1);
		friend.searchFriend($friendsAddUserMenu);
		event.stopPropagation();
	});
	$friendsModifyUserMenu.find(".prevBtn").click(function(event) {
		var $f_t_currentPage = $friendsModifyUserMenu.find(".currentPage");
		var f_t_currentPage = Number($f_t_currentPage.text());
		if(f_t_currentPage == 1) {
			toastr.info("当前为第一页");
			event.stopPropagation();
			return;
		}
		$f_t_currentPage.text(f_t_currentPage - 1);
		friend.searchFriend($friendsModifyUserMenu);
		event.stopPropagation();
	});

	// 好友列表 下翻页
	$friendsAddUserMenu.find(".nextBtn").click(function(event) {
		var $f_t_currentPage = $friendsAddUserMenu.find(".currentPage");
		var $f_t_totalPages = $friendsAddUserMenu.find(".totalPages");
		var f_t_currentPage = Number($f_t_currentPage.text());
		var f_t_totalPages = Number($f_t_totalPages.text());
		if(f_t_currentPage == f_t_totalPages) {
			toastr.info("当前为最后一页");
			event.stopPropagation();
			return;
		}
		$f_t_currentPage.text(f_t_currentPage + 1);
		friend.searchFriend($friendsAddUserMenu);
		event.stopPropagation();
	});
	$friendsModifyUserMenu.find(".nextBtn").click(function(event) {
		var $f_t_currentPage = $friendsModifyUserMenu.find(".currentPage");
		var $f_t_totalPages = $friendsModifyUserMenu.find(".totalPages");
		var f_t_currentPage = Number($f_t_currentPage.text());
		var f_t_totalPages = Number($f_t_totalPages.text());
		if(f_t_currentPage == f_t_totalPages) {
			toastr.info("当前为最后一页");
			event.stopPropagation();
			return;
		}
		$f_t_currentPage.text(f_t_currentPage + 1);
		friend.searchFriend($friendsModifyUserMenu);
		event.stopPropagation();
	});

	// --------------------- 6) “用户列表“ 好友列表绑定  end -------------------

	// ---------------------------------------- “角色列表“ 绑定 end ---------------------------------------------------

	// 添加用户和修改用户的图片上传控件
	var $uploadImageForm = $(".uploadImageForm");
	$uploadImageForm.find(".preView").click(function(event) {
		$(this).parents(".uploadImageForm").find(".file").click();
		event.stopPropagation();
	});
	$uploadImageForm.find(".file").on("change", handler = function() {
		// get filename to calculate extension
		var temp_filename = $(this).parents(".uploadImageForm").find(".file").val();
		var $temp_this = $(this);

		// extension
		var _ext = temp_filename.split(".").slice(-1)[0].toLowerCase();
		if(temp_filename == "") {
			return;
		}
		// only .png, .bmp, .jpg and .gif formats are supported
		if(['png', 'bmp', 'jpg', 'gif'].indexOf(_ext) == -1) {
			toastr.warning("不支持的图片格式: " + _ext);
			return;
		}
		// html5 FileReader
		var reader = new FileReader();
		reader.readAsDataURL($(this).parents(".uploadImageForm").find(".file")[0].files[0]);

		// When this reader is loaded, set the src of the preView <img> to this.result
		reader.onload = function(e) {
			$temp_this.parents(".uploadImageForm").find(".preView").attr("src", this.result);
		}
		selectedPhotoName = temp_filename;
	});

	// 下拉菜单初始化静态方法
	DropDown.initAll();

});