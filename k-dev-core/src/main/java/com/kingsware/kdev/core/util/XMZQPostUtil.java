//package com.kingsware.kdev.core.util;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.*;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
///*
// * 厦门证券的短信接口（http url调用）
// */
//@Slf4j
//public class XMZQPostUtil {
//    // static XStream xstream = new XStream(new DomDriver ());
//
//    static String uri = null;
//    static String sys_no = null;
//    static String branch_no = null;
//
//    // static String uri = "https://www.cunnar.com/";
//    public static void init() {
////        if (uri == null) {
////            uri = GlobalName.getProperty("server_uri");
////        }
////        if (uri == null || uri.equals("")) {
////            uri = "http://168.166.129.175:8080";
////        }
////        if (sys_no == null) {
////            sys_no = GlobalName.getProperty("server_uri_sys_no");
////        }
////        if (sys_no == null || sys_no.equals("")) {
////            sys_no = "1234567890";
////        }
////        if (branch_no == null) {
////            branch_no = GlobalName.getProperty("server_uri_branch_no");
////        }
////        if (branch_no == null || branch_no.equals("")) {
////            branch_no = "1111111111";
////        }
//    }
//
//    /*
//     * 返回发送失败的手机号码和错误码
//     */
//    public static java.util.List sendSms(
//            java.util.List<String> mobiles, String userName, String smsContent) {
//        // String token = getToken();
//        init();
//        int iResult = -1;
//        java.util.List list = new ArrayList();
//        String url = uri;
//        String ret;
//        if (mobiles != null && mobiles.size() > 0) {
//            for (String tel : mobiles) {
//                java.util.Map<String, String> hm = new java.util.HashMap<String, String>();
//                String errorNo = "";
//                String errorMsg = "";
//                String telno = tel;
//                try {
//                    String xmlStr = getXml(tel,userName,smsContent);
//                    //HttpEntity inEntity = new StringEntity(xmlStr, "UTF-8");
//                    HttpEntity inEntity = new StringEntity(xmlStr, "GBK");
//                    int XZ_Type = strToAscII(tel,DateUtils.formatDate(new Date(),"yyyyMMdd"));
//
//                    log.info("url:"+url+",XZ_Type:"+XZ_Type+",xmlStr"+xmlStr);
//                    String pineUrl = "http://127.0.0.1:8080/api/v1/test/gbk";
//                    ret = post(pineUrl, inEntity, Integer.toString(XZ_Type));
//                    log.info("ret:"+ret);
//                    System.out.println("ret:"+ret);
//                    if (ret!=null && !ret.equals("")){
//                        Map<String, Object> map = parseXml2Map(ret);
//                        if (map.containsKey("Root")) {
//                            java.util.Map result = (java.util.HashMap) map
//                                    .get("Root");
//                            if (result.containsKey("ERROR_NO")) {
//                                errorNo = (String) result.get("ERROR_NO");
//                                if (!"0".equals(errorNo)) {
//                                    errorMsg = (String) result.get("ERROR_MSG");
//                                    telno = (String) result.get("PHONECODE");
//                                    log.error("telno=" + telno + ",errorNo="+ errorNo + ",errorMsg=" + errorMsg+ ".");
//                                } else {
//                                    log.info("telno=" + telno + ",errorNo="+ errorNo + ".");
//                                }
//                            }
//                        }
//                    }else{
//                        errorNo = "-1";
//                        errorMsg = "http请求失败";
//                    }
//                } catch (Exception e) {
//                    errorNo = "-1";
//                    errorMsg = "http请求失败";
//                    e.printStackTrace();
//                    log.error("发送短信异常", e);
//                }
//                if (!"0".equals(errorNo)) {
//                    //hm.put(telno, errorMsg);
//                    hm.put("telno", telno);
//                    hm.put("errCode", errorNo);
//                    hm.put("errMsg", errorMsg);
//                    list.add(hm);
//                }
//
//            }
//        }
//        return list;
//    }
//
//    public static String post(String url, HttpEntity inEntity, String XZ_Type) {
//        String ret = "";
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(url);
//
//        try {
//            httppost.addHeader("XZ-Type", XZ_Type);
//            httppost.setEntity(inEntity);
//
//            HttpResponse response;
//            response = httpclient.execute(httppost);
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                InputStream instream = entity.getContent();
//                try {
//                    //BufferedReader reader = new BufferedReader(new InputStreamReader(instream, "utf-8"));
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream, "GBK"));
//                    String temp = "";
//                    while (temp != null) {
//                        ret += temp;
//                        temp = reader.readLine();
//                    }
//                    System.out.println("ret:"+ret);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    instream.close();
//                }
//            }
//            httpclient.getConnectionManager().shutdown();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ret;
//    }
//
//    public static int strToAscII(String telno, String sDate) {
//        int iSum = 0;
//        if (telno != null && !telno.equals("") && sDate != null
//                && !sDate.equals("")) {
//            String tmpStr = telno + sDate;
//            char[] chars = tmpStr.toCharArray(); // 把字符中转换为字符数组
//            for (int i = 0; i < chars.length; i++) {
//                iSum += (int) chars[i];
//            }
//        }
//        return iSum;
//    }
//
//    public static String getXml(String telno, String userName, String smsMsg) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//        // sb.append("<root>");
//        sb.append("<SYS_NO>"+sys_no+"</SYS_NO>");
//        sb.append("<PHONECODE>" + telno + "</PHONECODE>");
//        sb.append("<CONTENT>" + smsMsg + "</CONTENT>");
//        sb.append("<BRANCH_NO>"+branch_no+"</BRANCH_NO>");
//        sb.append("<USERNAME>" + userName + "</USERNAME>");
//        sb.append("<SEQ>" + RandomUtils.randString(10) + "</SEQ>");
//        sb.append("<TASK_DATE></TASK_DATE>");
//        sb.append("<TASK_TIME></TASK_TIME>");
//        sb.append("<PRIORITY>0</PRIORITY>");
//        // sb.append("</root>");
//        return sb.toString();
//    }
//
//    /**
//     * 解析XML字符串为Map结构
//     * @param xmlStr XML字符串
//     * @return 解析后的Map
//     */
//    public static Map<String, Object> parseXml2Map(String xmlStr) {
//        Map<String, Object> result = new HashMap<>();
//        if (xmlStr == null || xmlStr.trim().isEmpty()) {
//            return result;
//        }
//
//        // 移除XML声明和空白字符
//        xmlStr = xmlStr.replaceAll("<?xml[^>]*?>", "").trim();
//
//        // 简单的XML解析，处理嵌套结构
//        parseXmlElement(xmlStr, result);
//
//        return result;
//    }
//
//    /**
//     * 递归解析XML元素
//     * @param xmlStr XML字符串
//     * @param parentMap 父级Map
//     */
//    private static void parseXmlElement(String xmlStr, Map<String, Object> parentMap) {
//        if (xmlStr == null || xmlStr.trim().isEmpty()) {
//            return;
//        }
//
//        // 查找第一个开始标签
//        int startTagStart = xmlStr.indexOf('<');
//        if (startTagStart == -1) {
//            return;
//        }
//
//        int startTagEnd = xmlStr.indexOf('>', startTagStart);
//        if (startTagEnd == -1) {
//            return;
//        }
//
//        String startTag = xmlStr.substring(startTagStart + 1, startTagEnd).trim();
//        String tagName = startTag.split("\\s+")[0]; // 获取标签名，忽略属性
//
//        // 查找对应的结束标签
//        String endTag = "</" + tagName + ">";
//        int endTagStart = xmlStr.indexOf(endTag, startTagEnd);
//
//        if (endTagStart == -1) {
//            // 自闭合标签或没有结束标签
//            parentMap.put(tagName, "");
//            return;
//        }
//
//        // 提取标签内容
//        String content = xmlStr.substring(startTagEnd + 1, endTagStart).trim();
//
//        if (content.isEmpty()) {
//            parentMap.put(tagName, "");
//        } else if (content.startsWith("<")) {
//            // 包含子元素
//            Map<String, Object> childMap = new HashMap<>();
//            parseXmlElement(content, childMap);
//            parentMap.put(tagName, childMap);
//        } else {
//            // 纯文本内容
//            parentMap.put(tagName, content);
//        }
//
//        // 处理剩余的内容
//        String remaining = xmlStr.substring(endTagStart + endTag.length()).trim();
//        if (!remaining.isEmpty()) {
//            parseXmlElement(remaining, parentMap);
//        }
//    }
//
//    public static void main(String[] args) {
//        java.util.ArrayList tel = new java.util.ArrayList();
//        // tel.add("15960239092");
//        tel.add("13859980911");
//        XMZQPostUtil.sendSms(tel, "陈鹏", "wen da 测试");
//        //XMZQPostUtil.strToAscII("18658283088", "2014-06-25");
//
//    }
//}
