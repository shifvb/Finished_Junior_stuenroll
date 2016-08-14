package cn.gov.hrss.ln.stuenroll.organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_OrganizationTransformDao;
import cn.gov.hrss.ln.stuenroll.db.I_UserLogDao;

/**
 * 机构参与转换业务模块实现
 * 
 * @author LiYaDong
 * @version 2.1
 */

public class OrganizationTransformService implements I_OrganizationTransformService {

	private I_OrganizationTransformDao i_OrganizationTransformDao;
	private I_UserLogDao i_UserLogDao;

	@Override
	public List<Record> searchOrganizations(Map<String, Object> map, long start, long length) {
		List<Record> records = new ArrayList<Record>();
		// 拼接记录
		List<Record> organsRecords = i_OrganizationTransformDao.searchOrganizations(map, start, length);
		List<Record> professionsAmount = i_OrganizationTransformDao.searchProfessionsAmount(map, start, length);
		List<Record> classAmount = i_OrganizationTransformDao.searchClassAmount(map, start, length);
		List<Record> archiveStudentsAmount = i_OrganizationTransformDao.searchArchiveStudentsAmount(map, start, length);
		List<Record> unarchiveStudentsAmount = i_OrganizationTransformDao.searchUnarchiveStudentsAmount(map, start, length);
		map.put("stateId", 4);
		List<Record> archiveQuitStudentsAmount = i_OrganizationTransformDao.searchArchiveStudentsAmount(map, start, length);
		List<Record> unarchiveQuitStudentsAmount = i_OrganizationTransformDao.searchUnarchiveStudentsAmount(map, start, length);
		map.replace("stateId", 5);
		List<Record> archiveEmployedStudentsAmount = i_OrganizationTransformDao.searchArchiveStudentsAmount(map, start, length);
		List<Record> unarchiveEmployedStudentsAmount = i_OrganizationTransformDao.searchUnarchiveStudentsAmount(map, start, length);
		String organizationId = "";
		Integer year = 0;
		Long count = 0L;
		Long quitCount = 0L;
		Long employedCount = 0L;
		for (Record r : organsRecords) {
			Record record = new Record();
			// 拼接记录
			record.setColumns(r);
			organizationId = r.getStr("id");
			year = r.getInt("year");
			count = 0L;
			quitCount = 0L;
			employedCount = 0L;
			if (professionsAmount.size() == 0) {
				record.set("professionAmount", 0L);
			} else {
				for (Record r1 : professionsAmount) {
					if (organizationId.equals(r1.getStr("id"))  && year.equals(r1.getInt("year"))) {
						record.set("professionAmount", r1.getLong("professionAmount"));
						break;
					}
					record.set("professionAmount", 0L);
				}
			}
			if (classAmount.size() == 0) {
				record.set("classAmount", 0L);
			} else {
				for (Record r2 : classAmount) {
					if (organizationId.equals(r2.getStr("id")) && year.equals(r2.getInt("year"))) {
						record.set("classAmount", r2.getLong("classAmount"));
						break;
					}
					record.set("classAmount", 0L);
				}
			}
			if (archiveStudentsAmount.size() == 0) {
				count += 0;
			} else {
				for (Record r3 : archiveStudentsAmount) {
					if (organizationId.equals(r3.getStr("id")) && year.equals(r3.getInt("year"))) {
						count += r3.getLong("studentsAmount");
						break;
					}
				}
			}
			if (unarchiveStudentsAmount.size() == 0) {
				count += 0;
			} else {
				for (Record r4 : unarchiveStudentsAmount) {
					if (organizationId.equals(r4.getStr("id")) && year.equals(r4.getInt("year"))) {
						count += r4.getLong("studentsAmount");
						break;
					}
				}
			}
			record.set("studentsAmount", count);
			if (archiveQuitStudentsAmount.size() == 0) {
				quitCount += 0;
			} else {
				for (Record r5 : archiveQuitStudentsAmount) {
					if (organizationId.equals(r5.getStr("id")) && year.equals(r5.getInt("year"))) {
						quitCount += r5.getLong("studentsAmount");
						break;
					}
				}
			}
			if (unarchiveQuitStudentsAmount.size() == 0) {
				quitCount += 0;
			} else {
				for (Record r6 : unarchiveQuitStudentsAmount) {
					if (organizationId.equals(r6.getStr("id")) && year.equals(r6.getInt("year"))) {
						quitCount += r6.getLong("studentsAmount");
						break;
					}
				}
			}
			record.set("quitStudentsAmount", quitCount);
			if (archiveEmployedStudentsAmount.size() == 0) {
				employedCount += 0;
			} else {
				for (Record r7 : archiveEmployedStudentsAmount) {
					if (organizationId.equals(r7.getStr("id")) && year.equals(r7.getInt("year"))) {
						employedCount += r7.getLong("studentsAmount");
						break;
					}
				}
			}
			if (unarchiveEmployedStudentsAmount.size() == 0) {
				employedCount += 0;
			} else {
				for (Record r8 : unarchiveEmployedStudentsAmount) {
					if (organizationId.equals(r8.getStr("id")) && year.equals(r8.getInt("year"))) {
						employedCount += r8.getLong("studentsAmount");
						break;
					}
				}
			}
			record.set("employedAmount", employedCount);
			records.add(record);
		}
		return records;
	}

	@Override
	public long searchOrganizationsAmount(Map<String, Object> map) {
		long count = i_OrganizationTransformDao.searchOrganizationsAmount(map);
		return count;
	}

	@Override
	public List<Record> searchProfessionsAmount(Map<String, Object> map, long start, long length) {
		List<Record> records = i_OrganizationTransformDao.searchProfessionsAmount(map, start, length);
		return records;
	}

	@Override
	public List<Record> searchClassAmount(Map<String, Object> map, long start, long length) {
		List<Record> records = i_OrganizationTransformDao.searchClassAmount(map, start, length);
		return records;
	}

	@Override
	public List<Record> searchArchiveStudentsAmount(Map<String, Object> map, long start, long length) {
		List<Record> records = i_OrganizationTransformDao.searchArchiveStudentsAmount(map, start, length);
		return records;
	}
	
	@Override
	public List<Record> searchUnarchiveStudentsAmount(Map<String, Object> map, long start, long length) {
		List<Record> records = i_OrganizationTransformDao.searchUnarchiveStudentsAmount(map, start, length);
		return records;
	}

	@Before(Tx.class)
	@Override
	public int updateOrganization(Map<String, Object> map) {
		int count = i_OrganizationTransformDao.updateOrganization(map);
		return count;
	}

	@Before(Tx.class)
	@Override
	public long deleteOrganizations(Long[] id, Integer year) {
		long count = i_OrganizationTransformDao.deleteOrganizations(id, year);
		return count;
	}
	
	@Before(Tx.class)
	@Override
	public long deleteOrganizations(Integer[] year, Long[] id) {
		if (year == null || id == null) {
			return 0;
		}
		if (year.length != id.length) {
			return 0;
		}
		long count = 0L;
		for (int i = 0; i < id.length; i++) {
			count += i_OrganizationTransformDao.deleteOrganization(id[i], year[i]);
		}
		return count;
	}

	@Before(Tx.class)
	public long deleteOrganizations(List<Integer> year, Map<Integer, List<Long>> ids) {
		if (year.size() != ids.size()) {
			return 0;
		}
		long count = 0;
		List<Long> id = null;
		for (Integer y : year) {
			id = ids.get(y);
			count += i_OrganizationTransformDao.deleteOrganizations((Long[]) id.toArray(), y);
		}
		return count;
	}

	@Before(Tx.class)
	@Override
	public int addOrganizationProfession(Long organizationId, Long[] professionId, Integer year) {
		int count = 0;
		for (Long profession : professionId) {
			count += i_OrganizationTransformDao.addOrganizationProfession(organizationId, profession, year);
		}
		return count;
	}

	@Before(Tx.class)
	@Override
	public int deleteOrganizationProfession(Long organizationId, Long[] professionId, Integer year) {
		int count = 0;
		for (Long profession : professionId) {
			count += i_OrganizationTransformDao.deleteOrganizationProfession(organizationId, profession, year);
		}
		return count;
	}

	@Override
	public List<Record> searchOrganizationsJoinWithProfessionByYearAtDropdown(Map<String, Object> map) {
		List<Record> records = i_OrganizationTransformDao.searchOrganizationsJoinWithProfessionByYearAtDropdown(map);
		return records;
	}

	public I_OrganizationTransformDao getI_OrganizationTransformDao() {
		return i_OrganizationTransformDao;
	}

	public void setI_OrganizationTransformDao(I_OrganizationTransformDao i_OrganizationTransformDao) {
		this.i_OrganizationTransformDao = i_OrganizationTransformDao;
	}

	public I_UserLogDao getI_UserLogDao() {
		return i_UserLogDao;
	}

	public void setI_UserLogDao(I_UserLogDao i_UserLogDao) {
		this.i_UserLogDao = i_UserLogDao;
	}
}
