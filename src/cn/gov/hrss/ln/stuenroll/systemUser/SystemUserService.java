package cn.gov.hrss.ln.stuenroll.systemUser;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_SystemUserDao;

public class SystemUserService implements I_SystemUserService {
	private I_SystemUserDao i_SystemUserDao;

	@Override
	public List<Record> searchUser(Long start, Long length) {
		List<Record> records = i_SystemUserDao.searchUser(start, length);
		return records;
	}

	@Override
	public Long searchUserCount() {
		Long i = i_SystemUserDao.searchUserCount();
		return i;
	}

	@Override
	public int changeUserStatus(Long user_id, boolean block) {
		int i = i_SystemUserDao.changeUserStatus(user_id, block);
		return i;
	}

	@Override
	public int addUser(List params) {
		int i = i_SystemUserDao.addUser(params);
		return i;
	}

	@Override
	public List<Record> searchAllOrganizationIdAndNames() {
		List<Record> records = i_SystemUserDao.searchAllOrganizationIdAndNames();
		return records;
	}

	@Override
	public List<Record> searchAllRoleIdAndNames() {
		List<Record> records = i_SystemUserDao.searchAllRoleIdAndNames();
		return records;
	}

	@Override
	public Record searchUserById(Long user_id) {
		Record record = i_SystemUserDao.searchUserById(user_id);
		return record;
	}

	@Override
	public int modifyUserById(Long user_id, Map params) {
		int i = i_SystemUserDao.modifyUserById(user_id, params);
		return i;
	}

	@Override
	public int deleteUserById(Long user_id) {
		int i = i_SystemUserDao.deleteUserById(user_id);
		return i;
	}

	@Override
	public List<Record> searchAllUser() {
		List<Record> records = i_SystemUserDao.searchAllUser();
		return records;
	}

	@Override
	public int deleteAllUser() {
		int i = i_SystemUserDao.deleteAllUser();
		return i;
	}

	@Override
	public int importAllUser(List params) {
		int i = i_SystemUserDao.importAllUser(params);
		return i;
	}

	public I_SystemUserDao getI_SystemUserDao() {
		return i_SystemUserDao;
	}

	public void setI_SystemUserDao(I_SystemUserDao i_SystemUserDao) {
		this.i_SystemUserDao = i_SystemUserDao;
	}

}
