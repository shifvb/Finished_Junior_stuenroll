package cn.gov.hrss.ln.stuenroll.profession;
//最新
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_ProfessionDao;

public class ProfessionService implements I_ProfessionService {
	private I_ProfessionDao i_ProfessionDao;
	
	@Override
	public List<Record> searchProfessionInYearAtDropDown(int year) {
		List<Record> list=i_ProfessionDao.searchProfessionInYearAtDropDown(year);
		return list;
	}
	@Override
	public List<Record> searchProfessionStatistics(HashMap map,long start, long length) {
		List<Record> list = new ArrayList<>();
		
		List<Record> professionNameAndOrganizationRecord = i_ProfessionDao.searchProfessionOrganization(map,start, length);
		List<Record> yearRecord = i_ProfessionDao.searchProfessionYearAmount(map,start, length);
		List<Record> classRecord = i_ProfessionDao.searchProfessionClassAmount(map,start, length);
		List<Record> studentRecordInArchive = i_ProfessionDao.searchProfessionStudentAmountInArchive(map,start, length);
		List<Record> studentRecordInEnroll = i_ProfessionDao.searchProfessionStudentAmountInEnroll(map,start, length);
		List<Record> studentGetAJobRecordInArchive = i_ProfessionDao.searchProfessionStudentAmountGetAJobInArchive(map,start, length);
		List<Record> studentGetAJobRecordInEnroll = i_ProfessionDao.searchProfessionStudentAmountGetAJobInEnroll(map,start, length);
		
		for (int i = 0; i < professionNameAndOrganizationRecord.size(); i++) {
			Record record = new Record();
			record.setColumns(professionNameAndOrganizationRecord.get(i));
			record.set("yearAmount", yearRecord.get(i).getLong("yearAmount"));
			record.set("classAmount", classRecord.get(i).getLong("classAmount"));
			double studentInArchive = studentRecordInArchive.get(i).getLong("studentAmount");
			double studentInEnroll = studentRecordInEnroll.get(i).getLong("studentAmount");
			double allStudents = studentInArchive+studentInEnroll;
			record.set("studentAmount", allStudents);
			double studentGetAJobInArchive = studentGetAJobRecordInArchive.get(i).getLong("studentAmountGetAJob");
			double studentGetAJobInEnroll = studentGetAJobRecordInEnroll.get(i).getLong("studentAmountGetAJob");
			double studentGetAJob = studentGetAJobInArchive + studentGetAJobInEnroll;
			DecimalFormat df = new DecimalFormat("0.00");
			if(allStudents==0){
				record.set("jobRate",0);
			}
			else{
				String jobRate = df.format((studentGetAJob/allStudents)*100);
				record.set("jobRate",jobRate);				
			}
			list.add(record);
		}
		return list;
	}
	@Override
	public long searchProfessionCount(HashMap map) {
		long count = i_ProfessionDao.searchProfessionCount(map);
		return count;
	}
	@Override
	public List<Record> searchAllProfession() {
		List<Record> list = i_ProfessionDao.searchAllProfession();
		return list;
	}

	@Override
	public int addProfession(HashMap map) {
		int count = i_ProfessionDao.addProfession(map);
		return count;
	}

	@Override
	public int updateProfession(HashMap map) {
		int count = i_ProfessionDao.updateProfession(map);
		return count;
	}
	
	@Before(Tx.class)
	@Override
	public int deleteProfessionById(Long[] id) {
		int count = i_ProfessionDao.deleteProfessionById(id);
		return count;
	}
	
	@Override
	public List<Record> searchProfessionsWithConditions(Map<String, Object> map, Long start, Long length) {
		List<Record> records = i_ProfessionDao.searchProfessionsWithConditions(map, start, length);
		return records;
	}

	
	public I_ProfessionDao getI_ProfessionDao() {
		return i_ProfessionDao;
	}

	public void setI_ProfessionDao(I_ProfessionDao i_ProfessionDao) {
		this.i_ProfessionDao = i_ProfessionDao;
	}
	@Override
	public void importProfession(Object... objects) {
		if (objects.length == 2){
			i_ProfessionDao.importProfession(objects);
		}
		
	}
}
