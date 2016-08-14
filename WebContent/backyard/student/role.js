$(function() {

	// 加载本模块必须要有2_4权限（查询角色）
	if (!checkPermission(["2_4"])) {
		return;
	}

	// 细粒度判断 tabContainer中的按钮
	var tabContainer = $(".tab-container");
	// 添加角色按钮需要2_1权限
	if (checkPermission(["2_1"])) {
		tabContainer.find(".operation-item[name='addRole']").show();
	}
	// 修改角色按钮需要2_3权限
	if (checkPermission(["2_3"])) {
		tabContainer.find(".operation-item[name='modifyRole']").show();
	}
	// 删除角色按钮需要2_2权限
	if (checkPermission(["2_2"])) {
		tabContainer.find(".operation-item[name='deleteRole']").show();
	}

	// 调试前端的时候用的，正常使用时请注释
	//	var tabContainer = $(".tab-container");
	//	tabContainer.find(".operation-item[name='addRole']").show();
	//	tabContainer.find(".operation-item[name='modifyRole']").show();
	//	tabContainer.find(".operation-item[name='deleteRole']").show();

	// 权限管理抽象接口
	var I_Role = function() {

	};

	// 根据当前页数显示角色列表
	I_Role.prototype.searchRole = function(json) {
		throw "抽象方法";
	};

	// 更新总条数和总页数
	I_Role.prototype.searchRoleCount = function() {
		throw "抽象方法";
	};

	// 改变role的开关状态
	I_Role.prototype.changeRoleStatus = function() {
		throw "抽象方法";
	};

	// 填充添加角色按钮和修改角色按钮的下拉菜单的权限选项(checkbox)
	I_Role.prototype.fillMenuPermissions = function() {
		throw "抽象方法";
	};

	// 将菜单恢复到新的状态
	I_Role.prototype.refreshMenu = function($menu) {
		throw "抽象方法";
	};

	// 将当前角色拥有的权限所对应的复选框打上勾
	I_Role.prototype.showPermissionsBelongToRole = function() {
		throw "抽象方法";
	}

	// 添加角色
	I_Role.prototype.addRole = function() {
		throw "抽象方法";
	}

	// 修改角色
	I_Role.prototype.changeRoleByRoleId = function() {
		throw "抽象方法";
	}

	// 删除角色
	I_Role.prototype.deleteRoleByRoleId = function() {
		throw "抽象方法";
	}

	// 改变网页右下角索引
	I_Role.prototype.setPageIndexes = function() {
		throw "抽象方法";
	}

	// 权限管理实现类
	var Role = function() {

	};

	Role.prototype = new I_Role();

	Role.prototype.searchRole = function(json) {
		// 如果调用的时候没有传json， 那么json中的page属性会被置为当前页

		if (json == null) {
			json = {
				"page": $("#currentPage").text(),
			}
		}
		console.log("[DEBUG] current page: " + json.page);
		$.ajax({
			"type": "post",
			"url": "/stuenroll/role/searchRole",
			"async": "false",
			"data": json,
			"success": function(json) {
				var data = json.result;
				var table = $(".tab-container .tab-content[data-index='角色列表'] .data-table");
				// 清空表格数据
				table.find("tr:gt(0)").remove();
				// 获得当前页数
				var currentPage = $(".tab-container .tab-content[data-index='角色列表'] #currentPage").text();
				currentPage = new Number(currentPage);
				// 当前页数的行号起始数字
				var start = (currentPage - 1) * 35;

				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td><input type='checkbox' name='id' value='" + one.id + "' /></td>";
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td><div class='text-overflow' title=" + one.角色名称 + ">" + one.角色名称 + "</div></td>";
					temp += "<td>" + one.权限数量 + "</td>";
					temp += "<td>" + one.用户数量 + "</td>";
					temp += "<td>" + one.有效用户 + "</td>";
					temp += "<td>" + one.无效用户 + "</td>";
					temp += "<td>" + one.就业网用户 + "</td>";
					temp += "<td>" + one.机构用户 + "</td>";
					temp += "<td>";
					if (one.角色状态) {
						temp += "<div class='statusBtnBase'>";
						temp += "	<div class='statusCircus'></div>"
						temp += "	<span class='statusText no-select'>开</span>"
						temp += "</div>";
					} else {
						temp += "<div class='statusBtnBase statusBtnBaseOff'>";
						temp += "	<div class='statusCircus statusCircusOff'></div>"
						temp += "	<span class='statusText statusTextOff no-select'>关</span>"
						temp += "</div>";
					}
					temp += "</td>";
					temp += "</tr>";
				}

				table.append(temp);
				// 重新绑定角色状态开关
				var $table = $(".tab-container .tab-content[data-index='角色列表'] .data-table");
				$table.find(".statusBtnBase").click(function(event) {
					$(this).toggleClass("statusBtnBaseOff");
					$(this).find(".statusCircus").toggleClass("statusCircusOff");
					var $statusText = $(this).find(".statusText");
					$statusText.toggleClass("statusTextOff");
					if ($statusText.text() == "开") {
						$statusText.text("关");
						role.changeRoleStatus($statusText.parents("tr").find("input").attr("value"), false);
					} else {
						$statusText.text("开");
						role.changeRoleStatus($statusText.parents("tr").find("input").attr("value"), true);
					}
					event.stopPropagation();
				});

				role.setPageIndexes();
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	};

	Role.prototype.searchRoleCount = function() {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/role/searchRoleCount",
			"async": "false",
			"success": function(json) {
				var count = json.result; // 总记录数
				var content = $(".tab-container .tab-content[data-index='角色列表']");
				content.find("#totalRows").text(count);
				var totalPages = (count % 35 == 0) ? count / 35 : Math.floor(count / 35) + 1;
				content.find("#totalPages").text(totalPages);
			},
			"error": function() {
				toastr.error("系统异常");
			}
		});
	};

	Role.prototype.setPageIndexes = function() {
		var $currentPage = Number($roleTab.find("#currentPage").text());
		var $totalPages = Number($roleTab.find("#totalPages").text());
		var indexs = $(".page-operation a").slice(1, -1);
		var start = -1;
		var end = -1;

		if ($currentPage <= 2) {
			start = 0;
			end = 4;
		} else {
			start = $currentPage - 3;
			end = $currentPage + 1;
		}

		for (var i = start; i <= end; i++) {
			var one = $(indexs[i - start]);
			one.text(i + 1);
			one.data("page", i + 1);
			one.removeClass("page-active");
			one.removeClass("page-disable");
			one.unbind("click");
			if (i + 1 <= $totalPages) {
				one.click(function(event) {
					$roleTab.find("#currentPage").text($(this).data("page"));
					role.searchRole({
						"page": $(this).data("page"),
					});
					role.setPageIndexes();
					event.stopPropagation();
				});
			} else {
				one.addClass("page-disable");
			}
			if (i + 1 == $currentPage) {
				one.addClass("page-active");
			}
		}
	}

	Role.prototype.changeRoleStatus = function(role_id, bool) {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/role/changeRoleStatus",
			"async": "false",
			"data": {
				"role_id": role_id,
				"bool": bool,
			},
			"success": function(json) {
				if (json.result) {
					toastr.info("改变角色状态成功！");
				} else {
					toastr.info("改变角色状态失败！请刷新重试！");
				}
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	};

	Role.prototype.fillMenuPermissions = function() {
		// 填充权限复选框
		$.ajax({
			"type": "post",
			"url": "/stuenroll/role/searchAllPermission",
			"async": "true",
			"data": null,
			"success": function(json) {
				var temp = "";
				for (var i = 0; i < json.result.length; i++) {
					var one = json.result[i];
					if (i % 7 == 0 && i != 0) {
						temp += "<br />";
					}
					temp += "<label>"
					temp += "<input type='checkbox' name='permission' id='" + one.permission_id + "' value='' />";
					temp += one.permission_name;
					temp += "</label>";
				}
				$addRoleMenu.find(".checkbox-wrapper").html(temp);
				$modifyRoleMenu.find(".checkbox-wrapper").html(temp);
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	};

	Role.prototype.refreshMenu = function($menu) {
		$menu.find("input[name='name']").val("");
		$menu.find("input[type='checkbox']").each(function(i, x) {
			x.checked = false;
		});
	};

	Role.prototype.showPermissionsBelongToRole = function() {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/role/searchRolePermissionsByRoleId",
			"async": true,
			"data": {
				"role_id": global_selectedRoleId,
			},
			"success": function(json) {
				var data = json.result;
				$modifyRoleMenu.find(".checkbox-wrapper input[type='checkbox']").each(function(i, x) {
					var current_permission_str = $(x).attr("id");
					if (data.indexOf(current_permission_str) != -1) {
						x.checked = true;
					}
				});
				global_selectedRolePermissions = data;
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	}

	Role.prototype.addRole = function(p_username, p_permission_str) {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/role/addRoleAndSetPermissions",
			"async": "false",
			"data": {
				"role_name": p_username,
				"permissions": p_permission_str,
			},
			"success": function(json) {
				if (json.result) {
					toastr.info("新建角色成功");
				} else {
					toastr.warning("新建角色失败!");
				}
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	};

	Role.prototype.changeRoleByRoleId = function(p_role_id, p_role_name, p_add_permissions, p_del_permissions) {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/role/changeRoleByRoleId",
			"async": true,
			"traditional": true,
			"data": {
				"role_id": p_role_id,
				"role_name": p_role_name,
				"add_permissions": p_add_permissions,
				"del_permissions": p_del_permissions,
			},
			"success": function(json) {
				if (json.result) {
					toastr.info("修改角色成功");
				} else {
					toastr.warning("修改角色失败，请重试！");
				}
			},
			"error": function() {
				toastr.error("系统异常");
			},
		});
	}

	Role.prototype.deleteRoleByRoleId = function(p_role_id_list) {
		$.ajax({
			"type": "post",
			"url": "/stuenroll/role/deleteRoleByRoleIds",
			"async": true,
			"data": {
				"role_id": p_role_id_list.join("A"),
			},
			"success": function(json) {
				if (json.result) {
					toastr.info("删除角色成功");
				} else {
					toastr.error("不能删除有关联用户的角色！");
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
		if (key == "Role") {
			return new Role();
		} else if (key == "Tab") {
			return new Tab();
		} else {
			return null;
		}
	}

	// ----------------------------------------工厂方法结束---------------------------------------------------

	// 初始化对象
	var role = factory("Role");
	var tab = factory("Tab");
	var $roleTab = $(".tab-container .tab-content[data-index='角色列表']");
	var $addRoleMenu = $("#addRoleMenu");
	var $modifyRoleMenu = $("#modifyRoleMenu");
	var global_selectedRoleId = "";
	var global_selectedRoleName = "";
	var global_selectedRolePermissions = [];

	// 显示html表格内容, 前台调试时请注释
	role.searchRoleCount();
	role.searchRole();
	role.fillMenuPermissions();

	// 绑定切换选项卡，切换tab-content的事件
	$(".tab-list .tab-item").click(function() {
		var temp = $(this).data("index");
		tab.showTab(temp);
		// 切换选项卡，重新查询数据
		if (temp == "角色列表") {
			$roleTab.find("#currentPage").text(1);
			role.searchRoleCount();
			role.searchRole();
		}
	});

	// ---------------------------------------- “角色列表“ 绑定 start ---------------------------------------------------

	// --------------------- 1) “角色列表“ 一般绑定  start-------------------

	// 绑定 “角色列表” 面板上一页的事件
	$roleTab.find("*[name='prevBtn']").click(function() {
		var temp = $(this).parents(".page-list").find("#currentPage");
		var currentPage = temp.text();
		currentPage = new Number(currentPage);
		// 请求Ajax并更新数据
		if (currentPage > 1) {
			role.searchRole({
				"page": currentPage - 1
			});
			temp.text(currentPage - 1);
		} else {
			toastr.info("当前为第一页");
		}

	});

	// 绑定 “角色列表” 面板下一页的事件
	$roleTab.find("*[name='nextBtn']").click(function() {
		var temp = $(this).parents(".page-list").find("#currentPage");
		var currentPage = temp.text();
		currentPage = new Number(currentPage);
		var totalPages = $(this).parents(".page-list").find("#totalPages").text();
		totalPages = new Number(totalPages);
		// 请求Ajax并更新数据
		if (currentPage < totalPages) {
			role.searchRole({
				"page": currentPage + 1
			});
			temp.text(currentPage + 1);
		} else {
			toastr.info("当前为最后一页");
		}
	});

	// 绑定 “角色列表”： 点击菜单以外的地方消失所有菜单
	$(document).click(function() {
		var $obj = $(".menu");
		$obj.fadeOut();
		$obj.parent().removeClass("operation-active");
		DropDown.closeDropDown();
	});
	$(".menu").click(function(event) {
		event.stopPropagation();
	});

	// --------------------- 1) “角色列表“ 一般绑定  end-------------------

	// --------------------- 2) “角色列表“ 添加按钮绑定  start-------------------

	// 绑定 “角色列表” 面板添加按钮 点击显示菜单
	$roleTab.find("*[name='addRole']").click(function(event) {
		// 清空菜单
		role.refreshMenu($addRoleMenu);

		var $obj = $roleTab.find(".menu");
		$(".menu").fadeOut();
		$obj.parent().siblings().removeClass("operation-active");
		$addRoleMenu.addClass("operation-active");
		$addRoleMenu.fadeIn();
		event.stopPropagation();
	});
	
	// 绑定 “角色列表” 面板添加菜单 输入框检查值
	$addRoleMenu.find("input[name='name']").keyup(function() {
		if (this.checkValidity()) {
			$(this).removeClass("input-error");
		} else {
			$(this).addClass("input-error");
		}
	});

	// 绑定 “角色列表” 面板添加菜单 保存按钮
	$addRoleMenu.find(".menu-btn[value='保存']").click(function(event) {
		// 检查角色名格式
		if (!$addRoleMenu.find("input[name='name']")[0].checkValidity()) {
			toastr.warning("角色名称格式不正确，请重新检查！");
			return;
		}
		// 弹出确认对话框
		if (confirm("确定新建角色？") == false) {
			return;
		}
		// 取得新建角色名
		var username = $addRoleMenu.find("input[name='name']").val();
		// 取得新建权限
		var temp_permission_list = [];
		$addRoleMenu.find("input[type='checkbox']").each(function(i, x) {
			if (x.checked) {
				temp_permission_list.push($(x).attr("id"));
			}
		});
		// 如果角色没有选取任何权限，是不能新建角色的
		if (temp_permission_list.length == 0) {
			toastr.warning("请选择此角色所需权限，不能新建没有任何权限的角色！");
			return;
		}

		// ajax请求
		role.addRole(username, temp_permission_list.join("A"));

		// 消失面板
		var $obj = $(".menu");
		$obj.fadeOut();
		$obj.parent().removeClass("operation-active");
		DropDown.closeDropDown();

		setTimeout(function() {
			role.searchRoleCount();
			role.searchRole();
		}, 4000);

		event.stopPropagation();
	});

	// 绑定 “角色列表” 面板添加菜单 清空按钮
	$addRoleMenu.find(".menu-btn[value='清空']").click(function(event) {
		role.refreshMenu($addRoleMenu);
		event.stopPropagation();
	});

	// --------------------- 2) “角色列表“ 添加按钮绑定  end -------------------

	// --------------------- 3) “角色列表“ 修改按钮绑定  start -------------------

	// 绑定 “角色列表” 面板修改按钮 点击显示菜单
	$roleTab.find("*[name='modifyRole']").click(function(event) {

		// 必须选中一条记录才能显示修改菜单
		var temp_select_count = 0;
		$(".data-table input").each(function(i, x) {
			if (x.checked) {
				global_selectedRoleId = $(x).attr("value");
				global_selectedRoleName = $(x).parents("tr").find("td:eq(2)").text();
				temp_select_count += 1;
			}
		});
		if (temp_select_count != 1) {
			toastr.warning("必须选定一个角色进行修改！");
			return;
		}

		// 清空菜单
		role.refreshMenu($modifyRoleMenu);

		// 填充角色名称
		$modifyRoleMenu.find("input[name='name']").val(global_selectedRoleName);

		// 显示已有权限
		role.showPermissionsBelongToRole();

		var $obj = $roleTab.find(".menu");
		$(".menu").fadeOut();
		$obj.parent().siblings().removeClass("operation-active");
		$modifyRoleMenu.addClass("operation-active");
		$modifyRoleMenu.fadeIn();
		event.stopPropagation();
	});
	
	// 绑定 “角色列表” 面板添加菜单 输入框检查值
	$modifyRoleMenu.find("input[name='name']").keyup(function() {
		if (this.checkValidity()) {
			$(this).removeClass("input-error");
		} else {
			$(this).addClass("input-error");
		}
	});

	// 绑定 “角色列表” 面板修改菜单 保存按钮
	$modifyRoleMenu.find(".menu-btn[value='保存']").click(function(event) {
		// 取得修改后的角色名
		var current_username = $modifyRoleMenu.find("input[name='name']").val();
		// 检查角色名称格式
		if (!$modifyRoleMenu.find("input[name='name']")[0].checkValidity()) {
			toastr.warning("角色名称格式不正确，请重新检查！");
			return;
		}
		// 如果角色名没有被修改，那么就置为空，与后台对接
		if (current_username == global_selectedRoleName) {
			current_username = "";
		}

		// 取得修改后的权限
		current_permission_list = [];
		$modifyRoleMenu.find("input[type='checkbox']").each(function(i, x) {
			if (x.checked) {
				current_permission_list.push($(x).attr("id"));
			}
		});
		// 如果角色没有选取任何权限，是不能修改角色的
		if (current_permission_list.length == 0) {
			toastr.warning("不能取消角色所有权限！");
			return;
		}
		// 生成应该添加的权限和应该删除的权限
		permissions_should_add = [];
		permissions_should_del = [];
		for (var i = 0; i < current_permission_list.length; i++) {
			var one = current_permission_list[i]
			if (global_selectedRolePermissions.indexOf(one) == -1) {
				permissions_should_add.push(one);
			}
		}
		for (var i = 0; i < global_selectedRolePermissions.length; i++) {
			var one = global_selectedRolePermissions[i];
			if (current_permission_list.indexOf(one) == -1) {
				permissions_should_del.push(one);
			}
		}

		add_permissions = permissions_should_add;
		del_permissions = permissions_should_del;

		// 弹出确认对话框
		if (confirm("确定修改角色？") == false) {
			return;
		}

		// ajax请求
		role.changeRoleByRoleId(global_selectedRoleId, current_username, add_permissions, del_permissions);

		// 消失面板
		var $obj = $(".menu");
		$obj.fadeOut();
		$obj.parent().removeClass("operation-active");
		DropDown.closeDropDown();

		setTimeout(function() {
			role.searchRoleCount();
			role.searchRole();
		}, 4000);
		
		event.stopPropagation();
	});

	// 绑定 “角色列表” 面板修改菜单 清空按钮
	$modifyRoleMenu.find(".menu-btn[value='清空']").click(function(event) {
		role.refreshMenu($modifyRoleMenu);
		event.stopPropagation();
	});

	// --------------------- 3) “角色列表“ 修改按钮绑定  end -------------------

	// --------------------- 4) “角色列表“ 删除按钮绑定  start -------------------

	$roleTab.find("*[name='deleteRole']").click(function(event) {
		// 检查选中了几条记录，用以确认
		var temp_selected_role_ids = [];
		var temp_selected_role_names = [];
		$(".data-table input").each(function(i, x) {
			if (x.checked) {
				temp_selected_role_ids.push($(x).attr("value"));
				temp_selected_role_names.push($(x).parents("tr").find("td:eq(2)").text());
			}
		});

		// 提示用户确认删除
		var confirm_str = "";
		confirm_str += "确定删除\n";
		temp_selected_role_names.forEach(function(x) {
			confirm_str += "\t" + x + ",\n";
		});
		confirm_str += "\t" + temp_selected_role_ids.length + "个角色？";
		if (!confirm(confirm_str)) {
			return;
		}

		// ajax请求删除
		role.deleteRoleByRoleId(temp_selected_role_ids);

		// 消失面板
		var $obj = $(".menu");
		$obj.fadeOut();
		$obj.parent().removeClass("operation-active");
		DropDown.closeDropDown();

		setTimeout(function() {
			role.searchRoleCount();
			// 如果删除的是最后一页并且已经删除空，那么转到前一页
			setTimeout(function() {
				var $temp_currentPage = $roleTab.find("#currentPage");
				var $temp_totalPages = $roleTab.find("#totalPages");
				if (Number($temp_currentPage.text()) > Number($temp_totalPages.text())) {
					if ($temp_currentPage.text() == 1) {
						console.log("[DEBUG] 1 ye")
						; // donothing 如果当前页数是第一页
					} else {
						console.log("[DEBUG] qian yiye")
						// 如果当前页数不是第一页，再转到前一页
						$temp_currentPage.text(Number($temp_totalPages.text()));
					}
				}
				setTimeout(function() {
					role.searchRole();
				}, 300);
			}, 300);
		}, 3400);

		event.stopPropagation();
	});

	// --------------------- 4) “角色列表“ 删除按钮绑定  end  -------------------

	// ---------------------------------------- “角色列表“ 绑定 end ---------------------------------------------------

	// 下拉菜单初始化静态方法
	DropDown.initAll();
});