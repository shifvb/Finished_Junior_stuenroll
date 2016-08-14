package cn.gov.hrss.ln.stuenroll.systemUser;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

public interface I_SystemUserService {

	// 基本上只能用于用户管理 显示表格 start

	/**
	 * 显示所有user的数据（不包括管理员）
	 * 
	 * @param start
	 * @param length
	 * @return
	 */
	public List<Record> searchUser(Long start, Long length);

	/**
	 * 查询共有多少个用户(不包括管理员)
	 * 
	 * @return
	 */
	public Long searchUserCount();

	/**
	 * 改变用户状态
	 * 
	 * @param user_id
	 * @param block
	 * @return
	 */
	public int changeUserStatus(Long user_id, boolean block);

	// 基本上只能用于用户管理 显示表格 end

	// 基本上只能用于用户管理 添加菜单 start

	/**
	 * 添加用户
	 * 
	 * @param params
	 * @return
	 */
	public int addUser(List params);

	/**
	 * 查找全部机构的id，name
	 * 
	 * @return
	 */
	public List<Record> searchAllOrganizationIdAndNames();

	/**
	 * 查找全部角色的id，name
	 * 
	 * @return
	 */
	public List<Record> searchAllRoleIdAndNames();

	// 基本上只能用于用户管理 添加菜单 end

	// 基本上只能用于用户管理 修改菜单 start

	/**
	 * 根据user的ID，为未来的修改查找所需要的信息
	 * 
	 * @param user_id
	 * @return
	 */
	public Record searchUserById(Long user_id);

	/**
	 * 根据user的ID,修改user
	 * 
	 * @param user_id
	 * @param params
	 * @return
	 */
	public int modifyUserById(Long user_id, Map params);

	// 基本上只能用于用户管理 修改菜单 end

	// 基本上只能用于用户管理 删除用户 start

	/**
	 * 根据userID删除用户
	 * 
	 * @param user_id
	 * @return
	 */
	public int deleteUserById(Long user_id);

	// 基本上只能用于用户管理 删除用户 end

	// 基本上只能用于用户管理 导入用户 start

	/**
	 * 删除所有user（不包括管理员）
	 * 
	 * @return
	 */
	public int deleteAllUser();

	/**
	 * 全舰装填user
	 * 
	 * @param params
	 * @return
	 */
	public int importAllUser(List params);

	// 基本上只能用于用户管理 导入用户 end

	// 基本上只能用于用户管理 导出用户 end

	/**
	 * 获得全部user（不包括管理员）
	 * 
	 * @return
	 */
	public List<Record> searchAllUser();

	// 基本上只能用于用户管理 导出用户 end
}
