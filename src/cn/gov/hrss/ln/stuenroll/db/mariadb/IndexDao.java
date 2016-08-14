package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_IndexDao;
import cn.gov.hrss.ln.stuenroll.plugin.MongoKit;

public class IndexDao implements I_IndexDao {

	@Override
	public List<Record> searchHotMajor(long oid) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS amount, ");
		sql.append("	e.profession_id, ");
		sql.append("  p.`name` ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("JOIN profession p ON p.id = e.profession_id ");
		if (oid != -1) {
			sql.append("WHERE ");
			sql.append("	organization_id = ? ");
			sql.append("	AND YEAR(e.create_time) = YEAR(NOW()) ");
			sql.append("GROUP BY ");
			sql.append("	profession_id ");
			sql.append("ORDER BY ");
			sql.append("	amount DESC ");
			sql.append("LIMIT 5 ");
			records = Db.find(sql.toString(),oid);
		}
		else {
			sql.append("WHERE  YEAR(e.create_time) = YEAR(NOW()) ");
			sql.append("GROUP BY ");
			sql.append("	profession_id ");
			sql.append("ORDER BY ");
			sql.append("	amount DESC ");
			records = Db.find(sql.toString());
		}
		return records;
		
	}
	
	@Override
	public List<Record> searchHotMajorArchive(long oid) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS amount, ");
		sql.append("	e.profession_id, ");
		sql.append("  p.`name` ");
		sql.append("FROM ");
		sql.append("	archive e ");
		sql.append("JOIN profession p ON p.id = e.profession_id ");
		if (oid != -1) {
			sql.append("WHERE ");
			sql.append("	organization_id = ? ");
			sql.append("	AND YEAR(e.create_time) = YEAR(NOW()) ");
			sql.append("GROUP BY ");
			sql.append("	profession_id ");
			sql.append("ORDER BY ");
			sql.append("	amount DESC ");
			sql.append("LIMIT 5 ");
			records = Db.find(sql.toString(),oid);
		}
		else {
			sql.append("WHERE  YEAR(e.create_time) = YEAR(NOW()) ");
			sql.append("GROUP BY ");
			sql.append("	profession_id ");
			sql.append("ORDER BY ");
			sql.append("	amount DESC ");
			records = Db.find(sql.toString());
		}
		return records;
		
	}

	@Override
	public List<Record> searchHotPlace(long oid) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT COUNT(*) AS amount,place FROM enroll e ");
		if (oid != -1) {
			sql.append("WHERE organization_id = ? AND YEAR(e.create_time) = YEAR(NOW()) AND e.place IS NOT NULL GROUP BY place ");
			sql.append("LIMIT 5 ");
			records = Db.find(sql.toString(),oid);
		}
		else {
			sql.append("WHERE  YEAR(e.create_time) = YEAR(NOW()) AND e.place IS NOT NULL ");
			sql.append(" GROUP BY place ");
			records = Db.find(sql.toString());
		}
		return records;
	}
	
	@Override
	public List<Record> searchHotPlaceArchive(long oid) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT COUNT(*) AS amount,place FROM archive e ");
		if (oid != -1) {
			sql.append("WHERE organization_id = ? AND YEAR(e.create_time) = YEAR(NOW()) AND e.place IS NOT NULL GROUP BY place ");
			records = Db.find(sql.toString(),oid);
		}
		else {
			sql.append("WHERE  YEAR(e.create_time) = YEAR(NOW()) AND e.place IS NOT NULL ");
			sql.append(" GROUP BY place ");
			records = Db.find(sql.toString());
		}
		return records;
	}
	
	@Override
	public List<Record> searchHotEducation(long oid) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT COUNT(*) AS amount,education FROM enroll e ");
		if (oid != -1) {
			sql.append("WHERE organization_id = ? AND YEAR(e.create_time) = YEAR(NOW()) GROUP BY education ");
			sql.append("LIMIT 5 ");
			records = Db.find(sql.toString(),oid);
		}
		else {
			sql.append("WHERE  YEAR(e.create_time) = YEAR(NOW()) ");
			sql.append(" GROUP BY education ");
			records = Db.find(sql.toString());
		}
		return records;
	}
	
	@Override
	public List<Record> searchHotEducationArchive(long oid) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT COUNT(*) AS amount,education FROM archive e ");
		if (oid != -1) {
			sql.append("WHERE organization_id = ? AND YEAR(e.create_time) = YEAR(NOW()) GROUP BY education ");
			sql.append("LIMIT 5 ");
			records = Db.find(sql.toString(),oid);
		}
		else {
			sql.append("WHERE  YEAR(e.create_time) = YEAR(NOW()) ");
			sql.append(" GROUP BY education ");
			records = Db.find(sql.toString());
		}
		return records;
	}
	
	@Override
	public List<Record> searchHotMajorByClass(long oid) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS amount, ");
		sql.append("	c.profession_id, ");
		sql.append("	p.`name` ");
		sql.append("FROM ");
		sql.append("	classinfo c ");
		sql.append("LEFT JOIN profession p ON c.profession_id = p.id ");
		if (oid != -1) {
			sql.append("WHERE ");
			sql.append("	c.organization_id = ? AND c.year = YEAR(NOW()) ");
			sql.append("GROUP BY ");
			sql.append("	c.profession_id ");
			sql.append("ORDER BY ");
			sql.append("	amount DESC ");
			sql.append("LIMIT 5 ");
			records = Db.find(sql.toString(),oid);
		}
		else {
			sql.append("WHERE  c.year = YEAR(NOW()) ");
			sql.append("GROUP BY ");
			sql.append("	c.profession_id ");
			sql.append("ORDER BY ");
			sql.append("	amount DESC ");
			sql.append("LIMIT 5 ");
			records = Db.find(sql.toString());
		}
		return records;
	}

	@Override
	public List<Record> searchHotMajorByJob(long oid) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS amount, ");
		sql.append("  profession_id, ");
		sql.append("  p.`name` ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("LEFT JOIN profession p ON e.profession_id = p.id ");
		if (oid != -1) {
			sql.append("WHERE ");
			sql.append("	e.organization_id = ? AND YEAR(e.create_time) = YEAR(NOW()) ");
			sql.append("AND e.state_id = 5 ");
			sql.append("GROUP BY e.profession_id ");
			sql.append("ORDER BY amount DESC ");
			records = Db.find(sql.toString(),oid);
		}
		else {
			sql.append("WHERE  YEAR(e.create_time) = YEAR(NOW()) ");
			sql.append("AND e.state_id = 5 ");
			sql.append("GROUP BY e.profession_id ");
			sql.append("ORDER BY amount DESC ");
			records = Db.find(sql.toString());
		}
		return records;
	}
	
	@Override
	public List<Record> searchHotMajorByJobArchive(long oid) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS amount, ");
		sql.append("  profession_id, ");
		sql.append("  p.`name` ");
		sql.append("FROM ");
		sql.append("	archive e ");
		sql.append("LEFT JOIN profession p ON e.profession_id = p.id ");
		if (oid != -1) {
			sql.append("WHERE ");
			sql.append("	e.organization_id = ? AND YEAR(e.create_time) = YEAR(NOW()) ");
			sql.append("AND e.state_id = 5 ");
			sql.append("GROUP BY e.profession_id ");
			sql.append("ORDER BY amount DESC ");
			records = Db.find(sql.toString(),oid);
		}
		else {
			sql.append("WHERE  YEAR(e.create_time) = YEAR(NOW()) ");
			sql.append("AND e.state_id = 5 ");
			sql.append("GROUP BY e.profession_id ");
			sql.append("ORDER BY amount DESC ");
			records = Db.find(sql.toString());
		}
		return records;
	}

	@Override
	public List<Record> searchHotMonthData(long oid, int year, int month) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS amount, ");
		sql.append("	e.state_id, ");
		sql.append("  s.`name` ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("LEFT JOIN student_state s ON e.state_id = s.id ");
		sql.append("WHERE ");
		sql.append("YEAR (create_time) = ? ");
		sql.append("AND MONTH (create_time) = ?  ");
		if (oid != -1) {
			sql.append("AND	e.organization_id = ? ");
			sql.append("GROUP BY ");
			sql.append("	state_id ");
			records = Db.find(sql.toString(),year,month,oid);
		}
		else {
			sql.append("GROUP BY ");
			sql.append("	state_id ");
			records = Db.find(sql.toString(),year,month);
		}
		return records;
	}
	
	@Override
	public List<Record> searchHotMonthDataArchive(long oid, int year, int month) {
		StringBuffer sql = new StringBuffer();
		List<Record> records;
		sql.append("SELECT ");
		sql.append("	COUNT(*) AS amount, ");
		sql.append("	e.state_id+10 as state_id, ");
		sql.append("  s.`name` ");
		sql.append("FROM ");
		sql.append("	archive e ");
		sql.append("LEFT JOIN student_state s ON e.state_id = s.id ");
		sql.append("WHERE ");
		sql.append("YEAR (create_time) = ?  ");
		sql.append("AND MONTH (create_time) = ?  ");
		if (oid != -1) {
			sql.append("AND	e.organization_id = ? ");
			sql.append("GROUP BY ");
			sql.append("	state_id ");
			records = Db.find(sql.toString(),year,month,oid);
		}
		else {
			sql.append("GROUP BY ");
			sql.append("	state_id ");
			records = Db.find(sql.toString(),year,month);
		}
		return records;
	}

	@Override
	public Page<Record> dynamic(String collection,String username) {
		HashMap hashMap = new HashMap();
		hashMap.put("receiver", username);
		Page<Record> record=MongoKit.paginate(collection, 1, 3,hashMap);
		return record;
	}

}
