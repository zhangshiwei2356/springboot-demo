# springboot-demo

企业级 Spring Boot 单机应用脚手架（JDK 21、Spring Boot 3.x、无 Lombok、无 MapStruct、五层架构 + 四层泛型基类）。

## 项目结构

单模块 Maven 工程，源码按包划分职责：

| 包路径 | 说明 |
|--------|------|
| `com.demo.common` | `BaseController` / `BaseService` / `BaseConverter` / `BaseHandler`、`Result`、`UserContext`、`GlobalException`、`JwtUtil`、Boot 自动配置 |
| `com.demo.auth` | 演示登录签发 JWT（密码固定 `123456`） |
| `com.demo.system` | 内存用户表 + SKU 商品价格 |
| `com.demo.business` | 业务域：公司/订单/档案等 CRUD、下单链路（Controller → Service → Converter → Handler → Mapper） |
| `com.demo.app` | 启动类、JWT 校验、静态页路由、Knife4j 文档 |

数据库脚本：`sql/schema.sql`（接入 MySQL 时参考，当前演示默认不使用）。

## 环境约定

1. JDK **21**，Maven **3.9+**。
2. 本 Demo **不要求 MySQL / Redis / Nacos**：订单内存占位；`RedisUtils` 为未接 Redis 的占位实现。
3. **单机运行**：一个进程，默认端口 **9000**。

## JWT 密钥

`src/main/resources/application.yml` 中 `demo.security.jwt.secret` 用于签发与校验；生产务必改为安全配置源注入。

## 启动

```bash
mvn clean install
mvn spring-boot:run
```

或在 IDE 中运行 `com.demo.app.DemoApplication`。

## 页面演示（前后端一体）

启动后浏览器访问：

| 地址 | 说明 |
|------|------|
| http://localhost:9000/ | **登录页**，成功后跳转管理后台 |
| http://localhost:9000/admin.html | 管理后台（需已登录） |
| http://localhost:9000/index.html | API 联调页（需已登录） |

演示账号：**userId=1/2/10086**，密码 **123456**。

## 演示调用（API）

### 登录拿 Token

```http
POST http://localhost:9000/auth/api/auth/login
Content-Type: application/json

{"userId":1,"password":"123456"}
```

### 提交订单

```http
POST http://localhost:9000/business/api/orders/submit
Authorization: Bearer <token>
Content-Type: application/json

{"buyerUserId":1,"productCode":"SKU-DEMO","quantity":2}
```

接口文档：`/doc.html`；兼容入口 `/auth/doc.html` 等会重定向到统一文档页。

## 编译与测试

```bash
mvn clean install
mvn test
```

## 架构约束回顾

- 禁止 Lombok、禁止 MapStruct；实体/DTO/VO 均手写构造器与访问器。  
- 业务代码必须继承四大基类并走 **Controller → Service → Converter → Handler → Mapper**。  
- 跨模块调用收口在 **Handler**（进程内 `SystemUserClient` / `SystemProductClient`）；对象转换收口在 **Converter**。  
- 异步线程中需**手动**设置/清理 `UserContext` 与 MDC（示例见 `OrderAsyncNotifyService`）。
