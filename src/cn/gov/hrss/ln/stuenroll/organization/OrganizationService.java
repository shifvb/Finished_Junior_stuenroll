package cn.gov.hrss.ln.stuenroll.organization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_OrganizationDao;
import cn.gov.hrss.ln.stuenroll.db.I_UserLogDao;

/**
 * 机构管理业务模块实现
 * 
 * @author LiYaDong
 * @version 2.1
 *
 */
public class OrganizationService implements I_OrganizationService {

	private I_OrganizationDao i_OrganizationDao;
	private I_UserLogDao i_UserLogDao;

	@Override
	public List<Record> searchOrganizations(Map<String, Object> map, long start, long length) {
		List<Record> records = new ArrayList<>();

		List<Record> organizationRecords = i_OrganizationDao.searchOrganizations(map, start, length);
		List<Record> professionRecords = i_OrganizationDao.searchOrganizatonOnProfessionAmount(map, start, length);
		List<Record> classRecords = i_OrganizationDao.searchOrganizationOnClassAmount(map, start, length);
		List<Record> archiveRecords = i_OrganizationDao.searchOrganizationOnArchiveAmount(map, start, length);
		List<Record> enrollRecords = i_OrganizationDao.searchOrganizationOnEnrollAmount(map, start, length);

		// 拼接record成一个完整的record
		String organizationId = "";
		Long count = 0L;
		for (Record r : organizationRecords) {
			Record record = new Record();
			record.setColumns(r);
			organizationId = r.getStr("id");
			count = 0L;
			for (Record r1 : professionRecords) {
				if (organizationId.equals(r1.getStr("id"))) {
					record.set("professionAmount", r1.getLong("professionAmount"));
				}
				else {
					record.set("professionAmount", 0L);
				}
			}
			for (Record r2 : classRecords) {
				if (organizationId.equals(r2.getStr("id"))) {
					record.set("classAmount", r2.getLong("classAmount"));
				}
				else {
					record.set("classAmount", 0L);
				}
			}
			for (Record r3 : archiveRecords) {
				if (organizationId.equals(r3.getStr("id"))) {
					count += r3.getLong("students");
				}
			}
			for (Record r4 : enrollRecords) {
				if (organizationId.equals(r4.getStr("id"))) {
					count += r4.getLong("students");
				}
			}
			record.set("students", count);
			records.add(record);
		}
		return records;
	}

	@Override
	public long searchOrganizationAmount(Map<String, Object> map) {
		long count = i_OrganizationDao.searchOrganizationAmount(map);
		return count;
	}

	@Before(Tx.class)
	@Override
	public int addOrganization(Map<String, Object> map) {
		int count = i_OrganizationDao.addOrganization(map);
		return count;
	}

	@Before(Tx.class)
	@Override
	public int updateOrganization(long organizationId, Map<String, Object> map) {
		int count = i_OrganizationDao.updateOrganization(organizationId, map);
		return count;
	}

	@Before(Tx.class)
	@Override
	public long deleteOrganizationsById(Long[] id) {
		long count = i_OrganizationDao.deleteOrganizationsById(id);
		return count;
	}

	@Before(Tx.class)
	@Override
	public long setOrganizationTransformInfo(Long[] id, int year, int block) {
		List<Record> records = i_OrganizationDao.getTransformOrganizations();
		List<Long> list = new ArrayList<Long>();
		List<Map<String, Long>> lists = new ArrayList<>();
		Long ids = null;
		int years = 0;
		Boolean blocks = false;
		Boolean blockss = block == 0 ? false : true;
		boolean flag = true;
		long count = 0L;
		for (int i = 0; i < id.length; i++) {
			flag = true;
			for (Record record : records) {
				ids = record.getLong("organization_id");
				years = record.getInt("year");
				blocks = record.getBoolean("block");
				if (ids.equals(id[i]) && years == year) {
					if (blocks.booleanValue() == blockss.booleanValue()) {
						flag = false;
						break;
					}
					else {
						Map<String, Long> map = new HashMap<>();
						map.put("id", record.getLong("id"));
						map.put("organizationId", id[i]);
						lists.add(map);
						flag = false;
						break;
					}
				}
				else {
					continue;
				}
			}
			if (flag) {
				list.add(id[i]);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			count += i_OrganizationDao.insertOrganizationTransformInfo(list.get(i), year, block);
		}
		for (int i = 0; i < lists.size(); i++) {
			count += i_OrganizationDao.updateOrganizationTransformInfo(lists.get(i).get("id"), lists.get(i).get("organizationId"), year,
					block);
		}
		return count;
	}

	public I_OrganizationDao getI_OrganizationDao() {
		return i_OrganizationDao;
	}

	public void setI_OrganizationDao(I_OrganizationDao i_OrganizationDao) {
		this.i_OrganizationDao = i_OrganizationDao;
	}

	public I_UserLogDao getI_UserLogDao() {
		return i_UserLogDao;
	}

	public void setI_UserLogDao(I_UserLogDao i_UserLogDao) {
		this.i_UserLogDao = i_UserLogDao;
	}
		
	@Override
	public List<Record> searchOrganization() {
		List<Record>  record =i_OrganizationDao.searchOrganization();
		return record;
	}
}
