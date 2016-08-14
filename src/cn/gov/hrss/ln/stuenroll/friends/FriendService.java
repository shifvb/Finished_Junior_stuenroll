package cn.gov.hrss.ln.stuenroll.friends;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_FriendDao;
import cn.gov.hrss.ln.stuenroll.db.I_UserLogDao;

public class FriendService implements I_FriendService {
	private I_FriendDao i_FriendDao;
	private I_UserLogDao i_UserLogDao;

	@Override
	public Record searchUserByUsername(String username) {
		Record record = i_FriendDao.searchUserByUsername(username);
		return record;
	}

	@Override
	public int addUserToFriendListByUserId(String sponser, String target, String favorite) {
		int i = i_FriendDao.addUserToFriendListByUserId(sponser, target, favorite);
		return i;
	}

	@Override
	public List<Record> searchFriend(Long user_id, Long start, Long end) {
		List<Record> records = i_FriendDao.searchFriend(user_id, start, end);
		return records;
	}

	@Override
	public Long searchFriendCount(Long user_id) {
		Long i = i_FriendDao.searchFriendCount(user_id);
		return i;
	}

	@Override
	public int changeFriendStatus(Long sponser_id, Long target_id, String favorite) {
		int i = i_FriendDao.changeFriendStatus(sponser_id, target_id, favorite);
		return i;
	}

	@Override
	public int deleteFriend(Long sponser_id, Long target_id) {
		int i = i_FriendDao.deleteFriend(sponser_id, target_id);
		return i;
	}

	@Override
	public int deleteAllFriends(String id) {
		int i = i_FriendDao.deleteAllFriends(id);
		return i;
	}

	@Override
	public Long searchFriendRelationCounts(Long sponser_id, Long target_id) {
		Long i = i_FriendDao.searchFriendRelationCounts(sponser_id, target_id);
		return i;
	}

	@Override
	public Record searchUserLogTimeAndType(String user_id) {
		Record record = i_UserLogDao.searchUserLogTimeAndType(user_id);
		return record;
	}

	public I_FriendDao getI_FriendDao() {
		return i_FriendDao;
	}

	public void setI_FriendDao(I_FriendDao i_FriendDao) {
		this.i_FriendDao = i_FriendDao;
	}

	public I_UserLogDao getI_UserLogDao() {
		return i_UserLogDao;
	}

	public void setI_UserLogDao(I_UserLogDao i_UserLogDao) {
		this.i_UserLogDao = i_UserLogDao;
	}

}
