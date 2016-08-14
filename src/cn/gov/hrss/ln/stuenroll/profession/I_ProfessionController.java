package cn.gov.hrss.ln.stuenroll.profession;
//最新

public interface I_ProfessionController {
	/**
	 * 在下拉框中根据年份查找相关机构的专业
	 */
	public void searchProfessionInYearAtDropDown();
	/**
	 * 查询专业相关统计信息
	 * @param map
	 * @return
	 */
	public void searchProfessionStatistics();
	/**
	 * 查询专业总数
	 * @return
	 */
	public void searchProfessionCount();
	/**
	 * 查询所有专业
	 * @return
	 */
	public void searchAllProfession();
	/**
	 * 添加专业信息
	 */
	public void addProfession();
	
	/**
	 * 修改专业信息
	 */
	public void updateProfession();
	/**
	 * 根据专业ID删除专业
	 * @param id
	 * @return
	 */
	public void deleteProfessionById();
	
	/**
	 * 根据条件查询专业
	 * 条件
	 * {
	 * 		"organizationId": 机构ID,
	 * 		"year": 年届
	 * }
 	 * 记录
	 * {
	 * 		"professionId": 专业ID,
	 * 		"professionName": 专业名称,
	 * 		"year": 年届
	 * }
	 */
	public void searchProfessionsWithOrganization();

	/**
	 * 导出专业记录
	 */
	public void exportProfession();
	
	/**			
	 * 导入专业记录
	 */
	public void importProfession();
}
