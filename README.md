# springboot-demo

企业级 Spring Boot 单机应用脚手架（JDK 21、Spring Boot 3.x、无 Lombok、无 MapStruct、五层架构 + 四层泛型基类）。

## 模块一览

| 模块 | 说明 |
|------|------|
| `cloud-common` | `BaseController` / `BaseService` / `BaseConverter` / `BaseHandler`、`Result`、`UserContext`、`GlobalException`、`JwtUtil`、`RedisUtils`、异步线程池、`logback`/MDC 约定、Boot 自动配置 |
| `cloud-auth` | 演示登录签发 JWT（密码固定 `123456`） |
| `cloud-system` | 内存用户表 + SKU 商品价格 |
| `cloud-business` | 下单完整链路：**Controller → Service → Converter → Handler → Mapper**，含 Spock + Mockito 单测示例 |
| `cloud-app` | **唯一启动入口**：聚合上述模块、JWT 校验、静态页、档案管理、Knife4j 文档 |

数据库脚本：`sql/schema.sql`（接入 MySQL 时参考，当前演示默认不使用）。

## 环境约定

1. JDK **21**，Maven **3.9+**。
2. 本 Demo **不要求 MySQL / Redis / Nacos**：订单内存占位；`RedisUtils` 为未接 Redis 的占位实现。
3. **单机运行**：只需启动 `cloud-app` 一个进程，默认端口 **9000**。

## JWT 密钥

`cloud-app` 的 `application.yml` 中 `cloud.security.jwt.secret` 用于签发与校验；生产务必改为安全配置源注入。

## 启动

```bash
mvn clean install
mvn spring-boot:run -pl cloud-app
```

或在 IDE 中运行 `com.cloud.app.CloudApplication`。

## 页面演示（前后端一体）

启动 **cloud-app** 后，浏览器访问：

| 地址 | 说明 |
|------|------|
| http://localhost:9000/ | **登录页**，成功后跳转管理后台 |
| http://localhost:9000/admin.html | 管理后台（需已登录） |
| http://localhost:9000/index.html | API 联调页（需已登录） |

演示账号：**userId=1/2/10086**，密码 **123456**。登录后展示用户名、角色、头像；左侧菜单可查询用户、询价、下单等。

## 演示调用（API）

API 路径与原微服务网关前缀保持一致，便于前端无改动迁移：

### 登录拿 Token

```http
POST http://localhost:9000/cloud-auth/api/auth/login
Content-Type: application/json

{"userId":1,"password":"123456"}
```

响应 `data.accessToken` 用作 `Authorization: Bearer <token>`。

### 提交订单

```http
POST http://localhost:9000/cloud-business/api/orders/submit
Authorization: Bearer <token>
Content-Type: application/json
X-Trace-Id: optional-custom-trace

{"buyerUserId":1,"productCode":"SKU-DEMO","quantity":2}
```

`buyerUserId` **必须与 JWT 解析出的 UID 一致**（JWT 过滤器已将 UID 写入 `X-Login-UserId`，业务侧也可用 `UserContext` 校验）。

接口文档：`/doc.html`、`/swagger-ui.html`；兼容入口 `/cloud-auth/doc.html` 等会重定向到统一文档页。

## 编译与测试

```bash
mvn clean install
mvn test -pl cloud-business
```

- `cloud-business` 内含 **Spock/Groovy + JUnit Mockito** 示例。  
- `OrderServiceImpl#page` 当前为占位实现；若需分页，请在父 POM 管理 `mybatis-plus-jsqlparser` 依赖并启用 `PaginationInnerInterceptor`（见 MyBatis-Plus 官方文档）。

## 架构约束回顾

- 禁止 Lombok、禁止 MapStruct；实体/DTO/VO 均手写构造器与访问器。  
- 业务代码必须继承四大基类并走 **Controller → Service → Converter → Handler → Mapper**。  
- 跨模块调用收口在 **Handler**（单机模式下为进程内 `SystemUserClient` / `SystemProductClient`）；对象转换收口在 **Converter**。  
- 异步线程中需**手动**设置/清理 `UserContext` 与 MDC（示例见 `OrderAsyncNotifyService`）。
