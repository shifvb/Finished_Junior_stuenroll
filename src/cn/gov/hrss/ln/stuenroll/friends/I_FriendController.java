package cn.gov.hrss.ln.stuenroll.friends;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public interface I_FriendController {

	/**
	 * 根据username查询user的ID, 姓名，角色名，机构名
	 * 
	 * 接口： /stuenroll/friend/searchUserByUsername?username=houyining
	 */
	public void searchUserByUsername();

	/**
	 * 根据自己的userID和对方的userID添加对方为好友， （对方并不会知道）
	 *
	 * 接口： /stuenroll/friend/addUserToFriendListByUserId?
	 * sponser=5019130901499904&target=19399815106560
	 */
	public void addUserToFriendListByUserId();

	/**
	 * 根据传入的userID查找这个user所有好友的ID,姓名，角色名，机构名，收藏状态，图片ID
	 * 
	 * 接口：
	 * 	/stuenroll/friend/searchFriend?user_id=5019130901499904&page=1
	 */
	public void searchFriend();
	
	/**
	 * 根据传入的userID查找这个user好友的数量
	 * 
	 * 接口：
	 * 	/stuenroll/friend/searchFriendCount?user_id=5019130901499904
	 */
	public void searchFriendCount();
	
	/**
	 * 改变好友关系中的“收藏”状态
	 * 
	 * 接口：
	 * 	/stuenroll/friend/changeFriendStatus?sponser_id=5019130901499904
	 * 		&target_id=19399815106560&favorite=0;
	 */
	public void changeFriendStatus();
	
	/**
	 * 删除用户
	 * 
	 * 接口：
	 * 	/stuenroll/friend/deleteFriend?sponser_id=5019130901499904
	 * 		&target_id=19399815106560
	 * 
	 */
	public void deleteFriend();

}
