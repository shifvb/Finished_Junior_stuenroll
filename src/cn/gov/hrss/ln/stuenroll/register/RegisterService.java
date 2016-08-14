package cn.gov.hrss.ln.stuenroll.register;

import org.eclipse.xtend.lib.annotations.Accessors;

import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_ArchiveDao;
import cn.gov.hrss.ln.stuenroll.db.I_EnrollDao;
import cn.gov.hrss.ln.stuenroll.redis.I_EnrollRedisDAO;

/**
 * 学员报名业务类
 */
@SuppressWarnings("all")
public class RegisterService implements I_RegisterService {
  
  private I_EnrollDao i_EnrollDao;
  
  //private I_ArchiveDao i_ArchiveDao;
  
  private I_EnrollRedisDAO i_EnrollRedisDAO;
  
  /**
   * 是否存在某条记录
   * @param pid 身份证号
   * @return 查询结果
   */
  @Override
  public boolean hasRecord(String pid) {
	  boolean bool = i_EnrollDao.isEnrollEligible(pid) || i_EnrollRedisDAO.isEnrollEligible(pid);
	  return !bool;
  }
  
  /**
   * 查询报名记录
   * @param pid 身份证号
   * @return 查询结果
   */
  @Override
  public Record searchRegisterRecord(String pid) {
	  Record record_1 = i_EnrollRedisDAO.searchRegisterRecord(pid);
	  Record record_2 = i_EnrollDao.searchRegisterRecord(pid);
	  if(record_1 != null) {
		  return record_1;
	  } else if(record_2 != null) {
		  return record_2;
	  }
	  else {
		  return null;
	  }
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
  
  
}
