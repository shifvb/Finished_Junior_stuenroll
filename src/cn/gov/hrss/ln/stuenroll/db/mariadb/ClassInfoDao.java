package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_ClassInfoDao;
import cn.gov.hrss.ln.stuenroll.tools.DaoTools;

/**
 * 班级管理数据访问实现
 * 
 * @author LiYadong
 * @version 2.0
 */

public class ClassInfoDao implements I_ClassInfoDao {
	
	@Override
	public List<Record> searchAllClasses(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		List<Object> paras = new ArrayList<>();
		Long organizationId = (Long) map.get("organizationId");
		Long professionId = (Long) map.get("professionId");
		sql.append("SELECT * FROM classinfo WHERE 1=1 ");
		if (organizationId != null && organizationId.longValue() > 0) {
			sql.append("AND organization_id = ? ");
			paras.add(organizationId);
		}
		if (professionId != null && professionId.longValue() > 0) {
			sql.append("AND profession_id = ? ");
			paras.add(professionId);
		}
		sql.append("ORDER BY id, `year` DESC; ");
		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id", "organization_id", "profession_id");
	}

	@Override
	public List<Record> searchClassInfo(Map<String, Object> map, Long start, Long length) {
		Long organizationId = (Long) map.get("organizationId");
		String name = (String) map.get("name");
		Integer year = (Integer) map.get("year");
		String organizationName = (String) map.get("organizationName");
		String professionName = (String) map.get("professionName");
		Integer state = (Integer) map.get("classState");
		Long professionId = (Long) map.get("professionId");
		Long classInfoId = (Long) map.get("classInfoId");

		List<Object> paras = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	cf.id, ");
		sql.append("	cf.`name` AS className, ");
		sql.append("	o.`name` AS organizationName, ");
		sql.append("	p.`name` AS professionName, ");
		sql.append("	cf.`year`, ");
		sql.append("	cs.`name` AS classStateInfo ");
		sql.append("FROM ");
		sql.append("	classinfo cf ");
		sql.append("LEFT JOIN organization o ON cf.organization_id = o.id ");
		sql.append("LEFT JOIN profession p ON cf.profession_id = p.id ");
		sql.append("LEFT JOIN class_state cs ON cf.state_id = cs.id ");
		sql.append("WHERE 1=1 ");
		if (classInfoId != null && classInfoId > 0) {
			sql.append("AND cf.id = ? ");
			paras.add(classInfoId);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND cf.organization_id = ? ");
			paras.add(organizationId);
		}
		if (name != null && name.length() > 0) {
			sql.append("AND cf.`name` = ? ");
			paras.add(name);
		}
		if (year != null && year > 0) {
			sql.append("AND cf.`year` = ? ");
			paras.add(year);
		}
		if (organizationName != null && organizationName.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(organizationName);
		}
		if (professionName != null && professionName.length() > 0) {
			sql.append("AND p.`name` = ? ");
			paras.add(professionName);
		}
		if (state != null && (state == 1 || state == 2)) {
			sql.append("AND cf.state_id = ? ");
			paras.add(state);
		}
		if (professionId != null && professionId.longValue() > 0) {
			sql.append("AND p.id = ? ");
			paras.add(professionId);
		}
		sql.append("AND o.id != '738620600423157760' ");
		sql.append("ORDER BY ");
		sql.append("	cf.id, cf.`year` DESC, o.`name`, p.`name`, cf.`name`, cs.`name` ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);

		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public List<Record> searchArchiveClassStudentsAmountWithState(Map<String, Object> map, Long start, Long length) {
		Long organizationId = (Long) map.get("organizationId");
		String name = (String) map.get("name");
		Integer year = (Integer) map.get("year");
		String organizationName = (String) map.get("organizationName");
		String professionName = (String) map.get("professionName");
		Integer state = (Integer) map.get("stuState");
		Long classInfoId = (Long) map.get("classInfoId");

		StringBuffer sql = new StringBuffer();
		List<Object> paras = new ArrayList<>();

		sql.append("SELECT ");
		sql.append("	cf.id, ");
		sql.append("	COUNT(a.id) AS archiveStatedStudents, ");
		sql.append("	cf.`year` ");
		sql.append("FROM ");
		sql.append("	classinfo cf ");
		sql.append("LEFT JOIN archive a ON a.classinfo_id = cf.id ");
		sql.append("LEFT JOIN organization o ON a.organization_id = o.id AND o.id != '738620600423157760' ");
		sql.append("LEFT JOIN profession p ON a.profession_id = p.id ");
		sql.append("WHERE 1=1 ");
		if (classInfoId != null && classInfoId > 0) {
			sql.append("AND cf.id = ? ");
			paras.add(classInfoId);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND cf.organization_id = ? ");
			paras.add(organizationId);
		}
		if (state != null && state > 0) {
			sql.append("AND a.state_id = ? ");
			paras.add(state);
		}
		if (name != null && name.length() > 0) {
			sql.append("AND cf.`name` = ? ");
			paras.add(name);
		}
		if (year != null && year > 0) {
			sql.append("AND cf.`year` = ? ");
			paras.add(year);
		}
		if (organizationName != null && organizationName.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(organizationName);
		}
		if (professionName != null && professionName.length() > 0) {
			sql.append("AND p.`name` = ? ");
			paras.add(professionName);
		}
		sql.append("GROUP BY ");
		sql.append("	cf.id ");
		sql.append("ORDER BY ");
		sql.append("	cf.id, ");
		sql.append("	cf.`year` DESC ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);

		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public List<Record> searchUnarchiveClassStudentsAmountWithState(Map<String, Object> map, Long start, Long length) {
		Long organizationId = (Long) map.get("organizationId");
		String name = (String) map.get("name");
		Integer year = (Integer) map.get("year");
		String organizationName = (String) map.get("organizationName");
		String professionName = (String) map.get("professionName");
		Integer state = (Integer) map.get("stuState");
		Long classInfoId = (Long) map.get("classInfoId");

		StringBuffer sql = new StringBuffer();
		List<Object> paras = new ArrayList<>();

		sql.append("SELECT ");
		sql.append("	cf.id, ");
		sql.append("	COUNT(e.id) AS unarchiveStatedStudents, ");
		sql.append("	cf.`year` ");
		sql.append("FROM ");
		sql.append("	classinfo cf ");
		sql.append("LEFT JOIN enroll e ON e.classinfo_id = cf.id ");
		sql.append("LEFT JOIN organization o ON e.organization_id = o.id AND o.id != '738620600423157760' ");
		sql.append("LEFT JOIN profession p ON e.profession_id = p.id ");
		sql.append("WHERE 1=1 ");
		if (classInfoId != null && classInfoId > 0) {
			sql.append("AND cf.id = ? ");
			paras.add(classInfoId);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND cf.organization_id = ? ");
			paras.add(organizationId);
		}
		if (state != null && state > 0) {
			sql.append("AND e.state_id = ? ");
			paras.add(state);
		}
		if (name != null && name.length() > 0) {
			sql.append("AND cf.`name` = ? ");
			paras.add(name);
		}
		if (year != null && year > 0) {
			sql.append("AND cf.`year` = ? ");
			paras.add(year);
		}
		if (organizationName != null && organizationName.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(organizationName);
		}
		if (professionName != null && professionName.length() > 0) {
			sql.append("AND p.`name` = ? ");
			paras.add(professionName);
		}
		sql.append("GROUP BY ");
		sql.append("	cf.id ");
		sql.append("ORDER BY ");
		sql.append("	cf.id, ");
		sql.append("	cf.`year` DESC ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);

		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public long searchClassAmountWithState(Map<String, Object> map) {
		Long organizationId = (Long) map.get("organizationId");
		String name = (String) map.get("name");
		Integer year = (Integer) map.get("year");
		String organizationName = (String) map.get("organizationName");
		String professionName = (String) map.get("professionName");
		Integer state = (Integer) map.get("classState");
		Long classInfoId = (Long) map.get("classInfoId");

		StringBuffer sql = new StringBuffer();
		List<Object> paras = new ArrayList<>();

		sql.append("SELECT ");
		sql.append("	COUNT(cf.id)  ");
		sql.append("FROM ");
		sql.append("	classinfo cf ");
		sql.append("LEFT JOIN organization o ON cf.organization_id = o.id AND o.id != '738620600423157760' ");
		sql.append("LEFT JOIN profession p ON cf.profession_id = p.id ");
		sql.append("LEFT JOIN class_state cs ON cf.state_id = cs.id ");
		sql.append("WHERE 1=1 ");
		if (classInfoId != null && classInfoId > 0) {
			sql.append("AND cf.id = ? ");
			paras.add(classInfoId);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND cf.organization_id = ? ");
			paras.add(organizationId);
		}
		if (name != null && name.length() > 0) {
			sql.append("AND cf.`name` = ? ");
			paras.add(name);
		}
		if (year != null && year > 0) {
			sql.append("AND cf.`year` = ? ");
			paras.add(year);
		}
		if (organizationName != null && organizationName.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(organizationName);
		}
		if (professionName != null && professionName.length() > 0) {
			sql.append("AND p.`name` = ? ");
			paras.add(professionName);
		}
		if (state != null && (state == 1 || state == 2)) {
			sql.append("AND cf.state_id = ? ");
			paras.add(state);
		}
		sql.append(";");

		long count = Db.queryLong(sql.toString(), paras.toArray());
		return count;
	}

	@Override
	public int addClass(Map<String, Object> map) {
		String name = (String) map.get("className");
		Long organizationId = (Long) map.get("organizationId");
		Long professionId = (Long) map.get("professionId");
		Integer year = (Integer) map.get("year");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Integer classState = (Integer) map.get("classState");
		List<Object> paras = new ArrayList<>();
		paras.add(name);
		paras.add(organizationId);
		paras.add(professionId);
		paras.add(year);
		if (classState != null && (classState == 1 || classState == 2)) {
			paras.add(classState);
		}
		else {
			paras.add(1);
		}
		paras.add(df.format(new Date()));
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `classinfo` ( ");
		sql.append("	`id`, ");
		sql.append("	`name`, ");
		sql.append("	`organization_id`, ");
		sql.append("	`profession_id`, ");
		sql.append("	`year`, ");
		sql.append("	`state_id`, ");
		sql.append("	`date` ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT VALUE FOR MYCATSEQ_GLOBAL, ?, ?, ?, ?, ?, ?); ");

		int count = Db.update(sql.toString(), paras.toArray());
		return count;
	}

	@Before(Tx.class)
	@Override
	public int updateClassInfo(Map<String, Object> map) {
		Long classId = (Long) map.get("classId");
		String name = (String) map.get("className");
		Long organizationId = (Long) map.get("organizationId");
		Long professionId = (Long) map.get("professionId");
		Integer year = (Integer) map.get("year");
		Integer classState = (Integer) map.get("classState");
		List<Object> paras = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `classinfo` ");
		sql.append("SET ");
		if (classState != null && (classState == 1 || classState == 2)) {
			sql.append("state_id = ? ");
			paras.add(classState);
		} else {
			paras.add(name);
			paras.add(organizationId);
			paras.add(professionId);
			paras.add(year);
			sql.append(" `name` = ?, ");
			sql.append(" `organization_id` = ?, ");
			sql.append(" `profession_id` = ?, ");
			sql.append(" `year` = ? ");
		}
		paras.add(classId);
		sql.append("WHERE ");
		sql.append("	(`id` = ?); ");

		int count = Db.update(sql.toString(), paras.toArray());
		return count;
	}

	@Override
	public long deleteClasses(Long[] id) {
		StringBuffer sql = new StringBuffer();
		List<Long> paras = Arrays.asList(id);
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	classinfo ");
		sql.append("WHERE ");
		sql.append("	id IN ( ");
		for (int i = 0; i < id.length; i++) {
			sql.append("?");
			if (i != id.length - 1) {
				sql.append(",");
			}
		}
		sql.append("); ");
		long count = Db.update(sql.toString(), paras.toArray());
		return count;
	}

	@Override
	public List<Record> getUnarchiveStudentsInClass(Long classId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	e.`id`, ");
		sql.append("	e.`name`, ");
		sql.append("	e.`sex`, ");
		sql.append("	e.`pid`, ");
		sql.append("	e.`graduate_school`, ");
		sql.append("	e.`major`, ");
		sql.append("	e.`birthday`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(e.`resident_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `resident_address`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(e.`permanent_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `permanent_address`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(e.`home_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `home_address`, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(e.`tel`), 'HelloHrss') AS CHAR) AS `tel`, ");
		sql.append("	e.`email`, ");
		sql.append("	e.`profession_id`, ");
		sql.append("	e.`classinfo_id`, ");
		sql.append("	ss.`name` AS `state`, ");
		sql.append("	e.`organization_id` ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("LEFT JOIN student_state ss ON e.state_id = ss.id ");
		sql.append("WHERE ");
		sql.append("	e.classinfo_id = ? ");
		sql.append("ORDER BY ");
		sql.append("	e.id, ");
		sql.append("	e.pid, ");
		sql.append("	e.birthday; ");
		List<Record> records = Db.find(sql.toString(), classId);
		return DaoTools.castLongToString(records, "id");
	}
	
	@Override
	public List<Record> getArchiveStudentsInClass(Long classId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	a.`id`, ");
		sql.append("	a.`name`, ");
		sql.append("	a.`sex`, ");
		sql.append("	a.`pid`, ");
		sql.append("	a.`graduate_school`, ");
		sql.append("	a.`major`, ");
		sql.append("	a.`birthday`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.`resident_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `resident_address`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.`permanent_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `permanent_address`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.`home_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `home_address`, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(a.`tel`), 'HelloHrss') AS CHAR) AS `tel`, ");
		sql.append("	a.`email`, ");
		sql.append("	a.`profession_id`, ");
		sql.append("	a.`classinfo_id`, ");
		sql.append("	ss.`name` AS `state`, ");
		sql.append("	a.`organization_id` ");
		sql.append("FROM ");
		sql.append("	archive a ");
		sql.append("LEFT JOIN student_state ss ON a.state_id = ss.id ");
		sql.append("WHERE ");
		sql.append("	a.classinfo_id = ? ");
		sql.append("ORDER BY ");
		sql.append("	a.id, ");
		sql.append("	a.pid, ");
		sql.append("	a.birthday; ");
		List<Record> records = Db.find(sql.toString(), classId);
		return DaoTools.castLongToString(records, "id");
	}
	
	@Override
	public List<Record> getUnarchiveStudentsInClass(Long classId, Long start, Long length) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	e.`id`, ");
		sql.append("	e.`name`, ");
		sql.append("	e.`sex`, ");
		sql.append("	e.`pid`, ");
		sql.append("	e.`graduate_school`, ");
		sql.append("	e.`major`, ");
		sql.append("	e.`birthday`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(e.`resident_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `resident_address`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(e.`permanent_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `permanent_address`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(e.`home_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `home_address`, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(e.`tel`), 'HelloHrss') AS CHAR) AS `tel`, ");
		sql.append("	e.`email`, ");
		sql.append("	e.`profession_id`, ");
		sql.append("	e.`classinfo_id`, ");
		sql.append("	ss.`name` AS `state`, ");
		sql.append("	e.`organization_id` ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("LEFT JOIN student_state ss ON e.state_id = ss.id ");
		sql.append("WHERE ");
		sql.append("	e.classinfo_id = ? ");
		sql.append("ORDER BY ");
		sql.append("	e.id, ");
		sql.append("	e.pid, ");
		sql.append("	e.birthday ");
		sql.append("LIMIT ?, ?; ");
		List<Record> records = Db.find(sql.toString(), classId, start, length);
		return DaoTools.castLongToString(records, "id");
	}
	
	@Override
	public List<Record> getArchiveStudentsInClass(Long classId, Long start, Long length) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	a.`id`, ");
		sql.append("	a.`name`, ");
		sql.append("	a.`sex`, ");
		sql.append("	a.`pid`, ");
		sql.append("	a.`graduate_school`, ");
		sql.append("	a.`major`, ");
		sql.append("	a.`birthday`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.`resident_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `resident_address`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.`permanent_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `permanent_address`, ");
		sql.append("	CAST(AES_DECRYPT( ");
		sql.append("		UNHEX(a.`home_address`), ");
		sql.append("		'HelloHrss' ");
		sql.append("	) AS CHAR) AS `home_address`, ");
		sql.append("	CAST(AES_DECRYPT(UNHEX(a.`tel`), 'HelloHrss') AS CHAR) AS `tel`, ");
		sql.append("	a.`email`, ");
		sql.append("	a.`profession_id`, ");
		sql.append("	a.`classinfo_id`, ");
		sql.append("	ss.`name` AS `state`, ");
		sql.append("	a.`organization_id` ");
		sql.append("FROM ");
		sql.append("	archive a ");
		sql.append("LEFT JOIN student_state ss ON a.state_id = ss.id ");
		sql.append("WHERE ");
		sql.append("	a.classinfo_id = ? ");
		sql.append("ORDER BY ");
		sql.append("	a.id, ");
		sql.append("	a.pid, ");
		sql.append("	a.birthday ");
		sql.append("LIMIT ?, ?; ");
		List<Record> records = Db.find(sql.toString(), classId, start, length);
		return DaoTools.castLongToString(records, "id");
	}
	
	@Override
	public long getUnarchiveStudentsAmountInClass(Long classId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(e.`id`) AS amount ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("WHERE ");
		sql.append("	e.classinfo_id = ?; ");
		long count = Db.queryLong(sql.toString(), classId);
		return count;
	}
	
	@Override
	public long getArchiveStudentsAmountInClass(Long classId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(a.`id`) AS amount ");
		sql.append("FROM ");
		sql.append("	archive a ");
		sql.append("WHERE ");
		sql.append("	a.classinfo_id = ?; ");
		long count = Db.queryLong(sql.toString(), classId);
		return count;
	}

	@Before(Tx.class)
	@Override
	public int archiveStudent(Record record) {
		record.remove("wechat", "create_time");
		DaoTools.castLongToString(record, "id");
		Map<String, Object> map = record.getColumns();
		int count = this.archiveStudent(map);
		return count;
	}

	@Before(Tx.class)
	@Override
	public int archiveStudent(Map<String, Object> map) {
		String id = (String) map.get("id");
		String name = (String) map.get("name");
		String sex = (String) map.get("sex");
		String nation = (String) map.get("nation");
		String pid = (String) map.get("pid");
		String graduate_school = (String) map.get("graduate_school");
		Integer graduate_year = (Integer) map.get("graduate_year");
		String graduate_date = (String) map.get("graduate_date");
		String education = (String) map.get("education");
		String major = (String) map.get("major");
		String healthy = (String) map.get("healthy");
		String politics = (String) map.get("politics");
		String birthday = (String) map.get("birthday");
		String resident_address = (String) map.get("resident_address");
		String permanent_address = (String) map.get("permanent_address");
		String home_address = (String) map.get("home_address");
		String tel = (String) map.get("tel");
		String home_tel = (String) map.get("home_tel");
		String email = (String) map.get("email");
		Long profession_id = (Long) map.get("profession_id");
		Long classinfo_id = (Long) map.get("classinfo_id");
		Long state_id = (Long) map.get("state_id");
		Long organization_id = (Long) map.get("organization_id");
		String place = (String) map.get("place");
		Integer year = (Integer) map.get("year");
		String remark = (String) map.get("remark");
		Long sharding = (Long) map.get("sharding");
		List<Object> paras = new ArrayList<>();
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
		sql.append("	`remark`, ");
		sql.append("	`year`, ");
		sql.append("	`sharding` ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
		sql.append("	);");
		
		paras.add(id);
		paras.add(name);
		paras.add(sex);
		paras.add(nation);
		paras.add(pid);
		paras.add(graduate_school);
		paras.add(graduate_year);
		paras.add(graduate_date);
		paras.add(education);
		paras.add(major);
		paras.add(healthy);
		paras.add(politics);
		paras.add(birthday);
		paras.add(resident_address);
		paras.add(permanent_address);
		paras.add(home_address);
		paras.add(tel);
		paras.add(home_tel);
		paras.add(email);
		paras.add(profession_id);
		paras.add(classinfo_id);
		paras.add(state_id);
		paras.add(organization_id);
		paras.add(place);
		paras.add(year);
		paras.add(remark);
		paras.add(sharding);
		
		int count = Db.update(sql.toString(), paras.toArray());
		return count;
	}

	@Before(Tx.class)
	@Override
	public long archiveStudentsInClass(List<Record> records) {
		long count = 0L;
		for (Record record : records) {
			count += this.archiveStudent(record);
		}
		return count;
	}

	@Before(Tx.class)
	@Override
	public long archiveStudentsInClass(Object[] id, Map<Object, Map<String, Object>> info) {
		if (id == null || info == null) {
			return 0;
		}
		if (id.length != info.size()) {
			return 0;
		}
		long count = 0L;
		for (Object obj : id) {
			Map<String, Object> map = info.get(obj);
			count += this.searchClassAmountWithState(map);
		}
		return count;
	}

	@Before(Tx.class)
	@Override
	public long archiveStudentsInClass(List<Object> id, Map<Object, Map<String, Object>> info) {
		if (id == null || info == null) {
			return 0;
		}
		if (id.size() != info.size()) {
			return 0;
		}
		long count = 0L;
		for (Object obj : id) {
			Map<String, Object> map = info.get(obj);
			count += this.searchClassAmountWithState(map);
		}
		return count;
	}

}
