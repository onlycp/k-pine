package com.kingsware.kdev.core.cache.session;

import com.kingsware.kdev.core.auth.AuthToken;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.kmq.websocket.SessionToken;
import com.kingsware.kdev.core.model.SysOnlineUser;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.expression.Expr;
import com.kingsware.kdev.core.util.BeanUtils;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.MD5Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * // 会话管理 单实例.
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/6 9:25 上午
 */
@Slf4j
public class SessionManager {
    /**
     * 实例
     **/
    private static SessionManager instance;
    /**
     * 字典缓存
     **/
    private Map<String, Set<TokenSession>> sessionMapping = new ConcurrentHashMap<>();
    private Map<String, String> tokenHashMap = new ConcurrentHashMap<>();
    /**
     * 最后更新时间，用于增量加载
     **/
    private Timestamp lastReloadTime = null;

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
     *
     * @param onlineUser 在线用户
     */
    public void addSession(SysOnlineUser onlineUser) {
        Set<TokenSession> onlineUsers = sessionMapping.computeIfAbsent(onlineUser.getUserId(), key -> new HashSet<>());
        TokenSession tokenSession = BeanUtils.copyObject(onlineUser, TokenSession.class);
        tokenSession.setActiveTime(onlineUser.getLoginTime());
        boolean isNew = onlineUsers.stream().noneMatch(ts -> ts.isMe(tokenSession.getLoginToken()));
        if (isNew) {
//            log.info("session添加1:" + tokenSession.getId());
            onlineUsers.add(tokenSession);
//            log.info("session添加完成1:" + JsonUtil.toJson(sessionMapping.get(onlineUser.getUserId())));
            sessionMapping.put(onlineUser.getUserId(), onlineUsers);
//            log.info("session添加完成2:" + JsonUtil.toJson(sessionMapping.get(onlineUser.getUserId())));
//            log.info("session添加2:" + tokenSession.getId());
        }

    }

    /**
     * 通过md5获取token
     *
     * @param md5 md5
     * @return token
     */
    public String getTokenByMd5(String md5) {
        if (!tokenHashMap.containsKey(md5)) {
            reloadSessions();
        }
        return tokenHashMap.get(md5);
    }

    /**
     * 重置所有会话（支持增量加载）
     */
    public void reloadSessions() {

        // 构建查询条件：如果 lastReloadTime 为空则查全部，否则只查增量（使用>=避免漏数据）
        List<SysOnlineUser> onlineUserList = lastReloadTime == null
                ? DB.findList(SysOnlineUser.class, Collections.emptyList())
                : DB.findList(SysOnlineUser.class, Expr.builder().add("loginTime", ">=", lastReloadTime).build());

        // 如果是首次加载（全量），重建 sessionMapping
        if (lastReloadTime == null) {
            this.sessionMapping = new ConcurrentHashMap<>();
        }

        // 更新会话数据
        for (SysOnlineUser onlineUser : onlineUserList) {
            Set<TokenSession> onlineUsers = sessionMapping.computeIfAbsent(onlineUser.getUserId(), key -> new HashSet<>());
            // 从当前缓存中读取，如果已存在，则直接以缓存中的为准
            TokenSession ts = getByToken(onlineUser.getUserId(), onlineUser.getLoginToken());
            if (ts == null) {
                ts = BeanUtils.copyObject(onlineUser, TokenSession.class);
                ts.setActiveTime(onlineUser.getLoginTime());
                onlineUsers.add(ts);
            }
        }

        // 更新 lastReloadTime 为最大的 loginTime
        if (!onlineUserList.isEmpty()) {
            Timestamp maxLoginTime = onlineUserList.stream()
                    .map(SysOnlineUser::getLoginTime)
                    .filter(Objects::nonNull)
                    .max(Timestamp::compareTo)
                    .orElse(lastReloadTime);
            if (maxLoginTime != null) {
                lastReloadTime = maxLoginTime;
            }
        }

        // 计算所有的hash
        sessionMapping.forEach((userId, onlineUsers) -> {
            onlineUsers.forEach(tokenSession -> {
                String md5Value = MD5Utils.md5(tokenSession.getLoginToken());
                tokenHashMap.put(md5Value, tokenSession.getLoginToken());
            });
        });
        List<String> dbIds = DB.findSingleAttributeList(String.class, "select id from sys_online_user");
        sessionMapping.forEach((userId, onlineUsers) -> {
            onlineUsers.removeIf(ts -> !dbIds.contains(ts.getId()));
        });


    }

    /**
     * 通过用户id和令牌获取令牌会话
     *
     * @param userId 用户id
     * @param token  令牌
     * @return
     */
    public TokenSession getByToken(String userId, String token) {
        if (sessionMapping.containsKey(userId)) {
            Set<TokenSession> tokenSessions = sessionMapping.get(userId);
            for (TokenSession ts : tokenSessions) {
                if (ts.isMe(token)) {
                    return ts;
                }
            }
        }
        return null;
    }

    /**
     * 判断给定的token是否存在于哈希映射中
     *
     * @param token 要检查的token字符串
     * @return 如果token存在于哈希映射中，则返回true；否则返回false
     */
    public boolean hasToken(String token) {
        return tokenHashMap.containsKey(token);
    }

    /**
     * 根据用户ID移除相应的会话映射
     *
     * @param userId 用户ID，用于定位并移除相应的会话映射
     */
    public void removeByUserId(String userId) {
        this.sessionMapping.remove(userId);
    }


    /**
     * 根据ID移除会话
     * @param userId
     * @param id
     */
    public void removeById(String userId, String id) {
        if (sessionMapping.containsKey(userId)) {
            sessionMapping.get(userId).removeIf(tokenSession -> tokenSession.getId().equals(id));
        }
    }

    /**
     * 根据ID列表批量移除会话
     * @param ids ID列表
     */
    public void removeByIds(List<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            sessionMapping.values().forEach(tokenSessions ->
                    tokenSessions.removeIf(tokenSession -> ids.contains(tokenSession.getId()))
            );
        }
    }

    /**
     * 检查会话
     *
     * @param userId     用户id
     * @param loginToken 令牌
     * @return 是否成功
     */
    public boolean checkSession(String userId, String loginToken) {
        if (sessionMapping.containsKey(userId)) {
            Set<TokenSession> onlineUsers = sessionMapping.get(userId);
            if (!onlineUsers.isEmpty()) {
                TokenSession ts = onlineUsers.stream().max(Comparator.comparing(TokenSession::getLoginTime)).get();
                return ts.isMe(loginToken);
            }
        }
        return false;
    }

    /**
     * 删除会话
     *
     * @param userId     用户id
     * @param loginToken 令牌
     */
    public void removeSession(String userId, String loginToken) {
        String token = getTokenByMd5(MD5Utils.md5(loginToken));
        // 移除在线会话
        DB.executeUpdateSql("delete from sys_online_user where login_token=?", token);
        if (sessionMapping.containsKey(userId)) {
            Set<TokenSession> onlineUsers = sessionMapping.get(userId);
            onlineUsers.removeIf(tokenSession -> tokenSession.isMe(loginToken));
        }
    }

    /**
     * 通过 loginToken 删除会话（无需 userId）
     *
     * @param loginToken 令牌
     */
    public void removeSessionByToken(String loginToken) {
        for (Map.Entry<String, Set<TokenSession>> entry : sessionMapping.entrySet()) {
            Set<TokenSession> onlineUsers = entry.getValue();
            boolean removed = onlineUsers.removeIf(tokenSession -> tokenSession.isMe(loginToken));
            if (removed) {
                return;
            }
        }
    }


    public void updateSession(TokenSession session) {
        if (session == null) {
            return;
        }
        sessionMapping.values().forEach(tokenSessions -> {
            tokenSessions.stream()
                    .filter(onlineUser -> onlineUser.isMe(session.getLoginToken()))
                    .forEach(onlineUser -> {
                        onlineUser.setActiveTime(session.getActiveTime());
                        onlineUser.setExpireTime(session.getExpireTime());
                        onlineUser.setPingTime(session.getPingTime());
                        onlineUser.setLoginTime(session.getLoginTime());
//                   onlineUser.setHasChanged(session.isHasChanged());
                    });
        });
    }

    /**
     * 失活会话
     *
     * @param userId     用户id
     * @param loginToken 登录令牌
     */
    public void inActiveSession(String userId, String loginToken) {
        if (sessionMapping.containsKey(userId)) {
            Set<TokenSession> onlineUsers = sessionMapping.get(userId);
            onlineUsers.stream()
                    .filter(onlineUser -> onlineUser.isMe(loginToken))
                    .findFirst()
                    .ifPresent(onlineUser -> {
                        if (onlineUser.isActive()) {
                            log.info("用户名：{} 失活，原因是心跳超时", userId);
                        }
                        onlineUser.setActive(false);
                    });
        }
    }

    /**
     * 用户的活动会话数
     *
     * @param userId 用户id
     * @return 激活数
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
     *
     * @param userId        用户id
     * @param loginToken    登录令牌
     * @param updateExpired 是否要更新过期时间
     */
    public void updateActiveTime(String userId, String loginToken, int mockSessionExpireTime, boolean updateExpired) {
        if (sessionMapping.containsKey(userId)) {
            Set<TokenSession> onlineUsers = sessionMapping.get(userId);
            onlineUsers.stream()
                    .filter(onlineUser -> onlineUser.isMe(loginToken))
                    .forEach(onlineUser -> {
                        if (updateExpired) {
                            // 检查距离上次更新是否超过1分钟
                            long currentTime = System.currentTimeMillis();
                            long lastActiveTime = onlineUser.getActiveTime().getTime();
                            long timeDiff = Math.abs(currentTime - lastActiveTime);

                            // 只有超过1分钟（60000毫秒）才更新
                            if (timeDiff > 60000) {
                                onlineUser.setActiveTime(new Timestamp(currentTime));
                                //log.info("更新活动时间:{}", DateUtils.formatDate(onlineUser.getActiveTime(), DateUtils.DATE_TIME));
                                onlineUser.setHasChanged(true);
                                onlineUser.setExpireTime(new Timestamp(onlineUser.getActiveTime().getTime() + (long) mockSessionExpireTime * 1000 * 60));
                                InstanceManager.getInstance().broadMessage("session-update", JsonUtil.toJson(onlineUser));
                            }
                        }
                    });
        }
    }

    /**
     * 获取所有的
     *
     * @return
     */
    public Set<TokenSession> getChanged() {
        return sessionMapping.values().stream()
                .flatMap(Set::stream)
                .filter(TokenSession::isHasChanged)
                .collect(Collectors.toSet());
    }

    /**
     * 获取所有的会话
     *
     * @return 会话
     */
    public Set<TokenSession> sessions() {
        Set<TokenSession> tokenSessions = new HashSet<>();
        for (Map.Entry<String, Set<TokenSession>> entry : sessionMapping.entrySet()) {
            tokenSessions.addAll(entry.getValue());
        }
        return tokenSessions;
    }
}
