package cn.gov.hrss.ln.stuenroll.role;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_RoleDao;

public class RoleService implements I_RoleSevice {
	private I_RoleDao i_RoleDao;

	@Override
	public List<Record> searchAllFromRole(long start, long length) {
		List<Record> records = i_RoleDao.searchAllFromRole(start, length);
		return records;
	}

	@Override
	public Long searchOrganizationUserCountByRoleId(Long role_id) {
		Long i = i_RoleDao.searchOrganizationUserCountByRoleId(role_id);
		return i;
	}

	@Override
	public Long searchEmploymentUserCountByRoleId(Long role_id) {
		Long i = i_RoleDao.searchEmploymentUserCountByRoleId(role_id);
		return i;
	}

	@Override
	public Long searchPermissionCountByRoleId(Long role_id) {
		Long i = i_RoleDao.searchPermissionCountByRoleId(role_id);
		return i;
	}

	@Override
	public Long searchInvalidUserCountByRoleId(Long role_id) {
		Long i = i_RoleDao.searchInvalidUserCountByRoleId(role_id);
		return i;
	}

	@Override
	public Long searchValidUserCountByRoleId(Long role_id) {
		Long i = i_RoleDao.searchValidUserCountByRoleId(role_id);
		return i;
	}

	@Override
	public Long searchUserCountByRoleId(Long role_id) {
		Long i = i_RoleDao.searchUserCountByRoleId(role_id);
		return i;
	}

	public I_RoleDao getI_RoleDao() {
		return i_RoleDao;
	}

	public void setI_RoleDao(I_RoleDao i_RoleDao) {
		this.i_RoleDao = i_RoleDao;
	}

	@Override
	public Long searchRoleCount() {
		Long i = i_RoleDao.searchRoleCount();
		return i;
	}

	@Override
	public List<Record> searchAllPermissions() {
		List<Record> records = i_RoleDao.searchAllPermissions();
		return records;
	}

	@Override
	public int addRole(String roleName) {
		int i = i_RoleDao.addRole(roleName);
		return i;
	}

	@Override
	public Long searchRoleIdByRoleName(String role_name) {
		Long i = i_RoleDao.searchRoleIdByRoleName(role_name);
		return i;
	}

	@Override
	public int setRolePermissionByRoleId(Long role_id, String permission) {
		int i = i_RoleDao.setRolePermissionByRoleId(role_id, permission);
		return i;
	}

	@Override
	public int changeRoleStatus(Long role_id, boolean bool) {
		int i = i_RoleDao.changeRoleStatus(role_id, bool);
		return i;
	}

	@Override
	public List<Record> searchRolePermissionByRoleId(Long role_id) {
		List<Record> records = i_RoleDao.searchRolePermissionByRoleId(role_id);
		return records;
	}

	@Override
	public int deleteRolePermissionByRoleId(Long role_id, String permission) {
		int i = i_RoleDao.deleteRolePermissionByRoleId(role_id, permission);
		return i;
	}

	@Override
	public int updateRoleNameByRoleId(Long role_id, String role_name) {
		int i = i_RoleDao.updateRoleNameByRoleId(role_id, role_name);
		return i;
	}

	@Override
	public int deletePermissionsByRoleId(Long role_id) {
		int i = i_RoleDao.deletePermissionsByRoleId(role_id);
		return i;
	}

	@Override
	public int deleteRoleByRoleId(Long role_id) {
		int i = i_RoleDao.deleteRoleByRoleId(role_id);
		return i;
	}

}
