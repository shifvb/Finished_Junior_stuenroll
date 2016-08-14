package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.List;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import cn.gov.hrss.ln.stuenroll.db.I_RoleDao;

/**
 * Role表Dao实现类
 * 
 * @author shifvb
 *
 */
public class RoleDao implements I_RoleDao {

	@Override
	public List<Record> searchAllFromRole(long start, long length) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	`id`, ");
		sql.append("	`name`, ");
		sql.append("	`block` ");
		sql.append("FROM ");
		sql.append("	role ");
		sql.append("where id != 1 ");
		sql.append("ORDER BY `id` ");
		sql.append("LIMIT ?, ?; ");
		List<Record> records = Db.find(sql.toString(), start, length);
		return records;
	}

	@Override
	public Long searchOrganizationUserCountByRoleId(Long role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	count(*) organization_user_count ");
		sql.append("FROM ");
		sql.append("	`user` u ");
		sql.append("JOIN `organization` o ON u.organization_id = o.id ");
		sql.append("WHERE ");
		sql.append("	role_id = ? ");
		sql.append("AND o.`name` != '辽宁省就业网'; ");
		long i = Db.queryLong(sql.toString(), role_id);
		return i;
	}

	@Override
	public Long searchEmploymentUserCountByRoleId(Long role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	count(*) AS employment_user_count ");
		sql.append("FROM ");
		sql.append("	`user` u ");
		sql.append("JOIN `organization` o ON u.organization_id = o.id ");
		sql.append("WHERE ");
		sql.append("	role_id = ? ");
		sql.append("AND o.`name` = '辽宁省就业网'; ");
		long i = Db.queryLong(sql.toString(), role_id);
		return i;
	}

	@Override
	public Long searchPermissionCountByRoleId(Long role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	count(*) AS permission_count ");
		sql.append("FROM ");
		sql.append("	role_permission ");
		sql.append("WHERE ");
		sql.append("	role_id = ?; ");
		long i = Db.queryLong(sql.toString(), role_id);
		return i;
	}

	@Override
	public Long searchInvalidUserCountByRoleId(Long role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS invalid_user_count ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	role_id = ? ");
		sql.append("AND block = 1; ");
		long i = Db.queryLong(sql.toString(), role_id);
		return i;
	}

	@Override
	public Long searchValidUserCountByRoleId(Long role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS valid_user_count ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	role_id = ? ");
		sql.append("AND block = 0; ");
		long i = Db.queryLong(sql.toString(), role_id);
		return i;
	}

	@Override
	public Long searchUserCountByRoleId(Long role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS user_count ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	role_id = ? ");
		long i = Db.queryLong(sql.toString(), role_id);
		return i;
	}

	@Override
	public Long searchRoleCount() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS count ");
		sql.append("FROM ");
		sql.append("	`role` ");
		sql.append("where `role`.`id` != 1; ");
		return Db.queryLong(sql.toString());
	}

	@Override
	public List<Record> searchAllPermissions() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	`id` As permission_id, ");
		sql.append("	`name` AS permission_name ");
		sql.append("FROM ");
		sql.append("	`permission` ");
		sql.append("WHERE ");
		sql.append("	`permission`.`id` != 0; ");
		List<Record> records = Db.find(sql.toString());
		return records;
	}

	@Override
	public int addRole(String roleName) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `role`(`id`, `name`, `block`) ");
		sql.append("VALUES ( ");
		sql.append("	NEXT VALUE FOR MYCATSEQ_GLOBAL, ?, FALSE ");
		sql.append("); ");
		int i = Db.update(sql.toString(), roleName);
		return i;
	}

	@Override
	public Long searchRoleIdByRoleName(String role_name) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	`id` AS role_id ");
		sql.append("FROM ");
		sql.append("	`role` WHERE　 `role`.`name` = ?; ");
		Long i = Db.queryLong(sql.toString(), role_name);
		return i;
	}

	@Override
	public int setRolePermissionByRoleId(Long role_id, String permission) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `role_permission` ( ");
		sql.append("	`id`, ");
		sql.append("	`role_id`, ");
		sql.append("	`permission_id` ");
		sql.append(") ");
		sql.append("VALUES ( ");
		sql.append("	NEXT VALUE FOR MYCATSEQ_GLOBAL,  ");
		sql.append("	?, ");
		sql.append("	? ");
		sql.append("); ");
		int i = Db.update(sql.toString(), role_id, permission);
		return i;
	}

	@Override
	public int changeRoleStatus(Long role_id, boolean bool) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `role` ");	
		sql.append("SET  ");
		if (bool) {
			sql.append(" `block` = '0' ");
		}
		else {
			sql.append(" `block` = '1' ");
		}
		sql.append("WHERE ");
		sql.append("	(`id` = ?); ");
		int i = Db.update(sql.toString(), role_id);
		return i;
	}

	@Override
	public List<Record> searchRolePermissionByRoleId(Long role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	`permission_id` ");
		sql.append("FROM ");
		sql.append("	`role_permission` ");
		sql.append("WHERE ");
		sql.append("	`role_id` = ?; ");
		List<Record> records = Db.find(sql.toString(), role_id);
		return records;
	}

	@Override
	public int deleteRolePermissionByRoleId(Long role_id, String permission) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	`role_permission` ");
		sql.append("WHERE ");
		sql.append("	`role_id` =  ? ");
		sql.append("AND `permission_id` = ?; ");
		int i = Db.update(sql.toString(), role_id, permission);
		return i;
	}

	@Override
	public int updateRoleNameByRoleId(Long role_id, String role_name) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `role` ");
		sql.append("SET ");
		sql.append(" `name` = ? ");
		sql.append("WHERE ");
		sql.append("	(`id` = ?); ");
		int i = Db.update(sql.toString(), role_name, role_id);
		return i;
	}

	@Override
	public int deletePermissionsByRoleId(Long role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	`role_permission` ");
		sql.append("WHERE ");
		sql.append("	`role_id` = ?; ");
		int i = Db.update(sql.toString(), role_id);
		return i;
	}

	@Override
	public int deleteRoleByRoleId(Long role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	`role` ");
		sql.append("WHERE ");
		sql.append("	`id` = ?; ");
		int i = Db.update(sql.toString(), role_id);
		return i;
	}

}
