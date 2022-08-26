package com.kingsware.kdev.core.bean;

import com.kingsware.kdev.core.util.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogStack {

    class LogItem {
        String time;
        String message;
    }

    /** 消息 **/
    private final List<LogItem> messages = new ArrayList<>();

    /**
     * 增加消息
     * @param message
     */
    public void addMessage(String message) {
        LogItem item = new LogItem();
        item.time = DateUtils.getNow();
        item.message = message;
        messages.add(item);
    }

    /**
     * 格式化消息
     * @return
     */
    public Map<String, Object> formatMessages() {
        StringBuffer buffer = new StringBuffer();
        for (LogItem item: messages) {
            buffer.append(String.format("%s %s", item.time, item.message)).append("\n");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("logs", buffer.toString());
        return result;
    }
}
