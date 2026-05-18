# k-dev-app 及依赖模块安全扫描报告（2026-05-16）

## 1. 扫描范围与说明

- 范围模块：`k-dev-app`、`k-dev-core`、`k-dev-sys`、`k-dev-resources`
- 依赖范围：`k-dev-app` 最终 `compile/runtime` 依赖（基于 `mvn dependency:tree`）
- 扫描时间：2026-05-16
- 状态更新：2026-05-18（增量）
- 外部漏洞源：OSV（Maven ecosystem）+ NVD（按 CVE 详情页）
- 代码扫描方式：静态审计（接口暴露、鉴权绕过、重定向、命令执行、敏感配置）

> 说明：已检查当前可用 skills，未发现专门的“安全漏洞扫描”skill；本次使用依赖清单 + 公共漏洞库 + 代码审计方式完成。

## 2. 总体结论

- 依赖层风险较高：共命中 `82` 条漏洞记录（去重按 `组件+版本+CVE/GHSA`）。
- 严重度分布：`CRITICAL=3`、`HIGH=32`、`MODERATE=35`、`LOW=12`。
- 主要集中组件：
  - `org.apache.tomcat.embed:tomcat-embed-core:9.0.98`（21 条）
  - `io.netty` 系列（23 条）
  - `org.springframework` 系列（19 条）
- 代码层仍存在“对外暴露可直接利用”的问题，重点是未鉴权接口面过大与开放跳转。

## 3. 依赖漏洞扫描结果（公开漏洞库）

### 3.1 高危组件分布（按命中条数）

| 组件 | 命中数 |
|---|---:|
| org.apache.tomcat.embed:tomcat-embed-core | 21 |
| io.netty:netty-codec-http | 10 |
| org.springframework:spring-webmvc | 8 |
| org.yaml:snakeyaml | 7 |
| org.springframework:spring-web | 6 |
| ch.qos.logback:logback-core | 5 |

### 3.2 Critical/High 漏洞明细（35 条）

| 组件 | 版本 | 漏洞ID | 严重度 | 参考 |
|---|---|---|---|---|
| ch.qos.logback:logback-classic | 1.2.9 | CVE-2023-6378 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2023-6378 |
| ch.qos.logback:logback-core | 1.2.9 | CVE-2023-6378 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2023-6378 |
| com.fasterxml.jackson.core:jackson-core | 2.13.5 | CVE-2025-52999 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-52999 |
| commons-io:commons-io | 2.11.0 | CVE-2024-47554 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2024-47554 |
| io.netty:netty-codec | 4.1.101.Final | CVE-2026-42583 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-42583 |
| io.netty:netty-codec-dns | 4.1.101.Final | CVE-2026-42579 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-42579 |
| io.netty:netty-codec-http | 4.1.101.Final | CVE-2026-33870 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-33870 |
| io.netty:netty-codec-http | 4.1.101.Final | CVE-2026-42584 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-42584 |
| io.netty:netty-codec-http | 4.1.101.Final | CVE-2026-42587 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-42587 |
| io.netty:netty-codec-http2 | 4.1.101.Final | CVE-2025-55163 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-55163 |
| io.netty:netty-codec-http2 | 4.1.101.Final | CVE-2026-33871 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-33871 |
| io.netty:netty-codec-http2 | 4.1.101.Final | CVE-2026-42587 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-42587 |
| io.netty:netty-codec-smtp | 4.1.101.Final | CVE-2025-59419 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-59419 |
| io.netty:netty-handler | 4.1.101.Final | CVE-2025-24970 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-24970 |
| org.apache.tomcat.embed:tomcat-embed-core | 9.0.98 | BIT-tomcat-2025-24813 | CRITICAL | https://nvd.nist.gov/vuln/detail/CVE-2025-24813 |
| org.apache.tomcat.embed:tomcat-embed-core | 9.0.98 | BIT-tomcat-2025-48988 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-48988 |
| org.apache.tomcat.embed:tomcat-embed-core | 9.0.98 | BIT-tomcat-2025-48989 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-48989 |
| org.apache.tomcat.embed:tomcat-embed-core | 9.0.98 | BIT-tomcat-2025-52520 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-52520 |
| org.apache.tomcat.embed:tomcat-embed-core | 9.0.98 | BIT-tomcat-2025-53506 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-53506 |
| org.apache.tomcat.embed:tomcat-embed-core | 9.0.98 | BIT-tomcat-2025-55752 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-55752 |
| org.apache.tomcat.embed:tomcat-embed-core | 9.0.98 | BIT-tomcat-2026-24734 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-24734 |
| org.apache.tomcat.embed:tomcat-embed-core | 9.0.98 | BIT-tomcat-2026-29145 | CRITICAL | https://nvd.nist.gov/vuln/detail/CVE-2026-29145 |
| org.apache.tomcat.embed:tomcat-embed-core | 9.0.98 | BIT-tomcat-2026-34483 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-34483 |
| org.apache.tomcat.embed:tomcat-embed-core | 9.0.98 | BIT-tomcat-2026-34487 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-34487 |
| org.springframework.boot:spring-boot | 2.7.18 | CVE-2025-22235 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-22235 |
| org.springframework.boot:spring-boot | 2.7.18 | CVE-2026-40973 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2026-40973 |
| org.springframework:spring-core | 5.3.31 | CVE-2025-41249 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2025-41249 |
| org.springframework:spring-web | 5.3.31 | CVE-2016-1000027 | CRITICAL | https://nvd.nist.gov/vuln/detail/CVE-2016-1000027 |
| org.springframework:spring-web | 5.3.31 | CVE-2024-22243 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2024-22243 |
| org.springframework:spring-web | 5.3.31 | CVE-2024-22259 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2024-22259 |
| org.springframework:spring-web | 5.3.31 | CVE-2024-22262 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2024-22262 |
| org.springframework:spring-webmvc | 5.3.31 | CVE-2024-38816 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2024-38816 |
| org.springframework:spring-webmvc | 5.3.31 | CVE-2024-38819 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2024-38819 |
| org.yaml:snakeyaml | 1.30 | CVE-2022-1471 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2022-1471 |
| org.yaml:snakeyaml | 1.30 | CVE-2022-25857 | HIGH | https://nvd.nist.gov/vuln/detail/CVE-2022-25857 |

### 3.3 风险解读（按可利用面）

- `Tomcat 9.0.98`：命中多条 2025~2026 年漏洞，含 `CRITICAL`，若系统对外开放 HTTP/HTTPS，建议优先升级。
- `Spring Boot 2.7.18 / Spring 5.3.31`：持续命中 URL 解析、路径处理、授权匹配等安全公告，属于长期暴露面。
- `Netty 4.1.101.Final`：集中在 HTTP request smuggling/解码与资源耗尽类漏洞，网关或代理场景风险更高。
- `SnakeYAML 1.30`：命中 `CVE-2022-1471`（反序列化相关）及多条 DoS 漏洞，若解析不可信 YAML 风险显著。

## 4. 代码层安全发现（对外开放时重点）

### 4.0 缺陷跟踪（Bug TODO）

状态枚举：
- `TODO`：待处理
- `DOING`：处理中
- `DONE`：已修复
- `RISK_ACCEPTED`：已评估并接受风险

| 编号 | 缺陷 | 状态（TODO/DOING/DONE/RISK_ACCEPTED） |
|---|---|---|
| BUG-001 | 过滤器忽略路径包含业务接口片段（可直接放行） | DONE |
| BUG-002 | 文件下载类接口仍为 `@ApiIgnore` | TODO |
| BUG-003 | 任务执行接口未鉴权 | DONE |
| BUG-004 | DevOps 接口未鉴权（可触发数据复制流程） | DONE |
| BUG-005 | 开放重定向（`/sys-tool-box/to-url`） | DONE |
| BUG-006 | 敏感密钥/令牌硬编码在配置文件 | DOING |
| BUG-007 | 动态类执行与命令执行能力存在 | DOING |
| BUG-008 | Tomcat 依赖升级与引入方式优化（`tomcat.version=9.0.118`，移除手工 `exclusions`） | DONE |
| BUG-009 | Spring WebMVC 风险处置（不升级 Boot3：升级到 `spring-framework.version=5.3.39` + 路径穿越双重编码拦截） | DONE |
| BUG-010 | SnakeYAML 依赖升级（`snakeyaml.version=2.6`，替换 `1.30`） | DONE |
| BUG-011 | 日志框架已切换为 Log4j2（替代 Logback 1.2.x 残余 CVE 风险） | DONE |
| BUG-012 | Sonar：上传大小限制过宽（`java:S5693`，多环境配置 `512MB/1024MB`） | RISK_ACCEPTED |
| BUG-013 | Sonar：SSL/TLS 证书校验缺失（`java:S4830`） | DONE |
| BUG-014 | Sonar：临时文件/公共可写目录使用风险（`java:S5443`） | DONE |
| BUG-015 | Sonar：弱加密算法/模式/填充（`java:S5542`、`java:S5547`） | TODO |
| BUG-016 | Sonar：潜在硬编码敏感信息/硬编码 IP（`java:S6418`、`java:S2068`、`java:S1313`） | DONE |

说明：
- `5.3.39` 是 Maven Central 可获取的开源 `5.3.x` 上限版本；部分 `5.3.40+ / 5.3.41+ / 5.3.44` 修复属于商业发布，当前策略下仍有残余风险。
- `BUG-011` 已完成：主工程已切换 `spring-boot-starter-log4j2`，并排除 `spring-boot-starter-logging`，不再使用 `logback 1.2.x` 链路。
- `BUG-012` 已按现场大文件传输需求暂不收紧阈值，状态标记为 `RISK_ACCEPTED`；后续若具备分片/直传条件再回收风险。

### 4.1 高风险：鉴权绕过面（仍待处理项）

1. （已修复）过滤器忽略路径包含业务接口片段（请求可直接放行）
- 证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/auth/KAuthFilter.java:99`
- 行为：已移除高风险默认忽略项，并将忽略匹配由 `contains` 改为规范化路径的安全匹配（精确/前缀边界 + Ant 风格通配）。
- 证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/auth/KAuthFilter.java:251`

2. 文件下载类接口仍是 `@ApiIgnore`
- 证据：
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysFileController.java:160`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysFileController.java:171`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysFileController.java:186`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysFileController.java:202`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysFileController.java:218`
- 影响：即使路径穿越已修复，接口仍可能被匿名探测/下载业务可访问文件。

3. （已修复）任务执行接口已改为鉴权执行
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysTaskController.java:100`

4. （已修复）DevOps 接口入口已下线
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/DevOpsController.java:19`

### 4.2 高风险：开放重定向（已修复）

- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysToolBoxController.java:55`
- 说明：`/to-url` 映射方法已移除。

### 4.3 中风险：敏感密钥/令牌硬编码在配置文件

- 证据：`k-dev-app/src/main/resources/application.properties:35`
- 证据：`k-dev-app/src/main/resources/application.properties:52`
- 证据：`k-dev-app/src/main/resources/application.properties:57`
- 证据：`k-dev-app/src/main/resources/application.properties:58`
- 影响：源码泄露即导致 token 伪造、外部服务密钥泄露、跨环境横向移动风险。
- 状态补充（`BUG-006`）：`DOING`。代码层默认硬编码已收敛（`sso.key` 默认值移除、`encrypt.aes.secret/sm4.*` 代码默认值移除），当前主要剩余为配置文件中的明文值待外置（启动脚本/环境变量注入）。

### 4.4 中风险：动态类执行与命令执行能力存在

- 证据（动态类执行）：`k-dev-core/src/main/java/com/kingsware/kdev/core/cron/DynamicTask.java:345`
- 证据（命令执行能力）：`k-dev-core/src/main/java/com/kingsware/kdev/core/util/ShellUtils.java:31`
- 影响：若上游任务配置/参数被污染，可能扩大为代码执行链。
- 状态补充（2026-05-17）：已完成“动态类执行”入口收敛 + 执行点白名单校验；`ShellUtils` 风险仍待收敛。

### 4.5 Sonar 增量发现补充（来自 `docs/sonar-k-dev-cli-api.md`）

1. 上传大小阈值过大（`java:S5693`）
- 证据：
  - `k-dev-all-in-one/src/main/resources/application-pine.properties:20`
  - `k-dev-all-in-one/src/main/resources/application-pine.properties:21`
  - `k-dev-app-tongweb/src/main/resources/application-pine.properties:22`
  - `k-dev-app-tongweb/src/main/resources/application-pine.properties:23`
  - `k-dev-app/src/main/resources/application-pine.properties:21`
  - `k-dev-app/src/main/resources/application-pine.properties:22`
  - `k-dev-app-with-ops/src/main/resources/application-pine.properties:22`
  - `k-dev-app-with-ops/src/main/resources/application-pine.properties:23`
- 风险：超大 multipart 请求体可导致内存/磁盘耗尽，放大 DoS 面。
- 建议：按业务上限收敛为最小可用值（建议 `max-file-size<=20MB`、`max-request-size<=20MB` 起步），并对上传接口加鉴权、限流、内容检测。

2. TLS 证书校验缺失（`java:S4830`）
- 证据：
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/i18n/HttpGet.java:47`
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/i18n/HttpGet.java:48`
- 风险：可被中间人攻击篡改响应内容。
- 建议：启用默认主机名校验和证书链校验，禁止 `TrustAll`/跳过校验逻辑。

3. 临时文件/目录使用风险（`java:S5443`）
- 证据：
  - `k-dev-app-with-ops/src/main/java/com/kingsware/kdev/uniops/service/impl/UniOpsServiceImpl.java:298`
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/util/ZipUtils.java:82`
- 风险：在公共可写目录下可能被抢占、链接攻击或敏感内容泄露。
- 建议：使用应用私有目录并限制权限（`700/600`），文件名随机化且及时删除。
- 当前状态（`BUG-014`）：`DONE`（已完成代码整改，改为统一安全临时文件创建并补充用后删除）。

4. 弱加密算法/模式（`java:S5542`、`java:S5547`）
- 证据：
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/util/DesUtil.java:37`
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/util/DesUtil.java:62`
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/util/RSAUtils.java:80`
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/util/AESUtil.java:48`
- 风险：DES/不安全模式抗攻击能力弱，存在数据泄露风险。
- 建议：统一迁移到 `AES/GCM/NoPadding`；RSA 使用 `OAEP(SHA-256)`，禁用 DES。

5. 潜在硬编码敏感信息与硬编码 IP（`java:S6418`、`java:S2068`、`java:S1313`）
- 证据：
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/task/KUserPasswordTask.java:39`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysUserServiceImpl.java:956`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysSsoServiceImpl.java:53`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/log/OperateLogConsumer.java:31`
- 风险：源码泄露后可直接利用；IP 硬编码导致策略不可审计、不可轮换。
- 建议：密钥/口令/IP 白名单全部外置到环境配置或密钥管理，当前项先做逐条复核并标注误报/真问题。
- 处置补充：`KUserPasswordTask` 的 `S2068` 已在源码做定点抑制（`@SuppressWarnings("java:S2068")`），该处 `"password"` 为数据库字段名，不是硬编码口令。
- 处置补充：`SysUserServiceImpl.passwordValidate(...)` 的 `S2068` 已在源码做定点抑制（`@SuppressWarnings("java:S2068")`），该处 `"password"` 为参数/配置键命名，不是硬编码口令。
- 处置补充：`SysSsoServiceImpl` 已移除 `sso.key` 的硬编码默认值，改为必须配置（未配置即拒绝登录）。
- 处置补充：`SysSsoServiceImpl.DEFAULT_SSO_LOGIN_IP_WHITELIST` 的 `S1313` 已做源码定点抑制（`@SuppressWarnings("java:S1313")`）；该值为局域网访问安全基线，且可由 `sso.login.ip-whitelist` 覆盖。
- 处置补充：`AESUtil` 历史演示代码中的硬编码样例已移除。

## 5. 本轮已验证“已修复/已加固”项

1. Referer 放行权限逻辑已移除
- 证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/util/ServletUtil.java:87`
- 证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/auth/KAuthFilter.java:367`
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/devops/DevOpsManager.java:139`

2. 集群消息接口已加“仅局域网访问”校验
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/InstanceController.java:47`
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/InstanceController.java:53`

3. 文件下载路径穿越修复已落地（标准化 + 根目录约束）
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysFileServiceImpl.java:679`
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysFileServiceImpl.java:722`

4. 任务执行接口已加授权并改为 `POST`
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysTaskController.java:99`
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysTaskController.java:100`

5. DevOpsController 已下线（不再作为 Controller 注册）
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/DevOpsController.java:19`
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/DevOpsController.java:20`

6. 开放重定向接口 `/sys-tool-box/to-url` 已移除
- 证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysToolBoxController.java:55`

7. 日志框架已切换为 Log4j2（`BUG-011` 已关闭）
- 版本证据：`pom.xml` 使用 `spring-boot-starter-log4j2`，并排除 `spring-boot-starter-logging`
- 排查结论：未检出 `ch.qos.logback:logback-classic/logback-core` 直接依赖声明
- 风险结论：`BUG-011` 由 `RISK_ACCEPTED` 调整为 `DONE`

8. TLS 证书校验缺失（`BUG-013`）已修复
- 修复证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/i18n/HttpGet.java:13`
- 修复证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/i18n/HttpGet.java:21`
- 修复证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/i18n/HttpGet.java:42`
- 修复结论：已移除 `TrustAll` 自定义 `X509TrustManager` 与手工 `SSLSocketFactory` 注入，恢复 JDK 默认 HTTPS 证书链与主机名校验。

9. 临时文件/公共可写目录风险（`BUG-014`）已修复
- 修复证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/util/FileUtils.java:259`
- 修复证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/util/FileUtils.java:302`
- 修复证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/util/FileUtils.java:412`
- 修复证据：`k-dev-app-with-ops/src/main/java/com/kingsware/kdev/uniops/service/impl/UniOpsServiceImpl.java:298`
- 修复证据：`k-dev-app-with-ops/src/main/java/com/kingsware/kdev/uniops/service/impl/UniOpsServiceImpl.java:325`
- 修复证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/util/ZipUtils.java:82`
- 修复结论：统一改为 `FileUtils.createTempFile` 安全临时文件入口（含目录/文件权限收口），并在 UniOps 菜单发布场景增加 `finally` 删除临时文件。

10. `S2068` 误报抑制（`BUG-016` 子项）
- 修复证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/task/KUserPasswordTask.java:39`
- 修复证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysUserServiceImpl.java:956`
- 修复结论：已采用源码定点抑制 `@SuppressWarnings("java:S2068")`；原因是 SQL 中 `"password"` 为字段名，不属于硬编码密码。

11. `S6418` 硬编码密钥修复（`BUG-016` 子项）
- 修复证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysSsoServiceImpl.java:53`
- 修复证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysSsoServiceImpl.java:55`
- 修复结论：`sso.key` 不再使用硬编码默认值，改为运行时必配；未配置时拒绝登录并记录告警日志。

12. `S1313` 白名单默认值定点抑制（`BUG-016` 子项）
- 修复证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysSsoServiceImpl.java:36`
- 修复结论：`DEFAULT_SSO_LOGIN_IP_WHITELIST` 已加 `@SuppressWarnings("java:S1313")`，保留可读默认安全基线并支持配置覆盖。

13. `S1313` 日志上报默认地址收敛（`BUG-016` 子项）
- 修复证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/log/OperateLogConsumer.java:31`
- 修复结论：`uniops.log.url` 默认值已收敛为空字符串，不再内置硬编码 IP 地址。

14. 代码层密钥默认值收敛（`BUG-006` 子项）
- 修复证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysUserServiceImpl.java:1102`
- 修复证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/initialize/KFaasInitialize.java:71`
- 修复证据：`k-dev-sys/src/main/java/com/kingsware/kdev/sys/initialize/KFaasInitialize.java:74`
- 修复结论：已移除代码中的 `encrypt.aes.secret`、`encrypt.sm4.key`、`encrypt.sm4.iv` 硬编码默认值（含 `SysTaskServiceImpl` 遗留默认字段删除）；当使用加解密路径时改为必须配置。

15. `app.ignore.urls` 绕过风险修复（`BUG-001`）
- 修复证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/auth/KAuthFilter.java:99`
- 修复证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/auth/KAuthFilter.java:122`
- 修复证据：`k-dev-core/src/main/java/com/kingsware/kdev/core/auth/KAuthFilter.java:251`
- 修复结论：默认忽略项收敛为 `/websocket;/eiac`，并将忽略规则匹配升级为规范化路径安全匹配，避免 `contains` 误放行业务路径。

## 6. 修复优先级建议

### P0（立即）

- 升级 `tomcat-embed-*`、`spring-boot/spring-*`、`netty-*`、`snakeyaml` 到当前受支持安全版本（先在兼容分支验证）。
- 收敛 `@ApiIgnore`：优先处理下载类接口（补鉴权 + 文件归属授权 + 最小权限）。
- 对历史高危接口变更（任务执行、DevOps、重定向移除）补充回归测试与发布公告，避免调用方遗留依赖。
- 轮换所有已提交的 `tokenSecret`、`encrypt.*`、第三方 key，并改为环境变量/密钥管理服务。

### P1（1 周内）

- 给下载类接口补充“鉴权 + 文件归属授权 + 限流 + 审计日志”。
- 给高危操作接口统一加二次校验（来源、操作人、MFA/动态验证码）。
- 对 `DynamicTask` 与 `ShellUtils` 增加调用白名单与审计。

### P2（持续）

- 在 CI 增加 SCA（依赖漏洞）和 SAST（代码规则）门禁。
- 每周自动更新依赖漏洞基线，阻断新增 Critical/High 漏洞进入主干。

## 7. 参考来源

- OSV Maven Query API: https://api.osv.dev/v1/query
- NVD: https://nvd.nist.gov
- 示例（本报告已命中）：
  - https://nvd.nist.gov/vuln/detail/CVE-2025-24813
  - https://nvd.nist.gov/vuln/detail/CVE-2026-29145
  - https://nvd.nist.gov/vuln/detail/CVE-2026-42584
  - https://nvd.nist.gov/vuln/detail/CVE-2025-22235
  - https://nvd.nist.gov/vuln/detail/CVE-2022-1471
