package com.kingsware.kdev.core.cache.session;

import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.orm.DB;

import java.util.*;

/**
 * // 字典管理 单实例.
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:25 上午
 */
public class SessionManager {
    /** 实例 **/
    private static SessionManager instance;
    /** 字典缓存 **/
    private Map<String, Set<SysOnlineUser>> sessionMapping = new HashMap<>();

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private SessionManager() {
    }

    /**
     * 增加字典项
     * @param onlineUser  在线用户
     */
    public void addSession(SysOnlineUser onlineUser) {
        Set<SysOnlineUser> onlineUsers = sessionMapping.computeIfAbsent(onlineUser.getUserId(), key -> new HashSet<>());
        onlineUsers.add(onlineUser);
    }

    /**
     * 重置所有会话
     */
    public void reloadSessions() {
        // 查找所有会话
        List<SysOnlineUser> onlineUserList = DB.findList(SysOnlineUser.class, Collections.emptyList());
        Map<String, Set<SysOnlineUser>> map = new HashMap<>();
        for (SysOnlineUser onlineUser: onlineUserList) {
            Set<SysOnlineUser> onlineUsers = map.computeIfAbsent(onlineUser.getUserId(), key -> new HashSet<>());
            onlineUsers.add(onlineUser);
        }
        this.sessionMapping = map;
    }

    public void removeByUserId(String userId) {
        this.sessionMapping.remove(userId);
    }

    /**
     * 检查会话
     * @param userId  用户id
     * @param loginToken 令牌
     * @return  是否成功
     */
    public boolean checkSession(String userId, String loginToken) {
        if (sessionMapping.containsKey(userId)) {
            Set<SysOnlineUser> onlineUsers = sessionMapping.get(userId);
            for (SysOnlineUser onlineUser: onlineUsers) {
                if (onlineUser.getLoginToken().equals(loginToken)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除会话
     * @param userId  用户id
     * @param loginToken 令牌
     */
    public void removeSession(String userId, String loginToken) {
      if (sessionMapping.containsKey(userId)) {
          Set<SysOnlineUser> onlineUsers = sessionMapping.get(userId);
          for (SysOnlineUser onlineUser: onlineUsers) {
              if (onlineUser.getLoginToken().equals(loginToken)) {
                  onlineUsers.remove(onlineUser);
                  return;
              }
          }
      }
    }
}
