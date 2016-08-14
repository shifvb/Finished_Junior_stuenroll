package cn.gov.hrss.ln.stuenroll.role;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 权限管理模块网络类
 * 
 * @author shifvb
 *
 */
public interface I_RoleController {
	
	// 基本上只能用于权限管理模块的 查询角色 start

	/**
	 * 权限管理模块 得到role的列表
	 * 
	 * 接口：
	 * 	/stuenroll/role/searchRole?page=1
	 */
	public void searchRole();

	/**
	 * 权限管理模块 得到role的总行数(不包括管理员)
	 * 
	 * 接口：
	 * 	/stuenroll/role/searchRoleCount
	 * 
	 */
	public void searchRoleCount();
	
	
	/**
	 * 改变角色状态
	 * 
	 * 接口：
	 *  /stuenroll/role/changeRoleStatus?role_id=11111111&bool=true
	 */
	public void changeRoleStatus();
	
	// 基本上只能用于权限管理模块的 查询角色 end
	
	// 基本上只能用于权限管理模块的 添加角色面板 start
	
	/**
	 * 查询所有permission的ID和name(不包括超级权限)
	 * 
	 * 接口：
	 * 	/stuenroll/role/searchAllPermission
	 */
	public void searchAllPermission();
	
	/**
	 * 增加一个角色并赋予权限(role)
	 * 
	 * 接口:
	 * 	/stuenroll/role/addRoleAndSetPermissions?role_name=机构普通用户-15&permissions=3_4A2_3A2_2A2_1A2_4
	 */
	public void addRoleAndSetPermissions();
	
	// 基本上只能用于权限管理模块的 添加角色面板 end
	
	// 基本上只能用于权限管理模块的 修改角色面板 start

	/**
	 * 根据role_id, 查询角色的所有permission
	 * 
	 * 接口：
	 * 	/stuenroll/role/searchRolePermissionsByRoleId?role_id=234135151515
	 */
	public void searchRolePermissionsByRoleId();
	
	/**
	 * 根据role_id, 添加和删除角色的权限
	 * 
	 * 接口：
	 * 	/stuenroll/role/changeRoleByRoleId?role_id=1231231353&add_permissions=3_4A2_3&del_permissions=1_3&role_name=
	 */
	public void changeRoleByRoleId();
	
	// 基本上只能用于权限管理模块的 修改角色面板 end
	
	// 基本上只能用于权限管理模块的 删除角色start

	/**
	 * 根据role_id， 删除角色（关联用户的角色不能删除）
	 * 
	 * 接口：
	 * 	/stuenroll/role/deleteRoleByRoleIds?role_id=1231231353A44
	 */
	public void deleteRoleByRoleIds();

	// 基本上只能用于权限管理模块的 删除角色end
	
	

}
