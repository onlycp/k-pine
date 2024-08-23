package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.i18n.I18n;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author chenp
 * @date 2023/11/7
 */
public class IpUtils {

    /**
     * 根据IP地址获取对应的地址
     * @param ip IP地址
     * @return 地址
     */
    public static String getAddressByIp(String ip) {
        String address = I18n.t("IpUtils.unknown", "未知") ;
        // 1、创建 searcher 对象
        String dbPath = "ip2region.xdb";
        Searcher searcher = null;
        try {
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (IOException e) {
            System.out.printf("failed to create searcher with `%s`: %s\n", dbPath, e);
            return address;
        }

        // 2、查询
        try {
            long sTime = System.nanoTime();

            address = searcher.search(ip);
            String[] arr = address.split("\\|");
            List<String> list = new ArrayList<>();
            if (arr.length > 1) {
                for (int i = 0; i < arr.length-1; i++) {
                    String a = arr[i];
                    if (!NumberUtils.isParsable(a)) {
                        list.add(a);
                    }
                }
            }

            address = String.join("", list);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", address, searcher.getIOCount(), cost);
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", ip, e);
        }
        finally {
            // 3、关闭资源
            try {
                searcher.close();
            } catch (IOException e) {
                System.out.printf("failed to close searcher: %s\n", e);
            }
        }
        return address;
    }

}
