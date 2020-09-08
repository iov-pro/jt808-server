# jt808-server

基于 [spring-boot-starter-jt808](https://github.com/zhoyq/spring-boot-starter-jt808) 二次开发包的工程

- 多租户平台
- spring-boot-starter-jt808 保持最新版本

## 实体

- [x] GroupEntity ( uuid, parent, title, link, fromDate, updateDate, thruDate )
- [x] LinkEntity ( uuid, tableName, title, refUuid )
- [x] VehicleEntity ( uuid, license, provinceId, cityId, licenseColor, vin, fromDate, updateDate, thruDate )
- [x] DeviceEntity ( deviceId, manufacturer, model, rsa, fromDate, updateDate, thruDate )
- [x] DeviceMsgEntity ( uuid, user, vehicle, device, sim, msgId, stream, sendTime, sendData, replyId, replyTime, replyData, replyFlag)
- [x] VehicleDeviceSimLink ( uuid, license, device, sim, auth, fromDate, updateDate, thruDate )
- [x] UserGroupLink ( uuid, user, group, fromDate, updateDate, thruDate )

