package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_ArchiveDao;
import cn.gov.hrss.ln.stuenroll.tools.DaoTools;

/**
 * Archive表Dao类
 * 
 * @author YangDi
 *
 */

public class ArchiveDao implements I_ArchiveDao {

	@Override
	public long searchCountByCondition(int year, int month, int stateId, long organizationId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	archive ");
		sql.append("WHERE ");
		sql.append("	YEAR (create_time) = ? ");
		sql.append("AND MONTH (create_time) = ? ");
		sql.append("AND state_id = ? ");
		if(organizationId!=-1){
			sql.append("AND organization_id = ? ");
			long count = Db.queryLong(sql.toString(), year, month, stateId,organizationId);
			return count;
		}
		else{
			long count = Db.queryLong(sql.toString(), year, month, stateId);
			return count;
		}		
	}

	@Override
	public boolean isEnrollEligible(long pid) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	count(*) ");
		sql.append("FROM ");
		sql.append("	archive");
		sql.append("WHERE ");
		sql.append("	pid = ?; ");
		long count=Db.queryLong(sql.toString(), pid);
		boolean bool=(count==0);
		return bool;
	}

	@Override
	public List<Record> searchArchive(HashMap map, long start, long length) {
		ArrayList param = new ArrayList();
		Long id = (Long)map.get("id");
		String name = (String)map.get("name");
		String pid = (String)map.get("pid");
		String year = (String)map.get("year");
		String sex = (String)map.get("sex");
		String education = (String)map.get("education");
		Long organizationID = (Long)map.get("organizationID");
		Long professionId = (Long)map.get("professionId");
		Long classInfoId = (Long)map.get("classInfoId");
		Long stateId = (Long)map.get("stateId");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	a.id, ");
		sql.append("	a.`name`, ");
		sql.append("	a.`sex`, ");
		sql.append("	a.nation, ");
		sql.append("	a.pid, ");
		sql.append("	a.graduate_school, ");
		sql.append("	a.graduate_date, ");
		sql.append("	a.graduate_year, ");
		sql.append("	a.education, ");
		sql.append("	a.major, ");
		sql.append("	a.healthy, ");
		sql.append("	a.politics, ");
		sql.append("	a.birthday, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.resident_address), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS resident_address, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.permanent_address), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS permanent_address, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.home_address), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS home_address, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.tel), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS tel, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.home_tel), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS home_tel, ");
		sql.append("	a.email, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			p.`name` ");
		sql.append("		FROM ");
		sql.append("			profession p ");
		sql.append("		WHERE ");
		sql.append("			p.id = a.profession_id ");
		sql.append("	) AS profession, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			cl.`name` ");
		sql.append("		FROM ");
		sql.append("			classinfo cl ");
		sql.append("		WHERE ");
		sql.append("			cl.id = a.classinfo_id ");
		sql.append("	) AS classinfo, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			stu.`name` ");
		sql.append("		FROM ");
		sql.append("			student_state stu ");
		sql.append("		WHERE ");
		sql.append("			stu.id = a.state_id ");
		sql.append("	) AS state, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			o.`name` ");
		sql.append("		FROM ");
		sql.append("			organization o ");
		sql.append("		WHERE ");
		sql.append("			o.id = a.organization_id ");
		sql.append("	) AS organization, ");
		sql.append("	a.`year`, ");
		sql.append("	a.place, ");
		sql.append("	a.remark ");
		sql.append("FROM ");
		sql.append("	archive a ");
		sql.append("WHERE	1 = 1 ");
		if(id != null && id.longValue() > 0){
			sql.append("AND a.id=? ");
			param.add(id);
		}
		if(name != null && name.length() > 0){
			sql.append("AND a.name=? ");
			param.add(name);
		}
		if(pid != null && pid.length() == 18){
			sql.append("AND a.pid=? ");
			param.add(pid);
		}
		if(year != null && year.length() == 4){
			sql.append("AND a.year=? ");
			param.add(year);
		}
		if(sex != null && sex.length() > 0){
			sql.append("AND a.sex=? ");
			param.add(sex);
		}
		if(education != null && education.length() > 0){
			sql.append("AND a.education=? ");
			param.add(education);
		}
		if(organizationID != null && organizationID  > 0){
			sql.append("AND a.organization_id=? ");
			param.add(organizationID);
		}
		if(professionId != null && professionId > 0){
			sql.append("AND a.profession_id=? ");
			param.add(professionId);
		}
		if(classInfoId != null && classInfoId > 0){
			sql.append("AND a.classInfo_id=? ");
			param.add(classInfoId);
		}
		if(stateId != null && stateId > 0){
			sql.append("AND a.state_id=? ");
			param.add(stateId);
		}
		sql.append("ORDER BY a.id ");
		sql.append("LIMIT ?,?; ");
		param.add(start);
		param.add(length);
		
		List<Record> list = Db.find(sql.toString(), param.toArray());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < list.size(); i++) {
			list.get(i).set("graduate_date", df.format(list.get(i).getDate("graduate_date")));
			list.get(i).set("birthday", df.format(list.get(i).getDate("birthday")));
		}
		return DaoTools.castLongToString(list, "id");
	}

	@Override
	public long searchArchiveCount(HashMap map) {
		ArrayList param = new ArrayList();
		Long id = (Long)map.get("id");
		String name = (String)map.get("name");
		String pid = (String)map.get("pid");
		String year = (String)map.get("year");
		String sex = (String)map.get("sex");
		String education = (String)map.get("education");
		Long organizationId = (Long)map.get("organizationId");
		Long professionId = (Long)map.get("professionId");
		Long classInfoId = (Long)map.get("classInfoId");
		Long stateId = (Long)map.get("stateId");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	archive a ");
		sql.append("WHERE 1=1 ");
		if(id != null && id.longValue() > 0){
			sql.append("AND a.id=? ");
			param.add(id);
		}
		if(name != null && name.length() > 0){
			sql.append("AND a.name=? ");
			param.add(name);
		}
		if(pid != null && pid.length() == 18){
			sql.append("AND a.pid=? ");
			param.add(pid);
		}
		if(year != null && year.length() == 4){
			sql.append("AND a.year=? ");
			param.add(year);
		}
		if(sex != null && sex.length() > 0){
			sql.append("AND a.sex=? ");
			param.add(sex);
		}
		if(education != null && education.length() > 0){
			sql.append("AND a.education=? ");
			param.add(education);
		}
		if(organizationId != null && organizationId.longValue()  > 0){
			sql.append("AND a.organization_id=? ");
			param.add(organizationId);
		}
		if(professionId != null && professionId.longValue() > 0){
			sql.append("AND a.profession_id=? ");
			param.add(professionId);
		}
		if(classInfoId != null && classInfoId.longValue() > 0){
			sql.append("AND a.classInfo_id=? ");
			param.add(classInfoId);
		}
		if(stateId != null && stateId.longValue() > 0){
			sql.append("AND a.state_id=? ");
			param.add(stateId);
		}
		
		long count = Db.queryLong(sql.toString(), param.toArray());
		return count;
	}

	@Override
	public int addArchive(HashMap map) {
		ArrayList param = new ArrayList();
		String name = (String)map.get("name");
		String sex = (String)map.get("sex");
		String nation = (String)map.get("nation");
		String pid = (String)map.get("pid");
		String graduateSchool = (String)map.get("graduateSchool");
		String graduateYear = (String)map.get("graduateYear");
		String graduateDate = (String)map.get("graduateDate");
		String education = (String)map.get("education");
		String major = (String)map.get("major");
		String healthy = (String)map.get("healthy");
		String politics = (String)map.get("politics");
		String birthday = (String)map.get("birthday");
		String resident_address = (String)map.get("resident_address");
		String home_address = (String)map.get("home_address");
		String permanent_address = (String)map.get("permanent_address");
		String tel = (String)map.get("tel");
		String home_tel = (String)map.get("home_tel");
		String email = (String)map.get("email");	
		Long organizationID = (Long)map.get("organizationID");
		Long professionId = (Long)map.get("professionId");
		Long stateId = (Long)map.get("stateId");
		Long classInfoId = (Long)map.get("classInfoId");
		String place = (String)map.get("place");
		String year = (String)map.get("year");
		String remark = (String)map.get("remark");
		param.add(name);
		param.add(sex);
		param.add(nation);
		param.add(pid);
		param.add(graduateSchool);
		param.add(graduateYear);
		param.add(graduateDate);
		param.add(education);
		param.add(major);
		param.add(healthy);
		param.add(politics);
		param.add(birthday);
		param.add(resident_address);
		param.add(home_address);
		param.add(permanent_address);
		param.add(tel);
		param.add(home_tel);
		param.add(email);
		param.add(professionId);
		param.add(classInfoId);
		param.add(stateId);
		param.add(organizationID);
		param.add(place);
		param.add(year);
		param.add(remark);
		
		Date date = new Date();
		param.add(date.getTime());
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `archive` ( ");
		sql.append("	`id`, ");
		sql.append("	`name`, ");
		sql.append("	`sex`, ");
		sql.append("	`nation`, ");
		sql.append("	`pid`, ");
		sql.append("	`graduate_school`, ");
		sql.append("	`graduate_year`, ");
		sql.append("	`graduate_date`, ");
		sql.append("	`education`, ");
		sql.append("	`major`, ");
		sql.append("	`healthy`, ");
		sql.append("	`politics`, ");
		sql.append("	`birthday`, ");
		sql.append("	`resident_address`, ");
		sql.append("	`permanent_address`, ");
		sql.append("	`home_address`, ");
		sql.append("	`tel`, ");
		sql.append("	`home_tel`, ");
		sql.append("	`email`, ");
		sql.append("	`profession_id`, ");
		sql.append("	`classinfo_id`, ");
		sql.append("	`state_id`, ");
		sql.append("	`organization_id`, ");
		sql.append("	`place`, ");
		sql.append("	`year`, ");
		sql.append("	`remark`, ");
		sql.append("	`sharding` ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT VALUE FOR MYCATSEQ_GLOBAL,?,?,?,?,?,?,?,?,?,?,?,?, ");
		sql.append("		HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append("		HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append("		HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append("		HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append("		HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append("		?,?,?,?,?,?,?,?,?); ");
		
		int count = Db.update(sql.toString(), param.toArray());
		return count;
	}

	@Override
	public int updateArchive(HashMap map) {
		ArrayList param = new ArrayList();
		String id = (String)map.get("id");
		String name = (String)map.get("name");
		String sex = (String)map.get("sex");
		String nation = (String)map.get("nation");
		String pid = (String)map.get("pid");
		String graduateSchool = (String)map.get("graduateSchool");
		String graduateYear = (String)map.get("graduateYear");
		String graduateDate = (String)map.get("graduateDate");
		String education = (String)map.get("education");
		String major = (String)map.get("major");
		String healthy = (String)map.get("healthy");
		String politics = (String)map.get("politics");
		String birthday = (String)map.get("birthday");
		String resident_address = (String)map.get("resident_address");
		String home_address = (String)map.get("home_address");
		String permanent_address = (String)map.get("permanent_address");
		String tel = (String)map.get("tel");
		String home_tel = (String)map.get("home_tel");
		String email = (String)map.get("email");	
		Long organizationID = (Long)map.get("organizationID");
		Long professionId = (Long)map.get("professionId");
		Long stateId = (Long)map.get("stateId");
		Long classInfoId = (Long)map.get("classInfoId");
		String place = (String)map.get("place");
		String year = (String)map.get("year");
		String remark = (String)map.get("remark");
		param.add(name);
		param.add(sex);
		param.add(nation);
		param.add(pid);
		param.add(graduateSchool);
		param.add(graduateYear);
		param.add(graduateDate);
		param.add(education);
		param.add(major);
		param.add(healthy);
		param.add(politics);
		param.add(birthday);
		param.add(resident_address);
		param.add(home_address);
		param.add(permanent_address);
		param.add(tel);
		param.add(home_tel);
		param.add(email);
		param.add(professionId);
		param.add(classInfoId);
		param.add(stateId);
		param.add(organizationID);
		param.add(place);
		param.add(year);
		param.add(remark);
		param.add(id);
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE archive ");
		sql.append("SET `name` = ?, ");
		sql.append("  `sex` = ?, ");
		sql.append("  `nation` = ?, ");
		sql.append(" `pid` = ?, ");
		sql.append(" `graduate_school` = ?, ");
		sql.append(" `graduate_year` = ?, ");
		sql.append(" `graduate_date` = ?, ");
		sql.append(" `education` = ?, ");
		sql.append(" `major` = ?, ");
		sql.append(" `healthy` = ?, ");
		sql.append(" `politics` = ?, ");
		sql.append(" `birthday` = ?, ");
		sql.append(" `resident_address` = HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append(" `permanent_address` = HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append(" `home_address` = HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append(" `tel` = HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append(" `home_tel` = HEX(AES_ENCRYPT(?,'HelloHrss')), ");
		sql.append(" `email` = ?, ");
		sql.append(" `profession_id` = ?, ");
		sql.append(" `classinfo_id` = ?, ");
		sql.append(" `state_id` = ?, ");
		sql.append(" `organization_id` = ?, ");
		sql.append(" `place` = ?, ");
		sql.append(" `year` = ?, ");
		sql.append(" `remark` = ? ");
		sql.append("WHERE ");
		sql.append("	`id` = ?; ");
		
		int count = Db.update(sql.toString(), param.toArray());
		return count;
	}

	@Override
	public int deleteArchiveById(Long[] id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	archive ");
		sql.append("WHERE ");
		sql.append("	id IN ( ");
		for (int i = 0; i < id.length; i++) {
			sql.append("?");
			if(i != id.length - 1){
				sql.append(",");
			}
		}
		sql.append("	); ");
		
		int count = Db.update(sql.toString(),id);
		return count;
	}

	@Override
	public List<Record> searchStudentState() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	st.id, ");
		sql.append("	st.`name` ");
		sql.append("FROM ");
		sql.append("	student_state st ");
		sql.append("ORDER BY st.id; ");
		
		List<Record> list = Db.find(sql.toString());
		return DaoTools.castLongToString(list, "id");
	}

	@Override
	public List<Record> searchClassInfo() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	cl.id, ");
		sql.append("	cl.`name` ");
		sql.append("FROM ");
		sql.append("	classinfo cl ");
		sql.append("ORDER BY cl.id; ");
		
		List<Record> list = Db.find(sql.toString());
		return DaoTools.castLongToString(list, "id");
	}
}
