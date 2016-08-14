package cn.gov.hrss.ln.stuenroll.db;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public interface I_FriendDao {

	/**
	 * 根据username查询user的ID,姓名，角色名，机构名
	 * 
	 * @param username
	 * @return
	 */
	public Record searchUserByUsername(String username);

	/**
	 * 根据自己的userID和对方的userID添加对方为好友， （对方并不会知道）
	 * 
	 * @param sponser
	 * @param target
	 * @param favorite
	 * @return
	 */
	public int addUserToFriendListByUserId(String sponser, String target, String favorite);

	/**
	 * 根据传入的userID查找这个user所有好友的ID,姓名，角色名，机构名
	 * 
	 * @param user_id
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Record> searchFriend(Long user_id, Long start, Long end);

	/**
	 * 根据传入的userID查找这个user好友的数量
	 * 
	 * @param user_id
	 * @return
	 */
	public Long searchFriendCount(Long user_id);

	/**
	 * 改变好友关系中的“收藏”状态
	 * 
	 * @param sponser_id
	 * @param target_id
	 * @param favorite
	 * @return
	 */
	public int changeFriendStatus(Long sponser_id, Long target_id, String favorite);

	/**
	 * 删除用户
	 * 
	 * @param sponser_id
	 * @param target_id
	 * @return
	 */
	public int deleteFriend(Long sponser_id, Long target_id);

	/**
	 * 删除所有关于此用户的好友记录
	 * 
	 * @param id
	 * @return
	 */
	public int deleteAllFriends(String id);

	/**
	 * 寻找特定的好友关系在数据库里的数量，一般用来判断是否已经加了好友
	 * 
	 * @param sponser_id
	 * @param target_id
	 * @return
	 */
	public Long searchFriendRelationCounts(Long sponser_id, Long target_id);

}
