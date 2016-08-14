package cn.gov.hrss.ln.stuenroll.db;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

/**
 * 班级管理数据访问接口
 * @author LiYadong
 * @version 2.0
 */

public interface I_ClassInfoDao {
	
	/**
	 * 查询所有班级
	 * @return
	 */
	public List<Record> searchAllClasses(Map<String, Object> map);

	/**
	 * 查询班级的基本信息
	 * @param map 查询条件：
	 * {
	 * 		"organizationId": 机构ID,
	 * 		"name": 班级名称,
	 * 		"year": 年届,
	 * 		"organizationName": 机构名称,
	 * 		"professionName": 专业名称,
	 * 		"classState": 班级状态
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
	 * 		"classStateInfo": 班级状态
	 * }
	 */
	public List<Record> searchClassInfo(Map<String, Object> map, Long start, Long length);
	
	/**
	 * 根据条件查找已分班的学员数量
	 * @param map 条件：
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
	 * 		"archiveStatedStudents": 已归档的具有某种状态的学生人数, 
	 * 		"year": 年届,
	 * }
	 */
	public List<Record> searchArchiveClassStudentsAmountWithState(Map<String, Object> map, Long start, Long length);
	
	/**
	 * 根据条件查找没有分班学员的信息
	 * @param map 条件：
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
	 * 		"unarchiveStatedStudents": 未归档具有某种状态的学生人数, 
	 * 		"year": 年届,
	 * }
	 */
	public List<Record> searchUnarchiveClassStudentsAmountWithState(Map<String, Object> map, Long start, Long length);
	
	/**
	 * 根据条件查询班级数量
	 * @param map
	 * {
	 * 		"organizationId": 机构ID,
	 * 		"name": 班级名称,
	 * 		"year": 年届,
	 * 		"organizationName": 机构名称,
	 * 		"professionName": 专业名称,
	 * 		"classState": 班级状态,
	 * }
	 * @return 数量
	 */
	public long searchClassAmountWithState(Map<String, Object> map);
	
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
	public long deleteClasses(Long[] id);
	
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
	public List<Record> getArchiveStudentsInClass(Long classId, Long start, Long length);
	
	/**
	 * 查询一个未归档班级的学生
	 * @param classId 班级ID
	 * @return 学生信息列表
	 */
	public List<Record> getUnarchiveStudentsInClass(Long classId, Long start, Long length);
	
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
	 * 查询一个已归档班级的学生
	 * @param classId 班级ID
	 * @return 学生信息列表
	 */
	public List<Record> getArchiveStudentsInClass(Long classId);
	
	/**
	 * 通过一个Record来归档一个学生
	 * @param record 学生信息
	 * @return 成功的记录数
	 */
	public int archiveStudent(Record record);
	
	/**
	 * 通过一个map来归档学生
	 * @param map 学生信息
	 * @return 成功归档的记录数
	 */
	public int archiveStudent(Map<String, Object> map);
	
	/**
	 * 归档一个班级的学生
	 * @param records 班级学生信息
	 * @return 成功归档的记录数
	 */
	public long archiveStudentsInClass(List<Record> records);
	
	/**
	 * 归档一个班级的学生
	 * @param id key数组
	 * @param info 学生信息
	 * @return 成功归档的记录数
	 */
	public long archiveStudentsInClass(Object[] id, Map<Object, Map<String, Object>> info);
	
	/**
	 * 归档一个班级的学生
	 * @param id key数组
	 * @param info 学生信息
	 * @return 成功归档的记录数
	 */
	public long archiveStudentsInClass(List<Object> id, Map<Object, Map<String, Object>> info);
	
}
