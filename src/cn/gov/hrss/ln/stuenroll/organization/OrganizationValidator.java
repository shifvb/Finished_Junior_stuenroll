package cn.gov.hrss.ln.stuenroll.organization;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * 验证OrganizationController所需参数
 * 
 * @author LiYadong
 * @version 1.0
 *
 */
public class OrganizationValidator extends Validator {

	@Override
	protected void handleError(Controller controller) {
		controller.redirect("/validator/validator.html");
	}
	
	@Override
	protected void validate(Controller controller) {
		String actionKey = this.getActionKey();
		if ("/organization/searchOrganizations".equals(actionKey)) {
			// 验证 searchOrganizations 方法参数
			String name = controller.getAttrForStr("name");
			String abbreviation = controller.getAttrForStr("abbreviation");
			String address = controller.getAttrForStr("address");
			String liasion = controller.getAttrForStr("liasion");
			String tel = controller.getAttrForStr("tel");
			String liasion_tel = controller.getAttrForStr("liasion_tel");
			if (name != null && name.length() > 0) {
				validateRegex(name, "^[a-zA-Z0-9\\u4e00-\\u9fa5]{4,30}$", "searchOrganizations", "机构名称错误");
			}
			if (abbreviation != null && abbreviation.length() > 0) {
				validateRegex(abbreviation, "^[\\u4e00-\\u9fa5]{4,10}$", "searchOrganizations", "机构简称错误");
			}
			if (address != null && address.length() > 0) {
				validateRegex(address, "^[a-zA-Z0-9\\u4e00-\\u9fa5]{5,50}$", "searchOrganizations", "机构地址错误");
			}
			if (liasion != null && liasion.length() > 0) {
				validateRegex(liasion, "^[\\u4e00-\\u9fa5]{2,15}$", "searchOrganizations", "联系人错误");
			}
			if (tel != null && tel.length() > 0) {
				validateRegex(tel, "^[0]\\d{2,4}[-]\\d{8}$", "searchOrganizations", "机构联系方式错误");
			}
			if (liasion_tel != null && liasion_tel.length() > 0) {
				validateRegex(liasion_tel, "^[1-9]\\d{10}$", "searchOrganizations", "机构联系人联系方式错误");
			}
		}
		else if ("/organization/searchOrganizationAmount".equals(actionKey)) {
			String name = controller.getAttrForStr("name");
			String abbreviation = controller.getAttrForStr("abbreviation");
			String address = controller.getAttrForStr("address");
			String liasion = controller.getAttrForStr("liasion");
			String tel = controller.getAttrForStr("tel");

			if (name != null && name.length() > 0) {
				validateRegex(name, "^[a-zA-Z0-9\\u4e00-\\u9fa5]{4,30}$", "searchOrganizations", "机构名称错误");
			}
			if (abbreviation != null && abbreviation.length() > 0) {
				validateRegex(abbreviation, "^[\\u4e00-\\u9fa5]{4,10}$", "searchOrganizations", "机构简称错误");
			}
			if (address != null && address.length() > 0) {
				validateRegex(address, "^[a-zA-Z0-9\\u4e00-\\u9fa5]{5,50}$", "searchOrganizations", "机构地址错误");
			}
			if (liasion != null && liasion.length() > 0) {
				validateRegex(liasion, "^[\\u4e00-\\u9fa5]{2,15}$", "searchOrganizations", "联系人错误");
			}
			if (tel != null && tel.length() > 0) {
				validateRegex(tel, "^[0]\\d{2,4}[-]\\d{8}$", "searchOrganizations", "机构联系方式错误");
			}
		}
		else if ("/organization/addOrganization".equals(actionKey)) {
			String name = controller.getAttrForStr("name");
			String abbreviation = controller.getAttrForStr("abbreviation");
			String address = controller.getAttrForStr("address");
			String liasion = controller.getAttrForStr("liasion");
			String tel = controller.getAttrForStr("tel");
			String liasion_tel = controller.getAttrForStr("liasion_tel");
			if (name != null && name.length() > 0) {
				validateRegex(name, "^[a-zA-Z0-9\\u4e00-\\u9fa5]{4,30}$", "searchOrganizations", "机构名称错误");
			}
			if (abbreviation != null && abbreviation.length() > 0) {
				validateRegex(abbreviation, "^[\\u4e00-\\u9fa5]{4,10}$", "searchOrganizations", "机构简称错误");
			}
			if (address != null && address.length() > 0) {
				validateRegex(address, "^[a-zA-Z0-9\\u4e00-\\u9fa5]{5,50}$", "searchOrganizations", "机构地址错误");
			}
			if (liasion != null && liasion.length() > 0) {
				validateRegex(liasion, "^[\\u4e00-\\u9fa5]{2,15}$", "searchOrganizations", "联系人错误");
			}
			if (tel != null && tel.length() > 0) {
				validateRegex(tel, "^[0]\\d{2,4}[-]\\d{8}$", "searchOrganizations", "机构联系方式错误");
			}
			if (liasion_tel != null && liasion_tel.length() > 0) {
				validateRegex(liasion_tel, "^[1-9]\\d{10}$", "searchOrganizations", "机构联系人联系方式错误");
			}
		}
		else if ("/organization/updateOrganization".equals(actionKey)) {
			String name = controller.getAttrForStr("name");
			String abbreviation = controller.getAttrForStr("abbreviation");
			String address = controller.getAttrForStr("address");
			String liasion = controller.getAttrForStr("liasion");
			String tel = controller.getAttrForStr("tel");

			if (name != null && name.length() > 0) {
				validateRegex(name, "^[a-zA-Z0-9\\u4e00-\\u9fa5]{4,30}$", "searchOrganizations", "机构名称错误");
			}
			if (abbreviation != null && abbreviation.length() > 0) {
				validateRegex(abbreviation, "^[\\u4e00-\\u9fa5]{4,10}$", "searchOrganizations", "机构简称错误");
			}
			if (address != null && address.length() > 0) {
				validateRegex(address, "^[a-zA-Z0-9\\u4e00-\\u9fa5]{5,50}$", "searchOrganizations", "机构地址错误");
			}
			if (liasion != null && liasion.length() > 0) {
				validateRegex(liasion, "^[\\u4e00-\\u9fa5]{2,15}$", "searchOrganizations", "联系人错误");
			}
			if (tel != null && tel.length() > 0) {
				validateRegex(tel, "^[0]\\d{2,4}[-]\\d{8}$", "searchOrganizations", "机构联系方式错误");
			}

		}
		else if ("/organization/setOrganizationTransformInfo".equals(actionKey)) {
			String block = controller.getAttr("block");
			String year = controller.getAttr("year");
			validateRegex(block, "^[0-1]$", "setOrganizationTransformInfo", "参与转换设置错误");
			validateRegex(year, "^[1-9]\\d{3}$", "setOrganizationTransformInfo", "年届错误");
		}
		else if ("/organization/searchOrganizationsJoinByYearAtDropdown".equals(actionKey)) {
			// 验证 searchOrganizationsJoinByYearAtDropdown 方法参数
			String year = controller.getAttrForStr("year");
			validateRegex(year, "^[1-9]\\d{3}$", "searchOrganizationsJoinByYearAtDropdown", "year错误");
		}
		else if ("/organization/searchOrganizationsJoinWithProfessionByYearAtDropdown".equals(actionKey)) {
			// 验证 searchOrganizationsJoinWithProfessionByYearAtDropdown 方法参数
			validateRegex("year", "^[1-9]\\d{3}$", "searchOrganizationsJoinWithProfessionByYearAtDropdown", "year错误");
			validateRegex("professionId", "^[1-9]\\d{1,20}$", "searchOrganizationsJoinWithProfessionByYearAtDropdown", "professionId错误");
		}
	}

}
