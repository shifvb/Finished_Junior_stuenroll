package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_UserLogDao;

/**
 * UserLog表Dao类
 * 
 * @author YangDi
 *
 */
public class UserLogDao implements I_UserLogDao {

	@Override
	public void save(String username, String operation, String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO user_log ( ");
		sql.append("	id, ");
		sql.append("	user_id, ");
		sql.append("	operation, ");
		sql.append("	type, ");
		sql.append("	sharding ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	(NEXT VALUE FOR MYCATSEQ_GLOBAL, (SELECT id FROM `user` WHERE username = ?), ?, ?, ?); ");

		Db.update(sql.toString(), username, operation, type, new Date().getTime());

	}

	@Override
	public Record searchUserLogTimeAndType(String user_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ");
		sql.append("	`u`.`create_time`, ");
		sql.append("	`type` ");
		sql.append("FROM  ");
		sql.append("	`user_log` AS `u` ");
		sql.append("WHERE  ");
		sql.append("	`u`.user_id = ? ");
		sql.append("AND (`u`.`type` = '登陆' OR `u`.`type` = '退出') ");
		sql.append("ORDER BY `create_time` DESC ");
		sql.append("LIMIT 1; ");
		Record r = Db.findFirst(sql.toString(), user_id);
		return r;
	}

}
