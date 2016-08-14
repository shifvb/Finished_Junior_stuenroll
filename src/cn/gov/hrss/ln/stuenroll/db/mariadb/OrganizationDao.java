package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_OrganizationDao;
import cn.gov.hrss.ln.stuenroll.tools.DaoTools;

/**
 * 机构数据访问接口在MariaDB下的实现类
 * 
 * @author LiYaDong
 * @version 2.0
 *
 */
public class OrganizationDao implements I_OrganizationDao {

	@Override
	public List<Record> searchOrganizations(Map<String, Object> map, long start, long length) {

		ArrayList<Object> paras = new ArrayList<>();
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String liasion = (String) map.get("liasion");
		String tel = (String) map.get("tel");
		Long organizationId = (Long) map.get("organizationId");

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	o.id, ");
		sql.append("	o.`name`, ");
		sql.append("	o.abbreviation, ");
		sql.append("	o.address, ");
		sql.append("	o.tel, ");
		sql.append("	o.liaison ");
		sql.append("FROM organization o ");
		sql.append("WHERE 1=1 ");

		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append("AND o.abbreviation = ? ");
			paras.add(abbreviation);
		}
		if (name != null && name.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(name);
		}
		if (address != null && address.length() > 0) {
			sql.append("AND o.address = ? ");
			paras.add(address);
		}
		if (liasion != null && liasion.length() > 0) {
			sql.append("AND o.liaison = ? ");
			paras.add(liasion);
		}
		if (tel != null && tel.length() > 0) {
			sql.append("AND o.tel = ? ");
			paras.add(tel);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND o.id = ? ");
			paras.add(organizationId);
		}
		sql.append("AND o.id != '738620600423157760' ");
		sql.append("GROUP BY o.id ");
		sql.append("ORDER BY o.id ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);

		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public List<Record> searchOrganizatonOnProfessionAmount(Map<String, Object> map, long start, long length) {
		ArrayList<Object> paras = new ArrayList<>();
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String liasion = (String) map.get("liasion");
		String tel = (String) map.get("tel");
		Long organizationId = (Long) map.get("organizationId");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	o.id, ");
		sql.append("	COUNT(DISTINCT op.profession_id) AS professionAmount ");
		sql.append("FROM ");
		sql.append("	organization o ");
		sql.append("LEFT JOIN organization_join oj ON o.id = oj.organization_id ");
		sql.append("LEFT JOIN organization_profession op ON op.organization_join_id = oj.id ");
		sql.append("WHERE 1=1 ");

		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append("AND o.abbreviation = ? ");
			paras.add(abbreviation);
		}
		if (name != null && name.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(name);
		}
		if (address != null && address.length() > 0) {
			sql.append("AND o.address = ? ");
			paras.add(address);
		}
		if (liasion != null && liasion.length() > 0) {
			sql.append("AND o.liaison = ? ");
			paras.add(liasion);
		}
		if (tel != null && tel.length() > 0) {
			sql.append("AND o.tel = ? ");
			paras.add(tel);
		}
		if (organizationId != null && organizationId > 0 && organizationId != 738620600423157760L) {
			sql.append("AND o.id = ? ");
			paras.add(organizationId);
		}
		sql.append("AND o.id != '738620600423157760' ");
		sql.append("GROUP BY o.id ");
		sql.append("ORDER BY o.id ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);

		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public List<Record> searchOrganizationOnClassAmount(Map<String, Object> map, long start, long length) {
		ArrayList<Object> paras = new ArrayList<>();
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String liasion = (String) map.get("liasion");
		String tel = (String) map.get("tel");
		Long organizationId = (Long) map.get("organizationId");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	o.id, ");
		sql.append("	COUNT(DISTINCT ci.id) AS classAmount ");
		sql.append("FROM ");
		sql.append("	organization o ");
		sql.append("LEFT JOIN classinfo ci ON ci.organization_id = o.id ");
		sql.append("WHERE 1=1 ");

		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append("AND o.abbreviation = ? ");
			paras.add(abbreviation);
		}
		if (name != null && name.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(name);
		}
		if (address != null && address.length() > 0) {
			sql.append("AND o.address = ? ");
			paras.add(address);
		}
		if (liasion != null && liasion.length() > 0) {
			sql.append("AND o.liaison = ? ");
			paras.add(liasion);
		}
		if (tel != null && tel.length() > 0) {
			sql.append("AND o.tel = ? ");
			paras.add(tel);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND o.id = ? ");
			paras.add(organizationId);
		}
		sql.append("AND o.id != '738620600423157760' ");
		sql.append("GROUP BY o.id ");
		sql.append("ORDER BY o.id ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);

		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public List<Record> searchOrganizationOnEnrollAmount(Map<String, Object> map, long start, long length) {
		ArrayList<Object> paras = new ArrayList<>();
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String liasion = (String) map.get("liasion");
		String tel = (String) map.get("tel");
		Integer state = (Integer) map.get("state");
		Long organizationId = (Long) map.get("organizationId");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	o.id, ");
		sql.append("	COUNT(e.id) AS students ");
		sql.append("FROM ");
		sql.append("	organization o ");
		sql.append("LEFT JOIN enroll e ON e.organization_id = o.id ");
		sql.append("WHERE 1=1 ");

		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append("AND o.abbreviation = ? ");
			paras.add(abbreviation);
		}
		if (name != null && name.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(name);
		}
		if (address != null && address.length() > 0) {
			sql.append("AND o.address = ? ");
			paras.add(address);
		}
		if (liasion != null && liasion.length() > 0) {
			sql.append("AND o.liaison = ? ");
			paras.add(liasion);
		}
		if (tel != null && tel.length() > 0) {
			sql.append("AND o.tel = ? ");
			paras.add(tel);
		}
		if (state != null && state > 0) {
			sql.append("AND e.state_id = ? ");
			paras.add(state);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND o.id = ? ");
			paras.add(organizationId);
		}
		sql.append("AND o.id != '738620600423157760' ");
		sql.append("GROUP BY o.id ");
		sql.append("ORDER BY o.id ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);

		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public List<Record> searchOrganizationOnArchiveAmount(Map<String, Object> map, long start, long length) {
		ArrayList<Object> paras = new ArrayList<>();
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String liasion = (String) map.get("liasion");
		String tel = (String) map.get("tel");
		Integer state = (Integer) map.get("state");
		Long organizationId = (Long) map.get("organizationId");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	o.id, ");
		sql.append("	COUNT(a.id) AS students ");
		sql.append("FROM ");
		sql.append("	organization o ");
		sql.append("LEFT JOIN archive a ON a.organization_id = o.id ");
		sql.append("WHERE 1=1 ");

		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append("AND o.abbreviation = ? ");
			paras.add(abbreviation);
		}
		if (name != null && name.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(name);
		}
		if (address != null && address.length() > 0) {
			sql.append("AND o.address = ? ");
			paras.add(address);
		}
		if (liasion != null && liasion.length() > 0) {
			sql.append("AND o.liaison = ? ");
			paras.add(liasion);
		}
		if (tel != null && tel.length() > 0) {
			sql.append("AND o.tel = ? ");
			paras.add(tel);
		}
		if (state != null && state > 0) {
			sql.append("AND e.state_id = ? ");
			paras.add(state);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND o.id = ? ");
			paras.add(organizationId);
		}
		sql.append("AND o.id != '738620600423157760' ");
		sql.append("GROUP BY o.id ");
		sql.append("ORDER BY o.id ");
		sql.append("LIMIT ?, ?; ");
		paras.add(start);
		paras.add(length);

		List<Record> records = Db.find(sql.toString(), paras.toArray());
		return DaoTools.castLongToString(records, "id");
	}

	@Override
	public long searchOrganizationAmount(Map<String, Object> map) {
		ArrayList<Object> paras = new ArrayList<>();
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String liasion = (String) map.get("liasion");
		String tel = (String) map.get("tel");
		Long organizationId = (Long) map.get("organizationId");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM organization o WHERE 1=1 ");

		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append("AND o.abbreviation = ? ");
			paras.add(abbreviation);
		}
		if (name != null && name.length() > 0) {
			sql.append("AND o.`name` = ? ");
			paras.add(name);
		}
		if (address != null && address.length() > 0) {
			sql.append("AND o.address = ? ");
			paras.add(address);
		}
		if (liasion != null && liasion.length() > 0) {
			sql.append("AND o.liaison = ? ");
			paras.add(liasion);
		}
		if (tel != null && tel.length() > 0) {
			sql.append("AND o.tel = ? ");
			paras.add(tel);
		}
		if (organizationId != null && organizationId > 0) {
			sql.append("AND o.id = ? ");
			paras.add(organizationId);
		}
		sql.append("AND o.id != '738620600423157760' ");
		sql.append(";");

		long count = Db.queryLong(sql.toString(), paras.toArray());
		return count;
	}

	@Override
	public int addOrganization(Map<String, Object> map) {
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String liasion = (String) map.get("liasion");
		String tel = (String) map.get("tel");
		String liasion_tel = (String) map.get("liasion_tel");
		ArrayList<Object> paras = new ArrayList<>();
		paras.add(name);
		paras.add(abbreviation);
		paras.add(address);
		paras.add(tel);
		paras.add(liasion);
		paras.add(liasion_tel);
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `organization` ( ");
		sql.append("	`id`, ");
		sql.append("	`name`, ");
		sql.append("	`abbreviation`, ");
		sql.append("	`address`, ");
		sql.append("	`tel`, ");
		sql.append("	`liaison`, ");
		sql.append("	`liaison_tel` ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT ");
		sql.append("		VALUE ");
		sql.append("			FOR MYCATSEQ_GLOBAL, ");
		sql.append("			?, ?, ?, ?, ?, ? ");
		sql.append("	); ");
		int count = Db.update(sql.toString(), paras.toArray());
		return count;
	}

	@Override
	public int updateOrganization(long organizationId, Map<String, Object> map) {
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String liasion = (String) map.get("liasion");
		String tel = (String) map.get("tel");
		String liasion_tel = (String) map.get("liasion_tel");
		ArrayList<Object> paras = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `organization` ");
		sql.append("SET `id` = ? ");
		paras.add(organizationId);
		if (name != null && name.length() > 0) {
			sql.append(", `name` = ? ");
			paras.add(name);
		}
		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append(", `abbreviation` = ? ");
			paras.add(abbreviation);
		}
		if (address != null && address.length() > 0) {
			sql.append(", `address` = ? ");
			paras.add(address);
		}
		if (liasion != null && liasion.length() > 0) {
			sql.append(", `tel` = ? ");
			paras.add(tel);
		}
		if (tel != null && tel.length() > 0) {
			sql.append(", `liaison` = ? ");
			paras.add(liasion);
		}
		if (liasion_tel != null && liasion_tel.length() > 0) {
			sql.append(", `liaison_tel` = ? ");
			paras.add(liasion_tel);
		}
		sql.append("WHERE `id` = ?");
		paras.add(organizationId);

		int count = Db.update(sql.toString(), paras.toArray());
		return count;
	}

	@Override
	public long deleteOrganizationsById(Long[] id) {
		StringBuffer sql = new StringBuffer();
		List<Long> paras = Arrays.asList(id);
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	organization ");
		sql.append("WHERE ");
		sql.append("	id IN ( ");
		for (int i = 0; i < id.length; i++) {
			sql.append("?");
			if (i != id.length - 1) {
				sql.append(",");
			}
		}
		sql.append("); ");
		int count = Db.update(sql.toString(), paras.toArray());
		return count;
	}

	@Before(Tx.class)
	@Override
	public long insertOrganizationTransformInfo(Long id, int year, int block) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO organization_join( ");
		sql.append("	`id`, ");
		sql.append("	`organization_id`, ");
		sql.append("	`year`, ");
		sql.append("	`block` ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT VALUE FOR MYCATSEQ_GLOBAL, ?, ?, ?); ");
		long count = Db.update(sql.toString(), id, year, block);
		return count;
	}

	@Override
	public Record searchOrganization(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	id, ");
		sql.append("	`name`, ");
		sql.append("	abbreviation, ");
		sql.append("	address, ");
		sql.append("	tel, ");
		sql.append("	liaison, ");
		sql.append("	liaison_tel ");
		sql.append("FROM organization  ");
		sql.append("WHERE id = ? ");
		Record record = Db.findFirst(sql.toString(), id);
		return record;
	}

	@Override
	public List<Record> getTransformOrganizations() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM organization_join ORDER BY id, `year` DESC; ");
		List<Record> records = Db.find(sql.toString());
		return records;
	}

	@Override
	public long updateOrganizationTransformInfo(Long id, Long organizationId, int year, int block) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `organization_join` ");
		sql.append("SET `id` = ?, ");
		sql.append(" `organization_id` = ?, ");
		sql.append(" `year` = ?, ");
		sql.append(" `block` = ? ");
		sql.append("WHERE ");
		sql.append("	(`id` = ?); ");
		long count = Db.update(sql.toString(), id, organizationId, year, block, id);
		return count;
	}
	
	@Override
	public List<Record> searchOrganization() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	`name`, ");
		sql.append("	liaison, ");
		sql.append("	liaison_tel ");
		sql.append("FROM ");
		sql.append("	organization; ");
		
		List<Record> record = Db.find(sql.toString());
		return record;
	}

}
