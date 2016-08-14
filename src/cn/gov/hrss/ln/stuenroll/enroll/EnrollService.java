package cn.gov.hrss.ln.stuenroll.enroll;

import java.util.HashMap;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_EnrollDao;
import cn.gov.hrss.ln.stuenroll.db.I_QuitInfoDao;
import cn.gov.hrss.ln.stuenroll.redis.I_EnrollRedisDAO;

/**
 * @author York Chu
 *
 */
public class EnrollService implements I_EnrollService {
	private I_EnrollDao i_EnrollDao;
	private I_EnrollRedisDAO i_EnrollRedisDAO;
	private I_QuitInfoDao i_QuitInfoDao;
	
	// 查询全部记录
	@Override
	public List<Record> searchEnroll(HashMap map, long start, long length) {
		List<Record> list = i_EnrollDao.searchEnroll(map, start, length);
		return list;
	}
	
	// 查询记录总数
	@Override
	public long searchEnrollCount(HashMap map) {
		long count = i_EnrollDao.searchEnrollCount(map);
		return count;
	}
	
	// 报名注册
	@Override
	public void register(HashMap map) {
		i_EnrollRedisDAO.insert(map);
	}
	
	// 判断身份证是否重复
	@Override
	public boolean isEnrollEligible(String pid) {
		boolean bool1=i_EnrollDao.isEnrollEligible(pid);
		boolean bool2=i_EnrollRedisDAO.isEnrollEligible(pid);
		boolean bool=bool1 && bool2;
		return bool;
	}

	// 添加记录
	@Override
	public int addEnroll(Object[] obj) {
		int i = i_EnrollDao.addEnroll(obj);
		return i;
	}
	
	// 根据ID删除
	@Before(Tx.class)
	@Override
	public int deleteById(Long[] id) {
		int i = i_EnrollDao.deleteById(id);
		return i;
	}
	
	// 修改报名记录
	@Override
	public int modifyEnroll(HashMap map) {
		int count = i_EnrollDao.modifyEnroll(map);
		return count;
	}
	
	// 根据ID查找报名记录
	@Override
	public Record searchEnrollById(long id) {
		Record record = i_EnrollDao.searchEnrollById(id);
		return record;
	}
	
	// 学员分班
	@Override
	public int allot(Long[] id, long professionId, long organizationId, long classId, String place) {
		int i = i_EnrollDao.allot(id, professionId, organizationId, classId, place);
		return i;
	}
	
	// 学员分班取消
	@Override
	public int cancelAllot(Long[] id) {
		int i = i_EnrollDao.cancelAllot(id);
		return i;
	}
	
	// 学员中退
	@Before(Tx.class)
	public int quit(long id, String quitDate, String quitReason) {
		int i = i_EnrollDao.quit(id);
		if(i == 1)
			i_QuitInfoDao.save(id, quitDate, quitReason);
		return i;
	}
	
	// 学员取消中退
	@Before(Tx.class)
	public int cancelQuit(Long[] id) {
		int count = i_EnrollDao.cancelQuit(id);
		if(count > 0)
			i_QuitInfoDao.delete(id);
		return count;
	}
	
	public I_EnrollDao getI_EnrollDao() {
		return i_EnrollDao;
	}

	public void setI_EnrollDao(I_EnrollDao i_EnrollDao) {
		this.i_EnrollDao = i_EnrollDao;
	}

	public I_EnrollRedisDAO getI_EnrollRedisDAO() {
		return i_EnrollRedisDAO;
	}

	public void setI_EnrollRedisDAO(I_EnrollRedisDAO i_EnrollRedisDAO) {
		this.i_EnrollRedisDAO = i_EnrollRedisDAO;
	}


	public I_QuitInfoDao getI_QuitInfoDao() {
		return i_QuitInfoDao;
	}

	public void setI_QuitInfoDao(I_QuitInfoDao i_QuitInfoDao) {
		this.i_QuitInfoDao = i_QuitInfoDao;
	}



	
}
