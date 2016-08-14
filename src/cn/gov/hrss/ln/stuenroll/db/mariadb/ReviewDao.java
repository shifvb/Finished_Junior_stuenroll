package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_ReviewDao;
import cn.gov.hrss.ln.stuenroll.tools.DaoTools;

/**
 * 回访记录数据访问接口实现
 * 
 * @author LiYadong
 * @version 1.0
 */
public class ReviewDao implements I_ReviewDao {

	@Override
	public List<Record> searchReviewInfo(Map<String, Object> map, Long start, Long length) {

		List<Object> paras = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		Long reviewId = (Long) map.get("reviewId");
		String reviewer = (String) map.get("reviewer");
		String reviewee = (String) map.get("reviewee");
		String duration = (String) map.get("duration");
		String satisfaction = (String) map.get("satisfaction");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		Long classInfoId = (Long) map.get("classInfoId");
		Long studentId = (Long) map.get("studentId");
		Integer reviewResult = (Integer) map.get("reviewResult");

		sql.append("SELECT ");
		sql.append("	re.id, ");
		sql.append("	re.reviewee, ");
		sql.append("	re.time, ");
		sql.append("	re.duration, ");
		sql.append("	re.reviewer, ");
		sql.append("	re.satisfaction, ");
		sql.append("	re.content ");
		sql.append("FROM ");
		sql.append("	review re ");
		sql.append("WHERE 1=1 ");

		if (reviewId != null && reviewId.longValue() > 0) {
			sql.append("AND re.id = ? ");
			paras.add(reviewId);
		}
		if (reviewer != null && reviewer.length() > 0) {
			sql.append("AND re.reviewer = ? ");
			paras.add(reviewer);
		}
		if (reviewee != null && reviewee.length() > 0) {
			sql.append("AND re.reviewee = ? ");
			paras.add(reviewee);
		}
		if (duration != null && duration.length() > 0) {
			sql.append("AND re.duration = ? ");
			paras.add(duration);
		}
		if (satisfaction != null && satisfaction.length() > 0) {
			sql.append("AND re.satisfaction = ? ");
			paras.add(satisfaction);
		}
		if (startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0) {
			sql.append("AND re.tiem BETWEEN ? AND ? ");
			paras.add(startDate);
			paras.add(endDate);
		}
		if (classInfoId != null && classInfoId.longValue() > 0) {
			sql.append("AND re.classinfo_id = ? ");
			paras.add(classInfoId);
		}
		if (studentId != null && studentId.longValue() > 0) {
			sql.append("AND re.student_id = ? ");
			paras.add(studentId);
		}
		if (reviewResult != null && reviewResult.intValue() > 0) {
			sql.append("AND re.reviewResult = ? ");
			paras.add(reviewResult);
		}
		sql.append("ORDER BY re.time DESC, re.id, re.duration DESC ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);
		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public long searchReviewCounts(Map<String, Object> map) {
		List<Object> paras = new ArrayList<>();
		StringBuffer sql = new StringBuffer();

		Long id = (Long) map.get("id");
		String reviewer = (String) map.get("reviewer");
		String reviewee = (String) map.get("reviewee");
		String duration = (String) map.get("duration");
		String satisfaction = (String) map.get("satisfaction");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		Long classInfoId = (Long) map.get("classInfoId");
		Long studentId = (Long) map.get("studentId");
		Integer reviewResult = (Integer) map.get("reviewResult");

		sql.append("SELECT ");
		sql.append("	COUNT(re.id) ");
		sql.append("FROM ");
		sql.append("	review re ");
		sql.append("WHERE 1=1 ");

		if (id != null && id.longValue() > 0) {
			sql.append("AND re.id = ? ");
			paras.add(id);
		}
		if (reviewer != null && reviewer.length() > 0) {
			sql.append("AND re.reviewer = ? ");
			paras.add(reviewer);
		}
		if (reviewee != null && reviewee.length() > 0) {
			sql.append("AND re.reviewee = ? ");
			paras.add(reviewee);
		}
		if (duration != null && duration.length() > 0) {
			sql.append("AND re.duration = ? ");
			paras.add(duration);
		}
		if (satisfaction != null && satisfaction.length() > 0) {
			sql.append("AND re.satisfaction = ? ");
			paras.add(satisfaction);
		}
		if (startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0) {
			sql.append("AND re.tiem BETWEEN ? AND ? ");
			paras.add(startDate);
			paras.add(endDate);
		}
		if (classInfoId != null && classInfoId.longValue() > 0) {
			sql.append("AND re.classinfo_id = ? ");
			paras.add(classInfoId);
		}
		if (studentId != null && studentId.longValue() > 0) {
			sql.append("AND re.student_id = ? ");
			paras.add(studentId);
		}
		if (reviewResult != null && reviewResult.intValue() > 0) {
			sql.append("AND re.reviewResult = ? ");
			paras.add(reviewResult);
		}
		sql.append("; ");
		long count = Db.queryLong(sql.toString(), paras.toArray());
		return count;
	}
	
	@Override
	public List<Record> searchReviewCountsByClass(Map<String, Object> map, Long start, Long length) {
		List<Object> paras = new ArrayList<>();
		StringBuffer sql = new StringBuffer();

		Long id = (Long) map.get("id");
		String reviewer = (String) map.get("reviewer");
		String reviewee = (String) map.get("reviewee");
		String duration = (String) map.get("duration");
		String satisfaction = (String) map.get("satisfaction");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		Long classInfoId = (Long) map.get("classInfoId");
		Long studentId = (Long) map.get("studentId");
		Integer reviewResult = (Integer) map.get("reviewResult");
		Long orgainzationId = (Long) map.get("orgainzationId");
		Long professionId = (Long) map.get("professionId");
		Integer year = (Integer) map.get("year");

		sql.append("SELECT ");
		sql.append("	ci.id, ");
		sql.append("	COUNT(re.id) AS reviewCount ");
		sql.append("FROM ");
		sql.append("	review re ");
		sql.append("RIGHT JOIN classinfo ci ON re.classinfo_id = ci.id ");
		sql.append("WHERE 1=1 ");

		if (id != null && id.longValue() > 0) {
			sql.append("AND re.id = ? ");
			paras.add(id);
		}
		if (reviewer != null && reviewer.length() > 0) {
			sql.append("AND re.reviewer = ? ");
			paras.add(reviewer);
		}
		if (reviewee != null && reviewee.length() > 0) {
			sql.append("AND re.reviewee = ? ");
			paras.add(reviewee);
		}
		if (duration != null && duration.length() > 0) {
			sql.append("AND re.duration = ? ");
			paras.add(duration);
		}
		if (satisfaction != null && satisfaction.length() > 0) {
			sql.append("AND re.satisfaction = ? ");
			paras.add(satisfaction);
		}
		if (startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0) {
			sql.append("AND re.tiem BETWEEN ? AND ? ");
			paras.add(startDate);
			paras.add(endDate);
		}
		if (classInfoId != null && classInfoId.longValue() > 0) {
			sql.append("AND re.classinfo_id = ? ");
			paras.add(classInfoId);
		}
		if (studentId != null && studentId.longValue() > 0) {
			sql.append("AND re.student_id = ? ");
			paras.add(studentId);
		}
		if (reviewResult != null && reviewResult.intValue() > 0) {
			sql.append("AND re.reviewResult = ? ");
			paras.add(reviewResult);
		}
		if (orgainzationId != null && orgainzationId.longValue() > 0) {
			sql.append("AND ci.organization_id = ? ");
			paras.add(orgainzationId);
		}
		if (professionId != null && professionId.longValue() > 0) {
			sql.append("AND ci.profession_id = ? ");
			paras.add(professionId);
		}
		if (year != null && year.intValue() > 0) {
			sql.append("AND ci.`year` = ? ");
			paras.add(year);
		}
		sql.append("AND ci.organization_id != '738620600423157760' ");
		sql.append("GROUP BY ci.id ");
		sql.append("ORDER BY ci.id DESC ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);

		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public long deleteReviewInfo(Long[] id) {
		StringBuffer sql = new StringBuffer();
		List<Long> paras = Arrays.asList(id);
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	`review` ");
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
	public List<Record> searchReviewPeopleAmountInClass(Map<String, Object> map, Long start, Long length) {
		StringBuffer sql = new StringBuffer();
		List<Object> paras = new ArrayList<>();
		Long classInfoId = (Long) map.get("classInfoId");
		Long studentId = (Long) map.get("studentId");
		String reviewer = (String) map.get("reviewer");
		String reviewee = (String) map.get("reviewee");
		String duration = (String) map.get("duration");
		String satisfaction = (String) map.get("satisfaction");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		Integer reviewResult = (Integer) map.get("reviewResult");
		Long orgainzationId = (Long) map.get("orgainzationId");
		Long professionId = (Long) map.get("professionId");
		Integer year = (Integer) map.get("year");

		sql.append("SELECT ");
		sql.append("	ci.id, ");
		sql.append("	COUNT(DISTINCT re.student_id) AS reviewAmount ");
		sql.append("FROM ");
		sql.append("	review re ");
		sql.append("RIGHT JOIN classinfo ci ON re.classinfo_id = ci.id ");
		sql.append("WHERE 1=1 ");
		if (classInfoId != null && classInfoId.longValue() > 0) {
			sql.append("AND ci.id = ? ");
			paras.add(classInfoId);
		}
		if (studentId != null && studentId.longValue() > 0) {
			sql.append("AND re.student_id = ? ");
			paras.add(studentId);
		}
		if (reviewer != null && reviewer.length() > 0) {
			sql.append("AND re.reviewer = ? ");
			paras.add(reviewer);
		}
		if (reviewee != null && reviewee.length() > 0) {
			sql.append("AND re.reviewee = ? ");
			paras.add(reviewee);
		}
		if (duration != null && duration.length() > 0) {
			sql.append("AND re.duration = ? ");
			paras.add(duration);
		}
		if (satisfaction != null && satisfaction.length() > 0) {
			sql.append("AND re.satisfaction = ? ");
			paras.add(satisfaction);
		}
		if (startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0) {
			sql.append("AND re.tiem BETWEEN ? AND ? ");
			paras.add(startDate);
			paras.add(endDate);
		}
		if (reviewResult != null && reviewResult.intValue() > 0) {
			sql.append("AND re.reviewResult = ? ");
			paras.add(reviewResult);
		}
		if (orgainzationId != null && orgainzationId.longValue() > 0) {
			sql.append("AND ci.organization_id = ? ");
			paras.add(orgainzationId);
		}
		if (professionId != null && professionId.longValue() > 0) {
			sql.append("AND ci.profession_id = ? ");
			paras.add(professionId);
		}
		if (year != null && year.intValue() > 0) {
			sql.append("AND ci.`year` = ? ");
			paras.add(year);
		}
		sql.append("AND ci.organization_id != '738620600423157760' ");
		sql.append("GROUP BY ci.id ");
		sql.append("ORDER BY ci.id ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);
		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public long addReviewInfo(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		List<Object> paras = new ArrayList<>();
		String reviewer = (String) map.get("reviewer");
		String reviewee = (String) map.get("reviewee");
		String time = (String) map.get("time");
		Long duration = (Long) map.get("duration");
		String satisfaction = (String) map.get("satisfaction");
		String content = (String) map.get("content");
		Long classInfoId = (Long) map.get("classInfoId");
		Long studentId = (Long) map.get("studentId");
		Integer reviewResult = (Integer) map.get("reviewResult");

		sql.append("INSERT INTO `review` ( ");
		sql.append("	`id`, ");
		sql.append("	`reviewer`, ");
		sql.append("	`reviewee`, ");
		sql.append("	`time`, ");
		sql.append("	`duration`, ");
		sql.append("	`satisfaction`, ");
		sql.append("	`reviewResult`, ");
		sql.append("	`content`, ");
		sql.append("	`classinfo_id`, ");
		sql.append("	`student_id`, ");
		sql.append("	`sharding` ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT VALUE FOR MYCATSEQ_GLOBAL, ");
		sql.append("		?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ");

		Date date = new Date();
		paras.add(reviewer);
		paras.add(reviewee);
		if (time != null && time.length() > 0) {
			paras.add(time);
		} else {
			paras.add(DaoTools.generateSQLDateFromJavaDate(date, "yyyy-MM-dd hh:mm:ss"));
		}
		paras.add(duration);
		paras.add(satisfaction);
		paras.add(reviewResult);
		paras.add(content);
		paras.add(classInfoId);
		paras.add(studentId);
		Long sharding = date.getTime();
		paras.add(sharding);

		int count = Db.update(sql.toString(), paras.toArray());
		Long reviewId = 0L;
		if (count == 1) {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT id FROM review WHERE `sharding` = ? ODERE BY `time`; ");
			Record record = Db.findFirst(SQL.toString(), sharding);
			reviewId = record.getLong("id");
		}
		return reviewId;
	}

	@Override
	public int updateReviewInfo(Map<String, Object> map, Long reviewId) {
		StringBuffer sql = new StringBuffer();
		List<Object> paras = new ArrayList<>();
		String reviewer = (String) map.get("reviewer");
		String duration = (String) map.get("duration");
		String satisfaction = (String) map.get("satisfaction");
		String content = (String) map.get("content");

		sql.append("UPDATE `review` ");
		sql.append("SET `id` = ?, ");
		paras.add(reviewId);
		if (reviewer != null && reviewer.length() > 0) {
			sql.append(" `reviewer` = ?, ");
			paras.add(reviewer);
		}
		if (duration != null && duration.length() > 0) {
			sql.append(" `duration` = ?, ");
			paras.add(duration);
		}
		if (satisfaction != null && satisfaction.length() > 0) {
			sql.append(" `satisfaction` = ?, ");
			paras.add(satisfaction);
		}
		if (content != null) {
			sql.append(" `content` = ?, ");
			paras.add(content);
		}
		sql.append("WHERE ");
		sql.append("	(`id` = ?); ");
		paras.add(reviewId);
		
		int count = Db.update(sql.toString(), paras.toArray());
		return count;
	}

	@Override
	public List<Record> searchAllReview() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM `review` ORDER BY id, `time` DESC; ");
		List<Record> records = Db.find(sql.toString());
		return DaoTools.castLongToString(records, "id", "classinfo_id", "student_id");
	}

}
