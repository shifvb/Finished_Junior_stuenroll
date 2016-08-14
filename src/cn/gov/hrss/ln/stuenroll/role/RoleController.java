package cn.gov.hrss.ln.stuenroll.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.spring.Spring;

@Spring("roleController")
public class RoleController extends Controller implements I_RoleController {
	private I_RoleSevice i_RoleSevice;
	private int rowsInPage;

	@RequiresPermissions("2_4")
	@Override
	public void searchRole() {
		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		long start = (page - 1) * rowsInPage;
		long length = rowsInPage;

		List result = new ArrayList<Map>();
		List<Record> roles = i_RoleSevice.searchAllFromRole(start, length);
		for (Record record : roles) {
			Long role_id = record.getLong("id");
			HashMap role_row = new HashMap();
			role_row.put("id", String.valueOf(role_id));
			role_row.put("角色名称", record.getStr("name"));
			role_row.put("权限数量", i_RoleSevice.searchPermissionCountByRoleId(role_id));
			role_row.put("用户数量", i_RoleSevice.searchUserCountByRoleId(role_id));
			role_row.put("有效用户", i_RoleSevice.searchValidUserCountByRoleId(role_id));
			role_row.put("无效用户", i_RoleSevice.searchInvalidUserCountByRoleId(role_id));
			role_row.put("就业网用户", i_RoleSevice.searchEmploymentUserCountByRoleId(role_id));
			role_row.put("机构用户", i_RoleSevice.searchOrganizationUserCountByRoleId(role_id));
			role_row.put("角色状态", !record.getBoolean("block"));
			result.add(role_row);
		}
		renderJson("result", result);
	}

	@RequiresPermissions("2_4")
	@Override
	public void searchRoleCount() {
		Long count = i_RoleSevice.searchRoleCount();
		renderJson("result", count);
	}

	@Before(Tx.class)
	@RequiresPermissions({ "2_1", "2_3", "2_4" })
	@Override
	public void addRoleAndSetPermissions() {
		String role_name = getPara("role_name");
		String permissionStr = getPara("permissions");
		// 判断必须值，如果前台没有传入就不执行下面的代码
		if (role_name == null || permissionStr == null) {
			renderJson("result", false);
			return;
		}
		int addRoleResult = i_RoleSevice.addRole(role_name);
		Long role_id = i_RoleSevice.searchRoleIdByRoleName(role_name);
		// A 字符是用来分隔permission的，因为传入的是permission=3_4A3_3A2_3 之类的
		String[] permissions = permissionStr.split("A");
		for (String permission : permissions) {
			i_RoleSevice.setRolePermissionByRoleId(role_id, permission);
		}
		renderJson("result", true);
	}

	@RequiresPermissions("2_4")
	@Override
	public void searchAllPermission() {
		List<Record> records = i_RoleSevice.searchAllPermissions();
		renderJson("result", records);
	}

	@RequiresPermissions("2_3")
	@Before(Tx.class)
	@Override
	public void changeRoleStatus() {
		// 得到角色id
		String role_id_str = getPara("role_id");
		if (role_id_str == null) {
			return;
		}
		Long role_id = Long.valueOf(role_id_str);
		String bool_str = getPara("bool");
		// 得到要设置的状态
		boolean bool = false;
		if (bool_str.equals("true")) {
			bool = true;
		}
		else if (bool_str.equals("false")) {
			bool = false;
		}
		else {
			return;
		}
		int i = i_RoleSevice.changeRoleStatus(role_id, bool);
		renderJson("result", i == 1);
	}

	@RequiresPermissions("2_4")
	@Override
	public void searchRolePermissionsByRoleId() {
		String role_id_str = getPara("role_id");
		if (role_id_str == null) {
			return;
		}
		Long role_id = Long.valueOf(role_id_str);
		List<Record> records = i_RoleSevice.searchRolePermissionByRoleId(role_id);
		List<String> result = new ArrayList<String>();
		for (Record x : records) {
			result.add(x.getStr("permission_id"));
		}
		renderJson("result", result);
	}

	@RequiresPermissions("2_3")
	@Before(Tx.class)
	@Override
	public void changeRoleByRoleId() {
		// 获取role_id
		String role_id_str = getPara("role_id");
		System.out.println("role_id_str: " + role_id_str);
		if (role_id_str == null) {
			System.out.println("null role_id str!");
			return;
		}
		Long role_id = Long.valueOf(role_id_str);

		// 获取要添加的权限
		String[] add_permissions = getParaValues("add_permissions");
		if (add_permissions != null) {
			for (String x : add_permissions) {
				System.out.println("adding permissions: " + x);
				i_RoleSevice.setRolePermissionByRoleId(role_id, x);
			}
		}

		// 获取要删除的权限
		String[] del_permissions = getParaValues("del_permissions");
		if (del_permissions != null) {
			for (String x : del_permissions) {
				System.out.println("deleting permissions: " + x);
				i_RoleSevice.deleteRolePermissionByRoleId(role_id, x);
			}
		}

		// 获取是否需要更新角色名，如果是，获取新角色名
		String role_name = getPara("role_name");
		System.out.println("role_name: " + role_name);
		System.out.println("role_name equals \"\": " + "".equals(role_name));
		System.out.println("role_name equals null: " + String.valueOf(role_name == null));
		// 如果role_name不是空字符串，说明需要更新角色名
		if (!role_name.equals("")) {
			System.out.println("changing name: " + role_name);
			i_RoleSevice.updateRoleNameByRoleId(role_id, role_name);
		}

		renderJson("result", true);
	}

	@RequiresPermissions("2_2")
	@Before(Tx.class)
	@Override
	public void deleteRoleByRoleIds() {
		// 获取角色ID(s)
		if (getPara("role_id") == null) {
			return;
		}
		List<Long> role_ids = new ArrayList<Long>();
		for (String x : getPara("role_id").split("A")) {
			role_ids.add(Long.valueOf(x));
		}

		// 关联用户的角色不能删除
		for (Long role_id : role_ids) {
			if (i_RoleSevice.searchUserCountByRoleId(role_id) != 0) {
				renderJson("result", false);
				return;
			}
		}
		for (Long role_id : role_ids) {
			// 删除角色的权限
			i_RoleSevice.deletePermissionsByRoleId(role_id);
			// 删除角色
			i_RoleSevice.deleteRoleByRoleId(role_id);
		}
		renderJson("result", true);
	}

	public I_RoleSevice getI_RoleSevice() {
		return i_RoleSevice;
	}

	public void setI_RoleSevice(I_RoleSevice i_RoleSevice) {
		this.i_RoleSevice = i_RoleSevice;
	}

	public int getRowsInPage() {
		return rowsInPage;
	}

	public void setRowsInPage(int rowsInPage) {
		this.rowsInPage = rowsInPage;
	}

}
