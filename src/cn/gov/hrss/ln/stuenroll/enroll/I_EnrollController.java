package cn.gov.hrss.ln.stuenroll.enroll;

/**
 * 报名管理网络类
 * @author York Chu
 *
 */
/**
 * @author Administrator
 *
 */
public interface I_EnrollController {
	/**
	 * 查询报名记录
	 */
	public void searchEnroll();

	/**
	 * 查询记录总数
	 */
	public void searchEnrollCount();
	
	/**
	 * 添加记录
	 */
	public void addEnroll();

	/**
	 * 根据ID删除记录
	 */
	public void deleteById();
	
	/**
	 * 缓存报名记录
	 */
	public void register();
	
	/**
	 * 验证数据库与Redis中的身份证号码是否重复
	 */
	public void isEnrollEligible();
	/**
	 * 修改报名记录	 
	 *  
	 * @param obj
	 * @param id
	 * @return
	 */
	public void modifyEnroll();
	/**
	 * 根据ID查询报名记录
	 * @param id
	 * @return
	 */
	public void searchEnrollById();
	
	/**
	 * 学员分班
	 * @return
	 */
	public void allot();
	
	/**
	 * 学员分班取消
	 * @return
	 */
	public void cancelAllot();
	
	/**
	 * 学员中退
	 * @param id
	 * @param quitDate
	 * @param quitReason
	 * @return
	 */
	public void quit();
	
	/**
	 * 学员中退取消
	 * @param id
	 * @return
	 */
	public void cancelQuit();
	
}
