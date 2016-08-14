package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_SystemUserDao;

public class SystemUserDao implements I_SystemUserDao {

	@Override
	public List<Record> searchUser(Long start, Long length) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	CAST(id AS CHAR) AS `user_id`, ");
		sql.append("	`username`, ");
		sql.append("	`name`, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			`o`.`name` ");
		sql.append("		FROM ");
		sql.append("			`organization` AS o ");
		sql.append("		WHERE ");
		sql.append("			`u`.`organization_id` = `o`.id ");
		sql.append("	) AS `organization_name`, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			`r`.`name` ");
		sql.append("		FROM ");
		sql.append("			`role` AS `r` ");
		sql.append("		WHERE ");
		sql.append("			`u`.`role_id` = `r`.`id` ");
		sql.append("	) AS `role_name`, ");
		sql.append("	`tel`, ");
		sql.append("	`email`, ");
		sql.append("	`block` ");
		sql.append("FROM ");
		sql.append("	`user` AS `u` ");
		sql.append("WHERE ");
		sql.append("	`u`.`id` != '738598445098602496' ");
		sql.append("AND `u`.`role_id` != 1 ");
		sql.append("ORDER BY `user_id` ");
		sql.append("LIMIT ?, ?; ");
		List<Record> records = Db.find(sql.toString(), start, length);
		return records;
	}

	@Override
	public Long searchUserCount() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS `user_count` ");
		sql.append("FROM ");
		sql.append("	`user` AS `u` ");
		sql.append("WHERE ");
		sql.append("	`u`.`id` != '738598445098602496' ");
		sql.append("AND `u`.`role_id` != 1 ");
		Long i = Db.queryLong(sql.toString());
		return i;
	}

	@Override
	public int changeUserStatus(Long user_id, boolean block) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `user` ");
		sql.append("SET ");
		if (block) {
			sql.append(" `block` = '1' ");
		}
		else {
			sql.append(" `block` = '0' ");
		}
		sql.append("WHERE ");
		sql.append("	(`id` = ?); ");
		int i = Db.update(sql.toString(), user_id);
		return i;
	}

	@Override
	public int addUser(List params) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `user` ( ");
		sql.append("	`id`, ");
		sql.append("	`username`, ");
		sql.append("	`password`, ");
		sql.append("	`name`, ");
		sql.append("	`pid`, ");
		sql.append("	`sex`, ");
		sql.append("	`organization_id`, ");
		sql.append("	`tel`, ");
		sql.append("	`wechat`, ");
		sql.append("	`email`, ");
		sql.append("	`question`, ");
		sql.append("	`answer`, ");
		sql.append("	`block`, ");
		sql.append("	`role_id`, ");
		sql.append("	`position`, ");
		sql.append("	`photo_id` ");
		sql.append(") ");
		sql.append("VALUE ( ");
		sql.append("	?, ");
		sql.append("	?, ");
		sql.append("	HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append("	?, ");
		sql.append("	?, ");
		sql.append("	?, ");
		sql.append("	?, ");
		sql.append("	?, ");
		sql.append("	NULL, ");
		sql.append("	?, ");
		sql.append("	?, ");
		sql.append("	?, ");
		sql.append("	?, ");
		sql.append("	?, ");
		sql.append("	?, ");
		sql.append("	? ");
		sql.append("); ");
		int i = Db.update(sql.toString(), params.toArray());
		return i;
	}

	@Override
	public List<Record> searchAllOrganizationIdAndNames() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	CAST(`o`.`id` AS CHAR) AS organization_id, ");
		sql.append("	`o`.`name` AS organization_name ");
		sql.append("FROM ");
		sql.append("	`organization` AS `o` ");
		sql.append("ORDER BY ");
		sql.append("	`organization_id`; ");
		List<Record> records = Db.find(sql.toString());
		return records;
	}

	@Override
	public List<Record> searchAllRoleIdAndNames() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	CAST(`r`.`id` AS CHAR) AS role_id, ");
		sql.append("	`r`.`name` AS role_name ");
		sql.append("FROM ");
		sql.append("	`role` AS `r` ");
		sql.append("WHERE ");
		sql.append("	`r`.`id` != 1; ");
		List<Record> records = Db.find(sql.toString());
		return records;
	}

	@Override
	public Record searchUserById(Long user_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	CAST(`u`.`id` AS CHAR) AS `user_id`, ");
		sql.append("	`u`.`username`, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(`u`.`password`), 'HelloHrss') AS CHAR) AS `password`, ");
		sql.append("	`u`.`question`, ");
		sql.append("	`u`.`answer`, ");
		sql.append("	`u`.`name`, ");
		sql.append("	`u`.`sex`, ");
		sql.append("	`u`.`pid`, ");
		sql.append("	`u`.`tel`, ");
		sql.append("	`u`.`email`, ");
		sql.append("	CAST(`u`.`organization_id` AS CHAR) AS `organization_id`, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			`o`.`name` ");
		sql.append("		FROM ");
		sql.append("			`organization` AS `o` ");
		sql.append("		WHERE ");
		sql.append("			`o`.`id` = `u`.`organization_id` ");
		sql.append("	) AS organization_name, ");
		sql.append("	`u`.`position`, ");
		sql.append("	CAST(`u`.`role_id` AS CHAR) AS `role_id`, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			`r`.`name` ");
		sql.append("		FROM ");
		sql.append("			`role` AS `r` ");
		sql.append("		WHERE ");
		sql.append("			`r`.`id` = `u`.`role_id` ");
		sql.append("	) AS role_name, ");
		sql.append("	`u`.`block`, ");
		sql.append("	`u`.`photo_id` ");
		sql.append("FROM ");
		sql.append("	`user` AS `u` ");
		sql.append("WHERE ");
		sql.append("	`u`.`id` = ?; ");
		Record record = Db.findFirst(sql.toString(), user_id);
		return record;
	}

	@Override
	public int modifyUserById(Long user_id, Map map) {
		List params = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `user` ");
		sql.append("SET ");
		if (map.get("username") != null) {
			sql.append(" `username` = ?, ");
			params.add(map.get("username"));
		}
		if (map.get("password") != null) {
			sql.append(" `password` = HEX(AES_ENCRYPT(?, 'HelloHrss')), ");
			params.add(map.get("password"));
		}
		if (map.get("name") != null) {
			sql.append(" `name` = ?, ");
			params.add(map.get("name"));
		}
		if (map.get("pid") != null) {
			sql.append(" `pid` = ?, ");
			params.add(map.get("pid"));
		}
		if (map.get("sex") != null) {
			sql.append(" `sex` = ?, ");
			params.add(map.get("sex"));
		}
		if (map.get("organization_id") != null) {
			sql.append(" `organization_id` = ?, ");
			params.add(Long.valueOf((String) map.get("organization_id")));
		}
		if (map.get("tel") != null) {
			sql.append(" `tel` = ?, ");
			params.add(map.get("tel"));
		}
		if (map.get("email") != null) {
			sql.append(" `email` = ?, ");
			params.add(map.get("email"));
		}
		if (map.get("question") != null) {
			sql.append(" `question` = ?, ");
			params.add(map.get("question"));
		}
		if (map.get("answer") != null) {
			sql.append(" `answer` = ?, ");
			params.add(map.get("answer"));
		}
		if (map.get("block") != null) {
			sql.append(" `block` = ?, ");
			params.add(map.get("block"));
		}
		if (map.get("role_id") != null) {
			sql.append(" `role_id` = ?, ");
			params.add(Long.valueOf((String) map.get("role_id")));
		}
		if (map.get("position") != null) {
			sql.append(" `position` = ?, ");
			params.add(map.get("position"));
		}
		if (map.get("photo_id") != null) {
			sql.append(" `photo_id` = ?, ");
			params.add(map.get("photo_id"));
		}
		sql.append(" `create_time` = `create_time` ");
		sql.append("WHERE ");
		sql.append("	`id` = ?; ");
		params.add(user_id);
		int i = Db.update(sql.toString(), params.toArray());
		return i;
	}

	@Override
	public int deleteUserById(Long user_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	`id` = ?; ");
		int i = Db.update(sql.toString(), user_id);
		return i;
	}

	@Override
	public List<Record> searchAllUser() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  CAST (`id` AS CHAR) AS id, ");
		sql.append("	`username`, ");
		sql.append("	`password`, ");
		sql.append("	`name`, ");
		sql.append("	`pid`, ");
		sql.append("	`sex`, ");
		sql.append("  CAST (`organization_id` AS CHAR) AS organization_id, ");
		sql.append("	`tel`, ");
		sql.append("	`wechat`, ");
		sql.append("	`email`, ");
		sql.append("	`question`, ");
		sql.append("	`answer`, ");
		sql.append("	`block`, ");
		sql.append("  CAST (`role_id` AS CHAR) AS role_id, ");
		sql.append("	`create_time`, ");
		sql.append("	`position`, ");
		sql.append("	`photo_id` ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	role_id != 1; ");
		List<Record> records = Db.find(sql.toString());
		return records;
	}

	@Override
	public int deleteAllUser() {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	`role_id` != 1; ");
		int i = Db.update(sql.toString());
		return i;
	}

	@Override
	public int importAllUser(List params) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `hrss`.`user` ( ");
		sql.append("	`id`, ");
		sql.append("	`username`, ");
		sql.append("	`password`, ");
		sql.append("	`name`, ");
		sql.append("	`pid`, ");
		sql.append("	`sex`, ");
		sql.append("	`organization_id`, ");
		sql.append("	`tel`, ");
		sql.append("	`wechat`, ");
		sql.append("	`email`, ");
		sql.append("	`question`, ");
		sql.append("	`answer`, ");
		sql.append("	`block`, ");
		sql.append("	`role_id`, ");
		sql.append("	`create_time`, ");
		sql.append("	`position`, ");
		sql.append("	`photo_id` ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		?, ");
		sql.append("		? ");
		sql.append("	); ");
		int i = Db.update(sql.toString(), params.toArray());
		return i;
	}

}
