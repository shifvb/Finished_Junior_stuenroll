package cn.gov.hrss.ln.stuenroll.db.mariadb;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_UserDao;

/**
 * User表Dao类
 * 
 * @author YangDi
 *
 */
public class UserDao implements I_UserDao {

	@Override
	public boolean login(String username, String password) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	username = ? ");
		sql.append("AND `password` = HEX( ");
		sql.append("	AES_ENCRYPT(?, 'HelloHrss') ");
		sql.append("); ");

		// 执行查询语句
		long count = Db.queryLong(sql.toString(), username, password);
		boolean bool = (count == 1);
		return bool;
	}

	@Override
	public boolean checkRecoverPersonalInfo(String name, String pid, String organization) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	`name` = ? ");
		sql.append("AND pid = ? ");
		sql.append("AND organization_id = ( ");
		sql.append("	SELECT ");
		sql.append("		id ");
		sql.append("	FROM ");
		sql.append("		organization ");
		sql.append("	WHERE ");
		sql.append("		`name` = ? ");
		sql.append("); ");

		long count = Db.queryLong(sql.toString(), name, pid, organization);
		boolean bool = (count == 1);

		return bool;
	}

	@Override
	public boolean checkAccountInfo(String username, String email, String tel) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	`username` = ? ");
		sql.append("AND email = ? ");
		sql.append("AND tel = ? ; ");

		long count = Db.queryLong(sql.toString(), username, email, tel);
		boolean bool = (count == 1);

		return bool;
	}

	@Override
	public boolean checkQuestionInfo(String username, String question, String answer) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	`username` = ? ");
		sql.append("AND question = ? ");
		sql.append("AND answer = ?; ");

		long count = Db.queryLong(sql.toString(), username, question, answer);
		boolean bool = (count == 1);
		return bool;
	}

	@Override
	public int recoverPassword(String username, String password) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `user` ");
		sql.append("SET `password` = HEX( ");
		sql.append("	AES_ENCRYPT(?, 'HelloHrss') ");
		sql.append(") ");
		sql.append("WHERE ");
		sql.append("	username = ?;");

		int count = Db.update(sql.toString(), password, username);

		return count;
	}

	@Override
	public Record searchUserInfoAtRecoverPassword(String username) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	`name`, ");
		sql.append("	tel, ");
		sql.append("	wechat, ");
		sql.append("	email ");
		sql.append("FROM ");
		sql.append("	`user` ");
		sql.append("WHERE ");
		sql.append("	username = ?; ");

		Record record = Db.findFirst(sql.toString(), username);

		return record;
	}

	@Override
	public Record searchUserInfoAtLogin(String username) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	CAST(u.`id` AS CHAR) AS `id`, ");
		sql.append("	u.`name`, ");
		sql.append("	o.`name` AS organization, ");
		sql.append("	o.id AS organization_id, ");
		sql.append("	r.`name` AS role, ");
		sql.append("	u.`photo_id` ");
		sql.append("FROM ");
		sql.append("	`user` u ");
		sql.append("JOIN organization o ON u.organization_id = o.id ");
		sql.append("JOIN role r ON u.role_id = r.id ");
		sql.append("WHERE ");
		sql.append("	username =?; ");

		Record record = Db.findFirst(sql.toString(), username);
		return record;
	}

	@Override
	public Record searchIfUserHasBeenBlockedAndRoleId(String username) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	CAST(`u`.`block` AS CHAR) AS `block`, ");
		sql.append("	CAST(`u`.`role_id` AS CHAR) AS `role_id` ");
		sql.append("FROM ");
		sql.append("	`user` AS `u` ");
		sql.append("WHERE ");
		sql.append("	`u`.`username` = ? ");
		Record record = Db.findFirst(sql.toString(), username);
		return record;
	}

	@Override
	public Record searchIfRoleHasBeenBlocked(String role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  CAST(`r`.`block` AS CHAR) AS `block` ");
		sql.append("FROM  ");
		sql.append("	`role` AS `r` ");
		sql.append("WHERE ");
		sql.append("	`r`.`id` = ?; ");
		Record record = Db.findFirst(sql.toString(), role_id);
		return record;
	}

}
