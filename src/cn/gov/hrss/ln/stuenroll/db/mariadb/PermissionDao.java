package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_PermissionDao;

/**
 * Permission表DAO类
 * 
 * @author LiYadong
 *
 */
public class PermissionDao implements I_PermissionDao {

	@Override
	public List<Record> searchPermissions(String username) {
		
		StringBuffer sql = new StringBuffer();
		if ("admin".equals(username)) {
			sql.append("SELECT id AS permission_id FROM permission WHERE id != 0; ");
			List<Record> list = Db.find(sql.toString());
			return list;
		} 
		else {
			sql.append("SELECT ");
			sql.append("	rp.permission_id ");
			sql.append("FROM ");
			sql.append("	`user` u ");
			sql.append("JOIN role_permission rp ON u.role_id = rp.role_id ");
			sql.append("WHERE ");
			sql.append("	username = ?; ");
		
			List<Record> list = Db.find(sql.toString(), username);
			return list;
		}
		
	}
	
	@Override
	public List<Record> searchAllPermissions() {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT id AS permission_id FROM permission;");
		List<Record> list=Db.find(sql.toString());
		return list;
	}

}
