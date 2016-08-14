package cn.gov.hrss.ln.stuenroll.systemUser;


public interface I_SystemUserController {

	
	// 基本上只能用于用户管理 显示表格 start
	
	
	/**
	 * 显示所有user的数据（不包括管理员）
	 * 
	 * 接口：
	 * 	/stuenroll/systemUser/searchUser?page=1
	 */
	public void searchUser();
	
	
	/**
	 * 查询共有多少个用户(不包括管理员)
	 * 
	 * 接口：
	 * 	/stuenroll/systemUser/searchRoleCount
	 */
	public void searchUserCount();
	
	/**
	 * 改变用户状态
	 * 
	 * 接口：
	 * 	/stuenroll/systemUser/changeUserStatus?block=1
	 */
	public void changeUserStatus();
	
	// 基本上只能用于用户管理 显示表格 end
	
	// 基本上只能用于用户管理 添加菜单 start
	
	/**
	 * 添加用户
	 * 
	 * 接口：
	 * 	http://127.0.0.1/stuenroll/systemUser/addUser?username=shifvb2&password=97033925
	 * 		&name=侯一宁&pid=210881199409280832&sex=女&organization_id=738620746913419264
	 * 		&tel=18202415763&email=shifvb2@gmail.com&question=whoareyou&answer=GodPlease
	 * 		&status=开&role_id=746258230002454528&position=测试职位
	 */
	public void addUser();
	
	/**
	 * 查找全部机构的id，name
	 * 
	 * 接口：
	 * 	/stuenroll/systemUser/searchAllOrganizationIdAndNames
	 */
	public void searchAllOrganizationIdAndNames();
	
	/**
	 * 查找全部角色的id，name
	 * 
	 * 接口：
	 * 	/stuenroll/systemUser/searchAllRoleIdAndNames
	 */
	public void searchAllRoleIdAndNames();

	// 基本上只能用于用户管理 添加菜单 end
	
	// 基本上只能用于用户管理 修改菜单 start

	/**
	 * 根据user的ID，为未来的修改查找所需要的信息
	 * 
	 * 接口：
	 * 	/stuenroll/systemUser/searchUserById?user_id=753488533150371840
	 */
	public void searchUserById();
	
	/**
	 * 根据user的ID,修改user
	 * 
	 * 接口：
	 * 	http://127.0.0.1/stuenroll/systemUser/modifyUserById?user_id=753511453964767232
	 * 		&username=hanyue&password=97033925
	 *		&name=韩月&pid=440602199503202113&sex=男
	 * 		&organization_id=738625563522109440&tel=18604024053
     * 		&email=hanyue@gmail.com&question=此生无悔入东方&answer=来世愿生幻想乡
     *  	&block=0&role_id=746258230002454528&position=测试职位
	 */
	public void modifyUserById();

	// 基本上只能用于用户管理 修改菜单 end
	
	// 基本上只能用于用户管理 删除用户 start

	/**
	 * 根据userID删除用户
	 * 
	 * 接口：
	 * 	/stuenroll/systemUser/deleteUserById?user_ids=`这是个列表`
	 */
	public void deleteUserById();

	// 基本上只能用于用户管理 删除用户 end
	
	/**
	 * 导出user数据
	 * 接口：
	 * 	/stuenroll/systemUser/exportUser?type=Excel|CSV|XML
	 */
	public void exportUser();
	
	/**
	 * 导入user数据
	 * 接口：
	 * 	/stuenroll/systemUser/importUser
	 */
	public void importUser();
}
