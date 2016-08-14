package cn.gov.hrss.ln.stuenroll.user;

/**
 * 用户模块网络接口
 * 
 * @author YangDi
 *
 */
public interface I_UserController {
	/**
	 * 用户登录抽象方法
	 */
	public void login();

	/**
	 * 找回密码的时候核实用户身份信息
	 */
	public void checkRecoverPersonalInfo();

	/**
	 * 找回密码的时候核实账号信息
	 */
	public void checkAccountInfo();

	/**
	 * 找回密码的时候核实问题信息
	 */
	public void checkQuestionInfo();

	/**
	 * 重新设置密码
	 */
	public void recoverPassword();

	/**
	 * 退出登录
	 */
	public void logout();
}
