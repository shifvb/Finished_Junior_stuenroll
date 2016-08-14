package cn.gov.hrss.ln.stuenroll.permission;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 权限模块业务接口
 * 
 * @author YangDi
 *
 */
public interface I_PermissionService {
	/**
	 * 查询用户拥有的权限
	 * 
	 * @param username
	 * @return
	 */
	public List<Record> searchPermissions(String username);
	

	/**
	 * 查询所有权限
	 * 
	 * @return
	 */
	public List<Record> searchAllPermissions();
}
