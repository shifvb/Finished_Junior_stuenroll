package cn.gov.hrss.ln.stuenroll.review.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Review {
	
	private Long id;
	private String reviewer;
	private String reviewee;
	private String time;
	private Long duration;
	private String satisfaction;
	private Integer reviewResult;
	private String content;
	private Long classInfoId;
	private Long studentId;
	
	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getReviewee() {
		return reviewee;
	}

	public void setReviewee(String reviewee) {
		this.reviewee = reviewee;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}

	public Integer getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(Integer reviewResult) {
		this.reviewResult = reviewResult;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getClassInfoId() {
		return classInfoId;
	}

	public void setClassInfoId(Long classInfoId) {
		this.classInfoId = classInfoId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", reviewer=" + reviewer + ", reviewee=" + reviewee + ", time=" + time + ", duration=" + duration
				+ ", satisfaction=" + satisfaction + ", reviewResult=" + reviewResult + ", content=" + content + ", classInfoId="
				+ classInfoId + ", studentId=" + studentId + "]";
	}
	
	public List<Object> getParasList(){
		List<Object> paras = new ArrayList<Object>();
		paras.add(id);
		paras.add(reviewer);
		paras.add(reviewee);
		paras.add(time);
		paras.add(duration);
		paras.add(satisfaction);
		paras.add(reviewResult);
		paras.add(content);
		paras.add(classInfoId);
		paras.add(studentId);
		return paras;
	}
	
	public Map<String, Object> getParasMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("classInfoId", classInfoId);
		map.put("studentId", studentId);
		map.put("reviewee", reviewee);
		map.put("reviewer", reviewer);
		map.put("time", time);
		map.put("satisfaction", satisfaction);
		map.put("duration", duration);
		map.put("reviewResult", reviewResult);
		map.put("content", content);
		return map;
	}

}
