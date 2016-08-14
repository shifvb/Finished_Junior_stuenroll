package cn.gov.hrss.ln.stuenroll.classmanagement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_ClassInfoDao;
import cn.gov.hrss.ln.stuenroll.db.I_ReviewDao;
import cn.gov.hrss.ln.stuenroll.db.I_UserLogDao;
import cn.gov.hrss.ln.stuenroll.tools.UserOperation;
import cn.gov.hrss.ln.stuenroll.tools.UserOperationType;

/**
 * 班级管理业务模块实现
 * 
 * @author LiYadong
 * @version 2.0
 */

public class ClassInfoService implements I_ClassInfoService {

	private I_ClassInfoDao i_ClassInfoDao;
	private I_UserLogDao i_UserLogDao;
	private I_ReviewDao i_ReviewDao;

	@Override
	public List<Record> searchAllClasses(Map<String, Object> map) {
		List<Record> records = i_ClassInfoDao.searchAllClasses(map);
		return records;
	}

	@Override
	public List<Record> searchAllClassInfo(Map<String, Object> map, Long start, Long length) {
		// TODO user_log
		List<Record> records = new ArrayList<Record>();
		if (map.containsKey("classState")) {
			map.remove("classState");
		}
		List<Record> archiveRecords = this.searchArchiveClassInfo(map, start, length);
		List<Record> unarchiveRecords = this.searchUnarchiveClassInfo(map, start, length);
		records.addAll(unarchiveRecords);
		records.addAll(archiveRecords);
		return records;
	}

	@Override
	public long searchAllClassAmount(Map<String, Object> map) {
		// TODO user_log
		if (map.containsKey("classState")) {
			map.remove("classState");
		}
		return this.searchArchiveClassAmount(map) + this.searchUnarchiveClassAmount(map);
	}

	@Override
	public List<Record> searchArchiveClassInfo(Map<String, Object> map, Long start, Long length) {
		// TODO user_log
		List<Record> records = new ArrayList<Record>();
		map.put("classState", 2);
		List<Record> basicClassInfo = i_ClassInfoDao.searchClassInfo(map, start, length);
		List<Record> archiveStudentsAmountWithState = i_ClassInfoDao.searchArchiveClassStudentsAmountWithState(map, start, length);
		if (map.containsKey("stuState")) {
			map.remove("stuState");
		}
		List<Record> archiveClassStudentsAmount = i_ClassInfoDao.searchArchiveClassStudentsAmountWithState(map, start, length);
		Long studentsAmount = 0L;
		Long statedStudentsAmount = 0L;
		String classInfoId = "";
		DecimalFormat df = new DecimalFormat("0.00000");
		boolean flag = archiveStudentsAmountWithState.size() == 0;
		for (int i = 0; i < basicClassInfo.size(); i++) {
			studentsAmount = 0L;
			statedStudentsAmount = 0L;
			classInfoId = basicClassInfo.get(i).getStr("id");
			Record record = new Record();
			record.setColumns(basicClassInfo.get(i));
			for (int j = 0; j < archiveClassStudentsAmount.size(); j++) {
				if (classInfoId.equals(archiveClassStudentsAmount.get(j).getStr("id"))) {
					studentsAmount = archiveClassStudentsAmount.get(j).getLong("archiveStatedStudents");
				}
				record.set("studentsAmount", studentsAmount);
			}
			if (flag) {
				record.set("statedStudentsAmount", statedStudentsAmount);
			}
			else {
				for (int k = 0; k < archiveStudentsAmountWithState.size(); k++) {
					if (classInfoId.equals(archiveStudentsAmountWithState.get(k).getStr("id"))) {
						statedStudentsAmount = archiveStudentsAmountWithState.get(k).getLong("archiveStatedStudents");
					}
					record.set("statedStudentsAmount", statedStudentsAmount);
				}
			}
			if (studentsAmount.equals(0L)) {
				record.set("rate", 0);
			}
			else {
				record.set("rate", df.format((statedStudentsAmount * 1.0) / (studentsAmount * 1.0)));
			}
			records.add(record);
		}

		return records;
	}

	@Override
	public List<Record> searchUnarchiveClassInfo(Map<String, Object> map, Long start, Long length) {
		// TODO user_log
		List<Record> records = new ArrayList<Record>();
		map.put("classState", 1);
		List<Record> basicClassInfo = i_ClassInfoDao.searchClassInfo(map, start, length);
		List<Record> unarchiveStudentsAmountWithState = i_ClassInfoDao.searchUnarchiveClassStudentsAmountWithState(map, start, length);
		if (map.containsKey("stuState")) {
			map.remove("stuState");
		}
		List<Record> unarchiveClassStudentsAmount = i_ClassInfoDao.searchUnarchiveClassStudentsAmountWithState(map, start, length);
		Long studentsAmount = 0L;
		Long statedStudentsAmount = 0L;
		String classInfoId = "";
		DecimalFormat df = new DecimalFormat("0.00000");
		boolean flag = unarchiveStudentsAmountWithState.size() == 0;
		for (int i = 0; i < basicClassInfo.size(); i++) {
			studentsAmount = 0L;
			statedStudentsAmount = 0L;
			classInfoId = basicClassInfo.get(i).getStr("id");
			Record record = new Record();
			record.setColumns(basicClassInfo.get(i));
			for (int j = 0; j < unarchiveClassStudentsAmount.size(); j++) {
				if (classInfoId.equals(unarchiveClassStudentsAmount.get(j).getStr("id"))) {
					studentsAmount = unarchiveClassStudentsAmount.get(j).getLong("unarchiveStatedStudents");
				}
				record.set("studentsAmount", studentsAmount);
			}
			if (flag) {
				record.set("statedStudentsAmount", studentsAmount);
			}
			else {
				for (int k = 0; k < unarchiveStudentsAmountWithState.size(); k++) {
					if (classInfoId.equals(unarchiveStudentsAmountWithState.get(k).getStr("id"))) {
						statedStudentsAmount = unarchiveStudentsAmountWithState.get(k).getLong("unarchiveStatedStudents");
					}
					record.set("statedStudentsAmount", statedStudentsAmount);
				}
			}
			if (studentsAmount.equals(0L)) {
				record.set("rate", 0);
			}
			else {
				record.set("rate", df.format((statedStudentsAmount * 1.0) / (studentsAmount * 1.0)));
			}
			records.add(record);
		}
		return records;
	}

	@Override
	public long searchArchiveClassAmount(Map<String, Object> map) {
		// TODO user_log
		map.put("classState", 2);
		long count = i_ClassInfoDao.searchClassAmountWithState(map);
		return count;
	}

	@Override
	public long searchUnarchiveClassAmount(Map<String, Object> map) {
		// TODO user_log
		map.put("classState", 1);
		long count = i_ClassInfoDao.searchClassAmountWithState(map);
		return count;
	}

	@Before(Tx.class)
	@Override
	public int addClass(Map<String, Object> map) {
		int count = i_ClassInfoDao.addClass(map);
		// TODO user_log
		return count;
	}

	@Before(Tx.class)
	@Override
	public int updateClassInfo(Map<String, Object> map) {
		int count = i_ClassInfoDao.updateClassInfo(map);
		// TODO user_log
		return count;
	}

	@Before(Tx.class)
	@Override
	public long deleteClasses(Long[] id, String username) {
		long count = i_ClassInfoDao.deleteClasses(id);
		i_UserLogDao.save(username, UserOperation.删除班级.toString(), UserOperationType.业务操作.toString());
		return count;
	}

	@Before(Tx.class)
	@Override
	public boolean archiveStudentsInClass(Long classId, String username) {
		List<Record> records = i_ClassInfoDao.getUnarchiveStudentsInClass(classId);
		long result = i_ClassInfoDao.archiveStudentsInClass(records);
		Map<String, Object> map = new HashMap<>();
		map.put("classId", classId);
		map.put("classState", 2);
		i_ClassInfoDao.updateClassInfo(map);
		boolean flag = (result == records.size());
		if (flag) {
			i_UserLogDao.save(username, UserOperation.班级归档.toString(), UserOperationType.业务操作.toString());
		}
		return flag;
	}

	@Override
	public List<Record> getUnarchiveStudentsInClass(Long classId) {
		List<Record> records = i_ClassInfoDao.getUnarchiveStudentsInClass(classId);
		return records;
	}

	@Override
	public List<Record> getArchiveStudentsInClass(Long classId) {
		List<Record> records = i_ClassInfoDao.getArchiveStudentsInClass(classId);
		return records;
	}

	@Override
	public List<Record> getUnarchiveStudentsInClass(Long classId, Long start, Long length) {
		List<Record> records = i_ClassInfoDao.getUnarchiveStudentsInClass(classId, start, length);
		return records;
	}

	@Override
	public List<Record> getArchiveStudentsInClass(Long classId, Long start, Long length) {
		List<Record> records = i_ClassInfoDao.getArchiveStudentsInClass(classId, start, length);
		return records;
	}

	@Override
	public long archiveClasses(Long[] classId, String username) {
		boolean flag = false;
		long count = 0L;
		for (Long id : classId) {
			flag = this.archiveStudentsInClass(id, username);
			if (flag) {
				count++;
			}
		}
		return count;
	}

	@Override
	public List<Record> searchCallbackInfo(Map<String, Object> map, Long start, Long length) {
		List<Record> records = new ArrayList<>();
		List<Record> classInfoRecords = this.searchAllClassInfo(map, start, length);
		List<Record> reviewStudents = i_ReviewDao.searchReviewPeopleAmountInClass(map, start, length);
		List<Record> allReviewCount = i_ReviewDao.searchReviewCountsByClass(map, start, length);
		map.put("reviewResult", 0);
		List<Record> qualifiedReviewCount = i_ReviewDao.searchReviewCountsByClass(map, start, length);
		String classInfoId = "";
		DecimalFormat df = new DecimalFormat("0.00000");
		for (Record record : classInfoRecords) {
			Record r = new Record();
			classInfoId = record.getStr("id");
			r.setColumns(record);
			if (reviewStudents.size() == 0) {
				r.set("reviewCount", 0L);
				r.set("reviewRate", 0.0);
			}
			else {
				for (Record r1 : reviewStudents) {
					Long studentsAmount = record.getLong("studentsAmount");
					if (classInfoId.equals(r1.getStr("id")) && studentsAmount > 0) {
						Long reviewCount = r1.getLong("reviewAmount");
						r.set("reviewCount", reviewCount);
						r.set("reviewRate", df.format(reviewCount * 1.0 / (studentsAmount * 1.0)));
					}
					else {
						r.set("reviewCount", 0L);
						r.set("reviewRate", 0.0);
					}
				}
			}
			if (qualifiedReviewCount.size() == 0) {
				r.set("qualifiedRate", 0.0);
			}
			else {
				for (int i = 0; i < qualifiedReviewCount.size(); i++) {
					Long allCounts = allReviewCount.get(i).getLong("reviewCount");
					if (classInfoId.equals(qualifiedReviewCount.get(i).getStr("id"))) {
						Long qualifiedCounts = qualifiedReviewCount.get(i).getLong("reviewCount");
						if (allCounts != null && allCounts.longValue() > 0 && qualifiedCounts != null) {
							r.set("qualifiedRate", df.format(qualifiedCounts * 1.0 / (allCounts * 1.0)));
						}
						else {
							r.set("qualifiedRate", 0.0);
						}
					}
				}
			}
			records.add(r);
		}
		return records;
	}

	@Override
	public long searchCallbackCount(Map<String, Object> map) {
		return this.searchAllClassAmount(map);
	}

	@Override
	public Record chooseStudentInClassForReview(Long classInfoId) {
		// Auto-generated method stub
		List<Record> archiveRecords = i_ClassInfoDao.getArchiveStudentsInClass(classInfoId);
		List<Record> unarchiveRecords = i_ClassInfoDao.getUnarchiveStudentsInClass(classInfoId);
		Record record = null;
		if (archiveRecords != null && archiveRecords.size() > 0) {
			int size = archiveRecords.size();
			int index = (int) (Math.random() * (size - 1));
			record = archiveRecords.get(index);
		}
		else if (unarchiveRecords != null && unarchiveRecords.size() > 0) {
			int size = unarchiveRecords.size();
			int index = (int) (Math.random() * (size - 1));
			record = unarchiveRecords.get(index);
		}
		return record;
	}
	
	@Override
	public long getUnarchiveStudentsAmountInClass(Long classId) {
		long count = i_ClassInfoDao.getUnarchiveStudentsAmountInClass(classId);
		return count;
	}
	
	@Override
	public long getArchiveStudentsAmountInClass(Long classId) {
		long count = i_ClassInfoDao.getArchiveStudentsAmountInClass(classId);
		return count;
	}

	public I_ClassInfoDao getI_ClassInfoDao() {
		return i_ClassInfoDao;
	}

	public void setI_ClassInfoDao(I_ClassInfoDao i_ClassInfoDao) {
		this.i_ClassInfoDao = i_ClassInfoDao;
	}

	public I_UserLogDao getI_UserLogDao() {
		return i_UserLogDao;
	}

	public void setI_UserLogDao(I_UserLogDao i_UserLogDao) {
		this.i_UserLogDao = i_UserLogDao;
	}

	public I_ReviewDao getI_ReviewDao() {
		return i_ReviewDao;
	}

	public void setI_ReviewDao(I_ReviewDao i_ReviewDao) {
		this.i_ReviewDao = i_ReviewDao;
	}
}
