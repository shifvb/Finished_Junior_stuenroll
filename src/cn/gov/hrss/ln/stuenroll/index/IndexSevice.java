package cn.gov.hrss.ln.stuenroll.index;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_IndexDao;

public class IndexSevice implements I_IndexSevice {
private I_IndexDao  i_IndexDao;
	@Override
	public List<Record> searchHotMajor(long oid) {
		List<Record> records =i_IndexDao.searchHotMajor(oid);
		List<Record> records2 =i_IndexDao.searchHotMajorArchive(oid);
		ArrayList result = (ArrayList) enroll_archive(records, records2,"name");
		if(result.size()<5)
			return result;
		else
		    return result.subList(0, 5);
	}
	public I_IndexDao getI_IndexDao() {
		return i_IndexDao;
	}
	public void setI_IndexDao(I_IndexDao i_IndexDao) {
		this.i_IndexDao = i_IndexDao;
	}
	@Override
	public List<Record> searchHotPlace(long oid) {
		List<Record> records =i_IndexDao.searchHotPlace(oid);
		List<Record> records2 =i_IndexDao.searchHotPlaceArchive(oid);
		ArrayList result = (ArrayList) enroll_archive(records, records2,"place");
		if(result.size()<5)
			return result;
		else
		    return result.subList(0, 5);
	}
	@Override
	public List<Record> searchHotEducation(long oid) {
		List<Record> records =i_IndexDao.searchHotEducation(oid);
		List<Record> records2 =i_IndexDao.searchHotEducationArchive(oid);
		ArrayList result = (ArrayList) enroll_archive(records, records2,"education");
		if(result.size()<5)
			return result;
		else
		    return result.subList(0, 5);
	}
	@Override
	public List<Record> searchHotMajorByClass(long oid) {
		List<Record> records =i_IndexDao.searchHotMajorByClass(oid);
		return records;
	}
	@Override
	public List<Record> searchHotMajorByJob(long oid) {
		List<Record> records =i_IndexDao.searchHotMajorByJob(oid);
		List<Record> records2 =i_IndexDao.searchHotMajorByJobArchive(oid);
		ArrayList result = (ArrayList) enroll_archive(records, records2,"name");
		if(result.size()<6)
			return result;
		else
		    return result.subList(0, 6);
	}
	@Override
	public List<Record> searchHotMonthData(long oid,int year,int month) {
		List<Record> records =i_IndexDao.searchHotMonthData(oid,year,month);
		List<Record> records2 =i_IndexDao.searchHotMonthDataArchive(oid,year,month);
		records.addAll(records2);
		return records;
	}
	@Override
	public Page<Record> dynamic(String collection,String username) {
		Page<Record> record=i_IndexDao.dynamic(collection,username);
		return record;
	}
	@Override
	public List<Record> enroll_archive(List<Record> records, List<Record> records2,String indexname) {
		HashMap records3 = new HashMap();
		for(int i = 0;i<records.size();i++){
			Record enroll_item = records.get(i);
			long value1 = enroll_item.get("amount");
			String name1 = enroll_item.get(indexname);
			if(records3.get(name1) == null) {
				records3.put(name1, value1);
			} else {
				records3.put(name1, value1 + (Long)records3.get(name1));
			}
		}
		for(int i = 0;i<records2.size();i++){
			Record archive_item = records2.get(i);
			long value1 = archive_item.get("amount");
			String name1 = archive_item.get(indexname);
			if(records3.get(name1) == null) {
				records3.put(name1, value1);
			} else {
				records3.put(name1, value1 + (Long)records3.get(name1));
			}
		}
		
		ArrayList result = new ArrayList();
		for (Object o : records3.keySet()) {
			HashMap temp_hashmap = new HashMap();
			temp_hashmap.put(indexname, o);
			temp_hashmap.put("amount", records3.get(o));
			result.add(temp_hashmap);
		}
		
		result.sort(new Comparator<Map>() {

			@Override
			public int compare(Map o1, Map o2) {
				return (Long)o1.get("amount") - (Long)o2.get("amount") > 0 ? -1 : 1;
			}
		});
		
		return result;
	}

}
