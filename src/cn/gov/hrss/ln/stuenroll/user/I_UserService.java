package cn.gov.hrss.ln.stuenroll.user;

import com.jfinal.plugin.activerecord.Record;

/**
 * 用户模块业务接口
 * 
 * @author YangDi
 *
 */
public interface I_UserService {
	/**
	 * 用户登录抽象方法
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean login(String username, String password);

	/**
	 * 找回密码的时候核实用户身份信息
	 * 
	 * @param name
	 * @param pid
	 * @param organization
	 * @return
	 */
	public boolean checkRecoverPersonalInfo(String name, String pid, String organization);

	/**
	 * 找回密码的时候核实账号信息
	 * 
	 * @param username
	 * @param email
	 * @param tel
	 * @return
	 */
	public boolean checkAccountInfo(String username, String email, String tel);

	/**
	 * 找回密码的时候核实问题信息
	 * 
	 * @param username
	 * @param question
	 * @param answer
	 * @return
	 */
	public boolean checkQuestionInfo(String username, String question, String answer);

	
	/**
	 * 设置新密码
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public int recoverPassword(String username, String password);

	/**
	 * 用户登录后查询的基本信息
	 * 
	 * @param username
	 * @return
	 */
	public Record searchUserInfoAtLogin(String username);

	/**
	 * 退出系统
	 * 
	 * @param username
	 */
	public void logout(String username);

	/**
	 * 用户登录前,判断用户是否block,以及得到role_id
	 * 
	 * @param username
	 * @return
	 */
	public Record searchIfUserHasBeenBlockedAndRoleId(String username);

	
	/**
	 * 用户登录前,判断给定角色是否block
	 * 
	 * @param role_id
	 * @return
	 */
	public Record searchIfRoleHasBeenBlocked(String role_id);
}
