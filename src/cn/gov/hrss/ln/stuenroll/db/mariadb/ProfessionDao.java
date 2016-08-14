package cn.gov.hrss.ln.stuenroll.db.mariadb;
//最新

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_ProfessionDao;
import cn.gov.hrss.ln.stuenroll.tools.DaoTools;

public class ProfessionDao implements I_ProfessionDao {

	/**
	 * 在下拉框中根据年份查找相关机构的专业
	 * @param year
	 * @return
	 */
	@Override
	public List<Record> searchProfessionInYearAtDropDown(int year) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT DISTINCT ");
		sql.append("	t.id, ");
		sql.append("	t.`name` ");
		sql.append("FROM ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			CAST(p.id AS CHAR) AS id, ");
		sql.append("			p.`name` ");
		sql.append("		FROM ");
		sql.append("			organization o ");
		sql.append("		JOIN organization_join oj ON o.id = oj.organization_id ");
		sql.append("		JOIN organization_profession op ON op.organization_join_id = oj.id ");
		sql.append("		JOIN profession p ON p.id = op.profession_id ");
		sql.append("		WHERE ");
		sql.append("			oj.`year` = ? ");
		sql.append("		AND oj.block = FALSE ");
		sql.append("		ORDER BY ");
		sql.append("			p.id ");
		sql.append("	) t; ");
		List<Record> list=Db.find(sql.toString(),year);
		return list;
	}
	@Override
	public List<Record> searchProfessionOrganization(HashMap map,long start, long length) {
		ArrayList param = new ArrayList<>();
		Long organizationId = (Long)map.get("organizationId");
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	t.id, ");	
		sql.append("	t.`name`, ");
		sql.append("	COUNT(t.organization_id)AS organizationAmount ");
		sql.append("FROM ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			p.id, ");
		sql.append("			p.`name`, ");
		sql.append("			o.id AS organization_id ");
		sql.append("		FROM ");
		sql.append("			profession p ");
		sql.append("		LEFT JOIN organization_profession op ON op.profession_id = p.id ");
		sql.append("		LEFT JOIN organization_join oj ON op.organization_join_id = oj.id ");
		sql.append("		LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("		WHERE	1 = 1 ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" 		AND o.id = ? ");
			param.add(organizationId);
		}
		sql.append("		GROUP BY p.id,o.id ");
		sql.append("		ORDER BY p.id ");
		sql.append("	) t ");
		sql.append("GROUP BY	t.id ");
		sql.append("LIMIT ?,?; ");
		
		param.add(start);
		param.add(length);
		List<Record> list=Db.find(sql.toString(),param.toArray());
		for (Record record : list) {
			record.set("id", record.getLong("id").toString());
		}
		return list;
	}

	@Override
	public List<Record> searchProfessionYearAmount(HashMap map,long start, long length) {
		ArrayList param = new ArrayList<>();
		Long organizationId = (Long)map.get("organizationId");
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	tt.id,COUNT(tt.`year`) AS yearAmount ");
		sql.append("FROM ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			t.id,t.`year` ");
		sql.append("		FROM ");
		sql.append("			( ");
		sql.append("				SELECT ");
		sql.append("					p.id,oj.`year` ");
		sql.append("				FROM ");
		sql.append("					profession p ");
		sql.append("				LEFT JOIN organization_profession op ON op.profession_id = p.id ");
		sql.append("				LEFT JOIN organization_join oj ON op.organization_join_id = oj.id ");
		sql.append("				LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("				WHERE	1 = 1 ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append("				AND o.id = ? ");
			param.add(organizationId);
		}
		sql.append("			)t ");
		sql.append("		GROUP BY	t.id,	t.`year` ");
		sql.append("		ORDER BY	t.id ");
		sql.append("	) tt ");
		sql.append("GROUP BY tt.id ");
		sql.append("LIMIT ?,?; ");

		param.add(start);
		param.add(length);
		List<Record> list=Db.find(sql.toString(),param.toArray());
		for (Record record : list) {
			record.set("id", record.getLong("id").toString());
		}
		return list;
}

	@Override
	public List<Record> searchProfessionClassAmount(HashMap map,long start, long length) {
		ArrayList param = new ArrayList<>();
		Long organizationId = (Long)map.get("organizationId");
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	p.id, ");
		sql.append("	COUNT(cl.id)AS classAmount ");
		sql.append("FROM ");
		sql.append("	profession p ");
		sql.append("LEFT JOIN classinfo cl ON cl.profession_id = p.id ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND cl.organization_id=? ");
			param.add(organizationId);
		}
		sql.append("LEFT JOIN organization o ON o.id = cl.organization_id ");
		sql.append("WHERE ");
		sql.append("	p.id IN ( ");
		sql.append("		SELECT ");
		sql.append("			p.id ");
		sql.append("		FROM ");
		sql.append("			profession p ");
		sql.append("		LEFT JOIN organization_profession op ON op.profession_id = p.id ");
		sql.append("		LEFT JOIN organization_join oj ON op.organization_join_id = oj.id ");
		sql.append("		LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("		WHERE 1=1 ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND o.id=? ");
			param.add(organizationId);
		}
		sql.append("	) ");
		sql.append("GROUP BY p.id ");
		sql.append("ORDER BY p.id ");
		sql.append("LIMIT ?,?; ");
		
		param.add(start);
		param.add(length);
		List<Record> list=Db.find(sql.toString(),param.toArray());
		for (Record record : list) {
			record.set("id", record.getLong("id").toString());
		}
		return list;
	}

	@Override
	public List<Record> searchProfessionStudentAmountInArchive(HashMap map, long start, long length) {
		ArrayList param = new ArrayList<>();
		Long organizationId = (Long)map.get("organizationId");
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	p.id, ");
		sql.append("	COUNT(a.id)AS studentAmount ");
		sql.append("FROM ");
		sql.append("	profession p ");
		sql.append("LEFT JOIN archive a ON a.profession_id = p.id ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND a.organization_id=? ");
			param.add(organizationId);
		}
		sql.append("LEFT JOIN organization o ON o.id = a.organization_id ");
		sql.append("WHERE ");
		sql.append("	p.id IN ( ");
		sql.append("		SELECT ");
		sql.append("			p.id ");
		sql.append("		FROM ");
		sql.append("			profession p ");
		sql.append("		LEFT JOIN organization_profession op ON op.profession_id = p.id ");
		sql.append("		LEFT JOIN organization_join oj ON op.organization_join_id = oj.id ");
		sql.append("		LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("		WHERE 1=1 ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND o.id=? ");
			param.add(organizationId);
		}
		sql.append("	) ");
		sql.append("GROUP BY p.id ");
		sql.append("ORDER BY p.id ");
		sql.append("LIMIT ?,?; ");
		
		param.add(start);
		param.add(length);
		List<Record> list=Db.find(sql.toString(),param.toArray());
		for (Record record : list) {
			record.set("id", record.getLong("id").toString());
		}
		return list;
	}
	
	@Override
	public List<Record> searchProfessionStudentAmountGetAJobInArchive(HashMap map,long start, long length) {
		ArrayList param = new ArrayList<>();
		Long organizationId = (Long)map.get("organizationId");
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	p.id, ");
		sql.append("	COUNT(a.id)AS studentAmountGetAJob ");
		sql.append("FROM ");
		sql.append("	profession p ");
		sql.append("LEFT JOIN archive a ON a.profession_id = p.id ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND a.organization_id=? ");
			param.add(organizationId);
		}
		sql.append(" AND a.state_id = 5 ");
		sql.append("LEFT JOIN organization o ON o.id = a.organization_id ");
		sql.append("WHERE ");
		sql.append("	p.id IN ( ");
		sql.append("		SELECT ");
		sql.append("			p.id ");
		sql.append("		FROM ");
		sql.append("			profession p ");
		sql.append("		LEFT JOIN organization_profession op ON op.profession_id = p.id ");
		sql.append("		LEFT JOIN organization_join oj ON op.organization_join_id = oj.id ");
		sql.append("		LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("		WHERE 1=1 ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND o.id=? ");
			param.add(organizationId);
		}
		sql.append("	) ");
		sql.append("GROUP BY p.id ");
		sql.append("ORDER BY p.id ");
		sql.append("LIMIT ?,?; ");
		
		param.add(start);
		param.add(length);
		List<Record> list=Db.find(sql.toString(),param.toArray());
		for (Record record : list) {
			record.set("id", record.getLong("id").toString());
		}
		return list;
	}
	@Override
	public List<Record> searchProfessionClassAmountInEnroll(HashMap map,long start, long length) {
		ArrayList param = new ArrayList<>();
		Long organizationId = (Long)map.get("organizationId");
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	p.id, ");
		sql.append("	COUNT(cl.id)AS classAmount ");
		sql.append("FROM ");
		sql.append("	profession p ");
		sql.append("LEFT JOIN enroll e ON a.profession_id = p.id ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND e.organization_id=? ");
			param.add(organizationId);
		}
		sql.append("LEFT JOIN organization o ON o.id = e.organization_id ");
		sql.append("WHERE ");
		sql.append("	p.id IN ( ");
		sql.append("		SELECT ");
		sql.append("			p.id ");
		sql.append("		FROM ");
		sql.append("			profession p ");
		sql.append("		LEFT JOIN organization_profession op ON op.profession_id = p.id ");
		sql.append("		LEFT JOIN organization_join oj ON op.organization_join_id = oj.id ");
		sql.append("		LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("		WHERE 1=1 ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND o.id=? ");
			param.add(organizationId);
		}
		sql.append("	) ");
		sql.append("GROUP BY p.id ");
		sql.append("ORDER BY p.id ");
		sql.append("LIMIT ?,?; ");
		
		param.add(start);
		param.add(length);
		List<Record> list=Db.find(sql.toString(),param.toArray());
		for (Record record : list) {
			record.set("id", record.getLong("id").toString());
		}
		return list;
	}

	@Override
	public List<Record> searchProfessionStudentAmountInEnroll(HashMap map, long start, long length) {
		ArrayList param = new ArrayList<>();
		Long organizationId = (Long)map.get("organizationId");
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	p.id, ");
		sql.append("	COUNT(e.id)AS studentAmount ");
		sql.append("FROM ");
		sql.append("	profession p ");
		sql.append("LEFT JOIN enroll e ON e.profession_id = p.id ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND e.organization_id=? ");
			param.add(organizationId);
		}
		sql.append("LEFT JOIN organization o ON o.id = e.organization_id ");
		sql.append("WHERE ");
		sql.append("	p.id IN ( ");
		sql.append("		SELECT ");
		sql.append("			p.id ");
		sql.append("		FROM ");
		sql.append("			profession p ");
		sql.append("		LEFT JOIN organization_profession op ON op.profession_id = p.id ");
		sql.append("		LEFT JOIN organization_join oj ON op.organization_join_id = oj.id ");
		sql.append("		LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("		WHERE 1=1 ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND o.id=? ");
			param.add(organizationId);
		}
		sql.append("	) ");
		sql.append("GROUP BY p.id ");
		sql.append("ORDER BY p.id ");
		sql.append("LIMIT ?,?; ");
		
		param.add(start);
		param.add(length);
		List<Record> list=Db.find(sql.toString(),param.toArray());
		for (Record record : list) {
			record.set("id", record.getLong("id").toString());
		}
		return list;
	}
	
	@Override
	public List<Record> searchProfessionStudentAmountGetAJobInEnroll(HashMap map,long start, long length) {
		ArrayList param = new ArrayList<>();
		Long organizationId = (Long)map.get("organizationId");
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("	p.id, ");
		sql.append("	COUNT(e.id)AS studentAmountGetAJob ");
		sql.append("FROM ");
		sql.append("	profession p ");
		sql.append("LEFT JOIN enroll e ON e.profession_id = p.id ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND e.organization_id=? ");
			param.add(organizationId);
		}
		sql.append(" AND e.state_id = 5 ");
		sql.append("LEFT JOIN organization o ON o.id = e.organization_id ");
		sql.append("WHERE ");
		sql.append("	p.id IN ( ");
		sql.append("		SELECT ");
		sql.append("			p.id ");
		sql.append("		FROM ");
		sql.append("			profession p ");
		sql.append("		LEFT JOIN organization_profession op ON op.profession_id = p.id ");
		sql.append("		LEFT JOIN organization_join oj ON op.organization_join_id = oj.id ");
		sql.append("		LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("		WHERE 1=1 ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append(" AND o.id=? ");
			param.add(organizationId);
		}
		sql.append("	) ");
		sql.append("GROUP BY p.id ");
		sql.append("ORDER BY p.id ");
		sql.append("LIMIT ?,?; ");
		
		param.add(start);
		param.add(length);
		List<Record> list=Db.find(sql.toString(),param.toArray());
		for (Record record : list) {
			record.set("id", record.getLong("id").toString());
		}
		return list;
	}
	
	@Override
	public long searchProfessionCount(HashMap map) {
		ArrayList param = new ArrayList<>();
		Long organizationId = (Long)map.get("organizationId");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	count(*) ");
		sql.append("FROM ");
		sql.append("	profession p ");
		sql.append("WHERE ");
		sql.append("	p.id IN ( ");
		sql.append("		SELECT ");
		sql.append("			p.id ");
		sql.append("		FROM ");
		sql.append("			profession p ");
		sql.append("		LEFT JOIN organization_profession op ON op.profession_id = p.id ");
		sql.append("		LEFT JOIN organization_join oj ON op.organization_join_id = oj.id ");
		sql.append("		LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("		WHERE	1 = 1 ");
		if(organizationId != null && organizationId.longValue() > 0){
			sql.append("AND o.id = ? ");
			param.add(organizationId);
		}
		sql.append("	); ");
		long count = Db.queryLong(sql.toString(),param.toArray());
		return count;
	}
	@Override
	public int addProfession(HashMap map) {
		ArrayList param = new ArrayList();
		//TODO 获取前台传回来的值
		String name = (String)map.get("name");
		StringBuffer sql=new StringBuffer();
		sql.append("INSERT INTO profession (`id`, `name`) ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT ");
		sql.append("		VALUE ");
		sql.append("			FOR MYCATSEQ_GLOBAL, ");
		if(name != null && name.length() > 0){
			sql.append("			? ");			
		}
		sql.append("	); ");
		param.add(name);
		int count = Db.update(sql.toString(),param.toArray());
		return count;
	}

	/**
	 * 修改专业信息
	 */
	@Override
	public int updateProfession(HashMap map) {
		ArrayList param = new ArrayList();
		String name = (String)map.get("name");
		Long professionId = (Long)map.get("professionId");
		param.add(name);
		param.add(professionId);
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE profession p ");
		if(name != null && name.length() > 0){
			sql.append("SET p.`name` = ? ");			
		}
		sql.append("WHERE ");
		sql.append("	p.id = ?; ");		
		int count = Db.update(sql.toString(),param.toArray());
		return count;
	}

	@Override
	public int deleteProfessionById(Long[] id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	profession ");
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
	public String searchName(long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	`name` ");
		sql.append("FROM ");
		sql.append("	profession ");
		sql.append("WHERE ");
		sql.append("    id = ?; ");
		String name = Db.queryStr(sql.toString(),id);
		return name;
	}
	
	public List<Record> searchAllProfession() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	CAST (p.id AS CHAR) AS id, ");
		sql.append("	p.`name` AS name ");
		sql.append("FROM ");
		sql.append("	profession p ");
		sql.append("ORDER BY p.id; ");
		List<Record> list = Db.find(sql.toString());
		return list;
	}
	
	@Override
	public List<Record> searchProfessionsWithConditions(Map<String, Object> map, Long start, Long length) {
		Long organizationId = (Long) map.get("organizationId");
		Long professionId = (Long) map.get("professionId");
		Integer year = (Integer) map.get("year");
		StringBuffer sql = new StringBuffer();
		List<Object> paras = new ArrayList<>();
		sql.append("SELECT ");
		sql.append("	IFNULL(o.id,0) AS id, ");
		sql.append("	p.`name`, ");
		sql.append("	IFNULL(oj.`year`,0) AS `year`, ");
		sql.append("	p.id AS professionId ");
		sql.append("FROM ");
		sql.append("	profession p ");
		sql.append("LEFT JOIN organization_profession op ON op.profession_id = p.id ");
		sql.append("LEFT JOIN organization_join oj ON op.organization_join_id = oj.id ");
		sql.append("LEFT JOIN organization o ON oj.organization_id = o.id ");
		sql.append("WHERE 1=1 ");
		if (organizationId != null && organizationId.longValue() > 0) {
			sql.append("AND o.id = ? ");
			paras.add(organizationId);
		}
		if (professionId != null && professionId.longValue() > 0) {
			sql.append("AND p.id = ? ");
			paras.add(professionId);
		}
		if (year != null && year.intValue() > 0) {
			sql.append("AND oj.`year` = ? ");
			paras.add(year);
		}
		sql.append("GROUP BY p.id, o.id, oj.`year` ");
		sql.append("ORDER BY p.id, oj.`year` DESC, o.id  ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);
		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id", "professionId");
	}
	/**
	 * 导入专业信息
	 */
	@Override
	public int importProfession(Object... objects) {
		//TODO 获取前台传回来的值
		StringBuffer sql=new StringBuffer();
		sql.append("INSERT INTO profession (`id`, `name`) ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		?, ? ); ");
		int count = Db.update(sql.toString(), objects);
		return count;
	}
}
