package cn.gov.hrss.ln.stuenroll.db;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

/**
 * review_details表数据访问接口
 * @author LYD
 * @version 1.0
 */

public interface I_ReviewDetailsDao {
	
	/**
	 * 根据回访记录ID查询回访归档学生详细信息
	 * @param reviewId
	 * @return
	 */
	public Record searchArchiveReviewStudent(Long reviewId);
	
	/**
	 * 根据回访记录ID查询回访未归档学生详细信息
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
	 * 查询归档学生的头像
	 * @param studentId
	 * @return
	 */
	public String searchArchiveStudentImage(Long studentId);
	
	/**
	 * 查询未归档学生头像
	 * @param studentId
	 * @return
	 */
	public String searchUnarchiveStudentImage(Long studentId);
	
	/**
	 * 添加回访记录
	 * @param map 回访具体记录
	 * @return
	 */
	public int addReviewDetails(Map<String, Object> map);

}
