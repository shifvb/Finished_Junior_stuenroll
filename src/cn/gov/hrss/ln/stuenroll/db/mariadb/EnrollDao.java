package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_EnrollDao;

/**
 * Enroll表Dao类
 * 
 * @author York Chu
 *
 */
public class EnrollDao implements I_EnrollDao {

	@Override
	public long searchCountByCondition(int year, int month, int stateId, long organizationId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	enroll ");
		sql.append("WHERE ");
		sql.append("	YEAR (create_time) = ? ");
		sql.append("AND MONTH (create_time) = ? ");
		sql.append("AND state_id = ? ");
		if (organizationId != -1) {
			sql.append("AND organization_id = ? ");
			long count = Db.queryLong(sql.toString(), year, month, stateId, organizationId);
			return count;
		}
		else {
			long count = Db.queryLong(sql.toString(), year, month, stateId);
			return count;
		}

	}

	@Override
	public List<Record> searchEnroll(HashMap map, long start, long length) {
		ArrayList param = new ArrayList();
		String name = (String) map.get("name");
		String pid = (String) map.get("pid");
		Integer year = (Integer) map.get("year");
		String sex = (String) map.get("sex");
		String education = (String) map.get("education");
		Long organizationId = (Long) map.get("organizationId");
		Long professionId = (Long) map.get("professionId");
		Long classinfoId = (Long) map.get("classinfoId");
		Long stateId = (Long) map.get("stateId");
		// 作用于查询未分班与已分班的记录
		String flag=(String) map.get("flag");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	e.id, ");
		sql.append("	e.`name`, ");
		sql.append("	e.pid, ");
		sql.append("	AES_DECRYPT(UNHEX(e.tel), 'HelloHrss') AS tel, ");
		sql.append("	IFNULL(o.`name`,'') AS organization, ");
		sql.append("	IFNULL(p.`name`,'') AS profession, ");
		sql.append("	IFNULL(c.`name`,'') AS classinfo , ");
		sql.append("	e.`year`, ");
		sql.append("	ss.`name` AS state ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("LEFT JOIN organization o ON e.organization_id = o.id ");
		sql.append("LEFT JOIN profession p ON e.profession_id = p.id ");
		sql.append("LEFT JOIN classinfo c ON e.classinfo_id = c.id ");
		sql.append("LEFT JOIN student_state ss ON e.state_id = ss.id ");
		sql.append("WHERE ");
		sql.append("	1 = 1 ");
		if (name != null && name.length() > 0) {
			sql.append(" AND e.name = ? ");
			param.add(name);
		}
		if (pid != null && pid.length() > 0) {
			sql.append(" AND e.pid = ? ");
			param.add(pid);
		}
		if (year != null) {
			sql.append(" AND e.year = ? ");
			param.add(year);
		}
		if (sex != null && sex.length() > 0) {
			sql.append(" AND e.sex = ? ");
			param.add(sex);
		}
		if (education != null && education.length() > 0) {
			sql.append(" AND e.education = ? ");
			param.add(education);
		}
		if (organizationId != null) {
			sql.append(" AND e.organization_id = ? ");
			param.add(organizationId);
		}
		if (professionId != null) {
			sql.append(" AND e.profession_id = ? ");
			param.add(professionId);
		}
		if (classinfoId != null) {
			sql.append(" AND e.classinfo_id = ? ");
			param.add(classinfoId);
		}
		if (stateId != null) {
			sql.append(" AND e.state_id = ? ");
			param.add(stateId);
		}
		if(flag!=null && flag.length()>0){
			if(flag.equals("已分班")){
				sql.append(" AND e.classinfo_id is NOT NULL ");
			}
			else if(flag.equals("未分班")){
				sql.append(" AND e.classinfo_id is NULL ");
			}
		}
		sql.append("ORDER BY e.id ");
		sql.append("LIMIT ?, ? ");
		param.add(start);
		param.add(length);

		List<Record> list = Db.find(sql.toString(), param.toArray());
		for (Record record : list) {
			byte[] tel = record.getBytes("tel");
			record.set("tel", new String(tel));
			record.set("id", record.getLong("id").toString());
		}
		return list;
	}

	@Override
	public long searchEnrollCount(HashMap map) {
		ArrayList param = new ArrayList();
		String name = (String) map.get("name");
		String pid = (String) map.get("pid");
		Integer year = (Integer) map.get("year");
		String sex = (String) map.get("sex");
		String education = (String) map.get("education");
		Long organizationId = (Long) map.get("organizationId");
		Long professionId = (Long) map.get("professionId");
		Long classinfoId = (Long) map.get("classinfoId");
		Long stateId = (Long) map.get("stateId");
		String flag=(String) map.get("flag");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("LEFT JOIN organization o ON e.organization_id = o.id ");
		sql.append("LEFT JOIN profession p ON e.profession_id = p.id ");
		sql.append("LEFT JOIN classinfo c ON e.classinfo_id = c.id ");
		sql.append("LEFT JOIN student_state ss ON e.state_id = ss.id ");
		sql.append("WHERE ");
		sql.append("	1 = 1  ");
		if (name != null && name.length() > 0) {
			sql.append(" AND e.name = ? ");
			param.add(name);
		}
		if (pid != null && pid.length() > 0) {
			sql.append(" AND e.pid = ? ");
			param.add(pid);
		}
		if (year != null) {
			sql.append(" AND e.year = ? ");
			param.add(year);
		}
		if (sex != null && sex.length() > 0) {
			sql.append(" AND e.sex = ? ");
			param.add(sex);
		}
		if (education != null && education.length() > 0) {
			sql.append(" AND e.education = ? ");
			param.add(education);
		}
		if (organizationId != null) {
			sql.append(" AND e.organization_id = ? ");
			param.add(organizationId);
		}
		if (professionId != null) {
			sql.append(" AND e.profession_id = ? ");
			param.add(professionId);
		}
		if (classinfoId != null) {
			sql.append(" AND e.classinfo_id = ? ");
			param.add(classinfoId);
		}
		if (stateId != null) {
			sql.append(" AND e.state_id = ? ");
			param.add(stateId);
		}
		if(flag!=null && flag.length()>0){
			if(flag.equals("已分班")){
				sql.append(" AND e.classinfo_id is NOT NULL ");
			}
			else if(flag.equals("未分班")){
				sql.append(" AND e.classinfo_id is NULL ");
			}
		}
		long count = Db.queryLong(sql.toString(), param.toArray());
		return count;
	}

	@Override
	public int deleteById(Long[] id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	enroll ");
		sql.append("WHERE ");
		sql.append("	id IN ( ");
		for (int i = 0; i < id.length; i++) {
			sql.append("?");
			if (i != id.length - 1) {
				sql.append(",");
			}
		}
		sql.append(") ");
		int i = Db.update(sql.toString(), id);
		return i;
	}

	@Override
	public boolean isEnrollEligible(String pid) {
		String sql="SELECT COUNT(*) FROM enroll WHERE pid = ?";
		long count=Db.queryLong(sql, pid);
		boolean bool=(count==0);
		return bool;
	}

	@Override
	public int addEnroll(Object[] obj) {		
		StringBuffer sql=new StringBuffer();
		sql.append("INSERT INTO enroll ( ");
		sql.append("	id, ");
		sql.append("	`name`, ");
		sql.append("	sex, ");
		sql.append("	nation, ");
		sql.append("	pid, ");
		sql.append("	graduate_school, ");
		sql.append("	graduate_year, ");
		sql.append("	graduate_date, ");
		sql.append("	education, ");
		sql.append("	major, ");
		sql.append("	healthy, ");
		sql.append("	politics, ");
		sql.append("	birthday, ");
		sql.append("	resident_address, ");
		sql.append("	permanent_address, ");
		sql.append("	home_address, ");
		sql.append("	tel, ");
		sql.append("	home_tel, ");
		sql.append("	email, ");
		sql.append("	profession_id, ");
		sql.append("	state_id, ");
		sql.append("	organization_id, ");
		sql.append("	place, ");
		sql.append("	`year`, ");
		sql.append("	sharding ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT VALUE FOR MYCATSEQ_GLOBAL, ");
		sql.append("			?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		sql.append("	HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append("	HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append("	HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append("	HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append("	HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append(" ?, ?, ?, ?, ?, ?, ? ");
		sql.append("	); ");
		int i=Db.update(sql.toString(), obj);
		return i;
	}

	@Override
	public Record searchEnrollById(long id) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	id, ");
		sql.append("	`name`, ");
		sql.append("	sex, ");
		sql.append("	nation, ");
		sql.append("	pid, ");
		sql.append("	graduate_school, ");
		sql.append("	graduate_year, ");
		sql.append("	CAST(DATE_FORMAT(graduate_date,'%Y-%m-%d') AS CHAR) AS graduate_date, ");
		sql.append("	education, ");
		sql.append("	major, ");
		sql.append("	healthy, ");
		sql.append("	politics, ");
		sql.append("	CAST(DATE_FORMAT(birthday,'%Y-%m-%d') AS CHAR) AS birthday, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(resident_address),'HelloHrss') AS CHAR) AS resident_address, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(permanent_address),'HelloHrss') AS CHAR) AS permanent_address, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(home_address),'HelloHrss') AS CHAR) AS home_address, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(tel),'HelloHrss') AS CHAR) AS tel, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(home_tel),'HelloHrss') AS CHAR) AS home_tel, ");
		sql.append("	email, ");
		sql.append("	profession_id, ");
		sql.append("	state_id, ");
		sql.append("	organization_id, ");
		sql.append("	place ");
		sql.append("FROM ");
		sql.append("	enroll ");
		sql.append("WHERE ");
		sql.append("	id = ? ");
		Record record = Db.findFirst(sql.toString(), id);
		record.set("id", record.getLong("id").toString());
		return record;
	}

	@Override
	public int modifyEnroll(HashMap map) {
		ArrayList param = new ArrayList();
		
		Long id  =  (Long) map.get("id");
		String name = (String) map.get("name");
		String pid = (String) map.get("pid");
		Integer graduateYear = (Integer) map.get("graduateYear");
		String nation = (String) map.get("nation");
		String graduteSchool = (String) map.get("graduteSchool");
		String graduateDate = (String) map.get("graduateDate");
		String major = (String) map.get("major");
		String healthy = (String) map.get("healthy");
		String politics = (String) map.get("politics");
		String birthday = (String) map.get("birthday");
		String residentAddress = (String) map.get("residentAddress");
		String tel = (String) map.get("tel");
		String email = (String) map.get("email");
		String permanentAddress = (String) map.get("permanentAddress");
		String homeAddress = (String) map.get("homeAddress");
		String homeTel = (String) map.get("homeTel");
		String sex = (String) map.get("sex");
		String place = (String) map.get("place");
		String education = (String) map.get("education");
		Long organizationId = (Long) map.get("organizationId");
		Long professionId = (Long) map.get("professionId");
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE enroll SET ");
		if (name != null && name.length() > 0) {
			sql.append("    `name` = ?, ");
			param.add(name);
		}
		if (sex != null && sex.length() > 0) {
			sql.append("	sex = ?, ");
			param.add(sex);
		}
		if (nation != null && nation.length() > 0) {
			sql.append("	nation = ?, ");
			param.add(nation);
		}
		if (pid != null && pid.length() > 0) {
			sql.append("	pid = ?, ");
			param.add(pid);
		}
		if (graduteSchool != null && graduteSchool.length() > 0) {
			sql.append("	graduate_school = ?, ");
			param.add(graduteSchool);
		}
		if (graduateYear != null) {
			sql.append("	graduate_year = ?, ");
			param.add(graduateYear);
		}
		if (graduateDate != null && graduateDate.length() > 0) {
			sql.append("	graduate_date = ?, ");
			param.add(graduateDate);
		}
		if (education != null && education.length() > 0) {
			sql.append("	education = ?, ");
			param.add(education);
		}
		if (major != null && major.length() > 0) {
			sql.append("	major = ?, ");
			param.add(major);
		}
		if (healthy != null && healthy.length() > 0) {
			sql.append("	healthy = ?, ");
			param.add(healthy);
		}
		if (politics != null && politics.length() > 0) {
			sql.append("	politics = ?, ");
			param.add(politics);
		}
		if (birthday != null && birthday.length() > 0) {
			sql.append("	birthday = ?, ");
			param.add(birthday);
		}
		if (residentAddress != null && residentAddress.length() > 0) {
			sql.append("	resident_address = HEX(AES_ENCRYPT(?, 'HelloHrss')), ");
			param.add(residentAddress);
		}
		if (permanentAddress != null && permanentAddress.length() > 0) {
			sql.append("	permanent_address = HEX(AES_ENCRYPT(?, 'HelloHrss')), ");
			param.add(permanentAddress);
		}
		if (homeAddress != null && homeAddress.length() > 0) {
			sql.append("	home_address = HEX(AES_ENCRYPT(?, 'HelloHrss')), ");
			param.add(homeAddress);
		}
		if (tel != null && tel.length() > 0) {
			sql.append("	tel = HEX(AES_ENCRYPT(?, 'HelloHrss')), ");
			param.add(tel);
		}
		if (homeTel != null && homeTel.length() > 0) {
			sql.append("	home_tel = HEX(AES_ENCRYPT(?, 'HelloHrss')), ");
			param.add(homeTel);
		}
		if (professionId != null) {
			sql.append("	profession_id = ?, ");
			param.add(professionId);
		}
		if (organizationId != null) {
			sql.append("	organization_id = ?, ");
			param.add(organizationId);
		}
		if (place != null && place.length() > 0) {
			sql.append("	place = ? ");
			param.add(place);
		}
		//sql.append("	create_time = '2016-01-09 00:00:00' ");
		param.add(id);
		sql.append("WHERE ");
		sql.append("	id = ? ");
		int count = Db.update(sql.toString(), param.toArray());
		return count;
	}

	@Override
	public Record searchRegisterRecord(String pid) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	e.`name`, ");
		sql.append("	e.sex, ");
		sql.append("	e.nation, ");
		sql.append("	e.pid, ");
		sql.append("	e.graduate_school, ");
		sql.append("	e.graduate_year, ");
		sql.append("	CAST(DATE_FORMAT(e.graduate_date,'%Y-%m-%d') AS CHAR) AS graduate_date, ");
		sql.append("	e.education, ");
		sql.append("	e.major, ");
		sql.append("	e.healthy, ");
		sql.append("	e.politics, ");
		sql.append("	CAST(DATE_FORMAT(e.birthday,'%Y-%m-%d') AS CHAR) AS birthday, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(e.resident_address),'HelloHrss') AS CHAR) AS resident_address, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(e.permanent_address),'HelloHrss') AS CHAR) AS permanent_address, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(e.home_address),'HelloHrss') AS CHAR) AS home_address, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(e.tel),'HelloHrss') AS CHAR) AS tel, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(e.home_tel),'HelloHrss') AS CHAR) AS home_tel, ");
		sql.append("	e.email, ");
		sql.append("	CAST(IFNULL(AES_DECRYPT(UNHEX(e.wechat),'HelloHrss'),'') AS CHAR) AS wechat, ");
		sql.append("	CAST(IFNULL(e.remark,'') AS CHAR) AS remark, ");
		sql.append("	o.`name` AS organization, ");
		sql.append("	o.abbreviation, ");
		sql.append("	o.liaison, ");
		sql.append("	o.liaison_tel, ");
		sql.append("	p.`name` AS profession ");
		sql.append("FROM ");
		sql.append("	enroll AS e ");
		sql.append("JOIN organization AS o ON e.organization_id = o.id ");
		sql.append("JOIN profession AS p ON e.profession_id = p.id ");
		sql.append("WHERE ");
		sql.append("	e.pid = ? ");
		Record record = Db.findFirst(sql.toString(), pid);
		return record;
	}

	@Override
	public int allot(Long[] id, long professionId, long organizationId, long classId, String place) {
		ArrayList list = new ArrayList();
		list.add(professionId);
		list.add(organizationId);
		list.add(classId);
		list.add(place);
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE enroll ");
		sql.append("SET profession_id =?, organization_id =?, classinfo_id =?,place =? ");
		sql.append("WHERE id IN( ");
		for(int i = 0; i < id.length; i++){
			sql.append("? ");
			list.add(id[i]);
			if(i != id.length-1)
				sql.append(", ");
		}
		sql.append(") ");
		int i = Db.update(sql.toString(), list.toArray());
		return i;
	}
	
	// 学员分班取消
	@Override
	public int cancelAllot(Long[] id) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE enroll ");
		sql.append("SET classinfo_id = NULL ");
		sql.append("WHERE ");
		sql.append("id IN ( ");
		for (int i = 0; i < id.length; i++) {
			sql.append("? ");
			if (i != id.length - 1) {
				sql.append(",");
			}
		}
		sql.append(") ");
		int i = Db.update(sql.toString(), id);
		return i;
	}

	@Override
	public int quit(long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE enroll ");
		sql.append("SET state_id = 4 ");
		sql.append("WHERE id = ? ");
		int i = Db.update(sql.toString(), id);
		return i;
	}

	@Override
	public int cancelQuit(Long[] id) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE enroll ");
		sql.append("SET state_id = 1 ");
		sql.append("WHERE ");
		sql.append("id IN ( ");
		for (int i = 0; i < id.length; i++) {
			sql.append("? ");
			if (i != id.length - 1) {
				sql.append(",");
			}
		}
		sql.append(") ");
		int count = Db.update(sql.toString(), id);
		return count;
	}
}
