package cn.gov.hrss.ln.stuenroll.permission;

import java.util.ArrayList;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.jfinal.plugin.activerecord.Record;

/**
 * Shiro模块类
 * 
 * @author YangDi
 *
 */
public class ShiroRealm extends AuthorizingRealm {
	/**
	 * 授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String role = (String) session.getAttribute("role");
		ArrayList<Record> permissions = (ArrayList) session.getAttribute("permissions");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole(role);
		for (Record record : permissions) {
			String permission=record.getStr("permission_id");
			info.addStringPermission(permission);
		}
		return info;

	}

	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken tk = (UsernamePasswordToken) token;
		String username = tk.getUsername();
		String password = new String(tk.getPassword());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName()); // 认证信息
		return info;
	}

}
