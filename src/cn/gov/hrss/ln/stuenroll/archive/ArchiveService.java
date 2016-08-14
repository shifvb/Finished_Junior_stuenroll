package cn.gov.hrss.ln.stuenroll.archive;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_ArchiveDao;

public class ArchiveService implements I_ArchiveService{
	private I_ArchiveDao i_ArchiveDao;

	@Override
	public List<Record> searchArchive(HashMap map, long start, long length) {
		List<Record> list = i_ArchiveDao.searchArchive(map, start, length);
		return list;
	}

	@Override
	public long searchArchiveCount(HashMap map) {
		long count = i_ArchiveDao.searchArchiveCount(map);
		return count;
	}

	@Override
	public int addArchive(HashMap map) {
		int count = i_ArchiveDao.addArchive(map);
		return count;
	}

	@Override
	public int updateArchive(HashMap map) {
		int count = i_ArchiveDao.updateArchive(map);
		return count;
	}

	@Override
	public int deleteArchiveById(Long[] id) {
		int count = i_ArchiveDao.deleteArchiveById(id);
		return count;
	}
	@Override
	public boolean isEnrollEligible(long pid) {
		boolean bool = i_ArchiveDao.isEnrollEligible(pid);
		return bool;
	}

	@Override
	public List<Record> searchStudentState() {
		List<Record> list = i_ArchiveDao.searchStudentState();
		return list;
	}
	@Override
	public List<Record> searchClassInfo() {
		List<Record> list = i_ArchiveDao.searchClassInfo();
		return list;
	}
	public I_ArchiveDao getI_ArchiveDao() {
		return i_ArchiveDao;
	}

	public void setI_ArchiveDao(I_ArchiveDao i_ArchiveDao) {
		this.i_ArchiveDao = i_ArchiveDao;
	}

}
