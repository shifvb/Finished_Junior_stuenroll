package cn.gov.hrss.ln.stuenroll.classmanagement;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

/**
 * 回访记录业务模块接口
 * @author LiYadong
 * @version 1.0
 */

public interface I_ReviewService {

	/**
	 * 根据条件搜索回访记录详细信息
	 * @param map 条件
	 * 
	 * @param start 起始位置
	 * @param length 偏移量
	 * @return 记录
	 */
	public List<Record> searchReviewInfo(Map<String, Object> map, Long start, Long length);
	
	/**
	 * 搜索所有的回访记录
	 * @return
	 */
	public List<Record> searchAllReview();
	
	/**
	 * 搜索符合条件的记录数
	 * @param map 条件
	 * @return 返回值
	 */
	public long searchReviewCounts(Map<String, Object> map);
	
	/**
	 * 添加回访信息
	 * @param map 回访信息
	 * @param username 操作用户名
	 * @return
	 */
	public long addReviewInfo(Map<String, Object> map, String username);
	
	/**
	 * 更新回访信息 
	 * @param map 回访信息
	 * @param reviewId 回访记录ID
	 * @param username 操作用户名
	 * @return 
	 */
	public int updateReviewInfo(Map<String, Object> map, Long reviewId, String username);
	
	/**
	 * 删除回访记录
	 * @param id 
	 * @param username 操作用户名
	 * @return 删除的记录数
	 */
	public long deleteReviewInfo(Long[] id, String username);
	
	/**
	 * 查询回访人数
	 * @param map 条件
	 * @return 记录
	 */
	public List<Record> searchReviewPeopleAmountInClass(Map<String, Object> map, Long start, Long length);
	
}
