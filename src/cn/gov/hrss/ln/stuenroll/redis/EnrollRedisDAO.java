package cn.gov.hrss.ln.stuenroll.redis;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import cn.gov.hrss.ln.stuenroll.db.I_OrganizationDao;
import cn.gov.hrss.ln.stuenroll.db.I_ProfessionDao;

public class EnrollRedisDAO implements I_EnrollRedisDAO {
	I_OrganizationDao i_OrganizationDao; 
	I_ProfessionDao i_ProfessionDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void insert(HashMap map) {
		Cache cache=Redis.use("报名缓存");
		cache.hmset(map.get("pid"), map);
	}

	@Override
	public boolean isEnrollEligible(String pid) {
		Cache cache=Redis.use("报名缓存");
		boolean bool=!cache.exists(pid);
		return bool;
	}

	@Override
	public Record searchRegisterRecord(String pid) {
		Cache cache=Redis.use("报名缓存");
		if(isEnrollEligible(pid)){
			return null;
		}
		List list = cache.hmget(pid, "name", "sex", "nation", "pid", "graduateSchool", "graduateYear", "graduateDate", "education", "major",
				"healthy", "politics", "birthday", "residentAddress", "permanentAddress", "homeAddress", "tel", "homeTel", "email", "wechat",
				"remark", "organizationId", "professionId");	
		
		Long organizationId = Long.parseLong((String)list.get(20));
		Record org_record = i_OrganizationDao.searchOrganization(organizationId);
		String organization = org_record.getStr("name");
		String abbreviation=org_record.getStr("abbreviation");
		String liaison=org_record.getStr("liaison");	
		String liaison_tel=org_record.getStr("liaison_tel");
		Long professionId = Long.parseLong((String)list.get(20));
		String profession = i_ProfessionDao.searchName(professionId);
		
		Record record = new Record();
		record.set("name", list.get(0));
		record.set("sex", list.get(1));
		record.set("nation", list.get(2));
		record.set("pid", list.get(3));
		record.set("graduate_school", list.get(4));
		record.set("graduate_year", Integer.parseInt((String)list.get(5)));
		record.set("graduate_date", list.get(6));
		record.set("education", list.get(7));
		record.set("major", list.get(8));
		record.set("healthy", list.get(9));
		record.set("politics", list.get(10));
		record.set("birthday", list.get(11));
		record.set("resident_address", list.get(12));
		record.set("permanent_address", list.get(13));
		record.set("home_address", list.get(14));
		record.set("tel", list.get(15));
		record.set("home_tel", list.get(16));
		record.set("email", list.get(17));
		record.set("wechat", list.get(18));
		record.set("remark", list.get(19)!=null ? list.get(19) : "");
		record.set("organization", organization);
		record.set("abbreviation", abbreviation);
		record.set("liaison_tel", liaison_tel);
		record.set("organization", organization);
		record.set("profession", profession);
		
		return record;
	}

	public I_OrganizationDao getI_OrganizationDao() {
		return i_OrganizationDao;
	}

	public void setI_OrganizationDao(I_OrganizationDao i_OrganizationDao) {
		this.i_OrganizationDao = i_OrganizationDao;
	}

	public I_ProfessionDao getI_ProfessionDao() {
		return i_ProfessionDao;
	}

	public void setI_ProfessionDao(I_ProfessionDao i_ProfessionDao) {
		this.i_ProfessionDao = i_ProfessionDao;
	}
	
}
