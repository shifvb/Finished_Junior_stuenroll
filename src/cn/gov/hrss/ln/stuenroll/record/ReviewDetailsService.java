package cn.gov.hrss.ln.stuenroll.record;

import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_ReviewDao;
import cn.gov.hrss.ln.stuenroll.db.I_ReviewDetailsDao;
import cn.gov.hrss.ln.stuenroll.db.I_UserLogDao;
import cn.gov.hrss.ln.stuenroll.tools.UserOperationType;

public class ReviewDetailsService implements I_ReviewDetailsService {

	private I_ReviewDao i_ReviewDao;
	private I_ReviewDetailsDao i_ReviewDetailsDao;
	private I_UserLogDao i_UserLogDao;

	@Override
	public Record searchReviewDetails(Long reviewId) {
		Record record = i_ReviewDetailsDao.searchReviewDetails(reviewId);
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
	
	@Override
	public String searchStudentImage(Long studentId) {
		String archiveImage = i_ReviewDetailsDao.searchArchiveStudentImage(studentId);
		String unarchiveImage = i_ReviewDetailsDao.searchUnarchiveStudentImage(studentId);
		if (archiveImage != null) {
			return archiveImage;
		} else if (unarchiveImage != null) {
			return unarchiveImage;
		} else {
			return null;
		}
	}

	@Before(Tx.class)
	@Override
	public int addReviewDetails(Map<String, Object> map, String username) {
		int count = i_ReviewDetailsDao.addReviewDetails(map);
		i_UserLogDao.save(username, "添加回访内容", UserOperationType.业务操作.toString());
		return count;
	}

	@Before(Tx.class)
	@Override
	public boolean addReviewRecord(Map<String, Object> review, Map<String, Object> reviewDetails, String username) {
		Long reviewId = i_ReviewDao.addReviewInfo(review);
		i_UserLogDao.save(username, "添加回访记录", UserOperationType.业务操作.toString());
		if (reviewId != null && !reviewId.equals(0L) && reviewDetails != null) {
			reviewDetails.put("reviewId", reviewId);
			int reviewDetailsResult = i_ReviewDetailsDao.addReviewDetails(reviewDetails);
			i_UserLogDao.save(username, "添加回访内容", UserOperationType.业务操作.toString());
			boolean bool = reviewDetailsResult == 1 ? true : false;
			return bool;
		} 
		return false;

	}
	
	@Override
	public Record searchArchiveReviewStudent(Long reviewId) {
		Record record = i_ReviewDetailsDao.searchArchiveReviewStudent(reviewId);
		return record;
	}
	
	@Override
	public Record searchUnarchiveReviewStudent(Long reviewId) {
		Record record = i_ReviewDetailsDao.searchUnarchiveReviewStudent(reviewId);
		return record;
	}

	public I_ReviewDetailsDao getI_ReviewDetailsDao() {
		return i_ReviewDetailsDao;
	}

	public void setI_ReviewDetailsDao(I_ReviewDetailsDao i_ReviewDetailsDao) {
		this.i_ReviewDetailsDao = i_ReviewDetailsDao;
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
