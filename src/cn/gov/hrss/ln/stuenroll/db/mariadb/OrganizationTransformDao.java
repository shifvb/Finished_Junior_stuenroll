package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_OrganizationTransformDao;
import cn.gov.hrss.ln.stuenroll.tools.DaoTools;

public class OrganizationTransformDao implements I_OrganizationTransformDao {

	@Override
	public List<Record> searchOrganizations(Map<String, Object> map, long start, long length) {
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		Integer year = (Integer) map.get("year");
		Integer block = (Integer) map.get("block");
		Long organizationId = (Long) map.get("organizationId");
		ArrayList<Object> paras = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	o.id, ");
		sql.append("	o.`name`, ");
		sql.append("	o.abbreviation, ");
		sql.append("	oj.`year`, ");
		sql.append("	oj.block ");
		sql.append("FROM ");
		sql.append("	organization_join oj ");
		sql.append("LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("WHERE 1 = 1 ");
		if (name != null && name.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(name);
		}
		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append("AND o.abbreviation = ? ");
			paras.add(abbreviation);
		}
		if (year != null && year > 0) {
			sql.append("AND oj.`year` = ? ");
			paras.add(year);
		}
		if (block != null && (block == 0 || block == 1)) {
			sql.append("AND oj.block = ? ");
			paras.add(block);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND o.id = ? ");
			paras.add(organizationId);
		}
		sql.append("AND o.id != '738620600423157760' ");
		sql.append("ORDER BY ");
		sql.append("	oj.`year` DESC, o.id ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);
		
		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}
	
	@Override
	public List<Record> searchProfessionsAmount(Map<String, Object> map, long start, long length) {
		ArrayList<Object> paras = new ArrayList<>();
		Long organizationId = (Long) map.get("organizationId");
		Integer year = (Integer) map.get("year");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	id, ");
		sql.append("	`year`, ");
		sql.append("	COUNT(DISTINCT profession_id) AS professionAmount ");
		sql.append("FROM ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			o.id, ");
		sql.append("			oj.`year`, ");
		sql.append("			op.profession_id ");
		sql.append("		FROM ");
		sql.append("			organization_join oj ");
		sql.append("		LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("		LEFT JOIN organization_profession op ON oj.id = op.organization_join_id ");
		sql.append("		WHERE 1=1 ");
		if (organizationId != null && organizationId > 0) {
			sql.append("		AND o.id = ? ");
			paras.add(organizationId);
		}
		if (year != null && year > 0) {
			sql.append("		AND oj.`year` = ? ");
			paras.add(year);
		}
		sql.append("AND o.id != '738620600423157760' ");
		sql.append("		GROUP BY ");
		sql.append("			op.profession_id, ");
		sql.append("			o.id, ");
		sql.append("			oj.`year` ");
		sql.append("		ORDER BY ");
		sql.append("			oj.`year` DESC, ");
		sql.append("			o.id, ");
		sql.append("			op.profession_id ");
		sql.append("	) t ");
		sql.append("GROUP BY ");
		sql.append("	id, ");
		sql.append("	`year` ");
		sql.append("ORDER BY ");
		sql.append("	`year` DESC, ");
		sql.append("	id ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);
		
		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}
	
	@Override
	public List<Record> searchClassAmount(Map<String, Object> map, long start, long length) {
		ArrayList<Object> paras = new ArrayList<>();
		Long organizationId = (Long) map.get("organizationId");
		Integer year = (Integer) map.get("year");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	oj.organization_id AS id, ");
		sql.append("	oj.`year`, ");
		sql.append("	COUNT(cl.id) AS classAmount ");
		sql.append("FROM ");
		sql.append("	organization_join oj ");
		sql.append("LEFT JOIN classinfo cl ON oj.organization_id = cl.organization_id ");
		sql.append("AND oj.`year` = cl.`year` ");
		sql.append("WHERE 1=1 ");
		if (organizationId != null && organizationId > 0) {
			sql.append("AND oj.organization_id = ? ");
			paras.add(organizationId);
		}
		if (year != null && year > 0) {
			sql.append("AND oj.`year` = ? ");
			paras.add(year);
		}
		sql.append("AND oj.organization_id != '738620600423157760' ");
		sql.append("GROUP BY ");
		sql.append("	id, ");
		sql.append("	oj.`year` ");
		sql.append("ORDER BY ");
		sql.append("	oj.`year` DESC, ");
		sql.append("	id ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);
		
		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}
	
	@Override
	public List<Record> searchArchiveStudentsAmount(Map<String, Object> map, long start, long length) {
		ArrayList<Object> paras = new ArrayList<>();
		Long organizationId = (Long) map.get("organizationId");
		Integer year = (Integer) map.get("year");
		Integer stateId = (Integer) map.get("stateId");
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT ");
		sql.append("	oj.organization_id AS id, ");
		sql.append("	oj.`year`, ");
		sql.append("	COUNT(a.id) AS studentsAmount ");
		sql.append("FROM ");
		sql.append("	organization_join oj ");
		sql.append("LEFT JOIN archive a ON oj.organization_id = a.organization_id ");
		sql.append("AND oj.`year` = a.`year` ");
		sql.append("WHERE 1=1 ");
		if (organizationId != null && organizationId > 0) {
			sql.append("AND oj.organization_id = ? ");
			paras.add(organizationId);
		}
		if (year != null && year > 0) {
			sql.append("AND oj.`year` = ? ");
			paras.add(year);
		}
		if (stateId != null && stateId > 0 && stateId < 7) {
			sql.append("AND a.state_id = ? ");
			paras.add(stateId);
		}
		sql.append("AND oj.organization_id != '738620600423157760' ");
		sql.append("GROUP BY ");
		sql.append("	oj.`year`, ");
		sql.append("	id ");
		sql.append("ORDER BY ");
		sql.append("	oj.`year` DESC, ");
		sql.append("	id ");
		
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);
		
		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}
	
	@Override
	public List<Record> searchUnarchiveStudentsAmount(Map<String, Object> map, long start, long length) {
		ArrayList<Object> paras = new ArrayList<>();
		Long organizationId = (Long) map.get("organizationId");
		Integer year = (Integer) map.get("year");
		Integer stateId = (Integer) map.get("stateId");
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT ");
		sql.append("	oj.organization_id AS id, ");
		sql.append("	oj.`year`, ");
		sql.append("	COUNT(e.id) AS studentsAmount ");
		sql.append("FROM ");
		sql.append("	organization_join oj ");
		sql.append("LEFT JOIN enroll e ON oj.organization_id = e.organization_id ");
		sql.append("AND oj.`year` = e.`year` ");
		sql.append("WHERE 1=1 ");
		if (organizationId != null && organizationId > 0) {
			sql.append("AND oj.organization_id = ? ");
			paras.add(organizationId);
		}
		if (year != null && year > 0) {
			sql.append("AND oj.`year` = ? ");
			paras.add(year);
		}
		if (stateId != null && stateId > 0 && stateId < 7) {
			sql.append("AND e.state_id = ? ");
			paras.add(stateId);
		}
		sql.append("AND oj.organization_id != '738620600423157760' ");
		sql.append("GROUP BY ");
		sql.append("	oj.`year`, ");
		sql.append("	id ");
		sql.append("ORDER BY ");
		sql.append("	oj.`year` DESC, ");
		sql.append("	id ");
		
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);
		
		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}
	
	@Override
	public long searchOrganizationsAmount(Map<String, Object> map) {
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		Integer year = (Integer) map.get("year");
		Integer block = (Integer) map.get("block");
		Long organizationId = (Long) map.get("organizationId");
		ArrayList<Object> paras = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	organization_join oj ");
		sql.append("LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("WHERE 1 = 1 ");
		if (name != null && name.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(name);
		}
		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append("AND o.abbreviation = ? ");
			paras.add(abbreviation);
		}
		if (year != null && year > 0) {
			sql.append("AND oj.`year` = ? ");
			paras.add(year);
		}
		if (block != null && (block == 0 || block == 1)) {
			sql.append("AND oj.block = ? ");
			paras.add(block);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND o.id = ? ");
			paras.add(organizationId);
		}
		sql.append("AND oj.organization_id != '738620600423157760' ");
		sql.append("ORDER BY ");
		sql.append("	oj.`year` DESC, o.id; ");
		
		long count = Db.queryLong(sql.toString(), paras.toArray());
		return count;
	}

	@Before(Tx.class)
	@Override
	public int updateOrganization(Map<String, Object> map) {
		ArrayList<Object> paras = new ArrayList<>();
		Integer oldYear = (Integer) map.get("oldYear");
		Integer newYear = (Integer) map.get("newYear");
		Integer block = (Integer) map.get("block");
		Long organizationId = (Long) map.get("organizationId");
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE organization_join oj SET oj.`year` = ? ");
		paras.add(newYear);
		if (block != null && (block == 0 || block == 1)) {
			sql.append(", oj.block = ? ");
			paras.add(block);
		}
		sql.append("WHERE oj.`year` = ? ");
		paras.add(oldYear);
		sql.append("AND oj.organization_id = ?; ");
		paras.add(organizationId);
		int count = Db.update(sql.toString(), paras.toArray());
		return count;
	}

	@Before(Tx.class)
	@Override
	public long deleteOrganizations(Long[] id, int year) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	organization_join ");
		sql.append("WHERE ");
		sql.append("`year` = ? ");
		sql.append("AND organization_id IN ( ");
		for (int i = 0; i < id.length; i++) {
			sql.append("?");
			if (i != id.length - 1) {
				sql.append(", ");
			}
		}
		sql.append("); ");
		long count = Db.update(sql.toString(), year, id);
		return count;
	}
	
	@Before(Tx.class)
	@Override
	public long deleteOrganization(Long id, int year) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	organization_join ");
		sql.append("WHERE ");
		sql.append("`year` = ? ");
		sql.append("AND organization_id = ?; ");
		long count = Db.update(sql.toString(), year, id);
		return count;
	}

	@Override
	public int addOrganizationProfession(long organizationId, long professionId, int year) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `organization_profession` ( ");
		sql.append("	`id`, ");
		sql.append("	`organization_join_id`, ");
		sql.append("	`profession_id` ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT ");
		sql.append("		VALUE ");
		sql.append("			FOR MYCATSEQ_GLOBAL, ");
		sql.append("			( ");
		sql.append("				SELECT ");
		sql.append("					oj.id ");
		sql.append("				FROM ");
		sql.append("					organization_join oj ");
		sql.append("				WHERE ");
		sql.append("					oj.organization_id = ? ");
		sql.append("				AND oj.`year` = ? ");
		sql.append("			), ");
		sql.append("			?); ");

		int count = Db.update(sql.toString(), organizationId, year, professionId);
		return count;
	}

	@Override
	public int deleteOrganizationProfession(long organizationId, long professionId, int year) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	organization_profession ");
		sql.append("WHERE ");
		sql.append("	profession_id = ? ");
		sql.append("AND organization_join_id = ( ");
		sql.append("	SELECT ");
		sql.append("		oj.id ");
		sql.append("	FROM ");
		sql.append("		organization_join oj ");
		sql.append("	WHERE ");
		sql.append("		oj.organization_id = ? ");
		sql.append("	AND oj.`year` = ? ");
		sql.append(");");

		int count = Db.update(sql.toString(), professionId, organizationId, year);
		return count;
	}
	
	@Override
	public List<Record> searchOrganizationsJoinWithProfessionByYearAtDropdown(Map<String, Object> map) {
		Long professionId = (Long) map.get("professionId");
		Integer year = (Integer) map.get("year");
		Integer block = (Integer) map.get("block");
		List<Object> paras = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	o.id, ");
		sql.append("	o.`name` ");
		sql.append("FROM ");
		sql.append("	organization o ");
		sql.append("JOIN organization_join oj ON o.id = oj.organization_id ");
		sql.append("JOIN organization_profession op ON oj.id = op.organization_join_id ");
		sql.append("JOIN profession p ON op.profession_id = p.id ");
		sql.append("WHERE 1=1 ");
		if (professionId != null && professionId.longValue() > 0) {
			sql.append("AND p.id = ? ");
			paras.add(professionId);
		}
		if (block != null && block.intValue() > 0) {
			sql.append("AND oj.block = ? ");
			paras.add(block);
		}
		if (year != null && year.intValue() > 0) {
			sql.append("AND oj.`year` = ? ");
			paras.add(year);
		}
		sql.append("AND o.id != '738620600423157760' ");
		sql.append(";");
		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

}
