# Security Audit Report (External Exposure)

- Project: `k-dev-cli-api`
- Audit date: `2026-05-14`
- Auditor: `Codex`
- Method: static code review (no live penetration test)

## 1. Executive Summary

This project currently has multiple externally exploitable risks if deployed to the Internet.

- Critical: 5
- High: 4
- Medium: 1

Top risks are concentrated in:

1. Unauthenticated sensitive endpoints
2. Auth bypass design (`ignoreUrls` + referer-based bypass)
3. File path handling and arbitrary file download/zip
4. Secret/default credential exposure

## 2. Scope

- Modules reviewed:
  - `k-dev-core`
  - `k-dev-sys`
  - `k-dev-app`
  - `k-dev-resources`
- Focus:
  - Authentication/authorization bypass
  - Dangerous unauthenticated APIs
  - File access risks
  - Exception/info leakage
  - Secret management

## 3. Findings

## Critical-01: Global auth bypass via `ignoreUrls` includes business path

- Risk: Critical
- Impact:
  - Requests matching ignored path bypass auth/security checks in filter.
  - `/sys-tool-box` APIs become directly reachable from Internet.
- Evidence:
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/auth/KAuthFilter.java:99`
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/auth/KAuthFilter.java:251`
- Exploitable chain:
  - `GET /api/v1/sys-tool-box/executeScript/{appId}/{sourceName}`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysToolBoxController.java:130`
  - Calls SQL execution: `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/DevModelSqlServiceImpl.java:90`
- Recommendation:
  - Remove `/sys-tool-box` from `app.ignore.urls`.
  - Keep `ignoreUrls` only for static/public minimal resources.
  - Add explicit auth for all toolbox endpoints.

## Critical-02: Unauthenticated cluster message receiver (`/instances/recvMessage`)

- Risk: Critical
- Impact:
  - Remote attacker can submit crafted topic/message to trigger internal actions.
  - Includes task execution, session manipulation, websocket broadcast.
- Evidence:
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/InstanceController.java:35`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/InstanceController.java:36`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/InstanceServiceImpl.java:43`
  - Java class task execution path: `k-dev-core/src/main/java/com/kingsware/kdev/core/cron/DynamicTask.java:345`
- Recommendation:
  - Require strong service-to-service auth (mTLS or HMAC signature + timestamp + nonce).
  - Restrict source IP to trusted cluster CIDR.
  - Reject unknown topics by strict allowlist.

## Critical-03: Unauthenticated Faas flow execution

- Risk: Critical
- Impact:
  - Any external user can call arbitrary flow by `flowId`, causing unauthorized business operations.
- Evidence:
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/KubboController.java:90`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/KubboController.java:91`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/KubboServiceImpl.java:50`
- Recommendation:
  - Remove `@ApiIgnore` and require auth+permission.
  - Add flow-level ACL (allowed app/user/role).
  - Add request rate limits for this API.

## Critical-04: File download/path handling risks (unauth + traversal/overreach)

- Risk: Critical
- Impact:
  - Unauthenticated file access endpoints.
  - Path validation order issue may allow encoded traversal bypass.
  - Static zip endpoint can pack arbitrary existing server paths.
- Evidence:
  - Open download endpoints:
    - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysFileController.java:170`
    - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysFileController.java:200`
    - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysFileController.java:185`
  - Validate `..` before decode:
    - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysFileServiceImpl.java:406`
    - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysFileServiceImpl.java:410`
  - Arbitrary file path ingestion in zip:
    - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysFileServiceImpl.java:642`
- Recommendation:
  - Require auth for all download APIs unless explicitly public.
  - Decode first, then canonicalize (`normalize` + real path check), enforce allowed root.
  - For zip API, only accept approved logical IDs, never raw filesystem path.

## Critical-05: Unauthenticated system config read

- Risk: Critical
- Impact:
  - Anonymous caller can read system config records; may leak sensitive operational data.
- Evidence:
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysConfigController.java:106`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysConfigServiceImpl.java:140`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysConfigServiceImpl.java:142`
- Recommendation:
  - Remove `@ApiIgnore`.
  - Return only strict public keys via allowlist, never full config table for anonymous users.

## High-01: Unauthenticated task trigger endpoint

- Risk: High
- Impact:
  - External caller can trigger scheduled tasks on demand; may cause data corruption/high load.
- Evidence:
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysTaskController.java:100`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysTaskController.java:101`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/service/impl/SysTaskServiceImpl.java:172`
- Recommendation:
  - Remove `@ApiIgnore`.
  - Require admin permission + operation audit + rate limit.

## High-02: Unauthenticated license activation endpoint

- Risk: High
- Impact:
  - Unauthorized modification of license status; can disrupt service behavior.
- Evidence:
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysLicenseController.java:51`
  - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/web/SysLicenseController.java:52`
- Recommendation:
  - Require admin auth and MFA/secondary approval for license changes.

## High-03: Stack trace and internal details returned to client

- Risk: High
- Impact:
  - Leaks class names, paths, SQL and internals; helps attackers pivot.
- Evidence:
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/auth/KAuthFilter.java:482`
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/exception/GlobalExceptionHandler.java:43`
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/exception/GlobalExceptionHandler.java:54`
- Recommendation:
  - Return generic error to client; keep full stack only in server logs.

## High-04: Referer-based auth bypass design

- Risk: High
- Impact:
  - If `app.ignore.referer` configured, attacker can spoof `Referer` header to bypass checks.
- Evidence:
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/util/ServletUtil.java:98`
  - `k-dev-core/src/main/java/com/kingsware/kdev/core/auth/KAuthFilter.java:367`
  - Related config mutation logic:
    - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/devops/DevOpsManager.java:147`
    - `k-dev-sys/src/main/java/com/kingsware/kdev/sys/devops/DevOpsManager.java:162`
- Recommendation:
  - Remove referer-based bypass entirely from auth decision.
  - Use signed server-side trust signals only.

## Medium-01: Hardcoded/reused secrets and default credentials in repo

- Risk: Medium
- Impact:
  - If reused in real environments, token forging and credential compromise become likely.
- Evidence:
  - `k-dev-app/src/main/resources/application.properties:35`
  - `k-dev-app/src/main/resources/application.properties:52`
  - `k-dev-app/src/main/resources/application-prod.properties:31`
  - `k-dev-resources/src/main/resources/initSql/Postgresql/version_70_1.sql:1088`
- Recommendation:
  - Rotate all secrets immediately.
  - Move secrets to env/secret manager.
  - Remove default admin/demo credentials from initialization scripts for production bundles.

## 4. Priority Remediation Plan

## P0 (within 24 hours)

1. Remove/disable Internet access for:
   - `/api/v1/instances/*`
   - `/api/v1/kubbo/execute/faas`
   - `/api/v1/sys-files/download*`
   - `/api/v1/sys-config/get-sys-config`
   - `/api/v1/sys-tasks/executeTask/*`
2. Remove `/sys-tool-box` from `app.ignore.urls`.
3. Disable stack trace response to clients.

## P1 (within 3 days)

1. Refactor file path security checks (decode -> normalize -> root-allowlist).
2. Add endpoint-level permission model for operational APIs (task/license/devops/cluster).
3. Remove referer-based bypass logic.

## P2 (within 1-2 weeks)

1. Secret rotation and secret-manager migration.
2. Add security regression tests for all previously unauth endpoints.
3. Add API gateway protections (WAF/rate limit/IP allowlist).

## 5. Notes

- This report is based on source-level review as of `2026-05-14`.
- Real exposure depends on deployment topology (gateway rules, private network, firewall, etc.).
- A follow-up dynamic penetration test is strongly recommended after fixes.
