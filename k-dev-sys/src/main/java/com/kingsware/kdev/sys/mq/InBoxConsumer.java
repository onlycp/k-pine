package com.kingsware.kdev.sys.mq;

import com.kingsware.kdev.core.bean.NoticeMessage;
import com.kingsware.kdev.core.kmq.KmqConsumer;
import com.kingsware.kdev.core.kmq.KmqMessageCenter;
import com.kingsware.kdev.core.model.SysLoginLog;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.DateUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.model.SysNoticeRecord;
import com.kingsware.kdev.sys.model.SysUser;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站内信
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/31 1:48 下午
 */
@Slf4j
public class InBoxConsumer implements KmqConsumer {

    @Override
    public void onMessage(List<String> payloads) throws Exception {
        List<SysUser> sysUsers = DB.findList(SysUser.class, "select id, username from sys_user");
        Map<String, String> usernameMap = new HashMap<>();
        List<String> userIds = new ArrayList<>();
        sysUsers.forEach(it-> usernameMap.put(it.getId(), it.getUsername()));
        sysUsers.forEach(it-> userIds.add(it.getId()));
        String allUserIds = StringUtils.joinToString(userIds, ",");
        List<SysNoticeRecord> addRecords = new ArrayList<>();
        for (String payload: payloads) {
            NoticeMessage noticeMessage = JsonUtil.toBean(payload, NoticeMessage.class);
            // 广播给所有有人
            if (StringUtils.isEmpty(noticeMessage.getToWhos())) {
                noticeMessage.setToWhos(allUserIds);
            }
            if (StringUtils.isEmpty(noticeMessage.getFromWho())) {
                noticeMessage.setFromWho("056fb0eeb9a44cb0953534b4c0ca01fa");;
            }
            String[] toWhoUserIds = noticeMessage.getToWhos().split(",");
            for(String toWhoId: toWhoUserIds) {
                if (usernameMap.containsKey(toWhoId)) {
                    SysNoticeRecord sysNoticeRecord = new SysNoticeRecord();
                    sysNoticeRecord.setContent(noticeMessage.getContent());
                    sysNoticeRecord.setTitle(noticeMessage.getTitle() );
                    sysNoticeRecord.setFromWho(noticeMessage.getFromWho());
                    sysNoticeRecord.setFromWhoName(usernameMap.get(noticeMessage.getFromWho()));
                    sysNoticeRecord.setToWho(toWhoId);
                    sysNoticeRecord.setToWhoName(usernameMap.get(toWhoId));
                    sysNoticeRecord.setNoticeTime(Timestamp.valueOf(DateUtils.getNow()));
                    if (StringUtils.isNotEmpty(noticeMessage.getId())) {
                        sysNoticeRecord.setId(noticeMessage.getId());
                    }
                    sysNoticeRecord.setIsRead(0);
                    addRecords.add(sysNoticeRecord);

                }
            }
        }
        if (!addRecords.isEmpty()) {
            DB.saveAll(addRecords);
            // 发送websocket消息
            addRecords.forEach(it -> {
                KmqMessageCenter.getInstance().produceWebsocketMessageToUser(it.getToWho(),"notice-center", JsonUtil.toJson(it));
            });

        }
    }

    @Override
    public String topic() {
        return "inbox";
    }

}
