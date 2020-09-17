# jt808-server

基于 [spring-boot-starter-jt808](https://github.com/zhoyq/spring-boot-starter-jt808) 二次开发包的工程

- 多租户平台
- spring-boot-starter-jt808 保持最新版本

## 实体

- [x] GroupEntity ( uuid, parent, title, link, fromDate, updateDate, thruDate )
- [x] LinkEntity ( uuid, tableName, title, refUuid )
- [x] VehicleEntity ( uuid, license, provinceId, cityId, licenseColor, vin, fromDate, updateDate, thruDate )
- [x] DeviceEntity ( deviceId, manufacturer, model, rsa, fromDate, updateDate, thruDate )
- [x] DeviceMsgEntity ( uuid, user, vehicle, device, sim, msgId, stream, sendTime, sendData, replyId, replyTime, 
                        replyData, replyFlag)
- [x] VehicleDeviceSimLink ( uuid, license, device, sim, auth, fromDate, updateDate, thruDate )
- [x] UserGroupLink ( uuid, user, group, fromDate, updateDate, thruDate )
- [x] DictionaryEntity ( uuid, key, value, type, fromDate, updateDate, thruDate )
- [x] DivisionEntity ( id, name )
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

- [ ] InfoEntity
- [ ] InfoOrderEntity
- [ ] DeviceInfoEntity

### 电子运单相关

- [ ] ElectronicWaybillEntity

### 驾驶员信息相关

- [ ] DriverInfoEntity

### CAN总线 多媒体 透传 压缩

- [ ] MediaEntity 多媒体信息（不含实体）
- [ ] UploadDataEntity 包含CAN总线、多媒体实体、透传数据、压缩数据