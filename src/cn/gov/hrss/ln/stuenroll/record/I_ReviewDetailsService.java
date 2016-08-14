package cn.gov.hrss.ln.stuenroll.record;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

/**
 * 回访记录详细信息业务模块接口
 * @author LYD
 * @version 2.0
 */

public interface I_ReviewDetailsService {
	
	/**
	 * 根据回访记录ID查询回访学生详细信息
	 * @param reviewId
	 * @return
	 */
	public Record searchArchiveReviewStudent(Long reviewId);
	
	/**
	 * 根据回访记录ID查询回访学生详细信息
	 * @param reviewId
	 * @return
	 */
	public Record searchUnarchiveReviewStudent(Long reviewId);

	/**
	 * 查询某个回访记录的详细信息
	 * @param reviewId 回访记录ID
	 * @return 记录
	 */
	public Record searchReviewDetails(Long reviewId);
	
	/**
	 * 查询学生出勤情况
	 * @param studentId 学生ID
	 * @return 
	 */
	public List<Record> searchStudentAttendence(Long studentId);
	
	/**
	 * 查询学生成绩
	 * @param studentId 学生ID
	 * @return 
	 */
	public List<Record> searchStudentScore(Long studentId);
	
	/**
	 * 查找学生头像
	 * @param studentId
	 * @return
	 */
	public String searchStudentImage(Long studentId);
	
	/**
	 * 添加回访记录
	 * @param map 回访具体记录
	 * @param username 用户名
	 * @return
	 */
	public int addReviewDetails(Map<String, Object> map, String username);
	
	/**
	 * 添加review表记录和review_details表记录
	 * @param review review表信息
	 * @param reviewDetails review_details表信息
	 * @param username 用户名
	 * @return
	 */
	public boolean addReviewRecord(Map<String, Object> review, Map<String, Object> reviewDetails, String username);
	
}
