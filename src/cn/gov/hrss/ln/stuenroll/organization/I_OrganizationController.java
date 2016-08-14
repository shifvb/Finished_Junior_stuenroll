package cn.gov.hrss.ln.stuenroll.organization;


/**
 * 机构管理网络接口
 * 
 * @author LiYaDong
 * @version 1.0
 *
 */
public interface I_OrganizationController {
	
	
	/**
	 * 根据传入的条件查询机构信息
	 * 
	 */
	public void searchOrganizations();	
	
	/**
	 * 根据条件查询符合条件的机构数
	 */
	public void searchOrganizationAmount();
	
	/**
	 * 根据传入的机构信息插入机构
	 * 
	 */
	public void addOrganization();
	
	/**
	 * 根据传入的机构新信息更新数据库
	 * 
	 */
	public void updateOrganization();
	
	/**
	 * 根据传入的ID数组批量删除机构
	 * 
	 */
	public void deleteOrganizationsById();
	
	/**
	 * 设置机构参与转换
	 */
	public void setOrganizationTransformInfo();

	/**
	 * 查询机构列表
	 */
	public void searchOrganization(); 
}
