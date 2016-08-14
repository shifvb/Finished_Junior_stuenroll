package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.ICallback;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_FriendDao;

public class FriendDao implements I_FriendDao {

	@Override
	public Record searchUserByUsername(String username) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ");
		sql.append("	CAST(`u`.`id` AS CHAR) AS `user_id`, ");
		sql.append("	`u`.`name`, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			`r`.`name` ");
		sql.append("		FROM ");
		sql.append("			`role` AS `r` ");
		sql.append("		WHERE ");
		sql.append("			`u`.`role_id` = `r`.`id` ");
		sql.append("	) AS `role_name`, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			`o`.`name` ");
		sql.append("		FROM ");
		sql.append("			`organization` AS `o` ");
		sql.append("		WHERE ");
		sql.append("			`u`.`organization_id` = `o`.`id` ");
		sql.append("	) AS `organization_name`, ");
		sql.append("	`photo_id` ");
		sql.append("FROM ");
		sql.append("	`user` AS `u` ");
		sql.append("WHERE ");
		sql.append("	`u`.`username` = ?; ");
		Record record = Db.findFirst(sql.toString(), username);
		return record;
	}

	@Override
	public int addUserToFriendListByUserId(String sponser, String target, String favorite) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `friend_list` ( ");
		sql.append("	`id`, ");
		sql.append("	`sponser`, ");
		sql.append("	`target`, ");
		sql.append("	`favorite` ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT VALUE FOR MYCATSEQ_GLOBAL, ");
		sql.append("			?, ");
		sql.append("			?, ");
		sql.append("			? ");
		sql.append("	); ");
		int i = Db.update(sql.toString(), sponser, target, favorite);
		return i;
	}

	@Override
	public List<Record> searchFriend(Long user_id, Long start, Long end) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	CAST(`u`.`id` AS CHAR) AS `user_id`, ");
		sql.append("	`u`.`name`,");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			`r`.`name` ");
		sql.append("		FROM ");
		sql.append("			`role` AS `r` ");
		sql.append("		WHERE ");
		sql.append("			`u`.`role_id` = `r`.`id` ");
		sql.append("	) AS `role_name`, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			`o`.`name` ");
		sql.append("		FROM ");
		sql.append("			`organization` AS `o` ");
		sql.append("		WHERE ");
		sql.append("			`u`.`organization_id` = `o`.`id` ");
		sql.append("	) AS `organization_name`, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			`f2`.`favorite` ");
		sql.append("		FROM ");
		sql.append("			`friend_list` AS `f2` ");
		sql.append("		WHERE ");
		sql.append("			`f2`.sponser = ? ");
		sql.append("		AND `f2`.target = `u`.`id` ");
		sql.append("	) AS `favorite`, ");
		sql.append("	`photo_id` ");
		sql.append("FROM ");
		sql.append("	`user` AS `u` ");
		sql.append("WHERE ");
		sql.append("	`u`.`id` IN ( ");
		sql.append("		SELECT ");
		sql.append("			`f`.`target` ");
		sql.append("		FROM ");
		sql.append("			`friend_list` AS `f` ");
		sql.append("		WHERE ");
		sql.append("			`f`.`sponser` = ? ");
		sql.append("	) ");
		sql.append("LIMIT ?, ?; ");
		List<Record> records = Db.find(sql.toString(), user_id, user_id, start, end);
		return records;
	}

	@Override
	public Long searchFriendCount(Long user_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS `friend_count` ");
		sql.append("FROM ");
		sql.append("	`user` AS `u` ");
		sql.append("WHERE ");
		sql.append("	`u`.`id` IN ( ");
		sql.append("		SELECT ");
		sql.append("			`f`.`target` ");
		sql.append("		FROM ");
		sql.append("			`friend_list` AS `f` ");
		sql.append("		WHERE ");
		sql.append("			`f`.`sponser` = ? ");
		sql.append("	); ");
		Long i = Db.queryLong(sql.toString(), user_id);
		return i;
	}

	@Override
	public int changeFriendStatus(Long sponser_id, Long target_id, String favorite) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `friend_list` ");
		sql.append("SET `favorite` = ? ");
		sql.append("WHERE ");
		sql.append("	`sponser` = ? ");
		sql.append("AND `target` = ?; ");
		int i = Db.update(sql.toString(), favorite, sponser_id, target_id);
		return i;
	}

	@Override
	public int deleteFriend(Long sponser_id, Long target_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	`friend_list` ");
		sql.append("WHERE ");
		sql.append("	sponser = ? ");
		sql.append("AND ");
		sql.append("	target = ? ");
		int i = Db.update(sql.toString(), sponser_id, target_id);
		return i;
	}

	@Override
	public int deleteAllFriends(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	`friend_list` ");
		sql.append("WHERE	 ");
		sql.append("	`sponser` = ? ");
		sql.append("OR `target` = ?; ");
		int i = Db.update(sql.toString(), id, id);
		return i;
	}

	@Override
	public Long searchFriendRelationCounts(Long sponser_id, Long target_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS `relation_counts` ");
		sql.append("FROM  ");
		sql.append("	`friend_list` ");
		sql.append("WHERE ");
		sql.append("	`sponser` = ? ");
		sql.append("AND `target` = ?; ");
		Long i = Db.queryLong(sql.toString(), sponser_id, target_id);
		return i;
	}

}
