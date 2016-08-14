package cn.gov.hrss.ln.stuenroll.role;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 角色管理模块业务接口
 * 
 * @author shifvb
 *
 */
public interface I_RoleSevice {
	// 基本上只能用于权限管理 显示表格 start
	/**
	 * 查询所有角色的id和name和block（不包括管理员）
	 * 
	 * @param start
	 * @param length
	 * @return
	 */
	public List<Record> searchAllFromRole(long start, long length);

	/**
	 * 根据角色ID查询机构用户数量
	 * 
	 * @param role_id
	 * @return
	 */
	public Long searchOrganizationUserCountByRoleId(Long role_id);

	/**
	 * 根据角色ID查询就业网用户数量
	 * 
	 * @param role_id
	 * @return
	 */
	public Long searchEmploymentUserCountByRoleId(Long role_id);

	/**
	 * 根据角色ID查询权限数量
	 * 
	 * @param role_id
	 * @return
	 */
	public Long searchPermissionCountByRoleId(Long role_id);

	/**
	 * 根据角色ID查询无效用户数量
	 * 
	 * @param role_id
	 * @return
	 */
	public Long searchInvalidUserCountByRoleId(Long role_id);

	/**
	 * 根据角色ID查询有效用户数量
	 * 
	 * @param role_id
	 * @return
	 */
	public Long searchValidUserCountByRoleId(Long role_id);

	/**
	 * 根据角色ID查询用户总数量
	 * 
	 * @param role_id
	 * @return
	 */
	public Long searchUserCountByRoleId(Long role_id);

	/**
	 * 查询共有多少个角色(不包括管理员)
	 * 
	 * @return
	 */
	public Long searchRoleCount();

	/**
	 * 改变角色状态
	 * 
	 * @param role_id
	 * @param bool
	 * @return
	 */
	public int changeRoleStatus(Long role_id, boolean bool);

	// 基本上只能用于权限管理 显示表格 end

	// 基本上只能用于权限管理模块的 添加角色面板 start
	/**
	 * 
	 * 查询所有permission的ID和name(不包括超级权限)
	 * 
	 * @return
	 */
	public List<Record> searchAllPermissions();

	/**
	 * 添加一个新的角色
	 * 
	 * @param roleName
	 * @return
	 */
	public int addRole(String roleName);

	/**
	 * 根据角色的名字查询角色的ID
	 * 
	 * @param role_name
	 * @return
	 */
	public Long searchRoleIdByRoleName(String role_name);

	/**
	 * 根据role_id，设置角色的permission
	 * 
	 * @param role_id
	 * @param permissions
	 * @return
	 */
	public int setRolePermissionByRoleId(Long role_id, String permission);

	// 基本上只能用于权限管理模块的 添加角色面板 end

	// 基本上只能用于权限管理模块的 修改角色面板 start

	/**
	 * 根据role_id, 查询角色的所有permission
	 * 
	 * @param role_id
	 * @return
	 */
	public List<Record> searchRolePermissionByRoleId(Long role_id);

	/**
	 * 根据role_id, 删除角色一条权限
	 * 
	 * @param role_id
	 * @param permission
	 * @return
	 */
	public int deleteRolePermissionByRoleId(Long role_id, String permission);

	/**
	 * 根据role_id, 修改角色名
	 * 
	 * @param role_id
	 * @param role_name
	 * @return
	 */
	public int updateRoleNameByRoleId(Long role_id, String role_name);

	// 基本上只能用于权限管理模块的 修改角色面板 end

	// 基本上只能用于权限管理模块的 删除角色start

	/**
	 * 根据角色的roleID删除所有权限
	 * 
	 * @param role_id
	 * @return
	 */
	public int deletePermissionsByRoleId(Long role_id);

	/**
	 * 根据角色的roleID删除角色
	 * 
	 * @param role_id
	 * @return
	 */
	public int deleteRoleByRoleId(Long role_id);

	// 基本上只能用于权限管理模块的 删除角色end

}
