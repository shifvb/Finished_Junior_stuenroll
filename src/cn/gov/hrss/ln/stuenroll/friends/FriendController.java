package cn.gov.hrss.ln.stuenroll.friends;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.spring.Spring;

import cn.gov.hrss.ln.stuenroll.plugin.MongoKit;

@Spring("friendController")
public class FriendController extends Controller implements I_FriendController {
	private I_FriendService i_FriendService;
	private int friendPerPage;

	@Override
	public void searchUserByUsername() {
		String username = getPara("username");
		Record record = i_FriendService.searchUserByUsername(username);
		// 如果没有找到user，那么返回false
		if (record == null) {
			renderJson("result", false);
			return;
		}
		else {
			// 如果找到，根据user_id查询状态
			Record temp_record = i_FriendService.searchUserLogTimeAndType(record.getStr("user_id"));
			if (temp_record == null) {
				record.set("online", false);
			} else {
				String type = temp_record.getStr("type");
				if (type.equals("退出")) {
					record.set("online", false);
				}
				else if (type.equals("登陆")) {
					Long now = new Date().getTime();
					Long old = temp_record.getTimestamp("create_time").getTime();
					record.set("online", now - old <= 1800000L);
				}
			}
			renderJson("result", record);
		}

	}

	@Before(Tx.class)
	@Override
	public void addUserToFriendListByUserId() {
		String sponser = getPara("sponser");
		String target = getPara("target");
		if (sponser.equals(target)) {
			Map map = new HashMap();
			map.put("result", false);
			map.put("reason", "不能添加自己为好友！");
			renderJson(map);
			return;
		}
		Long relation_counts = i_FriendService.searchFriendRelationCounts(Long.valueOf(sponser), Long.valueOf(target));
		if (relation_counts > 0) {
			Map map = new HashMap();
			map.put("result", false);
			map.put("reason", "好友已存在");
			renderJson(map);
			return;
		}

		int i = i_FriendService.addUserToFriendListByUserId(sponser, target, "0");
		renderJson("result", i == 1);
	}

	@Override
	public void searchFriend() {
		String user_id_str = getPara("user_id");
		if (user_id_str == null) {
			renderJson("result", false);
		}
		Long user_id = Long.valueOf(user_id_str);

		String page_str = getPara("page");
		if (page_str == null) {
			page_str = "1";
		}
		Long page = Long.valueOf(page_str);
		Long start = (page - 1) * friendPerPage;
		Long end = start + friendPerPage;

		List<Record> records = i_FriendService.searchFriend(user_id, start, end);
		for (Record x : records) {
			// 得到photo的base64字符串
			x.set("photo_base64", MongoKit.findImageToBase64String(x.getStr("photo_id"), "image"));
			// 判断用户登录状态
			Record temp_record = i_FriendService.searchUserLogTimeAndType(x.getStr("user_id"));
			if (temp_record == null) {
				x.set("online", false);
				continue;
			} else {
				String type = temp_record.getStr("type");
				if (type.equals("退出")) {
					x.set("online", false);
					continue;
				}
				else if (type.equals("登陆")) {
					Long now = new Date().getTime();	
					Long old = temp_record.getTimestamp("create_time").getTime();
					x.set("online", now - old <= 1800000L);
				}
			}
		}
		renderJson("result", records);
	}

	@Override
	public void searchFriendCount() {
		String user_id_str = getPara("user_id");
		if (user_id_str == null) {
			renderJson("result", false);
		}

		Long user_id = Long.valueOf(user_id_str);
		Long i = i_FriendService.searchFriendCount(user_id);
		renderJson("result", i);
	}

	@Before(Tx.class)
	@Override
	public void changeFriendStatus() {
		String sponser_id_str = getPara("sponser_id");
		String target_id_str = getPara("target_id");
		String favorite = getPara("favorite");
		if (sponser_id_str == null || target_id_str == null || favorite == null) {
			renderJson("result", false);
		}
		Long sponser_id = Long.valueOf(sponser_id_str);
		Long target_id = Long.valueOf(target_id_str);
		int i = i_FriendService.changeFriendStatus(sponser_id, target_id, favorite);
		renderJson("result", i == 1);
	}

	@Before(Tx.class)
	@Override
	public void deleteFriend() {
		String sponser_id_str = getPara("sponser_id");
		String target_id_str = getPara("target_id");
		if (sponser_id_str == null || target_id_str == null) {
			renderJson("result", false);
		}
		Long sponser_id = Long.valueOf(sponser_id_str);
		Long target_id = Long.valueOf(target_id_str);
		int i = i_FriendService.deleteFriend(sponser_id, target_id);
		renderJson("result", i == 1);
	}

	public I_FriendService getI_FriendService() {
		return i_FriendService;
	}

	public void setI_FriendService(I_FriendService i_FriendService) {
		this.i_FriendService = i_FriendService;
	}

	public int getFriendPerPage() {
		return friendPerPage;
	}

	public void setFriendPerPage(int friendPerPage) {
		this.friendPerPage = friendPerPage;
	}

}
