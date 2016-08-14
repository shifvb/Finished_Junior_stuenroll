package cn.gov.hrss.ln.stuenroll.organization;

/**
 * 参与转换机构网络类
 * 
 * @author LiYadong
 * @version 1.0
 */

public interface I_OrganizationTransformController {

	/**
	 * 根据条件查询参与转换的机构列表
	 * 
	 */
	public void searchOrganizations();

	/**
	 * 查询符合条件参与转换的机构数量
	 */
	public void searchOrganizationsAmount();

	/**
	 * 根据条件查询机构关联的专业数
	 * 
	 */
	public void searchProfessionsAmount();

	/**
	 * 根据条件查询机构关联的班级数
	 * 
	 */
	public void searchClassAmount();

	/**
	 * 根据条件查询机构关联的学生数
	 */
	public void searchStudentsAmount();

	/**
	 * 更新参与转换机构信息
	 * 
	 */
	public void updateOrganization();

	/**
	 * 删除参与转换的机构
	 * 
	 */
	public void deleteOrganizations();

	/**
	 * 向机构专业关联表插入数据
	 */
	public void addOrganizationProfession();

	/**
	 * 在机构专业关联表删除数据
	 */
	public void deleteOrganizationProfession();

	/**
	 * 根据年份和专业ID查询参与转换的机构列表
	 */
	public void searchOrganizationsJoinWithProfessionByYearAtDropdown();
	
	/**
	 * 根据专业ID查询参与转换的机构列表
	 */
	public void searchOrganizationsJoinAtDropdown();
	
	/**
	 * 根据年份查询参与转换的机构列表
	 * 
	 */
	public void searchOrganizationsJoinByYearAtDropdown();

}
