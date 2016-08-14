package cn.gov.hrss.ln.stuenroll.db.mariadb;

import com.jfinal.plugin.activerecord.Db;

import cn.gov.hrss.ln.stuenroll.db.I_QuitInfoDao;

public class QuitInfoDao implements I_QuitInfoDao {

	@Override
	public void save(long enrollId, String quitDate, String quitReason) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO quit_info ( ");
		sql.append("	id, ");
		sql.append("	enroll_id, ");
		sql.append("	quit_date, ");
		sql.append("	quit_reason ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT ");
		sql.append("		VALUE ");
		sql.append("			FOR MYCATSEQ_GLOBAL, ");
		sql.append("?, ?, ? ");
		sql.append("	); ");
		Db.update(sql.toString(), enrollId, quitDate, quitReason);
	}

	@Override
	public int delete(Long[] enrollId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	quit_info ");
		sql.append("WHERE ");
		sql.append("	enroll_id IN ( ");
		for (int i = 0; i < enrollId.length; i++) {
			sql.append("?");
			if (i != enrollId.length - 1) {
				sql.append(",");
			}
		}
		sql.append(") ");
		int count = Db.update(sql.toString(), enrollId);
		return count;
	}
}
