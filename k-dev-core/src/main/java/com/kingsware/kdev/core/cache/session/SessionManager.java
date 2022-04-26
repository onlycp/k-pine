package com.kingsware.kdev.core.cache.session;

import com.kingsware.kdev.core.auth.AuthToken;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.*;

/**
 * // 会话管理 单实例.
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:25 上午
 */
@Slf4j
public class SessionManager {
    /** 实例 **/
    private static SessionManager instance;
    /** 字典缓存 **/
    private Map<String, Set<TokenSession>> sessionMapping = new HashMap<>();

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
        Set<TokenSession> onlineUsers = sessionMapping.computeIfAbsent(onlineUser.getUserId(), key -> new HashSet<>());
        TokenSession tokenSession = BeanUtils.copyObject(onlineUser, TokenSession.class);
        tokenSession.setActiveTime(onlineUser.getLoginTime());
        onlineUsers.add(tokenSession);
    }

    /**
     * 重置所有会话
     */
    public void reloadSessions() {
        // 查找所有会话
        List<SysOnlineUser> onlineUserList = DB.findList(SysOnlineUser.class, Collections.emptyList());
        Map<String, Set<TokenSession>> map = new HashMap<>();
        for (SysOnlineUser onlineUser: onlineUserList) {
            Set<TokenSession> onlineUsers = map.computeIfAbsent(onlineUser.getUserId(), key -> new HashSet<>());
            // 从当前缓存中读取，如果已存在，则直接以缓存中的为准
            TokenSession ts = getByToken(onlineUser.getUserId(), onlineUser.getLoginToken());
            if (ts == null) {
                ts = BeanUtils.copyObject(onlineUser, TokenSession.class);
                ts.setActiveTime(onlineUser.getLoginTime());
            }
            onlineUsers.add(ts);

        }
        this.sessionMapping = map;
    }

    /**
     * 通过用户id和令牌获取令牌会话
     * @param userId    用户id
     * @param token     令牌
     * @return
     */
    public TokenSession getByToken(String userId, String token) {
        if (sessionMapping.containsKey(userId)) {
            Set<TokenSession> tokenSessions = sessionMapping.get(userId);
            for (TokenSession ts: tokenSessions) {
                if (ts.getLoginToken().equals(token)) {
                    return ts;
                }
            }
        }
        return null;
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
            Set<TokenSession> onlineUsers = sessionMapping.get(userId);
            for (TokenSession onlineUser: onlineUsers) {
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
          Set<TokenSession> onlineUsers = sessionMapping.get(userId);
          for (TokenSession onlineUser: onlineUsers) {
              if (onlineUser.getLoginToken().equals(loginToken)) {
                  onlineUsers.remove(onlineUser);
                  return;
              }
          }
      }
    }

    /**
     * 失活会话
     * @param userId    用户id
     * @param loginToken    登录令牌
     */
    public void inActiveSession(String userId, String loginToken) {
        if (sessionMapping.containsKey(userId)) {
            Set<TokenSession> onlineUsers = sessionMapping.get(userId);
            for (TokenSession onlineUser: onlineUsers) {
                if (onlineUser.getLoginToken().equals(loginToken)) {
                    if (onlineUser.isActive()) {
                        log.info("用户名：{} 失活，原因是心跳超时", userId);
                    }
                    onlineUser.setActive(false);
                    return;
                }
            }
        }
    }

    /**
     * 用户的活动会话数
     * @param userId    用户id
     * @return          激活数
     */
    public long activeCount(String userId, String sessionId) {
        Set<TokenSession> tokenSessions = sessionMapping.get(userId);
        if (tokenSessions == null) {
            return 0L;
        }
        // 同一个会话的不算
        return tokenSessions.stream().filter(TokenSession::isActive).filter(it -> {
            AuthToken authToken = TokenUtil.getAuthToken(it.getLoginToken());
            if (authToken == null) {
                return false;
            }
            return !sessionId.equals(authToken.getKSessionId());
        }).count();

    }


    /**
     * 更新活动时间
     * @param userId        用户id
     * @param loginToken    登录令牌
     * @param updateExpired  是否要更新过期时间
     */
    public void updateActiveTime(String userId, String loginToken, int mockSessionExpireTime, boolean updateExpired) {
        if (sessionMapping.containsKey(userId)) {
            Set<TokenSession> onlineUsers = sessionMapping.get(userId);
            for (TokenSession onlineUser: onlineUsers) {
                if (onlineUser.getLoginToken().equals(loginToken)) {
                    onlineUser.setActiveTime(new Timestamp(System.currentTimeMillis()));
                    onlineUser.setHasChanged(true);
                    if (mockSessionExpireTime > 0 && updateExpired) {
                        onlineUser.setExpireTime(new Timestamp(onlineUser.getActiveTime().getTime() + (long) mockSessionExpireTime * 1000 * 60));
                    }
                }
            }
        }
    }

    /**
     * 获取所有的
     * @return
     */
    public Set<TokenSession> getChanged() {
        Set<TokenSession> tokenSessions = new HashSet<>();
        for (Map.Entry<String, Set<TokenSession>> entry: sessionMapping.entrySet()) {
            for (TokenSession ts: entry.getValue()) {
                if (ts.isHasChanged()) {
                    tokenSessions.add(ts);
                }
            }
        }
        return tokenSessions;
    }

    /**
     * 获取所有的会话
     * @return  会话
     */
    public Set<TokenSession> sessions() {
        Set<TokenSession> tokenSessions = new HashSet<>();
        for (Map.Entry<String, Set<TokenSession>> entry: sessionMapping.entrySet()) {
            tokenSessions.addAll(entry.getValue());
        }
        return tokenSessions;
    }
}
