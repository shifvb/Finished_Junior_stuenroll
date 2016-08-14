package cn.gov.hrss.ln.stuenroll.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class EnrollValidator extends Validator {

	@Override
	protected void validate(final Controller c) {
		String actionKey = this.getActionKey();
		boolean equals = actionKey.equals("/enroll/register");
		if(equals){
			this.validateRegex("name","^[\u4e00-\u9fa5]{2,15}$","errorMsgOfRigister","姓名错误");
			this.validateRegex("sex","^[一-龥]{1}$","errorMsgOfRigister","性别错误");
			this.validateDate("birthday","errorMsgOfRigister","生日错误");
			this.validateDate("graduateDate","errorMsgOfRigisterOfRigister","毕业日期错误");
			this.validateRegex("nation","^[\u4e00-\u9fa5]{2,15}$","errorMsgOfRigister","民族错误");
			this.validateRegex("pid","^[0-9]{18}$","errorMsgOfRigister","身份证错误");
			this.validateRequiredString("healthy","errorMsgOfRigister","健康状况不能为空");
			this.validateRequiredString("education","errorMsgOfRigister","学历不能为空");
			this.validateRequiredString("major","errorMsgOfRigister","专业不能为空");
			this.validateRequiredString("politics","errorMsgOfRigister","政治面貌不能为空");
			this.validateRegex("graduteSchool","^[a-zA-Z0-9\u4e00-\u9fa5]{4,30}$","errorMsgOfRigister","毕业学校错误");
			this.validateRegex("residentAddress","^[a-zA-Z0-9\u4e00-\u9fa5]{4,30}$","errorMsgOfRigister","现居住地错误");
			this.validateRegex("homeAddress","^[a-zA-Z0-9\u4e00-\u9fa5]{4,30}$","errorMsgOfRigister","家庭地址错误");
			this.validateRegex("permanentAddress","^[a-zA-Z0-9\u4e00-\u9fa5]{4,30}$","errorMsgOfRigister","户籍地址错误");
			this.validateRegex("place","^[\u4e00-\u9fa5]{2,8}$","errorMsgOfRigister","地点错误");
			this.validateRegex("wechat","^[a-zA-Z0-9]{4,30}$","errorMsgOfRigister","微信号错误");
			this.validateRegex("graduateYear","^[0-9]{4}$","errorMsgOfRigister","毕业年份错误");
			this.validateRegex("tel","^1[0-9]{10}$","errorMsgOfRigister","电话错误");
			this.validateRequiredString("homeTel","errorMsgOfRigister","家庭电话错误");
			//this.validateRegex("homeTel","^\\d{7,}$","errorMsgOfRigister","家庭电话错误");
			this.validateEmail("email", "errorMsgOfRigister", "电子邮箱错误");
			this.validateLong("professionId", "errorMsgOfRigister", "专业ID错误");
			this.validateLong("organizationId", "errorMsgOfRigister", "机构ID错误");
		}
	}

	@Override
	protected void handleError(final Controller c) {
		//c.redirect("http://127.0.0.1:1180/stuenroll/validator/validator.html");
		c.keepPara();//将提交的值再传回页面以便保持原先输入的值
		c.renderJson();
	}
	
}
