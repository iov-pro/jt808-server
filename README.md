# 交通标准808协议完整服务

## 介绍

基于 [spring-boot-starter-jt808](https://github.com/zhoyq/spring-boot-starter-jt808) 二次开发包的多租户车辆管理服务

## 版本特性

### v1.4.0

- 启动时，插入根组织
- 增加新建组织的接口
- 车辆、设备等和组织关联，主要用于多租户的权限范围管理
- 增加 车辆管理接口
- 增加 设备管理接口
- 增加 车辆 设备 卡号 连接管理接口
- 增加 虚拟群组（用户相关）接口
- 增加 设备消息管理接口
- 增加 下发指令接口
- 规则1 车辆、设备、卡 是 多对多对多 的关系 方便一车多设备管理 以设备多卡 或者 一设备多车的情况
- 规则2 组织内车辆、设备、卡号不可以重复添加

### v1.0.1

- 支持 `Mysql5` 版本的数据库，启动参数 `--spring.profiles.active=mysql5`

### v1.0.0

- 使用内嵌数据库 HsqlDB（自动创建数据库表，理论上兼容 `Mysql` 数据库，如果遇到问题可以提交 [`ISSUE`](https://github.com/iov-pro/jt808-server/issues) ）
- 多租户数据结构
- 完整存储 808 协议相关数据
- 依赖 [spring-boot-starter-jt808](https://github.com/zhoyq/spring-boot-starter-jt808) 并保持最新版本
- 默认会添加 测试用设备和车辆

## 如何使用

> 请确保JDK版本高于15版本，如有其他版本的需要，则应修改 spring-boot-starter-jt808 的版本支持低版本JDK，并使用低版本JDK编译此源码。

```bash
# 打包
mvn clean package
# 运行 （默认 HSQLDB）
java -jar target/jt808-server.jar
# 运行 （使用 MYSQL5）
# java -jar target/jt808-server.jar --spring.profiles.active=mysql5
# 808 服务端口默认是 10001
```

## FAQ

暂无

## 致谢

暂无

## 授权

使用 MIT 授权
