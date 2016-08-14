package cn.gov.hrss.ln.stuenroll.classmanagement;

/**
 * 回访记录网络类接口
 * @author LiYadong
 * @version 1.0
 */

public interface I_ReviewController {

	/**
	 * 根据条件搜索回访记录详细信息
	 */
	public void searchReviewInfo();
	
	/**
	 * 搜索符合条件的记录数
	 */
	public void searchReviewCounts();
	
	/**
	 * 添加回访信息
	 */
	public void addReviewInfo();
	
	/**
	 * 更新回访信息 
	 */
	public void updateReviewInfo();
	
	/**
	 * 删除回访记录
	 */
	public void deleteReviewInfo();
	
	/**
	 * 查询回访人数
	 */
	public void searchReviewPeopleAmountInClass();
	
	/**
	 * 导入回访记录
	 */
	public void importReviewInfo();
	
	/**
	 * 导出回访记录
	 */
	public void exportReviewInfo();
	
}
