package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.cache.instance.HostInfo;
import com.kingsware.kdev.core.cache.license.MacAddress;
import com.kingsware.kdev.core.context.SpringContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.*;

/**
 * @author chenp
 * @date 2023/2/21
 */
@Slf4j
public class SystemUtil {

    private static String myIp = null;
    /**
     * 获取主机信息
     * @return
     */
    @SneakyThrows
    public static HostInfo getHost() {
        HostInfo hostInfo = new HostInfo();
        String ip = "";

        String ip = "127.0.0.1";
        // 如果有配置ip，直接从配置里读取
        String configIp = SpringContext.getProperties("network.ip", "");
        if (StringUtils.isNotEmpty(configIp)) {
            ip = configIp;
        }
        else {
            if (myIp != null) {
                ip = myIp;
            }
            else {
                Optional<Inet4Address> inet4Address = IpUtil.
                        getLocalIp4Address();
                if (inet4Address.isPresent()) {
                    ip = inet4Address.get().getHostAddress();
                    myIp = ip;
                }
            }

        }
        else {
            ip = getLocalIPv4Address();
        }

        hostInfo.setHostName(ip);
        hostInfo.setPort(Integer.parseInt(SpringContext.getProperties("server.port", "8080")));
        hostInfo.setClusterNo(SpringContext.getInt("app.cluster-no", 1));
        return hostInfo;

    }

    public static String getLocalIPv4Address() {
        try {
            List<MacAddress> macAddresses = getMacs();
            if (macAddresses.isEmpty()) {
                return "127.0.0.1";
            }
            // 先按mac名称升序排序
            macAddresses.sort(Comparator.comparing(MacAddress::getName));
            for (MacAddress macAddress: macAddresses) {
                if (macAddress.getName().startsWith("en")) {
                    return macAddress.getIp();
                }
            }
            return macAddresses.get(0).getIp();
        }
        catch (Exception e) {
            log.error("getLocalIPv4Address error", e);
            return "127.0.0.1";
        }
    }

    public static List<MacAddress> getMacs() {
        List<MacAddress> macAddresses = new ArrayList<>();
        try {
            // 根据网卡地址进行过滤
            String[] ignoreMacs = new String[]{
                    "00:0F:4B", // Virtual Iron Software, Inc. (was: Katana Technology)
                    "00:13:07", //  Paravirtual Corporation (was: Accenia, Inc.)
                    "00:13:BE", //  Virtual Conexions
                    "00:21:F6", //  Virtual Iron Software
                    "00:24:0B", //  Virtual Computer Inc.
                    "00:A0:B1", //  First Virtual Corporation
                    "00:E0:C8", //  virtual access, ltd.
                    "54:52:00", //  linux kernal virtual machine (kvm)
                    "00:0F:4B", //  Virtual Iron Software, Inc. (was: Katana Technology)
                    "00:13:07", //  Paravirtual Corporation (was: Accenia, Inc.)
                    "00:13:BE", //  Virtual Conexions
                    "00:21:F6", //  Oracle Corporation (was: Virtual Iron Software)
                    "00:24:0B", //  Virtual Computer Inc.
                    "00:A0:B1", //  First Virtual Corporation
                    "00:E0:C8", //  virtual access, ltd.
                    "18:92:2C", //  Virtual Instruments
                    "3C:F3:92", //  Virtualtek. Co. Ltd
            };
            Set<String> ignoreMacSet = new HashSet<>(Arrays.asList(ignoreMacs));


            java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            StringBuilder sb = new StringBuilder();
            while (en.hasMoreElements()) {
                NetworkInterface networkInterface = en.nextElement();
                List<InterfaceAddress> addresses = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress address : addresses) {
                    InetAddress ip = address.getAddress();
                    NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                    if (network == null) {
                        continue;
                    }
                    // 排除掉回环接口、虚拟接口及非活跃状态的接口
                    if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                        continue;
                    }

                    byte[] mac = network.getHardwareAddress();

                    if (mac == null) {
                        continue;
                    }
                    sb.delete(0, sb.length());
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    String macName = network.getName();;
                    String displayName = network.getDisplayName();
                    // 忽略的网卡地址
                    Set<String> strings = new HashSet<>();
                    strings.add("awdl");
                    strings.add("lo");
                    strings.add("docker");
                    strings.add("veth");
                    strings.add("tun");
                    strings.add("bridge");
                    strings.add("llw");
                    strings.add("utun");
                    strings.add("stf");
                    strings.add("gif");
                    strings.add("wg");
                    strings.add("tail");
                    strings.add("br");
                    boolean isIngore = false;
                    for (String s: strings) {
                        if (macName.startsWith(s) || displayName.startsWith(s)) {
                            isIngore = true;
                            break;
                        }
                    }
                    for (String s: ignoreMacSet) {
                        if (sb.toString().startsWith(s)) {
                            isIngore = true;
                            break;
                        }
                    }

                    if (isIngore) {
                        continue;
                    }
                    // 去掉虚拟网卡以及不活动网卡
                    if (network.isVirtual()) {
                        continue;
                    }
                    String ipAddress = "";
                    if (ip instanceof Inet4Address && !ip.isLoopbackAddress() && !ip.isLoopbackAddress()) {
                        ipAddress = ip.getHostAddress();
                    }
                    if (StringUtils.isEmpty(ipAddress)) {
                        continue;
                    }
                    if (sb.toString().toLowerCase().startsWith("00-50-56-C0".toLowerCase())) {
                        continue;
                    }
                    MacAddress macAddress = new MacAddress();
                    macAddress.setName(macName);
                    macAddress.setMac(sb.toString().trim());
                    macAddress.setIp(ipAddress);
                    macAddresses.add(macAddress);

                }
            }

        } catch (Exception ignored) {
        }
//        for (MacAddress macAddress: macAddresses) {
//            log.info("网卡信息，网卡名称：{}， Mac地址:{}, IP:{}", macAddress.getName(), macAddress.getMac(), macAddress.getIp());
//        }
        return macAddresses;
    }

    /**
     * 获取当前主机的名称。
     * 该方法首先尝试从环境变量"COMPUTERNAME"中获取主机名，这是Windows系统的标准环境变量。
     * 如果在Windows环境中未找到该变量，方法将尝试从"HOSTNAME"环境变量中获取主机名，这是Linux/Unix系统的标准环境变量。
     *
     * @return 返回当前主机的名称。如果无法获取主机名，则返回null。
     */
    public static String getHostName() {
        String machineName = System.getenv("COMPUTERNAME"); // 尝试获取Windows系统的主机名
        if (machineName == null) {
            machineName = System.getenv("HOSTNAME"); // 如果是Linux/Unix系统，尝试获取主机名
        }
        return machineName;
    }

}
