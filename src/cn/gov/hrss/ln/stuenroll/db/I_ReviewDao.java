package cn.gov.hrss.ln.stuenroll.db;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

/**
 * 电话回访数据访问接口
 * @author LiYadong
 * @version 1.0
 */
public interface I_ReviewDao {
	
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
	 * 搜索每个班级回访记录数
	 * @param map
	 * @return
	 */
	public List<Record> searchReviewCountsByClass(Map<String, Object> map, Long start, Long length);
	
	/**
	 * 添加回访信息
	 * @param map 回访信息
	 * @return
	 */
	public long addReviewInfo(Map<String, Object> map);
	
	/**
	 * 更新回访信息 
	 * @param map 回访信息
	 * @param reviewId 回访记录ID
	 * @return 
	 */
	public int updateReviewInfo(Map<String, Object> map, Long reviewId);
	
	/**
	 * 删除回访记录
	 * @param id 
	 * @return 删除的记录数
	 */
	public long deleteReviewInfo(Long[] id);
	
	/**
	 * 查询回访人数
	 * @param map 条件
	 * @return 记录
	 */
	public List<Record> searchReviewPeopleAmountInClass(Map<String, Object> map, Long start, Long length);

}
