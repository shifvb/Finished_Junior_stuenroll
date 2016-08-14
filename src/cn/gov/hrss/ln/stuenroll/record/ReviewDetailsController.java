package cn.gov.hrss.ln.stuenroll.record;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

import cn.gov.hrss.ln.stuenroll.image.I_ImageService;

@Spring("reviewDetailsController")
public class ReviewDetailsController extends Controller implements I_ReviewDetailsController {
	
	private I_ReviewDetailsService i_ReviewDetailsService;
	private I_ImageService i_ImageService;

	@Override
	public void searchReviewDetails() {
		Long reviewId = getParaToLong("reviewId");
		Record record = i_ReviewDetailsService.searchReviewDetails(reviewId);
		renderJson("result", record);
	}

	@Override
	public void searchStudentAttendence() {
		Long studentId = getParaToLong("studentId");
		List<Record> records = i_ReviewDetailsService.searchStudentAttendence(studentId);
		renderJson("result", records);
	}

	@Override
	public void searchStudentScore() {
		Long studentId = getParaToLong("studentId");
		List<Record> records = i_ReviewDetailsService.searchStudentScore(studentId);
		renderJson("result", records);

	}
	
	@Override
	public void searchStudentImage() {
		Long studentId = getParaToLong("studentId");
		String id = i_ReviewDetailsService.searchStudentImage(studentId);
		renderJson("result", id);
	}

	@Override
	public void addReviewDetails() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void addReviewRecord() {
		Map<String, Object> review = new HashMap<>(); 
		Map<String, Object> reviewDetails = null;
		String username = getPara("username");
		String reviewee = getPara("reviewee");
		Long duration = getParaToLong("time"); 
		String reviewer = getPara("reviewer");
		String content = getPara("content");
		Long classInfoId = getParaToLong("classInfoId");
		Long studentId = getParaToLong("studentId"); 
		Integer reviewResult = getParaToInt("reviewResult");
		String satisfaction = getPara("satisfaction");
		review.put("reviewee", reviewee);
		review.put("duration", duration);
		review.put("reviewer", reviewer);
		review.put("content", content);
		review.put("classInfoId", classInfoId);
		review.put("studentId", studentId);
		review.put("satisfaction", satisfaction);
		review.put("reviewResult", reviewResult);
		if (reviewResult == 0) {
			reviewDetails = new HashMap<>();
			String[] answer = getParaValues("answer");
			reviewDetails.put("student_info", answer[0]);
			reviewDetails.put("conversion_trianing", answer[1]);
			reviewDetails.put("conversion_organization", answer[2]);
			reviewDetails.put("conversion_teaching", answer[3]);
			reviewDetails.put("daliy_management", answer[4]);
			reviewDetails.put("profession_setting", answer[5]);
			reviewDetails.put("learning_cycle", answer[6]);
			reviewDetails.put("organization_changes", answer[7]);
			reviewDetails.put("employed", answer[8]);
			reviewDetails.put("company_size", answer[9]);
			reviewDetails.put("company_nature", answer[10]);
			reviewDetails.put("salary_level", answer[11]);
			reviewDetails.put("profession_counterparts", answer[12]);
			reviewDetails.put("job_satisfaction", answer[13]);
			reviewDetails.put("employed_way", answer[14]);
			reviewDetails.put("employed_satisfaction", answer[15]);
			reviewDetails.put("job_advice", answer[16]);
		}
		boolean bool = i_ReviewDetailsService.addReviewRecord(review, reviewDetails, username);
		renderJson("result", bool);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void searchReviewStudent() {
		Long reviewId = getParaToLong("reviewId");
		Record archiveRecord = i_ReviewDetailsService.searchArchiveReviewStudent(reviewId);
		Record unarchiveRecord = i_ReviewDetailsService.searchUnarchiveReviewStudent(reviewId);
		if (archiveRecord != null) {
			Date date = archiveRecord.getDate("birthday");
			archiveRecord.remove("birthday");
			archiveRecord.set("day", date.getDate());
			archiveRecord.set("month", date.getMonth() + 1);
			archiveRecord.set("year", date.getYear() + 1900);
			renderJson("result", archiveRecord);
		} else if (unarchiveRecord != null) {
			Date date = unarchiveRecord.getDate("birthday");
			unarchiveRecord.remove("birthday");
			unarchiveRecord.set("day", date.getDate());
			unarchiveRecord.set("month", date.getMonth() + 1);
			unarchiveRecord.set("year", date.getYear() + 1900);
			renderJson("result", unarchiveRecord);
		} else {
			renderJson("result", null);
		}
	}
	
	public I_ReviewDetailsService getI_ReviewDetailsService() {
		return i_ReviewDetailsService;
	}
	
	public void setI_ReviewDetailsService(I_ReviewDetailsService i_ReviewDetailsService) {
		this.i_ReviewDetailsService = i_ReviewDetailsService;
	}
	
	public I_ImageService getI_ImageService() {
		return i_ImageService;
	}
	
	public void setI_ImageService(I_ImageService i_ImageService) {
		this.i_ImageService = i_ImageService;
	}

}
