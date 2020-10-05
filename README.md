# 交通标准808协议完整服务

## 介绍

基于 [spring-boot-starter-jt808](https://github.com/zhoyq/spring-boot-starter-jt808) 二次开发包的完整工程

## 版本特性

### v1.0.0

- 使用内嵌数据库 HsqlDB（自动创建数据库表，理论上兼容 `Mysql` 数据库，如果遇到问题可以提交 [`ISSUE`](https://github.com/zhoyq/spring-boot-starter-jt808/issues) ）
- 多租户数据结构
- 完整存储 808 协议相关数据
- 依赖 [spring-boot-starter-jt808](https://github.com/zhoyq/spring-boot-starter-jt808) 并保持最新版本
- 默认会添加 测试用设备和车辆

## 实体定义

### 字典和地区

- [x] DictionaryEntity ( uuid, key, value, type, fromDate, updateDate, thruDate )
- [x] DivisionEntity ( id, name )

### 设备、车辆、卡、以及定位数据的存储

- [x] GroupEntity ( uuid, parent, title, link, fromDate, updateDate, thruDate )
- [x] LinkEntity ( uuid, tableName, title, refUuid )
- [x] VehicleEntity ( uuid, license, provinceId, cityId, licenseColor, vin, fromDate, updateDate, thruDate )
- [x] DeviceEntity ( deviceId, manufacturer, model, rsa, fromDate, updateDate, thruDate )
- [x] DeviceMsgEntity ( uuid, user, vehicle, device, sim, msgId, stream, sendTime, sendData, replyId, replyTime, 
                        replyData, replyFlag)
- [x] VehicleDeviceSimLink ( uuid, license, device, sim, auth, fromDate, updateDate, thruDate )
- [x] UserGroupLink ( uuid, user, group, fromDate, updateDate, thruDate )

- [x] TraceEntity ( uuid, sim, traceAlarm, traceStatus, longitude, latitude, height, speed, direction, receiveTime, 
                    receiveServerTime, mileage, oilMass, recordSpeed, humanEnsureAlarmId, overSpeedPositionType, 
                    overSpeedPositionId, inOutPositionType, inOutPositionId, inOutDirection, driveTimePositionId,
                    driveTime, driveTimeResult, traceStatusExt, ioStatus, tirePressure, partTemp, ad0, ad1,
                    signalStrength, gnssNumber )
- [x] TraceAlarmEntity ( uuid, sim, alarmKey, startTime, endTime )

### 事件上报相关

- [x] EventEntity
- [x] EventReportEntity
- [x] DeviceEventEntity

### 信息点播相关

- [x] InfoEntity
- [x] InfoOrderEntity
- [x] DeviceInfoEntity

### 电子运单相关

- [x] ElectronicWaybillEntity

### 驾驶员信息相关

- [x] DriverInfoEntity

### CAN总线 多媒体 透传 压缩

- [x] MediaEntity 多媒体信息（不含实体）
- [x] UploadDataEntity 包含CAN总线、多媒体实体、透传数据、压缩数据

## 如何使用

> 请确保JDK版本高于15版本，如有其他版本的需要，则应修改 spring-boot-starter-jt808 的版本支持低版本JDK，并使用低版本JDK编译此源码。

```bash
# 打包
mvn clean package
# 运行
java -jar target/jt808-server.jar
# 808 服务端口默认是 10001
```

## FAQ

暂无

## 致谢

暂无

## 授权

使用 MIT 授权
