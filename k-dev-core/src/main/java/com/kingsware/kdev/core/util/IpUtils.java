package com.kingsware.kdev.core.util;

import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
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
        String address = "未知";
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
