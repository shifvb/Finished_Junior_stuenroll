package cn.gov.hrss.ln.stuenroll.classmanagement;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

/**
 * 班级管理业务模块接口
 * @author LiYadong
 * @version 2.0
 */

public interface I_ClassInfoService {
	
	/**
	 * 查询所有班级
	 * @return
	 */
	public List<Record> searchAllClasses(Map<String, Object> map);
	
	/**
	 * 查询所有班级的信息
	 * @param map 查询条件：
	 * {
	 * 		"organizationId": 机构ID,
	 * 		"name": 班级名称,
	 * 		"year": 年届,
	 * 		"organizationName": 机构名称,
	 * 		"professionName": 专业名称,
	 * 		"stuState": 学生状态
	 * } 
	 * @param start 起始位置
	 * @param length 偏移量
	 * @return 记录:
	 * {
	 * 		"id": 班级ID, 
	 * 		"className": 班级名称, 
	 * 		"organizationName": 机构名称，
	 * 		"professionName": 专业名称,
	 * 		"year": 年届,
	 * 		"classStateInfo": 班级状态,
	 * 		"studentsAmount": 学生总数,
	 * 		"statedStudentsAmount": 具有某种状态的学生人数,
	 * 		"rate": 比率
	 * } 
	 */
	public List<Record> searchAllClassInfo(Map<String, Object> map, Long start, Long length);

	/**
	 * 查询已分班班级的信息
	 * @param map 查询条件：
	 * {
	 * 		"organizationId": 机构ID,
	 * 		"name": 班级名称,
	 * 		"year": 年届,
	 * 		"organizationName": 机构名称,
	 * 		"professionName": 专业名称,
	 * 		"classState": 2,
	 * 		"stuState": 学生状态
	 * } 
	 * @param start 起始位置
	 * @param length 偏移量
	 * @return 记录:
	 * {
	 * 		"id": 班级ID, 
	 * 		"className": 班级名称, 
	 * 		"organizationName": 机构名称，
	 * 		"professionName": 专业名称,
	 * 		"year": 年届,
	 * 		"classStateInfo": 班级状态,
	 * 		"studentsAmount": 学生总数,
	 * 		"statedStudentsAmount": 具有某种状态的学生人数,
	 * 		"rate": 比率
	 * } 
	 */
	public List<Record> searchArchiveClassInfo(Map<String, Object> map, Long start, Long length);
	
	/**
	 * 查询未分班班级的信息
	 * @param map 查询条件：
	 * {
	 * 		"organizationId": 机构ID,
	 * 		"name": 班级名称,
	 * 		"year": 年届,
	 * 		"organizationName": 机构名称,
	 * 		"professionName": 专业名称,
	 * 		"classState": 1,
	 * 		"stuState": 学生状态
	 * } 
	 * @param start 起始位置
	 * @param length 偏移量
	 * @return 记录:
	 * {
	 * 		"id": 班级ID, 
	 * 		"className": 班级名称, 
	 * 		"organizationName": 机构名称，
	 * 		"professionName": 专业名称,
	 * 		"year": 年届,
	 * 		"classStateInfo": 班级状态,
	 * 		"studentsAmount": 学生总数,
	 * 		"statedStudentsAmount": 具有某种状态的学生人数,
	 * 		"rate": 比率
	 * } 
	 */
	public List<Record> searchUnarchiveClassInfo(Map<String, Object> map, Long start, Long length);
	
	/**
	 * 根据条件查询所有班级数量
	 * @param map
	 * {
	 * 		"organizationId": 机构ID,
	 * 		"name": 班级名称,
	 * 		"year": 年届,
	 * 		"organizationName": 机构名称,
	 * 		"professionName": 专业名称
	 * }
	 * @return 数量
	 */
	public long searchAllClassAmount(Map<String, Object> map);
	
	/**
	 * 根据条件查询已分班班级数量
	 * @param map
	 * {
	 * 		"organizationId": 机构ID,
	 * 		"name": 班级名称,
	 * 		"year": 年届,
	 * 		"organizationName": 机构名称,
	 * 		"professionName": 专业名称
	 * }
	 * @return 数量
	 */
	public long searchArchiveClassAmount(Map<String, Object> map);
	
	/**
	 * 根据条件查询未分班班级数量
	 * @param map
	 * {
	 * 		"organizationId": 机构ID,
	 * 		"name": 班级名称,
	 * 		"year": 年届,
	 * 		"organizationName": 机构名称,
	 * 		"professionName": 专业名称
	 * }
	 * @return 数量
	 */
	public long searchUnarchiveClassAmount(Map<String, Object> map);
	
	/**
	 * 添加班级
	 * @param map 数据
	 * @return 插入成功的条数
	 */
	public int addClass(Map<String, Object> map);
	
	/**
	 * 更新班级信息
	 * @param map 数据
	 * @return 更新的记录数
	 */
	public int updateClassInfo(Map<String, Object> map);
	
	/**
	 * 删除班级
	 * @param id Long数组
	 * @return 成功删除的记录数
	 */
	public long deleteClasses(Long[] id, String username);
	
	/**
	 * 对一个班的学生进行归档
	 * @param classId 班级ID
	 * @return 归档成功与否
	 */
	public boolean archiveStudentsInClass(Long classId, String username);
	
	/**
	 * 对多个班级进行归档
	 * @param classId 班级ID集合
	 * @param username 操作用户名
	 * @return 归档成功的班级数目
	 */
	public long archiveClasses(Long[] classId, String username);
	
	/**
	 * 查询一个未归档班级的学生
	 * @param classId 班级ID
	 * @return 学生信息列表
	 */
	public List<Record> getUnarchiveStudentsInClass(Long classId);
	
	/**
	 * 查询一个已归档班级的学生
	 * @param classId 班级ID
	 * @return 学生信息列表
	 */
	public List<Record> getArchiveStudentsInClass(Long classId);
	
	/**
	 * 查询一个未归档班级的学生
	 * @param classId 班级ID
	 * @return 学生信息列表
	 */
	public List<Record> getUnarchiveStudentsInClass(Long classId, Long start, Long length);
	
	/**
	 * 查询一个已归档班级的学生
	 * @param classId 班级ID
	 * @return 学生信息列表
	 */
	public List<Record> getArchiveStudentsInClass(Long classId, Long start, Long length);
	
	/**
	 * 查询一个未归档班级的所有学生人数
	 * @param classId
	 * @return
	 */
	public long getUnarchiveStudentsAmountInClass(Long classId);
	
	/**
	 * 查询一个已归档班级的所有学生人数
	 * @param classId
	 * @return
	 */
	public long getArchiveStudentsAmountInClass(Long classId);
	
	/**
	 * 根据条件查询回访记录信息
	 * @param map 条件
	 * @param start 起始位置
	 * @param length 偏移量
	 * @return 列表
	 */
	public List<Record> searchCallbackInfo(Map<String, Object> map, Long start, Long length);
	
	/**
	 * 查询符合条件的回访记录数
	 * @param map 条件
	 * @return 记录数
	 */
	public long searchCallbackCount(Map<String, Object> map);
	
	/**
	 * 从班级中随机选择一个学生进行电话回访
	 * @param classInfoId
	 * @return 被选中的学生记录
	 */
	public Record chooseStudentInClassForReview(Long classInfoId);
	
}
