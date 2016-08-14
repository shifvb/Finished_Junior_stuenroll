package cn.gov.hrss.ln.stuenroll.classmanagement;

import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_ReviewDao;
import cn.gov.hrss.ln.stuenroll.db.I_UserLogDao;
import cn.gov.hrss.ln.stuenroll.tools.UserOperationType;

/**
 * 回访记录业务模块接口实现
 * @author LiYadong
 * @version 1.0
 */

public class ReviewService implements I_ReviewService {
	
	private I_ReviewDao i_ReviewDao;
	private I_UserLogDao i_UserLogDao;

	@Override
	public List<Record> searchReviewInfo(Map<String, Object> map, Long start, Long length) {
		List<Record> records = i_ReviewDao.searchReviewInfo(map, start, length);
		return records;
	}

	@Override
	public long searchReviewCounts(Map<String, Object> map) {
		long count = i_ReviewDao.searchReviewCounts(map);
		return count;
	}
	
	@Before(Tx.class)
	@Override
	public long addReviewInfo(Map<String, Object> map, String username) {
		long count = i_ReviewDao.addReviewInfo(map);
//		i_UserLogDao.save(username, "添加回访记录", UserOperationType.业务操作.toString());
		return count;
	}
	
	@Before(Tx.class)
	@Override
	public int updateReviewInfo(Map<String, Object> map, Long reviewId, String username) {
		int count = i_ReviewDao.updateReviewInfo(map, reviewId);
		i_UserLogDao.save(username, "更新回访记录", UserOperationType.业务操作.toString());
		return count;
	}
	
	@Before(Tx.class)
	@Override
	public long deleteReviewInfo(Long[] id, String username) {
		long count = i_ReviewDao.deleteReviewInfo(id);
		i_UserLogDao.save(username, "删除回访记录", UserOperationType.业务操作.toString());
		return count;
	}
	
	@Override
	public List<Record> searchReviewPeopleAmountInClass(Map<String, Object> map, Long start, Long length) {
		List<Record> records = i_ReviewDao.searchReviewPeopleAmountInClass(map, start, length);
 		return records;
	}
	
	@Override
	public List<Record> searchAllReview() {
		List<Record> records = i_ReviewDao.searchAllReview();
		return records;
	}
	
	public I_ReviewDao getI_ReviewDao() {
		return i_ReviewDao;
	}
	
	public void setI_ReviewDao(I_ReviewDao i_ReviewDao) {
		this.i_ReviewDao = i_ReviewDao;
	}
	
	public I_UserLogDao getI_UserLogDao() {
		return i_UserLogDao;
	}
	
	public void setI_UserLogDao(I_UserLogDao i_UserLogDao) {
		this.i_UserLogDao = i_UserLogDao;
	}

}
