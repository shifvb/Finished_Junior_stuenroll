package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_ReviewDetailsDao;
import cn.gov.hrss.ln.stuenroll.tools.DaoTools;

public class ReviewDetailsDao implements I_ReviewDetailsDao {

	@Override
	public Record searchReviewDetails(Long reviewId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM review_details WHERE review_id = ?; ");
		Record record = Db.findFirst(sql.toString(), reviewId);
		return record;
	}

	@Override
	public List<Record> searchStudentAttendence(Long studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Record> searchStudentScore(Long studentId) {
		// TODO Auto-generated method stub
		return null;	
	}

	@Before(Tx.class)
	@Override
	public int addReviewDetails(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		List<Object> paras = new ArrayList<>();
		sql.append("INSERT INTO `review_details` ( ");
		sql.append("	`id`, ");
		sql.append("	`review_id`, ");
		sql.append("	`student_info`, ");
		sql.append("	`conversion_trianing`, ");
		sql.append("	`conversion_organization`, ");
		sql.append("	`conversion_teaching`, ");
		sql.append("	`daliy_management`, ");
		sql.append("	`profession_setting`, ");
		sql.append("	`learning_cycle`, ");
		sql.append("	`organization_changes`, ");
		sql.append("	`employed`, ");
		sql.append("	`company_size`, ");
		sql.append("	`company_nature`, ");
		sql.append("	`salary_level`, ");
		sql.append("	`profession_counterparts`, ");
		sql.append("	`job_satisfaction`, ");
		sql.append("	`employed_way`, ");
		sql.append("	`employed_satisfaction`, ");
		sql.append("	`job_advice` ");
		sql.append(") ");
		sql.append("VALUES( NEXT VALUE FOR MYCATSEQ_GLOBAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ");
		
		int count = Db.update(sql.toString(), paras.toArray());
		return count;
	}

	@Override
	public String searchUnarchiveStudentImage(Long studentId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT img_id FROM enroll_img WHERE enroll_id = ?; ");
		String id = Db.queryStr(sql.toString(), studentId);
		return id;
	}

	@Override
	public String searchArchiveStudentImage(Long studentId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT img_id FROM archive_img WHERE archive_id = ?; ");
		String id = Db.queryStr(sql.toString(), studentId);
		return id;
	}

	@Override
	public Record searchArchiveReviewStudent(Long reviewId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	student_id ");
		sql.append("FROM ");
		sql.append("	review ");
		sql.append("WHERE ");
		sql.append("	id = ?; ");
		Long studentId = Db.queryLong(sql.toString(), reviewId);
		sql = new StringBuffer();
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
		sql.append("	a.`id` = ?; ");
		Record record = Db.findFirst(sql.toString(), studentId);
		if (record == null) {
			return null;
		}
		return DaoTools.castLongToString(record, "id", "profession_id", "classinfo_id", "organization_id");
	}
	
	@Override
	public Record searchUnarchiveReviewStudent(Long reviewId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	student_id ");
		sql.append("FROM ");
		sql.append("	review ");
		sql.append("WHERE ");
		sql.append("	id = ?; ");
		Long studentId = Db.queryLong(sql.toString(), reviewId);
		sql = new StringBuffer();
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
		sql.append("	e.`id` = ?; ");
		Record record = Db.findFirst(sql.toString(), studentId);
		if (record == null) {
			return null;
		}
		return DaoTools.castLongToString(record, "id", "profession_id", "classinfo_id", "organization_id");
	}

}
