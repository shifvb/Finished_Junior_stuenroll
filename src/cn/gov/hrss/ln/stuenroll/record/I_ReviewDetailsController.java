package cn.gov.hrss.ln.stuenroll.record;

/**
 * 回访记录详细信息网络类接口
 * @author LYD
 * @version 2.0
 */

public interface I_ReviewDetailsController {
	
	/**
	 * 根据回访记录ID查询回访学生详细信息
	 */
	public void searchReviewStudent();

	/**
	 * 查询某个回访记录的详细信息
	 */
	public void searchReviewDetails();
	
	/**
	 * 查询学生出勤情况
	 */
	public void searchStudentAttendence();
	
	/**
	 * 查询学生成绩
	 */
	public void searchStudentScore();
	
	/**
	 * 查询学生头像
	 */
	public void searchStudentImage();
	
	/**
	 * 添加回访记录
	 */
	public void addReviewDetails();
	
	/**
	 * 添加review表记录和review_details表记录
	 */
	public void addReviewRecord();
	
}
