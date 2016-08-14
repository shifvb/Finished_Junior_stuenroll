package cn.gov.hrss.ln.stuenroll.db;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * Permission表Dao接口
 * 
 * @author YangDi
 *
 */
public interface I_PermissionDao {
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
