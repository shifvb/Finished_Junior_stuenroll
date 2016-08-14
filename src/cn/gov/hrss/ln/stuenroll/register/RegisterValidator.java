package cn.gov.hrss.ln.stuenroll.register;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

import cn.gov.hrss.ln.util.PidUtil;

public class RegisterValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		String actionKey = this.getActionKey();
		if(actionKey == "/register/isEnrollEligible" || actionKey == "/register/downloadRegisterReport") {
			validateRequired("pid", "pidError", "身份证号必填");
			String pid = c.getPara("pid");
			if(PidUtil.validate(pid).length() > 0) {
				addError("pidError", "身份证号无效");
			}
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.redirect("http://127.0.0.1:1180/stuenroll/validator/validator.html");
	}
	
}