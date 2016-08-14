package cn.gov.hrss.ln.stuenroll.classmanagement;


/**
 * 班级管理网络接口
 * @author LiYadong
 * @version 1.0
 */

public interface I_ClassInfoController {
	
	/**
	 * 查询所有班级
	 * @return
	 */
	public void searchAllClasses();
	
	/**
	 * 查询班级管理的信息
	 * 条件：{
	 * 		"organizationId": 机构ID,
	 * 		"name": 班级名称,
	 * 		"year": 年届,
	 * 		"organizationName": 机构名称,
	 * 		"professionName": 专业名称,
	 * 		"classState": 班级状态,
	 * 		"stuState": 学生状态
	 * } 
	 * 返回结果：{
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
	public void searchClassInfo();
	
	/**
	 * 根据条件查询班级数量
	 * 条件：{
	 * 		"organizationId": 机构ID,
	 * 		"name": 班级名称,
	 * 		"year": 年届,
	 * 		"organizationName": 机构名称,
	 * 		"professionName": 专业名称,
	 * 		"classState": 班级状态,
	 * }
	 */
	public void searchClassAmount();
	
	/**
	 * 添加班级
	 */
	public void addClass();
	
	/**
	 * 更新班级信息
	 */
	public void updateClassInfo();
	
	/**
	 * 删除班级
	 */
	public void deleteClasses();
	
	/**
	 * 查询一个班级的学生
	 */
	public void getStudentsInClass();
	
	/**
	 * 查询一个班级的所有学生人数
	 */
	public void getStudentsAmountInClass();
	
	
	/**
	 * 班级归档
	 */
	public void archiveClasses();
	
	/**
	 * 根据条件查询回访记录信息
	 */
	public void searchCallbackInfo();
	
	/**
	 * 查询符合条件的回访记录数
	 */
	public void searchCallbackCount();
	
	/**
	 * 从班级中随机选择一个学生进行电话回访
	 */
	public void chooseStudentInClassForReview();
	
}
