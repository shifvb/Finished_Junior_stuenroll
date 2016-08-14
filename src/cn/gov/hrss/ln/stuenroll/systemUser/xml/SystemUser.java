package cn.gov.hrss.ln.stuenroll.systemUser.xml;

import java.util.ArrayList;
import java.util.List;

public class SystemUser {
	private String id;
	private String username;
	private String password;
	private String name;
	private String pid;
	private String sex;
	private String organization_id;
	private String tel;
	private String wechat;
	private String email;
	private String question;
	private String answer;
	private String block;
	private String role_id;
	private String create_time;
	private String position;
	private String photo_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(String organization_id) {
		this.organization_id = organization_id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(String photo_id) {
		this.photo_id = photo_id;
	}

	@Override
	public String toString() {
		return "SystemUser [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", pid=" + pid + ", sex="
				+ sex + ", organization_id=" + organization_id + ", tel=" + tel + ", wechat=" + wechat + ", email=" + email + ", question="
				+ question + ", answer=" + answer + ", block=" + block + ", role_id=" + role_id + ", create_time=" + create_time
				+ ", position=" + position + ", photo_id=" + photo_id + "]";
	}

	public List<String> getParams() {
		List<String> params = new ArrayList<String>();
		params.add(id);
		params.add(username);
		params.add(password);
		params.add(name);
		params.add(pid);
		params.add(sex);
		params.add(organization_id);
		params.add(tel);
		params.add(wechat);
		params.add(email);
		params.add(question);
		params.add(answer);
		params.add(block);
		params.add(role_id);
		params.add(create_time);
		params.add(position);
		params.add(photo_id);
		return params;
	}
}
